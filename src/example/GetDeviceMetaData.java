package example;

import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.Data;
import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;

public class GetDeviceMetaData {
	public static void main(String[] args) throws Exception{
		new GetDeviceMetaData();
	}
	
	public GetDeviceMetaData() throws Exception{
		
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp",true); //anonymous login
		SoxDevice soxDevice = new SoxDevice(con, "hogehoge");

		Device device = soxDevice.getDevice();
		
		System.out.println("[Device Meta Info] ID:"+device.getId()+", Name:"+device.getName()+" Type:"+device.getDeviceType().toString());
		List<Transducer> transducerList = device.getTransducers();
		for(Transducer t:transducerList){
			System.out.println("[Transducer] Name:"+t.getName()+", ID:"+t.getId()+", unit:"+t.getUnits()+", minValue:"+t.getMinValue()+", maxValue"+t.getMaxValue());
		}
	

		System.out.println("Getting Last published Item");
		Data data = soxDevice.getLastPublishData();
		for(TransducerValue t:data.getTransducerValue()){
			System.out.println("[TransducerValue] ID:"+t.getId()+" timestamp:"+t.getTimestamp()+" rawValue:"+t.getRawValue()+" typedValue:"+t.getTypedValue());
		}

	}

}
