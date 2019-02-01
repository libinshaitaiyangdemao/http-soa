package com.bingo.http.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/4/27 16:50
 * @lastdate:
 */
public class StringUtil {

    public static String firstLetterUp(String str){
        char[] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        builder.append(Character.toUpperCase(chars[0]));
        builder.append(chars,1,chars.length-1);
        return builder.toString();
    }

    public static String[] split(String str,String p){
        return str.split(p);
    }

    public static String removeStart(String str,String start){
        while (str.startsWith(start)){
            str = str.substring(start.length());
        }
        return str;
    }

    public static String removeEnd(String str,String end){
        while (str.endsWith(end)){
            str = str.substring(0,str.lastIndexOf(end));
        }
        return str;
    }

    public static String urlEncode(String content) {
        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
