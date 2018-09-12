<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="APP管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#appManager"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterpriseWaste/style.css">
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item>APP管理</el-breadcrumb-item>
        </el-breadcrumb>
        <div>
            <el-row style="margin-bottom: 15px">
                <b class="b_label">APP类型：</b>
                <el-select v-model="appType" placeholder="选择APP类型" style="margin:0 12px" @change="changeCondition()">
                    <el-option v-for="item in appTypeList" :key="item.code" :label="item.value" :value="item.code">
                    </el-option>
                </el-select>
                <b class="b_label">企业类型：</b>
                <el-select v-model="entType" placeholder="选择企业类型" style="margin:0 12px" @change="changeCondition()">
                    <el-option v-for="item in entTypeList" :key="item.code" :label="item.value" :value="item.code">
                    </el-option>
                </el-select>
                <b class="b_label">发布状态:</b>
                <el-select v-model="busiStatus" placeholder="选择发布状态"  style="margin:0 12px"  @change="changeCondition()">
                    <el-option v-for="item in busiStatusList" :key="item.code" :label="item.value" :value="item.code">
                    </el-option>
                </el-select>
            </el-row>
            <el-row style="margin-bottom: 10px">
                <el-col :span="20">
                    <el-button type="primary" size="small" icon="el-icon-plus" @click="add">新增</el-button>
                    <el-button type="primary" size="small" icon="el-icon-view" @click="view">查看</el-button>
                    <el-button type="primary" size="small" icon="el-icon-edit" @click="edit">编辑</el-button>
                    <el-button type="primary" size="small" icon="el-icon-delete" @click="del">删除</el-button>
                </el-col>
                <el-col :span="4" align="right">
                    <el-button type="primary" size="small" icon="el-icon-time" @click="start">发布</el-button>
                </el-col>
            </el-row>
            <div class="selectTable">
                <el-table  tooltip-effect="dark" @selection-change="handleSelectionChange"
                           :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable" border
                           style="width: 100%">
                    <el-table-column
                            prop="id"  type="selection" align="center">
                    </el-table-column>
                    <el-table-column
                            label="app类型"
                            prop="appType">
                    </el-table-column>
                    <el-table-column
                            label="企业类型"
                            prop="entType">
                    </el-table-column>
                    <el-table-column
                            label="app版本号"
                            prop="versionCode">
                    </el-table-column>
                    <el-table-column
                            label="更新日志"
                            prop="description">
                        <template scope="scope">
                            <span style="text-overflow: ellipsis;white-space: nowrap;">{{scope.row.description}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="发布时间"
                            width="160px"
                            prop="createTime">
                    </el-table-column>
                    <el-table-column
                            label="状态"
                            prop="busiStatus">
                    </el-table-column>
                </el-table>
            </div>
            <div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
            <el-dialog title="APP详情" :visible.sync="dialogFormVisible" v-if="appRecord" :modal-append-to-body="false">
                <el-form label-position="right" label-width="160px">
                    <el-form-item label="app类型：">
                        {{appRecord.appType}}
                    </el-form-item>
                    <el-form-item label="企业类型：">
                        {{appRecord.entType}}
                    </el-form-item>
                    <el-form-item label="版本号：">
                        {{appRecord.versionCode}}
                    </el-form-item>
                    <el-form-item label="更新日志：">
                        {{appRecord.description}}
                    </el-form-item>
                    <el-form-item label="安装包：">
                        <a  :href="uploadPrev+'/download?fileID='+appRecord.fileId" target="_blank">点击下载</a>
                    </el-form-item>
                </el-form>
            </el-dialog>
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
            appTypeList: [{'value': '全部', 'code': ''},{'value': 'ANDROID', 'code': 'ANDROID'}, {'value': 'IOS', 'code': 'IOS'}],
            entTypeList: [{'value': '全部', 'code': ''},{'value': '处置企业', 'code': 'DISPOSITION'}, {'value': '产废企业', 'code': 'PRODUCTION'}],
            busiStatusList: [{'value': '全部', 'code': ''},{'value': '待发布', 'code': 'SUBMIT'}, {'value': '已发布', 'code': 'RELEASE'}],
            appType:'',
            entType:'',
            busiStatus:'',
            multipleSelection:[],
            dialogVisible:false,
            pageIndex:1,
            pageSize:10,
            total:0,
            dialogFormVisible:false,
            appRecord: {
                id: '',
                appType: '',
                entType: '',
                versionCode: '',
                description: '',
                fileId:'',
                fileName: ''
            },
            uploadPrev:IMG_PREV
        },
        created:function() {
            this.getList();
        },
        methods:{
            getList:function () {
                this.listLoading = true;
                var _this=this;
                var param={
                    pageIndex:this.pageIndex,
                    pageSize:this.pageSize,
                    appType:this.appType,
                    entType:this.entType,
                    busiStatus:this.busiStatus
                }
                ajax({
                    url:'/appManagement/listAppManagement.htm',
                    dataType:'json',
                    data:param,
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data.appList&&result.data.appList.length>0){
                            _this.total=result.data.pagingParameter.totalRecord;
                            _this.tableData=result.data.appList;
                        }else{
                            _this.tableData=[];
                            _this.total=0;
                        }
                    }
                })
            },
            changeCondition:function(){
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
            select:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请选择危废',{status:'info',timeout:1500});
                    return;
                }
                this.wasteSelected=true;
            },
            add:function () {
                window.location='<%=appPath%>/userManage/addAPP.htm?ticketId=<%=ticketId%>';
            },
            edit:function () {
                if(this.multipleSelection.length==0||this.multipleSelection.length>1){
                    $.notify('请选择一条进行编辑',{status:'info',timeout:2500});
                    return;
                }
                window.location='<%=appPath%>/appManagement/initAddAppManagement.htm?ticketId=<%=ticketId%>&appId='+this.multipleSelection[0]['id'];
            },
            del:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请至少选择一条进行删除',{status:'info',timeout:2500});
                    return;
                }
                var arr=[];
                for(var i in vue.multipleSelection){
                    var str=vue.multipleSelection[i]['id'];
                    arr.push(str);
                }
                vue.$confirm('此操作将删除该APP, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/appManagement/deleteAppMangement.htm?ticketId=<%=ticketId%>',
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
            },
            view:function (row, event, column) {
                if(this.multipleSelection.length!=1){
                    $.notify('请选择一条信息进行查看',{status:'info',timeout:1500});
                    return;
                }
                this.appRecord=this.multipleSelection[0];
                this.dialogFormVisible=true;
            },
            start:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请至少选择一条进行启动',{status:'info',timeout:2500});
                    return;
                }
                var arr=[];
                for(var i in vue.multipleSelection){
                    if(vue.multipleSelection[i]['busiStatus']=='已发布'  ){
                        $.notify('已发布应用程序不能再发布',{status:'info',timeout:2500});
                        return;
                    }
                    var str=vue.multipleSelection[i]['id'];
                    arr.push(str);
                }
                vue.$confirm('此操作将发最新应用程序, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/appManagement/releaseAppManagementStatus.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(arr),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status&&result.data){
                                $.notify('发布成功',{status:'success',timeout:2500});
                                vue.pageIndex=1;
                                vue.getList();
                            }
                        }
                    })
                }).catch(function(){});
            },
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