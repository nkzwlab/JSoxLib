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

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;

public class RecreateAllNodeFromBackupFiles {

	public static void main(String[] args) {

		try {
			new RecreateAllNodeFromBackupFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RecreateAllNodeFromBackupFiles() throws Exception {
		recreateNonSensorizeNode();

	}

	public void recreateSensorizeNode() throws Exception {
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp",
				"sensorizer", "miromiro", true);

		String path = "backup";
		File dir = new File(path);
		File[] files = dir.listFiles();

		String path2 = "backup/sensorized";
		File dir2 = new File(path2);
		File[] files2 = dir2.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			// System.out.println((i + 1) + ":    " + file);

			boolean ownBySensorizer = false;
			for (int j = 0; j < files2.length; j++) {
				if (file.getName().replace(".txt", "")
						.equals(files2[j].getName().replace(".json", ""))) {
					ownBySensorizer = true;
				}

				if (file.getName().startsWith("三井")
						|| file.getName().startsWith("タイムズ")) {
					ownBySensorizer = true;
				}

			}

			if (!file.getName().equals("sensorized") && ownBySensorizer) {

				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);

				String xml = "";
				String line;
				while ((line = br.readLine()) != null) {
					xml = xml + line;

				}


				if (!xml.equals("")) {
					// Create Device object from XML
					Serializer serializer = new Persister(new Matcher() {
						public Transform match(Class type) throws Exception {
							if (type.isEnum()) {
								return new SoxEnumTransform(type);
							}
							return null;
						}
					});
					System.out.println(file.getName() + " to device obj.."
							+ xml);
					Device device = serializer.read(Device.class, xml);

					// Create XMPP node
					con.createNode(file.getName().replace(".txt", ""), device, AccessModel.open,
							PublishModel.open);
					System.out.println(device.getName() + " is created ..." + i
							+ "/" + files.length);

				} else {
					con.createNode(file.getName().replace(".txt", ""),
							AccessModel.open, PublishModel.open);
					System.out.println(file.getName().replace(".txt", "") + " is created ..." + i
							+ "/" + files.length);

				}

			}

		}
		con.disconnect();
		System.out.println("done!!");
	}

	public void recreateNonSensorizeNode() throws Exception {

		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", "guest",
				"miroguest", true);

		String path = "backup";
		File dir = new File(path);
		File[] files = dir.listFiles();

		String path2 = "backup/sensorized";
		File dir2 = new File(path2);
		File[] files2 = dir2.listFiles();

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
			// System.out.println((i + 1) + ":    " + file);

			boolean ownBySensorizer = false;
			for (int j = 0; j < files2.length; j++) {
				if (file.getName().replace(".txt", "")
						.equals(files2[j].getName().replace(".json", ""))) {
					ownBySensorizer = true;
					break;
				}

				if (file.getName().startsWith("三井")
						|| file.getName().startsWith("タイムズ")) {
					ownBySensorizer = true;
					break;
				}

			}

			if (!file.getName().equals("sensorized")) {

				System.out.println(file.getName());
				if (!ownBySensorizer) {

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
						con.createNode(file.getName().replace(".txt", ""),
								device, AccessModel.open, PublishModel.open);
						System.out.println(file.getName().replace(".txt", "") + " is created ..."
								+ i + "/" + files.length);
				
					} else {

						con.createNode(file.getName().replace(".txt", ""),
								AccessModel.open, PublishModel.open);
						System.out.println(file.getName().replace(".txt", "") + " is created ..."
								+ i + "/" + files.length);
					}

				}
			}

		}
		con.disconnect();
		System.out.println("done!!");
	}
}



