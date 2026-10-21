
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;



class Demo {
    public static void main(String[] args) {



			JFrame frame = new JFrame("Water Level Observer");
			// -----
			frame.getContentPane().setBackground(Color.decode("#080D16"));

				JSlider slider = new JSlider(JSlider.VERTICAL, 0, 100, 0);
				slider.setBorder(new EmptyBorder(10, 50, 10, 10));
				// -----
				slider.setBackground(Color.decode("#080D16")); slider.setForeground(Color.decode("#60CFFF"));
				frame.add(BorderLayout.WEST, slider);      



				JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
				// -----
				panel.setBackground(Color.decode("#080D16"));
					JPanel waterCard = new JPanel();
					// -----
					waterCard.setBackground(Color.decode("#10233F")); waterCard.setBorder(BorderFactory.createLineBorder(Color.decode("#1B3B60"), 2));
							JLabel display1 = new JLabel("Water Level: 0%");
							// -----
							display1.setForeground(Color.decode("#D9F2FF"));
			// ----- center
			display1.setHorizontalAlignment(SwingConstants.CENTER);
							JButton button1 = new JButton("Veiw Detail");
							// -----
							button1.setBackground(Color.decode("#1B3B60")); button1.setForeground(Color.decode("#D9F2FF"));

					JPanel alamCard = new JPanel();
					// -----
					alamCard.setBackground(Color.decode("#10233F")); alamCard.setBorder(BorderFactory.createLineBorder(Color.decode("#1B3B60"), 2));
							JLabel display2 = new JLabel("Alarm: OFF");
							// -----
							display2.setForeground(Color.decode("#D9F2FF"));
			// ----- center
			display2.setHorizontalAlignment(SwingConstants.CENTER);
							JButton button2 = new JButton("Veiw Detail");
							// -----
							button2.setBackground(Color.decode("#1B3B60")); button2.setForeground(Color.decode("#D9F2FF"));

					JPanel damCard = new JPanel();
					// -----
					damCard.setBackground(Color.decode("#10233F")); damCard.setBorder(BorderFactory.createLineBorder(Color.decode("#1B3B60"), 2));
							JLabel display3 = new JLabel("Damm: Close");
							// -----
							display3.setForeground(Color.decode("#D9F2FF"));
			// ----- center
			display3.setHorizontalAlignment(SwingConstants.CENTER);
							JButton button3 = new JButton("Veiw Detail");
							// -----
							button3.setBackground(Color.decode("#1B3B60")); button3.setForeground(Color.decode("#D9F2FF"));

					JPanel smsCard = new JPanel();
					// -----
					smsCard.setBackground(Color.decode("#10233F")); smsCard.setBorder(BorderFactory.createLineBorder(Color.decode("#1B3B60"), 2));
							JLabel display4 = new JLabel("SMS: Normal");
							// -----
							display4.setForeground(Color.decode("#D9F2FF"));
			// ----- center
			display4.setHorizontalAlignment(SwingConstants.CENTER);
							JButton button4 = new JButton("Veiw Detail");
							// -----
							button4.setBackground(Color.decode("#1B3B60")); button4.setForeground(Color.decode("#D9F2FF"));


        // ++++++++  control room and observer objects
        ControlRoom controlRoom = new ControlRoom();

        // +++++ +++    Create the four detail-window observers
        Display waterDisplay = new Display();
        Alarm alarm = new Alarm();
        Splitter splitter = new Splitter();
        SMSSender sms = new SMSSender();

        // ++++       Register all observers with the control room
        controlRoom.addWaterLevelObserver(waterDisplay);
        controlRoom.addWaterLevelObserver(alarm);
        controlRoom.addWaterLevelObserver(splitter);
        controlRoom.addWaterLevelObserver(sms);

        // +++++  Keep the four main dashboard cards updated
        controlRoom.addWaterLevelObserver(new WaterDashboard(
            display1, display2, display3, display4
        ));


        // ++++++++++  buttons underneath the labels

        waterCard.setLayout(new BorderLayout(10, 10));
        waterCard.add(display1, BorderLayout.CENTER);
        waterCard.add(button1, BorderLayout.SOUTH);

        alamCard.setLayout(new BorderLayout(10, 10));
        alamCard.add(display2, BorderLayout.CENTER);
        alamCard.add(button2, BorderLayout.SOUTH);

        damCard.setLayout(new BorderLayout(10, 10));
        damCard.add(display3, BorderLayout.CENTER);
        damCard.add(button3, BorderLayout.SOUTH);

        smsCard.setLayout(new BorderLayout(10, 10));
        smsCard.add(display4, BorderLayout.CENTER);
        smsCard.add(button4, BorderLayout.SOUTH);



        panel.add(waterCard);
        panel.add(alamCard);
        panel.add(damCard);
        panel.add(smsCard);


        frame.add(panel);


        // ++++++++++  Button under the first panel  click actions ++++ 

				// +++++   Open the water display ob  win 
				button1.addActionListener(e -> {
					waterDisplay.showWindow(controlRoom.getWaterLevel());
				});

				// +++++  Open the alarm obser
				button2.addActionListener(e -> {
					alarm.showWindow(controlRoom.getWaterLevel());
				});

				// +++++         Open  spliter  window
				button3.addActionListener(e -> {
					splitter.showWindow(controlRoom.getWaterLevel());
				});

				// +++++  Open the SMS obse  window
				button4.addActionListener(e -> {
					sms.showWindow(controlRoom.getWaterLevel());
				});

/*
				// ++++++++++  Slider updates the control room

				slider.addChangeListener(e -> {
					controlRoom.setWaterLevel(slider.getValue());
				});

*/





				 // ++++++++++  Slider force open sms 

				int[] x = {0};

				slider.addChangeListener(e -> {
					int level = slider.getValue();

					controlRoom.setWaterLevel(level);

					// -----
					if (level > 75 && x[0] <= 75) {
						sms.showWindow(level);     //  open window 
					}

					x[0] = level;
				});














				frame.setSize(600, 400);
				// ----- font size
				frame.addComponentListener(new ComponentAdapter() {
				    public void componentResized(ComponentEvent e) {

				        int fontSize = frame.getWidth() / 32;

				        display1.setFont(new Font(
				            "Segoe UI", Font.BOLD, Math.max(12, fontSize)
				        ));
				        display2.setFont(new Font(
				            "Segoe UI", Font.BOLD, Math.max(12, fontSize)
				        ));
				        display3.setFont(new Font(
				            "Segoe UI", Font.BOLD, Math.max(12, fontSize)
				        ));
				        display4.setFont(new Font(
				            "Segoe UI", Font.BOLD, Math.max(12, fontSize)
				        ));
				    }
				});
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		}



class WaterLevelObserver {
    public void update(int waterLevel) {
    }
}



class ControlRoom {
    private WaterLevelObserver[] observers = new WaterLevelObserver[0];
    private int waterLevel = 0;

    public void addWaterLevelObserver(WaterLevelObserver observer) {
        WaterLevelObserver[] temp = new WaterLevelObserver[observers.length + 1];

        for (int i = 0; i < observers.length; i++) {
            temp[i] = observers[i];
        }

        temp[observers.length] = observer;
        observers = temp;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;

        for (WaterLevelObserver observer : observers) {
            observer.update(waterLevel);
        }
    }
}



class Display extends WaterLevelObserver {
    private JFrame frame;
    private JLabel label;
    private JProgressBar bar;

    public void showWindow(int waterLevel) {
        // +++++             Create the window only when the button is clicked
        if (frame == null) {
            frame = new JFrame("Water Level Details");
            // -----
            frame.getContentPane().setBackground(Color.decode("#080D16"));

            JPanel waterPanel = new JPanel(new BorderLayout(15, 25));
            waterPanel.setBackground(Color.decode("#080D16"));
            waterPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

            label = new JLabel();
            // ----- center
            label.setHorizontalAlignment(SwingConstants.CENTER); label.setVerticalAlignment(SwingConstants.CENTER);
            // -----
            label.setForeground(Color.decode("#D9F2FF"));

            // water card bar progess bar 
            bar = new JProgressBar(0, 100);

            bar.setValue(65);
            bar.setStringPainted(true);
            bar.setForeground(Color.decode("#60CFFF"));
            bar.setBackground(Color.decode("#1B3B60"));
            bar.setBorderPainted(false);
            bar.setFont(new Font("Segoe UI", Font.BOLD, 14));

            waterPanel.add(label, BorderLayout.NORTH);
            waterPanel.add(bar, BorderLayout.CENTER);

            frame.add(waterPanel);
            frame.setSize(350, 200);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            // ----- font size
            FontSizer.install(frame, label, bar);
        }

        //                Show the latest water level
        update(waterLevel);
        frame.setVisible(true);
    }

    public void update(int waterLevel) {
        // ++++               Update the label when the slider changes
        if (label != null) {
            label.setText("Water Level: " + waterLevel + "%");
            bar.setValue(waterLevel);
        }
    }
}


// +++++ Alarm observer
class Alarm extends WaterLevelObserver {
    private JFrame frame;
    private JLabel label;




    public void showWindow(int waterLevel) {
        // +++++        Create the alarm window when press buttoin 
			if (frame == null) {
				frame = new JFrame("Alarm Details");
				// -----
				frame.getContentPane().setBackground(Color.decode("#080D16"));
				label = new JLabel();
            // ----- center
            label.setHorizontalAlignment(SwingConstants.CENTER); label.setVerticalAlignment(SwingConstants.CENTER);
				// -----
				label.setForeground(Color.decode("#D9F2FF"));
				label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				frame.add(label);
				frame.setSize(350, 200);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                // ----- font size
                FontSizer.install(frame, label, null);
			}



        // +++++       Show the current alarm status
        update(waterLevel);
        frame.setVisible(true);
    }


    public void update(int waterLevel) {
			// +++++  Update the alarm status
			if (label != null) {
				if (waterLevel > 60) {
					label.setText("Alarm: ON");
					
					// -----
					label.setForeground(Color.decode("#60CFFF"));
				} else {
					label.setText("Alarm: OFF");
					
					// -----
					label.setForeground(Color.decode("#D9F2FF"));
				}
			}
		}
	}


// ++++         Splitter observer
class Splitter extends WaterLevelObserver {
    private JFrame frame;
    private JLabel label;

    public void showWindow(int waterLevel) {
        // +++++             Create the spliter window when prss

			if (frame == null) {
				frame = new JFrame("Splitter Details");
				
				// -----
				frame.getContentPane().setBackground(Color.decode("#080D16"));
				label = new JLabel();
            // ----- center
            label.setHorizontalAlignment(SwingConstants.CENTER); label.setVerticalAlignment(SwingConstants.CENTER);
				// -----
				
				label.setForeground(Color.decode("#D9F2FF"));
				label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				frame.add(label);
				frame.setSize(350, 200);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                // ----- font size
                FontSizer.install(frame, label, null);
        }


			// +++++  Show the current splitter status
			update(waterLevel);
			frame.setVisible(true);
    }



    public void update(int waterLevel) {
        // ++++  Update the splitter status
        if (label != null) {
            if (waterLevel > 75) {
                label.setText("Damm: Open");
                // -----
                label.setForeground(Color.decode("#60CFFF"));
            } else {
                label.setText("Damm: Close");
                // -----
                label.setForeground(Color.decode("#D9F2FF"));
            }
        }
    }
}


// ++++   SMS

class SMSSender extends WaterLevelObserver {
    private JFrame frame;
    private JLabel label;

    public void showWindow(int waterLevel) {
        // +++++      Create the sms window when prss 
        if (frame == null) {
            frame = new JFrame("SMS Details");
            // -----
            frame.getContentPane().setBackground(Color.decode("#080D16"));
            label = new JLabel();
            // ----- center
            label.setHorizontalAlignment(SwingConstants.CENTER); label.setVerticalAlignment(SwingConstants.CENTER);
            // -----
            label.setForeground(Color.decode("#D9F2FF"));
            label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            frame.add(label);
            frame.setSize(350, 200);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            // ----- font size
            FontSizer.install(frame, label, null);
        }

        // ++++   Show the current sms massage

        update(waterLevel);
        frame.setVisible(true);
    }

    public void update(int waterLevel) {
        // +++++        Update the SMS message
        if (label != null) {
            label.setText(getMessage(waterLevel));
            // -----
            label.setForeground(waterLevel > 60 ? Color.decode("#60CFFF") : Color.decode("#D9F2FF"));
        }
    }


//       msg if else 
		public String getMessage(int waterLevel) {

			if (waterLevel > 75) {
				return "<html><center><font color='#FF9F43'>IMMEDIATE ACTIONS REQUIRED:</font><br><font color='white'>Evacuate Low-Lying Areas:</font></center></html>";
			} else if (waterLevel > 60) {
				return "Water level are Rising";
			} else {
				return "Normal";
			}
		}
	}




// +++ ++       Observer for the four dashboard cards
class WaterDashboard extends WaterLevelObserver {
    private JLabel display1;
    private JLabel display2;
    private JLabel display3;
    private JLabel display4;


			public WaterDashboard(JLabel display1, JLabel display2,
								  JLabel display3, JLabel display4) {
				this.display1 = display1;
				this.display2 = display2;
				this.display3 = display3;
				this.display4 = display4;
			}

    public void update(int waterLevel) {

        // ++++    Update the water-level card
        display1.setText("Water Level: " + waterLevel + "%");



			// +++++  Update the alarm card
			if (waterLevel > 60) {
				display2.setText("Alarm: ON");
				// -----
				display2.setForeground(Color.decode("#60CFFF"));
			} else {
				display2.setText("Alarm: OFF");
				// -----
				display2.setForeground(Color.decode("#D9F2FF"));
			}



        // +++++ A Update   the spliter    card

			if (waterLevel > 75) {
				display3.setText("Damm: Open");
				// -----
				display3.setForeground(Color.decode("#60CFFF"));
			} else {
				display3.setText("Damm: Close");
				// -----
				display3.setForeground(Color.decode("#D9F2FF"));
			}


        // +++++  Update the sms    card
        SMSSender sms = new SMSSender();
        display4.setText(sms.getMessage(waterLevel));
        // -----
        display4.setForeground(waterLevel > 60 ? Color.decode("#60CFFF") : Color.decode("#D9F2FF"));
    }
}


// ----- font size
class FontSizer {
    public static void install(JFrame frame, JLabel label, JProgressBar bar) {
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                int fontSize = frame.getWidth() / 32;
                Font font = new Font("Segoe UI", Font.BOLD, Math.max(12, fontSize));

                label.setFont(font);

                if (bar != null) {
                    bar.setFont(font);
                }
            }
        });
    }
}
