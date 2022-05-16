<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="multi-checkout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<template:page pageTitle="${pageTitle}" hideHeaderLinks="true">

<div class="row">
    <div class="col-sm-6">
        <div class="checkout-headline">
            <span class="glyphicon glyphicon-lock"></span>
            <spring:theme code="checkout.multi.secure.checkout" />
        </div>
		<multi-checkout:checkoutSteps checkoutSteps="${checkoutSteps}" progressBarId="${progressBarId}">
			<jsp:body>
				<ycommerce:testId code="checkoutStepThree">
					<div class="checkout-customer-note">
						<spring:htmlEscape defaultHtmlEscape="true" />
                        <c:set var="containerClass">
                            <c:choose>
                                <c:when test="${not empty cnErrorMsg}">has-error</c:when>
                                <c:when test="${not empty cnSuccessMsg}">has-success</c:when>
                                <c:otherwise></c:otherwise>
                            </c:choose>
                        </c:set>
                        <spring:url value="/checkout/multi/customer-note/add" var="updateCustomerNoteAction" htmlEscape="false"/>
                        <spring:url value="/checkout/multi/customer-note/remove" var="removeCustomerNoteAction" htmlEscape="false"/>
                        <spring:url value="/checkout/multi/payment-method/add" var="nextStepUrl" htmlEscape="false"/>
                        <c:set value="${cartData.customerNote}" var="customerNoteEncoded"/>
                        <div class="${containerClass}">
                            <form:form id="updateCustomerNoteForm" action="${updateCustomerNoteAction}" method="post" modelAttribute="customerNoteForm">
                                <form:input cssClass="" name="customer-note"
                                            id="" placeholder="${voucherInputPlaceholder}" value="${customerNoteEncoded}"
                                            path="customerNote"/>
                                <button type="submit" id="js-customer-note-update-btn" class="btn btn-primary btn-small">
                                                <spring:theme code="text.customernote.update.button.label"/></button>
                            </form:form>

                            <form:form id="removeCustomerNoteForm" action="${removeCustomerNoteAction}" method="post" modelAttribute="customerNoteForm">
                                    <button type="submit" id="js-customer-note-update-btn" class="btn btn-secondary btn-small">
                                                    <spring:theme code="text.customernote.remove.button.label"/></button>
                                </form:form>
                            <div class="js-customernote-validation-container help-block cart-customernote__help-block">
                                    ${fn:escapeXml(cnErrorMsg)}
                                    ${fn:escapeXml(cnSuccessMsg)}
                                </div>
                        </div>
					</div>
					<button id="customerMethodNext" type="button" class="btn btn-primary btn-block checkout-next" ><spring:theme code="checkout.multi.deliveryMethod.continue"/></button>
				</ycommerce:testId>
			</jsp:body>
		</multi-checkout:checkoutSteps>
    </div>

    <div class="col-sm-6 hidden-xs">
		<multi-checkout:checkoutOrderDetails cartData="${cartData}" showDeliveryAddress="true" showPaymentInfo="false" showTaxEstimate="false" showTax="true" />
    </div>

    <div class="col-sm-12 col-lg-12">
        <cms:pageSlot position="SideContent" var="feature" element="div" class="checkout-help">
            <cms:component component="${feature}"/>
        </cms:pageSlot>
    </div>
</div>

</template:page>