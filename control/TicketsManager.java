package control;

import control.backup.Memento;
import control.validation.InvalidCredentialsException;
import entity.Ticket;

import java.util.List;

public class TicketsManager implements Manager {
    private Dao<String, Ticket> tickets;

    public TicketsManager(DaoFactory<String, Ticket> factory) {
        tickets = factory.createDao();
    }

    public Ticket getTicket(String title) {
        return tickets.get(title);
    }

    public List<Ticket> getTickets() {
        return tickets.getAll();
    }

    public void addTicket(String title, String author, String description) {
        tickets.add(title, new Ticket(title, author, description));
    }

    public void deleteTicket(String title) {
        tickets.delete(title);
    }

    public void EditDescription(String ticketTitle, String newDescription) {
        Ticket ticket = getTicket(ticketTitle);
        ticket.setDescription(newDescription);
        tickets.update(ticket.getTitle(), ticket);
    }

    @Override
    public Memento createMemento() {
        return tickets.createMemento();
    }

    @Override
    public void restoreMemento(Memento m) {
        tickets.restoreMemento(m);
    }

    @Override
    public void insert(Arguments data) {
        List<Object> args = data.getArgs();
        addTicket((String) args.get(0), (String) args.get(1), (String) args.get(2));
    }

    @Override
    public void delete(Arguments data) {
        List<Object> args = data.getArgs();
        deleteTicket((String) args.get(0));
    }

    @Override
    public void update(Arguments data) throws InvalidCredentialsException {
        List<Object> args = data.getArgs();
        EditDescription((String) args.get(0), (String) args.get(1));
    }

    @Override
    public void viewAll(EntityViewer visitor) {
        for (Ticket ticket : getTickets()) {
            ticket.view(visitor);
        }
    }
}
