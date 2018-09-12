<%@ page contentType="text/html; charset=UTF-8" language="java"
         errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>

<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="用户信息"/>
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#personalInformation"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/css/user/user.css?1">

<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">个人中心</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">个人信息</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>

        <%--<div class="content-heading" id="myAccount">--%>
            <%--<strong class="lead text-muted">我的帐户</strong>--%>
        <%--</div>--%>
        <div style="" class="bg-cover user-bg">
            <div class="p-xl text-center text-primary">
                <form id="myform" method="post" enctype="multipart/form-data">
                    <input id="uploadImage" type="file" class="fimg1" name="file"/>
                    <div id="uploadPreview">
                        <img src="<%=appPath%>/app/img/u54.png">
                    </div>
                    <input type="hidden" id="appkey" name="appKey"
                           value="5da5441f62e48aedc7a3853ffc75c2db"/>
                    <input type="hidden" id="proId" name="prodID" value="gf"/>
                    <input type="hidden" id="businessCode" name="businessCode" value="njknnvskAWD"/>
                    <!--<input type="hidden" id="name" name="name" value="营业执照" />-->
                    <div id="hasSelectedImg">
                        <a id="edit" class="btn btn-default">重新选择</a>
                        <a href="javascript:void(0);" id="uploadImg"
                           class="btn btn-default">确认上传</a>
                    </div>
                </form>
                <div>
                    <span id="orginalNameDiv" data-toggle='tooltip' title='点击编辑用户名'
                          class="text-center pv fontSize cursor"
                          onclick="display('#orginalNameDiv','#editUserNameDiv')">${user.userName}</span>
                </div>
                <form:form method="post" class="form-horizontal" action="#" id="updateUserNameForm"
                           data-parsley-validate="true" modelAttribute="user">
                    <div class="hide clearfix rowtop" id="editUserNameDiv">
                        <div class="leftFloat">
                            <form:input type="text" id="userNewName" path="userNewName"
                                        placeholder="请输入姓名" class="form-control center-block"
                                        data-trigger="change" maxlength="20"
                                        data-parsley-required="required"
                                        data-parsley-required-message="请输入姓名"
                                        oninput="userNameBlur(this)"
                                        data-parsley-pattern="^[0-9a-zA-Z\u3E00-\u9FA5_.]+$"
                                        data-parsley-pattern-message="姓名不能出现特殊字符"/>
                        </div>
                        <div class="center-block">
                            <button type="button" id="updateUserName"
                                    class="btn btn-sm btn-primary btnCss confirmBtn"
                                    onclick="updateNewName()">确认
                            </button>
                            <button type="button" id="cancelUserName"
                                    class="btn btn-sm btn-default btnCss"
                                    onclick="displayOrShowDiv('#editUserNameDiv','#orginalNameDiv')">
                                取消
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

        <div class="row text-center">
            <c:if test="${user.phoneNo!=null}">
                <span> 手机号码：</span>
                <span id="phoneNo">${user.phoneNo}</span>
                <img src="<%=appPath%>/app/img/u78.png"/>
            </c:if>
            <c:if test="${user.mailAddress!=null}">
                <span class="mailCss"> 邮箱：</span>
                <span id="orgMailAddress"> ${user.mailAddress}</span>
                <img src="<%=appPath%>/app/img/u78.png"/>
            </c:if>
        </div>

        <div class="row rowtop">
            <div class="col-md-12 text-center">
                <button type="button" class="btn btn-sm btn-link"
                        onclick="displaySubPage('#editUserPasswordDiv')">修改密码
                </button>
                <button type="button" class="btn btn-sm btn-link"
                        onclick="displaySubPage('#editUserMail')">绑定邮箱
                </button>
                <!-- <button type="button" class="btn btn-sm btn-link"
                        onclick="displaySubPage('#verifyUserInfo')">修改手机号码
                </button> -->
                <!-- <button type="button"  class="btn btn-sm btn-link" onclick="displaySubPage('#verifyUserInfo')" >注销用户</button> -->
            </div>
        </div>
        <div class="clearfix rowtop ">
            <div class=" hide editCss center-block" id="editUserPasswordDiv">
                <div class="panel b m0">
                    <div class="panel-body">
                        <form:form method="post" id="updatePasswordForm"
                                   class="form-horizontal" data-parsley-validate="true" action="#"
                                   modelAttribute="editUser">
                            <div class="form-group has-feedback">
                                <span class="col-md-4  text-right">当前密码</span>
                                <div class="col-md-8 ">
                                    <form:input type="password" id="oldPassword" path="password"
                                                placeholder="输入当前密码" value="" class="form-control"
                                                maxlength="18" data-trigger="change"
                                                oncontextmenu="return false"
                                                data-parsley-required="required"
                                                data-parsley-required-message="请输入密码"
                                                data-parsley-password="true"
                                                data-parsley-password-message=""/>
                                </div>
                            </div>
                            <div class="form-group has-feedback">
                                <span class="col-md-4  text-right">新密码</span>
                                <div class="col-md-8 ">
                                    <form:input type="password" id="newPassword" path="newPassword"
                                                placeholder="输入新密码" value="" class="form-control"
                                                maxlength="18" data-trigger="change"
                                                oncontextmenu="return false"
                                                data-parsley-required="required"
                                                data-parsley-required-message="请输入密码"
                                                data-parsley-password="true"
                                                data-parsley-password-message=""
                                                onblur="passwordCheck(this,'rePassword')"/>
                                </div>
                            </div>
                            <div class="form-group has-feedback">
                                <span class="col-md-4  text-right">再次输入新密码</span>
                                <div class="col-md-8 ">
                                    <form:input type="password" id="rePassword"
                                                path="newPasswordAgain" value=""
                                                placeholder="再次输入新密码" class="form-control"
                                                maxlength="18" data-trigger="change"
                                                oncontextmenu="return false"
                                                data-parsley-required="required"
                                                data-parsley-required-message="请输入确认密码"
                                                data-parsley-password="true"
                                                data-parsley-password-message=""
                                                onblur="passwordCheck(this,'newPassword')"/>
                                </div>
                            </div>
                            <div class="form-group has-feedback ">
                                <div class="col-md-12 text-center">
                                    <button type="button" id="updatePassword"
                                            class="btn btn-sm btn-primary confirmBtn"
                                            onclick="updateNewPassword()">确认
                                    </button>
                                    <button type="button" id="cancelPassword"
                                            class="btn btn-sm btn-default"
                                            onclick="closeCurrentPage('#editUserPasswordDiv')">取消
                                    </button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>


            <div class=" hide editCss center-block" id="editUserMail">
                <div class="panel b m0">
                    <div class="panel-body">
                        <form:form method="post" id="editUserMailForm" class="form-horizontal"
                                   data-parsley-validate="true" action="#"
                                   modelAttribute="editUser">
                            <div class="form-group text-center">
                                <div class="col-md-12 text-left">
                                    <p>绑定邮箱后，可使用通过邮箱重置密码、接收邮件消息等功能。</p>
                                </div>
                            </div>
                            <div class="form-group has-feedback">
                                <span for="mailAddress" class="col-md-2  text-left">您的邮箱</span>
                                <div class="col-md-8">
                                    <form:input type="text" id="mailNewAddress" path="mailAddress"
                                                placeholder="@ ." oninput="mailCheck(this)" value=""
                                                class="form-control" data-trigger="change"
                                                data-parsley-required="required"
                                                data-parsley-required-message="请填写邮箱"
                                                data-parsley-mailcode="true"
                                                data-parsley-mailcode-message="邮箱已被使用"
                                                data-parsley-pattern="^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$"
                                                data-parsley-pattern-message="输入邮箱码格式不正确"/>
                                </div>
                            </div>
                            <div class="form-group ">
                                <div class="col-md-12 text-center">
                                    <button type="button" id="verifymailbtn"
                                            class="btn btn-sm btn-primary confirmBtn"
                                            onclick="updateMailAddress('#editUserMail','#verifyMailDiv')">
                                        确认
                                    </button>
                                    <button type="button" id="cancelmail"
                                            class="btn btn-sm btn-default "
                                            onclick="closeMailCurrentPage('#editUserMail')">取消
                                    </button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
            <div class=" hide editCss center-block" id="verifyMailDiv">
                <div class="panel b m0">
                    <div class="panel-body">
                        <form:form method="post" class="form-horizontal"
                                   data-parsley-validate="true" action="#"
                                   modelAttribute="editUser">
                            <div class="form-group">
                                <div class="col-md-12">
                                    <span> 已经向您的邮箱发送验证邮件，请前往确认。</span>
                                </div>
                            </div>
                            <div class="form-group mailCss">
                                <div class="col-md-12 text-center ">
                                    <button type="button" id="updateMail"
                                            class="btn btn-sm btn-primary confirmBtn"
                                            onclick="closeVerifyCurrentPage('#verifyMailDiv')">确认
                                    </button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
            <div class=" hide editCss center-block" id="verifyUserInfo">
                <div class="panel b m0">
                    <div class="panel-body">
                        <form:form method="post" id="verifyUserInfoForm" class="form-horizontal"
                                   data-parsley-validate="true" action="#"
                                   modelAttribute="editUser">
                            <input type="text" value="" style="display: none;"/>
                            <div class="form-group has-feedback">
                                <span class="col-md-4  text-right">手机号码</span>
                                <div id="latestPhoneNo"
                                     class="col-md-8 text-left">${user.phoneNo}</div>
                            </div>
                            <div class="form-group has-feedback">
                                <span class="col-md-4  text-right">登录密码</span>
                                <div class="col-md-8 text-left">
                                    <form:input type="password" path="password" id="currentPassword"
                                                placeholder="输入当前密码" value="" class="form-control"
                                                maxlength="18" data-trigger="change"
                                                oncontextmenu="return false"
                                                data-parsley-required="required"
                                                data-parsley-required-message="请输入密码"
                                                data-parsley-password="true"
                                                data-parsley-password-message=""/>
                                    <span class="fa fa-lock form-control-feedback text-muted"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12 text-center">
                                    <button type="button" id="verfiyUserInfo"
                                            class="btn btn-sm btn-primary nextStepBtn"
                                            onclick="verifyPassword('#verifyUserInfo','#editUserPoneNo')">
                                        下一步
                                    </button>
                                    <button type="button" id="cancelUserInfo"
                                            class="btn btn-sm btn-default "
                                            onclick="closeVerifyPassword('#verifyUserInfo')">取消
                                    </button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
            <div class=" hide editCss center-block" id="editUserPoneNo">
                <div class="panel b m0">
                    <div class="panel-body">
                        <form:form method="post" id="editUserPoneNoForm"
                                   class="form-horizontal" data-parsley-validate="true" action="#"
                                   modelAttribute="editUser">
                            <div class="form-group has-feedback">
                                <span class="col-md-3  text-right">输入新手机号</span>
                                <div class="col-md-8 text-left">
                                    <form:input type="text" id="newPhoneNo" path="newPhoneNo"
                                                placeholder="请输入11位手机号" value=""
                                                class="form-control" maxlength="11"
                                                oninput="poneNumInputCheck(this)"
                                                onblur="poneNumBlurCheck(this)"
                                                data-trigger="change"
                                                data-parsley-required="required"
                                                data-parsley-required-message="请输入手机号码"
                                                data-parsley-existcode="true"
                                                data-parsley-existcode-message="该手机号已被使用"
                                                data-parsley-phonenum="true"
                                                data-parsley-phonenum-message=""/>
                                </div>
                            </div>
                            <div class="form-group has-feedback">
                                <span class="col-md-3  text-right">短信验证码</span>
                                <div class="col-md-5 ">
                                    <form:input type="text" id="userVerificationCode"
                                                path="userVerificationCode" placeholder="请输入收到验证码"
                                                value="" class="form-control" data-trigger="change"
                                                maxlength="6"
                                                data-parsley-required="required"
                                                data-parsley-required-message="请输入验证码"
                                                data-parsley-securitycode="true"
                                                data-parsley-securitycode-message="输入的验证码不正确"/>
                                </div>
                                <div class="col-md-4 ">
                                    <button type="button" id="resendSecurityCodebtn"
                                            class="btn btn-sm btn-default"
                                            onclick="resendSecurityCode(this)">获取验证码
                                    </button>
                                    <span id="infoMsg"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12 text-center">
                                    <button type="button" id="updatePhoneNo"
                                            class="btn btn-sm btn-primary confirmBtn"
                                            onclick="submitNewPhoneNo('#editUserPoneNo','#userInfo')">
                                        确定
                                    </button>
                                    <button type="button" id="cancelPhoneNo"
                                            class="btn btn-sm btn-default "
                                            onclick="closeEditUserPoneNoForm('#editUserPoneNo')">取消
                                    </button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    <div class="panel panle-body">
            <div class="content-wrapper">
                <div class="row">
                    <div class="col-md-12">
                        <p style="font-size:20px"> 安全评分: ${user.scurityScore }分

                        </p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-7">
                        <div class="progress">
                            <div id="passwordDegreeDiv" class="progress-bar" aria-valuenow="60"
                                 aria-valuemin="0" aria-valuemax="100" style="width: ${user.scurityScore}%">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <c:if test="${(user.scurityDesc)!= null && fn:length(user.scurityDesc) > 0}">
                            <div class="">
                                您的帐号存在安全风险，建议立即优化以下<b>${fn:length(user.scurityDesc)}</b>项:
                            </div>
                            <ul class="list-unstyled clearfix" id="score_detail">
                                <c:forEach var="scurityItem" items="${user.scurityDesc}" varStatus="status"
                                           begin="0" step="1">
                                    <li class="score-detail-list score-detail-setquestion clearfix">
                                        <span>${status.index+1}.${scurityItem}</span></li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </div>
                </div>
            </div>

        <!--    <div class="row">
        <div class = "col-md-1">
            <div class="progress">
               <div class="progress-bar"  id="passwordDetailDiv" role="progressbar" aria-valuenow="60" 
                  aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                  <span class="sr-only">40% 完成</span>
               </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class = "col-md-1">
            <div class="progress">
               <div class="progress-bar" id="phoneDetailDiv" role="progressbar" aria-valuenow="60" 
                  aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                  <span class="sr-only">40% 完成</span>
               </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class = "col-md-1">
            <div class="progress">
               <div class="progress-bar" id="mailDetailDiv"  role="progressbar" aria-valuenow="60" 
                  aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                  <span class="sr-only">40% 完成</span>
               </div>
            </div>
        </div>
    </div> -->
    </div>
</section>


<script src="<%=appPath%>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/main/common/jquery.form.js"></script>
<script src="<%=appPath %>/app/js/RSA.js"></script>

<script type="text/javascript">
  var subId = "";
  var securitycodeError = true;// 手机验证码正确性验证
  var passwordError = true; // 确认密码正确性验证
  var mailcodeError = true;  //确认该邮箱是否被使用
  var phoneExist = true; //手机号已被使用
  var phoneNumError = true;
  var second;// 120秒倒计时
  var passwordMsg0 = "两次输入的密码不一致，请重新输入。";
  var imgType = 'c';
  var userId = "${user.userId}";
  var ticketId = "<%=ticketId %>";
  var businessCode = imgType + userId;
  $(function () {
    $('.tooltip-editName').tooltip('点击可以修改名字');
  });
  function saveImg(fileFingerPrint, fileID) {
    $.ajax({
      url: "<%=appPath %>/fileUpload/updateFileByBusinessCode",
      data: {
        'businessCode': businessCode,
        'fileFingerPrint': fileFingerPrint,
        'fileId': fileID,
        'fileType': imgType,
        'referenceId': userId,
        'ticketId': ticketId
      },
      type: "POST",
      dataType: 'json',
      async: false,
      success: function (data) {
        if (data.status == 1) {
          $.notify("修改头像成功", {status: 'success'});
          $("#uploadPreview img").attr('src',
              IMG_VIEW_URL + '&businessCode=' + businessCode + '&fileID=' + fileID);
          $('#addImg').show();
          $('#uploadImage').removeClass('second');
          $('#hasSelectedImg').hide();
        } else {
          $.notify("修改头像失败", {status: 'danger'});
        }
      },
      error: function (er) {
        $.notify("保存图片信息出错", {status: 'danger'});
      }
    });
  }
  $(document).ready(function () {
      init();
  });
  function init() {

    $('#businessCode').val(businessCode);
    $('#appkey').val(APPKEY);
    $('#prodId').val(PRODID);
    $.ajax({
      url: "<%=appPath %>/fileUpload/getFileByBusinessCode",
      data: {
        'businessCode': businessCode,
        'ticketId': ticketId
      },
      type: "POST",
      dataType: 'json',
      async: false,
      success: function (data) {
        if (data.status == 1) {
          //$.notify("修改成功",{status:'success'});
          var uploadFileInfo = data.data.uploadFileInfo;
          if (uploadFileInfo) {
            $("#uploadPreview img").attr('src',
                IMG_VIEW_URL + '&businessCode=' + uploadFileInfo.businessCode + '&fileID='
                + uploadFileInfo.fileId);
          }
        } else {
          $.notify("获取图片信息失败", {status: 'danger'});
        }
      },
      error: function (er) {
        $.notify("获取图片信息失败", {status: 'danger'});
      }
    });
    $('#uploadImage').change(function () {
      var files = !!this.files ? this.files : [];
        if(files.length==1&&FILETYPE.indexOf(files[0].type)==-1){
            $.notify(TYPE_ERROR_MSG, {status:'info',timeout:1500 });
            return;
        }
      if (!files.length || !window.FileReader) return;
      $('#uploadImage').addClass('second');
      $('#hasSelectedImg').show();
      if (/^image/.test(files[0].type)) {
        var reader = new FileReader();
        reader.readAsDataURL(files[0]);
        reader.onloadend = function () {
          $("#uploadPreview img").attr('src', this.result);
        }
      }
    });
    $('#uploadImg').click(function () {
      $('#myform').ajaxSubmit({
        url: IMG_PREV + UPLOAD_ACTION,
        type: 'POST',
        async: false,
        success: function (data) {
          if (data.success) {
            //开始插入数据
            saveImg(data.message.fileFingerPrint, data.message.fileID);
          } else {
            $.notify("上传失败", {status: 'danger'});
          }
        },
        error: function (err) {
          $.notify(err.message, {status: 'danger'});
        }
      });
    });
    $("input:password").bind("copy cut paste", function (e) {
      return false;
    });

    var phoneValid = ${user.phoneValid};
    if (phoneValid == true) {
      $("#phoneNoVerfiy").attr("checked", "checked");
    }

    var mailValid = ${user.mailValid};
    if (mailValid == true) {
      $("#mailValid").attr("checked", "checked");
    }

    $("#phoneNoVerfiy").attr("disabled", "disabled");
    $("#mailValid").attr("disabled", "disabled");

    // 确认密码正确性验证
    window.Parsley.addValidator('password', function (value, elem) {
      return passwordError;
    }, 32);
    window.Parsley.addValidator('securitycode', function (value, elem) {
      return securitycodeError;
    }, 32);

    window.Parsley.addValidator('mailcode', function (value, elem) {
      return mailcodeError;
    }, 32);

    window.Parsley.addValidator('existcode', function (value, elem) {
      return phoneExist;
    }, 32);

    window.Parsley.addValidator('phonenum', function (value, elem) {
      return phoneNumError;
    }, 32);

    $("input:password").bind("copy cut paste", function (e) {
      return false;
    });

    var passwordDegree = ${user.scurityScore};

    if (passwordDegree > 86) {
        /*  $("#passwordDegreeDiv").addClass("progress-bar-success"); */
    } else {
        /* $("#passwordDegreeDiv").addClass("progress-bar-warning"); */
    }

  }

  function mailCheck(t) {
    $(t).parsley().validate();
    if ($(t).parsley().isValid()) {
      $("#verifymailbtn").removeAttr("disabled");
    }
  }

  function displaySubPage(id) {
	$("#orginalNameDiv").removeClass('hide');
    $('#editUserNameDiv').addClass('hide');
    $('#editUserPasswordDiv').addClass('hide');
    $('#verifyUserInfo').addClass('hide');
    $('#editUserPoneNo').addClass('hide');
    $('#editUserMail').addClass('hide');
    $('#verifyMailDiv').addClass('hide');
    $(id).removeClass('hide');
  }

  function closeCurrentPage(id) {
	$("#orginalNameDiv").removeClass('hide');
	$("#editUserNameDiv").addClass('hide');
    $(id).addClass('hide');
    $("input").val("");

    isPasswordCorrect = true;
    $("#oldPassword").parsley().validate();

    $("input").removeClass('parsley-error');
    $(".parsley-errors-list").each(function () {
      $(this).children().html("");
    });
  }

  function closeVerifyCurrentPage(id) {
    $(id).addClass('hide');
    $("input").val("");

    isPasswordCorrect = true;
    $("#oldPassword").parsley().validate();

    $("input").removeClass('parsley-error');
    $(".parsley-errors-list").each(function () {
      $(this).children().html("");
    });
    location.reload();
  }

  function closeVerifyPassword(id) {
    $(id).addClass('hide');
    $("input").val("");
    passwordError = true;
    $('#currentPassword').parsley().validate();

    $("#currentPassword").removeClass('parsley-error');
    $(".parsley-errors-list").each(function () {
      $(this).children().html("");
    });

  }

  function displayOrShowDiv(parentId, id) {
    $(parentId).addClass('hide');
    $(id).removeClass('hide');
    $("input").val("");
    $("input").each(function () {
      $(this).removeClass('parsley-error');
      $(".parsley-errors-list").each(function () {
        $(this).children().html("");
      });
    });
  }

  function display(parentId, id) {
      $('#userNewName').val($('#orginalNameDiv').html())
      $(parentId).addClass('hide');
      $(id).removeClass('hide');
  }

  function closeMailCurrentPage(id) {
	 $("#orginalNameDiv").removeClass('hide');
	 $("#editUserNameDiv").addClass('hide');
    $(id).addClass('hide');
    $("input").val("");
    mailcodeError = true;
    $("#mailNewAddress").parsley().validate();
    $("#mailNewAddress").removeClass('parsley-error');
    $(".parsley-errors-list").each(function () {
      $(this).children().html("");
    });
  }
  //手机号码离焦事件
  function poneNumBlurCheck(t) {
    // 手机号码格式正确性验证
    phoneNumError = true;
    if (!phoneNumReg3.test($(t).val())) {
      // 手机号码必须是11位数字
      phoneNumError = false;
      $(t).attr("data-parsley-phonenum-message", phoneNumMsg3);
      $(t).parsley().validate();
    } else {
      checkPhoneNumExist();
    }
  }
  ;

  //手机号码输入事件
  function poneNumInputCheck(t) {
    // 手机号码格式正确性验证

    phoneNumError = true;
    if (!phoneNumReg1.test($(t).val())) {
      // 手机号码只能以数字1开头
      phoneNumError = false;
      $(t).attr("data-parsley-phonenum-message", phoneNumMsg1);
      $(t).parsley().validate();
    } else if (!phoneNumReg2.test($(t).val())) {
      // 手机号码只能是数字
      phoneNumError = false;
      $(t).attr("data-parsley-phonenum-message", phoneNumMsg2);
      $(t).parsley().validate();
    }
    if ($(t).parsley().isValid()) {
      $("#resendSecurityCodebtn").removeAttr("disabled");
      $("#updatePhoneNo").removeAttr("disabled");
    } else {
      $("#resendSecurityCodebtn").attr("disabled", "disabled");
      $("#updatePhoneNo").attr("disabled", "disabled");
    }
  }
  ;

  function checkPhoneNumExist() {
    $.ajax({
      async: false,
      type: 'post',
      url: "checkPhoneNumExist.htm?ticketId=<%=ticketId%>",
      data: $('#editUserPoneNoForm').serialize(),
      success: function (result) {
        var obj = $.parseJSON(result);
        msg = obj.data.msg;
        status = obj.data.status;
        if (status == "1") {
          phoneExist = true;
          $('#newPhoneNo').parsley().validate();

          $("#resendSecurityCodebtn").removeAttr("disabled");
          $("#updatePhoneNo").removeAttr("disabled");
        } else {
          phoneExist = false;
          $("#newPhoneNo").parsley().validate();
          $("#newPhoneNo").attr("data-parsley-phonenum-message", "手机号已存在");
          $("#resendSecurityCodebtn").attr("disabled", "disabled");

          phoneExist = true;
          $("#newPhoneNo").attr("data-parsley-phonenum-message", "");
          $("#updatePhoneNo").removeAttr("disabled");
        }
      }
    });

  }

  function verifyPassword(currentId, nextId) {
    var form = $("#verifyUserInfoForm").parsley();
    form.validate();
    if (form.isValid()) {
      $.ajax({
        async: false,
        type: 'post',
        url: "checkUserPassword.htm?ticketId=<%=ticketId%>",
        data: $('#verifyUserInfoForm').serialize(),
        success: function (result) {
          var obj = $.parseJSON(result);
          msg = obj.data.msg;
          status = obj.data.status;
          if (status == "1") {
            $(currentId).addClass('hide');
            $(nextId).removeClass('hide');
            $("#currentPassword").val("");
          } else {
            passwordError = false;
            $('#currentPassword').attr("data-parsley-password-message", "密码错误");
            $('#currentPassword').parsley().validate();

            passwordError = true;
            $("#currentPassword").attr("data-parsley-password-message", "");
          }
        }
      });
    }

  }

  function closeEditUserPoneNoForm(id) {
    $(id).addClass('hide');
    $("input").val("");
    securitycodeError = true;
    $("#userVerificationCode").parsley().validate();
    $("#newPhoneNo").removeClass('parsley-error');
    $("#userVerificationCode").removeClass('parsley-error');
    $(".parsley-errors-list").each(function () {
      $(this).children().html("");
    });

  }

  //120秒内获取验证码btn不可使用
  function change() {
    second--;
    if (second > -1) {
      $("#resendSecurityCodebtn").attr("disabled", "disabled");
      $("#infoMsg").text(second + "秒后可重新获取验证码");
      timer = setTimeout('change()', 1000);//调用自身实现
    } else {
      clearTimeout(timer);
      $("#resendSecurityCodebtn").removeAttr("disabled");
      $("#infoMsg").text("");
    }
  }

  //获取验证码事件
  function resendSecurityCode(t) {
    $("#resendSecurityCodebtn").attr("disabled", "disabled");
    $.ajax({
      async: false,
      url: "<%=appPath%>/userRegister/getIdentifyCode.htm?etc=" + new Date().getTime(),
      data: {
        "phoneNum": $("#newPhoneNo").val(),
        "smsType":"2"
      },
      success: function (result) {
        var obj = $.parseJSON(result);
        if (obj.status == 1) {
          // 120秒内获取验证码btn不可使用
          second = second_constants;
          setTimeout('change()', 1000);
        } else {
          // 获取验证码失败
        }
        ;
      }
    });
  }
  ;

  function submitNewPhoneNo(closeid, id) {
    var form = $("#editUserPoneNoForm").parsley();
    form.validate();
    if (form.isValid()) {
      $.ajax({
        type: 'post',
        url: "verifyCode.htm?ticketId=<%=ticketId%>",
        data: $('#editUserPoneNoForm').serialize(),
        dataType:'json',
        success: function (result) {
          if(result.status == 1){
            $("#updatePhoneNo").removeAttr("disabled");
            phoneNumError = true;
            securitycodeError = true;
            $("#userVerificationCode").parsley().validate();
            $("#newPhoneNo").parsley().validate();
            $.ajax({
              type: 'post',
              url: "updateUserPhoneNo.htm?ticketId=<%=ticketId%>",
              data: $('#editUserPoneNoForm').serialize(),
              dataType:'json',
              success: function (result) {
                if (result.status == 1) {
                  var phoneNo = result.data;
                  $("#updateUserName").removeAttr("disabled");
                  $("#phoneNo").html(phoneNo);
                  $("#latestPhoneNo").html(phoneNo);
                  $(closeid).addClass('hide');
                  $(id).removeClass('hide');
                  $("#newPhoneNo").val("");
                  $("#userVerificationCode").val("");
                  var options = {"status": "success"};
                  $.notify("更新手机号成功!", options);
                } else {
                  var options = {"status": "danger"};
                  $.notify(result.infoList[0], options);
                }
              }
            });
          }else{
            securitycodeError = false;
            $("#userVerificationCode").attr("data-parsley-securitycode-message", result.infoList[0]);
            $("#userVerificationCode").parsley().validate();

            securitycodeError = true;
            $("#userVerificationCode").attr("data-parsley-securitycode-message", "");
            $("#updatePassword").removeAttr("disabled");
          }
        }
      });
    }

  }

  function updateMailAddress(currentId, nextId) {
    mailcodeError = true;
    $("#mailNewAddress").parsley().validate();

    var form = $("#editUserMailForm").parsley();
    form.validate();
    if (form.isValid()) {
      $.ajax({
        async: false,
        type: 'post',
        url: "checkMailAddressExist.htm?ticketId=<%=ticketId%>",
        data: $('#editUserMailForm').serialize(),
        success: function (result) {
          var obj = $.parseJSON(result);
          msg = obj.data.msg;
          status = obj.data.status;
          if (status == "1") {
            $.ajax({
              async: false,
              type: 'post',
              url: "updateUserMailAddress.htm?ticketId=<%=ticketId%>",
              data: $('form').serialize(),
              success: function (result) {
                obj = $.parseJSON(result);
                msg = obj.data.msg;
                status = obj.data.status;
                var mailAddress = obj.data.newMailAddress;
                if (status == "1") {
                  $("#mailNewAddress").val("");
                  $("#orgMailAddress").html(mailAddress);
                  $("#updateUserName").removeAttr("disabled");
                  $(currentId).addClass('hide');
                  $(nextId).removeClass('hide');
                  var options = {"status": "success"};
                  $.notify("更新邮箱成功！", options);
                } else {
                  var options = {"status": "danger"};
                  $.notify("更新邮箱失败！", options);
                }
              }
            });
          } else {
            mailcodeError = false;
            $("#mailNewAddress").attr("data-parsley-mailcode-message", "邮箱已被使用");
            $("#mailNewAddress").parsley().validate();

            mailcodeError = true;
            $("#mailNewAddress").attr("data-parsley-mailcode-message", "");
          }
          ;
        }
      });
    }
  }

  function updateNewName() {
    var form = $("#updateUserNameForm").parsley();
    form.validate();
    if (form.isValid()) {
        var userName = $('#updateUserNameForm input').val();
        $.ajax({
        type: 'post',
        async: true,
        url: "../personaluser/updateNewName.htm",
        data: {
            userNewName:userName,
            ticketId:'<%=ticketId%>'
        },
        dataType:'json',
        success: function (result) {

          if (result.status == "1") {
              var options = {"status": 'success',timeout:2000};
              $("#updateUserName").removeAttr("disabled");
              $("#userNewName").val("");
              $("#orginalNameDiv").html(userName);
              $('#editUserNameDiv').addClass('hide');
              $('#orginalNameDiv').removeClass('hide');
              $.notify("用户名更新成功", options);
          } else {
              var options = {"status": "danger"};
              $.notify("更新用户名失败", options);
              $("#updateUserName").attr("disabled", "disabled");
          }
          ;
        }
      });
    }
  }

  function updateNewPassword() {
    passwordCheck($("#oldPassword"));
    passwordCheck($("#newPassword"), "rePassword");
    passwordCheck($("#rePassword"), "newPassword");
    if (passwordError) {
      $.ajax({
        type: 'post',
        async: false,
        url: "checkUserPassword.htm?ticketId=<%=ticketId%>",
        data: $('#updatePasswordForm').serialize(),
        success: function (result) {
          var obj = $.parseJSON(result);
          msg = obj.data.msg;
          status = obj.data.status;
          if (status == "1") {
            isPasswordCorrect = true;
            $("#oldPassword").parsley().validate();
            $("#updatePassword").removeAttr("disabled");
            $.ajax({
              type: 'post',
              async: false,
              url: "updateUserPassword.htm?ticketId=<%=ticketId%>",
              data: $('form').serialize(),
              success: function (result) {
                var obj = $.parseJSON(result);
                msg = obj.data.msg;
                status = obj.data.status;
                var options = {
                  "status": status
                };
                if (status == "1") {
                  $("#updatePassword").removeAttr("disabled");
                  $('#editUserPasswordDiv').addClass('hide');
                  $('#userInfo').removeClass('hide');
                  $("#updatePasswordForm")[0].reset();
                  var options = {"status": "success"};
                  $.notify("密码更新成功", options);
                  location.reload();
                } else {
                  var options = {"status": "danger"};
                  $.notify("密码更新失败", options);
                }
              }
            });
          } else {
            passwordError = false;
            $('#oldPassword').attr("data-parsley-password-message", "密码错误");
            $("#oldPassword").parsley().validate();

            passwordError = true;
            $('#oldPassword').attr("data-parsley-password-message", "");
            $("#updatePassword").removeAttr("disabled");
          }
          ;
        }
      });
    }

  }

  function passwordCheck(t, passwordId) {
    // 密码正则检查
    if (!passwordReg1.test($(t).val())) {
      // 密码长度在6到18位之间。
      passwordError = false;
      $(t).attr("data-parsley-password-message", passwordMsg1);
      $(t).parsley().validate();
    } else if (passwordReg2.test($(t).val())) {
      // 密码不能全是数字。
      passwordError = false;
      $(t).attr("data-parsley-password-message", passwordMsg2);
      $(t).parsley().validate();
    } else if (passwordReg3.test($(t).val())) {
      // 密码不能全是字母。
      passwordError = false;
      $(t).attr("data-parsley-password-message", passwordMsg3);
      $(t).parsley().validate();
    } else if (!passwordReg4.test($(t).val())) {
      // 允许含有的特殊字符
      passwordError = false;
      $(t).attr("data-parsley-password-message", passwordMsg4);
      $(t).parsley().validate();
    } else {
      passwordError = true;
    }

    // 密码一致性检查
    if (passwordError && passwordId) {
      var $other = $("#" + passwordId);
      if ($other.val() != "") {
        if ($(t).val() == $other.val()) {
          passwordError = true;
          $other.parsley().validate();
        } else {
          passwordError = false;
          $(t).attr("data-parsley-password-message", passwordMsg0);
        }
      }
    }

    $(t).parsley().validate();
  }

  function userNameBlur(t) {
    $(t).parsley().validate();
    if ($(t).parsley().isValid()) {
      $("#updateUserName").removeAttr("disabled");
    } else {
      $("#updateUserName").attr("disabled", "disabled");
    }
  }
</script>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>

