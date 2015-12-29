package jp.ac.keio.sfc.ht.sox.tools;

import java.io.IOException;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;

public class DeleteAllNode {

	public static void main(String[] args){
		new DeleteAllNode();
	}
	
	public DeleteAllNode(){
		
		try {
			SoxConnection con = new SoxConnection("sox-dev.ht.sfc.keio.ac.jp","guest","miroguest",true);
			List<String> nodeList = con.getAllSensorList(); //get sensor node list from loginned server
			//List<String> nodeList = con.getAllSensorList("takusox.ht.sfc.keio.ac.jp"); //get sensor node list from specific server (for SOX federation)
						
			for(String node:nodeList){
				con.deleteNode(node);
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
