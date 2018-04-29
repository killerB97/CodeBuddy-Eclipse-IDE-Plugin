package myToolWindow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    public static List<String> scrapeWebsite(String website, String url) {

        List<String> solutions = new ArrayList<String>();

        try {
            System.out.println("URL IS " + url);
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();

            if (website.equals("w3resource")) {
                Elements temp = doc.select("pre.line-numbers");
                for (Element code : temp) {
                    solutions.add((code.getElementsByClass("language-java").first().text()));
                }
            } else if (website.equals("programiz")) {
                Elements temp = doc.select("pre>code");
                for (Element code : temp) {
                    solutions.add(code.getAllElements().first().text());
                }
            } else if (website.equals("geeksforgeeks")) {
                Elements temp = doc.select("pre[class*=brush: java]");
                for (Element code : temp) {
                    solutions.add(code.getAllElements().first().text());
                }
            } else if (website.equals("javatpoint")) {
                Elements temp = doc.select("div.codeblock");
                for (Element code : temp) {
                    solutions.add(code.getElementsByClass("java").first().text());
                }
            } else if (website.equals("sanfoundry")) {
                Elements temp = doc.select("div.java");
                for (Element code : temp) {
                    solutions.add(code.getElementsByClass("java").first().text());
                }
            } else if (website.equals("stackoverflow")) {
                Elements temp = doc.select("div.answer");
                for (Element code : temp) {
                    solutions.add(code.getElementsByClass("lang-java prettyprint prettyprinted").first().text());
                }
            } else if (website.equals("gist.github")) {
                Elements temp = doc.select("div.blob-wrapper.data.type-java");
                for (Element code : temp) {
                    solutions.add(code.getElementsByClass("blob-wrapper data type-java").first().text());
                }
            } else if (website.equals("mkyong")) {
                Elements temp = doc.select("code.language-java");
                for (Element code : temp) {
                    solutions.add(code.getElementsByClass("language-java").first().text());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return solutions;

    }

    public static List<List<String>> selectWebsites(List<String> urls) {

        List<String> websitesRanked = new ArrayList<>();
        List<String> urlsRanked = new ArrayList<>();

        List<String> selectedWebistes = new ArrayList<>();
        selectedWebistes.add("w3resource");
        selectedWebistes.add("mkyong");
        selectedWebistes.add("programiz");
        selectedWebistes.add("geeksforgeeks");
        selectedWebistes.add("javatpoint");
        selectedWebistes.add("sanfoundry");
        selectedWebistes.add("gist.github");

        for (String url : urls) {
            for (String website : selectedWebistes) {
                if (url.contains(website)) {
                    websitesRanked.add(website);
                    urlsRanked.add(url);
                }
            }
        }

        List<List<String>> retList = new ArrayList<>();
        retList.add(websitesRanked);
        retList.add(urlsRanked);

        return retList;
    }


    public static List<List<String>> fetchCode(String searchTerm) {

        List<List<String>> fetchedSolutionsAndUrls = new ArrayList<>();
        List<String> fetchedSolutions = new ArrayList<>();
        List<String> fetchedUrls = new ArrayList<>();

        searchTerm += " java ";

        List<String> urls = GoogleSearch.search(searchTerm);

        List<List<String>> websitesAndUrls = selectWebsites(urls);
        List<String> websitesRanked = websitesAndUrls.get(0);
        List<String> urlsRanked = websitesAndUrls.get(1);

        if (websitesRanked.size() == 0) {
            return fetchedSolutionsAndUrls;
        }

        int num = websitesRanked.size();


        try {

            for (int i = 0; i < num; i++) {
                String codeWebsite = websitesRanked.get(i);
                String codeUrl = urlsRanked.get(i);

                List<String> solutions = scrapeWebsite(codeWebsite, codeUrl);

                for (String solution : solutions) {
                    fetchedSolutions.add(solution);
                    fetchedUrls.add(codeUrl);
                }

            }

            fetchedSolutionsAndUrls.add(fetchedSolutions);
            fetchedSolutionsAndUrls.add(fetchedUrls);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fetchedSolutionsAndUrls;

    }


}
