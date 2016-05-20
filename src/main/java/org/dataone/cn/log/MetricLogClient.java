/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.log;

/**
 * Provides a uniform manner to report MetricEvents through a
 * MetricLogEntry
 * 
 * @author waltz
 */
public interface MetricLogClient {
    
    public boolean logMetricEvent(MetricLogEntry metricLogEntry);
}
