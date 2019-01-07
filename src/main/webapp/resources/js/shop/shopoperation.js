/**
 * js两个功能
 * 1.从后台获取店铺分类以及区域的等信息 将其填充到前端的Html文件里面去
 * 2.将表单的信息获取到，传递给后台，用其注册店铺
 * $(function () 属于jQuery
 */
$(function () {

    var shopId = getQueryString('shopId');
    var isEdit = shopId ? true : false;
    /*获取店铺信息及分类路径*/
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    /*注册店铺这里与web包中manager类中的url对应*/
    var registerShopUrl = '/o2o/shopadmin/registershop';
    /*根据店铺的id获取店铺的信息  shopId根据URL地址获取*/
    var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
    /*修改店铺*/
    var editShopUrl = '/o2o/shopadmin/modifyshop';

    // 判断是更新店铺还是注册店铺
    //根据是否有shopId是否使用哪个URL
    if (!isEdit) {
        getShopInitInfo();
    }else {
        getShopInfo(shopId);
    }


    /*获取店铺的基本信息*/
    function getShopInitInfo() {
        /*获取信息的方法  以JSON串的形式返回 function (date)：回调方法*/
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                //初始化一个变量
                var tempHtml = '';
                //初始化变量 获取区域信息的
                var tempAreaHtml = '';
                //data 是从后台获取的数据 店铺分类的列表 map的方式遍历列表
                data.shopCategoryList.map(function (item, index) {
                    //路径拼接
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' +
                        item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' +
                        item.areaName + '</option>';
                });
                //获取到的信息填充到前台前端页面
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }

        });
    }
    /*获取店铺的基本信息*/
    function getShopInfo(shopId) {
        alert("shopId")
        $.getJSON(shopInfoUrl, function(data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function(item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + ' </option>';
                });
                //获取到的信息填充到前台前端页面
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disable','disabled');
                $('#area').html(tempAreaHtml);
                /*做修改店铺信息提交时候*/
                $("#area option[data-id='" + shop.area.areaId + "']").attr(
                    "selected", "selected");
            }

        });
    }

        /*第二个方法需要实现获取表单的信息*/
        /*点击响应*/
        $('#submit').click(function () {
            alert("提交按钮js");
            var shop = {};
            if (isEdit) {
                shop.shopId = shopId;
            }
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();
            shop.shopCategory = {
                shopCategoryId: $('#shop-category').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                areaId: $('#area').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            var shopImg = $('#shop-img')[0].files[0];

            var formDate = new FormData();

            formDate.append('shopImg', shopImg);

            formDate.append('shopStr', JSON.stringify(shop));
            /*点击提交验证码*/
            var verifyCodeActual = $('#j_captcha').val();
            /*如果输入栏为空提示用户输入验证码*/
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }
            /*输入框有填入 就可以传入后台了*/
            formDate.append('verifyCodeActual', verifyCodeActual);
            $.ajax({
                url: (isEdit? editShopUrl:registerShopUrl),
                data: formDate,
                type: 'POST',
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        $.toast('提交成功！');
                    } else {
                        $.toast('提交失败！' + data.errMsg);
                    }
                    /*无论是不是提交成功都需要点击更新  这个是系统生成的*/
                    $('#captcha_img').click();
                }
            });

        });

})