package clark.jobapplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
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
        
        try{
            Application application = new Application();
        
            application.setId(applicationMap.size() + 1);
            application.setJobid(Integer.valueOf(request.getParameter("Id")));
            application.setDateTimeSubmitted(Instant.now());
            application.setActive(true);
            
            application.setFirstNameError("");
            application.setLastNameError("");
            application.setEmailError("");
            application.setPhoneNumberError("");
            application.setStartDateError("");
            application.setAttachmentError("");
            
            boolean valid = true;
            if (request.getParameter("firstName") != null && !request.getParameter("firstName").isBlank()) {
                application.setFirstName(request.getParameter("firstName"));
            }
            else{
                application.setFirstNameError("Please input a name. ");
                valid = false;
            }

            if (request.getParameter("lastName") != null && !request.getParameter("lastName").isBlank()) {
                application.setLastName(request.getParameter("lastName"));
            }
            else{
                application.setLastNameError("Please input a name. ");
                valid = false;
            }

            if (request.getParameter("email") != null && !request.getParameter("email").isBlank()) {
                application.setEmail(request.getParameter("email"));
            }
            else{
                application.setEmailError("Please input a valid email. ");
                valid = false;
            }

            if (request.getParameter("phone") != null && request.getParameter("phone").length() >= 7) {
                application.setPhoneNumber(request.getParameter("phone").replaceAll("[^\\d.]", ""));
            }
            else{
                application.setPhoneNumberError("Please input a valid phone number greater than 7 digits. ");
                valid = false;
            }

            try{
                LocalDate start = LocalDate.parse(request.getParameter("startDate"));
                if (request.getParameter("startDate") != null && LocalDate.parse(request.getParameter("startDate")).isAfter(LocalDate.now().minusDays(1))) {
                    application.setStartDate(LocalDate.parse(request.getParameter("startDate")));
                }
                else{
                    throw new NumberFormatException();
                }
            }
            catch(Exception e){
                application.setStartDateError("Please input a valid start date. ");
                valid = false;
            }
            
            
            try{
                Part filePart = request.getPart("attachment");
                if (filePart != null && filePart.getSize() > 0) {
                    Attachment attachment = processAttachment(filePart);
                    if (attachment != null) {
                        application.setAttachment(attachment);
                    }
                }else{
                    application.setAttachmentError("Must provide an attachment less than 20MB. ");
                    valid = false;
                    
                }
            }catch(Exception e){
                request.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
            }
            
            if (valid) {
                applicationMap.put(application.getId(), application);
            }
            else{
                
                request.setAttribute("application", application);
            }
            request.setAttribute("success", valid);
            request.getRequestDispatcher("jobs?id=" + request.getParameter("Id")).forward(request, response);
        }
        catch(Exception e) {
            request.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
        }
        
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
         if (request.getParameter("id") != null) {
             try{
                 application = applicationMap.get(Integer.valueOf(request.getParameter("id")));
                 request.setAttribute("application", application);
                 request.getRequestDispatcher("/WEB-INF/jsp/view/application.jsp").forward(request, response);
                 
             }
             catch(Exception e){
                request.getSession().setAttribute("error", e.toString() + Arrays.toString(e.getStackTrace()));
                request.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
             }
         }
    }

    private void listApplications(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("applicationList", this.applicationMap.values());
        request.getRequestDispatcher("/WEB-INF/jsp/view/applications.jsp").forward(request, response);
    }
}
