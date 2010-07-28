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
import org.apache.log4j.Logger;

/**
 *
 * @author rwaltz
 */
public class MetadataPackageAccess {

    Logger logger = Logger.getLogger(MetadataPackageAccess.class.getName());
    static public final String SKIP_IN_LOG_FIELD = "skipInLogFile";
    static public final String DATE_TIME_LAST_ACCESSED_FIELD = "dateTimeLastAccessed";
    private String eventLogFilePersistDataPath;
    private String eventLogFilePersistDataName;
    File logfilePersistData;
    private HashMap<String, Long> persistMappings = new HashMap<String, Long>();

    public void init() throws FileNotFoundException, IOException, ClassNotFoundException, Exception {
        this.logfilePersistData = new File(eventLogFilePersistDataPath + File.separator + eventLogFilePersistDataName);
        FileInputStream fis = null;
//        try {
            if (this.logfilePersistData.exists() && this.logfilePersistData.length() > 0) {
                if (this.logfilePersistData.canRead() ) {

                    fis = new FileInputStream(this.logfilePersistData);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    this.persistMappings.putAll((HashMap<String, Long>) ois.readObject());
                    fis.close();

                } else {
                    throw new Exception("LogReader: LogFileSkipData file " + eventLogFilePersistDataName + " either does not exist or cannot be read!");
                }
            } else {
                this.logfilePersistData.createNewFile();
                this.persistMappings.put(SKIP_IN_LOG_FIELD, new Long(0));
                this.persistMappings.put(DATE_TIME_LAST_ACCESSED_FIELD, new Long(0));
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
        if (this.logfilePersistData.canWrite()) {
            FileOutputStream fos = new FileOutputStream(this.logfilePersistData);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.persistMappings);
            oos.flush();
            fos.close();
        } else {
            throw new Exception("LogReader: LogFileSkipData file " + eventLogFilePersistDataName + " either does not exist or cannot be read!");
        }
    }

    public String getEventLogFilePersistDataName() {
        return eventLogFilePersistDataName;
    }

    public void setEventLogFilePersistDataName(String eventLogFilePersistDataName) {
        this.eventLogFilePersistDataName = eventLogFilePersistDataName;
    }

    public String getEventLogFilePersistDataPath() {
        return eventLogFilePersistDataPath;
    }

    public void setEventLogFilePersistDataPath(String eventLogFilePersistDataPath) {
        this.eventLogFilePersistDataPath = eventLogFilePersistDataPath;
    }

    public HashMap<String, Long> getPersistMappings() {
        return persistMappings;
    }

    public void setPersistMappings(HashMap<String, Long> persistMappings) {
        this.persistMappings = persistMappings;
    }
}
