<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>购物车</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			.container .row div {
				/* position:relative;
	 float:left; */
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
	</head>

	<body>


    <%@ include file="header.jsp"%>


	<c:if test="${empty cart.cartItems}">
		<h1 style="margin-left: 300px;">开启剁手模式</h1>
	</c:if>


	<c:if test="${not empty cart.cartItems}">
	<div class="container">
		<div class="row">

			<div style="margin:0 auto; margin-top:10px;width:950px;">
				<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
				<table class="table table-bordered">
					<tbody>
					<tr class="warning">
						<th>图片</th>
						<th>商品</th>
						<th>价格</th>
						<th>数量</th>
						<th>小计</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${cart.cartItems}" var="cartOne">
						<tr class="active">
							<td width="60" width="40%">
								<input type="hidden" name="id" value="22">
								<img src="${pageContext.request.contextPath}/${cartOne.product.pimage}" width="70" height="60">
							</td>
							<td width="30%">
								<a target="_blank">${cartOne.product.pname}</a>
							</td>
							<td width="20%">
								&yen;${cartOne.product.market_price}
							</td>
							<td width="10%">
								<input type="text" name="quantity" value="${cartOne.num}" data-num="${cartOne.product.pid}" maxlength="4" size="10" onblur="changeNum(this)">
							</td>
							<td width="15%">
								<span class="subtotal">&yen;${cartOne.productprice}</span>
							</td>
							<td>
								<a href="javascript:;" class="delete" onclick="removeProduct(this)" data-pid="${cartOne.product.pid}">删除</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div style="margin-right:130px;">
			<div style="text-align:right;">
				<em style="color:#ff6600;">
					登录后确认是否享有优惠&nbsp;&nbsp;
				</em> 赠送积分: <em style="color:#ff6600;">596</em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥${cart.productsPrice}元</strong>
			</div>
			<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
				<a href="javascript:;" id="clear" class="clear" onclick="clearProduct()">清空购物车</a>
				<a href="${pageContext.request.contextPath}/OrderServlet?method=addOrder">
						<%--提交表单 --%>
					<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
							height:35px;width:100px;color:white;">
				</a>
			</div>
		</div>

	</div>
	</c:if>





		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/img/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a href="${pageContext.request.contextPath}/jsp/info.jsp">关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>

	</body>
	<script>
		function removeProduct(remove) {
			if (confirm("确认移除商品？")){
				var id=remove.getAttribute("data-pid");
				window.location.href="${pageContext.request.contextPath}/CartServlet?method=removeProduct&id="+id;
			}
		}

		function clearProduct() {
			if (confirm("确认清空购物车？")){
				window.location.href="${pageContext.request.contextPath}/CartServlet?method=clearProduct";
			}
		}
		function changeNum(chan) {
			var num=chan.value.trim();
			var pid = chan.getAttribute("data-num");
			if (num>=1&&num<=9){
				window.location.href="${pageContext.request.contextPath}/CartServlet?method=changeProductNum&pid="+pid+"&num="+num;
			}else if(num<1){
				alert("最少为一件哦！");
				return ;
			}else if(num>9){
				alert("每人最多能买9件哦！");
				return ;
			}else {
				alert("请输入有效数字");
			}
		}
	</script>

</html>