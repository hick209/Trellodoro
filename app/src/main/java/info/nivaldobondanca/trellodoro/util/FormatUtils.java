package info.nivaldobondanca.trellodoro.util;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Nivaldo Bondança
 */
public class FormatUtils {
	private FormatUtils() {}

	public static CharSequence millisToSecondsAndMinutesString(long timeMillis) {
		final long seconds = TimeUnit.SECONDS.convert(timeMillis, TimeUnit.MILLISECONDS) % 60;
		final long minutes = TimeUnit.MINUTES.convert(timeMillis, TimeUnit.MILLISECONDS) % 60;

		return String.format(Locale.ENGLISH, "%d:%02d", minutes, seconds);
	}
}
