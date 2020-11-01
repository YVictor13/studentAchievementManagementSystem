package com.example.demo.util;

import org.apache.ibatis.annotations.Mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IsNumber {
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.trim());
        if (isNum.matches()) {
            return true;
        }
        return false;
    }

}
