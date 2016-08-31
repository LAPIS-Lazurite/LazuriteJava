//package com.lapis_semi.lazurite.io;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class LibraryTest {

	interface LazuriteLib extends Library {
		// load library
		LazuriteLib INSTANCE = (LazuriteLib) Native.loadLibrary("lazurite",LazuriteLib.class);

		// functions
		int lazurite_close();
		int lazurite_link(short addr);
		int lazurite_setRxAddr(short tmp_rxaddr);
		int lazurite_setPanid(short tmp_panid);
		int lazurite_setCh(char tmp_ch);
		int lazurite_open();
		int lazurite_write(String payload, short size);
		int lazurite_available();
		int lazurite_read(char[] payload, short[] size);

		int lazurite_getRxTime(Pointer tv_sec,Pointer tv_nsec);
		int lazurite_getRxRssi(Pointer rssi);
		int lazurite_getTxRssi(Pointer rssi);
		int lazurite_test(byte[] payload,short[] size);
	}
	public static void main(String[] args)
	{
		byte[] payload = new byte[256];
		short[] size = new short[1];
		int result;
		LazuriteLib lazurite = LazuriteLib.INSTANCE;
		
		result = lazurite.lazurite_test(payload,size);

		try {
			String str = new String(payload,"UTF-8");
			System.out.println(str+",size="+String.valueOf(size[0])+"result="+String.valueOf(result));
		} catch (Exception e) {}

	}
}

