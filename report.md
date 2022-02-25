#RESTful Café API

[https://rawdin.se/restfulcafe](https://rawdin.se/restfulcafe)  
[Postman collection](collection.json)  
[API documentation](Rawdin_API_Documentation.txt) (also available below)

##Introduction

RESTful Cafe is a coffeeshop that specializes in the finest coffee beans from around the world and offers great coffee drinks made with the best quality beans.  
The cafe offers an API for its employees and customers that lists available coffee beans and drinks available on its menu.  
Customers can register for an account and login to the application in order to subscribe and get notified when new coffee beans or drinks are added to the menu.  
The café employs a number of employees that handles the coffee beans that are available and the drinks on the menu. These employees need to be assigned an account in order to manage these resources.

##The application

To get out of my comfort zone of working with JavaScript and Node.js, I decided to build this project in Java. The application is written with Java 17, Maven, Spring Framework and JWT-auth package with a MySQL database as the backend.
The application is deployed on my personal server and domain (https://rawdin.se). The traffic and communication with the API should be done with HTTPS.

##HATEOAS

From the entry point of the API, users are greeted with a list of sub-directories that are publicly available for users (beans, drinks and orders). Each of these sub-directories contain a link that makes it possible for the users to browse through the resources of each sub-directory. Some of the directories of the API are not listed in the entry point since they may contain information regarding registered users and employees, these are therefore protected (and hidden) for only the user groups.  
Each sub-directory of the API provides a list of the resources through the GET method. With an identifier attached to the URI, users can also view a specific resource. Authorized users can additionally perform POST, PUT and DELETE actions on the resources.
Using hypermedia, each resource has links to its target URI (self) and its link relation type (or parent directory) (rel). This makes the API browsable and the users can navigate and traverse through resources in similar fashion as with web pages or web applications.  

##Multiple representations of the resources

The Bean and Drink resources can be accessed through two different types of identifiers, the ID or name of the resources. I chose to design these URIs this way since the initial way of only being able to access the resources by their IDs as identifier didn’t feel so “user-friendly”. When the user tries to access a specific resource with a valid URI, the application will verify if the identifier is a numeric ID or name string and will find it accordingly.  

Examples: 
Both of these URIs point to the same resource.
```https://rawdin.se/restfulcafe/beans/1```
```https://rawdin.se/restfulcafe/beans/sidamo```

##Webhooks

Registered customers can sign up for subscriptions of new beans and drinks that are added. When users subscribe for these events through the ```https://rawdin.se/subscribe``` URI, they must specify the URI to receive the subscription on, the event that they want to subscribe to and a secret to be used with the webhook. The available events are "NEWBEAN" and "NEWDRINK". Only registered users are allowed to sign up for the webhooks subscriptions. These subscriptions are then stored in the database. When any of the events is triggered, the application will loop through all subscribers, make a POST request with the resource (together with HATEOAS links) that has been added as the payload. The secret that was provided by the user will be attached to the ```x-app-secret``` header in the POST request. 
When I initially wrote the webhook functionality, it was done in a blocking way and when there were many subscribers to manage, the process of adding a resource took a longer time. I therefore rewrote this and the sequence of sending the POST requests is now done asynchronously.

##Security and Authentication/Authorization

When creating an account in the application, the user needs to be assigned with a role (customer, employee, manager, or administrator). Many of the applications methods and routes are limited for these user roles. Most of the POST, PUT and DELETE methods are restricted for registered users, the only exceptions for this are the register and order routes (both POST).  
When a new account is registered in the application, the provided password is encrypted using ```bcrypt``` hashing function. Authentication and authorization in the application are managed through the usage of JWT tokens. When a user properly logins to the application, an access and refresh token is provided for the user to perform the restricted actions. The JWT token has a payload containing the username, role, and lifetime of the token. The access token has a lifetime of 10 minutes before it expires. A new access token can be obtained after it expires with the refresh token that is provided. This refresh token has a longer lifespan of 30 minutes. The secret key that is used is a random 20 character (numeric and alphabetical) long string and is passed to the application through an environment variable. 
I may have wanted to try with OAuth/Social Login for this assignment but found it to possibly be complicated to implement with the learning curve required.

##Assignment reflection and on what could be improved

Working with this assignment has given me the opportunity to further explore REST API and discovering new concepts such as HATEOAS. Having touched on working with building an API before, this assignment gave me additional experience and a deeper understanding of designing APIs. I got to explore new technologies such as the Spring framework which I understand is a widely used framework for building APIs in the industry.  
As one of the improvements that I may have wanted to implement is additional HATEOAS links for the resources such as next and previous links. I had some challenges implementing the functionality of this properly, so it was unfortunately left out.  
There is one minor issue with the HATEOAS links in the resources which is that the links have HTTP scheme and not HTTPS as they suppose to. My server and domain supports HTTPS, but even through longer troubleshooting of both the application and the reverse proxy (nginx), I was unable to resolve this before the hand-in.  
Besides these, I may have also wanted to improve the database design with better relationships between the resources. 

## Linguistic design

The application follows the following rules of linguistic design

- [x] rule 1: Forward slash separator (/) must be used to indicate a "hierarchical relationship".  
It feels logical and familiar to read URIs with the forward slash to indicate hierarchy. Example: ```https://rawdin.se/restfulcafe/drinks/mocha```
- [x] rule 2: A trailing forward-slash (/) should not be included in URIs.  
A trailing forward-slash feels redundant when specifying the URI (even though the API allows this).
- [x] rule 4: Underscores (_) should not be used in URIs.  
I chose to not have underscores in the URIs since this adds a bit of complexity when specifying or reading the URI.
- [x] rule 5: Lowercase letters should be preferred in URI paths.  
Only having lowercase letters simplifies the readability of the URI. 
- [x] rule 6: File extensions should not be included in URIs.  
Having file extensions is redundant and complicated to have in the URIs. My API does not support this.
- [x] rule 7: A "singular" noun should be used for document names.  
Logical to have a singular noun for a single document/resource. 
- [x] rule 8: A "plural" noun should be used for collection names.  
Logical to have a plural noun for a collection. Also adds clarity of location for the user.
- [x] rule 10: CRUD function names or their synonyms should not be used in URIs.  
For simplicity of using and managing the resources, specifying the HTTP method to perform CRUD actions are enough.
- [x] rule 11: A verb or verb phrase should be used for controller names.  
Adds clarity and readability for the functionality that can be performed on the URI.

##URIs


| URI                                                | HTTP Method | Response code (expected) | Protected (Auth) | Description                                  | Notes                                                                 |
| -------------------------------------------------- | ----------- | ------------------------ | ---------------- | -------------------------------------------- | --------------------------------------------------------------------- |
| https://rawdin.se/restfulcafe/                     | GET         | 200                      | No               | Entry point of the API                       | List all public subdirectories (beans, drinks, orders)                |
| https://rawdin.se/restfulcafe/beans                | GET         | 200                      | No               | List of all Bean resources                   |                                                                       |
| https://rawdin.se/restfulcafe/beans                | POST        | 201                      | Yes              | Add Bean resource                            |                                                                       |
| https://rawdin.se/restfulcafe/beans/{id}           | GET         | 200                      | No               | Single Bean resource                         | URI with ID of the resource as identifier                             |
| https://rawdin.se/restfulcafe/beans/{id}           | PUT         | 200                      | Yes              | Update single Bean resource                  | URI with ID of the resource as identifier                             |
| https://rawdin.se/restfulcafe/beans/{id}           | DELETE      | 204                      | Yes              | Delete single Bean resource                  | URI with ID of the resource as identifier                             |
| https://rawdin.se/restfulcafe/beans/{name}         | GET         | 200                      | No               | Single Bean resource                         | URI with name of the resource as identifier                           |
| https://rawdin.se/restfulcafe/beans/{name}         | PUT         | 200                      | Yes              | Update single Bean resource                  | URI with name of the resource as identifier                           |
| https://rawdin.se/restfulcafe/beans/{name}         | DELETE      | 204                      | Yes              | Delete single Bean resource                  | URI with name of the resource as identifier                           |
| https://rawdin.se/restfulcafe/drinks               | GET         | 200                      | No               | List of all Drink resources                  |                                                                       |
| https://rawdin.se/restfulcafe/drinks               | POST        | 201                      | Yes              | Add Drink resource                           |                                                                       |
| https://rawdin.se/restfulcafe/drinks/{id}          | GET         | 200                      | No               | Single Drink resource                        | URI with ID of the resource as identifier                             |
| https://rawdin.se/restfulcafe/drinks/{id}          | PUT         | 200                      | Yes              | Update single Drink resource                 | URI with ID of the resource as identifier                             |
| https://rawdin.se/restfulcafe/drinks/{id}          | DELETE      | 204                      | Yes              | Delete single Drink resource                 | URI with ID of the resource as identifier                             |
| https://rawdin.se/restfulcafe/drinks/{name}        | GET         | 200                      | No               | Single Drink resource                        | URI with name of the resource as identifier                           |
| https://rawdin.se/restfulcafe/drinks/{name}        | PUT         | 200                      | Yes              | Update single Drink resource                 | URI with name of the resource as identifier                           |
| https://rawdin.se/restfulcafe/drinks/{name}        | DELETE      | 204                      | Yes              | Delete single Drink resource                 | URI with name of the resource as identifier                           |
| https://rawdin.se/restfulcafe/employees            | GET         | 200                      | Yes              | List of users with Employee roles            | Users with Employee, Manager or Admin roles                           |
| https://rawdin.se/restfulcafe/customers            | GET         | 200                      | Yes              | List of users with Customer role             |                                                                       |
| https://rawdin.se/restfulcafe/users                | GET         | 200                      | Yes              | List of all user resources                   |                                                                       |
| https://rawdin.se/restfulcafe/users                | POST        | 201                      | Yes              | Add User resource                            |                                                                       |
| https://rawdin.se/restfulcafe/users/{id}           | GET         | 200                      | Yes              | Single User resource                         |                                                                       |
| https://rawdin.se/restfulcafe/users/{id}           | PUT         | 200                      | Yes              | Update single User resource                  |                                                                       |
| https://rawdin.se/restfulcafe/users/{id}           | DELETE      | 204                      | Yes              | Delete single User resource                  |                                                                       |
| https://rawdin.se/restfulcafe/register             | POST        | 201                      | No               | Register new customer account                |                                                                       |
| https://rawdin.se/restfulcafe/login                | POST        | 200                      | Yes              | Login for users                              |                                                                       |
| https://rawdin.se/restfulcafe/login/refresh        | GET         | 200                      | Yes              | Get new access token with refresh token      |                                                                       |
| https://rawdin.se/restfulcafe/subscribe            | POST        | 201                      | Yes              | Register for webhook subscription            | Alternative URI for customers/end-users                               |
| https://rawdin.se/restfulcafe/subscriptions        | GET         | 200                      | Yes              | List of all Subscription (webhook) resources |
| https://rawdin.se/restfulcafe/subscriptions        | POST        | 201                      | Yes              | Register for webhook subscription            |                                                                       |
| https://rawdin.se/restfulcafe/subscriptions        | PUT         | 200                      | Yes              | Update webhook resource                      |                                                                       |
| https://rawdin.se/restfulcafe/subscriptions        | DELETE      | 204                      | Yes              | Delete webhook resource                      |                                                                       |
| https://rawdin.se/restfulcafe/orders               | GET         | 200                      | Yes              | List of all Order resources                  |                                                                       |
| https://rawdin.se/restfulcafe/orders/{id}          | POST        | 200                      | No               | Make a new Order                             |                                                                       |
| https://rawdin.se/restfulcafe/orders/{id}          | GET         | 201                      | Yes              | Single Order resource                        |                                                                       |
| https://rawdin.se/restfulcafe/orders/{id}/complete | PUT         | 200                      | Yes              | Complete an Order                            | Sets an Order status to SERVED (Order must be in status PREPARING)    |
| https://rawdin.se/restfulcafe/orders/{id}/cancel   | DELETE      | 204                      | Yes              | Cancels an Order                             | Sets an Order status to CANCELLED (Order must be in status PREPARING) |