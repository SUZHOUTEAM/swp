<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="消息管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#CustomerSuggest"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterpriseWaste/style.css">
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>消息管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户建议</el-breadcrumb-item>
        </el-breadcrumb>
        <div>
            <div class="selectTable">
                <el-table  tooltip-effect="dark"
                          :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable" border
                           @row-click="view"
                          style="width: 100%">
                    <el-table-column
                            label="用户建议"
                            prop="suggestion">
                    </el-table-column>
                    <el-table-column
                            label="回访电话"
                            prop="phoneNo">
                    </el-table-column>
                    <el-table-column
                            label="公司/组织"
                            prop="company">
                    </el-table-column>
                    <el-table-column
                            label="提交时间"
                            prop="createTime">
                    </el-table-column>
                </el-table>
            </div>
            <div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
            <el-dialog title="建议详情" :visible.sync="dialogFormVisible" v-if="suggest" :modal-append-to-body="false">
                <el-form label-position="right" label-width="160px">
                    <el-form-item label="建议内容：">
                        {{suggest.suggestion}}
                    </el-form-item>
                    <el-form-item label="回访电话：">
                        {{suggest.phoneNo}}
                    </el-form-item>
                    <el-form-item label="公司/组织：">
                        {{suggest.company||'--'}}
                    </el-form-item>
                    <el-form-item label="提交时间：">
                        {{suggest.createTime||'--'}}
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
            total:0,
            suggest: {},
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
                    current:this.pageIndex,
                    size:this.pageSize,
                }
                ajax({
                    url:'/customerSuggestion/listCustomerSuggestion.htm?ticketId=<%=ticketId%>',
                    dataType:'json',
                    contentType:'application/json',
                    data:JSON.stringify(param),
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data.records&&result.data.records.length>0){
                            _this.total=result.data.total;
                            _this.tableData=result.data.records;
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
            view:function (row, event, column) {
                this.suggest=row;
                this.dialogFormVisible=true;
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