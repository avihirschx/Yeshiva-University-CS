#!/bin/bash

#NOTE: the pipe (|) command must be enclosed in quotation marks ("|")!!

clear

echo "---------Starting Script---------"
echo "beginning object-oriented implementation..."
echo
echo "compiling in java..."

javac ./JavaOO/*.java

echo "running in java..."

echo
java -cp ./JavaOO ProcessController "$@"
echo

echo "running in python..."

echo
python ./PythonOO/OOProject.py "$@"
echo

echo "running in javascript..."

echo
node ./JavascriptOO/OOProject.js "$@"

#Leave some space before functional!
echo
echo

echo "beginning functional implementation..."
echo
echo "compiling java..."

javac ./JavaFunc/FunctionalProject.java

echo "running in java..."

echo
java -cp ./JavaFunc FunctionalProject "$@"
echo

echo "running in python..."

echo
python ./PythonFunc/FunctionalProject.py "$@"
echo

echo "running in javascript..."

echo
node ./JavascriptFunc/FunctionalProject.js "$@"
echo