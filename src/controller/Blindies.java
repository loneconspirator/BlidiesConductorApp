package controller;

import java.io.IOException;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import enums.PatternMode;
import enums.SendType;
import view.BlindiesTabletFrame;

public class Blindies {
	BlindiesTabletFrame frame;
	UdpDriver udp;
	
	private SendThread sender;
	
	private PatternMode mode;
	
	private int value1, value2, value3;
	
	private SendType sendType;
	
	private String host;
	private int port;
	
	Blindies(String host, int port) throws SocketException, UnknownHostException {
		this.host = host;
		this.port = port;
		udp = new UdpDriver();
		udp.setupTarget(host, port);
		frame = new BlindiesTabletFrame(this);
        frame.setVisible(true);
        sender = new SendThread(this);
        sender.start();
	}
	
	// Mode accessors
	public PatternMode getMode() { return mode; }
	public void setMode(PatternMode mode) { 
		this.mode = mode;
		if (sendType == SendType.CHANGES) {
			frame.clickSendButton();
		} else if (sendType == SendType.SLIDER_ONLY) {
			frame.setSendType(SendType.CLICK);
		}
	}
	
	// Slider accessors
	public int getValue1() { return value1; }
	public void setValue1(int val) { this.value1 = val; valChanged(); }

	public int getValue2() { return value2; }
	public void setValue2(int val) { this.value2 = val; valChanged(); }
	
	public int getValue3() { return value3; }
	public void setValue3(int val) { this.value3 = val; valChanged(); }

	// Target accessors
	public String getHost() { return host; }
	public int getPort() { return port; }
	public void setTarget(String host, int port) throws UnknownHostException { 
		this.host = host;
		this.port = port;
		udp.setupTarget(host, port);
	}

	// IP Helper
	private static String getLocalBroadcastIP() {
		try {
		    return InetAddress.getLocalHost()
		                      .getHostAddress()
		                      .replaceFirst("\\d+$", "255");
		}
		catch (Exception e) {
			return "192.168.1.255";
		}
    }

	public void setSendType(SendType sendType) {
		if (this.sendType == SendType.CONTINUOUS)
			sender.setGo(false);
		this.sendType = sendType;
		if (this.sendType == SendType.CONTINUOUS)
			sender.setGo(true);
	}
	public void sendClicked() throws IOException {
		String formattedOutput = String.format("Sending: %c - %d %d %d ...", mode.getCommandValue(), value1, value2, value3);
        System.out.printf(formattedOutput);
        frame.appendMessage(formattedOutput);
		udp.sendCommand(new byte[]{(byte) mode.getCommandValue(), (byte) value1, (byte) value2, (byte) value3});
	}
	
	// Model behavior
	private void valChanged() {
		if (sendType == SendType.CHANGES || sendType == SendType.SLIDER_ONLY)
			frame.clickSendButton();
	}
	public void send() {
		frame.clickSendButton();
	}

	// main
	public static void main(String[] args) throws Exception {
		String host = getLocalBroadcastIP();
		int port = 2390;
		if (args.length == 2) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		new Blindies(host, port);
	}
}
