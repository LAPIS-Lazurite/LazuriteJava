package com.lapis_semi.lazurite.io;

import java.util.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class Raw {
	String[] keys;
	int[] values;
	private int threadInterval;
	public boolean start = false;
	public Main_Raw th;
	private int interval;

	public Raw () throws IOException {
		// Initializing Parameter
		keys=null;
		values=null;
		start = false;
		th = new Main_Raw();
	}

	public void setInterval(int interval) {
		th.setInterval(interval);
	}

	public void notifyOnRawDataAvailable(boolean enable) {
		th.notifyOnRawDataAvailable(enable);
	}

	public void setParameters(String[] keys, int[] values) {
		this.keys = keys;
		this.values = values;
	}

	public void addEventListener(RawEventListener listener){
		th.addEventListener(listener);
	}

	public void load(String driver) throws IOException {
		String cmd = "sudo insmod "+driver;
		InputStream IS;

		if(driver == "") {
			return;
		}

		// Generate command to load driver
		if(keys != null && values != null) {
			int optSize = keys.length;
			for(int i=0;i<optSize;i++)
			{
				if(values[i] <0) continue;
				cmd += " "+keys[i] + "=" + String.valueOf(values[i]);
			}
		}

		// load driver
		System.out.println("Load SubGHz driver");
		System.out.println(cmd);
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			proc.waitFor();

			// output log
			common.printInputStream(proc.getInputStream());
			System.out.println("");

			// display log of loading driver
			proc = Runtime.getRuntime().exec("tail -n 2 /var/log/messages");
			common.printInputStream(proc.getInputStream());
			System.out.println("");
			System.out.println("enabling SubGHz.");
		} catch (Exception e) {
			throw new IOException("Can not load driver");
		}
	}

	public void unload(String driver) throws IOException {
		unload();
	}
	public void unload() throws IOException {
		InputStream IS;
		// Driver insmod
		System.out.println("Unload SubGHz driver");
		try {
			// rmmod driver
			Process proc = Runtime.getRuntime().exec("sudo rmmod DRV_802154");
			proc.waitFor();
			common.printInputStream(proc.getInputStream());
			System.out.println("");
			// printing log
			proc = Runtime.getRuntime().exec("tail -n 1 /var/log/messages");
			common.printInputStream(proc.getInputStream());
			System.out.println("");
		} catch (Exception e) {
			throw new IOException("Can not load driver");
		}
		keys=null;
		values=null;
	}

	public void open(String device) throws IOException {
		String cmd = "sudo chmod 777 ";
		if(device.equals("")) {
			throw new IOException("can not open device");
		}
		cmd += device;
		// change authrization of driver
		try {
			Process proc = Runtime.getRuntime().exec("sudo chmod 777 /dev/bp3596");
			proc.waitFor();
			common.printInputStream(proc.getInputStream());
			// open driver
			th.fis = new FileInputStream(device);
		} catch (Exception e) {
			throw new IOException("Can not open device");
		}
		th.start();
		start = true;
		return;
	}

	public void close(String device) throws IOException {
		close();
	}

	public void close() throws IOException {
		th.fis.close();
	}

	// return number of elements
	public int getRxPacketLength() {
		int size = th.rxList.size();
		int length = 0;
		if(size>0) {
			length = th.rxList.get(0).length;
		}
		return length;
	}

	// receiving packet
	public int getRxPacket(byte[] dist)  {
		int size = th.rxList.size();
		int length = th.rxList.get(0).length;
		// buffer check
		if(size == 0) return 0;
		// buffer size check
		if(dist.length < length) return -1;
		byte[] src = th.rxList.get(0);
		// data copy
		for(int i=0;i<src.length;i++) {
			dist[i] = src[i];
		}
		th.rxList.remove(0);
		return dist.length;
	}

	// Raw level thread class
	public class Main_Raw extends Thread {
		public List<byte[]> rxList;
		public List<byte[]> txList;
		private FileInputStream fis;
		private int interval;
		private boolean start;
		private boolean enbRawDataAvailable=false;
		private RawEventListener listener;

		public Main_Raw() {
			txList = new ArrayList<byte[]>();
			rxList = new ArrayList<byte[]>();
			interval = 20;
			enbRawDataAvailable = false;
		}

		public void setInterval(int interval) {
			this.interval = interval;
		}

		public void notifyOnRawDataAvailable(boolean enable) {
			enbRawDataAvailable = enable;
		}

		public void run() {
			start = true;
			int readlen;
			int rxSize;
			byte[] rxSizeBuf = new byte[2];
			boolean send=false;

			System.out.println("Thread Start");
			try {
				while(start) {
					// get length of receiving list
					readlen = fis.read(rxSizeBuf,0,2);
					// receiving data process
					while(readlen>0) {
						send = true;
						// convert to buffer size;
						rxSize = common.ub2i(rxSizeBuf);
						byte[] b = new byte[rxSize];
						// receive from driver
						readlen = fis.read(b,0,rxSize);
						// Add to list
						rxList.add(b);
						readlen = fis.read(rxSizeBuf,0,2);
					}
					// Event Listener
					if(enbRawDataAvailable && send) {
						this.listener.RawDataAvailable( new RawEventObject(RawEventObject.RAW_DATA_AVAILABLE));
						send = false;
					}
					Thread.sleep(interval);
				}
			} catch(Exception e) {
				start = false;
			}
			start = false;
		}

		public void addEventListener(RawEventListener listener){
			this.listener = listener;
		}

		public void removeEventListener(RawEventListener listener){
			this.listener = null;
		}

		// stop thread
		public void Stop() {
			start = false;
			// waiting thread stop
			try {
				this.join();
			} catch(Exception e){
				e.printStackTrace();
				System.out.println("end receiving SubGHz");
			}
		}
	}
}
