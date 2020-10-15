package com.youngmanster.collectionlibrary.network.download;

import com.youngmanster.collectionlibrary.network.NetWorkCodeException;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.rx.utils.RxJavaUtils;
import com.youngmanster.collectionlibrary.network.rx.utils.RxUITask;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by yangy
 * 2020/6/24
 * Describe:
 */
public class DownloadProgressBody extends ResponseBody {
    //实际的待包装响应体
    private  ResponseBody responseBody;
    //进度回调接口
    private RequestBuilder requestBuilder;
    //包装完成的BufferedSource
    private BufferedSource bufferedSource;
    private int code;
    private String downUrl;
    /**
     * 构造函数，赋值
     *
     * @param responseBody     待包装的响应体
     */
    public DownloadProgressBody(ResponseBody responseBody, RequestBuilder requestBuilder,int code) {
        this.responseBody = responseBody;
        this.code=code;
        this.requestBuilder=requestBuilder;
        this.downUrl = requestBuilder.getSaveDownloadFilePath()+ File.separator+requestBuilder.getSaveDownloadFileName();
    }

    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * 重写进行包装source
     *
     * @return BufferedSource
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            //包装
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;
            float preValue=-1;
            File file = new File(downUrl);

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength()不知道长度，会返回-1
                if(requestBuilder!=null&&bytesRead != -1&&!NetWorkCodeException.isError(code)){
                    final long loacalSize = file.length();//本地已下载的长度
                    final long trueTotal = loacalSize + responseBody.contentLength() - totalBytesRead;//文件真实长度

                    float progress=(float) loacalSize /trueTotal;
                    if(progress>0){
                        DecimalFormat fmt = new DecimalFormat("#0.00");
                        fmt.setRoundingMode(RoundingMode.DOWN);
                        String s = fmt.format(progress);
                        progress= Float.parseFloat(s);
                    }

                    if(preValue!=progress){

                        RxJavaUtils.doInUIThread(new RxUITask<Float>(progress) {
                            /**
                             * 在UI线程中执行
                             *
                             * @param  progress 任务执行的入参
                             */
                            @Override
                            public void doInUIThread(Float progress) {
                                requestBuilder.getRxObservableListener().onDownloadProgress(trueTotal,loacalSize, progress);
                            }
                        });

                        preValue=progress;
                    }

                }
                return bytesRead;
            }
        };
    }
}
