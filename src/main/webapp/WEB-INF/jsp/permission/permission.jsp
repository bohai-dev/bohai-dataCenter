<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome  </title>
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    
    
    <script type="text/javascript">
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            
        });
        
        function operationFormatter(value,row,index) {
            
            var html = '<button type="button" id="cog'+row.username+'" class="btn btn-default btn-sm" title="设置">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button>'
                     /* + '<button type="button" id="trash'+row.username+'" class="btn btn-default btn-sm" title="删除任务">'
                         + '<i class="glyphicon glyphicon-trash"></i>'
                     + '</button>' */;
            
            $("#sysUserTable").on("click","#cog"+row.username,row,function(event){
                
                //console.log(row);
                config(row);
                
            });
            return html;
        }
        
        /* 修改任务模态框 */
        function config(row){
            
            $('#userName').val(row.username);
            
            var param = {userName:row.username};
            
            $.ajax({
                url: 'queryUserPermissions',
                type: 'post',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(param),
                success: function (result) {
                    
                    //$('#permissionTree').treeview({data: result,showCheckbox:true});
                    var checkableTree=$('#permissionTree').treeview({
                		data:result,
                		showCheckbox: true,
                		levels:1,
                		
                		onNodeChecked:function(event,node){
                			console.log(node.id);
                			//doCheckedNode(node);
                			nodeChecked(event,node);
                		},
                		
                		onNodeUnchecked:function(event,node){
                			//child 无父无子
                			
                			//doUncheckedNode(node);
                			nodeUnchecked(event,node);
                		}
                	});
                	 $('#btn-check-all').on('click', function (e) {  
                		 $('#permissionTree').treeview('checkAll', { silent: true});  
                     });  
                     $('#btn-uncheck-all').on('click', function (e) {  
                    	 $('#permissionTree').treeview('uncheckAll', { silent: true });  
                     }); 
                    
                }
            });
            
            $("#configModal").modal('show');
            
        }
        
        function updateUsersPermissions(){
            
            var checkedList = $('#permissionTree').treeview('getChecked');
            console.info(checkedList);
            
            var userName = $('#userName').val();
            var param = {userName:userName,
                         permissionList:checkedList}
            
            $.ajax({
                url: 'updateUserPermissions',
                type: 'post',
                contentType: "application/json;charset=UTF-8",
                data:JSON.stringify(param),
                success: function (result) {
                    alert("保存成功");
                    $('#configModal').modal('hide')
                }
            });
            
        }
        
        /* 选中节点事件 */
        /* function doCheckedNode(node){
			//parent		
			checkAllParent(node);
			
			var childNodes=node.nodes;
			console.log(childNodes);
			if(childNodes!=null){
				for(var i=0;i<childNodes.length;i++){
					$('#permissionTree').treeview('checkNode',childNodes[i].nodeId,{silent:true});
				}
			}
			
		}
        
        function doUncheckedNode(node){
            //初始化
            if(node&&node.nodes&&0<node.nodes.length){
                var childNodes=node.nodes;
                for(var i=0;i<childNodes.length;i++){
                    //取消选中
                    $('#permissionTree').treeview('uncheckNode',childNodes[i].nodeId,{silent:true});
                    //递归
                    doUncheckedNode(childNodes[i]);
                    
                }
            }
        }
        
        function checkAllParent(node){ 
            
        	$('#permissionTree').treeview('checkNode',node.nodeId,{silent:true});  
            var parentNode = $('#permissionTree').treeview('getParent',node);  
            if(parentNode && 0 <= parentNode.nodeId){  
                checkAllParent(parentNode);  
            }else{  
                return;  
            }  
        } */  
        
         var nodeCheckedSilent = false;
        function nodeChecked (event, node){  
            if(nodeCheckedSilent){  
                return;  
            }  
            nodeCheckedSilent = true;  
            checkAllParent(node);  
            checkAllSon(node);  
            nodeCheckedSilent = false;  
        }  
          
        var nodeUncheckedSilent = false;  
        
        function nodeUnchecked  (event, node){
            if(nodeUncheckedSilent)  
                return;  
            nodeUncheckedSilent = true;  
            //uncheckAllParent(node);  
            uncheckAllSon(node);  
            nodeUncheckedSilent = false;  
        }  
          
        //选中全部父节点  
        function checkAllParent(node){ 
            $('#permissionTree').treeview('checkNode',node.nodeId,{silent:true});  
            var parentNode = $('#permissionTree').treeview('getParent',node.nodeId);  
            if(!("nodeId" in parentNode)){  
                return;  
            }else{  
                checkAllParent(parentNode);  
            }  
        }  
        //取消全部父节点  
        function uncheckAllParent(node){
            $('#permissionTree').treeview('uncheckNode',node.nodeId,{silent:true});  
            var siblings = $('#permissionTree').treeview('getSiblings', node.nodeId);  
            var parentNode = $('#permissionTree').treeview('getParent',node.nodeId);  
            if(!("nodeId" in parentNode)) {
                return;  
            }
            var isAllUnchecked = true;  //是否全部没选中  
            for(var i in siblings){
                if(siblings[i].state.checked){
                    isAllUnchecked=false;  
                    break;
                }
            }
            if(isAllUnchecked){  
                uncheckAllParent(parentNode);  
            }  
          
        }  
          
        //级联选中所有子节点  
        function checkAllSon(node){  
            $('#permissionTree').treeview('checkNode',node.nodeId,{silent:true});  
            if(node.nodes!=null&&node.nodes.length>0){  
                for(var i in node.nodes){  
                    checkAllSon(node.nodes[i]);  
                }  
            }  
        }  
        //级联取消所有子节点  
        function uncheckAllSon(node){  
            $('#permissionTree').treeview('uncheckNode',node.nodeId,{silent:true});  
            if(node.nodes!=null&&node.nodes.length>0){  
                for(var i in node.nodes){  
                    uncheckAllSon(node.nodes[i]);  
                }  
            }  
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="" style="text-decoration: none;">权限管理</a> --> <a href="toPermission" style="text-decoration: none;">用户权限管理</a></h1>

          <div class="row placeholders">
          </div>

          <h2 class="sub-header">用户权限信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
            
            </div>
            <table id="sysUserTable"
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
    </div>
    
    <footer class="footer">
      <div class="container">
        <p class="text-muted">Place sticky footer content here.</p>
      </div>
    </footer>
    
    
        <!-- 新建客户模态框 -->
    <div class="modal fade" id="configModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">用户权限设置</h4>
          </div>
          <div class="modal-body">
	          <div class="form-group">
	            <button type="button" class="btn btn-success" id="btn-check-all">Check All</button>  
	            <button type="button" class="btn btn-danger" id="btn-uncheck-all">Uncheck All</button>  
                <input type="hidden" id="userName" />
              </div>
                
              <div id="permissionTree" class="form-group"></div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="updateUsersPermissions();">保存</button>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
