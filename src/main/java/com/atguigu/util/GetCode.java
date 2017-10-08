package com.atguigu.util;

import java.util.Random;

public class GetCode {

	public static String getCode(int len) {
		String code = "";
		
		for (int i = 0; i < len; i++) {
           code += new Random().nextInt(10);
		}

		return code;
	}
}
