package jp.ac.keio.sfc.ht.sox.soxlib.event;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;

public class SoxEvent extends EventObject{
	
	private String nodeID;
	private Device device;
	private String originServer;
	private List<TransducerValue> transducerValues;
	
	public SoxEvent(Object source, String _originServer, Device _device, List<TransducerValue> _transducerValues){
		super(source);
		this.device = _device;
		this.originServer = _originServer;
		this.nodeID = _device.getId();
		this.transducerValues = _transducerValues;
	}
	
	public SoxEvent(Object source, String _originServer, String _name, List<TransducerValue> _transducerValues){
		super(source);
		this.originServer = _originServer;
		this.nodeID = _name;
		this.transducerValues = _transducerValues;
	}

	public Device getDevice() {
		return device;
	}

	public String getNodeID(){
		return nodeID;
	}
	
	public String getOriginServer(){
		return originServer;
	}

	public List<TransducerValue> getTransducerValues() {
		return transducerValues;
	}

	

}
