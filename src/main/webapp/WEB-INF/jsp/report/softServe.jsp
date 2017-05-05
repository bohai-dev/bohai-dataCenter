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
    
    <!-- PickList -->
    <link href="resources/css/pickList.css"  rel="stylesheet">
    <script src="resources/js/pickList.js"></script>
    
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
            $('li a[href="toSoftServeReport"]').parent().addClass("active");
            
        });
        
        
        //软件服务费合计
        function tbfooter(data){
            
            var interest = 0;
            for(var i=0;i<data.length;i++){
                  interest += parseFloat(data[i].SOFT_CHARGE);
                }
            //保留两位小数
            return interest.toFixed(2);
        }
        
        //总佣金合计
        function tbfooter0(data){
            
            var interest = 0;
            for(var i=0;i<data.length;i++){
                  interest += parseFloat(data[i].CHARGE);
                }
            //保留两位小数
            return interest.toFixed(2);
        }
        
        //交易所佣金合计
		function tbfooter1(data){
		    
		    var interest = 0;
		    for(var i=0;i<data.length;i++){
		          interest += parseFloat(data[i].ONHAND_CHARGE);
		        }
		    //保留两位小数
		    return interest.toFixed(2);
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toSoftServeReport" style="text-decoration: none;">统计报表</a> --> <a href="toSoftServeReport" style="text-decoration: none;">软件服务费统计表</a></h1>

          <div class="row placeholders">
            <div class="col-sm-12 col-md-6">
                <label class="control-label">请选择文件上传</label>
                <input id="finput" type="file" class="file" multiple >
                <script type="text/javascript">
                   $("#finput").fileinput({
                       language: 'zh',
                       uploadAsync: true,
                       uploadUrl: "fileUpload", //异步上传地址
                       maxFileCount: 10,//最大上传文件数限制
                       showCaption: true,//是否显示标题
                       showUpload: true,//是否显示上传按钮
                       allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀 
                       //previewFileIcon: "<i class='glyphicon glyphicon-king'></i>" //选择文件后缩略图
                   });
                </script>
              </div>
              <div class="col-sm-12 col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                       <h3 class="panel-title">请选择需要统计的营业部</h3>
                    </div>
                    <div class="panel-body">
        
                       <div id="pickList"></div>
        
                       <br><br>
                       <button class="btn btn-primary" id="count">统计</button>
                       &nbsp;&nbsp;&nbsp;&nbsp;
                       <button class="btn btn-primary" id="export">导出</button>
                    </div>
                 </div>
              <script type="text/javascript">
                  var val = {
                          01: {id: 01, text: '北京营业部'},
                          02: {id: 02, text: '福州营业部'},
                          03: {id: 03, text: '上海营业部'},
                          04: {id: 04, text: '大连分公司'},
                          05: {id: 05, text: '广州营业部'},
                          06: {id: 06, text: '杭州营业部'},
                          07: {id: 07, text: '深圳营业部'},
                          08: {id: 08, text: '济南营业部'},
                          09: {id: 09, text: '成都营业部'},
                          10: {id: 10, text: '厦门营业部'},
                          11: {id: 11, text: '北方事业部'}
                       };
        
                       var pick = $("#pickList").pickList({data: val});
        
                       $("#getSelected").click(function () {
                          console.log(pick.getValues());
                       });
              </script>
            </div>
          </div>
          
          
          <div>

              <!-- Nav tabs -->
              <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">TB软件服务费</a></li>
                <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">MC软件服务费</a></li>
                <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Messages</a></li>
                <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">Settings</a></li>
              </ul>
            
              <!-- Tab panes -->
              <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="home">
			          <div class="table-responsive">
			            <div id="toolbar" class="btn-group">
			                <a href="download"><i class="glyphicon glyphicon-export"></i></a>
			            </div>
			            <table class="table table-striped"
			                   data-toggle="table" 
			                   data-toolbar="#toolbar"
			                   data-show-refresh="true"
			                   data-show-toggle="true"
			                   data-show-columns="true"
			                   data-height="566"
			                   data-pagination="true"
			                   data-method="post"
			                   data-page-list="[5, 10, 20, 50]"
			                   data-search="true"
			                   data-show-footer="true"
			                   data-url="querySoftCharge">
			                <thead>
			                <tr>
			                    <!-- <th data-field="state" data-checkbox="true"></th> -->
			                    <th data-field="INVESTOR_NO" data-align="center" data-footer-formatter="合计">投资者编号</th>
			                    <th data-field="INVESTOR_NAME" data-align="center" >投资者姓名</th>
			                    <th data-field="DEPT" data-align="center" >所在营业部</th>
			                    <th data-field="CHARGE" data-align="center" data-footer-formatter="tbfooter0">总佣金</th>
			                    <th data-field="ONHAND_CHARGE" data-align="center" data-footer-formatter="tbfooter1">交易所佣金</th>
			                    <th data-field="SOFT_CHARGE" data-align="center" data-footer-formatter="tbfooter" data-sortable="true">软件服务费</th>
			                    <th data-field="BATCH_NO" data-align="center">计费时间段</th>
			                </tr>
			                </thead>
			            </table>
			          </div>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="profile">...</div>
                <div role="tabpanel" class="tab-pane fade" id="messages">...</div>
                <div role="tabpanel" class="tab-pane fade" id="settings">...</div>
              </div>
            
          </div>
          
          
          
        </div>
        
        
      <div class="row placeholders">
        <footer class="footer">
                
                <p class="text-muted">      </p>
                <p class="text-muted">      版权所有 渤海期货       </p>
                <p class="text-muted">      </p>
        </footer>
      </div>
    </div>
    

  </body>
</html>
