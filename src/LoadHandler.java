import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * COS80007 Assignment 2: The class is a handler for SAX parser computing a
 * keyword index
 * 
 * @author Ngoc Duy Pham (102888457)
 * @author Gabriel Andreas (101272020)
 * @author Van Anh Tran (123456789)
 * 
 * @version 1.0
 */
public class LoadHandler extends DefaultHandler {

	// hash table index
	private Hashtable<String, Integer> index;
	// flag showing begin of root element
	private boolean isRoot = false;
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
	// flag showing begin of item element
	private boolean isItem = false;
	// flag showing begin of keyword element
	private boolean isKw = false;
	// file content
	private String content = "";

	/**
	 * Constructor
	 * 
	 * @param index hash table object from model
	 */
	public LoadHandler(Hashtable<String, Integer> index) {
		this.index = index;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// check whether it is at beginning of root element and subsequent elements of
		// movies
		if (qName.equalsIgnoreCase("imdb")) {
			isRoot = true;
		} else if (qName.equalsIgnoreCase("title")) {
			isTitle = true;
		} else if (qName.equalsIgnoreCase("year")) {
			isYear = true;
		} else if (qName.equalsIgnoreCase("rating")) {
			isRate = true;
		} else if (qName.equalsIgnoreCase("director")) {
			isDirector = true;
		} else if (qName.equalsIgnoreCase("name") && isDirector) {
			isName = true;
		} else if (qName.equalsIgnoreCase("item")) {
			isItem = true;
		} else if (qName.equalsIgnoreCase("kw")) {
			isKw = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("director")) {
			isDirector = false;
		} else if (qName.equalsIgnoreCase("imdb")) {
			content += "]";
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// get the text content of each element
		String str = new String(ch, start, length);

		if (isRoot) {
			content += "[Root Element: imdb\n";
			isRoot = false;
		} else if (isTitle) {
			content += ", Current Element: movie\n, Title: " + str + "\n";
			isTitle = false;
		} else if (isYear) {
			content += ", Year: " + str + "\n";
			isYear = false;
		} else if (isRate) {
			content += ", Rating: " + str + "\n";
			isRate = false;
		} else if (isName) {
			content += ", Director Name: " + str + "\n";
			isName = false;
		} else if (isItem) {
			content += ", Genre: " + str + "\n";
			isItem = false;
		} else if (isKw) {
			// add keyword and its frequency score into index table
			Integer frequency = index.get(str);
			if (frequency != null) {
				frequency++;
				index.put(str, frequency);
			} else {
				index.put(str, 1);
			}

			isKw = false;
		}
	}

	public String getContent() {
		return content;
	}

}