package src.Util;

import java.net.MalformedURLException;
import java.net.URL;

public class ResourceLoader {
	private static ResourceLoadStrategy strategy = new LoadFromAbsolutePath();
	
	public static URL getResourceUrl(String basePath, String path) throws MalformedURLException {
		return strategy.getLoadPath(basePath, path);
	}
	
	public static void setStrategy(ResourceLoadStrategy strategy) {
		ResourceLoader.strategy = strategy;
	}
}
