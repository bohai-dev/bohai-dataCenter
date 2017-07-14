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
     <!-- echarts -->
    <script src="resources/echarts/echarts.min.js"></script>
    <script src="resources/echarts/china.js"></script>
    <script type="text/javascript">
    
        $(function(){
            /* var tree = [
            {
                  text: "Node 1",
                  icon: "glyphicon glyphicon-stop",
                  selectedIcon: "glyphicon glyphicon-stop",
                  color: "#000000",
                  backColor: "#FFFFFF",
                  href: "http://localhost:8081/bohai-dataCenter/admin/123",
                  selectable: true,
                  state: {
                    checked: true,
                    disabled: true,
                    expanded: true,
                    selected: true
                  }, 
                  tags: ['available']
                },
              {
                text: "Parent 1",
                nodes: [
                  {
                    text: "Child 1",
                    nodes: [
                      {
                        text: "Grandchild 1",
                        href: "http://localhost:8081/bohai-dataCenter/admin/123"
                      },
                      {
                        text: "Grandchild 2",
                        href: "http://localhost:8081/bohai-dataCenter/admin/123",
                      }
                    ]
                  },
                  {
                    text: "Child 2",
                    href: "http://localhost:8081/bohai-dataCenter/admin/123"
                  }
                ]
              },
              {
                text: "Parent 2",
                tags: ["23"]
              },
              {
                text: "Parent 3"
              },
              {
                text: "Parent 4"
              },
              {
                text: "Parent 5"
              }
            ];  */
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
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
          <h1 class="page-header"><a href="toHome" style="text-decoration: none;">首页</a></h1>

          <div class="row placeholders">
            
                <div class="jumbotron">
				  <h1>渤海期货数据中心</h1>
				  <p>欢迎！</p>
				  <p><a class="btn btn-primary btn-lg" href="http://www.bhfcc.com/" role="button">Learn more</a></p>
				</div>
            
          </div>
          
		  <div class="row placeholders" style="width: 100%;height:800px;" id="chinaMap">
		  
		  </div>
		  
        </div>
      </div>
      
    </div>
    
    <footer class="footer">
      <div class="container">
        <p class="text-muted">Place sticky footer content here.</p>
      </div>
    </footer>
<script type="text/javascript">

var myChart1 = echarts.init(document.getElementById('chinaMap'));

/* function randomData() {
    return Math.round(Math.random()*1000);
} */

$.get("queryCustomerDistribution",function(data,status){
	var array=new Array();
	var max=0;
	for(i=0;i<data.length;i++){
		var object=new Object();
		object.name=data[i].NAME;
		object.value=data[i].VALUE;
		array.push(object);
		if(data[i].VALUE>max){
			max=data[i].VALUE;
		}
	}
	
	
     console.log(array);
	option = {
		    title: {
		        text: '客户数量各省分布图',
		        subtext: '',
		        left: 'center'
		    },
		    tooltip: {
		        trigger: 'item'
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data:['客户数量']
		    },
		    visualMap: {
		        min: 0,
		        max: max,
		        left: 'left',
		        top: 'bottom',
		        text: ['高','低'],           // 文本，默认为数值文本
		        calculable: true
		    },
		    toolbox: {
		        show: true,
		        orient: 'vertical',
		        left: 'right',
		        top: 'center',
		        feature: {
		            dataView: {readOnly: false},
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    series: [
		        {
		            name: '客户数量',
		            type: 'map',
		            mapType: 'china',
		            roam: false,
		            label: {
		                normal: {
		                    show: true
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            data:array
		        }
		        
		    ]
		};
		myChart1.setOption(option);
		myChart1.on('click',function(params){
			window.location.href="toBusinessReport";
		});
  });



</script>
  </body>
</html>
