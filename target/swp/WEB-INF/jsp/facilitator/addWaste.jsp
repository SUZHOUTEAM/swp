<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
	<jsp:param name="title" value="添加危废" />
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
	<jsp:param name="menuId" value="#myRelease" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterpriseWaste/style.css">

<section id="app">
	<template>
		<el-breadcrumb separator="/">
			<el-breadcrumb-item>我的易废圈</el-breadcrumb-item>
			<el-breadcrumb-item onclick="back();">选择待处置危废</el-breadcrumb-item>
			<el-breadcrumb-item>新增产废</el-breadcrumb-item>
		</el-breadcrumb>
		<div class="panel panel-body">
			<div class="panel panel-body">
				<el-form label-position="right" label-width="160px" style="max-width: 800px;margin-top: 16px;" :model="enterpriseWaste" ref="enterpriseWasteValidateForm" :rules="enterpriseWasteRules">
					<el-form-item label="八位码：" prop="wasteCode">
						<el-autocomplete
								popper-class="my-autocomplete"
								v-model="enterpriseWaste.wasteCode"
								:fetch-suggestions="querySearch"
								placeholder="请输入或选择匹配的八位码"
								@select="handleSelect">
							<template slot-scope="props">
								<div class="name">{{ props.item.addenterwastecode }}&nbsp;&nbsp;{{ props.item.addenterwastename }}&nbsp;&nbsp;<span class="addr">{{ props.item.addenterwastedesc }}</span></div>
							</template>
						</el-autocomplete>
						<span style="font-size: 12px;color: #606266;">(只知道二位码可用：900-000-xx，二位码也不知道可用：900-000-000)</span>
					</el-form-item>
					<el-form-item label="危废名称：" prop="wasteName">
						<el-autocomplete
								popper-class="my-autocomplete"
								v-model="enterpriseWaste.wasteName"
								:fetch-suggestions="querySearchName"
								:disabled="!enterpriseWaste.wasteId"
								v-if="action!='view'"
								placeholder="请输入危废名称"
								@select="handleSelectName">
							<template slot-scope="props">
								<div class="name">{{ props.item.name }}</div>
							</template>
						</el-autocomplete>
						<span v-else>{{enterpriseWaste.wasteName}}</span>
					</el-form-item>
					<el-form-item label="有害物质名称和含量：" prop="harmfulSubstance">
						<el-input v-model="enterpriseWaste.harmfulSubstance" type="textarea" :rows="3" v-if="action!='view'" placeholder="请输入有害物质名称和含量"></el-input>
						<span v-else>{{enterpriseWaste.harmfulSubstance||'--'}}</span>
					</el-form-item>
					<el-form-item label="单位：" prop="unitCode">
						<el-select v-model="enterpriseWaste.unitCode" placeholder="请选择单位" style="width: 100%" v-if="action!='view'">
							<el-option :label="item.value" :value="item.code" v-for="(item,index) in unitList"></el-option>
						</el-select>
						<span v-else>{{enterpriseWaste.unitValue}}</span>
					</el-form-item>
					<%--<el-form-item label="危废图片：">
						<div class="el-upload__tip" v-if="action!='view'" style="margin-top: 0">只能上传jpg/png/gif文件，最多3张照片</div>
						<el-upload
								:action="uploadAction"
								ref="enterpriseWasteUpload"
								:data="enterpriseWasteParam"
								list-type="picture-card"
								:file-list="enterpriseWasteImgList"
								:auto-upload="false"
								:on-preview="handlePictureCardPreview"
								:limit="3"
								:disabled="action=='view'"
								accept=".jpg,.png,.gif"
								:on-success="uploadSuccess"
								:on-remove="handleRemove">
							<i class="upload"></i>
						</el-upload>
						<el-dialog :visible.sync="imgDialogVisible" width="90%" :modal-append-to-body="false" title="危废图片">
							<img :src="dialogImageUrl" alt="">
						</el-dialog>
					</el-form-item>--%>
					<el-form-item align="left" v-if="enterpriseWasteAction!='view'">
						<el-button type="primary" class="save" v-if="action!='view'" @click="saveEnterpriseWaste">保存</el-button>
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
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>
<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: function () {
            var validateWasteCode=function (rule, value, callback) {
                if (value === '') {
                    callback(new Error('请输入八位码'));
                } else {
                    ajax({
                        url:'/entWaste/isWasteCodeExistent.htm',
                        async:false,
                        data:{
                            wasteCode:value
                        },
                        success:function (result) {
                            if(result.status == 0) {
                                callback(new Error('该八位码不存在'));
                            } else {
                                Vue.set(vue.enterpriseWaste,'wasteId',result.data.wasteId);
                                callback();
                            }
                        }
                    })
                }
            };
            var validateWasteName=function (rule, value, callback) {
                if (value === '') {
                    callback(new Error('请输入危废名称'));
                } else {
                    if(vue.oldWasteName&&value==vue.oldWasteName){
                        callback();
                        return;
					}
                    ajax({
                        url:'/entWaste/checkWasteNameDuplicate.htm',
                        async:false,
                        data:{
                            wasteId:vue.enterpriseWaste.wasteId,
                            wasteName:value,
							entId:vue.customerId
                        },
                        success:function (result) {
                            if(result.data) {
                                callback(new Error('该危废名称已存在'));
                            } else {
                                callback();
                            }
                        }
                    })
                }
            };
            return{
                tableData: [],
                publishLoading:false,
                dialogVisible:false,
                enterpriseWasteAction:'add',
                enterpriseWaste: {
                    wasteCode: '',
                    wasteName: '',
                    wasteId:'',
                    wasteNameId:'',
                    harmfulSubstance: '',
                    unitCode:'',
                    entWasteId:getParam('entWasteId')?getParam('entWasteId'):'',
                },
                enterpriseWasteRules: {
                    wasteCode: [
                        { required: true, message: '请输入或选择八位码', trigger: 'blur' },
                        { validator: validateWasteCode, trigger: 'change' },
                        { validator: validateWasteCode, trigger: 'blur' },
                    ],
                    wasteName: [
                        { required: true, message: '请输入危废名称', trigger: 'blur' },
                        { validator: validateWasteName, trigger: 'change' },
                        { validator: validateWasteName, trigger: 'blur' }
                    ],
                    unit: [
                        { required: true, message: '请选择单位', trigger: 'blur' }
                    ]
                },
                unitList:[],
				action:getParam('action'),
				oldWasteName:'',
				customerId:getParam('customerId')?getParam('customerId'):'',
                releaseIndex:getParam('releaseIndex'),
                pageIndex:getParam('pageIndex')
            }
        },
        created:function() {
            this.getUnitList();
        },
        methods:{
            getUnitList:function () {
                var _this=this;
                ajax({
                    url:'/codeType/listCodeValue',
                    data:{
                        typeCode:'UNIT_TYPE'
                    },
                    success:function (data) {
                        if(data.status==1){
                            _this.unitList=data.data;
                            for(var i in data.data){
                                if(data.data[i]['value']=='吨'){
                                    Vue.set(vue.enterpriseWaste,'unitCode',data.data[i]['code']);
                                }
                            }
                        }
                    }
                });
            },
            querySearch:function(queryString, cb) {
                ajax({
                    url:'/entWaste/getWasteIdDropDownList.htm',
                    data:{
                        keyword:queryString
                    },
                    success:function (result) {
                        for(var i in result.value){
                            result.value[i]['addenterwastecode']=result.value[i]['addenterwastecode'].replace(/<font color='red'>/g,'').replace(/<\/font>/g,'');
                            result.value[i]['addenterwastename']=result.value[i]['addenterwastename'].replace(/<font color='red'>/g,'').replace(/<\/font>/g,'');
                            result.value[i]['addenterwastedesc']=result.value[i]['addenterwastedesc'].replace(/<font color='red'>/g,'').replace(/<\/font>/g,'');
                        }
                        cb(result.value);
                    }
                });
            },
            querySearchName:function (queryString, cb) {
                ajax({
                    url:'/entWaste/listWasteName.htm',
                    data:{
                        keyword:queryString,
                        wasteid:this.enterpriseWaste.wasteId,
						entId:vue.customerId
                    },
                    success:function (result) {
                        if(result.status==1&&result.data){
                            cb(result.data);
                        }
                    }
                });
            },
            handleSelect:function(item) {
                this.enterpriseWaste.wasteCode=item.addenterwastecode;
                this.enterpriseWaste.wasteId=item.id;
            },
            handleSelectName:function(item) {
                this.enterpriseWaste.wasteName=item.name;
                this.enterpriseWaste.wasteNameId=item.id;
            },
            saveEnterpriseWaste:function () {
                this.$refs['enterpriseWasteValidateForm'].validate(function(valid){
                    if (valid) {
                        vue.saveEnterpriseWasteAction();
                    }
                });
            },
            saveEnterpriseWasteAction:function () {
                var enterpriseWaste={};//this.enterpriseWaste;
                enterpriseWaste.entId=this.customerId;
                enterpriseWaste.wasteId = this.enterpriseWaste.wasteId;
                enterpriseWaste.wasteCode = this.enterpriseWaste.wasteCode;
                enterpriseWaste.wasteNameId = this.enterpriseWaste.wasteNameId;
                enterpriseWaste.wasteName = this.enterpriseWaste.wasteName;
                enterpriseWaste.unitCode = this.enterpriseWaste.unitCode;
                enterpriseWaste.harmfulSubstance = this.enterpriseWaste.harmfulSubstance;
                if(!this.enterpriseWaste.entWasteId){//新增
                    ajax({
                        url:'/entWaste/save.htm',
                        data:enterpriseWaste,
                        success:function (result) {
                            if(result.status==1&&result.data){
                                $.notify('新增产废成功',{status:'success'});
                                setTimeout(function () {
                                    back();
                                },1000)
                            }
                        }
                    });
                }else{
                    enterpriseWaste.entWasteId=this.enterpriseWaste.entWasteId;
                    enterpriseWaste.inquiried=this.enterpriseWaste.inquiried;
                    ajax({
						url:'/entWaste/updateEntWaste?ticketId=<%=ticketId%>',
						data:JSON.stringify(enterpriseWaste),
						contentType:'application/json',
						success:function (result) {
							if(result.status==1&&result.data){
                                $.notify('修改产废成功',{status:'success'});
                                var uploadFiles=vue.$refs.enterpriseWasteUpload.uploadFiles;
                                var flag=false;
                                for(var i in uploadFiles){
                                    if(!uploadFiles[i]['fileID']){
										flag=true;
										break;
									}
								}
								if(flag){
                                    Vue.set(vue.enterpriseWasteParam,'businessCode',enterpriseWaste.entWasteId);
                                    vue.$refs.enterpriseWasteUpload.submit();
								}else{
                                    setTimeout(function () {
                                        back();
                                    },1000)
								}
							}
                        }
					});
                }
            },
        }

    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    function back() {
        if(vue.releaseIndex||vue.releaseIndex==0){//返回到委托页面
            window.location='<%=appPath%>/facilitator/publishList.htm?ticketId=<%=ticketId%>&pageIndex='+vue.pageIndex+'&releaseIndex='+vue.releaseIndex;
		}else{
            window.location='<%=appPath%>/facilitator/publish.htm?ticketId=<%=ticketId%>&customerId='+vue.customerId
		}
    }
</script>