import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IndexHandler extends DefaultHandler {

	private Hashtable<String, Integer> index;
	private boolean isKeyword = false;
	private String keyword = null;

	public IndexHandler(Hashtable<String, Integer> index) {
		this.index = index;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("kw")) {
			isKeyword = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isKeyword) {
			keyword = new String(ch, start, length);

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