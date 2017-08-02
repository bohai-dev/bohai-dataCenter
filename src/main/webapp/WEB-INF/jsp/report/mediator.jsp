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
    <!-- datepicker -->
    <link href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css" rel="stylesheet">
    
    <!-- 菜单树 -->
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <!-- table插件 -->
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <!-- optionally if you need translation for your language then include 
        locale file as mentioned below -->
    <script src="resources/fileInput/js/locales/zh.js"></script>
    <!-- bootstrap-select -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    <!-- datepicker -->
    <script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
   
    
    
    <script type="text/javascript">
        function operationFormatter(value,row,index) {
        
            var html = '<button type="button" id="cog'+index+'" class="btn btn-default btn-sm" title="修改">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button>';
                    
            //添加修改事件
             $("#mediatorTable").off("click","#cog"+index);
             $("#mediatorTable").on("click","#cog"+index,row,function(event){
                   config(row);
                });       
            return html;
        }
        /* 修改任务模态框 */
        function config(row){
            //console.log(row);
            $("#reportMonth1").val(row.reportMonth);  
            $("#mediatorCode1").val(row.mediatorNo);
            $("#mediatorName1").val(row.mediatorName);
            $("#depName1").val(row.depName);
            $("#origInterest1").val(row.origInterest);
            $("#origExchangeRebate1").val(row.origExchangeRebate);
            $("#modInterest1").val(row.modInterest);
            $("#modExchangeRebate1").val(row.modExchangeRebate);
            $("#remark1").val(row.remark);
            
            $("#editModal").modal('show');
        }
        
        //更新营业部信息
        function updateMediator(){
          var param = {reportMonth:$('#reportMonth1').val(),
            		  mediatorNo:$('#mediatorCode1').val(),
            		  modInterest:$('#modInterest1').val(),
            		  modExchangeRebate:$('#modExchangeRebate1').val(),                   
                      remark:$('#remark1').val()
                    }
           $.ajax({
               url: 'modifyMediatorReport',
               type: 'post',
               contentType: "application/json;charset=UTF-8",
               data: JSON.stringify(param),
               success: function (data,status) {
                   $('#editModal').modal('hide')
                   $("#mediatorTable").bootstrapTable('refresh');
                   }
           });
        }
        
        
        //加载页面后自动初始化
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toMediatorReport"]').parent().addClass("active");
            
            
            //初始化  加载select项
            $('.selectpicker').selectpicker();
            
            //绑定初始化方法
            $('#depCode').on('loaded.bs.select', function (e) {
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
                         
                         $('#depCode').html(optionString);
                         $('#depCode').selectpicker('refresh');
                        
                    }
                });
            });
            
            /* 初始化datepicker */
            $('#reportMonth').datepicker({
                format: "yyyy-mm",
                  startView: 1,
                  minViewMode: 1,
                  maxViewMode: 2,
                  todayBtn: "linked",
                  clearBtn: true,
                  language: "zh-CN",
                  autoclose: true,
                  todayHighlight: true
            });
            
        });
        
        //查询居间人人员信息
        function queryMediator(){
            
            $("#mediatorTable").bootstrapTable(
                'refresh',{url:"queryMediatorReport",
                           query: {mediatorNo:$('#mediatorNo').val(),
                                   mediatorName:$("#mediatorName").val(),
                                   depNo:$("#depNo").val(),
                        	       reportMonth:$("#reportMonth").val(),
                        	       isChange:$("#isChange").val()
                           }
                          }
            );
        }
        
        function queryParams(params){
          return {mediatorNo:$('#mediatorNo').val(),
            	 mediatorName:$("#mediatorName").val(),
            	 depNo:$("#depNo").val(),
            	 reportMonth:$("#reportMonth").val(),
            	 isChange:$("#isChange").val(),
                 pageNumber:params.pageNumber,
                 pageSize:params.pageSize}
        }
        
        function isNull(value){
            if(value == "" || value == undefined || value == null){
                return true;
            }else{
                return false;
            }
        } 
        
        function numberFormate(value,row,index) {
            //console.log(value);
            if(isNull(value)){
                return null;
            }
            return (value+'').replace(/(\d)(?=(\d{3})+\.)/g, '$1,');//使用正则替换，每隔三个数加一个','  
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toBusinessReport" style="text-decoration: none;">统计报表</a> --> <a href="toMediatorReport" style="text-decoration: none;">居间人提成</a></h4>
              
          

              
              <!-- Tab panes -->
              <div class="tab-content">
               
                <div role="tabpanel" class="tab-pane fade  in active" id="messages">
                
                    <form class="form-horizontal" style="margin-top: 30px" id="customerForm">
                        <div class="form-group">
                        
                              <!-- <label for="reportMonth1" class="col-sm-2 col-md-1 col-md-offset-1 control-label">统计年月</label>
                              <div class="col-sm-10 col-md-2">
                                <input type="text" class="form-control" id="reportMonth1">
                              </div> -->
                              
                              <label for="mediatorNo" class="col-sm-2 col-md-1 control-label">居间人编号</label>
                              <div class="col-sm-10 col-md-2">
                                <input type="text" class="form-control" id="mediatorNo">
                              </div>
                              
                              <label for="mediatorName" class="col-sm-2 col-md-1 control-label">居间人名称</label>
                              <div class="col-sm-10 col-md-2">
                                <input type="text" class="form-control" id="mediatorName">
                              </div><br><br><br>
                              <label for="depCode" class="col-sm-2 col-md-1 control-label">所在营业部</label>
                               <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="depCode" name="depName" data-live-Search="true">
                                    </select>
                               </div>
                               <label for="reportMonth" class="col-sm-2 col-md-1 control-label">统计月份</label>
                               <div class="col-sm-10 col-md-2">
                                  <input type="text" class="form-control" id="reportMonth">
                               </div>
                               <br><br><br>
                               <label for="isChange" class="col-sm-2 col-md-1 control-label">是否修改过</label>
                                <div class="col-sm-10 col-md-2">
                                  <select class="selectpicker form-control" id="isChange" name="isChange">
			                          <option > </option>
			                          <option value="1">是</option>
			                          <option value="0">否</option>
			                      </select>
                                </div>
                              <div class="col-sm-10 col-md-2 col-md-offset-1 ">
                                  <input class="btn btn-default col-xs-7" type="button" value="查询" onclick="queryMediator()">
                              </div>
                              
                             
                        </div>
                          
                    </form>
              
                    
                    <div class="table-responsive" id="investorPrintArea">
                      <div id="toolbar" class="btn-group">
                        <div id="toolbar" class="btn-group">
                            <span id="invetorOverView"></span>
                        </div>
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
                             data-height="542"
                             data-url="queryMediatorReport"
                             data-pagination="true"
                             data-side-pagination="server"
                             data-query-params-type="pageNum"
                             data-query-params="queryParams"
                             data-method="post"
                             data-page-list="[5, 10, 20, 50]"
                             data-height="300">
                          <thead>
                          <tr>
                              <!-- <th data-field="state" data-checkbox="true"></th> -->
                              <th data-field="reportMonth" data-align="center" >统计月份</th>
                              <th data-field="mediatorNo" data-align="center" >居间人编号</th>
                              <th data-field="mediatorName" data-align="center" >居间人名称</th>
                              <th data-field="depNo" data-align="center" >营业部编号</th>
                              <th data-field="depName" data-align="center" >营业部名称</th>
                              <th data-field="origInterest" data-align="center" data-formatter="numberFormate">原始利息</th>
                              <th data-field="origExchangeRebate" data-align="center" data-formatter="numberFormate">原始交返</th>
                              <th data-field="modInterest" data-align="center" data-formatter="numberFormate">修改后利息</th>
                              <th data-field="modExchangeRebate" data-align="center" data-formatter="numberFormate">修改后交返</th>
                              <th data-field="" data-formatter="operationFormatter">操作</th>
                          </tr>
                          </thead>
                      </table>
                    </div>
                    
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
    
<!-- 修改营业部信息 -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">修改居间人提成信息</h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label for="reportMonth1" class="col-sm-3 control-label">统计月份</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="reportMonth1" placeholder="" readonly>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="mediatorName1" class="col-sm-3 control-label">居间人编号</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="mediatorCode1" placeholder="" readonly>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="mediatorName1" class="col-sm-3 control-label">居间人名称</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="mediatorName1" placeholder="" readonly>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="depName1" class="col-sm-3 control-label">营业部名称</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="depName1" placeholder="" readonly>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="origInterest1" class="col-sm-3 control-label">原始利息</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="origInterest1" placeholder="" readonly>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="origExchangeRebate1" class="col-sm-3 control-label">原始交返</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="origExchangeRebate1" placeholder="" readonly>
                    </div>
                  </div>
                   
                  <hr>
                   <div class="form-group">
                    <label for="modInterest1" class="col-sm-3 control-label">修改后利息</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="modInterest1" placeholder="">
                    </div>
                  </div>
                   <div class="form-group">
                    <label for="modExchangeRebate1" class="col-sm-3 control-label">修改后交返</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="modExchangeRebate1" placeholder="">
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
            <button type="button" class="btn btn-primary" onclick="updateMediator()">修改</button>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
