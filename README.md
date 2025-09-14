# jPAX
A Java object tree implementation based on the Composite Pattern for reading and writing _any_ XML - no need for an XSD.

## Introduction
Each created object can be stored as a node in a tree. There are no special types. Any object can start a fresh tree by
being root. A root can store _children_. Those can be _added_ or _set_. Any children can be fetched _by tag_ or in case
of _many_ with the same name, _by listing and filtering_. Each object can also store attributes. Those are organized in
the same way as child but are used as a list. By any object, the tree can be recursed and generated to any data format.
Currently, the generation of XML im implemented only.

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

## ChangeLog
The change log in upwards chronology.
- 20250914
  - Established gradle build environment,
  - created this readme file and published _this_ repository.
- 20240827
  - First implementation in Java was established,
  - Changes concerning the utilisation of IPax are settled,
  - name of base class changed from ICompo to IPax.
- 20230701
  - Reimplementation of sub-structure established,
  - introduced ISubset as base for IChildren and IAttributes. 
- 20170723
  - Concept and idea were created,
  - Project was named ICompo instead IPax,
  - first implementation settled in C++,
  - licensed by the MIT license.