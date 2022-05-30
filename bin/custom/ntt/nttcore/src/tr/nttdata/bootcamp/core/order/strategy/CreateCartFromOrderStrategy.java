package tr.nttdata.bootcamp.core.order.strategy;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;

public interface CreateCartFromOrderStrategy {

    CartModel createCartFromOrder(OrderModel order);
}
