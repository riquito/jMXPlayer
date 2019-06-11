package test.Util;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Color;
import src.Util.CustomColor;

public class CustomColorTest {

	/**
	* Purpose: Get Next Color 
	* Input: 
	* Expected:
	*
	*	return SUCCESS 
	*	getNextColor instanceof Color
	*/
	@Test
	public void getNextColorTest() {
		assertTrue(CustomColor.getNextColor() instanceof Color);
	}
	
	/**
	* Purpose: Get Current Alpha Value 
	* Input: 
	* Expected:
	*
	*	return SUCCESS 
	*	 0 <= getAlpha() <= 255
	*/
	@Test
	public void getAlphaTest() {
		assertTrue(CustomColor.getAlpha() >= 0 || CustomColor.getAlpha() <= 255);
	}
}
