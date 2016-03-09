package com.lapis_semi.lazurite.io;

import java.util.Formatter;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PipedOutputStream;
import java.io.PipedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;

public class SubGHz implements RawEventListener{
	private String name;
	private String driver;
	private String device;
	private String[] optName;
	private int[] optValue;
	private Raw raw;
	private PipedOutputStream pos;
	private PipedInputStream pis;
	private BufferedOutputStream bos;
	private int rxBufSize = 2048;
	private byte[] txAddr=new byte[8];
	private boolean enbSerialMode = false;
	private boolean enbNotifyOnRawDataAvailable=false;
	private boolean enbNotifyOnDataAvailable=false;
	private SubGHzEventListener listener;

	public SubGHz(String name) throws IOException{
		init(name);
		raw = new Raw();
		raw.addEventListener(this);
	}
	public void open() throws IOException {
		raw.setParameters(optName,optValue);
		raw.load(driver);
		raw.open(device);
	}

	public void close() throws IOException {
		raw.close(device);
		raw.unload(driver);
		try {
			Thread.sleep(100);
		}catch (Exception e){ }
	}

	public int getRxPacketLength() {
		return raw.getRxPacketLength();
	}

	public int getRxPacket(byte[] b) {
		return raw.getRxPacket(b);
	}

	public void addEventListener(SubGHzEventListener listener) {
		this.listener = listener;
	}

	public void removeEventListener() {
		this.listener = null;
	}

	public void notifyOnRawDataAvailable(boolean enable) {
		enbNotifyOnRawDataAvailable = enable;
		raw.notifyOnRawDataAvailable(enable);
	}

	public void notifyOnDataAvailable(boolean enable) {
		enbNotifyOnDataAvailable = enable;
	}

	// Event from Raw
	public void RawDataAvailable(RawEventObject evt){
		// Event for Raw Access mode
		if(enbSerialMode == false && enbNotifyOnRawDataAvailable) {
			this.listener.SubGHzEvent(new SubGHzEventObject(SubGHzEventObject.RAW_DATA_AVAILABLE));
			// Stack data for Serial Stream mode
		} else if(enbSerialMode) {
			if(SerialModeRxAvailable()) { 
				if(enbNotifyOnDataAvailable) {
					this.listener.SubGHzEvent(new SubGHzEventObject(SubGHzEventObject.DATA_AVAILABLE));
				}
			}
		}
	}

	private boolean SerialModeRxAvailable() {
		// get receiving packet length;
		int length = raw.getRxPacketLength();
		boolean sent = false;
		while(length>0) {
			byte[] b = new byte[length];
			raw.getRxPacket(b);
			mac_info.fromRaw(b);
			if(mac_info.comp_txAddr(txAddr)) {
				try {
					bos.write(mac_info.payload);
					bos.flush();
					length = raw.getRxPacketLength();
					sent = true;
				} catch(Exception e) {
					System.out.println(e);
				}
			}
			length = raw.getRxPacketLength();
		}
		return sent;
	}

	public boolean setSerialMode(String txAddr) {
		byte[] b = DatatypeConverter.parseHexBinary(txAddr);
		return setSerialMode(b);
	}
	public boolean setSerialMode(byte[] txAddr) {
		String out = DatatypeConverter.printHexBinary(txAddr);
		System.out.print("linked addr::" );
		System.out.println(out);
		if(txAddr.length != 8) return false;

		for(int i=0;i<8;i++){
			this.txAddr[i] = txAddr[i];
		}
		// set flag of SerialMode
		enbSerialMode = true;
		// reserve byte buffer
		initRxBuffer();
		raw.notifyOnRawDataAvailable(true);
		return true;
	}
	private void initRxBuffer(){
		try {
			pis = new PipedInputStream();
			pos = new PipedOutputStream();
			pis.connect(pos);
			bos = new BufferedOutputStream(pos,rxBufSize);
		} catch (Exception e) {
			System.out.print("initRxBuffer:: ");
			System.out.println(e);
		}
	}

	public java.io.InputStream getInputStream() {
		return pis;
	}

	public void setInterval(int interval) {
		raw.setInterval(interval);
	}

	public void setInputBufferSize(int size) {
		rxBufSize=size;
	}

	public int getInputBufferSize() {
		return rxBufSize;
	}

	private void init(String name) throws IOException{
		String tmp;
		System.out.println("name:: \t"+name);
		String dir = System.getProperty("user.dir");
		CtrlDeviceList pref = new CtrlDeviceList(dir+"/device.pref");
		this.name = name;

		// serch device
		int index = pref.findIndex("name",name);

		// get  driver
		driver = pref.getPropertyOf("driver",index);
		System.out.println("driver::\t"+driver);

		// get device 
		device = pref.getPropertyOf("device",index);
		System.out.println("device::\t"+device);

		// get parameter name
		String s = pref.getPropertyOf("option",index);
		optName = s.split(",");

		// get parameter value
		s = pref.getPropertyOf("value",index);
		String[] a=s.split(",");
		optValue = new int[a.length];
		for(int i=0;i<a.length;i++) {
			optValue[i] = Integer.valueOf(a[i]);
			System.out.println(optName[i]+"::\t"+a[i]);
		}
	}
}
