<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome  </title>
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    
    <!-- 文件上传 -->
    <link href="resources/fileInput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
    <!-- canvas-to-blob.min.js is only needed if you wish to resize images before upload.
     This must be loaded before fileinput.min.js -->
    <script src="resources/fileInput/js/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
    <!-- sortable.min.js is only needed if you wish to sort / rearrange files in initial preview.
         This must be loaded before fileinput.min.js -->
    <script src="resources/fileInput/js/plugins/sortable.min.js" type="text/javascript"></script>
    <!-- purify.min.js is only needed if you wish to purify HTML content in your preview for HTML files.
         This must be loaded before fileinput.min.js -->
    <script src="resources/fileInput/js/plugins/purify.min.js" type="text/javascript"></script>
    <!-- the main fileinput plugin file -->
    <script src="resources/fileInput/js/fileinput.min.js"></script>
    
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">
    
    <!-- 菜单树 -->
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <!-- table插件 -->
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <!-- 文件上传插件fileInput -->
    <script src="resources/fileInput/themes/fa/theme.js"></script>
    <!-- optionally if you need translation for your language then include 
        locale file as mentioned below -->
    <script src="resources/fileInput/js/locales/zh.js"></script>
    
    <!-- jqprint -->
    <script src="resources/jqprint/jquery.jqprint-0.3.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.1.0.js"></script>
    
    
    <script type="text/javascript">
        function operationFormatter(value,row,index) {
            var html = '<button type="button" id="cog'+index+'" class="btn btn-default btn-sm" title="设置">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button>'
                     + '<button type="button" id="trash'+index+'" class="btn btn-default btn-sm" title="删除任务">'
                         + '<i class="glyphicon glyphicon-trash"></i>'
                     + '</button>';
                     
            return html;
        }
        
        //加载页面后自动初始化
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toInvestorReport"]').parent().addClass("active");
            
            $("#customerForm").keypress(function(e){
                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                if (eCode == 13){
                    //自己写判断函数
                    queryInvestorProfit();
                }
            });
            
            $("#mediatorForm").keypress(function(e){
                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                if (eCode == 13){
                    //自己写判断函数
                    queryMediatorProfit();
                }
            });
            
            $("#marketerForm").keypress(function(e){
                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                if (eCode == 13){
                    //自己写判断函数
                    queryMarketerProfit();
                }
            });
            
        });
        
        /* 查询投资者产生的利润 */
        function queryInvestorProfit(){
            
        	//删除打印内容
        	$('#investorNameCell').html('');
            $('#investorNoCell').html('');
            $('#depNameCell').html('');
            $('#INVESTOR_MARKETER_NAME').html('');
            $('#INVESTOR_MEDIATOR_NAME').html('');
        	$('#NET_PROFIT_SUM').html('');
             $('#LCSXF_SUM').html('');
             $('#SXFJSR_SUM').html('');
             $('#INTEREST_SUM').html('');
             $('#NET_INTEREST_SUM').html('');
             $('#EXCHANGE_RETURN_SUM').html('');
             $('#NET_AMOUNT_SUM').html('');
             for (var index = 0; index < 3; index++) {
            	 $('#MONTH'+index).html('');
                 $('#NET_PROFIT'+index).html('');
                 $('#LCSXF'+index).html('');
                 $('#SXFJSR'+index).html('');
                 $('#INTEREST'+index).html('');
                 $('#NET_INTEREST'+index).html('');
                 $('#EXCHANGE_RETURN'+index).html('');
                 $('#NET_AMOUNT'+index).html('');
			}
             
            if(isNull($('#investorName').val()) && isNull($("#investorNo").val())){
                alert("请输入投资者代码或投资者名称");
                return;
            }
            
            $("#investorProfit").bootstrapTable(
                    'refresh',{url:"queryInvestorProfit",
                               query: {investorName:$('#investorName').val(),
                                       investorNo:$("#investorNo").val()
                                      }
                              }
                );
            
            var param = {investorNo:$('#investorNo').val(),
                         investorName:$('#investorName').val()}
            
            $.ajax({
                url: 'queryInvestorOverview',
                type: 'post',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(param),
                success: function (data) {
                    $('#invetorOverView').empty();
                    if(isNull(data.investorNo)){
                        return;
                    }
                    $('#invetorOverView').append("简介：投资者名称："+data.investorName+", &nbsp&nbsp 投资者代码："+data.investorNo+", &nbsp&nbsp 开户日期："+data.openDate+"<br />"
                            +"居间人信息：居间人名称："+data.mediatorName+", &nbsp&nbsp 居间人编号："+data.mediatorNo+"&nbsp&nbsp(近一个月手续费比例："+ data.chargeRate +",&nbsp&nbsp利息比例："+ data.interestRate+",&nbsp&nbsp交返比例："+ data.exchangeRate+")<br />"
                            +"营销人员信息：营销人员名称："+data.marketerName+", &nbsp&nbsp 营销人员编号："+data.marketerNo+"&nbsp&nbsp(近一个月提成比例："+data.marketerRate+")<br />"
                            +"属于营业部："+data.depName)
                            
                    $('#investorNameCell').html(data.investorName);
                    $('#investorNoCell').html(data.investorNo);
                    $('#depNameCell').html(data.depName);
                    $('#INVESTOR_MARKETER_NAME').html(data.marketerName);
                    $('#INVESTOR_MEDIATOR_NAME').html(data.mediatorName);
                }
            });
            
        }
        
        /* 查询居间人产生的利润 */
        function queryMediatorProfit(){
        	
        	//删除打印内容
            $('#mediatorNameCell').html('');
            $('#mediatorNoCell').html('');
            $('#customerCountCell').html('');
            $('#depNameCell1').html('');
            $('#expireDateCell').html('');
            $('#chargeRateCell').html('');
            $('#interestRateCell').html('');
            $('#exchangeRateCell').html('');
            $('#marketerNameCell').html('');
            $('#MEDIATOR_NET_PROFIT_SUM').html('');
            $('#MEDIATOR_SXFJSR_SUM').html('');
            $('#MEDIATOR_NET_SXFJSR_SUM').html('');
            $('#MEDIATOR_INTEREST_SUM').html('');
            $('#MEDIATOR_NET_INTEREST_SUM').html('');
            $('#MEDIATOR_EXCHANGE_RETURN_SUM').html('');
            $('#MEDIATOR_NET_AMOUNT_SUM').html('');
            for (var index = 0; index < 3; index++) {
            	$('#MEDIATOR_MONTH'+index).html('');
                $('#MEDIATOR_NET_PROFIT'+index).html('');
                $('#MEDIATOR_SXFJSR'+index).html('');
                $('#MEDIATOR_NET_SXFJSR'+index).html('');
                $('#MEDIATOR_INTEREST'+index).html('');
                $('#MEDIATOR_NET_INTEREST'+index).html('');
                $('#MEDIATOR_EXCHANGE_RETURN'+index).html('');
                $('#MEDIATOR_NET_AMOUNT'+index).html('');
			}
            
            if(isNull($('#mediatorNo').val()) && isNull($("#mediatorName").val())){
                alert("请输入投资者代码或投资者名称");
                return;
            }
            
            $("#mediatorProfit").bootstrapTable(
                    'refresh',{url:"queryMediatorProfit",
                               query: {mediatorNo:$('#mediatorNo').val(),
                                       mediatorName:$("#mediatorName").val()
                                      }
                              }
                );
            
            
            var param = {mediatorNo:$('#mediatorNo').val(),
                    mediatorName:$('#mediatorName').val()}
       
           $.ajax({
               url: 'queryMediatorOverview',
               type: 'post',
               dataType: 'json',
               contentType: "application/json;charset=UTF-8",
               data: JSON.stringify(param),
               success: function (data) {
                   $('#mediatorOverview').empty();
                   if(isNull(data.mediatorNo)){
                       return;
                   }
                   $('#mediatorOverview').append("简介：居间人名称："+data.mediatorName+",&nbsp&nbsp居间人编号："+data.mediatorNo+",&nbsp&nbsp生效日期："+data.effectDate+", &nbsp&nbsp失效日期："+data.expireDate+",  电话："+data.phone+"<br />"
                           +"(近一个月手续费比例："+ data.chargeRate +",&nbsp&nbsp利息比例："+ data.interestRate+",&nbsp&nbsp交返比例："+ data.exchangeRate+")<br />"
                           +"客户数量："+data.customerCount+"<br />"
                           +"营销人员信息：营销人员名称："+data.marketerName+"&nbsp&nbsp,营销人员编号："+data.marketerNo+"&nbsp&nbsp(近一个月提成比例："+data.marketerRate+")<br />"
                           +"属于营业部："+data.depName)
                   
                   $('#mediatorNameCell').html(data.mediatorName);
                   $('#mediatorNoCell').html(data.mediatorNo);
                   $('#customerCountCell').html(data.customerCount);
                   $('#depNameCell1').html(data.depName);
                   $('#expireDateCell').html(data.expireDate);
                   $('#chargeRateCell').html(data.chargeRate);
                   $('#interestRateCell').html(data.interestRate);
                   $('#exchangeRateCell').html(data.exchangeRate);
                   $('#marketerNameCell').html(data.marketerName);
               }
           });
        }
        
        /* 查询营销人与贡献度 */
        function queryMarketerProfit(){
        	
        	//删除打印内容
            $('#MARKETER_MARKETER_NAME').html('');
            $('#MARKETER_DEP_NAME').html('');
            $('#MARKETER_CUSTOMER_COUNT').html('');
            $('#MARKETER_MEDIATOR_COUNT').html('');
            $('#MARKETER_RATE').html('');
            $('#MARKETER_NET_PROFIT_SUM').html('');
            $('#MARKETER_SXFJSR_SUM').html('');
            $('#MARKETER_NET_SXFJSR_SUM').html('');
            $('#MARKETER_INTEREST_SUM').html('');
            $('#MARKETER_NET_INTEREST_SUM').html('');
            $('#MARKETER_EXCHANGE_RETURN_SUM').html('');
            $('#MARKETER_NET_AMOUNT_SUM').html('');
            for (var index = 0; index < 3; index++) {
				
	            $('#MARKETER_MONTH'+index).html('');
	            $('#MARKETER_NET_PROFIT'+index).html('');
	            $('#MARKETER_SXFJSR'+index).html('');
	            $('#MARKETER_NET_SXFJSR'+index).html('');
	            $('#MARKETER_INTEREST'+index).html('');
	            $('#MARKETER_NET_INTEREST'+index).html('');
	            $('#MARKETER_EXCHANGE_RETURN'+index).html('');
	            $('#MARKETER_NET_AMOUNT'+index).html('');
			}
            
            
            if(isNull($('#marketerNo').val()) && isNull($("#marketerName").val())){
                alert("请输入投资者代码或投资者名称");
                return;
            }
            
            $("#marketerProfit").bootstrapTable(
                    'refresh',{url:"queryMarketerProfit",
                               query: {marketerNo:$('#marketerNo').val(),
                                       marketerName:$("#marketerName").val()
                                      }
                              }
                );
            
            var param = {marketerNo:$('#marketerNo').val(),
                    marketerName:$('#marketerName').val()}
       
           $.ajax({
               url: 'queryMarketerOverview',
               type: 'post',
               dataType: 'json',
               contentType: "application/json;charset=UTF-8",
               data: JSON.stringify(param),
               success: function (data) {
                   $('#marketerOverview').empty();
                   if(isNull(data.marketerNo)){
                       return;
                   }
                   $('#marketerOverview').append("简介：营销人员名称："+data.marketerName+",&nbsp&nbsp营销人员编号："+data.marketerNo+",&nbsp&nbsp 入职日期："+data.entryDate+",&nbsp&nbsp 电话："+data.phone+"<br />"
                           +"(近一个月提成比例："+data.marketerRate+")<br />"
                           +"居间人数量："+data.mediatorCount+"<br />"
                           +"直属客户数量："+data.directCustomerCount+"<br />"
                           +"属于营业部："+data.depName);
                           
                   $('#MARKETER_MARKETER_NAME').html(data.marketerName);
                   $('#MARKETER_DEP_NAME').html(data.depName);
                   $('#MARKETER_CUSTOMER_COUNT').html(data.directCustomerCount);
                   $('#MARKETER_MEDIATOR_COUNT').html(data.mediatorCount);
                   $('#MARKETER_RATE').html(data.marketerRate);
               }
            });
            
            
            
        }
        
        
        
        function queryInvestorProfitParams(params){
            
            return {investorName:$('#investorName').val(),
                investorNo:$("#investorNo").val(),
                pageNumber:params.pageNumber,
                pageSize:params.pageSize}
        }
        
        function isNull(value){
            if(value == "" || value == undefined || value == null){
                return true;
            }else{
                return false;
            }
        }
        
        function numberFormate(value,row,index) {
            //console.log(value);
            if(isNull(value)){
                return null;
            }
            return (value+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');//使用正则替换，每隔三个数加一个','  
        }
        
        //打印客户利润
        function printInvestorProfit(){
             //$("#investorPrintArea").jqprint();
             var allTableData = $("#investorProfit").bootstrapTable('getData');//获取表格的所有内容行  
             console.info(allTableData)
             
             
             var NET_PROFIT_SUM = 0;
             var LCSXF_SUM = 0;
             var SXFJSR_SUM = 0;
             var INTEREST_SUM = 0;
             var NET_INTEREST_SUM = 0;
             var EXCHANGE_RETURN_SUM = 0;
             var NET_AMOUNT_SUM = 0;
             
             $.each(allTableData, function(index, content){
                 
                 if(index > 2){
                     return;
                 }
                 
                 NET_PROFIT_SUM += content.NET_PROFIT;
                 LCSXF_SUM += parseFloat(content.LCSXF);
                 SXFJSR_SUM += parseFloat(content.SXFJSR);
                 INTEREST_SUM += content.INTEREST;
                 NET_INTEREST_SUM += content.NET_INTEREST;
                 EXCHANGE_RETURN_SUM += content.EXCHANGE_RETURN;
                 NET_AMOUNT_SUM += content.NET_AMOUNT;
                 
                 $('#MONTH'+index).html(content.MONTH);
                 $('#NET_PROFIT'+index).html(numberFormate(content.NET_PROFIT.toFixed(2)));
                 $('#LCSXF'+index).html(numberFormate(content.LCSXF));
                 $('#SXFJSR'+index).html(numberFormate(content.SXFJSR));
                 $('#INTEREST'+index).html(numberFormate(content.INTEREST.toFixed(2)));
                 $('#NET_INTEREST'+index).html(numberFormate(content.NET_INTEREST.toFixed(2)));
                 $('#EXCHANGE_RETURN'+index).html(numberFormate(content.EXCHANGE_RETURN.toFixed(2)));
                 $('#NET_AMOUNT'+index).html(numberFormate(content.NET_AMOUNT.toFixed(2)));
                 
             });
             //合计
             $('#NET_PROFIT_SUM').html(numberFormate(NET_PROFIT_SUM.toFixed(2)));
             $('#LCSXF_SUM').html(numberFormate(LCSXF_SUM.toFixed(2)));
             $('#SXFJSR_SUM').html(numberFormate(SXFJSR_SUM.toFixed(2)));
             $('#INTEREST_SUM').html(numberFormate(INTEREST_SUM.toFixed(2)));
             $('#NET_INTEREST_SUM').html(numberFormate(NET_INTEREST_SUM.toFixed(2)));
             $('#EXCHANGE_RETURN_SUM').html(numberFormate(EXCHANGE_RETURN_SUM.toFixed(2)));
             $('#NET_AMOUNT_SUM').html(numberFormate(NET_AMOUNT_SUM.toFixed(2)));
             
             $('#investorArea').css('display','block');
             $("#investorArea").jqprint();
             $('#investorArea').css('display','none');
        }
        
        //打印居间人利润
        function printMediatorProfit(){
        	
        	
        	
        	var NET_PROFIT_SUM = 0;
            var SXFJSR_SUM = 0;
            var DBL16_SUM = 0;
            var NET_SXF_SUM = 0;
            var INTEREST_SUM = 0;
            var NET_INTEREST_SUM = 0;
            var EXCHANGE_RETURN_SUM = 0;
            var NET_AMOUNT_SUM = 0;
            
        	var allTableData = $("#mediatorProfit").bootstrapTable('getData');//获取表格的所有内容行  
            console.info(allTableData)
            
            $.each(allTableData, function(index, content){
                
                if(index > 2){
                    return;
                }
                
                NET_PROFIT_SUM += content.NET_PROFIT;
                //手续费净收入
                SXFJSR_SUM += content.SXFJSR;
                NET_SXF_SUM += (content.SXFJSR - content.DBL16);
                INTEREST_SUM += content.INTEREST;
                NET_INTEREST_SUM += (content.INTEREST - content.INVESTOR_SPECIAL_INTEREST - content.MEDIATOR_SPECIAL_INTEREST);
                EXCHANGE_RETURN_SUM += content.EXCHANGE_RETURN;
                NET_AMOUNT_SUM += (content.EXCHANGE_RETURN - content.INVESTOR_SPECIAL_EXCHANGE - content.MEDIATOR_SPECIAL_EXCHANGE);
                
                
                $('#MEDIATOR_MONTH'+index).html(content.MONTH);
                $('#MEDIATOR_NET_PROFIT'+index).html(numberFormate(content.NET_PROFIT.toFixed(2)));
                $('#MEDIATOR_SXFJSR'+index).html(numberFormate(content.SXFJSR.toFixed(2)));
                $('#MEDIATOR_NET_SXFJSR'+index).html(numberFormate((content.SXFJSR - content.DBL16).toFixed(2)));
                $('#MEDIATOR_INTEREST'+index).html(numberFormate(content.INTEREST.toFixed(2)));
                $('#MEDIATOR_NET_INTEREST'+index).html(numberFormate((content.INTEREST - content.INVESTOR_SPECIAL_INTEREST - content.MEDIATOR_SPECIAL_INTEREST).toFixed(2)));
                $('#MEDIATOR_EXCHANGE_RETURN'+index).html(numberFormate(content.EXCHANGE_RETURN.toFixed(2)));
                $('#MEDIATOR_NET_AMOUNT'+index).html(numberFormate((content.EXCHANGE_RETURN - content.INVESTOR_SPECIAL_EXCHANGE - content.MEDIATOR_SPECIAL_EXCHANGE).toFixed(2)));
                
            });
        	
            //合计
            $('#MEDIATOR_NET_PROFIT_SUM').html(numberFormate(NET_PROFIT_SUM.toFixed(2)));
            $('#MEDIATOR_SXFJSR_SUM').html(numberFormate(SXFJSR_SUM.toFixed(2)));
            $('#MEDIATOR_NET_SXFJSR_SUM').html(numberFormate(NET_SXF_SUM.toFixed(2)));
            $('#MEDIATOR_INTEREST_SUM').html(numberFormate(INTEREST_SUM.toFixed(2)));
            $('#MEDIATOR_NET_INTEREST_SUM').html(numberFormate(NET_INTEREST_SUM.toFixed(2)));
            $('#MEDIATOR_EXCHANGE_RETURN_SUM').html(numberFormate(EXCHANGE_RETURN_SUM.toFixed(2)));
            $('#MEDIATOR_NET_AMOUNT_SUM').html(numberFormate(NET_AMOUNT_SUM.toFixed(2)));
            
            $('#mediatorArea').css('display','block');
            $('#mediatorArea').jqprint();
            $('#mediatorArea').css('display','none');
        }
        
        //打印营销人员利润
        function printMarketerProfit(){
        	
        	var allTableData = $("#marketerProfit").bootstrapTable('getData');//获取表格的所有内容行   
        	
        	var NET_PROFIT_SUM = 0;
            var SXFJSR_SUM = 0;
            var DBL16_SUM = 0;
            var NET_SXF_SUM = 0;
            var INTEREST_SUM = 0;
            var NET_INTEREST_SUM = 0;
            var EXCHANGE_RETURN_SUM = 0;
            var NET_AMOUNT_SUM = 0;
            
            $.each(allTableData, function(index, content){
                
                if(index > 2){
                    return;
                }
                
                NET_PROFIT_SUM += content.NET_PROFIT;
                //手续费净收入
                SXFJSR_SUM += content.SXFJSR;
                NET_SXF_SUM += (content.SXFJSR - content.DBL16);
                INTEREST_SUM += content.INTEREST;
                NET_INTEREST_SUM += (content.INTEREST - content.INVESTOR_SPECIAL_INTEREST - content.MEDIATOR_SPECIAL_INTEREST);
                EXCHANGE_RETURN_SUM += content.EXCHANGE_RETURN;
                NET_AMOUNT_SUM += (content.EXCHANGE_RETURN - content.INVESTOR_SPECIAL_EXCHANGE - content.MEDIATOR_SPECIAL_EXCHANGE);
                
                
                $('#MARKETER_MONTH'+index).html(content.MONTH);
                $('#MARKETER_NET_PROFIT'+index).html(numberFormate(content.NET_PROFIT.toFixed(2)));
                $('#MARKETER_SXFJSR'+index).html(numberFormate(content.SXFJSR.toFixed(2)));
                $('#MARKETER_NET_SXFJSR'+index).html(numberFormate((content.SXFJSR - content.DBL16).toFixed(2)));
                $('#MARKETER_INTEREST'+index).html(numberFormate(content.INTEREST.toFixed(2)));
                $('#MARKETER_NET_INTEREST'+index).html(numberFormate((content.INTEREST - content.INVESTOR_SPECIAL_INTEREST - content.MEDIATOR_SPECIAL_INTEREST).toFixed(2)));
                $('#MARKETER_EXCHANGE_RETURN'+index).html(numberFormate(content.EXCHANGE_RETURN.toFixed(2)));
                $('#MARKETER_NET_AMOUNT'+index).html(numberFormate((content.EXCHANGE_RETURN - content.INVESTOR_SPECIAL_EXCHANGE - content.MEDIATOR_SPECIAL_EXCHANGE).toFixed(2)));
                
            });
            
          //合计
            $('#MARKETER_NET_PROFIT_SUM').html(numberFormate(NET_PROFIT_SUM.toFixed(2)));
            $('#MARKETER_SXFJSR_SUM').html(numberFormate(SXFJSR_SUM.toFixed(2)));
            $('#MARKETER_NET_SXFJSR_SUM').html(numberFormate(NET_SXF_SUM.toFixed(2)));
            $('#MARKETER_INTEREST_SUM').html(numberFormate(INTEREST_SUM.toFixed(2)));
            $('#MARKETER_NET_INTEREST_SUM').html(numberFormate(NET_INTEREST_SUM.toFixed(2)));
            $('#MARKETER_EXCHANGE_RETURN_SUM').html(numberFormate(EXCHANGE_RETURN_SUM.toFixed(2)));
            $('#MARKETER_NET_AMOUNT_SUM').html(numberFormate(NET_AMOUNT_SUM.toFixed(2)));
            
            $('#marketerArea').css('display','block');
            $('#marketerArea').jqprint();
            $('#marketerArea').css('display','none');
        }
        
    </script>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">渤海期货</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            
            
            
            <li><a href="#">欢迎：${sessionScope.username}</a></li>
            <li><a href="logout">退出</a></li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div id="tree"></div>
            
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toBusinessReport" style="text-decoration: none;">统计报表</a> --> <a href="toInvestorReport" style="text-decoration: none;">利润查询</a></h4>
              
              <!-- <h2 class="sub-header">客户利润产出查询</h2> -->

            <!-- Nav tabs -->
              <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">客户利润产出查询</a></li>
                <li role="presentation"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">居间人利润产出查询</a></li>
                <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">营销人员利润产出查询</a></li>
              </ul>
              
              
              <!-- Tab panes -->
              <div class="tab-content">
                <!-- 客户带来利润查询 -->
                <div role="tabpanel" class="tab-pane fade  in active" id="messages">
                
                    <form class="form-horizontal" style="margin-top: 30px" id="customerForm">
                        <div class="form-group">
                        
                              <!-- <label for="reportMonth1" class="col-sm-2 col-md-1 col-md-offset-1 control-label">统计年月</label>
                              <div class="col-sm-10 col-md-2">
                                <input type="text" class="form-control" id="reportMonth1">
                              </div> -->
                              
                              <label for="investorNo" class="col-sm-2 col-md-1 control-label">投资者代码</label>
                              <div class="col-sm-10 col-md-2">
                                <input type="text" class="form-control" id="investorNo">
                              </div>
                              
                              <label for="investorName" class="col-sm-2 col-md-1 control-label">投资者名称</label>
                              <div class="col-sm-10 col-md-2">
                                <input type="text" class="form-control" id="investorName">
                              </div>
                              
                              <div class="col-sm-10 col-md-2 col-md-offset-1 ">
                                  <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryInvestorProfit()">
                              </div>
                              
                              <div class="col-sm-10 col-md-2 ">
                                  <input class="btn btn-default col-xs-7" type="button" value="打印" onclick="printInvestorProfit()">
                              </div>
                        </div>
                          
                    </form>
              
                    
                    <div class="table-responsive" id="investorPrintArea">
                      <div id="toolbar" class="btn-group">
                        <div id="toolbar" class="btn-group">
                            <span id="invetorOverView"></span>
                        </div>
                      </div>
                      <table id="investorProfit"
                             class="table table-striped"
                             data-toggle="table"
                             data-toolbar="#toolbar"
                             data-show-refresh="true"
                             data-show-toggle="true"
                             data-show-columns="true"
                             data-show-export="true"
                             data-detail-view="false"
                             data-height="542"
                             data-pagination="true"
                             data-method="post"
                             data-page-list="[5, 10, 20, 50]"
                             data-search="true"
                             data-height="300">
                          <thead>
                          <tr>
                              <!-- <th data-field="state" data-checkbox="true"></th> -->
                              
                              <th data-field="MONTH" data-align="center" >月份</th>
                              <th data-field="INVESTOR_NO" data-align="center" >投资者代码</th>
                              <th data-field="INVESTOR_NAME" data-align="center" >投资者名称</th>
                              <th data-field="LCSXF" data-align="center" data-formatter="numberFormate">留存手续费</th>
                              <th data-field="INTEREST" data-align="center" data-formatter="numberFormate">利息</th>
                              <th data-field="EXCHANGE_RETURN" data-align="center" data-formatter="numberFormate">交返</th>
                              <th data-field="SPECIAL_INTEREST" data-align="center" data-formatter="numberFormate">客户拿走利息</th>
                              <th data-field="AMOUNT" data-align="center" data-formatter="numberFormate">客户拿走交返</th>
                              <th data-field="SXFJSR" data-align="center" data-formatter="numberFormate">手续费净收入</th>
                              <th data-field="NET_INTEREST" data-align="center" data-formatter="numberFormate">净利息</th>
                              <th data-field="NET_AMOUNT" data-align="center" data-formatter="numberFormate">净交返</th>
                              <th data-field="NET_PROFIT" data-align="center" data-formatter="numberFormate">净利润</th>
                          </tr>
                          </thead>
                      </table>
                    </div>
                    
                    <div id="investorArea"  class="form-group" style="display: none;">
                        <table style="width: 210mm;" border="1px">
                            <tr>
                                <td class="col-sm-2 col-md-3">投资者名称</td>
                                <td class="col-sm-2 col-md-3" id="investorNameCell">投资者名称</td>
                                <td class="col-sm-2 col-md-3">投资者编号</td>
                                <td class="col-sm-2 col-md-3" id="investorNoCell">投资者编号</td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3"> 营业部 </td>
                                <td class="col-sm-2 col-md-3" id="depNameCell"> </td>
                                <td> </td>
                                <td> </td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">营销人员</td>
                                <td class="col-sm-2 col-md-3" id="INVESTOR_MARKETER_NAME"></td>
                                <td class="col-sm-2 col-md-3">居间人</td>
                                <td class="col-sm-2 col-md-3" id="INVESTOR_MEDIATOR_NAME"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" colspan="4" align="center">前三月留存利润</td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MONTH0"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="NET_PROFIT0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存手续费</td>
                                <td class="col-sm-2 col-md-3" id="LCSXF0"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="SXFJSR0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="INTEREST0"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="NET_INTEREST0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="EXCHANGE_RETURN0"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="NET_AMOUNT0"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MONTH1"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="NET_PROFIT1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存手续费</td>
                                <td class="col-sm-2 col-md-3" id="LCSXF1"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="SXFJSR1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="INTEREST1"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="NET_INTEREST1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="EXCHANGE_RETURN1"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="NET_AMOUNT1"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MONTH2"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="NET_PROFIT2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存手续费</td>
                                <td class="col-sm-2 col-md-3" id="LCSXF2"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="SXFJSR2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="INTEREST2"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="NET_INTEREST2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="EXCHANGE_RETURN2"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="NET_AMOUNT2"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">合计</td>
                                <td class="col-sm-2 col-md-3"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="NET_PROFIT_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存手续费</td>
                                <td class="col-sm-2 col-md-3" id="LCSXF_SUM"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="SXFJSR_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="INTEREST_SUM"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="NET_INTEREST_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="EXCHANGE_RETURN_SUM"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="NET_AMOUNT_SUM"></td>
                            </tr>
                        </table>
                    </div>
              </div>
              
              <!-- 居间人带来利润查询 -->
              <div role="tabpanel" class="tab-pane fade  in " id="home">
                
                  <form class="form-horizontal" style="margin-top: 30px" id="mediatorForm">
                      <div class="form-group">
                      
                            <!-- <label for="reportMonth1" class="col-sm-2 col-md-1 col-md-offset-1 control-label">统计年月</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="reportMonth1">
                            </div> -->
                            
                            <label for="mediatorNo" class="col-sm-2 col-md-1 control-label">居间人编号</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="mediatorNo">
                            </div>
                            
                            <label for="mediatorName" class="col-sm-2 col-md-1 control-label">居间人名称</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="mediatorName">
                            </div>
                            
                            <div class="col-sm-10 col-md-2 col-md-offset-1 ">
                                <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryMediatorProfit()">
                            </div>
                            <div class="col-sm-10 col-md-2 ">
                                <input class="btn btn-default col-xs-7" type="button" value="打印" onclick="printMediatorProfit()">
                            </div>
                      </div>
                        
                  </form>
              
                  <div class="table-responsive">
                    <div id="toolbar" class="btn-group">
                      <div id="toolbar1" class="btn-group">
                        <span id="mediatorOverview"></span>
                      </div>
                    </div>
                    <table id="mediatorProfit"
                           class="table table-striped"
                           data-toggle="table"
                           data-toolbar="#toolbar1"
                           data-show-refresh="true"
                           data-show-toggle="true"
                           data-show-columns="true"
                           data-show-export="true"
                           data-detail-view="false"
                           data-height="542"
                           data-pagination="true"
                           data-method="post"
                           data-page-list="[5, 10, 20, 50]"
                           data-search="true"
                           data-height="300">
                        <thead>
                        <tr>
                            <!-- <th data-field="state" data-checkbox="true"></th> -->
                            
                            <th data-field="MONTH" data-align="center" >月份</th>
                            <th data-field="MEDIATOR_NO" data-align="center" >居间人编号</th>
                            <th data-field="MEDIATOR_NAME" data-align="center" >居间人名称</th>
                            <th data-field="SXFJSR" data-align="center" data-formatter="numberFormate">总手续费净收入</th>
                            <th data-field="INTEREST" data-align="center" data-formatter="numberFormate">总利息</th>
                            <th data-field="EXCHANGE_RETURN" data-align="center" data-formatter="numberFormate">总交返（剔税）</th>
                            <th data-field="INVESTOR_SPECIAL_INTEREST" data-align="center" data-formatter="numberFormate">客户拿走利息</th>
                            <th data-field="INVESTOR_SPECIAL_EXCHANGE" data-align="center" data-formatter="numberFormate">客户拿走交返（剔税）</th>
                            <th data-field="MEDIATOR_SPECIAL_INTEREST" data-align="center" data-formatter="numberFormate">居间人拿走利息</th>
                            <th data-field="MEDIATOR_SPECIAL_EXCHANGE" data-align="center" data-formatter="numberFormate">居间人拿走交返（剔税）</th>
                            <th data-field="DBL16" data-align="center" data-formatter="numberFormate">居间人拿走返佣</th>
                            <th data-field="NET_PROFIT" data-align="center" data-formatter="numberFormate">净利润</th>
                        </tr>
                        </thead>
                    </table>
                  </div>
                  
                  <div id="mediatorArea"  class="form-group" style="display: none;">
                    <table style="width: 210mm;" border="1px">
                        <tr>
                            <td class="col-sm-2 col-md-3">居间人名称</td>
                            <td class="col-sm-2 col-md-3" id="mediatorNameCell"></td>
                            <td class="col-sm-2 col-md-3">居间人编号</td>
                            <td class="col-sm-2 col-md-3" id="mediatorNoCell"></td>
                        </tr>
                        <tr>
                            <td class="col-sm-2 col-md-3">客户数量</td>
                            <td class="col-sm-2 col-md-3" id="customerCountCell"></td>
                            <td class="col-sm-2 col-md-3">营业部</td>
                            <td class="col-sm-2 col-md-3" id="depNameCell1"></td>
                        </tr>
                        <tr>
                            <td class="col-sm-2 col-md-3">合同失效日期</td>
                            <td class="col-sm-2 col-md-3" id="expireDateCell"></td>
                            <td class="col-sm-2 col-md-3">手续费比例</td>
                            <td class="col-sm-2 col-md-3" id="chargeRateCell"></td>
                        </tr>
                        <tr>
                            <td class="col-sm-2 col-md-3">利息比例</td>
                            <td class="col-sm-2 col-md-3" id="interestRateCell"></td>
                            <td class="col-sm-2 col-md-3">交返比例</td>
                            <td class="col-sm-2 col-md-3" id="exchangeRateCell"></td>
                        </tr>
                        <tr>
                            <td class="col-sm-2 col-md-3">所属营销人员</td>
                            <td class="col-sm-2 col-md-3" id="marketerNameCell"></td>
                            <td class="col-sm-2 col-md-3"></td>
                            <td class="col-sm-2 col-md-3"></td>
                        </tr>
                        <tr>
                            <td class="col-sm-2 col-md-3" colspan="4" align="center">前三月留存利润</td>
                        </tr>
                        <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_MONTH0"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_PROFIT0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >手续费净收入(未扣除提成)</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_SXFJSR0"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_SXFJSR0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_INTEREST0"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_INTEREST0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_EXCHANGE_RETURN0"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_AMOUNT0"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_MONTH1"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_PROFIT1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >手续费净收入(未扣除提成)</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_SXFJSR1"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_SXFJSR1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_INTEREST1"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_INTEREST1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_EXCHANGE_RETURN1"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_AMOUNT1"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_MONTH2"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_PROFIT2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >手续费净收入(未扣除提成)</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_SXFJSR2"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_SXFJSR2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_INTEREST2"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_INTEREST2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_EXCHANGE_RETURN2"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_AMOUNT2"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">合计</td>
                                <td class="col-sm-2 col-md-3"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_PROFIT_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >手续费净收入</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_SXFJSR_SUM"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_SXFJSR_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_INTEREST_SUM"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_INTEREST_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_EXCHANGE_RETURN_SUM"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MEDIATOR_NET_AMOUNT_SUM"></td>
                            </tr>
                    </table>
                  </div>
              </div>
              
              <!-- 营销人员带来利润查询 -->
              <div role="tabpanel" class="tab-pane fade  in " id="profile">
                
                  <form class="form-horizontal" style="margin-top: 30px" id="marketerForm">
                      <div class="form-group">
                      
                            <!-- <label for="reportMonth1" class="col-sm-2 col-md-1 col-md-offset-1 control-label">统计年月</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="reportMonth1">
                            </div> -->
                            
                            <label for="investorNo" class="col-sm-2 col-md-1 control-label">营销人员编号</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="marketerNo">
                            </div>
                            
                            <label for="investorName" class="col-sm-2 col-md-1 control-label">营销人员名称</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="marketerName">
                            </div>
                            
                            <div class="col-sm-10 col-md-2 col-md-offset-1 ">
                                <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryMarketerProfit()">
                            </div>
                            
                            <div class="col-sm-10 col-md-2 ">
                                <input class="btn btn-default col-xs-7" type="button" value="打印" onclick="printMarketerProfit()">
                            </div>
                      </div>
                        
                  </form>
              
                  <div class="table-responsive">
                    <div id="toolbar" class="btn-group">
                      <div id="toolbar2" class="btn-group">
                          <span id="marketerOverview"></span>
                      </div>
                    </div>
                    <table id="marketerProfit"
                           class="table table-striped"
                           data-toggle="table"
                           data-toolbar="#toolbar2"
                           data-show-refresh="true"
                           data-show-toggle="true"
                           data-show-columns="true"
                           data-show-export="true"
                           data-detail-view="false"
                           data-height="542"
                           data-pagination="true"
                           data-method="post"
                           data-page-list="[5, 10, 20, 50]"
                           data-search="true"
                           data-height="300">
                        <thead>
                        <tr>
                            <!-- <th data-field="state" data-checkbox="true"></th> -->
                            
                            <th data-field="MONTH" data-align="center" >月份</th>
                            <th data-field="MARKETER_NO" data-align="center" >营销人员编号</th>
                            <th data-field="MARKETER_NAME" data-align="center" >营销人员名称</th>
                            <th data-field="SXFJSR" data-align="center" data-formatter="numberFormate">总手续费净收入</th>
                            <th data-field="INTEREST" data-align="center" data-formatter="numberFormate">总利息</th>
                            <th data-field="EXCHANGE_RETURN" data-align="center" data-formatter="numberFormate">总交返（剔税）</th>
                            <th data-field="INVESTOR_SPECIAL_INTEREST" data-align="center" data-formatter="numberFormate">投资者拿走利息</th>
                            <th data-field="INVESTOR_SPECIAL_EXCHANGE" data-align="center" data-formatter="numberFormate">投资者拿走交返（剔税）</th>
                            <th data-field="MEDIATOR_SPECIAL_INTEREST" data-align="center" data-formatter="numberFormate">居间人拿走利息</th>
                            <th data-field="MEDIATOR_SPECIAL_EXCHANGE" data-align="center" data-formatter="numberFormate">居间人拿走交返（剔税）</th>
                            <th data-field="DBL16" data-align="center" data-formatter="numberFormate">居间人拿走返佣</th>
                            <th data-field="MONEY" data-align="center" data-formatter="numberFormate">营销人员提成</th>
                            <th data-field="NET_PROFIT" data-align="center" data-formatter="numberFormate">净利润</th>
                        </tr>
                        </thead>
                    </table>
                  </div>
                  
                  <div id="marketerArea"  class="form-group" style="display: none">
                    <table style="width: 210mm;" border="1px">
                        <tr>
                            <td class="col-sm-2 col-md-3">营销人员名称</td>
                            <td class="col-sm-2 col-md-3" id="MARKETER_MARKETER_NAME"></td>
                            <td class="col-sm-2 col-md-3">营业部</td>
                            <td class="col-sm-2 col-md-3" id="MARKETER_DEP_NAME"></td>
                        </tr>
                        <tr>
                            <td class="col-sm-2 col-md-3">直属客户数量</td>
                            <td class="col-sm-2 col-md-3" id="MARKETER_CUSTOMER_COUNT"></td>
                            <td class="col-sm-2 col-md-3">居间人数量</td>
                            <td class="col-sm-2 col-md-3" id="MARKETER_MEDIATOR_COUNT"></td>
                        </tr>
                        <tr>
                            <td class="col-sm-2 col-md-3" >提成比例</td>
                            <td class="col-sm-2 col-md-3" id="MARKETER_RATE"></td>
                            <td class="col-sm-2 col-md-3"></td>
                            <td class="col-sm-2 col-md-3"></td>
                        </tr>
                        <tr>
                            <td class="col-sm-2 col-md-3" colspan="4" align="center">前三月留存利润</td>
                        </tr>
                        <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_MONTH0"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_PROFIT0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存手续费</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_SXFJSR0"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_SXFJSR0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_INTEREST0"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_INTEREST0"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_EXCHANGE_RETURN0"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_AMOUNT0"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_MONTH1"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_PROFIT1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >手续费净收入</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_SXFJSR1"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_SXFJSR1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_INTEREST1"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_INTEREST1"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_EXCHANGE_RETURN1"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_AMOUNT1"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">月份</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_MONTH2"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_PROFIT2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存手续费</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_SXFJSR2"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_SXFJSR2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_INTEREST2"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_INTEREST2"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_EXCHANGE_RETURN2"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_AMOUNT2"></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">合计</td>
                                <td class="col-sm-2 col-md-3"></td>
                                <td class="col-sm-2 col-md-3">净利润</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_PROFIT_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >谁续费净收入</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_SXFJSR_SUM"></td>
                                <td class="col-sm-2 col-md-3">净留存</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_SXFJSR_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3" >留存利息</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_INTEREST_SUM"></td>
                                <td class="col-sm-2 col-md-3">净利息</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_INTEREST_SUM"></td>
                            </tr>
                            <tr>
                                <td class="col-sm-2 col-md-3">交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_EXCHANGE_RETURN_SUM"></td>
                                <td class="col-sm-2 col-md-3">净交易所返还</td>
                                <td class="col-sm-2 col-md-3" id="MARKETER_NET_AMOUNT_SUM"></td>
                            </tr>
                    </table>
                  </div>
                  
              </div>
              
            </div>
              
        </div>
      </div>
      <div class="row placeholders">
        <footer class="footer">
                <p class="text-muted">   版权所有 渤海期货    </p>
        </footer>
      </div>
    </div>
    

  </body>
</html>
