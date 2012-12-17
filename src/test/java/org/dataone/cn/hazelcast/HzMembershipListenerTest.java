/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dataone.cn.hazelcast;

import static junit.framework.Assert.assertEquals;

import java.io.Serializable;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import org.dataone.cn.hazelcast.membership.BaseHazelcastMembershipListener;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hazelcast.cluster.AddOrRemoveConnection;
import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.core.Member;
import com.hazelcast.core.MultiTask;
import com.hazelcast.impl.FactoryImpl;
import com.hazelcast.impl.GroupProperties;
import com.hazelcast.impl.Node;
import com.hazelcast.impl.TestUtil;

/**
 * Sets up 3 node cluster and interrupts communication of various nodes.
 * Resulting cluster topology / partition is observed with HzMembershipListener.
 * Cluster is observed to repair once communication is repaired.
 * 
 * Based on unit testing strategies employed by hazelcast.
 * 
 * @author sroseboo
 * 
 */
public class HzMembershipListenerTest {

    @BeforeClass
    public static void init() throws Exception {
        Hazelcast.shutdownAll();
    }

    @After
    public void cleanup() throws Exception {
        Hazelcast.shutdownAll();
    }

    @Test
    public void testPartitionAndMergeWithLocksSingleThread() throws Exception {
        List<String> allMembers = Arrays
                .asList("127.0.0.1:35701, 127.0.0.1:35702, 127.0.0.1:35703");

        final HazelcastInstance h1 = buildInstance(35701, allMembers);
        final HazelcastInstance h2 = buildInstance(35702, allMembers);
        final HazelcastInstance h3 = buildInstance(35703, allMembers);

        // All three nodes join into one cluster
        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());

        final AtomicBoolean doingWork = new AtomicBoolean(true);
        Thread[] workThreads = setupWork(h1, h2, h3, doingWork);

        final Node n1 = TestUtil.getNode(h1);
        final Node n2 = TestUtil.getNode(h2);
        final Node n3 = TestUtil.getNode(h3);

        doingWork.set(false);
        for (Thread t : workThreads) {
            t.join();
        }

        // create communication outage and wait for partition to take effect
        // ////// symmetric loss of master - h1 leaves the cluster
        closeConnectionBetween(h1, h3);
        closeConnectionBetween(h1, h2);
        // create lock in each partition for same value
        ILock lock1 = h1.getLock("DEADLOCK");
        lock1.lock();
        ILock lock2 = h2.getLock("DEADLOCK");
        lock2.lock();

        // allow partitions to merge - see whats going on with the lock.
        Thread.sleep(1000 * 20);
        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());
        assertEquals(true, lock1.isLocked());
        assertEquals(true, lock2.isLocked());
        // appears master node gets the lock so trying to unlock 2 first
        // will fail.
        Exception illegalStateException = null;
        try {
            lock2.unlock();
        } catch (IllegalMonitorStateException imse) {
            imse.printStackTrace();
            illegalStateException = imse;
        }
        Assert.assertNotNull(illegalStateException);
        lock1.unlock();
        assertEquals(false, lock1.isLocked());
        assertEquals(false, lock2.isLocked());
    }

    @Test
    public void testPartitionAndMergeWithLockMultipleThreads() throws Exception {

        List<String> allMembers = Arrays
                .asList("127.0.0.1:35701, 127.0.0.1:35702, 127.0.0.1:35703");

        final HazelcastInstance h1 = buildInstance(35701, allMembers);
        final HazelcastInstance h2 = buildInstance(35702, allMembers);
        final HazelcastInstance h3 = buildInstance(35703, allMembers);

        // All three nodes join into one cluster
        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());

        final AtomicBoolean doingWork = new AtomicBoolean(true);
        Thread[] workThreads = setupWork(h1, h2, h3, doingWork);

        final Node n1 = TestUtil.getNode(h1);
        final Node n2 = TestUtil.getNode(h2);
        final Node n3 = TestUtil.getNode(h3);

        doingWork.set(false);
        for (Thread t : workThreads) {
            t.join();
        }

        // create communication outage and wait for partition to take effect
        // ////// symmetric loss of master - h1 leaves the cluster
        closeConnectionBetween(h1, h3);
        closeConnectionBetween(h1, h2);

        Thread lockThread1 = getLockThread(h1);
        Thread lockThread2 = getLockThread(h2);
        lockThread1.setUncaughtExceptionHandler(new TestUncaughtExceptionHandler());
        lockThread2.setUncaughtExceptionHandler(new TestUncaughtExceptionHandler());
        lockThread1.start();
        lockThread2.start();

        // allow partitions to merge - see what's going on with the lock.
        Thread.sleep(1000 * 20);
        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());

        lockThread1.join();
        lockThread2.join();
    }

    private class TestUncaughtExceptionHandler implements UncaughtExceptionHandler {

        public void uncaughtException(Thread t, Throwable e) {
            synchronized (this) {
                System.err.println("Uncaught exception in thread '" + t.getName() + "': "
                        + e.getMessage());
                Assert.fail();
            }
        }
    }

    @Test
    public void testSymmetricLossMasterNode() throws Exception {
        List<String> allMembers = Arrays
                .asList("127.0.0.1:35701, 127.0.0.1:35702, 127.0.0.1:35703");

        final HazelcastInstance h1 = buildInstance(35701, allMembers);
        final HazelcastInstance h2 = buildInstance(35702, allMembers);
        final HazelcastInstance h3 = buildInstance(35703, allMembers);

        // All three nodes join into one cluster
        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());

        final AtomicBoolean doingWork = new AtomicBoolean(true);
        Thread[] workThreads = setupWork(h1, h2, h3, doingWork);

        final Node n1 = TestUtil.getNode(h1);
        final Node n2 = TestUtil.getNode(h2);
        final Node n3 = TestUtil.getNode(h3);

        // symmetric loss of master - h1 leaves the cluster
        closeConnectionBetween(h1, h3);
        closeConnectionBetween(h1, h2);

        // symmetric loss of h1 - all nodes loose communication.
        assertEquals(1, n1.clusterManager.getMembers().size());
        assertEquals(1, n2.clusterManager.getMembers().size());
        assertEquals(2, n3.clusterManager.getMembers().size());
        // when the master node(n1) looses n2, n3 eventually also looses n2.
        Thread.sleep(1000);
        assertEquals(1, n3.clusterManager.getMembers().size());

        Thread.sleep(25 * 1000);

        assertEquals(3, n3.clusterManager.getMembers().size());
        assertEquals(3, n2.clusterManager.getMembers().size());
        assertEquals(3, n1.clusterManager.getMembers().size());

        doingWork.set(false);
        for (Thread t : workThreads) {
            t.join();
        }

        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());
    }

    @Test
    public void testAsymmestricLostMasterNode() throws Exception {
        List<String> allMembers = Arrays
                .asList("127.0.0.1:35701, 127.0.0.1:35702, 127.0.0.1:35703");

        final HazelcastInstance h1 = buildInstance(35701, allMembers);
        final HazelcastInstance h2 = buildInstance(35702, allMembers);
        final HazelcastInstance h3 = buildInstance(35703, allMembers);

        // All three nodes join into one cluster
        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());

        final AtomicBoolean doingWork = new AtomicBoolean(true);
        Thread[] workThreads = setupWork(h1, h2, h3, doingWork);

        final Node n1 = TestUtil.getNode(h1);
        final Node n2 = TestUtil.getNode(h2);
        final Node n3 = TestUtil.getNode(h3);

        // asymmetric loss of master - h1 and h2 loose communication.
        closeConnectionBetween(h1, h2);

        // asymmetric lost of h1 - even n3 loses n2
        assertEquals(2, n1.clusterManager.getMembers().size());
        assertEquals(2, n2.clusterManager.getMembers().size());
        assertEquals(2, n3.clusterManager.getMembers().size());

        Thread.sleep(20 * 1000);

        assertEquals(3, n3.clusterManager.getMembers().size());
        assertEquals(3, n2.clusterManager.getMembers().size());
        assertEquals(3, n1.clusterManager.getMembers().size());

        doingWork.set(false);
        for (Thread t : workThreads) {
            t.join();
        }

        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());
    }

    @Test
    public void testSymmetricLossSlaveNode() throws Exception {
        List<String> allMembers = Arrays
                .asList("127.0.0.1:35701, 127.0.0.1:35702, 127.0.0.1:35703");

        final HazelcastInstance h1 = buildInstance(35701, allMembers);
        final HazelcastInstance h2 = buildInstance(35702, allMembers);
        final HazelcastInstance h3 = buildInstance(35703, allMembers);

        // All three nodes join into one cluster
        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());

        final AtomicBoolean doingWork = new AtomicBoolean(true);
        Thread[] workThreads = setupWork(h1, h2, h3, doingWork);

        final Node n1 = TestUtil.getNode(h1);
        final Node n2 = TestUtil.getNode(h2);
        final Node n3 = TestUtil.getNode(h3);

        // symmetric loss of non-master - h3 leaves the cluster
        closeConnectionBetween(h2, h3);
        closeConnectionBetween(h1, h3);

        // symmetric lost of h3
        assertEquals(2, n1.clusterManager.getMembers().size());
        assertEquals(2, n2.clusterManager.getMembers().size());
        assertEquals(1, n3.clusterManager.getMembers().size());

        Thread.sleep(20 * 1000);

        assertEquals(3, n3.clusterManager.getMembers().size());
        assertEquals(3, n2.clusterManager.getMembers().size());
        assertEquals(3, n1.clusterManager.getMembers().size());

        doingWork.set(false);
        for (Thread t : workThreads) {
            t.join();
        }

        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());
    }

    @Test
    public void testAsymmetricLossSlaveNode() throws Exception {
        List<String> allMembers = Arrays
                .asList("127.0.0.1:35701, 127.0.0.1:35702, 127.0.0.1:35703");

        final HazelcastInstance h1 = buildInstance(35701, allMembers);
        final HazelcastInstance h2 = buildInstance(35702, allMembers);
        final HazelcastInstance h3 = buildInstance(35703, allMembers);

        // All three nodes join into one cluster
        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());

        final AtomicBoolean doingWork = new AtomicBoolean(true);
        Thread[] workThreads = setupWork(h1, h2, h3, doingWork);

        final Node n1 = TestUtil.getNode(h1);
        final Node n2 = TestUtil.getNode(h2);
        final Node n3 = TestUtil.getNode(h3);

        // asymmetric loss of non-master - h3 and h2 loose communication
        closeConnectionBetween(h2, h3);

        // asymmetric loss of non-master - h1 uneffected.
        assertEquals(3, n1.clusterManager.getMembers().size());
        assertEquals(2, n2.clusterManager.getMembers().size());
        assertEquals(2, n3.clusterManager.getMembers().size());

        Thread.sleep(20 * 1000);

        assertEquals(3, n3.clusterManager.getMembers().size());
        assertEquals(3, n2.clusterManager.getMembers().size());
        assertEquals(3, n1.clusterManager.getMembers().size());

        doingWork.set(false);
        for (Thread t : workThreads) {
            t.join();
        }

        assertEquals(3, h1.getCluster().getMembers().size());
        assertEquals(3, h2.getCluster().getMembers().size());
        assertEquals(3, h3.getCluster().getMembers().size());
    }

    private HazelcastInstance buildInstance(int port, List<String> allMembers) {
        Config c1 = buildConfig(false);
        c1.getNetworkConfig().setPort(port);
        c1.getNetworkConfig().getJoin().getTcpIpConfig().setMembers(allMembers);
        final HazelcastInstance h1 = Hazelcast.newHazelcastInstance(c1);
        BaseHazelcastMembershipListener ml1 = new TestMembershipListener(h1);
        ml1.startListener();
        return h1;
    }

    private Thread[] setupWork(final HazelcastInstance h1, final HazelcastInstance h2,
            final HazelcastInstance h3, final AtomicBoolean doingWork) {

        // This simulates each node reading from the other nodes in the list at
        // regular intervals
        // This prevents the heart beat code from timing out

        final HazelcastInstance[] instances = new HazelcastInstance[] { h1, h2, h3 };
        Thread[] workThreads = new Thread[instances.length];
        for (int i = 0; i < instances.length; i++) {
            final int threadNum = i;
            workThreads[threadNum] = new Thread(new Runnable() {

                public void run() {
                    while (doingWork.get()) {
                        final HazelcastInstance hz = instances[threadNum];

                        Set<Member> members = new HashSet<Member>(hz.getCluster().getMembers());
                        members.remove(hz.getCluster().getLocalMember());

                        MultiTask<String> task = new MultiTask<String>(new PingCallable(), members);
                        hz.getExecutorService().execute(task);

                        try {
                            task.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            workThreads[threadNum].start();
        }
        return workThreads;
    }

    private Thread getLockThread(HazelcastInstance hazelcast) {
        final HazelcastInstance hz = hazelcast;
        Thread lockThread = new Thread(new Runnable() {
            public void run() {
                System.out.println("thread running");
                ILock lock = hz.getLock("DEADLOCK");
                System.out.println("locking");
                lock.lock();
                System.out.println("locked");
                try {
                    Thread.sleep(1000 * 25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("unlocking");
                lock.unlock();
                System.out.println("unlocked");
            }
        });
        return lockThread;
    }

    private static class PingCallable implements Callable<String>, Serializable {
        public String call() throws Exception {
            return "ping response";
        }
    }

    private static Config buildConfig(boolean multicastEnabled) {
        Config c = new Config();
        c.getGroupConfig().setName("group").setPassword("pass");
        c.setProperty(GroupProperties.PROP_MERGE_FIRST_RUN_DELAY_SECONDS, "10");
        c.setProperty(GroupProperties.PROP_MERGE_NEXT_RUN_DELAY_SECONDS, "5");
        c.setProperty(GroupProperties.PROP_MAX_NO_HEARTBEAT_SECONDS, "10");
        c.setProperty(GroupProperties.PROP_MASTER_CONFIRMATION_INTERVAL_SECONDS, "2");
        c.setProperty(GroupProperties.PROP_MAX_NO_MASTER_CONFIRMATION_SECONDS, "10");
        c.setProperty(GroupProperties.PROP_MEMBER_LIST_PUBLISH_INTERVAL_SECONDS, "10");
        final NetworkConfig networkConfig = c.getNetworkConfig();
        networkConfig.getJoin().getMulticastConfig().setEnabled(multicastEnabled);
        networkConfig.getJoin().getTcpIpConfig().setEnabled(!multicastEnabled);
        networkConfig.setPortAutoIncrement(false);
        return c;
    }

    private void closeConnectionBetween(HazelcastInstance h1, HazelcastInstance h2) {
        if (h1 == null || h2 == null) {
            return;
        }

        final FactoryImpl f1 = (FactoryImpl) ((FactoryImpl.HazelcastInstanceProxy) h1)
                .getHazelcastInstance();
        final FactoryImpl f2 = (FactoryImpl) ((FactoryImpl.HazelcastInstanceProxy) h2)
                .getHazelcastInstance();

        AddOrRemoveConnection addOrRemoveConnection1 = new AddOrRemoveConnection(
                f2.node.getThisAddress(), false);
        addOrRemoveConnection1.setNode(f1.node);

        AddOrRemoveConnection addOrRemoveConnection2 = new AddOrRemoveConnection(
                f1.node.getThisAddress(), false);
        addOrRemoveConnection2.setNode(f2.node);

        f1.node.clusterManager.enqueueAndWait(addOrRemoveConnection1, 5);
        f2.node.clusterManager.enqueueAndWait(addOrRemoveConnection2, 5);
    }
}
