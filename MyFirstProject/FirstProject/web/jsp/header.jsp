<%--
  Created by IntelliJ IDEA.
  User: wanghui
  Date: 2019-04-10
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>header</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<!--
            	描述：菜单栏
            -->
<div class="container-fluid">
    <div class="col-md-4">
        <img src="${pageContext.request.contextPath}/img/logo2.png" />
    </div>
    <div class="col-md-5">
        <img src="${pageContext.request.contextPath}/img/header.png" />
    </div>
    <div class="col-md-3" style="padding-top:20px">
        <ol class="list-inline">
            <c:if test="${empty loginUser}">
                <li><a href="${pageContext.request.contextPath}/jsp/login.jsp">登录</a></li>
                <li><a href="${pageContext.request.contextPath}/jsp/register.jsp">注册</a></li>
            </c:if>
            <c:if test="${not empty loginUser}">
                <li>欢迎${loginUser.username}</li>
                <li><a href="${pageContext.request.contextPath}/RegisterServlet?method=layout">退出</a></li>
                <li><a href="${pageContext.request.contextPath}/jsp/cart.jsp">购物车</a></li>
                <li><a href="${pageContext.request.contextPath}/OrderServlet?method=MyOrder&num=1">我的订单</a></li>
            </c:if>
        </ol>
    </div>
</div>
<!--
    描述：导航条
-->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/ProductServlet">首页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="myHeader">
                    <%--<c:forEach items="${allCats}" var="c">--%>
                    <%--<li class="active"><a href="${pageContext.request.contextPath}/jsp/product_list.jsp">${c.cname}<span class="sr-only">(current)</span></a></li>--%>
                    <%--</c:forEach>--%>

                    <%--导航栏--%>
                </ul>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>


</body>
<script>

<%--// 通过ajax异步请求--%>
$(function(){
    //请求路径，请求方法，响应数据
    $.post("${pageContext.request.contextPath}/CategoryServlet",{"method":"findCats"},function(data,status){
        //data是json里的所有数据
        //拿到响应的数据，然后把数据给展示出来
        $.each(data,function(index,obj){
            var li="<li><a href='${pageContext.request.contextPath}/ProductServlet?method=findProduct&num=1&cid="+obj.cid+"'>"+obj.cname+"</a></li>"
            $("#myHeader").append(li);
        });

    },"json");
});


</script>
</html>
