# _DinaBodic

DinaBodic is a 2D starship platform game written in Java.

For more information, read the ![report about the game](relatorio.pdf) (in Portuguese).

![](screen1.png)

# Building

To build, make sure you have "javac" and "jar", and run:

```
make
```

# Running

Download or build the "DinaBodic.zip" file and run:

```
unzip DinaBodic.zip
cd DinaBodic
java -jar DinaBodic.jar
```

# Troubleshooting Java sound problem

On Linux (Debian 11), add the following lines to "/etc/java-11-openjdk/sound.properties":

```
javax.sound.sampled.Clip=com.sun.media.sound.DirectAudioDeviceProvider
javax.sound.sampled.Port=com.sun.media.sound.PortMixerProvider
javax.sound.sampled.SourceDataLine=com.sun.media.sound.DirectAudioDeviceProvider
javax.sound.sampled.TargetDataLine=com.sun.media.sound.DirectAudioDeviceProvider
```


# Author

Graphics were created by Samir Piccolotto Issa, VFX and sound
by Felipe Ridolfi. Code was created by Rafael Diniz.

