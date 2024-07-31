package tools;

import exceptions.EmptyListException;
import objects.Ticket;

import java.util.List;

public interface Calculator {
    String calculate(List<Ticket> tickets) throws EmptyListException;
}
