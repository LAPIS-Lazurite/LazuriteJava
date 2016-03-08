package com.lapis_semi.lazurite.io;

import java.util.*;

public class SubGHzEventObject extends EventObject {
	public final static int RAW_DATA_AVAILABLE = 0x1;
	public final static int DATA_AVAILABLE = 0x1;
	private static final long serialVersionUID = 1L;
	private int event;
	public SubGHzEventObject(Object source) {
		super(source);
		event = (int)source;
	}
	public int getEventType(){
		return event;
	}
}
