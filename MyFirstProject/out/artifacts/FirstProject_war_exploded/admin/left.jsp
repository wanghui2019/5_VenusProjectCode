<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单</title>
<link href="${pageContext.request.contextPath}/css/left.css" rel="stylesheet" type="text/css"/>
<link rel="StyleSheet" href="${pageContext.request.contextPath}/css/dtree.css" type="text/css" />
</head>
<body>
<table width="100" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="100%" border="0">
  <tr>
    <td>
<div class="dtree">

	<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
	<script type="text/javascript">


	
		d = new dTree('d');
        //-1表示是树的最顶层，01是给这个顶层起的编号，系统菜单树是叫的名字
        d.add('01',-1,'系统菜单树');
		//0102是新起的分类编号，01表示0102是01的子树，分类管理是名称
        d.add('0102','01','分类管理','','','mainFrame');
        //010201是新起的分类编号，0102是新起的分类编号的父树，分类管理是名称，
        <%--'${pageContext.request.contextPath}/AdminCategoryServlet?method=showCategory' 指点了分类管理后要跳转的路径地址--%>
        //后面的''里写的是一些提示信息，当鼠标放到分类管理名称上时，会显示一个提示信息
        //'mainFrame'指路径显示在哪个框架里，这个在框架里有个name="mainFrame"
		d.add('010201','0102','分类管理','${pageContext.request.contextPath}/AdminCategoryServlet?method=showCategory','','mainFrame');
		d.add('0104','01','商品管理');
		d.add('010401','0104','商品管理','${pageContext.request.contextPath}/AdminProductServlet?method=showProduct&num=1','','mainFrame');
		d.add('010402','0104','已下架商品管理','${pageContext.request.contextPath}/admin/product/pushDown_list.jsp','','mainFrame');
		d.add('0105','01','订单管理');
		d.add('010501','0105','订单管理','${pageContext.request.contextPath}/AdminOrderServlet?method=showOrder&state=0&num=1','','mainFrame');
		d.add('010502','0105','未付款的订单','${pageContext.request.contextPath}/AdminOrderServlet?method=showOrder&state=1&num=1','','mainFrame');
		d.add('010503','0105','已付款订单','${pageContext.request.contextPath}/AdminOrderServlet?method=showOrder&state=2&num=1','','mainFrame');
		d.add('010504','0105','已发货的订单','${pageContext.request.contextPath}/AdminOrderServlet?method=showOrder&state=3&num=1','','mainFrame');
		d.add('010505','0105','已完成的订单','${pageContext.request.contextPath}/AdminOrderServlet?method=showOrder&state=4&num=1','','mainFrame');
		document.write(d);
		
	</script>
</div>	</td>
  </tr>
</table>
</body>
</html>
