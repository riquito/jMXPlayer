/**
 * jMXPlayer, a GUI to IEEE PAR1599 (MX) data
 * Copyright © 2010 Riccardo Attilio Galli <riccardo@sideralis.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package src.Controller;

import java.awt.Rectangle;
import java.io.File;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import src.Model.AudioClip;
import src.Model.GraphicInstance;
import src.Model.GraphicInstanceGroup;
import src.Model.MXData;
import src.Model.Voice;
import src.Util.PathExtension;
import src.Util.RectangleExtension;

public class MXHandler extends DefaultHandler {
	private MXData MX;

	private Boolean isMainTitle = false;
	private Boolean isWorkTitle = false;
	private String currentVoiceName = ""; // crnt = current

	private GraphicInstanceGroup currentGraphicInstanceGroup;
	private GraphicInstance currentGraphicInstance;
	private AudioClip currentAudioClip;

	public void startDocument() {
		this.MX = new MXData();
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		// author saltiamo per ora
		if (localName.equals("rest") || localName.equals("chord")) {
			this.MX.addSpine2Voice(attributes.getValue("event_ref"), this.currentVoiceName);
		} else if (localName.equals("voice")) {
			currentVoiceName = attributes.getValue("voice_item_ref");
			this.MX.addVoice(currentVoiceName, new Voice(currentVoiceName));
		} else if (localName.equals("graphic_event")) {
			int x = new Integer(attributes.getValue("upper_left_x"));
			int y = new Integer(attributes.getValue("upper_left_y"));
			int width = new Integer(attributes.getValue("lower_right_x")) - x;
			int height = new Integer(attributes.getValue("lower_right_y")) - y;
			Rectangle temporaryRectangle = new Rectangle(x, y, width, height);

			currentGraphicInstance.getSpine2point().put(attributes.getValue("event_ref"), temporaryRectangle);
			currentGraphicInstance.getTree().insertNewElement(temporaryRectangle);
			/* crnt_gInstance.tree.insertNewElement(tmpCoord); */
		} else if (localName.equals("track_event")) {
			Float num = new Float(attributes.getValue("start_time")) * 100;
			currentAudioClip.addSpineTime(attributes.getValue("event_ref"), num.intValue());
		} else if (localName.equals("graphic_instance")) {
			currentGraphicInstanceGroup.addInstance(new GraphicInstance());
			currentGraphicInstance.setRelativeImagePath(attributes.getValue("file_name"));
			// mancano nel nuovo xml spine_start_ref ed end_ref
		} else if (localName.equals("graphic_instance_group")) {
			currentGraphicInstanceGroup = new GraphicInstanceGroup(attributes.getValue("description"));
			this.MX.addGroup(currentGraphicInstanceGroup);
		} else if (localName.equals("track")) {
			currentAudioClip = this.MX.addAudioClip(attributes.getValue("file_name"));
		} else if (localName.equals("main_title")) {
			this.isMainTitle = true;
		} else if (localName.equals("work_title")) {
			this.isWorkTitle = true;
		}
	}

	public void endElement(String uri, String localName, String qName) {
		if (localName.equals("graphic_istance_group")) {
			this.currentGraphicInstanceGroup = null;
			this.currentGraphicInstance = null;
		}
	}

	public void characters(char[] chars, int start, int length) {
		if (isMainTitle)
			this.MX.setMovementTitle(new String(chars));
		else if (isWorkTitle)
			this.MX.setWorkTitle(new String(chars));
	}

	public static MXData parse(String path) {
		try {
			MXHandler handler = new MXHandler();
			XMLReader reader = XMLReaderFactory.createXMLReader();
			
			reader.setContentHandler(handler);
			reader.parse(path);
			handler.MX.setBaseDirectory(PathExtension.getParent(path));

			/*
			 * questa parte prende un audio a caso ed individua spineStart e spineEnd che ci
			 * servono ma non esistono piu' nel nuovo formato MX
			 */
			
			if (handler.MX.getAudioClipDictionary().values().isEmpty()) {
				return null;
			}
			
			AudioClip audioClip = handler.MX.getAudioClipDictionary().values().iterator().next();
			parseGraphicInstanceSpine(handler.MX.getGraphicInstanceGroup().values(), audioClip);

			return handler.MX;
		} catch (org.xml.sax.SAXException e) {
			System.err.println(e);
		} catch (java.io.IOException e) {
			System.err.println(e);
		} catch (NullPointerException e) {
		}
		return null;
	}

	private static void parseGraphicInstanceSpine(Collection<GraphicInstanceGroup> graphicInstanceGroupCollection, AudioClip audioClip) {
		String spineStart, spineEnd;
		int startTime, endTime, currentTime;
		
		for (GraphicInstanceGroup instanceGroup : graphicInstanceGroupCollection) {
			for (GraphicInstance graphicInstance : instanceGroup.getInstances()) {
				spineStart = null;
				spineEnd = null;
				startTime = 999999999;
				endTime = -1;

				for (String tmpSpine : graphicInstance.getSpine2point().keySet()) {
					currentTime = audioClip.getSpine2time().get(tmpSpine);

					if (currentTime < startTime) {
						startTime = currentTime;
						spineStart = tmpSpine;
					} else if (currentTime > endTime) {
						endTime = currentTime;
						spineEnd = tmpSpine;
					}
				}
				graphicInstance.setSpineStart(spineStart);
				graphicInstance.setSpineEnd(spineEnd);
			}
		}
	}
}
