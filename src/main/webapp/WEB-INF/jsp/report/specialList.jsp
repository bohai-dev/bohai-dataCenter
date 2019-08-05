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
    <!-- axios -->
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    
    <!-- 操作完成提示插件 -->
    <script src="https://cdn.bootcss.com/toastr.js/latest/js/toastr.min.js"></script>
    <link href="https://cdn.bootcss.com/toastr.js/latest/css/toastr.min.css" rel="stylesheet" />
    
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
                    },
                    returnTypeOptions:[{value:'1',label:'客户'},
                                       {value:'2',label:'居间人'}],
                    returnWayOptions:[{value:'1',label:'加帐户'},
                                      {value:'2',label:'客户银行卡'}],
                    tableData:{}
                },
                created: function (){
                    console.log('specialListApp created')
                    this.onSubmit();
                },
                methods:{
                    query:function(pageNum, pageSize){
                        this.queryForm.pageNum = pageNum;
                        this.queryForm.pageSize = pageSize;
                        var self = this;
                        axios.get('querySpecial', {
                            params: self.queryForm
                          })
                          .then(function (response) {
                              self.tableData = response.data;
                          })
                          .catch(function (error) {
                              toastr.error('查询交返特例名单失败');
                          })
                          .then(function () {
                            // always executed
                          });
                    },
                    onSubmit:function(){
                        this.query(1,10);
                    },
                    onBlur:function(){
                        this.saveForm.DCE = this.saveForm.SHFE;
                        this.saveForm.CZCE = this.saveForm.SHFE;
                        //this.saveForm.INE = this.saveForm.SHFE;
                        //this.saveForm.CFFEX = this.saveForm.SHFE;
                    },
                    onExport:function(){
                        console.log('export!');
                        axios.post('exportSpecial', this.queryForm, {responseType:'blob' })
                    },
                    onInsert:function(){
                        
                    },
                    handleSizeChange:function(val) {
                        console.log('每页 ${val} 条');
                        this.query(this.queryForm.pageNum,val);
                      },
                    handleCurrentChange:function(val) {
                        console.log('当前页: ${val}');
                        this.query(val,this.queryForm.pageSize);
                        
                    },
                    update:function(row) {
                        console.log(row);
                        this.saveDialogVisible = true;
                        this.saveForm = JSON.parse(JSON.stringify(row));
                    },
                    save:function(){
                        
                        var self =this;
                        axios.post('saveSpecial', self.saveForm)
                          .then(function (response) {
                              if(response.data.code == '00000'){
                                  self.saveDialogVisible = false;
                                  toastr.success('保存成功');
                                  //清空表单
                                  self.saveForm = {};
                              }else{
                                  toastr.error('保存失败:'+response.data.msg);
                              }
                          })
                          .catch(function (error) {
                              toastr.error('保存失败');
                          })
                          .then(function () {
                              self.onSubmit();
                          });
                    },
                    remove:function(row){
                        
                        var self = this;
                        if(row.RETURN_TYPE == '2'){
                            this.$confirm('是否删除居间名下所有客户?', '提示', {
                                distinguishCancelAndClose: true,
                                confirmButtonText: '删除所有',
                                cancelButtonText: '仅删除此条',
                                type: 'warning'
                              }).then(() => {
                                  row.REMOVE_ALL = '1';
                                  axios.get('removeSpecial', {
                                      params: row
                                    })
                                    .then(function (response) {
                                        if(response.data.code == '00000'){
                                            toastr.success('删除成功');
                                        }else{
                                            toastr.error('删除失败:'+response.data.msg);
                                        }
                                    })
                                    .catch(function (error) {
                                        toastr.error('删除失败');
                                    })
                                    .then(function () {
                                      // always executed
                                        self.onSubmit();
                                    });
                              }).catch(action => {
                                  if(action === 'cancel'){
                                      axios.get('removeSpecial', {
                                          params: row
                                        })
                                        .then(function (response) {
                                            if(response.data.code == '00000'){
                                                toastr.success('删除成功');
                                            }else{
                                                toastr.error('删除失败:'+response.data.msg);
                                            }
                                        })
                                        .catch(function (error) {
                                            toastr.error('删除失败');
                                        })
                                        .then(function () {
                                          // always executed
                                            self.onSubmit();
                                        });
                                  }else {
                                      this.$message({
                                          type: 'info',
                                          message: '已取消删除'
                                        })
                                }
                                  
                              });
                        }else{
                            this.$confirm('是否删除所选数据?', '提示', {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning'
                              }).then(() => {
                                  axios.get('removeSpecial', {
                                      params: row
                                    })
                                    .then(function (response) {
                                        if(response.data.code == '00000'){
                                            toastr.success('删除成功');
                                        }else{
                                            toastr.error('删除失败:'+response.data.msg);
                                        }
                                    })
                                    .catch(function (error) {
                                        toastr.error('删除失败');
                                    })
                                    .then(function () {
                                      // always executed
                                        self.onSubmit();
                                    });
                              }).catch(() => {
                                  this.$message({
                                      type: 'info',
                                      message: '已取消删除'
                                    });
                              });
                        }
                        
                    },
                    formatReturnType:function(row, column, cellValue, index){
                        if(row.RETURN_TYPE == '1'){
                            return '客户';
                        }else{
                            return '居间人';
                        }
                    },
                    formatReturnWay:function(row, column, cellValue, index){
                        if(row.RETURN_WAY == '1'){
                            return '加帐户';
                        }else if(row.RETURN_WAY == '2'){
                            return '客户银行卡';
                        }
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
                              <el-select v-model="queryForm.RETURN_TYPE" clearable placeholder="请选择">
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
                            <el-input placeholder="请输入内容" v-model="queryForm.INVESTOR_NO" clearable></el-input>
                        </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="居间人编号">
                            <el-input placeholder="请输入内容" v-model="queryForm.MEDIATOR_NO" clearable></el-input>
                        </el-form-item>
                    </div>
                  </el-col>
                </el-row>
                
                <el-row :gutter="60">
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="返还方式">
                              <el-select v-model="queryForm.RETURN_WAY" clearable placeholder="请选择">
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
                              v-model="queryForm.BEGIN_EXPIRE_DATE"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <div class="grid-content bg-purple">
                        <el-form-item label="到期结束时间">
                            <el-date-picker
                              v-model="queryForm.END_EXPIRE_DATE"
                              type="date"
                              value-format="yyyy-MM-dd"
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
                        <a href="exportSpecial"><el-button type="danger" >导出</el-button></a>
                        <el-button type="success" @click="saveDialogVisible = true">新增</el-button>
                    </div>
                  </el-col>
                </el-row>
            </el-form>
            
            <el-divider>交返特例名单</el-divider>
            <template>
                <el-table :data="tableData.list" border >
                    <el-table-column prop="RETURN_TYPE" :formatter="formatReturnType" label="返还类型" ></el-table-column>
                    <el-table-column prop="INVESTOR_NO" label="投资者编码" ></el-table-column>
                    <el-table-column prop="INVESTOR_NAME" label="投资者名称" ></el-table-column>
                    <el-table-column prop="MEDIATOR_NO" label="居间人编号" ></el-table-column>
                    <el-table-column prop="MEDIATOR_NAME" label="居间人名称" ></el-table-column>
                    <el-table-column prop="SHFE" label="上期返还比例" ></el-table-column>
                    <el-table-column prop="DCE" label="大商返还比例" ></el-table-column>
                    <el-table-column prop="CZCE" label="郑商返还比例" ></el-table-column>
                    <el-table-column prop="INE" label="能源中心返还比例" ></el-table-column>
                    <el-table-column prop="CFFEX" label="中金返还比例" ></el-table-column>
                    <el-table-column prop="RETURN_WAY" :formatter="formatReturnWay" label="返还方式" ></el-table-column>
                    <el-table-column prop="EFFECT_DATE" label="生效日期" ></el-table-column>
                    <el-table-column prop="EXPIRE_DATE" label="失效日期" ></el-table-column>
                    <el-table-column label="操作" width="100">
                      <template slot-scope="scope">
                        <el-button @click="update(scope.row)" type="text" size="small">编辑</el-button>
                        <el-button @click="remove(scope.row)" type="text" size="small">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page.sync="tableData.pageNum"
                  :page-sizes="[10, 20, 30, 40, 50]"
                  :page-size.sync="tableData.pageSize"
                  :total="tableData.total"
                  layout="total,sizes, prev, pager, next">
                </el-pagination>
            </template>
            
            
            <el-dialog title="特例名单维护" :visible.sync="saveDialogVisible">
              <el-form ref="saveForm" :model="saveForm">
              
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
                        <el-form-item label="返还类型">
                              <el-select v-model="saveForm.RETURN_TYPE" style="width :217px;" clearable placeholder="请选择">
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
                                <el-option label="否" value="0"></el-option>
                                <el-option label="是" value="1"></el-option>
                              </el-select>
                        </el-form-item>
                    </div>
                  </el-col>
                </el-row>
                
                <el-row :gutter="60">
                    <el-col :span="10" :offset="2">
                        <div class="grid-content bg-purple">
                            <el-form-item label="上期返还" >
                                <el-input placeholder="小数格式如0.5" style="width :217px;" v-model="saveForm.SHFE" @blur="onBlur" clearable></el-input>
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
                                <el-input placeholder="小数格式如0.5" style="width :217px;" v-model="saveForm.CZCE" clearable></el-input>
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
                                <el-input placeholder="小数格式如0.5" style="width :217px;" v-model="saveForm.CFFEX" clearable></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    
                    <el-col :span="12">
                        <div class="grid-content bg-purple">
                            <el-form-item label="返还方式">
                              <el-select v-model="saveForm.RETURN_WAY" clearable placeholder="请选择">
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
                    
                </el-row>
                
                <el-row :gutter="60">
                    <el-col :span="10" :offset="2" >
                        <div class="grid-content bg-purple">
                            <el-form-item label="生效时间">
                                <el-date-picker
                                  v-model="saveForm.EFFECT_DATE"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择日期">
                                </el-date-picker>
                            </el-form-item>
                        </div>
                    </el-col>
                    
                    <el-col :span="12" >
                        <div class="grid-content bg-purple">
                            <el-form-item label="到期时间">
                                <el-date-picker
                                  v-model="saveForm.EXPIRE_DATE"
                                  type="date"
                                  value-format="yyyy-MM-dd"
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
                <el-button type="primary" @click="save">确 定</el-button>
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
