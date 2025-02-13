# jsp-servlet-ecommerce

## Features include
```
1. Login and Registration.
2. Products Management
   2.1 List products
   2.2 Add new product
   2.3 Edit product
   2.4 Delete product
3. Order product
   3.1 Search and browse items.
   3.2 Add to cart.
   3.3 Manage shopping cart.
   3.4 Checkout.
4. Orders management
5. Update customer/product information.
6. Chatbot AI with OpenAI
```

## Steps to run application
#### Step 01: Checkout source code from git
```
$ git clone https://github.com/tuantranquoc2102/jsp-servlet-ecommerce.git
```

#### Step 02: Build .WAR file JSP Application
```
$ mvn clean package
```

#### Step 03: Run application with Docker
```
$ docker-compose up --build -d
```