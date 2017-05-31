package thread;

import java.io.IOException;

public class MonteCarlo {
	static int length = 10000000;//一千万个数据
	static long array1[] = new long[4];
	static long array2[] = new long[4];
	
	public static void MonteCarloMethod(int type){
		double x, y = 0;

		long count1 = 0, count2 = 0;
		for (count2 = 0; count2 < length; count2++) {
			x = Math.random() * (1 - 0);
			y = Math.random();
			if (x * x + y * y < 1) {
				count1++;
			}
		}
		array1[type] = count1;
		array2[type] = count2;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		long time;
		double pi;

		time = System.currentTimeMillis();// 计时开始
//		MonteCarloMethod(0);
//		MonteCarloMethod(1);
//		MonteCarloMethod(2);
//		MonteCarloMethod(3);
		MonteCarloThread t1 = new MonteCarloThread(0);
		MonteCarloThread t2 = new MonteCarloThread(1);
		MonteCarloThread t3 = new MonteCarloThread(2);
		MonteCarloThread t4 = new MonteCarloThread(3);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		pi = (double)4*(array1[0] + array1[1] + array1[2] + array1[3])/ (array2[0] + array2[1] + array2[2] + array2[3]);
		System.out.println(pi);
		time = System.currentTimeMillis() - time;// 计时结束
		
		System.out.println("计算时间:" + time + "ms");
	}
}

class MonteCarloThread extends Thread {
	int type;

	MonteCarloThread(int type) {
		this.type = type;
	}

	public void run() {
		MonteCarlo.MonteCarloMethod(type);
	}
}