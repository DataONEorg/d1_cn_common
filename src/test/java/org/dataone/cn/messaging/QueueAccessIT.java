package org.dataone.cn.messaging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageListener;
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

    @BeforeClass
    public static void beforeClassSetup() throws Exception {
        try {  
            logger.info("****** declaring a durable queue for use in tests");
            RabbitAdmin channel = new RabbitAdmin(new CachingConnectionFactory("localhost"));
            Queue queue = new Queue(testQueueName, true);
            channel.declareQueue(queue);
        } catch (Throwable e) {
            throw new Exception("Check that broker is started - Failed to (re)declare durable queue during test setup, due to: " 
                    + e.getClass().getCanonicalName() + ": " + e.getMessage(), e);
        }

    }

    @Before
    public void setUp() throws Exception  {

        connFactory.setUsername("guest");
        connFactory.setPassword("guest");
        connFactory.setPublisherConfirms(true);
        rabbitTemplate = new RabbitTemplate(connFactory);


        Message m = null;
        do {
            m = rabbitTemplate.receive(testQueueName, 10);
            logger.info("cleaned one item off the queue...");
        } while (m != null); 
        logger.info("******* end of test setup...");
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


    //   @Ignore
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


    @Ignore ("Can't automate broker stop yet")
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


    @Ignore ("Can't automate broker stop yet")
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

    @Test
    public void testDelayedConsumeAcknowledgement() throws InterruptedException {


        String simpleMessage1 = "simpleMessage1";
        // publish the simple message
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage1");
 
        logger.warn("*#*#*#*#*#*#*#*#*#*   Please check broker for message count. should be 1");
        Thread.sleep(4 * 1000);


        // use QueueAccess for consumption
        QueueAccess qa = new QueueAccess(this.connFactory, testQueueName);
        qa.registerAsynchronousMessageListener(1, new MessageListener() {

            @Override
            public void onMessage(Message message) {
                String simple = new String(message.getBody());
                System.out.println(String.format("Thread: %s  -  date:  %s  :::::: %s", 
                        Thread.currentThread().getName(), new Date(), simple ));
                try {   
                    System.out.println(String.format("Thread: %s  -  date:  %s  :::::: %s", 
                            Thread.currentThread().getName(), new Date(), "CHECK BROKER FOR MESSAGE COUNT, SHOULD STILL BE 1"));
                    Thread.sleep(4000);

                } catch (InterruptedException e) {
                    throw new RuntimeException("a runtime execption");
                }   
            }
        });
        
        logger.info("slept 8sec,...Going to sleep another 10 to match the onMessage execution pause...");
        Thread.sleep(10500); // sleep in parallel with onMessage
        logger.warn("*#*#*#*#*#*#*#*#*#*   Please check broker for message count. should now be 0");
        Thread.sleep(3000);
        qa.clearAsynchronousMessageListener();
    }


    
    @Test
    public void testParallelConsumption_TimingShouldBeLikeOneMessage() throws InterruptedException {

        // push a bunch of messages on the queue
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage1");
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage2");
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage3");
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage4");
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage5");
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage6");
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage7");
        rabbitTemplate.convertAndSend(testQueueName,"simpleMessage8");

        logger.warn("*#*#*#*#*#*#*#*#*#*   Please check broker for message count. should be 8");
        Thread.sleep(5 * 1000);

        long start = System.currentTimeMillis();
        final Map<String,Long> threadTimings = new HashMap<>();
        final long threadSleepTime = 4000;
        final long overheadTime = 500;
        
        // use QueueAccess for consumption
        QueueAccess qa = new QueueAccess(this.connFactory, testQueueName);
        qa.registerAsynchronousMessageListener(8, new MessageListener() {

            @Override
            public void onMessage(Message message) {
                String simple = new String(message.getBody());
                System.out.println(String.format("Thread: %s  -  date:  %s  :::::: %s", 
                        Thread.currentThread().getName(), new Date(), simple ));
                try {   
                    System.out.println(String.format("Thread: %s  -  date:  %s  :::::: %s", 
                            Thread.currentThread().getName(), new Date(), "CHECK BROKER FOR MESSAGE COUNT, SHOULD STILL BE 8"));
                    Thread.sleep(threadSleepTime);
                    threadTimings.put(Thread.currentThread().getName(), System.currentTimeMillis());
                } catch (InterruptedException e) {
                    throw new RuntimeException("a runtime execption");
                }
            }
        });
        logger.info("...Going to sleep while messages are consumed...");
        
        Thread.sleep(threadSleepTime + overheadTime); // sleep in parallel with onMessage with a little more
        assertEquals("all threads should have reported back", 8, threadTimings.size());
        Long latest = 0L;
        for (String thr : threadTimings.keySet()) { 
            if (threadTimings.get(thr) > latest) {
                latest = threadTimings.get(thr);
            }
        }
        assertTrue("latest should be greater than start", latest > start);
        logger.info("Total time:  " + String.valueOf(latest - start));
        assertTrue("Total time to process should be less than or equal to size of one task sleep (plus small amount)",
                latest - start < 4000 + overheadTime);
        
        qa.clearAsynchronousMessageListener();
    }


    @Test
    public void exampleSynchronousConsumer_Pipeline() throws InterruptedException {

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

        logger.warn("*#*#*#*#*#*#*#*#*#*   PAUSING:  please list_queues");
        Thread.sleep(8000);

        QueueAccess starting = new QueueAccess(this.connFactory,startingQ);
        Message consumed = starting.getNextDelivery(2000);
        String messageBody = "null";
        if (consumed != null && consumed.getBody() != null) {
            messageBody = new String(consumed.getBody());
        }
        logger.info(String.format("Got message from queue %s: %s", startingQ, messageBody));
        QueueAccess ending = new QueueAccess(this.connFactory,endingQ);
        ending.publish(consumed);
        //        starting.acknowledgeConsume(consumed);
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
