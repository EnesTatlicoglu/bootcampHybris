<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:url value="${product.url}/askQuestion" var="productAskQuestionActionUrl"/>

<div class="tab-question">
<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
	<div class="ask-question">
		<form:form method="post" action="${productAskQuestionActionUrl}" modelAttribute="productQuestionForm">
			<div class="form-group">
                <formElement:formSelectBox idKey="productQuestion.category" labelKey="productQuestion.category" path="categoryCode" mandatory="true" skipBlank="false" skipBlankMessageKey="productQuestion.category" items="${questionCategories}" selectCSSClass="form-control"/>
			</div>
			<div class="form-group">
				<formElement:formTextArea idKey="productQuestion.question" labelKey="productQuestion.question" path="question" areaCSS="form-control" mandatory="true"/>
			</div>
			
			<div class="form-group">
				<formElement:formCheckbox idKey="productQuestion.hideUser" labelKey="productQuestion.hideUser" path="hideUser" inputCSS="form-control" mandatory="false"/>
				<formElement:formCheckbox idKey="productQuestion.onlyForUser" labelKey="productQuestion.onlyForUser" path="onlyForUser" inputCSS="form-control" mandatory="false"/>
			</div>

			<button type="submit" class="btn btn-primary" value="<spring:theme code="review.submit"/>"><spring:theme code="question.submit"/></button>
		</form:form>

	</div>
</sec:authorize>
    <c:if test="${not empty product.questions}">
        <c:forEach items="${product.questions}" var="question">
            </br></br>
            <c:out value="${question.question}"/>:
            <c:out value="${question.answer}"/>
        </c:forEach>
    </c:if>
</div>
