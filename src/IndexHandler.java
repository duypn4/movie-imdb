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
public class IndexHandler extends DefaultHandler {

	// hash table index
	private Hashtable<String, Integer> index;
	// flag showing begin of keyword element
	private boolean isKeyword = false;
	// correlated keyword
	private String keyword = null;

	/**
	 * Constructor
	 * 
	 * @param index hash table object from model
	 */
	public IndexHandler(Hashtable<String, Integer> index) {
		this.index = index;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// check whether it is at beginning of keyword element
		if (qName.equalsIgnoreCase("kw")) {
			isKeyword = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isKeyword) {
			// get keyword
			keyword = new String(ch, start, length);

			// add keyword and its frequency score into index table
			Integer frequency = index.get(keyword);
			if (frequency != null) {
				frequency++;
				index.put(keyword, frequency);
			} else {
				frequency = 1;
				index.put(keyword, frequency);
			}

			isKeyword = false;
		}
	}

}