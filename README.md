# jPAX
A Java object tree implementation based on the Composite Pattern for reading and writing _any_ XML - no need for an XSD.

```
 ███████████    █████████   █████ █████
▒▒███▒▒▒▒▒███  ███▒▒▒▒▒███ ▒▒███ ▒▒███ 
 ▒███    ▒███ ▒███    ▒███  ▒▒███ ███  
 ▒██████████  ▒███████████   ▒▒█████   
 ▒███▒▒▒▒▒▒   ▒███▒▒▒▒▒███    ███▒███  
 ▒███         ▒███    ▒███   ███ ▒▒███ 
 █████        █████   █████ █████ █████
▒▒▒▒▒        ▒▒▒▒▒   ▒▒▒▒▒ ▒▒▒▒▒ ▒▒▒▒▒ 
                                       
```

## Introduction
Each created object can be stored as a node in a tree. There are no special types. Any object can start a fresh tree by
being root. A root can store _children_. Those can be _added_ or _set_. Any children can be fetched _by tag_ or in case
of _many_ with the same name, _by listing and filtering_. Each object can also store attributes. Those are organized in
the same way as child but are used as a list. By any object, the tree can be recursed and generated to any data format.
Currently, the generation of XML im implemented only.

## HowTo
The following examples show how to use jPAX.

### Creating a root and adding Children
Children can be added by string for their _tag name_ or by a factory added as object.
```java
IPax root = Instances.Factory().produce("root"); // produce a pax node as named root
root.Child().add("child1"); // add a child node
root.Child().add("child2");
IPax child3 = Instances.Factory().produce("child3");
root.Child().add(child3);
```

### Creating Attributes
Following the code from above, _any attribute_ may be added to _any object_.
```java
root.Child().get("child2").Attrib().add("is", "active");
IPax child = root.Child().get("child3");
child.Attrib().add("is", "inactive");
```

### Generating XML and writting it
Any tree node can generate itself. Therefore, one can select the generation.

From code above to be continued.
```java
String xml = root.XML();
System.out.println(xml);
```

### Parsing XML to object Tree Node
Any XML can be parsed to an object tree node, without support of an XSD.

From code above to be continued.
```java
IPax loaded = Reader.Instance.parse("./root.xml");
String xml_ = loaded.XML();
System.out.println(xml_);
```

Alternatively, one can parse a _Java InputStream_.

```Java

String xml; // keeping the xml as string, e.g.
IPax loaded = Reader.Instance.stream(new ByteArrayInputStream(xml.getBytes()));
```

## Build

Gradle Wrapper is generated in version 9; try:
```bash
./gradlew build jar
```

If you need to settle another version of the wrapper, get the latest gradle version and [install it](https://gradle.org/install/#manually).

The change to your cloned directory of jPAX and
```bash
gradle wrapper
./gradlew build
```
