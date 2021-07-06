## For building and running the application you need
  - Java 8
  - Maven 3 (If you dont have maven installed, you can use this link https://mkyong.com/maven/how-to-install-maven-in-windows/ a guide to install maven)
## Running the application locally
  - You can use Eclipse/STS IDE to import the project, then do a maven build and run as a spring boot application. Or you can use commad line to run the project as mentioned below.
  - Import the project, to be able to run your project you have to first build it and package it into a single executable jar. Go to the project folder containing pom.xml file and run the command: **mvn install**
  - To run the spring boot project you can use : **mvn spring-boot:run** , command or use: **java -jar target/SQI-Pipedrive01-0.0.1-SNAPSHOT.jar** . Assuming your project is running on 8080 port, this will be the base url **https://localhost:8080**.






## SIQ-Test-Project
 - An authentication token is added into application.properties as per my Pipedrive account, you can change this token if you want to access any other acount's data. Below is the list of classes and APIs present in the project.
 - SIQCommonController => Common Controller Class for all the Api Calls. Below is the path and list of APIs you can trigger:
   - **PATH**: **/getPersonByName** => Returns the person's data in Json format.
     - Method: `GET`
     - Query Params: `name [String]` (**Required**)
   - **PATH**: **/getPersonById** => Returns the person's data in Json format.
     - Method: `GET`
     - Query Params: `id [int]` (**Required**)
   - **PATH**: **/updatePerson** => Updates properties of the person whose id is given.
     - Method: `PUT`
     - Query Params: `Id [int]` (**Required**)
     - Body Params :
       - `name [String]`: Person Name
       - `owner_id [int]`: ID of the user who will be marked as the owner of this person. When omitted, the authorized user ID will be used.
       - `org_id [int]`: ID of the organization this person will belong to.
       - `phone [String Array]` :Phone numbers (one or more) associated with the person.
       - `email [String Array]`: Email addresses (one or more) associated with the person.
       - `visible_to [String]`:Visibility of the person. If omitted, visibility will be set to the default visibility setting of this item type for the authorized user. Values => **1** :Owner & followers (private), **2** :Entire company (shared). Example :
           ``` javascript
	     {
              "name":"Hitman",
              "owner_id":10101,
              "org_id":3,
              "email":[
                "dakshinewmail@gmail.com"
                     ],
               "phone":[
                 "1122334455"
                  ]
	     }	   
	   

   - **PATH**: **/addNotes** =>Adds a new note to the person.
     - Method: `POST`
     - Query Params:none
     - Body Params :
       - `content [String]`: Content of the note in HTML format.
       - `user_id[int]`: ID of the user who will be marked as the author of this note. Only an admin can change the author.
       - `person_id [int]`: The ID of the person this note will be attached to.
       - `add_time [String]` :Optional creation date & time of the Note in UTC. Can be set in the past or in the future. Requires admin user API token. Format: YYYY-MM-DD HH:MM:SS.
       - `pinned_to_person_flag`: If set, then results are filtered by note to person pinning state (person_id is also required). Values 0/1. Example:
           ``` javascript
	     {
              "content":"Another Note for my man",
               "user_id":2020,
               "person_id":8,
               "add_time":"2015-01-01",
               "pinned_to_person_flag":1
              }
	   
    - **PATH**: **/addWebhook** =>Creates a new webhook and returns its details.
      - Method: `POST`
      - Query Params:none
      - Body Params :
        - `subscription_url [String]` (**Required**): A full, valid, publicly accessible URL. Determines where to send the notifications.
        - `event_action[String]` (**Required**):Type of action to receive notifications about. Wildcard will match all supported actions. Values=[added,updated].
        - `event_object[String]` (**Required**): Type of object to receive notifications about. Wildcard will match all supported objects. Value=[Person].
        - `user_id[int]` :The ID of the user this webhook will be authorized with. If not set, current authorized user will be used. Example:
          ``` javascript
	      {
	       "subscription_url":"www.google.com",
	       "event_action":"added",
	       "event_object":"person",
	       "user_id":12281097
               }

    - **PATH**: **/webhookHit** =>This is the mock webhook path which will be called for desired action on our object, returns Person's data with Activity and SIQ Stop Value in Json.Expecting input to be in Json.
      - Method: `POST`
      - Query Params:none
      - Body Params : Webhook metadata in Json format.
          ```
	            {
			    "v": 1,
			    "matches_filters": {
			      "current": [],
			      "previous": []
			    },
			    "meta": {
			      "v": 1,
			      "action": "added",
			      "object": "deal",
			      "change_source": "app",
			      "id": xxx,
			      "company_id": xxxxx,
			      "user_id": xxxxx,
			      "host": "company.pipedrive.com",
			      "timestamp": 1523440213,
			      "timestamp_micro": 1523440213384700,
			      "permitted_user_ids": [],
			      "trans_pending": false,
			      "is_bulk_update": false,
			      "pipedrive_service_name": false,
			      "matches_filters": {
				"current": [],
				"previous": []
			      },
			      "webhook_id": xxx
			    },
			    "retry": 0,
			    "current": (the object data as of this update),
			    "previous": (the object data prior to this update),
			    "event": "event name"
			  }
  
              
     

 - NotesService, PersonService,WebhookService => Interfaces
 - NotesServiceImpl, PersonServiceImpl,WebhookServiceImpl => Function implementation classes
