<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="管理员验证企业"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#auditEnterpriseList" />
</jsp:include>

<!-- Main section-->
<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="<%=appPath %>/thirdparty/jquery/dist/jquery.js"></script>
<!-- Main section-->
<section>
   <!-- Page content-->
    <div class="content-wrapper">
        <div class="content-heading">
			<strong class="lead">平台管理员验证</strong>
			<small>新加入企业验证</small>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class=" ">
                    <div class="btn-group">
                        <button type="button" id="detail" class="btn btn-default" onclick="detail11(this)">查看</button>
                        <button type="button" id="auditEnterprisePass" class="btn btn btn-primary" onclick="pass(this)">验证通过</button>
                        <button type="button" id="auditEnterpriseRefuse" class="btn btn-default" onclick="refuse(this)">验证退回</button>
                    </div>
                </div>
                <br/>
                <table class="table table-bordered table-condensed" style=" text-align: center">
                    <tr>
                        <td><span>选择</span></td>
                        <td><span>企业名称</span></td>
                        <td><span>企业状态</span></td>
                        <td><span>企业类型</span></td>
                        <td><span>申请时间</span></td>
                    </tr>
                    <c:forEach var="it" items="${enterpriseList}" varStatus="status" begin="0" step="1">
                        <tr>
                            <td>
                                <input type="checkbox" name="enterpriseId" id="enterpriseId" class="enterpriseId" value="${it.enterpriseId}">
                            </td>
                            <td>
                                <span>${it.enpName}</span>
                            </td>
                            <td>
                                <span>${it.eventType},</span>
                                <span>${it.eventStatus}</span>
                            </td>
                            <td>
                                <span>${it.enterpriseType }</span>
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

<jsp:include page="/common/bottom.jsp" flush="true"/>
<script type="text/javascript">
//页面加载完成，把按钮放开
$(document).ready( function () {
    
});

//查看企业详情
function detail11(that){
    var num =  $("input[type='checkbox']:checked").length;
    if(num == 1){
         $("#auditEnterprisePass").attr("disabled","disabled");
         $("#auditEnterpriseRefuse").attr("disabled","disabled");
         $("#detail").attr("disabled","disabled");
         var enterpriseId = $("input:checkbox:checked").val();
         location="<%=appPath %>/myenterprise/viewEnterpriseDetail.htm?ticketId=<%=ticketId %>&onlyViewFlg=false&etc="+new Date().getTime() + "&enterpriseId=" + enterpriseId;
    }else{
        swal({  
            title: "请选择一条进行查看",   
            type: "warning",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    }
}
    
function pass(that){
     var num =  $("input[type='checkbox']:checked").length;
     if(num > 0){
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
                 $("#auditEnterprisePass").attr("disabled","disabled");
                 $("#auditEnterpriseRefuse").attr("disabled","disabled");
                 $("#detail").attr("disabled","disabled");
                 var enterpriseId = "";
                 $("input:checkbox:checked").each(function(i){ 
                     enterpriseId += $(this).val() + ","; 
                }); 
                 
                 $.post('<%=appPath %>/myenterprise/auditEnterprisePass.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime() + "&enterpriseId=" + enterpriseId,function(result){
                     var obj = $.parseJSON(result);
                     if(obj.status == 1){
                         location="<%=appPath %>/myenterprise/auditEnterpriseList.htm?ticketId=${ticketId}";
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
    
//平台管理员审核企业退回
function refuse(that){
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
                $("#auditEnterprisePass").attr("disabled","disabled");
                $("#auditEnterpriseRefuse").attr("disabled","disabled");
                $("#detail").attr("disabled","disabled");
                var enterpriseId = "";
                $("input:checkbox:checked").each(function(i){ 
                    enterpriseId += $(this).val() + ","; 
               });
               $.post('<%=appPath %>/myenterprise/auditEnterpriseRefuse.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime() + "&enterpriseId="+enterpriseId,function(result){
                   var obj = $.parseJSON(result);
                   if(obj.status == 1){
                       location="<%=appPath %>/myenterprise/auditEnterpriseList.htm?ticketId=<%=ticketId %>";
                       
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
