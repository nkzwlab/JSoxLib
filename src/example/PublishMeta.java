package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.DeviceType;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;

/*
 * Notice: please use publish meta function if you would like to re-publish meta data.
 * Usually meta data was post when the sensor node created.
 */

public class PublishMeta {

	public static void main(String[] args) {

		try {
			new PublishMeta();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public PublishMeta() throws Exception {

		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", true); // anonymous login
		String nodeName = "hogehoge";

		SoxDevice soxDevice = new SoxDevice(con, nodeName);

		// set device info and it's transducer meta data
		Device device = new Device();
		device.setId(nodeName);
		device.setDeviceType(DeviceType.OUTDOOR_WEATHER);
		device.setName(nodeName);

		List<Transducer> transducers = new ArrayList<Transducer>();
		Transducer t = new Transducer();
		t.setName("humidity");
		t.setId("humidity");
		t.setUnits("percent");
		transducers.add(t);
		
		Transducer t2 = new Transducer();
		t2.setName("temperature");
		t2.setId("temperature");
		t2.setUnits("celcius");
		transducers.add(t2);
	
		device.setTransducers(transducers);
		
		
		soxDevice.publishMetaData(device); //Publish Meta
		
	}

}
