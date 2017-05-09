package org.dataone.cn.messaging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


/**
 * These are local integration tests that will pass if there's a rabbitmq broker running 
 * on the local machine, and using the default configuration (guest:guest)
 *  
 * @author rnahf
 *
 */
public class QueueAccessIT {

    private static Logger logger = Logger.getLogger(QueueAccessIT.class.getName());
    
    static String testQueueName = "brokerTestQueue";
   
    CachingConnectionFactory connFactory = new CachingConnectionFactory("localhost");
    RabbitTemplate rabbitTemplate;
    

    @Before
    public void setUp() throws Exception  {
       
        connFactory.setUsername("guest");
        connFactory.setPassword("guest");
        connFactory.setPublisherConfirms(true);
        rabbitTemplate = new RabbitTemplate(connFactory);
        
//        try {  
//            logger.info("****** declaring a durable queue for use in tests");
//
//            final RabbitAdmin admin = new RabbitAdmin(connFactory);
//            final Queue queue = new Queue(testQueueName, true);
//            admin.declareQueue(queue);
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//            throw new Exception("Failed to (re)declare durable queue during test setup, due to: " + e.getClass().getCanonicalName() + ": " + e.getMessage());
//        }
//        
//        Message m = null;
//        do {
//            m = rabbitTemplate.receive(testQueueName, 10);
//            logger.info("cleaned one item off the queue...");
//        } while (m != null); 
//        logger.info("******* end of test setup...");
    }

    
    
    
    
    @Ignore("It doesn't look like an exceptionp throwing situation")
    @Test 
    public void testConfirmedPublishToNonExistingQueue_ShouldThrowAMQPException() {

        try {            
            rabbitTemplate.convertAndSend("aNonExistingQueue", "foo");
            fail("publishing to a non-existing queue should throw exception");
        } catch (AmqpException e) {
            logger.info(e.getClass().getCanonicalName() + ": " + e.getMessage());
        }
    }
    
    @Ignore
    @Test
    public void testConsumeFromNonExistingQueue_ShouldThrowAMQUException() {
        try { 
            Object o = rabbitTemplate.receiveAndConvert("aNonExistingQueue",500);
            fail("Should not successfully retrieve from non existing queue");
        }
        catch (AmqpException e) {
            logger.info(e.getClass().getCanonicalName() + ": " + e.getMessage());
        }
    }
 
    
    void stopBroker() {
        logger.warn("*#*#*#*#*#*#*#*#*#*    PLEASE STOP BROKER while test sleeps for 15 seconds     *#*#*#*#*#*#*#*#*#*");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            logger.warn("OK, moving on...");
        }
//        try {
//            Process p = Runtime.getRuntime().exec("/usr/local/Cellar/rabbitmq/3.6.9/sbin/rabbitmqctl stop");
//            String stdout = IOUtils.toString(p.getInputStream());
//            String stderr = IOUtils.toString(p.getErrorStream());
//            System.out.println("stdout: " + stdout);
//            System.out.println("stderr: " + stderr);
//            
//        } catch (IOException e) {
//            logger.warn("****** FAILED to stop broker ********",e);
//        }
//        logger.info("****** broker STOPPED ********");
    }
    
    void startBroker() {
        logger.warn("*#*#*#*#*#*#*#*#*#*    PLEASE RESTART BROKER while test sleeps for 15 seconds    *#*#*#*#*#*#*#*#*#*");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            logger.warn("OK, moving on...");
        }
        
//        try {
//            Runtime.getRuntime().exec("/usr/local/Cellar/rabbitmq/3.6.9/sbin/rabbitmqctl wait");
//        } catch (IOException e) {
//            logger.warn("****** FAILED to start broker ********",e);
//        }
//        logger.info("****** broker STARTED ********");
    }
    
    @Ignore
    @Test
    public void testMessageDurability_messageShouldSurviveBrokerRestart() {
        String messageOne = "testBrokerStop1";
        String messageTwo = "testBrokerStop2";
        
        Message m = null;
        
        
        rabbitTemplate.convertAndSend(testQueueName, messageOne);
        String message = (String)rabbitTemplate.receiveAndConvert(testQueueName,250);
        logger.info("   =======> received firstMessage, so broker is up and running: " + message);
        rabbitTemplate.convertAndSend(testQueueName, messageTwo);
        stopBroker();
        try {
            String ms = (String) rabbitTemplate.receiveAndConvert(testQueueName, 250);
            fail("Should not be able to get a message, so the stop probably didn't happen. " + ms);
        } catch (AmqpException e) {
            logger.info("Broker down, as planned... :" + e.getClass().getCanonicalName());
        }
        startBroker();
        String secondMessage = (String) rabbitTemplate.receiveAndConvert(testQueueName,2000);
        assertEquals("Message should have survived broker restart",messageTwo,secondMessage);
    }
    
    
    @Ignore
    @Test
    public void testQueueDurability_queueDeclaredAsDurableShouldSurviveBrokerRestart() throws InterruptedException {
     
        String newQueue = "aNonDurableQueue";
        RabbitAdmin admin = new RabbitAdmin(connFactory);
        Queue queue = new Queue(newQueue, true);
        admin.declareQueue(queue);
        
        stopBroker();

        startBroker();
        
        
        // test that the queue is there by publishing to it.
        rabbitTemplate.convertAndSend(newQueue, "a test message that could have been anything");
        
    }
    
    @Ignore
    @Test
    public void testDelayedConsumeAcknowledgement() throws InterruptedException {
        
        String simpleMessage1 = "simpleMessage1";
        // publish the simple message
        rabbitTemplate.convertAndSend(testQueueName,simpleMessage1);

        logger.warn("Please check broker for message count. should be 1");
        Thread.sleep(8 * 1000);


        // use QueueAccess for consumption
        QueueAccess qa = new QueueAccess(this.connFactory, testQueueName);
        Message consumed = qa.consumeNextNoAck(200);

        logger.warn("Please check broker for message count. should be 1");
        Thread.sleep(8 * 1000);

        qa.acknowledgeConsume(consumed);

        logger.warn("Please check broker for message count. should be 0");
        Thread.sleep(8 * 1000);


    }
    
    @Ignore
    @Test
    public void examplePipeline() throws InterruptedException {

        long pause = 1000;
        
        RabbitAdmin admin = new RabbitAdmin(connFactory);
        final Queue queue1 = new Queue("pipe1", false);
        admin.declareQueue(queue1);
        
        final Queue queue2 = new Queue("pipe2", false);
        admin.declareQueue(queue2);
        
        final Queue queue3 = new Queue("pipe3", false);
        admin.declareQueue(queue3);
        
        final Queue queue4 = new Queue("pipe4", false);
        admin.declareQueue(queue4);
        
        
        String simpleMessage1 = "demoMessage";
        rabbitTemplate.convertAndSend(testQueueName,simpleMessage1);

        

        processStep(   testQueueName,queue1.getName());
        processStep(queue1.getName(),queue2.getName());
        processStep(queue2.getName(),queue3.getName());
        processStep(queue3.getName(),queue4.getName());

    }
    
    
    private void processStep(String startingQ, String endingQ) throws InterruptedException {
        
        logger.warn("PAUSING:  please list_queues");
        Thread.sleep(8000);
        
        QueueAccess starting = new QueueAccess(this.connFactory,startingQ);
        Message consumed = starting.consumeNextNoAck(200);
        logger.info(String.format("Got message from queue %s: %s", startingQ, consumed.getBody().toString()));
        QueueAccess ending = new QueueAccess(this.connFactory,endingQ);
        ending.publish(consumed);
        starting.acknowledgeConsume(consumed);
    }
        

    
    
    
    @Ignore
    @Test 
    public void simpleroundTripTest() throws Exception {
      
        
        logger.info("********** running the test");
        Thread.sleep(2000);
        CachingConnectionFactory cf = new CachingConnectionFactory("localhost");
        cf.setPassword("guest");
        cf.setUsername("guest");
        cf.setPublisherConfirms(true);
        
        
        final String messageIn = "hello world message!";
        
        final RabbitTemplate template = new RabbitTemplate(cf);
        Message m = MessageBuilder.withBody(messageIn.getBytes())
            .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
            .build();

        
        template.convertAndSend(testQueueName, messageIn);
//        template.send(testQueueName, m);

        Thread.sleep(6000);
        
        logger.info("************ message sent / trying to retrieve...");
        String messageOut = (String) template.receiveAndConvert(testQueueName, 10000L);      
        logger.info("************* message received");      
        assertEquals("Message in should match message out.", messageIn,messageOut);
        
        logger.info("************* done.");
    }
    
    @Test public void testPlaceHolderUntilCanGetMockBroker() {
        ;
    }
    

}
