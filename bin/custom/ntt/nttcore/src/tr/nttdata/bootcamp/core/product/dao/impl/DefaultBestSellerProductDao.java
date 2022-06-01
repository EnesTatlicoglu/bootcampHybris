package tr.nttdata.bootcamp.core.product.dao.impl;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import tr.nttdata.bootcamp.core.model.BestSellerProductModel;
import tr.nttdata.bootcamp.core.product.dao.BestSellerProductDao;
import tr.nttdata.bootcamp.core.product.data.ProductSellData;

import java.util.*;

public class DefaultBestSellerProductDao extends DefaultGenericDao<BestSellerProductModel> implements BestSellerProductDao {

    private static final String SOLD_COUNT_QUERY = "SELECT {p:" + ProductModel.CODE + "}, " +
            " SUM({oe:" + AbstractOrderEntryModel.QUANTITY + "}) " +
            " FROM {" + OrderEntryModel._TYPECODE + " AS oe" +
            " JOIN " + OrderModel._TYPECODE + " AS o ON {o:" + ItemModel.PK + "}={oe:" + AbstractOrderEntryModel.ORDER + "}" +
            " JOIN " + ProductModel._TYPECODE + " AS p ON {p:" + ItemModel.PK + "}={oe:" + AbstractOrderEntryModel.PRODUCT + "}}" +
            " WHERE {o:" + AbstractOrderModel.DATE + "} > ?" + AbstractOrderModel.DATE +
            " AND {o:" + OrderModel.VERSIONID + "} IS NULL" +
            " GROUP BY {p:" + ProductModel.CODE + "}" +
            " ORDER BY SUM({oe:" + AbstractOrderEntryModel.QUANTITY + "}) DESC";

    public DefaultBestSellerProductDao() {
        super(BestSellerProductModel._TYPECODE);
    }

    @Override
    public Map<String, Long> getSoldCounts(Integer day, Integer count) {
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(SOLD_COUNT_QUERY);
        fsq.addQueryParameter(AbstractOrderModel.DATE, DateUtils.truncate(DateUtils.addDays(new Date(), -day), Calendar.MINUTE));
        fsq.setResultClassList(Arrays.asList(String.class, Long.class));
        fsq.setCount(count);
        List<List<Object>> result = getFlexibleSearchService().<List<Object>>search(fsq).getResult();
        if (CollectionUtils.isEmpty(result)){
            return Collections.emptyMap();
        }
        Map<String, Long> soldCounts = new HashMap<>(result.size());
        result.forEach(r -> {
            soldCounts.put((String) r.get(0), (Long) r.get(1));
        });
        return soldCounts;
    }

    @Override
    public List<ProductSellData> getSoldCountsData(Integer day, Integer count) {
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(SOLD_COUNT_QUERY);
        fsq.addQueryParameter(AbstractOrderModel.DATE, DateUtils.truncate(DateUtils.addDays(new Date(), -day), Calendar.MINUTE));
        fsq.setResultClassList(Arrays.asList(String.class, Long.class));
        fsq.setCount(count);
        List<List<Object>> result = getFlexibleSearchService().<List<Object>>search(fsq).getResult();
        if (CollectionUtils.isEmpty(result)){
            return Collections.emptyList();
        }
        List<ProductSellData> soldCounts = new ArrayList<>(result.size());
        result.forEach(r -> {
            ProductSellData data = new ProductSellData();
            data.setProductCode((String) r.get(0));
            data.setSoldCount((Long) r.get(1));
            soldCounts.add(data);
        });
        return null;
    }

    @Override
    public BestSellerProductModel getBestSellerProduct(String productCode) {
        List<BestSellerProductModel> result = find(Collections.singletonMap(BestSellerProductModel.PRODUCTCODE, productCode));
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }

    @Override
    public List<BestSellerProductModel> getAllBestSellerProducts() {
        return find();
    }
}
