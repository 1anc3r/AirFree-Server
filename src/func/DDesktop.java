package func;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.imageio.ImageIO;

import org.json.JSONException;

import util.Robot0;
import util.Robot1;

public class DDesktop {

	private dRunnable runnable;
	private Thread thread;

	public DDesktop() {
		runnable = new dRunnable();
		runnable.init();
	}

	public void startServer() {
		thread = new Thread(runnable);
		thread.start();
	}

	public void stopServer() {
		runnable.shutdown();
		thread.interrupt();
	}

	public class dRunnable implements Runnable {
		private final int txtPort = 59673;
		private final int imgPort = 59674;
		private DatagramSocket txtSocket;
		private DatagramSocket imgSocket;
		private int phonePort = -1;
		private InetAddress phoneInetAddress;
		private boolean connected = false;
		private boolean iprepared = false;
		private byte[] txtBuf = new byte[1000];
		private byte[] imgBuf = new byte[8192];
		public static final String msgInit = "Init", msgBegin = "Begin",
				msgEnd = "End";
		private DatagramPacket dataPacket;

		private String msg;
		private Robot0 bot;

		public dRunnable() {
			dataPacket = new DatagramPacket(txtBuf, txtBuf.length);
			bot = new Robot0();
		}
		
		public void init(){
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				txtSocket = new DatagramSocket(txtPort, inetAddress);
				imgSocket = new DatagramSocket(imgPort, inetAddress);
				connected = true;
				iprepared = true;
			} catch (BindException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void shutdown() {
			try {
				txtSocket.close();
				imgSocket.close();
			} catch (Exception e) {
			}
		}

		public void run() {

			msgListener();

			while (connected) {
				try {
					txtSocket.receive(dataPacket);
					msg = new String(dataPacket.getData(), 0,
							dataPacket.getLength());
					if (msg.equals("Connectivity")) {
						txtSocket.send(dataPacket);
					} else if (msg.equals("Connected")) {
						if (!iprepared) {
							iprepared = true;
						}
						txtSocket.send(dataPacket);
					} else if (msg.equals("Close")) {
						iprepared = false;
					} else {
						bot.handleMessage(msg);
					}
				} catch (Exception e) {
					connected = false;
					iprepared = false;
				}
			}
		}

		private void msgListener() {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (connected) {
						imgBuf = new byte[8192];
						DatagramPacket imgTransPacket = new DatagramPacket(
								imgBuf, imgBuf.length);
						try {
							imgSocket.receive(imgTransPacket);
						} catch (IOException e) {
							e.printStackTrace();
						}
						String content = new String(imgTransPacket.getData(),
								0, imgTransPacket.getLength());
						if (content.equals(dRunnable.msgInit)) {
							phonePort = imgTransPacket.getPort();
							phoneInetAddress = imgTransPacket.getAddress();
							try {
								imgSocket.send(imgTransPacket);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (content.equals(dRunnable.msgBegin)) {
							iprepared = connected;
							while (iprepared) {
								try {
									imgBuf = dRunnable.msgBegin.getBytes();
									imgTransPacket = new DatagramPacket(imgBuf,
											imgBuf.length, phoneInetAddress,
											phonePort);
									imgSocket.send(imgTransPacket);
									byte[] tmpBuf = getScreenShot();
									ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
											tmpBuf);
									imgBuf = new byte[8192];
									while ((byteArrayInputStream
											.read(imgBuf)) != -1) {
										imgTransPacket = new DatagramPacket(
												imgBuf, imgBuf.length,
												phoneInetAddress, phonePort);
										imgSocket.send(imgTransPacket);
									}
									imgBuf = dRunnable.msgEnd.getBytes();
									imgTransPacket = new DatagramPacket(imgBuf,
											imgBuf.length, phoneInetAddress,
											phonePort);
									imgSocket.send(imgTransPacket);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}).start();
		}

		public byte[] getScreenShot() {
			Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			BufferedImage image = Robot1.captureWholeScreen(robot, 0);
			byte[] result = null;
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				Image cursorImg = ImageIO.read(new File(
						".\\resources\\cursor.png"));
				int curCursorX = MouseInfo.getPointerInfo().getLocation().x;
				int curCursorY = MouseInfo.getPointerInfo().getLocation().y;
				Graphics2D graphics2d = image.createGraphics();
				graphics2d.drawImage(cursorImg, curCursorX, curCursorY, 32, 32,
						null);
				int width = (int) (image.getWidth(null));
				int height = (int) (image.getHeight(null));
				image = resize(image, width, height);
				ImageIO.write(image, "gif", byteArrayOutputStream);
				result = byteArrayOutputStream.toByteArray();
				byteArrayOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		public BufferedImage resize(BufferedImage source, int tWidth,
				int tHeight) {
			int type = source.getType();
			BufferedImage target = null;
			double sWidth = (double) tWidth / source.getWidth();
			double sHeight = (double) tHeight / source.getHeight();
			if (sWidth < sHeight) {
				sHeight = sWidth;
				tHeight = (int) (sWidth * source.getHeight());
			} else {
				sWidth = sHeight;
				tWidth = (int) (sHeight * source.getWidth());
			}
			if (type == BufferedImage.TYPE_CUSTOM) {
				ColorModel cm = source.getColorModel();
				WritableRaster raster = cm.createCompatibleWritableRaster(
						tWidth, tHeight);
				boolean alphaPremultiplied = cm.isAlphaPremultiplied();
				target = new BufferedImage(cm, raster, alphaPremultiplied, null);
			} else {
				target = new BufferedImage(tWidth, tHeight, type);
			}
			Graphics2D g = target.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawRenderedImage(source,
					AffineTransform.getScaleInstance(sWidth, sHeight));
			g.dispose();
			return target;
		}
	}

	public static void main(String[] args) throws IOException, JSONException {
		DDesktop dd = new DDesktop();
		dd.startServer();
	}
}
