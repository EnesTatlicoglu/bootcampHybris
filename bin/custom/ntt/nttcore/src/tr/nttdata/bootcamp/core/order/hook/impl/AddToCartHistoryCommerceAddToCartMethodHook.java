package tr.nttdata.bootcamp.core.order.hook.impl;

import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.hook.CommerceAddToCartMethodHook;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.servicelayer.model.ModelService;
import tr.nttdata.bootcamp.core.model.AddToCartHistoryModel;

public class AddToCartHistoryCommerceAddToCartMethodHook implements CommerceAddToCartMethodHook {

    private ModelService modelService;

    @Override
    public void beforeAddToCart(CommerceCartParameter parameters) throws CommerceCartModificationException {
        // Do nothing
    }

    @Override
    public void afterAddToCart(CommerceCartParameter parameters, CommerceCartModification result) throws CommerceCartModificationException {
        final AddToCartHistoryModel history = getModelService().create(AddToCartHistoryModel.class);
        history.setCartCode(parameters.getCart().getCode());
        history.setUser(parameters.getCart().getUser());
        history.setProduct(parameters.getProduct());
        history.setQuantity(parameters.getQuantity());
        history.setQuantityAdded(result.getQuantityAdded());
        history.setStatusCode(result.getStatusCode());
        getModelService().save(history);
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

}