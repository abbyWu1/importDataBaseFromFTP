package com.example.importDataBaseFromFTP.importData;

import com.example.importDataBaseFromFTP.entity.StaffInfo;
import com.example.importDataBaseFromFTP.importData.mapper.StaffMapper;
import com.example.importDataBaseFromFTP.importData.utils.FileMappingVoUtil;
import com.example.importDataBaseFromFTP.importData.utils.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@Service
/**
 * 员工信息导入
 */
public class StaffInfoService {
    private static final Logger log = LoggerFactory.getLogger(StaffInfoService.class);
    static final String LOCALPATH = "/resource";
    static final String FILENAME = "STAFF_";
    static final String FTP_HOST = "172.0.0.1";
    static final int FTP_PORT = 21;
    static final String FTP_USERNAME = "admin";
    static final String FTP_PASSWORD = "admin";
    static final String FTP_PATH = "/staff/";

    @Resource
    StaffMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public Boolean execute(String openDay) throws Exception {

        //文件暂存本地目录
        String staffInfoSFTPLocalPath = LOCALPATH;
        log.info("开始批量获取员工信息信息,openDay:" + openDay);
        String staffInfoSFTPFileName = FILENAME + openDay + ".txt";
        log.info("文件名,{}", staffInfoSFTPFileName);
        try {
            boolean isGetFileFromSFTP = getFileFromSFTP(staffInfoSFTPFileName, staffInfoSFTPFileName);
            if (!isGetFileFromSFTP) {
                log.info("当日SFTP无营销人员信息文件");
                return true;
            }
            FileReader fileReader = new FileReader(new File(staffInfoSFTPLocalPath + staffInfoSFTPFileName));
            BufferedReader buffer = new BufferedReader(fileReader);
            String line;
            List<StaffInfo> staffInfos = new LinkedList<>();
            Boolean firstFlag = true;
            try {


                while ((line = buffer.readLine()) != null) {
                    String[] fields = line.split("\\|");
                    StaffInfo staffInfo = FileMappingVoUtil.getMappingVo(StaffInfo.class, fields);
                    staffInfos.add(staffInfo);
                    if (staffInfos.size() > 2000) {
                        if (firstFlag) {
                            //全量更新表，先删除现有表中所有的数据
                            mapper.truncateTable("staff_info");
                            //更新firstFlag为false
                            firstFlag = false;
                        }
                        //批量插入数据
                        mapper.batchInser(staffInfos);
                        //清空list中的数据
                        staffInfos.clear();
                    }
                }
            } catch (IOException e) {
                log.info("获取营销人员信息失败", e);
                throw new Exception("获取营销人员信息失败", e);
            } finally {
                fileReader.close();
                buffer.close();
            }
            //最后一批数据入库
            if (staffInfos.size() > 0) {
                if (firstFlag) {
                    //全量更新表，先删除现有表中所有的数据
                    mapper.truncateTable("staff_info");
                    //   int a = 1 / 0;
                }
                mapper.batchInser(staffInfos);
            }

        } catch (Exception e) {
            log.info("获取营销人员信息失败", e);
            throw new Exception("获取营销人员信息失败", e);
        }
        log.info("当日导入营销人员信息完成");
        return true;
    }

    private boolean getFileFromSFTP(String staffInfoSFTPLocalPath, String staffInfoSFTPFileName) throws Exception {
        try {
            log.info("开始从ftp上获取文件");
            String staffInfoSFTPHost = FTP_HOST;
            log.info("staffInfoSFTPHost,{}", staffInfoSFTPHost);

            int staffInfoSFTPPort = FTP_PORT;
            log.info("staffInfoSFTPPort,{}", staffInfoSFTPPort);

            String staffInfoSFTPUser = FTP_USERNAME;
            log.info("staffInfoSFTPUser,{}", staffInfoSFTPUser);

            String staffInfoSFTPPwd = FTP_PASSWORD;
            log.info("staffInfoSFTPPwd,{}", staffInfoSFTPPwd);

            String staffInfoSFTPFilePath = FTP_PATH;
            log.info("staffInfoSFTPFilePath,{}", staffInfoSFTPFilePath);
            log.info("staffInfoSFTPLocalPath,{}", staffInfoSFTPLocalPath);
            File localFile = new File(staffInfoSFTPLocalPath + staffInfoSFTPFileName);
            FTPUtil ftpUtil = new FTPUtil();
            ftpUtil.connect(staffInfoSFTPHost, staffInfoSFTPPort, staffInfoSFTPUser, staffInfoSFTPPwd);
            ftpUtil.download(staffInfoSFTPFilePath, staffInfoSFTPLocalPath);
            return true;
        } catch (Exception e) {
            log.info("从ftp获取客户信息异常获取客户信息:" + e);
            throw new Exception("从ftp获取客户信息异常获取客户信息异常", e);
        }

    }
}
