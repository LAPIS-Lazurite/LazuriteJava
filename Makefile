#CLASSPATH = -classpath ./jcommon-1.0.23.jar:./jfreechart-1.0.19.jar
SRCS = $(wildcard $(SRCPATH)/*.java)
SRCPATH = lib/com/lapis_semi/lazurite/io
LIBPATH =  /usr/lib/jvm/jdk-8-oracle-arm-vfp-hflt/jre/lib/ext
JAVAC = /usr/bin/javac
JAVA = /usr/bin/java
JAR = /usr/bin/jar

all:
	$(JAVAC) $(CLASSPATH) -g -Xlint $(SRCS)

.PHONY: clean
clean:
	rm -f $(SRCPATH)/*.class
	rm SubGHz.jar 

install:
	jar cvf SubGHz.jar -C lib/ com
	sudo cp SubGHz.jar $(LIBPATH)

