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

package src.Component;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* ScrollablePicture.java is used by ScrollDemo.java. */

public class ScrollablePicture extends JLabel implements Scrollable, MouseMotionListener {

	private int maxUnitIncrement = 1;
	private boolean missingPicture = false;

	public ScrollablePicture(ImageIcon icon, int maxUnitIncrement) {
		super(icon);

		if (icon == null) {
			missingPicture = true;
			setText("No picture found.");
			setHorizontalAlignment(CENTER);
			setOpaque(true);
			setBackground(Color.white);
		}

		this.maxUnitIncrement = maxUnitIncrement;

		// Let the user scroll by dragging to outside the window.
		setAutoscrolls(true); // enable synthetic drag events
		addMouseMotionListener(this); // handle mouse drags
	}

	// Methods required by the MouseMotionListener interface:
	public void mouseMoved(MouseEvent event) {
	}

	public void mouseDragged(MouseEvent event) {
		// The user is dragging us, so scroll!
		scrollRectToVisible(new Rectangle(event.getX(), event.getY(), 1, 1));
	}

	public Dimension getPreferredSize() {
		return missingPicture ? new Dimension(320, 480) : super.getPreferredSize();
	}

	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		// Get the current position.
		int currentPosition = orientation == SwingConstants.HORIZONTAL ? visibleRect.x : visibleRect.y;

		// Return the number of pixels between currentPosition
		// and the nearest tick mark in the indicated direction.
		if (direction < 0) {
			int newPosition = currentPosition - (currentPosition / maxUnitIncrement) * maxUnitIncrement;
			return (newPosition == 0) ? maxUnitIncrement : newPosition;
		} else {
			return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition;
		}
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return orientation == SwingConstants.HORIZONTAL ? visibleRect.width - maxUnitIncrement
				: visibleRect.height - maxUnitIncrement;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public void setMaxUnitIncrement(int pixels) {
		maxUnitIncrement = pixels;
	}
}
