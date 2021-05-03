package yahooApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import stockanalyzer.ui.YahooException;
import yahooApi.beans.YahooResponse;

import javax.json.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class YahooFinance {

    public static final String URL_YAHOO = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=%s";

    public String requestData(List<String> tickers) throws YahooException {
        String symbols = String.join(",", tickers);
        String query = String.format(URL_YAHOO, symbols);
        System.out.println(query);
        URL obj = null;
        try {
            obj = new URL(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new YahooException("Please check your URL!");
        }
        HttpURLConnection con = null;
        StringBuilder response = new StringBuilder();
        BufferedReader in = null;

        try {
            con = (HttpURLConnection) obj.openConnection();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new YahooException("Please check your connection!");
        }finally {
            try {
                if(in != null)
                    in.close();
            } catch(IOException e) {
                System.out.println("Failed to close file");
            }
        }
        return response.toString();
    }

    protected JsonObject convert(String jsonResponse) {
        InputStream is = new ByteArrayInputStream(jsonResponse.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject jo = reader.readObject();
        reader.close();
        return jo;
    }

    private String extractName(JsonObject jo) {
        String returnName = "";
        Map<String, JsonObject> stockData = ((Map) jo.getJsonObject("quoteResponse"));
        JsonArray x = (JsonArray) stockData.get("result");
        JsonObject y = (JsonObject) x.get(0);
        JsonValue name = y.get("longName");
        returnName = name.toString();
        return returnName;
    }

    public YahooResponse getCurrentData(List<String> tickers) throws YahooException{
        String jsonResponse = requestData(tickers);
        ObjectMapper objectMapper = new ObjectMapper();
        YahooResponse result = null;
        try {
            result  = objectMapper.readValue(jsonResponse, YahooResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new YahooException("There is a problem with JSON!");
        }
        return result;
    }
}