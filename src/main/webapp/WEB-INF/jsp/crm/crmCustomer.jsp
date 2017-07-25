<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome </title>
    
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
            var html = '<button type="button" id="cog'+row.investorNo+'" class="btn btn-default btn-sm" title="设置">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button> &nbsp;'
                     
                     + '<button type="button" id="trash'+row.investorNo+'" class="btn btn-default btn-sm" title="删除">'
                         + '<i class="glyphicon glyphicon-trash"></i>'
                     + '</button>';
                     
            $("#investorTable").off("click","#cog"+row.investorNo);
            $("#investorTable").on("click","#cog"+row.investorNo,row,function(event){
                config(row);
            });
            
          //添加删除事件
            $("#investorTable").off("click","#trash"+row.investorNo);
            $("#investorTable").on("click","#trash"+row.investorNo,row,function(event){
                trash(row);
            });
            return html;
        }
        
        /* 修改任务模态框 */
        function config(row){
            
        	$('#deptCode1').val(row.deptCode);
        	$('#deptName1').val(row.deptName);
        	$('#investorNo1').val(row.investorNo);
        	$('#investorName1').val(row.investorName);
        	$('#openDate1').val(row.openDate);
        	$('#cancelDate1').val(row.cancelDate);
        	$('#effectDate1').val(row.effectDate);
        	$('#expireDate1').val(row.expireDate);
        	$('#certNo1').val(row.certNo);
        	$('#postcode1').val(row.postcode);
        	$('#address1').val(row.address);
        	$('#mobilePhone1').val(row.mobilePhone);
        	$('#email1').val(row.email);
        	$('#contractNo1').val(row.contractNo);
        	$('#remark1').val(row.remark);
        	
        	$('#certType1').selectpicker('val', row.certType);
        	$('#belongType1').selectpicker('val', row.belongType);
        	if(row.belongType == "2"){
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
                         
                         $('#belongTo1').html(optionString);
                         $('#belongTo1').selectpicker('refresh');
                         $('#belongTo1').selectpicker('val', row.belongTo);
                    }
             });
            }else if(row.belongType == "1"){
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
            }else if(row.belongType == "0"){      //营业部      belongTo1
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
        	
        	$("#editModal").modal('show');
        	
        }
        
        /* 删除客户信息 */
        function trash(row){
            if(confirm("删除客户："+row.investorName+"("+row.investorNo+")后将不可恢复,确定吗？")){
                var param = {investorNo:row.investorNo};
                $.ajax({
                    type: "post",
                    url: "removeCrmCustomer",
                    //dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(param),
                    success: function (date, status){
                        alert("删除成功");
                        $("#investorTable").bootstrapTable('refresh');
                    }
                });
            }
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
            
            $('#cancelDate').datepicker({
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
            
            $('#cancelDate1').datepicker({
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
                if(belongType == "2"){
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
                }else if(belongType == "1"){
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
                if(belongType == "2"){
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
                             
                             $('#belongTo1').html(optionString);
                             $('#belongTo1').selectpicker('refresh');
                        }
                 });
                }else if(belongType == "1"){
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
                }else if(belongType == "0"){    //----营业部
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
                                 
                                 $('#belongTo1').selectpicker('val',$('#deptCode1').val());
                            }
                     });  
                }
            });
            
            
            $('#belongTo').on('changed.bs.select', function (e) {
                var belongType = $('#belongType').val();
                var belongTo = $('#belongTo').val();
                if(belongType == "0"){
                    //营业部
                    $('#deptCode').val(belongTo);
                    $('#deptName').val($('#belongTo option:selected').text());
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
                            $('#deptName').val(data[0].depName);
                            $('#deptCode').val(data[0].depCode);
                        }
                     });
                }else if (belongType == "2") {
                    //居间人
                	var param = {mediatorNo:belongTo};
                    $.ajax({
                        url: 'queryCrmMediator',
                        type: 'post',
                        dataType: 'json',
                        contentType: "application/json;charset=UTF-8",
                        data: JSON.stringify(param),
                        success: function (data) {
                            $('#deptName').val(data[0].depName);
                            $('#deptCode').val(data[0].depCode);
                        }
                     });
                }
            });
            
            $('#belongTo1').on('changed.bs.select', function (e) {
                var belongType = $('#belongType1').val();
                var belongTo = $('#belongTo1').val();
                if(belongType == "0"){
                    //营业部
                    $('#deptCode1').val(belongTo);
                    $('#deptName1').val($('#belongTo1 option:selected').text());
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
                            $('#deptCode1').val(data[0].depCode);
                            $('#deptName1').val(data[0].depName);
                        }
                     });
                }else if (belongType == "2") {
                    //居间人
                    var param = {mediatorNo:belongTo};
                    $.ajax({
                        url: 'queryCrmMediator',
                        type: 'post',
                        dataType: 'json',
                        contentType: "application/json;charset=UTF-8",
                        data: JSON.stringify(param),
                        success: function (data) {
                            $('#deptCode1').val(data[0].depCode);
                            $('#deptName1').val(data[0].depName);
                        }
                     });
                }
            });
            
            
            $("#queryForm").keypress(function(e){
                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                if (eCode == 13){
                    //自己写判断函数
                    queryCrmCustomer();
                }
            });
            
        });
        
        function saveCrmCustomer(){
            
            var param = {
            		     deptCode:$('#deptCode').val(),
                         investorNo:$('#investorNo').val(),
                         investorName:$('#investorName').val(),
                         openDate:$('#openDate').val(),
                         cancelDate:$('#cancelDate').val(),
                         effectDate:$('#effectDate').val(),
                         expireDate:$('#expireDate').val(),
                         certType:$('#certType').val(),
                         certNo:$('#certNo').val(),
                         postcode:$('#postcode').val(),
                         address:$('#address').val(),
                         mobilePhone:$('#mobilePhone').val(),
                         email:$('#email').val(),
                         contractNo:$('#contractNo').val(),
                         remark:$('#remark').val(),
                         belongType:$('#belongType').val(),
                         belongTo:$('#belongTo').val()
                         }
            $.ajax({
                url: 'saveCrmCustomer',
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
        
        function updateCrmCustomer(){
            
            var param = {
                         deptCode:$('#deptCode1').val(),
                         investorNo:$('#investorNo1').val(),
                         investorName:$('#investorName1').val(),
                         openDate:$('#openDate1').val(),
                         cancelDate:$('#cancelDate1').val(),
                         effectDate:$('#effectDate1').val(),
                         expireDate:$('#expireDate1').val(),
                         certType:$('#certType1').val(),
                         certNo:$('#certNo1').val(),
                         postcode:$('#postcode1').val(),
                         address:$('#address1').val(),
                         mobilePhone:$('#mobilePhone1').val(),
                         email:$('#email1').val(),
                         contractNo:$('#contractNo1').val(),
                         remark:$('#remark1').val(),
                         belongType:$('#belongType1').val(),
                         belongTo:$('#belongTo1').val()
                         }
            $.ajax({
                url: 'updateCrmCustomer',
                type: 'post',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(param),
                success: function (data,status) {
                    $('#editModal').modal('hide')
                    $("#investorTable").bootstrapTable('refresh');
                }
            });
        }
        
      //查询营业部返利息报表
        function queryCrmCustomer(){
            
            $("#investorTable").bootstrapTable(
                'refresh',{url:"queryCrmCustomer",
                           query: {investorName:$('#qinvestorName').val(),
                        	       investorNo:$("#qinvestorNo").val(),
                        	       deptCode:$("#qdepName").val(),
                        	       belongType:$("#qbelongType").val(),
                        	       belongTo:$("#qbelongTo").val()
                                  }
                          }
            );
        }
      
      function queryParams(params){
    	  return {investorName:$('#qinvestorName').val(),
              investorNo:$("#qinvestorNo").val(),
              deptCode:$("#qdepName").val(),
              belongType:$("#qbelongType").val(),
              belongTo:$("#qbelongTo").val(),
              pageNumber:params.pageNumber,
              pageSize:params.pageSize}
      }
      
      
    //导出excel
      function exportCustomer(){
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toCrmOverview" style="text-decoration: none;">客户关系管理</a> --> <a href="toCrmCustomer" style="text-decoration: none;">客户信息维护</a></h1>

          <div class="row placeholders">
            
            <!-- 查询条件表单 -->
                      <form id="queryForm" method="post" action="exportCustomer" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                                
                                <label for="qdepName" class="col-sm-2 col-md-1 control-label">所在营业部</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="qdepName" name="deptCode" data-live-Search="true">
                                    </select>
                                </div>
                          
                                <label for="qinvestorNo" class="col-sm-2 col-md-1 control-label">投资者账号</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="qinvestorNo" name="investorNo">
                                </div>
                            
                                <label for="qinvestorName" class="col-sm-2 col-md-1 control-label">投资者姓名</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="qinvestorName" name="investorName">
                                </div>
                                
                          </div>
                          <div class="form-group">
                                <label for="qbelongType" class="col-sm-2 col-md-1 control-label">归属类型</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="qbelongType" name="belongType">
                                      <option > </option>
                                      <option value="0">营业部</option>
                                      <option value="1">营销人员</option>
                                      <option value="2">居间人</option>
                                      <option value="9">无归属</option>
                                  </select>
                                </div>
                                
                                <label for="qbelongTo" class="col-sm-2 col-md-1 control-label">归属代码</label>
                                <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="qbelongTo" name="belongTo">
                                </div>
                                
                          </div>
                          
                          <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-4 ">
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryCrmCustomer()">
                            </div>
                            <div class=" col-sm-10 col-md-2 ">
                              <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportCustomer()">
                            </div>
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
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
                   data-detail-view="false"
                   data-detail-formatter="detailFormatter"
                   data-height="562"
                   data-url="queryCrmCustomer"
                   data-query-params-type="pageNum"
                   data-pagination="true"
                   data-side-pagination="server"
                   data-query-params="queryParams"
                   data-method="post"
                   data-page-list="[5, 10, 20, 50]"
                   data-search="false">
                <thead>
                <tr>
                    <!-- <th data-field="state" data-checkbox="true"></th> -->
                    <th data-field="deptName" data-align="center" >所属营业部</th>
                    <th data-field="investorNo" data-align="center" >投资者编号</th>
                    <th data-field="investorName" data-align="center" >投资者姓名</th>
                    <th data-field="belongType" data-align="center" data-formatter="belongTypeFormatter">归属类型</th>
                    <th data-field="belongTo" data-align="center" >归属代码</th>
                    <th data-field="belongToName" data-align="center" >归属名称</th>
                    <th data-field="openDate" data-align="center" >开户日期</th>
                    <th data-field="cancelDate" data-align="center" >销户日期</th>
                    <th data-field="certType" data-align="center" data-formatter="certTypeFormatter">证件类型</th>
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
              <form id="addForm" class="form-horizontal" role="form">
                  
                  <div class="form-group">
                    <label for="deptName" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="hidden" class="form-control" id="deptCode" placeholder="" readOnly>
                      <input type="text" class="form-control" id="deptName" placeholder="" readOnly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="investorNo" class="col-sm-3 control-label">投资者编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="investorNo" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="investorName" class="col-sm-3 control-label">投资者姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="investorName" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="belongType" class="col-sm-3 control-label">归属类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="belongType" data-live-Search="true">
                          <option value="0">营业部</option>
                          <option value="1">营销人员</option>
                          <option value="2">居间人</option>
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
                      <select class="selectpicker form-control" id="certType" data-live-Search="true">
                          <option value="0">身份证</option>
                          <option value="1">营业执照</option>
                          <option value="2">组织机构代码</option>
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
                    <label for="openDate" class="col-sm-3 control-label">开户日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="openDate" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="cancelDate" class="col-sm-3 control-label">销户日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="cancelDate" placeholder="">
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
                    <label for="mobilePhone" class="col-sm-3 control-label">手机</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="mobilePhone" placeholder="">
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
            <button type="button" class="btn btn-primary" onclick="saveCrmCustomer()">保存</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 修改客户模态框 -->
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
                      <input type="hidden" class="form-control" id="deptCode1" placeholder="" readOnly>
                      <input type="text" class="form-control" id="deptName1" placeholder="" readOnly>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="investorNo1" class="col-sm-3 control-label">投资者编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="investorNo1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="investorName1" class="col-sm-3 control-label">投资者姓名</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="investorName1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="belongType1" class="col-sm-3 control-label">归属类型</label>
                    <div class="col-sm-8">
                      <select class="selectpicker form-control" id="belongType1" data-live-Search="true">
                          <option value="0">营业部</option>
                          <option value="1">营销人员</option>
                          <option value="2">居间人</option>
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
                      <select class="selectpicker form-control" id="certType1" data-live-Search="true">
                          <option value="0">身份证</option>
                          <option value="1">营业执照</option>
                          <option value="2">组织机构代码</option>
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
                    <label for="openDate1" class="col-sm-3 control-label">开户日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="openDate1" placeholder="">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <label for="cancelDate1" class="col-sm-3 control-label">销户日期</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="cancelDate1" placeholder="">
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
            <button type="button" class="btn btn-primary" onclick="updateCrmCustomer()">保存</button>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
