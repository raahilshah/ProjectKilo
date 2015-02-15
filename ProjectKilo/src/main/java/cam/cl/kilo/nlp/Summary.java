package cam.cl.kilo.nlp;


import java.io.Serializable;

/**
 * Created by dcultrera on 14/02/2015.
 */

public class Summary implements Serializable {

    private static final long serialVersionUID = 2900729725728472406L;
    private String title;
    private String authors;
    private String text;

    public Summary(ItemInfo info, String text) {
        this.title = info.getTitle();
        this.authors = info.getAuthors().toString().replaceAll("\\[", "").replaceAll("\\]","");
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getText() {
        return text;
    }

}
