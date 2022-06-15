package tr.nttdata.bootcamp.core.product.dao;

import de.hybris.platform.core.model.product.ProductModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionCategoryModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;

import java.util.List;

public interface ProductQuestionDAO {

    List<ProductQuestionModel> getQuestionsForProduct(ProductModel product);

    List<ProductQuestionCategoryModel> getQuestionCategories();

    ProductQuestionCategoryModel getQuestionCategoryForCode(String code);

}
