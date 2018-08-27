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
    
     <!-- datepicker -->
    <link href="resources/bootstrap-datepicker/css/bootstrap-datepicker3.css" rel="stylesheet">
    
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
    
    <!-- datepicker -->
    <script src="resources/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="resources/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    
        <!-- VUE -->
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
    
    <script type="text/javascript">
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toCharts"]').parent().addClass("active");
            
            $.ajax({
                url: 'queryTrunoverBarChart',
                type: 'get',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    
                  //app.title = '坐标轴刻度与标签对齐';
                    option = {
                        title: {
                            text: '客户成交额(亿元)',
                            /* subtext: 'From ExcelHome',
                            sublink: 'http://e.weibo.com/1341556070/Aj1J2x5a5' */
                        },
                        //color: ['#3398DB'],
                        tooltip : {
                            trigger: 'axis',
                            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis : [
                            {
                                type : 'category',
                                data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun', 'weekend'],
                                axisTick: {
                                    alignWithLabel: true
                                }
                            }
                        ],
                        legend: {
                            data: ['上期所', '大商所','郑商所','中金所','能源交易所']
                        },
                        yAxis : [
                            {
                                type : 'value'
                            }
                        ],
                        series : [
                            {
                                name:'上期所',
                                type:'bar',
                                barWidth: '10%',
                                data:[10, 52, 200, 334, 390, 330, 220,70]
                            },
                            {
                                name:'大商所',
                                type:'bar',
                                barWidth: '10%',
                                data:[10, 52, 200, 334, 390, 330, 220,70]
                            },
                            {
                                name:'郑商所',
                                type:'bar',
                                barWidth: '10%',
                                data:[10, 52, 200, 334, 390, 330, 220,70]
                            },
                            {
                                name:'中金所',
                                type:'bar',
                                barWidth: '10%',
                                data:[10, 52, 200, 334, 390, 330, 220,70]
                            },
                            {
                                name:'能源交易所',
                                type:'bar',
                                barWidth: '10%',
                                data:[10, 52, 200, 334, 390, 330, 220,70]
                            }
                        ]
                    };
                    var myChart = echarts.init(document.getElementById('barChart'));
                    option.xAxis[0].data = result.xAxis.data;
                    option.legend.data = result.legend.data;
                    option.series = result.series;
                    myChart.setOption(option);
                }
            });
            
            
            $.ajax({
                url: 'queryInvestorIncreament',
                type: 'get',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    option = {
                            title: {
                                text: '新增客户数',
                                /* subtext: 'From ExcelHome',
                                sublink: 'http://e.weibo.com/1341556070/Aj1J2x5a5' */
                            },
                            color: ['#3398DB'],
                            tooltip : {
                                trigger: 'axis',
                                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                }
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            xAxis : [
                                {
                                    type : 'category',
                                    data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                                    axisTick: {
                                        alignWithLabel: true
                                    }
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value'
                                }
                            ],
                            series : [
                                {
                                    name:'新增客户数',
                                    type:'bar',
                                    barWidth: '60%',
                                    data:[10, 52, 200, 334, 390, 330, 220]
                                }
                            ]
                        };
                    
                    option.xAxis[0].data = result.xAxis.data;
                    option.series = result.series;
                    var myChart1 = echarts.init(document.getElementById('barChart1'));
                    myChart1.setOption(option);
                }
            });
            
            
            option = {
                    title: {
                        text: '月出入金金额（万元）'/* ,
                        subtext: 'From ExcelHome',
                        sublink: 'http://e.weibo.com/1341556070/Aj1J2x5a5' */
                    },
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        },
                        formatter: function (params) {
                            var tar;
                            if (params[1].value != '-') {
                                tar = params[1];
                            }
                            else {
                                tar = params[2];
                            }
                            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
                        }
                    },
                    legend: {
                        data:['净入金','净出金']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type : 'category',
                        splitLine: {show:false},
                        data :  function (){
                            var list = [];
                            for (var i = 1; i <= 11; i++) {
                                list.push('11月' + i + '日');
                            }
                            return list;
                        }()
                    },
                    yAxis: {
                        type : 'value'
                    },
                    series: [
                        {
                            name: '辅助',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {
                                normal: {
                                    barBorderColor: 'rgba(0,0,0,0)',
                                    color: 'rgba(0,0,0,0)'
                                },
                                emphasis: {
                                    barBorderColor: 'rgba(0,0,0,0)',
                                    color: 'rgba(0,0,0,0)'
                                }
                            },
                            data: [0, 900, 1245, 1530, 1376, 1376, 1511, 1689, 1856, 1495, 1292]
                        },
                        {
                            name: '净入金',
                            type: 'bar',
                            stack: '总量',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top'
                                }
                            },
                            data: [900, 345, 393, '-', '-', 135, 178, 286, '-', '-', '-']
                        },
                        {
                            name: '净出金',
                            type: 'bar',
                            stack: '总量',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'bottom'
                                }
                            },
                            data: ['-', '-', '-', 108, 154, '-', '-', '-', 119, 361, 203]
                        }
                    ]
                };
            var myChart2 = echarts.init(document.getElementById('barChart2'));
            myChart2.setOption(option);
            
            
            $.ajax({
                url: 'queryInvestorCharge',
                type: 'get',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    option = {
                            title: {
                                text: '存量客户佣金'
                            },
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: {
                                    type: 'shadow'
                                }
                            },
                            legend: {
                                data: ['交易所佣金', '公司佣金']
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            xAxis: {
                                type: 'value',
                                boundaryGap: [0, 0.01]
                            },
                            yAxis: {
                                type: 'category',
                                data: ['201801','201802','201803','201804','201805','201806']
                            },
                            series: [
                                {
                                    name: '交易所佣金',
                                    type: 'bar',
                                    data: [18203, 23489, 29034, 104970, 131744, 630230]
                                },
                                {
                                    name: '公司佣金',
                                    type: 'bar',
                                    data: [19325, 23438, 31000, 121594, 134141, 681807]
                                }
                            ]
                        };
                    option.yAxis.data = result.xAxis.data;
                    option.series = result.series;
                    var myChart3 = echarts.init(document.getElementById('barChart3'));
                    myChart3.setOption(option);
                }
            });
            
            
            option = {
                    title : {
                        text: '本年三方存管各渠道开户数',
                        subtext: '纯属虚构',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['交通银行','兴业银行','建设银行','平安银行','招商银行']
                    },
                    series : [
                        {
                            name: '开户渠道',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value:335, name:'交通银行'},
                                {value:310, name:'兴业银行'},
                                {value:234, name:'建设银行'},
                                {value:135, name:'平安银行'},
                                {value:1548, name:'招商银行'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
            var myChart4 = echarts.init(document.getElementById('barChart4'));
            myChart4.setOption(option);
            
            $.ajax({
                url: 'queryInvestorAndRight',
                type: 'get',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    
                    option = {
                            title : {
                                text: '存量客户数及总权益',
                                subtext: '纯属虚构',
                                x:'center'
                            },
                            color: ['#3398DB'],
                            tooltip : {
                                trigger: 'axis',
                                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                },
                                formatter: function (params) {
                                    var tar = params[0];
                                    if (params[0].name == '总权益') {
                                        
                                        return tar.name + '<br/>' + result.series[0].name+'亿元';
                                    }else {
                                        return tar.name + '<br/>' + tar.value+'户';
                                    }
                                }
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            xAxis : [
                                {
                                    type : 'category',
                                    data : ['存量客户数', '总权益'],
                                    axisTick: {
                                        alignWithLabel: true
                                    }
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value',
                                    show:false
                                }
                            ],
                            series : [
                                {
                                    name:'',
                                    type:'bar',
                                    barWidth: '60%',
                                    data:[10, 52],
                                    label: {
                                        normal: {
                                            show: true,
                                            position: 'top',
                                            formatter:function(params){
                                                if(params.name == '总权益'){
                                                    return result.series[0].name;
                                                }
                                            }
                                        }
                                    },
                                }
                            ]
                        };
                    option.series[0].data = result.series[0].data;
                    option.series[0].barWidth = result.series[0].barWidth;
                    var myChart5 = echarts.init(document.getElementById('barChart5'));
                    myChart5.setOption(option);
                }
            });
            
            
            //排行榜表格
            var tableApp = new Vue({
                el: '#tableApp',
                data: {items:[]},
                created: function (){
                    this.rightsRanking();
                },
                methods:{
                    rightsRanking: function(){
                        $.ajax({
                            url: 'rightsRanking',
                            type: 'get',
                            dataType: 'json',
                            success: function (result) {
                                tableApp.items = result;
                            }
                        });
                    }
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
            <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> <a href="toBusinessReport" style="text-decoration: none;">统计报表</a> --> <a href="toBusinessReport" style="text-decoration: none;">图表</a></h4>

            <div class="row placeholders">
                <div class="col-sm-12 col-md-6" id="barChart" style="height:400px;">
                
                </div>
                
                <div class="col-sm-12 col-md-6" id="barChart1" style="height:400px;">
                
                </div>
                
                <div class="col-sm-12 col-md-6" id="barChart2" style="height:400px;">
                
                </div>
                
                <div class="col-sm-12 col-md-6" id="barChart3" style="height:400px;">
                
                </div>
                
                <div class="col-sm-12 col-md-6" id="barChart4" style="height:400px;">
                
                </div>
                
                <div class="col-sm-12 col-md-6" id="barChart5" style="height:400px;">
                
                </div>
                
                <div class="col-sm-12 col-md-6" id="barChart6" style="height:400px;">
                    <label for="tableApp">存量客户权益排名</label>
                    <table id="tableApp" align="center" class="table table-bordered">
                        <thead>
                            <tr>
                                <th width="10%" style="text-align: center;">排名</th >
                                <th width="50%" style="text-align: center;">营业部</th >
                                <th width="30%" style="text-align: center;">总权益</th >
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="item in items">
                                <td style="text-align: center;">
                                    {{item.ranking}}
                                </td>
                                <td style="text-align: center;">
                                    {{item.depName}}
                                </td>
                                <td style="text-align: center;">
                                    {{item.money}}
                                </td>
                            </tr>
                        </tbody>
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
