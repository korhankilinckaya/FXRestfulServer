package com.fx.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.fx.io.FileReader;
import com.fx.lib.FXBase;
import com.fx.lib.FXForward;
import com.fx.lib.FXOptionsAmerican;
import com.fx.lib.FXOptionsEuropean;
import com.fx.lib.FXSpot;

public class FXServerTest {

	FileInputStream fstream;
	BufferedReader br;
	
	@Test
	public void testFXData() {
		FileReader fr = new FileReader();
		try {
			List<FXBase> list = fr.getFXData("options", "european");
			
			System.out.println(list.size());
			
			for(int i=0; i<list.size();i++)
			{
				System.out.println(list.get(i).getCustomer());
			}	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFXSpotFill() {
		List<FXBase> list = new ArrayList<FXBase>();

		try {
			fstream = new FileInputStream("./FXData/fxData.txt");

		br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		while ((strLine = br.readLine()) != null) {

		FXBase base = new FXSpot();
		if (strLine.contains("Spot")) {
					list.add(base.fill(strLine));
			}
		}

		for(int i=0; i<list.size();i++)
		{
			System.out.println(list.get(i).toString());
		}
		
		br.close();
		fstream.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void testFXForwardFill() {
		List<FXBase> list = new ArrayList<FXBase>();

		try {
			fstream = new FileInputStream("./FXData/fxData.txt");

		br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		while ((strLine = br.readLine()) != null) {

		FXBase base = new FXForward();
		if (strLine.contains("Forward")) {
					list.add(base.fill(strLine));
			}
		}
		
		for(int i=0; i<list.size();i++)
		{
			System.out.println(list.get(i).toString());
		}

		br.close();
		fstream.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFXOptionsEuropeanFill() {
		List<FXBase> list = new ArrayList<FXBase>();

		try {
			fstream = new FileInputStream("./FXData/fxData.txt");

		br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		while ((strLine = br.readLine()) != null) {

		FXBase base = new FXOptionsEuropean();
		if (strLine.contains("EUROPEAN")) {
					list.add(base.fill(strLine));
			}
		}
		
		for(int i=0; i<list.size();i++)
		{
			System.out.println(list.get(i).toString());
		}

		br.close();
		fstream.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFXOptionsAmericanFill() {
		List<FXBase> list = new ArrayList<FXBase>();

		try {
			fstream = new FileInputStream("./FXData/fxData.txt");

		br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		while ((strLine = br.readLine()) != null) {

		FXBase base = new FXOptionsAmerican();
		if (strLine.contains("AMERICAN")) {
					list.add(base.fill(strLine));
			}
		}
		
		for(int i=0; i<list.size();i++)
		{
			System.out.println(list.get(i).toString());
		}

		br.close();
		fstream.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void validateValueDateBeforeTradeDate() {

		String valueDateStr = "2016-08-11";
		String tradeDateStr = "2016-08-19";
		String resultStr = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date valueDate = format.parse(valueDateStr);
			Date tradeDate = format.parse(tradeDateStr);

			if (valueDate.before(tradeDate)) {
				resultStr = "not passed: value date is before trade date";
			} else
				resultStr = "passed: value date is after trade date";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(resultStr);
	}

	public boolean isInWeekend(Date valueDate) {
		
		int month = valueDate.getMonth();
		Calendar cal = new GregorianCalendar(valueDate.getYear(), valueDate.getMonth(), valueDate.getDay());

		int day = cal.get(Calendar.DAY_OF_WEEK);

		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	@Test
	public void validateValueDateNonWorkingDay() {
		String valueDateStr = "2016-08-19";
		String resultStr = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date valueDate = format.parse(valueDateStr);

			if (isInWeekend(valueDate)) {
				resultStr = "not passed: value date is in weekend days";
			} else
				resultStr = "passed: value date is not in weekend days";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(resultStr);
	}

	@Test
	public void validateCounterparty() {
		String customerName = "PLUTO1";
		String resultStr = "";
		if (customerName.equals("PLUTO1") || customerName.equals("PLUTO2")) {
			resultStr = "passed: counterparty is supported";
		}
		resultStr = "not passed: counterparty is not supported";
		
		System.out.println(resultStr);
	}

	@Test
	public void validateCurrency() {
		Set<Currency> ccyList = Currency.getAvailableCurrencies();
		Iterator<Currency> iterator = ccyList.iterator();
		String resultStr = "";
		while (iterator.hasNext()) {
			Currency setElement = iterator.next();
			
			if (setElement.getCurrencyCode().equals("USDEUR".substring(0, 3))) {
				resultStr = "currency check is passed";
			}
			if (setElement.getCurrencyCode().equals("USDEUR".substring(3, 6))) {
				resultStr = "currency check is passed";
			}

		}

		resultStr =  "not passed: currency pairs are not valid";

		System.out.println(resultStr);
	}
	
	@Test
	public void validateExcerciseDate() {
		String excerciseStartDateStr = "2016-08-20";
		String tradeDateStr = "2016-08-19";
		String expiryDateStr = "2016-08-18";

		String resultStr = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date excerciseStartDate = format.parse(excerciseStartDateStr);
			Date tradeDate = format.parse(tradeDateStr);
			Date expiryDate = format.parse(expiryDateStr);

			if (excerciseStartDate.before(tradeDate)) {
				resultStr = "not passed: excercise start date is before trade date";
			} else if (excerciseStartDate.after(expiryDate)) {
				resultStr = "not passed: excercise start date is after expiry date";
			} else
				resultStr = "passed: excercise start date is after trade date and before expiry date";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(resultStr);
	
	}

	@Test
	public void validateExpiryDate() {
		String deliveryDateStr = "2016-08-20";
		String premiumDateStr = "2016-08-19";
		String expiryDateStr = "2016-08-18";

		String resultStr = "";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date deliveryDate = format.parse(deliveryDateStr);
			Date premiumDate = format.parse(premiumDateStr);
			Date expiryDate = format.parse(expiryDateStr);

			if (premiumDate.after(deliveryDate)) {
				resultStr = "not passed: premium date is after delivery date";
			}else if (expiryDate.after(deliveryDate)) {
				resultStr = "not passed: expiry date is after delivery date";
			} else
				resultStr = "passed: premium date and expiry date is before delivery date";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(resultStr);
	}

}
