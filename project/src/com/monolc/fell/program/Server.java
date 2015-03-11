package com.monolc.fell.program;

import com.monolc.fell.net.ServerIO;
import com.monolc.fell.version.VersionData;

public class Server {
	VersionData version;
	public Server(VersionData v) {
		version = v;
	}
	public void run() {
		System.out.println("Server running: " + version.getStage() + " V" + version.getVersion() + ", Build#" + version.getBuild());
		ServerIO sio = new ServerIO();
		sio.run();
	}
}
