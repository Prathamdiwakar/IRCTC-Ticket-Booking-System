package org.irctc.model;

public class Train {
    private int train_id;
    private String trainName;
    private String trainSource;
    private String destination;
    private int totalSeat;
    private int availableSeat;

    public Train(String trainName,String trainSource,String destination,int totalSeat,int availableSeat) {
        this.trainName= trainName;
        this.trainSource= trainSource;
        this.destination= destination;
        this.totalSeat= totalSeat;
        this.availableSeat=availableSeat;
    }

    public int getTrain_id() {
        return train_id;
    }

    public void setTrain_id(int train_id) {
        this.train_id = train_id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainSource() {
        return trainSource;
    }

    public void setTrainSource(String trainSource) {
        this.trainSource = trainSource;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }
}
