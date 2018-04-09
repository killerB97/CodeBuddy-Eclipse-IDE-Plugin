import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GoogleSearch {

    public static final String GOOGLE_SEARCH_QUERY_URL = "https://www.google.com/search?q=";

    public static Elements search(String searchQuery) {
        try {
            String url = GOOGLE_SEARCH_QUERY_URL + searchQuery + "&num=" + "10";
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").timeout(5000).get();
            Elements results = doc.select("h3.r > a");
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return new Elements();
        }
    }

}

