<%--
  Created by IntelliJ IDEA.
  User: guangchao zhu
  Date: 2019-8-1
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}
${sessionScope}
</body>
</html>
