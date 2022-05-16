package tr.nttdata.bootcamp.core.order.strategy.impl;

import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.model.ModelService;
import tr.nttdata.bootcamp.core.order.strategy.CartCustomerNoteStrategy;

public class DefaultCartCustomerNoteStrategy implements CartCustomerNoteStrategy {

    private ModelService modelService;

    @Override
    public boolean updateCustomerNote(CommerceCartParameter parameter) {
        CartModel cart = parameter.getCart();
        if(cart == null){
            return false;
        }

        cart.setCustomerNote(parameter.getCustomerNote());
        getModelService().save(cart);
        return true;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}