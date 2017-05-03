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
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">
    <!-- datepicker -->
    <link href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css" rel="stylesheet">
    
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
            $('li a[href="toExchangeRebateReport"]').parent().addClass("active");
            
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
        
        function queryExchangeRebateReport(){
            var reportMonth = $("#reportMonth").val();
            if(reportMonth == "" || reportMonth == undefined || reportMonth == null){
                alert("请先选择统计年月！");
                return;
            }
            
            var param = {month:reportMonth,
                    depName:$("#depName").val()};
            
            $("#exchangeRebateTable").bootstrapTable(
                'refresh',{url:"queryExchangeRebateReport",
                           query: {month:reportMonth,
                                   depName:$("#depName").val()
                                   }
                          }
            );
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toRebateReport" style="text-decoration: none;">统计报表</a> --> <a href="toExchangeRebateReport" style="text-decoration: none;">交易所返还统计表</a></h1>
          </h4>
          <div class="row placeholders">
            <label class="control-label">请上传成交明细和平仓明细文件</label>
            <input id="finput" type="file" class="file" multiple >
            <script type="text/javascript">
               $("#finput").fileinput({
                   language: 'zh',
                   uploadAsync: true,
                   uploadUrl: "uploadExchangeRebate", //异步上传地址
                   maxFileCount: 10,//最大上传文件数限制
                   showCaption: true,//是否显示标题
                   showUpload: true,//是否显示上传按钮
                   allowedFileExtensions: ["xls", "xlsx","csv","txt"], //接收的文件后缀 
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
                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">交易所返佣月统计表</a></li>
                <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab"></a></li>
              </ul>
            
              <!-- Tab panes -->
              <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="home">
                      
                      <!-- 查询条件表单 -->
                      <form class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                          
                                <label for="reportMonth" class="col-sm-2 col-md-1 col-md-offset-2 control-label">统计年月</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="reportMonth">
                                </div>
                                
                                <label for="depName" class="col-sm-2 col-md-1 control-label">机构名称</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="depName">
                                </div>
                          </div>
                          
                          <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-5 ">
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryExchangeRebateReport()">
                            </div>
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
                      
                      <div class="table-responsive">
                        <div id="toolbar" class="btn-group">
                            
                        </div>
                        <!-- data-url="queryExchangeRebateReport" -->
                        <table class="table table-striped" id="exchangeRebateTable"
                               data-toggle="table" 
                               data-toolbar="#toolbar"
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-detail-view="true"
                               data-detail-formatter="detailFormatter"
                               data-height="564"
                               data-pagination="true"
                               data-method="post"
                               data-page-list="[5, 10, 20, 50]"
                               data-search="true"
                               data-show-footer="true">
                            <thead>
                            <tr>
                                <!-- <th data-field="state" data-checkbox="true"></th> -->
                                <th data-field="month" data-align="center" >统计月份</th>
                                <th data-field="depName" data-align="center" >机构名称</th>
                                <th data-field="srebate" data-align="center" >上期所返还</th>
                                <th data-field="zrebate" data-align="center" >郑商所返还</th>
                                <th data-field="drebate" data-align="center" >大商所返还</th>
                                <th data-field="jrebate" data-align="center" >中金所返还</th>
                                <th data-field="sum" data-align="center" >合计</th>
                            </tr>
                            </thead>
                        </table>
                      </div>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="profile">
                
                    <!-- 交易所返佣查询条件表单-->
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
                                
                                <label for="depName" class="col-sm-2 col-md-1 control-label">营业部名称</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="depName">
                                </div>
                                
                                <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-1">
                                  <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryExchangeRebateReport()">
                                </div>
                          </div>
                      </form>
                      <!-- 交易所返佣查询条件表单结束 -->
                      
                    <div class="table-responsive">
                        <table class="table table-striped"
                               data-toggle="table" 
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-height="542"
                               data-url="queryRebateDetail"
                               data-pagination="true"
                               data-method="get"
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
                                <th data-field="interestAmount" data-align="center" data-sortable="true">利息</th>
                                <th data-field="availableFunds" data-align="center" data-sortable="true">可用资金</th>
                                <th data-field="rights" data-align="center" data-sortable="true">客户权益</th>
                            </tr>
                            </thead>
                        </table>
                      </div>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="messages">...</div>
                <div role="tabpanel" class="tab-pane fade" id="settings">...</div>
              </div>
            
          </div>

          <!-- <h2 class="sub-header">用户信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="创建任务">
                    <i class="glyphicon glyphicon-plus"></i>
                </button>
            </div>
            <table class="table table-striped"
                   data-toggle="table" 
                   data-toolbar="#toolbar"
                   data-show-refresh="true"
                   data-show-toggle="true"
                   data-show-columns="true"
                   data-show-export="true"
                   data-detail-view="true"
                   data-detail-formatter="detailFormatter"
                   data-height="542"
                   data-url="user/queryUsers"
                   data-pagination="true"
                   data-side-pagination="server"
                   data-method="get"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true"
                   data-height="300">
                <thead>
                <tr>
                    <th data-field="state" data-checkbox="true"></th>
                    <th data-field="username" data-align="center" >用户名</th>
                    <th data-field="password" data-formatter="********" data-align="center" >密码</th>
                    <th data-field="dept" data-align="center" >部门</th>
                    <th data-field="locked" data-align="center" >状态</th>
                    <th data-field="createTime" data-align="center" data-sortable="true">创建时间</th>
                    <th data-field="updateTime" data-align="center" >更新时间</th>
                    <th data-field="" data-formatter="operationFormatter">操作</th>
                </tr>
                </thead>
            </table>
          </div> -->
          
          
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
