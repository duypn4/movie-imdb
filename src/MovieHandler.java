import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MovieHandler extends DefaultHandler {

	private boolean isTitle = false;
	private boolean isYear = false;
	private boolean isRate = false;
	private boolean isDirector = false;
	private boolean isName = false;
	private boolean isGenres = false;
	private boolean isItem = false;
	private boolean found = false;
	private int counter = 0;
	private String results = "";
	private String keyword;

	public MovieHandler(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
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
			String title = new String(ch, start, length);

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

	public String getResults() {
		if (results.isEmpty()) {
			results = "There was no movies matching your keywords!";
		} else {
			results = "MATCHING MOVIES\n------------------------------------------" + results;
		}

		return results;
	}

}