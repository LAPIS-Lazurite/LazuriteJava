package com.lapis_semi.lazurite.io;

import java.util.Formatter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class common {
	public common(){}
	static public int ub2i(byte[] b){
		int tmp;
		int out = 0;
		for (int cycle = b.length -1;cycle >=0;cycle--) {
			out *= 256;
			if(b[cycle] < 0) {
				tmp = 256+b[cycle];
			} else {
				tmp = b[cycle];
			}
			out += tmp;
		}
		return out;
	}

	public static void printInputStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			for (;;) {
				String line = br.readLine();
				if (line == null) break;
				System.out.println(line);
			}
		} finally {
			br.close();
		}
	}
}
