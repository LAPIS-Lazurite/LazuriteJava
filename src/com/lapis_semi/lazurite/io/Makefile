#CLASSPATH = -classpath ./jcommon-1.0.23.jar:./jfreechart-1.0.19.jar
SRCS = $(wildcard ./*.java)
JAVAC = /usr/bin/javac
JAVA = /usr/bin/java

all:
	$(JAVAC) $(CLASSPATH) -g -Xlint $(SRCS)

.PHONY: clean
clean:
	rm -f *.class

install:
	jar cvf SubGHz.jar -C ../../../../ com
	sudo cp SubGHz.jar /usr/lib/jvm/jdk-8-oracle-arm-vfp-hflt/jre/lib/ext/

