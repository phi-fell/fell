package com.monolc.fell.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {
	Thread t;
	ArrayList<Client> clients;
	ServerSocket socket;
	boolean exit;
	public ConnectionHandler(ServerSocket s) {
		exit = false;
		t = null;
		clients = new ArrayList<Client>();
		socket = s;
	}
	public void exitGracefully() {
		synchronized (this) {
			exit = true;
		}
	}
	public boolean shouldExit() {
		boolean ret = false;
		synchronized (this) {
			ret = exit;
		}
		return ret;
	}
	public Client getClient() {
		if (!clientsAvailable()) {
			return null;
		}
		Client c = null;
		synchronized (this) {
			c = clients.remove(0);
		}
		return c;
	}
	public boolean clientsAvailable() {
		boolean ret = false;
		synchronized (this) {
			ret = clients.size() > 0;
		}
		return ret;
	}
	public void start() {
		t = new Thread(this, "Server Connection Handler");
		t.start();
	}
	public void run() {
		while (!shouldExit()) {
			try {
				Client next = new Client(socket.accept());
				next.send("acknowledged");
				synchronized (this) {
					clients.add(next);
				}
			} catch (SocketTimeoutException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
