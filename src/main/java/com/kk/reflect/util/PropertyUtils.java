package com.kk.reflect.util;

import java.lang.reflect.Field;

public class PropertyUtils {

    //person.setXxx(value);
    public static void setProperty(Object object,String fieldName,Object value)throws Exception{
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object,value);
    }

}
