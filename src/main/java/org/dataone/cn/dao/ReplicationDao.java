package org.dataone.cn.dao;

import java.util.Date;

import org.dataone.service.types.v1.Identifier;
import org.springframework.beans.support.PagedListHolder;

/**
 * Abstract definition of a replication data access object used to identify
 * replicas that need to be audited and replicas that are pending/queued.
 * 
 * @author sroseboo
 * 
 */
public interface ReplicationDao {

    public PagedListHolder<Identifier> getReplicasByDate(Date auditDate, int pageSize,
            int pageNumber);

    public PagedListHolder<Identifier> getFailedReplicas(int pageSize, int pageNumber);

    public PagedListHolder<Identifier> getInvalidReplicas(int pageSize, int pageNumber);

    public PagedListHolder<Identifier> getStaleQueuedRelicas(int pageSize, int pageNumber);

}
