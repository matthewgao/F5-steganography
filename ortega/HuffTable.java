/* Version 0.1 of F5 Steganography Software by Andreas Westfeld 1999 */
/*********************************************************/
/*      JPEG Decoder                                     */
/*      Sean Breslin                                     */
/*      EE590 Directed Research                          */
/*      Dr. Ortega                                       */
/*      Fall 1997                                        */
/*                                                       */
/*      HuffTable.class:                                 */
/*            Extracts Huffman table from image header   */
/*            data. Instanciate one class for each table */
/*            in the file the header.                    */
/*                                                       */
/*            Methods:                                   */
/*                 getHUFFVAL(), returns HUFFVAL array   */
/*                 getVALPRT(),  returns VALPTR  array   */
/*                 getMAXCODE(), returns MAXCODE array   */
/*                 getMINCODE(), returns MINCODE array   */
/*                                                       */
/********************** 11/4/97 **************************/
/* /////////////// DISCLAIMER///////////////////////////////// 
   This software is provided by the author and 
   contributors ``as is'' and any express or implied
   warranties, including, but not limited to, the
   implied warranties of merchantability and 
   fitness for a particular purpose are dis-
   claimed. In no event shall the author or con-
   tributors be liable for any direct, indirect,
   incidental, special, exemplary, or consequen-
   tial damages (including, but not limited to,
   procurement of substitute goods or services;
   loss of use, data, or profits; or business 
   interruption) however caused and on any 
   theory of liability, whether in contract,
   strict liability, or tort (including negligence 
   or otherwise) arising in any way out of the use 
   of this software, even if advised of the poss-
   ibility of such damage.				
//////////////////////////////////////////////////////*/ 

// westfeld
package ortega;

import java.io.*;

public class HuffTable {

    // Instance variables
    private int[] BITS = new int[17];
    private int[] HUFFVAL = new int[256];
    private int[] HUFFCODE = new int[257];
    private int[] HUFFSIZE = new int[257];
    private int[] EHUFCO = new int[257];
    private int[] EHUFSI = new int[257];
    private int[] MINCODE = new int[17];
    private int[] MAXCODE = new int[18];
    private int[] VALPTR = new int[17];
    private int Ln, SI, I, J, K, LASTK, CODE;

    // Constructor Method
    public HuffTable(DataInputStream d, int l) {
	dis = d;
// System.out.println("Länge="+l);
	// Get table data from input stream
	Ln=19+getTableData();
// System.out.println(Ln);
	Generate_size_table();  // Flow Chart C.1
	Generate_code_table();  // Flow Chart C.2
	Order_codes();          // Flow Chart C.3
	Decoder_tables();       // Generate decoder tables Flow Chart F.15
    }

    public int getLen() {
	return Ln;
    }

    // IO MethodS
    public int[] getHUFFVAL() { return HUFFVAL; }
    public int[] getVALPTR() { return VALPTR; }
    public int[] getMAXCODE() { return MAXCODE; }
    public int[] getMINCODE() { return MINCODE; }

    private int getByte() {
        try {
            return dis.readUnsignedByte();
        }
        catch (IOException e) { return -1; }
    }

    private int getTableData(){
        // Get BITS list
	    int count=0;
	    for(int x=1;x<17;x++) {
	        BITS[x] = getByte();
		count += BITS[x];
	    }

	    // Read in HUFFVAL
	    for(int x=0;x<count;x++){
		//System.out.println(Ln);
	        HUFFVAL[x] = getByte();
	    }
	    return count;
    }

    private void Generate_size_table() {
	    // Generate HUFFSIZE table Flow Chart C.1
	    K=0; I=1; J=1;
	    while(true) {
	        if(J>BITS[I]) {
	            J=1; I++;
	            if(I > 16)
	                break;
	        }
	        else {
                HUFFSIZE[K++]=I;
                J++;
            }
	    }
	    HUFFSIZE[K] = 0;
	    LASTK = K;
	}

    private void Generate_code_table() {
	    // Generate Code table Flow Chart C.2
    	K = 0; CODE = 0; SI = HUFFSIZE[0];
    	while(true) {
    	    HUFFCODE[K++] = CODE++;

	        if(HUFFSIZE[K] == SI)
		        continue;

		    if(HUFFSIZE[K] == 0)
		        break;

		    while(true){
		        CODE<<=1;
		        SI++;
		        if(HUFFSIZE[K] == SI)
		            break;
	        }
	    }
	}

	private void Order_codes() {
	    // Order Codes Flow Chart C.3
    	K=0;

	    while(true) {
	        I = HUFFVAL[K];
	        EHUFCO[I] = HUFFCODE[K];
		    EHUFSI[I] = HUFFSIZE[K++];
		    if(K >= LASTK) break;
	   }
    }

    private void Decoder_tables() {
        // Decoder table generation Flow Chart F.15
        I = 0; J = 0;
        while(true) {
            if(++I > 16)
                return;

	        if(BITS[I] == 0)
		        MAXCODE[I] = -1;
	        else {
		        VALPTR[I] = J;
		        MINCODE[I] = HUFFCODE[J];
		        J = J + BITS[I] - 1;
		        MAXCODE[I] = HUFFCODE[J++];
            }
        }
    }

    // Declare input steam
    DataInputStream dis;
}
