package com.lapis_semi.lazurite.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.xml.bind.DatatypeConverter;

public class mac_info {
	static byte[] raw;
	static int command;
	static long time;
	static String area;
	static short ch;
	static short rate;
	static short pwr;
	static int header;
	static int rxPanid;
	static byte rxAddrType;
	static byte[] rxAddr = new byte[8];
	static int txPanid;
	static byte txAddrType;
	static byte[] txAddr = new byte[8];
	static short rssi;
	static byte[] payload;

	public mac_info(byte[] raw){
		fromRaw(raw);
	}

	static void fromRaw(byte[] raw) {
		ByteBuffer buffer = ByteBuffer.wrap(raw);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		// command
		command = buffer.getShort(0);

		// epoch time
		long timeH = buffer.getInt(2);
		long timeL = buffer.getInt(6);
		time = timeH*1000+timeL/1000000;

		// area
		byte[] b = new byte[2];
		b[0] = raw[10];
		b[1] = raw[11];
		area = new String(b);

		ch = buffer.getShort(12);	// ch
		rate = buffer.getShort(14);	// rate
		pwr = buffer.getShort(16);	// pwr
		header=buffer.getInt(18);	// header
		rxPanid=buffer.getInt(22);	// 
		rxAddrType=raw[26];			// 
		rxAddr[0]=raw[27];			// 
		rxAddr[1]=raw[28];			// 
		rxAddr[2]=raw[29];			// 
		rxAddr[3]=raw[30];			// 
		rxAddr[4]=raw[31];			// 
		rxAddr[5]=raw[32];			// 
		rxAddr[6]=raw[33];			// 
		rxAddr[7]=raw[34];			// 

		txPanid=buffer.getInt(35);	// 
		txAddrType=raw[39];			// 
		txAddr[0]=raw[40];			// 
		txAddr[1]=raw[41];			// 
		txAddr[2]=raw[42];			// 
		txAddr[3]=raw[43];			// 
		txAddr[4]=raw[44];			// 
		txAddr[5]=raw[45];			// 
		txAddr[6]=raw[46];			// 
		txAddr[7]=raw[47];			// 

		if(raw[48]<0) {
			rssi = (short)(256 + raw[48]);
		}
		else rssi = raw[48];

		payload = new byte[raw.length -49];
		for(int i =49;i<raw.length;i++) {
			payload[i-49] = raw[i];
		}
	}
	static boolean comp_txAddr(byte[] txAddr)
	{
		boolean result=false;
		if(txAddrType == 0) result = true;
		else if(txAddrType == 1) {
			System.out.println("AddrComp mode 1");
			if(		mac_info.txAddr[7] == txAddr[7])
				result = true;
		}
		else if(txAddrType == 2) {
			if(		mac_info.txAddr[6] == txAddr[6] &&
					mac_info.txAddr[7] == txAddr[7])
				result = true;
		}
		else if(txAddrType == 3) {
			System.out.println("AddrComp mode 3");
			if(		mac_info.txAddr[0] == txAddr[0] &&
					mac_info.txAddr[1] == txAddr[1] &&
					mac_info.txAddr[2] == txAddr[2] &&
					mac_info.txAddr[3] == txAddr[3] &&
					mac_info.txAddr[4] == txAddr[4] &&
					mac_info.txAddr[5] == txAddr[5] &&
					mac_info.txAddr[6] == txAddr[6] &&
					mac_info.txAddr[7] == txAddr[7]) result = true;
		}
		return result;
	}
}
