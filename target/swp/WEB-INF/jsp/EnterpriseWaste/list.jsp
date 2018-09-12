<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<c:if test="${isSysUser}">
    <jsp:include page="/common/top.jsp" flush="true">
        <jsp:param name="title" value="企业信息"/>
    </jsp:include>
    <jsp:include page="/common/left.jsp" flush="true">
        <jsp:param name="menuId" value="#enterpriseManagement" />
    </jsp:include>
</c:if>
<c:if test="${not isSysUser}">
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
        <jsp:param name="title" value="企业产废信息"/>
    </jsp:include>
    <jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
        <jsp:param name="menuId" value="#wasteManage" />
    </jsp:include>
</c:if>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterpriseWaste/style.css">
<style type="text/css">
    .el-upload--picture-card{
        display: none;
    }
    .el-form-item{
        margin-bottom: 11px;
    }
</style>
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>我的企业</el-breadcrumb-item>
            <el-breadcrumb-item>产废管理</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="危废名称">
                <el-input v-model="formInline.wasteName" placeholder="危废名称"></el-input>
            </el-form-item>
            <el-form-item label="八位码">
                <el-input v-model="formInline.wasteCode" placeholder="八位码"></el-input>
            </el-form-item>
            <el-form-item label="二位码">
                <el-input v-model="formInline.wasteTypeDesc" placeholder="二位码"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit" size="small">查询</el-button>
            </el-form-item>
        </el-form>
        <div style="margin-bottom: 10px">
            <el-button type="primary" size="small" icon="el-icon-plus" @click="add">新增</el-button>
            <el-button type="primary" size="small" icon="el-icon-view" @click="view">查看</el-button>
            <el-button type="primary" size="small" icon="el-icon-edit" @click="edit">编辑</el-button>
            <el-button type="primary" size="small" icon="el-icon-delete" @click="del">删除</el-button>
        </div>
        <div class="selectTable">
            <el-table @selection-change="handleSelectionChange" tooltip-effect="dark"
                      :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable" border
                      style="width: 100%">
                <el-table-column
                        prop="entWasteId"  type="selection" align="center">
                </el-table-column>
                <el-table-column
                        label="危废名称"
                        prop="wasteName">
                </el-table-column>
                <el-table-column
                        label="危废编码"
                        prop="wasteCode">
                </el-table-column>
                <el-table-column
                        label="二位码"
                        prop="wasteTypeDesc">
                </el-table-column>
            </el-table>
        </div>
        <div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <el-dialog title="危废详情" :visible.sync="dialogFormVisible" v-if="enterpriseWaste" :modal-append-to-body="false">
            <el-form label-position="right" label-width="160px">
                <el-form-item label="八位码：">
                    {{enterpriseWaste.wasteCode}}
                </el-form-item>
                <el-form-item label="危废名称：">
                    {{enterpriseWaste.wasteName}}
                </el-form-item>
                <el-form-item label="有害物质名称和含量：">
                    {{enterpriseWaste.harmfulSubstance||'--'}}
                </el-form-item>
                <el-form-item label="单位：">
                    {{enterpriseWaste.unitValue}}
                </el-form-item>
                <el-form-item label="危废图片：">
                    <el-upload
                            action="xx"
                            list-type="picture-card"
                            :file-list="enterpriseWasteImgList"
                            v-if="enterpriseWasteImgList.length>0"
                            :on-preview="handlePictureCardPreview"
                            :disabled="true"></el-upload>
                    <span v-else>暂无图片信息</span>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog :visible.sync="imgDialogVisible" width="90%" :modal-append-to-body="false" title="危废图片">
            <img :src="dialogImageUrl" alt="">
        </el-dialog>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: {
            tableData: [],
            listLoading:true,
            multipleSelection:[],
            wasteSelected:false,
            publishLoading:false,
            dialogVisible:false,
            totalQtyText:'0吨',
            totalQtyT:'',
            remark:'',
            pageIndex:1,
            pageSize:10,
            total:0,
            formInline: {
                wasteCode: '',
                wasteName: '',
                wasteTypeDesc:''
            },
            enterpriseWaste: {
                wasteCode: '',
                wasteName: '',
                wasteId:'',
                wasteNameId:'',
                harmfulSubstance: '',
                unitCode:'',
                entWasteId:'',
            },
            enterpriseWasteImgList:[],
            dialogImageUrl:'',
            imgDialogVisible:false,
            dialogFormVisible:false
        },
        created:function() {
            this.getList();
        },
        methods:{
            getList:function () {
                this.listLoading = true;
                var _this=this;
                var param={
                    wasteCode:this.formInline.wasteCode,
                    wasteName:this.formInline.wasteName,
                    wasteTypeDesc:this.formInline.wasteTypeDesc
                }
                ajax({
                    url:'/entWaste/listEntWaste.htm?ticketId=<%=ticketId%>&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,
                    dataType:'json',
                    contentType:'application/json',
                    data:JSON.stringify(param),
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data.entWasteList&&result.data.entWasteList.length>0){
                            _this.total=result.data.paging.totalRecord;
                            _this.tableData=result.data.entWasteList;
                        }
                    }
                })
            },
            onSubmit:function() {
//                console.log(this.formInline.wasteCode+' '+this.formInline.wasteName+' '+this.formInline.wasteTypeDesc);
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
            select:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请选择危废',{status:'info',timeout:1500});
                    return;
                }
                this.wasteSelected=true;
            },
            add:function () {
                window.location='<%=appPath%>/entRelease/publish.htm?ticketId=<%=ticketId%>&breadcrumbName=产废管理';
            },
            del:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请至少选择一条进行删除',{status:'info',timeout:2500});
                    return;
                }
                var flag=false;
                for(var i in this.multipleSelection){
                    if(this.multipleSelection[i]['inquiried']){
                        flag=true;
                        break;
                    }
                }
                if(flag){
                    $.notify('该危废中已有询价，不能删除',{status:'info',timeout:2500});
                    return;
                }
                vue.$confirm('此操作将删除该产废, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    var arr=[];
                      for(var i in vue.multipleSelection){
                          var obj={};
                          obj.entWasteId=vue.multipleSelection[i]['entWasteId'];
                          obj.Inquiried=vue.multipleSelection[i]['inquiried'];
                          arr.push(obj)
                      }
                      ajax({
                        url:'/entWaste/deleteEntWaste.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(arr),
                        dataType:'json',
                        contentType:'application/json',
                        success:function (data) {
                            if(data.status==1&&data.data){
                                $.notify('删除成功',{status:'success',timeout:2500});
                                vue.pageIndex=1;
                                vue.getList();
                            }
                        }
                    })
                }).catch(function(){});
            },
            edit:function () {
                if(this.multipleSelection.length!=1){
                    $.notify('请选择一个危废信息进行编辑',{status:'info',timeout:2500});
                    return;
                }
                if(this.multipleSelection[0]['inquiried']){
                    $.notify('该危废下有询价，不能编辑',{status:'info',timeout:2500});
                    return;
                }
                window.location='<%=appPath%>/entRelease/publish.htm?ticketId=<%=ticketId%>&entWasteId='+this.multipleSelection[0]['entWasteId']+'&action=edit&breadcrumbName=产废管理';
            },
            view:function () {
                if(this.multipleSelection.length!=1){
                    $.notify('请选择一个危废信息进行查看',{status:'info',timeout:1500});
                    return;
                }
                this.enterpriseWaste=this.multipleSelection[0];
                this.dialogFormVisible=true;
                this.getImgList();
            },
            getImgList:function () {
                var fileList=upload.getImgListByBusinessCode(this.enterpriseWaste.entWasteId);
                this.enterpriseWasteImgList=[];
                for(var i in fileList){
                    var obj=fileList[i];
                    obj.name='0'+(i*1+1)+'.jpg';
                    obj.url=IMG_VIEW_URL+'&fileID='+obj['fileID'];
                    this.enterpriseWasteImgList.push(obj);
                }
            },
            handlePictureCardPreview:function(file) {
                this.dialogImageUrl = file.url;
                this.imgDialogVisible = true;
            },
            unselected:function () {
                this.wasteSelected=false;
            },
            closeDialog:function () {
                this.wasteSelected=false;
                this.dialogVisible=false;
                this.$refs.dataSelectTable.clearSelection();
                for(var i in this.tableData){
                    this.tableData[i].quality=0;
                }
            },
        }

    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
        var rs = window.globalInit();
        rs.done(function () {
//            init();
        });
    });
</script>