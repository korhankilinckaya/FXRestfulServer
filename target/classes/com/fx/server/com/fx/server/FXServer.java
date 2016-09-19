package com.fx.server.com.fx.server;

import javax.ws.rs.core.MediaType;

import com.fx.io.FileReader;
import com.fx.lib.FXBase;
import com.fx.lib.FXForward;
import com.fx.lib.FXOptions;
import com.fx.lib.FXOptionsAmerican;
import com.fx.lib.FXOptionsEuropean;
import com.fx.lib.FXSpot;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/data")
public class FXServer {
	FileReader reader = new FileReader();

	public String validateValueDateBeforeTradeDate(FXBase fx, String type) { //passed

		String valueDateStr = "";
		String tradeDateStr = "";
		String resultStr = "";

		if (type.equals("spot")) {
			valueDateStr = ((FXSpot) fx).getValueDate().trim().replace("\"", "");
			tradeDateStr = ((FXSpot) fx).getTradeDate().trim().replace("\"", "");
		} else {
			valueDateStr = ((FXForward) fx).getValueDate().trim().replace("\"", "");
			tradeDateStr = ((FXForward) fx).getTradeDate().trim().replace("\"", "");
		}

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

		return resultStr;
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

	public String validateValueDateNonWorkingDay(FXBase fx, String type) {
		String valueDateStr = "";
		String resultStr = "";

		if (type.equals("spot")) {
			valueDateStr = ((FXSpot) fx).getValueDate().trim().replace("\"", "");
		} else {
			valueDateStr = ((FXForward) fx).getValueDate().trim().replace("\"", "");
		}

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

		return resultStr;
	}

	public String validateCounterparty(FXBase fx) { //passed
		if (fx.getCustomer().replace("\"", "").equals("PLUTO1") || fx.getCustomer().replace("\"", "").equals("PLUTO2")) {
			return "passed: counterparty is supported";
		}
		return "not passed: counterparty is not supported";
	}

	public String validateCurrency(FXBase fx) { //passed
		Set<Currency> ccyList = Currency.getAvailableCurrencies();
		Iterator<Currency> iterator = ccyList.iterator();

		while (iterator.hasNext()) {
			Currency setElement = iterator.next();
			
			if (setElement.getCurrencyCode().equals(fx.getCcyPair().replace("\"", "").substring(0, 3))) {
				return "passed: currency is valid";
			}
			if (setElement.getCurrencyCode().equals(fx.getCcyPair().replace("\"", "").substring(3, 6))) {
				return "passed: currency is valid";
			}
		}

		return "not passed: currency pairs are not valid";
	}

	public String validateExcerciseDate(FXBase fx) {
		String excerciseStartDateStr = "";
		String tradeDateStr = "";
		String expiryDateStr = "";

		String resultStr = "";
		
		excerciseStartDateStr = ((FXOptionsAmerican)fx).getExcerciseStartDate().trim().replace("\"", "");
		tradeDateStr = ((FXOptionsAmerican)fx).getTradeDate().trim().replace("\"", "");
		expiryDateStr = ((FXOptionsAmerican)fx).getExpiryDate().trim().replace("\"", "");

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

		return resultStr;
	}

	public String validateExpiryDate(FXBase fx, String style) {//passed
		String deliveryDateStr = "";
		String premiumDateStr = "";
		String expiryDateStr = "";

		String resultStr = "";
		if(style.equals("american")){
			deliveryDateStr = ((FXOptionsAmerican)fx).getDeliveryDate().trim().replace("\"", "");
			premiumDateStr = ((FXOptionsAmerican)fx).getPremiumDate().trim().replace("\"", "");
			expiryDateStr = ((FXOptionsAmerican)fx).getExpiryDate().trim().replace("\"", "");
		}else{
			deliveryDateStr = ((FXOptionsEuropean)fx).getDeliveryDate().trim().replace("\"", "");
			premiumDateStr = ((FXOptionsEuropean)fx).getPremiumDate().trim().replace("\"", "");
			expiryDateStr = ((FXOptionsEuropean)fx).getExpiryDate().trim().replace("\"", "");
		}
		
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
		
		return resultStr;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String validate(@QueryParam("type") String type, @QueryParam("style") String style) {

		String validationResults = "";
		try {
			List<FXBase> data = reader.getFXData(type, style);

			for (int i = 0; i < data.size(); i++) {

				validationResults += validateCounterparty(data.get(i)) + " for customer"+data.get(i).getCustomer()+" with type "+data.get(i).getType()+" for record # "+(i+1)+"\n\n";
				validationResults += validateCurrency(data.get(i)) + " for customer"+data.get(i).getCustomer()+" with type "+data.get(i).getType()+" for record # "+(i+1)+"\n\n";

				if (type.equals("spot") || type.equals("forward")) {
					validationResults += validateValueDateBeforeTradeDate(data.get(i), type) + " for customer"+data.get(i).getCustomer()+" with type "+data.get(i).getType()+" for record # "+(i+1)+"\n\n";
					validationResults += validateValueDateNonWorkingDay(data.get(i), type) + " for customer"+data.get(i).getCustomer()+" with type "+data.get(i).getType()+" for record # "+(i+1)+"\n\n";
				}

				if (type.equals("options")) {
					if (style.equals("american")) {
						validationResults += validateExcerciseDate(data.get(i)) + " for customer"+((FXOptions) data.get(i)).getCustomer()+" with type "+((FXOptions) data.get(i)).getCustomer()+" and style"+((FXOptions) data.get(i)).getStyle()+" for record # "+(i+1)+"\n\n";
					}
					validationResults += validateExpiryDate(data.get(i),style) + " for customer"+((FXOptions) data.get(i)).getCustomer()+" with type "+((FXOptions) data.get(i)).getCustomer()+" and style"+((FXOptions) data.get(i)).getStyle()+" for record # "+(i+1)+"\n\n";
				}
			}
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}

		return validationResults;
	}

}
