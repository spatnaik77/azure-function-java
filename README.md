# azure-function-java
POC for hybrid connection

<h1> How to run this <h1>

1. git clone https://github.com/spatnaik77/azure-function-java
2. mvn clean install
3. mvn azure-functions:run
4. curl http://localhost:7071/api/sendMail?to=siddharth.patnaik@walmartlabs.com   Change the to address accordingly. 
You should see an email in your mail box. 
