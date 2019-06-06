package src.Component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import src.Model.Voice;

public class SpinedLabel extends JLabel {
	public Voice voice; // stored for possible future uses

	public SpinedLabel(Voice voice) {
		super();
		this.voice = voice;

		this.setOpaque(true);
		this.setBackground(voice.getColor());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}