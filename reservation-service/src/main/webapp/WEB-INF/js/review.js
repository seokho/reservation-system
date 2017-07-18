(function (window) {
    var REVIEW_DATA;
    var PRODUCT_DATA;
    var productId;

    var initModule = {
        init: function () {
            this.setData();
        },
        setData: function () {
            productId = location.href.split("/")[4];
            var getProductData = $.commonAPIModule.ajax(undefined, API_URL + "/" + productId, "json", "get", "json");
            getProductData.then(function (data) {
                PRODUCT_DATA = data;
                initModule.render();
            });
        },
        render: function () {
            var getReviewData = $.commonAPIModule.ajax(undefined, API_URL + "/review/" + productId, "json", "get", "json");
            getReviewData.then(function(data) {
               REVIEW_DATA = data;
            });
        }
    }

    initModule.init();
})(window)
