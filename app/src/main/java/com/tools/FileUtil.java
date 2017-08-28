package com.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by Fang Ruijiao on 2017/7/17.
 */

public class FileUtil {

    public static File saveFileToCompletePath(String path, String data, boolean var2) {
        File var3 = new File(path);
        FileOutputStream var4 = null;

        try {
            if(!var3.getParentFile().exists()) {
                var3.getParentFile().mkdirs();
            }

            var4 = new FileOutputStream(var3, var2);
            var4.write(data.getBytes());
            var4.flush();
            var4.close();
            return var3;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static String readFile(String var0) {
        StringBuilder var2 = new StringBuilder();

        try {
            FileInputStream var1 = new FileInputStream(var0);
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var1));
            String var4 = "";

            while((var4 = var3.readLine()) != null) {
                var2.append(var4 + "\r\n");
            }

            var3.close();
            var1.close();
            return var2.toString();
        } catch (Exception var5) {
            return "";
        }
    }

}
