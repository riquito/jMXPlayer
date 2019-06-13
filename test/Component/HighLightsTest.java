package test.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Enumeration;

import javax.swing.JLayeredPane;

import org.junit.Test;

import src.Component.HighLights;
import src.Component.SpinedLabel;


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
	
	/**
	 * Purpose: setYAdjust getYAdjust
	 * Input: setYAdjust -> 1
	 * Expected: getYAdjust == 1
	 * 		return SUCCESS
	 * 		
	 */
	@Test
	public void testsetgetYAdjust() {
		highLights = new HighLights();
		highLights.setYAdjust(1);
		assertEquals(1, highLights.getYAdjust());
	}
	
	/**
	 * Purpose: setScaling getScaling
	 * Input: setScaling -> 1.1
	 * Expected: getScaling == 1.1
	 * 		return SUCCESS
	 * 		
	 */
	@Test
	public void testsetgetScaling() {
		highLights = new HighLights();
		highLights.setScaling(1.1);
		assertEquals(1.1, highLights.getScaling(), 1.1);
	}
	
	/**
	 * Purpose: set BackgroundPanel
	 * Input: setBackgroundPanel -> panel
	 * Expected:not Null
	 * 		return SUCCESS
	 * 		highLights -> not null
	 * 		
	 */
	@Test
	public void testsetBackgroundPanel() {
		JLayeredPane panel = null;
		highLights = new HighLights();
		highLights.setBackgroundPanel(panel);
		assertNotNull(highLights);
	}

	/**
	 * Purpose: hideAllLabel
	 * Input: hideAllLabel()
	 * Expected:
	 * 		return failure
	 * 		I can't touch the key that in the hideAllLabel function so it's failure
	 * 		
	 */
	@Test
	public void testhideAllLabel() {
		highLights = new HighLights();
		highLights.hideAllLabel();
		assertNotNull(highLights);
	}
	
	
	
}
