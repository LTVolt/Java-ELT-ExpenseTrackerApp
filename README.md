# Java-ELT-ExpenseTrackerApp

Major project using Java, this time combining the backend with a frontend for a full-stack approach.

Documents on how to use both the backend and the frontend will be on each respective folders, while this one will serve as documentation.

**Backend Documentation:**

This project utilizes authentication in order to create and manage a stable userbase, and each user has access to their own dashboard with their own expenses.
As such, 2 Entites were created: User, and Expense. A User is nothing more than a JPA entity mapped to the users table in the database. It is used in Auth, links to expenses by using the @OneToMany annotation, and in security layers though roles. Therefore, a User entity has:

1. An id - the unique identifier, user does not control this number, as it is the auto incremented primary key for the database;
2. A username - the unique login credential indentifying the user to themselves;
3. A password - An SHA encrypted password using base64 decryption along with a 256+ byte admin defined seed;
4. A role - An authorization level used to let each user access different parts of the backend / frontend;
5. A token - It's a JWT generated using the HS256 algorithm upon login. This token allows for Authorization when using backend methods. Resets upon logout, or expires after 10 hours.

Similarly, an Expense, which is the main businees entity representing each individual expense for each user, has:

1. An id - the unique identifier, it is the auto incremented primary key for the database;
2. A description - Short explanation for what the expense is for;
3. An amount - number representing how much the expense was for;
4. A date - timestamp signaling when the expense was processed;
5. A category - An enum separating expenses by category;
6. A user - This is the database's foreign key that allows identification of each expense.

Needing several Users and Expenses, it is natural to create Repositories for each Entity. These are Spring Data JPA repos that handle CRUD operations and abstract SQL operaions.

They both manage access to each table in the DB, the Users one making use of both the AuthService and ExpenseService detailed further down, and Expense one only the latter.
