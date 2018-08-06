# Steganography Software F5
[![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/matthewgao/F5-steganography?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This package is meant to demonstrate a new steganographic algorithm.  It
is a very preliminary version to embed files into true colour BMP, GIF,
or JPEG images.  To have secure steganography choose a good passphrase.
It is also recommended to scan a new carrier image in true colour BMP format
for every new steganographic message.  Delete the carrier BMP after you
created the lossy compressed steganogram.

The attacks presented on the third Workshop on Information Hiding are not
successful with F5.
http://wwwrn.inf.tu-dresden.de/~westfeld/attacks.html

## How to run
To run this software you need a Java runtime environment.  There are two
shell scripts to demonstrate what you can do with this software:

e (stands for encrypt)
This is the embedding script merging the two files bandits.bmp and
bin.noise to one single file bandits.jpg.  This JPEG image is the data
to delivered.  The receiver of this file can extract the hidden message
using the second script

d (stands for decrypt)
which extracts a file output.txt from bandits.jpg.  Output.txt and
bin.noise are equal.

To run this software from a DOS prompt you have e.bat and d.bat.  To run
it with Microsoft's c:\windows\jview.exe you have ms_e.bat and ms_d.bat.

I make no warranty about the usability of this code.  It is for
educational purposes and should be regarded as such.

Best regards,

Andreas Westfeld
westfeld@inf.tu-dresden.de
http://www.inf.tu-dresden.de/~aw4



Branch Notes

HuffmanDecode.java
Fixed an issues with it hanging if given a progressive JPEG. This is what
was causing that "Nf weder 1 noch 3" error. Now just throws IOException.

JpegEncoder.java and Embed,java
Changed the default comment to throw off suspicion. Now lies and claims to
be the product of a popular PHP jpeg lib. 
