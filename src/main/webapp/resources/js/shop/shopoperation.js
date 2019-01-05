
/**
 * js两个功能
 * 1.从后台获取店铺分类以及区域的等信息 将其填充到前端的Html文件里面去
 * 2.将表单的信息获取到，传递给后台，用其注册店铺
 * $(function () 属于jQuery
 */
 $(function () {
    /*获取店铺信息及分类路径*/
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    /*注册店铺这里与web包中manager类中的url对应*/
    var registerShopUrl = '/o2o/shopadmin/registershop';

    alert(initUrl);
    getShopInitInfo();

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
                    tempHtml += '<option data-id"' + item.shopCategoryId + '">' +
                        item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id"' + item.areaId + '">' +
                        item.areaName + '</option>';
                });
                //获取到的信息填充到前台前端页面
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }

        });

        /*第二个方法需要实现获取表单的信息*/
        /*点击响应*/
        $('submit').click(function () {
            var shop = {};
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();
            shop.CategoryId = {
                shopCategory: $('#shop-category').find('option').not(function () {
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
            $.ajax({
                url: registerShopUrl,
                type: formDate,
                contentType: false,
                proceesData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        $.toast('提交成功！');
                    } else {
                        $.toast('提交失败！' + data.errMsg);
                    }
                }
            });

        });

    }
})