import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * COS80007 Assignment 2: The class is a handler for SAX parser finding movies
 * via their title
 * 
 * @author Ngoc Duy Pham (102888457)
 * @author Gabriel Andreas (101272020)
 * @author Van Anh Tran (123456789)
 *
 * @version 1.0
 */
public class SearchHandler extends DefaultHandler {

	// list of keywords inside desirable movies
	private Hashtable<String, Integer> temp;
	// flag showing begin of title element
	private boolean isTitle = false;
	// flag showing begin of year element
	private boolean isYear = false;
	// flag showing begin of rate element
	private boolean isRate = false;
	// flag showing begin of director element
	private boolean isDirector = false;
	// flag showing begin of name element
	private boolean isName = false;
	// flag showing begin of genres element
	private boolean isGenres = false;
	// flag showing begin of item element
	private boolean isItem = false;
	// flag showing begin of keywords element
	private boolean isKws = false;
	// flag showing begin of keyword element
	private boolean isKw = false;
	// flag showing desirable movies
	private boolean found = false;
	// counter for number of matching movies
	private int counter = 0;
	// search results
	private String results = "";
	// user keyword
	private String keyword;

	/**
	 * Constructor
	 * 
	 * @param keyword string keyword entered by users
	 * @param temp    array list of relevant keyword
	 */
	public SearchHandler(String keyword, Hashtable<String, Integer> temp) {
		this.keyword = keyword;
		this.temp = temp;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// check whether it is at beginning of title element and subsequent elements of
		// movies
		if (qName.equalsIgnoreCase("title")) {
			isTitle = true;
		} else if (found) {
			if (qName.equalsIgnoreCase("year")) {
				isYear = true;
			} else if (qName.equalsIgnoreCase("rating")) {
				isRate = true;
			} else if (qName.equalsIgnoreCase("director")) {
				isDirector = true;
			} else if (qName.equalsIgnoreCase("name") && isDirector) {
				isName = true;
			} else if (qName.equalsIgnoreCase("genres")) {
				isGenres = true;
			} else if (qName.equalsIgnoreCase("item") && isGenres) {
				isItem = true;
			} else if (qName.equalsIgnoreCase("kws")) {
				isKws = true;
			} else if (qName.equalsIgnoreCase("kw") && isKws) {
				isKw = true;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// check whether it is at closing tag of director, genres, keyword elements
		if (qName.equalsIgnoreCase("director")) {
			isDirector = false;
		} else if (qName.equalsIgnoreCase("genres")) {
			isGenres = false;
		} else if (qName.equalsIgnoreCase("kws")) {
			isKws = false;
			found = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// get the text content of each element
		String str = new String(ch, start, length);

		if (isTitle) {
			// check whether movie title containing user keyword
			if (str.toLowerCase().contains(keyword)) {
				found = true;
				counter++;
				results += "\nMovie: " + counter + "\n" + "Title: " + str + "\n";
			}
			isTitle = false;
		} else if (isYear) {
			results += "Year: " + str + "\n";
			isYear = false;
		} else if (isRate) {
			results += "Rate: " + str + "\n";
			isRate = false;
		} else if (isName) {
			results += "Director Name: " + str + "\n";
			isName = false;
		} else if (isItem) {
			results += "Genre: " + str + "\n";
			isItem = false;
		} else if (isKw) {
			// add relevant keyword into list
			Integer frequency = temp.get(str);
			if (frequency == null) {
				temp.put(str, 0);
			}

			isKw = false;
		}
	}

	/**
	 * Getter for results
	 * 
	 * @return string of all matching movies
	 */
	public String getResults() {
		// check movies matched then format results
		if (!results.isEmpty()) {
			results = "MATCHING MOVIES\n------------------------------------------" + results;
		}

		return results;
	}

}