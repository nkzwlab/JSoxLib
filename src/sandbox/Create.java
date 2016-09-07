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
		SoxConnection con = new SoxConnection("nictsox-lv2.ht.sfc.keio.ac.jp","guest","miroguest",true);
		String nodeName = "soxStat";

		/**
		 * Create sensor
		 */		
		
		//set device info and it's transducer meta data
		Device device = new Device();
		device.setId(nodeName);
		device.setDeviceType(DeviceType.RESOURCE_CONSUMPTION);
		device.setName(nodeName);

		List<Transducer> transducers = new ArrayList<Transducer>();

		Transducer cpu_1min = new Transducer();
		cpu_1min.setName("cpu_1min");
		cpu_1min.setId("cpu_1min");
		cpu_1min.setUnits("%");
		transducers.add(cpu_1min);
		
		
		Transducer cpu_5min = new Transducer();
		cpu_5min.setName("cpu_5min");
		cpu_5min.setId("cpu_5min");
		cpu_5min.setUnits("%");
		transducers.add(cpu_5min);
		

		Transducer cpu_15min = new Transducer();
		cpu_15min.setName("cpu_15min");
		cpu_15min.setId("cpu_15min");
		cpu_15min.setUnits("%");
		transducers.add(cpu_15min);
		
		
		//From here for stats log
		Transducer dbmin = new Transducer();
		dbmin.setName("dbmin");
		dbmin.setId("dbmin");
		dbmin.setUnits("int");
		transducers.add(dbmin);

		Transducer dbmax = new Transducer();
		dbmax.setName("dbmax");
		dbmax.setId("dbmax");
		dbmax.setUnits("int");
		transducers.add(dbmax);

		Transducer dbcurrent = new Transducer();
		dbcurrent.setName("dbcurrent");
		dbcurrent.setId("dbcurrent");
		dbcurrent.setUnits("int");
		transducers.add(dbcurrent);

		Transducer dbused = new Transducer();
		dbused.setName("dbused");
		dbused.setId("dbused");
		dbused.setUnits("int");
		transducers.add(dbused);

		Transducer core_threads = new Transducer();
		core_threads.setName("core_threads");
		core_threads.setId("core_threads");
		core_threads.setUnits("int");
		transducers.add(core_threads);

		Transducer active_threads = new Transducer();
		active_threads.setName("active_threads");
		active_threads.setId("active_threads");
		active_threads.setUnits("int");
		transducers.add(active_threads);

		Transducer queue_tasks = new Transducer();
		queue_tasks.setName("queue_tasks");
		queue_tasks.setId("queue_tasks");
		queue_tasks.setUnits("int");
		transducers.add(queue_tasks);
		
		Transducer  complete_tasks = new Transducer();
		complete_tasks.setName("complete_tasks");
		complete_tasks.setId("complete_tasks");
		complete_tasks.setUnits("int");
		transducers.add(complete_tasks);
		
		Transducer  sessions = new Transducer();
		sessions.setName("sessions");
		sessions.setId("sessions");
		sessions.setUnits("int");
		transducers.add(sessions);
		
		Transducer  nio_read = new Transducer();
		nio_read.setName("nio_read");
		nio_read.setId("nio_read");
		nio_read.setUnits("int");
		transducers.add(nio_read);
		
		
		Transducer  nio_written = new Transducer();
		nio_written.setName("nio_written");
		nio_written.setId("nio_written");
		nio_written.setUnits("int");
		transducers.add(nio_written);
		
		
		Transducer  queued_nio_events = new Transducer();
		queued_nio_events.setName("queued_nio_events");
		queued_nio_events.setId("queued_nio_events");
		queued_nio_events.setUnits("int");
		transducers.add(queued_nio_events);
		
		Transducer  queued_nio_writes = new Transducer();
		queued_nio_writes.setName("queued_nio_writes");
		queued_nio_writes.setId("queued_nio_writes");
		queued_nio_writes.setUnits("int");
		transducers.add(queued_nio_writes);
		
		device.setTransducers(transducers);
		
		
		//create node
		con.createNode(nodeName, device, AccessModel.open,PublishModel.open);
		
		System.out.println("node created!");

	
		
	}
	
}
