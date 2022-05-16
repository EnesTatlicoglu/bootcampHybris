package tr.nttdata.bootcamp.core.order;

import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;

public interface CartCustomerNoteService {

    boolean updateCustomerNote(CommerceCartParameter parameter);

}