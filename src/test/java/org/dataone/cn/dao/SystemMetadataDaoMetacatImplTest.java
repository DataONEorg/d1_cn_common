/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright 2014
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

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * A class to test /select/insert/update operations on the pertinent Metacat system metadata tables
 * 
 * @author cjones
 *
 */
public class SystemMetadataDaoMetacatImplTest {
    
	private JdbcTemplate jdbc = new JdbcTemplate(DataSourceFactory.getMetacatDataSource());

    private SystemMetadataDao systemMetadataDao = DaoFactory.getSystemMetadataDao();

    /**
     * Create tables before testing
     */
    @Before
    public void createTables() {
        SystemMetadataDaoMetacatImplTestUtil.createTables(jdbc);
        
    }

    /**
     * Drop tables after testing
     */
    @After
    public void dropTables() {
    	SystemMetadataDaoMetacatImplTestUtil.dropTables(jdbc);
    	
    }

    @Test
    public void testInit() {
    	Assert.assertTrue(1 == 1);
    }
}
