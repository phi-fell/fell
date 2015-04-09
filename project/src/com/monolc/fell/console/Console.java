package com.monolc.fell.console;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

class ConsoleOutputWrapper extends OutputStream {
	@Override
	public void write(int b) throws IOException {
	}
}

class ConsolePrintWrapper extends PrintStream {
	private Console console;
	public ConsolePrintWrapper(Console c) {
		super(new ConsoleOutputWrapper());
		console = c;
	}
	public void print(String s) {
		if (s == null) {
			s = "null";
		}
		console.print(s);
	}
	public void print(boolean b) {
		print(b ? "true" : "false");
	}
	public void print(char c) {
		print(String.valueOf(c));
	}
	public void print(int i) {
		print(String.valueOf(i));
	}
	public void print(long l) {
		print(String.valueOf(l));
	}
	public void print(float f) {
		print(String.valueOf(f));
	}
	public void print(double d) {
		print(String.valueOf(d));
	}
	public void print(char s[]) {
		print(String.valueOf(s));
	}
	public void print(Object obj) {
		print(String.valueOf(obj));
	}
	public void println() {
		print('\n');
	}
	public void println(boolean x) {
		synchronized (this) {
			print(x);
			println();
		}
	}
	public void println(char x) {
		synchronized (this) {
			print(x);
			println();
		}
	}
	public void println(int x) {
		synchronized (this) {
			print(x);
			println();
		}
	}
	public void println(long x) {
		synchronized (this) {
			print(x);
			println();
		}
	}
	public void println(float x) {
		synchronized (this) {
			print(x);
			println();
		}
	}
	public void println(double x) {
		synchronized (this) {
			print(x);
			println();
		}
	}
	public void println(char x[]) {
		synchronized (this) {
			print(x);
			println();
		}
	}
	public void println(String x) {
		synchronized (this) {
			print(x);
			println();
		}
	}
	public void println(Object x) {
		String s = String.valueOf(x);
		synchronized (this) {
			print(s);
			println();
		}
	}
}

@SuppressWarnings("serial")
public class Console extends JFrame implements KeyListener {
	public static final int MAX_CHARS_PER_LINE = 94;
	private String text;
	JTextPane textPane;
	JTextPane inputField;
	JScrollPane scrollPane;
	ConsolePrintWrapper cpw;
	public Console(String title) {
		cpw = new ConsolePrintWrapper(this);
		text = "";
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		textPane = new JTextPane();
		textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		textPane.setEditable(false);
		scrollPane = new JScrollPane(textPane);
		scrollPane.setPreferredSize(new Dimension(650, 300));
		inputField = new JTextPane();
		inputField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		inputField.setEditable(true);
		inputField.addKeyListener(this);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(new Insets(4, 4, 4, 4)));
		panel.add(scrollPane);
		panel.add(Box.createRigidArea(new Dimension(0, 4)));
		panel.add(inputField);
		add(panel);
		addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				inputField.requestFocusInWindow();
			}
		});
		textPane.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				inputField.requestFocusInWindow();
			}
			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		pack();
		setResizable(false);
		setVisible(true);
	}
	public ConsolePrintWrapper getAsPrintStream() {
		return cpw;
	}
	public void print(String s) {
		boolean scrollDown = scrollPane.getVerticalScrollBar().getValue() == scrollPane.getVerticalScrollBar().getMaximum() - scrollPane.getVerticalScrollBar().getVisibleAmount();
		text += s;
		textPane.setText(text);
		pack();
		if (scrollDown) {
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		}
	}
	public void println(String s) {
		print(s + "\n");
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			println(inputField.getText().replace("\n", ""));
			inputField.setText("");
			e.consume();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
}