package tools;

import exceptions.EmptyListException;
import lombok.Getter;
import objects.Ticket;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

@Getter
public class MedianAverageDifferenceCalculator implements Calculator {
    private Double average;
    private Double median;

    private Double calculateAverage(List<Ticket> tickets) {
        average = 0.0;

        if (tickets.isEmpty()) { return 0.0; }

        for (Ticket ticket : tickets) {
            average = average + ticket.getPrice();
        }

        average /= tickets.size();

        return average;
    }

    private Double calculateMedian(List<Ticket> tickets) {
        TreeSet<Integer> prices = new TreeSet<>();
        median = 0.0;

        if (tickets.isEmpty()) { return 0.0; }

        for (Ticket ticket : tickets) {
            prices.add(ticket.getPrice());
        }

        if (prices.size() % 2 != 0) {
            Iterator<Integer> iterator = prices.iterator();

            for (int i = 0; i <= prices.size() / 2; i++) {
                median = Double.valueOf(iterator.next());
            }
            return median;
        } else {
            Iterator<Integer> iterator = prices.iterator();
            Integer prev = 0;
            Integer next = 0;

            for (int i = 0; i <= prices.size() / 2; i++) {
                prev = next;
                next = iterator.next();
            }

            median = (next + prev) / 2.0;

            return median;
        }
    }

    @Override
    public String calculate(List<Ticket> tickets) throws EmptyListException {
        if (tickets == null || tickets.isEmpty()) {
            throw new EmptyListException("There is zero elements in list");
        }

        return "The difference between average and median cost is " + (calculateAverage(tickets) - calculateMedian(tickets)) + "\n";
    }
}
