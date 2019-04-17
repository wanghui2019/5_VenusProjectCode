<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商品列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
            width: 100%;
        }
        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }
    </style>
</head>

<body>

        <!--分页 -->
        <div style="width:800px;margin:0 auto;margin-top:50px;">
            <ul class="pagination" style="text-align:center; margin-top:10px;">

                <li><a href="${pageContext.request.contextPath}/${cm.url}&num=1">首页</a></li>

                <li class="disabled"><a href="${pageContext.request.contextPath}/${cm.url}&num=${cm.prePageNum}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span></a></li>
                <c:forEach begin="${cm.startPage}" end="${cm.endPage}" var="pageNum">
                    <%--class="active"--%>
                    <li ><a href="${pageContext.request.contextPath}/${cm.url}&num=${pageNum}">${pageNum}</a></li>
                </c:forEach>

                <li>
                    <a href="${pageContext.request.contextPath}/${cm.url}&num=${cm.nextPageNum}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>

                <li><a href="${pageContext.request.contextPath}/${cm.url}&num=${cm.totalPageNum}">末页</a></li>
                <li style="float: left;line-height: 30px;">共${cm.totalPageNum}页/第${cm.curNum}页</li>
                <div style="float:left">
                    <%--点击跳转到第几页--%>
                    <input type="text" size="2" id="jumpPage">
                    <input type="button" value="前往" onclick="jump()">
                </div>
            </ul>

            <script>
                function jump() {
                    var totalPage=${cm.totalPageNum};
                    var page=document.getElementById("jumpPage").value;
                    //判断其输入的是数字
                    var reg=/^[1-9][0-9]{0,1}$/;
                    if (!reg.test(page)){
                        alert("请输入符合规定的数字")
                        return ;
                    }
                    //判断输入的页数不能大于总页数
                    else if (parseInt(page)>parseInt(totalPage)){
                        //超过了总页数
                        alert("没有那么多页")
                        return ;
                    }
                    else{
                        location.href="${pageContext.request.contextPath}/${cm.url}&num="+page;
                    }
                }
            </script>
        </div>
        <!-- 分页结束=======================        -->


</body>

</html>