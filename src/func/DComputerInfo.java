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
		str += "用户名:" + map.get("USERNAME") + "\n";
		str += "主机名:" + addr.getHostName() + "\n";
		str += "主机IP:" + addr.getHostAddress() + "\n";
		str += "操作系统名称：" + props.getProperty("os.name") + "\n";
		str += "操作系统构架：" + props.getProperty("os.arch")
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
	// str += "CPU生产商:" + info.getVendor() + "\n";
	// str += "CPU型号:" + info.getModel() + "\n\n";
	// str += printCpuPerc(cpuList[i]);
	// return str;
	// }
	//
	// private static String printCpuPerc(CpuPerc cpu) {
	// String str = "";
	// str += "CPU用户使用率:" + CpuPerc.format(cpu.getUser()) + "\n";
	// str += "CPU系统使用率:" + CpuPerc.format(cpu.getSys()) + "\n";
	// str += "CPU使用率:" + CpuPerc.format(cpu.getCombined()) + "\n";
	// str += "CPU空闲率:" + CpuPerc.format(cpu.getIdle()) + "\n\n";
	// return str;
	// }
	//
	// private static String memory() throws SigarException {
	// String str = "";
	// DecimalFormat df = new DecimalFormat("0.#");
	// Sigar sigar = new Sigar();
	// Mem mem = sigar.getMem();
	// str += "内存使用率:" + df.format(mem.getUsed()*100.0 / mem.getTotal()) + "%" +
	// "\n";
	// str += "内存空闲率:" + df.format(mem.getFree()*100.0 / mem.getTotal()) + "%" +
	// "\n\n";
	// Swap swap = sigar.getSwap();
	// str += "交换区使用率:" + df.format(swap.getUsed()*100.0 / swap.getTotal()) +
	// "%" + "\n";
	// str += "交换区空闲率:" + df.format(swap.getFree()*100.0 / swap.getTotal()) +
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