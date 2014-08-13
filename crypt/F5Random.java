package crypt;
import sun.security.provider.SecureRandom;

public class F5Random {
    private SecureRandom random=null;
    private byte[] b=null;

    public F5Random(byte[] password) {
	random = new SecureRandom();
	random.engineSetSeed(password);
	b = new byte[1];
    }

    // get a random integer 0 ... (maxValue-1)
    public int getNextValue(int maxValue) {
	int retVal = getNextByte()
			| (getNextByte() << 8)
			| (getNextByte() << 16)
			| (getNextByte() << 24);
	retVal %= maxValue;
	if (retVal<0)
	    retVal += maxValue;
	return retVal;
    }

    // get a random byte
    public int getNextByte() {
	random.engineNextBytes(b);
	return b[0];
    }
}
