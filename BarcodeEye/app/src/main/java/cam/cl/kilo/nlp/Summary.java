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
        this.authors = info.getAuthors().toString().replaceAll("\\[", "").replaceAll("\\]", "");
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
        * Given a summarised string (or a full raw text), clean of
        * line numbers and split into sentences.
        * Returns a Vector of String.
        * */

        Vector<String> v = new Vector<String>();

        // Remove line numbers, e.g. [1], [2], ...
        text = text.replaceAll("\\[\\d+\\]", "\n");

        // Add a newline at the end of each sentence
        // Problem with abbreviations: Dr. Paulson becomes [Dr.], [Paulson]
        text = text.replaceAll("(?<=[\\?\\.;!])\\s+(?=[A-Z]+)", "\n");

        // Split on newlines, compose Vector of non-empty strings
        for (String s : text.split("\\s*\\n\\s*")) {
            if (!s.matches("^\\s*$"))
                v.add(s);
        }

        return v;
    }

    public static void main(String[] args) {
        String str1 = "[1] Blah.\n[2] Another sentence.\n[3]What is this even doing here?\n\n[4] Foobar.\n";
        String str2 = "The new edition of this successful and established textbook retains its two original intentions of explaining how to program in the ML language, and teaching the fundamentals of functional programming. The major change is the early and prominent coverage of modules, which the author extensively uses throughout. In addition, Paulson has totally rewritten the first chapter to make the book more accessible to students who have no experience of programming languages. The author describes the main features of new Standard Library for the revised version of ML, and gives many new examples, e.g. polynomial arithmetic and new ways of treating priority queues. Finally he has completely updated the references. Dr. Paulson has extensive practical experience of ML, and has stressed its use as a tool for software engineering; the book contains many useful pieces of code, which are freely available (via Internet) from the author. He shows how to use lists, trees, higher-order functions and infinite data structures.  He includes many illustrative and practical examples, covering sorting, matrix operations, and polynomial arithmetic. He describes efficient functional implementations of arrays, queues, and priority queues. Larger examples include a general top-down parser, a lambda-calculus reducer and a theorem prover. A chapter is devoted to formal reasoning about functional programs. The combination of careful explanation and practical advice will ensure that this textbook continues to be the preferred text for many courses on ML for students at all levels.t\\x00\\x1dML for the Working Programmer";
        Summary summ1 = new Summary(new ItemInfo(), str1);
        Summary summ2 = new Summary(new ItemInfo(), str2);

        for (String s : summ1.getText()) {
            System.out.println(s);
        }
        for (String s : summ2.getText()) {
            System.out.println(s);
        }
    }
}
