<%@page import="com.mlsc.waste.utils.Constant" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
    String menuId = request.getParameter("menuId");
%>
<link rel="stylesheet" href="<%=appPath %>/css/menu.css?2">
<style>
    .marginTop1 {
        margin-top: 20px;
    }

    .liborderleft {
        border-left: 3px solid #1171d1;
        margin-left: -3px;
    }

    .tip-other {
        margin-top: 20px;
        margin-left: 35px;
    }

    .aside-collapsed .tip-other {
        display: none;
    }
</style>
<!-- sidebar-->
<aside class="aside">
    <!-- START Sidebar (left)-->
    <div class="aside-inner">
        <nav data-sidebar-anyclick-close="" class="sidebar">
            <!-- START sidebar nav-->
            <ul class="nav">
                <li class="tip-other marginTop">
                    <div class="row operation-tool-bar searchtool">
                        <div id="businessDiv">
                            <button type="button" class="btn btn-labeled btn-release" style="margin-bottom: 20px" data-toggle="tooltip" onclick="initPublishMsg(this,'/facilitator/publish.htm')">
                                委托处理 <span class="btn-label1"><i class="fa1 fa-plus1"></i></span>
                            </button>
                        </div>
                    </div>
                </li>
                <li class="leafMenu" id="myIndex">
                    <a href="<%=appPath %>/facilitator/cfList.htm?ticketId=<%=ticketId %>" title="资源池"
                       class="menuli">
                        <em id="menuMyIndex"></em>
                        <span data-localize="sidebar.nav.menu.MENU" class="menufont">资源池</span>
                    </a>
                </li>
                <li>
                    <a href="#mywastecircle" title="我的易废圈" data-toggle="collapse" class="menuli">
                        <em id="menuWasteCircle"></em>
                        <span data-localize="sidebar.nav.menu.MENU" class="menufont">我的易废圈</span>
                    </a>
                    <ul id="mywastecircle" class="nav sidebar-subnav collapse">
                        <li class="menuli leafMenu" id="myCustomer">
                            <a href="<%=appPath %>/facilitator/customerList.htm?ticketId=<%=ticketId %>&tabIndex=1"
                               title="我的客户">
                                <span data-localize="sidebar.nav.menu.SUBMENU" class="menufont"><em
                                        id="menuMyFollow"></em>客户</span>
                            </a>
                        </li>
                        <li class="menuli leafMenu" id="myShoppingCart">
                            <a href="<%=appPath %>/facilitator/buyList.htm?ticketId=<%=ticketId %>"
                               title="我的询价">
                                <span data-localize="sidebar.nav.menu.SUBMENU" class="menufont"><em
                                        id="menuShoppingCart"></em>平台报价</span>
                            </a>
                        </li>
                        <li class="menuli leafMenu" id="myRelease">
                            <a href="<%=appPath %>/facilitator/publishList.htm?ticketId=<%=ticketId %>"
                               title="委托">
                                <%--<span class="label label-danger" style="float:right;" id="releaseCount" title="未处理的数量">0</span>--%>
                                <span data-localize="sidebar.nav.menu.SUBMENU" class="menufont"><em
                                        id="menuMyRelease"></em>委托</span>
                            </a>
                        </li>
                        <li class="menuli leafMenu" id="myReleaseOrders">
                            <a href="<%=appPath %>/facilitator/releaseOrderList.htm?ticketId=<%=ticketId %>"
                               title="我的订单">
                                <span data-localize="sidebar.nav.menu.SUBMENU" class="menufont"><em
                                        id="menuMyBusiness"></em>线下订单</span>
                            </a>
                        </li>
                        <li class="menuli leafMenu" id="myInquiryOrders">
                            <a href="<%=appPath %>/facilitator/inquiryOrderList.htm?ticketId=<%=ticketId %>"
                               title="我的订单">
                                <span data-localize="sidebar.nav.menu.SUBMENU" class="menufont"><em
                                        id="menuMyBusiness"></em>平台订单</span>
                            </a>
                        </li>
                        <%--<li class="menuli leafMenu" id="myTransfer">
                            <a href="<%=appPath %>/facilitator/transferList.htm?ticketId=<%=ticketId %>"
                               title="我的转移">
                                <span data-localize="sidebar.nav.menu.SUBMENU" class="menufont"><em
                                        id="myActivityIcon"></em>转移</span>
                            </a>
                        </li>--%>
                    </ul>
                </li>
                <li class="">
                    <a href="#myEnt" title="我的企业" data-toggle="collapse" class="menuli">
                        <em id="menuMyEnterprise"></em>
                        <span data-localize="sidebar.nav.menu.MENU">我的企业</span>
                    </a>
                    <ul id="myEnt" class="nav sidebar-subnav collapse">
                        <li class="menuli leafMenu" id="myEnterprise">
                            <a href="<%=appPath %>/facilitator/entInfo.htm?ticketId=<%=ticketId %>"
                               title="企业信息">
                                <span data-localize="sidebar.nav.menu.SUBMENU" class="menufont"><em
                                        id="enterpriseInfo"></em>企业信息</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="" id="myInfo">
                    <a href="#myinformation" title="个人中心" data-toggle="collapse" class="menuli">
                        <em id="usercenter"></em>
                        <span data-localize="sidebar.nav.menu.MENU">个人中心</span>
                    </a>
                    <ul id="myinformation" class="nav sidebar-subnav collapse">
                        <li class="menuli leafMenu" id="personalInformation">
                            <a href="<%=appPath %>/facilitator/userInfo.htm?ticketId=<%=ticketId %>"
                               title="个人信息 ">
                                <span data-localize="sidebar.nav.menu.SUBMENU" class="menufont"><em
                                        id="menuPersonalInformation"></em>个人信息</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- END sidebar nav-->
        </nav>
    </div>
    <!-- END Sidebar (left)-->
</aside>
<!-- offsidebar-->
<aside class="offsidebar hide">
    <!-- START Off Sidebar (right)-->
    <nav>
        <div role="tabpanel">
            <!-- Nav tabs-->
            <ul role="tablist" class="nav nav-tabs nav-justified">
                <li role="presentation" class="">
                    <a href="#app-settings" aria-controls="app-settings" role="tab"
                       data-toggle="tab">
                        <em class="icon-equalizer fa-lg"></em>
                    </a>
                </li>
                <li role="presentation">
                    <a href="#app-chat" aria-controls="app-chat" role="tab" data-toggle="tab">
                        <em class="icon-user fa-lg"></em>
                    </a>
                </li>
            </ul>
            <!-- Tab panes-->
            <div class="tab-content">
                <div id="app-settings" role="tabpanel" class="tab-pane fade in ">
                    <h3 class="text-center text-thin">Tab 1</h3>
                </div>
                <div id="app-chat" role="tabpanel" class="tab-pane fade">
                    <h3 class="text-center text-thin">Tab 2</h3>
                </div>
            </div>
        </div>
    </nav>
    <!-- END Off Sidebar (right)-->
</aside>
<script type="text/javascript">
  $(document).ready(function () {
    if("<%=menuId %>"=='#myIndex'){
        $("<%=menuId %>").addClass('active');
        $('#mywastecircle').addClass('in');
    }else if("<%=menuId %>"=='#flagshipStore'){
        $('#tmall').addClass('active');
        $('#mywastecircle').addClass('in');
    }else{
        $("<%=menuId %>").addClass('active');
        $("<%=menuId %>").parent().parent().addClass("active");
        $("<%=menuId %>").parent().addClass("in");
        $(".active .active a").addClass("liborderleft");
    }
//    window.globalInit = globalInit;
    <%--getEnterpriseBusinessService();--%>
    <%--getShoppingCardCount();--%>
    <%--getPublishCount();--%>

  });
  function getInquiryCount() {
      ajax({
          url:'/entInquiry/countUntreatedInquiry',
          data:{},
          success:function (result) {
              console.log(result);
              if(result.status==1&&result.data&&result.data!=0){
                  $('#releaseCount').html(result.data).show();
              }else{
                  $('#releaseCount').hide();
              }
          }
      })
  }
  function globalInit() {
    var dfd = $.Deferred();
    window.globalParam = {};
    getEnterpriseBusinessService().done(function () {
      dfd.resolve();
      if(window.globalParam.userStatus == "PASS"){
      }
    });
    return dfd.promise();
  }
  function getEnterpriseBusinessService() {
    var dfd = $.Deferred();
    $.ajax({
      type: "POST",
      dataType: 'json',
      async: false,
      url: "<%=appPath %>/wastecircle/getEnterpriseInfoByUserId.htm?ticketId=<%=ticketId %>",
      success: function (data) {
        if (data.status == 1 && data.data) {
          var rs = true;
          var enterpriseInfo = data.data.enterpriseInfo;
          var userInfo = data.data.userInfo;
          if(userInfo.userId){
              window.globalInit.userId=userInfo.userId;
          }
          var Buttons = "";
          if (enterpriseInfo != undefined) {
              if(enterpriseInfo.id){
                  window.globalInit.enterpriseId=enterpriseInfo.id;
              }
        	  if(enterpriseInfo.enterType != null){
        		  if (enterpriseInfo.enterType.code == 'PRODUCTION' && enterpriseInfo.enterStatus
        	                == "PASS" && userInfo.userStatus == "PASS") {
        	              for (var i = 0; i < enterpriseInfo.coopBusList.length; i++) {
        	                Buttons += ' <button  type="button" class="btn btn-labeled btn-release"   data-toggle="tooltip" onclick="initPublishMsg(this,\''
        	                    + enterpriseInfo.coopBusList[i].busUrl + '\',\''
        	                    + enterpriseInfo.coopBusList[i].busCode + '\')">' +
        	                    + ' <span class="btn-label1"><i class="fa1 fa-plus1"></i></span> ' +
        	                    '</button>';
        	              }
        	            }
        	  }
            
            if (enterpriseInfo.enterType == null) {
              $("#licenceManage").addClass("hidden");
              $("#identificationManage").addClass("hidden");
              $("#wasteManage").addClass("hidden");
            }else{
            	if (enterpriseInfo.enterType.code == "PRODUCTION") {
                    $("#licenceManage").addClass("hidden");
                    $("#identificationManage").addClass("hidden");
                    $("#myShoppingCart").addClass("hidden");
                    $("#myCZEnterprise").addClass("hidden");
                    $("#myActivity").addClass("hidden");
                    $('#guide_li').show();
                    getInquiryCount();
                  }

                  if (enterpriseInfo.enterType.code == "DISPOSITION") {
                    $("#wasteManage").addClass("hidden");
                    $("#identificationManage").addClass("hidden");
                    $("#myRelease").addClass("hidden");
                  }
                  if (enterpriseInfo.enterType.code == "IDENTIFICATION") {
                    $("#wasteManage").addClass("hidden");
                    $("#licenceManage").addClass("hidden");
                      $("#myRelease").addClass("hidden");
                  }
            }
            window.globalParam.enterType = enterpriseInfo.enterType?enterpriseInfo.enterType.code:'';
            window.globalParam.enterStatus = enterpriseInfo.enterStatus;
            window.globalParam.userStatus = userInfo.userStatus;
            window.globalParam.userRole = userInfo.userRole;
            $('#businessDiv').html(Buttons);
            if (enterpriseInfo.enterStatus != "PASS" || userInfo.userStatus != "PASS") {
              $("#wasteManage").addClass("hidden");
              $("#licenceManage").addClass("hidden");
              $("#identificationManage").addClass("hidden");
              if (!($("#myEnterprise").hasClass("active") || $("#personalInformation").hasClass(
                      "active")) && $("li.leafMenu.active").length > 0) {
                rs = false;
              }
              var reminder = "";
              if(enterpriseInfo.enterStatus == "SUBMIT"){
                reminder = "<p>你已申请创建该企业，正在审核中...</p>请等待管理员核实信息，核实完成后将发送短信通知，请保持手机畅通";
              }else if(enterpriseInfo.enterStatus == "PASS"){
                if(userInfo.userStatus =="SUBMIT"){
                  reminder = "<p>您已申请绑定该企业，正在审核中...</p>请等待企业管理员核实信息，核实完成后将发送短信通知，请保持手机畅通";
                }
              }
              window.globalParam.reminder = reminder;
            }
          } else {
            $("#licenceManage").addClass("hidden");
            $("#identificationManage").addClass("hidden");
            $("#wasteManage").addClass("hidden");
            var reminder = "";
            if(userInfo.userStatus =="QUIT"){
              reminder = "<p>创建企业的申请未通过验证</p>请核对信息后重新提交申请";
            }else if(userInfo.userStatus =="REJECT"){
              if(userInfo.userRole == "ADMIN"){
                reminder = "<p>创建企业的申请未通过验证</p>请核对信息后重新提交申请";
              }else{
                reminder = "<p>加入企业的申请未通过验证</p>请核对信息后重新提交申请";
              }
            }else if(userInfo.userStatus =="SUBMIT"){
                reminder = "<p>加入企业的申请未通过验证</p>请核对信息后重新提交申请";
            }else{
              reminder = "你还没加入企业，请添加企业";
            }
            window.globalParam.reminder = reminder;
            window.globalParam.userStatus = userInfo.userStatus;
            if (!($("#myEnterprise").hasClass("active") || $("#personalInformation").hasClass(
                    "active")) && $("li.leafMenu.active").length > 0) {
              rs = false;
            }
            $("#wasteManage").addClass("hidden");
            $("#licenceManage").addClass("hidden");
            $("#identificationManage").addClass("hidden");
          }
          if(rs){
            dfd.resolve();
          }else{
            dfd.reject();
            window.location.href = "<%=appPath %>/myenterprise/myEnterprise.htm?ticketId=<%=ticketId %>";
          }
        } else {
          dfd.reject();
        }
      },
      error: function () {
        dfd.reject();
      }
    });
    return dfd.promise();
  }

  function initPublishMsg(t, url) {
    if (url != "") {
      window.location = "<%=appPath%>" + url + "?ticketId=<%=ticketId %>";
    }
  }

</script>