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
	    function belongTypeFormatter(value,row,index){
	        var result = row.belongType;
	        if(result == '0'){
	            result = '营业部';
	        }else if (result == '1') {
	            result = '营销人员';
	        }else if (result == '2') {
	            result = '居间人';
	        }
	        return result;
	    }
	    
	    function certTypeFormatter(value,row,index){
	        var result = row.certType;
	        if(result == '0'){
	            result = '身份证';
	        }else if (result == '1') {
	            result = '营业执照';
	        }
	        return result;
	    }
    
        function operationFormatter(value,row,index) {
            var html = '<button type="button" id="cog'+row.mediatorNo+'" class="btn btn-default btn-sm" title="设置">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+row.mediatorNo+'" class="btn btn-default btn-sm" title="删除">'
                         + '<i class="glyphicon glyphicon-trash"></i>'
                     + '</button>';
                     
                     $("#mediatorTable").off("click","#cog"+row.mediatorNo);
                     $("#mediatorTable").on("click","#cog"+row.mediatorNo,row,function(event){
                         config(row);
                     });
                     
                     //添加删除事件
                     $("#mediatorTable").off("click","#trash"+row.mediatorNo);
                     $("#mediatorTable").on("click","#trash"+row.mediatorNo,row,function(event){
                         trash(row);
                     });
            return html;
        }
        
        /* 修改任务模态框 */
        function config(row){
            console.log(row);
            $("#mediatorNo1").val(row.mediatorNo);
            $("#mediatorName1").val(row.mediatorName);
            $('#depCode1').val(row.depCode);
            $('#depName1').val(row.depName);
            $('#rebateRate1').val(row.allocationProportion);
            $('#telephone1').val(row.telephone),
            $("#address1").val(row.address);
            $("#email1").val(row.email);
            $("#remark1").val(row.remark);
            $("#certNo1").val(row.certNo);
            $("#effectDate1").val(row.effectDate);
            $("#expireDate1").val(row.expireDate);
            
            $('#isIb1').selectpicker('val', row.isIb);
            //设置bootstrap-select的值
            $('#belongType1').selectpicker('val', row.belongType);
            
            if(row.belongType == "1"){
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
                         
                         $('#belongTo1').html(optionString);
                         $('#belongTo1').selectpicker('refresh');
                         $('#belongTo1').selectpicker('val', row.belongTo);
                    }
             });
            }else if(row.belongType == "0"){
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
                             
                             $('#belongTo1').html(optionString);
                             $('#belongTo1').selectpicker('refresh');
                             $('#belongTo1').selectpicker('val', row.belongTo);
                        }
                 });
            }
            
            $('#status1').selectpicker('val', row.status);
            $('#certType1').selectpicker('val', row.certType);
            
            $("#editModal").modal('show');
        }
        
        /* 删除营销人员 */
        function trash(row){
            if(confirm("删除居间人："+row.mediatorName+"("+row.mediatorNo+")后将不可恢复,确定吗？")){
                var param = {mediatorNo:row.mediatorNo};
                $.ajax({
                    type: "post",
                    url: "removeCrmMediator",
                    //dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(param),
                    success: function (date, status){
                        alert("删除成功");
                        $("#mediatorTable").bootstrapTable('refresh');
                    }
                });
            }
        }
        
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toCrmMediator"]').parent().addClass("active");
            
            
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
            
            $('#qexpireBegin').datepicker({
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
            
            $('#qexpireEnd').datepicker({
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
                                 $('#qdepName').html(optionString);
                                 $('#qdepName').selectpicker('refresh');
                            }
                     });
                }
            });
            
            $('#belongType1').on('changed.bs.select', function (e) {
                var belongType = $('#belongType1').val();
                
                if(belongType == "1"){
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
                             
                             $('#belongTo1').html(optionString);
                             $('#belongTo1').selectpicker('refresh');
                             
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
                                 
                                 $('#belongTo1').html(optionString);
                                 $('#belongTo1').selectpicker('refresh');
                            }
                     });
                }
            });
            
            
            $('#belongTo').on('changed.bs.select', function (e) {
                var belongType = $('#belongType').val();
                var belongTo = $('#belongTo').val();
                if(belongType == "0"){
                    //营业部
                    $('#depName').val($('#belongTo option:selected').text());
                    $('#depCode').val(belongTo);
                }else if (belongType == "1") {
                	//查询营销人员对应的部门
                    var param = {marketerNo:belongTo};
                    $.ajax({
                        url: 'queryCrmMarketer',
                        type: 'post',
                        dataType: 'json',
                        contentType: "application/json;charset=UTF-8",
                        data: JSON.stringify(param),
                        success: function (data) {
                            $('#depName').val(data[0].depName);
                            $('#depCode').val(data[0].depCode);
                        }
                     });
                }
            });
            
            $('#belongTo1').on('changed.bs.select', function (e) {
                var belongType = $('#belongType1').val();
                var belongTo = $('#belongTo1').val();
                if(belongType == "0"){
                    //营业部
                    $('#depName1').val($('#belongTo1 option:selected').text());
                    $('#depCode1').val(belongTo);
                }else if (belongType == "1") {
                	
                    //查询营销人员对应的部门
                    var param = {marketerNo:belongTo};
                    $.ajax({
                        url: 'queryCrmMarketer',
                        type: 'post',
                        contentType: "application/json;charset=UTF-8",
                        dataType: 'json',
                        data: JSON.stringify(param),
                        success: function (data) {
                        	$('#depName1').val(data[0].depName);
                            $('#depCode1').val(data[0].depCode);
                        }
                     });
                    
                }
            });
            
            $("#queryForm").keypress(function(e){
                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                if (eCode == 13){
                    //自己写判断函数
                    queryCrmMediator();
                }
            });
            
        });
        
        function saveCrmMediator(){
            var param = {mediatorNo:$('#mediatorNo').val(),
            		mediatorName:$('#mediatorName').val(),
            		status:$('#status').val(),
            		depCode:$('#depCode').val(),
            		depName:$('#depName').val(),
            		allocationProportion:$('#rebateRate').val(),
            		payType:$('#payType').val(),
            		telephone:$('#telephone').val(),
                    address:$('#address').val(),
                    email:$('#email').val(),
                    certType:$('#certType').val(),
                    certNo:$('#certNo').val(),
                    remark:$('#remark').val(),
                    effectDate:$('#effectDate').val(),
                    expireDate:$('#expireDate').val(),
            		belongType:$('#belongType').val(),
            		belongTo:$('#belongTo').val(),
            		isIb:$('#isIb').val()
                    }
             $.ajax({
                 url: 'saveCrmMediator',
                 type: 'post',
                 contentType: "application/json;charset=UTF-8",
                 data: JSON.stringify(param),
                 success: function (data,status) {
                     $('#addModal').modal('hide');
                     $('#mediatorTable').bootstrapTable('refresh');
                 }
            });
        }
        
        function updateCrmMediator(){
            var param = {mediatorNo:$('#mediatorNo1').val(),
                    mediatorName:$('#mediatorName1').val(),
                    status:$('#status1').val(),
                    depCode:$('#depCode1').val(),
                    depName:$('#depName1').val(),
                    allocationProportion:$('#rebateRate1').val(),
                    payType:$('#payType1').val(),
                    telephone:$('#telephone1').val(),
                    address:$('#address1').val(),
                    email:$('#email1').val(),
                    certType:$('#certType1').val(),
                    certNo:$('#certNo1').val(),
                    remark:$('#remark1').val(),
                    effectDate:$('#effectDate1').val(),
                    expireDate:$('#expireDate1').val(),
                    belongType:$('#belongType1').val(),
                    belongTo:$('#belongTo1').val(),
                    isIb:$('#isIb1').val()
                    }
             $.ajax({
                 url: 'updateCrmMediator',
                 type: 'post',
                 contentType: "application/json;charset=UTF-8",
                 data: JSON.stringify(param),
                 success: function (data,status) {
                     $('#editModal').modal('hide');
                     $('#mediatorTable').bootstrapTable('refresh');
                 }
            });
        }
        
      //查询营业部返利息报表
        function queryCrmMediator(){
            
            $("#mediatorTable").bootstrapTable(
                'refresh',{url:"queryCrmMediator",
                           query: {mediatorNo : $('#qmediatorNo').val(),
                        	       mediatorName : $('#qmediatorName').val(),
                                   deptCode : $('#qdepName').val(),
                                   belongType : $('#qbelongType').val(),
                                   belongTo : $('#qbelongTo').val(),
                                   isIb:$('#qisIb').val()
                                  }
                          }
            );
        }
      
        function queryParams(params){
            return {mediatorNo : $('#qmediatorNo').val(),
                mediatorName : $('#qmediatorName').val(),
                deptCode : $('#qdepName').val(),
                belongType : $('#qbelongType').val(),
                belongTo : $('#qbelongTo').val(),
                expireBegin : $('#qexpireBegin').val(),
                expireEnd : $('#qexpireEnd').val(),
                pageNumber:params.pageNumber,
                pageSize:params.pageSize}
        }
        
        //生成居间人编号
        function generateMediatorNo(){
        	$.ajax({
                url: 'generateMediatorNo',
                type: 'post',
                dataType: 'text',
                contentType: "application/json;charset=UTF-8",
                success: function (data,status) {
                    $('#mediatorNo').val(data);
                }
           });
        }
        
        //导出excel
        function exportCrmMediator(){
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toCrmOverview" style="text-decoration: none;">客户关系管理</a> --> <a href="toCrmMediator" style="text-decoration: none;">居间人信息维护</a></h1>

          <div class="row placeholders">
            <!-- 查询条件表单 -->
                      <form id="queryForm" method="post" action="exportCrmMediator" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                          
                                <label for="qdepName" class="col-sm-2 col-md-1 control-label">所在营业部</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="qdepName" name="deptCode" data-live-Search="true">
                                    </select>
                                </div>
                                
                                <label for="qmediatorNo" class="col-sm-2 col-md-1 control-label">居间人编号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="qmediatorNo" name="mediatorNo">
                                </div>
                          
                                <label for="qmediatorName" class="col-sm-2 col-md-1 control-label">居间人姓名</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="qmediatorName" name="mediatorName">
                                </div>
                            
                          </div>
                          
                          <div class="form-group">
                                <label for="qbelongType" class="col-sm-2 col-md-1 control-label">归属类型</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="qbelongType" name="belongType">
			                          <option > </option>
			                          <option value="0">营业部</option>
			                          <option value="1">营销人员</option>
			                      </select>
                                </div>
                                
                                <label for="qbelongTo" class="col-sm-2 col-md-1 control-label">归属代码</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="qbelongTo" name="belongTo">
                                </div>
                                
                                 <label for="qisIb" class="col-sm-2 col-md-1 control-label">IB居间区分</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="qisIb" name="isIb">
			                          <option > </option>
			                          <option value="1">是</option>
			                          <option value="0">否</option>
			                      </select>
                                </div>
                                
                          </div>
                          
                          <div class="form-group">
                                <label for="qexpireBegin" class="col-sm-2 col-md-1 control-label">开始失效日期</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="qexpireBegin" name="expireBegin">
                                </div>
                                
                                <label for="qexpireEnd" class="col-sm-2 col-md-1 control-label">结束失效日期</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="qexpireEnd" name="expireEnd">
                                </div>
                                
                          </div>
                          
                          <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-4 ">
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryCrmMediator()">
                            </div>
                            <div class=" col-sm-10 col-md-2 ">
                              <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportCrmMediator()">
                            </div>
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
          </div>

          <h2 class="sub-header">居间人信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="新增居间人">
                    <i class="glyphicon glyphicon-plus">新建</i>
                </button>
            </div>
            <table id="mediatorTable"
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
                   data-url="queryCrmMediator"
                   data-pagination="true"
                   data-query-params="queryParams"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="true">
                <thead>
                <tr>
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="depName" data-align="center" >所属营业部</th>
                    <th data-field="mediatorNo" data-align="center" >居间人编号</th>
                    <th data-field="mediatorName" data-align="center" >居间人名称</th>
                    <th data-field="belongType" data-align="center" data-formatter="belongTypeFormatter">归属类型</th>
                    <th data-field="belongTo" data-align="center" >归属代码</th>
                    <th data-field="belongToName" data-align="center" >归属名称</th>
                    <!-- <th data-field="status" data-align="center" >在职状态</th> -->
                    <th data-field="certType" data-align="center" data-formatter="certTypeFormatter">证件类型</th>
                    <th data-field="certNo" data-align="center" >证件号码</th>
                    <th data-field="effectDate" data-align="center" >生效日期</th>
                    <th data-field="expireDate" data-align="center" >失效日期</th>
                    <th data-field="allocationProportion" data-align="center" >默认分配比例</th>
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
    
    <!-- 新建居间人模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">新增居间人</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="depName" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="hidden" class="form-control" id="depCode" placeholder="" readOnly>
                      <input type="text" class="form-control" id="depName" placeholder="" readOnly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="mediatorNo" class="col-sm-3 control-label">居间人编号</label>
                    <div class="col-sm-8">
                        <div class="input-group">
                            <span class="input-group-btn">
                              <button class="btn btn-info" type="button" onclick="generateMediatorNo()">生成编号</button>
                            </span>
                            <input type="text" class="form-control" id="mediatorNo" placeholder="">
                        </div>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="mediatorName" class="col-sm-3 control-label">居间人姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="mediatorName" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="isIb" class="col-sm-3 control-label">IB居间区分</label>
                    <div class="col-sm-8">
                     <select class="selectpicker form-control" id="isIb" >
                          <option value="1">是</option>
                          <option value="0">否</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="belongType" class="col-sm-3 control-label">归属类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="belongType" >
                          <option value="0">营业部</option>
                          <option value="1">营销人员</option>
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
                  
                  <div class="form-group">
                    <label for="effectDate" class="col-sm-3 control-label">生效日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="effectDate" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="expireDate" class="col-sm-3 control-label">失效日期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="expireDate" placeholder="">
                    </div>
                  </div>
                  
                  <!-- <div class="form-group">
                    <label for="gender" class="col-sm-3 control-label">返佣周期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="gender" placeholder="">
                      <label class="btn btn-default col-sm-3">
					    <input type="radio" name="genderOptions" autocomplete="off" checked="checked"> 按月返佣
					  </label>
					  <label class="btn btn-default col-sm-3 col-sm-offset-1">
					    <input type="radio" name="genderOptions" autocomplete="off"> 按年返佣
					  </label>
                    </div>
                  </div> -->
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="rebateRate" class="col-sm-3 control-label">默认返佣比例</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="rebateRate" placeholder="例：如果返50%就填写0.5">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="accountRate" class="col-sm-3 control-label">居间人核算比例</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="accountRate" placeholder="例：如果返50%就填写0.5">
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
            <button type="button" class="btn btn-primary" onclick="saveCrmMediator()">保存</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 修改居间人模态框 -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">新增居间人</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="depName1" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="hidden" class="form-control" id="depCode1" placeholder="" readOnly>
                      <input type="text" class="form-control" id="depName1" placeholder="" readOnly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="mediatorNo1" class="col-sm-3 control-label">居间人编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="mediatorNo1" placeholder="" readOnly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="mediatorName1" class="col-sm-3 control-label">居间人姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="mediatorName1" placeholder="">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="isIb1" class="col-sm-3 control-label">IB居间区分</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="isIb1" data-live-Search="true">
                          <option value="1">是</option>
                          <option value="0">否</option>
                      </select>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="belongType1" class="col-sm-3 control-label">归属类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="belongType1" data-live-Search="true">
                          <option value="0">营业部</option>
                          <option value="1">营销人员</option>
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
                  
                  <!-- <div class="form-group">
                    <label for="gender" class="col-sm-3 control-label">返佣周期</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="gender" placeholder="">
                      <label class="btn btn-default col-sm-3">
                        <input type="radio" name="genderOptions1" autocomplete="off" checked="checked"> 按月返佣
                      </label>
                      <label class="btn btn-default col-sm-3 col-sm-offset-1">
                        <input type="radio" name="genderOptions1" autocomplete="off"> 按年返佣
                      </label>
                    </div>
                  </div> -->
                  
                  <hr>
                  
                  <div class="form-group">
                    <label for="rebateRate1" class="col-sm-3 control-label">默认返佣比例</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="rebateRate1" placeholder="例：如果返50%就填写0.5">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="accountRate1" class="col-sm-3 control-label">居间人核算比例</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="accountRate1" placeholder="例：如果返50%就填写0.5">
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
            <button type="button" class="btn btn-primary" onclick="updateCrmMediator()">保存</button>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
