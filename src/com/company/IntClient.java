package com.company;
import java.io.*;
import java.rmi.*;


public class IntClient {
	public static void main(String args[]){
		try{
			int RMIPort;
			String hostName;
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
			System.out.println("Enter the RMIRegistry host name:");
			hostName = br.readLine();
			System.out.println("Enter the RMIRegistry port number:");
			String portNum = br.readLine();
			RMIPort = Integer.parseInt(portNum);
			String registryURL = "rmi://" + hostName + ":" + portNum + "/int";
			IntInterface h = (IntInterface)Naming.lookup(registryURL);
			System.out.println("Lookup completed " );
			int message = h.addInt(1,2);
			System.out.println("IntClient: " + message);
		}
		catch (Exception e){
			System.out.println("Exception in IntClient: " + e);
		}
	}
}