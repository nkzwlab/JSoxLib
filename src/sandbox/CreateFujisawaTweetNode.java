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


public class CreateFujisawaTweetNode {

	public static void main(String[] args){
		try {
			try {
				new CreateFujisawaTweetNode();
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
	
	public CreateFujisawaTweetNode() throws SmackException, IOException, XMPPException, InterruptedException{
		

		//you have to connect with JID and password to create node. you cannot create node with anonymous login.
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp","guest","miroguest",true);
		String nodeName = "fujisawaGeoTweets";

		/**
		 * Create sensor
		 */		
		
		//set device info and it's transducer meta data
		Device device = new Device();
		device.setId(nodeName);
		device.setDeviceType(DeviceType.OTHER);
		device.setName(nodeName);

		List<Transducer> transducers = new ArrayList<Transducer>();

		Transducer cpu_1min = new Transducer();
		cpu_1min.setName("user");
		cpu_1min.setId("user");
		cpu_1min.setUnits("");
		transducers.add(cpu_1min);
		
		
		Transducer cpu_5min = new Transducer();
		cpu_5min.setName("text");
		cpu_5min.setId("text");
		cpu_5min.setUnits("");
		transducers.add(cpu_5min);
		

		Transducer cpu_15min = new Transducer();
		cpu_15min.setName("latitude");
		cpu_15min.setId("latitude");
		cpu_15min.setUnits("latitude");
		transducers.add(cpu_15min);
		
		
		//From here for stats log
		Transducer dbmin = new Transducer();
		dbmin.setName("longitude");
		dbmin.setId("longitude");
		dbmin.setUnits("longitude");
		transducers.add(dbmin);

		Transducer dbmax = new Transducer();
		dbmax.setName("image");
		dbmax.setId("image");
		dbmax.setUnits("jpeg");
		transducers.add(dbmax);

		
		
		device.setTransducers(transducers);
		
		
		//create node
		con.createNode(nodeName, device, AccessModel.open,PublishModel.open);
		
		System.out.println("node created!");

	
		
	}
	
}
