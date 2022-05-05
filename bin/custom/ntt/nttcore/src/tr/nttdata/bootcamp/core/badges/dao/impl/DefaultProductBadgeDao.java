package tr.nttdata.bootcamp.core.badges.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.core.badges.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.core.model.ProductBadgeModel;

import java.util.Collections;
import java.util.List;

public class DefaultProductBadgeDao extends DefaultGenericDao<ProductBadgeModel> implements ProductBadgeDao {

    public DefaultProductBadgeDao() {
        super(ProductBadgeModel._TYPECODE);
    }

    @Override
    public ProductBadgeModel getProductBadgeForCode(String code) {
        final List<ProductBadgeModel> result = find(Collections.singletonMap(ProductBadgeModel.CODE, code));
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }
}
