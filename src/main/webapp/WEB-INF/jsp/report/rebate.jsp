<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome  </title>
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- <script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script> -->
    
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
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">
    <!-- datepicker -->
    <link href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css" rel="stylesheet">
    
    <!-- 菜单树 -->
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <!-- table插件 -->
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- <script src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script> -->
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/extensions/export/tableExport.min.js"></script>
    <script src="resources/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    
    <!-- 文件上传插件fileInput -->
    <script src="resources/fileInput/themes/fa/theme.js"></script>
    <!-- optionally if you need translation for your language then include 
        locale file as mentioned below -->
    <script src="resources/fileInput/js/locales/zh.js"></script>
    <!-- datepicker -->
    <script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    
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
            $('li a[href="toRebateReport"]').parent().addClass("active");
            
            /* 初始化datepicker */
            $('#datepicker').datepicker({
                format: "yyyy-mm",
                  startView: 1,
                  minViewMode: 1,
                  maxViewMode: 2,
                  todayBtn: "linked",
                  clearBtn: true,
                  language: "zh-CN",
                  autoclose: true,
                  todayHighlight: true
            });
            
            $('#reportMonth').datepicker({
                format: "yyyy-mm",
                  startView: 1,
                  minViewMode: 1,
                  maxViewMode: 2,
                  todayBtn: "linked",
                  clearBtn: true,
                  language: "zh-CN",
                  autoclose: true,
                  todayHighlight: true
            });
            
            $('#reportMonth1').datepicker({
                format: "yyyy-mm",
                  startView: 1,
                  minViewMode: 1,
                  maxViewMode: 2,
                  todayBtn: "linked",
                  clearBtn: true,
                  language: "zh-CN",
                  autoclose: true,
                  todayHighlight: true
            });
            $('#reportMonth2').datepicker({
                format: "yyyy-mm",
                  startView: 1,
                  minViewMode: 1,
                  maxViewMode: 2,
                  todayBtn: "linked",
                  clearBtn: true,
                  language: "zh-CN",
                  autoclose: true,
                  todayHighlight: true
            });
            
            $('#beginDate').datepicker({
                format: "yyyy-mm-dd",
                  startView: 0,
                  minViewMode: 0,
                  maxViewMode: 2,
                  todayBtn: "linked",
                  clearBtn: true,
                  language: "zh-CN",
                  autoclose: true,
                  todayHighlight: true
            });
            
            $('#endDate').datepicker({
                format: "yyyy-mm-dd",
                  startView: 0,
                  minViewMode: 0,
                  maxViewMode: 2,
                  todayBtn: "linked",
                  clearBtn: true,
                  language: "zh-CN",
                  autoclose: true,
                  todayHighlight: true
            });
        });
        
        function countRebatReport(){
            
            
            var dateStr = $("#datepicker").val();
            if(dateStr == "" || dateStr == undefined || dateStr == null){
                alert("请先选择统计年月！");
                return;
            }
            
            var param = {year: dateStr.substr(0,4),
                       month: dateStr.substr(5,7)}
            
            $.ajax({
                type: "POST",
                url: "countRebatReport",
                data: JSON.stringify(param),
                contentType: "application/json;charset=UTF-8",
                success: function (date, status){
                    alert("统计完成！");
                }
             }); 
        }
        
        //查询返利息报表
        function queryRebateReport(){
            var reportMonth = $("#reportMonth").val();
            if(reportMonth == "" || reportMonth == undefined || reportMonth == null){
                alert("请先选择统计年月！");
                return;
            }
            
            $("#rebateTable").bootstrapTable(
                'refresh',{url:"queryRebateReport",
                           query: {month:reportMonth,
                                   depName:$("#depName").val(),
                                  }
                          }
            );
        }
        
        //查询营业部返利息报表
        function queryMarketRebateReport(){
            var reportMonth = $("#reportMonth1").val();
            if(reportMonth == "" || reportMonth == undefined || reportMonth == null){
                alert("请先选择统计年月！");
                return;
            }
            
            $("#marketRebateTable").bootstrapTable(
                'refresh',{url:"queryMarketRebateReport",
                           query: {month:reportMonth,
                                   depName:$("#depName1").val(),
                                  }
                          }
            );
        }
        
        
        //查询营销人员返利息报表
        function queryMarketerRebateReport(){
            var reportMonth = $("#reportMonth2").val();
            if(reportMonth == "" || reportMonth == undefined || reportMonth == null){
                alert("请先选择统计年月！");
                return;
            }
            
            $("#marketerRebateTable").bootstrapTable(
                'refresh',{url:"queryMarketerRebateReport",
                           query: {month:reportMonth,
                                   depName:$("#depName2").val(),
                                  }
                          }
            );
        }
        
        function queryRebateDetail(){
        	
        	var param = {beginDate:$('#beginDate').val(),
        			     endDate: $('#endDate').val(),
        			     depName: $('#depName3').val(),
        			     investorNo: $('#investorNo3').val(),
        			     investorName: $('#investorName3').val(),
        			     mediatorNo: $('#mediatorNo3').val(),
        			     mediatorName: $('#mediatorName3').val()
        		        
        			   };
        	
        	$("#rebateDetailTable").bootstrapTable(
                    'refresh',{url:"queryRebateDetail",
                               query: param
                              }
                );
        }
        
        //营业部统计合计
        function footer(data){
            
            var interest = 0;
            for(var i=0;i<data.length;i++){
                  interest += parseFloat(data[i].INTEREST_AMOUNT);
                }
            //保留两位小数
            return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');;
        }
        
        /* 扣减后剩余利息合计 */
        function interestRemainfooter(data){
            
            var interest = 0;
            for(var i=0;i<data.length;i++){
                  interest += parseFloat(data[i].INTEREST_REMAIN);
                }
            //保留两位小数
            return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
        }
        
        //客户统计合计
        function rebateTableFooter(data){
        	
        	var interest = 0;
            for(var i=0;i<data.length;i++){
                  interest += parseFloat(data[i].INTEREST_AMOUNT);
                }
            //保留两位小数
            return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
        }
        
        //营销人员返利息合计
        function interestFooter(data){
            
            var interest = 0;
            for(var i=0;i<data.length;i++){
                  interest += parseFloat(data[i].INTEREST);
                }
            //保留两位小数并增加千分位
            return (interest.toFixed(2)+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toRebateReport" style="text-decoration: none;">统计报表</a> --> <a href="toRebateReport" style="text-decoration: none;">返佣统计表</a></h1>
          </h4>
          <div class="row placeholders">
            <label class="control-label">请选择文件上传</label>
            <input id="finput" type="file" class="file" multiple >
            <script type="text/javascript">
               $("#finput").fileinput({
                   language: 'zh',
                   uploadAsync: true,
                   uploadUrl: "uploadCapitalStatement", //异步上传地址
                   maxFileCount: 10,//最大上传文件数限制
                   showCaption: true,//是否显示标题
                   showUpload: true,//是否显示上传按钮
                   allowedFileExtensions: ["xls", "xlsx","csv"], //接收的文件后缀 
                   //previewFileIcon: "<i class='glyphicon glyphicon-king'></i>" //选择文件后缩略图
               });
            </script>
          </div>
          
          <!-- 统计表单 -->
          <form class="form-inline" style="margin-bottom: 30px;">
              <div class="form-group">
                <label for="datepicker">请选择统计年月：</label>
                <input type="text" class="form-control" id="datepicker">
                
              </div>
              <input class="btn btn-default" type="button" value="开始统计" onclick="countRebatReport()">
          </form>
          <hr>
          
          <div>

              <!-- Nav tabs -->
              <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">营业部返利息统计表</a></li>
                <li role="presentation"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">客户返利息统计表</a></li>
                <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">返利息明细</a></li>
                <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">营销人员返利息</a></li>
              </ul>
            
              <!-- Tab panes -->
              <div class="tab-content">
                <!-- 营业部返利息统计表 -->
                <div role="tabpanel" class="tab-pane fade  in active" id="messages">
                    <form class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                          
                                <label for="reportMonth1" class="col-sm-2 col-md-1 col-md-offset-1 control-label">统计年月</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="reportMonth1">
                                </div>
                                
                                <label for="depName1" class="col-sm-2 col-md-1 control-label">营业部名称</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="depName1">
                                </div>
                                
                                <div class="col-sm-10 col-md-2 col-md-offset-1 ">
                                    <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryMarketRebateReport()">
                                </div>
                          </div>
                          
                      </form>
                      <!-- 查询条件表单结束 -->
                      
                      <div class="table-responsive">
                        <div id="toolbar1" class="btn-group">
                            
                        </div>
                        <table class="table table-striped" id="marketRebateTable"
                               data-toggle="table" 
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-export="true"
                               data-show-columns="true"
                               data-toolbar="#toolbar1"
                               data-detail-view="true"
                               data-detail-formatter="detailFormatter"
                               data-pagination="true"
                               data-method="post"
                               data-page-list="[5, 10, 20, 50]"
                               data-search="true"
                               data-height="566"
                               data-show-footer="true">
                            <thead>
                            <tr>
                                <!-- <th data-field="state" data-checkbox="true"></th> -->
                                <th data-field="DEPT_NAME" data-align="center" data-footer-formatter="合计">营业部</th>
                                <th data-field="AVAILABLE_FUNDS" data-align="center" data-formatter="numberFormate">可用资金</th>
                                <th data-field="INTEREST_AMOUNT" data-align="center" data-footer-formatter="footer" data-formatter="numberFormate">利息</th>
                                <!-- <th data-field="INTEREST_REMAIN" data-align="center" data-footer-formatter="interestRemainfooter">扣减后剩余利息</th> -->
                            </tr>
                            </thead>
                        </table>
                      </div>
                </div>
                
                <!-- 客户返利息统计表 -->
                <div role="tabpanel" class="tab-pane fade" id="home">
                      
                      <!-- 查询条件表单 -->
                      <form class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                          
                                <label for="reportMonth" class="col-sm-2 col-md-1 col-md-offset-1 control-label">统计年月</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="reportMonth">
                                </div>
                                
                                <label for="depName" class="col-sm-2 col-md-1 control-label">营业部名称</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="depName">
                                </div>
                                
                                <label for="investorNo" class="col-sm-2 col-md-1 control-label">投资者账号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="investorNo">
                                </div>
                            
                          </div>
                          <div class="form-group">
                                <label for="investorName" class="col-sm-2 col-md-1 col-md-offset-1 control-label">投资者姓名</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="investorName">
                                </div>
                                
                                <label for="mediatorNo" class="col-sm-2 col-md-1 control-label">居间人编号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="mediatorNo">
                                </div>
                                
                                <label for="mediatorName" class="col-sm-2 col-md-1 control-label">居间人姓名</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="mediatorName">
                                </div>
                          </div>
                          
                          <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-5 ">
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryRebateReport()">
                            </div>
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
                      
                      <div class="table-responsive">
                        <div id="toolbar" class="btn-group">
                            
                        </div>
                        <table class="table table-striped" id="rebateTable"
                               data-toggle="table" 
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-export="true"
                               data-show-columns="true"
                               data-toolbar="#toolbar"
                               data-detail-view="true"
                               data-detail-formatter="detailFormatter"
                               data-pagination="true"
                               data-method="post"
                               data-page-list="[5, 10, 20, 50, 500]"
                               data-search="true"
                               data-height="566"
                               data-show-footer="true"
                               data-url="#">
                            <thead>
                            <tr>
                                <!-- <th data-field="state" data-checkbox="true"></th> -->
                                <th data-field="INVESTOR_NO" data-align="center" data-footer-formatter="合计">投资者编号</th>
                                <th data-field="INVESTOR_NAME" data-align="center" >投资者姓名</th>
                                <th data-field="MEDIATOR_NAME" data-align="center" >居间人姓名姓名</th>
                                <th data-field="DEPT_NAME" data-align="center" >营业部</th>
                                <th data-field="AVAILABLE_FUNDS" data-align="center" data-formatter="numberFormate">可用资金</th>
                                <th data-field="EXPIRE_DATE" data-align="center" >居间到期日期</th>
                                <th data-field="INTEREST_AMOUNT" data-align="center" data-footer-formatter="rebateTableFooter" data-formatter="numberFormate">利息</th>
                            </tr>
                            </thead>
                        </table>
                      </div>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="profile">
                
                    <!-- 返利息明细查询条件表单 -->
                      <form class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                          
                                <label for="beginDate" class="col-sm-2 col-md-1 col-md-offset-1 control-label">统计开始日期</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="beginDate">
                                </div>
                                
                                <label for="endDate" class="col-sm-2 col-md-1 control-label">统计结束日期</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="endDate">
                                </div>
                                
                                <label for="depName3" class="col-sm-2 col-md-1 control-label">营业部名称</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="depName3">
                                </div>
                                
                          </div>
                          <div class="form-group">
                          
                                <label for="investorNo3" class="col-sm-2 col-md-1 col-md-offset-1 control-label">投资者账号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="investorNo3">
                                </div>
                                
                                <label for="investorName3" class="col-sm-2 col-md-1 control-label">投资者姓名</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="investorName3">
                                </div>
                                
                                <label for="mediatorNo3" class="col-sm-2 col-md-1 control-label">居间人编号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="mediatorNo3">
                                </div>
                                
                          </div>
                          
                          <div class="form-group">
                          
                            <label for="mediatorName3" class="col-sm-2 col-md-1 col-md-offset-1 control-label">居间人姓名</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="mediatorName3">
                            </div>
                            
                            <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-1">
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryRebateDetail()">
                            </div>
                            
                          </div>
                      </form>
                      <!-- 返利息明细查询条件表单结束 -->
                      
                      
                    <div class="table-responsive">
                        <table id="rebateDetailTable"
                               class="table table-striped"
                               data-toggle="table" 
                               data-show-export="true"
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-height="542"
                               data-url="queryRebateDetail"
                               data-pagination="true"
                               data-method="post"
                               data-page-list="[5, 10, 20, 50]"
                               data-search="true"
                               data-height="300">
                            <thead>
                            <tr>
                                <!-- <th data-field="state" data-checkbox="true"></th> -->
                                <th data-field="tradeDateStr" data-align="center" data-sortable="true">日期</th>
                                <th data-field="investorNo" data-align="center" >投资者编号</th>
                                <th data-field="investorName" data-align="center" >投资者姓名</th>
                                <th data-field="deptName" data-align="center" >营业部</th>
                                <th data-field="fixProportion" data-align="center" >固定利率</th>
                                <th data-field="dailyRights" data-align="center" >日均权益利率</th>
                                <th data-field="interestAmount" data-align="center" data-sortable="true" data-formatter="numberFormate">利息</th>
                                <th data-field="availableFunds" data-align="center" data-sortable="true" data-formatter="numberFormate">可用资金</th>
                                <th data-field="rights" data-align="center" data-sortable="true" data-formatter="numberFormate">客户日均权益</th>
                            </tr>
                            </thead>
                        </table>
                      </div>
                </div>
                
                <div role="tabpanel" class="tab-pane fade" id="settings">
                
                    <form class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                          
                                <label for="reportMonth2" class="col-sm-2 col-md-1 col-md-offset-1 control-label">统计年月</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="reportMonth2">
                                </div>
                                
                                <div class="col-sm-10 col-md-2 col-md-offset-1 ">
                                    <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryMarketerRebateReport()">
                                </div>
                          </div>
                          
                    </form>
                
                    <div class="table-responsive">
                        <table id="marketerRebateTable"
                               class="table table-striped"
                               data-toggle="table" 
                               data-show-export="true"
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-height="564"
                               data-pagination="true"
                               data-method="post"
                               data-page-list="[5, 10, 20, 50]"
                               data-search="true"
                               data-show-footer="true">
                            <thead>
                            <tr>
                                <!-- <th data-field="state" data-checkbox="true"></th> -->
                                <th data-field="MARKETER_NO" data-align="center" data-footer-formatter="合计">营销人员编号</th>
                                <th data-field="MARKETER_NAME" data-align="center" >营销人员姓名</th>
                                <th data-field="DEP_NAME" data-align="center" >营业部</th>
                                <th data-field="INTEREST" data-align="center" data-footer-formatter="interestFooter" data-formatter="numberFormate">返利息</th>
                            </tr>
                            </thead>
                        </table>
                      </div>
                
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
