package com.mlsc.waste.utils;

import java.io.File;

public class FileUtil {
    public static boolean deleteFolder(File fl){
        boolean flag = true; 
        if (fl.isFile() || fl.exists()) {  
            File[] files = fl.listFiles();  
            for (int i = 0; i < files.length; i++) {  
                //删除子文件  
                if (files[i].isFile()) {  
                    flag = deleteFile(files[i].getAbsolutePath());  
                    if (!flag) break;  
                } //删除子目录  
                else {  
                    flag = deleteDirectory(files[i].getAbsolutePath());  
                    if (!flag) break;  
                }  
            }  
        }
        return flag;
    }
    public static boolean deleteDirectory(String sPath) {  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag) break;  
            } 
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;
        return dirFile.delete();
    }  
    
    public static boolean deleteFile(String sPath) {  
        boolean flag = false;  
        File file = new File(sPath);  
        
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    }  
    
    
    
    public static boolean isDataFile(String sPath){
        boolean flag = false; 
        File file = new File(sPath);  
        if (file.getName().endsWith(".cfs")) {  
            flag = true;  
        }  
        return flag;  
    }
    
    
    public static boolean hasDataFolder(String sPath) {  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = false;  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            if (files[i].isFile()) {  
                flag = isDataFile(files[i].getAbsolutePath());  
                if (flag) break;  
            } 
            else {  
                flag = hasDataFolder(files[i].getAbsolutePath());  
                if (flag) break;  
            }  
        }  
        
        return flag;
        
    }  
    
}
