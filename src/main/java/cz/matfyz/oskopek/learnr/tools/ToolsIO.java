package cz.matfyz.oskopek.learnr.tools;

import java.util.concurrent.TimeUnit;

/**
 * A class for static helper methods related to input/output.
 */
public class ToolsIO {

    /**
     * Converts the long value to a <emph>HH:MM:SS</emph> formatted string.
     *
     * @param nanoTime the time value in nanoseconds
     * @return a correctly formatted string
     */
    public static String convertNanosToHMS(long nanoTime) {
        long millis = nanoTime / 1_000_000l;
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

}
