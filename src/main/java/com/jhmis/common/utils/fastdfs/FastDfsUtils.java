package com.jhmis.common.utils.fastdfs;

import com.jhmis.common.config.Global;
import com.luhuiguo.fastdfs.conn.*;
import com.luhuiguo.fastdfs.service.DefaultFastFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.luhuiguo.fastdfs.service.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class FastDfsUtils {
    private static Logger logger = LoggerFactory.getLogger(FastDfsUtils.class);
    private static FastDfsUtils instance;
    private FastFileStorageClient fastFileStorageClient;

    public static FastDfsUtils getInstance() {
        if (instance == null) {
            synchronized (FastDfsUtils.class) {
                instance = new FastDfsUtils();
            }
        }
        return instance;
    }

    private FastDfsUtils() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setSoTimeout(Global.getConfigAsInt("fdfs.soTimeout", 1000));
        pooledConnectionFactory.setConnectTimeout(Global.getConfigAsInt("fdfs.connectTimeout", 1000));
        ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
        FdfsConnectionPool pool = new FdfsConnectionPool(pooledConnectionFactory, connectionPoolConfig);
        TrackerConnectionManager trackerConnectionManager = new TrackerConnectionManager(pool,
                Arrays.asList(Global.getConfig("fdfs.trackerList").split(",")));
        TrackerClient trackerClient = new DefaultTrackerClient(trackerConnectionManager);
        ConnectionManager connectionManager = new ConnectionManager(pool);
        fastFileStorageClient = new DefaultFastFileStorageClient(trackerClient, connectionManager);
    }

    public String upload(String groupName, final byte[] content,final String ext) {
        String path = fastFileStorageClient.uploadFile(groupName, content, ext)
            .getFullPath();
        logger.info("Upload to fastdfs success =>" + path);
        //String remoteUrl = Global.getConfig("fdfs.fileHost") + path;
        //return  remoteUrl;
        return path;
    }

    public String upload(final byte[] content,final String ext) {
        String path = fastFileStorageClient.uploadFile(content,ext).getFullPath();
        logger.info("Upload to fastdfs success =>" + path);
        //String remoteUrl = Global.getConfig("fdfs.fileHost") + path;
        //return  remoteUrl;
        return path;
    }

    public byte[] getFile(String groupName, String path) {
        byte[] content = fastFileStorageClient.downloadFile(groupName, path);
        return content;
    }

    public void deleteFile(String groupName, String path) throws Exception {
        fastFileStorageClient.deleteFile(groupName, path);
    }
}
