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

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.configuration.Settings;

/**
 * Factory class to provide consumers obtaining a handle on a metacat db schema
 * BasicDataSource instances. org.apache.commons.dbcp.BasicDataSource instances
 * provide connection pooling for jdbc data sources.
 * 
 * @author sroseboo
 * 
 */
public class MetacatDataSourceFactory {

    private static final Log log = LogFactory.getLog(MetacatDataSourceFactory.class);

    private static BasicDataSource metacatDataSource;

    private static final String metacatUrlProperty = "metacat.datasource.url";
    private static final String metacatDriverClassProperty = "metacat.datasource.driverClass";
    private static final String metacatUsernameProperty = "database.user";
    private static final String metacatPasswordProperty = "database.password";
    private static final String metacatInitialPoolSizeProperty = "metacat.datasource.initialSize";
    private static final String metacatMaxPoolSizeProperty = "metacat.datasource.maxSize";

    private static final String metacatUrl = Settings.getConfiguration().getString(
            metacatUrlProperty);
    private static final String metacatDriverClass = Settings.getConfiguration().getString(
            metacatDriverClassProperty);
    private static final String metacatUsername = Settings.getConfiguration().getString(
            metacatUsernameProperty);
    private static final String metacatPassword = Settings.getConfiguration().getString(
            metacatPasswordProperty);
    private static final String metacatInitialPoolSize = Settings.getConfiguration().getString(
            metacatInitialPoolSizeProperty);
    private static final String metacatMaxPoolSize = Settings.getConfiguration().getString(
            metacatMaxPoolSizeProperty);

    private MetacatDataSourceFactory() {
    }

    /**
     * Returns the data source instance for the metacat datasource. The metacat
     * datasource is a postgres relational database source.
     * 
     * @return BasicDataSource
     */
    public static BasicDataSource getMetacatDataSource() {
        if (metacatDataSource == null) {
            initMetacatDataSource();
        }
        return metacatDataSource;
    }

    private static void initMetacatDataSource() {
        if (log.isDebugEnabled()) {
            log.debug("Metacat Data Source JDBC settings:");
            log.debug("\tmetacat.datasource.url:" + metacatUrl);
            log.debug("\tmetacat.datasource.driverClass:" + metacatDriverClass);
            log.debug("\tdatabase.user:" + metacatUsername);
            log.debug("\tdatabase.password:" + metacatPassword);
            log.debug("\tmetacat.datasource.initialSize:" + metacatInitialPoolSize);
            log.debug("\tmetacat.datasource.maxSize:" + metacatMaxPoolSize);
        }
        metacatDataSource = new BasicDataSource();
        metacatDataSource.setUrl(metacatUrl);
        metacatDataSource.setDriverClassName(metacatDriverClass);
        metacatDataSource.setUsername(metacatUsername);
        metacatDataSource.setPassword(metacatPassword);
        metacatDataSource.setInitialSize(Integer.valueOf(metacatInitialPoolSize).intValue());
        metacatDataSource.setMaxActive(Integer.valueOf(metacatMaxPoolSize).intValue());
    }

}
