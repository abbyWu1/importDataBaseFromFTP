package com.example.importDataBaseFromFTP.anotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IndexFromFile {
    int index();
}
