import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Model {

	private static Model model;
	private Hashtable<String, Integer> index;
	private ArrayList<Entry<String, Integer>> tops;
	private SAXParser parser;

	private Model() {
		index = new Hashtable<String, Integer>();
		tops = new ArrayList<Entry<String, Integer>>(10);
		initParser();
	}

	public void initParser() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			parser = factory.newSAXParser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}

		return model;
	}

	public ArrayList<Entry<String, Integer>> getTops() {
		return tops;
	}

	public String readFileContent(File file) {
		String content = "";

		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			String line = input.readLine();
			while (line != null) {
				content += line + "\n";
				line = input.readLine();
			}
			input.close();
		} catch (IOException e) {
			content = "There was an error of reading this file!";
		}

		return content;
	}

	public void createIndex(File file) {
		index.clear();

		try {
			IndexHandler handler = new IndexHandler(index);
			parser.parse(file, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createTopKeywordList() {
		List<Entry<String, Integer>> temp = new ArrayList<Entry<String, Integer>>(index.entrySet());

		Collections.sort(temp, Collections.reverseOrder(new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> element1, Entry<String, Integer> element2) {
				return element1.getValue().compareTo(element2.getValue());
			}
		}));

		tops.clear();
		for (int i = 0; i < 10; i++) {
			Entry<String, Integer> element = temp.get(i);
			tops.add(element);
		}
	}

	public String searchMovie(String keyword, File file) {
		String results = "";

		try {
			MovieHandler handler = new MovieHandler(keyword);
			parser.parse(file, handler);
			results = handler.getResults();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

}