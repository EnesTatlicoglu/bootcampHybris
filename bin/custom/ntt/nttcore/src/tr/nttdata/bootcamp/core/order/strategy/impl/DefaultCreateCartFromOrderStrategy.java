package tr.nttdata.bootcamp.core.order.strategy.impl;

import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.strategies.ordercloning.CloneAbstractOrderStrategy;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import tr.nttdata.bootcamp.core.order.strategy.CreateCartFromOrderStrategy;

public class DefaultCreateCartFromOrderStrategy implements CreateCartFromOrderStrategy {

    private CloneAbstractOrderStrategy cloneAbstractOrderStrategy;
    private KeyGenerator keyGenerator;

    @Override
    public CartModel createCartFromOrder(OrderModel order) {
        return getCloneAbstractOrderStrategy().clone(null, null, order, generateOrderCode(order), CartModel.class,
                CartEntryModel.class);
    }

    protected String generateOrderCode(final OrderModel order) {
        final Object generatedValue = keyGenerator.generate();
        if (generatedValue instanceof String) {
            return (String) generatedValue;
        } else {
            return String.valueOf(generatedValue);
        }
    }

    public CloneAbstractOrderStrategy getCloneAbstractOrderStrategy() {
        return cloneAbstractOrderStrategy;
    }

    public void setCloneAbstractOrderStrategy(CloneAbstractOrderStrategy cloneAbstractOrderStrategy) {
        this.cloneAbstractOrderStrategy = cloneAbstractOrderStrategy;
    }

    public KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }

    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }
}
