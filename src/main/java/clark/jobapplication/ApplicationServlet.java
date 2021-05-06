package clark.jobapplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@MultipartConfig(
        fileSizeThreshold = 5_242_880, //5MB
        maxFileSize = 20_971_520L //20MB
)
/**
 *
 * @author Home
 */
@WebServlet(name = "ApplicationServlet", urlPatterns = {"/applications"})
public class ApplicationServlet extends HttpServlet {
    
    private static Map<Integer, Application> applicationMap = new HashMap<>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        try ( PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ApplicationServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ApplicationServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "view":
                viewApplication(request, response);
                break;
            case "download":
                downloadAttachment(request, response);
                break;
            case "list":
            default:
                listApplications(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        Application application = new Application();
        
        application.setId(applicationMap.size() + 1);
        application.setJobid(Integer.valueOf(request.getParameter("Id")));
        application.setDateTimeSubmitted(Instant.now());
        application.setActive(true);
        boolean valid = true;
        if (request.getAttribute("firstName") != null && !request.getAttribute("firstName").toString().isBlank()) {
            application.setFirstName(request.getAttribute("firstName").toString());
        }
        else{
            application.setFirstNameError("Please input a name. ");
            valid = false;
        }
        
        if (request.getAttribute("lastName") != null && !request.getAttribute("lastName").toString().isBlank()) {
            application.setLastName(request.getAttribute("lastName").toString());
        }
        else{
            application.setLastNameError("Please input a name. ");
            valid = false;
        }
        
        if (request.getAttribute("email") != null && !request.getAttribute("email").toString().isBlank()) {
            application.setEmail(request.getAttribute("email").toString());
        }
        else{
            application.setEmailError("Please input a valid email. ");
            valid = false;
        }
        
        if (request.getAttribute("phone") != null && request.getAttribute("phone").toString().length() >= 7) {
            application.setPhoneNumber(Integer.valueOf(request.getAttribute("phone").toString()));
        }
        else{
            application.setPhoneNumberError("Please input a valid phone number. ");
            valid = false;
        }
        
        if (request.getAttribute("startDate") != null && LocalDate.parse(request.getAttribute("startDate").toString()).isAfter(LocalDate.now())) {
            application.setPhoneNumber(Integer.valueOf(request.getAttribute("phone").toString()));
        }
        else{
            application.setPhoneNumberError("Please input a valid start date. ");
            valid = false;
        }
        
        Part filePart = request.getPart("attachment");
        if (filePart != null && filePart.getSize() > 0) {
            Attachment attachment = processAttachment(filePart);
            if (attachment != null) {
                application.setAttachment(attachment);
            }
        }else{
            valid = false;
        }
        if (valid) {
            applicationMap.put(application.getId(), application);
        }
        request.setAttribute("application", application);
        request.getRequestDispatcher("/WEB-INF/jsp/view/jobs?id=" + request.getAttribute("Id").toString()).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

     private Attachment processAttachment(Part filePart) throws IOException {
        Attachment attachment = new Attachment();
        try (InputStream inputStream = filePart.getInputStream();
                java.io.ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            attachment.setName(filePart.getSubmittedFileName());
            attachment.setContents(outputStream.toByteArray());
        }
        return attachment;
    }

     private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         Application application = new Application();
         if (request.getParameter("applicationId") != null) {
             try{
                 application = applicationMap.get(Integer.valueOf(request.getParameter("applicationId")));
                 
                 response.setHeader("Content-Disposition", "attachment; filename=" + application.getAttachment().getName());
                 response.setContentType("application/octet-stream");

                 try (ServletOutputStream stream = response.getOutputStream()) {
                    stream.write(application.getAttachment().getContents());
                 } 
             }
             catch(Exception e){
                 response.sendRedirect("applications");
                 return;
             }
         }
         else{
             response.sendRedirect("applications");
             return;
         }
        
    }

    private void viewApplication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Application application = new Application();
         if (request.getParameter("applicationId") != null) {
             try{
                 application = applicationMap.get(Integer.valueOf(request.getParameter("applicationId")));
                 request.setAttribute("application", application);
                request.getRequestDispatcher("/WEB-INF/jsp/view/application.jsp").forward(request, response);
                 
             }
             catch(Exception e){
                listApplications(request, response);
             }
         }
    }

    private void listApplications(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("applicationList", this.applicationMap.entrySet().toArray());
        request.getRequestDispatcher("/WEB-INF/jsp/view/applications.jsp").forward(request, response);
    }

}
