package test.Model;

import static org.junit.Assert.*;
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

}
