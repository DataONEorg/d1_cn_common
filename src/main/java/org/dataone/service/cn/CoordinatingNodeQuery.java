package org.dataone.service.cn;

import java.util.Date;
import java.util.List;

import javax.management.Query;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.IdentifierType;
import org.dataone.service.types.LogRecordSet;

/**
 * The DataONE CoordinatingNode CRUD programmatic interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CoordinatingNodeQuery 
{
    public List<IdentifierType> search(AuthToken token, Query query)
        throws NotAuthorized, InvalidRequest;
    public LogRecordSet getLogRecords(AuthToken token, 
            Date fromDate, Date toDate)
        throws NotAuthorized, InvalidRequest;
}
