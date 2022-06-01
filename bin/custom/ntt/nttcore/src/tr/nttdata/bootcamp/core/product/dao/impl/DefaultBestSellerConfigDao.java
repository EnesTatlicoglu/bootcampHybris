package tr.nttdata.bootcamp.core.product.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.core.model.BestSellerConfigModel;
import tr.nttdata.bootcamp.core.product.dao.BestSellerConfigDao;

import java.util.List;

public class DefaultBestSellerConfigDao extends DefaultGenericDao<BestSellerConfigModel> implements BestSellerConfigDao {

    public DefaultBestSellerConfigDao() {
        super(BestSellerConfigModel._TYPECODE);
    }

    @Override
    public BestSellerConfigModel getConfig() {
        List<BestSellerConfigModel> result = find();
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }
}
