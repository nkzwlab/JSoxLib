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


public class CreateReplaySensorNode {

	public static void main(String[] args){
		try {
			
			String no="";
			for(int i=0;i<100;i++){
				if(i<10){
					no = "00"+i;
				}else{
					no = "0"+i;
				}
				new CreateReplaySensorNode("carsensor_replay_"+no);
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CreateReplaySensorNode(String nodeName) throws SmackException, IOException, XMPPException, InterruptedException{
		

		//you have to connect with JID and password to create node. you cannot create node with anonymous login.
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp","guest","miroguest",true);

		/**
		 * Create sensor
		 */		
		
		//set device info and it's transducer meta data
		Device device = new Device();
		device.setId(nodeName);
		device.setDeviceType(DeviceType.OUTDOOR_WEATHER);
		device.setName(nodeName);

		List<Transducer> transducers = new ArrayList<Transducer>();
		
		Transducer ang_y = new Transducer();
		ang_y.setName("Angular Velocity Y");
		ang_y.setId("Angular Velocity Y");
		ang_y.setUnits("rad/s");
		transducers.add(ang_y);
		
		Transducer ang_z = new Transducer();
		ang_z.setName("Angular Velocity Z");
		ang_z.setId("Angular Velocity Z");
		ang_z.setUnits("rad/s");
		transducers.add(ang_z);
		
		Transducer UV = new Transducer();
		UV.setName("UV");
		UV.setId("UV");
		UV.setUnits("W/m^2");
		transducers.add(UV);
		
		Transducer Speed = new Transducer();
		Speed.setName("Speed");
		Speed.setId("Speed");
		Speed.setUnits("km/hr");
		transducers.add(Speed);
		
		Transducer ang_x = new Transducer();
		ang_x.setName("Angular Velocity X");
		ang_x.setId("Angular Velocity X");
		ang_x.setUnits("rad/s");
		transducers.add(ang_x);
		
		Transducer sat = new Transducer();
		sat.setName("Satellite Number");
		sat.setId("Satellite Number");
		transducers.add(sat);
		
		Transducer lat = new Transducer();
		lat.setName("Latitude");
		lat.setId("Latitude");
		transducers.add(lat);
		
		Transducer acc_y = new Transducer();
		acc_y.setName("Acceleration Y");
		acc_y.setId("Acceleration Y");
		acc_y.setUnits("m/s^2");
		transducers.add(acc_y);
		
		Transducer Cource = new Transducer();
		Cource.setName("Cource");
		Cource.setId("Cource");
		Cource.setUnits("°");
		transducers.add(Cource);
		
		
		Transducer acc_x = new Transducer();
		acc_x.setName("Acceleration X");
		acc_x.setId("Acceleration X");
		acc_x.setUnits("m/s^2");
		transducers.add(acc_x);
		
		Transducer lon = new Transducer();
		lon.setName("Longitude");
		lon.setId("Longitude");
		transducers.add(lon);
		
		Transducer airp = new Transducer();
		airp.setName("Atmospheric Pressure");
		airp.setId("Atmospheric Pressure");
		airp.setUnits("hPa");
		transducers.add(airp);
		
		
		Transducer sn = new Transducer();
		sn.setName("Serial Number");
		sn.setId("Serial Number");
		transducers.add(sn);
		
		Transducer acc_z = new Transducer();
		acc_z.setName("Acceleration Z");
		acc_z.setId("Acceleration Z");
		acc_z.setUnits("m/s^2");
		transducers.add(acc_z);
		
		Transducer data = new Transducer();
		data.setName("Data");
		data.setId("Data");
		transducers.add(data);
		
		Transducer humid = new Transducer();
		humid.setName("Atmospheric Humidity");
		humid.setId("Atmospheric Humidity");
		humid.setUnits("%RH");
		transducers.add(humid);
		
		Transducer pm = new Transducer();
		pm.setName("PM2.5");
		pm.setId("PM2.5");
		pm.setUnits("µg/m^3");
		transducers.add(pm);
		
		
		Transducer gmz = new Transducer();
		gmz.setName("Geomagnetism Z");
		gmz.setId("Geomagnetism Z");
		gmz.setUnits("µT");
		transducers.add(gmz);
		
		
		Transducer temp = new Transducer();
		temp.setName("Atmospheric Temperature");
		temp.setId("Atmospheric Temperature");
		temp.setUnits("°C");
		transducers.add(temp);
		
		Transducer gmx = new Transducer();
		gmx.setName("Geomagnetism X");
		gmx.setId("Geomagnetism X");
		gmx.setUnits("µT");
		transducers.add(gmx);
		
		Transducer gmy = new Transducer();
		gmy.setName("Geomagnetism Y");
		gmy.setId("Geomagnetism Y");
		gmy.setUnits("µT");
		transducers.add(gmy);
		
		Transducer ill = new Transducer();
		ill.setName("Illuminance");
		ill.setId("Illuminance");
		ill.setUnits("Lx");
		transducers.add(ill);

		Transducer alt = new Transducer();
		alt.setName("Altitude");
		alt.setId("Altitude");
		alt.setUnits("m");
		transducers.add(alt);
		
		
		
		
		
		device.setTransducers(transducers);
		
		
		//create node
		con.createNode(nodeName, device, AccessModel.open,PublishModel.open);
		
		System.out.println("node created!");

	
		
	}
	
}
