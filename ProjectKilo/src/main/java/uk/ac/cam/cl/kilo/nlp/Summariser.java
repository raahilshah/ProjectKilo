package uk.ac.cam.cl.kilo.nlp;

import java.util.Vector;

public class Summariser {

	public static void summarise(ItemInfo itemInfo) {
		
		if (itemInfo.getDescriptions() != null) {
			
		    String[] sentences = itemInfo.getDescriptions().get(0).split("\\. ", 0);
		    
		    itemInfo.setDescriptions(new Vector<String>());
		    
		    for (String s : sentences) {
		    	
		    	itemInfo.addDescription(s);
		    	
		    }
	    
		}
		
		//System.out.println(itemInfo.getDescriptions().get(0));
	    
	}

    public static void main(String[] args) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.addDescription("This is a rather short description. The product descibed does not even exist. Testing out sentences. Here's another one");
        summarise(itemInfo);
        for (String d : itemInfo.getDescriptions()) {
        	System.out.println(d);
        }
    }

}
