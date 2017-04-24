<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome  </title>
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <!-- 表格插件 -->
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript">
    
    var data = [
                {
                    "name": "自然人",
                    "style": "投",
                    "charge": "1533.00",
                    "payCharge": "822.00",
                    "remainCharge": "711.00",
                    "win": "-92080.00",
                    "dealVolume": "237",
                    "closeToday": "33",
                    "dealAmount": "5517560.00",
                    "closeWin": "-30580.00",
                    "positionWin": "-61500.00",
                    "netMargin": "-93613.00"
                },
                {
                	"name": "自然人",
                    "style": "投",
                    "charge": "1533.00",
                    "payCharge": "822.00",
                    "remainCharge": "711.00",
                    "win": "-92080.00",
                    "dealVolume": "237",
                    "closeToday": "33",
                    "dealAmount": "5517560.00",
                    "closeWin": "-30580.00",
                    "positionWin": "-61500.00",
                    "netMargin": "-93613.00"
                },
                {
                	"name": "自然人",
                    "style": "投",
                    "charge": "1533.00",
                    "payCharge": "822.00",
                    "remainCharge": "711.00",
                    "win": "-92080.00",
                    "dealVolume": "237",
                    "closeToday": "33",
                    "dealAmount": "5517560.00",
                    "closeWin": "-30580.00",
                    "positionWin": "-61500.00",
                    "netMargin": "-93613.00"
                },
                {
                	"name": "自然人",
                    "style": "投",
                    "charge": "1533.00",
                    "payCharge": "822.00",
                    "remainCharge": "711.00",
                    "win": "-92080.00",
                    "dealVolume": "237",
                    "closeToday": "33",
                    "dealAmount": "5517560.00",
                    "closeWin": "-30580.00",
                    "positionWin": "-61500.00",
                    "netMargin": "-93613.00"
                },
                {
                	"name": "自然人",
                    "style": "投",
                    "charge": "1533.00",
                    "payCharge": "822.00",
                    "remainCharge": "711.00",
                    "win": "-92080.00",
                    "dealVolume": "237",
                    "closeToday": "33",
                    "dealAmount": "5517560.00",
                    "closeWin": "-30580.00",
                    "positionWin": "-61500.00",
                    "netMargin": "-93613.00"
                }
            ];

        $(function(){
        	$('#table').bootstrapTable({
                data: data
            });
        });
    </script>
  </head>

  <body>
  <div>
    <h1 align="center">交易统计表</h1>
    
    <div class="table-responsive">
	    <table class="table table-striped table-bordered" id="table" style="width: 80%;" align="center">
	        <thead>
	            <tr>
	                <th data-field="name" data-align="center">投资者类型</th>
	                <th data-field="style" data-align="center">投保</th>
	                <th data-field="charge" data-align="center">手续费</th>
	                <th data-field="payCharge" data-align="center">上交手续费</th>
	                <th data-field="remainCharge" data-align="center">留存手续费</th>
	                <th data-field="win" data-align="center">盈亏</th>
	                <th data-field="dealVolume" data-align="center">总成交量</th>
	                <th data-field="closeToday" data-align="center">平今量</th>
	                <th data-field="dealAmount" data-align="center">成交额</th>
	                <th data-field="closeWin" data-align="center">平仓盈亏</th>
	                <th data-field="positionWin" data-align="center">持仓盈亏</th>
	                <th data-field="netMargin" data-align="center">净利润</th>
	            </tr>
	        </thead>
	    </table>
    </div>
  </div>
  </body>
</html>
