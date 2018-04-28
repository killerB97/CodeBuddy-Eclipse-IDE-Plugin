import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
                }}
              else if (website.equals("gist.github"))  {
                Elements temp = doc.select("div.blob-wrapper data type-text");
                for ( Element code: temp){
                    solutions.add(code.getElementsByClass("div.blob-wrapper data type-text").first().text());
                }
            }
              else if (website.equals("mykyong")){
                Elements temp = doc.select("code.language-java");
                for ( Element code: temp){
                    solutions.add(code.getElementsByClass("language-java").first().text());
            }}

        } catch (Exception e) {
            e.printStackTrace();
        }

        return solutions;

    }

    public static String[] selectWebsite(List<String> urls) {

        List<String> selectedWebistes = new ArrayList<String>();
        selectedWebistes.add("w3resource");
        selectedWebistes.add("programiz");
        selectedWebistes.add("geeksforgeeks");
        selectedWebistes.add("javatpoint");
        selectedWebistes.add("sanfoundry");

        String[] websiteUrl = new String[2];

        boolean isFound = false;

        for (String url : urls) {
            if (!isFound) {
                for (String website : selectedWebistes) {
                    if (url.contains(website)) {
                        websiteUrl[0] = website;
                        websiteUrl[1] = url;
                        isFound = true;
                        break;
                    }
                }
            }
        }

        if(!isFound){
            for (String url : urls) {
                if (url.contains("gist.github")) {
                    websiteUrl[0] = "gist.github";
                    websiteUrl[0] = url;
                    isFound = true;
                    break;
                }
            }
        }

        if (!isFound) {
            for (String url : urls) {
                if (url.contains("stackoverflow")) {
                    websiteUrl[0] = "stackoverflow";
                    websiteUrl[0] = url;
                    isFound = true;
                    break;
                }
            }
        }
        return websiteUrl;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the function you want to generate the code for: ");
        String searchTerm = scanner.nextLine();
        searchTerm += " java ";

        Elements results = GoogleSearch.search(searchTerm);

        List<String> urls = new ArrayList<String>();

        for (Element result : results) {
            String url = result.attr("href");
            url = url.substring(6, url.indexOf("&"));
            url = url.substring(1); // remove '='
            urls.add(url);
            System.out.println(url);
        }

        String[] websiteUrl = selectWebsite(urls);

        System.out.println(websiteUrl[0]);
        System.out.println(websiteUrl[1]);

        try {

            if (websiteUrl[0] != null) {

                String website = websiteUrl[0];
                String url = websiteUrl[1];

                List<String> solutions = scrapeWebsite(website, url);

                for (int i = 0; i < solutions.size(); i++) {
                    System.out.println("Solution " + Integer.toString(i + 1));
                    System.out.println(solutions.get(i));
                }

            } else {
                System.out.println("No resource available to fetch!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
