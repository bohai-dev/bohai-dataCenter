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
        
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toInvestorReport"]').parent().addClass("active");
            
        });
        
        /* 查询投资者产生的利润 */
        function queryInvestorProfit(){
        	
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
                }
        	});
        	
        }
        
        /* 查询居间人产生的利润 */
        function queryMediatorProfit(){
        	
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
	           }
	       });
        }
        
        /* 查询营销人与贡献度 */
        function queryMarketerProfit(){
        	
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
                           +"属于营业部："+data.depName)
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
			return (value+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');//使用正则替换，每隔三个数加一个','  
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
            <li><a href="#">Dashboard</a></li>
            <li><a href="#">Settings</a></li>
            <li><a href="#">Profile</a></li>
            <li><a href="#">欢迎：${sessionScope.username}</a></li>
            <li><a href="logout">Sign Out</a></li>
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
                
                    <form class="form-horizontal" style="margin-top: 30px">
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
                        </div>
                          
                    </form>
              
                    <div class="table-responsive">
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
                             data-detail-view="true"
                             data-detail-formatter="detailFormatter"
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
              </div>
              
              <!-- 居间人带来利润查询 -->
              <div role="tabpanel" class="tab-pane fade  in " id="home">
                
                  <form class="form-horizontal" style="margin-top: 30px">
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
                           data-detail-view="true"
                           data-detail-formatter="detailFormatter"
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
              </div>
              
              <!-- 营销人员带来利润查询 -->
              <div role="tabpanel" class="tab-pane fade  in " id="profile">
                
                  <form class="form-horizontal" style="margin-top: 30px">
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
                           data-detail-view="true"
                           data-detail-formatter="detailFormatter"
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
