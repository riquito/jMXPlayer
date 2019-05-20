package src.Controller;

//Declare the event. It must extend EventObject.
class CanvasResizedEvent extends java.util.EventObject {
	public CanvasResizedEvent(Object source) {
	    super(source);
	}
}