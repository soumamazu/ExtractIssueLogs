# ExtractIssueLogs

The Application makes use of the GitHub API v3 to extract the Issues of a GitHub repository. Although, the application is capable of capturing all types of Issue data -

	"url",
	"labels_url"
	"comments_url"
	"events_url"
	"html_url"
	"id"
	"number"
	"title"
	"user"
		"login"
		"id"
		"avatar_url"
		"gravatar_id"
		"url"
		"html_url"
		"followers_url"
		"following_url"
		"gists_url"
		"starred_url"
		"subscriptions_url"
		"organizations_url"
		"repos_url"
		"events_url"
		"received_events_url"
		"type"
		"site_admin": false
	"labels"
		"url"
		"name"
		"color"
	"url"
	"name"
	"color"
	"state"
	"locked"
	"assignee"
		"login"
		"id"
		"avatar_url"
		"gravatar_id"
		"url"
		"html_url"
		"followers_url"
		"following_url"
		"gists_url"
		"starred_url"
		"subscriptions_url"
		"organizations_url"
		"repos_url"
		"events_url"
		"received_events_url"
		"type"
		"site_admin"
	"milestone"
	"comments"
	"created_at"
	"updated_at"
	"closed_at"
	"body"
	
For the purpose of our assignment, we are focusing on the "created_at" attribute for the Issue. The difference between the System current time and the Issue creation time, serves as the basis of our log.

#Using the Application Locally

1. Download the Netbeans project.
2. Add JARs - json-java.jar (http://www.java2s.com/Code/JarDownload/java-json/)
3. Deploy WAR file

#Public URL

http://extractissuelogsapp-mazumdar.rhcloud.com/ExtractIssueLogs/
