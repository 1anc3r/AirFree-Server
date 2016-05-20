package func;
import java.io.IOException;

public class DBright {

	private String parameter;

	public DBright(String parameter) throws IOException {
		this.parameter = parameter;

		String[] args = new String[] { ".\\command\\ClickMonitorDDC.exe brightness ", parameter };
		
		Runtime.getRuntime().exec(args[0]+args[1]);
	}
}
