# StarLion
StarLion is a visualiation tool (java application) for RDFS/XML files. It can load ontologies expressed in .rdf/.rdfs files or stored at the FORTH-ICS Semantic Web Knowledge Middleware and present them as graphs to the end user.

## Features

StarLion supports a great variety of intersting features like Top-K diagrams, forece directed layout algorithms, various exploration modes including star-graphs, etc.

### Force Directed Placement Algorithms (FDPAs)

The Force Directed Placement (FDP) visualization algorithm which is implemented in StarLion tries to achieve an optimal positioning of nodes suitable for the visualization of RDF/S information. The algorithm is based on three types of forces:

* the electrical repulsion between the nodes,
* the stiffness of the springs and
* the magnetic field which applies to "subclassof" (ISA) edges.

### Star-Graph Exploration Method

Star-Graph mode allows the gradual exploration of big ontologies by visualizing only a part of the graph at each time. 

### Top-K Diagrams

StarLion allows the provision of Top-K diagrams for aiding the process of understanding large in size ontologies. Currently it supports three Top-K methods:

* Graph Degree Method
* Random Surfer Explicit
* Random Surfer Inferred

### List View Mode

With List View mode the user can get an overview of all the classes and properties which exist in the .rdfs file in text representation. There is a also a field for quickly viewing the superclass of a class by clicking on any class and the subproperties of a property by clicking on a any property.

### Semi-Automatic Layout Process

The procedure of drawing the graph can be done entirely automatic. To match exactly the preferences of the users StarLion supports various options making the procedure semi-automatic. They can change the position of nodes by clicking and moving the mouse around and they can change the default layout parameters of the force directed placement algorithm to achieve different layouts based on their needs. Furthermore StarLion offers options for nailing down nodes (in order not to be moved from the Force Directed Placement Algorithms), selecting and hiding of some others.

### Multiple Namespaces

Multiple namespace support is one of StarLion's distinctive features. The user is able to load the main ontology and all the correlated ontologies at the same time. The different namespaces are visualized with different colours which the user is able to change according to their preferences.

## Publications 

* Stamatis Zampetakis, Yannis Tzitzikas, Asterios Leonidis, Dimitris Kotzinos, "Star-like auto-configurable layouts of variable radius for visualizing and exploring RDF/S ontologies", Journal of Visual Languages & Computing, Available online since 9 March 2012, ISSN 1045-926X, 10.1016/j.jvlc.2012.01.002.
* Stamatis Zampetakis, Yannis Tzitzikas, Asterios Leonidis, Dimitris Kotzinos StarLion: Auto-Configurable Layouts for Exploring Ontologies, Procs of the 7th Extended Semantic Web Conference (Demo Track), ESWC'2010, Heraklion, Greece, June 2010
* Yannis Tzitzikas, Dimitris Kotzinos and Yannis Theoharis,On Ranking RDF Schema Elements (and its Application in Visualization), Journal of Universal Computer Science (JUCS), Special Issue: Ontologies and Their Applications, Nov 2007

## Read More

More details can be found on the public page of StarLion at https://projects.ics.forth.gr/isl/starlion
