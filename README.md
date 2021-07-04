# SIQ-Test-Project
1. SIQCommonController => Common Controller Class for all the Api Calls. Actions :
a)getPersonByName/Id=>return the person's data in Json format.  

b)addWebhook=>Add a webhook on Pipedrive,action and object type can be passed. Input will be json object. Ex:
{
	 "subscription_url":"www.google.com",
	 "event_action":"added",
	 "event_object":"person",
	 "user_id":" ",
	 "http_auth_user":" ",
	 "http_auth_password":" "
}

c)webhookHit=>this is the webhook path which will be triggered for desired action on our object, returns Person data with Activity and SIQ Stop Value in Json.Expecting input to be in Json.

d)updatePerson=> It will update the person's details, by Id. Passed parameters and person's Id and the updated info Json. Ex:
{
    "name": "Aditya",
    "owner_id": "01",
    "phone": [
        "8952000277"
    ]
}

e)addNotes=> add a note for the person,input Json.Ex:
{
"content":"Too many notes",
"user_id":2020,
"person_id":8
}

2.NotesService, PersonService,WebhookService => Interfaces
3.NotesServiceImpl, PersonServiceImpl,WebhookServiceImpl => Function implementation classes
