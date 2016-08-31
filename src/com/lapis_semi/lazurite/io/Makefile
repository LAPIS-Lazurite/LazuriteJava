#CLASSPATH = -classpath ./jcommon-1.0.23.jar:./jfreechart-1.0.19.jar
SRCS = $(wildcard ./*.java)
JAVAC = /usr/bin/javac
JAVA = /usr/bin/java

all:
	$(JAVAC) $(CLASSPATH)  -g -Xlint $(SRCS)

.PHONY: clean
clean:
	rm -r -f *.class *.jar bak html latex doxygen_sqlite3.db

install:
	jar cvf Liblazurite.jar -C ../../../../ com
	sudo cp Liblazurite.jar /usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/jre/lib/ext/

