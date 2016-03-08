package com.lapis_semi.lazurite.io;

import java.util.*;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CtrlDeviceList {
	private BufferedReader br;
	private FileReader fr;
	private String file;

	public CtrlDeviceList(String file) throws IOException {
		if(file != null) {
			this.file = file;
		}
		open();
		close();
	}

	public static void main(String[] args){
		String[] nameList;
		CtrlDeviceList devList;
		int num;

		try {
			System.out.println("test2.pref open::");
			devList = new CtrlDeviceList("test2.pref");
			System.out.println("Sucess???");
		} catch (Exception e) {
			System.out.println("Can Not Open device list");
		}

		try {
			System.out.println("test.pref open::");
			devList = new CtrlDeviceList("test.pref");
			System.out.println("Sucess!!");
		} catch (Exception e) {
			System.out.println("Can Not Open device list");
			return;
		}

		try {
			System.out.print("LazuritePiGateway::");
			num = devList.findIndex("name","LazuritePiGateway");
			System.out.print("\tindex="+Integer.valueOf(num));
			System.out.print("\toption="+devList.getPropertyOf("option",num));
			System.out.println("\tvalue="+devList.getPropertyOf("value",num));
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			System.out.print("LazuritePiGateway1::");
			num = devList.findIndex("name","LazuritePiGateway1");
			System.out.print("\tindex="+Integer.valueOf(num));
			System.out.print("\toption="+devList.getPropertyOf("option",num));
			System.out.println("\tvalue="+devList.getPropertyOf("value",num));
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("Try wrong name::");
		try {
			num = devList.findIndex("name","Wrong name");
			System.out.println("Sucess to get wrong device.....");
		} catch(Exception e) {
			try {
				nameList = devList.getAllProperties("name");
				System.out.println("Can't fine device!!");
				System.out.println("get all supporting devices::");
				for(int i=0;i<nameList.length;i++) {
					System.out.println(nameList[i]);
				}
			}catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	public  String[] getAllProperties(String key) throws IOException{
		open();
		List<String> list = new ArrayList<String>();
		String[] strArray = {""};
		String line;
		String[] str;
		try {
			line = br.readLine();
			while(line!= null){
				str=line.split("=",2);
				if(key.equals(str[0])) {
					list.add(str[1]);
				}
				line=br.readLine();
			}
			strArray = list.toArray(new String[0]);
		} catch(Exception e) {
			System.out.println(e);
		}
		close();
		return strArray;
	}

	public int findIndex(String key, String prop ) throws IOException {
		open();
		String[] array = getAllProperties(key);
		List<String> list = Arrays.asList(array);
		int result = list.indexOf(prop);
		close();
		if (result < 0) {
			throw new IOException("mismuch key and property");
		}
		return result;
	}

	public String getPropertyOf(String key, int index) throws IOException {
		open();
		String s[] = getAllProperties(key);
		close();

		return s[index];
	}

	private void open() throws IOException {
		fr = new FileReader(file);
		br = new BufferedReader(fr);
	}

	private void close() {
		try {
			br.close();
			fr.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
