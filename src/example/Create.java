package example;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.PublishModel;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.DeviceType;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;


public class Create {

	public static void main(String[] args){
		try {
			new Create();
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
	
	public Create() throws SmackException, IOException, XMPPException{
		

		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp","guest","miroguest", true);
		String nodeName = "testSensor";


		/**
		 * Create sensor
		 */
		
		//set device info and it's transducer meta data
		Device device = new Device();
		device.setId(nodeName);
		device.setDeviceType(DeviceType.OUTDOOR_WEATHER);
		device.setName(nodeName);
		
		List<Transducer> transducers = new ArrayList<Transducer>();
		Transducer t = new Transducer();
		t.setName("temperature");
		t.setId("temperature");
		t.setUnits("celius");
		t.setMinValue(-30f);
		t.setMaxValue(100f);
		t.setResolution(0.1f);
		transducers.add(t);
		
		Transducer t2 = new Transducer();
		t2.setName("humidity");
		t2.setId("humidity");
		t2.setUnits("percent");
		t2.setMinValue(0f);
		t2.setMaxValue(100f);
		t.setResolution(0.1f);
		transducers.add(t2);
		
		
		Transducer t3 = new Transducer();
		t3.setName("longitude");
		t3.setId("longitude");
		t3.setUnits("longitude");
		transducers.add(t3);
		
		Transducer t4 = new Transducer();
		t4.setName("latitude");
		t4.setId("latitude");
		t4.setUnits("latitude");
		transducers.add(t4);
		
		device.setTransducers(transducers);

		//create node
		con.createNode(nodeName, device, AccessModel.open,PublishModel.open);
		
		
		
		/*
		 * If you want to delete sensor node, please use following code
		 */
		
		//con.deleteNode(nodeName);
		
	}
	
}
