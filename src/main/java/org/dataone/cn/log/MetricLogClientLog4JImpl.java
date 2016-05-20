/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.log;


import org.apache.log4j.Logger;

/**
 *
 * @author waltz
 */
public class MetricLogClientLog4JImpl implements MetricLogClient {
    static Logger logger = Logger.getLogger(MetricLogClientLog4JImpl.class);
    @Override
    public boolean logMetricEvent(MetricLogEntry metricLogEntry) {
        logger.info(metricLogEntry);
        return true;
    }
    
}
