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
            var html = '<button type="button" id="cog'+row.deptCode+'" class="btn btn-default btn-sm" title="设置">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+index+'" class="btn btn-default btn-sm" title="合并">'
                         + '<i class="glyphicon glyphicon-plus"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+index+'" class="btn btn-default btn-sm" title="拆分">'
                         + '<i class="glyphicon glyphicon-minus"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+row.deptCode+'" class="btn btn-default btn-sm" title="删除">'
                         + '<i class="glyphicon glyphicon-trash"></i>'
                     + '</button>';
                     
                     //添加修改事件
                     $("#deptTable").off("click","#cog"+row.deptCode);
                     $("#deptTable").on("click","#cog"+row.deptCode,row,function(event){
                         config(row);
                     });
                     //添加删除事件
                     $("#deptTable").off("click","#trash"+row.deptCode);
                     $("#deptTable").on("click","#trash"+row.deptCode,row,function(event){
                         trash(row);
                     });
            return html;
        }
        
        /* 修改任务模态框 */
        function config(row){
            
            $("#deptCode1").val(row.deptCode);
            $("#deptName1").val(row.deptName);
            $("#deptTelephone1").val(row.deptTelephone);
            $("#deptAddress1").val(row.deptAddress);
            $("#deptHead1").val(row.deptHead);
            $("#establishDate1").val(row.establishDate);
            $("#remark1").val(row.remark);
            $("#editModal").modal('show');
        }
        
        /* 删除营业部 */
        function trash(row){
            if(confirm("删除营业部："+row.deptName+",确定吗？")){
                var param = {deptCode:row.deptCode};
                $.ajax({
                    type: "post",
                    url: "removeCrmDept",
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(param),
                    success: function (date, status){
                    	alert("删除成功");
                        $("#deptTable").bootstrapTable('refresh');
                    }
                });
            }
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
        
        //保存营业部信息
        function saveCrmDept(){
            var param = {deptCode:$('#deptCode').val(),
                    deptName:$('#deptName').val(),
                    deptTelephone:$('#deptTelephone').val(),
                    deptAddress:$('#deptAddress').val(),
                    deptHead:$('#deptHead').val(),
                    establishDate:$('#establishDate').val(),
                    remark:$('#remark').val()
                    }
           $.ajax({
               url: 'saveCrmDept',
               type: 'post',
               contentType: "application/json;charset=UTF-8",
               data: JSON.stringify(param),
               success: function (data,status) {
                   $('#addModal').modal('hide')
                   $("#deptTable").bootstrapTable('refresh');
                   }
           });
        }
        
        //更新营业部信息
        function updateCrmDept(){
            var param = {deptCode:$('#deptCode1').val(),
                    deptName:$('#deptName1').val(),
                    deptTelephone:$('#deptTelephone1').val(),
                    deptAddress:$('#deptAddress1').val(),
                    deptHead:$('#deptHead1').val(),
                    establishDate:$('#establishDate1').val(),
                    remark:$('#remark1').val()
                    }
           $.ajax({
               url: 'updateCrmDept',
               type: 'post',
               contentType: "application/json;charset=UTF-8",
               data: JSON.stringify(param),
               success: function (data,status) {
                   $('#editModal').modal('hide')
                   $("#deptTable").bootstrapTable('refresh');
                   }
           });
        }
        
      //生成居间人编号
        function generateDepNo(){
            $.ajax({
                url: 'generateDepNo',
                type: 'post',
                dataType: 'text',
                contentType: "application/json;charset=UTF-8",
                success: function (data,status) {
                    $('#deptCode').val(data);
                }
           });
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toCrmOverview" style="text-decoration: none;">客户关系管理</a> --> <a href="toCrmDept" style="text-decoration: none;">营业部信息维护</a></h1>

          <div class="row placeholders">
            
          </div>

          <h2 class="sub-header">营业部信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="创建任务">
                    <i class="glyphicon glyphicon-plus">新建</i>
                </button>
            </div>
            <table id="deptTable"
                   class="table table-striped"
                   data-toggle="table" 
                   data-toolbar="#toolbar"
                   data-show-refresh="true"
                   data-show-toggle="true"
                   data-show-columns="true"
                   data-show-export="true"
                   data-detail-view="false"
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
                    <label for="deptCode" class="col-sm-3 control-label">营业部编号</label>
                    <div class="col-sm-8">
                        <div class="input-group">
                          <span class="input-group-btn">
                            <button class="btn btn-info" type="button" onclick="generateDepNo()">生成编号</button>
                          </span>
                          <input type="text" class="form-control" id="deptCode" placeholder="">
                      </div>
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
            <button type="button" class="btn btn-primary" onclick="saveCrmDept()">保存</button>
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
                    <label for="deptCode1" class="col-sm-3 control-label">营业部编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptCode1" placeholder="" readonly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptName1" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptName1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptHead1" class="col-sm-3 control-label">营业部负责人</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptHead1" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="deptTelephone1" class="col-sm-3 control-label">营业部电话</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptTelephone1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="deptAddress1" class="col-sm-3 control-label">营业部地址</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="deptAddress1" placeholder="">
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
                    <label for="remark1" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3" id="remark1"></textarea>
                    </div>
                  </div>
                </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="updateCrmDept()">修改</button>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
