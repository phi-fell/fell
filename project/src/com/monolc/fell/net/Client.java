package com.monolc.fell.net;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String recieved;
	public Client(Socket s) {
		in = null;
		out = null;
		socket = s;
		try {
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		recieved = "";
	}
	public void send(String message) {
		out.println(message);
	}
	public boolean messageAvailable() {
		try {
			while (in.ready()) {
				int val = in.read();
				if (val != -1) {
					recieved += (char) val;
				} else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return recieved.contains("\n");
	}
	public String recieve() {
		String ret = recieved.substring(0, recieved.indexOf("\n"));
		recieved = recieved.substring(recieved.indexOf("\n") + 1);
		return ret;
	}
}
