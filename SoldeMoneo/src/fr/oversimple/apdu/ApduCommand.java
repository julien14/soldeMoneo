package fr.oversimple.apdu;


public class ApduCommand {

	protected byte[] command;

	public ApduCommand(byte[] command) {
		this.command = command;
	}
	
	public byte getCla() {
		return command[ISO7816.OFFSET_CLA];
	}

	public byte getIns() {
		return command[ISO7816.OFFSET_INS];
	}

	public byte getP1() {
		return command[ISO7816.OFFSET_P1];
	}

	public byte getP2() {
		return command[ISO7816.OFFSET_P2];
	}

	public byte getLc() {
		return command[ISO7816.OFFSET_P3];
	}
	
	public byte[] getData() {
		// No data 
		if(command.length == 5) {
			return null;
		} else {
			int lc = BytesOperations.toInt(getLc());
			byte[] data = new byte[lc];
			System.arraycopy(command, ISO7816.OFFSET_DATA, data, 0, lc);
			return data;
		}
	}

	public byte getLe() {
		return command[command.length - 1];
	}
	
	public byte[] getBytes() {
		return command;
	}
	
	@Override
	public String toString() {
		return BytesOperations.convert(getBytes());
	}

}
