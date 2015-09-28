package jp.ac.keio.sfc.ht.sox.soxlib.event;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValueTuple;

public class SoxTupleEvent extends EventObject{
	
	private Device device;
	private List<TransducerValue> transducerValues;
	private List<TransducerValueTuple> transducerValueTuples;
	
	public SoxTupleEvent(Object source, Device _device, List<TransducerValue> _transducerValues, List<TransducerValueTuple> _transducerValueTuples){
		super(source);
		this.device = _device;
		this.transducerValues = _transducerValues;
		this.transducerValueTuples = _transducerValueTuples;
	}

	public Device getDevice() {
		return device;
	}


	public List<TransducerValue> getTransducerValues() {
		return transducerValues;
	}

	
	public List<TransducerValueTuple> getTransducerValueTuples(){
		return transducerValueTuples;
	}

}
