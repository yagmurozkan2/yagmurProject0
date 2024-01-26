Before we even get started with our project, let's go back into an architectural review of everything involved in our java project.

Java 
- Compiled
  - Code that is more-or-less human-readable needs to converted into a machine-readable format (in this case called bytecode) prior to being run.
  - .java file gets converted into a .class file 
- High level 
  - A high-level language provides many abstractions of some "background logic" of the programming language
    - Memory management: When we ask Java to create a variable, or create an object, the process of allocation memory, and when the object is no longer in use, 
    for deallocating memory, is automatically handled by Java (garbage collection)
  - "Syntactic Sugar"
    - The language is verbose/easy to read (comparatively to other languages)
- OOP
  - Java mandates that we use the Object-Oriented Programming paradigm to make anything.
    - Classes & Objects
      - Classes are what we're writing.
      - Classes define what an Object is capable of doing.
        - State (some variables, data that it might store)
          - Eg, a Scanner needs to keep of track of what its input source is. 
        - Behavior (methods for that object)
          - Eg, a Scanner behaves in a certain way depending on method inputs/the object's stored data - eg it knows to behave differently for different input 
          sources.
      - Objects must be instantiated in order to be used. This is done bya constructor, which is a special method that "sets up" an object.
      - We can break out of using OOP using the 'static' keyword. (We no longer have to create an object prior to using a variable or method.)

Maven
- Compiling things in Java generally works fine, but on a large scale, it's helpful to have a tool that "manages" a project - a place to store metadata, 
versioning, external dependencies, that also provides the commands necessary to compile, test, jar our project, as well as commands to manage our dependencies.
  - 'mvn'
    - mvn compile
    - mvn test (for test cases)
    - mvn package (for jarfiles)
- Maven requires a certain structure for us to use it (src/main/java, src/main/test, target/classes)
  - Maven is aware of this structure of the project. It knows to search the entire Java directory for everything that we might use - and knows to send the 
  compiled code to classes.
  - Maven is able to connect external .jar files (for your dependencies) to our project.
    - Jar is a convenient way to transfer java code -  eg it's by external dependencies, it's also the format that code which is about to be deployed to the 
    cloud is transferred in.
  - Maven can also manage metadata/versioning - if we were to change the project name, description, etc - the most useful piece of configuration here is 
  determining the version of java that the code is compiled to 
  - Maven provides a single file that manages configuration of the project - pom.xml (project object model)

GIT 
- Git is a version control tool
- It allows us to manage version ("commits"), which, is also going to be necessary for collaboration (because effectively everyone works on different "version" 
of the project simultaneously) and also for general project management (eg keeping track of when issues are resolved or when feature are implemented)
  - git init (creates a local .git file that keeps track of all the versions of this project)
  - git add. (tells git to keep track of all files in this directory for the following commit)
  - git commit - m "message" (create a new version of the project)
  - git push (send the new versions to the remote repository)
  - git pull
  - git clone 
- A merge conflict happens when git doesn't know how to combine different commits to the same file. 
  