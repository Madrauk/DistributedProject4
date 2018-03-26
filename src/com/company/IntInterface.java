package com.company;
import java.rmi.*;

public interface IntInterface extends Remote {
	public int addInt(int a, int b) throws java.rmi.RemoteException;
}