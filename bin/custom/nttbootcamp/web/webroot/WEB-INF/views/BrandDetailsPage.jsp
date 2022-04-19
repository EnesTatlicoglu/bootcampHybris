<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <title>Brand Details for ${brand.code}</title>
  <body>
    <h1>${brand.name} (${brand.code})</h1>
    <c:if test="${not empty brand.logo}">
        <img src="${brand.logo}" style="height:1rem;"/>
    </c:if>
    <c:if test="${not empty products}">
        <ul>
            <c:forEach var="product" items="${products}">
                <li><a href="<c:url value="/products/${product.code}"/>">${product.name}</a></li>
            </c:forEach>
        </ul>
    </c:if>
  </body>
</html>