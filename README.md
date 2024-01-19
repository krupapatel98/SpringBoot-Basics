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


### Setting up MySQL Database 

* MYSQL includes two components - **MySQL Database Server and MySQL Workbench**
* MySQL Database Server is the main engine of database. It stores data for database
* MySQL Workbench is a GUI for interacting with the database 