package io.cdap.wrangler.api.parser;

public class ByteSize extends Token {
    private final long bytes;

    public ByteSize(String value) {
        super(TokenType.BYTE_SIZE, value);
        this.bytes = parse(value);
    }

    private long parse(String input) {
        input = input.trim().toUpperCase();

        double number = Double.parseDouble(input.replaceAll("[^0-9.]", ""));
        if (input.endsWith("TB")) return (long)(number * 1024 * 1024 * 1024 * 1024);
        if (input.endsWith("GB")) return (long)(number * 1024 * 1024 * 1024);
        if (input.endsWith("MB")) return (long)(number * 1024 * 1024);
        if (input.endsWith("KB")) return (long)(number * 1024);
        if (input.endsWith("B"))  return (long)(number);
        return 0; // default/fallback
    }

    public long getBytes() {
        return bytes;
    }
}