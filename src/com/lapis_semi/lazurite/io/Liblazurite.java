/*! 
  @file Liblazurite.java
  @mainpage

  Liblzaurite.java for liblazurite

  @brief JAVA api for liblazurite

  @section general description
  Documentation of Raw.java for Lazurite

  @section How to install
  @code
  git clone git://github.com/LAPIS-Lazurite/LazuriteJava
  make
  make install
  @endcode

*/
package com.lapis_semi.lazurite.io;
import com.ochafik.lang.jnaerator.runtime.Structure;
import java.util.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
//import com.sun.jna.Structure;
import com.sun.jna.NativeLong;

/*! About Liblazurite
  this class is API to use liblazurite for LazuriteGraph



*/
public class Liblazurite {

	private LazuriteLib lz;
	private SUBGHZ_MAC mac = new SUBGHZ_MAC();

	interface LazuriteLib extends Library {

		// load library
		LazuriteLib INSTANCE = (LazuriteLib) Native.loadLibrary("lazurite",LazuriteLib.class);

		// functions
		int lazurite_link(short addr);
		int lazurite_init();
		int lazurite_remove();
		int lazurite_setRxAddr(short rxaddr);
		int lazurite_setTxPanid(short txpanid);
		int lazurite_begin(byte ch, short mypanid, byte rate,byte pwr);
		int lazurite_close();
		int lazurite_send(short rxpanid,short rxaddr,byte[] payload, short length);
		int lazurite_rxEnable();
		int lazurite_rxDisable();
		int lazurite_getMyAddress(short[] myaddr);
		int lazurite_write(byte[] payload, short size);
		int lazurite_decMac(SUBGHZ_MAC mac,byte[] raw,short raw_size);
		int lazurite_available();
		int lazurite_read(byte[] raw, short[] size);
		int lazurite_readPayload(byte[] payload, short[] size);
		int lazurite_readLink(byte[] payload, short[] size);

		int lazurite_getRxTime(int[] tv_sec,int[] tv_nsec);

		int lazurite_getRxRssi();

		int lazurite_getTxRssi();

		int lazurite_getAddrType();

		int lazurite_setAddrType(byte addr_type);

		int lazurite_getSenseTime();
		int lazurite_setSenseTime(byte senseTime);
		int lazurite_getTxRetry();
		int lazurite_setTxRetry(byte retry);
		int lazurite_getTxInterval();

		int lazurite_setTxInterval(short txinterval);
		int lazurite_getCcaWait();
		int lazurite_setCcaWait(short cawait);

	}

	/*! initial sequence
	*/
	public Liblazurite() throws IOException {
		int result;
		lz = LazuriteLib.INSTANCE;
	}

		/******************************************************************************/
		/*! @brief set linked address
		  addr = 0xffff		receiving all data
		  addr != 0xffff	receiving data of linked address only
		  @param[out]     addr   linked address
		  @par            Refer
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public void link(short addr) throws IOException {
		int result;
		result = lz.lazurite_link(addr);
		if(result < 0) {
			String msg = "link error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief load LazDriver
		  @param      none
		  @return         0=success <br> 0 < fail
		  @exception  none
		  @todo  must be change folder name of lazdriver
		 ******************************************************************************/
	public void init() throws IOException {
		int result;
		result = lz.lazurite_init();
		if(result < 0) {
			String msg = "init error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief remove driver from kernel
		  @param     none
		  @return         0=success <br> 0 < fail
		  @exception none
		 ******************************************************************************/
	public void remove() throws IOException {
		int result;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		result = lz.lazurite_remove();
		if(result < 0) {
			String msg = "remove error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief set rx address to be sent
		  @param[out]     tmp_rxaddr   rxaddr to be sent
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public void setRxAddr(short rxaddr) throws IOException {
		int result;
		result = lz.lazurite_setRxAddr(rxaddr);
		System.out.println("setRxAddri="+String.valueOf(rxaddr));
		if(result < 0) {
			String msg = "setRxAddr error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief set PANID for TX
		  @param[out]     txpanid    set PANID(Personal Area Network ID) for sending
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public void setTxPanid(short txpanid) throws IOException {
		int result;
		result = lz.lazurite_setTxPanid(txpanid);
		if(result < 0) {
			String msg = "setTxPanid error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief setup lazurite module
		  @param[in]  ch (RF frequency)<br>
		  in case of 100kbps, ch is 24-31, 33-60<br>
		  in case of 50kbps, ch is 24-61
		  @param[in]  mypanid
		  set my PANID.
		  @param[in] rate 50 or 100<br>
		  100 = 100kbps<br>
		  50  = 50kbps 
		  @param[in] pwr 1 or 20<br>
		  1  = 1mW<br>
		  20 = 20mW
		  @return         0=success <br> 0 < fail
		  @exception  none
		 ******************************************************************************/
	public void begin(byte ch, short mypanid, byte rate, byte pwr) throws IOException {
		int result;
		result = lz.lazurite_begin(ch,mypanid,rate,pwr);
		if(result < 0) {
			String msg = "begin error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief close driver (stop RF)
		  @param     none
		  @return         0=success <br> 0 < fail
		  @exception none
		 ******************************************************************************/
	public void close() throws IOException {
		int result;
		result = lz.lazurite_close();
		if(result < 0) {
			String msg = "close error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief send data
		  @param[in]     rxpanid	panid of receiver
		  @param[in]     txaddr     16bit short address of receiver<br>
		  rxpanid & txaddr = 0xffff = broadcast <br>
		  others = unicast <br>
		  @param[in]     payload start poiter of data to be sent
		  @param[in]     length length of payload
		  @return         0=success=0 <br> -ENODEV = ACK Fail <br> -EBUSY = CCA Fail
		  @exception none
		 ******************************************************************************/
	public int send(short rxpanid,short rxaddr,byte[] payload, short length) throws IOException {
		int result;
		result = lz.lazurite_send(rxpanid,rxaddr,payload,length);
		if (( result == -16 ) || (result  == -18))
		{
			// -16 = -ENODEV   NO ACK
			// -18 = -EBUSY    CCA FAIL
			return result;
		}
		else if(result < 0) {
			String msg = "send error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief enable RX
		  @param     none
		  @return         0=success <br> 0 < fail
		  @exception none
		 ******************************************************************************/
	public void rxEnable() throws IOException {
		int result;
		result = lz.lazurite_rxEnable();
		if(result < 0) {
			String msg = "rxEnable error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief disable RX
		  @param     none
		  @return         0=success <br> 0 < fail
		  @exception none
		 ******************************************************************************/
	public void rxDisable() throws IOException {
		int result;
		result = lz.lazurite_rxDisable();
		if(result < 0) {
			String msg = "rxDisable error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief get my address
		  @param[out]     myaddr pointer to return my address <br>
		  short[] myaddr = new short[1];
		  @return         0=success <br> 0 < fail
		  @exception none
		 ******************************************************************************/
	public void getMyAddress(short[] myaddr) throws IOException {
		int result;
		result = lz.lazurite_getMyAddress(myaddr);
		if(result < 0) {
			String msg = "getMyAddress error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief send data via 920MHz
		  @param[in]     *payload     data <br>
		  byte[] payload = new byte[256]
		  @param[in]      size        data of length
		  @return         0=success=0 <br> -ENODEV = ACK Fail <br> -EBUSY = CCA Fail
		  @exception      none
		 ******************************************************************************/
	public int write(byte[]payload,short length) throws IOException {
		int result;
		result = lz.lazurite_write(payload,length);

		if (( result == -16 ) || (result  == -18))
		{
			// -16 = -ENODEV   NO ACK
			// -18 = -EBUSY    CCA FAIL
			return result;
		}
		else if(result < 0) {
			String msg = "write error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief decoding mac header for external function
		  @param[out]     *mac    result of decoding raw
		  @param[in]      *raw    raw data of ieee802154
		  @param[in]      raw_len length of raw
		  @return         length of raw data
		  @exception      none
		 ******************************************************************************/
	public int decMac(SUBGHZ_MAC mac, byte[] raw, short length) throws IOException {
		int result;
		result = lz.lazurite_decMac(mac,raw,length);
		if(result < 0) {
			String msg = "decMac error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief get size of receiving data
		  @param      none
		  @return     length of receiving packet
		  @exception  none
		 ******************************************************************************/
	public int available() throws IOException {
		int result;
		result = lz.lazurite_available();
		if(result < 0) {
			String msg = "available error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief read raw data
		  @param[out]     *raw
		  pointer to write received packet data.<br>
		  byte[] raw = new byte[256]
		  @param[out]     size
		  size of raw data
		  short[] size = new short[1];
		  @return     length of receiving packet
		  @exception  none
		 ******************************************************************************/
	public int read(byte[] raw, short[] length) throws IOException {
		int result;
		result = lz.lazurite_read(raw,length);
		if(result < 0) {
			String msg = "read error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief read only payload. header is abandoned.
		  @param[out]     *payload    memory for payload to be written. need to reserve 250 byte in maximum.
		  byte[] payload = new byte[256]
		  @param[out]     *size       size of payload
		  short[] size = new short[1]
		  @return         size of payload
		  @exception      none
		  @note           about lazurite_readPayload:
		  mac header is abandoned.
		 ******************************************************************************/
	public int readPayload(byte[] payload, short[] length) throws IOException	{
		int result;
		result = lz.lazurite_readPayload(payload,length);
		if(result < 0) {
			String msg = "readPayload error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief read payload from linked address
		  @param[out]     *payload   pointer of payload
		  byte[] payload = new byte[256];
		  @param[out]     *size       length of payload
		  short[] size = new short[1];
		  @return         size
		  @exception      none
		  @note  About linkedAddress mode:
		  The size is length of receiving packet in kernel driver.
		  When tx address is wrong in linked address mode, lazurite_readPayload or lazurite_read return 0.
		  mac header is abandoned in this mode.
		 ******************************************************************************/
	public int readLink(byte[] payload, short[] length) throws IOException
	{
		int result;
		result = lz.lazurite_readLink(payload,length);
		if(result < 0) {
			String msg = "readLink error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief get Receiving time
		  @param[out]     *tv_sec     32bit linux time data
		  int[] tv_sec = new int[1];
		  @param[out]     *tv_nsec    32bit nsec time
		  int[] tv_nsec = new int[1];
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public int getRxTime(int[] tv_sec, int[] tv_nsec) throws IOException
	{
		int result;
		result = lz.lazurite_getRxTime(tv_sec,tv_nsec);
		if(result < 0) {
			String msg = "getRxTime error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief get Rx RSSI of last receiving packet
		  @param[out]     *rssi   value of RSSI.  0-255. 255 is in maxim
		  @return         0 > rssi <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public int getRxRssi() throws IOException
	{
		int result;
		result = lz.lazurite_getRxRssi();
		if(result < 0) {
			String msg = "getRxRssi error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief get RSSI of ack in last tx packet
		  @param[out]     *rssi   value of RSSI.  0-255. 255 is in maxim
		  @return         Success=0, Fail<0
		  @return         0 > rssi <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public int getTxRssi() throws IOException
	{
		int result;
		result = lz.lazurite_getTxRssi();
		if(result < 0) {
			String msg = "getRxRssi error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief get address type
		  @param[in]     none 
		  @return         address type
		  type | rx_addr | tx_addr | panid_comp | rx panid | tx_panid
		  -----| --------| --------| ---------- | -------- | --------
		  0 | N | N | 0 | N | N
		  1 | N | N | 1 | Y | N
		  2 | N | Y | 0 | N | Y
		  3 | N | Y | 1 | N | N
		  4 | Y | N | 0 | Y | N
		  5 | Y | N | 1 | N | N
		  6 | Y | Y | 0 | Y | N
		  7 | Y | Y | 1 | N | N
		  @exception      none
		 ******************************************************************************/
	public int getAddrType() throws IOException
	{
		int result;
		result = lz.lazurite_getAddrType();
		if(result < 0) {
			String msg = "setAddrType error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief set address type
		  @param[in]      mac address type to send
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public void setAddrType(byte addr_type) throws IOException
	{
		int result;
		result = lz.lazurite_setAddrType(addr_type);
		if(result < 0) {
			String msg = "setAddrType error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief get cycle time to resend, when tx is failed.
		  @param[in]      none
		  @return         0 > txretry <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public int getTxRetry() throws IOException
	{
		int result;
		result = lz.lazurite_getTxRetry();
		if(result < 0) {
			String msg = "getTxRetry error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief set cycle to resend, when Tx is failed.
		  @param[in]      retry cycle 0-255(3 in default)
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public void setTxRetry(byte addr_type) throws IOException
	{
		int result;
		result = lz.lazurite_setTxRetry(addr_type);
		if(result < 0) {
			String msg = "setTxRetry error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}


		/******************************************************************************/
		/*! @brief get CCA cycle Time
		  @param[in]     none 
		  @return         0 > senseTime <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public int getSenseTime() throws IOException
	{
		int result;
		result = lz.lazurite_getSenseTime();
		if(result < 0) {
			String msg = "getSenseTime error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief set cycle of CCA
		  @param[in]      sense_time <br> cycle 0-255(20 in default)
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public void setSenseTime(byte sense_time) throws IOException
	{
		int result;
		result = lz.lazurite_setSenseTime(sense_time);
		if(result < 0) {
			String msg = "setSenseTime error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief set tx interval until resend, when Tx is failed.
		  @param[in]      none
		  @return         txinterval(ms) >0 <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public int getTxInterval() throws IOException
	{
		int result;
		result = lz.lazurite_getTxInterval();
		if(result < 0) {
			String msg = "getTxInterval error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief set interval to resend, when tx is failed.
		  @param[in]      txinterval 0(0ms) - 500(500ms), 500 in default
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public void setTxInterval(short txinterval) throws IOException
	{
		int result;
		result = lz.lazurite_setTxInterval(txinterval);
		if(result < 0) {
			String msg = "setTxInterval error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}

		/******************************************************************************/
		/*! @brief get backoff time
		  @param[in]      none
		  @return         0 > backoff time <br> 0 < fail <br>
		  backoff time = 320us * 2^cca_wait
		  @exception      none
		 ******************************************************************************/
	public int getCcaWait() throws IOException
	{
		int result;
		result = lz.lazurite_getCcaWait();
		if(result < 0) {
			String msg = "getCcaWait error="+String.valueOf(result);
			throw new IOException(msg);
		}
		return result;
	}

		/******************************************************************************/
		/*! @brief set cca backoff time
		  @param[in]      ccawait (0 - 7), 7 in default <br>
		  backoff time = 320us * 2^cca_wait
		  @return         0=success <br> 0 < fail
		  @exception      none
		 ******************************************************************************/
	public void setCcaWait(byte ccawait) throws IOException
	{
		int result;
		result = lz.lazurite_setCcaWait(ccawait);
		if(result < 0) {
			String msg = "setCcaWait error="+String.valueOf(result);
			throw new IOException(msg);
		}
	}
}


