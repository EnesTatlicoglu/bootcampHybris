package tr.nttdata.bootcamp.core.product.dao.impl;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.model.ProductViewHistoryModel;
import tr.nttdata.bootcamp.core.product.dao.ProductViewHistoryDao;

import java.util.Collections;

public class DefaultProductViewHistoryDao extends DefaultGenericDao<ProductViewHistoryModel> implements ProductViewHistoryDao {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProductViewHistoryDao.class);

    private static final String TOTAL_VIEW_COUNT_QUERY = "SELECT COUNT({" + ItemModel.PK + "})" +
            " FROM {" + ProductViewHistoryModel._TYPECODE + "}" +
            " WHERE {" + ProductViewHistoryModel.PRODUCT + "} = ?" + ProductViewHistoryModel.PRODUCT;

    public DefaultProductViewHistoryDao() {
        super(ProductViewHistoryModel._TYPECODE);
    }

    @Override
    public int getTotalView(ProductModel product) {
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(TOTAL_VIEW_COUNT_QUERY);
        fsq.addQueryParameter(ProductViewHistoryModel.PRODUCT, product);
        fsq.setResultClassList(Collections.singletonList(Integer.class));
        return getFlexibleSearchService().<Integer>search(fsq).getResult().get(0);
    }
}
