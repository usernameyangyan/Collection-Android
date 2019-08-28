package com.youngmanster.collectionlibrary.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;


/**
 * 文件操作类
 */
public class FileUtils {

    public static boolean WriterTxtFile(String filePath, String fileName,
                                        String content, boolean append) {
        return WriterTxtFile(filePath, fileName, content, append, null);
    }

    /**
     * 写文件
     *
     * @param filePath
     * @param fileName
     * @param content
     * @param append   是否添加在原内容的后边
     * @return
     */
    public static boolean WriterTxtFile(String filePath, String fileName,
                                        String content, boolean append, FileBack fileBack) {
        String strFile = filePath + "/" + fileName;
        File file;

        // 判断目录是否存在。如不存在则创建一个目录
        file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(strFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream(strFile, append);
            out.write(content.getBytes("UTF-8"));
            out.close();
            if (fileBack != null) {
                fileBack.success(content);
            }
        } // true表示在文件末尾添加
        catch (Exception e) {
            if (fileBack != null) {
                fileBack.error(e);
            }
        }

        return true;

    }

    /**
     * 读取文本文件中的内容
     *
     * @param strFilePath 文件详细路径
     * @return
     */
    public static String ReadTxtFile(String strFilePath) {
        String content = ""; // 文件内容字符串
        // 打开文件
        File file = new File(strFilePath);
        // 如果path是传递过来的参数，可以做一个非目录的判断
        if (!file.isDirectory() && file.exists()) {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    // 分行读取
                    while ((line = buffreader.readLine()) != null) {
                        if (strFilePath.contains("ggid")) {
                            content += line;
                        } else {
                            content += line + "\n";
                        }
                    }
                    instream.close();
                }
            } catch (FileNotFoundException e) {
                // Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                // Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }

    /**
     * 判断缓存是否失效
     *
     * @param hours 小时
     * @return
     */
    public static boolean isCacheDataFailure(String filePath, int hours) {
        int cache_time_millis = hours * 60 * 60000; // 把小时转换为毫秒
        boolean failure = false;
        File data = new File(filePath);
        if (data.exists()
                && (System.currentTimeMillis() - data.lastModified()) > cache_time_millis) {
            failure = true;
        } else if (!data.exists()) {
            failure = true;
        }
        return failure;
    }

    /**
     * 检查文件是否存在
     *
     * @param filepath
     * @return
     */
    public static boolean checkFileExists(String filepath) {
        boolean status;
        if (!filepath.equals("")) {
            File newPath = new File(filepath);
            status = newPath.exists();
        } else {
            status = false;
        }
        return status;

    }

    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 删除目录(包括：目录里的所有文件)
     *
     * @return
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag =deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean status=false;
        SecurityManager checker = new SecurityManager();

        File newPath = new File(filePath);
        checker.checkDelete(newPath.toString());
        if (newPath.isFile()) {
            try {
                newPath.delete();
                status = true;
            } catch (SecurityException se) {
                se.printStackTrace();
                status = false;
            }
        }

        return status;
    }

    public interface FileBack {
        void success(String text);

        void error(Exception e);
    }


    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath,int sizeType){
        File file=new File(filePath);
        long blockSize=0;
        try {
            if(file.isDirectory()){
                blockSize = getFileSizes(file);
            }else{
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 获取指定文件大小
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception
    {
        long size = 0;
        if (file.exists()){
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        else{
            file.createNewFile();
        }
        return size;
    }


    /**
     * 获取指定文件夹
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++){
            if (flist[i].isDirectory()){
                size = size + getFileSizes(flist[i]);
            }
            else{
                size =size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小,指定转换的类型
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS,int sizeType)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong=Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }
}
