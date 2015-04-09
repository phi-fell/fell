package com.monolc.fell.launcher.ui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Launcher extends JFrame {
	public static final String DEFAULT_IP = "localhost";
	public static final int DEFAULT_PORT = 53476;
	JPanel main;
	JPanel connect;
	JPanel host;
	JTextField serverAddress;
	JTextField portInput;
	public Launcher() {
		initUI();
		initMain();
		initConnect();
		initHost();
		pack();
	}
	private void initUI() {
		setTitle("Fell Launcher");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private void initMain() {
		JButton connectButton = new JButton("Connect");
		connectButton.setAlignmentX(CENTER_ALIGNMENT);
		JButton hostButton = new JButton("Host Server");
		hostButton.setAlignmentX(CENTER_ALIGNMENT);
		JButton exitButton = new JButton("Exit");
		exitButton.setAlignmentX(CENTER_ALIGNMENT);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				remove(main);
				add(connect);
				pack();
			}
		});
		hostButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				remove(main);
				add(host);
				pack();
			}
		});
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
		main.add(connectButton);
		main.add(Box.createRigidArea(new Dimension(0, 5)));
		main.add(hostButton);
		main.add(Box.createRigidArea(new Dimension(0, 5)));
		main.add(exitButton);
		add(main);
	}
	private void initConnect() {
		JButton joinButton = new JButton("Join");
		joinButton.setAlignmentX(CENTER_ALIGNMENT);
		JButton backButton = new JButton("Back");
		backButton.setAlignmentX(CENTER_ALIGNMENT);
		serverAddress = new JTextField(21);
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println("Connecting...");
				String ip = serverAddress.getText().contains(":") ? serverAddress.getText().substring(0, serverAddress.getText().lastIndexOf(':')) : serverAddress.getText();
				if (ip.length() == 0) {
					ip = DEFAULT_IP;
				}
				int port = serverAddress.getText().contains(":") ? Integer.parseInt(serverAddress.getText().substring(serverAddress.getText().lastIndexOf(':') + 1)) : DEFAULT_PORT;
				try {
					Process p = Runtime.getRuntime().exec("java -jar fell.jar client " + ip + " " + port + " nogui");
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("#YOLO");
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				remove(connect);
				add(main);
				pack();
			}
		});
		connect = new JPanel();
		connect.setLayout(new BoxLayout(connect, BoxLayout.Y_AXIS));
		connect.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
		connect.add(serverAddress);
		connect.add(Box.createRigidArea(new Dimension(0, 5)));
		connect.add(joinButton);
		connect.add(Box.createRigidArea(new Dimension(0, 5)));
		connect.add(backButton);
	}
	private void initHost() {
		JButton hostButton = new JButton("Host");
		hostButton.setAlignmentX(CENTER_ALIGNMENT);
		JButton backButton = new JButton("Back");
		backButton.setAlignmentX(CENTER_ALIGNMENT);
		portInput = new JTextField(5);
		hostButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println("Hosting...");
				int port = portInput.getText().length() == 0 ? Integer.parseInt(portInput.getText()) : DEFAULT_PORT;
				try {
					Process p = Runtime.getRuntime().exec("java -jar fell.jar server " + port);
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				remove(host);
				add(main);
				pack();
			}
		});
		host = new JPanel();
		host.setLayout(new BoxLayout(host, BoxLayout.Y_AXIS));
		host.setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
		host.add(portInput);
		host.add(Box.createRigidArea(new Dimension(0, 5)));
		host.add(hostButton);
		host.add(Box.createRigidArea(new Dimension(0, 5)));
		host.add(backButton);
	}
}