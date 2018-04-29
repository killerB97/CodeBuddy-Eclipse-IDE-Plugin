package myToolWindow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GoogleSearch {


    public static List<String> search(String searchQuery) {
        List<String> searchResults = new ArrayList<>();
        try {
            String key = "AIzaSyALVogoi_SGj4UnnYcjE70hLQzn-f2wn3g";
            String[] searchQuerySplit = searchQuery.split("\\s+");
            String qry = "";
            for (int i = 0; i < searchQuerySplit.length; i++) {
                qry += searchQuerySplit[i];
                if (i != searchQuerySplit.length - 1) {
                    qry += "+";
                }
            }
            URL url = new URL(
                    "https://www.googleapis.com/customsearch/v1?key=" + key + "&cx=013036536707430787589:_pqjad5hr1a&q=" + qry + "&alt=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {

                if (output.contains("\"link\": \"")) {
                    String link = output.substring(output.indexOf("\"link\": \"") + ("\"link\": \"").length(), output.indexOf("\","));
                    searchResults.add(link);
                }
            }
            conn.disconnect();
            for (String result : searchResults) {
                System.out.println(result);
            }
            return searchResults;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}

