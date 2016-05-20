/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.log;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author waltz
 */
public class MetricLogEntryTest {

    private static final Logger log = LoggerFactory.getLogger(MetricLogEntryTest.class);
    MetricLogClient metricLogClient = MetricLogClientFactory.getMetricLogClient();

    private static final String JSON_START_TOKEN = "^\\{";
    private static final String JSON_END_TOKEN = "\\}$";;
    private static final String SYNC_QUEUED_TOKEN="\\\"event\\\"\\:\\\"synchronization\\squeued\\\"\\,";
    private static final String DATE_LOGGED_TOKEN="\\\"dateLogged\\\"\\:\\\"\\d{4}\\-\\d{2}\\-\\d{2}T\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{3}(?:(?:[\\+\\-]\\d\\d:\\d\\d)|Z)\\\"";
    private static final String THREAD_TOKEN="\"threadName\"\\:\"\\w+\"\\,\"threadId\"\\:\\d+,";
 
   // static final Pattern minJsonPattern = Pattern.compile("^\\{\"event\"\\:\"synchronization\\squeued\"\\,\"dateLogged\"\\:.+\"\\}$");
    @Test
    public void testMinimalValidMetricLogEntry() {
        String minJsonRegex = JSON_START_TOKEN + SYNC_QUEUED_TOKEN + THREAD_TOKEN + DATE_LOGGED_TOKEN  + JSON_END_TOKEN ;
        Pattern minJsonPattern = Pattern.compile(minJsonRegex);
        MetricLogEntry minMetricLogEntry = new MetricLogEntry(MetricEvent.SYNCHRONIZATION_QUEUED);
        String json = minMetricLogEntry.toString();
        metricLogClient.logMetricEvent(minMetricLogEntry);
        log.info(json);
        Matcher jsonMatcher = minJsonPattern.matcher(json);
        assertTrue(jsonMatcher.matches());
        
    }
   // static final Pattern minJsonPattern = Pattern.compile("^\\{\"event\"\\:\"synchronization\\squeued\"\\,\"dateLogged\"\\:.+\"\\}$");
    @Test(expected=IllegalArgumentException.class)
    public void testFailedMetricLogEntry() {

        MetricLogEntry minMetricLogEntry = new MetricLogEntry(null);

        
    }
}
