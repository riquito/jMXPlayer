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

/*
 * HighLights.java
 *
 * This class is meant to organize and move
 *  the various higlights that are shown on an image
 *
 */

package src.Component;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JLayeredPane;

import src.Exception.AllParameterNullException;
import src.Model.Voice;
import src.Util.RectangleExtension;

/**
 *
 * @author Riquito
 */
public class HighLights {
	private double scaling = 1.0;
	private int xAdjust = 0, yAdjust = 0;
	private int layerIndex = 1; // index 0 is the background partiture image
	private JLayeredPane backgroundPanel = null;
	private Hashtable<String, SpinedLabel> spinedLabels;

	/** Creates a new instance of HighLights */
	public HighLights() {
		this.spinedLabels = new Hashtable<String, SpinedLabel>();
	}
	
	public int getXAdjust() {
		return xAdjust;
	}

	public void setXAdjust(int xAdjust) {
		this.xAdjust = xAdjust;
	}

	public int getYAdjust() {
		return yAdjust;
	}

	public void setYAdjust(int yAdjust) {
		this.yAdjust = yAdjust;
	}

	public double getScaling() {
		return scaling;
	}

	public void setScaling(double scaling) {
		this.scaling = scaling;
	}

	public void setBackgroundPanel(JLayeredPane panel) {
		this.backgroundPanel = panel;
	}

	public void hideAllLabel() {
		Rectangle reactangle = new Rectangle(-100, -100, 1, 1);
		Enumeration keys = this.spinedLabels.keys();
		
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			SpinedLabel singleLabel = this.spinedLabels.get(key);
			singleLabel.setBounds(reactangle.x, reactangle.y, (int) reactangle.getWidth(), (int) reactangle.getHeight());
			singleLabel.setPreferredSize(new Dimension((int) reactangle.getWidth(), (int) reactangle.getHeight()));
			// setlayer is needed to show again the label (setvisible, validate, updateUI
			// don't work)
			this.backgroundPanel.setLayer(singleLabel, this.layerIndex);
		}
	}

	/**
	 * Aggiunge un rettangolo trasparente su di una nota
	 * @throws AllParameterNullException 
	 */
	public void appendLabel(Rectangle rectangle, Voice voice) throws AllParameterNullException {
		SpinedLabel singleLabel = null;

		if (rectangle == null || voice == null) {		
			throw new AllParameterNullException("Error in appendLabel");
		}

		singleLabel = this.spinedLabels.get(voice.getName());
		if (singleLabel == null) {
			// first time we listen to this voice
			singleLabel = new SpinedLabel(voice);
			this.spinedLabels.put(voice.getName(), singleLabel);
			this.backgroundPanel.add(singleLabel, this.layerIndex);
		}

		Rectangle newRectangle = RectangleExtension.scale(rectangle, this.getScaling());
		singleLabel.setBounds(newRectangle.x + this.getXAdjust(), newRectangle.y + this.getYAdjust(), (int) newRectangle.getWidth(), (int) newRectangle.getHeight());
		singleLabel.setPreferredSize(new Dimension((int) newRectangle.getWidth(), (int) newRectangle.getHeight()));
		// setlayer is needed to show again the label (setvisible, validate, updateUI
		// don't work)
		this.backgroundPanel.setLayer(singleLabel, this.layerIndex);
	}
}
