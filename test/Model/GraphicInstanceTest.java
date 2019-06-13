package test.Model;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.util.Hashtable;

import org.junit.Test;
import org.junit.Before;

import src.Model.GraphicInstance;
import src.Model.RTree;
import src.Model.Tree;

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
	
	/**
	 * Purpose: Get Image path at graphic instance 
	 * Input: 
	 * Expected: NotNull
	 *
	 * return SUCCESS
	 * 
	 * set relative path at graphic instance
	 * NotNull
	 */
	@Test
	public void testGetImagePath() {
		String basePath = "http://jjalbang.today";
		instance.setBasePath(basePath);
		String relativeImagePath = "jj237.jpg";
		instance.setRelativeImagePath(relativeImagePath);
		assertNotNull(instance.getImagePath());
	}
	
	/**
	 * Purpose: Get Tree at graphic instance 
	 * Input: 
	 * Expected: NotNull
	 *
	 * return SUCCESS
	 * 
	 * Get Tree at graphic instance
	 * NotNull
	 */
	@Test
	public void testGetTree() {
		assertNotNull(instance.getTree());
	}
	
	/**
	 * Purpose: Set Tree at graphic instance 
	 * Input: new RTree -> instance.tree
	 * Expected: Equals
	 *
	 * return SUCCESS
	 * 
	 * new RTree -> instance.tree
	 * Equals
	 */
	@Test
	public void testSetTree() {
		RTree newTree = new RTree(6);
		instance.setTree(newTree);
		assertEquals(newTree, instance.getTree());
	}
	
	/**
	 * Purpose: Get spine2point at graphic instance 
	 * Input: 
	 * Expected: NotNull
	 *
	 * return SUCCESS
	 * 
	 * Get spine2point at graphic instance
	 * NotNull
	 */
	@Test
	public void testGetSpine2point() {
		assertNotNull(instance.getSpine2point());
	}
	
}
