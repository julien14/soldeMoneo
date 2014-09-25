package fr.oversimple.apdu;

import java.util.ArrayList;
import java.util.List;

public class ApduReadRecord extends ApduCommand {

	public static List<ApduCommand> getRecordList(byte[] afl) {
		ArrayList<ApduCommand> recordsList = new ArrayList<ApduCommand>();
		
		for(int index=0; index < afl.length; index = index + 4) {
			byte sfi = (byte) ((byte) afl[index] | (byte) 0x04);
			for(byte currentRecord = afl[index +1]; currentRecord <= afl[index+2] ; currentRecord++) {
				recordsList.add(new ApduReadRecord(currentRecord, sfi));
			}
		}

		return recordsList;
	}

	public ApduReadRecord(byte[] command) {
		super(command);
	}

	public ApduReadRecord(byte p1, byte p2) {
		super(new byte[5]);
		command[ISO7816.OFFSET_CLA] = ISO7816.CLA_ISO;
		command[ISO7816.OFFSET_INS] = ISO7816.INS_READ_RECORD;
		command[ISO7816.OFFSET_P1] = p1;
		command[ISO7816.OFFSET_P2] = (byte) (p2 | (byte) 0x04);
		command[ISO7816.OFFSET_P3] = 0;
	}

}
