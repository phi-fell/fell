package com.monolc.fell.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {
	Thread t;
	ArrayList<Client> clients;
	ServerSocket socket;
	public ConnectionHandler(ServerSocket s) {
		t = null;
		clients = new ArrayList<Client>();
		socket = s;
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
		boolean cont = true;
		while (cont) {
			try {
				Client next = new Client(socket.accept());
				synchronized (this) {
					clients.add(next);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
