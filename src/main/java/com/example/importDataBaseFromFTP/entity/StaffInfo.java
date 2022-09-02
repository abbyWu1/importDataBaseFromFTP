package com.example.importDataBaseFromFTP.entity;

import com.example.importDataBaseFromFTP.anotation.IndexFromFile;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "staff_info")
public class StaffInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 员工号
     **/
    @IndexFromFile(index = 0)
    @Column(name = "staff_no")
    private String staffNo;

    /**
     * 姓名
     **/
    @IndexFromFile(index = 1)
    @Column(name = "cust_name")
    private String custName;

    /**
     * 电话号码
     **/
    @IndexFromFile(index = 2)
    @Column(name = "mobile", unique = true)
    private String mobile;

    /**
     * 身份证号
     **/
    @IndexFromFile(index = 3)
    @Column(name = "cert_code", unique = true)
    private String certCode;

    /**
     * 机构号
     **/
    @IndexFromFile(index = 4)
    @Column(name = "branch_no")
    private String branchNo;

    /**
     * 机构名称
     **/
    @IndexFromFile(index = 5)
    @Column(name = "branch_name")
    private String branchName;

    /**
     * 岗位名称
     **/
    @IndexFromFile(index = 6)
    @Column(name = "job_title")
    private String jobTitle;
}
