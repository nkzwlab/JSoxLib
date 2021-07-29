package example;

import java.io.IOException;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;

public class DiscoverAllSensors {

	public static void main(String[] args){
		new DiscoverAllSensors();
	}
	
	public DiscoverAllSensors(){
		
		try {
			SoxConnection con=null;
			con = new SoxConnection("sox.ucl.nuee.nagoya-u.ac.jp",true);
			
			List<String> nodeList = con.getAllSensorList(); //get sensor node list from loginned server
			//List<String> nodeList = con.getAllSensorList("takusox.ht.sfc.keio.ac.jp"); //get sensor node list from specific server (for SOX federation)
			
			for(String node:nodeList){
				System.out.println(node);
			}
			System.out.println(nodeList.size());


		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
