package com.monolc.fell.program;

public abstract class ConcurrentProgram implements Runnable {
	private Thread thread;
	private String name;
	private boolean shouldExit;
	public ConcurrentProgram(String id) {
		name = id;
		shouldExit = true;
	}
	public void stop() {
		shouldExit = true;
	}
	public void start() {
		shouldExit = false;
		thread = new Thread(this, name);
		thread.start();
	}
	public void run() {
		init();
		while (!shouldExit) {
			iter();
		}
		destroy();
		thread = null;
	}
	/*
	 * Do not call this method in any class other than ConcurrentProgram, for
	 * any reason.
	 */
	public abstract void init();
	public abstract void iter();
	public abstract void destroy();
}
