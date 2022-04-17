package tr.nttdata.bootcamp.dao.impl;

import de.hybris.platform.core.*;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.genericsearch.GenericSearchService;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tr.nttdata.bootcamp.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.enums.BadgeStatus;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DefaultProductBadgeDao extends DefaultGenericDao<ProductBadgeModel> implements ProductBadgeDao {

    @Autowired
    private GenericSearchService genericSearchService;

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
        final GenericQuery query = new GenericQuery(ProductBadgeModel._TYPECODE);
        GenericSearchField statusField = new GenericSearchField(ProductBadgeModel.STATUS);
        GenericSearchField startDateField = new GenericSearchField(ProductBadgeModel.STARTDATE);
        GenericSearchField endDateField = new GenericSearchField(ProductBadgeModel.ENDDATE);
        GenericSearchField modifiedTimeField = new GenericSearchField(ItemModel.MODIFIEDTIME);

        final Date now = DateUtils.truncate(new Date(), Calendar.MINUTE); // For caching
        query.addCondition(GenericCondition.createConditionForValueComparison(statusField, Operator.EQUAL, BadgeStatus.ACTIVE));
        query.addCondition(GenericCondition.createConditionList(Operator.OR, GenericCondition.createIsNullCondition(startDateField),
                GenericCondition.createConditionForValueComparison(startDateField, Operator.LESS_OR_EQUAL, now)));
        query.addCondition(GenericCondition.createConditionList(Operator.OR, GenericCondition.createIsNullCondition(endDateField),
                GenericCondition.createConditionForValueComparison(endDateField, Operator.GREATER, now)));
        query.addOrderBy(new GenericSearchOrderBy(modifiedTimeField, false));

        return genericSearchService.<ProductBadgeModel>search(query).getResult();
    }

}