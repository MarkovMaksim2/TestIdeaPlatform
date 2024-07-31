package tools;

import exceptions.EmptyListException;
import objects.Ticket;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinimalTimeCalculator implements Calculator {
    private final Map<String, Duration> career_times = new HashMap<>();

    @Override
    public String calculate(List<Ticket> tickets) throws EmptyListException {
        if (tickets == null || tickets.isEmpty()) {
            throw new EmptyListException("There is zero elements in list");
        }

        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        final DateTimeFormatter timeFormatter1 = DateTimeFormatter.ofPattern("HH:mm");
        final DateTimeFormatter timeFormatter2 = DateTimeFormatter.ofPattern("H:mm");
        for (Ticket ticket : tickets) {
            LocalTime arrival_local_time, departure_local_time;

            if (ticket.getArrival_time().length() == 5) {
                arrival_local_time = LocalTime.parse(ticket.getArrival_time(), timeFormatter1);
            } else {
                arrival_local_time = LocalTime.parse(ticket.getArrival_time(), timeFormatter2);
            }

            if (ticket.getDeparture_time().length() == 5) {
                departure_local_time = LocalTime.parse(ticket.getDeparture_time(), timeFormatter1);
            } else {
                departure_local_time = LocalTime.parse(ticket.getDeparture_time(), timeFormatter2);
            }

            LocalDateTime departure_time = LocalDateTime.of(LocalDate.parse(ticket.getDeparture_date(), dateFormatter), departure_local_time);
            LocalDateTime arrival_time = LocalDateTime.of(LocalDate.parse(ticket.getArrival_date(), dateFormatter), arrival_local_time);

            Duration duration = Duration.between(departure_time, arrival_time);

            if (career_times.get(ticket.getCarrier()) == null || duration.compareTo(career_times.get(ticket.getCarrier())) < 0) {
                career_times.put(ticket.getCarrier(), duration);
            }
        }

        StringBuilder returned = new StringBuilder();

        for (Map.Entry<String, Duration> entry : career_times.entrySet()) {
            returned.append("Minimal flight time of company ")
                    .append(entry.getKey())
                    .append(" is ")
                    .append(entry.getValue().toMinutes() / 60)
                    .append(" hours and ")
                    .append(entry.getValue().toMinutes() % 60)
                    .append(" minutes.")
                    .append("\n");
        }
        return returned.toString();
    }
}
