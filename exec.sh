#!/bin/bash
#
# Starting up java application by getting and setting 
# environement variables and dynamically runtime dependencies.
#
# Make sure your taught build.gradle.kts the following lines:
#
# tasks.register("printRuntimeClasspath") {
#    println(sourceSets.main.get().runtimeClasspath.asPath)
# }
#
# Christian (graetz23@gmail.com)
#
# created 220226
# updated 260226
#
# Configure these two lines to your needs:
#
JARPATH="./build/libs/pax-1.0.0.jar:"
CLASSPATH="de.graetz23.pax.Main"

#
# No need to change things, unless those got improved.
#

echo "building application .."
GRDLW=$(./gradlew clean jar 2>&1 | tee .logBuild)

echo "retrieving runtime dependencies .."
DEPSFILE=".runtimeClasspaths"
RNTMDEPS=$(./gradlew printRuntimeClasspath | head -n 1)
echo $RNTMDEPS > $DEPSFILE

echo "starting application .."
RUNSERVICE=$(java -cp $JARPATH:$(cat $DEPSFILE) $CLASSPATH 2>&1 | tee .logService)
