<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%
    String appPath = request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
	<jsp:param name="title" value="我的收藏" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
	<jsp:param name="menuId" value="#myFollow" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/css/wastecircle/index.css">
<!-- 我的易废圈所使用的css文件 -->
<section>
	<div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的易废圈</span>
        	<span class="el-breadcrumb__separator">/</span>
        </span>
		<span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的收藏</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
	</div>
	<!--<div id="msg">已成功加入购物车</div>-->
	<div class="message-container">
		<div class="messages">
			<div class="list" style="margin-top: 20px">

			</div>
			<div class="loading"><img src="<%=appPath %>/main/pc/img/load.gif"></div>
		</div>
	</div>
</section>
<script type="text/javascript">
	var appPath = '<%=appPath%>';
</script>
<script type="text/javascript" src="<%=appPath%>/app/js/constants.js"></script>
<script src="<%=appPath%>/app/js/constants.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var rs = window.globalInit();
        rs.done(function () {
            initValue();
        });
    })
	function initValue() {
        var searchCondition={"entId":"","entName":"","codeWaste":"","area":"","isable":1,"iscare":0,"isdistance":1,"favorited":true};
        ajax({
            url:'/wastecircle/initWasteCircleData.htm',
            data:{
                ticketId:'<%=ticketId%>',
                searchCondition:JSON.stringify(searchCondition),
                pageIndex:1,
                pageSize:100
            },
            success:function (data) {
                if(data.status==1){
                    var arr=data.data.messageList;
                    var str='';
                    for (var i = 0; i < arr.length; i++) {
                        var obj = arr[i];
                        var qipao = '../main/pc/img/qipao.jpg';
                        var imgurl = !obj.imgBusinessCode ? '../main/pc/img/company_default.png' : (IMG_VIEW_URL + '&businessCode=' + obj.imgBusinessCode + '&fileID='
                        + obj.imgFileId);
                        str += '<div class="item_container" id="chanfei_' + obj.enterId
                            + '">' +
                            '<div class="item">' +
                            '<div class="item_logo"><img src="' + imgurl + '"></div>' +
                            '<div class="item_message">' +
                            '<p class="item_info">' +
                            '<span class="item_name" onclick="getEnterpriseDetail(\''
                            + obj.enterId + '\')">' + obj.enterName + '</span>'
                            + parseEnterType(obj.enterType) +
                            '<span class="item_xukezheng" style="margin-right: 18px"><i class="xukezheng_icon"></i>许可证有效期：'
                            + obj.licenceAbledDate
                            + '</span>' +
                            '</p>' +
                            '<p class="item_address"><i class="publisharea"></i>'
                            + obj.area + '<span>（距离' + (obj.distance || '--')
                            + '）</span></p>' +
                            '<p class="item_weifei"><i class="weifei_icon"></i>与你可匹配的危废有<span>'
                            + obj.processableCount + '</span>类</p>'
                            + parseDetailReleaseList(obj.entWasteModels)
							+'<p class="item_buy"><a class="qipao_logo" href="javascript:;" style="margin-right: 38px" onclick="getEnterpriseContacts(\''
                            + obj.enterId + '\',\''+obj.enterName+'\')" ><img src="'
                            + qipao
                            + '">联系TA</a><a class="favorite_logo' + (obj.favorited ? ' favorited' : '') + '" href="javascript:;" onclick="favorite(this,\''
                            + obj.enterId + '\')" ><i class="favorite"></i><span>' + (obj.favorited ? '已收藏' : '收藏') + '</span></a></p>'
                            +
                            '</div>' +
                            '</div>' +
                            '<div class="chanfei_list" id="' + obj.busCode + '_'
                            + obj.enterBusId + '"></div>' +
                            '</div>';
                    }
                    $('.list').html(str);
                }
            }
        });
    }
    function parseEnterType(parseEnterType) {
        var className = parseEnterType.code;
        var classText = parseEnterType.value;
        var classNameStr = ' ' + className.toLowerCase() + '-badge';
        var str = '<span class="enterprise-type-badge ' + className + classNameStr
            + '">' + classText + '</span>';
        return str;
    }
    function  parseDetailReleaseList (releaseWasteDetails) {
        var str = '';
        str+='<div class="capacityDetailRelease">';
        var map = {},
            dest = [];
        for(var i = 0; i < releaseWasteDetails.length; i++){
            var ai = releaseWasteDetails[i];
            if(!map[ai.wasteCode]){
                dest.push({
                    wasteCode: ai.wasteCode,
                    data: [ai.wasteName]
                });
                map[ai.wasteCode] = ai;
            }else{
                for(var j = 0; j < dest.length; j++){
                    var dj = dest[j];
                    if(dj.wasteCode == ai.wasteCode){
                        dj.data.push(ai.wasteName);
                        break;
                    }
                }
            }
        }

        for(var i=0;i<dest.length;i++){
            str+='<div class="detailRelease"><span>'+dest[i]['wasteCode']+'</span>';
            for(var k=0;k<dest[i].data.length;k++) {
                str+='<span>'+dest[i].data[k]+'</span>';
            }
            str+= "</div>";
        }


        str+='</div>';
        return str;
    }
    function favorite(obj,entId) {
        var url=$(obj).hasClass('favorited')?'/entFavorite/cancelEntFavorite':'/entFavorite/addEntFavorite  ';
        ajax({
            url:url,
            data:{
                entId:window.globalInit.enterpriseId,
                referenceId:entId,
                favoriteType:'ENTID'
            },
            success:function (data) {
                if(data.status==1&&data.data){
                    if($(obj).hasClass('favorited')){
                        $.notify('取消收藏成功',{status:'success',timeout:1000});
                        $(obj).parent().parent().parent().parent().remove();
                    }else{
                        $.notify('收藏成功',{status:'success',timeout:1000});
                        $(obj).addClass('favorited');
                        $(obj).find('span').html('已收藏');
                    }
                }
            }
        })
    }
    function getEnterpriseDetail(enterId) {
        window.location = "<%=appPath%>/enterprise/czDetail.htm?ticketId=<%=ticketId%>&enterpriseId=" + enterId + "&breadcrumbName=我的收藏";
    }
</script>