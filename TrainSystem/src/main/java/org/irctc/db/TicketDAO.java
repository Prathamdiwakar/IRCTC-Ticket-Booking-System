package org.irctc.db;

import org.irctc.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
        public boolean bookTicket(Ticket ticket) {
            String qr = "INSERT INTO tickets (user_email, train_id, seats_booked) VALUES(?,?,?)";
            String updateQuery = "UPDATE trains SET available_seats =available_seats-? WHERE train_id=? AND available_seats >= ?";
            try (Connection connection = DatabaseConnection.getconnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setInt(1, ticket.getSeatBooked());
                preparedStatement.setInt(2, ticket.getTrainId());
                preparedStatement.setInt(3, ticket.getSeatBooked());

                int update = preparedStatement.executeUpdate();

                if (update > 0) {
                    PreparedStatement ps1 = connection.prepareStatement(qr);
                    ps1.setString(1, ticket.getUserEmail());
                    ps1.setInt(2, ticket.getTrainId());
                    ps1.setInt(3, ticket.getSeatBooked());
                    ps1.executeUpdate();
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean cancelTicket(int ticketId) {
            String getTicketQr = "SELECT train_id, seats_booked FROM tickets WHERE ticket_id =?";
            String deleteTicket = "DELETE FROM tickets WHERE ticket_id =?";
            String updateQr = "UPDATE trains SET available_seats =available_seats+? WHERE train_id=?";
            try (Connection connection = DatabaseConnection.getconnection()) {
                // get ticket details
                PreparedStatement preparedStatement = connection.prepareStatement(getTicketQr);
                preparedStatement.setInt(1, ticketId);
                var rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    int trainID = rs.getInt("train_id");
                    int bookedSeats = rs.getInt("seats_booked");

                    // 2. Delete ticket

                    PreparedStatement preparedStatement1 = connection.prepareStatement(deleteTicket);
                    preparedStatement1.setInt(1, ticketId);
                    preparedStatement1.executeUpdate();

                    // 3. Update train seats
                    PreparedStatement preparedStatement2 = connection.prepareStatement(updateQr);
                    preparedStatement2.setInt(1, bookedSeats);
                    preparedStatement2.setInt(2, trainID);
                    preparedStatement2.executeUpdate();

                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
    public List<Ticket> getTicketsByEmail(String email) {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM tickets WHERE user_email=?";
        try (Connection con = DatabaseConnection.getconnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(rs.getString("user_email"),
                        rs.getInt("train_id"),
                        rs.getInt("seats_booked"));
                t.setTicketId(rs.getInt("ticket_id"));
                tickets.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }
}

