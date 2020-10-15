# **jv-internet-shop** 

## general description
This project presents an online store with basic functionality.  
The project has such models as: Product, shopping cart, order, user, role. A product represents an item placed in a store.  
A shopping cart contains an item that a user wants to buy. An order is a completed application for the purchase of a product. The final state of the shopping cart transitions to the order state.   
The user represents the customer of the store. The available user functionality depends on the role.  
The role can be administrator, user. After registration, the user receives the default role - User. Only authorized users have access to the store's functionality.

##### ***ADMINISTRATOR:***
Can view all orders and shopping carts;
+ Can add / delete products from the database;
+ Can create / delete orders; 
+ Can create / delete shopping cart;
+ Can delete users;
+ Have all functional of USER
##### ***USER:***
+ Can add a product to the shopping cart;
+ Can place an order;
+ Can view their orders.

##### ***This project used technologies such as:***
+ Java 8
+ JDBC
+ Tomcat
+ Servlets
+ MqSQL
+ OOP, SOLID, RBAC principles, n-tier architecture.