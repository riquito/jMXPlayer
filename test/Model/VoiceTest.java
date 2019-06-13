package test.Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.Model.Voice;

public class VoiceTest {
	Voice voice;
	final String voiceName = "hello";

	@Before
	public void setUp() {
		voice = new Voice(voiceName);
	}
	
	/**
	 * Purpose: set visible to voice  
	 * Input: changedVisible = true
	 * Expected: true
	 *
	 * return SUCCESS
	 * 
	 * visible of voice -> changedVisible
	 */
	@Test
	public void testSetVisible() {
		boolean changedVisible = true;
		voice.setVisible(changedVisible);
		assertEquals(changedVisible, voice.isVisible());
	}
	
	/**
	 * Purpose: get name of voice  
	 * Input: 
	 * Expected: hello
	 *
	 * return SUCCESS
	 * 
	 * hello
	 */
	@Test
	public void testGetName() {
		assertEquals(voiceName, voice.getName());
	}

	/**
	 * Purpose: set name of voice  
	 * Input: world
	 * Expected: 
	 *
	 * return SUCCESS
	 * 
	 * hello -> world
	 */
	@Test
	public void testSetName() {
		voice.setName("world");
		assertEquals("world", voice.getName());
	}

	
}
