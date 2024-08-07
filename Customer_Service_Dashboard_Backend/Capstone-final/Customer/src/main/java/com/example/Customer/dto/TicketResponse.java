package com.example.Customer.dto;


import java.time.LocalDateTime;
import com.example.Customer.entity.Ticket;
 
public class TicketResponse {
    private Ticket ticket;
    private String customerUserName;
 
    public TicketResponse(Ticket ticket, String customerUserName) {
        this.ticket = ticket;
        this.customerUserName = customerUserName;
    }
 
    public Ticket getTicket() {
        return ticket;
    }
 
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
 
    public String getCustomerUserName() {
        return customerUserName;
    }
 
    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }
}
 
