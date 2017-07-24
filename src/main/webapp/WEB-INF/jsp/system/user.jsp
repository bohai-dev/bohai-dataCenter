<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome  </title>
     <!-- datepicker -->
    <link href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css" rel="stylesheet">
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">
    
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    
     <!-- datepicker -->
    <script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    
    <!-- bootstrap-select -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    
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
            $('li a[href="toUser"]').parent().addClass("active");
        });
        
       
function saveSysUser(){
            
            var param = {
            		     username:$('#username').val(),
                         password:$('#password').val(),
                         locked:$('#locked').val(),
                         createTime:$('#createTime').val(),
                         fullName:$('#fullName').val()
                         }
            console.log(param);
            $.ajax({
                url: 'saveSysUser',
                type: 'post',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(param),
                success: function (data,status) {
                    $('#addModal').modal('hide')
                	alert("添加成功")
                    $("#investorTable").bootstrapTable('refresh');
                }
            });
        }
        
function queryUsersPagination(){
    
    $("#investorTable").bootstrapTable(
        'refresh',{url:"queryUsersPagination",
                   query: { username:$('#username').val(),
                       		password:$('#password').val(),
                       		locked:$('#locked').val(),
                       		crateTime:$('#createTime').val(),
                       		updateTime:$('#updateTime').val()
                       
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toUser" style="text-decoration: none;">系统管理</a> --> <a href="toUser" style="text-decoration: none;">用户管理</a></h1>

          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
                <div class="row placeholders">
                    <div class="col-xs-3 col-sm-3 placeholder">
                        <label for="name">用户名：</label>
                    </div>
                    <div class="col-xs-6 col-sm-6 placeholder ">
                        <input type="text" class="form-control" id="name" placeholder="">
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

          <h2 class="sub-header">用户信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="创建任务">
                    <i class="glyphicon glyphicon-plus"></i>
                </button>
            </div>
            <table id="userTable"
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
          </div>
        </div>
      </div>
      <div class="row placeholders">
        <footer class="footer">
                <p class="text-muted">   版权所有 渤海期货    </p>
        </footer>
      </div>
    </div>
    
     <!-- 新建用户模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">新建用户</h4>
          </div>
          <div class="modal-body">
              <form id="addForm" class="form-horizontal" role="form">
                  
                  
                  <div class="form-group">
                    <label for="username" class="col-sm-3 control-label">用户名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="username" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="password" class="col-sm-3 control-label">密码</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="password" placeholder="">
                    </div>
                  </div>

 					<div class="form-group">
                    <label for="fullname" class="col-sm-3 control-label">全名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="fullName" placeholder="">
                    </div>
                  </div>
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="saveSysUser()">保存</button>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
