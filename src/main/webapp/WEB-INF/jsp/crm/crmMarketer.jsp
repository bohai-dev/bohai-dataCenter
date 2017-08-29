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
    
		function divisionFormatter(value,row,index){
		    var result = row.personnelDivision;
		    if(result == '0'){
		        result = '前台';
		    }else if (result == '1') {
		        result = '后台';
		    }
		    return result;
		}
		
		function statusFormatter(value,row,index){
            var result = row.status;
            if(result == '0'){
                result = '离职';
            }else if (result == '1') {
                result = '在职';
            }
            return result;
        }
		
		
        function operationFormatter(value,row,index) {
            var html = '<button type="button" id="cog'+row.marketerNo+'" class="btn btn-default btn-sm" title="设置">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+row.marketerNo+'" class="btn btn-default btn-sm" title="删除">'
                         + '<i class="glyphicon glyphicon-trash"></i>'
                     + '</button>';
                     
             $("#marketerTable").off("click","#cog"+row.marketerNo);
             $("#marketerTable").on("click","#cog"+row.marketerNo,row,function(event){
                 config(row);
             });
             
           //添加删除事件
             $("#marketerTable").off("click","#trash"+row.marketerNo);
             $("#marketerTable").on("click","#trash"+row.marketerNo,row,function(event){
                 trash(row);
             });
             
             return html;
        }
        
        /* 修改任务模态框 */
        function config(row){
            
            $("#marketerNo1").val(row.marketerNo);
            $("#marketerName1").val(row.marketerName);
            //设置bootstrap-select的值
            $('#deptName1').selectpicker('val', row.depCode);
            $('#status1').selectpicker('val', row.status);
            $('#certType1').selectpicker('val', row.certType);
            //人员分区
            //$("#divisionOptions1").val(row.personnelDivision);
            
            if(row.personnelDivision == "0"){
            	$("#divisionOptions0").attr("checked","checked");
            }else if (row.personnelDivision == "1") {
            	$("#divisionOptions1").attr("checked","checked");
			}
            
            $("#entryDate1").val(row.entryDate);
            $("#leaveDate1").val(row.leaveDate);
            //$("#birthday1").val(row.birthday);
            $("#rebateRate1").val(row.allocationProportion);
            $("#certNo1").val(row.certNo);
            $("#telephone1").val(row.telephone);
            $("#address1").val(row.address);
            $("#email1").val(row.email);
            $("#remark1").val(row.remark);
            
            $("#editModal").modal('show');
        }
        
        /* 删除营销人员 */
        function trash(row){
            if(confirm("删除营销人员："+row.marketerName+"("+row.marketerNo+")后将不可撤销,确定吗？")){
                var param = {marketerNo:row.marketerNo};
                $.ajax({
                    type: "post",
                    url: "removeCrmMarketer",
                    //dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(param),
                    success: function (date, status){
                        alert("删除成功");
                        $("#marketerTable").bootstrapTable('refresh');
                    }
                });
            }
        }
        
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toCrmMarketer"]').parent().addClass("active");
            
            
            $('#entryDate1').datepicker({
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
            
            $('#entryDate').datepicker({
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
            
            $('#leaveDate').datepicker({
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
            
            $('#leaveDate1').datepicker({
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
            
            //绑定初始化方法
            $('#deptName').on('loaded.bs.select', function (e) {
                $.ajax({
                    url: 'queryCrmDept',
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        var len = data.length;
                         var optionString = "<option > </option>";
                         for (i = 0; i < len; i++) {
                             optionString += "<option value=\'"+ data[i].deptCode +"\'>" + data[i].deptName + "</option>";
                         }
                         
                         $('#deptName').html(optionString);
                         $('#deptName').selectpicker('refresh');
                         $('#deptName1').html(optionString);
                         $('#deptName1').selectpicker('refresh');
                         $('#qdepName').html(optionString);
                         $('#qdepName').selectpicker('refresh');
                    }
                });
            });
            
            $("#queryForm").keypress(function(e){
                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                if (eCode == 13){
                    //自己写判断函数
                    queryCrmMarketer();
                }
            });
            
        });
        
        function saveCrmMarketer(){
            var param = {marketerNo:$('#marketerNo').val(),
            		marketerName:$('#marketerName').val(),
            		depCode:$('#deptName').val(),
            		depName:$('#deptName option:selected').text(),
            		personnelDivision:$("input[name='divisionOptions']:checked").val(),
            		status:$('#status').val(),
            		entryDate:$('#entryDate').val(),
            		leaveDate:$('#leaveDate').val(),
            		//birthday:$('#birthday').val(),
            		allocationProportion:$('#rebateRate').val(),
                    certType:$('#certType').val(),
            		certNo:$('#certNo').val(),
            		telephone:$('#telephone').val(),
            		address:$('#address').val(),
                    email:$('#email').val(),
                    remark:$('#remark').val(),
                    }
             $.ajax({
                 url: 'saveCrmMarketer',
                 type: 'post',
                 contentType: "application/json;charset=UTF-8",
                 data: JSON.stringify(param),
                 success: function (data,status) {
                     $('#addModal').modal('hide');
                     $('#marketerTable').bootstrapTable('refresh');
                 }
            });
        }
        
        function updateCrmMarketer(){
        	var param = {marketerNo:$('#marketerNo1').val(),
                    marketerName:$('#marketerName1').val(),
                    depCode:$('#deptName1').val(),
                    depName:$('#deptName1 option:selected').text(),
                    personnelDivision:$("input[name='divisionOptions1']:checked").val(),
                    status:$('#status1').val(),
                    entryDate:$('#entryDate1').val(),
                    leaveDate:$('#leaveDate1').val(),
                    //birthday:$('#birthday').val(),
                    allocationProportion:$('#rebateRate1').val(),
                    certType:$('#certType1').val(),
                    certNo:$('#certNo1').val(),
                    telephone:$('#telephone1').val(),
                    address:$('#address1').val(),
                    email:$('#email1').val(),
                    remark:$('#remark1').val(),
                    }
             $.ajax({
                 url: 'updateCrmMarketer',
                 type: 'post',
                 contentType: "application/json;charset=UTF-8",
                 data: JSON.stringify(param),
                 success: function (data,status) {
                     $('#editModal').modal('hide');
                     $('#marketerTable').bootstrapTable('refresh');
                 }
            });
        }
        
        //查询营销人员信息
        function queryCrmMarketer(){
            
            $("#marketerTable").bootstrapTable(
                'refresh',{url:"queryCrmMarketer",
                           query: {depCode:$('#qdepName').val(),
                        	       marketerNo:$("#qmarketerNo").val(),
                        	       marketerName:$("#qmarketerName").val()
                                  }
                          }
            );
        }
        
        function queryParams(params){
            return {depCode:$('#qdepName').val(),
                marketerNo:$("#qmarketerNo").val(),
                marketerName:$("#qmarketerName").val(),
                pageNumber:params.pageNumber,
                pageSize:params.pageSize}
        }
        
      //生成营销人员编号
        function generateMarketerNo(){
            $.ajax({
                url: 'generateMarketerNo',
                type: 'post',
                dataType: 'text',
                contentType: "application/json;charset=UTF-8",
                success: function (data,status) {
                    $('#marketerNo').val(data);
                }
           });
        }
      
      //导出excel
        function exportCrmMarketer(){
            $('#queryForm').submit();
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toCrmOverview" style="text-decoration: none;">客户关系管理</a> --> <a href="toCrmMarketer" style="text-decoration: none;">营销人员信息维护</a></h1>

          <div class="row placeholders">
            <!-- 查询条件表单 -->
                      <form id="queryForm" method="post" action="exportCrmMarketer" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                          
                                <label for="qdepName" class="col-sm-2 col-md-1 control-label">所在营业部</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="qdepName" name="depCode" data-live-Search="true">
                                    </select>
                                </div>
                                
                                <label for="qmarketerNo" class="col-sm-2 col-md-1 control-label">营销人员编号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" name="marketerNo" id="qmarketerNo">
                                </div>
                            
                                <label for="qmarketerName" class="col-sm-2 col-md-1 control-label">营销人员姓名</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" name="marketerName" id="qmarketerName">
                                </div>
                          </div>
                          
                          <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-4 ">
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryCrmMarketer()">
                            </div>
                            <div class=" col-sm-10 col-md-2 ">
                              <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportCrmMarketer()">
                            </div>
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
          </div>

          <h2 class="sub-header">营销人员信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="创建任务">
                    <i class="glyphicon glyphicon-plus">新建</i>
                </button>
            </div>
            <table id="marketerTable"
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
                   data-url="queryCrmMarketer"
                   data-pagination="true"
                   data-query-params="queryParams"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="depName" data-align="center" >所属营业部</th>
                    <th data-field="marketerNo" data-align="center" >营销人员编号</th>
                    <th data-field="marketerName" data-align="center" >营销人员名称</th>
                    <th data-field="personnelDivision" data-align="center" data-formatter="divisionFormatter">人员分区</th>
                    <th data-field="status" data-align="center" data-formatter="statusFormatter">在职状态</th>
                    <th data-field="entryDate" data-align="center" >入职日期</th>
                    <th data-field="leaveDate" data-align="center" >离职日期</th>
                    <th data-field="allocationProportion" data-align="center" >默认分配比例</th>
                    <th data-field="certType" data-align="center" >证件类型</th>
                    <th data-field="certNo" data-align="center" >证件号码</th>
                    <th data-field="telephone" data-align="center" >联系电话</th>
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
    
    <!-- 新建营销人员模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">新建营销人员</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="deptName" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="deptName" data-live-Search="true">
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="marketerNo" class="col-sm-3 control-label">营销人员编号</label>
                    <div class="col-sm-8">
                        <div class="input-group">
                            <span class="input-group-btn">
                              <button class="btn btn-info" type="button" onclick="generateMarketerNo()">生成编号</button>
                            </span>
                            <input type="text" class="form-control" id="marketerNo"  placeholder="">
                        </div>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="marketerName" class="col-sm-3 control-label">营销人员姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="marketerName" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="divisionOptions" class="col-sm-3 control-label">人员分区</label>
                    <div class="col-sm-8">
                      <label class="btn btn-info col-sm-3">
                        <input type="radio" name="divisionOptions"  value="0" checked="checked" autocomplete="off"> 前台
                      </label>
                      <label class="btn btn-info col-sm-3 col-sm-offset-1">
                        <input type="radio" name="divisionOptions"  value="1" autocomplete="off"> 后台
                      </label>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="status" class="col-sm-3 control-label">在职状态</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="status" >
                        <option value="1">在职</option>
                        <option value="0">离职</option>
                      </select>
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="certType" class="col-sm-3 control-label">证件类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="certType" >
                        <option value="0">身份证</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="certNo" class="col-sm-3 control-label">证件号码</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="certNo" placeholder="">
                    </div>
                  </div>
                  
                  <!-- <div class="form-group">
                    <label for="gender" class="col-sm-3 control-label">性别</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="gender" placeholder="">
                      <label class="btn btn-primary col-sm-3">
                        <input type="radio" name="genderOptions" id="option1" value="1" checked="checked" autocomplete="off"> 男
                      </label>
                      <label class="btn btn-danger col-sm-3 col-sm-offset-1">
                        <input type="radio" name="genderOptions" id="option0" value="0" autocomplete="off"> 女
                      </label>
                    </div>
                  </div> -->
                  
                  <!-- <div class="form-group">
                    <label for="birthday" class="col-sm-3 control-label">出生日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="birthday" placeholder="">
                    </div>
                  </div> -->
                  
                  <div class="form-group">
                    <label for="entryDate" class="col-sm-3 control-label">入职日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="entryDate" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="leaveDate" class="col-sm-3 control-label">离职日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="leaveDate" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="rebateRate" class="col-sm-3 control-label">默认返佣比例</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="rebateRate" placeholder="例：如果返50%就填写0.5">
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
            <button type="button" class="btn btn-primary" onclick="saveCrmMarketer()">保存</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 修改营销人员模态框 -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">修改营销人员</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="deptName1" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="deptName1" data-live-Search="true">
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="marketerNo1" class="col-sm-3 control-label">营销人员编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="marketerNo1" placeholder="" readOnly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="marketerName1" class="col-sm-3 control-label">营销人员姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="marketerName1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="divisionOptions1" class="col-sm-3 control-label">人员分区</label>
                    <div class="col-sm-8">
                      <label class="btn btn-info col-sm-3">
                        <input type="radio" name="divisionOptions1" id="divisionOptions0" value="0" autocomplete="off"> 前台
                      </label>
                      <label class="btn btn-info col-sm-3 col-sm-offset-1">
                        <input type="radio" name="divisionOptions1" id="divisionOptions1" value="1" autocomplete="off"> 后台
                      </label>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="status1" class="col-sm-3 control-label">在职状态</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="status1">
                        <option value="1">在职</option>
                        <option value="0">离职</option>
                      </select>
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="certType1" class="col-sm-3 control-label">证件类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="certType1" >
                        <option value="0">身份证</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="certNo1" class="col-sm-3 control-label">证件号码</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="certNo1" placeholder="">
                    </div>
                  </div>
                  
                 <!--  <div class="form-group">
                    <label for="gender1" class="col-sm-3 control-label">性别</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="gender" placeholder="">
                      <label class="btn btn-primary col-sm-3">
                        <input type="radio" name="genderOptions1" autocomplete="off"> 男
                      </label>
                      <label class="btn btn-danger col-sm-3 col-sm-offset-1">
                        <input type="radio" name="genderOptions1" autocomplete="off"> 女
                      </label>
                    </div>
                  </div> -->
                  
                  <!-- <div class="form-group">
                    <label for="birthday1" class="col-sm-3 control-label">出生日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="birthday1" placeholder="">
                    </div>
                  </div> -->
                  
                  <div class="form-group">
                    <label for="entryDate1" class="col-sm-3 control-label">入职日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="entryDate1" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="leaveDate1" class="col-sm-3 control-label">离职日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="leaveDate1" placeholder="">
                    </div>
                  </div>
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="rebateRate1" class="col-sm-3 control-label">默认返佣比例</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="rebateRate1" placeholder="例：如果返50%就填写0.5">
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
                    <label for="telephone1" class="col-sm-3 control-label">手机</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="telephone1" placeholder="">
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
            <button type="button" class="btn btn-primary" onclick="updateCrmMarketer()">保存</button>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
