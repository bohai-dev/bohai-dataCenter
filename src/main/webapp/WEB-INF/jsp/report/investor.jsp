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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toBusinessReport" style="text-decoration: none;">统计报表</a> --> <a href="toBusinessReport" style="text-decoration: none;">营业部统计表</a></h4>


            <!-- Nav tabs -->
              <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">客户利润产出查询</a></li>
                <li role="presentation"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">居间人利润产出查询</a></li>
                <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">营销人员利润产出查询</a></li>
              </ul>
              <h2 class="sub-header">利润产出查询</h2>
              
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
                                  <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryInvestorReport()">
                              </div>
                        </div>
                          
                    </form>
              
                    <div class="table-responsive">
                      <div id="toolbar" class="btn-group">
                        <div id="toolbar" class="btn-group">
                            <span>简介：</span>
                        </div>
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
                              <!-- <th data-field="state" data-checkbox="true"></th> -->
                              
                              <th data-field="month" data-align="center" >月份</th>
                              <th data-field="investorNo" data-align="center" >投资者代码</th>
                              <th data-field="investorName" data-formatter="********" data-align="center" >投资者名称</th>
                              <th data-field="dept" data-align="center" >利息产出</th>
                              <th data-field="locked" data-align="center" >客户拿走利息</th>
                              <th data-field="locked" data-align="center" >居间人拿走利息</th>
                              <th data-field="locked" data-align="center" >交返产出</th>
                              <th data-field="locked" data-align="center" >客户拿走交返</th>
                              <th data-field="locked" data-align="center" >居间人拿走交返</th>
                              <th data-field="updateTime" data-align="center" >更新时间</th>
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
                            
                            <label for="investorNo" class="col-sm-2 col-md-1 control-label">投资者代码</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="investorNo">
                            </div>
                            
                            <label for="investorName" class="col-sm-2 col-md-1 control-label">投资者名称</label>
                            <div class="col-sm-10 col-md-2">
                              <input type="text" class="form-control" id="investorName">
                            </div>
                            
                            <div class="col-sm-10 col-md-2 col-md-offset-1 ">
                                <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryInvestorReport()">
                            </div>
                      </div>
                        
                  </form>
              
                  <div class="table-responsive">
                    <div id="toolbar" class="btn-group">
                      <div id="toolbar" class="btn-group">
                          <span>简介：</span>
                      </div>
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
                            <!-- <th data-field="state" data-checkbox="true"></th> -->
                            
                            <th data-field="month" data-align="center" >月份</th>
                            <th data-field="investorNo" data-align="center" >投资者代码</th>
                            <th data-field="investorName" data-formatter="********" data-align="center" >投资者名称</th>
                            <th data-field="dept" data-align="center" >利息产出</th>
                            <th data-field="locked" data-align="center" >客户拿走利息</th>
                            <th data-field="locked" data-align="center" >居间人拿走利息</th>
                            <th data-field="locked" data-align="center" >交返产出</th>
                            <th data-field="locked" data-align="center" >客户拿走交返</th>
                            <th data-field="locked" data-align="center" >居间人拿走交返</th>
                            <th data-field="updateTime" data-align="center" >更新时间</th>
                        </tr>
                        </thead>
                    </table>
                  </div>
              </div>
              
              <!-- 营销人员带来利润查询 -->
              <div role="tabpanel" class="tab-pane fade  in " id="home">
                
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
                                <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryInvestorReport()">
                            </div>
                      </div>
                        
                  </form>
              
                  <div class="table-responsive">
                    <div id="toolbar" class="btn-group">
                      <div id="toolbar" class="btn-group">
                          <span>简介：</span>
                      </div>
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
                            <!-- <th data-field="state" data-checkbox="true"></th> -->
                            
                            <th data-field="month" data-align="center" >月份</th>
                            <th data-field="investorNo" data-align="center" >投资者代码</th>
                            <th data-field="investorName" data-formatter="********" data-align="center" >投资者名称</th>
                            <th data-field="dept" data-align="center" >利息产出</th>
                            <th data-field="locked" data-align="center" >客户拿走利息</th>
                            <th data-field="locked" data-align="center" >居间人拿走利息</th>
                            <th data-field="locked" data-align="center" >交返产出</th>
                            <th data-field="locked" data-align="center" >客户拿走交返</th>
                            <th data-field="locked" data-align="center" >居间人拿走交返</th>
                            <th data-field="updateTime" data-align="center" >更新时间</th>
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
