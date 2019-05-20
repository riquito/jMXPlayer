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

import java.util.HashSet;

/**
 *
 * @author  Riquito
 */


// Declare the event. It must extend EventObject.
class PartitudeSelectedEvent extends java.util.EventObject {
    public PartitudeSelectedEvent(Object source) {
        super(source);
    }
}

// Declare the listener class. It must extend EventListener.
// A class must implement this interface to get MyEvents.


public class PartitureSelectionWindow extends javax.swing.JFrame {
    private Hashtable<JCheckBox, GraphicInstanceGroup> mxChkbox2groups;
    
    //this attribute contains the graphic groups wich are selected in JCheckBoxes
    public HashSet<GraphicInstanceGroup> selected;
    
    // Create the listener list
    protected javax.swing.event.EventListenerList listenerList =
        new javax.swing.event.EventListenerList();
    
    // This methods allows classes to register for MyEvents
    public void addMyEventListener(PartitureSelectedListener listener) {
        listenerList.add(PartitureSelectedListener.class, listener);
    }
    
    // This methods allows classes to unregister for MyEvents
    public void removeMyEventListener(PartitureSelectedListener listener) {
        listenerList.remove(PartitureSelectedListener.class, listener);
    }
    
    
    /** Creates new form PartitureSelectionWindow */
    public PartitureSelectionWindow() {
        initComponents();
        
        this.jPanel1.setLayout(new GridLayout(0, 1, 10, 20));
        this.jPanel1.setVisible(true);
        
        this.mxChkbox2groups = new Hashtable<JCheckBox, GraphicInstanceGroup>();
        this.selected = new HashSet<GraphicInstanceGroup>();
        
        this.addMyEventListener(new PartitureSelectedListener() {
            public void on_partiture_selected(GraphicInstanceGroup group, boolean isSelected) {
                if (isSelected) {
                	selected.add(group);
                } else {
                	selected.remove(group);
                }
            }
        });
    }
    
    /*Clean the window deleting/hiding present checkboxes*/
    public void cleanAll(){
        for(JCheckBox tmpBox: this.mxChkbox2groups.keySet()){
            this.jPanel1.remove(tmpBox);
        }
        this.mxChkbox2groups.clear();
    }
    
    /*Insert the new mxdata.group in the window*/
    /**
     * 
     * @param group 
     */
    public void addElement(GraphicInstanceGroup group){
        JCheckBox tmpBox = new JCheckBox(group.getDescription());
        mxChkbox2groups.put(tmpBox,group);
        this.jPanel1.add(tmpBox);
        
        tmpBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCheckBox tmpBox = (JCheckBox)evt.getSource();
                
                Object[] listeners = listenerList.getListenerList();
                // Each listener occupies two elements - the first is the listener class
                // and the second is the listener instance
                for (int i = 0; i < listeners.length; i += 2) {
                    if (listeners[i] == PartitureSelectedListener.class) {
                        ((PartitureSelectedListener) listeners[i+1]).on_partiture_selected(mxChkbox2groups.get(tmpBox), tmpBox.isSelected());
                    }
                }
            }
        });
    }
    
    /*
     Check/Uncheck a chkbox wheter the partiture should be visible or not
     */
    public void enablePartiture(GraphicInstanceGroup group, boolean isEnabled){
        //XXX metodo chiaramente "lento". alternative? servono ? (e' solo un'interazione con l'utente)
        for(JCheckBox chkBox: this.mxChkbox2groups.keySet()){
            if(this.mxChkbox2groups.get(chkBox)==group) {
                chkBox.setSelected(isEnabled);
                break;
            }
        }
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 233, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 332, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PartitureSelectionWindow().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
