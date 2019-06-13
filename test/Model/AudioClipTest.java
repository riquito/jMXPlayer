package test.Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import src.Model.AudioClip;
import src.Model.Tree;

public class AudioClipTest {
	AudioClip audioClip;
	
	@Before
	public void setUp() {
		audioClip = new AudioClip();
	}

	/**
	 * Purpose: check getSpine2time() is instance of Hashtable 
	 * Input:
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * check getSpine2time() is instance of Hashtable
	 * True 
	 */
	@Test
	public void testGetSpine2Time() {
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
	
	/**
	 * Purpose: check getTime2Spine() is instance of Hashtable 
	 * Input:
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * check getTime2Spine() is instance of Hashtable
	 * True 
	 */
	@Test
	public void testGetTime2Spine() {
		assertTrue(audioClip.getTime2Spine() instanceof Hashtable);
	}
	
	/**
	 * Purpose: check getTimeRBTree() is instance of Tree 
	 * Input:
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * check getTimeRBTree() is instance of Tree
	 * True 
	 */
	@Test
	public void testGetTimeRBTree() {
		assertTrue(audioClip.getTimeRBTree() instanceof Tree);
	}
	
	/**
	 * Purpose: check setTimeRBTree() well added Tree 
	 * Input: new Tree()
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * new Tree -> audioClip.timeRBTree
	 * audioClip.timeRBTree is equals new Tree
	 * True
	 */
	@Test
	public void testSetTimeRBTree() {
		Tree tree = new Tree();
		audioClip.setTimeRBTree(tree);
		assertEquals(audioClip.getTimeRBTree(), tree);
	}
	
	/**
	 * Purpose: get Spine matched time <add (spine = "abcd" : time = "30") key value>
	 * Input: time = "30"
	 * Expected: NotNull
	 *
	 * return SUCCESS
	 * 
	 * add (spine = "abcd" : time = "30") key value
	 * getSpine matched time
	 * NotNull 
	 */
	@Test
	public void testGetSpine() {
		String spine = "abcd";
		int time = 30;
		audioClip.addSpineTime(spine, time);
		assertNotNull(audioClip.getSpines(time));
	}

}
