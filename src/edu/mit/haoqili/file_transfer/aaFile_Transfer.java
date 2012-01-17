package edu.mit.haoqili.file_transfer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class aaFile_Transfer extends Activity
{
	private Button serverTransmitButton;
	private Button clientReceiveButton;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		serverTransmitButton = (Button) findViewById(R.id.button_server);
		serverTransmitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.i("Start Server Button Clicked", "yipee");

				try {
					// from: http://www.rgagnon.com/javadetails/java-0542.html
					// create socket
					// TODO: the port should match the one in Client
					ServerSocket servsock = new ServerSocket(5005);
					while (true) {
						Log.i("************", "Waiting...");

						Socket sock = servsock.accept();
						Log.i("************", "Accepted connection : " + sock);

						// sendfile

						// TODO: put the source of the file
						File myFile = new File ("/mnt/sdcard/download/3.pdf");
						byte [] mybytearray  = new byte [(int)myFile.length()];
						Log.i("####### file length = ", String.valueOf(myFile.length()) );
						FileInputStream fis = new FileInputStream(myFile);
						BufferedInputStream bis = new BufferedInputStream(fis);
						bis.read(mybytearray,0,mybytearray.length);
						OutputStream os = sock.getOutputStream();
						Log.i("************", "Sending...");
						os.write(mybytearray,0,mybytearray.length);
						os.flush();
						sock.close();
					}   
				} catch (IOException e) {
					Log.i("Io execption ", "e: " + e);
				}

				Log.i("=============== the end of start ==============", "==");
			}	
		});

		clientReceiveButton = (Button) findViewById(R.id.button_client);
		clientReceiveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.i("Read Button Clicked", "yipee");

				try {
					int filesize=900000; // filesize temporary hardcoded

					long start = System.currentTimeMillis();
					int bytesRead;
					int current = 0;
					// localhost for testing
					// TODO: server's IP address. Socket should match one above in server
					Socket sock = new Socket("192.168.5.20",5005);
				
					Log.i("************", "Connecting...");

					// receive file
					byte [] mybytearray  = new byte [filesize];
					InputStream is = sock.getInputStream();
					// TODO: Put where you want to save the file
					/* N.B.:
					 * * To view if the file transfer was successful:
					 *       * use `./adb shell` 
					 *       * use the app: File Manager
					 * 
					 * * If you downloaded to '/mnt/sdcard/download', 
					 *   your download might not show up in 'Downloads'
					 *   
					 * * You might not have '/mnt/sdcard/download' directory
					 *   if you have never downloaded anything on your iPhone
					 */
					FileOutputStream fos = new FileOutputStream("/mnt/sdcard/download/source-copy2.pdf");
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					bytesRead = is.read(mybytearray,0,mybytearray.length);
					current = bytesRead;
					do {
						bytesRead =
								is.read(mybytearray, current, (mybytearray.length-current));
						if(bytesRead >= 0) current += bytesRead;
					} while(bytesRead > -1);

					bos.write(mybytearray, 0 , current);
					bos.flush();
					long end = System.currentTimeMillis();
					Log.i("************ end-start = ", String.valueOf(end-start));
					bos.close();
					sock.close();
				} catch ( UnknownHostException e ) {
					Log.i("******* :( ", "UnknownHostException");
				} catch (IOException e){
					Log.i("Read has IOException", "e: " + e);
				}

				Log.i("=============== the end of read ===============", "==");

			}
		});
	}
}
