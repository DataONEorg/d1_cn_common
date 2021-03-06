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
package org.dataone.cn.dao;

/**
 * Factory class to provide consumers handle on data access object (dao pattern)
 * instances. DaoFactory returns Dao interfaces while encapsulating the concrete
 * implementation class and details.
 * 
 * @author sroseboo
 * 
 */
public class DaoFactory {

    private static ReplicationDao replicationDao;
    private static SystemMetadataDao systemMetadataDao;

    private DaoFactory() {
    }

    /**
     * Returns a concrete instance of the ReplicationDao interface.
     * 
     * @return ReplicationDao
     */
    public static final ReplicationDao getReplicationDao() {
        if (replicationDao == null) {
            replicationDao = new ReplicationDaoMetacatImpl();
        }
        return replicationDao;
    }
    
    /**
     * Returns a concrete instance of the SystemMetadataDao interface.
     * 
     * @return SystemMetadataDao
     */
    public static final SystemMetadataDao getSystemMetadataDao() {
        if (systemMetadataDao == null) {
        	systemMetadataDao = new SystemMetadataDaoMetacatImpl();
        }
        return systemMetadataDao;
    }

}
