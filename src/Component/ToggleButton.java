package src.Component;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JToggleButton;

public class ToggleButton {
	private final Icon icon = null;
	private final String text = "";
	private final boolean isSelected = false;
	private final boolean isEnable = false;
	private final ActionListener actionListener = null;
	
	public static class Builder {
		private Icon icon = null;
		private String text = "";
		private boolean isSelected = false;
		private boolean isEnable = false;
		private ActionListener actionListener = null;
		
		public Builder() {
		}
		
		public Builder icon(Icon icon) {
			this.icon = icon;
			return this;
		}
		
		public Builder text(String text) {
			this.text = text;
			return this;
		}
		
		public Builder select(boolean isSelected) {
			this.isSelected = isSelected;
			return this;
		}

		public Builder enable(boolean isEnable) {
			this.isEnable = isEnable;
			return this;
		}
		
		public Builder actionListener(ActionListener listener) {
			this.actionListener = listener;
			return this;
		}
		
		public JToggleButton build() {
			JToggleButton newButton = new JToggleButton(text, icon, isSelected);
			newButton.setEnabled(isEnable);
			newButton.addActionListener(actionListener);
			return newButton;
		}
	}
	
	private ToggleButton() {
	}
}
