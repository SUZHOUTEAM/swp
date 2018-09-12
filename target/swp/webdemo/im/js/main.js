/**
 * 主要业务逻辑相关
 */
var userUID = readCookie("uid")
/**
 * 实例化
 * @see module/base/js
 */
var yunXin = new YX(userUID)
function getRecordHtml() {
    return $('#sessions .m-panel.j-session').html();
}
// $(document).ready(function () {
//     parent.window.
// })
