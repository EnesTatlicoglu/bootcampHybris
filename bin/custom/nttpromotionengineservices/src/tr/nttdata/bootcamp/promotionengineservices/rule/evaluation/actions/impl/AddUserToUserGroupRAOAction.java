package tr.nttdata.bootcamp.promotionengineservices.rule.evaluation.actions.impl;

import de.hybris.platform.ruleengineservices.rao.*;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.AbstractRuleExecutableSupport;
import de.hybris.platform.ruleengineservices.util.RAOConstants;

public class AddUserToUserGroupRAOAction extends AbstractRuleExecutableSupport {

    @Override
    public boolean performActionInternal(final RuleActionContext context)
    {
        final String userGroupCode = context.getParameter(RAOConstants.VALUE_PARAM, String.class);
        performAction(context, userGroupCode);
        return true;
    }

    protected void performAction(final RuleActionContext context, final String userGroupCode)
    {
        final CartRAO cartRao = context.getCartRao();
        final RuleEngineResultRAO result = context.getRuleEngineResultRao();

        final ChangeUserGroupRAO changeUserGroupRAO = new ChangeUserGroupRAO();
        getRaoUtils().addAction(cartRao, changeUserGroupRAO);
        changeUserGroupRAO.setUserGroupId(userGroupCode);
        result.getActions().add(changeUserGroupRAO);

        final UserGroupRAO userGroupRAO = new UserGroupRAO();
        userGroupRAO.setId(userGroupCode);
        final UserRAO user = cartRao.getUser();
        user.getGroups().add(userGroupRAO);

        setRAOMetaData(context, changeUserGroupRAO);
        context.insertFacts(changeUserGroupRAO, userGroupRAO);
        context.scheduleForUpdate(user);
    }
}