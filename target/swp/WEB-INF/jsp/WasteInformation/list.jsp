<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
    <jsp:include page="/common/top.jsp" flush="true">
        <jsp:param name="title" value="小课堂管理" />
    </jsp:include>
    <jsp:include page="/common/left.jsp" flush="true">
        <jsp:param name="menuId" value="#wasteInformation" />
    </jsp:include>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link rel="stylesheet" href="<%=appPath %>/main/common/elementui/index.css">
    <!-- 先引入 Vue -->
    <script src="<%=appPath %>/main/common/elementui/vue.min.js"></script>
    <!-- 引入组件库 -->
    <script src="<%=appPath %>/main/common/elementui/index.js"></script>
    <!-- start Dplus --><script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?id=1262787667",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("1262787667");</script><!-- end Dplus -->
    <section id="app">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>运营管理</el-breadcrumb-item>
            <el-breadcrumb-item>危废资讯</el-breadcrumb-item>
        </el-breadcrumb>
        <div >
            <el-row style="margin-bottom: 10px">
                <el-col :span="18">
                    <el-button type="primary" size="small" icon="el-icon-plus" @click="add">新增</el-button>
                    <el-button type="primary" size="small" icon="el-icon-view" @click="view">查看</el-button>
                    <el-button type="primary" size="small" icon="el-icon-edit" @click="edit">编辑</el-button>
                    <el-button type="primary" size="small" icon="el-icon-delete" @click="del">删除</el-button>
                </el-col>
                <el-col :span="6" align="right">
                    <el-button type="primary" size="small" icon="el-icon-share" @click="updateTohome" style="float: right;margin-left: 10px">更新到详情页面</el-button>
                </el-col>
            </el-row>
            <el-table :data="list" style="width: 100%" border @selection-change="handleSelectionChange" v-loading="listLoading">
                <el-table-column
                        prop="id"  type="selection" align="center">
                </el-table-column>
                <el-table-column
                    prop="title"
                    label="资讯标题">
                </el-table-column>
                <el-table-column
                        prop="clickAmount"
                        label="阅读次数">
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="发布时间">
                </el-table-column>
            </el-table>
            <div v-show="list&&list.length>0" align="right">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
        </div>
    </section>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>

<script>
    var vue = new Vue({
        el: '#app',
        data:{
            list:[],
            pageIndex:1,
            pageSize:10,
            total:0,
            listLoading:true,
            multipleSelection:[]
        },
        created:function () {
            this.getList();
        },
        mounted:function() {

        },
        methods:{
            getList:function () {
                var param={
                    ticketId:'<%=ticketId%>',
                    pageIndex:this.pageIndex,
                    pageSize:this.pageSize
                }
                this.listLoading=true;
              $.ajax({
                  url:'<%=appPath %>/wasteInformation/listWasteInformation',
                  data:param,
                  type:'post',
                  daraType:'json',
                  success:function (result) {
                      vue.listLoading=false;
                      if(result.status==1&&result.data.wasteInformationModels&&result.data.wasteInformationModels.length>0){
                          vue.list=result.data.wasteInformationModels;
                          vue.total=result.data.pagingParameter.totalRecord;
                      }else{
                          vue.list=[];
                          vue.total=0;
                      }
                  }
              })
            },
            updateTohome:function () {
                vue.$confirm('此操作将数据更新到详情页面, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    $.ajax({
                        url:'<%=appPath %>/wasteInformation/generateStaticJS',
                        type:'post',
                        daraType:'json',
                        success:function (result) {
                            vue.listLoading=false;
                            if(result.status==1&&result.data){
                                $.notify('更新成功',{status:'success',timeout:2500});
                            }else{
                                $.notify('更新失败',{status:'danger',timeout:2500});
                            }
                        }
                    })
                }).catch(function(){});
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
            handleSelectionChange:function(val){
                this.multipleSelection = val;
            },
            add:function () {
                window.location='<%=appPath%>/wasteInformation/add.htm?ticketId=<%=ticketId%>';
            },
            edit:function () {
                if(this.multipleSelection.length==0||this.multipleSelection.length>1){
                    $.notify('请选择一条进行编辑',{status:'info',timeout:2500});
                    return;
                }
                window.location='<%=appPath%>/wasteInformation/add.htm?ticketId=<%=ticketId%>&id='+this.multipleSelection[0]['id'];
            },
            view:function () {
                if(this.multipleSelection.length==0||this.multipleSelection.length>1){
                    $.notify('请选择一条进行查看',{status:'info',timeout:2500});
                    return;
                }
                window.location='<%=appPath%>/wasteInformation/add.htm?ticketId=<%=ticketId%>&id='+this.multipleSelection[0]['id']+'&action=view';
            },
            del:function () {
                if(this.multipleSelection.length==0){
                    $.notify('至少选择一条进行删除',{status:'info',timeout:2500});
                    return;
                }
                var arr=[];
                for(var i in vue.multipleSelection){
                    var str=vue.multipleSelection[i]['id'];
                    arr.push(str);
                }
                vue.$confirm('此操作将删除该资讯, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/wasteInformation/deleteWasteInformationById.htm?ticketId=<%=ticketId%>',
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
    });
</script>
</body>
