package com.company;
import java.rmi.*;
import java.rmi.server.*;

public class IntImpl extends UnicastRemoteObject implements IntInterface {
	
	public IntImpl() throws RemoteException {
		super();
	}
	
	public int addInt(int a, int b) throws RemoteException {
		return a + b;
	}
}