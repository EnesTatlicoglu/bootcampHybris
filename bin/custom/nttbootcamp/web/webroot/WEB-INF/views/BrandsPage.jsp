<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
  <title>Brands</title>
  <body>
    <h1>Brands</h1>
    <ul>
      <c:forEach var="brand" items="${brands}">
        <li><img src="${brand.logo}" style="height:3rem;"/> <a href="<c:url value="/brands/${brand.code}" />">${brand.name}</a></li>
      </c:forEach>
    </ul>
  </body>
</html>