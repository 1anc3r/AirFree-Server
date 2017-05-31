package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//public class Main {
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		int n = sc.nextInt();
//		int q = sc.nextInt();
//		int[] A = new int[n];
//		int[] B = new int[n];
//		for (int i = 0; i < n; i++) {
//			A[i] = sc.nextInt();
//		}
//		for (int i = 0; i < n; i++) {
//			B[i] = sc.nextInt();
//		}
//		for (int j = 0; j < q; j++) {
//			int x = sc.nextInt();
//			int y = sc.nextInt();
//			int count = 0;
//			for (int k = 0; k < n; k++) {
//				if (A[k] >= x && B[k] >= y) {
//					count++;
//				}
//			}
//			System.out.println(count);
//		}
//	}
//}
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		int n = sc.nextInt();
//		int[] arr = new int[n];
//		for (int i = 0; i < n; i++) {
//			arr[i] = sc.nextInt();
//		}
//		List<Integer> list = new ArrayList<>();
//		int start = -1;
//		int end = -1;
//		list.add(0);
//		for (int i = 2; i < n; i++) {
//			if (arr[i - 2] > arr[i - 1] && arr[i - 1] < arr[i]) {
//				if (i - 1 - list.get(list.size() - 1) > end - start) {
//					start = list.get(list.size() - 1);
//					end = i - 1;
//				}
//				list.add(i - 1);
//			}
//		}
//		System.out.println(start + " " + end);
//	}
//}

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String stra = sc.next();
		String strb = sc.next();
		char[] arrc = stra.toCharArray();
		int[] arri = new int[arrc.length];
		int count = 0;
		for (char item : arrc) {
			switch (item) {
			case '2':
				arri[count++] = 2;
				break;
			case '3':
				arri[count++] = 3;
				break;
			case '4':
				arri[count++] = 4;
				break;
			case '5':
				arri[count++] = 5;
				break;
			case '6':
				arri[count++] = 6;
				break;
			case '7':
				arri[count++] = 7;
				break;
			case '8':
				arri[count++] = 8;
				break;
			case '9':
				arri[count++] = 9;
				break;
			case '0':
				arri[count++] = 10;
				break;
			case 'J':
				arri[count++] = 11;
				break;
			case 'Q':
				arri[count++] = 12;
				break;
			case 'K':
				arri[count++] = 13;
				break;
			case 'A':
				arri[count++] = 14;
				break;

			}
		}
		quick(arri);
		for(int i : arri){
			System.out.print(i+" ");
		}
	}

	private static void quick(int[] a) {
		if (a.length > 0) {
			quickSort(a, 0, a.length - 1);
		}
	}

	private static void quickSort(int[] a, int low, int high) {
		if (low < high) {
			int middle = getMiddle(a, low, high);
			quickSort(a, 0, middle - 1);
			quickSort(a, middle + 1, high);
		}
	}

	private static int getMiddle(int[] a, int low, int high) {
		int temp = a[low];
		while (low < high) {
			while (low < high && a[high] >= temp) {
				high--;
			}
			a[low] = a[high];
			while (low < high && a[low] <= temp) {
				low++;
			}
			a[high] = a[low];
		}
		a[low] = temp;
		return low;
	}
}