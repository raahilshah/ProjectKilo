package uk.ac.cam.kilo.backend;

public class Summariser {

	public static String summarise(ItemInfo itemInfo) {
	    String[] sentences = itemInfo.description.split("\\.", 0);
        return sentences[0];
	}

    public static void main(String[] args) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.description = "This is a rather short description. The product descibed does not even exist";

        String result;
        result = summarise(itemInfo);
        System.out.println(result);
    }

}
