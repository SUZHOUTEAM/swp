<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="企业管理员审核用户"/>
</jsp:include>
<%------- 结束导入页头信息 -------%>

<%------- 导入左侧信息 -------%>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#auditUserList" />
</jsp:include>
<%------- 结束导入左侧信息 -------%>

<!-- Main section-->
<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="<%=appPath %>/thirdparty/jquery/dist/jquery.js"></script>
<script type="text/javascript">
    //页面加载完成，把按钮放开
    $(document).ready( function () {
      var rs = window.globalInit();
      rs.done(function () {
        $("#auditUserPass").removeAttr("disabled");
        $("#auditUserRefuse").removeAttr("disabled");
        $("#detail").removeAttr("disabled");
      });

    });
    
    //企业管理员审核通过
    function pass(){
         var num =  $("input[type='checkbox']:checked").length;
         if(num > 0 && num <= 10){
             swal({  title: "申请通过",   
                 text: "请确认是否通过该申请",   
                 type: "warning",   
                 showCancelButton: true,   
                 confirmButtonColor: "#DD6B55",   
                 confirmButtonText: "通过",   
                 cancelButtonText: "取消",   
                 closeOnConfirm: false,   
                 closeOnCancel: false 
             },
             
             function(isConfirm){
                 if(isConfirm){
                     $("#auditUserPass").attr("disabled","disabled");
                     $("#auditUserRefuse").attr("disabled","disabled");
                     $("#detail").attr("disabled","disabled");
                     var itemId = "";
                     $("input:checkbox:checked").each(function(i){ 
                         itemId += $(this).val() + ","; 
                    }); 
                     $.post('auditUserPass.htm?etc='+new Date().getTime() + "&itemId="+itemId+"&ticketId=${ticketId}",function(result){
                         var obj = $.parseJSON(result);
                         if(obj.status == 1){
                             location="<%=appPath %>/myenterprise/auditUserList.htm?ticketId=${ticketId}";
                         }else{
                             $.notify("系统错误,请联系管理员!","danger");
                         }
                     });
                 }
             }); 
         }else{
             swal({  
                 title: "请至少选择一条进行操作",   
                 type: "warning",   
                 confirmButtonColor: "#3399FF",   
                 confirmButtonText: "确定",   
                 closeOnConfirm: false,   
                 closeOnCancel: false 
             });
         }
         
    }
    
    
   //企业管理员审核退回
     function refuse(){
         var num =  $("input[type='checkbox']:checked").length;
         if(num > 0 && num <= 10){
             swal({  title: "申请退回",   
                 text: "请确认是否退回该申请",   
                 type: "warning",   
                 showCancelButton: true,   
                 confirmButtonColor: "#DD6B55",   
                 confirmButtonText: "退回",   
                 cancelButtonText: "取消",   
                 closeOnConfirm: false,   
                 closeOnCancel: false 
             }, 
             
             function(isConfirm){
                 if(isConfirm){
                     $("#auditUserPass").attr("disabled","disabled");
                     $("#auditUserRefuse").attr("disabled","disabled");
                     $("#detail").attr("disabled","disabled");
                     var itemId = "";
                     $("input:checkbox:checked").each(function(i){ 
                         itemId += $(this).val() + ","; 
                    });
                     $.post('auditUserRefuse.htm?etc='+new Date().getTime() + "&itemId="+itemId+"&ticketId=${ticketId}",function(result){
                         var obj = $.parseJSON(result);
                         if(obj.status == 1){
                             location="<%=appPath %>/myenterprise/auditUserList.htm?ticketId=${ticketId}";
                             
                         }else{
                             $.notify("系统错误,请联系管理员!","danger");
                         }
                     });  
                 }
             });    
         }else{
             swal({  
                 title: "请至少选择一条进行操作",   
                 type: "warning",   
                 confirmButtonColor: "#3399FF",   
                 confirmButtonText: "确定",   
                 closeOnConfirm: false,   
                 closeOnCancel: false 
             });
         }
     }
</script>


<!-- Main section-->
<section>
   <!-- Page content-->
   <div class="content-wrapper">
      <div class="content-heading">
          <strong class="lead">企业管理员审核</strong> <small>申请加入用户审核</small>
      </div>
      <div class="row">
          <div class="col-md-12">
                <div class=" ">
                    <div class="btn-group">
                        <button type="button" id="auditUserPass" class="btn btn btn-primary"
                            onclick="pass()">申请通过</button>
                        <button type="button" id="auditUserRefuse" class="btn btn-default"
                            onclick="refuse()">申请退回</button>
                    </div>
                </div>
                <br/>
                <table class="table table-bordered table-condensed" style=" text-align: center">
                    <tr>
                        <td><span>选择</span></td>
                        <td><span>姓名</span></td>
                        <td><span>电话</span></td>
                        <td><span>性别</span></td>
                        <td><span>用户状态</span></td>
                        <td><span>用户类型</span></td>
                        <td><span>申请时间</span></td>
                    </tr>
                    <c:forEach var="it" items="${userList}" varStatus="status" begin="0" step="1">
                        <tr>
                            <td>
                                 <input type="checkbox" name="itemId" id="itemId" class="itemId" value="${it.id}">
                                 <%-- <input type="hidden" value="${it.enterpriseId}" id="enterpriseId" name="enterpriseId">
                                 <input type="hidden" value="${it.userId}" id="userId" name="userId"> --%>
                            </td>
                            <td>
                                <span>${it.chineseName}</span>
                            </td>
                            <td>
                                <span>${it.phoneNum}</span>
                            </td>
                            <td>
                                <span>${it.gender}</span>
                            </td>
                            <td>
                                <span>${it.eventType},</span>
                                <span>${it.eventStatus}</span>
                            </td>
                            <td>
                                <span>${it.userType }</span>
                            </td>
                            <td>
                                <span>${it.applicationTime }</span>
                            </td>
                        </tr>
                    </c:forEach>
            </table>
          </div>
      </div>
    </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>
