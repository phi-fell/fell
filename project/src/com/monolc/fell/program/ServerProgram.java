package com.monolc.fell.program;

public class ServerProgram extends ConcurrentProgram {
	int i = 0;
	public ServerProgram(int port) {
		super("Fell Server Thread");
		System.out.println("Hosting on port " + port);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
	@Override
	public void iter() {
		// TODO Auto-generated method stub
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}