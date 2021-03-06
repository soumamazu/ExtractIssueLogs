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
        
        //If Repository Name is Session Attribute or a Different Input
        if(session.getAttribute("repo_name")==null || session.getAttribute("repo_name")!=request.getParameter("repo"))
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
            
            out.println("</br><p>Repository:<br>"+repo+"</p>");

            if(code==0)
            {
                out.println("<table border=\"1\" style=\"width:50%\">");
                
                out.println("<tr>");
                out.println("<td>&nbsp;&nbsp;Total number of open issues :</td>");
                out.println("<td>&nbsp;&nbsp;"+el.total_open+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>&nbsp;&nbsp;Number of open issues that were opened in the last 24 hours :</td>");
                out.println("<td>&nbsp;&nbsp;"+el.last_24+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>&nbsp;&nbsp;Number of open issues that were opened more than 24 hours ago but less than 7 days ago :</td>");
                out.println("<td>&nbsp;&nbsp;"+el.more_24_less_7+"</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>&nbsp;&nbsp;Number of open issues that were opened more than 7 days ago :</td>");
                out.println("<td>&nbsp;&nbsp;"+el.more_7+"</td>");
                out.println("</tr>");                
                
                out.println("<table>");
            }
            else
                out.println("</br><p>Repository Not Found!!!</p>");
            
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