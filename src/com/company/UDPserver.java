package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPserver {

    public static void main(String[] args) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum = "";
        int port = 0;
        try {
            System.out.println("Enter the port number:");
            portNum = br.readLine();
            port = Integer.parseInt(portNum);
        }
        catch(Exception e){

        }
        while(true){
            DatagramPacket packet = ReceiveUDP(port);
            byte[] b = packet.getData();
            String out = new String(b);
            String[] inputs = out.split(";");
            int total = 0;
            for (String s:inputs) {
                s = s.trim();
                int i = Integer.parseInt(s);
                total += i;
            }
            String t = "" + total;
            SendUDP(packet.getAddress().getHostName(), packet.getPort(), port, t);
        }
    }

    public static void SendUDP(String IPaddress, int portNumber, int localPort, String message)
    {
        byte[] buffer = new byte[500];
        InetAddress address;
        DatagramPacket pack;
        DatagramSocket sock;
        try{
            address = InetAddress.getByName(IPaddress);
        }
        catch (Exception e){
            System.out.println("Unable to resolve address.");
            return;
        }
        try {
            sock = new DatagramSocket(localPort);
        }
        catch(Exception e)
        {
            System.out.println("Unable to establish socket to local port.");
            return;
        }
        try{
            pack = new DatagramPacket(buffer,500, address, portNumber);
        }
        catch(Exception e)
        {
            System.out.println("Unable to create data packet.");
            return;
        }
        buffer = message.getBytes();
        pack.setData(buffer);
        try{
            sock.send(pack);
        }
        catch(Exception e)
        {
            System.out.println("Unable to send packet.");
            return;
        }
        sock.close();
    }
    public static DatagramPacket ReceiveUDP(int portNumber)
    {
        DatagramSocket sock;
        DatagramPacket pack;
        byte[] buffer = new byte[500];
        try{
            sock = new DatagramSocket(portNumber);
            pack = new DatagramPacket(buffer, 500);
            sock.receive(pack);
            sock.close();
            return pack;
        }
        catch(Exception e){
            System.out.println("Failed to receive packet.");
            return new DatagramPacket(buffer, 500);
        }
    }
}
