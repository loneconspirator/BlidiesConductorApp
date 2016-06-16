package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpDriver {
    private DatagramSocket clientSocket = null;
	private InetAddress addy;
	private String host;
	private int port;
	
	public UdpDriver() throws SocketException
	{
        clientSocket = new DatagramSocket();
	}
	
    public String getHost() { return this.host; }
    public int getPort() { return this.port; }
    
    public void setupTarget(String host, int port) throws UnknownHostException
    {
        addy = InetAddress.getByName(host);
        if (port <= 0)
        	throw new IllegalArgumentException("port must be greater than 0");
       	this.port = port;
        this.host = host;
        System.out.printf("Now set to send to %s:%d\n", host, port);
    }
    
    public void sendCommand(byte[] sendData) throws IOException
    {
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, addy, port);
        System.out.println("sent.");
        clientSocket.send(sendPacket);
    }
}
