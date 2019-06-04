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
	public String work_title;
	public String movement_title;
	public String author;
	public String baseDir;

	// collezioni di dati fondamentali, accessibili a tutti
	public Hashtable<String, GraphicInstanceGroup> graphic_instance_group;
	public Hashtable<String, AudioClip> audioClipDict;
	public Hashtable<String, String> spine2voice;
	public Hashtable<String, Voice> voices;

	public MXData() {
		this.graphic_instance_group = new Hashtable<String, GraphicInstanceGroup>();
		this.audioClipDict = new Hashtable<String, AudioClip>();
		this.spine2voice = new Hashtable<String, String>();
		this.voices = new Hashtable<String, Voice>();
	}

	public GraphicInstanceGroup addGroup(String description) {
		GraphicInstanceGroup group = new GraphicInstanceGroup(description);
		this.graphic_instance_group.put(description, group);
		return group;
	}

	public AudioClip addAudioClip(String relativePath) {
		AudioClip clip = new AudioClip();
		clip.setRelativePath(relativePath);
		this.audioClipDict.put(relativePath, clip);
		return clip;
	}

}
