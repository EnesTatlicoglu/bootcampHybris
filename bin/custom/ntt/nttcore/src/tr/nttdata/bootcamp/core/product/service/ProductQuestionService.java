package tr.nttdata.bootcamp.core.product.service;

import de.hybris.platform.core.model.product.ProductModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionCategoryModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;
import tr.nttdata.bootcamp.core.product.parameter.ProductQuestionParameter;

import java.util.List;


public interface ProductQuestionService {

    List<ProductQuestionModel> getQuestionsForProduct(ProductModel product);

    List<ProductQuestionCategoryModel> getQuestionCategories();

    ProductQuestionCategoryModel getQuestionCategoryForCode(String code);

    ProductQuestionModel askQuestion(ProductQuestionParameter parameter);
}
