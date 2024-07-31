import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.EmptyListException;
import objects.Ticket;
import objects.TicketsWrapper;
import tools.Calculator;
import tools.MedianAverageDifferenceCalculator;
import tools.MinimalTimeCalculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TicketsWrapper tickets = new TicketsWrapper();
        Gson g = new GsonBuilder().create();

        try {
            tickets = g.fromJson(Files.readString(Paths.get("tickets.json")), tickets.getClass());

            List<Ticket> ticketsListVVO = tickets.getTickets().stream()
                    .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
                    .toList();

            List<Calculator> calculators = new ArrayList<>();

            calculators.add(new MedianAverageDifferenceCalculator());
            calculators.add(new MinimalTimeCalculator());

            for (Calculator calculator : calculators) {
                System.out.println(calculator.calculate(ticketsListVVO));
            }
        } catch (IOException e) {
            System.out.println("Error reading tickets.json, check that file and executable are in the same directory\n");
        } catch (EmptyListException e) {
          System.out.println("Ooops, it seems tickets.json is empty.\n");
        } catch (Exception e) {
            System.out.println("Unhandled exception: " + e + "\n");
        }
    }
}
