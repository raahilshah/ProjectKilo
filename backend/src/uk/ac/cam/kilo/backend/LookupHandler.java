package uk.ac.cam.kilo.backend;

public abstract class LookupHandler {
	//Chooses which API to use based on barcodeType and runs the relevant APIClient
	public abstract ItemInfo chooseAPI(String barcode, String barcodeType);

}
