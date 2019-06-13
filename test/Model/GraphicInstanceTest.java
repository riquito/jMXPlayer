package test.Model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import src.Model.GraphicInstance;

public class GraphicInstanceTest {
	GraphicInstance instance;
	
	@Before
	public void setUp() {
		instance = new GraphicInstance();
	}
	
	/**
	 * Purpose: get base path at graphic instance 
	 * Input:
	 * Expected: null (initialize)
	 *
	 * return SUCCESS
	 * 
	 * get base path at graphic instance
	 * null
	 */
	@Test
	public void testGetBasePath() {
		assertNull(instance.getBasePath());
	}
	

}
