##
# _DinaBodic top-level Makefile

all: DinaBodic.jar

DinaBodic.jar: java dinabodic
	mkdir DinaBodic
	mv src/DinaBodic.jar DinaBodic
	cp -a media/* DinaBodic
	zip DinaBodic.zip DinaBodic/*

java:
	$(MAKE) -C javaPlay

dinabodic:
	$(MAKE) -C src

clean:
	$(MAKE) -C javaPlay clean
	$(MAKE) -C src clean
	rm -rf DinaBodic DinaBodic.zip
