package jp.ac.keio.sfc.ht.sox.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

import jp.ac.keio.sfc.ht.sox.protocol.Data;
import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxEnumTransform;

public class RecreateAllNodeWithDataFromBackupFiles {

	public static void main(String[] args) {

		try {
			new RecreateAllNodeWithDataFromBackupFiles(args[0], args[1], args[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RecreateAllNodeWithDataFromBackupFiles(String back_dir, String jid, String pass) throws Exception {

		SoxConnection con = new SoxConnection("sox-dev.ht.sfc.keio.ac.jp", jid, pass, false);

		String path = back_dir;
		File dir = new File(path);
		File[] files = dir.listFiles();

		Serializer serializer = new Persister(new Matcher() {
			public Transform match(Class type) throws Exception {
				if (type.isEnum()) {
					return new SoxEnumTransform(type);
				}
				return null;
			}
		});

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			System.out.println(file.getName());

			if (file.getName().contains("_meta.txt")) {

				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);

				String xml = "";
				String line;
				while ((line = br.readLine()) != null) {
					xml = xml + line;

				}

				if (!xml.equals("")) {
					System.out.println(xml);

					// Create Device object from XML

					Device device = serializer.read(Device.class, xml);

					// Create XMPP node
					con.createNode(file.getName().replace("_meta.txt", ""), device, AccessModel.open,
							PublishModel.open);
					System.out.println(
							file.getName().replace("_meta.txt", "") + " is created ..." + i + "/" + files.length);

					// data publish
					FileReader fr2 = new FileReader(path+"/"+file.getName().replace("_meta.txt", "_data.txt"));
					BufferedReader br2 = new BufferedReader(fr2);
					xml = "";
					line = "";
					while ((line = br2.readLine()) != null) {
						xml = xml + line;
					}

					if (!xml.equals("")) {
						Data data = serializer.read(Data.class, xml);
						SoxDevice soxdev = new SoxDevice(con, file.getName().replace("_meta.txt", ""));
						soxdev.publishValues(data.getTransducerValue());

					}

				}
			}

		}
		con.disconnect();
		System.out.println("done!!");

	}

}
