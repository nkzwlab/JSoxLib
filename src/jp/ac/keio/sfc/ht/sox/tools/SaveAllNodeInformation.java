/*
 * This is a tool for saving all sensor node meta information.
 * Usage:java SaveAllNodeInformation [directory name]
 * Then, it saves all meta information into specified directory.
 */

package jp.ac.keio.sfc.ht.sox.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxEnumTransform;

public class SaveAllNodeInformation {

	public static void main(String[] args) {
		try {
			new SaveAllNodeInformation(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SaveAllNodeInformation(String dir) throws Exception {

		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", true);

		List<String> list = con.getAllSensorList();
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);

			File file = new File(dir + "/" + s + ".txt");
			if (!file.exists()) {
				FileWriter filewriter = new FileWriter(file);

				System.out.println("getting meta data from " + s + " ..." + i
						+ "/" + list.size());

				SoxDevice dev = new SoxDevice(con, s);
				Device device = dev.getDevice();

				// transform device object into XML string
				if (device != null) {
					StringWriter writer = new StringWriter();
					Persister serializer = new Persister(new Matcher() {
						public Transform match(Class type) throws Exception {
							if (type.isEnum()) {
								return new SoxEnumTransform(type);
							}
							return null;
						}
					});

					try {
						serializer.write(device, writer);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println(writer.toString()); // device XML
															// description

					// save the information as file

					filewriter.write(writer.toString());

				}

				filewriter.close();
			}
		}

		System.exit(0);
	}

}