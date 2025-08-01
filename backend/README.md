
This document details how to use the backend properly.

Go to the application.properties file in the resources folder of the project.

First off, you might want to comment out every line EXCEPT the first one. If you don't do that, since this project was made using Docker with Postgres as support for the Databases and Tables, it WILL NOT run. 
Unless of course you create the appropriate Docker Container.

This was done to create a persisting database. If you do not have one, and you stop the Application, every single piece of data you created will be lost, so you would have to register again, add expenses again, etc.

After running the App successfully, you can open the proper backend App (such as Postman, which will be the one I use to demonstrate this) to use the backend.

The first thing you should do is head into the ".../api/auth/register" endpoint. We will set the method to POST.

In the body section, as the raw format, we must tell the backend what username and respective password we want to register, in JSON formatting. Make sure there are NO headers. It should look something like this:
```JSON
{
"username": "peter",
"password": "password123"
}
```
In the top right corner of the answer, you should see the code 200 OK, meaning that it worked, and you now have a register token. Mine looks like this:
```JSON
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRlciIsImlhdCI6MTc1NDA1ODE0NSwiZXhwIjoxNzU0MDk0MTQ1fQ.B1yDjpwSCpjMTof1JaW-8_N-0vBjxk_ojAP_AVns9nQ"
}
```
Yours will be different, even if you use the same credentials as I demonstrated here. Copy the result of the token's code, head to the Headers tab right below the URL, and add a Key of type Authorization, and the Value to be as follows: Bearer token. It MUST start with Bearer (Capital B!), have ONE space, and then the token.

After this, had on to the /api/auth/login endpoint, use the POST method as well, leave the body unchanged (we want to login using those credentials) and press send. Again, you should have the 200 OK code, and Postman should give you another token. Here's mine:
```JSON
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRlciIsImlhdCI6MTc1NDA1ODM3MiwiZXhwIjoxNzU0MDk0MzcyfQ.D_Yw3ncPFAwqVKaVnckO8sksdXtof8yvrlGXZ1Ii8p4"
}
```
As you can see, this one is a different token, as it will be used for your session. You are now logged into the system for 10 hours, or until you manually log out. Copy this token and replace the one you have in the Headers tab. We won't be needing to change this anymore until our next session.

Now, head to the /api/expenses endpoint, use the GET method, and send. You should have the 200 OK code again, and the answer should be an empty array ( [] ). This is true because this user does not have any expenses in their dashboard.

So let's add one!

Change the method to POST, and change the body to something like this:
```JSON
{
    "description": "Lunch",
    "amount": 11.99,
    "date": "2025-07-14",
    "category": "FOOD"
}
```
And hit send. Again, you should be greeted with the 200 OK code and you should have a response with the same details AND an ID attached to it. Please keep in mind you syntax must be perfectly correct, such as:
1. Even the variable names must be enclosd in quotes;
2. Numbers do not need quotes;
3. Date must be in YYYY-MM-DD format;;
4. Category, being an Enum, must be in all caps, and a proper enum category. If you would like to add some, head to the Category Enum under the model package and add some there.

Here's what i got:
```JSON
{
    "id": 29,
    "description": "Lunch",
    "amount": 11.99,
    "date": "2025-07-14",
    "category": "FOOD"
}
```
As you can see, eveything checks out, and we also see this expenses' ID. Mine was 29, because this is the 29th expense added overall.

Try adding another expense. Anything.

Now, let's change the method back to GET, and hit send. Now, our array isn't empty anymore! Here's mine:
```JSON
[
    {
        "id": 29,
        "description": "Lunch",
        "amount": 11.99,
        "date": "2025-07-14",
        "category": "FOOD"
    },
    {
        "id": 30,
        "description": "Dinner",
        "amount": 23.99,
        "date": "2025-07-14",
        "category": "FOOD"
    }
]
```

Wanna see the total spent in each category for a month? Simple! Head to the /api/expenses/summary?month=X&year=XXXX endpoint. Change the X's to the relevant month and year. Mine will be 7 and 2025 respectively. Then, use the GET method, and hit send. You should now see the totals for each category for that month! Here's mine:
```JSON
{
    "FOOD": 35.98
}
```
So that means i spent 35.98$ in July of 2025 on Food.

One last thing: Deleting and Editing!

Memorize the id of the expense you want to delete. I'll delete id 30, so i'm heading to the /api/expenses/30 endpoint. Then, change the method to DELETE and send. There should be no body for the answer, only the 200 OK code. Now go back to /api/expenses (NO / at the end!) and use the GET method again.

There you go! Expense deleted!

Editing is similar. Go to the /api/expenses/ID endpoint, change the method to PUT, change the body to the new expense, and hit send. EZPZ.

And that's it!
