# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.SBProject.hello-spring' is invalid and this project uses 'com.SBProject.hellospring' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/#build-image)

## Adding Project to the GIT
1. Install Git into your system. 
2. Then open project in IntelliJ 
3. Go to VCS Menu ---> Enable VCS integration option ---> Select Git. This will create empty git repository in local system and this folder will be hidden. 
4. Again Go to VCS --> Remotes --> A window will pop up. 
5. Create a GIT repository on GitHub --> Copy the URL of Git Repo and paste it into the URL field of the popup --> Click OK. 
6. In Commit window there will be files which are not part of GIT repo. 
7. Now **Commit and Push** onto the Git. 
8. While committing for the first time it will ask to login to GitHub using Login or Token Method.
<hr>

## SECTION 1 - Spring Boot 3
### SpringBoot DevTools
### Actuators
### Securing the Endpoints
### Custom Application Properties

<hr>

## SECTION 2 - Spring Core
### Inversion of Control
### Dependency Injection
### Constructor Injection
### Component Scanning
### Setter Injection

* Injecting dependencies by calling setter methods on the class.
* We can use any method name and autowire it.
* Example for setter injection --
      
```java
//the setCoach method can have other name as well as far as it is Autowired
@Autowired
public void setCoach(Coach theCoach) 
{
    myCoach = theCoach;
}
```


### Field Injection
* No longer used
* used on legacy projects
* Makes code harder to unit test
* Inject dependencies by setting field values on your class directly even private fields
* Accomplished by using Java Reflection
* Diectly autowire the Field. No need for constructor or setter method for injection.
* Example --

```java
@Autowired 
private Coach myCoach
```

### Qualifiers

* Spring will scan **_@Component_** on the class and if it finds them, it injects it.
* But, if there are multiple implementations then it provides following error  -- <br>
```error
  Parameter 0 of constructor in com.SBProject.hellospring.rest.DemoController required a single bean, but 2 were found:
  baseballCoach
  cricketCoach 
```
* Hence, we can use **_@Qualifier_** in Constructor and Setter injection, and it will select the class that is mentioned in the Qualifier.
* If we do not mention **_@Component_** over the class then it is not recognized as Spring Bean.
* Example --<br>

```java
    @Autowired
    public DemoController(@Qualifier("baseballCoach") Coach theCoach){
        myCoach = theCoach;
    }
```



* Here the **_@Qualifier("baseballCoach")_** is the default class implementation that will be considered. It is same name as class name except first character is lower-case.
* If the class mentioned in the Qualifier is changed/wrongly named then it throws following error -- <br>

```error 
  Parameter 0 of constructor in com.SBProject.hellospring.rest.DemoController required a bean of type 'com.SBProject.hellospring.common.Coach' that could not be found.<br>
  The injection point has the following annotations:<br>
  @org.springframework.beans.factory.annotation.Qualifier("baseballCoac")
  The following candidates were found but could not be injected:
  User-defined bean
  User-defined bean
```  
  

### Primary Annotation
* Instead of using Qualifier use an annotation **@Primary** over a class.
* There is only one class can be marked as Primary.
* When **_@Primary_** and **_@Qualifier_** is used together, then @Qualifier has higher priority.
* Example --<br>
  
```java
@Component
@Primary
public class TennisCoach implements Coach{ 
    @Override 
    public String getDailyWorkout() {
        return "Practice your backhand volley!";
    }
}
```

* If multiple **_@Primary_** used on various classes then it throws an error.

```error
ERROR -- No qualifying bean of type 'com.SBProject.hellospring.common.Coach' available: more than one 'primary' bean found among candidates: [baseballCoach, cricketCoach, tennisCoach, trackCoach]
```

### Lazy Initialization
* By default, when application starts all beans are initialized, Spring will create an instance of each and make them available.
* Instead of creating all beans we can specify lazy initialization.
* A bean will only be initialized when --
  * It is needed for dependency injection
  * It is explicitly requested
* Add **_@Lazy_** annotation to a given class.
* We can add lazy initialization to all the classes with Global configuration. Once this property is added, classes are only initialized when the endpoint is hit.  
```properties
spring.main.lazy-initialization = true
```
* On creating constructor with print statements for all the classes, it prints output on the console for all the classes, i.e. all the classes are initialized. 
```java
//BaseballCoach class
@Component
public class BaseballCoach implements Coach{

    public BaseballCoach(){
        System.out.println("In constructor: "+ getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Spend 30 mintues in batting practice!!";
    }
}

//Tennis Coach
@Component
public class TennisCoach implements Coach{

  public TennisCoach(){
    System.out.println("In constructor: "+ getClass().getSimpleName());
  }
  @Override
  public String getDailyWorkout() {
    return "Practice your backhand volley!";
  }
}

// In the same way all the classes like TrackCoach, CricketCoach and DemoController constructor are added.
```
Following is the output on the console -- 
```output
In constructor: BaseballCoach
In constructor: CricketCoach
In constructor: TennisCoach
In constructor: TrackCoach
In constructor: DemoController
```

* When **_@Lazy_** annotation is added to TrackCoach class, then its constructor is not initialized.
```java
package com.SBProject.hellospring.common;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class TrackCoach implements Coach{

    public TrackCoach(){
        System.out.println("In constructor: "+ getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Run a hard 5K!";
    }
}
```
Following is the output on the console where output of TrackCoach constructor is not initialized --
```output
In constructor: BaseballCoach
In constructor: CricketCoach
In constructor: TennisCoach
In constructor: DemoController
```
  
### Bean Scopes
* Scope refers to life cycle of bean.
* Default scope is **SINGLETON**. 
* **Singleton** -- spring container creates only one instance of the bean by default. It is cached in memory and all dependency injections for the bean will reference the same bean.
* Other additional scopes are -- Prototype, Request, Session, global-session.

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CricketCoach implements Coach{

    public CricketCoach(){
        System.out.println("In constructor: "+ getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout(){
        return "Practice fast bowling for 15 minutes :) :)";
    }
}
```

Here **_@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)_** creates new bean instance and hence its objects don't point to same bean.

### Java Configuration Bean
* **_@Configuration_** is a configuration class for configuring spring using custom approach.
* **_@Bean_** -- used to configure Bean which has method and returns instance of that class.
  * Makes an existing third-party class available to Spring framework. 
  * May not have access to the source code of third party class and have only JAR file of the class and we have to use it.

```java
//We are not using @Component as we need to configure it using custom approach.
public class SwimCoach implements Coach{

    public SwimCoach(){
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Swim 1000 meters as warmup!";
    }
}

@Autowired
public DemoController(@Qualifier("swimCoach") Coach theCoach){
    System.out.println("In constructor: "+ getClass().getSimpleName());
    myCoach = theCoach;
}
```
We have not used **_@Component_** for the SwimClass and initialized in the Controller and hence it throws below error.
```error
Parameter 0 of constructor in com.SBProject.hellospring.rest.DemoController required a bean of type 'com.SBProject.hellospring.common.Coach' that could not be found.

The injection point has the following annotations:
	- @org.springframework.beans.factory.annotation.Qualifier("swimCoach")
```

To resolve the above problem use following steps -- 
1. Create a package called **_Config_**
2. **_@Configuration_** class -- In config package create a Configuration class with @Configuration annotation.
3. Define @Bean method to configure the bean.

```java
package com.SBProject.hellospring.config;

@Configuration
public class SportConfig {

    @Bean
    public Coach swimCoach(){           // bean id defaults to the method name with first letter lowercase
        return new SwimCoach();
    }
}
```
<hr>

## SECTION 3 - Hibernate/JPA CRUD

### Hibernate/JPA Overview
* Hibernate is for persisting/saving Java Object in a database.
* www.hibernate.org/orm
* It handles all low-level SQL.
* Minimizes the amount of JDBC code you have to develop
* Hibernate provides ORM.

Object to Relational Mapping (ORM) - A developer defines mapping between Java class and database table.<br>
Jakarta Persistence API (JPA) - Standard API for ORM. Defines set of interfaces.


### Setting up MySQL Database and Spring Boot Application

* MYSQL includes two components - **MySQL Database Server and MySQL Workbench**
* MySQL Database Server is the main engine of database. It stores data for database
* MySQL Workbench is a GUI for interacting with the database
* Add following dependencies in the project --
  * MySQL Drivers - **_mysql-connector-j_**
  * Spring Data JPA - **_spring-boot-starter-data-jpa_**
* Add sql url, user and password in the application.properties file --
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_tracker
spring.datasource.username=springstudent
spring.datasource.password=springstudent
```

### JPA Annotations
* Hibernate is the default JPA implementation in SpringBoot.
* **Entity Class** - 
  * Java class that is mapped to a database table.
  * Must be annotated as **_@Entity_**
  * Must have public/protected no-arg constructor
  * The class can have other constructor
1. **Entity and Table annotation -**
```java
@Entity
@Table(name="student")
public class Student {
}
```

2. **Column annotation -**
```java
@Column(name="first_name")
private String firstName;
```

3. **To specify Primary Key -**
   * Use @Id and @GeneratedValue 
   * Various Id generation stategies can be used like - 
     * GenerationType.AUTO
     * GenerationType.IDENTITY
     * GenerationType.SEQUENCE
     * GenerationType.TABLE
   * You can also define custom generation strategy
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) 
@Column(name="id")
private int id;
```

### Saving the Java Object

* To perform CRUD operations we required - 
1. Create a **Data Access Object (DAO)** - DAO is responsible for interfacing with the database. This is common design pattern.
2. Various methods can be used like save(), findById(), deleteAll().
3. DAO needs JPA entity manager. Entity manager is the main component for saving/retrieving entities. 
4. Entity manager needs a data source. Data source defines a database connection. Both are automatically created by Spring boot.

* Created DAO interface and implemented its method into the class -
```java
public interface StudentDAO {
    void save(Student theStudent);
}
```

```java
@Repository
public class StudentDAOImpl implements StudentDAO{

    //define field for entity manager
    private EntityManager entityManager;

    //inject entity manager using constructor injection

    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // implements save method
    @Override
    @Transactional
    public void save(Student theStudent) {
        entityManager.persist(theStudent);
    }
}
```
* **_@Transactional_** - it automatically begins and ends a transaction for JPA code.

### Reading an Object

```java
@Override
public Student findById(Integer id) {
    return entityManager.find(Student.class, id);
}
```

### Updating the Object
```java
@Override
@Transactional
public void update(Student theStudent) {
    entityManager.merge(theStudent);
}
```


### Removing the Object
```java
@Override
@Transactional
public void delete(Integer id) {
    //retrieve the student
    Student theStudent = entityManager.find(Student.class, id);

    //delete the student
    entityManager.remove(theStudent);
}
```

### Create Database Tables from Java Code
* JPA/Hibernate provides an option to automatically create database tables using annotations.
* When you run the application JPA/Hibernate will drop tables then create them based on annotations in JAVA code.
* Various property values which can be used are -- **none, create-only, drop, create, update, and validate** 

Add the following property in application.properties file -- 
```properties
# Configure JPA/Hibernate to auto create the tables
spring.jpa.hibernate.ddl-auto=create
```

**NOTE-** On using **create** property, it drops the table every time and creates new table. The data is lost in this case. If one wants to keep the data as it is then use **update** property.

## SECTION 3 - REST Crud APIs


* To load the data only once use - **_@PostConstruct_**
* Example - 
```java
    //define @PostConstruct to load the student data.. only called once!
    @PostConstruct
    public void loadData() {
        theStudents = new ArrayList<>();
        theStudents.add(new Student("Yash", "Patel", "yash@gmail.com"));
        theStudents.add(new Student("Vishwa", "Patel", "vishwa@gmail.com"));
        theStudents.add(new Student("Jainil", "Patel", "jainil@gmail.com"));
    }
```

### Path Variables 

```java
//define endpoint or "/students/{studentId}" - return student at index
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){
        return theStudents.get(studentId);
    }
```
### REST Exception Handling
1. Create custom error response class
```java
public class StudentErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
    
    //Define constructors, getters and setters for the class.
}
```

2. Create custom exception class inherits from RuntimeException
```java
// inherits from RuntimeException
public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundException(Throwable cause) {
        super(cause);
    }
}
```
3. Update REST service to throw exception
```java
@GetMapping("/students/{studentId}")
public Student getStudent(@PathVariable int studentId){
    if((studentId>= theStudents.size()) || (studentId < 0)){
        throw new StudentNotFoundException("Student id is not found -  " + studentId);
    }
    return theStudents.get(studentId);
}
```
4. Add a exception handler using **_@ExceptionHandler_**
```java
 //Add an exception handler using @ExceptionHandler
@ExceptionHandler
public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){
    //create a student respons
    StudentErrorResponse error = new StudentErrorResponse();
    error.setStatus(HttpStatus.NOT_FOUND.value());
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());
        
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
```

```output
On running the api - http://localhost:8080/api/students/333
Output received when student is not present- 
{
    "status": 404,
    "message": "Student id is not found -  333",
    "timeStamp": 1707186744330
}
```

* Add a generic exception handler -- to handle all exceptions
```java
//Add an exception handler to catch any exception (catch all)
@ExceptionHandler
public ResponseEntity<StudentErrorResponse> handleException(Exception exc){
    StudentErrorResponse error = new StudentErrorResponse();

    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());

    return new ResponseEntity<>(error, HttpStatus. BAD_REQUEST);
}
```

### REST Global Exception Handling

1. Create new **_@ControllerAdvice_**
2. Add exception handling code to **_@ControllerAdvice_**

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentRestExceptionHandler {

  // add exception handler code here
  //Add an exception handler using @ExceptionHandler
  @ExceptionHandler
  public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){
    //create a student response
    StudentErrorResponse error = new StudentErrorResponse();
    error.setStatus(HttpStatus.NOT_FOUND.value());
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  //Add an exception handler to catch any exception (catch all)
  @ExceptionHandler
  public ResponseEntity<StudentErrorResponse> handleException(Exception exc){
    StudentErrorResponse error = new StudentErrorResponse();

    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());

    return new ResponseEntity<>(error, HttpStatus. BAD_REQUEST);

  }
}
```