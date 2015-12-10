package ExtractData;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/* Author: Soumadeep Mazumdar */

public class Logs extends HttpServlet
{
    protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();                                                   //Capture Current Session
        String repo;
        
        if(session.getAttribute("repo_name")==null)                                                 //If Repository Name is Session Attribute
        {
            repo=request.getParameter("repo");                                                      //Get Repository Name from Form
            session.setAttribute("repo_name",repo);
        }
        else
            repo=session.getAttribute("repo_name").toString();                                      //Set Repository Name as Session Attribute
        
        Extract el=new Extract();
        int code=el.sendGet(repo);                                                                  //Get Counts
        
        try(PrintWriter out=response.getWriter())
        {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Repository Issue Logs</title>");
            out.println("<body>");
            
            out.println("</br><p>Repository:</br>"+repo+"</p>");
            
            if(code==0)
            {
                out.println("</br><p>- Total number of open issues:</br>"+el.total_open+"</p>");
                out.println("</br><p>- Number of open issues that were opened in the last 24 hours:</br>"+el.last_24+"</p>");
                out.println("</br><p>- Number of open issues that were opened more than 24 hours ago but less than 7 days ago:</br>"+el.more_24_less_7+"</p>");
                out.println("</br><p>- Number of open issues that were opened more than 7 days ago:</br>"+el.more_7+"</p>");
            }
            else
                out.println("</br><p>Repository Not Found!!!</p>");
            
            out.println("<button onclick=\"/ExtractIssueLogs/Logs\">Refresh</button>");
            
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