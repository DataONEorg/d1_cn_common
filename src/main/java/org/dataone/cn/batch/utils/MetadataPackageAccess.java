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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.batch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Manage persistent metadata concerning the processing of Metacat log files
 * The log files contain information about create events and replication events
 * The files crossreference DataONE GUIDs of sci and sys metadata to the Metacat
 * named files on the file system
 *
 * @author rwaltz
 */
public class MetadataPackageAccess {

    Logger logger = Logger.getLogger(MetadataPackageAccess.class.getName());


    // This file contains metadata (last date and # of bytes read) for the packager
    // reading the
    private String logFilePersistAccessDataPath = null;
    private String logFilePersistAccessDataName = null;

    private String logFilePersistPendingDataPath = null;
    private String logFilePersistPendingDataName = null;

    File logfilePersistAccessData = null;
    File logfilePersistPendingData = null;
    private HashMap<String, Long> persistMappings = new HashMap<String, Long>();
    private Map<String, Map<String, String>> pendingDataQueue = new HashMap<String, Map<String, String>>();

    public void init() throws FileNotFoundException, IOException, ClassNotFoundException, Exception {
        this.logfilePersistAccessData = new File(logFilePersistAccessDataPath + File.separator + logFilePersistAccessDataName);
        FileInputStream fis = null;
        logger.info(this.logfilePersistAccessData.getAbsolutePath());
        if (this.logfilePersistAccessData.exists() && this.logfilePersistAccessData.length() > 0) {
            if (this.logfilePersistAccessData.canRead() ) {

                fis = new FileInputStream(this.logfilePersistAccessData);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.persistMappings.putAll((HashMap<String, Long>) ois.readObject());
                fis.close();

            } else {
                throw new Exception("LogFileSkipData file " + logFilePersistAccessDataName + " either does not exist or cannot be read!");
            }
        } else {
            this.logfilePersistAccessData.createNewFile();
            this.persistMappings.put(MetadataPackageAccessKey.SKIP_IN_LOG_FIELD.toString(), new Long(0));
            this.persistMappings.put(MetadataPackageAccessKey.DATE_TIME_LAST_ACCESSED_FIELD.toString(), new Long(0));
        }
        
        if (logFilePersistPendingDataPath != null && logFilePersistPendingDataName != null) {
            this.logfilePersistPendingData = new File(logFilePersistPendingDataPath + File.separator + logFilePersistPendingDataName);
            if (this.logfilePersistPendingData.exists() && this.logfilePersistPendingData.length() > 0) {
                if (this.logfilePersistPendingData.canRead() ) {

                    fis = new FileInputStream(this.logfilePersistPendingData);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    this.pendingDataQueue.putAll((HashMap<String, HashMap<String, String>>) ois.readObject());
                    fis.close();

                } else {
                    throw new Exception("LogFilePendingData file " + logFilePersistPendingDataName + " either does not exist or cannot be read!");
                }
            } else {
                this.logfilePersistPendingData.createNewFile();
            }
        }
/*        } catch (FileNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            try {
              if (fis != null) {
                fis.close();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        } */
    }

    // only call this after all transactions have succeeded
    public void writePersistentData() throws FileNotFoundException, IOException, Exception {
        if (this.logfilePersistAccessData.canWrite()) {
            FileOutputStream fos = new FileOutputStream(this.logfilePersistAccessData);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.persistMappings);
            oos.flush();
            fos.close();
        } else {
            throw new Exception("LogFileSkipData file " + logFilePersistAccessDataName + " either does not exist or cannot be read!");
        }
        if (this.logfilePersistPendingData != null && this.logfilePersistPendingData.exists() && (this.logfilePersistPendingData.canWrite())) {
            FileOutputStream fos = new FileOutputStream(this.logfilePersistPendingData);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.pendingDataQueue);
            oos.flush();
            fos.close();
        }
    }

    public String getLogFilePersistAccessDataName() {
        return logFilePersistAccessDataName;
    }

    public void setLogFilePersistAccessDataName(String logFilePersistAccessDataName) {
        this.logFilePersistAccessDataName = logFilePersistAccessDataName;
    }

    public String getLogFilePersistAccessDataPath() {
        return logFilePersistAccessDataPath;
    }

    public void setLogFilePersistAccessDataPath(String logFilePersistAccessDataPath) {
        this.logFilePersistAccessDataPath = logFilePersistAccessDataPath;
    }

    public String getLogFilePersistPendingDataName() {
        return logFilePersistPendingDataName;
    }

    public void setLogFilePersistPendingDataName(String logFilePersistPendingDataName) {
        this.logFilePersistPendingDataName = logFilePersistPendingDataName;
    }

    public String getLogFilePersistPendingDataPath() {
        return logFilePersistPendingDataPath;
    }

    public void setLogFilePersistPendingDataPath(String logFilePersistPendingDataPath) {
        this.logFilePersistPendingDataPath = logFilePersistPendingDataPath;
    }

    public Map<String, Map<String, String>> getPendingDataQueue() {
        return pendingDataQueue;
    }

    public void setPendingDataQueue(Map<String, Map<String, String>> pendingDataQueue) {
        this.pendingDataQueue = pendingDataQueue;
    }

    public HashMap<String, Long> getPersistMappings() {
        return persistMappings;
    }

    public void setPersistMappings(HashMap<String, Long> persistMappings) {
        this.persistMappings = persistMappings;
    }
}
