# Decide

The project is an implementation of an anti-ballistic missile system's launch interceptor program based on _An experimental evaluation of the assumption of independence in multiversion programming_ by J. C. Knight and N. G. Leveson from 1986.

## Short overview  

The project provides a class ```AntiMissileSystem``` with a method ```decide()``` that generate a boolean signal which determines whether an interceptor should be launched based upon input radar tracking information (e.g. an array of 2D-coordinates, representing the missile's position at different points in time). The input radar tracking information is provided when an object of ```AntiMissileSystem``` is constructed together with additional information about the configuration of the system.

Besides the ```AntiMissileSystem``` class the project contains the classes ```Parameters``` which is a helper class to keep track of the system's configurations, a ```Point``` class to help represent the input radar tracking information and a ```Connector``` enumeration class used to describe certain aspects of the system's configuration. All of these can be found in the ```main``` subfolder.

## Requirements
- **Java JDK**: 1.8 or higher
- **Maven**: 3.5.2 or higher

## User manual

To use the anti-ballistic missile system simply create an instance of the ```AntiMissileSystem``` class providing the following parameters:

- **numPoints**: the number of planar data points from the radar tracking
- **points**: an array of ```Point``` instances representing the missiles position at different points in time
- **parameters**: an instance of the ```Parameters``` class configured with the correct parameter values for the system
- **lcm**: the _logical connector matrix_ for the system in the form of a 15x15 symmetric matrix consisting of ```Connector``` enum types.
- **puv**: the preliminary unlocking vector for the system in the form of a 15 element boolean vector.

After an instance has been created simply call the ```decide()```method and an answer will be provided based on the data provided and the configuration of the system.

## Testing

The anti-ballistic system has been tested using happy path tests, all of which can be found in the ```AntiMissileSystemTest``` class in the ```test``` subfolder.

## Built With
- [Maven](https://maven.apache.org/) - Dependency Management

## Contributions

- **Johannes Westlund**
  - Helped create the skeleton for the project.
  - Implemented the requirements of LIC 2.
  - Implemented the requirements of LIC 3.
  - Implemented the requirements of LIC 9.
  - Implemented the requirements of LIC 10.
  - Implemented the requirements of LIC 11.
  - Implemented the requirements of LIC 12.

- **Kai Böhrnsen**
   - Helped create the skeleton for the project.
   - Implemented the setup of Maven as the build tool.
   - Implemented the requirements of LIC 0.
   - Implemented the requirements of LIC 1.
   - Implemented the requirements of LIC 14.

- **Sara Ervik**
   - Helped create the skeleton for the project.
   - Set up Slack channel for communication.
   - Implemented the requirements of LIC 5.
   - Implemented the requirements of LIC 6.
   - Implemented the requirements of LIC 7.
   
- **Madeleine Berner**
   - Helped create the skeleton for the project.
   - Created Wiki on GitHub.
   - Created How-we-code in Wiki.
   - Implemented the requirements of LIC 4.
   - Implemented the requirements of LIC 8.
  
For more detailed information see [closed issues](https://github.com/OscarMelin/decide/issues?q=is%3Aissue+is%3Aclosed).

## Abbreviations

**LIC**: Launch Interceptor Condition
