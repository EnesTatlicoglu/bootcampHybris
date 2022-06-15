package tr.nttdata.bootcamp.facades.product;

import tr.nttdata.bootcamp.facades.product.data.ProductQuestionCategoryData;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionData;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionParameterData;

import java.util.List;

public interface ProductQuestionFacade {

    List<ProductQuestionData> getQuestionsForProduct(String productCode);

    List<ProductQuestionCategoryData> getQuestionCategories();

    ProductQuestionData askQuestion(ProductQuestionParameterData parameter);
}
