package ExtractData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

class Entry
{
    String url;
    String date;
    String title;
    
    public Entry(String url,String date,String title)
    {
        this.url=url;
        this.date=date;
        this.title=title;
    }
}

public class Extract
{
    public ArrayList <Entry> logs=new ArrayList <> ();
    
    private void getResponse(String url) throws Exception
    {
        URL urlobj=new URL(url);
	HttpURLConnection con=(HttpURLConnection)urlobj.openConnection();
        con.setRequestMethod("GET");
        
        if(con.getResponseCode()!=200)
        {
            logs.add(new Entry("about:blank","0000-00-00T00:00:00Z","Page Not Found!"));
            return;
        }
        
        StringBuffer response;
        
        try(BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream())))
        {
            String inputLine;
            response=new StringBuffer();
            
            while((inputLine=in.readLine())!=null)
                response.append(inputLine);
        }
        
        JSONArray json=new JSONArray(response.toString());
        
        for(int i=0;i<json.length();i++)
        {
            JSONObject obj=json.getJSONObject(i);
            logs.add(new Entry(obj.getString("html_url"),obj.getString("updated_at"),obj.getString("title")));
        }
    }

    public void sendGet(String repo) throws Exception
    {
        String url="https://api.github.com/repos/"+repo+"/issues";
        URL urlobj=new URL(url);
	HttpURLConnection con=(HttpURLConnection)urlobj.openConnection();
        con.setRequestMethod("GET");
        
        getResponse(url);
        
        if(con.getHeaderField("Link")==null)
            return;
        
        String links=con.getHeaderField("Link");
            
        int next=Integer.parseInt(links.substring(links.indexOf("page=")+5,links.indexOf(">; rel=\"next\"")));
        int last=Integer.parseInt(links.substring(links.lastIndexOf("page=")+5,links.indexOf(">; rel=\"last\"")));
            
        for(int i=next;i<=last;i++)
            getResponse(url+"?page="+i);
    }
}