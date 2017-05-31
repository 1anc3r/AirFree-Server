package main;

import java.awt.Color;
import java.awt.SystemTray;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.json.JSONException;

import util.QRCodeUtil;

public class GUI_MAIN extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JLabel iptextLabel;
	private JLabel qrcodeLabel;
	private static TrayIcon trayIcon;
	private String text = "";
	private Image image = new ImageIcon(".\\resources\\paper.png").getImage();

	public GUI_MAIN() {
		super("AirFree");
		initPanel();
		initTray();
		this.setIconImage(image);
		this.setSize(222, 285);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			public void windowIconified(WindowEvent e) {
				setVisible(false);
			}
		});
	}

	public void initPanel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		try {
			// System.out.println(getLocalHostAddr());
			// InetAddress ip;
			// ip = InetAddress.getLocalHost();
			// text = ip.getHostAddress();
			text = getLocalHostAddr();
			QRCodeUtil.encode(text, "", ".\\inetaddress\\", text + ".png", true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iptextLabel = new JLabel();
		iptextLabel.setSize(216, 24);
		iptextLabel.setLocation(0, 0);
		iptextLabel.setText(text);
		iptextLabel.setFont(new java.awt.Font(text, 0, 20));
		iptextLabel.setForeground(Color.BLACK);
		iptextLabel.setBackground(Color.WHITE);
		iptextLabel.setOpaque(true);
		// iptextLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		iptextLabel.setHorizontalTextPosition(JLabel.CENTER);
		iptextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(iptextLabel);

		Icon icon = null;
		File picall = new File(".\\inetaddress\\");
		File[] piclist = picall.listFiles();
		for (File pic : piclist) {
			if (pic.getAbsolutePath().contains(text)) {
				System.out.println(pic.toString());
				icon = new ImageIcon(pic.toString());
			}
		}

		qrcodeLabel = new JLabel();
		qrcodeLabel.setSize(216, 216);
		qrcodeLabel.setLocation(0, 24);
		qrcodeLabel.setIcon(icon);
		// qrcodeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		qrcodeLabel.setHorizontalTextPosition(JLabel.CENTER);
		qrcodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		qrcodeLabel.setBackground(Color.WHITE);
		mainPanel.add(qrcodeLabel);

		this.add(mainPanel);
	}

	public void initTray() {
		PopupMenu menu = new PopupMenu();
		MenuItem open = new MenuItem("    ´ò¿ª");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ex) {
				if (!isVisible()) {
					setVisible(true);
					toFront();
				} else {
					toFront();
				}
			}
		});
		MenuItem hide = new MenuItem("    Òþ²Ø");
		hide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ex) {
				if (isVisible()) {
					setVisible(false);
					toFront();
				} else {
					toFront();
				}
			}
		});
		MenuItem exit = new MenuItem("    ÍË³ö");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ex) {
				try {
					// Runtime.getRuntime().exec(
					// "taskkill /f /im RemoteDesktop.exe");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		menu.add(open);
		menu.addSeparator();
		menu.add(hide);
		menu.addSeparator();
		menu.add(exit);

		if (SystemTray.isSupported()) {
			SystemTray systemTray = SystemTray.getSystemTray();
			String name = "AirFree";
			String wifi = text;
			trayIcon = new TrayIcon(image, name, menu);
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!isVisible()) {
						setVisible(true);
						toFront();
					} else {
						setVisible(false);
						toFront();
					}
				}

			});
			try {
				systemTray.add(trayIcon);
				trayIcon.displayMessage(name, wifi, MessageType.INFO);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean findRemoteDesktop() {
		BufferedReader bufferedReader = null;
		try {
			Process proc = Runtime.getRuntime()
					.exec("TASKLIST /FI \"IMAGENAME eq RemoteDesktop.exe\" /FI \"STATUS eq running");
			bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("RemoteDesktop.exe")) {
					return true;
				}
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public String getLocalHostAddr() {
		Enumeration<?> enum0;
		Vector<String> ipTexts = new Vector<String>();
		String ipText = null;
		InetAddress inetAddr = null;
		try {
			enum0 = NetworkInterface.getNetworkInterfaces();
			while (enum0.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) enum0.nextElement();
				Enumeration<?> enum1 = ni.getInetAddresses();
				while (enum1.hasMoreElements()) {
					inetAddr = (InetAddress) enum1.nextElement();
					ipTexts.add(inetAddr.toString());
					if (inetAddr != null && inetAddr instanceof Inet4Address) {
						String hostAddr = inetAddr.getHostAddress();
						if (!hostAddr.equals("127.0.0.1") && !hostAddr.equals("/127.0.0.1")) {
							ipText = inetAddr.toString().split("[/]")[1];
							return ipText;
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		 if (!findRemoteDesktop()) {
			 Runtime.getRuntime().exec("cmd /c start .\\RemoteDesktop.exe");
		 } else {
			 Runtime.getRuntime().exec("cmd /c start .\\RemoteDesktop.vbs");
		 }
		new GUI_MAIN();
		new CONSOLE_MAIN(trayIcon);
	}
}
