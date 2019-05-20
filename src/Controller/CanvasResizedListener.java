package src.Controller;

//Declare the listener class. It must extend EventListener.
public interface CanvasResizedListener extends java.util.EventListener {
	public void on_canvas_resized(int x_offset,int y_offset,double ratio);
}
