package io.cdap.wrangler.directive;

import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;

import java.util.Collections;
import java.util.List;

public class AggregateStats implements Directive {
    private String sizeColumn;
    private String timeColumn;
    private String outputSizeColumn;
    private String outputTimeColumn;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
                .define("size_col", TokenType.COLUMN)
                .define("time_col", TokenType.COLUMN)
                .define("output_size_col", TokenType.IDENTIFIER)
                .define("output_time_col", TokenType.IDENTIFIER)
                .build();
    }

    @Override
    public void initialize(Arguments args) throws DirectiveParseException {
        sizeColumn = ((ColumnName) args.value("size_col")).value();
        timeColumn = ((ColumnName) args.value("time_col")).value();
        outputSizeColumn = ((Identifier) args.value("output_size_col")).value();
        outputTimeColumn = ((Identifier) args.value("output_time_col")).value();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException {
        long totalBytes = 0;
        long totalTimeNs = 0;

        for (Row row : rows) {
            ByteSize size = new ByteSize((String) row.getValue(sizeColumn));
            TimeDuration duration = new TimeDuration((String) row.getValue(timeColumn));
            totalBytes += size.getBytes();
            totalTimeNs += duration.getNanoseconds();
        }

        double sizeInMB = totalBytes / (1024.0 * 1024.0);
        double timeInSeconds = totalTimeNs / 1_000_000_000.0;

        Row output = new Row();
        output.add(outputSizeColumn, sizeInMB);
        output.add(outputTimeColumn, timeInSeconds);

        return Collections.singletonList(output);
    }
}