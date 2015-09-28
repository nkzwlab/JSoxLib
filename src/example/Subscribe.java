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

		
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", true); //anonymous login
		//SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", "guest","miroguest", true); //login with JID and password
		
		/** Create new device object from virtualized device **/
		SoxDevice exampleDevice = new SoxDevice(con, "TupleExample"); 
		//SoxDevice exampleDevice = new SoxDevice(con, "hogehoge","takusox.ht.sfc.keio.ac.jp"); //you can specify another SOX server where the node exists
		

		/** Getting Device Meta Data **/
		Device deviceInfo = exampleDevice.getDevice();
		
		System.out.println("[Device Meta Info] ID:"+deviceInfo.getId()+", Name:"+deviceInfo.getName()+" Type:"+deviceInfo.getDeviceType().toString());
		List<Transducer> transducerList = deviceInfo.getTransducers();
		for(Transducer t:transducerList){
			System.out.println("[Transducer] Name:"+t.getName()+", ID:"+t.getId()+", unit:"+t.getUnits()+", minValue:"+t.getMinValue()+", maxValue:"+t.getMaxValue());
		}
		
		List<TransducerTuple> transducerTupleList = deviceInfo.getTransducerTuples();
		for(TransducerTuple tuple:transducerTupleList){
			System.out.println("-----TUPLE: name:"+tuple.getName()+" ID:"+tuple.getId()+"----");
			for(Transducer t:tuple.getTransducers()){
				System.out.println("[Transducer] Name:"+t.getName()+", ID:"+t.getId()+", unit:"+t.getUnits()+", minValue:"+t.getMinValue()+", maxValue:"+t.getMaxValue());				
			}
			System.out.println("---------------------");
		}
		
		exampleDevice.subscribe();
		exampleDevice.addSoxEventListener(this);
		//exampleDevice.addSoxTupleEventListener(this);  //this is another type of event listener
	
		
	}

	public void handlePublishedSoxEvent(SoxEvent e) {
		
		Device device = e.getDevice();
		Transducer transducer = e.getTransducer();
		TransducerValue value = e.getTransducerValue();
		TransducerTuple tuple = e.getTransducerTuple();
		TransducerValueTuple valueTuple = e.getTransducerValueTuple();
		
		System.out.println(":::::Received Data:::::");
		
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
		
		if(device!=null && tuple!=null && valueTuple!=null){
			System.out.println("----TransducerTuple Start ID:"+valueTuple.getId()+" -----");

			for(TransducerValue tvalue:valueTuple.getTransducerValues()){

					System.out.println("TransducerValue[id:" + tvalue.getId()
							+ ", rawValue:" + tvalue.getRawValue() + ", typedValue:"
							+ tvalue.getTypedValue() + ", timestamp:"
							+ tvalue.getTimestamp() + "]");
				
			}
			System.out.println("----TransducerTuple End-----");
		}
		
	}

	public void handlePublishedSoxTupleEvent(SoxTupleEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println(":::::Received Data:::::");
		
		List<TransducerValue> values = e.getTransducerValues();
		for(TransducerValue value:values){
			System.out.println("TransducerValue[id:" + value.getId()
					+ ", rawValue:" + value.getRawValue() + ", typedValue:"
					+ value.getTypedValue() + ", timestamp:"
					+ value.getTimestamp() + "]");
		}

		List<TransducerValueTuple> valueTuples = e.getTransducerValueTuples();
		for(TransducerValueTuple tuple:valueTuples){
			System.out.println("----TransducerTuple Start ID:"+tuple.getId()+" -----");
			for(TransducerValue value:tuple.getTransducerValues()){
				System.out.println("TransducerValue[id:" + value.getId()
						+ ", rawValue:" + value.getRawValue() + ", typedValue:"
						+ value.getTypedValue() + ", timestamp:"
						+ value.getTimestamp() + "]");
			}
			System.out.println("----TransducerTuple End-----");
		}
		
	}
}
