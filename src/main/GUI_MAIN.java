package main;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.json.JSONException;

import util.QRCodeUtil;

public class GUI_MAIN extends JPanel {
	private JLabel iptextLabel;
	private JLabel qrcodeLabel;
	private String text = "";
	
	public GUI_MAIN(){
		super(null);
		init();
	}

	public void init() {
		try {
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			text = ip.getHostAddress();
			QRCodeUtil.encode(text, "", ".\\inetaddress\\",text+".png", true);
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
		iptextLabel.setFont(new java.awt.Font(text, 1, 17));
		iptextLabel.setForeground(Color.BLACK);
		iptextLabel.setBackground(Color.WHITE);
		iptextLabel.setOpaque(true);
		iptextLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		iptextLabel.setHorizontalTextPosition(JLabel.CENTER);
		iptextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(iptextLabel);

		Icon icon = null;
		File picall = new File(".\\inetaddress\\");
		File [] piclist = picall.listFiles();
		for(File pic : piclist){
			if(pic.getAbsolutePath().contains(text)){
				System.out.println(pic.toString());
				icon = new ImageIcon(pic.toString());
			}
		}

		qrcodeLabel = new JLabel();
		qrcodeLabel.setSize(216, 216);
		qrcodeLabel.setLocation(0, 24);
		qrcodeLabel.setIcon(icon);
		qrcodeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		qrcodeLabel.setHorizontalTextPosition(JLabel.CENTER);
		qrcodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		qrcodeLabel.setBackground(Color.WHITE);
		add(qrcodeLabel);
	}
	
	public static void main(String[] args) throws IOException, JSONException{
        JFrame frame = new JFrame();
        frame.setSize(222,269);
        frame.setLocationRelativeTo(null);
        frame.add(new GUI_MAIN());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(3);
        CONSOLE_MAIN main = new CONSOLE_MAIN();
    }
}
