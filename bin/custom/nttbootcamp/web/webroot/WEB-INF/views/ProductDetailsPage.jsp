<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <title>Product Details for ${product.code}</title>
  <body>
    <h1>${product.name} (${product.code})</h1>
    <c:if test="${not empty product.badges}">
      <ul>
        <c:forEach var="badge" items="${product.badges}">
          <li>${badge.title} - ${badge.description}</li>
        </c:forEach>
      </ul>
    </c:if>
  </body>
</html>