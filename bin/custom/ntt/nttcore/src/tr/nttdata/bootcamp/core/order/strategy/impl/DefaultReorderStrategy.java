package tr.nttdata.bootcamp.core.order.strategy.impl;

import de.hybris.platform.commerceservices.order.CommerceCartRestoration;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.model.ModelService;
import tr.nttdata.bootcamp.core.order.strategy.CreateCartFromOrderStrategy;
import tr.nttdata.bootcamp.core.order.strategy.ReorderStrategy;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class DefaultReorderStrategy implements ReorderStrategy {

    private CreateCartFromOrderStrategy createCartFromOrderStrategy;
    private ModelService modelService;
    private CommerceCartRestorationStrategy commerceCartRestorationStrategy;
    private CartService cartService;

    @Override
    public CommerceCartRestoration reorder(CommerceOrderParameter parameter) throws CommerceCartRestorationException {
        if (parameter.getOrder() instanceof OrderModel) {
            OrderModel order = (OrderModel) parameter.getOrder();
            CartModel cart = getCreateCartFromOrderStrategy().createCartFromOrder(order);
            if(cart != null && getCartService().hasSessionCart()){
                getCartService().removeSessionCart();
            }

            if (cart != null) {
                cart.setDate(new Date());
                cart.setAllPromotionResults(Collections.emptySet());
                getModelService().save(cart);


                CommerceCartParameter cartParameter = new CommerceCartParameter();
                cartParameter.setCart(cart);
                cartParameter.setEnableHooks(true);
                cartParameter.setIgnoreRecalculation(true);
                return getCommerceCartRestorationStrategy().restoreCart(cartParameter);
            }
        }
        return null;
    }

    public CreateCartFromOrderStrategy getCreateCartFromOrderStrategy() {
        return createCartFromOrderStrategy;
    }

    public void setCreateCartFromOrderStrategy(CreateCartFromOrderStrategy createCartFromOrderStrategy) {
        this.createCartFromOrderStrategy = createCartFromOrderStrategy;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public CommerceCartRestorationStrategy getCommerceCartRestorationStrategy() {
        return commerceCartRestorationStrategy;
    }

    public void setCommerceCartRestorationStrategy(CommerceCartRestorationStrategy commerceCartRestorationStrategy) {
        this.commerceCartRestorationStrategy = commerceCartRestorationStrategy;
    }

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
