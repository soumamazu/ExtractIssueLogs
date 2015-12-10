package ExtractData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;

/* Author: Soumadeep Mazumdar */

public class Extract
{
    public int total_open,last_24,more_24_less_7,more_7;                                            //Counts of Issues
    private final String access_token="access_token=9f0b2863d76df03f7d579187b6a8565b8a104436";      //OAuth Token for API
    private final SimpleDateFormat formatter;                                                       //Date Formatter
    
    public Extract()
    {
        total_open=last_24=more_24_less_7=more_7=0;                                                 //Initialize Counts
        formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");                                 //Date Format
    }
    
    private void getResponse(String url) throws Exception
    {
        URL urlobj=new URL(url);                                                                    //Fetch the URL
	HttpURLConnection con=(HttpURLConnection)urlobj.openConnection();
        con.setRequestMethod("GET");
        
        if(con.getResponseCode()!=200)                                                              //Respone Code
            return;
        
        StringBuffer response;
        
        try(BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream())))      //Capture the Response
        {
            String inputLine;
            response=new StringBuffer();
            
            while((inputLine=in.readLine())!=null)
                response.append(inputLine);
        }
        
        JSONArray json=new JSONArray(response.toString());                                          //Convert to JSON Object
        long current_time=System.currentTimeMillis();                                               //Current System Time
        long issue_update_time,time_diff_in_hours;
        
        for(int i=0;i<json.length();i++)                                                            //For each Issue
        {
            JSONObject obj=json.getJSONObject(i);
            total_open++;                                                                           //Count of Open Issues
            issue_update_time=this.formatter.parse(obj.getString("created_at")).getTime();
            time_diff_in_hours=Math.abs(current_time-issue_update_time)/3600000;                    //Difference between current time & Issue Creation time in hours
            
            if(time_diff_in_hours<=24)
                last_24++;
            else if(time_diff_in_hours>24 && time_diff_in_hours<=168)
                more_24_less_7++;
            else if(time_diff_in_hours>168)
                more_7++;
        }
    }

    public int sendGet(String repo) throws Exception
    {
        repo=repo.replace("https://github.com/","").replace("/issues","").trim();                   //Remove Junk Entry
        String url="https://api.github.com/repos/"+repo+"/issues?";
        URL urlobj=new URL(url);
	HttpURLConnection con=(HttpURLConnection)urlobj.openConnection();
        con.setRequestMethod("GET");
        
        if(con.getResponseCode()!=200)                                                              //Error in Fetching Page
            return -1;
        
        getResponse(url+access_token);
        
        if(con.getHeaderField("Link")==null)                                                        //If no more pages in header
            return 0;
        
        String links=con.getHeaderField("Link");

        /*Link to next and last page */
        int next=Integer.parseInt(links.substring(links.indexOf("page=")+5,links.indexOf(">; rel=\"next\"")));
        int last=Integer.parseInt(links.substring(links.lastIndexOf("page=")+5,links.indexOf(">; rel=\"last\"")));
        
        for(int i=next;i<=last;i++)                                                                 //Iterate over all pages
            getResponse(url+"page="+i+"&"+access_token);
        
        return 0;                                                                                   //Success
    }
}