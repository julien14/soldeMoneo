package fr.oversimple.soldemoneo;

import java.io.IOException;

import android.nfc.tech.IsoDep;
import android.util.Log;
import fr.oversimple.apdu.ApduAnswer;
import fr.oversimple.apdu.ApduCommand;
import fr.oversimple.apdu.BytesOperations;

public class NfcCardReader implements ApduCardReader{

	private IsoDep isoDepTag;
	
	
	public NfcCardReader(IsoDep tag) {
		isoDepTag = tag;
	}
	
	/* The nfc reader to put outside */
	@Override
	public byte[] reset() throws Exception {
		try {
			isoDepTag.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isoDepTag.getHistoricalBytes();
	}

	@Override
	public ApduAnswer send(ApduCommand apdu) throws Exception {
		Log.d("NFC reader",BytesOperations.convert(apdu.getBytes()));
		byte[] answer;
		try {
			answer = isoDepTag.transceive(apdu.getBytes());
			
			Log.d("NFC reader",BytesOperations.convert(answer));
			
			if (2 == answer.length) {
				return new ApduAnswer(answer);
			} else {
				byte[] sw = new byte[2];
				byte[] data = new byte[answer.length - 2];
				System.arraycopy(answer, 0, data, 0, answer.length - 2);
				System.arraycopy(answer, answer.length -2, sw, 0, 2);
				return new ApduAnswer(data, sw);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void disconnect() {
		try {
			isoDepTag.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCardPresent() {
		return true;
	}

	@Override
	public String getName() {
		return "NFC antenna";
	}

}
