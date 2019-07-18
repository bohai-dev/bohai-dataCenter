<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome  </title>
    <!-- jquery -->
    <script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
    <!-- <script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script> -->
    
    
    <!-- Bootstrap -->
    <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/bootstrap-table/bootstrap-table.css">
    <link href="resources/css/dashboard.css" rel="stylesheet">
    <link href="resources/css/sticky-footer.css" rel="stylesheet">
    
    <!-- VUE -->
    <link href="https://unpkg.com/element-ui@2.8.2/lib/theme-chalk/index.css" rel="stylesheet">
    <script src="https://unpkg.com/vue/dist/vue.js"></script>
    <script src="https://unpkg.com/element-ui@2.8.2/lib/index.js"></script>
    
    <!-- 菜单树 -->
    <script type="text/javascript" src="resources/tree/bootstrap-treeview.min.js"></script>
    
    <script type="text/javascript">
        
        $(function(){
            var treeObj = ${sessionScope.treeView};
            $('#tree').treeview({data: treeObj,enableLinks: true});
            $('li a[href="toSpecialList"]').parent().addClass("active");
            
            
            var app = new Vue({
                el: '#specialListApp',
                data: {
                    saveDialogVisible:false,
                    queryForm:{},
                    saveForm:{
                        tableData:[]
                    },
                    returnTypeOptions:[{value:'1',label:'客户'},
                                       {value:'2',label:'居间人'}],
                    returnWayOptions:[{value:'1',label:'加帐户'},
                                      {value:'2',label:'客户银行卡'}],
                    tableData:{total:23,
                        data:[{
                            date: '2016-05-02',
                            name: '王小虎',
                            address: '上海市普陀区金沙江路 1518 弄'
                          }, {
                            date: '2016-05-04',
                            name: '王小虎',
                            address: '上海市普陀区金沙江路 1517 弄'
                          }, {
                            date: '2016-05-01',
                            name: '王小虎',
                            address: '上海市普陀区金沙江路 1519 弄'
                          }, {
                            date: '2016-05-03',
                            name: '王小虎',
                            address: '上海市普陀区金沙江路 1516 弄'
                          }]}
                },
                created: function (){
                    console.log('specialListApp created')
                },
                methods:{
                    onSubmit(){
                        console.log('submit!');
                    },
                    onExport(){
                        console.log('export!');
                    },
                    onInsert(){
                        
                    },
                    handleSizeChange(val) {
                        console.log('每页 ${val} 条');
                      },
                    handleCurrentChange(val) {
                        console.log('当前页: ${val}');
                    },
                    handleClick(row) {
                        console.log(row);
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
          <h4 class="page-header"><a href="toHome" style="text-decoration: none;"><i class="glyphicon glyphicon-home"></i></a> --> 
                <a href="toRebateReport" style="text-decoration: none;">统计报表</a> --> 
                <a href="toSpecialList" style="text-decoration: none;">交返特例名单维护</a></h1>
          </h4>
          <div class="row placeholders" id = "specialListApp">
          
            <el-form ref="queryForm" :model="queryForm" label-width="100px">
                <el-row :gutter="60">
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="返还类型">
                              <el-select v-model="queryForm.returnType" clearable placeholder="请选择">
                                <el-option
                                  v-for="item in returnTypeOptions"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                                </el-option>
                              </el-select>
                        </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="投资者代码">
                            <el-input placeholder="请输入内容" v-model="queryForm.input" clearable></el-input>
                        </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="居间人编号">
                            <el-input placeholder="请输入内容" v-model="queryForm.input" clearable></el-input>
                        </el-form-item>
                    </div>
                  </el-col>
                </el-row>
                
                <el-row :gutter="60">
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="返还方式">
                              <el-select v-model="queryForm.returnWay" clearable placeholder="请选择">
                                <el-option
                                  v-for="item in returnWayOptions"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                                </el-option>
                              </el-select>
                        </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="到期开始时间">
                            <el-date-picker
                              v-model="queryForm.beginDate"
                              type="date"
                              placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="到期结束时间">
                            <el-date-picker
                              v-model="queryForm.endDate"
                              type="date"
                              placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                    </div>
                  </el-col>
                </el-row>
                
                <el-row :gutter="60">
                  <el-col :span="6" :offset="6">
                    <div class="grid-content bg-purple">
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                        <el-button type="danger" @click="onExport">导出</el-button>
                        <el-button type="success" @click="saveDialogVisible = true">新增</el-button>
                    </div>
                  </el-col>
                </el-row>
            </el-form>
            
            <el-divider>交返特例名单</el-divider>
            <template>
                <el-table :data="tableData.data" border style="height:550px">
                    <el-table-column prop="date" label="日期" ></el-table-column>
                    <el-table-column prop="name" label="姓名" ></el-table-column>
                    <el-table-column prop="province" label="省份" ></el-table-column>
                    <el-table-column prop="city" label="市区" ></el-table-column>
                    <el-table-column prop="address" label="地址" ></el-table-column>
                    <el-table-column prop="zip" label="邮编" ></el-table-column>
                    <el-table-column label="操作" width="100">
                      <template slot-scope="scope">
                        <el-button @click="handleClick(scope.row)" type="text" size="small">编辑</el-button>
                        <el-button type="text" size="small">编辑</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                <span class="demonstration">调整每页显示条数</span>
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page.sync="tableData.currentPage2"
                  :page-sizes="[10, 20, 30, 40, 50]"
                  :page-size="10"
                  layout="sizes, prev, pager, next"
                  :total="tableData.total">
                </el-pagination>
            </template>
            
            
            <el-dialog title="特例名单维护" :visible.sync="saveDialogVisible">
              <el-form ref="saveForm" :model="saveForm">
                <el-row :gutter="60">
                  <el-col :span="10" :offset="2">
                    <div class="grid-content bg-purple">
                        <el-form-item label="返还类型">
                              <el-select v-model="saveForm.returnType" style="width :217px;" clearable placeholder="请选择">
                                <el-option
                                  v-for="item in returnTypeOptions"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                                </el-option>
                              </el-select>
                        </el-form-item>
                    </div>
                  </el-col>
                  
                  <el-col :span="12" >
                    <div class="grid-content bg-purple">
                        <el-form-item label="所有客户">
                              <el-select v-model="saveForm.IS_ALL" style="width :217px;" clearable placeholder="请选择">
                                <el-option label="否" value="shanghai"></el-option>
                                <el-option label="是" value="beijing"></el-option>
                              </el-select>
                        </el-form-item>
                    </div>
                  </el-col>
                </el-row>
                
                <el-row :gutter="60">
                    <el-col :span="10" :offset="2">
                        <div class="grid-content bg-purple">
                            <el-form-item label="投资者代码" >
                                <el-input placeholder="请输入内容" style="width :217px;" v-model="saveForm.INVESTOR_NO" clearable></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    
                    <el-col :span="12" >
                        <div class="grid-content bg-purple">
                            <el-form-item label="居间人代码">
                                <el-input placeholder="请输入内容" style="width :217px;" v-model="saveForm.MEDIATOR_NO" clearable></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                
                <el-row :gutter="60">
                    <el-col :span="10" :offset="2">
                        <div class="grid-content bg-purple">
                            <el-form-item label="上期返还" >
                                <el-input placeholder="小数格式如0.5" style="width :217px;" v-model="saveForm.SHEF" clearable></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    
                    <el-col :span="12" >
                        <div class="grid-content bg-purple">
                            <el-form-item label="大商返还">
                                <el-input placeholder="小数格式如0.5" style="width :217px;" v-model="saveForm.DCE" clearable></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                
                <el-row :gutter="60">
                    <el-col :span="10" :offset="2">
                        <div class="grid-content bg-purple">
                            <el-form-item label="郑商返还" >
                                <el-input placeholder="小数格式如0.5" style="width :217px;" v-model="saveForm.ZCZE" clearable></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    
                    <el-col :span="12" >
                        <div class="grid-content bg-purple">
                            <el-form-item label="能源返还">
                                <el-input placeholder="小数格式如0.5" style="width :217px;" v-model="saveForm.INE" clearable></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                
                <el-row :gutter="60">
                    <el-col :span="10" :offset="2">
                        <div class="grid-content bg-purple">
                            <el-form-item label="中金返还" >
                                <el-input placeholder="小数格式如0.5" style="width :217px;" v-model="saveForm.ZJ" clearable></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    
                </el-row>
                
                <el-row :gutter="60">
                    <el-col :span="10" :offset="2">
                        <div class="grid-content bg-purple">
                            <el-form-item label="返还方式">
                              <el-select v-model="saveForm.returnWay" clearable placeholder="请选择">
                                <el-option
                                  v-for="item in returnWayOptions"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                                </el-option>
                              </el-select>
                            </el-form-item>
                        </div>
                    </el-col>
                    
                    <el-col :span="12" >
                        <div class="grid-content bg-purple">
                            <el-form-item label="到期时间">
                                <el-date-picker
                                  v-model="saveForm.EXPIRE_DATE"
                                  type="date"
                                  placeholder="选择日期">
                                </el-date-picker>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>
                
                <el-row :gutter="60">
                    <el-col :span="10" :offset="2">
                        <div class="grid-content bg-purple">
                            <el-form-item label="备注">
                            <el-input
                              type="textarea"
                              :autosize="{ minRows: 2, maxRows: 4}"
                              placeholder="请输入内容"
                              v-model="saveForm.REMARK">
                            </el-input>
                        </div>
                    </el-col>
                    
                </el-row>
                
              </el-form>
              
              
              <div slot="footer" class="dialog-footer">
                <el-button @click="saveDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="">确 定</el-button>
              </div>
            </el-dialog>
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
