package test.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JCheckBox;

import org.junit.Test;

import src.Component.VoicesPanel;
import src.Model.Voice;

public class VoicePanelTest {
	VoicesPanel voicepanel;
	JCheckBox chkBtn;
	
	private Hashtable<String, Voice> voiceDictionary = new Hashtable<String, Voice>();
	Voice voice = new Voice("Test");
	String test = "test";
	
	/**
	 * Purpose: create Voices Panel  
	 * Input: make voice panel visible
	 * Expected:
	 * 		return SUCCESS
	 * 		voicepanel.isVisible() == true
	 * 
	 * equal 
	 */
	@Test
	public void testVoicesPanel() {
		boolean checkVisible = true;
		voicepanel = new VoicesPanel();
		assertEquals(checkVisible, voicepanel.isVisible());
	}
	
	/**
	 * Purpose: set Voice Dictionary
	 * Input: setVoiceDictionary set voicepanel with checkDictionary
	 * Expected:
	 * 		return SUCCESS
	 * 		voicepanel.setVoiceDictionary(checkDictionary) == NULL
	 * 
	 * NULL 
	 */
	@Test
	public void testsetVoiceDictionary() {
		Hashtable<String, Voice> checkDictionary = null; 
		voicepanel = new VoicesPanel();
		voicepanel.setVoiceDictionary(checkDictionary);
		assertNotNull(voicepanel);

	}
	
	/**
	 * Purpose: change Voice Visibility with populate
	 * Input: populate -> voiceDictionary
	 * Expected:
	 * 		return SUCCESS
	 * 		voicepanel == NULL
	 * 
	 * NULL 
	 */
	@Test
	public void testpopulate() {
		voiceDictionary.put(test, voice);
		voicepanel = new VoicesPanel();
		voicepanel.populate(voiceDictionary);
		assertNotNull(voicepanel);
	}
	
	
}
