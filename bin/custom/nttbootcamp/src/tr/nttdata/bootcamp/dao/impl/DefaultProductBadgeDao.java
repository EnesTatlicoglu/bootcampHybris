package tr.nttdata.bootcamp.dao.impl;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.enums.BadgeStatus;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.Collections;
import java.util.List;

public class DefaultProductBadgeDao extends DefaultGenericDao<ProductBadgeModel> implements ProductBadgeDao {

    public DefaultProductBadgeDao() {
        super(ProductBadgeModel._TYPECODE);
    }

    @Override
    public ProductBadgeModel findBadgeForCode(String code) {
        List<ProductBadgeModel> result = find(Collections.singletonMap(ProductBadgeModel.CODE, code));
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }

    @Override
    public List<ProductBadgeModel> findActiveBadges() {
        return find(Collections.singletonMap(ProductBadgeModel.STATUS, BadgeStatus.ACTIVE),
                SortParameters.singletonDescending(ItemModel.MODIFIEDTIME));
    }

}