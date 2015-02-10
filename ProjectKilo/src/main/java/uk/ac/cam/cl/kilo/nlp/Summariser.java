package uk.ac.cam.cl.kilo.nlp;

public class Summariser {

	public static String summarise(ItemInfo itemInfo) {
	    String[] sentences = itemInfo.getDescriptions().get(0).split("\\.", 0);
	    return sentences[0];
	}

    public static void main(String[] args) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.addDescription("This is a rather short description. The product descibed does not even exist");

        String result;
        result = summarise(itemInfo);
        System.out.println(result);
    }

}
