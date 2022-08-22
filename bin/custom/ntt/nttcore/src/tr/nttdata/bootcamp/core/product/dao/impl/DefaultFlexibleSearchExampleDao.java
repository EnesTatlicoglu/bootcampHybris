package tr.nttdata.bootcamp.core.product.dao.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.c2l.C2LItemModel;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.europe1.model.PDTRowModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.order.Order;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import org.apache.commons.collections4.CollectionUtils;
import org.hsqldb.rights.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.product.dao.FlexibleSearchExampleDao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DefaultFlexibleSearchExampleDao implements FlexibleSearchExampleDao {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFlexibleSearchExampleDao.class);

    private FlexibleSearchService flexibleSearchService;

    private static final String GET_ALL_ORDERS_QUERY = "Select {o:" + AbstractOrderModel.CODE + "}, "+
            "{o:" + AbstractOrderModel.DATE + "}, "+
            "{u:" + PrincipalModel.UID + "}, "+
            "{u:" + PrincipalModel.NAME + "}, "+
            "{o:" + AbstractOrderModel.TOTALPRICE + "}"+
            "FROM {" + OrderModel._TYPECODE + " AS o " +
            "LEFT JOIN " + UserModel._TYPECODE + " AS u ON {o:" + AbstractOrderModel.USER + "} = {u:" + ItemModel.PK + "}}";

    private static final String GET_ALL_ORDER_ENTRIES_QUERY = "Select {o:" + AbstractOrderModel.CODE + "}, " +
            "{p:" + ProductModel.CODE + "}, " +
            "{oe:" + AbstractOrderEntryModel.QUANTITY + "}, " +
            "{oe:" + AbstractOrderEntryModel.BASEPRICE + "}, " +
            "{oe:" + AbstractOrderEntryModel.TOTALPRICE + "}, " +
            "{u:" + PrincipalModel.UID + "}, " +
            "{u:" + PrincipalModel.NAME + "}, " +
            "{o:" + AbstractOrderModel.TOTALPRICE + "} " +
            "FROM {" + OrderEntryModel._TYPECODE +" AS oe " +
            "LEFT JOIN " + OrderModel._TYPECODE + " AS o ON {oe:" + AbstractOrderEntryModel.ORDER + "} = {o:" + ItemModel.PK + "} " +
            "LEFT JOIN " + ProductModel._TYPECODE + " AS p ON {oe:" + AbstractOrderEntryModel.PRODUCT + "} = {p:" + ItemModel.PK + "} " +
            "LEFT JOIN " + UserModel._TYPECODE + " AS u ON {o:" + AbstractOrderModel.USER + "} = {u:" + ItemModel.PK + "}}";

    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT {p:" + ProductModel.CODE + "}, " +
            "{cv:" + ItemModel.PK + "}, " +
            "{cv:" + CatalogVersionModel.VERSION + "}, " +
            "{p:" + ProductModel.NAME + "}, " +
            "CASE " +
            "WHEN CAST ({pr:" + PriceRowModel.PRICE + "} AS varchar) IS NULL THEN 'N/A' " +
            "ELSE CAST ({pr:" + PriceRowModel.PRICE + "} AS varchar) " +
            "END, "+
            "{c:" + C2LItemModel.ISOCODE + "}, "+
            "{p:" + ItemModel.MODIFIEDTIME + "} " +
            "FROM {" + ProductModel._TYPECODE + " AS p " +
            "LEFT JOIN " + CatalogVersionModel._TYPECODE + " AS cv ON {p:" + ProductModel.CATALOGVERSION + "} = {cv:" + ItemModel.PK + "} " +
            "LEFT JOIN " + PriceRowModel._TYPECODE + " AS pr ON {p:" + ProductModel.CODE + "} = {pr:" + PDTRowModel.PRODUCTID + "} " +
            "LEFT JOIN " + CurrencyModel._TYPECODE + " AS c ON {pr:" + PriceRowModel.CURRENCY + "} = {c:" + ItemModel.PK + "}} " +
            "WHERE {c:" + C2LItemModel.ISOCODE + "} = 'USD' " +
            //"AND {p:" + ProductModel.CODE+ "} = '289540' " +
            "";

    private static final String GET_ALL_PRICE_ROWS_QUERY = "Select {pr:" + PriceRowModel.PRICE + "}, " +
            "{c:" + C2LItemModel.ISOCODE + "}, " +
            "{p:" + ProductModel.CODE + "}, " +
            "{p:" + ProductModel.NAME + "[en]}, " +
            "{pr:" + PDTRowModel.STARTTIME + "}, " +
            "{pr:" + PDTRowModel.ENDTIME + "}, " +
            "{pr:" + ItemModel.MODIFIEDTIME + "} " +
            "FROM {" + PriceRowModel._TYPECODE + " AS pr " +
            "LEFT JOIN " + CurrencyModel._TYPECODE + " AS c ON {pr:" + PriceRowModel.CURRENCY + "} = {c:" + ItemModel.PK + "} " +
            "LEFT JOIN " + ProductModel._TYPECODE + " AS p ON {pr:" + PDTRowModel.PRODUCTID + "} = {p:" + ProductModel.CODE + "}}";

    private static final String GET_ALL_USERS_QUERY = "Select {u:" + PrincipalModel.UID + "}, " +
            "{u:" + PrincipalModel.NAME + "}, " +
            "COUNT(DISTINCT {o:" + ItemModel.PK + "}), " +
            "COUNT({oe:" + ItemModel.PK + "}), " +
            "CASE " +
            "WHEN SUM({oe:" + AbstractOrderEntryModel.QUANTITY + "}) IS NULL THEN 0 " +
            "ELSE SUM({oe:" + AbstractOrderEntryModel.QUANTITY + "}) " +
            "END " +
            "FROM {" + UserModel._TYPECODE + " AS u " +
            "LEFT JOIN " + OrderModel._TYPECODE + " AS o ON {u:" + ItemModel.PK + "} = {o:" + AbstractOrderModel.USER + "} " +
            "LEFT JOIN " + OrderEntryModel._TYPECODE + " AS oe ON {o:" + ItemModel.PK + "} = {oe:" + AbstractOrderEntryModel.ORDER + "}}" +
            "GROUP BY {u:" + PrincipalModel.UID + "}, {u:" + PrincipalModel.NAME + "}";

    public void getAllOrders(){
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(GET_ALL_ORDERS_QUERY);
        fsq.setResultClassList(Arrays.asList(String.class, Date.class, String.class, String.class, Double.class));
        List<List<Object>> result = getFlexibleSearchService().<List<Object>>search(fsq).getResult();


        LOG.info("**************");
        LOG.info("getAllOrders()");
        if (CollectionUtils.isNotEmpty(result)){
            result.forEach(r -> LOG.info("***\nCode = {}\nDate = {}\nUser UID = {}\nUser Name = {}\nTotal Price = {}",
                    r.get(0),r.get(1),r.get(2),r.get(3),r.get(4)));
        }
        LOG.info("**************");


    }

    public void getAllOrderEntries(){
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(GET_ALL_ORDER_ENTRIES_QUERY);
        fsq.setResultClassList(Arrays.asList(String.class, String.class, Long.class, Double.class,
                Double.class, String.class, String.class, Double.class));
        List<List<Object>> result = getFlexibleSearchService().<List<Object>>search(fsq).getResult();


        LOG.info("**************");
        LOG.info("getAllOrderEntries()");
        if (CollectionUtils.isNotEmpty(result)){
            result.forEach(r -> LOG.info("***\nOrder Code = {}\nProduct Code = {}\nQuantity = {}\nBase Price = {}\n" +
                            "Total Price = {}\nUser UID = {}\nUser Name = {}\nOrder Total Price = {}",
                            r.get(0),r.get(1),r.get(2),r.get(3),r.get(4),r.get(5),r.get(6),r.get(7)));
        }
        LOG.info("**************");

    }

    public void getAllProducts(){
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(GET_ALL_PRODUCTS_QUERY);
        fsq.setResultClassList(Arrays.asList(String.class, String.class, String.class, String.class, String.class,
                String.class, Date.class));
        fsq.setDisableSearchRestrictions(true);
        fsq.setCount(5);
        List<List<Object>> result = getFlexibleSearchService().<List<Object>>search(fsq).getResult();


        LOG.info("**************");
        LOG.info("getAllProducts()");
        if (CollectionUtils.isNotEmpty(result)){
            result.forEach(r -> {
                LOG.info("***\nProduct Code = {}\nVersion ID = {}\nVersion = {}\nProduct Name = {}\nPrice = {} {}\nModified Time = {}",
                        r.get(0),r.get(1),r.get(2),r.get(3),r.get(4) != null ? r.get(4) : "N/A",r.get(5),r.get(6));
            });
        }
        LOG.info("**************");

    }

    public void getAllPriceRows(){
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(GET_ALL_PRICE_ROWS_QUERY);
        fsq.setResultClassList(Arrays.asList(Double.class, String.class, String.class, String.class, Date.class, Date.class,
                Date.class));

        fsq.setDisableSearchRestrictions(true);
        fsq.setCount(5);

        List<List<Object>> result = getFlexibleSearchService().<List<Object>>search(fsq).getResult();


        LOG.info("**************");
        LOG.info("getAllPriceRows()");
        if (CollectionUtils.isNotEmpty(result)){
            result.forEach(r -> {
                LOG.info("***\nPrice = {}\nCurrency Isocode = {}\nProduct Code = {}\nProduct Name = {}\nActive From = {}\n" +
                                "Active To = {}\nModified Time = {}",
                        r.get(0),r.get(1),r.get(2),r.get(3),r.get(4),r.get(5),r.get(6));
            });
        }
        LOG.info("**************");


    }

    public void getAllUsers(){

        FlexibleSearchQuery fsq = new FlexibleSearchQuery(GET_ALL_USERS_QUERY);
        fsq.setResultClassList(Arrays.asList(String.class, String.class, Integer.class, Integer.class, Long.class));

        fsq.setCount(5);

        List<List<Object>> result = getFlexibleSearchService().<List<Object>>search(fsq).getResult();


        LOG.info("**************");
        LOG.info("getAllUsers()");
        if (CollectionUtils.isNotEmpty(result)){
            result.forEach(r -> {
                LOG.info("***\nUser UID = {}\nUser Name = {}\nOrder Count = {}\nOrder Entry Count = {}\nTotal Quantity Of Order Entries = {}",
                        r.get(0),r.get(1),r.get(2),r.get(3),r.get(4));
            });
        }
        LOG.info("**************");



    }

    public void getProducts(){
        FlexibleSearchQuery fsq = new FlexibleSearchQuery("SELECT {" + ItemModel.PK + "} FROM {" + ProductModel._TYPECODE + "}");
        fsq.setStart(0);
        fsq.setCount(10);
        fsq.setNeedTotal(true);
        fsq.setDisableSearchRestrictions(true);

        SearchResult<ProductModel> result = getFlexibleSearchService().<ProductModel>search(fsq);

        LOG.info("********");//8796102492161
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
