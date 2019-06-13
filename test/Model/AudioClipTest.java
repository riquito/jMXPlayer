package test.Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import src.Model.AudioClip;

public class AudioClipTest {
	AudioClip audioClip;
	
	@Before
	public void setUp() {
		audioClip = new AudioClip();
	}

	/**
	 * Purpose: check getSpine2time() is instance of Hashtable 
	 * Input: "abcd", 30
	 * Expected: NotNull
	 *
	 * return SUCCESS
	 * 
	 * check getSpine2time() is instance of Hashtable
	 * True 
	 */
	@Test
	public void testGetSpineTime() {
		assertTrue(audioClip.getSpine2time() instanceof Hashtable);
	}
	
	/**
	 * Purpose: add (spine = "abcd" : time = "30") key value  
	 * Input: "abcd", 30
	 * Expected: NotNull
	 *
	 * return SUCCESS
	 * 
	 * add (spine = "abcd" : time = "30") key value
	 * NotNull 
	 */
	@Test
	public void testAddSpineTime() {
		String spine = "abcd";
		int time = 30;
		audioClip.addSpineTime(spine, time);
		assertNotNull(audioClip.getSpine2time().containsKey(spine));
	}

}
