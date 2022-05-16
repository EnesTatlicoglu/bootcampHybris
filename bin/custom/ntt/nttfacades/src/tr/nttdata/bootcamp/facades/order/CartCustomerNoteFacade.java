package tr.nttdata.bootcamp.facades.order;

public interface CartCustomerNoteFacade {

    boolean updateCustomerNote(String note);

    boolean removeCustomerNote();
}