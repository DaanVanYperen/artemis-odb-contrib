package net.mostlyoriginal.api.utils;

/**
 * Time conversion helper.
 *
 * @author Daan van Yperen
 */
public class Duration {
	private Duration() {}

	/**
	 * Convert seconds to default time unit for artemis-odb.
	 * @return seconds
	 */
	public static float seconds(float seconds) {
		return seconds;
	}

	/**
	 * Convert milliseconds to default time unit for artemis-odb.
	 * @return seconds
	 */
	public static float ms(float ms) {
		return ms / 1000;
	}

	/**
	 * Convert milliseconds to default time unit for artemis-odb.
	 * @return seconds.
	 */
	public static float milliseconds(float ms) {
		return ms / 1000;
	}

}
