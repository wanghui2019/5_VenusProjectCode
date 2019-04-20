<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/productdetail.css">
        <style>
            .orderDiv{
                width: 1000px;
                height: 1000px;
            }
        </style>
        <script>
            $(function () {
                $(".bottonorder").click(function(){
                    var bottonvalue=this.value;
                    var oid=this.getAttribute("data-value");

                    if (bottonvalue=="订单详情"){
                        this.value="关闭";
                        document.getElementById("upid").style.display="block";      //关闭

                    } else{
                        this.value="订单详情";
                        document.getElementById("upid").style.display="none";
                    }


                    $("#upid2").html("");

                    $.post("${pageContext.request.contextPath}/AdminOrderServlet",
                        {
                            "method":"findProductDetail",
                            "oid":oid
                        },
                        function(data,status){
                            //对响应来的数据进行遍历
                            $.each(data,function(index,obj){
                                var ul0=$("<ul class='headerul2'></ul>");

                                var li1="<li class='bodyli headerli1 bodyli1'><img src='${pageContext.request.contextPath}/"+obj.product.pimage+"' alt=''></li>";
                                var li2="<li class='bodyli headerli2'>"+"<span>"+obj.product.pname+"</span>"+"</li>";
                                var li3="<li class='bodyli headerli3'>"+"¥"+obj.product.market_price+"</li>";
                                var li4="<li class='bodyli headerli4'>"+obj.quantity+"</li>";
                                var li5="<li class='bodyli headerli5'>"+"¥"+obj.total+"</li>";
                                $("#upid2").append(ul0);

                                ul0.append(li1);
                                ul0.append(li2);
                                ul0.append(li3);
                                ul0.append(li4);
                                ul0.append(li5);

                            });
                        },"json");
                });
            });



        </script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										订单编号
									</td>
									<td align="center" width="10%">
										订单金额
									</td>
									<td align="center" width="10%">
										收货人
									</td>
									<td align="center" width="10%">
										订单状态
									</td>
									<td align="center" width="50%">
										订单详情
									</td>
								</tr>
                                <c:forEach items="${cm.list}" var="order" varStatus="status">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="18%">
												${status.count}
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${order.oid}
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${order.totalPrice}
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
                                                ${order.address}
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
                                                <c:if test="${order.state==1}">未付款</c:if>
                                                <c:if test="${order.state==2}">
                                                    <a href="AdminOrderServlet?method=changeOrderState&oid=${order.oid}&num=${cm.curNum}" style="color: blue">付款</a>
                                                </c:if>
                                                <c:if test="${order.state==3}">已发货</c:if>
                                                <c:if test="${order.state==4}">订单完成</c:if>
											</td>
											<td align="center" style="HEIGHT: 22px">
												<input type="button" value="订单详情"  class="bottonorder" data-value="${order.oid}"/>
											</td>
										</tr>
                                </c:forEach>
							</table>



						</td>
					</tr>

					<tr align="center">
						<td colspan="7">
							
						</td>
					</tr>
				</TBODY>


			</table>
		</form>
        <%@ include file="../../jsp/page.jsp"%>

        <%--要显示商品具体情况的表格--%>
        <div class="updiv"  id="upid">

            <div class="updiv1"  >
                <ul class="headerul1">
                    <li class="headerli1">图片</li>
                    <li class="headerli2">商品</li>
                    <li class="headerli3">价格</li>
                    <li class="headerli4">数量</li>
                    <li class="headerli5">小计</li>
                </ul>
            </div>

            <div class="updiv2"  id="upid2">

            </div>
        </div>



	</body>

</HTML>

