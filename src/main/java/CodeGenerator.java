import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CodeGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the function you want to generate the code for: ");
        String searchTerm = scanner.nextLine();
        searchTerm += " java w3resource";


        Elements results = GoogleSearch.search(searchTerm);
        String url = "";
        String title = "";
        for (Element result : results) {
            url = result.attr("href");
            title = result.text();
        }
        url = url.substring(6, url.indexOf("&"));
        url = url.substring(1); // remove '='

        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
            Elements temp = doc.select("pre.line-numbers");
            int i = 1;
            for (Element code : temp) {
                System.out.println("Solution "+i+":"+"\n");
                System.out.println(code.getElementsByClass("language-java").first().text());
                System.out.println("\n");
                i++;
            }

        }


        catch(IOException e){
                e.printStackTrace();
            }

    }
}
