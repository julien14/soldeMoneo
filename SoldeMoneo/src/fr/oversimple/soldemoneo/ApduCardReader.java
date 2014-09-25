package fr.oversimple.soldemoneo;

import fr.oversimple.apdu.ApduAnswer;
import fr.oversimple.apdu.ApduCommand;

public interface ApduCardReader {
	
	public byte[] reset() throws Exception;
	public ApduAnswer send(ApduCommand apdu) throws Exception;
	public void disconnect();
	public boolean isCardPresent();
	public String getName();
}
