# Makefile for F5

CLASS= \
image/Bmp.class \
randomX/randomX.class \
randomX/randomLEcuyer.class \
crypt/F5Random.class \
crypt/Permutation.class \
james/JpegEncoder.class \
ortega/HuffTable.class \
ortega/HuffmanDecode.class \
Embed.class \
Extract.class

all:	f5

.java.class:
	javac $*.java

.SUFFIXES:  .java .class

f5:	$(CLASS)

clean:
	rm $(CLASS) *.jpg output.txt
