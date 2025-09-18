package org.irctc.model;

public class Ticket {
    private int ticketId;
    private String userEmail;
    private int trainId;
    private int seatBooked;

    public Ticket(String userEmail, int trainId, int seatBooked ) {
        this.userEmail= userEmail;
        this.trainId = trainId;
        this.seatBooked= seatBooked;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getTrainId() {
        return trainId;
    }

    public int getSeatBooked() {
        return seatBooked;
    }
}
