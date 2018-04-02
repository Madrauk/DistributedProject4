package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPserver {

    public static void main(String[] args) {
        Socket s;
        ServerSocket server;
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum = "";
        int port = 0;
        try {
            System.out.println("Enter the port number:");
            portNum = br.readLine();
            port = Integer.parseInt(portNum);
            server = new ServerSocket(port);
            PrintWriter out;
            BufferedReader in;
            while(true){
                s = server.accept();
                out = new PrintWriter(s.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                while(s.isConnected()){
                    char[] cbuf = new char[500];
                    in.read(cbuf);
                    String input = new String(cbuf);
                    String[] ints = input.split(";");
                    int max = 0;
                    for(String st:ints){
                        st = st.trim();
                        max += Integer.parseInt(st);
                    }
                    out.println("" + max);
                }
                s.close();
            }
        }
        catch(Exception e){
            System.exit(-1);
        }
    }

}
