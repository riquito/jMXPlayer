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
	
	/**
	 * Purpose: get relative path at graphic instance 
	 * Input:
	 * Expected: null (initialize)
	 *
	 * return SUCCESS
	 * 
	 * get relative path at graphic instance
	 * null
	 */
	@Test
	public void testGetRelativePath() {
		assertNull(instance.getRelativeImagePath());
	}

	/**
	 * Purpose: Set base path at graphic instance 
	 * Input: "http://jjalbang.today"
	 * Expected: Equals
	 *
	 * return SUCCESS
	 * 
	 * set base path at graphic instance
	 * Equals
	 */
	@Test
	public void testSetBasePath() {
		String basePath = "http://jjalbang.today";
		instance.setBasePath(basePath);
		assertEquals(basePath, instance.getBasePath());
	}
	
	/**
	 * Purpose: Set relative path at graphic instance 
	 * Input: "jj237.jpg"
	 * Expected: Equals
	 *
	 * return SUCCESS
	 * 
	 * set relative path at graphic instance
	 * Equals
	 */
	@Test
	public void testSetRelativeImagePath() {
		String relativeImagePath = "jj237.jpg";
		instance.setRelativeImagePath(relativeImagePath);
		assertEquals(relativeImagePath, instance.getRelativeImagePath());
	}

}
