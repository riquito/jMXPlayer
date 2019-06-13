package test.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import src.Component.SpinedLabel;
import src.Model.Voice;

public class SpinedLabelTest {
	
	/**
	 * Purpose: make spine label 
	 * Input: 
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		spinedLabel -> not null
	 * 		
	 *
	 */
	@Test
	public void testSpinedLabel() {
		Voice voice = new Voice("test");
		SpinedLabel spinedLabel = new SpinedLabel(voice);
		assertNotNull(spinedLabel);
	}
}
