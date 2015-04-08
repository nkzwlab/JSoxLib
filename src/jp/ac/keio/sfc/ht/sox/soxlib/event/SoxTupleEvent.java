package jp.ac.keio.sfc.ht.sox.soxlib.event;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;

public class SoxTupleEvent extends EventObject{
	
	private Device device;
	private List<TransducerValue> transducerValueList;
	
	public SoxTupleEvent(Object source, Device _device, List<TransducerValue> _transducerValueList){
		super(source);
		this.device = _device;
		this.transducerValueList = _transducerValueList;
	}

	public Device getDevice() {
		return device;
	}


	public List<TransducerValue> getTransducerValueList() {
		return transducerValueList;
	}

	

}
