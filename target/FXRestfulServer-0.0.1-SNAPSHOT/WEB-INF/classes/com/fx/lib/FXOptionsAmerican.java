package com.fx.lib;

public class FXOptionsAmerican extends FXOptions{
	private String excerciseStartDate;

	public String getExcerciseStartDate() {
		return excerciseStartDate;
	}

	public void setExcerciseStartDate(String excerciseStartDate) {
		this.excerciseStartDate = excerciseStartDate;
	}

	@Override
	public FXBase fill(String line) {
		// TODO Auto-generated method stub
		FXOptionsAmerican retVal = new FXOptionsAmerican();
		line = line.replace("{", "");
		line = line.replace("}", "");
		String[] splittedByComma = line.split(",");
		for(int i = 0; i<splittedByComma.length; i++){
			String[] splittedByDots = splittedByComma[i].split(":");
			
			if(splittedByDots[0].equals("\"customer\"")){
				retVal.setCustomer(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"ccyPair\"")){
				retVal.setCcyPair(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"type\"")){
				retVal.setType(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"direction\"")){
				retVal.setDirection(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"tradeDate\"")){
				retVal.setTradeDate(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"amount1\"")){
				retVal.setAmount1(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"amount2\"")){
				retVal.setAmount2(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"rate\"")){
				retVal.setRate(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"legalEntity\"")){
				retVal.setLegalEntity(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"trader\"")){
				retVal.setTrader(splittedByDots[1]);
			}
			
			if(splittedByDots[0].equals("\"style\"")){
				retVal.setStyle(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"strategy\"")){
				retVal.setStrategy(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"deliveryDate\"")){
				retVal.setDeliveryDate(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"expiryDate\"")){
				retVal.setExpiryDate(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"payCcy\"")){
				retVal.setPayCcy(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"premium\"")){
				retVal.setPremium(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"premiumCcy\"")){
				retVal.setPremiumCcy(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"premiumType\"")){
				retVal.setPremiumType(splittedByDots[1]);
			}
			if(splittedByDots[0].equals("\"premiumDate\"")){
				retVal.setPremiumDate(splittedByDots[1]);
			}
			
			if(splittedByDots[0].equals("\"excerciseStartDate\"")){
				retVal.setExcerciseStartDate(splittedByDots[1]);
			}
			
		}
		
		return retVal;
	}
	
	@Override
	public String toString(){
		return "customer "+getCustomer()+" type "+getType()+" style " +getStyle()+ " direction" + getDirection();
	}

}
