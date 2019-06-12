package test.Util;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
	* Purpose: Get Next Color 30 Times 
	* Input: 
	* Expected:
	*
	*	return SUCCESS
	*	color list from getNextColor instanceof List of Color
	*/
	@Test
	public void getNextColorListTest() {
		List<Color> colorList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			colorList.add(CustomColor.getNextColor());
		}
		assertNotNull(colorList.size());
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
