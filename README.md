# Decide

The project is an implementation of an anti-ballistic missile system's launch interceptor program based on _An experimental evaluation of the assumption of independence in multiversion programming_ by J. C. Knight and N. G. Leveson from 1986.

### Short overview  

The project provides a class ```AntiMissileSystem``` with a method ```decide()``` that generate a boolean signal which determines whether an interceptor should be launched based upon input radar tracking information (e.g. an array of 2D-coordinates, representing the missile's position at different points in time). The input radar tracking information is provided when an object of ```AntiMissileSystem``` is constructed together with additional information about the configuration of the system.

Besides the ```AntiMissileSystem``` class the project contains the classes ```Parameters``` which is a helper class to keep track of the system's configurations, a ```Point``` class to help represent the input radar tracking information and a ```Connector``` enumeration class used to describe certain aspects of the system's configuration.

### User manual

To use the anti-ballistic missile system simply create an instance of the ```AntiMissileSystem``` class providing the following parameters:

- **numPoints**: the number of planar data points from the radar tracking
- **points**: an array of ```Point``` instances representing the missiles position at different points in time
- **parameters**: an instance of the ```Parameters``` class configured with the correct parameter values for the system
- **lcm**: the _logical connector matrix_ for the system in the form of a 15x15 symmetric matrix consisting of ```Connector``` enum types.
- **puv**: the preliminary unlocking vector for the system in the form of a 15 element boolean vector.
