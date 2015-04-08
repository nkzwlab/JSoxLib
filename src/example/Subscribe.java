/*
 * Copyright (C) 2014 Takuro Yonezawa
 * Keio University, Japan
 */

package example;

import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.*;
import jp.ac.keio.sfc.ht.sox.soxlib.*;
import jp.ac.keio.sfc.ht.sox.soxlib.event.*;

public class Subscribe implements SoxEventListener, SoxTupleEventListener {

	
	public static void main(String[] args) throws Exception {
		new Subscribe();
	}

	
	public Subscribe() throws Exception {

		
		//SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", "guest","miroguest", true);
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", true); //anonymous login

		SoxDevice soxDevice = new SoxDevice(con, "hogehoge");

		soxDevice.subscribe();
		soxDevice.addSoxEventListener(this); //for soxEvent
		
		//soxDevice.addSoxTupleEventListener(this); //for soxTupleEvent

		
		/**
		 * The threads that are handling various event dispatches (such as waiting
		 * on pub sub notifications) are daemon threads, so if we exit here, the
		 * other threads will die too. So, sit and spin. Pick a big timeout (60
		 * seconds) so that we don't use up much CPU while waiting on notifications.
		 */
		while (Utility.simpleSleepMs(60000)) {
			// sleep until interrupted
		}
		
	}

	public void handlePublishedSoxEvent(SoxEvent e) {
		
		Device device = e.getDevice();
		Transducer transducer = e.getTransducer();
		TransducerValue value = e.getTransducerValue();
		if (transducer != null && device != null && value != null) {
			System.out.println("-----------------------");
			System.out.println("Device[name:" + device.getName() + ", type:"
					+ device.getDeviceType().toString() + "]");
			System.out.println("Transducer[name:" + transducer.getName()
					+ ", id:" + transducer.getId() + ", unit:"
					+ transducer.getUnits() + "]");
			System.out.println("TransducerValue[id:" + value.getId()
					+ ", rawValue:" + value.getRawValue() + ", typedValue:"
					+ value.getTypedValue() + ", timestamp:"
					+ value.getTimestamp() + "]");
			

		}
		
	}

	public void handlePublishedSoxTupleEvent(SoxTupleEvent e) {
		// TODO Auto-generated method stub
		List<TransducerValue> values = e.getTransducerValueList();
		for(TransducerValue value:values){
			System.out.println("in sox tuple event"+value.getId());
		}

	}
}
