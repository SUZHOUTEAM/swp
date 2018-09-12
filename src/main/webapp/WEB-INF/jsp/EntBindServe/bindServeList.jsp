<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="绑定服务列表"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#bindServeList"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/main/common/elementui/index.css">
<section id="app" v-cloak>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item>服务管理</el-breadcrumb-item>
        <el-breadcrumb-item>购买服务列表</el-breadcrumb-item>
    </el-breadcrumb>
    <div >
        <el-form :model="entBindServeParam" :inline="true" class="demo-form-inline">
            <el-form-item label="服务类型">
                <el-select v-model="entBindServeParam.serviceType" placeholder="选择服务类型" style="margin:0 12px" @change="search">
                    <el-option v-for="item in serviceTypes" :key="item.code" :label="item.value" :value="item.code">
                    </el-option>
                </el-select>
            </el-form-item>
            <%--<el-form-item>--%>
                <%--<el-button type="primary" @click="listBindServe">查询</el-button>--%>
            <%--</el-form-item>--%>
        </el-form>
        <el-table :data="entBindServeModels" border style="width:100%">
            <el-table-column
                    prop="userName"
                    width="100px"
                    label="用户名称">
            </el-table-column>
            <el-table-column
                    prop="bindEntName"
                    width="200px"
                    label="所在公司">
            </el-table-column>
            <el-table-column
                    prop="PhoneNum"
                    width="120px"
                    label="用户电话">
            </el-table-column>
            <el-table-column
                    prop="serviceType"
                    width="100px"
                    label="服务类型">
            </el-table-column>
            <el-table-column
                    prop="remark"
                    label="备注">
            </el-table-column>
        </el-table>
        <div v-show="entBindServeModels&&entBindServeModels.length>0" align="right">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                           :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize"
                           layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
    </div>
    </div>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath %>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath %>/main/common/elementui/index.js"></script>
<script>
    var vue = new Vue({
        el: '#app',
        data: {
            entBindServeModels: [],
            pageIndex: 1,
            pageSize: 10,
            total: 0,
            entBindServeParam: {
                serviceType: ''
            },
            serviceTypes: [{code: 'RESOURCE_POOL', value: '资源池'}, {code: 'ACTIVITY', value: '活动'}, {
                code: 'GOLDEN',
                value: '公爵套餐'
            }, {code: 'SLIVER', value: '伯爵套餐'}, {code: 'CUPRUM', value: '候爵套餐'}]
        },
        created: function () {
            this.listBindServe();
        },
        mounted: function () {

        },
        methods: {
            search:function () {
              this.pageIndex=1;
              this.entBindServeModels=[];
              this.listBindServe();
            },
            listBindServe:function () {
                var param = {
                    serviceType: this.entBindServeParam.serviceType
                };
                $.ajax({
                    url:'<%=appPath%>/entBindServe/listBindServe.htm?ticketId=<%=ticketId%>&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,
                    data:JSON.stringify(param),
                    dataType:'json',
                    contentType: 'application/json',
                    success: function (result) {
                        console.log(result);
                        if (result.status == 1) {
                            vue.entBindServeModels = result.data.entBindServeModels;
                            vue.total = result.data.pagingParameter.totalRecord;
                        }
                    }
                })
            },
            handleSizeChange:function (val) {
                this.pageIndex = 1;
                this.pageSize = val;
                this.listBindServe();
            },
            handleCurrentChange:function (val) {
                this.pageIndex = val;
                this.listBindServe();
            }
        }
    });
</script>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>

