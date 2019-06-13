package test.Util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.Util.LoadFromAbsolutePath;
import src.Util.LoadFromRelativePath;
import src.Util.ResourceLoader;
import java.net.MalformedURLException;
import java.net.URL;

public class ResourceLoaderTest {
	/**
	 * Purpose: MalformedURLException Test while get resource url using ResourceLoader 
	 * Input: "jjalbang.today/jj237.jpg"
	 * Expected: @throws MalformedURLException
	 *
	 * @throws MalformedURLException
	 */
	@Test(expected = MalformedURLException.class)
	public void testResourceLoaderWithMalformedUrlString() throws MalformedURLException {
		ResourceLoader.getResourceUrl("jjalbang.today/jj237.jpg", "");
	}
	
}
