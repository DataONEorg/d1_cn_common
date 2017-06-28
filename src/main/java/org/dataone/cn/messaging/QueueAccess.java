package org.dataone.cn.messaging;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.support.CorrelationData;


/**
 * QueueAccess is a class for reading from and writing to a queue.  Both synchronous 
 * and asynchronous reading strategies are supported.
 * <p/>
 * It is meant as a facade for Spring-rabbit package to implement a standard
 * set of behaviors to simplify client interaction with the RabbitMQ broker. 
 * Specifically, publisher confirmations are enabled, consumer acknowledgments
 * are send after message processing, and a AMQP basic.qos of 1 are employed
 * to support a competing consumer pattern with message durability. 
 * delivery 
 * 
 * @author rnahf
 *
 */
public class QueueAccess {

    /* Using the concrete implementation here because it allows setting publisher confirmation */
    CachingConnectionFactory connectionFactory;
    RabbitTemplate template;
    String queueName;
    PublisherConfirmCallback pubCallback;
    List<SimpleMessageListenerContainer> listenerContainers;
    
    static final long DEFAULT_CONSUME_TIMEOUT = 10 * 1000;
    static Logger logger = Logger.getLogger(QueueAccess.class.getName());
    String sendMode;
    protected final Map<String,String> correlationIdMap = new HashMap<>();
    
    public QueueAccess(CachingConnectionFactory connFactory, String queueName) {
        this.connectionFactory = connFactory;
        
        this.template = new RabbitTemplate(this.connectionFactory);
        this.pubCallback = new PublisherConfirmCallback();
        this.template.setConfirmCallback(this.pubCallback);
        
        this.template.setReturnCallback(new ReturnCallback() {

            @Override
            public void returnedMessage(Message message, int replyCode,
                    String replyText, String exchange, String routingKey) {
                logger.info("Got returnedMessage: " + message + " with reply code: " + replyCode + " : " + replyText);
                
            }
            
        });
        
 
        this.queueName = queueName;
        listenerContainers = new ArrayList<>();
        logger.info("new Queue Access created for " + queueName);
    }
    
    public String getQueueName() {
        return queueName;
    }
 

    /** 
     * Publishes a message to the specified exchange
     * @param exchange
     * @return false upon AmqpException (a RuntimeException)
     */
    public boolean publish(String exchange, Message message) {
        try {
            CorrelationData cData = new CorrelationData();
            String uniqueId = String.format("%s : exchange = %s : routingKey = %s", UUID.randomUUID().toString(), exchange, this.queueName);

            cData.setId(uniqueId);
//            correlationIdMap.put(uniqueId, getQueueName() );
            
            if (connectionFactory.isPublisherReturns()) {
                this.template.sendAndReceive(exchange, null, message, cData);
            }
            else if (connectionFactory.isPublisherConfirms()) {
                this.template.send(exchange, null, message, cData);
            }
            else { 
                this.template.send(exchange, null, message, null);
            }
            return true;
            
        } catch (AmqpException runtimeException) {
            runtimeException.printStackTrace();
            return false;
        }
    }
    
    
    /** 
     * Publishes a message to the associated queue.
     * @param message
     * @return false upon AmqpException (a RuntimeException)
     */
    public boolean publish(Message message) {

        try {
            CorrelationData cData = new CorrelationData();
            String uniqueId = String.format("%s : exchange = %s : routingKey = %s", UUID.randomUUID().toString(), "", this.queueName);
            cData.setId(uniqueId);
//            correlationIdMap.put(uniqueId, getQueueName() );
            
            if (connectionFactory.isPublisherReturns()) {
                this.template.sendAndReceive(null, this.queueName, message, cData);
            }
            else if (connectionFactory.isPublisherConfirms()) {
                this.template.send(null, this.queueName, message, cData);
            }
            else { 
                this.template.send(this.queueName, message);
            }
            return true;
            
        } catch (AmqpException runtimeException) {
            runtimeException.printStackTrace();
            return false;
        }
    } 
    
    /**
     * A class used to help in logging publishing publishing attempts.
     * @author rnahf
     *
     */
    static class PublisherConfirmCallback implements RabbitTemplate.ConfirmCallback {

        private static final Log LOGGER = LogFactory.getLog(PublisherConfirmCallback.class);

        @Override
        public void confirm(CorrelationData correlationData, boolean ack,
                String cause) {
            String ackType = ack ? "ACK" : "NACK";

//            LOGGER.info(
            System.out.println(        String.format("%s received from broker: [cause=%s : correlationData=%s]",
                    ackType, cause,  correlationData == null ? "null" : correlationData.getId()));
        }
    }
    
    /**
     * The synchronous consumer method for getting the next item off the queue,
     * blocking for the timeout period before returning a Message or null.
     * <p/>
     * If a message is returned an ACK to the broker has been sent.
     */
    public Message getNextDelivery(final long timeoutMillis) {
        return this.template.receive(this.queueName, timeoutMillis);
    }
    
    
            
            
    /**
     * This attaches one or more competing consumers to the channel that the channel triggers when
     * a message is available.  The consumer containers will be in AcknowledgementMode.AUTO (default) 
     * which means they acknowledge message receipt if the messageListener does not throw an exception.
     * <p/>
     * WARNING: If count > 1, the configured MessageListener method needs to be thread safe, as the
     * instance will be shared by multiple threads to achieve concurrency.  If the listener routine is 
     * not thread safe, instantiation of multiple QueueAccess instances with different MessageListener
     * instantiations is an alternative.
     * <p/> 
     *  
     * @param - the number of competing consumers to register.
     * @param channelAwareMessageListener
     * @throws RuntimeException - throwing a runtime exception prevents the consumer from acknowledging
     * receipt of the message, and it remains on the source queue.
     */
    public void registerAsynchronousMessageListener(int count, MessageListener messageListener) {
       
        logger.info("Registering AsynchronousMessageListener containers: count = " + count);
        ExecutorService executors = Executors.newFixedThreadPool(count);
        for (int i=0; i<count; i++) {
            
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.addQueueNames(this.queueName);
            container.setConnectionFactory(this.connectionFactory);

            container.setupMessageListener(messageListener);
            this.listenerContainers.add(container);

            // should each container just use a single thread executor?
            container.setTaskExecutor(executors);
            // need to start the listener container 
            container.start();
        }  
    }
    
    
//    public void registerAsynchronousMessageListener(int count, CompetingConsumerFactory listenerFactory) 
//            throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        
//        logger.info("Registering AsynchronousMessageListener containers: count = " + count);
//        ExecutorService executors = Executors.newFixedThreadPool(count);
//        for (int i=0; i<count; i++) {
//            
//            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//            container.addQueueNames(this.queueName);
//            container.setConnectionFactory(this.connectionFactory);
//
//
//            if (listenerFactory instanceof MessageListener) {
//                container.setupMessageListener(listenerFactory.createInstance());
//            } 
//            else { // we'll need to adapt
//                MessageListenerAdapter adapter = new MessageListenerAdapter();
//                adapter.setDefaultListenerMethod(listenerFactory.getListenerMethod());
//                adapter.setMessageConverter(null);
//                container.setupMessageListener(adapter);
//            }
//
//            this.listenerContainers.add(container);
//
//            // should each container just use a single thread executor?
//            container.setTaskExecutor(executors);
//            // need to start the listener container 
//            container.start();
//        }  
//    }
    
    
    

    /**
     * Call this method to stop the registered message listener
     */
    public void clearAsynchronousMessageListeners() {
        
        // no need to clear out the property
        if (this.listenerContainers != null) {
            for (MessageListenerContainer c : this.listenerContainers) {
                c.stop();
            }
        }
    }

}
