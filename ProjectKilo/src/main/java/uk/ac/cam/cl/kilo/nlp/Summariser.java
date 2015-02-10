package uk.ac.cam.cl.kilo.nlp;

import java.util.Vector;

public class Summariser {

	public static String summarise(ItemInfo itemInfo) {
	    String[] sentences = itemInfo.getDescriptions().get(0).split("\\.", 0);
	    
	    //Start building a formatted string
	    String summarised = itemInfo.getTitle();
	    
	    Vector<String> authors = itemInfo.getAuthors();
	    String length = String.valueOf(authors.size());
	    summarised += "~" + length;
	    for (String a : authors) {
	    	summarised += "~" + a;
	    }
	    for (String s : sentences) {
	    	summarised += "~" + s;
	    }
	    return summarised;
	}

    public static void main(String[] args) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.addDescription("This is a rather short description. The product descibed does not even exist. Testing out sentences. Here's another one");

        String result;
        result = summarise(itemInfo);
        System.out.println(result);
    }

}
