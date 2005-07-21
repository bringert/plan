JAVAC = jsr14_javac # -warnunchecked

STAT_FILES = src/*.java src/*/*.{java,jj}
SRC_FILES = src/*.java src/*/*.java src/parser/parser.java

TODAY=`date '+%y%m%d'`


default all: $(SRC_FILES)
	cd src && $(JAVAC) *.java */*.java 

src/parser/parser.java: src/parser/parser.jj
	cd src/parser && javacc parser.jj

javadoc:
	mkdir -p doc/javadoc
	javadoc -d doc/javadoc src/*.java src/*/*.java

test: 
	cd src && time java plan ../test.scheme < /dev/null

clean:
	find . -name '*.class*' -exec rm -f {} ';'
	-rm -rf doc/javadoc

genclean: 
	-rm -f src/parser/*.java

distclean: clean genclean
	-rm -f plan.zip

stats: genclean
	@echo `ls $(STAT_FILES) | wc -l` "files"
	@echo `cat $(STAT_FILES) | grep -v '^$$' | wc -l` "non-empty lines"
	@echo `cat $(STAT_FILES) | wc -c` "bytes"

dist: distclean
	mkdir -p dist/plan-$(TODAY)
	find * -maxdepth 0 ! -name dist -exec cp -a {} dist/plan-$(TODAY) ';'
	find dist -name CVS | xargs rm -rf
	cd dist && zip -r ../plan-$(TODAY).zip plan-$(TODAY)
	rm -rf dist