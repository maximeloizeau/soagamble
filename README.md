soagamble
=========

It is an implementation of an automatic gambling system specially designed for the course "Architecture for Service-based systems".

The application is divided into three parts: Workflow editor, workflow parser/visualizer, and the application allowing to launch the betting sequences and to display the results from the betting services.

The back-end consists of a RSP (Reseach Service Platform) project embedded in a GWT project in order to communicate between the front and the back-end (see diagram).


## Installation and launch procedure


You need to configure Eclipse to be able to compile and run a GWT project. You only need a few additionnal packages as stated on the [documentation](http://www.gwtproject.org/usingeclipse.html) : 
- Install new software from Eclipse by using this website url : http://dl.google.com/eclipse/plugin/4.3 (change the version if needed)
- Check to install :
    - Plugin > Google plugin for Eclipse
    - SDKs > Google App Engine Java SDK and Google Web Toolkit SDK

Import the project in eclipse.
*> If there are some import problems with google packages, go to your project properties > Google > Web Toolkit > and make sure "Use GWT" is checked.*
Then launch the project (Right click on it > Run As > Web application)


## Libraries

GWTEventService (https://code.google.com/p/gwteventservice/) : to realize the communication between the GWT server and the GWT client.

Snap.svg (http://snapsvg.io) : to facilitate the drawing of shapes in SVG


## Contributor


@AmauryC
@t3hExi
@ohlala