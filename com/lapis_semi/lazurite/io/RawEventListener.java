package com.lapis_semi.lazurite.io;

import java.util.*;

public interface RawEventListener extends EventListener {
	public void RawDataAvailable(RawEventObject ev);
}
