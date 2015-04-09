package OLDcom.monolc.fell.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import OLDcom.monolc.fell.version.VersionData;

public class ClientIO {
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String recieved;
	public ClientIO(VersionData v, String server) {
		recieved = "";
		socket = null;
		try {
			socket = new Socket(server, 53476);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out = null;
		in = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(v.getVersion());
	}
	public void send(String s) {
		out.println(s.replace('\n', ';'));
	}
	public boolean hasMessage() {
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
		return ret.replace(';', '\n');
	}
}
