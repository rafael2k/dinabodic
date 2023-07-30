##
# _DinaBodic Makefile
#
JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: dinabodic.jar

dinabodic.jar:
	$(JAVAC) -cp ../javaPlay/javaPlay.jar *.java
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: dinabodic.Main" >> manifest.txt
	@echo "" >> manifest.txt

	rm -rf dinabodic
	mkdir dinabodic
	mv *.class dinabodic
	cp -a ../javaPlay/javaPlay .
	cp -a ../media/* .
	jar -cmf manifest.txt DinaBodic.jar dinabodic javaPlay
clean:
	rm -rf *.class DinaBodic.jar javaPlay
	rm manifest.txt
