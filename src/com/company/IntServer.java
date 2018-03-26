package com.company;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

public class IntServer {
	public static void main(String args[]){
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		String portNum, registryURL;
		try{
			System.out.println("Enter the RMIregistry port number:");
			portNum = (br.readLine()).trim();
			int RMIPortNum = Integer.parseInt(portNum);
			startRegistry(RMIPortNum);
			IntImpl exportedObj = new IntImpl();
			registryURL = "rmi://localhost:" + portNum + "/int";
			Naming.rebind(registryURL, exportedObj);
			System.out.println("Server registered. Registry currently contains:");
			listRegistry(registryURL);
			System.out.println("Int Server ready.");
		}
		catch (Exception re){
			System.out.println("Exception in IntServer.main: " + re);
		}
	}
	
	private static void startRegistry(int RMIPortNum) throws RemoteException{
		try{
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list();
		}
		catch (RemoteException e){
			System.out.println("RMI registry cannot be located at port " + RMIPortNum);
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
			System.out.println(" RMI registry created at port " + RMIPortNum);
		}
	}
	
	private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
		System.out.println("Registry " + registryURL + " contains: ");
		String[] names = Naming.list(registryURL);
		for(int i=0;i<names.length;i++){
			System.out.println(names[i]);
		}
	}
}