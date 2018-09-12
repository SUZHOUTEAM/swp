<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
	<jsp:param name="title" value="智能运营"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
	<jsp:param name="menuId" value="#operationList"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterpriseWaste/style.css">
<section id="app">
	<template>
		<el-breadcrumb separator="/">
			<el-breadcrumb-item>运营管理</el-breadcrumb-item>
			<el-breadcrumb-item>运营计划</el-breadcrumb-item>
		</el-breadcrumb>
		<div >
			<el-row style="margin-bottom: 10px">
				<el-col :span="15">
					<el-button type="primary" size="small" icon="el-icon-plus" @click="add">新增</el-button>
					<el-button type="primary" size="small" icon="el-icon-edit" @click="edit">编辑</el-button>
					<el-button type="primary" size="small" icon="el-icon-delete" @click="del">删除</el-button>
				</el-col>
				<el-col :span="9" align="right">
					<el-button type="primary" size="small" icon="el-icon-time" @click="start">启动</el-button>
					<el-button type="primary" size="small" icon="el-icon-remove-outline" @click="stop">停止</el-button>
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
							label="运营计划名称"
							prop="operationName">
					</el-table-column>
					<el-table-column
							label="开始时间"
							prop="startTime">
					</el-table-column>
					<el-table-column
							label="结束时间"
							prop="endTime">
					</el-table-column>
					<el-table-column
							label="覆盖的产废企业家数"
							prop="totalEntCount">
					</el-table-column>
					<el-table-column
							label="已查询企业数"
							prop="readEntCount">
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
		</div>
	</template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: {
            tableData: [],
            listLoading:true,
            multipleSelection:[],
            pageIndex:1,
            pageSize:10,
            total:0
        },
        created:function() {
            this.getList();
        },
        methods:{
            getList:function () {
                this.listLoading = true;
                var _this=this;
                var param={
                    current:this.pageIndex,
                    size:this.pageSize,
                }
                ajax({
                    url:'/websiteOperation/listWebSiteOperationInfo.htm?ticketId=<%=ticketId%>',
                    dataType:'json',
                    contentType:'application/json',
                    data:JSON.stringify(param),
                    success:function (result) {
                        _this.listLoading = false;
                        if(result.status==1&&result.data.websiteOperationList&&result.data.websiteOperationList.length>0){
                            _this.total=result.data.pagingParameter.totalRecord;
                            _this.tableData=result.data.websiteOperationList;
                        }else{
                            _this.tableData=[];
                            _this.total=0;
						}
                    }
                })
            },
            onSubmit:function() {
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
			add:function () {
				window.location='<%=appPath%>/websiteOperation/addOperation.htm?ticketId=<%=ticketId%>';
            },
            edit:function () {
                if(this.multipleSelection.length==0||this.multipleSelection.length>1){
                    $.notify('请选择一条进行编辑',{status:'info',timeout:2500});
                    return;
                }
                if(this.multipleSelection[0]['busiStatus']=='开始'){
                    $.notify('已启动的运营计划不能编辑',{status:'info',timeout:2500});
                    return;
                }
                window.location='<%=appPath%>/websiteOperation/addOperation.htm?ticketId=<%=ticketId%>&operationId='+this.multipleSelection[0]['id'];
            },
            del:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请至少选择一条进行删除',{status:'info',timeout:2500});
                    return;
                }
                var arr=[];
                for(var i in vue.multipleSelection){
                    if(vue.multipleSelection[i]['busiStatus']=='开始'){
                        $.notify('已启动运营计划不能删除',{status:'info',timeout:2500});
                        return;
                    }
                    var str=vue.multipleSelection[i]['id'];
                    arr.push(str);
                }
                vue.$confirm('此操作将删除该运营计划, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
					ajax({
						url:'/websiteOperation/deleteWebSiteOperationInfo.htm?ticketId=<%=ticketId%>',
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
			start:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请至少选择一条进行启动',{status:'info',timeout:2500});
                    return;
                }
                var arr=[];
                for(var i in vue.multipleSelection){
                    if(vue.multipleSelection[i]['busiStatus']=='开始' || vue.multipleSelection[i]['busiStatus']=='运行中' ){
                        $.notify('已启动或运行中运营计划不能再启动',{status:'info',timeout:2500});
                        return;
					}
                    var str=vue.multipleSelection[i]['id'];
                    arr.push(str);
                }
                vue.$confirm('此操作将启动该运营计划, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/websiteOperation/startWebSiteOperationInfo.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(arr),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status&&result.data){
                                $.notify('启动成功',{status:'success',timeout:2500});
                                vue.pageIndex=1;
                                vue.getList();
                            }
                        }
                    })
                }).catch(function(){});
            },
			stop:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请至少选择一条进行停止',{status:'info',timeout:2500});
                    return;
                }
                var arr=[];
                for(var i in vue.multipleSelection){
                    if(vue.multipleSelection[i]['busiStatus']!='运行中' && vue.multipleSelection[i]['busiStatus']!='开始'){
                        $.notify('已停止和未开始的运营计划不能停止',{status:'info',timeout:2500});
                        return;
                    }
                    var str=vue.multipleSelection[i]['id'];
                    arr.push(str);
                }
                vue.$confirm('此操作将停止该运营计划, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/websiteOperation/stopWebSiteOperationInfo.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(arr),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status&&result.data){
                                $.notify('停止成功',{status:'success',timeout:2500});
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