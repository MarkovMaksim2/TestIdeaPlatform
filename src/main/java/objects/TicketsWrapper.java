package objects;

import lombok.Data;

import java.util.List;

@Data
public class TicketsWrapper {
    private List<Ticket> tickets;
}
