# JCIC
JCIC stands for Joy of Coding - Interactive Code. This open-source project will provide web services during the 2017 workshop of the Joy of Coding conference
For more info visit http://joyofcoding.org/


# License
The JCIC project is made possible by Finalist, and by their choice, this code is completely open-source. 
Therefor this project uses the Creative Commons (CC0) License. Anyone can view or copy this code for their own purposes and create derivative work for both personal and commercial use. This project uses plugins and libraries from other sources to which this CC0 license does not extend, and these may have their own licenses. 
By using this source code it is now yours, and we are no longer responsible for what happens with it in any way. 
Please read the license : https://creativecommons.org/publicdomain/zero/1.0/


# How to get started
This Java project is a Maven project. The project can be opened and runned in Netbeans without the need of Java EE. 
Through Maven it downloads Spring, Springboot, Swagger and all the other plugins and libraries automatically when you build the project. You do not need to install these manually.
When you run it, it will try to host the webservices on http://localhost:8080 and you can test the web services yourself with whatever browser you prefer by going to that link.
Make sure the 8080 port is not in use. This project has been tested on both Windows and OS X.

JCIC-Visuals is a Unity project. You can open this in Unity, but it will be lacking third party assets. 
You will need to acquire and import these external assets to get the project to work:
https://www.assetstore.unity3d.com/en/#!/content/32351
https://www.assetstore.unity3d.com/en/#!/content/73764

In Unity, importing these assets will create some folders. Move in Unity the following folders in to a folder called "External Assets":
Gems Ultimate pack
SampleScenes
Standard Assets

The assets should then work, you might have to restart the project.


# Branches
Joy of Coding - Interactive Code
This project uses feature branches: for each feature a branch is created that is merged and deleted once that specific feature is complete.
The features that will be implemented are placed on a JIRA project with the same name, that is only accessible to other colleagues at Finalist.
To get the latest stable code, please use the master branch.


# Code Coverage
JCIC uses JaCoCo for Java code coverage. To build with JaCoCo you must use the following Maven Commands :
-mvn clean test
-mvn jacoco:report

You can run these manually through a terminal if you have Maven installed; or you can add these to the Maven Build of Netbeans. 
In Netbeans you can do this through Options -> Java -> Maven -> Edit Global Custom Goal Definitions -> Add.
Then, you will have to "build with dependencies". 

JaCoCo generates a website with all the data on it. You can find the site at target -> site -> index.html.


# Class Structure
app : Contains application, config and threading classes for running the application.
app.entity : Entity classes represent the data objects that can be stored by the application or shared by web services.
app.dao : Data access layer, Spring-data Repository package.
app.service : Service layer, contains service logic that validates requests.
app.rest : Resource/Controller layer with Spring-web, handles incoming REST requests to access game data from outside. 
app.exception : Custom exceptions with Spring-web annotations that help with better HTTP error handling.


# Credits
Dion van Dam (Finalist Intern) - Software Developing and documentation

Martin van Amersfoorth (Joy of Coding, Finalist) - Project leading as product owner

Martijn van der Maas (Finalist) - Tutoring and reviewing
