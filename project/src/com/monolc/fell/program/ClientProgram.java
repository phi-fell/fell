package com.monolc.fell.program;

public class ClientProgram extends ConcurrentProgram {
	// Window window;
	// ClientIO server;
	public ClientProgram(String ip, int port) {
		super("Fell Client Thread");
		System.out.println("Connection to " + ip + ":" + port + "...");
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
	@Override
	public void iter() {
		this.stop();
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}
