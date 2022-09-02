package com.example.importDataBaseFromFTP.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: abby
 * @Date: 2022/4/19 13:49
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix="person")
public class Person {
   // @Value("${person.name}")
    private String name;
   // @Value("${person.age}")
    private Integer age;
   // @Value("#{'${person.like}'.split(',')}")
    List<String> like;
}
