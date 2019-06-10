package src.Controller;

//Declare the listener class. It must extend EventListener.
public interface CanvasResizedListener extends java.util.EventListener {
	public void onCanvasResized(int xOffset, int yOffset, double ratio);
}
