package func;
import java.io.IOException;

public class DBright {

	public DBright(String parameter) throws IOException {
		String[] args = new String[] { ".\\command\\ClickMonitorDDC.exe brightness ", parameter };
		
		Runtime.getRuntime().exec(args[0]+args[1]);
	}
}
