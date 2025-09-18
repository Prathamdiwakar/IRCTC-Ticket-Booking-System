package org.irctc.db;
import com.mysql.cj.protocol.a.TracingPacketReader;
import org.irctc.model.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TrainDAO {
    public boolean addTrain(Train train){
        String query = "INSERT INTO trains(train_name, Source ,destination,total_seats, available_seats) VALUES (?,?,?,?,?)";
        try(Connection connection = DatabaseConnection.getconnection();
            PreparedStatement preparedStatement= connection.prepareStatement(query)){

            preparedStatement.setString(1,train.getTrainName());
            preparedStatement.setString(2,train.getTrainSource());
            preparedStatement.setString(3,train.getDestination());
            preparedStatement.setInt(4,train.getTotalSeat());
            preparedStatement.setInt(5,train.getAvailableSeat());

            int row = preparedStatement.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Train> getAllTrain(){
        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM trains";
        try(Connection connection = DatabaseConnection.getconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            var rs = preparedStatement.executeQuery()){
            while(rs.next()){
                Train train = new Train(rs.getString("train_name"), rs.getString("source"), rs.getString("destination"),rs.getInt("total_seats"), rs.getInt("available_seats"));
                train.setTrain_id(rs.getInt("train_id"));
                trains.add(train);
        }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return trains;
    }
    public  List<Train> SearchTrains(String source, String destination){
        List<Train> trains = new ArrayList<>();
        String qr = "SELECT * FROM trains WHERE source = ? AND destination =?";
        try(Connection connection = DatabaseConnection.getconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(qr)){

            preparedStatement.setString(1, source);
            preparedStatement.setString(2, destination);

            var rs = preparedStatement.executeQuery();
            while(rs.next()){
                Train train =new Train(rs.getString("train_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("total_seats"),
                        rs.getInt("available_seats")
                );
                train.setTrain_id(rs.getInt("train_id"));
                trains.add(train);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trains;
    }
}
