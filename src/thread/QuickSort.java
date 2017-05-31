package thread;

import java.lang.String;
import java.io.IOException;

public class QuickSort {

	public static int QuickSortPartion(long array[], int start, int end) {// 分治快排
		long first = array[start];
		int i = start, j = end;
		while (i < j) {
			while (array[i] <= first && i < end) {
				i++;
			}
			while (array[j] > first && j >= start) {
				j--;
			}
			if (i < j) {
				array[i] = array[i] ^ array[j];
				array[j] = array[i] ^ array[j];
				array[i] = array[i] ^ array[j];
			}
		}
		if (j != start) {
			array[j] = array[start] ^ array[j];
			array[start] = array[start] ^ array[j];
			array[j] = array[start] ^ array[j];
		}
		return j;
	}

	public static void QuickSortProcess(long array[], int start, int end) {// 快排过程
		int i;
		if (start < end) {
			i = QuickSortPartion(array, start, end);
			QuickSortProcess(array, start, i - 1);
			QuickSortProcess(array, i + 1, end);
		}
	}

	public static void main(String args[]) throws IOException, InterruptedException {
		long time;
		int length = 10000000;//一千万个数据
		long[] array = new long[length];

		for (int i = 0; i < length; i++) {// 生成随机数组
			array[i] = (int) (Math.random() * length);
		}
		// System.out.println("排序前:");
		// for (int i = 0; i < length; i++) {
		// System.out.print(array[i]+",");
		// }

		time = System.currentTimeMillis();// 计时开始
		int p1 = QuickSortPartion(array, 0, length - 1);
		QuickSortThread t1 = new QuickSortThread(array, 0, p1 - 1);
		QuickSortThread t2 = new QuickSortThread(array, p1 + 1, length - 1);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
//		 QuickSortProcess(array, 0, length-1);
		time = System.currentTimeMillis() - time;// 计时结束

		// System.out.println("排序后:");//打印有序数组
		// for (int i = 0; i < length; i++) {
		// System.out.print(array[i]+",");
		// }

		System.out.println("排序时间:" + time + "ms");
	}
}

class QuickSortThread extends Thread {
	long array[];
	int start;
	int end;

	QuickSortThread(long array[], int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}

	public void run() {
		QuickSort.QuickSortProcess(array, start, end);
	}
}