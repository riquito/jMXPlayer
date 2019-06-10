package src.View;

import java.awt.event.WindowListener;

import javax.swing.JFrame;

public abstract class Window extends JFrame{
	public abstract void render();
	public abstract void clearAll();
}
