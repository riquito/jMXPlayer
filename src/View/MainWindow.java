/**
 * jMXPlayer, a GUI to IEEE PAR1599 (MX) data
 * Copyright © 2010 Riccardo Attilio Galli <riccardo@sideralis.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * NewJFrame.java
 *
 */

package src.View;

import java.awt.event.MouseEvent;
import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import java.util.Vector;
import javax.media.GainControl;
import javax.media.Manager;
import javax.media.Player;
import javax.media.Time;

import src.Component.HighLights;
import src.Controller.MXHandler;
import src.Controller.PartitureSelectedListener;
import src.Model.AudioClip;
import src.Model.GraphicInstance;
import src.Model.GraphicInstanceGroup;
import src.Model.MXData;
import src.Model.Voice;

import javax.media.RealizeCompleteEvent;
import javax.media.ControllerListener;
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Riccardo Galli
 */
public class MainWindow extends JFrame implements ActionListener, Window {
	private MXData MX;
	private AudioClip currentAudioClip = null;

	// seguono le variabili necessarie all'esecuzione di audio
	private Timer timer;
	private Player player;
	private Vector<Player> playerList;

	private int timerDelay = 5; // in millisecondi
	private long startTime = 0; // in millisecondi
	private long currentStep = 0;

	private String lastSeenSpine;
	private boolean wasPlaying;
	private boolean audioSliderDragged;

	private VoicesWindow voicesWindow;
	private PlayerTrackWindow trackWindow;
	private PartitureSelectionWindow partitureSelectionWindow;
	private JSlider trackSlider;
	private Vector<PartitureWindow> partitureWindows;

	private WindowAdapter closingWindowAdapter = new WindowAdapter() {
		public void windowClosing(WindowEvent event) {
			event.getWindow().setVisible(false);
			partitureToggleBtn.setSelected(false);
		}
	};
	
	public MainWindow() {
	}
	

	@Override
	public void render() {
		initComponents();

		this.timer = new Timer(this.timerDelay, this);
		this.timer.setInitialDelay(0);

		this.partitureWindows = new Vector<PartitureWindow>();

		this.partitureSelectionWindow = new PartitureSelectionWindow();
		partitureSelectionWindow.render();
		this.partitureSelectionWindow.addPartitureSelectedListener(new PartitureSelectedListener() {
			public void onPartitureSelected(GraphicInstanceGroup group, boolean isSelected) {
				// System.out.println(group.description);
				// System.out.println("selected = "+isSelected);

				for (PartitureWindow window : partitureWindows) {
					if (window.getGroup() == group) {
						window.setVisible(isSelected);
						break;
					}
				}
			}
		});
		this.partitureSelectionWindow.addWindowListener(closingWindowAdapter);

		trackWindow = new PlayerTrackWindow();
		trackWindow.render();
		trackSlider = trackWindow.getSlider();

		trackWindow.addWindowListener(closingWindowAdapter);

		trackWindow.getPlayButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ButtonModel a = ((JToggleButton) event.getSource()).getModel();
				onTrackWindowToggleButtonClicked("play");
			}
		});

		trackWindow.getPauseButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				onTrackWindowToggleButtonClicked("pause");
			}
		});

		trackWindow.getStopButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				onTrackWindowToggleButtonClicked("stop");
			}
		});

		voicesWindow = new VoicesWindow();
		voicesWindow.render();
		voicesWindow.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				evt.getWindow().setVisible(false);
				voicesToggleBtn.setSelected(false);
			}
		});

		trackSlider.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				trackSliderMousePressed(evt);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				trackSliderMouseReleased(evt);
			}
		});
		trackSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent evt) {
				trackSliderMouseDragged(evt);
			}
		});

		this.playerList = new Vector<Player>();

		// non ho connesso audioCombo via designer perché l'evento
		// actionPerformed avviene durante il popolamento
		this.audioCombo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				audioComboActionPerformed(evt);
			}
		});

		this.wasPlaying = false;
		this.audioSliderDragged = false;
		this.setVisible(true);
		// XXX per prove
		// this.loadMX("C:\\MX_media\\Aida\\aida.xml");
	}

	@Override
	public void clearAll() {
	}
	
	@Override
	public void addWindowListener(WindowAdapter windowAdapter) {
	}

	private void genPartitureWindows() {
		HighLights marks;

		for (String grpKey : this.MX.getGraphicInstanceGroup().keySet()) {

			// creo un'istanza di evidenziatori per ogni partitura
			marks = new HighLights();
			final GraphicInstanceGroup group = this.MX.getGraphicInstanceGroup().get(grpKey);
			final PartitureWindow window = new PartitureWindow(marks, group);
			this.partitureWindows.add(window);

			/*
			 * quando una partitura (finestra) viene chiusa, la nascondo e la deseleziono da
			 * PartitureSelectionWindow
			 */
			window.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent evt) {
					evt.getWindow().setVisible(false);
					partitureSelectionWindow.enablePartiture(group, false);
				}
			});

			/*
			 * azioni legate a click su partitura
			 */
			window.getCanvas().addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
					trackWindow.selectButton("play"); // just make the play button selected
					window.getMarks().hideAllLabel();
					try {
						int x, y;
						x = (int) Math
								.round((e.getX() - window.getMarks().getXAdjust()) / window.getMarks().getScaling());
						y = (int) Math
								.round((e.getY() - window.getMarks().getYAdjust()) / window.getMarks().getScaling());
						Rectangle test = (Rectangle) ((ArrayList) window.getCurrentGraphicInstance().getTree()
								.foundRect(window.getCurrentGraphicInstance().getTree().getRoot(), x, y)).get(0);

						String spine = null;
						// XXX soluzione temporanea, serve un point2spine?
						// forse e' abbastanza veloce
						// potremmo risparmiare spazio?
						for (String key : window.getCurrentGraphicInstance().getSpine2point().keySet()) {
							if (window.getCurrentGraphicInstance().getSpine2point().get(key) == test) {
								spine = key;
								break;
							}

						}
						if (spine != null)
							updateRectangles(currentAudioClip.getSpine2time().get(spine));
					} catch (IndexOutOfBoundsException evt) {
						System.out.println("mancato " + e.getPoint());
					}

					int y = 0;
					if (lastSeenSpine != null)
						y = currentAudioClip.getSpine2time().get(lastSeenSpine);
					Time x = new Time(new Double(y) / 100.0D);

					player.getGainControl().setMute(false);
					player.setMediaTime(x);
					player.start();
					currentStep = System.currentTimeMillis();
					startTime = currentStep - y * 10;
					timer.start();

				}

				public void mouseReleased(MouseEvent e) {
				}
			});
		}
	}

	private void onTrackWindowToggleButtonClicked(String buttonType) {
		if (this.player == null)
			return;

		boolean isStarting = this.player.getState() != this.player.Started;

		if (buttonType.equals("play")) {
			if (isStarting) {
				this.playerStart(true);
			}
		} else if (buttonType.equals("pause")) {
			if (!isStarting) {
				this.playerStart(false);
			}
		} else if (buttonType.equals("stop")) {
			if (!isStarting) {
				this.playerStart(false); /* stop player and timer */
			}

			this.trackSlider.setValue(0);
			this.player.setMediaTime(new Time(0));

			for (PartitureWindow window : partitureWindows) {
				window.getMarks().hideAllLabel();
			}

			// XXX questo non serve più ora che ci sono partiture multiple
			/*
			 * for(PartitureWindow win: partitureWindows) { win.marks.hideAllLabel();
			 * win.resetPrefetching(); win.refreshPartitureImage(); }
			 */
		}

	}

	public void showOpenMXDialog() {
		JFileChooser fileChooser = new JFileChooser();
		
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// This is where a real application would open the file.
			this.loadMX(file.getPath());
		}
	}

	public void loadMX(String path) {
		// I clear everything that could have been used by a previous MX
		if (this.player != null && this.player.getState() != this.player.Started)
			this.player.stop();
		if (this.timer != null)
			this.timer.stop();

		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		// disconnect comboboxes listeners so that they not trigger while I'm
		// populating them
		ActionListener[] audioComboLnrs = this.audioCombo.getActionListeners();
		for (ActionListener obj : audioComboLnrs)
			this.audioCombo.removeActionListener(obj);

		this.partitureWindows.clear();
		currentAudioClip = null;
		startTime = 0;
		currentStep = 0;
		lastSeenSpine = null;

		this.audioCombo.removeAllItems();

		this.voicesWindow.clearAll();

		this.playerList.removeAllElements();

		this.trackWindow.selectButton("stop");
		this.trackWindow.getSlider().setValue(0);

		this.partitureSelectionWindow.clearAll();

		// ... and now populate the whole application
		this.MX = MXHandler.parse(path);
		if (this.MX == null) {
			// XXX andrebbe mostrato un message dialog, in cui veniamo informati che
			// qualcosa e' andato storto (o manca audio o altro motivo)
			return;
		}

		for (String key : this.MX.getGraphicInstanceGroup().keySet()) {
			this.partitureSelectionWindow.addElement(this.MX.getGraphicInstanceGroup().get(key));
		}

		for (String key : this.MX.getAudioClipDictionary().keySet()) {
			this.audioCombo.addItem(this.MX.getAudioClipDictionary().get(key).getRelativePath());
		}
		this.audioCombo.setSelectedIndex(0);

		genPartitureWindows();
		for (PartitureWindow win : partitureWindows) {
			win.loadFirstPage();
		}

		this.voicesWindow.populate(MX.getVoices());

		this.loadMusic();
		this.trackWindow.setPlayer(this.player);

		// riconnect comboboxes listeners
		for (ActionListener obj : audioComboLnrs)
			this.audioCombo.addActionListener(obj);

		this.trackWindow.getPlayButton().setEnabled(true);
		this.trackWindow.getPauseButton().setEnabled(true);
		this.trackWindow.getStopButton().setEnabled(true);
		this.trackWindow.getSlider().setEnabled(true);

		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public void on_endOfPlayerTime_event() {
		this.onTrackWindowToggleButtonClicked("stop");
		this.trackWindow.selectButton("stop");
	}

	public void loadMusic() {
		try {

			Player newPlayer;
			// GainControl gainControl;

			this.currentAudioClip = this.MX.getAudioClipDictionary().get(this.audioCombo.getSelectedItem());

			for (int i = 0; i < this.audioCombo.getItemCount(); i++) {
				newPlayer = Manager.createPlayer(
						new File(this.MX.getAudioClipDictionary().get(this.audioCombo.getItemAt(i)).getFileAudioPath()).toURI()
								.toURL());
				newPlayer.addControllerListener(new ControllerListener() {
					public synchronized void controllerUpdate(ControllerEvent event) {
						if (event instanceof RealizeCompleteEvent) {
							System.out.println("player realized");

							GainControl gainControl = ((Player) event.getSource()).getGainControl();
							gainControl.setMute(true);

							trackWindow.setTrackLength(
									((Double) ((Player) event.getSource()).getDuration().getSeconds()).intValue());
						} else if (event instanceof EndOfMediaEvent) {
							System.out.println("audio terminato");
							playerStart(false); /* stop player and timer */
							on_endOfPlayerTime_event();
						}
					}

				}

				);
				newPlayer.realize();

				this.playerList.add(newPlayer);

			}

			int index = this.audioCombo.getSelectedIndex();
			this.player = this.playerList.get(index);

			// aggiornare lo slider qui non funziona sempre (a volte player non realized?)
			// this.trackWindow.setTrackLength(((Double)this.player.getDuration().getSeconds()).intValue());

		} catch (IOException e) {
			System.err.println("Errore nel carimento del file audio");
			e.printStackTrace();
		} catch (javax.media.NoPlayerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Individua e setta la variabile d'istanza "currentGraphicInstance"
	 *
	 * @param instance la nuova graphicInstance che indica la pagina corrente della
	 *                 partitura
	 */
	/*
	 * public void
	 * setCurrentGraphicInstance(MXData.GraphicInstanceGroup.GraphicInstance
	 * instance) { this.currentGraphicInstance=instance; }
	 */
	/**
	 * Individua e setta la variabile d'istanza "currentGraphicInstance"
	 * comprendendo in quale pagina della partitura ci troviamo in base al punto di
	 * esecuzione del brano
	 *
	 * @param time punto di esecuzione del brano correntem, in centesimi di secondo
	 */
	/*
	 * public void setCurrentGraphicInstance(int time) { MXData.GraphicInstanceGroup
	 * group=this.MX.graphic_instance_group.get((String)this.partitureCombo.
	 * getSelectedItem());
	 * 
	 * if (time==0) this.currentGraphicInstance=group.instances.get(0); else {
	 * for(int i=0;i<group.instances.size();i++) { if
	 * (this.currentAudioClip.spine2time.get(group.instances.get(i).spineEnd)>time)
	 * { this.currentGraphicInstance=group.instances.get(i); break; } } } }
	 * 
	 */

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		jPanel2 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		audioCombo = new javax.swing.JComboBox();
		jLabel1 = new javax.swing.JLabel();
		partitureToggleBtn = new javax.swing.JToggleButton();
		voicesToggleBtn = new javax.swing.JToggleButton();
		trackToggleBtn = new javax.swing.JToggleButton();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		jMenuItem2 = new javax.swing.JMenuItem();

		org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 694, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0,
				232, Short.MAX_VALUE));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("jMXPlayer");

		jLabel1.setText("Traccia corrente");

//        partitureToggleBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainFrame/Resources/spartito.png"))); // NOI18N
		partitureToggleBtn.setText("Partiture");
		partitureToggleBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		partitureToggleBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				partitureToggleBtnActionPerformed(evt);
			}
		});

//        voicesToggleBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainFrame/Resources/voices.png"))); // NOI18N
		voicesToggleBtn.setText("Voci");
		voicesToggleBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		voicesToggleBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				voicesToggleBtnActionPerformed(evt);
			}
		});

//        trackToggleBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainFrame/Resources/player.png"))); // NOI18N
		trackToggleBtn.setText("Traccia");
		trackToggleBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		trackToggleBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				trackToggleBtnActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel1Layout.createSequentialGroup().addContainerGap()
						.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(jPanel1Layout.createSequentialGroup()
										.add(partitureToggleBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(voicesToggleBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(trackToggleBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(jPanel1Layout.createSequentialGroup()
										.add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 101,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(audioCombo, 0, 226, Short.MAX_VALUE)))
						.addContainerGap()));

		jPanel1Layout.linkSize(new java.awt.Component[] { trackToggleBtn, voicesToggleBtn },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel1Layout.createSequentialGroup().addContainerGap().add(jPanel1Layout
						.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
						.add(partitureToggleBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(voicesToggleBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
								.add(trackToggleBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 81,
										Short.MAX_VALUE)))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(audioCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jLabel1))
						.addContainerGap()));

		jMenu1.setText("File");

		jMenuItem1.setText("Open MX");
		jMenuItem1.setToolTipText("Select a .mx file to open");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);
		jMenu1.add(jSeparator1);

		jMenuItem2.setText("Quit");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup().addContainerGap()
								.add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap()));
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup().addContainerGap()
								.add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
		this.setVisible(false);
		System.exit(0);
	}// GEN-LAST:event_jMenuItem2ActionPerformed

	private void partitureToggleBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_partitureToggleBtnActionPerformed
		// this.imageWin.setVisible(this.partitureToggleBtn.isSelected());
		this.partitureSelectionWindow.setVisible(this.partitureToggleBtn.isSelected());
	}// GEN-LAST:event_partitureToggleBtnActionPerformed

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
		this.showOpenMXDialog();
	}// GEN-LAST:event_jMenuItem1ActionPerformed

	private void trackToggleBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_trackToggleBtnActionPerformed
		this.trackWindow.setVisible(this.trackToggleBtn.isSelected());
	}// GEN-LAST:event_trackToggleBtnActionPerformed

	private void voicesToggleBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_voicesToggleBtnActionPerformed
		this.voicesWindow.setVisible(this.voicesToggleBtn.isSelected());
	}// GEN-LAST:event_voicesToggleBtnActionPerformed

	private void trackSliderMouseReleased(java.awt.event.MouseEvent evt) {
		System.out.println("slider CLICKED");

		for (PartitureWindow win : partitureWindows) {
			win.getMarks().hideAllLabel();
		}

		this.trackSliderMouseSomeHowPressed(evt);
		if (!this.audioSliderDragged) {
			/* il cursore non è stato trascinato, quindi dobbiamo riposizionarlo */
			float x = evt.getX();
			int width = this.trackSlider.getWidth();
			int maxValue = this.trackSlider.getMaximum();
			if (x > width)
				x = width;
			else if (x < 0)
				x = 0;

			this.trackSlider.setValue(new Float(maxValue * (x / this.trackSlider.getWidth())).intValue());
		}

		this.audioSliderDragged = false;

		if (this.wasPlaying) {
			System.out.println("chiamo da mouseReleased playerStart(true)");
			this.playerStart(true);
		}
		this.wasPlaying = false;

	}

	private void trackSliderMousePressed(java.awt.event.MouseEvent evt) {
		this.wasPlaying = this.player.getState() == this.player.Started;
		if (this.wasPlaying) {
			System.out.println("chiamo playerStart(false)");
			this.playerStart(false); // we stop the player
		}
	}

	private void trackSliderMouseDragged(java.awt.event.MouseEvent evt) {
		this.audioSliderDragged = true;
		this.trackSliderMouseSomeHowPressed(evt);

	}

	private void trackSliderMouseSomeHowPressed(java.awt.event.MouseEvent evt) {
		System.out.println(new Point(evt.getX(), evt.getY()));
		System.out.println(this.trackSlider.getValue());

		float x = evt.getX();
		int width = this.trackSlider.getWidth();
		int maxValue = this.trackSlider.getMaximum();
		if (x > width)
			x = width;
		else if (x < 0)
			x = 0;

		int media_time = new Float(maxValue * (x / this.trackSlider.getWidth()) * 100).intValue();

		GraphicInstance gInstance;
		for (PartitureWindow window : partitureWindows) {
			gInstance = window.getCurrentGraphicInstance();

			if (media_time < this.currentAudioClip.getSpine2time().get(gInstance.getSpineStart())) {
				// controllo se siamo tornati indietro di una pagina. il controllo per la pagina
				// successiva
				// si trova gia' in updateRectangles: potrei spostare questo là, ma durante
				// l'esecuzione del
				// brano sarebbe un if completamente inutile (l'audio va solo avanti)
				window.loadPrevPage();
			}
		}

		/*
		 * XXX il metodo che segue fa la stessa cosa dell'albero rb ... l'ho scoperto
		 * dopo java.util.List<Integer>
		 * test=Arrays.asList(this.currentAudioClip.time2spine.keySet().toArray(new
		 * Integer[0])); Collections.sort(test); int
		 * idx=Collections.binarySearch(test,media_time);
		 * 
		 * if (idx<0) { media_time=test.get(-idx); }
		 */

		/*
		 * XXX attenzione. dovremmo controllare che la distanza non sia troppo elevata?
		 * (per esempio, se il tempo più vicino fosse dopo 10 sec non dovremmo
		 * evidenziarlo)
		 */
		media_time = (Integer) this.currentAudioClip.getTimeRBTree().getNearest(media_time);
		System.out.println("media_time calcolato " + media_time / 100.0);
		this.player.setMediaTime(new Time(new Double(media_time) / 100.0));
		this.startTime = System.currentTimeMillis() - media_time * 10;

		this.currentStep = System.currentTimeMillis() - 1001; // I assure the slider will be updated next time

		this.updateRectangles(media_time);
	}

	/*
	 * private void partitureComboActionPerformed(java.awt.event.ActionEvent evt){
	 * this.setCurrentGraphicInstance(this.trackWindow.getTrackTime()*100);
	 * this.imageWin.resetPrefetching();
	 * 
	 * this.imageWin.refreshPartitureImage(this.currentGraphicInstance);
	 * this.marks.hideAllLabel(); }
	 */

	private void audioComboActionPerformed(java.awt.event.ActionEvent evt) {

		Player oldPlayer = this.player;
		int index = this.audioCombo.getSelectedIndex();
		this.player = this.playerList.get(index);

		this.currentAudioClip = this.MX.getAudioClipDictionary().get(this.audioCombo.getSelectedItem());

		System.out.println("Passo a " + this.currentAudioClip.getFileAudioPath());
		System.out.println(" ||| lastseen spine " + lastSeenSpine);
		int y = 0;
		if (lastSeenSpine != null)
			y = this.currentAudioClip.getSpine2time().get(lastSeenSpine);
		Time x = new Time(new Double(y) / 100.0D);
		System.out.println("tempo " + x.getSeconds());

		this.player.setMediaTime(x);
		System.out.println("nuovo mediatime " + this.player.getMediaTime().getSeconds());
		if (oldPlayer.getState() == oldPlayer.Started) {
			this.player.getGainControl().setMute(false);
			this.player.start();
			this.player.setMediaTime(x);
			System.out.println("ATTUALE mediatime " + this.player.getMediaTime().getSeconds());
		}
		oldPlayer.getGainControl().setMute(true);
		startTime = System.currentTimeMillis() - y * 10;
		oldPlayer.stop();

		this.trackWindow.setTrackLength(((Double) this.player.getDuration().getSeconds()).intValue());
	}

	public void playerStart(boolean is_starting) {
		if (is_starting) {
			long mediaTime = 0L;
			System.out.println("in player start, currentStep " + this.currentStep);
			if (this.currentStep != 0) {
				// I have paused and now restarting the media
				mediaTime = ((long) (this.player.getMediaNanoseconds() / 1000000.0));
			}
			System.out.println("in player start, mediaTime " + mediaTime);

			System.out.println("st1 db " + this.player.getGainControl().getDB());
			System.out.println("st2 lv " + this.player.getGainControl().getLevel());

			this.player.start();
			this.currentStep = System.currentTimeMillis();
			this.startTime = this.currentStep - mediaTime;
			this.player.getGainControl().setMute(false);
			this.timer.start();

			System.out.println("st 3 db " + this.player.getGainControl().getDB());
			System.out.println("st 4 lv " + this.player.getGainControl().getLevel());

		} else {
			long pauseTime = System.currentTimeMillis();
			this.player.getGainControl().setMute(true);
			this.player.stop();
			this.timer.stop();
			// XXX per quale motivo lo facciamo? stop non azzera il tempo del media, da api
			// (credo - CONTROLLARE)
			// oppure e' per correggere l'errore di stop che generea setmute? (non sarebbe
			// irrisorio? )
			this.player.setMediaTime(new Time(new Double((pauseTime - this.startTime) / 1000.0)));
		}
	}

	// Handle timer event.
	public void actionPerformed(ActionEvent evt) {
		if (this.currentStep == 0)
			return;
		long now = System.currentTimeMillis();
		if (now - this.currentStep >= 1000) {
			this.trackSlider.setValue((int) (now - this.startTime) / 1000);
			// System.out.println("secondo: "+(int)(now-this.startTime)/1000);
			this.currentStep = now;
		}

		this.updateRectangles(Math.round(((float) (now - this.startTime)) / 10));
	}

	/**
	 * @media_time is mesured in 100th of seconds (media_time=100 -> 1 second)
	 */
	public void updateRectangles(int media_time) {

		GraphicInstance gInstance;
		for (PartitureWindow win : partitureWindows) {
			gInstance = win.getCurrentGraphicInstance();
			// controllo se dobbiamo cambiare pagina
			if (media_time > this.currentAudioClip.getSpine2time().get(gInstance.getSpineEnd())) {
				win.loadNextPage();
			}
		}

		// check if there are events associated to the current time
		if (this.currentAudioClip.getSpine2time().containsKey(media_time)) {
			Vector tmpSpines = null;
			// System.out.println(time);
			tmpSpines = this.currentAudioClip.getSpines(media_time);
			// System.out.println("action performed, le spines sono "+tmpSpines.size()+" al
			// tempo "+ time);
			if (media_time == 0)
				return;

			Rectangle tmpCoord;
			String voiceName;
			Voice tmpVoice;

			// highlights all the spines
			for (int i = 0, length = tmpSpines.size(); i < length; i++) {
				// check if the voice must be shown
				this.lastSeenSpine = (String) tmpSpines.get(i);
				voiceName = this.MX.getSpine2voice().get(lastSeenSpine);
				if (voiceName == null) {
					// XXX capire perche' esistono questi risultati. errore nel MX?
					// come puo uno spine event non essere legato ad una voce?
					System.out.println("SPINE2VOICE fallita ! -> " + " spina: " + lastSeenSpine);
					continue;
				}
				tmpVoice = this.MX.getVoices().get(voiceName);

				for (PartitureWindow win : partitureWindows) {
					if (!win.isVisible())
						continue;

					// i get the spatial coordinates of the event
					if (tmpVoice.isVisible())
						tmpCoord = win.getCurrentGraphicInstance().getSpine2point().get(lastSeenSpine);
					else
						tmpCoord = null;

					try {
						if (tmpCoord != null) {
							// voiceName=this.MX.spine2voice.get(tmpSpines.get(i));
							win.getMarks().appendLabel(tmpCoord, tmpVoice);
						} else {
							// Delete possibly visible mark
							tmpCoord = new Rectangle(-100, -100, 1, 1);
							win.getMarks().appendLabel(tmpCoord, tmpVoice);
							// System.out.println("OUT OF TIME -> "+time+" spina: "+lastSeenSpine);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JComboBox audioCombo;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JToggleButton partitureToggleBtn;
	private javax.swing.JToggleButton trackToggleBtn;
	private javax.swing.JToggleButton voicesToggleBtn;
	// End of variables declaration//GEN-END:variables
}
