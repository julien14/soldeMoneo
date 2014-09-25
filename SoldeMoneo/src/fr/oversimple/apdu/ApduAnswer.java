package fr.oversimple.apdu;


public class ApduAnswer {

	private byte[] statusWord;
	private byte[] data;
	
	public ApduAnswer(byte[] statusWord) {
		data = null;
		this.statusWord = statusWord;
	}
	
	public ApduAnswer(byte[] data, byte[] statusWord) {
		this.data = data;
		this.statusWord = statusWord;
	}
	
	public boolean hasData() {
		if(null == data) {
			return false;
		} else {
			return true;
		}
	}

	public byte[] getStatusWord() {
		return statusWord;
	}

	public byte[] getData() {
		return data;
	}
	@Override
	public String toString() {
		if(hasData()) {
			return BytesOperations.convert(getData()) + BytesOperations.convert(getStatusWord());
		}
		return BytesOperations.convert(getStatusWord());
	}
}
