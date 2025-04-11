1.	Install MySQL
2.	create database test;
3.	create 3 tables by using below queries
A.
CREATE TABLE test.CUSTOMER (
    customer_id BINARY(16) DEFAULT (UUID())  PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL ,
    customer_slug VARCHAR(255) NOT NULL default ""  
);

B.
CREATE TABLE test.PARCEL (
    parcel_id INT AUTO_INCREMENT PRIMARY KEY,
    origin_country_id VARCHAR(255) NOT NULL ,
    destination_country_id VARCHAR(255) NOT NULL ,
    weight float NOT NULL,
    created_at datetime Not NULL,
    customer_id BINARY(16) Not NULL,    
    tracking_Number  VARCHAR(16) default "",
    count int not null,
    foreign key (customer_id) references CUSTOMER(customer_id)
);

C.
CREATE TABLE test.TRACKING (
    tracking_Number  VARCHAR(16) PRIMARY KEY,
    parcel_id INT Not NULL,
    foreign key (parcel_id) references PARCEL(parcel_id)
);


4. Edit DB details in Springboot Application.properties file as per the installed db credentials
spring.datasource.url=
jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

5.Start the application

6. First Create Customer and Parcel in DB using the below APIS
      Note : Calling APIS in postman


A. 
Create customer in DB by calling below API
Method: Post
URL: http://localhost:8080/application/customer/createCustomer
Input : 
{

   "customerName":  "Sameena Logistics",    
    "customerSlug" : "sameena-logistics"    
   
}
Output:
{
    "customerId": "c4d05348-03a7-4196-a8b2-8bcca80546d2",
    "customerName": "Sameena Logistics",
    "customerSlug": "sameena-logistics"
}




B. 
Create Parcel in DB by calling below API
Method: Post
URL: http://localhost:8080/application/parcel/createParcel
Input : 
{

    "originCountryId": "MY",
    
     "destinationCountryId" : "SG",
    
     "weight": 100,

    "customerId":"c4d05348-03a7-4196-a8b2-8bcca80546d2"
}
Note : Take customerID from the firstAPI Response

Output:
{
    "parcelId": 15,
    "originCountryId": "MY",
    "destinationCountryId": "SG",
    "weight": 100.0,
    "createdAt": "2025-04-10T17:04:24-07:00",
    "trackingNumber": "SAMC4D05151",
    "count": 1,
    "customer": {
        "customerId": "c4d05348-03a7-4196-a8b2-8bcca80546d2",
        "customerName": "Sameena Logistics",
        "customerSlug": "sameena-logistics"
    },
    "customerId": "c4d05348-03a7-4196-a8b2-8bcca80546d2"
}






7. Generate next tracking number for the parcel by calling below API
Method: GET
URL: http://localhost:8080/application/tracknumber/next-tracking-number
Input : 
{
   "customerName":  "Sameena Logistics",    
    "customerSlug" : "sameena-logistics"    ,
    "originCountryId": "MY",    
     "destinationCountryId" : "SG", 
     "weight": 100,
    "customerId":"c4d05348-03a7-4196-a8b2-8bcca80546d2"
}

Output : 
{
    "createdAt": "2025-04-10T17:04:58-07:00",
    "trackingNumber": "SAMC4D05152"
}

After hitting again

{
    "createdAt": "2025-04-10T17:05:21-07:00",
    "trackingNumber": "SAMC4D05153"
}


Note : Same input data CAN be submitted , every time it generates unique tracking number for the parcel.
Note:
Formula for Tracking Number :
customerID 3 characters – parcelID 3 characters -parcelcount
Each parcel has count column while will be incremented in every  tracking number request.

Note. Postman can be used to test this apis
