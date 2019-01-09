$(function() {
	//解析路径中的额shopId传入 根据url想后端的controller查数据
	var shopId = getQueryString('shopId');
	console.log("shopId:" + shopId);
	var shopInfoUrl = '/o2o/shopadmin/getShopmanagementinfo?shopId=' + shopId;

	$.getJSON(shopInfoUrl, function(data) {
		//如果session中没有查询到值重定向到controller层getshopmanagementinfo里面定义的方法
		if (data.redirect) {
			window.location.href = data.url;
		} else {
			if (data.shopId != undefined && data.shopId != null) {
				shopId = data.shopId;
			}
			$('#shopInfo').attr('href',
					'/o2o/shopadmin/shopoperation?shopId=' + shopId);
		}
	});
});