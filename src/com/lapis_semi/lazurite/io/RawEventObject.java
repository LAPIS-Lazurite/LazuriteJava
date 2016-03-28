package com.lapis_semi.lazurite.io;

import java.util.*;

public class RawEventObject extends EventObject {
	// Event Code Definision
	public final static int RAW_DATA_AVAILABLE = 0x1;
	public final static int DATA_AVAILABLE = 0x2;
	private static final long serialVersionUID = 1L;
	private int event;

	public RawEventObject(int source) {
		super(source);
		event = source;
	}
	public int getEventType(){
		return event;
	}
}
