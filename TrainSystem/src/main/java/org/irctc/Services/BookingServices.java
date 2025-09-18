package org.irctc.Services;


import org.irctc.db.TicketDAO;

public class BookingServices {
    private TicketDAO ticketDAO = new TicketDAO();

    public boolean cancelTicket(int ticketId) {
        return ticketDAO.cancelTicket(ticketId);
    }
}
