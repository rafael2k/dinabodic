##
# javaPlay Makefile
#
JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: javaPlay.jar

javaPlay.jar:
	javac *.java
	mkdir javaPlay
	mv *.class javaPlay
	jar -cf javaPlay.jar javaPlay/*

clean:
	rm -rf javaPlay javaPlay.jar
