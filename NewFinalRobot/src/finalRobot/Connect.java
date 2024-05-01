package finalRobot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import lejos.hardware.Button;

public class Connect implements Runnable {
    
	DataExchange DE;
    
    public Connect(DataExchange DE) { 
    	this.DE = DE;
    }
    
    public void run() {
        
        String s = null;

        while (!Button.ESCAPE.isDown()) {
            try {
            	URL url = new URL(""); //database url here

                HttpURLConnection conn = null;
                InputStream is = null;
                InputStreamReader isr = null;
                BufferedReader br = null;

                try {
                    conn = (HttpURLConnection) url.openConnection();
                    is = conn.getInputStream();
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);

                    s = br.readLine();
                    String[] values = s.split(" ");
                    String value1S = values[0];
                    String value2S = values[1]; //Read values
                    String value3S = values[2];
                    
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error.");
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (isr != null) {
                        try {
                            isr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error.");
            }
            try {
            	Thread.sleep(2500);
	        } catch (InterruptedException e1) {
	            e1.printStackTrace();
	        }
        }
    }
}