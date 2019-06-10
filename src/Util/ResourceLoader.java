package src.Util;

import java.net.MalformedURLException;
import java.net.URL;

public class ResourceLoader {
	private static ResourceLoadStrategy strategy;
	
	public static URL getResourceUrl(String basePath, String path) throws MalformedURLException {
		return strategy.getLoadPath(basePath, path);
	}
	
	public void setStrategy(ResourceLoadStrategy strategy) {
		ResourceLoader.strategy = strategy;
	}
}
