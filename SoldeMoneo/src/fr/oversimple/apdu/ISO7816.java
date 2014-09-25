package fr.oversimple.apdu;

/**
 * 
 * @author Julien Hatin
 *
 */
public class ISO7816 {

	/* Common class */
	public final static byte CLA_ISO = 0;
	public final static byte CLA_EMV = (byte) 0x80;
	
	/* Common instruction */
	public final static byte INS_SELECT = (byte) 0xA4;
	public final static byte INS_GPO = (byte) 0xA8;
	public final static byte INS_READ_RECORD = (byte) 0xB2;
	public final static byte INS_VERIFY = (byte) 0x20;
	public final static byte INS_GENERATE_AC = (byte) 0xAE;
	public final static byte INS_GET_DATA = (byte) 0xCA;
	
	/* Common SW */
	public final static byte[] SW_NORMAL = new byte[]{(byte) 0x90, 0};
	
	/* Command */
	public final static int OFFSET_CLA = 0;
	public final static int OFFSET_INS = OFFSET_CLA + 1;
	public final static int OFFSET_P1 = OFFSET_INS +1;
	public final static int OFFSET_P2 = OFFSET_P1 + 1;
	public final static int OFFSET_P3 = OFFSET_P2 + 1;
	public final static int OFFSET_DATA = OFFSET_P3 + 1;
	
	/* Status word */
	public final static int OFFSET_SW1 = 0;
	public final static int OFFSET_SW2 = OFFSET_SW1 + 1;
	
}
