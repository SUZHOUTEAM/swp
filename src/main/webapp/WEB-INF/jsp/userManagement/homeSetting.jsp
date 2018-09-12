<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="首页设置"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#homeSetting"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterpriseWaste/style.css">
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>运营管理</el-breadcrumb-item>
            <el-breadcrumb-item>首页设置</el-breadcrumb-item>
        </el-breadcrumb>
        <div >
            <el-row style="margin-bottom: 10px">
                <el-col :span="12">
                    <el-button type="primary" size="small" icon="el-icon-plus" @click="add">新增</el-button>
                    <el-button type="primary" size="small" icon="el-icon-edit" @click="edit">编辑</el-button>
                    <el-button type="primary" size="small" icon="el-icon-delete" @click="del">删除</el-button>
                </el-col>
                <el-col :span="12" align="right">
                    <el-select v-model="section" placeholder="请选择栏目" @change="changeSection">
                        <el-option
                                v-for="(value,key) in sectionList"
                                :key="key"
                                :label="value"
                                :value="key">
                        </el-option>
                    </el-select>
                    <el-button type="primary" size="small" icon="el-icon-share" @click="updateTohome" style="float: right;margin-left: 10px">更新到首页</el-button>
                </el-col>
            </el-row>
            <div class="selectTable">
                <el-table  tooltip-effect="dark" @selection-change="handleSelectionChange"  ref="multipleTable"
                           :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable" border
                           style="width: 100%">
                    <el-table-column
                            prop="id"  type="selection" align="center">
                    </el-table-column>
                    <el-table-column
                            label="区域"
                            width="150px"
                            prop="cantonName">
                        <template scope="scope">
                            <span style="text-overflow: ellipsis;white-space: nowrap;">{{scope.row.cantonName?scope.row.cantonName:'跨省'}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="栏目"
                            width="80px"
                            prop="section">
                        <template scope="scope">
                            <span style="text-overflow: ellipsis;white-space: nowrap;">{{sectionList[scope.row.section]}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="版面"
                            width="60px"
                            prop="page">
                    </el-table-column>
                    <el-table-column
                            label="序号"
                            width="60px"
                            prop="index">
                    </el-table-column>
                    <el-table-column
                            label="企业"
                            prop="entName">
                    </el-table-column>
                    <el-table-column
                            label="开始时间"
                            width="110px"
                            prop="startDate">
                        <template scope="scope">
                            <span style="text-overflow: ellipsis;white-space: nowrap;">{{scope.row.startDate.substring(0,10)}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="结束时间"
                            width="110px"
                            prop="endDate">
                        <template scope="scope">
                            <span style="text-overflow: ellipsis;white-space: nowrap;">{{scope.row.endDate.substring(0,10)}}</span>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
        </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>

<script>
    var vue=new Vue({
        el: '#app',
        data: {
            tableData: [],
            listLoading:true,
            sectionList:{'total':'全部','DISPOSITION':'处置','RECYCLING':'利用','SPECIALCATEGORY':'特殊类别'},
            multipleSelection:[],
            pageIndex:1,
            pageSize:10,
            total:0,
            section:''
        },
        created:function() {
            this.getList();
        },
        mounted:function () {
        },
        methods:{
//            checked:function(){
//                this.$refs.multipleTable.toggleRowSelection(this.tableData[0],true);
//            },
            getList:function () {
                this.listLoading = true;
                var _this=this;
                var param={
                    section:this.section=='total'?'':this.section
                }
                ajax({
                    url:'/enterpriseConfiguration/listEnterpriseInfo.htm?ticketId=<%=ticketId%>&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,
                    dataType:'json',
                    contentType:'application/json',
                    data:JSON.stringify(param),
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data.enterpriseModelList&&result.data.enterpriseModelList.length>0){
                            _this.total=result.data.paging.totalRecord;
                            _this.tableData=result.data.enterpriseModelList;
//                            _this.$nextTick(function(){
//                                _this.checked();//每次更新了数据，触发这个函数即可。
//                            });
                        }else{
                            _this.tableData=[];
                            _this.total=0;
                        }
                    }
                })
            },
            changeSection:function () {
            $('#section').val(this.section);
              this.pageIndex=1;
              this.getList();
            },
            handleSizeChange:function(val) {
                this.pageIndex=1;
                this.pageSize = val
                this.getList()
            },
            handleCurrentChange:function(val) {
                this.pageIndex = val
                this.getList()
            },
            handleFilter:function() {
                this.getList()
            },
            handleSelectionChange:function(val){
                this.multipleSelection = val;
            },
            updateTohome:function () {
                vue.$confirm('此操作将更新最新配置到首页, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/enterpriseConfiguration/updateToHome',
                        success:function (result) {
                            if(result.status&&result.data){
                                $.notify('更新成功',{status:'success',timeout:2500});
                            }
                        }
                    })
                }).catch(function(){});
            },
            add:function () {
                window.location='<%=appPath%>/appManagement/homeEdit.htm?ticketId=<%=ticketId%>';
            },
            edit:function () {
                if(this.multipleSelection.length==0||this.multipleSelection.length>1){
                    $.notify('请选择一条进行编辑',{status:'info',timeout:2500});
                    return;
                }
                window.location='<%=appPath%>/appManagement/homeEdit.htm?ticketId=<%=ticketId%>&id='+this.multipleSelection[0]['configId'];
            },
            del:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请至少选择一条进行删除',{status:'info',timeout:2500});
                    return;
                }
                var arr=[];
                for(var i in vue.multipleSelection){
                    var str=vue.multipleSelection[i]['configId'];
                    arr.push(str);
                }
                vue.$confirm('此操作将删除该首页配置, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/enterpriseConfiguration/deleteEnterpriseConfiguration.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(arr),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status&&result.data){
                                $.notify('删除成功',{status:'success',timeout:2500});
                                vue.pageIndex=1;
                                vue.getList();
                            }
                        }
                    })
                }).catch(function(){});
            }
        }

    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
    });
</script>