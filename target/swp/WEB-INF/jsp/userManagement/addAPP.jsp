<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="APP管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#appManager"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/user/appRecord.css">
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item onclick="history.go(-1);">APP管理</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.appId?'编辑':'创建'}}APP管理</el-breadcrumb-item>
        </el-breadcrumb>
        <div>
            <div>
                <el-form label-position="right" label-width="160px" style="max-width: 800px;margin-top: 16px;"
                         :model="appRecord" ref="appRecordValidateForm" :rules="appRecordRules">
                    <el-form-item label="app类型：" prop="appType">
                        <el-select v-model="appRecord.appType" placeholder="请选择APP类型" style="width: 100%">
                            <el-option :label="item.value" :value="item.code" v-for="(item,index) in appTypeList"
                                       :key="item.code"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="企业类型：" prop="entType">
                        <el-select v-model="appRecord.entType" placeholder="请选择企业类型" style="width: 100%">
                            <el-option :label="item.value" :value="item.code" v-for="(item,index) in entTypeList"
                                       :key="item.code"></el-option>
                        </el-select>
                    </el-form-item>
                    <%--<el-form-item label="上传安装包：" prop="fileName">--%>
                        <%--&lt;%&ndash;<div class="el-upload__tip" v-if="action!='view'" style="margin-top: 0">只能上传jpg/png/gif文件，最多3张照片</div>&ndash;%&gt;--%>
                        <%--<el-upload--%>
                                <%--ref="appRecordUpload"--%>
                                <%--:action="uploadAction"--%>
                                <%--:data="appRecordParam"--%>
                                <%--:disabled="appRecordAction=='view'"--%>
                                <%--:show-file-list="false"--%>
                                <%--:on-change="handleAvatarSuccess"--%>
                                <%--:on-success="appRecordImgUploadSuccess"--%>
                                <%--:on-error="imgUploadError"--%>
                                <%--v-if="!appRecord.fileId"--%>
                                <%--:auto-upload="false">--%>
                            <%--<el-button size="small" type="primary" v-if="!appRecord.fileName">点击上传</el-button>--%>
                            <%--<span v-else>{{appRecord.fileName}}</span>--%>
                        <%--</el-upload>--%>
                            <%--<a v-else :href="uploadPrev+'/download?fileID='+appRecord.fileId" target="_blank">点击下载</a>--%>
                    <%--</el-form-item>--%>
                    <el-form-item label="版本号：" prop="versionCode">
                        <el-input v-model="appRecord.versionCode" placeholder="请输入版本号"></el-input>
                    </el-form-item>
                    <el-form-item label="更新日志：" prop="description">
                        <el-input v-model="appRecord.description" type="textarea" :rows="3"
                                  placeholder="请输入更新日志，多个功能用“%”分割"></el-input>
                    </el-form-item>
                    <el-form-item align="left">
                        <el-button type="primary" class="save" @click="saveAppRecord">保存</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>
<script>
    var vue = new Vue({
        el: '#app',
        appPath:'',
        data: {
            appId: getParam('appId') ? getParam('appId') : '',
            appRecord: {
                id: '',
                appType: '',
                entType: '',
                versionCode: '',
                description: '',
                fileId:'',
                fileName: ''
            },
            appTypeList: [{'value': 'ANDROID', 'code': 'ANDROID'}, {'value': 'IOS', 'code': 'IOS'}],
            entTypeList: [{'value': '处置企业', 'code': 'DISPOSITION'}, {'value': '产废企业', 'code': 'PRODUCTION'}],
            fileID: '',
            uploadAction: upload.IMG_URL + '/upload',
            appRecordAction: 'add',
            appRecordRules: {
                appType: [
                    {required: true, message: '请选择app类型', trigger: 'blur'}
                ],
                entType: [
                    {required: true, message: '请选择企业类型', trigger: 'blur'}
                ],
//                fileName: [
//                    {required: true, message: '请选择安装包', trigger: 'blur'}
//                ],
                description: [
                    {required: true, message: '请输入更新日志', trigger: 'blur'}
                ],
                versionCode: [
                    {required: true, message: '请输入版本号', trigger: 'blur'}
                ],
            },
            appRecordParam: {'appKey': APPKEY, 'prodID': PRODID, businessCode: upload.randomChar(19)},
            uploadPrev:IMG_PREV
        },
        mounted: function () {
            if(this.appId!=''){
                this.getAppInfo();
            }
        },
        methods: {
            saveAppRecord: function () {
                this.$refs['appRecordValidateForm'].validate(function (valid) {
                    if (valid) {
                        vue.saveAppRecordAction();
                    }
                });
            },
            getAppInfo: function () {
                ajax({
                    url: '/appManagement/getAppManagementById.htm',
                    data: {
                        id: this.appId
                    },
                    success: function (result) {
                        if(result.status==1){
                            vue.appRecord=result.data;
                        }

                    }
                });
            },
            saveAppRecordAction: function () {
                if(vue.appId==''){
                    ajax({
                        url:'/appManagement/saveAppManagement.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(vue.appRecord),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status==1){
//                                vue.$refs.appRecordUpload.submit();
                                vue.appRecord.id= result.data;
                                $.notify('新增APP成功',{status:'success',timeout:2000});
                                setTimeout(function () {
                                    history.go(-1);
                                },2000)
                            }
                        }
                    })
                }else{
                    ajax({
                        url:'/appManagement/updateAppManagement.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(vue.appRecord),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status==1){
                                $.notify('更新APP成功',{status:'success',timeout:2000});
                                setTimeout(function () {
                                    history.go(-1);
                                },2000);
                                <%--location='<%=appPath%>/appManagement/initListAppManagement.htm?ticketId=<%=ticketId%>';--%>
                            }
                        }
                    })
                }

            },
            imgUploadError: function (error, file, fileList) {
                console.log(error);
            },
            handleAvatarSuccess: function (file, fileList) {
                console.log('选择成功');
                Vue.set(this.appRecord, 'fileName', file.name);
            },
            appRecordImgUploadSuccess: function (response, file, fileList) {
                console.log(file);
                if (!file.response.success) {
                    $.notify('文件保存失败', {status: 'danger', timeout: 1000});
                    return;
                }
                var obj = file.response.message;
                var fileID = obj.fileID;
                vue.appRecord.fileId = fileID;
                ajax({
                    url:'/appManagement/updateAppManagement.htm?ticketId=<%=ticketId%>',
                    data:JSON.stringify(vue.appRecord),
                    contentType:'application/json',
                    success:function (result) {
                        if(result.status==1){
                            $.notify('新增APP成功',{status:'success',timeout:2000});
                            setTimeout(function () {
                                history.go(-1);
                            },2000);
                            <%--location='<%=appPath%>/appManagement/initListAppManagement.htm?ticketId=<%=ticketId%>';--%>
                        }
                    }
                })


            },
            init:function(){
                this.appPath=$('#appPath').val();
            }
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