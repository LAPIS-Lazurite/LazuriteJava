#CLASSPATH = -classpath ./jcommon-1.0.23.jar:./jfreechart-1.0.19.jar
SRCS = $(wildcard ./*.java)
JAVAC = /usr/bin/javac
JAVA = /usr/bin/java

all:
	$(JAVAC) $(CLASSPATH) -g -Xlint $(SRCS)

.PHONY: clean
clean:
	rm -f *.class
