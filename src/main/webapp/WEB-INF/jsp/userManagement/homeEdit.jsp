<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="首页设置"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#homeSetting"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>运营管理</el-breadcrumb-item>
            <el-breadcrumb-item onclick="history.go(-1);">首页设置</el-breadcrumb-item>
            <el-breadcrumb-item>创建首页内容</el-breadcrumb-item>
        </el-breadcrumb>
        <div >
            <div class="panel panel-body">
                <el-form label-position="right" label-width="160px" style="max-width: 800px;margin-top: 16px;" :inline="true"
                         :model="homeConfig" ref="homeConfigValidateForm" :rules="homeConfigRules">
                    <el-form-item label="区域：" prop="cantonCode">
                        <el-select v-model="homeConfig.cantonCode" placeholder="区域" style="width: 100%">
                            <el-option :label="item.value" :value="item.code" v-for="(item,index) in provinces"
                                       :key="item.code"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="企业：" prop="entId">
                            <el-select v-model="homeConfig.entId"
                                        filterable
                                        remote
                                        reserve-keyword
                                        placeholder="请输入企业名称"
                                        :remote-method="remoteMethod"
                                        :loading="outLoading">
                                <el-option
                                        v-for="item in czList"
                                        :key="item.entId"
                                        :label="item.entName"
                                        :value="item.entId">
                                </el-option>
                            </el-select>
                    </el-form-item>
                    <el-form-item label="栏目：" prop="section">
                        <el-select v-model="homeConfig.section" placeholder="请选择栏目" style="width: 100%" @change="changeSection">
                            <el-option :label="item.value" :value="item.code" v-for="(item,index) in sectionList"
                                       :key="item.code"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="类别：" prop="wasteType" v-if="homeConfig.section!='DISPOSITION'&&homeConfig.section!=''">
                        <el-select v-model="homeConfig.wasteType"
                                   filterable
                                   remote
                                   reserve-keyword
                                   placeholder="请输入并选择类别"
                                   :remote-method="remoteMethod2"
                                   :loading="outLoading2">
                            <el-option
                                    v-for="item in typeList"
                                    :key="item.code"
                                    :label="item.code"
                                    :value="item.code">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="版面：" prop="page">
                        <el-input-number v-model="homeConfig.page" :min="1" :max="10" label="版面"></el-input-number>
                    </el-form-item>
                    <el-form-item label="序号：" prop="index">
                        <el-input-number v-model="homeConfig.index" :min="1" :max="10" label="序号"></el-input-number>
                    </el-form-item>
                    <el-form-item label="开始时间：" prop="startDate">
                        <el-date-picker
                                v-model="homeConfig.startDate"
                                type="date"
                                placeholder="选择开始时间">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束时间：" prop="endDate">
                        <el-date-picker
                                v-model="homeConfig.endDate"
                                type="date"
                                placeholder="选择结束时间">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item align="left"  label=" ">
                        <el-button type="primary" class="save" @click="saveHomeConfig">保存</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/main/common/provinces.js"></script>
<script>
    var vue = new Vue({
        el: '#app',
        data: {
            homeConfig: {
                id: getParam('id')?getParam('id'):'',
                cantonCode:'',//区域
                entId:'',//企业ID
                section:'',//栏目
                wasteType:'',//类别
                page:'',//版面
                index:'',//序号
                startDate:'',//开始日期
                endDate:''//结束日期
            },
            homeConfigAction: 'add',
            homeConfigRules: {
                entId: [
                    {required: true, message: '请选择企业', trigger: 'change'}
                ],
                section: [
                    {required: true, message: '请选择栏目', trigger: 'change'}
                ],
                startDate:[
                    {required: true,type:'date', message: '请选择开始日期', trigger: 'change'}
                ],
                endDate:[
                    {required: true,type:'date', message: '请选择结束日期', trigger: 'change'}
                ],
            },
            provinces:provinces,
            czList:[],
            outLoading:false,
            sectionList:[{'code':'DISPOSITION',value:'处置'},{'code':'RECYCLING',value:'利用'},{'code':'SPECIALCATEGORY',value:'特殊类别'}],
            wasteInfoList:[],
            wasteTypeList:[],
            typeList:[],
            outLoading2:false
        },
        mounted: function () {
            this.getWasteInfoList();
            this.getWasteTypeList();
            if(this.homeConfig.id){
                this.getHomeConfigInfo();
            }
        },
        methods: {
            saveHomeConfig: function () {
                this.$refs['homeConfigValidateForm'].validate(function (valid) {
                    if (valid) {
                        vue.savehomeConfigAction();
                    }
                });
            },
            getWasteInfoList:function () {
                ajax({
                    url:'/yifeiwaste/listWasteCode',
                    success:function (result) {
                        if(result.status==1&&result.data){
                            vue.wasteInfoList=result.data;
                        }
                    }
                })
            },
            getWasteTypeList:function () {
                ajax({
                    url:'/yifeiwaste/listWasteType',
                    success:function (result) {
                        if(result.status==1&&result.data){
                            vue.wasteTypeList=result.data;
                        }
                    }
                })
            },
            getHomeConfigInfo: function () {
                ajax({
                    url: '/enterpriseConfiguration/getEnterpriseConfig.htm?ticketId=<%=ticketId%>',
                    data: JSON.stringify({id: this.homeConfig.id,rows:1}),
                    contentType:'application/json',
                    success: function (result) {
                        console.log(result);
                        if(result.status==1){
                            result.data.startDate=new Date(result.data.startDate.replace(/-/g,'/'));
                            result.data.endDate=new Date(result.data.endDate.replace(/-/g,'/'));
                            result.data.id=result.data.configId;
                            vue.homeConfig=result.data;
                            vue.remoteMethod(result.data.entName);
                        }
                    }
                });
            },
            savehomeConfigAction: function () {
                if(!this.homeConfig.id){//新增
                    this.homeConfig.national=this.homeConfig.cantonCode?'0':'1';
                    var date=this.homeConfig.endDate;
                    var endDate=new Date(date.getFullYear(),date.getMonth()+1,date.getDate(),23,59,59);
                    Vue.set(this.homeConfig,'endDate',endDate);
                    ajax({
                        url:'/enterpriseConfiguration/saveEnterpriseConfiguration.htm',
                        data:this.homeConfig,
                        success:function (result) {
                            if(result.status==1&&result.data){
                                $.notify('首页内容创建成功',{status:'success',timeout:2000});
                                setTimeout(function () {
                                    history.go(-1);
                                },2000)
                            }
                        }
                    })
                }else{//编辑
                    ajax({
                        url:'/enterpriseConfiguration/updateEnterpriseConfiguration.htm',
                        data:this.homeConfig,
                        success:function (result) {
                            if(result.status==1&&result.data){
                                $.notify('修改首页内容成功',{status:'success',timeout:2000});
                                setTimeout(function () {
                                    history.go(-1);
                                },2000)
                                <%--location='<%=appPath%>/appManagement/initListAppManagement.htm?ticketId=<%=ticketId%>';--%>
                            }
                        }
                    })
                }

            },
            remoteMethod:function (query) {
                this.outLoading = true;
                ajax({
                    url:'/userservice/getSuggestEnterpriseList.htm',
                    data:{
                        enterpriseName:query,
                        enterpriseType:'DISPOSITION'
                    },
                    success:function (result) {
                        vue.outLoading = false;
                        console.log(result);
                        if(result.status==1){
                            vue.czList=result.data.enterpriselist;
                        }
                    }
                })
            },
            remoteMethod2:function (query) {
                this.outLoading2=true;
                var arr=[];
                if(this.homeConfig.section=='RECYCLING'){
                    arr=this.wasteTypeList;
                }
                if(this.homeConfig.section=='SPECIALCATEGORY'){
                    arr=this.wasteInfoList;
                }
                var results =[];
                for(var i in arr){
                    if(arr[i]['code'].indexOf(query)>-1){
                        results.push(arr[i]);
                    }
                }
                this.typeList=results;
                this.outLoading2=false;
            },
            createStateFilter:function(queryString) {
                return function(state){
                    return (state.code.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            changeSection:function () {
                this.homeConfig.wasteType='';
            },
        }

    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function () {
    });
</script>