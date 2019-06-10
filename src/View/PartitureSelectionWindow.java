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

package src.View;

import java.awt.GridLayout;
import java.util.Hashtable;
import javax.swing.JCheckBox;

import src.Controller.PartitureSelectedListener;
import src.Model.GraphicInstanceGroup;
import javax.swing.event.EventListenerList;
import java.util.HashSet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import org.jdesktop.layout.GroupLayout;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;

/**
 *
 * @author Riquito
 */

public class PartitureSelectionWindow extends Window {
	private Hashtable<JCheckBox, GraphicInstanceGroup> checkBoxHashTable;

	// this attribute contains the graphic groups wich are selected in JCheckBoxes
	public HashSet<GraphicInstanceGroup> graphicGroupFromCheckBox;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JPanel panel;
	private JScrollPane scrollPane;
	// End of variables declaration//GEN-END:variables

	// Create the listener list
	protected EventListenerList listenerList = new EventListenerList();

	// This methods allows classes to register for MyEvents
	public void addPartitureSelectedListener(PartitureSelectedListener listener) {
		listenerList.add(PartitureSelectedListener.class, listener);
	}

	// This methods allows classes to unregister for MyEvents
	public void removePartitureSelectedListener(PartitureSelectedListener listener) {
		listenerList.remove(PartitureSelectedListener.class, listener);
	}

	private PartitureSelectedListener partitureSelectedListener = new PartitureSelectedListener() {
		public void onPartitureSelected(GraphicInstanceGroup group, boolean isSelected) {
			if (isSelected) {
				graphicGroupFromCheckBox.add(group);
			} else {
				graphicGroupFromCheckBox.remove(group);
			}
		}
	};

	/** Creates new form PartitureSelectionWindow */
	public PartitureSelectionWindow() {
	}
	
	@Override
	public void render() {
		initComponents();

		this.panel.setLayout(new GridLayout(0, 1, 10, 20));
		this.panel.setVisible(true);

		this.checkBoxHashTable = new Hashtable<JCheckBox, GraphicInstanceGroup>();
		this.graphicGroupFromCheckBox = new HashSet<GraphicInstanceGroup>();

		this.addPartitureSelectedListener(partitureSelectedListener);
	}

	@Override
	public void clearAll() {
		for (JCheckBox checkBox : this.checkBoxHashTable.keySet()) {
			this.panel.remove(checkBox);
		}
		this.checkBoxHashTable.clear();
	}

	private ActionListener checkBoxActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			JCheckBox actionSourceCheckBox = (JCheckBox) event.getSource();

			Object[] listeners = listenerList.getListenerList();
			// Each listener occupies two elements - the first is the listener class
			// and the second is the listener instance
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] instanceof PartitureSelectedListener) {
					((PartitureSelectedListener) listeners[i + 1]).onPartitureSelected(
							checkBoxHashTable.get(actionSourceCheckBox), actionSourceCheckBox.isSelected());
				}
			}
		}
	};

	/* Insert the new mxdata.group in the window */
	/**
	 * 
	 * @param group
	 */
	public void addElement(GraphicInstanceGroup group) {
		JCheckBox newCheckBox = new JCheckBox(group.getDescription());
		checkBoxHashTable.put(newCheckBox, group);
		newCheckBox.addActionListener(checkBoxActionListener);
		this.panel.add(newCheckBox);
	}

	/*
	 * Check/Uncheck a chkbox wheter the partiture should be visible or not
	 */
	public void enablePartiture(GraphicInstanceGroup group, boolean isEnabled) {
		// XXX metodo chiaramente "lento". alternative? servono ? (e' solo
		// un'interazione con l'utente)
		for (JCheckBox checkBox : this.checkBoxHashTable.keySet()) {
			if (this.checkBoxHashTable.get(checkBox) == group) {
				checkBox.setSelected(isEnabled);
				break;
			}
		}

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		scrollPane = new JScrollPane();
		panel = new JPanel();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		GroupLayout basePanelLayout = new GroupLayout(panel);
		basePanelLayout.setHorizontalGroup(
				basePanelLayout.createParallelGroup(GroupLayout.LEADING).add(0, 233, Short.MAX_VALUE));
		basePanelLayout.setVerticalGroup(
				basePanelLayout.createParallelGroup(GroupLayout.LEADING).add(0, 332, Short.MAX_VALUE));
		panel.setLayout(basePanelLayout);

		scrollPane.setViewportView(panel);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(layout.createSequentialGroup()
				.addContainerGap().add(scrollPane, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(layout.createSequentialGroup()
				.addContainerGap().add(scrollPane, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE).addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>//GEN-END:initComponents
}
