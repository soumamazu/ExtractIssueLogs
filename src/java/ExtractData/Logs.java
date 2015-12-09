package ExtractData;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logs extends HttpServlet
{
    protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        response.setContentType("text/html;charset=UTF-8");
        
        String repo=request.getParameter("element_1");
        Extract el=new Extract();
        el.sendGet(repo);
        
        try(PrintWriter out=response.getWriter())
        {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet IssueLogs</title>");
            out.println("<style>\n"+"table, th, td {\n" +"    border: 1px solid black;\n"+
                    "    border-collapse: collapse;\n" +"}\n" +"th, td {\n" +"    padding: 5px;\n"+
                    "}\n" +"</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IssueLogs at "+request.getContextPath()+"</h1>");
            out.println("<p> Repository"+repo+"</p>");
            
            
            out.println("<table style=\"width:100%\">\n"+"  <tr>\n"+"    <th>Issue</th>\n"+
                        "    <th>Last Update</th>		\n"+"  </tr>\n"+"  <tr>\n");
            
            for(Entry e:el.logs)
            {
                out.println("<tr>");
                out.println("<td><a href="+e.url+">"+e.title+"</a></td>");
                out.println("<td>"+e.date+"</td>");
                out.println("</tr>");
            }
            
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request,response);
        }
        catch(Exception ex)
        {
            Logger.getLogger(Logs.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request,response);
        }
        catch(Exception ex)
        {
            Logger.getLogger(Logs.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }
}