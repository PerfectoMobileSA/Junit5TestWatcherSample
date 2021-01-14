### Sample Junit 5 + Test watcher project

![CircleCI status](https://circleci.com/gh/PerfectoMobileSA/Junit5TestWatcherSample.svg?style=shield "CircleCI status")

This sample project is designed to get you up and running within few simple steps.

Begin with installing the dependencies below, and continue with the Getting Started procedure below.

### Dependencies
There are several prerequisite dependencies you should install on your machine prior to starting to work with this project:

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

* An IDE to write your tests on - [Eclipse](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/marsr) or [IntelliJ](https://www.jetbrains.com/idea/download/#)

* [Maven](https://maven.apache.org/)


Eclipse users should also install:

1. [Maven Plugin](http://marketplace.eclipse.org/content/m2e-connector-maven-dependency-plugin)


IntelliJ IDEA users should also install:

1. [Maven Plugin for IDEA](https://plugins.jetbrains.com/plugin/1166)


## Downloading the Sample Project

* Clone this repository.

* After downloading and unzipping the project to your computer, open it from your IDE by choosing the folder containing the pom.xml 

**********************


## Running sample as is


* Open TestBase.java </p>

* Search for the below line and replace `<<cloud name>>` with your perfecto cloud name (e.g. demo) or pass it as maven properties: `-DcloudName=<<cloud name>>`</br>  
		&nbsp;&nbsp;	&nbsp;&nbsp; String cloudName = `"<<cloud name>>"`;
		</br>
		</p>
* Search for the below line and replace `<<SECURITY TOKEN>>` with your perfecto [security token](https://developers.perfectomobile.com/display/PD/Generate+security+tokens) or pass it as maven properties: `-DsecurityToken=<<SECURITY TOKEN>>` </br></p>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String securityToken = `"<<SECURITY TOKEN>>"`;
	</br>

Note: Refer to official documentation on how to execute from eclipse / IntelliJ. </br>
* Run pom.xml with the below maven goals & properties when: </p>
   a. If credentials are hardcoded:
		
		clean
		install
		test
		
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b. If credentials are passed as parameters:
		
		clean
		install
		test
		-DcloudName=${cloudName}
		-DsecurityToken=${securityToken}
</p>

* CI dashboard integration can be performed by supplying the below maven parameters

		clean
		install
		test
		-DcloudName=${cloudName}
		-DsecurityToken=${securityToken}
		-Dreportium-job-name=${JOB_NAME} 
		-Dreportium-job-number=${BUILD_NUMBER} 
		-Dreportium-job-branch=${GIT_BRANCH} 
		-Dreportium-tags=${myTag}