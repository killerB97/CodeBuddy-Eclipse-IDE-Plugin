import java.util.Scanner;

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

        System.out.println(url);

    }
}
