package example;

import java.util.ArrayList;
import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;

public class Publish {

	public static void main(String[] args) throws Exception {
		new Publish();
	}

	public Publish() throws Exception {

		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp",  true); //anonymous login
		//SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", "guest","miroguest", true);
		
		SoxDevice soxDevice = new SoxDevice(con, "mogeraTest");

		// Single value publishing
		/**
		TransducerValue value = new TransducerValue();
		value.setId("temperature");
		value.setRawValue("15");
		value.setTypedValue("15");
		value.setCurrentTimestamp();

		soxDevice.publishValue(value);
	**/
		// Multiple values publishing
		
		while (true) {
			
		
			List<TransducerValue> valueList = new ArrayList<TransducerValue>();

			TransducerValue value2 = new TransducerValue();
			value2.setId("temperature");
			String do1 = new Double(Math.random()*40).toString();
			value2.setRawValue(do1); //celcius
			value2.setTypedValue(do1); //celcius
			value2.setCurrentTimestamp();
			
			valueList.add(value2);

			TransducerValue value3 = new TransducerValue();
			String do2 = new Double(Math.random()*100).toString();
			value3.setId("humidity");
			value3.setRawValue(do2); //percent
			value3.setTypedValue(do2); //percent
			value3.setCurrentTimestamp();
			
			valueList.add(value3);

			
			soxDevice.publishValues(valueList);
				
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
