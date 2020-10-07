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
public class MovieHandler extends DefaultHandler {

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
	 */
	public MovieHandler(String keyword) {
		this.keyword = keyword;
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
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// check whether it is at closing tag of director and genres elements
		if (qName.equalsIgnoreCase("director")) {
			isDirector = false;
		} else if (qName.equalsIgnoreCase("genres")) {
			isGenres = false;
			found = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isTitle) {
			// get movie title
			String title = new String(ch, start, length);

			// check whether movie title containing user keyword
			if (title.toLowerCase().contains(keyword)) {
				found = true;
				counter++;
				results += "\nMovie: " + counter + "\n" + "Title: " + title + "\n";
			}
			isTitle = false;
		} else if (isYear) {
			String year = new String(ch, start, length);

			results += "Year: " + year + "\n";
			isYear = false;
		} else if (isRate) {
			String rate = new String(ch, start, length);

			results += "Rate: " + rate + "\n";
			isRate = false;
		} else if (isName) {
			String name = new String(ch, start, length);

			results += "Director Name: " + name + "\n";
			isName = false;
		} else if (isItem) {
			String item = new String(ch, start, length);

			results += "Genre: " + item + "\n";
			isItem = false;
		}
	}

	/**
	 * Getter for results
	 * 
	 * @return string of all matching movies
	 */
	public String getResults() {
		// check no movies matched then format results
		if (results.isEmpty()) {
			results = "There was no movies matching your keywords!";
		} else {
			results = "MATCHING MOVIES\n------------------------------------------" + results;
		}

		return results;
	}

}