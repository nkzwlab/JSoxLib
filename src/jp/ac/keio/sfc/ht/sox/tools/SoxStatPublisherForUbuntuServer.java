package jp.ac.keio.sfc.ht.sox.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;

public class SoxStatPublisherForUbuntuServer {

	static String file_path;
	static String server;

	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.out.println("usage: %SoxStatForUbuntuServer path_for_log_file sox_server_name");
			System.exit(0);
		}

		file_path = args[0];
		server = args[1];
		
		new SoxStatPublisherForUbuntuServer();

	}

	public SoxStatPublisherForUbuntuServer() throws Exception {

		while (true) {
			try {

				/*
				 * For CPU status
				 */
				Runtime runtime = Runtime.getRuntime();
				Process p = runtime.exec("uptime");
				InputStream is = p.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				String[] strs = br.readLine().split("load average:"); 
															// 0.05
				for(int i=0;i<strs.length;i++){
					System.out.println(i+": "+strs[i]);
				}
				System.out.println("------");
				String[] strs2 = strs[1].split(","); // 0.00, 0.01, 0.05 for
														// Ubuntu
				// String[] strs2 = strs[2].split(","); //0.00, 0.01, 0.05 for
				// OSX

				String cpu_1min = strs2[0].trim();
				String cpu_5min = strs2[1].trim();
				String cpu_15min = strs2[2].trim();

				/*
				 * For stats log
				 */
				p = runtime.exec("cat " + file_path);
				is = p.getInputStream();
				br = new BufferedReader(new InputStreamReader(is));
				String str, last_str = "";
				while ((str = br.readLine()) != null) {
					last_str = str;
				}
				System.out.println(last_str);

				String[] strs_stats = last_str.split(",");
				String dbmin = strs_stats[1];
				String dbmax = strs_stats[2];
				String dbcurrent = strs_stats[3];
				String dbused = strs_stats[4];
				String core_threads = strs_stats[5];
				String active_threads = strs_stats[6];
				String queue_tasks = strs_stats[7];
				String completed_tasks = strs_stats[8];
				String sessions = strs_stats[9];
				String nio_read = strs_stats[10];
				String nio_written = strs_stats[11];
				String queued_nio_events = strs_stats[12];
				String queued_nio_writes = strs_stats[13];

				/*
				 * Publish data
				 */
				SoxConnection con = new SoxConnection(server, false);
				SoxDevice dev = new SoxDevice(con, "soxStat");

				List<TransducerValue> valueList = new ArrayList<TransducerValue>();

				TransducerValue v_cpu_1min = new TransducerValue();
				v_cpu_1min.setId("cpu_1min");
				v_cpu_1min.setRawValue(cpu_1min);
				v_cpu_1min.setCurrentTimestamp();
				valueList.add(v_cpu_1min);

				TransducerValue v_cpu_5min = new TransducerValue();
				v_cpu_5min.setId("cpu_5min");
				v_cpu_5min.setRawValue(cpu_5min);
				v_cpu_5min.setCurrentTimestamp();
				valueList.add(v_cpu_5min);

				TransducerValue v_cpu_15min = new TransducerValue();
				v_cpu_15min.setId("cpu_15min");
				v_cpu_15min.setRawValue(cpu_15min);
				v_cpu_15min.setCurrentTimestamp();
				valueList.add(v_cpu_15min);

				TransducerValue v_dbmin = new TransducerValue();
				v_dbmin.setId("dbmin");
				v_dbmin.setRawValue(dbmin);
				v_dbmin.setCurrentTimestamp();
				valueList.add(v_dbmin);

				TransducerValue v_dbmax = new TransducerValue();
				v_dbmax.setId("dbmax");
				v_dbmax.setRawValue(dbmax);
				v_dbmax.setCurrentTimestamp();
				valueList.add(v_dbmax);

				TransducerValue v_dbcurrent = new TransducerValue();
				v_dbcurrent.setId("dbcurrent");
				v_dbcurrent.setRawValue(dbcurrent);
				v_dbcurrent.setCurrentTimestamp();
				valueList.add(v_dbcurrent);

				TransducerValue v_dbused = new TransducerValue();
				v_dbused.setId("dbused");
				v_dbused.setRawValue(dbused);
				v_dbused.setCurrentTimestamp();
				valueList.add(v_dbused);

				TransducerValue v_core_threads = new TransducerValue();
				v_core_threads.setId("core_threads");
				v_core_threads.setRawValue(core_threads);
				v_core_threads.setCurrentTimestamp();
				valueList.add(v_core_threads);

				TransducerValue v_active_threads = new TransducerValue();
				v_active_threads.setId("active_threads");
				v_active_threads.setRawValue(active_threads);
				v_active_threads.setCurrentTimestamp();
				valueList.add(v_active_threads);

				TransducerValue v_queue_tasks = new TransducerValue();
				v_queue_tasks.setId("queue_tasks");
				v_queue_tasks.setRawValue(queue_tasks);
				v_queue_tasks.setCurrentTimestamp();
				valueList.add(v_queue_tasks);

				TransducerValue v_completed_tasks = new TransducerValue();
				v_completed_tasks.setId("completed_tasks");
				v_completed_tasks.setRawValue(completed_tasks);
				v_completed_tasks.setCurrentTimestamp();
				valueList.add(v_completed_tasks);

				TransducerValue v_sessions = new TransducerValue();
				v_sessions.setId("sessions");
				v_sessions.setRawValue(sessions);
				v_sessions.setCurrentTimestamp();
				valueList.add(v_sessions);

				TransducerValue v_nio_read = new TransducerValue();
				v_nio_read.setId("nio_read");
				v_nio_read.setRawValue(nio_read);
				v_nio_read.setCurrentTimestamp();
				valueList.add(v_nio_read);

				TransducerValue v_nio_written = new TransducerValue();
				v_nio_written.setId("nio_written");
				v_nio_written.setRawValue(nio_written);
				v_nio_written.setCurrentTimestamp();
				valueList.add(v_nio_written);

				TransducerValue v_queued_nio_events = new TransducerValue();
				v_queued_nio_events.setId("queued_nio_events");
				v_queued_nio_events.setRawValue(queued_nio_events);
				v_queued_nio_events.setCurrentTimestamp();
				valueList.add(v_queued_nio_events);

				TransducerValue v_queued_nio_writes = new TransducerValue();
				v_queued_nio_writes.setId("queued_nio_writes");
				v_queued_nio_writes.setRawValue(queued_nio_writes);
				v_queued_nio_writes.setCurrentTimestamp();
				valueList.add(v_queued_nio_writes);

				dev.publishValues(valueList);

				con.disconnect();

				try{
				Thread.sleep(10000);
				}catch(Exception e){
					e.printStackTrace();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
