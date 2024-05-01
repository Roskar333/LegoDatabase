package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import conn.Connections;
import data.Atrib;

@Path("/robot")
public class RobotServices {

    @POST
    @Path("/settings")
    @Consumes(MediaType.APPLICATION_JSON)
    public void setRobotSettings(Atrib attributes) {
        try (Connection conn = Connections.getConnection()) {
            String query = "INSERT INTO robot_settings (speed, timesLooped, obstacleDetectionDistance) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, attributes.getSpeed());
            pstmt.setInt(2, attributes.getTimesLooped());
            pstmt.setFloat(3, attributes.getObstacleDetectionDistance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or SQL errors
        }
    }

    @GET
    @Path("/settings")
    @Produces(MediaType.APPLICATION_JSON)
    public Atrib getRobotSettings() {
        Atrib attributes = null;
        try (Connection conn = Connections.getConnection()) {
            String query = "SELECT * FROM robot_settings ORDER BY id DESC LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                attributes = new Atrib();
                attributes.setSpeed(rs.getInt("speed"));
                attributes.setTimesLooped(rs.getInt("timesLooped"));
                attributes.setObstacleDetectionDistance(rs.getFloat("obstacleDetectionDistance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or SQL errors
        }
        return attributes;
    }

    @POST
    @Path("/statistics")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveRobotStatistics(Atrib attributes) {
        try (Connection conn = Connections.getConnection()) {
            String query = "INSERT INTO robot_statistics (completionTime, distanceTraveled) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, attributes.getCompletionTime());
            pstmt.setFloat(2, attributes.getDistanceTraveled());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or SQL errors
        }
    }

    @GET
    @Path("/currentSettings")
    @Produces(MediaType.APPLICATION_JSON)
    public Atrib getCurrentSettings() {
        return getRobotSettings(); // Reuse the existing method to fetch settings
    }
}

