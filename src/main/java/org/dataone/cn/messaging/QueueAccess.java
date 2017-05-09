package org.dataone.cn.messaging;

import java.io.IOException;
//import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ChannelProxy;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.ConsumerCancelledException;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.Delivery;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


/**
 * QueueAccess is a class for pushing a message onto a queue, or consuming one off the end.
 * The method implementations wrap and a Spring-AMQP RabbitTemplate. 
 * It ensures publishing confirmations from the broker, and 
 * @author rnahf
 *
 */
public class QueueAccess {

    /* Using the concrete implementation here because it allows setting publisher confirmation */
    CachingConnectionFactory connectionFactory;
    RabbitTemplate template;
    String queueName;
    MessageListener messageListener = null;
    PublisherConfirmCallback pubCallback;
    
    static final long DEFAULT_CONSUME_TIMEOUT = 10 * 1000;
    static Logger logger = Logger.getLogger(QueueAccess.class.getName());
    
    
    public QueueAccess(CachingConnectionFactory connFactory, String queueName) {
        this.connectionFactory = connFactory;
        this.connectionFactory.setPublisherConfirms(true);
        this.connectionFactory.setPublisherReturns(false);

        
        this.template = new RabbitTemplate(this.connectionFactory);
        this.pubCallback = new PublisherConfirmCallback();
        this.template.setConfirmCallback(this.pubCallback);
 
        this.queueName = queueName;
    }
    
    
    /** 
     * publish a message to the queue
     * @param message
     * @return
     */
    public boolean publish(Message message) {
        try { 
            this.template.send(this.queueName, message);
            return true;
        } catch (AmqpException runtimeException) {
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
            if(ack){
                 LOGGER.info("ACK received");
            }
            else{
                LOGGER.info("NACK received");
            }       
        }
    }
    
    
    /**
     * The synchronous consume method for getting the next item off the queue,
     * blocking for the timeout period before returning a Message or null.
     * 
     * This method does  wait to acknowledge receipt of Message, so if called,
     * the Message received from the queue is in an unpersisted state.
     * 
     * For control over acknowledging consumption, use the Handler methods.
     * 
     * @param timeoutMillis - the time to wait for a Message
     * @return - a Message or null
     * @throws AmqpException - a runtime exception
     */
    public Message consumeNextNoAck(final long timeoutMillis) throws AmqpException {
        return this.template.execute(new ChannelCallback<Message>() {

            @Override
            public Message doInRabbit(Channel channel) throws Exception {
                
                Delivery delivery = consumeDelivery(channel, queueName, timeoutMillis);
                if (delivery == null) {
                    return null;
                }
                MessagePropertiesConverter mpc = new DefaultMessagePropertiesConverter();
                MessageProperties mp = mpc.toMessageProperties(delivery.getProperties(), delivery.getEnvelope(), "");
                return new Message(delivery.getBody(),mp);       
            }
        });
    }
    
    
    public void acknowledgeConsume(Message m) {
        final long tag = m.getMessageProperties().getDeliveryTag();
        this.template.execute(new ChannelCallback<Boolean>() {

            @Override
            public Boolean doInRabbit(Channel channel) throws Exception {
                channel.basicAck(tag, false);
                return true;
            }   
        });
        
    }
    
    // copied from RabbitTemplate  https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/core/RabbitTemplate.java     
    // to gain control over acknowledgements
    private Delivery consumeDelivery(Channel channel, String queueName, long timeoutMillis) throws Exception {
        Delivery delivery = null;
//        Throwable exception = null;
//        CompletableFuture<Delivery> future = new CompletableFuture<>();
//        DefaultConsumer consumer = createConsumer(queueName, channel, future,
//                timeoutMillis < 0 ? DEFAULT_CONSUME_TIMEOUT : timeoutMillis);
//        try {
//            if (timeoutMillis < 0) {
//                delivery = future.get();
//            }
//            else {
//                delivery = future.get(timeoutMillis, TimeUnit.MILLISECONDS);
//            }
//        }
//        catch (ExecutionException e) {
//            logger.error("Consumer failed to receive message: " + consumer, e.getCause());
//            exception = e.getCause();
//        }
//        catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        catch (TimeoutException e) {
//            // no result in time
//        }
//        try {
//            if (exception == null || !(exception instanceof ConsumerCancelledException)) {
//                channel.basicCancel(consumer.getConsumerTag());
//            }
//        }
//        catch (Exception e) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("Failed to cancel consumer: " + consumer, e);
//            }
//        }
//        if (exception != null) {
//            if (exception instanceof RuntimeException) {
//                throw (RuntimeException) exception;
//            }
//            else if (exception instanceof Error) {
//                throw (Error) exception;
//            }
//            else  {
//                throw new AmqpException(exception);
//            }
//        }
        return delivery;
    }
    
    // copied from RabbitAccessor https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/connection/RabbitAccessor.java
//    private DefaultConsumer createConsumer(final String queueName, Channel channel,
//           final CompletableFuture<Delivery> future, long timeoutMillis) throws Exception {
//        channel.basicQos(1);
//        final CountDownLatch latch = new CountDownLatch(1);
//        DefaultConsumer consumer = new TemplateConsumer(channel) {
//
//            @Override
//            public void handleCancel(String consumerTag) throws IOException {
//                future.completeExceptionally(new ConsumerCancelledException());
//            }
//
//            @Override
//            public void handleConsumeOk(String consumerTag) {
//                super.handleConsumeOk(consumerTag);
//                latch.countDown();
//            }
//
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
//                    throws IOException {
//                future.complete(new Delivery(consumerTag, envelope, properties, body));
//            }
//
//        };
//        channel.basicConsume(queueName, consumer);
//        if (!latch.await(timeoutMillis, TimeUnit.MILLISECONDS)) {
//            if (channel instanceof ChannelProxy) {
//                ((ChannelProxy) channel).getTargetChannel().close();
//            }
//            future.completeExceptionally(
//                    new AmqpException("Blocking receive, consumer failed to consume: " + consumer));
//        }
//        return consumer;
//    }
    
    /**
     * Adds {@link #toString()} to the {@link DefaultConsumer}.
     * @since 2.0
     */
    protected static abstract class TemplateConsumer extends DefaultConsumer {

        public TemplateConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public String toString() {
            return "TemplateConsumer [channel=" + this.getChannel() + ", consumerTag=" + this.getConsumerTag() + "]";
        }

    }
        
   
            
            
    
//    public void registerAsynchronousMessageListener(MessageListener listener) {
//        this.messageListener = listener;
//        
//    }
//        
//            ChannelAwareMessageListener consumer  = new ChannelAwareMessageListener() {
//
//                @Override
//                public void onMessage(Message message, Channel channel)
//                        throws Exception {
//                    
//                    if (preAcknowledgeHandler(message)) {
//                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//                    }
//                    postAcknowledgeHandler(message);
//                }
//                
//            };
//            
//    
//    public boolean preAcknowledgeHandler(Message message) {
//        return true;
//    }
//    
//    public boolean postAcknowledgeHandler(Message message) {
//        return true;
//    }
   
}
