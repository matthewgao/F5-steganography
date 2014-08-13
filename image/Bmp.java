package image;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.awt.image.MemoryImageSource;

public class Bmp {
    int iDataOffset;
    int pixel[]=null;
    BufferedInputStream imageFile;

    public Bmp(String fileName) {
	try {
	    imageFile = new BufferedInputStream(new FileInputStream(fileName));
	    readBitmapFileHeader();
	    readBitmapInfoHeader();
	    pixel=new int[biWidth*biHeight];
	    int padding=(3*biWidth)%4;
	    if (padding > 0)
		padding = 4-padding;
	    int offset;
	    for (int y=1; y<=biHeight; y++) {
		offset = (biHeight-y)*biWidth;
		for (int x=0; x<biWidth; x++)
		    pixel[offset+x]=readPixel();
		for (int x=0; x<padding; x++)
		    imageFile.read();
	    }
	} catch (Exception e) {
	    System.out.println(fileName+" is not a true colour file.");
	    System.exit(1);
	}
    }
    
    int readInt() throws IOException {
	int retVal = 0;
	
	for (int i=0; i<4; i++)
	    retVal += (imageFile.read()&0xff) << (8*i);
	return retVal;
    }
    
    int readPixel() throws IOException {
	int retVal = 0;
	
	for (int i=0; i<3; i++)
	    retVal += (imageFile.read()&0xff) << (8*i);
	return retVal|0xff000000;
    }
    
    int readShort() throws IOException {
	int retVal;
	
	retVal = (imageFile.read()&0xff);
	retVal += (imageFile.read()&0xff) << 8;
	return retVal;
    }

    int bfSize;
    int bfOffBits;

    void readBitmapFileHeader() throws Exception {
	if (imageFile.read() != 'B')
	    throw new Exception();
	if (imageFile.read() != 'M')
	    throw new Exception();
	bfSize = readInt();
	readInt();  // ignore 4 bytes reserved
	bfOffBits = readInt();
    }

    int biSize;
    int biWidth;
    int biHeight;
    int biPlanes;
    int biBitCount;
    int biCompression;
    int biSizeImage;
    int biXPelsPerMeter;
    int biYPelsPerMeter;
    int biClrUsed;
    int biClrImportant;

    void readBitmapInfoHeader() throws Exception {
	biSize = readInt();
	biWidth = readInt();
	biHeight = readInt();
	biPlanes = readShort();
	biBitCount = readShort();
	if (biBitCount != 24)
	    throw new Exception();
	biCompression = readInt();
	biSizeImage = readInt();
	biXPelsPerMeter = readInt();
	biYPelsPerMeter = readInt();
	biClrUsed = readInt();
	biClrImportant = readInt();
    }

    public Image getImage() {
	MemoryImageSource mis;
	mis = new MemoryImageSource(biWidth, biHeight, pixel, 0, biWidth);
	return Toolkit.getDefaultToolkit().createImage(mis);
    }
}
