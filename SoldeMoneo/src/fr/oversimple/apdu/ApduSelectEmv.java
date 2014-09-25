package fr.oversimple.apdu;


public class ApduSelectEmv extends ApduCommand{

	public ApduSelectEmv(byte[] aid) {
		super(new byte[5 + aid.length]);
		command[ISO7816.OFFSET_CLA] = ISO7816.CLA_ISO;
		command[ISO7816.OFFSET_INS] = ISO7816.INS_SELECT;
		command[ISO7816.OFFSET_P1] = (byte) 0x04;
		command[ISO7816.OFFSET_P2] = 0;
		command[ISO7816.OFFSET_P3] = (byte) (aid.length & 0xff);
		System.arraycopy(aid, 0, command, ISO7816.OFFSET_DATA, aid.length);
	}

	
}
