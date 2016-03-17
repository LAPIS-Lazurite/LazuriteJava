package com.lapis_semi.lazurite.io;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.xml.bind.DatatypeConverter;
//import com.lapis_semi.lazurite.io.*;

class test_serial implements SubGHzEventListener{ 
	SubGHz subghz;
	private BufferedReader input;
	static public void main(String[] args){
		System.out.println("test_serial");
		test_serial object = new test_serial();
	}

	public test_serial(){
		// Initializing SubGHz
		try {
			subghz = new SubGHz("LazuritePiGateway");
			subghz.setSerialMode("001d12900003902B");
			subghz.setInterval(1);
			subghz.open();
			input = new BufferedReader(new InputStreamReader(subghz.getInputStream()));
			subghz.addEventListener(this);
			subghz.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		// Shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
				try {
				subghz.removeEventListener();
				System.out.println("shutdownHook !!");
				subghz.close();
				Thread.sleep(100);
				}catch (Exception e){ }
				}
				});
		// start process
		System.out.println("start loop process");
	}

	//  Event Process from SubGHz
	@Override
		public synchronized void SubGHzEvent(SubGHzEventObject evt){
			if(evt.getEventType() == SubGHzEventObject.DATA_AVAILABLE){
				String inputLine;
				try {
					inputLine=input.readLine();
					while(inputLine != null) {
						System.out.println(inputLine);
						inputLine=input.readLine();
					}
				} catch(Exception e) {
//					System.out.println(e);
//					e.printStackTrace();
				}
			}
		}
}
