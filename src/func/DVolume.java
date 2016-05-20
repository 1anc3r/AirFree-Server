package func;
import java.io.IOException;

public class DVolume {

	private String parameter;

	public DVolume(String parameter) throws IOException {
		this.parameter = parameter;

		String[] args = new String[] { ".\\command\\ClickMonitorDDC.exe volume ", parameter };
		
		Runtime.getRuntime().exec(args[0]+args[1]);
	}
}
