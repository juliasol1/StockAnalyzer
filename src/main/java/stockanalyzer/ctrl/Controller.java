package stockanalyzer.ctrl;

//Cisco Systems, Inc. (CSCO)
//Apple Inc. (AAPL)
//Uber Technologies, Inc. (UBER)

import stockanalyzer.ui.YahooException;
import yahoofinance.Stock;
import yahoofinance.histquotes.Interval;
import java.io.IOException;
import java.util.*;

public class Controller {

	List<String> myTickers = new ArrayList<>();
	Stock stock = null;
	Calendar calendar = null;

	public void process(String ticker) throws YahooException {

		calender = Calendar.getInstance();
		calender.add(Calendar.DAY_OF_WEEK, -11);
		System.out.println("Start process");

		myTickers.add(ticker);

		System.out.println("The history:");

		for (String str : myTickers) {
			System.out.println("Name: " + str + ", Last price: " + Math.round(getHighestHistorical(str)*100.0)/100.0);
		}

		System.out.println("Last 10 days");

		for (String str : myTickers) {
			System.out.println("Name: " + str + ", Last price: " + Math.round(getAverageHistorical(str)*100.0)/100.0);
		}

		System.out.println("The number of records in your shares");

		for (String str : myTickers) {
			System.out.println("Data sets from: "+ str +" are: " + getDataQuantityHistorical(str));
		}


	}

	public double getHighestHistorical(String myTicker) {
		double result = 0;
		try {
			stock = yahoofinance.YahooFinance.get(myTicker);
			result = stock.getHistory(calender, Interval.DAILY).stream()
					.mapToDouble(value -> value.getClose().doubleValue())
					.max()
					.orElse(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public double getAverageHistorical(String myTicker) {
		double result = 0;
		try {
			stock = yahoofinance.YahooFinance.get(myTicker);
			result = stock.getHistory(calender, Interval.DAILY).stream()
					.mapToDouble(value -> value.getClose().doubleValue())
					.average()
					.orElse(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public double getDataQuantityHistorical(String myTicker) {
		double count = 0;
		try {
			stock = yahoofinance.YahooFinance.get(myTicker);
			count = stock.getHistory().stream()
					.count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;

	}
}
