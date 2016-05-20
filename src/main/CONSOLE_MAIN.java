package main;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.SwingUtilities;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import func.DBright;
import func.DKeyBoard;
import func.DMouse;
import func.DPower;
import func.DRemote;
import func.DScreenShot;
import func.DVolume;
import util.Networking;

public class CONSOLE_MAIN {

	private String ip = "";
	private OutputStream os;
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	final private Runnable readRun;
	final private Runnable readSuccess;
	final private Runnable readFail;
	final private Runnable sendRun;
	final private Runnable sendSuccess;
	final private Runnable sendFail;
	final private Integer port = new Integer(59672);
	private String readName, sendName;
	private List<String> filelist = new ArrayList<>();

	public CONSOLE_MAIN() throws IOException, JSONException {

		readRun = (new Runnable() {
			public void run() {
				readName = ".\\download\\" + readName;
				boolean failed = false;
				try {
					Networking.do_read(port, readName);
				} catch (IOException ex) {
					failed = true;
				}

				try {
					if (failed)
						SwingUtilities.invokeLater(readFail);
					else
						SwingUtilities.invokeLater(readSuccess);
				} catch (Exception ex) {
				}
			}
		});

		readSuccess = (new Runnable() {
			public void run() {
				System.out.println("接收成功!");
			}
		});

		readFail = (new Runnable() {
			public void run() {
				System.out.println("接收失败!");
			}
		});

		sendRun = (new Runnable() {
			public void run() {
				Integer port = new Integer(59672);
				System.out.println(ip + ":" + port);
				boolean failed = false;
				try {
					Networking.do_write(ip, port, sendName);
				} catch (IOException ex) {
					failed = true;
				}

				try {
					if (failed)
						SwingUtilities.invokeLater(sendFail);
					else
						SwingUtilities.invokeLater(sendSuccess);
				} catch (Exception ex) {
				}
			}
		});

		sendSuccess = (new Runnable() {
			public void run() {
				System.out.println("接收成功!");
			}
		});

		sendFail = (new Runnable() {
			public void run() {
				System.out.println("接收失败!");
			}
		});

		InetAddress ip = InetAddress.getLocalHost();
		String text = ip.getHostAddress();
		System.out.println(ip.getHostAddress());
		ServerSocket serverSocket = new ServerSocket(59671);
		System.out.println("服务器创建成功!");

		while (true) {
			Socket socket = serverSocket.accept();
			os = socket.getOutputStream();
			System.out.println("客户端连接成功!");
			socketList.add(socket);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "utf-8"));
			try {
				String content = null;
				while ((content = reader.readLine()) != null) {
					System.out.println(content);
					JSONTokener jt = new JSONTokener(content);
					JSONObject jb = (JSONObject) jt.nextValue();
					String command = jb.getString("command");
					String parameter = jb.getString("parameter");
					AnalyzeCode(command, parameter);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void AnalyzeCode(String command, String parameter)
			throws IOException {
		if (command.equals("link")) {
			ip = parameter;
		} else if (command.equals("computer")) {
			filelist.clear();
			if (parameter.equals("My Computer")) {
				File[] parts = File.listRoots();
				for (File part : parts) {
					filelist.add(part.getAbsolutePath());
					System.out.println(part.getAbsolutePath());
				}
			} else if (parameter.contains("iwanna:")) {
				sendName = parameter.replaceAll("iwanna:", "");
				new Thread(new Runnable() {
					public void run() {
						SwingUtilities.invokeLater(sendRun);
					}
				}).start();
			} else {
				System.out.println(parameter);
				File path = new File(parameter + "\\");
				System.out.println(path.getAbsolutePath());
				if (path.isDirectory()) {
					File[] parts = path.listFiles();
					for (File part : parts) {
						filelist.add(part.getAbsolutePath());
						System.out.println(part.getAbsolutePath());
					}
				} else {
					filelist.clear();
				}
			}
			System.out.println(filelist.toString());
			os.write((filelist.toString()).getBytes("utf-8"));
			os.flush();
		} else if (command.equals("power")) {
			DPower p = new DPower(parameter);
			os.write((parameter).getBytes("utf-8"));
			os.flush();
		} else if (command.equals("screenshot")) {
			DScreenShot ss = new DScreenShot(ip);
			ss.shot();
			os.write(("" + ss.getFileName()).getBytes("utf-8"));
			os.flush();
		} else if (command.equals("mouse")) {
			DMouse m = new DMouse();
			if (parameter.equals("3") || parameter.equals("6")) {
				m.singleClick();
			} else if (parameter.equals("7")) {
				m.doubleClick();
			} else if (parameter.equals("5")) {
				m.rightClick();
			} else if (parameter.equals("8")) {
				m.wheel(-1);
			} else if (parameter.equals("9")) {
				m.wheel(1);
			} else if (parameter.length() > 3) {
				String args[] = parameter.split(":");
				System.out.println(args[1]);
				int x = (int) Double.parseDouble(args[1]);
				int y = (int) Double.parseDouble(args[2]);
				m.move(x, y);
			}
		} else if (command.equals("keyboard")) {
			DKeyBoard kb = new DKeyBoard();
			String args[] = parameter.split(":");
			System.out.println(args[1]);
			if (args[1].contains("67")) {
				kb.keyBoardPress(KeyEvent.VK_BACK_SPACE);
			} else {
				kb.type(args[1].toCharArray()[0]);
			}
		} else if (command.equals("volume")) {
			DVolume v = new DVolume(parameter);
		} else if (command.equals("bright")) {
			DBright b = new DBright(parameter);
		} else if (command.equals("remote")) {
			DRemote ss = new DRemote(parameter);
		} else if (command.equals("file")) {
			String args[] = parameter.split("/");
			System.out.println(args[args.length - 1]);
			readName = args[args.length - 1];
			new Thread(new Runnable() {
				public void run() {
					SwingUtilities.invokeLater(readRun);
				}
			}).start();
		}
		os.flush();
	}

	public static void main(String[] args) throws IOException, JSONException {
		CONSOLE_MAIN main = new CONSOLE_MAIN();
	}
}
