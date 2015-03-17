package com.monolc.fell.program;

import com.monolc.fell.net.ServerIO;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.version.VersionData;
import com.monolc.fell.world.Floor;

public class Server {
	VersionData version;
	public Server(VersionData v) {
		version = v;
	}
	public void run() {
		System.out.println("Server running...");
		ResourceHandler resources = new ResourceHandler();
		ServerIO sio = new ServerIO(version);
		sio.start();
		Floor currentFloor = new Floor(resources, 121, 101);
		sio.setFloor(currentFloor);
	}
}
