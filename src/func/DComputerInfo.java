package func;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
//import java.text.DecimalFormat;
import java.util.Map;
import java.util.Properties;

//import org.hyperic.sigar.*;

public class DComputerInfo {

	String str = "";

	public DComputerInfo() {
		// try {
		// str += property();
		// // str += cpu();
		// // str += memory();
		// System.out.println(str);
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("cmd /c systeminfo");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "GBK"));
			String line = null;
			while ((line = input.readLine()) != null) {
				str = str + line + "\n";
			}
			int exitVal = process.waitFor();
			str += "Exited with error code " + exitVal;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getStr() {
		return str;
	}

	private static String property() throws UnknownHostException {
		String str = "";
		Map<String, String> map = System.getenv();
		InetAddress addr = InetAddress.getLocalHost();
		Properties props = System.getProperties();
		str += "�û���:" + map.get("USERNAME") + "\n";
		str += "������:" + addr.getHostName() + "\n";
		str += "����IP:" + addr.getHostAddress() + "\n";
		str += "����ϵͳ���ƣ�" + props.getProperty("os.name") + "\n";
		str += "����ϵͳ���ܣ�" + props.getProperty("os.arch")
		// + "\n\n"
		;
		return str;
	}

	// private static String cpu() throws SigarException {
	// String str = "";
	// Sigar sigar = new Sigar();
	// CpuInfo infos[] = sigar.getCpuInfoList();
	// CpuPerc cpuList[] = null;
	// cpuList = sigar.getCpuPercList();
	// int i = 0;
	// CpuInfo info = infos[i];
	// str += "CPU������:" + info.getVendor() + "\n";
	// str += "CPU�ͺ�:" + info.getModel() + "\n\n";
	// str += printCpuPerc(cpuList[i]);
	// return str;
	// }
	//
	// private static String printCpuPerc(CpuPerc cpu) {
	// String str = "";
	// str += "CPU�û�ʹ����:" + CpuPerc.format(cpu.getUser()) + "\n";
	// str += "CPUϵͳʹ����:" + CpuPerc.format(cpu.getSys()) + "\n";
	// str += "CPUʹ����:" + CpuPerc.format(cpu.getCombined()) + "\n";
	// str += "CPU������:" + CpuPerc.format(cpu.getIdle()) + "\n\n";
	// return str;
	// }
	//
	// private static String memory() throws SigarException {
	// String str = "";
	// DecimalFormat df = new DecimalFormat("0.#");
	// Sigar sigar = new Sigar();
	// Mem mem = sigar.getMem();
	// str += "�ڴ�ʹ����:" + df.format(mem.getUsed()*100.0 / mem.getTotal()) + "%" +
	// "\n";
	// str += "�ڴ������:" + df.format(mem.getFree()*100.0 / mem.getTotal()) + "%" +
	// "\n\n";
	// Swap swap = sigar.getSwap();
	// str += "������ʹ����:" + df.format(swap.getUsed()*100.0 / swap.getTotal()) +
	// "%" + "\n";
	// str += "������������:" + df.format(swap.getFree()*100.0 / swap.getTotal()) +
	// "%" + "";
	// return str;
	// }

	public static void main(String[] args) {
		try {
			String str = "";
			str += property();
			// str += cpu();
			// str += memory();
			System.out.println(str);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}