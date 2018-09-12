<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
	<jsp:param name="title" value="企业信息维护" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
	<jsp:param name="menuId" value="#myCZEnterprise" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/detail.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/timeline.css">
<link rel="stylesheet" href="<%=appPath%>/main/common/upload/upload.css" />
<script type="text/javascript" src="<%=appPath%>/thirdparty/city-picker/js/city-picker.data.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>
<script type="text/javascript" src="<%=appPath%>/main/common/elementui/vue-html5-editor.js"></script>

<section>
	<div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的企业</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
		<span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">完善信息</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
	</div>
	<div>
		<div id="app">
			<template>
				<el-tabs v-model="activeName" type="card" @tab-click="handleClick">
					<el-tab-pane name="first" class="first">
						<span slot="label">企业概述
							<i v-if="summary" class="el-icon-circle-check"></i>
							<i v-else class="fa fa-minus-circle"></i>
						</span>
						<div class="tabTitle">
							完善企业概述
						</div>
						<div class="panel panel-default">
							<div class="panel-left">
								1.企业概述：
							</div>
							<div class="panel-right">
								<vue-html5-editor :content="summary||''" @change="ctnUpdate" :height="300"></vue-html5-editor>
								<el-button type="primary" class="save" @click="updateSummary">保存</el-button>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-left">
								2.企业图片：
							</div>
							<div class="panel-right">
								<div  class="el-upload__tip">（企业相关的图片展示，如企业形象照片等，数量为1~4张，每张图片大小为968*330像素,格式为JPG或PNG。）</div>
								<div class="imgList">
									<el-upload
											:action="uploadAction"
											ref="bannerUpload"
											:data="bannerParam"
											list-type="picture-card"
											:file-list="bannerList"
											:auto-upload="false"
											:on-preview="handlePictureCardPreview"
											:on-remove="handleRemove">
										<i class="banner-upload"></i>
									</el-upload>
									<el-dialog :visible.sync="imgDialogVisible" width="90%" :modal-append-to-body="false" title="企业图片">
										<img :src="dialogImageUrl" alt="">
									</el-dialog>
								</div>
								<el-button type="primary" class="save" @click="uploadBanner">保存</el-button>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-left">
								3.我的客户：
							</div>
							<div class="panel-right">
								<div>
									<el-button class="btn_addCustomer" @click="showCustomerDialog()"><i class="el-icon-plus"></i>添加客户信息</el-button>
								</div>
								<el-table
										class="partnerList"
										:data="partnerList"
										border
										style="width: 100%">
									<el-table-column
											type="index"
											width="100"
											align="center"
											label="排列序号">
									</el-table-column>
									<el-table-column
											prop="name"
											label="企业名称">
									</el-table-column>
									<el-table-column
											align="center"
											label="企业Logo图片">
										<template scope="scope">
											<img :src="scope.row.logo"/>
										</template>
									</el-table-column>
									<el-table-column label="操作" width="150px" align="center">
										<template scope="scope">
											<el-button type="text" title="编辑" @click.native.prevent="editCustomerRow(scope.$index,partnerList,'view')">查看</el-button>
											<el-button type="text" title="编辑" @click.native.prevent="editCustomerRow(scope.$index,partnerList,'edit')">编辑</el-button>
											<el-button type="text" title="编辑" @click.native.prevent="delCustomer(scope.$index,partnerList)">删除</el-button>
										</template>
									</el-table-column>
								</el-table>
								<el-dialog :visible.sync="customerDialogVisible" size="tiny" :modal-append-to-body="false" title="编辑客户信息" class="customerDialog">
									<span slot="title" class="dialog_title">{{actions[customerAction]}}客户信息</span>
									<el-form label-position="right" label-width="160px" :model="customer" ref="customerValidateForm" :rules="customerRules">
										<el-form-item label="企业名称：" prop="name">
											<el-input v-model="customer.name" placeholder="请输入企业名称" :disabled="customerAction=='view'"></el-input>
										</el-form-item>
										<el-form-item label="上传logo："  prop="logo">
											<div  class="el-upload__tip" v-if="customerAction!='view'">（图片不得超过2M，每张图片大小为200*200像素,格式为JPG或PNG。）</div>
											<el-upload style="float: left"
													   ref="customerUpload"
													   :action="uploadAction"
													   :data="customerParam"
													   :disabled="customerAction=='view'"
													   :show-file-list="false"
													   :on-change="handleAvatarSuccess"
													   :on-success="customerImgUploadSuccess"
													   :on-error="imgUploadError"
													   :auto-upload="false">
												<img v-if="customer.logo" :src="customer.logo">
												<i v-else class="customer-upload"></i>
											</el-upload>
										</el-form-item>
										<el-form-item align="left" v-if="customerAction!='view'">
											<el-button type="primary" class="save" @click="saveCustomer">保存</el-button>
											<el-button style="width: 80px;height: 30px;line-height: 30px;padding: 0;" @click="hideCustomerDialog">取消</el-button>
										</el-form-item>
									</el-form>
								</el-dialog>
							</div>
						</div>
					</el-tab-pane>
					<el-tab-pane name="second" class="second">
						<span slot="label">经营许可证
							<i v-if="JSON.stringify(operationLicence)=='{}'||!operationLicence" class="fa fa-minus-circle"></i>
							<i v-else  class="el-icon-circle-check"></i>
						</span>
						<el-row class="license" v-if="operationLicence">
							<el-col :span="10">
								<div class="licenseTableDiv">
									<table style="width: 100%" >
										<tr>
											<td colspan="2" style="font-size: 14px;color: #222;">危险废物经营许可证</td>
										</tr>
										<tr>
											<td>许可证编号：</td>
											<td class="text">{{operationLicence.licence_no||'--'}}</td>
										</tr>
										<tr>
											<td>有效期：</td>
											<td class="text">{{operationLicence.start_date?operationLicence.start_date.substring(0,10):''}}~{{operationLicence.end_date?operationLicence.end_date.substring(0,10):''}}</td>
										</tr>
										<tr>
											<td>年许可量：</td>
											<td class="text">{{approvedQuantity}}吨</td>
										</tr>
										<tr>
											<td colspan="2" style="padding: 20px 10px">
												<img v-if="licenceImg" :src="licenceImg" @click="showLargeImg(licenceImg)"/>
												<span v-else>暂无图片信息</span>
											</td>
										</tr>
									</table>
								</div>

							</el-col>
							<el-col :span="14" style="padding: 20px" v-show="licenceImg">
								<img v-if="licenceImg" :src="licenceImg" @click="showLargeImg(licenceImg)"/>
								<span class="tip">危险废物经营许可证副本照片（点击查看大图）</span>
							</el-col>
							<el-dialog
									title="经营许可证"
									:visible.sync="dialogVisible"
									:modal-append-to-body="false"
									width="90%">
								<img :src="licenceImg"/>
							</el-dialog>
						</el-row>
						<div v-else  style="margin-top: 28px" align="center">
							<span>暂无有效许可证</span>
						</div>
					</el-tab-pane>
					<el-tab-pane name="third" id="third">
						<span slot="label">资质与荣誉
							<i v-if="ryList&&ryList.length>0" class="el-icon-circle-check"></i>
							<i v-else  class="fa fa-minus-circle"></i>
						</span>
						<div class="tabTitle">
							企业资质与荣誉
						</div>
						<div class="panel panel-default">
							<div class="panel-left">
								添加信息：
							</div>
							<div class="panel-right">
								<div>
									<el-button class="btn_addCustomer" @click="showGloryDialog"><i class="el-icon-plus"></i>添加资质与荣誉</el-button>
								</div>
								<el-table
										class="partnerList"
										:data="ryList"
										border
										style="width: 100%">
									<el-table-column
											type="index"
											width="100"
											align="center"
											label="排列序号">
									</el-table-column>
									<el-table-column
											prop="gloryType"
											label="荣誉资质名称">
									</el-table-column>
									<el-table-column
											prop="date"
											label="获得时间">
									</el-table-column>
									<el-table-column
											align="center"
											label="企业Logo图片">
										<template scope="scope">
											<img :src="scope.row.logo"/>
										</template>
									</el-table-column>
									<el-table-column label="操作" width="150px" align="center">
										<template scope="scope">
											<el-button type="text" title="查看" @click.native.prevent="editGloryRow(scope.$index,ryList,'view')">查看</el-button>
											<el-button type="text" title="编辑" @click.native.prevent="editGloryRow(scope.$index,ryList,'edit')">编辑</el-button>
											<el-button type="text" title="删除" @click.native.prevent="delGlory(scope.$index,ryList)">删除</el-button>
										</template>
									</el-table-column>
								</el-table>
								<el-dialog :visible.sync="gloryDialogVisible" size="tiny" :modal-append-to-body="false" class="customerDialog">
									<span slot="title" class="dialog_title">{{actions[gloryAction]}}资质或荣誉</span>
									<el-form label-position="right" label-width="160px" :model="glory" ref="gloryValidateForm" :rules="gloryRules">
										<el-form-item label="资质类型：" prop="gloryType">
											<el-select v-model="glory.gloryType" placeholder="请选择资质类型" style="width: 100%" :disabled="gloryAction=='view'">
												<el-option :label="item.value" :value="item.code" v-for="(item,index) in gloryTypeList"></el-option>
											</el-select>
										</el-form-item>
										<el-form-item label="获得时间：" prop="date">
											<el-date-picker
													v-model="glory.date"
													type="date"
													placeholder="选择日期"
													:disabled="gloryAction=='view'">
											</el-date-picker>
										</el-form-item>
										<el-form-item label="上传图片：" prop="logo">
											<div  class="el-upload__tip" v-if="gloryAction!='view'">（上传1张相关文件的照片，大小不超过512kb，图片格式为jpg或PNG。）</div>
											<el-upload style="float: left"
													   ref="gloryUpload"
													   :action="uploadAction"
													   :data="gloryParam"
													   :show-file-list="false"
													   :on-change="handleGlorySuccess"
													   :disabled="gloryAction=='view'"
													   :on-success="gloryImgUploadSuccess"
													   :on-error="imgUploadError"
													   :auto-upload="false">
												<img v-if="glory.logo" :src="glory.logo">
												<i v-else class="customer-upload"></i>
											</el-upload>
										</el-form-item>
										<el-form-item align="left" v-if="gloryAction!='view'">
											<el-button type="primary" class="save" @click="saveGlory">保存</el-button>
											<el-button style="width: 80px;height: 30px;line-height: 30px;padding: 0;" @click="hideGloryDialog">取消</el-button>
										</el-form-item>
									</el-form>
								</el-dialog>
							</div>
						</div>
					</el-tab-pane>
					<el-tab-pane name="five">
						<span slot="label">销售及合同
							<i v-if="salesNote" class="el-icon-circle-check"></i>
							<i v-else  class="fa fa-minus-circle"></i>
						</span>
						<div class="tabTitle">
							销售及合同
						</div>
						<div class="panel panel-default">
							<div class="panel_row">
								<div class="panel-left">
									说明：
								</div>
								<div class="panel-right">
									<%--<el-input--%>
											<%--type="textarea"--%>
											<%--:rows="8"--%>
											<%--placeholder="请输入销售说明"--%>
											<%--v-model="salesNote">--%>
									<%--</el-input>--%>
									<vue-html5-editor :content="salesNote||''" @change="salesUpdate" :height="300"></vue-html5-editor>
										<el-button type="primary" class="save" @click="saveNote">保存</el-button>
								</div>
							</div>
							<%--<div class="panel_row">--%>
								<%--<div class="panel-left">--%>
									<%--2.付款规则：--%>
								<%--</div>--%>
								<%--<div class="panel-right">--%>
									<%--&lt;%&ndash;<el-input&ndash;%&gt;--%>
											<%--&lt;%&ndash;type="textarea"&ndash;%&gt;--%>
											<%--&lt;%&ndash;:rows="5"&ndash;%&gt;--%>
											<%--&lt;%&ndash;placeholder="请输入付款规则"&ndash;%&gt;--%>
											<%--&lt;%&ndash;v-model="paymentRule">&ndash;%&gt;--%>
									<%--&lt;%&ndash;</el-input>&ndash;%&gt;--%>
									<%--<vue-html5-editor :content="paymentRule||''" @change="paymentUpdate" :height="300"></vue-html5-editor>--%>
								<%--</div>--%>
							<%--</div>--%>
							<%--<div class="panel_row">--%>
								<%--<div class="panel-left">--%>
									<%--3.其它说明：--%>
								<%--</div>--%>
								<%--<div class="panel-right">--%>
									<%--&lt;%&ndash;<el-input&ndash;%&gt;--%>
											<%--&lt;%&ndash;type="textarea"&ndash;%&gt;--%>
											<%--&lt;%&ndash;:rows="5"&ndash;%&gt;--%>
											<%--&lt;%&ndash;placeholder="请输入其它说明"&ndash;%&gt;--%>
											<%--&lt;%&ndash;v-model="otherNote">&ndash;%&gt;--%>
									<%--&lt;%&ndash;</el-input>&ndash;%&gt;--%>
									<%--<vue-html5-editor :content="otherNote||''" @change="otherUpdate" :height="300"></vue-html5-editor>--%>

								<%--</div>--%>
							<%--</div>--%>
						</div>
						<%--<div class="panel panel-default">--%>
							<%--<div class="panel-left">--%>
								<%--4.上传附件：--%>
							<%--</div>--%>
							<%--<div class="panel-right">--%>
								<%--<div>--%>
									<%--<el-button class="btn_addCustomer" @click="showAttachDialog"><i class="el-icon-plus"></i>添加附件信息</el-button>--%>
								<%--</div>--%>
								<%--<el-table--%>
										<%--class="partnerList"--%>
										<%--:data="attachList"--%>
										<%--border--%>
										<%--style="width: 100%">--%>
									<%--<el-table-column--%>
											<%--type="index"--%>
											<%--width="100"--%>
											<%--align="center"--%>
											<%--label="排列序号">--%>
									<%--</el-table-column>--%>
									<%--<el-table-column--%>
											<%--prop="fileName"--%>
											<%--label="附件名称">--%>
									<%--</el-table-column>--%>
									<%--<el-table-column--%>
											<%--align="fileName"--%>
											<%--label="附件">--%>
										<%--<template scope="scope">--%>
											<%--<a href="javascript:" @click="downloadAttach(scope.$index,attachList)" title="下载">{{attachList[scope.$index]['fileName']}}</a>--%>
										<%--</template>--%>
									<%--</el-table-column>--%>
									<%--<el-table-column label="操作" width="150px" align="center">--%>
										<%--<template scope="scope">--%>
											<%--<el-button type="text" title="查看" @click.native.prevent="downloadAttach(scope.$index,attachList)">下载</el-button>--%>
											<%--<el-button type="text" title="编辑" @click.native.prevent="delAttach(scope.$index,attachList)">删除</el-button>--%>
										<%--</template>--%>
									<%--</el-table-column>--%>
								<%--</el-table>--%>
								<%--<el-dialog :visible.sync="attachDialogVisible" size="tiny" :modal-append-to-body="false" class="customerDialog">--%>
									<%--<span slot="title" class="dialog_title">{{actions[attachAction]}}附件信息</span>--%>
									<%--<el-form label-position="right" label-width="160px" :model="attach" ref="attachValidateForm" :rules="attachRules">--%>
										<%--<el-form-item label="附件名称：" prop="name">--%>
											<%--<el-input v-model="attach.name" placeholder="请输入附件名称" :disabled="attachAction=='view'"></el-input>--%>
										<%--</el-form-item>--%>
										<%--<el-form-item label="上传附件："  prop="fileName">--%>
											<%--<div  class="el-upload__tip" v-if="attachAction!='view'">（附件大小不超过5Mb,文件格式为word,pdf。）</div>--%>
											<%--<el-upload style="float: left"--%>
													   <%--ref="attachUpload"--%>
													   <%--:action="uploadAction"--%>
													   <%--:data="attachParam"--%>
													   <%--:show-file-list="false"--%>
													   <%--:on-change="handleAttachSuccess"--%>
													   <%--:disabled="attachAction=='view'"--%>
													   <%--:on-success="attachUploadSuccess"--%>
													   <%--:on-error="imgUploadError"--%>
													   <%--:auto-upload="false">--%>
												<%--<div v-if="attach.fileName" class="fileDiv">{{attach.fileName}}<font>({{attach.size}})</font></div>--%>
												<%--<i v-else class="customer-upload"></i>--%>
											<%--</el-upload>--%>
										<%--</el-form-item>--%>
										<%--<el-form-item align="left" v-if="attachAction!='view'">--%>
											<%--<el-button type="primary" class="save" @click="saveAttach">保存</el-button>--%>
											<%--<el-button style="width: 80px;height: 30px;line-height: 30px;padding: 0;" @click="hideAttachDialog">取消</el-button>--%>
										<%--</el-form-item>--%>
									<%--</el-form>--%>
								<%--</el-dialog>--%>
							<%--</div>--%>
						<%--</div>--%>
						<div class="panel panel-default">
							<div class="panel-left">
								上传合同：
							</div>
							<div class="panel-right">
								<div>
									<el-button class="btn_addCustomer"  @click="showContractDialog"><i class="el-icon-plus"></i>添加合同信息</el-button>
								</div>
								<el-table
										class="partnerList"
										:data="contractList"
										border
										style="width: 100%">
									<el-table-column
											type="index"
											width="100"
											align="center"
											label="排列序号">
									</el-table-column>
									<el-table-column
											prop="name"
											label="合同名称">
									</el-table-column>
									<el-table-column
											align="name"
											label="合同">
										<template scope="scope">
											<a href="javascript:" @click="downloadAttach(scope.$index,contractList)" title="下载">{{contractList[scope.$index]['name']}}</a>
										</template>
									</el-table-column>
									<el-table-column label="操作" width="130px" align="center">
										<template scope="scope">
											<el-button type="text" title="查看" @click.native.prevent="downloadAttach(scope.$index,contractList)">下载</el-button>
											<el-button type="text" title="编辑" @click.native.prevent="delContract(scope.$index,contractList)">删除</el-button>
										</template>
									</el-table-column>
								</el-table>
								<el-dialog :visible.sync="contractDialogVisible" size="tiny" :modal-append-to-body="false" class="customerDialog">
									<span slot="title" class="dialog_title">{{actions[contractAction]}}合同信息</span>
									<el-form label-position="right" label-width="160px" :model="contract" ref="contractValidateForm" :rules="contractRules">
										<el-form-item label="合同名称：" prop="name">
											<el-input v-model="contract.name" placeholder="请输入合同名称" :disabled="contractAction=='view'"></el-input>
										</el-form-item>
										<el-form-item label="上传合同：" prop="fileName">
											<div  class="el-upload__tip" v-if="contractAction!='view'">（合同大小不超过5Mb,文件格式为word,pdf。）</div>
											<el-upload style="float: left"
													   ref="contractUpload"
													   :action="uploadAction"
													   :data="contractParam"
													   :show-file-list="false"
													   :on-change="handleContractSuccess"
													   :disabled="contractAction=='view'"
													   :on-success="contractUploadSuccess"
													   :on-error="imgUploadError"
													   :auto-upload="false">
												<div v-if="contract.fileName" class="fileDiv">{{contract.fileName}}<font>({{contract.size}})</font></div>
												<i v-else class="customer-upload"></i>
											</el-upload>
										</el-form-item>
										<el-form-item align="left" v-if="contractAction!='view'">
											<el-button type="primary" class="save" @click="saveContract">保存</el-button>
											<el-button style="width: 80px;height: 30px;line-height: 30px;padding: 0;" @click="hideContractDialog">取消</el-button>
										</el-form-item>
									</el-form>
								</el-dialog>
							</div>
						</div>
					</el-tab-pane>
					<el-tab-pane name="six" label="预览">
						<span slot="label">预览<i class="fa fa-eye"></i></span>
					</el-tab-pane>
				</el-tabs>
			</template>
		</div>

		<!-- 先引入 Vue -->
		<script src="<%=appPath%>/main/common/elementui/vue.js"></script>
		<%--<!-- 引入组件库 -->--%>
		<script src="<%=appPath%>/main/common/elementui/index.js"></script>
		<script src="<%=appPath %>/main/pc/js/data.js"></script>
		<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
		<script src="<%=appPath%>/main/common/elementui/vue-editor.js"></script>
		<script>
            var vue = new Vue({
                el: '#app',
                data:{
                    entId:'${enterprise.entId}',
                    activeName: 'first',
                    cactiveName:'cfirst',
					imgList:imgList,
                    videoList:videoList,
                    pfList:pfList,
                    partnerList:[],
                    attachList:[],
                    contractList:[],
                    summary:'',
                    ryList:[],
                    dialogVisible:false,
                    imgDialogVisible:false,
                    dialogImageUrl:'',
                    bannerParam:{'appKey':APPKEY,'prodID':PRODID,businessCode:'${enterprise.entId}_banner'},
                    customerParam:{'appKey':APPKEY,'prodID':PRODID,businessCode:upload.randomChar(19)},
                    gloryParam:{'appKey':APPKEY,'prodID':PRODID,businessCode:upload.randomChar(19)},
                    attachParam:{'appKey':APPKEY,'prodID':PRODID,businessCode:'${enterprise.entId}_attach'},
                    contractParam:{'appKey':APPKEY,'prodID':PRODID,businessCode:'${enterprise.entId}_contract'},
                    bannerList:[],
					uploadAction:upload.IMG_URL+'/upload',
                    customerDialogVisible:false,
                    customer: {
                        name: '',
						logo:'',
						id:'',
						index:''
                    },
					glory:{
                        gloryType:'',
						date:'',
						logo:''
                    },
                    attach:{
                        name:'',
                        fileName:'',
                        fileId:'',
                        fileExtension:''
                    },
                    contract:{
                        name:'',
                        fileName:'',
                        fileId:'',
                        fileExtension:''
                    },
					customerAction:'add',
					gloryAction:'add',
                    attachAction:'add',
                    contractAction:'add',
                    actions:ACTIONS,
                    gloryDialogVisible:false,
                    gloryTypeList:[],
                    salesNote:'',
                    paymentRule:'',
                    otherNote:'',
                    attachDialogVisible:false,
                    contractDialogVisible:false,
                    operationLicence:{},
                    approvedQuantity:0,
                    licenceImg:'',
                    customerRules: {
                        name: [
                            { required: true, message: '请输入企业名称', trigger: 'blur' },
                            { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
                        ],
						logo:[
                            { required: true, message: '请上传logo', trigger: 'blur' }
						]
                    },
                    attachRules: {
                        name: [
                            { required: true, message: '请输入附件名称', trigger: 'blur' },
                            { min: 1, max: 20, message: '长度在 1 到20 个字符', trigger: 'blur' }
                        ],
						fileName:[
                            { required: true, message: '请上传附件', trigger: 'blur' }
						]
                    },
                    contractRules: {
                        name: [
                            { required: true, message: '请输入合同名称', trigger: 'blur' },
                            { min: 1, max: 20, message: '长度在 1 到20 个字符', trigger: 'blur' }
                        ],
						fileName:[
                            { required: true, message: '请上传合同', trigger: 'blur' }
						]
                    },
                    gloryRules:{
                        gloryType:[{ required: true, message: '请选择荣誉类型', trigger: 'blur' }],
						date:[ { type: 'date', required: true, message: '请选择时间', trigger: 'blur' }],
                        logo:[{ required: true, message: '请上传荣誉相关照片'}]
                    }
                },
                created:function () {
                    this.getList();
					this.getBannerList();
					this.getGloryTypeList();
					this.getAttachList();
					this.getContractList();
                },
                mounted:function () {
                    var heights=document.body.clientHeight;
                    $('#app >.el-tabs >.el-tabs__content').height(heights-240+'px');
                    window.onresize=function () {
                        var heights=document.body.clientHeight;
                        $('#app >.el-tabs >.el-tabs__content').height(heights-240+'px');
                    }
                },
                methods:{
                    ctnUpdate:function (data) {
                        this.summary = data;
                    },
                    salesUpdate:function (data) {
                        this.salesNote = data;
                    },
                    paymentUpdate:function (data) {
                        this.paymentRule = data;
                    },
                    otherUpdate:function(data){
                        this.otherNote = data;
                    },
                    getList:function () {
                        var _this=this;
						ajax({
							url:'/sysEnterpriseBase/getEnterpriseSummaryInfo.htm',
							data:{
                                entId:this.entId
							},
							success:function (data) {
								console.log(data);
								if(data.status==1){
								    var enterprise=data.data;
                                    _this.summary=enterprise.summary;
                                   //客户信息
                                    for(var i in enterprise.customerList){
                                        enterprise.customerList[i].name=enterprise.customerList[i]['customerName'];
                                        enterprise.customerList[i].logo=IMG_VIEW_URL+'&fileID='+enterprise.customerList[i]['fileId'];
									}
                                    _this.partnerList=enterprise.customerList;
									//许可证
                                    _this.operationLicence=enterprise.operationLicence;
                                    _this.approvedQuantity=fmoney(enterprise.approvedQuantity,4);
                                    if(enterprise.licenceFileId){
                                        _this.licenceImg=IMG_VIEW_URL+'&fileID='+enterprise.licenceFileId;
									}
									//资质和荣誉
                                    for(var i in enterprise.entGloryList){
                                        enterprise.entGloryList[i].logo=IMG_VIEW_URL+'&fileID='+enterprise.entGloryList[i]['fileId'];
                                        enterprise.entGloryList[i].date=enterprise.entGloryList[i]['getTime']?enterprise.entGloryList[i]['getTime'].substring(0,10):'';
                                    }
									_this.ryList=enterprise.entGloryList;
									//销售说明，付款规则，其它说明
                                    _this.salesNote=enterprise.salesNote,
									_this.paymentRule=enterprise.paymentRule,
									_this.otherNote=enterprise.otherNote
								}
                            }
						});
                    },
                    handleClick:function(tab, event) {
                        console.log(tab, event);
                        if(tab.index=='4'){
							window.location='<%=appPath%>/enterprise/czDetail.htm?ticketId=<%=ticketId%>&enterpriseId='+this.entId+'&&breadcrumbName=完善信息';
                        }
                    },
                    showLargeImg:function (url) {
                        this.licenceImg=url;
						this.dialogVisible=true;
                    },
                    handleRemove:function(file) {
                        var fileID=file.fileID||file.response.message.fileID;
                        if(fileID){
                            upload.delete(fileID);
                        }
                    },
                    handlePictureCardPreview:function(file) {
                        this.dialogImageUrl = file.url;
                        this.imgDialogVisible = true;
                    },
                    uploadBanner:function () {
						this.$refs.bannerUpload.submit();
                    },
					getBannerList:function () {
						var fileList=upload.getImgListByBusinessCode(this.entId+'_banner');
						console.log(fileList);
						for(var i in fileList){
						    var obj=fileList[i];
						    obj.name='0'+(i*1+1)+'.jpg';
						    obj.url=IMG_VIEW_URL+'&fileID='+obj['fileID'];
						    this.bannerList.push(obj);
						}
                    },
                    getGloryTypeList:function () {
						ajax({
							url:'/codeType/listCodeValue',
							data:{
                                typeCode:'GLORY_TYPE'
							},
							success:function (data) {
								console.log(data);
								if(data.status==1){
                                    vue.gloryTypeList=data.data;
								}
                            }
						});
                    },
					updateSummary:function () {
						console.log(this.summary+' '+this.entId);
						if(!this.summary){
                            $.notify('概述不能为空',{status:'danger',timeout:1000});
							return;
                        }
						var param={
                            entId:this.entId,
                            summary:this.summary
                        };
						ajax({
							url:'/sysEnterpriseBase/updateEnterpriseSummary?ticketId=<%=ticketId%>',
                            contentType: 'application/json',
							data:JSON.stringify(param),
							success:function (data) {
								console.log(data);
								if(data.status==1&&data.data==true){
                                    $.notify('概述修改成功',{status:'success',timeout:1000});
								}
                            }
						});
                    },
                    showCustomerDialog:function () {
                        this.customerAction='add';
						this.customerDialogVisible=true;
                        Vue.set(vue.customer,'name','');
                        Vue.set(vue.customer,'logo','');
                        Vue.set(vue.customer,'id','');
                    },
                    hideCustomerDialog:function () {
                        this.customerDialogVisible=false;
                    },
                    hideGloryDialog:function () {
                        this.gloryDialogVisible=false;
                    },
                    handleAvatarSuccess:function(file, fileList) {
                        Vue.set(vue.customer,'logo',file.url)
                    },
                    handleGlorySuccess:function(file, fileList) {
                        Vue.set(vue.glory,'logo',file.url)
                    },
                    customerImgUploadSuccess:function(response, file, fileList) {
						console.log(file);
						if(!file.response.success){
                            $.notify('客户信息保存失败',{status:'danger',timeout:1000});
						    return;
                        }
                        var url=this.customer.id?'/entCustomer/updateEntCustomer':'/entCustomer/saveEntCustomer';
						var obj=file.response.message;
						ajax({
							url:url,
							data:{
                                id:obj.businessCode,
                                customerName:this.customer.name,
                                fileId:obj.fileID,
                                entId:this.entId
							},
							success:function (data) {
								if(data.status&&data.data){
								    vue.customerDialogVisible=false;
                                    $.notify('客户信息保存成功',{status:'success',timeout:1000});
                                    var partner={
                                        name:vue.customer.name,
										logo:IMG_VIEW_URL+'&fileID='+obj.fileID,
										fileId:obj.fileID,
										id:obj.businessCode
									};
									if(vue.customer.id){
                                        Vue.set(vue.partnerList,vue.customer.index,partner);
									}else{
                                        vue.partnerList.push(partner);
									}
								}
                            }
						});
                    },
                    gloryImgUploadSuccess:function (response, file, fileList) {
                        console.log(file);
                        if(!file.response.success){
                            $.notify('荣誉信息保存失败',{status:'danger',timeout:1000});
                            return;
                        }
                        var url=this.glory.id?'/entGlory/updateEntGlory':'/entGlory/saveEntGlory';//新增
                        var obj=file.response.message;
                        var date=(typeof this.glory.date=='object')?(this.glory.date.format('yyyy-MM-dd')):this.glory.date;
                        ajax({
                            url:url,
                            data:{
                                id:obj.businessCode,
                                gloryType:this.glory.gloryType,
                                fileId:obj.fileID,
                                entId:this.entId,
								getTime:date
                            },
                            success:function (data) {
                                if(data.status&&data.data){
                                    vue.gloryDialogVisible=false;
                                    $.notify('荣誉或资质信息保存成功',{status:'success',timeout:1000});
                                    var item={
                                        gloryType:vue.glory.gloryType,
                                        logo:IMG_VIEW_URL+'&fileID='+obj.fileID,
                                        fileId:obj.fileID,
                                        id:obj.businessCode,
                                        date:date
                                    };
                                    if(vue.glory.id){
                                        Vue.set(vue.ryList,vue.glory.index,item);
                                    }else{
                                        vue.ryList.push(item);
                                    }
                                }
                            }
                        });
                    },
                    imgUploadError:function(error, file, fileList) {
						console.log(error);
                    },
					saveCustomerAction:function () {
                        if(!this.customer.id){//新增
                            Vue.set(vue.customerParam,'businessCode',upload.randomChar(19));
                            this.$refs.customerUpload.submit();
                        }else{
                            if(this.customer.logo!=this.partnerList[this.customer.index]['logo']){//修改了图片
                                upload.delete(this.partnerList[this.customer.index]['fileId']);
                                Vue.set(vue.customerParam,'businessCode',this.customer.id);
                                this.$refs.customerUpload.submit();
                            }else{
                                var fileId=vue.partnerList[vue.customer.index]['fileId'];
                                ajax({
                                    url:'/entCustomer/updateEntCustomer',
                                    data:{
                                        id:this.customer.id,
                                        customerName:this.customer.name,
                                        fileId:fileId,
                                        entId:this.entId,
                                    },
                                    success:function (data) {
                                        if(data.status&&data.data){
                                            vue.customerDialogVisible=false;
                                            $.notify('客户信息保存成功',{status:'success',timeout:1000});
                                            var partner={
                                                name:vue.customer.name,
                                                logo:IMG_VIEW_URL+'&fileID='+fileId,
                                                fileId:fileId,
                                                id:vue.customer.id,
                                            };
                                            Vue.set(vue.partnerList,vue.customer.index,partner);
                                        }
                                    }
                                });
                            }
                        }
                    },
                    saveCustomer:function () {
                        this.$refs['customerValidateForm'].validate(function(valid){
							if (valid) {
								vue.saveCustomerAction();
							}
						});
                    },
                    saveGloryAction:function () {
                        if(!this.glory.id) {//新增
                            Vue.set(vue.gloryParam,'businessCode',upload.randomChar(19));
                            this.$refs.gloryUpload.submit();
                        }else{
                            if(this.glory.logo!=this.ryList[this.glory.index]['logo']){//修改了图片
                                upload.delete(this.ryList[this.glory.index]['fileId']);
                                Vue.set(vue.gloryParam,'businessCode',this.glory.id);
                                this.$refs.gloryUpload.submit();
                            }else{
                                var fileId=vue.ryList[vue.glory.index]['fileId'];
                                var date=(typeof this.glory.date=='object')?(this.glory.date.format('yyyy-MM-dd')):this.glory.date;
                                ajax({
                                    url:'/entGlory/updateEntGlory',
                                    data:{
                                        id:this.glory.id,
                                        gloryType:this.glory.gloryType,
                                        fileId:fileId,
                                        entId:this.entId,
                                        getTime:date
                                    },
                                    success:function (data) {
                                        if(data.status&&data.data){
                                            vue.gloryDialogVisible=false;
                                            $.notify('荣誉资质信息保存成功',{status:'success',timeout:1000});
                                            var item={
                                                gloryType:vue.glory.gloryType,
                                                logo:IMG_VIEW_URL+'&fileID='+fileId,
                                                fileId:fileId,
                                                id:vue.glory.id,
                                                date:date
                                            };
                                            Vue.set(vue.ryList,vue.glory.index,item);
                                        }
                                    }
                                });
                            }
                        }
                    },
                    saveGlory:function () {
                        this.$refs['gloryValidateForm'].validate(function(valid){
                            if (valid) {
                                vue.saveGloryAction();
                            }
                        });
                    },
					delCustomer:function (index,partnerList) {
                        vue.$confirm('此操作将删除该客户, 是否确定?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function(){
                            ajax({
                                url:'/entCustomer/deleteEntCustomer',
                                data:{
                                    id:partnerList[index]['id']
                                },
                                success:function (data) {
                                    if(data.status&&data.data){
                                        $.notify('客户信息删除成功',{status:'success',timeout:1000});
                                        upload.delete(partnerList[index].fileId);
                                        vue.partnerList.splice(index,1);
                                    }
                                }
                            });
                        }).catch(function(){});
                    },
                    delGlory:function (index,list) {
                        vue.$confirm('此操作将删除该荣誉, 是否确定?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function(){
                            ajax({
                                url:'/entGlory/deleteEntGlory',
                                data:{
                                    id:list[index]['id']
                                },
                                success:function (data) {
                                    if(data.status&&data.data){
                                        $.notify('荣誉信息删除成功',{status:'success',timeout:1000});
                                        upload.delete(list[index].fileId);
                                        vue.ryList.splice(index,1);
                                    }
                                }
                            });
                        }).catch(function(){});
                    },
                    editCustomerRow:function (index,list,type) {
						this.customerAction=type;
						this.customerDialogVisible=true;
                        Vue.set(vue.customer,'name',list[index]['name']);
                        Vue.set(vue.customer,'logo',list[index]['logo']);
                        Vue.set(vue.customer,'id',list[index]['id']);
                        Vue.set(vue.customer,'index',index);
                        if(type=='edit'){
                            this.$refs['customerValidateForm'].validate();
                        }
                    },
                    editGloryRow:function (index,list,type) {
						this.gloryAction=type;
						this.gloryDialogVisible=true;
                        Vue.set(vue.glory,'gloryType',list[index]['gloryType']);
                        Vue.set(vue.glory,'logo',list[index]['logo']);
                        Vue.set(vue.glory,'id',list[index]['id']);
                        Vue.set(vue.glory,'date',new Date(list[index]['date']+' 00:00:00'));
                        Vue.set(vue.glory,'index',index);
                        if(type=='edit'){
                            this.$refs['gloryValidateForm']&&this.$refs['gloryValidateForm'].validate();
                        }
                    },
                    showGloryDialog:function () {
                        this.gloryAction='add';
						this.gloryDialogVisible=true;
                        Vue.set(vue.glory,'id','');
                        Vue.set(vue.glory,'gloryType','');
                        Vue.set(vue.glory,'date','');
                        Vue.set(vue.glory,'logo','');
                    },
                    saveNote:function () {
						console.log(this.salesNote+' '+this.paymentRule+' '+this.otherNote);
//						if (!this.salesNote){
//                            $.notify('销售说明不能为空',{status:'danger',timeout:1000});
//                            return;
//						}
						var param={
                            salesNote:this.salesNote,
                            paymentRule:this.paymentRule,
                            otherNote:this.otherNote,
                            entId:this.entId
                        };
						ajax({
							url:'/sysEnterpriseBase/updateEnterpriseSalesNote.htm?ticketId=<%=ticketId%>',
                            dataType: 'json',
                            contentType: 'application/json',
							data:JSON.stringify(param),
							success:function (data) {
								if(data.status==1&&data.data){
                                    $.notify('销售说明，付款规则，其它说明修改成功',{status:'success',timeout:1000});
								}
                            }
						});
                    },
                    hideAttachDialog:function () {
						this.attachDialogVisible=false;
                    },
                    showAttachDialog:function () {
                        this.attachDialogVisible=true;
                        Vue.set(vue.attach,'name','');
                        Vue.set(vue.attach,'fileName','');
                    },
                    downloadAttach:function (index,list) {
						upload.download(list[index]['fileID']);
                    },
                    hideContractDialog:function () {
						this.contractDialogVisible=false;
                    },
                    showContractDialog:function () {
                        this.contractDialogVisible=true;
                        Vue.set(vue.contract,'name','');
                        Vue.set(vue.contract,'fileName','');
                    },
                    delAttach:function (index,list) {
                        vue.$confirm('此操作将删除该附件, 是否确定?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function(){
                            upload.delete(list[index]['fileID']);
                            vue.attachList.splice(index,1);
                            $.notify('附件删除成功',{status:'success',timeout:1000});
                        }).catch(function(){});
                    },
                    delContract:function (index,list) {
                        vue.$confirm('此操作将删除该合同, 是否确定?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function(){
                            upload.delete(list[index]['fileID']);
                            vue.contractList.splice(index,1);
                            $.notify('合同删除成功',{status:'success',timeout:1000});
                        }).catch(function(){});
                    },
                    getAttachList:function () {
                        var list=upload.getImgListByBusinessCode(this.entId+'_attach');
                        for(var i in list){
                            list[i].logo=IMG_VIEW_URL+'&fileID='+list[i]['fileID'];
                            list[i].name=list[i]['fileName'];
                        }
						this.attachList=list;
                    },
                    getContractList:function () {
                        var list=upload.getImgListByBusinessCode(this.entId+'_contract');
                        for(var i in list){
                            list[i].logo=IMG_VIEW_URL+'&fileID='+list[i]['fileID'];
                            list[i].name=list[i]['fileName'];
                        }
						this.contractList=list;
                    },
                    handleAttachSuccess:function(file, fileList) {
                        Vue.set(vue.attach,'fileName',file.name);
                        Vue.set(vue.attach,'fileExtension',file.name.split('.')[1]);
                        var str='';
                        var size=file.size;
                        if(size<1024){
                            str=size+'byte';
						}else if(size<1024*1024){
                            str=Math.floor(size/1024)+'k';
						}else if(size<1024*1024*1024){
                            str=Math.floor(size/1024/1024)+'M';
						}else{
                            str=Math.floor(size/1024/1024/1024)+'G';
						}
                        Vue.set(vue.attach,'size',str);
                    },
                    saveAttach:function () {
                        this.$refs['attachValidateForm'].validate(function(valid){
                            if (valid) {
                                Vue.set(vue.attachParam,'name',vue.attach.name+'.'+vue.attach.fileExtension);
                                vue.$refs.attachUpload.submit();
                            }
                        });
                    },
                    attachUploadSuccess:function (response, file, fileList) {
                        console.log(file);
                        if(!file.response.success){
                            $.notify('附件上传失败',{status:'danger',timeout:1000});
                            return;
                        }
                        $.notify('附件上传成功',{status:'success',timeout:1000});
                        var obj=file.response.message;
                        var item={
                            name:obj.fileName,
                            fileName:obj.fileName,
                            fileID:obj.fileID
                        };
						vue.attachList.push(item);
                        this.attachDialogVisible=false;
                    },
                    handleContractSuccess:function(file, fileList) {
                        Vue.set(vue.contract,'fileName',file.name);
                        Vue.set(vue.contract,'fileExtension',file.name.split('.')[1]);
                        var str='';
                        var size=file.size;
                        if(size<1024){
                            str=size+'byte';
						}else if(size<1024*1024){
                            str=Math.floor(size/1024)+'k';
						}else if(size<1024*1024*1024){
                            str=Math.floor(size/1024/1024)+'M';
						}else{
                            str=Math.floor(size/1024/1024/1024)+'G';
						}
                        Vue.set(vue.contract,'size',str);
                    },
                    saveContract:function () {
                        this.$refs['contractValidateForm'].validate(function(valid){
                            if (valid) {
                                Vue.set(vue.contractParam,'name',vue.contract.name+'.'+vue.contract.fileExtension);
                                vue.$refs.contractUpload.submit();
                            }
                        });
                    },
                    contractUploadSuccess:function (response, file, fileList) {
                        console.log(file);
                        if(!file.response.success){
                            $.notify('合同上传失败',{status:'danger',timeout:1000});
                            return;
                        }
                        $.notify('合同上传成功',{status:'success',timeout:1000});
                        var obj=file.response.message;
                        var item={
                            name:obj.fileName,
                            fileName:obj.fileName,
                            fileID:obj.fileID
                        };
						vue.contractList.push(item);
                        this.contractDialogVisible=false;
                    }
                }
            });
		</script>
	</div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
        var rs = window.globalInit();
        rs.done(function () {
//            initTimeLine();
			$('#enterpriseEvalution').load("<%=appPath %>/enterprise/loadEnterpriseEvalution.htm?ticketId=<%=ticketId %>&enterpriseId=${enterprise.entId}");
        });

    });
    function initTimeLine(){
        var str='';
        var flag=true;
        for(var k in ryList){
            var year=ryList[k]['year'];
            str+='<dt><i class="el-icon-time"></i><span class="time">'+year+'年</span></dt>';
            for(var i=0;i<ryList[k]['content'].length;i++){
                var obj=ryList[k]['content'][i];
                str+='<dd class="pos-'+(flag?'right':'left')+' clearfix" id="'+obj['id']+'">'+
                    '<div class="circ"><i class="el-icon-time"></i></div>'+
                    '<div class="time">'+obj['time']+'</div>'+
                    '<div class="events">'+
                    '<div class="events-header">'+obj['name']+'</div>'+
                    '<div class="events-body">'+obj['description']+'</div>'+
                    '<div class="events-footer">'+
                    '<img src="<%=appPath%>/app/img/shu.png" />'+
                    '</div>'+
                    '</div>'+
                    '</dd>';
                flag=!flag;
            }
        }
        $('#mainVivaTimeline dl').html(str);
    }
    function goBack(){
        var index = getParam("index");
        if(index == "true"){
            document.cookie="index=true;path=<%=appPath%>/wastecircle/";
        }
        window.history.go(-1);
    }
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
</body>