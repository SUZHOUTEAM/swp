<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="代理登记"/>
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#agencyRegistration"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css">
<link href="<%=appPath %>/css/enterprise/agency.css?5" rel="stylesheet" type="text/css" />
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>我的易废圈</el-breadcrumb-item>
            <el-breadcrumb-item>代理企业信息登记</el-breadcrumb-item>
        </el-breadcrumb>
        <div>
            <el-row style="margin-bottom: 10px">
                <el-button type="primary" size="small" icon="el-icon-plus" @click="add">登记新的代理企业</el-button>
                <%--<el-button type="primary" size="small" icon="el-icon-delete" @click="del">删除</el-button>--%>
            </el-row>
            <div class="selectTable">
                <div v-for="(item,index) in tableData" class="item">
                    <div class="item-title"><span class="item-index">{{(pageIndex-1)*pageSize+index+1}}.</span><span class="item-title-text">{{item.customerName}}</span>
                        <a href="javascript:" @click="edit(index)">变更</a></div>
                    <div class="item-imgs">
                        <div class="item-img item-img1">
                            <div class="image" v-if="item.busiLicImg" @click="showLargeImg(item.busiLicImg)" v-loading="!item['busiLicImg_imgLoaded']">
                                <img v-show="item['busiLicImg_imgLoaded']" :src="item.busiLicImg" />
                            </div>
                            <div class="item-text">营业执照</div>
                        </div>
                        <div  class="item-img item-img2">
                            <div class="image" v-if="item.licImg" @click="showLargeImg(item.licImg)" v-loading="!item['licImg_imgLoaded']">
                                <img v-show="item['licImg_imgLoaded']" :src="item.licImg" />
                            </div>
                            <div class="item-text">经营许可证</div>
                        </div>
                        <div  class="item-img item-img3">
                            <div class="image" v-if="item.authImg" @click="showLargeImg(item.authImg)" v-loading="!item['authImg_imgLoaded']">
                                <img v-show="item['authImg_imgLoaded']" :src="item.authImg" />
                            </div>
                            <div class="item-text">授权书</div>
                        </div>
                    </div>
                </div>
            </div>
            <div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[pageSize,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
            <div class="dialog" v-show="dialogVisible" @click.self="dialogVisible=false">
                <a href="javascript:" class="close-dialog" title="关闭" @click="dialogVisible=false"><i class="el-icon-circle-close-outline"></i></a>
                <img :src="largeImg" class="largeImg"/>
            </div>
            <%--<el-dialog
                    :title="title"
                    :visible.sync="dialogVisible"
                    :modal-append-to-body="false"
                    max-width="800px">
                <img :src="largeImg" class="largeImg"/>
            </el-dialog>--%>
        </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/main/common/resLoader.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>

<script>
    var vue=new Vue({
        el: '#app',
        data: {
            tableData: [],
            listLoading:true,
            pageIndex:1,
            pageSize:5,
            total:0,
            dialogVisible:false,
            largeImg:'',
            title:'',
        },
        created:function() {
            this.getList();
        },
        methods:{
            showLargeImg:function (url) {
                this.dialogVisible=true;
                this.largeImg=url;
            },
            getList:function () {
                this.listLoading = true;
                var _this=this;
                var param={
                    pageIndex:this.pageIndex,
                    pageSize:this.pageSize
                };
                ajax({
                    url:'/facilitatorCustomer/listFacilitatorCustomer.htm',
                    dataType:'json',
                    data:param,
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data.customerList&&result.data.customerList.length>0){
                            var customerList=result.data.customerList;
                            for(var i in customerList){
                                if(customerList[i]['busiLicImg']){
                                    var imgUrl=IMG_VIEW_URL+'&fileID='+customerList[i]['busiLicImg'];
                                    customerList[i]['busiLicImg']=imgUrl;
                                    customerList[i]['busiLicImg_imgLoaded']=false;
                                    vue.getLoaderEnt(imgUrl,i,'busiLicImg').start();
                                }else{
                                    customerList[i]['busiLicImg_imgLoaded']=true;
                                    customerList[i]['busiLicImg']='http://www.yifeiwang.com/img/source/upload.png';
                                }
                                if(customerList[i]['licImg']){
                                    var imgUrl=IMG_VIEW_URL+'&fileID='+customerList[i]['licImg'];
                                    customerList[i]['licImg']=imgUrl;
                                    customerList[i]['licImg_imgLoaded']=false;
                                    vue.getLoaderEnt(imgUrl,i,'licImg').start();
                                }else{
                                    customerList[i]['licImg_imgLoaded']=true;
                                    customerList[i]['licImg']='http://www.yifeiwang.com/img/source/upload.png';
                                }
                                if(customerList[i]['authImg']){
                                    var imgUrl=IMG_VIEW_URL+'&fileID='+customerList[i]['authImg'];
                                    customerList[i]['authImg_imgLoaded']=false;
                                    customerList[i]['authImg']=imgUrl;
                                    vue.getLoaderEnt(imgUrl,i,'authImg').start();
                                }else{
                                    customerList[i]['authImg_imgLoaded']=true;
                                    customerList[i]['authImg']='http://www.yifeiwang.com/img/source/upload.png';
                                }
                            }
                            _this.total=result.data.pagingParameter.totalRecord;
                            _this.tableData=customerList;
                        }else{
                            _this.tableData=[];
                            _this.total=0;
                        }
                    }
                })
            },
            search:function () {
              this.pageIndex=1;
              this.getList();
            },
            handleSizeChange:function(val) {
                this.pageIndex=1;
                this.pageSize = val;
                this.getList();
            },
            handleCurrentChange:function(val) {
                this.pageIndex = val;
                this.getList();
            },
            add:function () {
                window.location='<%=appPath%>/enterprise/addAgency.htm?ticketId=<%=ticketId%>';
            },
            edit:function (index) {
                window.location='<%=appPath%>/enterprise/addAgency.htm?ticketId=<%=ticketId%>&id='+vue.tableData[index]['customerId'];
            },
            del:function (id) {
//                if(this.multipleSelection.length==0){
//                    $.notify('请至少选择一条进行删除',{status:'info',timeout:2500});
//                    return;
//                }
                var arr=[id];
//                for(var i in vue.multipleSelection){
//                    var str=vue.multipleSelection[i]['id'];
//                    arr.push(str);
//                }
                vue.$confirm('此操作将解除该客户绑定, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/facilitatorCustomer/deleteFacilitatorCustomer.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(arr),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status&&result.data){
                                $.notify('客户删除成功',{status:'success',timeout:2500});
                                vue.pageIndex=1;
                                vue.getList();
                            }
                        }
                    })
                }).catch(function(){});
            },
            getLoaderEnt:function(imgUrl,index,type){
                var loader = new resLoader({
                    resources:[imgUrl],
                    onStart: function(total) {

                    },
                    onProgress: function(current, total) {
                        // $('.loading').html('正在加载中...'+Math.floor(current*100/total)+'%');
                    },
                    onComplete: function(total) {
                        var item=vue.tableData[index];
                        if(item){
                            item[type+'_imgLoaded']=true;
                            Vue.set(vue.tableData,index,item);
                        }
                    }
                });
                return loader;
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

        });
    });
    Date.prototype.format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    }
</script>