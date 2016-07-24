package main;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;
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
	private static ServerSocket serverSocket;
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	final private Integer port = new Integer(59672);
	final private Runnable readRun;
	final private Runnable readSuccess;
	final private Runnable readFail;
	final private Runnable sendRun;
	final private Runnable sendSuccess;
	final private Runnable sendFail;
	private String readName, sendName;
	private static boolean isPrint = false;
	private static List<String> fileList = new ArrayList<>();
	private static LinkedList<String> messageList = new LinkedList<>();
	private static List<ServerThread> threadsList = new ArrayList<>();

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

		try {
			if (makeDirs(".\\screenshot") && makeDirs(".\\download")
					&& makeDirs(".\\inetaddress")) {
				InetAddress ip;
				ip = InetAddress.getLocalHost();
				System.out.println(ip.getHostAddress());
				serverSocket = new ServerSocket(59671);
				new PrintThread();
				System.out.println("服务端创建成功!");

				while (true) {
					Socket socket = serverSocket.accept();
					os = socket.getOutputStream();
					socketList.add(socket);
					new ServerThread(socket);
					System.out.println("客户端连接成功!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if (msg.contains("Address already in use")) {
				JOptionPane.showMessageDialog(null, "服务端创建失败 : 端口被占用");
			}
		}
	}

	private void AnalyzeCode(String command, String parameter)
			throws IOException {
		if (command.equals("link")) {
			ip = parameter;
		} else if (command.equals("computer")) {
			fileList.clear();
			if (parameter.equals("this PC")) {
				File[] parts = File.listRoots();
				for (File part : parts) {
					fileList.add(part.getAbsolutePath());
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
						fileList.add(part.getAbsolutePath());
					}
				} else {
					fileList.clear();
				}
			}
			System.out.println(fileList.toString());
			// os.write((fileList.toString()).getBytes("utf-8"));
			// os.flush();
			sendMessage("computer", fileList.toString());
		} else if (command.equals("power")) {
			new DPower(parameter);
			// os.write((parameter).getBytes("utf-8"));
			// os.flush();
			sendMessage("power", parameter);
		} else if (command.equals("screenshot")) {
			DScreenShot ss = new DScreenShot(ip);
			ss.shot();
			// os.write((ss.getFileName()).getBytes("utf-8"));
			// os.flush();
			sendMessage("screenshot", ss.getFileName());
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
			new DVolume(parameter);
		} else if (command.equals("bright")) {
			new DBright(parameter);
		} else if (command.equals("remote")) {
			new DRemote(parameter);
		} else if (command.equals("file")) {
			String args[] = parameter.split("/");
			System.out.println(args[args.length - 1]);
			readName = args[args.length - 1];
			new Thread(new Runnable() {
				public void run() {
					SwingUtilities.invokeLater(readRun);
				}
			}).start();
		} else if (command.equals("talk")) {
			System.out.print(parameter);
			for (Socket item : socketList) {
				OutputStream itemOs = item.getOutputStream();
				itemOs.write(("" + parameter).getBytes("utf-8"));
			}
		}
		os.flush();
	}

	public void sendMessage(String command, String parameter) {
		if (os != null) {
			try {
				JSONObject jb = new JSONObject();
				jb.put("command", command);
				jb.put("parameter", parameter);
				System.out.println("发送成功:" + jb.toString());
				os.write((jb.toString() + "\n").getBytes("utf-8"));
				os.flush();
			} catch (Exception e) {
				System.out.println("发送异常:" + e.getMessage());
			}
		}
	}

	public static boolean makeDirs(String folderName) {
		if (folderName == null || folderName.isEmpty()) {
			return false;
		}
		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder
				.mkdirs();
	}

	class PrintThread extends Thread {

		public PrintThread() {
			start();
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (isPrint == true) {
					String msg = messageList.getFirst();
					for (ServerThread item : threadsList) {
						System.out.println(msg);
						item.sendMessage(msg);
					}
					synchronized (messageList) {
						messageList.removeFirst();
					}
					isPrint = messageList.size() > 0 ? true : false;
				}
			}
		}
	}

	class ServerThread extends Thread {

		private Socket client;
		private OutputStream os_;
		private BufferedReader br;
		int first = 1;

		public ServerThread(Socket socket) {
			try {
				client = socket;
				client.getOutputStream();
				os_ = socket.getOutputStream();
				br = new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "utf-8"));
				start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void pushMessage(String msg) {
			synchronized (messageList) {
				messageList.add(msg);
			}
			isPrint = true;
		}

		public void sendMessage(String msg) {
			try {
				// os_.write((msg).getBytes("utf-8"));
				if (os_ != null) {
					try {
						JSONObject jb_ = new JSONObject();
						System.out.println("发送成功:"
								+ "{\"command\":\"talk\",\"parameter\":\""
								+ msg + "\"}");
						os_.write(("{\"command\":\"talk\",\"parameter\":\""
								+ msg + "\"}" + "\n").getBytes("utf-8"));
						os_.flush();
					} catch (Exception e) {
						System.out.println("发送异常:" + e.getMessage());
					}
				}
				os_.flush();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				while (true) {
					String msg = br.readLine();
					System.out.println("接收成功:" + msg);
					JSONTokener jt = new JSONTokener(msg);
					JSONObject jb = (JSONObject) jt.nextValue();
					String command = jb.getString("command");
					String paramet = jb.getString("parameter");
					if (!msg.contains("talk")) {
						AnalyzeCode(command, paramet);
					} else {
						if (first == 1) {
							threadsList.add(this);
							first--;
						}
						this.pushMessage(paramet);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					client.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				threadsList.remove(this);
			}
		}
	}

	public static void main(String[] args) throws IOException, JSONException {
		if (makeDirs(".\\screenshot") && makeDirs(".\\download")
				&& makeDirs(".\\inetaddress")) {
			new CONSOLE_MAIN();
		}
	}
}
