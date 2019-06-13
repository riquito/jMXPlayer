package test.Model;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;
import org.junit.Before;

import src.Model.GraphicInstanceGroup;

public class GraphicInstanceGroupTest {
	GraphicInstanceGroup group;
	final String description = "description";
	@Before
	public void setUp() {
		group = new GraphicInstanceGroup(description);
	}
	
	/**
	 * Purpose: get description of graphic instance group 
	 * Input:
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * getDescription is equal description
	 * True 
	 */
	@Test
	public void testGetDescription() {
		assertEquals(group.getDescription(), description);
	}
	
	/**
	 * Purpose: set description of graphic instance group 
	 * Input: Hello World!
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * description -> Hello World!
	 * True 
	 */
	@Test
	public void testSetDescription() {
		String newDescription = "Hello World!";
		group.setDescription(newDescription);
		assertEquals(group.getDescription(), newDescription);
	}
	
	/**
	 * Purpose: get graphic instance group size  
	 * Input:
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * getGroupSize is equal to 0 (initialize)
	 * True 
	 */
	@Test
	public void testGetGroupSize() {
		assertEquals(group.getGroupSize(), 0);
	}
	
	/**
	 * Purpose: set graphic instance group size  
	 * Input: size -> 4
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * set GroupSize 4
	 * True 
	 */
	@Test
	public void testSetGroupSize() {
		group.setGroupSize(4);
		assertEquals(group.getGroupSize(), 4);
	}
	
	/**
	 * Purpose: get instances from GraphicInstanceGroup  
	 * Input: 
	 * Expected: True
	 *
	 * return SUCCESS
	 * 
	 * instances is instanceof Vector
	 * True 
	 */
	@Test
	public void testGetInstance() {
		assertTrue(group.getInstances() instanceof Vector);
	}
	
	

}
