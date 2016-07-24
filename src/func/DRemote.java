package func;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DRemote {

	public DRemote(String parameter) throws IOException {
		if (parameter.equals("1")) {
			Runtime.getRuntime().exec("cmd /k start cmd");
		} else if (parameter.equals("-1")) {
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		}  else if (parameter.equals("2")) {
			Runtime.getRuntime().exec("cmd /k start taskmgr");
		} else if (parameter.equals("3")) {
			Runtime.getRuntime().exec("cmd /k start explorer");
		} else if (parameter.equals("4")) {
			Runtime.getRuntime().exec("cmd /k start devmgmt.msc");
		} else if (parameter.equals("5")) {
			Runtime.getRuntime().exec("cmd /k start diskmgmt.msc");
		} else if (parameter.equals("6")) {
			Runtime.getRuntime().exec("cmd /k start regedit.exe");
		} else if (parameter.equals("7")) {
			Runtime.getRuntime().exec("cmd /k start calc");
		} else if (parameter.equals("-7")) {
			Runtime.getRuntime().exec("taskkill /f /im Calculator.exe");
		} else if (parameter.equals("8")) {
			Runtime.getRuntime().exec("cmd /k start notepad");
		} else if (parameter.equals("-8")) {
			Runtime.getRuntime().exec("taskkill /f /im notepad.exe");
		} else if (parameter.equals("9")) {
			Runtime.getRuntime().exec("cmd /k start mspaint");
		} else if (parameter.equals("-9")) {
			Runtime.getRuntime().exec("taskkill /f /im mspaint.exe");
		} else if (parameter.equals("10")) {
			Runtime.getRuntime().exec("cmd /k start write");
		} else if (parameter.equals("-10")) {
			Runtime.getRuntime().exec("taskkill /f /im write.exe");
		} else if (parameter.equals("11")) {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler www.baidu.com/");
		} else if (parameter.contains(":")) {
			try {
				Runtime.getRuntime().exec("explorer.exe " + parameter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (parameter.contains("music")) {
			try {
				String play = "";
				File file = new File(".\\download\\");
				File[] musics = file.listFiles();
				List<File> refence = new ArrayList<>();
				for (File music : musics) {
					if (music.getName().endsWith(".mp3")) {
						refence.add(music);
						System.out.println(music.getAbsolutePath());
					}
				}
				int position = (int) (Math.random() * refence.size());
				play = refence.get(position).getAbsolutePath();
				if (!play.equals("")) {
					Runtime.getRuntime().exec(
							"cmd /c start " + play.replaceAll(" ", "\" \""));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (parameter.contains("video")) {
			try {
				Runtime.getRuntime().exec(
						"cmd /c start " + ".\\command\\actcute.mp4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (parameter.contains(":\\")) {
			try {
				System.out.println(parameter);
				Runtime.getRuntime().exec(
						"cmd /c start " + parameter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Runtime.getRuntime().exec(
						"rundll32 url.dll,FileProtocolHandler www.baidu.com/"
								+ parameter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
