<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
   String appPath=request.getContextPath();
   String ticketId = (String)request.getAttribute("ticketId");
   String menuId = request.getParameter("menuId");
%>
<style type="text/css">
   .liborderleft {
      border-left: 3px solid #1171d1;
      margin-left: -3px;
   }
</style>
<!-- sidebar-->
<aside class="aside">
   <!-- START Sidebar (left)-->
   <div class="aside-inner">
      <nav data-sidebar-anyclick-close="" class="sidebar">
         <!-- START sidebar nav-->
         <ul class="nav">
            <!-- Iterates over all sidebar items-->
            <li class="nav-heading ">
               <span data-localize="sidebar.heading.HEADER">平台管理</span>
            </li>
            <li class=" ">
               <a href="#menuid" title="Menu" data-toggle="collapse">
                  <em class="icon-folder"></em>
                  <span data-localize="sidebar.nav.menu.MENU">基础数据管理</span>
               </a>
               <ul id="menuid" class="nav sidebar-subnav collapse">
                  <li class="" id="callingItem">
                     <a href="<%=appPath%>/calling/list.htm?ticketId=<%=ticketId %>" title="行业信息">
                        <span data-localize="sidebar.nav.menu.SUBMENU">行业信息</span>
                     </a>
                  </li>
                  <li class=" " id="wastetypeItem">
                     <a href="<%=appPath%>/WasteType/list.htm?ticketId=<%=ticketId %>" title="危废类别">
                        <span data-localize="sidebar.nav.menu.SUBMENU">危废类别</span>
                     </a>
                  </li>
                  <li class=" " id="wasteItem">
                     <a href="<%=appPath%>/Waste/list.htm?ticketId=<%=ticketId %>" title="危废名录">
                        <span data-localize="sidebar.nav.menu.SUBMENU">危废名录</span>
                     </a>
                  </li>
                  <li class=" " id="codeTypeItem">
                     <a href="<%=appPath%>/codeType/list.htm?ticketId=<%=ticketId %>" title="常用数据">
                        <span data-localize="sidebar.nav.menu.SUBMENU">常用数据</span>
                     </a>
                  </li>
               </ul>
            </li>
            <li class=" ">
               <a href="#menuid2" title="Menu" data-toggle="collapse">
                  <em class="icon-folder"></em>
                  <span data-localize="sidebar.nav.menu.MENU">权限管理</span>
               </a>
               <ul id="menuid2" class="nav sidebar-subnav collapse">
                  <li class=" " id="userItem">
                     <a href="<%=appPath%>/userManage/index.htm?ticketId=<%=ticketId %>" title="用户">
                        <span data-localize="sidebar.nav.menu.SUBMENU">用户</span>
                     </a>
                  </li>
                  <li class=" " id="enterpriseManagement">
                     <a href="<%=appPath%>/enterprisemanagement/list.htm?ticketId=<%=ticketId %>" title="企业管理">
                        <span data-localize="sidebar.nav.menu.SUBMENU">企业管理</span>
                     </a>
                  </li>
                  <li class=" " id="enterpriseIndexManagement">
                     <a href="<%=appPath%>/enterprisemanagement/enterpriseIndexInitList.htm?ticketId=<%=ticketId %>" title="企业索引管理">
                        <span data-localize="sidebar.nav.menu.SUBMENU">企业索引管理</span>
                     </a>
                  </li>
                  <li class=" " id="approveLicence">
                     <a href="<%=appPath%>/licenceApproved/list.htm?ticketId=<%=ticketId %>" title="审核许可证">
                        <span data-localize="sidebar.nav.menu.SUBMENU">审核许可证</span>
                     </a>
                  </li>
                  <li class=" " id="sysorgcomlist">
                     <a href="<%=appPath%>/sysorgcom/list.htm?ticketId=<%=ticketId %>" title="组织机构管理">
                        <span data-localize="sidebar.nav.menu.SUBMENU">组织机构管理</span>
                     </a>
                  </li>
                  <li class=" " id="removeUserEnt">
                     <a href="<%=appPath%>/removeUserEnt/index.htm?ticketId=<%=ticketId %>" title="删除用户和企业（开发用）">
                        <span data-localize="sidebar.nav.menu.SUBMENU">删除用户和企业</span>
                     </a>
                  </li>
                  <li class=" " id="contractRecordManager">
                     <a href="<%=appPath%>/facilitator/contractRecordList.htm.htm?ticketId=<%=ticketId %>" title="APP管理">
                        <span data-localize="sidebar.nav.menu.SUBMENU">合同备案</span>
                     </a>
                  </li>
                  <li class=" " id="appManager">
                     <a href="<%=appPath%>/appManagement/initListAppManagement.htm?ticketId=<%=ticketId %>" title="APP管理">
                        <span data-localize="sidebar.nav.menu.SUBMENU">APP管理</span>
                     </a>
                  </li>
               </ul>
            </li>
            <li class=" ">
               <a href="#menuid3" title="Menu" data-toggle="collapse">
                  <em class="icon-folder"></em>
                  <span data-localize="sidebar.nav.menu.MENU">消息管理</span>
               </a>
               <ul id="menuid3" class="nav sidebar-subnav collapse">
                  <li class=" " id="offlineMsg">
                     <a href="<%=appPath%>/userManage/offlineMsgList.htm?ticketId=<%=ticketId %>" title="离线消息">
                        <span data-localize="sidebar.nav.menu.SUBMENU">离线消息</span>
                     </a>
                  </li>
                  <li class=" " id="CustomerSuggest">
                     <a href="<%=appPath%>/userManage/suggestList.htm?ticketId=<%=ticketId %>" title="离线消息">
                        <span data-localize="sidebar.nav.menu.SUBMENU">用户建议</span>
                     </a>
                  </li>
               </ul>
            </li>
            <li class=" ">
               <a href="#menuid4" title="Menu" data-toggle="collapse">
                  <em class="icon-folder"></em>
                  <span data-localize="sidebar.nav.menu.MENU">活动管理</span>
               </a>
               <ul id="menuid4" class="nav sidebar-subnav collapse">
                  <li class=" " id="activityList">
                     <a href="<%=appPath%>/wasteActivity/list.htm?ticketId=<%=ticketId %>">
                        <span data-localize="sidebar.nav.menu.SUBMENU">活动列表</span>
                     </a>
                  </li>
                  <li class=" " id="enterActivity">
                     <a href="<%=appPath%>/wasteActivity/enterList.htm?ticketId=<%=ticketId %>">
                        <span data-localize="sidebar.nav.menu.SUBMENU">活动报名</span>
                     </a>
                  </li>
                  <li class=" " id="raiseActivity">
                     <a href="<%=appPath%>/wasteActivity/raiseList.htm?ticketId=<%=ticketId %>">
                        <span data-localize="sidebar.nav.menu.SUBMENU">众筹统计</span>
                     </a>
                  </li>
                  <li class=" " id="releaseBonusList">
                     <a href="<%=appPath%>/wasteActivity/releaseBonusList.htm?ticketId=<%=ticketId %>">
                        <span data-localize="sidebar.nav.menu.SUBMENU">红包口令</span>
                     </a>
                  </li>
               </ul>
            </li>
            <li class=" ">
               <a href="#menuid5" title="Menu" data-toggle="collapse">
                  <em class="icon-folder"></em>
                  <span data-localize="sidebar.nav.menu.MENU">运营管理</span>
               </a>
               <ul id="menuid5" class="nav sidebar-subnav collapse">
                  <li class=" " id="operationList">
                     <a href="<%=appPath%>/websiteOperation/operationList.htm?ticketId=<%=ticketId %>">
                        <span data-localize="sidebar.nav.menu.SUBMENU">运营计划</span>
                     </a>
                  </li>
                  <li class=" " id="homeSetting">
                     <a href="<%=appPath%>/appManagement/homeSetting.htm?ticketId=<%=ticketId %>">
                        <span data-localize="sidebar.nav.menu.SUBMENU">首页设置</span>
                     </a>
                  </li>
                  <li class=" " id="actionList">
                     <a href="<%=appPath%>/userManage/actionList.htm?ticketId=<%=ticketId %>">
                        <span data-localize="sidebar.nav.menu.SUBMENU">用户操作</span>
                     </a>
                  </li>
                  <li class=" " id="wasteInformation">
                     <a href="<%=appPath%>/wasteInformation/list.htm?ticketId=<%=ticketId %>">
                        <span data-localize="sidebar.nav.menu.SUBMENU">危废资讯</span>
                     </a>
                  </li>
               </ul>
            </li>
            <li class=" ">
               <a href="#serviceMange" title="Menu" data-toggle="collapse">
                  <em class="icon-folder"></em>
                  <span data-localize="sidebar.nav.menu.MENU">服务管理</span>
               </a>
               <ul id="serviceMange" class="nav sidebar-subnav collapse">
                  <li class="" id="bindServeList">
                     <a href="<%=appPath%>/entBindServe/intiBindServeList.htm?ticketId=<%=ticketId %>" title="绑定服务列表">
                        <span data-localize="sidebar.nav.menu.SUBMENU">购买服务列表</span>
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
            <li role="presentation" class="active">
               <a href="#app-settings" aria-controls="app-settings" role="tab" data-toggle="tab">
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
            <div id="app-settings" role="tabpanel" class="tab-pane fade in active">
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
        $("<%=menuId %>").addClass('active');
        $("<%=menuId %>").parent().addClass("in");
        $(".active >a").addClass("liborderleft");
        window.globalInit = globalInit;
    });
    function globalInit() {
        var dfd = $.Deferred();
        return  dfd.resolve();
    }
</script>