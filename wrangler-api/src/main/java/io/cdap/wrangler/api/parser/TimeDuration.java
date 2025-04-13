package io.cdap.wrangler.api.parser;

public class TimeDuration extends Token {
    private final long nanoseconds;

    public TimeDuration(String value) {
        super(TokenType.TIME_DURATION, value);
        this.nanoseconds = parse(value);
    }

    private long parse(String input) {
        input = input.trim().toLowerCase();
        double number = Double.parseDouble(input.replaceAll("[^0-9.]", ""));

        if (input.endsWith("ns")) return (long)(number);
        if (input.endsWith("ms")) return (long)(number * 1_000_000);
        if (input.endsWith("s") || input.endsWith("sec") || input.endsWith("seconds")) return (long)(number * 1_000_000_000);
        if (input.endsWith("min") || input.endsWith("minutes")) return (long)(number * 60 * 1_000_000_000);
        return 0; // fallback
    }

    public long getNanoseconds() {
        return nanoseconds;
    }

    public long getMilliseconds() {
        return nanoseconds / 1_000_000;
    }

    public double getSeconds() {
        return nanoseconds / 1_000z_000_000.0;
    }
}