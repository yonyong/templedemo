<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ page import="cn.yonyong.usetk.utils.*,java.util.*,cn.yonyong.usetk.pojo.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>消息</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<%
    long number= new Double((Math.random() + 1) * Math.pow(6, 7)).longValue();
%>

<div class="row">
    <div class="col-md-2"></div>
    <div class="col-md-7">
        <div class="table-responsive" align="center">
            <br/>  <br/>  <br/>
            <h3 align="center">消息列表 </h3>
            <table class="table" align="center">
                <c:forEach items="${requestScope.get('list')}" var="temp">
                    <tr>
                        <td>${temp.title}</td>
                        <td>${temp.date}</td>
                        <td><a href="/tologin">编辑</a></td>
                        <td> <a href="/tologin">删除</a><td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <input type="button" class="btn btn-primary btn-lg btn-block" value="添加"
               onclick="location.href='/tologin'">

    </div>

    <div class="col-md-1">
        <div class="form-group">
            当前账户：游客<%=number %>
        </div>
        <div class="form-group">
            <p class="help-block"><a href="/tologin">点我下线</a></p>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>