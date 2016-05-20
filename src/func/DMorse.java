package func;
public class DMorse {

	private String command;
	private String parameter;
	
	public DMorse(){}
	
	public DMorse(String command, String parameter){
		this.command = command;
		this.parameter = parameter;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getParameter() {
		return parameter;
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
