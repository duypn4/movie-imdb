import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map.Entry;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * COS80007 Assignment 2: The class is a model handling all business logic for
 * XML Keyword Search system
 * 
 * @author Ngoc Duy Pham (102888457)
 * @author Gabriel Andreas (101272020)
 * @author Van Anh Tran (123456789)
 * 
 * @version 1.0
 */
public class Model {

	// singleton model object
	private static Model model;
	// hash table index
	private Hashtable<String, Integer> index;
	// top-keywords list
	private ArrayList<Entry<String, Integer>> tops;
	// sax parser object
	private SAXParser parser;

	/**
	 * Constructor
	 */
	private Model() {
		index = new Hashtable<String, Integer>();
		initParser();
	}

	/**
	 * Method to create SAX parser object
	 */
	public void initParser() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			parser = factory.newSAXParser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to do lazy instantiation for unique model object
	 * 
	 * @return model object
	 */
	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}

		return model;
	}

	/**
	 * Getter for top-keywords list
	 * 
	 * @return array list of top-keywords
	 */
	public ArrayList<Entry<String, Integer>> getTops() {
		return tops;
	}

	/**
	 * Method to read elements from a XML source file
	 * 
	 * @param file XML source file
	 * @return file content string
	 */
	public String readFileContent(File file) {
		// file content string
		String content = "";

		// parse source file to compute index and content
		index.clear();
		try {
			LoadHandler handler = new LoadHandler(index);
			parser.parse(file, handler);
			content = handler.getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}

	/**
	 * Method to compute top-keywords list
	 * 
	 * @param temp array list of relevant keywords
	 */
	public void createTopKeywordList(Hashtable<String, Integer> temp) {
		// get frequency for relevant keyword
		for (String key : temp.keySet()) {
			Integer value = index.get(key);
			temp.put(key, value);
		}

		// sort set of relevant keywords in descending order
		tops = new ArrayList<Entry<String, Integer>>(temp.entrySet());
		Collections.sort(tops, Collections.reverseOrder(new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> element1, Entry<String, Integer> element2) {
				return element1.getValue().compareTo(element2.getValue());
			}
		}));
	}

	/**
	 * Method to find movies within a source file
	 * 
	 * @param keyword keyword string
	 * @param file    XML source file
	 * @return string matching movies
	 */
	public String searchMovie(String keyword, File file) {
		// results containing desirable movies
		String results = "";
		// list of relevant keyword
		Hashtable<String, Integer> temp = new Hashtable<String, Integer>(10);

		// parse source file and find movies
		try {
			SearchHandler handler = new SearchHandler(keyword, temp);
			parser.parse(file, handler);
			results = handler.getResults();
			createTopKeywordList(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

}