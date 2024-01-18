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
* Install Git into your system.
* Then open project in IntelliJ
* Go to VCS Menu ---> Enable VCS integration option ---> Select Git. This will create empty git repository in local system and this folder will be hidden.
* Again Go to VCS --> Remotes --> A window will pop up.
* Create a GIT repository on GitHub --> Copy the URL of Git Repo and paste it into the URL field of the popup --> Click OK.
* In Commit window there will be files which are not part of GIT repo.
* Now **Commit and Push** onto the Git.
* While committing for the first time it will ask to login to GitHub using Login or Token Method.

## Section 1
### SpringBoot DevTools
### Actuators
### Securing the Endpoints
### Custom Application Properties


## Section 2
### Inversion of Control
### Dependency Injection
### Constructor Injection
### Component Scanning
### Setter Injection

* Injecting dependencies by calling setter methods on the class.
* We can use any method name and autowire it.
* Example for setter injection --<br>
      //the setCoach method can have other name as well as far as it is Autowired
  <br>    @Autowired
    <br>public void setCoach(Coach theCoach) {
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;myCoach = theCoach;
    <br>}


### Field Injection
* No longer used
* used on legacy projects
* Makes code harder to unit test
* Inject dependencies by setting field values on your class directly even private fields
* Accomplished by using Java Reflection
* Diectly autowire the Field. No need for constructor or setter method for injection.
* Example --

@Autowired <br>
private Coach myCoach

### Qualifiers
* Spring will scan @Component on the class and if it finds them, it injects it.
* But, if there are multiple implementations then it provides following error  -- <br>

  **ERROR -- Parameter 0 of constructor in com.SBProject.hellospring.rest.DemoController required a single bean, but 2 were found:<br>
  baseballCoach<br>
  cricketCoach**

* Hence, we can use <b>@Qualifier</b> in Constructor and Setter injection, and it will select the class that is mentioned in the Qualifier.
* If we do not mention @Component over the class then it is not recognized as Spring Bean.
* Example --<br>
    @Autowired
  <br> public DemoController(@Qualifier("baseballCoach") Coach theCoach){
  <br>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; myCoach = theCoach;
  <br>   }


* Here the **@Qualifier("baseballCoach"** is the default class implementation that will be considered. It is same name as class name except first character is lower-case.
* If the class mentioned in the Qualifier is changed/wrongly named then it throws following error -- <br>
  
  **ERROR -- Parameter 0 of constructor in com.SBProject.hellospring.rest.DemoController required a bean of type 'com.SBProject.hellospring.common.Coach' that could not be found.<br>
  The injection point has the following annotations:<br>
  @org.springframework.beans.factory.annotation.Qualifier("baseballCoac")
  The following candidates were found but could not be injected:
  <br>User-defined bean
  <br>User-defined bean**
  

### Primary Annotation
* Instead of using Qualifier use an annotation **@Primary** over a class.
* There is only one class can be marked as Primary.
* When @Primary and @Qualifier is used together, then @Qualifier has higher priority.
* Example --<br>

    @Component
  <br>    @Primary
  <br>public class TennisCoach implements Coach{
  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;@Override
  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public String getDailyWorkout() {
  <br>    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return "Practice your backhand volley!";
  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}
  <br>}

* If multiple @Primary used on various classes then it throws an error.

  **ERROR -- No qualifying bean of type 'com.SBProject.hellospring.common.Coach' available: more than one 'primary' bean found among candidates: [baseballCoach, cricketCoach, tennisCoach, trackCoach]**  


