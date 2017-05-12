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
    
    <!-- bootstrap-select -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>


    <script type="text/javascript">
        function operationFormatter(value,row,index) {
            var html = '<button type="button" id="cog'+row.investorNo+'" class="btn btn-default btn-sm" title="设置">'
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
                     
            $("#investorTable").off("click","#cog"+row.investorNo);
            $("#investorTable").on("click","#cog"+row.investorNo,row,function(event){
                config(row);
            });
            return html;
        }
        
        /* 修改任务模态框 */
        function config(row){
        	
        	$("#deptName1").val(row.deptName);
            $("#investorNo1").val(row.investorNo);
            $("#investorName1").val(row.investorName);
            $("#certType1").val(row.certType);
            $("#certNo1").val(row.certNo);
            $("#openDate1").val(row.openDate);
            $("#effectDate1").val(row.effectDate);
            $("#email1").val(row.email);
            $("#mobilePhone1").val(row.mobilePhone);
            $("#contractNo1").val(row.contractNo);
            $("#editModal").modal('show');
        }
        
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toCrmCustomer"]').parent().addClass("active");
            
            
            $('#openDate').datepicker({
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
            
            $('#openDate1').datepicker({
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
            
            $('#effectDate').datepicker({
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
            
            $('#effectDate1').datepicker({
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
            
            $('#expireDate').datepicker({
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
            
            $('#expireDate1').datepicker({
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
            
            //初始化
            $('.selectpicker').selectpicker();
            
            $('#belongType').on('changed.bs.select loaded.bs.select', function (e) {
            	var belongType = $('#belongType').val();
            	if(belongType == "1"){
            		//居间人
            		$.ajax({
                        url: 'queryCrmMediator',
                        type: 'post',
                        dataType: 'json',
                        success: function (data) {
                            var len = data.length;
                             var optionString = "<option> </option>";
                             for (i = 0; i < len; i++) {
                                 optionString += "<option value=\'"+ data[i].mediatorNo +"\'>" + data[i].mediatorName + "(" +data[i].mediatorNo+")</option>";
                             }
                             
                             $('#belongTo').html(optionString);
                             $('#belongTo').selectpicker('refresh');
                        }
                 });
            	}else if(belongType == "2"){
            		//营销人员
            		$.ajax({
                        url: 'queryCrmMarketer',
                        type: 'post',
                        dataType: 'json',
                        success: function (data) {
                            var len = data.length;
                             var optionString = "<option> </option>";
                             for (i = 0; i < len; i++) {
                                 optionString += "<option value=\'"+ data[i].marketerNo +"\'>" + data[i].marketerName + "(" +data[i].marketerNo+")</option>";
                             }
                             
                             $('#belongTo').html(optionString);
                             $('#belongTo').selectpicker('refresh');
                        }
                 });
            	}else if(belongType == "0"){
            		 $.ajax({
            	            url: 'queryCrmDept',
            	            type: 'post',
            	            dataType: 'json',
            	            success: function (data) {
            	            	var len = data.length;
            	            	 var optionString = "<option> </option>";
            	                 for (i = 0; i < len; i++) {
            	                     optionString += "<option value=\'"+ data[i].deptCode +"\'>" + data[i].deptName + "</option>";
            	                 }
            	                 
            	                 $('#belongTo').html(optionString);
            	                 $('#belongTo').selectpicker('refresh');
            	            }
            		 });
                }
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toCrmOverview" style="text-decoration: none;">客户关系管理</a> --> <a href="toCrmCustomer" style="text-decoration: none;">客户信息维护</a></h1>

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

          <h2 class="sub-header">客户基本信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="创建任务">
                    <i class="glyphicon glyphicon-plus">新增客户</i>
                </button>
            </div>
            <table id="investorTable"
                   class="table table-striped"
                   data-toggle="table" 
                   data-toolbar="#toolbar"
                   data-show-refresh="true"
                   data-show-toggle="true"
                   data-show-columns="true"
                   data-show-export="true"
                   data-detail-view="true"
                   data-detail-formatter="detailFormatter"
                   data-height="562"
                   data-url="queryCrmCustomer"
                   data-pagination="true"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="deptName" data-align="center" >所属营业部</th>
                    <th data-field="investorNo" data-align="center" >客户编号</th>
                    <th data-field="investorName" data-align="center" >客户名称</th>
                    <th data-field="belongType" data-align="center" >归属类型</th>
                    <th data-field="belongToName" data-align="center" >归属名称</th>
                    <th data-field="openDate" data-align="center" >开户日期</th>
                    <th data-field="cancelDate" data-align="center" >销户日期</th>
                    <th data-field="certType" data-align="center" >证件类型</th>
                    <th data-field="certNo" data-align="center" >证件号码</th>
                    <th data-field="mobilePhone" data-align="center" >手机</th>
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
    
    <!-- 新建客户模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">新建客户信息</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="deptName" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptName" placeholder="" readOnly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="custNo" class="col-sm-3 control-label">客户编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="custNo" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="custName" class="col-sm-3 control-label">客户姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="custName" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="belongType" class="col-sm-3 control-label">归属类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="belongType" data-live-Search="true">
						  <option value="0">营业部</option>
						  <option value="1">居间人</option>
						  <option value="2">营销人员</option>
					  </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="belongTo" class="col-sm-3 control-label">归属名称</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="belongTo" data-live-Search="true">
                      </select>
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="certType" class="col-sm-3 control-label">证件类型</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="certType" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="certNo" class="col-sm-3 control-label">证件号码</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="certNo" placeholder="">
                    </div>
                  </div>
                  
                  
                  <div class="form-group">
                    <label for="openDate" class="col-sm-3 control-label">开户日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="openDate" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="effectDate" class="col-sm-3 control-label">生效日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="effectDate" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="expireDate" class="col-sm-3 control-label">销户日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="expireDate" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="contractNo" class="col-sm-3 control-label">合同编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="contractNo" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="email" class="col-sm-3 control-label">电子邮箱</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="email" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="address" class="col-sm-3 control-label">地址</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="address" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="telephone" class="col-sm-3 control-label">手机</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="telephone" placeholder="">
                    </div>
                  </div>
                  
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
    
    <!-- 修改客户基本信息 -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">修改客户信息</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="deptName1" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="deptName1" placeholder="" readOnly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="investorNo1" class="col-sm-3 control-label">客户编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="investorNo1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="investorName1" class="col-sm-3 control-label">客户姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="investorName1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="belongType1" class="col-sm-3 control-label">归属类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="belongType1" data-live-Search="true">
                          <option value="0">营业部</option>
                          <option value="1">居间人</option>
                          <option value="2">营销人员</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="belongTo1" class="col-sm-3 control-label">归属名称</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="belongTo1" data-live-Search="true">
                      </select>
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="certType1" class="col-sm-3 control-label">证件类型</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="certType1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="certNo1" class="col-sm-3 control-label">证件号码</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="certNo1" placeholder="">
                    </div>
                  </div>
                  
                  
                  <div class="form-group">
                    <label for="openDate1" class="col-sm-3 control-label">开户日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="openDate1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="effectDate1" class="col-sm-3 control-label">生效日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="effectDate1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="expireDate1" class="col-sm-3 control-label">失效日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="expireDate1" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="contractNo1" class="col-sm-3 control-label">合同编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="contractNo1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="email1" class="col-sm-3 control-label">电子邮箱</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="email1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="address1" class="col-sm-3 control-label">地址</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="address1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="mobilePhone1" class="col-sm-3 control-label">手机</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="mobilePhone1" placeholder="">
                    </div>
                  </div>
                  
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
            <button type="button" class="btn btn-primary" onclick="savejob()">修改</button>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
