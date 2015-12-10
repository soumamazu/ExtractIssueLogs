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

The application is deployed on OpenShift (https://www.openshift.com/)

1. Go to - http://extractissuelogsapp-mazumdar.rhcloud.com/ExtractIssueLogs/
2. Enter Repository URL preferably in <https://github.com/username/repository/issues> or <username/repository> format
3. Hit Submit
4. Table with Relevant Data is Displayed

#Solution Improvement

1. Work on the UI. The UI now is very basic just for functionality. Given more time, I would take measures for the UI to be more interactive. As in, the counts of the various types of Issues could be linked to the list of those issues. For example, the count for "open issues more than 7 days old" would take us to the list of such issues.
2. The application is very lightweight as it is. However, given more time, I would try to optimize the calculation of time differences between the issue creation date and current date and avoid having to read all the full response of the HTTP Request. However, reading the full response might come in handy later on if we need to implement other functionality which would require more information from the Issue other than just creation date.

#Notes

You will find a weblogic.xml file bundled with my project. This is because I tested my applicaiton by initially deploying it locally on Oracle Weblogic Server 12c.
