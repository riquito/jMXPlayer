package src.Util;

import java.net.MalformedURLException;
import java.net.URL;

public class LoadFromRelativePath implements ResourceLoadStrategy {
	@Override
	public URL getLoadPath(String basePath, String path) throws MalformedURLException {
		return new URL(new URL(basePath), path);
	}
}
