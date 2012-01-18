* TCP - start the server first, ServerSocket.accept() blocks
* UDP - start the client first, DatagramSocket.receive() blocks 

The Simplest Android TCP File Transfer
==================================

The meat of the server/client code comes from [Real's How To site][ref_site].

You Need To Do:
----------

1. Let the 2 phones connect to the same network.

    I turned on my ad-hoc [barnacle][my_barnacle] with the same name on both phones

2. Figure out the IP address of the server. Put it under the `TODO server's IP address` in src/.../aaFileSTransfer.java

    You can go into `./adb shell` and `netcfg` to see the IP
    If you need to change the IP, in the shell do: `ifconfig eth0 192.the.new.ip`

3. Put in the file source location in the server under `TODO: put the source of the file`

4. Put in the destination path in the client under `TODO: Put where you want to save the file`

5. Install this "aaFile_Transfer" app on both phones

6. On the Server phone, press "Start Server to Wait for Transmit". The button sometimes turn orange and stay that way because it's waiting for connections. Even if the button doesn't turn orange (I know, it's terrible UI), it's probably also working, so you can go on ...

7. On the Client phone, press "Start Client Receive". 

8. On the Client phone, view if the file transfer was successful, navigate to your specified destination path using:
    * `./adb shell` 
    * the app: File Manager
    * The third option is to view the contents of /mnt/sdcard as a USB storage. To do so:
        * swipe down on the home view
        * select `USB connected`
        * click `Turn off USB storage`
        * Now you can view /mnt/sdcard/* as a connected USB drive

Nota Bene
---------
 
* If you downloaded to '/mnt/sdcard/download', your download might not show up in 'Downloads'
   
* You might not have '/mnt/sdcard/download' directory if you have never downloaded anything on your iPhone

* To view You might have to try disable/enable the "USB Connected" option on the phone to be able to view in


What I need to do
-----------
* Better UI for the server button to let users know when the server is waiting for connections
* Make a button to let server stop waiting to transfer
* Let the user know the status of the transfer (success/fail)

[ref_site]: http://www.rgagnon.com/javadetails/java-0542.html
[my_barnacle]: https://github.com/haoqili/barnacle

UDP
======
Just a simple UDP transmission with the meat of the code coming from [helloandroid][h_a].

You Need To Do:
----------

1. Let the 2 phones connect to the same network.

    I turned on my ad-hoc [barnacle][my_barnacle] with the same name on both phones

2. Fill out the `TODO: fill in UDP Client IP`
3. Click on the "Start UDP Client" button
4. Click on "Start UDP Server" button
5. Observe in logcat: `D/Udp tutorial( 1167): message:Hello Android!`

[h_a]: http://www.helloandroid.com/tutorials/simple-udp-communication-example
