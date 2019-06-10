package src.Util;

import java.net.MalformedURLException;
import java.net.URL;

public interface ResourceLoadStrategy {
	public URL getLoadPath(String basePath, String path) throws MalformedURLException;
}
