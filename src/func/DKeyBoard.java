package func;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

public class DKeyBoard {
	private Robot robot;

	public DKeyBoard() {
		Toolkit.getDefaultToolkit().getScreenSize();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void type(String str) {
		keyPressString(str);
	}

	public void keyPress(int key) {
		try {
			robot.keyPress(key);
			robot.keyRelease(key);
		} catch (Exception e) {
		}
	}

	public void keyPress(int key, int key2) {
		try {
			robot.keyPress(key);
			robot.keyPress(key2);
			robot.keyRelease(key2);
			robot.keyRelease(key);
		} catch (Exception e) {
		}
	}

	// shift+ 按键
	public void keyPressWithShift(int key) {
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.delay(10);
	}

	// ctrl+ 按键
	public void keyPressWithCtrl(int key) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	// alt+ 按键
	public void keyPressWithAlt(int key) {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.keyRelease(KeyEvent.VK_ALT);
	}

	// 打印出字符串
	public void keyPressString(String str) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();// 获取剪切板
		Transferable tText = new StringSelection(str);
		clip.setContents(tText, null); // 设置剪切板内容
		keyPressWithCtrl(KeyEvent.VK_V);// 粘贴
	}

	// 单个 按键
	public void keyPress(Robot r, int key) {
		robot.keyPress(key);
		robot.keyRelease(key);
	}

}
