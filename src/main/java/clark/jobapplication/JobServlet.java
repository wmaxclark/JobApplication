package clark.jobapplication;

import clark.data.JobDataException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig(
        fileSizeThreshold = 5_242_880, //5MB
        maxFileSize = 20_971_520L //20MB
)
/**
 *
 * @author William Clark
 */
public class JobServlet extends HttpServlet {

    /**
     * The name of the file.
     */
    private static final String FILE_NAME = "listings.csv";
    private static final String FILE_PATH = "WEB-INF/assets/";
    
    /**
     * A collection of all Jobs.
     */
    private static SortedSet<Job> Jobs;
    private static Map<Integer, Job> Map = new HashMap<Integer, Job>();
    
    /**
     * Reads data from CSV file and stores in SortedSet
     * 
     * @throws JobDataException 
     */
    private static void readFromFile(HttpServletRequest request) throws JobDataException{
        SortedSet<Job> result = new TreeSet<>();
        Map<Integer, Job> map = new HashMap<>();
        String line;
        String[] fields;
        int lineCount = 0;
        try(Scanner in = new Scanner(new File(request.getServletContext().getRealPath(FILE_PATH + FILE_NAME)))){
            while(in.hasNextLine()){
                lineCount++;
                line = in.nextLine();
                fields = line.split(",");
                try{
                    Integer.parseInt(fields[0]);
                } catch(NumberFormatException nfe){
                    throw new JobDataException(nfe.getMessage()
                            + "CSV Line " + lineCount);
                }
                try{
                    Boolean.parseBoolean(fields[1]);
                } catch(NumberFormatException nfe){
                    throw new JobDataException(nfe.getMessage()
                            + "CSV Line " + lineCount);
                }
                try{
                    LocalDate.parse(fields[2]);
                } catch(NumberFormatException nfe){
                    throw new JobDataException(nfe.getMessage()
                            + "CSV Line " + lineCount);
                }
                try{
                    Boolean.parseBoolean(fields[6]);
                } catch(NumberFormatException nfe){
                    throw new JobDataException(nfe.getMessage()
                            + "CSV Line " + lineCount);
                }
                try{
                    Double.parseDouble(fields[10]);
                } catch(NumberFormatException nfe){
                    throw new JobDataException(nfe.getMessage()
                            + "CSV Line " + lineCount);
                }
                if (Boolean.parseBoolean(fields[1])) {
                    String description = "";
                    for (int i = 11; i < fields.length; i++) {
                        description += fields[i];
                    }
                    Job temp = new Job(Integer.parseInt(fields[0]), Boolean.parseBoolean(fields[1]), fields[2], fields[3], fields[4], fields[5], Boolean.parseBoolean(fields[6]), fields[7], fields[8], fields[9], Double.parseDouble(fields[10]), description);
                    result.add(temp);
                    map.put(temp.getId(), temp);
                }
            }
        } catch(FileNotFoundException fnfe){
            throw new JobDataException(fnfe);
        }
        Jobs = result;
        Map = map;
    }
    
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
        try {
            readFromFile(request);
        }
        catch(JobDataException e) {
            request.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
        }
        if (request.getParameter("id") != null) {
            try{
                int id = Integer.valueOf(request.getParameter("id"));
                request.setAttribute("job", Map.get(id));
                request.getRequestDispatcher("/WEB-INF/jsp/view/job.jsp").forward(request, response);
                return;
            }
            catch(Exception e){
                request.setAttribute("error", e);
                request.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
            }
        }
        int currentPage = 1;
        int listingsPerPage = 4;
        int totalPages = Jobs.size() / 4;
        if (Jobs.size() % listingsPerPage != 0) {
            totalPages++;
        }
        String requestedPage = request.getParameter("page");
        if (requestedPage != null && !requestedPage.equals("")) {
            try{
                currentPage = Integer.parseInt(requestedPage);
                currentPage = currentPage < 1 ? 1 : currentPage > totalPages ? totalPages : currentPage;
            }
            catch(NumberFormatException e){
                currentPage = 1;
            }
        }
        request.setAttribute("begin", (currentPage - 1) * listingsPerPage);
        request.setAttribute("end", ((currentPage - 1) * listingsPerPage) + listingsPerPage - 1);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("listings", Jobs);
        request.getRequestDispatcher("/WEB-INF/jsp/view/jobList.jsp").forward(request, response);
        

        
        
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
        processRequest(request, response);
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
        processRequest(request, response);
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

}
