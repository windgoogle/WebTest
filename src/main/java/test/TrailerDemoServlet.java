package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Servlet 4.0 HTTP Trailers示例：
 * 1. 流式发送响应体（模拟大文件传输）；
 * 2. 响应体发送完成后，添加Trailer头（校验和、总字节数）；
 */
@WebServlet("/trailerDemo")
public class TrailerDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // ========== 关键步骤1：声明要发送的Trailer头名称（必须先做） ==========
        // 声明后续会发送的Trailer头：Checksum（内容校验和）、Total-Bytes（总字节数）
        response.setHeader("Trailer", "Checksum, Total-Bytes");
        // 设置响应内容类型，禁用缓存（确保Trailers能正常传输）
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        // ========== 关键步骤2：流式发送响应体（分块编码自动触发） ==========
        // 模拟大文件流式传输：分多次发送响应体（而非一次性输出）
        OutputStream out = response.getOutputStream();
        String[] contentChunks = {
                "Servlet 4.0 HTTP Trailers demo :\n",
                "This is a response body sent in chunks - Chunk 1\n",
                "This is a response body sent in chunks - Chunk 2\n",
                "This is a response body sent in chunks - Chunk 3\n"
        };
        // 累计总字节数
        int totalBytes = 0;
        for (String chunk : contentChunks) {
            byte[] chunkBytes = chunk.getBytes(StandardCharsets.UTF_8);
            out.write(chunkBytes);
         //   out.flush(); // 刷出当前块（触发分块传输）----tomcat9里这步会导致Response commit ,进一步导致 判断不支持http trailer了
            totalBytes += chunkBytes.length;
            // 模拟延迟，更贴近真实流式传输
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // ========== 关键步骤3：添加Trailer头（响应体发送完成后） ==========
        // 方式1：单个添加（简单场景）
        // 模拟计算内容校验和（此处简化为固定值，实际可计算MD5/SHA1）
      //  response.addTrailer("Checksum", "md5:8f7d987a6b5c4d3e2f1a0b9c8d7e6f5a");------这个API不支持， AI写的
        //response.addTrailer("Total-Bytes", String.valueOf(totalBytes));
        final int  total=totalBytes;
        // 方式2：批量添加（Servlet 4.0新增，推荐复杂场景）
         response.setTrailerFields((Supplier<Map<String, String>>) () -> {
             Map<String, String> trailers = new HashMap<>();
             trailers.put("Checksum", "md5:8f7d987a6b5c4d3e2f1a0b9c8d7e6f5a");
             trailers.put("Total-Bytes", String.valueOf(total));
             return trailers;
         });


        // 关闭输出流，提交响应（Trailers会随最后一块数据发送）
        out.flush();
        out.close();

       // request.getTrailerFields().forEach((name, value) -> {
         //   System.out.println("Trailer: " + name + " = " + value);
         //});
    }
}