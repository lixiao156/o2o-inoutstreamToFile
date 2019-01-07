/**
 * 需要添加一个img控件
 * 获取验证码
 * @param img
 */
function changeVerifyCode(img) {
    //工具用于生成四位的随机数字
img.src = "../Kaptcha?"+Math.floor(Math.random()*100);
}

/**
 * 用于获取shopId参数获取店铺信息
 * @param name
 * @returns {string}
 */
function getQueryString (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}