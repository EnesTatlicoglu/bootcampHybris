package tr.nttdata.bootcamp.promotionengineservices.conditions;

import de.hybris.platform.ruledefinitions.conditions.AbstractRuleConditionTranslator;
import de.hybris.platform.ruledefinitions.conditions.builders.RuleIrAttributeConditionBuilder;
import de.hybris.platform.ruleengineservices.compiler.RuleCompilerContext;
import de.hybris.platform.ruleengineservices.compiler.RuleIrAttributeOperator;
import de.hybris.platform.ruleengineservices.compiler.RuleIrCondition;
import de.hybris.platform.ruleengineservices.rao.UserRAO;
import de.hybris.platform.ruleengineservices.rule.data.RuleConditionData;
import de.hybris.platform.ruleengineservices.rule.data.RuleConditionDefinitionData;

public class RuleCustomerFirstOrderTranslator extends AbstractRuleConditionTranslator {

    @Override
    public RuleIrCondition translate(RuleCompilerContext context, RuleConditionData condition, RuleConditionDefinitionData conditionDefinition) {
        return RuleIrAttributeConditionBuilder.newAttributeConditionFor(context.generateVariable(UserRAO.class)).withAttribute("hasOrder")
                .withOperator(RuleIrAttributeOperator.EQUAL).withValue(Boolean.FALSE).build();
    }

}