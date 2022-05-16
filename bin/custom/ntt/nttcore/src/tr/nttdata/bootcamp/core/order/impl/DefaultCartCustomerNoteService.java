package tr.nttdata.bootcamp.core.order.impl;

import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import tr.nttdata.bootcamp.core.order.CartCustomerNoteService;
import tr.nttdata.bootcamp.core.order.strategy.CartCustomerNoteStrategy;

public class DefaultCartCustomerNoteService implements CartCustomerNoteService {

    private CartCustomerNoteStrategy cartCustomerNoteStrategy;

    @Override
    public boolean updateCustomerNote(CommerceCartParameter parameter) {
        return cartCustomerNoteStrategy.updateCustomerNote(parameter);
    }

    public void setCartCustomerNoteStrategy(CartCustomerNoteStrategy cartCustomerNoteStrategy) {
        this.cartCustomerNoteStrategy = cartCustomerNoteStrategy;
    }

}