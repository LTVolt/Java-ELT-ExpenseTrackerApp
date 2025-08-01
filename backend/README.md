
This document details how to use the backend properly.

First off, you might want to comment out every line EXCEPT the first one. If you don't do that, since this project was made using Docker with Postgres as support for the Databases and Tables, it WILL NOT run. 
Unless of course you create the appropriate Docker Container.

This was done to create a persisting database. If you do not have one, and you stop the Application, every single piece of data you created will be lost, so you would have to register again, add expenses again, etc.

After running the App successfully, you can open the proper backend App (such as Postman, which will be the one I use to demonstrate this) to use the backend.

The first thing you should do is head into the ".../api/auth/register" endpoint. We will set the method to POST.

In the body section, we must tell the backend what username and respective password we want to register, in JSON formatting. It should look something like this:
```JSON
{
"username": "peter",
"password": "password123"
}
```
 
