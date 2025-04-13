package io.cdap.directives;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.test.TestingRig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {

    @Test
    public void testAggregation() throws Exception {
        List<Row> rows = Arrays.asList(
            new Row().add("data_transfer_size", "1MB").add("response_time", "500ms"),
            new Row().add("data_transfer_size", "512KB").add("response_time", "1500ms")
        );

        String[] recipe = new String[] {
            "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        List<Row> results = TestingRig.execute(recipe, rows);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(1.5, results.get(0).getValue("total_size_mb"));   // 1MB + 512KB = 1.5MB
        Assert.assertEquals(2.0, results.get(0).getValue("total_time_sec"));  // 500ms + 1500ms = 2s
    }
