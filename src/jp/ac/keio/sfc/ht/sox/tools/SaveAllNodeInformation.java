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

import jp.ac.keio.sfc.ht.sox.protocol.Data;
import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
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

		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp", false);

		List<String> list = con.getAllSensorList();
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);

			if (!s.contains("/")) {
				File file_meta = new File(dir + "/" + s + "_meta.txt");
				File file_data = new File(dir + "/" + s + "_data.txt");
				if (!file_meta.exists()) {
					FileWriter filewriter_meta = new FileWriter(file_meta);
					FileWriter filewriter_data = new FileWriter(file_data);

					System.out.println("getting meta data from " + s + " ..." + i + "/" + list.size());

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

						filewriter_meta.write(writer.toString());

					}

					Data data = dev.getLastPublishData();
					if (data != null) {

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
							serializer.write(data, writer);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println(writer.toString()); // device XML
																// description
						filewriter_data.write(writer.toString());
					}

					filewriter_meta.close();
					filewriter_data.close();
				}

			}
		}

		System.exit(0);
	}

}
