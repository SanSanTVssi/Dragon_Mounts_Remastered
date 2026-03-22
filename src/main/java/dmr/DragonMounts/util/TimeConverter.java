package dmr.DragonMounts.util;

import dmr.DragonMounts.ModConstants;

public class TimeConverter {
	public static int toTicks(int seconds) {
		return seconds * ModConstants.TICKS_PER_SECOND;
	}
}
