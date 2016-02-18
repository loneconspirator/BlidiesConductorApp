package view;

import controller.Blindies;
import enums.PatternMode;
import enums.SendType;

import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;

public class BlindiesFrame extends JFrame{
	private static final long serialVersionUID = -2139229150850736977L;
	
	// The model
	Blindies blindies;
	
	// Main panel
	private JPanel panel = new JPanel();
	
	// Mode panel variables
	private JLabel modeLabel = new JLabel("Mode:");
	private JComboBox<PatternMode> modeBox = new JComboBox<PatternMode>(PatternMode.values());
	private JPanel modePanel = new JPanel();
	
	// Sliders panel variables
	private JSlider  slider1  = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	private JSpinner spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
	private JLabel   label1   = new JLabel("Brightness");
	private JPanel   panel1   = new JPanel();
	private JSlider  slider2  = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	private JSpinner spinner2 = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
	private JLabel   label2   = new JLabel("Speed");
	private JPanel   panel2   = new JPanel();
	private JSlider  slider3  = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	private JSpinner spinner3 = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
	private JLabel   label3   = new JLabel("Duty");
	private JPanel   panel3   = new JPanel();
	
	//// Send Panel variables
	private JComboBox<SendType> sendType = new JComboBox<SendType>(SendType.values());
	private JButton sendButton = new JButton("Send Command");
	private JPanel sendPanel = new JPanel();

	// Target Panel Variables
	private JLabel hostLabel = new JLabel("Host:");
	private JTextField hostField = new JTextField(8);
	private JLabel portLabel = new JLabel(" Port:");
	private JTextField portField = new JTextField(3);
	private JButton targetEdit = new JButton("Edit");
	private JPanel targetPanel = new JPanel();

	public BlindiesFrame(Blindies blindies) {
		this.blindies = blindies;
		panel.setLayout(new GridBagLayout());
		
		this.add(panel);
		
		// Mode panel setup
		modePanel.add(modeLabel);
		modePanel.add(modeBox);
		modeBox.addItemListener(e -> modeChanged());
		addItem(panel, modePanel, 1, 1, 3, 1, GridBagConstraints.CENTER);
		
		// First slider panel setup
		panel1.setLayout(new GridBagLayout());
		slider1.addChangeListener(e -> slider1Changed());
		addItem(panel1, slider1,  1, 1, 1, 1, GridBagConstraints.CENTER);
		spinner1.addChangeListener(e -> spinner1Changed());
		addItem(panel1, spinner1, 1, 2, 1, 1, GridBagConstraints.CENTER);
		addItem(panel1, label1,   1, 3, 1, 1, GridBagConstraints.CENTER);
		panel1.setVisible(true);
		addItem(panel, panel1, 1, 2, 1, 1, GridBagConstraints.CENTER);
		// Second slider panel setup
		panel2.setLayout(new GridBagLayout());
		slider2.addChangeListener(e -> slider2Changed());
		addItem(panel2, slider2,  1, 1, 1, 1, GridBagConstraints.CENTER);
		spinner2.addChangeListener(e -> spinner2Changed());
		addItem(panel2, spinner2, 1, 2, 1, 1, GridBagConstraints.CENTER);
		addItem(panel2, label2,   1, 3, 1, 1, GridBagConstraints.CENTER);
		panel2.setVisible(true);
		addItem(panel, panel2, 2, 2, 1, 1, GridBagConstraints.CENTER);
		// Third slider panel setup
		panel3.setLayout(new GridBagLayout());
		slider3.addChangeListener(e -> slider3Changed());
		addItem(panel3, slider3,  1, 1, 1, 1, GridBagConstraints.CENTER);
		spinner3.addChangeListener(e -> spinner3Changed());
		addItem(panel3, spinner3, 1, 2, 1, 1, GridBagConstraints.CENTER);
		addItem(panel3, label3,   1, 3, 1, 1, GridBagConstraints.CENTER);
		panel3.setVisible(true);
		addItem(panel, panel3, 3, 2, 1, 1, GridBagConstraints.CENTER);
		
		// Send panel setup
		sendType.addItemListener(e -> sendTypeChanged());
		sendButton.addActionListener(e -> sendClicked());
		sendPanel.add(sendType);
		sendPanel.add(sendButton);
		sendPanel.setVisible(true);
		addItem(panel, sendPanel, 1, 3, 3, 1, GridBagConstraints.CENTER);
		
		// Target panel setup
		hostField.setText(blindies.getHost());
		hostField.setEditable(true);
		portField.setText(Integer.toString(blindies.getPort()));
		portField.setEditable(true);
		targetEdit.addActionListener(e -> targetEditClicked());
		targetPanel.add(hostLabel);
		targetPanel.add(hostField);
		targetPanel.add(portLabel);
		targetPanel.add(portField);
		targetPanel.add(targetEdit);
		targetPanel.setVisible(true);
		addItem(panel, targetPanel, 1, 4, 3, 1, GridBagConstraints.CENTER);
		
		// Frame setup
		this.setSize(350, 370);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Chaotic Blindies");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		// Initialize
		spinner1.setValue(1);
		spinner2.setValue(1);
		spinner3.setValue(1);
		modeBox.setSelectedIndex(1);
		modeBox.setSelectedItem(PatternMode.SOLID);
		sendType.setSelectedIndex(1);
		sendType.setSelectedItem(SendType.SLIDER_ONLY);;
	}
	
	public void clickSendButton() {
		sendButton.doClick();
	}
	public void setSendType(SendType sendType) {
		this.sendType.setSelectedItem(sendType);
	}

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
	
	
	/*
	 * Controller code here
	 */
	// Listener / controller methods
	public void modeChanged() {
		PatternMode mode = (PatternMode) modeBox.getSelectedItem();
		editArg(mode.arg1(), label1, slider1, spinner1);
		editArg(mode.arg2(), label2, slider2, spinner2);
		editArg(mode.arg3(), label3, slider3, spinner3);
		blindies.setMode(mode);
	}
	private void editArg(String arg, JLabel label, JSlider slider, JSpinner spinner) {
		boolean enable = (arg != null);
		if (!enable) 
			arg = "N/A";
		label.setText(arg);
		slider.setEnabled(enable);
		spinner.setEnabled(enable);
	}
	
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
			JOptionPane.showMessageDialog(this, "There was a problem sending that packet.");
		}
	}
	
	public void targetEditClicked() {
		try {
			blindies.setTarget(hostField.getText(), Integer.parseInt(portField.getText()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "There was a problem with the host you provided, please try another.");
		}
	}
}
