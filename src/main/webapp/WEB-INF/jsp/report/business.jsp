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
    
    <!-- 菜单树 -->
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    <!-- table插件 -->
    <script src="resources/bootstrap-table/bootstrap-table.js"></script>
    <!-- put your locale files after bootstrap-table.js -->
    <script src="resources/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <!-- 文件上传插件fileInput -->
    <script src="resources/fileInput/themes/fa/theme.js"></script>
    <!-- optionally if you need translation for your language then include 
        locale file as mentioned below -->
    <script src="resources/fileInput/js/locales/zh.js"></script>
    
    <!-- echarts -->
    <script src="resources/echarts/echarts.min.js"></script>
    
    <script type="text/javascript">
        function operationFormatter(value,row,index) {
            var html = '<button type="button" id="cog'+index+'" class="btn btn-default btn-sm" title="设置">'
                         + '<i class="glyphicon glyphicon-cog"></i>'
                     + '</button>'
                     + '<button type="button" id="trash'+index+'" class="btn btn-default btn-sm" title="删除任务">'
                         + '<i class="glyphicon glyphicon-trash"></i>'
                     + '</button>';
                     
            return html;
        }
        function numformat(params){
        	return params.value+'值';
        }
        
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toBusinessReport"]').parent().addClass("active");
            
            var myChart = echarts.init(document.getElementById('pieCharts'));
            
            myChart.on('click',function(params){
                // 点击到了 pie 上
                if (params.componentType === 'series') {
               // 点击到了 index 为 1 的 series 的 pie 上。   
                     if (params.seriesIndex === 1) {
                        
                    	 var json = params.data;
                    	 var seriesData = new Array();
                    	 var insterestJson = {};
                    	 insterestJson['value'] = json.interest;
                    	 insterestJson['name'] = '利息';
                    	 seriesData[0] = insterestJson;
                    	 var insterestJson1 = {};
                    	 insterestJson1['value'] = json.exchangeReturnTicktix;
                    	 insterestJson1['name'] = '交返(剔税)';
                    	 seriesData[1] = insterestJson1;
                    	 var insterestJson2 = {};
                    	 insterestJson2['value'] = json.commission;
                    	 insterestJson2['name'] = '手续费';
                    	 seriesData[2] = insterestJson2;
                    	 myChart.setOption({
                             series:[{
                                  // 根据名字对应到相应的系列
                                 name: json.name,
                                 //设置从服务器获取的数据
                                 data: seriesData
                             }]
                         });
                     }
                }
            });
            
            
            $.ajax({
                url: 'queryMarketProfitPieChart',
                type: 'post',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: null,
                success: function (result) {
                    var legendData = new Array();
                    var seriesData = new Array();
                    
                    $.each(result, function(index, content){
                        legendData[index] = content.depName;
                        var json = {};
                        json['value'] = (content.interest + content.exchangeReturnTicktix + content.commission).toFixed(2);
                        json['name'] = content.depName;
                        json['interest'] = content.interest;
                        json['exchangeReturnTicktix'] = content.exchangeReturnTicktix;
                        json['commission'] = content.commission;
                        seriesData[index] = json;
                    });
                    
                    option = {
                    		title : {
                    	        text: result[0].month+'营业部利润分布饼图',
                    	        x:'center'
                    	    },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b}: {c} ({d}%)"
                            },
                            legend: {
                                orient: 'vertical',
                                x: 'left',
                                data:legendData
                            },
                            series: [
                                {
                                    name:'利润组成',
                                    type:'pie',
                                    selectedMode: 'single',
                                    radius: [0, '35%'],

                                    label: {
                                        normal: {
                                            position: 'inner'
                                        }
                                    },
                                    labelLine: {
                                        normal: {
                                            show: false
                                        }
                                    },
                                    data:[
                                      /*   {value:335, name:'直达'},
                                        {value:679, name:'营销广告'},
                                        {value:1548, name:'搜索引擎'} */
                                    ]
                                },
                                {
                                    name:'毛利润',
                                    type:'pie',
                                    radius: ['45%', '60%'],

                                    data:seriesData
                                }
                            ]
                        };
                    myChart.setOption(option);
                    
                }
            });
            
            //柱状图
            var myChart2 = echarts.init(document.getElementById('chart2'));
            option2 = {
            	    tooltip : {
            	        trigger: 'axis',
            	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            	        }
            	    },
            	    legend: {
            	        data: ['净利润', '毛利润']
            	    },
            	    grid: {
            	        left: '3%',
            	        right: '4%',
            	        bottom: '2%',
            	        containLabel: true
            	    },
            	    xAxis:  {
            	        type: 'value',
            	        scale:true,
            	        min:0
            	        
            	    },
            	    yAxis: {
            	        type: 'category',
            	        max:11,
            	        data: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
            	    },
            	    series: [
            	        {
            	            name: '净利润',
            	            type: 'bar',
            	            stack: '总量',            	            
            	            label: {
            	                normal: {
            	                    show: true,
            	                    position: 'insideRight',
            	                   
            	                }
            	            },
            	            data: [320, 302, 301, 334, 390, 330, 320,290,310,295,235,298]
            	        },
            	        {
            	            name: '毛利润',
            	            type: 'bar',
            	            stack: '总量',
            	            barWidth:45,
            	            label: {
            	                normal: {
            	                    show: true,
            	                    position: 'insideRight',
            	                    formatter:numformat
            	                }
            	            },
            	            data: [120, 132, 101, 134, 90, 230, 210,200,230,242,180,196]
            	        }
            	        
            	    ]
            	};
            myChart2.setOption(option2);
        
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toBusinessReport" style="text-decoration: none;">统计报表</a> --> <a href="toBusinessReport" style="text-decoration: none;">营业部统计表</a></h1>

          <div class="row placeholders">
            <!-- <label class="control-label">请选择文件上传</label>
            <input id="finput" type="file" class="file" multiple >
            <script type="text/javascript">
               $("#finput").fileinput({
                   language: 'zh',
                   uploadAsync: true,
                   uploadUrl: "fileUpload", //异步上传地址
                   maxFileCount: 10,//最大上传文件数限制
                   showCaption: true,//是否显示标题
                   showUpload: true,//是否显示上传按钮
                   allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀 
                   //previewFileIcon: "<i class='glyphicon glyphicon-king'></i>" //选择文件后缩略图
               });
            </script> -->
            
            
                <div class="col-sm-12 col-md-12">
                    <div id="pieCharts" style="width: 100%;height:800px;"></div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div id="chart2" style="width: 100%;height:1000px;"></div>
                </div>
            
            
          </div>

          <!-- <h2 class="sub-header">用户信息</h2>
          <div class="table-responsive">
            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addModal" title="创建任务">
                    <i class="glyphicon glyphicon-plus"></i>
                </button>
            </div>
            <table class="table table-striped"
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
                    <th data-field="state" data-checkbox="true"></th>
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
          </div> -->
          
          
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
