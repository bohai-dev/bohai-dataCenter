<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome  </title>
    
    <!-- css -->
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">
    <!-- table -->    
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
    
    <!-- datepicker -->
    <link href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css" rel="stylesheet">
    
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    
    <!-- datepicker -->
    <script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <script type="text/javascript">
        function operationFormatter(value,row,index) {
            var html = '<button type="button" id="cog'+index+'" class="btn btn-default btn-sm" title="设置" data-toggle="modal" data-target="#editModal">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+index+'" class="btn btn-default btn-sm" title="合并">'
                         + '<i class="glyphicon glyphicon-plus"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+index+'" class="btn btn-default btn-sm" title="拆分">'
                         + '<i class="glyphicon glyphicon-minus"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+index+'" class="btn btn-default btn-sm" title="删除">'
                         + '<i class="glyphicon glyphicon-trash"></i>'
                     + '</button>';
            return html;
        }
        
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toCrmDept"]').parent().addClass("active");
            
            
            $('#establishDate').datepicker({
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
            
            $('#establishDate1').datepicker({
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toCrmOverview" style="text-decoration: none;">客户关系管理</a> --> <a href="toCrmDept" style="text-decoration: none;">营业部信息维护</a></h1>

          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
                <div class="row placeholders">
                    <div class="col-xs-3 col-sm-3 placeholder">
                        <label for="name">用户名：</label>
                    </div>
                    <div class="col-xs-6 col-sm-6 placeholder ">
                        <input type="text" class="form-control" id="name1231" placeholder="">
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
                123
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
                123
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
                123
            </div>
          </div>

          <h2 class="sub-header">营业部信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="创建任务">
                    <i class="glyphicon glyphicon-plus">新建</i>
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
                   data-height="562"
                   data-url="queryCrmDept"
                   data-pagination="true"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="deptCode" data-align="center" >营业部编号</th>
                    <th data-field="deptName" data-align="center" >营业部名称</th>
                    <th data-field="deptHead" data-align="center" >营业部负责人</th>
                    <th data-field="deptTelephone" data-align="center" >营业部联系电话</th>
                    <th data-field="deptAddress" data-align="center" >营业部联系地址</th>
                    <th data-field="establishDate" data-align="center" >营业部成立日期</th>
                    <th data-field="" data-formatter="operationFormatter">操作</th>
                </tr>
                </thead>
            </table>
          </div>
        </div>
      </div>
      <div class="row placeholders">
        <footer class="footer">
                <p class="text-muted">   版权所有 渤海期货    </p>
        </footer>
      </div>
    </div>
    
    <!-- 新建营业部模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">新建营业部</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label for="deptNo" class="col-sm-3 control-label">营业部编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptNo" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptName" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptName" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptHead" class="col-sm-3 control-label">营业部负责人</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptHead" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="deptTelephone" class="col-sm-3 control-label">营业部电话</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptTelephone" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptAddress" class="col-sm-3 control-label">营业部地址</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="deptAddress" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="establishDate1" class="col-sm-3 control-label">成立日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="establishDate1" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="remark" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3" id="remark"></textarea>
                    </div>
                  </div>
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="savejob()">保存</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 修改营业部信息 -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">修改营业部信息</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label for="deptNo" class="col-sm-3 control-label">营业部编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptNo" placeholder="" readonly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptName" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptName" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptHead" class="col-sm-3 control-label">营业部负责人</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptHead" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="deptTelephone" class="col-sm-3 control-label">营业部电话</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptTelephone" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptAddress" class="col-sm-3 control-label">营业部地址</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="deptAddress" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="establishDate" class="col-sm-3 control-label">成立日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="establishDate" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="remark" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3" id="remark"></textarea>
                    </div>
                  </div>
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="savejob()">修改</button>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>