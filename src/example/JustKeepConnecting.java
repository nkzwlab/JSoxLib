package example;

import java.io.IOException;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import jp.ac.keio.sfc.ht.sox.protocol.Data;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;

public class JustKeepConnecting {

	public static void main(String[] args){
	
		try {
			new JustKeepConnecting();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public JustKeepConnecting() throws Exception{
		try {
			SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", true);
			
			SoxDevice d = new SoxDevice(con, "hogeTest");
			
			while(true){
				Presence presence = new Presence(Presence.Type.available);
				presence.setStatus("keep connecting");
				con.getXMPPConnection().sendStanza(presence);

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		} //anonymous login
		
		
	}
	
}
