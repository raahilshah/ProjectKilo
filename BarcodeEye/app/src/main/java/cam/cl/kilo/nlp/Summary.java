package cam.cl.kilo.nlp;


import java.io.Serializable;
import java.util.Vector;


public class Summary implements Serializable {

    private static final long serialVersionUID = 2900729725728472406L;
    private String title;
    private String authors;
    private Vector<String> text;

    public Summary(ItemInfo info, String text) {
        this.title = info.getTitle();
        this.authors = info.getAuthors().toString().replaceAll("\\[", "").replaceAll("\\]","");
        this.text = stringToVector(text);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public Vector<String> getText() {
        return text;
    }

    public Vector<String> stringToVector(String text) {
        /*
        * Given a summarised String, do the following cleanup:
        * - remove line numbers ([1], [2], ...)
        * - add newlines at the end of sentences
        * - create Vector by splitting on new lines
        * - make sure no Vector element is empty or blank (use RegEx ^\s*$ )
        * output should look like:
        * Vector text = {"Blah.". "Another sentence.", "What is this even doing here?", "Foobar."}
        * */

        if(text.contains("\n")){
            //first case
            text = text.replaceAll("\\[.", "");
            text = text.replaceAll("\\]", "");
            String[] array = text.split("\n");
            Vector<String> vector = new Vector<String>();
            for(String element: array){
                vector.add(element);
            }
            Vector<String> empty = new Vector<String>();
            empty.add("");
            vector.removeAll(empty);

            return vector;
        }else{
            //second case

            text = text.replaceAll("[!;]","\\." );
            text = text.replaceAll("\\.","\\.{" );
            text = text.replaceAll("\\?","\\?{" );
            String[] split = text.split("\\{");
            Vector<String> vector = new Vector<String>();
            for(String element: split){
                vector.add(element);
            }
            return vector;
        }

    }

}
