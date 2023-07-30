##
# _DinaBodic top-level Makefile

all: DinaBodic.jar

DinaBodic.jar: java dinabodic
	mv src/DinaBodic.jar .

java:
	$(MAKE) -C javaPlay

dinabodic:
	$(MAKE) -C src

clean:
	$(MAKE) -C javaPlay clean
	$(MAKE) -C src clean
