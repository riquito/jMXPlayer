package src.View;

import java.awt.event.WindowAdapter;

public interface Window {
	void render();
	void clearAll();
	void addWindowListener(WindowAdapter windowAdapter);
}
