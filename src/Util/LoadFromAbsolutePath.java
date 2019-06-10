package src.Util;

import java.net.MalformedURLException;
import java.net.URL;

public class LoadFromAbsolutePath implements ResourceLoadStrategy{
	@Override
	public URL getLoadPath(String basePath, String path) throws MalformedURLException {
		return new URL(basePath);
	}
}
