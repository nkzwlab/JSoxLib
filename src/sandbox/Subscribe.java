/*
 * Copyright (C) 2014 Takuro Yonezawa
 * Keio University, Japan
 */

package sandbox;

import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.*;
import jp.ac.keio.sfc.ht.sox.soxlib.*;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxEvent;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxEventListener;

public class Subscribe implements SoxEventListener {

	public static void main(String[] args) throws Exception {
		new Subscribe();
	}

	public Subscribe() throws Exception {

		//anonymous login
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp","takuro","minatakuro",true); 
		
		//login with JID and password
		// SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp",
		// "guest","miroguest", true); 

		/** Create new device object from virtualized device **/
		SoxDevice exampleDevice = new SoxDevice(con, "soxStat","nictsox-lv1.ht.sfc.keio.ac.jp");

		//you can specify another SOX server where the node exists
		// SoxDevice exampleDevice = new SoxDevice(con,
		// "testNode","takusox.ht.sfc.keio.ac.jp");

		/** Getting Device Meta Data **/
		Device deviceInfo = exampleDevice.getDevice();

		System.out.println("[Device Meta Info] ID:" + deviceInfo.getId()
				+ ", Name:" + deviceInfo.getName() + " Type:"
				+ deviceInfo.getDeviceType().toString());
		List<Transducer> transducerList = deviceInfo.getTransducers();
		for (Transducer t : transducerList) {
			System.out.println("[Transducer] Name:" + t.getName() + ", ID:"
					+ t.getId() + ", unit:" + t.getUnits() + ", minValue:"
					+ t.getMinValue() + ", maxValue:" + t.getMaxValue());
		}

		Data data = exampleDevice.getLastPublishData();
		List<TransducerValue> values = data.getTransducerValue();
		System.out.println("--last published data starts--");
		for (TransducerValue value : values) {
			System.out.println("TransducerValue[id:" + value.getId()
					+ ", rawValue:" + value.getRawValue() + ", typedValue:"
					+ value.getTypedValue() + ", timestamp:"
					+ value.getTimestamp() + "]");
		}
		System.out.println("--last published data ends --");
		
		exampleDevice.subscribe();
		exampleDevice.addSoxEventListener(this);

		/*
		 * If you close the program: 
		 * exampleDevice.unsubscribe();
		 * exampleDevice.removeSoxEventListener(); 
		 * con.disconnect();
		 */
	}

	public void handlePublishedSoxEvent(SoxEvent e) {
		// TODO Auto-generated method stub

		System.out.println(":::::Received Data:::::");

		List<TransducerValue> values = e.getTransducerValues();
		for (TransducerValue value : values) {
			System.out.println("TransducerValue[id:" + value.getId()
					+ ", rawValue:" + value.getRawValue() + ", typedValue:"
					+ value.getTypedValue() + ", timestamp:"
					+ value.getTimestamp() + "]");
		}

	}
}
