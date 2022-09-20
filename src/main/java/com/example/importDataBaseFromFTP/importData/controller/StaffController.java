package com.example.importDataBaseFromFTP.importData.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: abby
 * @Date: 2022/9/16 23:58
 * @Version: 1.0
 */
@RestController
@RequestMapping("/a")
public class StaffController {
    @PostMapping("/b")
    public String test(){
        return "200";
    }
}
