package client;

import okhttp3.*;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

import java.io.IOException;

/**
 * @author tengdl
 * @ClassName RequestClient.java
 * @Description TODO
 * @createTime 2021/12/07 18:57:36
 */
public class RequestClient {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void main(String[] args) throws IOException {
        //was_post();
        tw_post();
    }

    private static void tw_post() {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new RequestInterceptor())
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("firstname", "admin")
                .add("lastname", "admin")
                .build();

       // String url = "http://localhost:8080/WebTest/testParam";

        String url = "http://168.1.11.9:8088/WebTest/testParam";
        final Request request = new Request.Builder()
                .url(url)
//                .header("Content-Encoding", "gzip")
                .post(requestBody)
                .build();

        Call call = httpClient.newCall(request);

        call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("okHttpPost enqueue: \n onResponse:" + response.toString() + "\n body:" + response.body().string());
            }
        });

    }

    private static void was_post() {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new RequestInterceptor())
                .build();

        String json = "{\"firstname\":\"admin\", \"lastname\":\"admin\"}";
        RequestBody requestBody = RequestBody.create(JSON, json);

//        String url = "http://192.168.22.189:9080/GzipResponseServer/test";

        String url = "http://192.168.22.189:8089/test";

        final Request request = new Request.Builder()
                .url(url)
                .header("Content-Encoding", "gzip")
                .post(requestBody)
                .build();

        Call call = httpClient.newCall(request);

        call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("okHttpPost enqueue: \n onResponse:" + response.toString() + "\n body:" + response.body().string());
            }
        });
    }

    private static class RequestInterceptor implements Interceptor {

        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                return chain.proceed(originalRequest);
            }

            Request compressedRequest = originalRequest.newBuilder()
                    .header("Content-Encoding", "application/gzip")
                    .method(originalRequest.method(), gzip(originalRequest.body()))
                    .build();
            return chain.proceed(compressedRequest);
        }

        private RequestBody gzip(final RequestBody body) {
            return new RequestBody() {
                @Override
                public MediaType contentType() {
                    return body.contentType();
                }

                @Override
                public long contentLength() {
                    return -1;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    body.writeTo(gzipSink);
                    gzipSink.close();
                }
            };
        }
    }
}
