package com.bingo.http.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/3/9 16:19
 * @lastdate:
 */
public class Utils {

    public static byte[] readByte(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[2048];
        int i = in.read(bytes);
        while(i>0){
            out.write(bytes,0,i);
            i = in.read(bytes);
        }
        return out.toByteArray();
    }
    public static String readString(InputStream in) throws IOException {
        InputStreamReader isr = new InputStreamReader(in,"UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while(line!=null){
            builder.append(line);
            line = reader.readLine();
        }
        reader.close();
        isr.close();
        return builder.toString();
    }
    public static String readString(String path) throws IOException {
        FileInputStream in = new FileInputStream(path);
        String result = readString(in);
        in.close();
        return result;
    }

    public static List<File> listFiles(File file, FileFilter filter){
        List<File> result = new ArrayList<>();
        if(filter.accept(file)){
            result.add(file);
        }else if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files!=null&&files.length>0){
                for (int i = 0; i < files.length; i++) {
                    result.addAll(listFiles(files[i],filter));
                }
            }
        }
        return result;
    }

    public static void writeString(OutputStream out,String string) throws IOException{
        OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
        BufferedWriter writer = new BufferedWriter(osw);
        writer.write(string);
        writer.close();
        osw.close();
        out.flush();
    }
}
