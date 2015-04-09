package com.monolc.fell.launcher;

import java.awt.EventQueue;

import com.monolc.fell.launcher.ui.Launcher;

public class Program {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Launcher l = new Launcher();
				l.setVisible(true);
			}
		});
	}
}
