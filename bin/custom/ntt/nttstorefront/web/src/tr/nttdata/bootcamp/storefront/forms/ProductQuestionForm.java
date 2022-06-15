package tr.nttdata.bootcamp.storefront.forms;

import de.hybris.platform.acceleratorstorefrontcommons.util.XSSFilterUtil;

import java.io.Serializable;

public class ProductQuestionForm implements Serializable {

    private String categoryCode;
    private String question;
    private Boolean hideUser;
    private Boolean onlyForUser;


    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = XSSFilterUtil.filter(question);
    }

    public Boolean getHideUser() {
        return hideUser;
    }

    public void setHideUser(Boolean hideUser) {
        this.hideUser = hideUser;
    }

    public Boolean getOnlyForUser() {
        return onlyForUser;
    }

    public void setOnlyForUser(Boolean onlyForUser) {
        this.onlyForUser = onlyForUser;
    }
}
