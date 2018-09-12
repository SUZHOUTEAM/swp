<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    String licenceId = (String)request.getAttribute("licenceId");
%>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/silviomoreto-bootstrap-select/dist/css/bootstrap-select.css">
<link rel="stylesheet" href="<%=appPath %>/thirdparty/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css">

<%------- 结束导入左侧信息 -------%>
<link rel="stylesheet" href="<%=appPath %>/css/licence/licence.css?1">
<style>
.form-horizontal .control-label {
    text-align:left;
}
</style>
<section>
<div class="table-grid table-grid-desktop">
    <div class="col col-md colPadding">
        <div class="panel panel-default">
            <div class="panel-heading">
                <form:form class="form-horizontal" modelAttribute="licenceItem" id="licenceItemForm" >
                    <form:hidden path="id"/><!-- operation_licence_item主键-->
                    <form:hidden path="licence_id" value="${licenceId}"/><!-- operation_licence_item的licence_id-->
                    <fieldset class="fieldsetcss" id="dispostionTypeFieldset">
                        <label class="col-md-12 control-label colcss">处置利用方式:</label>
                        <div class="col-md-4 colcss" id="disposition_type_div">
                            <form:select path="disposition_type" class="selectpicker form-control" data-live-search="true" data-trigger="change"
                                    data-parsley-required="required" data-parsley-required-message="请选择处置利用方式" data-parsley-errors-container="#disposition_type_div">
                                <option value=""></option>
                                 <c:forEach var="it" items="${dispositioTypeList}" varStatus="status" begin="0" step="1">
                                     <option value="${it.id}">${it.code}-${it.value}</option>
                                 </c:forEach>
                            </form:select>
                        </div>
                        <div class="col-md-4 colcss" id="approved_uantity_div">
                            <div class="input-group">
                                <form:input type="text" path="approved_quantity" placeholder="许可数量(单位：吨)" class="form-control h35" data-trigger="change" oninput="quantityValid(this)"
                                        data-parsley-required="required" data-parsley-required-message="请输入许可数量" data-parsley-errors-container="#approved_uantity_div"
                                        data-parsley-pattern="^([1-9]\d{0,12}|0)(\.\d{0,3})?$" data-parsley-pattern-message="许可数量的格式不正确"/>
                                <span class="input-group-addon">吨</span>
                            </div>
                        </div>
                        <div class="col-md-4 colcss">
                           <button type="button" id="saveDispositionItemBtn" class="btn btn-primary" onclick="saveDispositionItemClick(this)">保存</button>
                           <button type="button" id="cancelUpdateItemBtn" class="btn btn-default hidden" onclick="cancelClick()">取消</button>
                       </div>
                    </fieldset>
                    <fieldset class="fieldsetcss hidden" id="dispostionTypeDisplayFieldset">
                        <div>
                            <label class="control-label colcss"></label>
                        </div>
                        <button type="button" class="btn btn-default" onclick="updateDispostionType()">编辑</button>
                    </fieldset>
                </form:form>
            </div>
            <div class="panel-body hidden" id="dispositionItem">
               <form:form class="form-horizontal" modelAttribute="licenceItem" id="detailForm" >
                   <fieldset class="fieldsetcss" id="dispositionDetail">
                       <label class="col-md-1 control-label" style="width: 67px;padding: 0;">选择代码:</label>
                       <div class="col-md-8 colcss" id="wasteTypeField">
                           <select id="wasteTypeId" class="selectpicker form-control" style="width: 200px" data-live-search="true" data-trigger="change"
                                   data-parsley-required="required" data-parsley-required-message="请输入许可内容" data-parsley-errors-container="#wasteTypeField">
                               <option value=""></option>
                               <c:forEach var="it" items="${wasteTypeList}" varStatus="status" begin="0" step="1">
                                   <option value="${it.id}" class="${it.code}">${it.code} - ${it.description}</option>
                               </c:forEach>
                           </select>
                       </div>
                   </fieldset>
                   <fieldset class="fieldsetcss hidden" id="selectedWaste">
                   </fieldset>
                   <div class="col-md-12 colcss selectcss hidden" style="float: none">
                       <button type="button" id="saveDispositionDetailBtn" class="btn btn-primary" onclick="saveDispositionDetailsClick(this)">保存</button>
                   </div>
                   <div style="text-align: center;margin-top: 20px;border-top: 1px dotted #ccc;padding-top: 20px;" id="new-btn" class="hidden">
                       <button type="button" class="btn btn-primary" onclick="$('ul[role=menu] li:nth-child(2) a').click();">完成许可证创建并下一步</button>
                       <button type="button" class="btn btn-primary" onclick="addNewDispostionType()">新增处置利用方式</button>
                   </div>
               </form:form>
            </div>
        </div>
    </div>
    <div class="col col-md">
        <div class="panel panel-default">
            <div class="panel-heading">已保存的处置利用方式</div>
            <div class="panel-body col-right-height">
                <div id="dispostionDsiplayAccordion" role="tablist" aria-multiselectable="true" class="panel-group">
                </div>
            </div>
        </div>
    </div>
</div>
</section>
<script type="text/javascript" src="<%=appPath %>/thirdparty/silviomoreto-bootstrap-select/dist/js/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=appPath %>/thirdparty/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
<script src="<%=appPath %>/thirdparty/bootstrap-suggest-plugin-master/dist/tether.min.js"></script>
<script src="<%=appPath %>/thirdparty/bootstrap-suggest-plugin-master/dist/bootstrap-suggest.js"></script>
<script type="text/javascript" src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/thirdparty/layui/layer-v3.0.1/layer/layer.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    $('.selectpicker').selectpicker({
        noneSelectedText: '请选择',
        size:6
    });
    
    // 二位码绑定事件
    $('#wasteTypeId').change(function(e){
        $("#wasteTypeId").parsley().validate();
        if ($("#wasteTypeId").parsley().isValid()) {
            $("#selectedWaste").removeClass("hidden");
            wasteTypeEvent();
        }
    });
    
    dispositionItemDispaly ("<%=licenceId%>");
});

function saveDispositionItemClick(that){
    if (validateItem ()) {
        var $licenceItemForm = $("#licenceItemForm");
        var $selectedDispositionType = $('#disposition_type > option:selected');
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "/licence/isHasDispositionItem.htm?etc=" + new Date().getTime(),
            data: {
                "licenceId":$licenceItemForm.find("#licence_id").val(),
                "dispositionTypeId":$selectedDispositionType.val()
            },
            success: function (result, textStatus, jqXHR) {
                if (result.data.checkResult) {
                    layer.confirm('当前处置利用方式已经存在，是否更新？', {
                        btn: ['更新', '取消'],
                        shade: confirm_shade,
                        skin: confirm_skin
                    }, function (index) {
                        var itemId = result.data.checkResult;
                        var $licenceItemForm = $("#licenceItemForm");
                        $licenceItemForm.find("#id").val(itemId);
                        
                        saveOrUpdateDispositionItem (that);
                        layer.close(index);
                    }, function () {
                    });
                } else {
                    saveOrUpdateDispositionItem (that);
                }
            },complete: function (jqXHR, textStatus) {
            }
        }));
    }
}

function saveOrUpdateDispositionItem(that){
    if (validateItem ()) {
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "/licence/saveOrUpdateDispositionItem.htm?etc=" + new Date().getTime(),
            data: $("#licenceItemForm").serialize(),
            success: function (result, textStatus, jqXHR) {
                var itemId = result.data.licenceItemId;
                var $licenceItemForm = $("#licenceItemForm");
                $licenceItemForm.find("#id").val(itemId);
                cancelClick ();
                dispositionItemDispaly ("<%=licenceId%>");
            },complete: function (jqXHR, textStatus) {
            }
        }));
    }
}

function cancelClick(){
    var $dispostionTypeFieldset = $("#dispostionTypeFieldset");
    var $dispostionTypeDisplayFieldset = $("#dispostionTypeDisplayFieldset");
    var $dispositionItem = $("#dispositionItem");

    var dispostionTypeDisplayHtml = "<span>处置利用方式:</span>";
    dispostionTypeDisplayHtml += "<span>" + $dispostionTypeFieldset.find("#disposition_type option:selected").text() + "</span>";
    dispostionTypeDisplayHtml += "<span>" + parseNum($dispostionTypeFieldset.find("#approved_quantity").val()) + "吨</span>";
    $dispostionTypeDisplayFieldset.find("label").empty().append(dispostionTypeDisplayHtml);
    
    $dispostionTypeDisplayFieldset.removeClass("hidden");
    $dispostionTypeFieldset.addClass("hidden");
    $dispositionItem.removeClass("hidden");
}
//编辑
function updateDispostionType(){
    var $dispostionTypeFieldset = $("#dispostionTypeFieldset");
    var $dispostionTypeDisplayFieldset = $("#dispostionTypeDisplayFieldset");
    var $cancelUpdateItemBtn = $("#cancelUpdateItemBtn");

    $dispostionTypeDisplayFieldset.addClass("hidden");
    $dispostionTypeFieldset.removeClass("hidden");
    $cancelUpdateItemBtn.removeClass("hidden");
}
//新增处置利用方式
function addNewDispostionType(){
    var $itemId = $("#licenceItemForm #id");
    var $dispostionTypeFieldset = $("#dispostionTypeFieldset");
    var $dispostionTypeDisplayFieldset = $("#dispostionTypeDisplayFieldset");
    var $dispositionItem = $("#dispositionItem");
    var $selectedDispositionType = $('#disposition_type');
    var $approvedQuantity = $('#approved_quantity');
    var $cancelUpdateItemBtn = $("#cancelUpdateItemBtn");
    var $wasteTypeId = $("#wasteTypeId");
    var $selectedWasteDiv = $("#selectedWaste");
    
    $itemId.val("");
    $dispostionTypeDisplayFieldset.addClass("hidden");
    $dispostionTypeFieldset.removeClass("hidden");
    $dispositionItem.addClass("hidden");
    $selectedDispositionType.val("");
    $selectedDispositionType.selectpicker('refresh');
    $approvedQuantity.val("");
    $cancelUpdateItemBtn.addClass("hidden");
    $wasteTypeId.val("").selectpicker('refresh');
    $selectedWasteDiv.empty();
    $('#saveDispositionDetailBtn').parent().addClass("hidden");
    $("#new-btn").addClass("hidden");
}

//保存按钮可用设置
function validateItem(){
    var $dispositionType = $("#disposition_type");
    var $approvedQuantity = $("#approved_quantity");
    $approvedQuantity.parsley().validate();
    $dispositionType.parsley().validate();
    return $dispositionType.parsley().isValid() && 
           $approvedQuantity.parsley().isValid();
}

function saveDispositionDetailsClick(that){
    if (validateButton ()) {
        var detailList = dataToJsonObject ();
        if (detailList.length == 0) {
            return;
        }
        var berforeText = $(that).text();
        $(that).before("<i class='btn_loading'></i>").text("保存中...").parent().find("button").attr("disabled","disabled");
        $.page.ajax($.page.getAjaxSettings({
            async: false,
            type: "POST",
            dataType: "json",
            url: "/licence/saveDispositionDetails.htm?etc=" + new Date().getTime(),
            data: {
                "licenceDetailList":JSON.stringify(detailList)
            },
            success: function (result, textStatus, jqXHR) {
                $("#saveDispositionDetailBtn").popover({
                    trigger:'manual',
                    placement : 'right', 
                    html: 'true',
                    content : " <p style='color:#4f5669;font-size:16px;'><span class='fa fa-check-circle'  style='color:#58c558;font-size:26px;color:#58c558'></span>&nbsp保存成功，可以继续添加其他许可项或新增处置利用方式或点击下一步</p>",
                    animation: true
                 });
                 $("#saveDispositionDetailBtn").popover("show");
                 setTimeout('$("#saveDispositionDetailBtn").popover("hide");', 8000);
                 dispositionItemDispaly ("<%=licenceId%>");
            },complete: function (jqXHR, textStatus) {
                $(that).text(berforeText).parent().find("button").removeAttr("disabled");
                $(that).prev(".btn_loading").remove();
            }
        }));
    }
}

//许可数量验证事件
function quantityValid(that){
    $(that).parsley().validate();
}
//二位码绑定事件
function wasteTypeEvent() {
    var wasteTypeOptionId = $('#wasteTypeId > option:selected').val();
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licence/getWasteByWasteTypeId.htm?etc=" + new Date().getTime(),
        data: {
            "wasteTypeId": wasteTypeOptionId
        },
        success: function (result, textStatus, jqXHR) {
            var $selectedWasteDiv = $("#selectedWaste");
            var $saveDispositionDetailBtn = $("#saveDispositionDetailBtn").parent();
            $selectedWasteDiv.empty();
            if (result.data.wasteData.length > 0) {
                $saveDispositionDetailBtn.removeClass("hidden");
                $("#new-btn").removeClass("hidden");
                $selectedWasteDiv.append("<div class='col-lg-12 checkAllCss'></div>");
                $selectedWasteDiv.find("div").append('<label class="checkbox-inline c-checkbox"><input type="checkbox" class="checkAllItem"><span class="fa fa-check"></span></label>');
                $selectedWasteDiv.find("div").append('<label class="control-label">全选</label>');
                for (var index in result.data.wasteData) {
                    $selectedWasteDiv.append(creatWasteOption(result.data.wasteData[index],wasteTypeOptionId).prop("outerHTML"));
                }
                
                initCheckAllItem ();
                initCheckItemFocus ();
            } else {
                $saveDispositionDetailBtn.addClass("hidden");
            }
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                
            }
        }
    }));
}

function addWasteOption(that) {
    var removeButton = "<a href='javascript:;' class='removeBtn' title='删除危废名称' onclick='removeWasteOption(this)'>" +"<em class='fa fa-times'></em></a> ";
    var $wastePanel = $(that).parents(".wastecss");
    var $newWastePanel = $($wastePanel.prop("outerHTML"));
    $newWastePanel.find(".wasteNameId").val("");
    if($newWastePanel.find(".fa.fa-times").length <= 0) {
        $newWastePanel.find(".pluscopy").before(removeButton);
    }
    
    $wastePanel.after($newWastePanel.prop("outerHTML"));
    initCheckAllItem ();
    initCheckItemFocus ();
}
function removeWasteOption(that) {
    var $wastePanel = $(that).parents(".wastecss");
    $wastePanel.remove();
}

function initCheckAllItem(){
    /*“全选/反选”复选框*/  
    var $checkAll = $('#selectedWaste .checkAllItem');
    var $checkItem = $('#selectedWaste .checkItem');
    $checkAll.click(function(event){  
        /*将所有行的选中状态设成全选框的选中状态*/  
        $checkItem.prop('checked',$(this).prop('checked')); 
    });  
}

function initCheckItemFocus(){
    var $wasteNameInput = $('#selectedWaste .wastecss .input-group input');
    $wasteNameInput.focus(function(){
        initWsteNameBsSuggest(this);
    });  
}

//初始化危废名称搜索框
function initWsteNameBsSuggest(that){
    $('.wastecss .input-group input').bsSuggest("destroy");
    var wasteId = $(that).parent().siblings(".wasteId").val();
    $(that).bsSuggest('init', {
        url: "<%=appPath%>/licence/getWasteNamesByWasteId.htm?ticketId=<%=ticketId %>&wasteId="+ wasteId + "&keyword=",
        ignorecase: true,
        showHeader: false,
        showBtn: false,
        delayUntilKeyup: true,
        inputWarnColor:'#FFFFFF',
        getDataMethod: "url",
        searchFields: ["wasteName"],
        idField: "wasteNameId",
        keyField: "wasteName",
        effectiveFields:["wasteName"],
    }).on('onDataRequestSuccess', function (e, result) {
        
    }).on('onSetSelectValue', function (e, keyword, data) {
        $(that).parent().siblings(".wasteNameId").val(data.wasteNameId);
    }).on('onUnsetSelectValue', function () {
        
    });
}

function creatWasteOption(wasteVo,wasteTypeId){
    var addButton = "<a href='javascript:;' class='pluscopy' title='新增一个危废名称' onclick='addWasteOption(this)'>" +"<em class='fa fa-plus'></em></a> ";
    var $panel = $("<div class='wastecss'></div>");
//    $panel.append(addButton);
    $panel.append('<label class="checkbox-inline c-checkbox"><input type="checkbox" class="checkItem" value="'+ wasteVo.id +'">' + '<span class="fa fa-check"></span></label>');
    $panel.append('<label class="control-label">' + wasteVo.code + '</label>');
    $panel.append('<input type="hidden" class="wasteTypeId" value="'+ wasteTypeId +'"/>');
    $panel.append('<input type="hidden" class="wasteId" value="'+ wasteVo.id +'"/>');
    $panel.append('<input type="hidden" class="wasteNameId" value=""/>');
//    $panel.append('<div class="input-group float-left"><div>');
//    $panel.find(".input-group").append('<input type="text" class="form-control" placeholder="危废名称" oninput="clearWasteName(this)"/>');
//    $panel.find(".input-group").append('<ul class="dropdown-menu zgl" role="menu"></ul>');
    return $panel;
}

function clearWasteName(that){
    $(that).parent().siblings(".wasteNameId").val("");
}
// 创建需要提交的数据json对象
function dataToJsonObject(){
    var detailList = [];
    var $checkItem = $('#selectedWaste .checkItem:checked');
    var $licenceItemForm = $("#licenceItemForm");
    var licence_id = $licenceItemForm.find("#licence_id").val();
    var licence_item_id = $licenceItemForm.find("#id").val();
    $checkItem.each(function(){
        var detail = {};
        var $wastePanel = $(this).parents(".wastecss");
        
        detail.licence_id = licence_id;
        detail.operation_item_id = licence_item_id;
        detail.waste_type = $wastePanel.find(".wasteTypeId").val();
        detail.waste_id = $wastePanel.find(".wasteId").val();
        detail.waste_name_id = $wastePanel.find(".wasteNameId").val();
        detail.excuted_quantity = '0';
        detail.waste_name = $wastePanel.find(".input-group input").val();
        if (cleanRepeatData (detailList, detail)) {
            detailList.push(detail);
        }
    });
    
    return detailList;
}

function cleanRepeatData(detailList,detail){
    var result = true;//没有重复的
    if (isNotBlank (detail.licence_id) && isNotBlank (detail.operation_item_id) && isNotBlank (detail.waste_type) &&
        isNotBlank (detail.waste_id)) {
        detailList.forEach(function dataToFormq(value,index,arr){
            if (value.waste_type == detail.waste_type && value.waste_id == detail.waste_id) {
                if (value.waste_name_id == detail.waste_name_id) {
                    if (detail.waste_name_id == "" ) {
                        if (value.waste_name == detail.waste_name ) {
                            result = false;
                        }
                    } else {
                        result = false;
                    }
                } 
            }
        });
    } else {
        result = false;
    }
    
    return result;
}

function removeDispostionItem(that){
    var itemId = $(that).parents(".panel-heading").attr("id");
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licence/removeDispostionItem.htm?etc=" + new Date().getTime(),
        data: {
            "itemId": itemId,
            "licenceId": "<%=licenceId%>"
        },
        success: function (result, textStatus, jqXHR) {
            dispositionItemDispaly ("<%=licenceId%>");
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                
            }
        }
    }));
}

// 保存按钮可用设置
function validateButton(){
    var $dispositionTyp = $("#disposition_type");
    var $approvedQuantity = $("#approved_quantity");
    var $wasteType = $("#wasteTypeId");
    var $checkItem = $('#selectedWaste .checkItem:checked');
    var $dispostionTypeFieldset = $('#dispostionTypeFieldset');
    
    if (!$dispostionTypeFieldset.hasClass("hidden")) {
        $.notify("请先保存处置利用方式！", {"status": "info"});
        return false;
    }
    if ($checkItem.length == 0) {
        $.notify("尚未选择许可明细！", {"status": "info"});
        return false;
    }
    $dispositionTyp.parsley().validate();
    $approvedQuantity.parsley().validate();
    $wasteType.parsley().validate();
    return $dispositionTyp.parsley().isValid() && 
           $approvedQuantity.parsley().isValid() && $wasteType.parsley().isValid();
}

//获取处置方式以便于在右边显示
function dispositionItemDispaly(licenceId){
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licence/getDispositionItems.htm?etc=" + new Date().getTime(),
        data: {
            "licenceId":licenceId
        },
        success: function (result, textStatus, jqXHR) {
            var $dsiplayAccordion = $("#dispostionDsiplayAccordion");
            if (result.data.itemList.length > 0) {
                $dsiplayAccordion.empty();
                for (var index in result.data.itemList) {
                    $dsiplayAccordion.append(creatDispostionDsiplay(result.data.itemList[index]).prop("outerHTML"));
                }
                
                // 当前的处置方式下,默认展开
                var $selectedDispositionType = $('#disposition_type > option:selected');
                if ($selectedDispositionType.val() != "") {
                	var $displayItemHead = $(".displayItem").find("[href='#" + $selectedDispositionType.val() + "']");
	                if ($displayItemHead.length > 0) {
	                	$displayItemHead.attr("aria-expanded","true").removeClass("collapsed");
	                	var $displayItemBodyP = $displayItemHead.parents(".panel-heading").next("div");
	                	$displayItemBodyP.addClass("in").attr("aria-expanded","true").css("height","auto");
	                }
            	}
                loadingLicenceDetails();
            }
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                
            }
        }
    }));
}

// 创建已保存成功的处置方式
function creatDispostionDsiplay(itemVo){
    var $panelItem = $("<div class='panel panel-default displayItem'></div>");
    var $panelheading = $("<div id='"+ itemVo.itemId + "' role='tab' class='panel-heading'><span class='hidden licenceId'>" + itemVo.licenceId + "</span></div>");
    var $panelheadingtext = $("<a data-toggle='collapse' data-parent='#dispostionDsiplayAccordion' href='#" +itemVo.dispositionTypeId + "' aria-expanded='false' class='collapsed'></a>");
    $panelheadingtext.append("<span class='paddingleft'>" + itemVo.dispositionType + "</span>");
    $panelheadingtext.append("<span class='paddingleft'>" + "年处置量：" + itemVo.approved_quantity + "吨</span>");
    var $h4 = $("<span></span>").append($panelheadingtext.prop("outerHTML"));
    $h4.append("<span class='pull-right'><em class='fa fa-times clickable' onclick='removeDispostionItem(this)'></em></span>");
    $panelheading.append($h4.prop("outerHTML"));
    
    var $panelbodyP = $("<div id='"+ itemVo.dispositionTypeId + "' role='tabpanel' aria-labelledby='" + itemVo.itemId + "' class='panel-collapse collapse' aria-expanded='false' style='height: 0px;'></div>");
    var $panelbody = $("<div class='panel-body'></div>");
    
    $panelbodyP.append($panelbody.prop("outerHTML"));
    $panelItem.append($panelheading.prop("outerHTML")).append($panelbodyP.prop("outerHTML"));
    
    return $panelItem;
}

function loadingLicenceDetails(){
    $displayItem = $(".col-right-height .panel-default.displayItem");
    $displayItem.each(function(){
        loadingLicenceDetailData (this);
    });
}
function loadingLicenceDetailData(that){
    var licenceId = $(that).find(".panel-heading .licenceId").text();
    var licenceItemId = $(that).find(".panel-heading").attr("id");
    $.page.ajax($.page.getAjaxSettings({
        async: true,
        type: "POST",
        dataType: "json",
        url: "/licence/getDispositionDetails.htm?etc=" + new Date().getTime(),
        data: {
            "licenceId":licenceId,
            "licenceItemId":licenceItemId
        },
        success: function (result, textStatus, jqXHR) {
            var $panel_body = $(that).find(".panel-body");
            var detailList = result.data.detailList;
            $panel_body.empty();
            if (detailList.length > 0) {
                var str='';
                var obj1={},obj2={};
                for (var index in detailList) {
                    var item=detailList[index];
                    var key=item.wasteTypeCode+'/'+item.wasteTypeValue;
                    if(obj1[key]){
                        obj1[key]+=','+item.wasteCode;
                    }else{
                        obj1[key]=item.wasteCode;
                    }
                    if(obj2[key]){
                        obj2[key].push(item);
                    }else{
                        obj2[key]=[item];
                    }
                }
                console.log(obj1);
                console.log(obj2);
                var list={};
                for(var code in obj1){
                    var obj={};
                    obj['str']=obj1[code];
                    obj['list']=obj2[code];
                    list[code]=obj;
                }
                console.log('list==',list);
                for(var key in list){
                    $panel_body.append('<div class="waste-item"><div class="dList"><b>'+key+'</b>：('+list[key]['str']+')<a href="javascript:;" onclick="showEdit(this)">编辑</a></div>' +
                        '<div class="eList"><b>'+key+'：<a href="javascript:;" style="float:right" onclick="hideEdit(this)">完成</a></b>'+getHtmlByList(list[key]['list'])+'</div></div>');
                }
                initWastePanelFocus (that);
            }
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                
            }
        }
    }));
}
function getHtmlByList(list) {
    var str='';
    for(var i in list){
        var detailExtend=list[i];
        str+='<div class="col-lg-4 wastecss">' +
            '<a href="javascript:;" class="removeBtn" title="删除此许可明细" onclick="removeDetailClick(this)">' +
            '<em class="fa fa-times"></em></a> ' +
            '<a href="javascript:;" class="pluscopy" title="修改危废名称" onclick="editDetail(this)">' +
            '<em class="fa fa-edit"></em></a> <label class="control-label">'+detailExtend.wasteTypeCode + "/" + detailExtend.wasteCode+'</label>' +
            '<input type="hidden" class="licenceId" value="'+ detailExtend.itemId +'">' +
            '<input type="hidden" class="itemId" value="'+ detailExtend.itemId +'">' +
            '<input type="hidden" class="detailId" value="'+ detailExtend.detailId +'">' +
            '<input type="hidden" class="wasteTypeId" value="'+ detailExtend.wasteTypeId +'">' +
            '<input type="hidden" class="wasteId" value="'+ detailExtend.wasteId +'">' +
            '<input type="hidden" class="wasteNameId_bk" value="'+ detailExtend.wasteNameId +'">' +
            '<input type="hidden" class="wasteNameId" value="'+ detailExtend.wasteNameId +'">' +
            '<div class="input-group float-left"><div></div>' +
            '<input type="text" class="form-control" placeholder="无危废名称，添加请编辑" oninput="clearWasteName(this)" value="'+ detailExtend.wasteName +'" disabled="">' +
            '<ul class="dropdown-menu zgl" role="menu"></ul></div>' +
            '</div>';
    }
    return str;
}
function showEdit(that) {
    $(that).parent().hide().next().show();
}
function hideEdit(that) {
    $(that).parent().parent().hide().prev().show();
}

function creatWasteDisplayOption(detailExtend){
    var removeButton = "<a href='javascript:;' class='removeBtn' title='删除此许可明细' onclick='removeDetailClick(this)'>" +"<em class='fa fa-times'></em></a> ";
    var editButton = "<a href='javascript:;' class='pluscopy' title='修改危废名称' onclick='editDetail(this)'>" +"<em class='fa fa-edit'></em></a> ";
    var $panel = $("<div class='col-lg-4 wastecss'></div>");
    $panel.append(removeButton);
    $panel.append(editButton);
    $panel.append('<label class="control-label">' + detailExtend.wasteTypeCode + "/" + detailExtend.wasteCode + '</label>');
    $panel.append('<input type="hidden" class="licenceId" value="'+ detailExtend.licenceId +'"/>');
    $panel.append('<input type="hidden" class="itemId" value="'+ detailExtend.itemId +'"/>');
    $panel.append('<input type="hidden" class="detailId" value="'+ detailExtend.detailId +'"/>');
    $panel.append('<input type="hidden" class="wasteTypeId" value="'+ detailExtend.wasteTypeId +'"/>');
    $panel.append('<input type="hidden" class="wasteId" value="'+ detailExtend.wasteId +'"/>');
    $panel.append('<input type="hidden" class="wasteNameId_bk" value="'+ detailExtend.wasteNameId +'"/>');
    $panel.append('<input type="hidden" class="wasteNameId" value="'+ detailExtend.wasteNameId +'"/>');
    $panel.append('<div class="input-group float-left"><div>');
    $panel.find(".input-group").append('<input type="text" class="form-control" placeholder="无危废名称，添加请编辑" oninput="clearWasteName(this)" value="'+ detailExtend.wasteName +'"/>');
    $panel.find(".input-group").append('<ul class="dropdown-menu zgl" role="menu"></ul>');
    $panel.find(".input-group input").prop("disabled",true);
    return $panel;
}

function initWastePanelFocus(that){
    var $wasteNameInput = $(that).find('.wastecss .input-group input');
    $wasteNameInput.focus(function(){
        initWsteNameBsSuggest(this);
    });  
}

function removeDetailClick(that) {
    var $wastePanel = $(that).parents(".wastecss");
    var $em = $(that).find("em");
    var $fa_edit_em = $(that).siblings(".pluscopy").find("em");
    
    if ($fa_edit_em.hasClass("fa-edit")) {//删除此危废许可明细
        removeDetail (that);
    } else if ($fa_edit_em.hasClass("fa-check")) {// 取消修改危废名称
        $wastePanel.find(".input-group input").prop("disabled",true);
        $fa_edit_em.removeClass("fa-check").addClass("fa-edit");
        $wastePanel.find(".wasteNameId").val($wastePanel.find(".wasteNameId_bk").val());
        $em.prop("title","删除此许可明细");
    }
}
function editDetail(that) {
    var $wastePanel = $(that).parents(".wastecss");
    var $em = $(that).find("em");
    var $fa_times_em = $(that).siblings(".removeBtn").find("em");
    
    if ($em.hasClass("fa-edit")) {
        $wastePanel.find(".input-group input").prop("disabled",false);
        $em.removeClass("fa-edit").addClass("fa-check");
        $fa_times_em.prop("title","取消修改危废名称");
    } else if ($em.hasClass("fa-check")) {
        updateWasteName (that);//更新危废名称
        $em.removeClass("fa-check").addClass("fa-edit");
        $fa_times_em.prop("title","删除此许可明细");
    }
}

function removeDetail(that) {
    var $wastePanel = $(that).parents(".wastecss");
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licence/removeDetail.htm?etc=" + new Date().getTime(),
        data: {
            "licenceId":$wastePanel.find(".licenceId").val(),
            "detailId":$wastePanel.find(".detailId").val()
        },
        success: function (result, textStatus, jqXHR) {
            $wastePanel.remove();
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                
            }
        }
    }));
}
function updateWasteName(that) {
    var $wastePanel = $(that).parents(".wastecss");
    $.page.ajax($.page.getAjaxSettings({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/licence/updateWasteName.htm?etc=" + new Date().getTime(),
        data: {
            "licenceId":$wastePanel.find(".licenceId").val(),
            "detailId":$wastePanel.find(".detailId").val(),
            "wasteId":$wastePanel.find(".wasteId").val(),
            "wasteNameId":$wastePanel.find(".wasteNameId").val(),
            "wasteName":$wastePanel.find(".input-group input").val()
        },
        success: function (result, textStatus, jqXHR) {
            var wasteNameId = result.data.wasteNameId;
            $wastePanel.find(".wasteNameId").val(wasteNameId);
            $wastePanel.find(".wasteNameId_bk").val(wasteNameId);
            $wastePanel.find(".input-group input").prop("disabled",true);
            $wastePanel.find(".input-group input").bsSuggest("destroy");
        },complete: function (jqXHR, textStatus) {
            var result = jqXHR.responseJSON;
            if (result.status != 1) {
                
            }
        }
    }));
}

function isNotBlank(str) {
    var result = true;
    if (!str) {
        result = false;
    }
    
    if (str == 'undefined') {
        result = false;
    }
    if (str == '') {
        result = false;
    }
    
    return result;
}

</script>
