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
import javax.ws.rs.core.Response;

import conn.Connections;
import data.Atrib;

@Path("/robot")
public class RobotServices {

    @POST
    @Path("/settings")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setRobotSettings(Atrib attributes) {
        // Data validation
        if (!isValid(attributes)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid input data").build();
        }

        try (Connection conn = Connections.getConnection()) {
            // Database operation to save settings
            String query = "INSERT INTO robot_settings (speed, timesLooped, obstacleDetectionDistance) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, attributes.getSpeed());
                pstmt.setInt(2, attributes.getTimesLooped());
                pstmt.setFloat(3, attributes.getObstacleDetectionDistance());
                pstmt.executeUpdate();
            }
            // Handle successful operation
            return Response.status(Response.Status.OK).entity("Settings saved successfully").build();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or SQL errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error saving settings").build();
        }
    }

    @GET
    @Path("/settings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRobotSettings() {
        Atrib attributes = null;
        try (Connection conn = Connections.getConnection()) {
            // Database operation to retrieve settings
            String query = "SELECT * FROM robot_settings ORDER BY id DESC LIMIT 1";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        attributes = new Atrib();
                        attributes.setSpeed(rs.getInt("speed"));
                        attributes.setTimesLooped(rs.getInt("timesLooped"));
                        attributes.setObstacleDetectionDistance(rs.getFloat("obstacleDetectionDistance"));
                    }
                }
            }
            // Handle successful operation and return settings
            return Response.status(Response.Status.OK).entity(attributes).build();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or SQL errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving settings").build();
        }
    }

    @POST
    @Path("/statistics")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRobotStatistics(Atrib attributes) {
        // Data validation
        if (!isValidStatistics(attributes)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid input data").build();
        }

        try (Connection conn = Connections.getConnection()) {
            // Database operation to save statistics
            String query = "INSERT INTO robot_statistics (completionTime, distanceTraveled) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setLong(1, attributes.getCompletionTime());
                pstmt.setFloat(2, attributes.getDistanceTraveled());
                pstmt.executeUpdate();
            }
            // Handle successful operation
            return Response.status(Response.Status.OK).entity("Statistics saved successfully").build();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or SQL errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error saving statistics").build();
        }
    }

    @GET
    @Path("/currentSettings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentSettings() {
        return getRobotSettings(); // Reuse the existing method to fetch settings
    }

    // Method to validate input data for settings
    private boolean isValid(Atrib attributes) {
        // Implement validation logic here
        return true; // Placeholder for now
    }

    // Method to validate input data for statistics
    private boolean isValidStatistics(Atrib attributes) {
        // Implement validation logic here
        return true; // Placeholder for now
    }
}
