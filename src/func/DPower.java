package func;
import java.io.IOException;

public class DPower {
	
	private String parameter;
	
	public DPower(String parameter) throws IOException{
		this.parameter = parameter;
		
		if (parameter.equals("1")) {
			Runtime.getRuntime().exec("shutdown -s -t 0");
		} else if (parameter.equals("2")) {
			Runtime.getRuntime().exec("shutdown -r -t 0");
		} else if (parameter.equals("3")) {
			Runtime.getRuntime().exec("shutdown -l -t 0");
		}
	}
	
}
