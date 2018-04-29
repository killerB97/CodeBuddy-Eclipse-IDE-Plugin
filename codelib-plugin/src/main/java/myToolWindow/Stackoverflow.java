package myToolWindow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Stackoverflow {

    public static String[] fetchDiscussion(String stackOverflowUrl) {

        System.out.println("Stackoverflow URL is " + stackOverflowUrl);

        String[] results = new String[3];
        results[0] = "";
        results[1] = "";
        results[2] = "";
        try {
            int i = 1;
            Document doc = Jsoup.connect(stackOverflowUrl).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
            results[0] = doc.getElementsByTag("title").first().text();
            Elements temp = doc.select("div.post-text");
            for (Element code : temp) {
                if (i == 1) {
                    i += 1;
                    results[1] = code.getElementsByClass("post-text").first().text();
                } else {
                    results[2] += code.getElementsByClass("post-text").first().text();
                    results[2] += "\n";
                    results[2] += "================================== POST ==================================";
                    results[2] += "\n";
                    i += 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static List<List<String>> getStackOverflowDiscussions(String searchTerm) {

        searchTerm += " java  stackoverflow ";

        List<List<String>> results = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> questions = new ArrayList<>();
        List<String> discussions = new ArrayList<>();
        List<String> discussionUrls = new ArrayList<>();

        List<String> urls = GoogleSearch.search(searchTerm);

        for (String url : urls) {
            if (url.contains("stackoverflow")) {
                String[] urlResults = fetchDiscussion(url);
                if (!("".equals(urlResults[0]))) {
                    titles.add(urlResults[0]);
                    questions.add(urlResults[1]);
                    discussions.add(urlResults[2]);
                    discussionUrls.add(url);
                }
            }
        }

        results.add(titles);
        results.add(questions);
        results.add(discussions);
        results.add(discussionUrls);

        return results;


    }
}
