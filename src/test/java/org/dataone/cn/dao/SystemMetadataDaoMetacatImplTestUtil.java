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

import org.dataone.configuration.Settings;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * A utility class to ensure that the Metacat data tables are in place for testing
 * 
 * @author cjones
 */
public class SystemMetadataDaoMetacatImplTestUtil {

	// provide the CN router id for insert statements
    public static final String cnNodeId = Settings.getConfiguration().getString("cn.router.nodeId",
            "urn:node:cnDev");

	/**
	 * Create the Metacat tables of interest to testing
	 * 
	 * @param jdbc  the JdbcTemplate used to execute the statements
	 */
	public static void createTables(JdbcTemplate jdbc) {
		
		// identifier table
		jdbc.execute(
			"CREATE TABLE IF NOT EXISTS identifier        " +
			"(                                            " +
			"  guid text NOT NULL,                        " +
			"  docid character varying(250),              " +
			"  rev bigint,                                " +
			"  CONSTRAINT identifier_pk PRIMARY KEY (guid)" +
			");	                                          ");
		
		// systemmetadata table
		jdbc.execute(
			"CREATE TABLE IF NOT EXISTS systemmetadata          " +
			"(                                                  " +
			"  guid text NOT NULL,                              " +
			"  serial_version character varying(256),           " +
			"  date_uploaded timestamp,                         " +
			"  rights_holder character varying(250),            " +
			"  checksum character varying(512),                 " +
			"  checksum_algorithm character varying(250),       " +
			"  origin_member_node character varying(250),       " +
			"  authoritive_member_node character varying(250),  " +
			"  date_modified timestamp,					        " +
			"  submitter character varying(256),                " +
			"  object_format character varying(256),            " +
			"  size character varying(256),                     " +
			"  archived boolean,                                " +
			"  replication_allowed boolean,                     " +
			"  number_replicas bigint,                          " +
			"  obsoletes text,                                  " +
			"  obsoleted_by text,                               " +
			"  CONSTRAINT systemmetadata_pk PRIMARY KEY (guid));");
		
		// xml_access table
		jdbc.execute(
			"CREATE TABLE IF NOT EXISTS xml_access                   " +
			"(                                                       " +
			"  guid text,                                            " +
			"  accessfileid text,                                    " +
			"  principal_name character varying(100),                " +
			"  permission bigint,                                    " +
			"  perm_type character varying(32),                      " +
			"  perm_order character varying(32),                     " +
			"  begin_time date,                                      " +
			"  end_time date,                                        " +
			"  ticket_count bigint,                                  " +
			"  subtreeid character varying(32),                      " +
			"  startnodeid bigint,                                   " +
			"  endnodeid bigint,                                     " +
			"  CONSTRAINT xml_access_ck CHECK (begin_time < end_time)" +
			");                                                      ");
		
		// smreplicationpolicy table
		jdbc.execute(                                                          
			"CREATE TABLE IF NOT EXISTS smreplicationpolicy                    " +
			"(                                                                 " +
			"  guid text,                                                      " +
			"  member_node character varying(250),                             " +
			"  policy text,                                                    " +
			"  CONSTRAINT systemmetadatareplicationpolicy_fk FOREIGN KEY (guid)" +
			"      REFERENCES systemmetadata (guid));                          ");
		
		// smreplicationstatus table
		jdbc.execute(
			"CREATE TABLE IF NOT EXISTS smreplicationstatus                    " +
			"(                                                                 " +
			"  guid text,                                                      " +
			"  member_node character varying(250),                             " +
			"  status character varying(250),                                  " +
			"  date_verified timestamp,                                        " +
			"  CONSTRAINT systemmetadatareplicationstatus_fk FOREIGN KEY (guid)" +
			"      REFERENCES systemmetadata (guid));                          ");
		
	}
	
	/**
	 * Drop all database tables used for testing
	 * 
	 * @param jdbc  the JdbcTemplate used to execute the statements
	 */
	public static void dropTables(JdbcTemplate jdbc) {
		
		// Drop all tables in the correct order
        jdbc.execute("DROP TABLE IF EXISTS smreplicationstatus;");
        jdbc.execute("DROP TABLE IF EXISTS smreplicationpolicy;");
        jdbc.execute("DROP TABLE IF EXISTS xml_access;");
        jdbc.execute("DROP TABLE IF EXISTS systemmetadata;");
        jdbc.execute("DROP TABLE IF EXISTS identifier;");

	}
	
}
