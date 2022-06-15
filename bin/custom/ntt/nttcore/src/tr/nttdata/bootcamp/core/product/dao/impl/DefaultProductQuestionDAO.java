package tr.nttdata.bootcamp.core.product.dao.impl;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import tr.nttdata.bootcamp.core.model.ProductQuestionCategoryModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;
import tr.nttdata.bootcamp.core.product.dao.ProductQuestionDAO;

import java.util.Collections;
import java.util.List;

public class DefaultProductQuestionDAO implements ProductQuestionDAO {

    private static final String QUESTIONS_FOR_PRODUCT_QUERY = "SELECT {" + ItemModel.PK + "} " +
            " FROM {" + ProductQuestionModel._TYPECODE + "}" +
            " WHERE {" + ProductQuestionModel.PRODUCT + "} = ?" + ProductQuestionModel.PRODUCT;

    private static final String GET_QUESTION_CATEGORIES_QUERY = "SELECT {" + ItemModel.PK + "} " +
            " FROM {" + ProductQuestionCategoryModel._TYPECODE + "}";

    private static final String GET_QUESTION_CATEGORY_FOR_CODE = GET_QUESTION_CATEGORIES_QUERY +
            " WHERE {" + ProductQuestionCategoryModel.CODE + "} = ?" + ProductQuestionCategoryModel.CODE;

    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<ProductQuestionModel> getQuestionsForProduct(ProductModel product) {
        if (product == null){
            Collections.emptyList();
        }

        FlexibleSearchQuery fsq = new FlexibleSearchQuery(QUESTIONS_FOR_PRODUCT_QUERY);
        fsq.addQueryParameter(ProductQuestionModel.PRODUCT, product);
        List<ProductQuestionModel> result = getFlexibleSearchService().<ProductQuestionModel>search(fsq).getResult();
        return result != null ? result : Collections.emptyList();
    }

    @Override
    public List<ProductQuestionCategoryModel> getQuestionCategories() {
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(GET_QUESTION_CATEGORIES_QUERY);
        List<ProductQuestionCategoryModel> result = getFlexibleSearchService().<ProductQuestionCategoryModel>search(fsq).getResult();
        return result != null ? result : Collections.emptyList();
    }

    @Override
    public ProductQuestionCategoryModel getQuestionCategoryForCode(String code) {
        if (code == null){
            return null;
        }
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(GET_QUESTION_CATEGORY_FOR_CODE);
        fsq.addQueryParameter(ProductQuestionCategoryModel.CODE, code);
        List<ProductQuestionCategoryModel> result = getFlexibleSearchService().<ProductQuestionCategoryModel>search(fsq).getResult();
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
