package sandbox;

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


public class CreateHatarakuKurumaSmartPhoneNode {

	public static void main(String[] args){
		try {
			try {
				new CreateHatarakuKurumaSmartPhoneNode();
			} catch (InterruptedException e) {
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
	
	public CreateHatarakuKurumaSmartPhoneNode() throws SmackException, IOException, XMPPException, InterruptedException{
		

		//you have to connect with JID and password to create node. you cannot create node with anonymous login.
		SoxConnection con = new SoxConnection("nictsox-lv1.ht.sfc.keio.ac.jp","guest","miroguest",true);
		String nodeName = "藤沢はたらく車スマホ_kaisen134";

		/**
		 * Create sensor
		 */		
		
		//set device info and it's transducer meta data
		Device device = new Device();
		device.setId(nodeName);
		device.setDeviceType(DeviceType.VEHICLE);
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
		temp.setName("time");
		temp.setId("time");
		temp.setUnits("time");
		transducers.add(temp);
		
		Transducer temp1 = new Transducer();
		temp1.setName("accel_x");
		temp1.setId("accel_x");
		temp1.setUnits("m/s2");
		transducers.add(temp1);
		
		Transducer temp2 = new Transducer();
		temp2.setName("accel_y");
		temp2.setId("accel_y");
		temp2.setUnits("m/s2");
		transducers.add(temp2);
		
		
		Transducer temp3 = new Transducer();
		temp3.setName("accel_z");
		temp3.setId("accel_z");
		temp3.setUnits("m/s2");
		transducers.add(temp3);
		
		
		
		
		device.setTransducers(transducers);
		
		
		//create node
		con.createNode(nodeName, device, AccessModel.open,PublishModel.open);
		
		System.out.println("node created!");

	
		
	}
	
}
