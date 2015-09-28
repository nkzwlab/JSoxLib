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
		String nodeName = "SmartPhone_example";

		//con.deleteNode(nodeName);
		
		/**
		 * Create sensor
		 */
		
		
		
		//set device info and it's transducer meta data
		Device device = new Device();
		device.setId(nodeName);
		device.setDeviceType(DeviceType.PARTICIPATORY);
		device.setName(nodeName);
		

		List<Transducer> transducers = new ArrayList<Transducer>();

		
		Transducer tcb = new Transducer();
		tcb.setName("back_camera");
		tcb.setId("back_camera");
		tcb.setUnits("image");
		transducers.add(tcb);
		
		
		Transducer t = new Transducer();
		t.setName("accelerometer_x");
		t.setId("accelerometer_x");
		t.setUnits("m/s2");
		transducers.add(t);
		

		Transducer t2 = new Transducer();
		t2.setName("accelerometer_y");
		t2.setId("accelerometer_y");
		t2.setUnits("m/s2");
		transducers.add(t2);

		
		Transducer t3 = new Transducer();
		t3.setName("accelerometer_z");
		t3.setId("accelerometer_z");
		t3.setUnits("m/s2");
		transducers.add(t3);

		
		Transducer t4 = new Transducer();
		t4.setName("yaw");
		t4.setId("yaw");
		t4.setUnits("radian");
		transducers.add(t4);

		Transducer t5 = new Transducer();
		t5.setName("roll");
		t5.setId("roll");
		t5.setUnits("radian");
		transducers.add(t5);

		Transducer t6 = new Transducer();
		t6.setName("pitch");
		t6.setId("pitch");
		t6.setUnits("radian");
		transducers.add(t6);

		Transducer t7 = new Transducer();
		t7.setName("longitude");
		t7.setId("longitude");
		t7.setUnits("degree");
		transducers.add(t7);

		Transducer t8 = new Transducer();
		t8.setName("latitude");
		t8.setId("latitude");
		t8.setUnits("degree");
		transducers.add(t8);
		
		Transducer t12 = new Transducer();
		t12.setName("altitude");
		t12.setId("altitude");
		t12.setUnits("meter");
		transducers.add(t12);
				

		Transducer t9 = new Transducer();
		t9.setName("orientation");
		t9.setId("orientation");
		t9.setUnits("degree");
		transducers.add(t9);

		Transducer t10 = new Transducer();
		t10.setName("air_pressure");
		t10.setId("air_pressure");
		t10.setUnits("hpa");
		transducers.add(t10);
		
		Transducer t11 = new Transducer();
		t11.setName("speed");
		t11.setId("speed");
		t11.setUnits("m/s");
		transducers.add(t11);
		
		
		Transducer t13 = new Transducer();
		t13.setName("audio_level");
		t13.setId("audio_level");
		t13.setUnits("db");
		transducers.add(t13);
		
		Transducer t14 = new Transducer();
		t14.setName("battery");
		t14.setId("battery");
		t14.setUnits("percent");
		transducers.add(t14);
		
		device.setTransducers(transducers);
		
		
		//create node
		con.createNode(nodeName, device, AccessModel.open,PublishModel.open);
		System.out.println("node created!");


		
		/*
		 * If you want to delete sensor node, please use following code
		 */
		
		
		//con.deleteNode(nodeName);

		
	}
	
}
