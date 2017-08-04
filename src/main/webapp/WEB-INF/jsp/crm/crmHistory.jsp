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
                        $('#qdepName1').html(optionString);
                        $('#qdepName2').html(optionString);
                        $('#qdepName').selectpicker('refresh');
                        $('#qdepName1').selectpicker('refresh');
                        $('#qdepName2').selectpicker('refresh');
                   }
            });
                 
        });
        
        function queryCrmMediatorHistory(){
            
            $("#mediatorHistoryTable").bootstrapTable(
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
      
        function queryCrmMediatorHistoryParams(params){
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
            $('#crmMediatorHistoryForm').submit();
        }
        
        //查询营销人员信息
        function queryCrmMarketerHistory(){
            
            $("#marketerHistoryTable").bootstrapTable(
                'refresh',{url:"queryCrmMarketerHistory",
                           query: {depCode:$('#qdepName1').val(),
                                   marketerNo:$("#qmarketerNo").val(),
                                   marketerName:$("#qmarketerName").val()
                                  }
                          }
            );
        }
        
        function queryCrmMarketerHistoryParams(params){
            return {depCode:$('#qdepName1').val(),
                marketerNo:$("#qmarketerNo").val(),
                marketerName:$("#qmarketerName").val(),
                pageNumber:params.pageNumber,
                pageSize:params.pageSize}
        }
        
      
        //导出excel
        function exportCrmMarketerHistory(){
            $('#crmMarketerHistoryForm').submit();
        }
      
      
        //查询营业部返利息报表
        function queryCrmCustomerHistory(){
            
            $("#investorTable").bootstrapTable(
                'refresh',{url:"queryCrmCustomerHistory",
                           query: {investorName:$('#qinvestorName').val(),
                                   investorNo:$("#qinvestorNo").val(),
                                   deptCode:$("#qdepName2").val(),
                                   belongType:$("#qbelongType2").val(),
                                   belongTo:$("#qbelongTo2").val()
                                  }
                          }
            );
        }
        
        
        function queryCrmCustomerHistoryParams(params){
            
            return {investorName:$('#qinvestorName').val(),
                investorNo:$("#qinvestorNo").val(),
                deptCode:$("#qdepName2").val(),
                belongType:$("#qbelongType2").val(),
                belongTo:$("#qbelongTo2").val(),
                pageNumber:params.pageNumber,
                pageSize:params.pageSize}
            
        }
        
        
        //导出excel
        function exportCustomerHistory(){
            $('#customerHistoryForm').submit();
            
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toCrmOverview" style="text-decoration: none;">客户关系管理</a> --> <a href="toCrmHistory" style="text-decoration: none;">历史归属查询</a></h1>


          <!-- Nav tabs -->
          <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#mediatorTab" aria-controls="mediatorTab" role="tab" data-toggle="tab">查询居间人历史信息</a></li>
            <li role="presentation"><a href="#marketerTab" aria-controls="marketerTab" role="tab" data-toggle="tab">查询营销人员历史信息</a></li>
            <li role="presentation"><a href="#customerTab" aria-controls="customerTab" role="tab" data-toggle="tab">查询客户历史信息</a></li>
          </ul>
          
          <!-- Tab panes -->
          <div class="tab-content">
          
            <!-- 查询居间人历史信息 -->
            <div role="tabpanel" class="tab-pane fade  in active" id="mediatorTab">
                
            <!-- 查询条件表单 -->
                <form id="crmMediatorHistoryForm" method="post" action="exportCrmMediatorHistory" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
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
                        <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryCrmMediatorHistory()">
                      </div>
                      <div class=" col-sm-10 col-md-2 ">
                        <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportCrmMediatorHistory()">
                      </div>
                    </div>
                </form>
                <!-- 查询条件表单结束 -->

                <h2 class="sub-header">居间人信息</h2>
                <div class="table-responsive">
                  <div id="toolbar" class="btn-group">
                  
                  </div>
                  <table id="mediatorHistoryTable"
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
                         data-url="queryCrmMediatorHistory"
                         data-pagination="true"
                         data-query-params="queryCrmMediatorHistoryParams"
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
                          <th data-field="certType" data-align="center" data-formatter="certTypeFormatter">证件类型</th>
                          <th data-field="certNo" data-align="center" >证件号码</th>
                          <th data-field="effectDate" data-align="center" >生效日期</th>
                          <th data-field="expireDate" data-align="center" >失效日期</th>
                          <th data-field="allocationProportion" data-align="center" >默认分配比例</th>
                          <th data-field="telephone" data-align="center" >联系电话</th>
                      </tr>
                      </thead>
                  </table>
                </div>
                
            </div>
            
            
            
            <div role="tabpanel" class="tab-pane fade  in " id="marketerTab">
            <!-- 查询条件表单 -->
                      <form id="crmMarketerHistoryForm" method="post" action="exportCrmMarketerHistory" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
                          <div class="form-group">
                          
                                <label for="qdepName1" class="col-sm-2 col-md-1 control-label">所在营业部</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="qdepName1" name="depCode" data-live-Search="true">
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
                              <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryCrmMarketerHistory()">
                            </div>
                            <div class=" col-sm-10 col-md-2 ">
                              <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportCrmMarketerHistory()">
                            </div>
                          </div>
                      </form>
                      <!-- 查询条件表单结束 -->
                      
                      <h2 class="sub-header">营销人员信息</h2>
                      <div class="table-responsive">
                        <table id="marketerHistoryTable"
                               class="table table-striped"
                               data-toggle="table" 
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-show-export="true"
                               data-detail-view="false"
                               data-detail-formatter="detailFormatter"
                               data-height="562"
                               data-url="queryCrmMarketerHistory"
                               data-pagination="true"
                               data-query-params="queryCrmMarketerHistoryParams"
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
                            </tr>
                            </thead>
                        </table>
                      </div>
                      
          </div>
          
          <div role="tabpanel" class="tab-pane fade  in active" id="customerTab">
          <!-- 查询条件表单 -->
              <form id="customerHistoryForm" method="post" action="exportCustomerHistory" enctype="application/json;charset=UTF-8" class="form-horizontal" style="margin-top: 30px">
                  <div class="form-group">
                        
                        <label for="qdepName2" class="col-sm-2 col-md-1 control-label">所在营业部</label>
                        <div class="col-sm-10 col-md-2">
                          <select class="selectpicker form-control" id="qdepName2" name="deptCode" data-live-Search="true">
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
                        <label for="qbelongType2" class="col-sm-2 col-md-1 control-label">归属类型</label>
                        <div class="col-sm-10 col-md-2">
                          <select class="selectpicker form-control" id="qbelongType2" name="belongType">
                              <option > </option>
                              <option value="0">营业部</option>
                              <option value="1">营销人员</option>
                              <option value="2">居间人</option>
                          </select>
                        </div>
                        
                        <label for="qbelongTo2" class="col-sm-2 col-md-1 control-label">归属代码</label>
                        <div class="col-sm-10 col-md-2">
                          <input type="text" class="form-control" id="qbelongTo2" name="belongTo">
                        </div>
                        
                  </div>
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10 col-md-2 col-md-offset-4 ">
                      <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryCrmCustomerHistory()">
                    </div>
                    <div class=" col-sm-10 col-md-2 ">
                      <input class="btn btn-default col-xs-7" type="button" value="导出" onclick="exportCustomerHistory()">
                    </div>
                  </div>
              </form>
              <!-- 查询条件表单结束 -->
              
              <h2 class="sub-header">客户基本信息</h2>
              <div class="table-responsive">
                <table id="investorTable"
                       class="table table-striped"
                       data-toggle="table" 
                       data-show-refresh="true"
                       data-show-toggle="true"
                       data-show-columns="true"
                       data-show-export="true"
                       data-detail-view="false"
                       data-detail-formatter="detailFormatter"
                       data-height="562"
                       data-url="queryCrmCustomerHistory"
                       data-query-params-type="pageNum"
                       data-pagination="true"
                       data-side-pagination="server"
                       data-query-params="queryCrmCustomerHistoryParams"
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
                        <th data-field="openDate" data-align="center" >开户日期</th>
                        <th data-field="cancelDate" data-align="center" >销户日期</th>
                        <th data-field="certType" data-align="center" data-formatter="certTypeFormatter">证件类型</th>
                        <th data-field="certNo" data-align="center" >证件号码</th>
                        <th data-field="mobilePhone" data-align="center" >手机</th>
                    </tr>
                    </thead>
                </table>
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
