package com.example.importDataBaseFromFTP.importData.utils;

/**
 * @Author: abby
 * @Date: 2022/9/1 17:58
 * @Version: 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.net.ftp.FTPClient;
import java.io.*;


/**
 * FTP工具类<br>
 * 注：上传，可上传文件、文件夹；下载，仅实现下载文件功能，不能识别子文件夹
 * @version 1.0.0
 */
public class FTPUtil {
    private static final Logger log = LoggerFactory.getLogger(FTPUtil.class);

    private FTPClient ftpClient;

    /**
     * 连接FTP服务器，使用默认FTP端口
     *
     * @param serverIp 服务器Ip地址
     * @param user     登陆用户
     * @param password 密码
     * @since
     */
    public void connect(String serverIp, String user, String password) throws IOException {
        try {
            // serverIp：FTP服务器的IP地址；
            // user:登录FTP服务器的用户名
            // password：登录FTP服务器的用户名的口令；
            ftpClient = new FTPClient();
            ftpClient.connect(serverIp, 21);
            ftpClient.login(user, password);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    /**
     * 连接ftp服务器，指定端口
     *
     * @param serverIp
     * @param port     服务器FTP端口号
     * @param user
     * @param password
     * @since
     */
    public void connect(String serverIp, int port, String user, String password) throws IOException {
        try {
            // serverIp：FTP服务器的IP地址；
            // post:FTP服务器端口
            // user:登录FTP服务器的用户名
            // password：登录FTP服务器的用户名的口令；
            ftpClient = new FTPClient();
            ftpClient.connect(serverIp, port);
            ftpClient.login(user, password);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }


    /**
     * 从ftp下载文件到本地
     *
     * @param localFilePath  本地生成的文件名
     * @param remoteFilePath 服务器上的文件名
     * @return
     * @throws java.lang.Exception
     */
    public long download(String remoteFilePath, String localFilePath) throws Exception {
        long result = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(localFilePath);
        try {
            ftpClient.retrieveFile(remoteFilePath, fileOutputStream);
        } catch (Exception ex) {
            log.error("Downloading file failure! Detail:", ex);
            throw new Exception(ex);
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception ex) {
                throw new Exception(ex);
            }
        }
        return result;
    }

}
