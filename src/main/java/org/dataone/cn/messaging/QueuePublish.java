package org.dataone.cn.messaging;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Possible unnecessary variant of QueueAccess...
 * @author rnahf
 *
 */
public class QueuePublish {

    private ConnectionFactory connFactory;
     
    public QueuePublish(ConnectionFactory connectionFactory) {
        this.connFactory = connectionFactory;
    }
    
    public void publish(String exchange, String queue, Map<String, Object> customHeaders, byte[] message) throws IOException, TimeoutException {
       
        // Not sure if it makes sense to conserve the connection 
        Connection connection = this.connFactory.newConnection();

        //
        // set up the channel
        //
        Channel channel = connection.createChannel();        
        // this puts the channel in publisher confirm mode
        channel.confirmSelect();
        
        // confirmations should be processed asynchronously from publishing
        // so this ConfirmListener is the callback that does that 
        channel.addConfirmListener(new ConfirmListener() {

            /**
             * @param acknowledgeMultiple: if true, Acks everything up to this deliveryTag since last ack or nack
             */

            @Override
            public void handleAck(long deliveryTag, boolean acknowledgeMultiple) throws IOException {
                // TODO: log success
                // how do we get hold of the PID from the deliveryTag
                
            }

            /**
             * @param acknowledgeMultiple: if true, Nacks everything up to this deliveryTag since last ack or nack
             */
            @Override
            public void handleNack(long deliveryTag, boolean acknowledgeMultiple) throws IOException {
                
                // TODO log failure
                // what else needed here?  The queue rejected it because the queue it full.
                // we can hang onto it in Metacat in some sort of backlog in-memory queue,
                // but that could get swamped too.
            }            
        });
 
        // makes a null value the same as empty string
        // the empty/null exchange is the default exchange that is
        // used to route to a named queue
        if (exchange == null) {
            exchange = "";
        }
        
        // basic properties are used as delivery instructions
        // for sending a message, and are where custom headers are placed
        BasicProperties props = new AMQP.BasicProperties.Builder()
             .deliveryMode(2)  // persistent message
             .headers(customHeaders)  // from method parameters
             .build();
        
        
 //       log.info(String.format("Message with pid %s submitted with deliveryTag %s", pid, channel.getNextPublishSeqNo()));
        channel.basicPublish(exchange, queue, props, message);

        channel.close();
        connection.close();
    
    }
    
}
