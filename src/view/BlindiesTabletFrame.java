package view;

import controller.Blindies;
import enums.PatternMode;
import enums.SendType;

import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.border.TitledBorder; 
import javax.swing.UIManager.*;

import view.components.BlindiesSlider;

public class BlindiesTabletFrame extends JFrame{
	private static final long serialVersionUID = -2139229150850736977L;

	// The model
	Blindies blindies;

	// Main panel
	private JPanel panel;

    // Pattern Mode
    private JPanel patternPanel;
    private JButton patternAllOffButton;
    private JComboBox<PatternMode> modeBox = new JComboBox<PatternMode>(PatternMode.values());
    
    // Send On Type
    private JPanel sendOnPanel;
	private JButton sendButton;
	private JButton sendButtonLeft;
	private JButton sendButtonRight;
	private JComboBox<SendType> sendType = new JComboBox<SendType>(SendType.values());	

    // Network
    private JPanel networkPanel;
    private JTextField portField;
    private JLabel portLabel;
    private JTextField hostField;
    private JLabel hostLabel;
    private JButton targetEdit;

    // Slider 1
    private JPanel slider1Panel;
    private JSpinner spinner1;
    private JButton slider1UpButton;
    private JButton slider1DownButton;
    private JButton slider1MinButton;
    private JSlider slider1;
    private JButton slider1MaxButton;

    // Slider 2
    private JPanel slider2Panel;
    private JSpinner spinner2;
    private JButton slider2UpButton;
    private JButton slider2DownButton;
    private JButton slider2MinButton;
    private JSlider slider2;
    private JButton slider2MaxButton;
    
    // Slider 3
    private JPanel slider3Panel;
    private JSpinner spinner3;
    private JButton slider3UpButton;
    private JButton slider3DownButton;
    private JButton slider3MinButton;
    private JSlider slider3;
    private JButton slider3MaxButton;
    
    // Messages
    private JPanel messagePanel;
    private JScrollPane messageScrollPane;
    private JTextArea messageTextArea;
	private Integer messageCount = 0;
    
    private TitledBorder slider1Title;
    private TitledBorder slider2Title;
    private TitledBorder slider3Title;

    // Arrays of buttons for disable/enablement    
    private ArrayList slider1PanelButtons;
    private ArrayList slider2PanelButtons;
    private ArrayList slider3PanelButtons;
    
    private static final int MAX_MESSAGE_BOX_LENGTH = 1000;
    
    public BlindiesTabletFrame(Blindies blindies) {
    	this.blindies = blindies;
        initComponents();        
    }

	// Setup the frame	
    private void initComponents() {
        // Panels
        // --- Main panel
        panel = new JPanel();
        
        // --- Sub panels
        // ------ pattern panel components
        patternPanel = new JPanel();
        patternAllOffButton = new JButton("All Off");
        patternAllOffButton.addActionListener(e -> patternAllOffClicked());
                		
        // ------ send on panel components
        sendOnPanel = new JPanel();
		sendButton = new JButton("Send Command");
		sendButtonLeft = new JButton("Send");
		sendButtonRight = new JButton("Send");
		sendButton.addActionListener(e -> sendClicked());
		sendButtonLeft.addActionListener(e -> sendClicked());
		sendButtonRight.addActionListener(e -> sendClicked());

        // ------ network panel components
        networkPanel = new JPanel();
        hostLabel = new JLabel("Broadcast IP");
        portLabel = new JLabel("UDP Port");
        hostField = new JTextField(8);
        hostField.setText(blindies.getHost());
        hostField.setHorizontalAlignment(SwingConstants.RIGHT);
        hostField.setEditable(true);
        portField = new JTextField(8);
        portField.setText(Integer.toString(blindies.getPort()));
        portField.setHorizontalAlignment(SwingConstants.RIGHT);
        portField.setEditable(true);
        targetEdit = new JButton("GOGOGO!");
		targetEdit.addActionListener(e -> targetEditClicked());
        
        // ------ slider 1 panel components
        slider1Panel = new JPanel();
        slider1 = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        slider1.setUI(new BlindiesSlider(slider1));
        spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        slider1DownButton = new JButton();
        slider1UpButton = new JButton();
        slider1MinButton = new JButton();
        slider1MaxButton = new JButton();

        slider1PanelButtons = new ArrayList<JButton>();
        slider1PanelButtons.add(slider1DownButton);
        slider1PanelButtons.add(slider1UpButton);
        slider1PanelButtons.add(slider1MinButton);
        slider1PanelButtons.add(slider1MaxButton);

        slider1.addChangeListener(e -> slider1Changed());
        spinner1.addChangeListener(e -> spinner1Changed());
        slider1DownButton.addActionListener(e -> sliderStep(slider1, -25));
        slider1UpButton.addActionListener(e -> sliderStep(slider1, 25));
        slider1MinButton.addActionListener(e -> sliderMin(slider1));
        slider1MaxButton.addActionListener(e -> sliderMax(slider1));
        
        // ------ slider 2 panel components
        slider2Panel = new JPanel();
        slider2 = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        slider2.setUI(new BlindiesSlider(slider2));
        spinner2 = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        slider2DownButton = new JButton();
        slider2UpButton = new JButton();
        slider2MinButton = new JButton();
        slider2MaxButton = new JButton();

        slider2PanelButtons = new ArrayList<JButton>();
        slider2PanelButtons.add(slider2DownButton);
        slider2PanelButtons.add(slider2UpButton);
        slider2PanelButtons.add(slider2MinButton);
        slider2PanelButtons.add(slider2MaxButton);

        slider2.addChangeListener(e -> slider2Changed());
        spinner2.addChangeListener(e -> spinner2Changed());
        slider2DownButton.addActionListener(e -> sliderStep(slider2, -25));
        slider2UpButton.addActionListener(e -> sliderStep(slider2, 25));
        slider2MinButton.addActionListener(e -> sliderMin(slider2));
        slider2MaxButton.addActionListener(e -> sliderMax(slider2));

        // ------ slider 3 panel components
        slider3Panel = new JPanel();
        slider3 = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        slider3.setUI(new BlindiesSlider(slider3));
        spinner3 = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        slider3DownButton = new JButton();
        slider3UpButton = new JButton();
        slider3MinButton = new JButton();
        slider3MaxButton = new JButton();

        slider3PanelButtons = new ArrayList<JButton>();
        slider3PanelButtons.add(slider3DownButton);
        slider3PanelButtons.add(slider3UpButton);
        slider3PanelButtons.add(slider3MinButton);
        slider3PanelButtons.add(slider3MaxButton);

        slider3.addChangeListener(e -> slider3Changed());
        spinner3.addChangeListener(e -> spinner3Changed());
        slider3DownButton.addActionListener(e -> sliderStep(slider3, -25));
        slider3UpButton.addActionListener(e -> sliderStep(slider3, 25));
        slider3MinButton.addActionListener(e -> sliderMin(slider3));
        slider3MaxButton.addActionListener(e -> sliderMax(slider3));

        // ------ messages panel components
        messagePanel = new JPanel();
        messageScrollPane = new JScrollPane();
        messageTextArea = new JTextArea();


		// Set borders, sizing, and more properties
				
		// -- pattern panel border
		patternPanel.setBorder(
		    BorderFactory.createTitledBorder(
		        null, "PATTERN", 
		        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
		        javax.swing.border.TitledBorder.DEFAULT_POSITION, 
		        new Font("Lucida Grande", 0, 24)));

		// -- send on panel border
        sendOnPanel.setBorder(
        	BorderFactory.createTitledBorder(null, "SEND ON", 	
        	javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
        	javax.swing.border.TitledBorder.DEFAULT_POSITION, 
        	new Font("Lucida Grande", 0, 24)));

		// -- network panel border
        networkPanel.setBorder(
        	BorderFactory.createTitledBorder(null, "NETWR0K", 
        	javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
        	javax.swing.border.TitledBorder.DEFAULT_POSITION, 
        	new Font("Lucida Grande", 0, 24))); // NOI18N

		// -- message panel
        messagePanel.setBorder(BorderFactory.createTitledBorder("Messages & Sendies"));
		messageTextArea.setEditable(false);
        messageTextArea.setColumns(20);
        messageTextArea.setRows(3);
        messageTextArea.setText("All application messages will be logged here as well as stdout/stderr, with the exception of stack traces.\n----------------------------------------------------------------------\n");
        messageScrollPane.setViewportView(messageTextArea);

		// -- slider 1
		// ---- panel
		slider1Title = BorderFactory.createTitledBorder(
                null, "BRIGHTNESS", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Lucida Grande", 0, 24));

        slider1Panel.setBorder(slider1Title);
        
        // ---- slider 1 properties
        slider1.setPaintTicks(true);
        slider1.setBorder(BorderFactory.createTitledBorder(""));
        slider1.setSize(new Dimension(190, 120));

		// ---- slider 1 buttons
		slider1DownButton.setFont(
			slider1DownButton.getFont().deriveFont(
				slider1DownButton.getFont().getStyle() | java.awt.Font.BOLD, 			
				slider1DownButton.getFont().getSize()+11));
        slider1DownButton.setText("-25");

        slider1UpButton.setFont(
			slider1UpButton.getFont().deriveFont(
				slider1UpButton.getFont().getStyle() | java.awt.Font.BOLD, 			
				slider1UpButton.getFont().getSize()+11));
        slider1UpButton.setText("+25");

        slider1MinButton.setFont(
        	slider1MinButton.getFont().deriveFont(
        		slider1MinButton.getFont().getStyle() | java.awt.Font.BOLD, 		
        		slider1MinButton.getFont().getSize()+8));
        slider1MinButton.setText("min");

        slider1MaxButton.setFont(
        	slider1MaxButton.getFont().deriveFont(
        		slider1MaxButton.getFont().getStyle() | java.awt.Font.BOLD, 
        		slider1MaxButton.getFont().getSize()+6));
        slider1MaxButton.setText("max");

		// -- slider 2
		// ---- panel
		slider2Title = BorderFactory.createTitledBorder(
                null, "BRIGHTNESS", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Lucida Grande", 0, 24));

        slider2Panel.setBorder(slider2Title);

        // ---- slider 2 properties                
        slider2.setPaintTicks(true);
        slider2.setBorder(BorderFactory.createTitledBorder(""));
        slider2.setSize(new Dimension(190, 120));

		// ---- slider 2 buttons
		slider2DownButton.setFont(
			slider2DownButton.getFont().deriveFont(
				slider2DownButton.getFont().getStyle() | java.awt.Font.BOLD, 			
				slider2DownButton.getFont().getSize()+11));
        slider2DownButton.setText("-25");

        slider2UpButton.setFont(
			slider2UpButton.getFont().deriveFont(
				slider2UpButton.getFont().getStyle() | java.awt.Font.BOLD, 			
				slider2UpButton.getFont().getSize()+11));
        slider2UpButton.setText("+25");

        slider2MinButton.setFont(
        	slider2MinButton.getFont().deriveFont(
        		slider2MinButton.getFont().getStyle() | java.awt.Font.BOLD, 		
        		slider2MinButton.getFont().getSize()+8));
        slider2MinButton.setText("min");

        slider2MaxButton.setFont(
        	slider2MaxButton.getFont().deriveFont(
        		slider2MaxButton.getFont().getStyle() | java.awt.Font.BOLD, 
        		slider2MaxButton.getFont().getSize()+6));
        slider2MaxButton.setText("max");

        sendButtonLeft.setFont(
        	sendButtonLeft.getFont().deriveFont(
        		sendButtonLeft.getFont().getStyle() | java.awt.Font.BOLD, 
        		sendButtonLeft.getFont().getSize()+50));

        sendButtonRight.setFont(
        	sendButtonRight.getFont().deriveFont(
        		sendButtonRight.getFont().getStyle() | java.awt.Font.BOLD, 
        		sendButtonRight.getFont().getSize()+50));

		// -- slider 3
		// ---- panel
		slider3Title = BorderFactory.createTitledBorder(
                null, "BRIGHTNESS", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Lucida Grande", 0, 24));
                
        slider3Panel.setBorder(slider3Title);

		// ---- slider 3 properties                
        slider3.setPaintTicks(true);
        slider3.setBorder(BorderFactory.createTitledBorder(""));
        slider3.setSize(new Dimension(190, 120));

		// ---- slider 3 buttons
		slider3DownButton.setFont(
			slider3DownButton.getFont().deriveFont(
				slider3DownButton.getFont().getStyle() | java.awt.Font.BOLD, 			
				slider3DownButton.getFont().getSize()+11));
        slider3DownButton.setText("-25");

        slider3UpButton.setFont(
			slider3UpButton.getFont().deriveFont(
				slider3UpButton.getFont().getStyle() | java.awt.Font.BOLD, 			
				slider3UpButton.getFont().getSize()+11));
        slider3UpButton.setText("+25");

        slider3MinButton.setFont(
        	slider3MinButton.getFont().deriveFont(
        		slider3MinButton.getFont().getStyle() | java.awt.Font.BOLD, 		
        		slider3MinButton.getFont().getSize()+8));
        slider3MinButton.setText("min");

        slider3MaxButton.setFont(
        	slider3MaxButton.getFont().deriveFont(
        		slider3MaxButton.getFont().getStyle() | java.awt.Font.BOLD, 
        		slider3MaxButton.getFont().getSize()+6));
        slider3MaxButton.setText("max");
		
		// Setup main panel with GridBagLayout and add to the 
		panel.setLayout(new GridBagLayout());
		this.add(panel);

		// pattern panel
		patternPanel.setLayout(new BorderLayout());
		modeBox.setUI(new MetalComboBoxUI());
		modeBox.setBackground(new java.awt.Color(255, 153, 0));
		modeBox.setFont(modeBox.getFont().deriveFont(modeBox.getFont().getSize()+16f));
		modeBox.addItemListener(e -> modeChanged());
		patternPanel.add(modeBox, BorderLayout.CENTER);
		patternPanel.add(patternAllOffButton, BorderLayout.LINE_END);
		addItem(panel, patternPanel, 1, 1, 1, 1, GridBagConstraints.LINE_START, 100, 0, GridBagConstraints.HORIZONTAL);
		
		// send on panel
		sendOnPanel.setLayout(new BorderLayout());
		sendType.setUI(new MetalComboBoxUI());
		sendType.setBackground(new java.awt.Color(255, 153, 0));		
		sendType.setFont(sendType.getFont().deriveFont(sendType.getFont().getSize()+16f));
		sendType.addItemListener(e -> sendTypeChanged());
		sendOnPanel.add(sendType, BorderLayout.CENTER);
		sendOnPanel.add(sendButton, BorderLayout.LINE_END);
		addItem(panel, sendOnPanel, 2, 1, 1, 1, GridBagConstraints.LINE_START, 0, 0);

		// network panel
		networkPanel.setLayout(new GridBagLayout());
		addItem(networkPanel, hostField, 1, 1, 1, 1, GridBagConstraints.LINE_START);
		addItem(networkPanel, hostLabel, 2, 1, 1, 1, GridBagConstraints.LINE_START);
		addItem(networkPanel, portField, 1, 2, 1, 1, GridBagConstraints.LINE_START);
		addItem(networkPanel, portLabel, 2, 2, 1, 1, GridBagConstraints.LINE_START);
		addItem(networkPanel, targetEdit, 3, 1, 1, 2, GridBagConstraints.LINE_START);
		addItem(panel, networkPanel, 3, 1, 1, 1, GridBagConstraints.LINE_START, 100, 0, GridBagConstraints.HORIZONTAL);

		// slider 1
		slider1Panel.setLayout(new BorderLayout());
		JPanel slider1Subpanel = new JPanel();
		slider1Subpanel.setLayout(new GridBagLayout());
		addItem(slider1Subpanel, spinner1, 1, 1, 1, 2, GridBagConstraints.LINE_START);
		addItem(slider1Subpanel, slider1UpButton, 2, 1, 1, 1, GridBagConstraints.LINE_START);
		addItem(slider1Subpanel, slider1DownButton, 2, 2, 1, 1, GridBagConstraints.LINE_START);
		addItem(slider1Subpanel, slider1MinButton, 3, 1, 1, 2, GridBagConstraints.LINE_START);
		slider1Panel.add(slider1Subpanel, BorderLayout.LINE_START);
		slider1Panel.add(slider1, BorderLayout.CENTER);
		slider1Panel.add(slider1MaxButton, BorderLayout.LINE_END);
		addItem(panel, slider1Panel, 1, 2, 14, 1, GridBagConstraints.LINE_START, 100, 50, GridBagConstraints.HORIZONTAL);
		
		// slider 2
		slider2Panel.setLayout(new BorderLayout());
		JPanel slider2Subpanel = new JPanel();
		slider2Subpanel.setLayout(new GridBagLayout());
		addItem(slider2Subpanel, spinner2, 1, 1, 1, 2, GridBagConstraints.LINE_START);
		addItem(slider2Subpanel, slider2UpButton, 2, 1, 1, 1, GridBagConstraints.LINE_START);
		addItem(slider2Subpanel, slider2DownButton, 2, 2, 1, 1, GridBagConstraints.LINE_START);
		addItem(slider2Subpanel, slider2MinButton, 3, 1, 1, 2, GridBagConstraints.LINE_START);
		slider2Panel.add(slider2Subpanel, BorderLayout.LINE_START);
		slider2Panel.add(slider2, BorderLayout.CENTER);
		slider2Panel.add(slider2MaxButton, BorderLayout.LINE_END);
		addItem(panel, slider2Panel, 1, 3, 14, 1, GridBagConstraints.LINE_START, 100, 50, GridBagConstraints.HORIZONTAL);
		
		// slider 3
		slider3Panel.setLayout(new BorderLayout());
		JPanel slider3Subpanel = new JPanel();
		slider3Subpanel.setLayout(new GridBagLayout());
		addItem(slider3Subpanel, spinner3, 1, 1, 1, 2, GridBagConstraints.LINE_START);
		addItem(slider3Subpanel, slider3UpButton, 2, 1, 1, 1, GridBagConstraints.LINE_START);
		addItem(slider3Subpanel, slider3DownButton, 2, 2, 1, 1, GridBagConstraints.LINE_START);
		addItem(slider3Subpanel, slider3MinButton, 3, 1, 1, 2, GridBagConstraints.LINE_START);
		slider3Panel.add(slider3Subpanel, BorderLayout.LINE_START);
		slider3Panel.add(slider3, BorderLayout.CENTER);
		slider3Panel.add(slider3MaxButton, BorderLayout.LINE_END);
		addItem(panel, slider3Panel, 1, 4, 14, 1, GridBagConstraints.LINE_START, 100, 50, GridBagConstraints.HORIZONTAL);
		
		// messages
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add(sendButtonLeft, BorderLayout.LINE_START);
		messagePanel.add(messageScrollPane, BorderLayout.CENTER);
		messagePanel.add(sendButtonRight, BorderLayout.LINE_END);
		addItem(panel, messagePanel, 1, 5, 14, 1, GridBagConstraints.LINE_START, 100, 100, GridBagConstraints.BOTH);

		// Set window properties
		this.setSize(1200, 750);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Chaotic Blindies");
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		// Set something else as default focus
		messagePanel.requestFocusInWindow();
		initializeDefaults();
    }
    
    private void initializeDefaults() {
		spinner1.setValue(0);
		spinner2.setValue(0);
		spinner3.setValue(0);
		modeBox.setSelectedIndex(1);
		modeBox.setSelectedItem(PatternMode.SOLID);
		sendType.setSelectedIndex(1);
		sendType.setSelectedItem(SendType.SLIDER_ONLY);;    
    }
 
	// Event handlers
	public void clickSendButton() {
		sendClicked();
	}
	
	public void setSendType(SendType sendType) {
		this.sendType.setSelectedItem(sendType);
	}
	
	// Message box appender
	public void appendMessage(String string) {
		// avoid the message count getting out of control
		if (this.messageCount > 4000000) {
		    this.messageCount = 0;
		}

	    this.messageCount++;
	    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	    
	    if (this.messageTextArea.getDocument().getLength() > MAX_MESSAGE_BOX_LENGTH) {
	    	this.messageTextArea.setText("");
	    }
	    
	    this.messageTextArea.append("[" + this.messageCount.toString() + "] [" + timeStamp + "] " + string + "\n");
	    this.messageTextArea.setCaretPosition(this.messageTextArea.getDocument().getLength());
	}
	
	// Mode change handler
	public void modeChanged() {
		PatternMode mode = (PatternMode) modeBox.getSelectedItem();
		editArg(mode.arg1(), slider1Title, slider1, spinner1, slider1Panel, slider1PanelButtons);
		editArg(mode.arg2(), slider2Title, slider2, spinner2, slider2Panel, slider2PanelButtons);
		editArg(mode.arg3(), slider3Title, slider3, spinner3, slider3Panel, slider3PanelButtons);
		blindies.setMode(mode);
	}
	
	private void editArg(String arg, TitledBorder title, JSlider slider, JSpinner spinner, JPanel panel, ArrayList<JButton> buttons) {
		boolean enable = (arg != null);
		if (!enable) 
			arg = "N/A";
		title.setTitle(arg);
		slider.setEnabled(enable);
		spinner.setEnabled(enable);
		for (JButton button : buttons) {
		  button.setEnabled(enable);
		}
		panel.repaint();
	}
	
	// Slider and spinner handlers
	public void slider1Changed() {
		spinner1.setValue(slider1.getValue());
		blindies.setValue1(slider1.getValue());
	}
	public void spinner1Changed() {
		slider1.setValue((int) spinner1.getValue());
		blindies.setValue1((int) spinner1.getValue());
	}
	
	public void slider2Changed() {
		spinner2.setValue(slider2.getValue());
		blindies.setValue2(slider2.getValue());
	}
	public void spinner2Changed() {
		slider2.setValue((int) spinner2.getValue());
		blindies.setValue2((int) spinner2.getValue());
	}
	
	public void slider3Changed() {
		spinner3.setValue(slider3.getValue());
		blindies.setValue3(slider3.getValue());
	}
	public void spinner3Changed() {
		slider3.setValue((int) spinner3.getValue());
		blindies.setValue3((int) spinner3.getValue());
	}
	
	public void sendTypeChanged() {
		blindies.setSendType((SendType) sendType.getSelectedItem());
	}
	
	public void sendClicked() {
		try {
			blindies.sendClicked();
		} catch (IOException e) {
			e.printStackTrace();
			appendMessage("There was a problem sending a packet.");
		}
	}
	
	public void targetEditClicked() {
		try {
			blindies.setTarget(hostField.getText(), Integer.parseInt(portField.getText()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			 appendMessage("There was a problem with the host you provided, please try another.");
		}
	}
 
    private void patternAllOffClicked() {
    	modeBox.setSelectedIndex(PatternMode.getDefault().ordinal());
		modeBox.setSelectedItem(PatternMode.getDefault());    	
		clickSendButton();
    }

    private void sliderMax(JSlider slider) {
        slider.setValue(slider.getMaximum());
    }                   

    private void sliderMin(JSlider slider) {
        slider.setValue(slider.getMinimum());
    }       
    
    private void sliderStep(JSlider slider, int step) {
    	slider.setValue(slider.getValue() + step);
    }            
    
    // Layout helpers
    // Basic layout add item
	private void addItem(JPanel p, JComponent c, int x,
		    int y, int width, int height, int align)
		{
		    GridBagConstraints gc = new GridBagConstraints();
		    gc.gridx = x;
		    gc.gridy = y;
		    gc.gridwidth = width;
		    gc.gridheight = height;
		    gc.weightx = 100.0;
		    gc.weighty = 100.0;
		    gc.insets = new Insets(0,0,0,0);
		    gc.anchor = align;
		    gc.fill = GridBagConstraints.VERTICAL;
		    p.add(c, gc);
		}
    
    // Add item with weightx and weighty
	private void addItem(JPanel p, JComponent c, int x,
		    int y, int width, int height, int align, float weightx, float weighty)
		{
		    GridBagConstraints gc = new GridBagConstraints();
		    gc.gridx = x;
		    gc.gridy = y;
		    gc.gridwidth = width;
		    gc.gridheight = height;
		    gc.weightx = weightx;
		    gc.weighty = weighty;
		    gc.insets = new Insets(0,0,0,0);
		    gc.anchor = align;
		    gc.fill = GridBagConstraints.VERTICAL;
		    p.add(c, gc);
		}

    // Add item with weightx, weighty, and custom fill type
	private void addItem(JPanel p, JComponent c, int x,
		    int y, int width, int height, int align, float weightx, float weighty, int fill)
		{
		    GridBagConstraints gc = new GridBagConstraints();
		    gc.gridx = x;
		    gc.gridy = y;
		    gc.gridwidth = width;
		    gc.gridheight = height;
		    gc.weightx = weightx;
		    gc.weighty = weighty;
		    gc.insets = new Insets(0,0,0,0);
		    gc.anchor = align;
		    gc.fill = fill;
		    p.add(c, gc);
		}
		 		                       
}

