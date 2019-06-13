package test.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import src.Component.HighLights;


public class HighLightsTest {
	HighLights highLights;
	/**
	 * Purpose: make HighLights instance
	 * Input: 
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		highLights -> not null
	 */
	@Test
	public void testHighLights() {
		highLights = new HighLights();
		assertNotNull(highLights);
	}
	
	/**
	 * Purpose: setXAdjust getXAdjust
	 * Input: setXAdjust -> 1
	 * Expected: getXAdjust == 1
	 * 		return SUCCESS
	 * 		
	 */
	@Test
	public void testsetgetXAdjust() {
		highLights = new HighLights();
		highLights.setXAdjust(1);
		assertEquals(1, highLights.getXAdjust());
	}
}
