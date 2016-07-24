package func;
import java.io.IOException;

public class DVolume {

	public DVolume(String parameter) throws IOException {
		String[] args = new String[] { ".\\command\\ClickMonitorDDC.exe volume ", parameter };
		
		Runtime.getRuntime().exec(args[0]+args[1]);
	}
}
