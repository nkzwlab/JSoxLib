package example;

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

public class GetAllSensorNodeList {

	public static void main(String[] args){
		new GetAllSensorNodeList();
	}
	
	public GetAllSensorNodeList(){
		
		try {
			SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp",  true);
			List<String> nodeList = con.getAllSensorList(); //get sensor node list from loginned server
			//List<String> nodeList = con.getAllSensorList("takusox.ht.sfc.keio.ac.jp"); //get sensor node list from specific server
						
			for(String node:nodeList){
				System.out.println(node);
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

	
}
