package uk.ac.cam.kilo.backend;

import java.util.Vector;

public abstract class GenericAPIClient {
	private Vector<String> APIKeys;

	public abstract String APICall(String barcode, String barcodeType);
	
	public abstract ItemInfo parseResults(String results);

}
