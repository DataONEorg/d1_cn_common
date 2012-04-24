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

public class DataSourceFactory {

    private static final Log log = LogFactory.getLog(DataSourceFactory.class);

    private static BasicDataSource metacatDataSource;

    private static final String metacatUrlProperty = "metacat.datasource.url";
    private static final String metacatDriverClassProperty = "metacat.datasource.driver";
    private static final String metacatUsernameProperty = "metacat.datasource.username";
    private static final String metacatPasswordProperty = "metacat.datasource.password";
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

    public static BasicDataSource getMetacatDataSource() {
        if (metacatDataSource == null) {
            initMetacatDataSource();
        }
        return metacatDataSource;
    }

    private static void initMetacatDataSource() {
        metacatDataSource = new BasicDataSource();
        metacatDataSource.setUrl(metacatUrl);
        metacatDataSource.setDriverClassName(metacatDriverClass);
        metacatDataSource.setUsername(metacatUsername);
        metacatDataSource.setPassword(metacatPassword);
        metacatDataSource.setInitialSize(Integer.valueOf(metacatInitialPoolSize).intValue());
        metacatDataSource.setMaxActive(Integer.valueOf(metacatMaxPoolSize).intValue());
    }
}
