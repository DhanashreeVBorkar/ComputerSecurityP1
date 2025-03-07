JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Mono.class

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
