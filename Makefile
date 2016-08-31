SRCS = $(wildcard $(SRCPATH)/*.java)
SRCPATH = src/com/lapis_semi/lazurite/io
DOCPATH = src/com/lapis_semi/lazurite/io/html
LIBPATH =  /usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/jre/lib/ext
JAVAC = /usr/bin/javac
JAVA = /usr/bin/java
JAR = /usr/bin/jar

all:
	$(JAVAC) $(CLASSPATH) -g -Xlint $(SRCS)

.PHONY: clean
clean:
	rm -f -r $(SRCPATH)/*.class latex html doxygen_sqlite3.db
	rm Liblazurite.jar 

install:
	jar cvf Liblazurite.jar -C src/ com
	sudo cp Liblazurite.jar $(LIBPATH)

uninstall:
	sudo rm $(LIBPATH)/Liblazurite.jar
doc:
	doxygen -s $(SRCPATH)


