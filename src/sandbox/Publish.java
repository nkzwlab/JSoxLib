package sandbox;

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

		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp",  true);
		SoxDevice soxDevice = new SoxDevice(con, "example");

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
			

			List<TransducerValue> values = new ArrayList<TransducerValue>();

			TransducerValue tValue1 = new TransducerValue();
			tValue1.setId("temperature");
			String val1 = new Float(27.5f).toString();
			tValue1.setRawValue(val1);
			tValue1.setTypedValue(val1);
			tValue1.setCurrentTimestamp();
			values.add(tValue1);

			TransducerValue tValue2 = new TransducerValue();
			tValue2.setId("humidity");
			String val2 = new Float(85.1).toString();
			tValue2.setRawValue(val2);
			tValue2.setTypedValue(val2);
			tValue2.setCurrentTimestamp();
			values.add(tValue2);

			TransducerValue tValue3 = new TransducerValue();
			tValue3.setId("latitude");
			tValue3.setRawValue("35.30024");
			tValue3.setTypedValue("35.30024");
			tValue3.setCurrentTimestamp();
			values.add(tValue3);
			
			TransducerValue tValue4 = new TransducerValue();
			tValue4.setId("longitude");
			tValue4.setRawValue("139.479467");
			tValue4.setTypedValue("139.479467");
			tValue4.setCurrentTimestamp();
			values.add(tValue4);
			
			soxDevice.publishValues(values);
			
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
