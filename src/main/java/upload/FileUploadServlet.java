package upload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 从请求中获取上传的文件项
        Part filePart = request.getPart("file");
        if (filePart!= null) {
            // 获取文件名
            String fileName = filePart.getSubmittedFileName();
            // 定义文件保存路径
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            // 拼接完整的文件保存路径
            String filePath = uploadDir.getAbsolutePath() + File.separator + fileName;

            try (InputStream fileContent = filePart.getInputStream()) {
                // 将文件保存到服务器
                Files.copy(fileContent, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                out.println("<html><body>");
                out.println("<h3>文件 " + fileName + " 上传成功！</h3>");
                out.println("</body></html>");
            } catch (IOException e) {
                out.println("<html><body>");
                out.println("<h3>文件上传失败: " + e.getMessage() + "</h3>");
                out.println("</body></html>");
            }
        } else {
            out.println("<html><body>");
            out.println("<h3>未检测到上传文件</h3>");
            out.println("</body></html>");
        }
    }
}