package func;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import util.Networking;

public class DScreenShot {
	private String ip, name, fileName, defaultFileName = ".\\screenshot\\screenshot";
	private String imageFormat;
	private String defaultImageFormat = "png";
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	final private Runnable sendRun;
	final private Runnable sendSuccess;
	final private Runnable sendFail;

	public DScreenShot(final String ip) {
		
		sendRun = (new Runnable() {
			public void run() {
				Integer port = new Integer(59672);
				System.out.println(ip+ ":"+port);
				boolean failed = false;
				try {
					Networking.do_write(ip, port, name);
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmmss");
		Date date = new Date(System.currentTimeMillis());// 获取当前时间
		String date_ = sdf.format(date);
		fileName = defaultFileName + date_;
		imageFormat = defaultImageFormat;
	}

	public void shot() {

		try {
			BufferedImage screenshot = (new Robot())
					.createScreenCapture(new Rectangle(0, 0,
					(int) dimension.getWidth(), (int) dimension.getHeight()));
			name = fileName + "." + imageFormat;
			File file = new File(name);
			ImageIO.write(screenshot, imageFormat, file);
			System.out.println("截图成功:" + name);
			
			new Thread(new Runnable() {
				public void run() {
					SwingUtilities.invokeLater(sendRun);
				}
			}).start();
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public String getFileName(){
		return fileName;
	}
}