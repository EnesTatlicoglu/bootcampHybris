<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <title>Products</title>
  <body>
    <h1>Products</h1>
    <ul>
      <c:forEach var="product" items="${products}">
        <li><a href="<c:url value="/products/${product.code}" />">${product.name}</a></li>
      </c:forEach>
    </ul>
  </body>
</html>