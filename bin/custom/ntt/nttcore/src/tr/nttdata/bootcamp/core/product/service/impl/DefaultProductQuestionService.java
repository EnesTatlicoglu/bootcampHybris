package tr.nttdata.bootcamp.core.product.service.impl;

import com.google.common.base.Preconditions;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.site.BaseSiteService;
import org.apache.commons.lang.StringUtils;
import tr.nttdata.bootcamp.core.model.ProductQuestionCategoryModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;
import tr.nttdata.bootcamp.core.product.dao.ProductQuestionDAO;
import tr.nttdata.bootcamp.core.product.parameter.ProductQuestionParameter;
import tr.nttdata.bootcamp.core.product.service.ProductQuestionService;

import java.util.Collections;
import java.util.List;

public class DefaultProductQuestionService implements ProductQuestionService {

    private ProductQuestionDAO productQuestionDAO;
    private ModelService modelService;
    private UserService userService;
    private CommonI18NService commonI18NService;
    private BaseSiteService baseSiteService;

    @Override
    public List<ProductQuestionModel> getQuestionsForProduct(ProductModel product) {
        return getProductQuestionDAO().getQuestionsForProduct(product) ;
    }

    @Override
    public List<ProductQuestionCategoryModel> getQuestionCategories() {
        return getProductQuestionDAO().getQuestionCategories();
    }

    @Override
    public ProductQuestionCategoryModel getQuestionCategoryForCode(String code) {
        return getProductQuestionDAO().getQuestionCategoryForCode(code);
    }

    @Override
    public ProductQuestionModel askQuestion(ProductQuestionParameter parameter) {
        ServicesUtil.validateParameterNotNull(parameter.getProduct(), "Product should not be null!");
        ServicesUtil.validateParameterNotNull(parameter.getCategory(), "Category should not be null!");
        Preconditions.checkArgument(StringUtils.isNotEmpty(parameter.getQuestion()), "Question should not be empty");

        UserModel user = getUserService().getCurrentUser();
        if (getUserService().isAnonymousUser(user)){
            throw new IllegalArgumentException("User should not be anonymous!");
        }

        ProductQuestionModel question = getModelService().create(ProductQuestionModel.class);
        question.setProduct(parameter.getProduct());
        question.setCategory(parameter.getCategory());
        question.setQuestion(parameter.getQuestion());
        question.setUser(user);
        question.setLanguage(getCommonI18NService().getCurrentLanguage());
        question.setSite(getBaseSiteService().getCurrentBaseSite());
        question.setHideUser(parameter.isHideUser());
        question.setOnlyForUser(parameter.isOnlyForUser());
        getModelService().save(question);
        return question;
    }

    public ProductQuestionDAO getProductQuestionDAO() {
        return productQuestionDAO;
    }

    public void setProductQuestionDAO(ProductQuestionDAO productQuestionDAO) {
        this.productQuestionDAO = productQuestionDAO;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public CommonI18NService getCommonI18NService() {
        return commonI18NService;
    }

    public void setCommonI18NService(CommonI18NService commonI18NService) {
        this.commonI18NService = commonI18NService;
    }

    public BaseSiteService getBaseSiteService() {
        return baseSiteService;
    }

    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }
}
