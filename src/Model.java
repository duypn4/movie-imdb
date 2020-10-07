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
		tops = new ArrayList<Entry<String, Integer>>(10);
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
	 * Method to read lines from a XML source file
	 * 
	 * @param file XML source file
	 * @return file content string
	 */
	public String readFileContent(File file) {
		// file content string
		String content = "";

		// concatenate all lines into string
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			String line = input.readLine();
			while (line != null) {
				content += line + "\n";
				line = input.readLine();
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	/**
	 * Method to compute hash table index of correlated keywords
	 * 
	 * @param file XML source file
	 */
	public void createIndex(File file) {
		// clear all items inside index
		index.clear();

		// parse source file and compute index
		try {
			IndexHandler handler = new IndexHandler(index);
			parser.parse(file, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to compute top-keywords list
	 */
	public void createTopKeywordList() {
		// get set of index entries
		List<Entry<String, Integer>> temp = new ArrayList<Entry<String, Integer>>(index.entrySet());

		// sort set of index entries in descending order
		Collections.sort(temp, Collections.reverseOrder(new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> element1, Entry<String, Integer> element2) {
				return element1.getValue().compareTo(element2.getValue());
			}
		}));

		// add first ten index entries into top-keywords list
		tops.clear();
		for (int i = 0; i < 10; i++) {
			Entry<String, Integer> element = temp.get(i);
			tops.add(element);
		}
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

		// parse source file and find movies
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