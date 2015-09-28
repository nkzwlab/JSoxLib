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
		
		SoxDevice soxDevice = new SoxDevice(con, "htPriusMessanger");
		//SoxDevice soxDevice = new SoxDevice(con, "hogehoge","takusox.ht.sfc.keio.ac.jp"); //you can specify another SOX server where the node exists
		
		

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
			value2.setId("user");
			value2.setRawValue("たくろう"); //celcius
			value2.setTypedValue("たくろう"); //celcius
			value2.setCurrentTimestamp();
			
			valueList.add(value2);

			TransducerValue value3 = new TransducerValue();
			value3.setId("comment");
			value3.setRawValue("気をつけて帰ってね！"); //percent
			value3.setTypedValue("気をつけて帰ってね！"); //percent
			value3.setCurrentTimestamp();
			
			valueList.add(value3);

			
			soxDevice.publishValues(valueList);
				
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
