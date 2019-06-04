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

import java.awt.Color;

import src.Util.CustomColorList;

public class Voice {
	private String name;
	private Color color;
	private boolean visible = false;

	public Voice(String voiceName) {
		this.setName(voiceName);
		this.setColor(CustomColorList.getColor());
	}

	public void setVisible(boolean isVisible) {
		this.visible = isVisible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color rgba) {
		this.color = rgba;
	}

	public boolean isVisible() {
		return visible;
	}
}
