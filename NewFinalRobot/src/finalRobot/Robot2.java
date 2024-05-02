package finalRobot;

import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Stopwatch;

/**
 * This program controls an EV3 robot to follow a black line and avoid obstacles.
 * 
 * Date: April 2024
 * Team 16 - Error 404
 * 
 * Components:
 * - DataExchange: Manages data exchange between different threads.
 * - LineFollower: Controls the robot using two unregulated motors.
 * - ObstacleDetector: Detects obstacles using an ultrasonic sensor.
 * - MyColorSensot: Detects the black line using a red light.
 * 
 */
public class Robot2 {

	private static Connect CObj;
    private static DataExchange DE;
    private static LineFollower LFObj;
    private static ObstacleDetector OBObj;
    private static MyColorSensor colorSensorObj; // New instance of MyColorSensor
    public static Stopwatch stopwatch; // Using Stopwatch for timing

    public static void main(String[] args) {
        DE = new DataExchange();
        
        UnregulatedMotor motorA = new UnregulatedMotor(MotorPort.A);
        UnregulatedMotor motorB = new UnregulatedMotor(MotorPort.B);

        CObj = new Connect(DE);
        OBObj = new ObstacleDetector(DE);
        LFObj = new LineFollower(DE, motorA, motorB);
        colorSensorObj = new MyColorSensor(DE); // Initialize MyColorSensor

        Thread connThread = new Thread(CObj);
        Thread obstacleThread = new Thread(OBObj);
        Thread lineFollowerThread = new Thread(LFObj);
        Thread colorSensorThread = new Thread(colorSensorObj); // Create a thread for MyColorSensor

        // Start all threads
        lineFollowerThread.start();
        obstacleThread.start();
        colorSensorThread.start();
        connThread.start();

        try {
            // Join all threads
        	connThread.join();
            lineFollowerThread.join();
            obstacleThread.join();
            colorSensorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            motorA.close();
            motorB.close();
        }
    }
    public static void sendRobotStatistics(Atrib attributes) {
        try {
            URL url = new URL("http://localhost:8080/rest/robot/statistics");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(attributes);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle communication errors
        }
    }
}