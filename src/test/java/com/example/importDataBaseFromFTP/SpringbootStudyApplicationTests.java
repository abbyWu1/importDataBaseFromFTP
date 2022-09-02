package com.example.importDataBaseFromFTP;

import com.example.importDataBaseFromFTP.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootStudyApplicationTests {
    @Autowired
    Person person;

    @Test
    void contextLoads() {
        System.out.println(person.toString());
    }

}
