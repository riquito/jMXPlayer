package test.Component;

import static org.junit.Assert.*;
import org.junit.Test;
import javax.swing.JToggleButton;

import src.Component.ToggleButton;

public class ToggleButtonTest {
	/**
	 * Purpose: create Simple Toggle Button  
	 * Input: 
	 * Expected: equal to JToggleButton with empty text, no icon, 
	 * doesn't selected, doesn't enable, no actionListener
	 *
	 * return SUCCESS
	 * 
	 * equal 
	 */
	@Test
	public void testCreateSimpleToggleButton() {
		assertTrue(new ToggleButton.Builder().build() instanceof JToggleButton);
	}
	
	/**
	 * Purpose: create Toggle Button with text  
	 * Input: text = hello
	 * Expected: equal to JToggleButton with "hello" text, no icon, 
	 * doesn't selected, doesn't enable, no actionListener
	 *
	 * return SUCCESS
	 * 
	 * equal 
	 */
	@Test
	public void testCreateWithTextToggleButton() {
		String text = "hello";
		JToggleButton toggleButton = new ToggleButton.Builder().text(text).build(); 
		assertEquals(toggleButton.getText(), text);
	}

}
