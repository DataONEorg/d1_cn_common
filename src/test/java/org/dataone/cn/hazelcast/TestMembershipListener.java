package org.dataone.cn.hazelcast;

import org.dataone.cn.hazelcast.membership.BaseHazelcastMembershipListener;

import com.hazelcast.core.HazelcastInstance;

public class TestMembershipListener extends BaseHazelcastMembershipListener {

    public TestMembershipListener(HazelcastInstance instance) {
        super(instance);
    }

    public TestMembershipListener(HazelcastInstance instance, String configLocation) {
        super(instance, configLocation);
    }

    @Override
    public void handleMemberAddedEvent() {
    }

    @Override
    public void handleMemberRemovedEvent() {
    }

}
