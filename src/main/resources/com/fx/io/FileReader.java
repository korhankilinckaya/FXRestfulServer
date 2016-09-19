package com.fx.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fx.lib.FXBase;
import com.fx.lib.FXForward;
import com.fx.lib.FXOptionsAmerican;
import com.fx.lib.FXOptionsEuropean;
import com.fx.lib.FXSpot;

public class FileReader {

	FileInputStream fstream;
	BufferedReader br;

	public List<FXBase> getFXData(String type, String style) throws IOException, FileNotFoundException {

		List<FXBase> list = new ArrayList<FXBase>();

		fstream = new FileInputStream("/Users/korhankilinckaya/Documents/workspace/FXRestfulServer/FXData/fxData.txt");

		br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		String searchString = new String();

		while ((strLine = br.readLine()) != null) {

			if (type.equals("spot")) {
				searchString = "Spot";
				FXBase base = new FXSpot();
				if (strLine.contains(searchString)) {
					list.add(base.fill(strLine));
				}
			} else if (type.equals("forward")) {
				searchString = "Forward";
				FXBase base = new FXForward();
				if (strLine.contains(searchString)) {
					list.add(base.fill(strLine));
				}
			} else if (type.equals("options")) {
				searchString = "VanillaOption";

				if (strLine.contains(searchString)) {
					if (style.equals("european")) {

						searchString = "EUROPEAN";
						FXBase base = new FXOptionsEuropean();
						if (strLine.contains(searchString)) {
							list.add(base.fill(strLine));
						}
					} else if (style.equals("american")) {

						searchString = "AMERICAN";
						FXBase base = new FXOptionsAmerican();
						if (strLine.contains(searchString)) {
							list.add(base.fill(strLine));
						}
					}
				}
			}

		}

		br.close();
		fstream.close();

		return list;
	}

}
