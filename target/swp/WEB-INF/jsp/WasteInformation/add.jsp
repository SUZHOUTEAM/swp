<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="危废资讯"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#wasteInformation"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<style type="text/css">
    input[type="file"] {
        display: none;
    }
    .customer-upload{
        width: 100px;
        height: 100px;
        line-height: 100px;
        background: url(http://yifeiwang.com/img/source/upload.png) center center no-repeat;
        margin-top: 10px;
        position: relative;
        display: inline-block;
        border: 1px dashed #c0ccda;
    }
    .el-loading-mask{
        position: fixed;
    }
</style>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>
<script type="text/javascript" src="<%=appPath%>/main/common/elementui/vue-html5-editor.js"></script>
<section id="app" v-loading="loading"  :element-loading-text="loadingText"
         element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(255,255,255, 0.8)">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>运营管理</el-breadcrumb-item>
            <el-breadcrumb-item onclick="history.go(-1);">危废资讯</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.wasteInformationId?'编辑':'创建'}}资讯</el-breadcrumb-item>
        </el-breadcrumb>
        <div >
            <div class="panel panel-body">
                <el-form label-position="right" label-width="160px" style="margin-top: 16px;"
                         :model="wasteInformation" ref="wasteInformationValidateForm" :rules="wasteInformationRules">
                    <el-form-item label="标题：" prop="title">
                        <el-input v-model="wasteInformation.title" placeholder="请输入标题"></el-input>
                    </el-form-item>
                    <el-form-item label="类别：" prop="type">
                        <el-select v-model="wasteInformation.type" placeholder="选择类别">
                            <el-option :label="item1.value" :value="item1.code" v-for="(item1,index) in typeList"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="图片上传：" prop="fileId">
                        <div class="el-upload__tip" v-if="wasteInformationAction!='view'" style="margin-top: 0">只能上传jpg,png等图片文件，尺寸414*230像素</div>
                        <el-upload
                                ref="wasteInformationImgUpload"
                                :action="uploadAction"
                                <%--list-type="picture-card"--%>
                                :data="wasteInformationParam"
                                <%--:limit="1"--%>
                                :show-file-list="false"
                                <%--:file-list="bannerList"--%>
                                :on-change="handleAvatarImgSuccess"
                                :on-success="wasteInformationImgUploadSuccess"
                                :auto-upload="false"
                                :on-preview="handlePictureCardPreview"
                                :on-remove="handleRemove">
                            <img v-if="dialogImageUrl" :src="dialogImageUrl">
                            <i v-else class="customer-upload"></i>
                        </el-upload>
                       <el-dialog :visible.sync="imgDialogVisible">
                            <img width="100%" :src="dialogImageUrl" alt="">
                        </el-dialog>
                        <%--<el-input v-model="wasteInformation.fileId" placeholder="请输入图片"></el-input>--%>
                    </el-form-item>
                    <el-form-item label="视频类型：" prop="videoType">
                        <el-radio v-model="wasteInformation.videoType" label="0"  @change="changeVideoType">无视频</el-radio>
                        <el-radio v-model="wasteInformation.videoType" label="1"  @change="changeVideoType">直播回看</el-radio>
                        <el-radio v-model="wasteInformation.videoType" label="2"  @change="changeVideoType">短视频</el-radio>
                    </el-form-item>
                    <el-form-item label="视频地址：" prop="videoFileId" v-if="wasteInformation.videoType=='1'">
                        <el-input v-model="wasteInformation.videoFileId" placeholder="请输入视频地址"></el-input>
                    </el-form-item>
                    <el-form-item label="视频上传：" prop="fileName"  v-if="wasteInformation.videoType=='2'">
                        <div class="el-upload__tip" v-if="wasteInformationAction!='view'" style="margin-top: 0">只能上传mp4视频文件，视频编码为AVC(H264)</div>
                        <el-upload
                                style="display: inline-block;width: auto"
                                ref="wasteInformationUpload"
                                :action="uploadAction"
                                :data="wasteInformationParam"
                                :disabled="wasteInformationAction=='view'"
                                :show-file-list="false"
                                :on-change="handleAvatarSuccess"
                                accept=".mp4"
                                :on-success="wasteInformationUploadSuccess"
                                :on-error="imgUploadError"
                                :auto-upload="false">
                            <el-button size="small" type="primary" v-if="!wasteInformation.fileName">{{wasteInformation.videoFileId?'重新上传':'点击上传'}}</el-button>
                            <span v-else>{{wasteInformation.fileName}}</span>
                        </el-upload>
                        <a v-if="wasteInformation.videoFileId" :href="wasteInformation.videoFileId" target="_blank">点击下载</a>
                    </el-form-item>
                    <el-form-item label="title：" prop="htmlTitle">
                        <el-input v-model="wasteInformation.htmlTitle" placeholder="请输入页面title,(网站名称-主关键词或一句含有主关键词的描述),不超过30个字"></el-input>
                    </el-form-item>
                    <el-form-item label="keyword：" prop="keyword">
                        <el-input v-model="wasteInformation.keyword" placeholder="请输入关键词,多个用“,”号分割，重要关键词放前面,不超过30个字"></el-input>
                    </el-form-item>
                    <el-form-item label="description：" prop="desc">
                        <el-input v-model="wasteInformation.desc" placeholder="请输入描述,不超过150个字"></el-input>
                    </el-form-item>
                    <el-form-item label="概述：" prop="text">
                        <el-input v-model="wasteInformation.text" placeholder="请输入概述,不超过250个字" type="textarea"
                                  :rows="4"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="正文：" prop="context">
                        <vue-html5-editor :content="wasteInformation.context" @change="ctnUpdate" :height="300"></vue-html5-editor>
                    </el-form-item>
                    <el-form-item align="left">
                        <el-button type="primary" class="save" @click="savewasteInformation"  :disabled="wasteInformationAction=='view'">保存</el-button>
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
<script src="<%=appPath%>/main/common/elementui/vue-editor.js"></script>
<script>
    var vue = new Vue({
        el: '#app',
        appPath:'',
        data: {
            wasteInformationId: getParam('id') ? getParam('id') : '',
            wasteInformation: {
                id: '',
                title: '',
                context: '',
                text:'',
                fileId: '',
                videoFileId: '',
                type:'',
                videoType:'0',
                htmlTitle:'',
                keyword:'',
                desc:''
            },
            fileID: '',
            uploadAction: upload.IMG_URL + '/upload',
            wasteInformationAction: getParam('action'),
            wasteInformationRules: {
                title: [
                    {required: true, message: '请输入标题', trigger: 'blur'}
                ]
            },
            wasteInformationParam: {'appKey': APPKEY, 'prodID': PRODID, businessCode: upload.randomChar(19)},
            uploadPrev:IMG_PREV,
            dialogImageUrl:'',
            imgDialogVisible:false,
            bannerList:[],
            loading:false,
            loadingText:'提交中',
            typeList:[]
        },
        mounted: function () {
            this.getTypeList();
            if(this.wasteInformationId!=''){
                this.getAppInfo();
            }
        },
        methods: {
            getTypeList:function () {
                var _this=this;
                ajax({
                    url:'/codeType/listCodeValue',
                    data:{
                        typeCode:'WASTE_INFO_TYPE'
                    },
                    success:function (data) {
                        if(data.status==1){
                            _this.typeList=data.data;
//                            for(var i in data.data){
//                                if(data.data[i]['value']=='吨'){
//                                    Vue.set(vue.enterpriseWaste,'unitCode',data.data[i]['code']);
//                                }
//                            }
                        }
                    }
                });
            },
            changeType:function () {

            },
            changeVideoType:function () {
                Vue.set(this.wasteInformation,'videoFileId','');
                Vue.set(this.wasteInformation,'fileName','');
            },
            ctnUpdate:function (data) {
                Vue.set(this.wasteInformation,'context',data);
            },
            savewasteInformation: function () {
                this.$refs['wasteInformationValidateForm'].validate(function (valid) {
                    if (valid) {
                        vue.loading=true;
                        if(vue.wasteInformation.videoType=='1'&&!vue.wasteInformation.videoFileId){
                            vue.loading=false;
                            vue.$message.error('视频路径不能为空');
                            return;
                        }
                        if(vue.dialogImageUrl){//需要上传图片
                            vue.loadingText='图片上传中...';
                            vue.$refs.wasteInformationImgUpload.submit();
                        }
                        if(vue.wasteInformation.fileName){
                             vue.loadingText='视频上传中...';
                            vue.$refs.wasteInformationUpload.submit();
                        }
                        var timer=setInterval(function () {
                            if(vue.dialogImageUrl&&!vue.wasteInformation.fileId||vue.wasteInformation.fileName&&!vue.wasteInformation.videoFileId){
//                                return;
                            }else{
                                clearInterval(timer);
                                vue.loadingText='数据提交中...';
                                vue.saveWasteInformationAction();
                            }
                        },500)
                    }
                });
            },
            getAppInfo: function () {
                ajax({
                    url: '/wasteInformation/getWasteInformationById.htm',
                    data: {
                        id: this.wasteInformationId
                    },
                    success: function (result) {
                        if(result.status==1){
                            vue.wasteInformation=result.data;
                            var fileId=result.data['fileId'];
                            var videoFileId=result.data['videoFileId'];
                            if(videoFileId){
                                if(videoFileId.indexOf('mlsc-webupload')==-1){//直播回看
                                    Vue.set(vue.wasteInformation,'videoType','1');
                                }else{
                                    Vue.set(vue.wasteInformation,'videoType','2');
                                }
                            }else{
                                Vue.set(vue.wasteInformation,'videoType','0');
                            }
                            if(fileId){
                                var url=IMG_VIEW_URL+'&fileID='+fileId;
                                vue.dialogImageUrl=url;
                            }
                        }

                    }
                });
            },
            saveWasteInformationAction: function () {
                if(vue.wasteInformationId==''){
//                    var text=vue.wasteInformation.context.replace(/<.*?>/ig, "").replace(/&nbsp;/g, '');
//                    text=text.length>300?text.substring(0,300):text;
//                    vue.wasteInformation.text=text;
                    ajax({
                        url:'/wasteInformation/saveWasteInformation.htm',
                        data:vue.wasteInformation,
//                        contentType:'application/json',
                        success:function (result) {
                            vue.loading=false;
                            if(result.status==1&&result.data){
                                $.notify('创建资讯成功',{status:'success',timeout:2000});
                                setTimeout(function () {
                                    history.go(-1);
                                },2000)
                            }else{
                                $.notify('创建资讯失败,原因是'+result.infoList[0],{status:'danger',timeout:2000});
                            }
                        },
                        error:function () {
                            vue.loading=false;
                        }
                    })
                }else{
                    var wasteInformation=vue.wasteInformation;
//                    var text=wasteInformation.context.replace(/<.*?>/ig, "").replace(/&nbsp;/g, '');
//                    text=text.length>300?text.substring(0,300):text;
                    var param={
                        ticketId:'<%=ticketId%>',
                        id:wasteInformation.id,
                        context:wasteInformation.context,
                        title:wasteInformation.title,
                        fileId:wasteInformation.fileId,
                        text:wasteInformation.text,
                        videoFileId:wasteInformation.videoFileId,
                        type:wasteInformation.type,
                        htmlTitle:wasteInformation.htmlTitle,
                        keyword:wasteInformation.keyword,
                        desc:wasteInformation.desc,
                        htmlSrc:wasteInformation.htmlSrc
                    }
                    ajax({
                        url:'/wasteInformation/updateWasteInformation.htm',
                        data:param,
                        success:function (result) {
                            vue.loading=false;
                            if(result.status==1&&result.data){
                                $.notify('更新资讯成功',{status:'success',timeout:2000});
                                setTimeout(function () {
                                    history.go(-1);
                                },2000)
                            }else{
                                $.notify('更新资讯失败',{status:'danger',timeout:2000});
                            }
                        },
                        error:function () {
                            vue.loading=false;
                        }
                    })
                }

            },
            imgUploadError: function (error, file, fileList) {
                console.log(error);
            },
            handleAvatarSuccess: function (file, fileList) {
                console.log('选择成功');
                Vue.set(this.wasteInformation, 'fileName', file.name);
            },
            wasteInformationUploadSuccess: function (response, file, fileList) {
                console.log(file);
                if (!file.response.success) {
                    $.notify('视频上传失败', {status: 'danger', timeout: 1000});
                    return;
                }
                var obj = file.response.message;
                var fileID = obj.fileID;
                vue.wasteInformation.videoFileId = IMG_VIEW_URL+'&fileID='+fileID;
            },
            wasteInformationImgUploadSuccess: function (response, file, fileList) {
                console.log(file);
                if (!file.response.success) {
                    $.notify('图片上传失败', {status: 'danger', timeout: 1000});
                    return;
                }
                var obj = file.response.message;
                var fileID = obj.fileID;
                vue.wasteInformation.fileId = fileID;
            },
            handleAvatarImgSuccess:function (file, fileList) {
                this.dialogImageUrl=file.url;
            },
            handleRemove:function(file) {
                var fileID=file.fileID;
                if(fileID){
                    upload.delete(fileID);
                }
            },
            handlePictureCardPreview:function(file) {
                this.dialogImageUrl = file.url;
                this.imgDialogVisible = true;
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