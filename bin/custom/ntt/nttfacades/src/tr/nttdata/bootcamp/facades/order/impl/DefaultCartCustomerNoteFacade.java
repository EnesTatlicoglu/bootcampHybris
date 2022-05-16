package tr.nttdata.bootcamp.facades.order.impl;

import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.order.CartService;
import tr.nttdata.bootcamp.core.order.CartCustomerNoteService;
import tr.nttdata.bootcamp.facades.order.CartCustomerNoteFacade;

public class DefaultCartCustomerNoteFacade implements CartCustomerNoteFacade {

    private CartService cartService;
    private CartCustomerNoteService cartCustomerNoteService;

    @Override
    public boolean updateCustomerNote(String note) {
        if(!getCartService().hasSessionCart()){
            return false;
        }

        final CommerceCartParameter parameter = new CommerceCartParameter();
        parameter.setCart(getCartService().getSessionCart());
        parameter.setCustomerNote(note);
        return getCartCustomerNoteService().updateCustomerNote(parameter);
    }

    @Override
    public boolean removeCustomerNote() {
        return updateCustomerNote(null);
    }

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public CartCustomerNoteService getCartCustomerNoteService() {
        return cartCustomerNoteService;
    }

    public void setCartCustomerNoteService(CartCustomerNoteService cartCustomerNoteService) {
        this.cartCustomerNoteService = cartCustomerNoteService;
    }
}