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


public class Delete {

	public static void main(String[] args){
		try {
			new Delete();
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
	
	public Delete() throws SmackException, IOException, XMPPException{
		

		SoxConnection con = new SoxConnection("soxfujisawa.ht.sfc.keio.ac.jp","fujisawa","!htmiro1", true);

		
		con.deleteNode("ps_animal");
		con.deleteNode("ps_damage");
		con.deleteNode("ps_garbageStation");
		con.deleteNode("ps_graffiti");
		con.deleteNode("ps_illegalGarbage");
		con.deleteNode("ps_kyun");
		con.deleteNode("ps_others");
		con.deleteNode("ps_streetlight");
		con.deleteNode("ps_zansa");
		con.deleteNode("ps_disaster");
		con.deleteNode("ps_forgetGarbage");
		
	}
	
}
