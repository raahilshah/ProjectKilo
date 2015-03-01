/*
 * Copyright (C) 2015 Group Kilo (Cambridge Computer Lab)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cam.cl.kilo.lookup;

import cam.cl.kilo.NLP.ItemInfo;

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
 