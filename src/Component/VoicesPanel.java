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

import java.awt.GridLayout;

import java.util.Hashtable;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import src.Model.Voice;

/**
 *
 * @author Riquito
 */
public class VoicesPanel extends JPanel {
	private Hashtable<String, Voice> voiceDictionary = null;

	/** Creates a new instance of VoicesPanel2 */
	public VoicesPanel() {
		// this.setLayout(new BorderLayout());
		this.setVisible(true);
	}
	
	public void setVoiceDictionary(Hashtable<String, Voice> voiceDictionary) {
		this.voiceDictionary = voiceDictionary;
	}

	public void populate(Hashtable<String, Voice> voiceDictionary) {
		setVoiceDictionary(voiceDictionary);
		 
		this.setLayout(new GridLayout(0, 1, 10, 20));

		for (String voiceName : voiceDictionary.keySet()) {
			changeVoiceVisibility(this.voiceDictionary.get(voiceName));
			this.add(generateButton(voiceName));
		}
	}
	
	public void changeVoiceVisibility(Voice voice) {
		voice.setVisible(true);
	}
	
	public JCheckBox generateButton(String voiceName) {
		JCheckBox newButton = new JCheckBox(voiceName);
		newButton.setSelected(true);
		newButton.addActionListener(actionListener);
		return newButton;
	}
	
	private ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			changeVoiceVisiblityOnCheckbox((JCheckBox) event.getSource());
		}
	};

	/**
	 * Check wheter a button has been selected or deselected, and update the vector
	 * "voicesDict" accordingly
	 */
	private void changeVoiceVisiblityOnCheckbox(JCheckBox chkBtn) {
		this.voiceDictionary.get(chkBtn.getText()).setVisible(chkBtn.isSelected());
	}

	public void removeAllItems() {
		this.removeAll();
	}
}
