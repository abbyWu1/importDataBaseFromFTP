package com.example.importDataBaseFromFTP.importData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

@RestController
@RequestMapping("/ccbc/importData/staffInfoJob")
public class StaffInfoJob {
    private static final Logger log = LoggerFactory.getLogger(StaffInfoJob.class);

    @Resource
    StaffInfoService staffInfoService;

    @RequestMapping(value = "/execute", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    protected void doExecute(@RequestBody String openDay) throws Exception {
        log.info("批量获取营销人员信息,openDay:" + openDay);


        Boolean isSuccess = staffInfoService.execute(openDay);
        if (!isSuccess) {
            log.info("导入员工信息失败");
        }
        log.info("导入员工信息成功");
    }
}
