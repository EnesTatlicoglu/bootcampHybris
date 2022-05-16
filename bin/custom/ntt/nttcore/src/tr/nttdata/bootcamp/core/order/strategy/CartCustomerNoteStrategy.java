package tr.nttdata.bootcamp.core.order.strategy;

import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;

public interface CartCustomerNoteStrategy {

    boolean updateCustomerNote(CommerceCartParameter parameter);

}