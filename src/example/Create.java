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
		

		//you have to connect with JID and password to create node. you cannot create node with anonymous login.
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp","guest","miroguest",true);
		String nodeName = "testNode";

		/**
		 * Create sensor
		 */		
		
		//set device info and it's transducer meta data
		Device device = new Device();
		device.setId(nodeName);
		device.setDeviceType(DeviceType.OUTDOOR_WEATHER);
		device.setName(nodeName);

		List<Transducer> transducers = new ArrayList<Transducer>();

		Transducer lat = new Transducer();
		lat.setName("latitude");
		lat.setId("latitude");
		lat.setUnits("lat");
		transducers.add(lat);
		
		
		Transducer lon = new Transducer();
		lon.setName("longitude");
		lon.setId("longitude");
		lon.setUnits("lon");
		transducers.add(lon);
		

		Transducer temp = new Transducer();
		temp.setName("temperature");
		temp.setId("temperature");
		temp.setUnits("celcius");
		transducers.add(temp);
		
		device.setTransducers(transducers);
		
		
		//create node
		con.createNode(nodeName, device, AccessModel.open,PublishModel.open);
		
		System.out.println("node created!");

	
		
	}
	
}
