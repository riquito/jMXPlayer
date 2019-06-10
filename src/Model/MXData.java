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

package src.Model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import src.Util.PathExtension;

import java.util.Collections;

public class MXData implements Serializable {
	private String workTitle;
	private String movementTitle;
	private String author;
	private String baseDirectory;

	// collezioni di dati fondamentali, accessibili a tutti
	private Hashtable<String, GraphicInstanceGroup> graphicInstanceGroup;
	private Hashtable<String, AudioClip> audioClipDictionary;
	private Hashtable<String, String> spine2voice;
	private Hashtable<String, Voice> voices;

	public MXData() {
		this.graphicInstanceGroup = new Hashtable<String, GraphicInstanceGroup>();
		this.audioClipDictionary = new Hashtable<String, AudioClip>();
		this.spine2voice = new Hashtable<String, String>();
		this.voices = new Hashtable<String, Voice>();
	}

	public void addGroup(GraphicInstanceGroup group) {
		this.getGraphicInstanceGroup().put(group.getDescription(), group);
	}

	public AudioClip addAudioClip(String relativePath) {
		AudioClip clip = new AudioClip();
		clip.setRelativePath(relativePath);
		this.getAudioClipDictionary().put(relativePath, clip);
		return clip;
	}
	
	public void addSpine2Voice(String key, String voiceName) {
		this.spine2voice.put(key, voiceName);
	}
	
	public void addVoice(String key, Voice voice) {
		this.voices.put(key, voice);
	}

	public String getMovementTitle() {
		return movementTitle;
	}

	public void setMovementTitle(String movementTitle) {
		this.movementTitle = movementTitle;
	}

	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public Hashtable<String, GraphicInstanceGroup> getGraphicInstanceGroup() {
		return graphicInstanceGroup;
	}

	public Hashtable<String, AudioClip> getAudioClipDictionary() {
		return audioClipDictionary;
	}

	public Hashtable<String, Voice> getVoices() {
		return voices;
	}

	public Hashtable<String, String> getSpine2voice() {
		return spine2voice;
	}
}
