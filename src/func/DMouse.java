package func;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DMouse {
	private Dimension dimen;
	private Robot robot;

	public DMouse() {
		dimen = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void move(int x, int y) {
		Point point = MouseInfo.getPointerInfo().getLocation();
		x += point.x;
		y += point.y;
		try {
			robot.mouseMove(x, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void singleClick() {
		int x, y;
		Point point = MouseInfo.getPointerInfo().getLocation();
		x = point.x;
		y = point.y;
		try {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doubleClick() {
		int x, y;
		Point point = MouseInfo.getPointerInfo().getLocation();
		x = point.x;
		y = point.y;
		try {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(100);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rightClick() {
		int x, y;
		Point point = MouseInfo.getPointerInfo().getLocation();
		x = point.x;
		y = point.y;
		try {
			robot.mousePress(InputEvent.BUTTON3_MASK);
			robot.mouseRelease(InputEvent.BUTTON3_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void wheel(int w) {
		try {
			robot.mouseWheel(w);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
