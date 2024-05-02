package data;

public class Atrib {
    private int speed;
    private int timesLooped;
    private float obstacleDetectionDistance;
    private int id;
    private long completionTime;
    private float distanceTraveled;
    
    public long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public float getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(float distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getTimesLooped() {
        return timesLooped;
    }

    public void setTimesLooped(int timesLooped) {
        this.timesLooped = timesLooped;
    }

    public float getObstacleDetectionDistance() {
        return obstacleDetectionDistance;
    }

    public void setObstacleDetectionDistance(float obstacleDetectionDistance) {
        this.obstacleDetectionDistance = obstacleDetectionDistance;
    }
    
    public Atrib() {}

    public Atrib(int speed, int timesLooped, float obstacleDetectionDistance) {
        this.speed = speed;
        this.timesLooped = timesLooped;
        this.obstacleDetectionDistance = obstacleDetectionDistance;
    }
	public Atrib(int id, int speed, int timesLooped, int obstacleDetectionDistance) {
		this.id = id;
		this.speed = speed; 
	    this.timesLooped = timesLooped;
        this.obstacleDetectionDistance = obstacleDetectionDistance;
    }
}
