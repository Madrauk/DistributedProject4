package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.rmi.Naming;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String hostName = "";
        String portNum = "";
        String receivePort = "";
        String choice = "";
        String iterations = "";
        try {
            System.out.println("Enter the host name:");
            hostName = br.readLine();
            System.out.println("Enter the port number:");
            portNum = br.readLine();
            System.out.println("Enter the receiving port number:");
            receivePort = br.readLine();
            System.out.print("UDP, TCP, or RMI?");
            choice = br.readLine();
            System.out.print("Number of iterations?");
            iterations = br.readLine();
        }
        catch(Exception e){

        }
        if(choice.equals("UDP")){
            try{
                int intIterations = Integer.parseInt(iterations);
                int intPort = Integer.parseInt(portNum);
                int intReceivePort = Integer.parseInt(receivePort);
                //start timer
                long start = System.nanoTime();
                for(int i=0;i<intIterations;i++){
                    System.out.println(i);
                    SendUDP(hostName, intPort, intReceivePort, "8;12");
                    DatagramPacket p = ReceiveUDP(intReceivePort);
                    String out = new String(p.getData());
                }
                //end timer, display
                long elapsed = System.nanoTime() - start;
                long milli = TimeUnit.NANOSECONDS.toMillis(elapsed);
                System.out.println(milli);
            }
            catch (Exception e){
                System.out.println("Exception in IntClient: " + e);
            }
        }
        else if(choice.equals("TCP")){
            try{
                int intPort = Integer.parseInt(portNum);
                int intReceivePort = Integer.parseInt(receivePort);
                int intIterations = Integer.parseInt(iterations);
                //start timer
                long start = System.nanoTime();
                Socket s = new Socket();
                s.bind(new InetSocketAddress(intReceivePort));
                s.connect(new InetSocketAddress(hostName, intPort));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                for(int i=0;i<intIterations;i++){
                    System.out.println(i);
                    out.println("12;20");
                    char[] cbuf = new char[500];
                    in.read(cbuf);
                    String input = new String(cbuf);
                }
                s.close();
                //end timer, display
                long elapsed = System.nanoTime() - start;
                long milli = TimeUnit.NANOSECONDS.toMillis(elapsed);
                System.out.println(milli);
            }
            catch (Exception e){
                System.out.println("Exception in IntClient: " + e);
                e.printStackTrace();
            }
        }
        else if(choice.equals("RMI")){
            try{
                int intIterations = Integer.parseInt(iterations);
                //start timer
                long start = System.nanoTime();
                for(int i=0;i<intIterations;i++){
                    System.out.println(i);
                    String registryURL = "rmi://" + hostName + ":" + portNum + "/int";
                    IntInterface h = (IntInterface)Naming.lookup(registryURL);
                    System.out.println("Lookup completed " );
                    int message = h.addInt(5,17);
                }
                //end timer, display
                long elapsed = System.nanoTime() - start;
                long milli = TimeUnit.NANOSECONDS.toMillis(elapsed);
                System.out.println(milli);
            }
            catch (Exception e){
                System.out.println("Exception in IntClient: " + e);
            }
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
