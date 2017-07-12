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
    
/*         function operationFormatter(value,row,index) {
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
        } */
        
        
        
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toCrmHistory"]').parent().addClass("active");
            
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
                        
                        $('#qdepName').html(optionString);
                        $('#qdepName').selectpicker('refresh');
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
            
        });
        
        
        function queryCrmMediator(){
            
            $("#mediatorTable").bootstrapTable(
                'refresh',{url:"queryCrmMediatorHistory",
                           query: {mediatorNo : $('#qmediatorNo').val(),
                        	       mediatorName : $('#qmediatorName').val(),
                                   deptCode : $('#qdepName').val(),
                                   belongType : $('#qbelongType').val(),
                                   belongTo : $('#qbelongTo').val()
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
        
        
        //导出excel
        function exportCrmMediatorHistory(){
        	$('#mediatorHistoryForm').submit();
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toCrmOverview" style="text-decoration: none;">客户关系管理</a> --> <a href="toCrmHistory" style="text-decoration: none;">历史归属查询</a></h1>

          <div class="row placeholders">
            <!-- 查询条件表单 -->
                      <form id="selectMediatorCustomerRelation" method="post" action="exportCrmMediatorHistory" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
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
                              <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportCrmMediatorHistory()">
                            </div>
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
          </div>

          <h2 class="sub-header">居间人信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
            
            </div>
            <table id="mediatorTable"
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
                   data-url="queryCrmMediatorHistory"
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
                    <th data-field="certType" data-align="center" data-formatter="certTypeFormatter">证件类型</th>
                    <th data-field="certNo" data-align="center" >证件号码</th>
                    <th data-field="effectDate" data-align="center" >生效日期</th>
                    <th data-field="expireDate" data-align="center" >失效日期</th>
                    <th data-field="allocationProportion" data-align="center" >默认分配比例</th>
                    <th data-field="telephone" data-align="center" >联系电话</th>
                    <!-- <th data-field="" data-formatter="operationFormatter">操作</th> -->
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
    

  </body>
</html>
