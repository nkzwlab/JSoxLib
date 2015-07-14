package sandbox;

import java.io.IOException;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxEvent;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxEventListener;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxTupleEvent;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxTupleEventListener;

public class CrossDomainPubSubTest implements SoxEventListener, SoxTupleEventListener{

	public static void main(String[] args){
		new CrossDomainPubSubTest();
	}
	
	public CrossDomainPubSubTest(){
		
		try {
			SoxConnection con = new SoxConnection("takusox.ht.sfc.keio.ac.jp",  true);
		
			try {
				SoxDevice exampleDevice = new SoxDevice(con, "hogehoge","sox.ht.sfc.keio.ac.jp");
				
				Device deviceInfo = exampleDevice.getDevice();
				
				System.out.println("[Device Meta Info] ID:"+deviceInfo.getId()+", Name:"+deviceInfo.getName()+" Type:"+deviceInfo.getDeviceType().toString());
				List<Transducer> transducerList = deviceInfo.getTransducers();
				for(Transducer t:transducerList){
					System.out.println("[Transducer] Name:"+t.getName()+", ID:"+t.getId()+", unit:"+t.getUnits()+", minValue:"+t.getMinValue()+", maxValue:"+t.getMaxValue());
				}
				
				
				exampleDevice.subscribe();
				exampleDevice.addSoxEventListener(this);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		

		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
