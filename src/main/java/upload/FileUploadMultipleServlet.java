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
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet("/uploadMultiple")
@MultipartConfig
public class FileUploadMultipleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 获取所有上传的文件Part
        Collection<Part> fileParts = request.getParts();
        if (fileParts!= null &&!fileParts.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            for (Part filePart : fileParts) {
                // 获取文件名
                String fileName = filePart.getSubmittedFileName();
                String filePath = uploadDir.getAbsolutePath() + File.separator + fileName;

                try {
                    // 将文件保存到服务器
                    filePart.write(filePath);
                    out.println("<html><body>");
                    out.println("<h3>文件 " + fileName + " 上传成功！</h3>");
                    out.println("</body></html>");
                } catch (IOException e) {
                    out.println("<html><body>");
                    out.println("<h3>文件上传失败: " + e.getMessage() + "</h3>");
                    out.println("</body></html>");
                }
            }
        } else {
            out.println("<html><body>");
            out.println("<h3>未检测到上传文件</h3>");
            out.println("</body></html>");
        }
    }
}