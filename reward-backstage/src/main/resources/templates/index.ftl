<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<base href="admin-domain" />
<title>打赏系统</title>
<link rel="stylesheet" type="text/css"
	href="/static/web-libs/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/static/web-libs/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/static/css/common.css">
<script src="/static/web-libs/jquery-easyui/jquery-3.1.1.min.js"></script>
<script src="/static/web-libs/jquery-easyui/jquery.easyui.min.js"></script>
<script src="/static/web-libs/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<script src="/static/web-libs/higtCharts/highcharts.js"></script>
<script src="/static/web-libs/higtCharts/modules/export-data.js"></script>
<script src="/static/web-libs/higtCharts/modules/exporting.js"></script>
<script src="/static/web-libs/higtCharts/modules/series-label.js"></script>

<script src="/static/js/common.js"></script>
<script src="/static/js/DateUtils.js"></script>
<script src="/static/js/HttpUtils.js"></script>
<script src="/static/js/config.js"></script>
<script>
	$(document).ready(
			function() {
			// 布局自适应浏览器
			setLayoutHeight();
				$("div .easyui-accordion a[href!='javascript:void(0)']").click(
						ajaxLink);
				httpUtils.get("admin/query", function(err, response) {
					if (!err) {
						window.admin = response.admin;
					}
				});

				var msgParam = window.location.search;
				if (msgParam.length > 1) {
					msgParam = msgParam.substr(1); //取得所有参数
					var arr = msgParam.split("&"); //各个参数放到数组里

					var row = [];
					for (var i = 0; i < arr.length; i++) {
						var num = arr[i].indexOf("=");
						if (num > 0) {
							var name = arr[i].substring(0, num);
							var value = arr[i].substr(num + 1);
							row[name] = value;
						}
					}
					loadHTML("credit/loanTabs.html", null, null, row);
				}

			});

	function logout() {
		httpUtils.get("admin/logout", function(err) {
			if (!err) {
				window.location.href = "login.html";
			}
		})
	}
	function setLayoutHeight() {
        var height = $(window).height() - 30;
        $("#layout").attr("style", "width:100%;height:" + height + "px");
        $("#layout").layout("resize", {
            width: "100%",
            height: height + "px"
        });
    }

</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-layout" id="layout" style="width: 100%; height: 500px;" >
		<div data-options="region:'north'" style="height: 50px">
			<a href="javascript:logout()" class="easyui-linkbutton">退出登录</a>
		</div>
		<!--<div data-options="region:'south',split:true" style="height: 50px;">south</div> -->
		<div data-options="region:'west',split:true" title="菜单"
			style="width: 200px;">
			<div class="easyui-accordion" style="width: 100%;">
				<div title="系统管理" data-options="iconCls:'icon-man'"
					style="overflow: auto; padding: 10px;">
					<ul>
						<li><a href='credit/loanList.html?{"type":"todo"}'>账号管理</a></li>
						<li><a href='credit/loanList.html?{"type":"done"}'>角色管理</a></li>
						<li><a href='credit/loanList.html?{"type":"all"}'>权限管理</a></li>
						<!--<li><a href='credit/userLock.html'>用户锁定表</a></li>-->
					</ul>
				</div>
				<div title="审批中心" data-options="iconCls:'icon-lock'"
					style="overflow: auto; padding: 10px;">
					<ul>
						<li><a href="distribution/loanList.html?type=all">审核分配</a></li>
					</ul>
				</div>
				<div title="产品配置" data-options="iconCls:'icon-lock'"
					style="padding: 10px;">
					<ul>
						<li><a href="config/LoanConfig.html">融资配置</a></li>
						<!-- <li><a href="config/ProfitRate.html">利润率配置</a></li>-->
					</ul>
				</div>
				<div title="权限管理" data-options="iconCls:'icon-search'"
					style="padding: 10px;"></div>
				<div title="第三方数据测试" data-options="iconCls:'icon-search'"
					style="padding: 10px;">
					<ul>
						<li><a href="dataTest/dataTest.html">数据测试</a></li>
						<!-- <li><a href="dataTest/htmlTest.html">页面测试</a></li> -->
						<li><a href="dataTest/exchangeRate.html">汇率</a></li>
						<li><a href="dataTest/mnoData.html">运营商</a></li>
						<li><a href="dataTest/riskCode.html">风控代码映射</a></li>
					</ul>
				</div>
				<div title="合同模板更新" data-options="iconCls:'icon-search'"
					style="padding: 10px;">
					<ul>
						<li><a href="contract/templateProcess.html">合同模板更新</a></li>
					</ul>
				</div>
				<div title="银行信息" data-options="iconCls:'icon-search'"
					style="padding: 10px;">
					<!-- <ul>
						<li><a href="contract/bank.html">银行信息</a></li>
					</ul> -->
					<ul>
						<li><a href="contract/bankQuota.html">银行限额列表</a></li>
						<li><a href="contract/bankCardInvalid.html">失效银行卡列表</a></li>
					</ul>
				</div>
			</div>

		</div>
		<div id="load" data-options="region:'center',iconCls:'icon-ok'">


		</div>
	</div>


</body>
</html>