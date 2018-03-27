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
import java.io.UnsupportedEncodingException;


/**
 * 文件操作类
 *
 * @author minggo
 * @time 2014-12-2下午2:16:19
 */
public class FileUtils {

    /**
     * 写文件
     * @param filePath
     * @param fileName
     * @param content
     * @param append 是否添加在原内容的后边
     * @return
     */
    public static boolean WriterTxtFile(String filePath, String fileName,
                                        String content,boolean append) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }// true表示在文件末尾添加
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
     * @param fileName
     * @return
     */
    public static boolean deleteDirectory(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {

            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isDirectory()) {
                String[] listfile = newPath.list();
                // delete all files within the specified directory and then
                // delete the directory
                try {
                    for (int i = 0; i < listfile.length; i++) {
                        File deletedFile = new File(newPath.toString() + "/"
                                + listfile[i].toString());
                        deletedFile.delete();
                    }
                    newPath.delete();
                    LogUtils.info("DirectoryManager deleteDirectory", fileName);
                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;
                }

            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {

            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isFile()) {
                try {
                    newPath.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        } else
            status = false;
        return status;
    }
}
