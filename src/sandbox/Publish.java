package sandbox;

import java.util.ArrayList;
import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;

public class Publish {

	public static void main(String[] args) throws Exception {
		new Publish();
	}

	public Publish() throws Exception {

		SoxConnection con = new SoxConnection("nictsox-lv1.ht.sfc.keio.ac.jp",  false); //anonymous login
		//SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", "guest","miroguest", true);
		
		SoxDevice soxDevice = new SoxDevice(con, "testNode");
		//SoxDevice soxDevice = new SoxDevice(con, "testNode","takusox.ht.sfc.keio.ac.jp"); //you can specify another SOX server where the node exists

		/** Getting Device Meta Data **/
		Device deviceInfo = soxDevice.getDevice();

		System.out.println("[Device Meta Info] ID:" + deviceInfo.getId()
				+ ", Name:" + deviceInfo.getName() + " Type:"
				+ deviceInfo.getDeviceType().toString());
		List<Transducer> transducerList = deviceInfo.getTransducers();
		for (Transducer t : transducerList) {
			System.out.println("[Transducer] Name:" + t.getName() + ", ID:"
					+ t.getId() + ", unit:" + t.getUnits() + ", minValue:"
					+ t.getMinValue() + ", maxValue:" + t.getMaxValue());
		}
		

		while (true) {
		
			List<TransducerValue> valueList = new ArrayList<TransducerValue>();

			TransducerValue value2 = new TransducerValue();
			value2.setId("latitude");
			value2.setRawValue("35.3892923"); 
			value2.setTypedValue("35.3892923");
			value2.setCurrentTimestamp();
			
			valueList.add(value2);

			TransducerValue value3 = new TransducerValue();
			value3.setId("longitude");
			value3.setRawValue("139.4316725"); 
			value3.setTypedValue("139.4316725"); 
			value3.setCurrentTimestamp();
			
			valueList.add(value3);
			
			TransducerValue value4 = new TransducerValue();
			value4.setId("temperature");
			value4.setRawValue("23"); 
			value4.setTypedValue("23");
			value4.setCurrentTimestamp();
			
			valueList.add(value4);
			
			soxDevice.publishValues(valueList);
			
			System.out.println("Published !");
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
