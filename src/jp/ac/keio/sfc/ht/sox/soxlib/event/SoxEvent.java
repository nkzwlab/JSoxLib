/*
 * Copyright (C) 2014 Takuro Yonezawa
 * Keio University, Japan
 */


package jp.ac.keio.sfc.ht.sox.soxlib.event;

import java.util.EventObject;
import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerTuple;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValueTuple;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;

public class SoxEvent extends EventObject{

	private Device device;
	private Transducer transducer;
	private TransducerValue transducerValue;
	private TransducerTuple transducerTuple;
	private TransducerValueTuple transducerValueTuple;
	
	public SoxEvent (Object source, Device _device, Transducer _transducer, TransducerValue _transducerValue, TransducerTuple _transducerTuple, TransducerValueTuple _transducerValueTuple){
		super(source);
		this.device = _device;
		this.transducer = _transducer;
		this.transducerValue = _transducerValue;
		this.transducerTuple = _transducerTuple;
		this.transducerValueTuple = _transducerValueTuple;
	}

	public Device getDevice() {
		return device;
	}

	public Transducer getTransducer() {
		return transducer;
	}

	public TransducerValue getTransducerValue() {
		return transducerValue;
	}

	public TransducerTuple getTransducerTuple(){
		return transducerTuple;
	}
	
	
	public TransducerValueTuple getTransducerValueTuple(){
		return transducerValueTuple;
	}

}
