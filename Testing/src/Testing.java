import java.util.Vector;


public class Testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<String> vector = new Vector<String>();
		vector.add("empty");
		vector.add("");
		vector.add("words");
		vector.add("empty");
		vector.add("");
		vector.add("end");
		Vector<String> empty = new Vector<String>();
		empty.add("");
		vector.removeAll(empty);
		for(String element: vector){
			System.out.println(element);
		}

		String index = "[1]group\n[2]project\n[3]kilo\n";
		boolean contain = index.contains("\n");
		index = index.replaceAll("\\[.", "");
		index = index.replaceAll("\\]", "");
		System.out.println(index);
		System.out.println(contain);
		String split = "a.b.c.d?e?f.h?";
		String[] splitArray = split.split("[.\\?]");
		for(String thing: splitArray){
			System.out.println(thing);
		}
		String bracket = "lsjf.{lsj?{jlsjf.{";
		String[] bracketArray = bracket.split("\\{");
		for(String a : bracketArray){
			System.out.println(a);
		}
	}

}
