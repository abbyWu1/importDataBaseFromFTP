package com.example.importDataBaseFromFTP.importData.mapper;
import com.example.importDataBaseFromFTP.entity.StaffInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface StaffMapper {

    int truncateTable(@Param("table") String table);

    void batchInser(@Param(("staffInfos")) List<StaffInfo> staffInfos);
}
