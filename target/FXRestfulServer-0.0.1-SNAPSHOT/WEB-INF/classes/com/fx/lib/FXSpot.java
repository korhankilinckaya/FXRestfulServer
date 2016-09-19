package com.fx.lib;

public class FXSpot extends FXBase{
	private String valueDate;

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	@Override
	public FXBase fill(String line) {
		FXSpot retVal = new FXSpot();
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
			
			if(splittedByDots[0].equals("\"valueDate\"")){
				retVal.setValueDate(splittedByDots[1]);
			}
			
		}
		
		return retVal;

	}
	
	@Override
	public String toString(){
		return "customer "+getCustomer()+" type "+getType()+" direction" + getDirection();
	}

}
