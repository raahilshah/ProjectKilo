package cam.cl.kilo.lookup;

import cam.cl.kilo.nlp.ItemInfo;

public abstract class Lookup implements Runnable {
	
	protected String barcodeNo = "";
	protected String barcodeType = "";
	protected ItemInfo info;

	public Lookup(String barcodeNo, String barcodeType, ItemInfo info) {

		super();

		this.barcodeNo = barcodeNo;
		this.barcodeType = barcodeType;
		this.info = info;

	}
	
	public abstract void run();
	
	protected abstract void fillContent(String requestURL);

}
 