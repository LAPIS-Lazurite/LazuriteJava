import java.util.Formatter;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import com.lapis_semi.lazurite.io.*;

class sample_raw2 { 
	public sample_raw2(){
	}
	static public void main(String[] args){
		SubGHz subghz;
		// Initializing SubGHz
		try {
			subghz = new SubGHz("LazuritePiGateway");
			subghz.open();
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		// Shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
				try {
				System.out.println("shutdownHook !!");
				subghz.close();
				Thread.sleep(100);
				}catch (Exception e){ }
				}
				}); 
		// start process
		System.out.println("start loop process");
		for(;;){
			try {
				int length = subghz.getRxPacketLength();
				if(length>0) {
					byte[] b=new byte[length];
					subghz.getRxPacket(b);
					mac_info.fromRaw(b);
					Date time = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss.SSS zZ",Locale.US);
					time.setTime(mac_info.time);
					Formatter fm = new Formatter();
					String out = new String(mac_info.payload);
					fm.format("%s,%s,%d,%d,%d, %08x,%d,%02x%02x%02x%02x%02x%02x%02x%02x, %08x,%d,%02x%02x%02x%02x%02x%02x%02x%02x, %03d,%s",
							sdf.format(time),
							mac_info.area,
							mac_info.ch,
							mac_info.rate,
							mac_info.pwr,
							mac_info.rxPanid,
							mac_info.rxAddrType,
							mac_info.rxAddr[0],
							mac_info.rxAddr[1],
							mac_info.rxAddr[2],
							mac_info.rxAddr[3],
							mac_info.rxAddr[4],
							mac_info.rxAddr[5],
							mac_info.rxAddr[6],
							mac_info.rxAddr[7],
							mac_info.txPanid,
							mac_info.txAddrType,
							mac_info.txAddr[0],
							mac_info.txAddr[1],
							mac_info.txAddr[2],
							mac_info.txAddr[3],
							mac_info.txAddr[4],
							mac_info.txAddr[5],
							mac_info.txAddr[6],
							mac_info.txAddr[7],
							mac_info.rssi,
							out);
					System.out.println(fm);
				}
				else {
					Thread.sleep(100);
				}
				//Thread.sleep(1000);
			}catch (Exception e){
				System.out.println(e);}
		}
	}
}
