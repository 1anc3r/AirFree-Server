package util;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str;
		int len = 0;
		while((str = sc.nextLine())!=null){
            len = str.length();
            for(int i = 0; i < len; i++){
            	char ch = str.charAt(len-i-1);
            	System.out.println(ch);
            }
        }
	}
}