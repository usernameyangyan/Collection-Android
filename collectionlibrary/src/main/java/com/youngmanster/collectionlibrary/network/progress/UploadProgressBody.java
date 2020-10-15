package com.youngmanster.collectionlibrary.network.progress;


import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.rx.utils.RxJavaUtils;
import com.youngmanster.collectionlibrary.network.rx.utils.RxUITask;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by yangy
 * 2020/6/24
 * Describe:
 */
public class UploadProgressBody extends RequestBody {
    //实际的待包装请求体
    private  RequestBody requestBody;
    //进度回调接口
    private RequestBuilder requestBuilder;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;

    /**
     * 构造函数，赋值
     * @param requestBody 待包装的请求体
     */
    public UploadProgressBody(RequestBody requestBody, RequestBuilder requestBuilder) {
        this.requestBody = requestBody;
        this.requestBuilder = requestBuilder;
    }

    /**
     * 重写调用实际的响应体的contentType
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    /**
     * 重写进行写入
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
//            //包装
            bufferedSink = Okio.buffer(sink(sink));

        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    /**
     * 写入，回调进度接口
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;
            float preValue=-1;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调

                if(requestBuilder != null){
                    DecimalFormat fmt = new DecimalFormat("#0.00");
                    fmt.setRoundingMode(RoundingMode.DOWN);
                    String s = fmt.format((float) bytesWritten /contentLength);
                    float progress = Float.parseFloat(s);

                    if(preValue!=progress){
                        RxJavaUtils.doInUIThread(new RxUITask<Float>(progress) {
                            /**
                             * 在UI线程中执行
                             *
                             * @param progress 任务执行的入参
                             */
                            @Override
                            public void doInUIThread(Float progress) {
                                requestBuilder.getRxObservableListener().onUploadProgress(contentLength, progress);
                            }
                        });
                        preValue=progress;
                    }

                }
            }
        };
    }
}
