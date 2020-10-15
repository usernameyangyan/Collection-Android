package com.youngmanster.collectionlibrary.network.download;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by yangy
 * 2020-04-01
 * Describe:
 */
public class DownloadFileHelper {

    public static DownloadInfo createDownInfo(String url){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        DownloadInfo downloadInfo = new DownloadInfo(url);
        long contentLength = getContentLength(okHttpClient, url);//获得文件大小
        downloadInfo.setTotal(contentLength);
        String name = url.substring(url.lastIndexOf("/"));
        downloadInfo.setFileName(name);
        return downloadInfo;
    }


    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @param mClient
     * @return
     */
    private static long getContentLength(OkHttpClient mClient, String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? -1 : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static DownloadInfo getRealFileName(DownloadInfo downloadInfo, String filePath, String fileName, boolean isBreakPoint) {
        long downloadLength = 0, contentLength = downloadInfo.getTotal();
        File file = new File(filePath+ File.separator+fileName);
        if (file.exists()) {
            //找到了文件,代表已经下载过,则获取其长度
            downloadLength = file.length();
        }
        //之前下载过,需要重新来一个文件
        if (downloadLength >= contentLength||!isBreakPoint) {
            file.delete();
        }
        file = new File(filePath+ File.separator+fileName);
        //设置改变过的文件名/大小
        downloadInfo.setProgress(file.length());
        downloadInfo.setFileName(file.getName());
        return downloadInfo;
    }


    /**
     * 写入文件
     *
     * @throws IOException
     */
    public static DownloadInfo writeCache(ResponseBody responsebody, String url, String filePath, String fileName) {
        InputStream inputStream = null;
        RandomAccessFile raf = null;
        String path=filePath+ File.separator+fileName;
        File file = new File(path);

        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            // 创建文件夹
            fileDir.mkdirs();
        }

        try {
            raf = new RandomAccessFile(path, "rw");
            inputStream = responsebody.byteStream();
            byte[] fileReader = new byte[4096];
            raf.seek(file.length());

            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                raf.write(fileReader, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        DownloadInfo downloadInfo = new DownloadInfo(url);
        downloadInfo.setFileName(fileName);
        downloadInfo.setFilePath(filePath);
        downloadInfo.setProgress(file.length());
        return downloadInfo;
    }

}
