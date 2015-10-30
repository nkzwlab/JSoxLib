package jp.ac.keio.sfc.ht.sox.tools;

import java.io.IOException;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Subscription;

import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;

public class UnsubscribeAllDevice {

	public static void main(String[] args) throws SmackException, IOException, XMPPException {
		new UnsubscribeAllDevice(args[0],args[1]);
	}

	public UnsubscribeAllDevice(String jid,String password) throws SmackException, IOException, XMPPException {
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", jid,
				password, true);

		// test
		try {
			List<Subscription> subs = con.getPubSubManager().getSubscriptions();
		
			for (Subscription sub : subs) {
				System.out.println(sub.getNode().toString()+":"+sub.getJid()+" "+sub.getId());
				LeafNode test = con.getPubSubManager().getNode(sub.getNode());
				test.unsubscribe(sub.getJid(), sub.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
