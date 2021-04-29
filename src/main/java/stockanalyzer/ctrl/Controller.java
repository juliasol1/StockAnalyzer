package stockanalyzer.ctrl;

//Cisco Systems, Inc. (CSCO)
//Apple Inc. (AAPL)
//Uber Technologies, Inc. (UBER)

import stockanalyzer.ui.YahooException;
import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;


import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

public class Controller {

	List<String> myTickers = new ArrayList<>();
	Stock stock = null;

	public void process(String ticker) throws YahooException {

		System.out.println("Start process");

		System.out.println(ticker);

		System.out.println("The history:");

		for (String str : myTickers) {
			getHighestHistory(str);
		}
		System.out.println("The cours of last 10 days:");
		for (String str : myTickers) {
			getHighestHistory(str);
		}

		//TODO implement Error handling 

		//TODO implement methods for
		//1) Daten laden
		//2) Daten Analyse

	}


	public void getHighestHistory(String myTicker) {
		try {
			stock = yahoofinance.YahooFinance.get(myTicker);
			stock.getHistory().forEach(quote -> quote.getDate());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getAverageHistorical(String myTicker) {
		try {
			stock = yahoofinance.YahooFinance.get(myTicker);
			stock.getHistory().forEach(quote -> System.out.println(quote.getDate().toInstant()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataQuantityHistory(String myTicker) {
		try {
			stock = yahoofinance.YahooFinance.get(myTicker);
			stock.getHistory().forEach(quote -> System.out.println(quote.getDate().toInstant()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object getData(String searchString) {

		
		return null;
	}


	public void closeConnection() {
		
	}
}
