package com.example.importDataBaseFromFTP.importData.utils;


import com.example.importDataBaseFromFTP.anotation.IndexFromFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Date;

public class FileMappingVoUtil {
    public static <T> T getMappingVo(Class<T> classz, Object[] fileObjects) throws Exception {
        //获取对象的字段数组
        Field[] fields = classz.getDeclaredFields();
        //实例化数组
        T object = classz.newInstance();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            //获取对象字段的类型
            String typeStr = field.getType().getSimpleName().toLowerCase();
            //没有注解的字段直接不需要操作
            if (annotations.length <= 0) continue;
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getSimpleName().equals("IndexFromFile")) {
                    //字段设置成可修改
                    field.setAccessible(true);
                    //获取自定义注解的值
                    IndexFromFile indexFromFile = (IndexFromFile) annotation;
                    int index = indexFromFile.index();
                    //下标超过每组数据的长度就不需要再映射了，这边因为index从0开始，所所以判断条件用>=
                    if (index >= fileObjects.length) continue;
                    //判断字段类型并把value设置进去
                    if (typeStr.equals("int") || typeStr.equals("integer")) {
                        field.set(object, Integer.valueOf(fileObjects[index] == null ? "0" : fileObjects[index].toString()));
                    } else if (typeStr.equals("float")) {
                        field.set(object, Float.valueOf(fileObjects[index] == null ? "0" : fileObjects[index].toString()));
                    } else if (typeStr.equals("double")) {
                        field.set(object, Double.valueOf(fileObjects[index] == null ? "0" : fileObjects[index].toString()));
                    } else if (typeStr.equals("long")) {
                        field.set(object, Long.valueOf(fileObjects[index] == null ? "0" : fileObjects[index].toString()));
                    } else if (typeStr.equals("date")) {
                        field.set(object, Date.valueOf(fileObjects[index] == null ? null : fileObjects[index].toString()));
                    } else {
                        field.set(object, fileObjects[index]);
                    }
                }
            }
        }
        return object;
    }
}
