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

import src.Model.Voice;

/**
 *
 * @author Riquito
 */
public class VoicesPanel extends JPanel{
    private Hashtable<String,Voice> voicesDict=null;
    
    /** Creates a new instance of VoicesPanel2 */
    public VoicesPanel() {
        //this.setLayout(new BorderLayout());
        this.setVisible(true);
    }
    
    public void populate(Hashtable<String,Voice> voicesDict){
        this.voicesDict=voicesDict;
        JCheckBox tmpBtn;
        Voice tmpVoice;
        
        this.setLayout(new GridLayout(0,1,10,20));
        
        
        for (String voiceName : voicesDict.keySet()) {
            tmpBtn=new JCheckBox(voiceName);
            tmpBtn.setSelected(true);
            tmpVoice=(Voice)voicesDict.get(voiceName);
            tmpVoice.setVisible(true);
            this.add(tmpBtn);
            
            tmpBtn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    on_chkBox_clicked((JCheckBox)evt.getSource());
                }
            });
        }
    
    }
    
    /**
     * Check wheter a button has been selected or deselected, and update
     * the vector "voicesDict" accordingly
     */
    void on_chkBox_clicked(JCheckBox chkBtn){
        ((Voice)this.voicesDict.get(chkBtn.getText())).setVisible(chkBtn.isSelected());
    }
    
    public void removeAllItems(){
        this.removeAll();
    }
}
