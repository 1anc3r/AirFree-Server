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

	// shift+ ����
	public void keyPressWithShift(int key) {
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.delay(10);
	}

	// ctrl+ ����
	public void keyPressWithCtrl(int key) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	// alt+ ����
	public void keyPressWithAlt(int key) {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.keyRelease(KeyEvent.VK_ALT);
	}

	// ��ӡ���ַ���
	public void keyPressString(String str) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();// ��ȡ���а�
		Transferable tText = new StringSelection(str);
		clip.setContents(tText, null); // ���ü��а�����
		keyPressWithCtrl(KeyEvent.VK_V);// ճ��
	}

	// ���� ����
	public void keyPress(Robot r, int key) {
		robot.keyPress(key);
		robot.keyRelease(key);
	}

}
