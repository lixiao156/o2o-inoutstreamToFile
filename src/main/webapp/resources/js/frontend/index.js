$(function () {
    //定义访问后台，获取头条列表以及一级类别表的URL
    var url = '/o2o/frontend/listmainpageinfo';
    $.getJSON(url, function (data) {
        //如果通过通过url获取数据成功
        if (data.success) {
            //后台传递过来的头条列表
            var headLineList = data.headLineList;
            var swiperHtml = '';
            /*
             * function getContextPath(){
               return "/o2o/";
               }
               lineLink:属性是HeadLine实体类的属性
             */
            //遍历头条列表，并且拼接轮播图组
            headLineList.map(function (item, index) {
                swiperHtml += '' + '<div class="swiper-slide img-wrap">'
                    //映射到index.html中的一个字段
                    + '<a href="' + item.lineLink
                    + '" external> <img class = "banner-img" src ="' + getContextPath2() + item.lineImg
                    + '"alt = "' + item.lineName + '"></a>' + '</div>';
            });
            console.log(swiperHtml);

            //将轮播图组赋值给前端(注入)
            $('.swiper-wrapper').html(swiperHtml);
            //设置轮播图的时间为3秒
            $('.swiper-container').swiper({
                autoplay: 3000,
                //用户对轮播图进行操作是否停止自动轮播
                autoplayDisableOnInteraction: false
            });
            //获取后台传递过来的大类列表
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            //遍历大类的类别  拼接出50%两两一行的类别
            //将商品的中的分类以及分类描述拼接起来 然后再注入到html对应Id的位置中
            shopCategoryList.map(function (item, index) {
                categoryHtml += '' + '<div class = " col-50 shop-classify" data-category = '
                    //类别id
                    + item.shopCategoryId + '>' + '<div class="word">'
                    //类别名称
                    + '<p class="shop-title">' + item.shopCategoryName
                    + '</p>' + '<p class="shop-desc">'
                    //类别的描述
                    + item.shopCategoryDesc + '</p>' + '</div>'
                    + '<div class="shop-classify-img-warp">'
                    //类别图片
                    + '<img class="shop-img" src="' + getContextPath() + item.shopCategoryImg
                    + '">' + '</div>' + '</div>';

            });
            //将拼接好的类别赋值给前端HTML 展示
            $('.row').html(categoryHtml);
        }
    });
    /**
     * jQuery 语法实例
     $(this).hide()
     演示 jQuery hide() 函数，隐藏当前的 HTML 元素。
     $("#test").hide()
     演示 jQuery hide() 函数，隐藏 id="test" 的元素。
     $("p").hide()
     演示 jQuery hide() 函数，隐藏所有 <p> 元素。
     $(".test").hide()
     演示 jQuery hide() 函数，隐藏所有 class="test" 的元素
     */
    // #:  绑定的html中的id 属性的值  若点击“我的”，则显示侧栏
    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });

    //事件绑定 将shop-classify 与点击事件绑定
    $('.row').on('click', 'shop-classify', function (e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        //搜索大类下面的所有的列表
        var newUrl = '/o2o/frontend/shoplist?parentId=' + shopCategoryId;
        //windows.location.href="/url" 当前页面打开URL页面，前面三个用法相同。
        window.location.href = newUrl;
    })

});

/**
 * 获取项目的contextpath，修正图片路由让其正确显示
 */
function getContextPath2(){
    return "/o2o";
}