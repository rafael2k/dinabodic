all:
	rm -rf build
	mkdir build
	javac -d . javaPlay/src/javaPlay/*.java
	jar cvf javaPlay.jar javaPlay/*.class
	javac -d build -cp ./javaPlay.jar DinaBodic/src/dinabodic/*.java
	cp DinaBodic/*.jpg DinaBodic/*.png DinaBodic/*.wav DinaBodic/*.scn build/
	mkdir build/javaPlay
	mv javaPlay/*.class build/javaPlay/

run:
	java dinabodic.Main

clean:
	rm -rf build javaPlay/*.class javaPlay.jar dinabodic.jar
