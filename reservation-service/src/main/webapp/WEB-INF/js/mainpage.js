/**
 * Created by ODOL on 2017. 7. 10..
 */
(function (window) {

    //event box(product list)
    var productListModule = {
        init: function () {
            this.rendering(4);
            this.bindClickEvent();
        },
        bindClickEvent: function () {
            $.GLOBAL_VAR.$lstEventBox.on("click", "li.item", productListModule.moveToLocation);

        },
        moveToLocation: function () {
            location.href = "/detail/" + $(this).find("a.item_book").data("productid");
        },
        rendering: function (limit) {
            var url;
            var category = $.GLOBAL_VAR.activeCategory;
            if (category !== 0) {
                url = $.GLOBAL_VAR.API_ROOT_URL + "products/" + category + "?offset=" + $.GLOBAL_VAR.offset + "&limit=" + limit;
            } else {
                url = $.GLOBAL_VAR.API_ROOT_URL + "products?offset=" + $.GLOBAL_VAR.offset + "&limit=" + limit;
            }
            var getProducts = $.commonAPIModule.ajax(undefined, url, "json", "get", "json");
            getProducts.then(function (list) {
                $.GLOBAL_VAR.productList = list;
                productListModule.appendElement(category, list);
                $.GLOBAL_VAR.offset = $.GLOBAL_VAR.offset + list.length;
            });
        },

        appendElement: function (activeCategoryId, products) {
            for (var i in products) {
                var $target = ((i % 2 === 0) ? $('ul.left') : $('ul.right'));
                var template = $('#productListTemplate').html();
                var context = {
                    "categoryId": products[i].categoryId,
                    "productId": products[i].id,
                    "imgAlt": products[i].name,
                    "imgSrc": products[i].saveFileName,
                    "smallLocation": products[i].placeName,
                    "pDescription": products[i].description
                };
                var element = $.commonAPIModule.templeToElement(template, context);
                $target.append(element);
            }
        },
        removeListItem: function ($parentElement) {
            var target = $parentElement.find("li.item");
            $(target).remove();
            $.GLOBAL_VAR.offset = 0;
        }
    }


    //category select section
    var categoryModule = {
        init: function () {
            this.rendering();
            this.bindClickEvent();
            this.setActiveProductsCount();
        },

        bindClickEvent: function () {
            $.GLOBAL_VAR.$eventTabLst.on("click", "a.anchor", categoryModule.setActive.bind(this));
            $.GLOBAL_VAR.$btnMore.on("click", productListModule.rendering.bind(undefined, 2));
        },

        setActive: function (event) {
            event.stopPropagation();
            $.GLOBAL_VAR.$selectedCategory.removeClass("active");
            var $eventTarget = $(event.target).closest("li").find("a.anchor");
            $eventTarget.addClass("active");
            $.GLOBAL_VAR.$selectedCategory = $eventTarget;
            $.GLOBAL_VAR.activeCategory = $eventTarget.closest(".item").data("category");
            productListModule.removeListItem($.GLOBAL_VAR.$lstEventBox);
            productListModule.rendering(4);
            // categoryModule.setActiveProductsCount();
        },

        setActiveProductsCount: function () {
            var getCount = $.commonAPIModule.ajax(undefined, $.GLOBAL_VAR.API_ROOT_URL + "products/count", "json", "get", "json");
            getCount.then(function (count) {
                $.GLOBAL_VAR.$pEventLstTxt.text(count + "개");
            });
        },

        rendering: function () {
            var getCategories = $.commonAPIModule.ajax(undefined, $.GLOBAL_VAR.API_ROOT_URL + "categories/", "json", "get", "json");
            getCategories.then(function (list) {
                var defaultCategory = {
                    id: 0,
                    name: "전체"
                };
                var addCategory = [defaultCategory];
                categoryModule.appendElement(addCategory.concat(list));
            })
        },

        appendElement: function (elements) {
            for (var i in elements) {
                var template = $('#categoryListTemplate').html();
                var context = {
                    "id": elements[i].id,
                    "name": elements[i].name
                };
                $.GLOBAL_VAR.$eventTabLst.append($.commonAPIModule.templeToElement(template, context));
            }
            $.GLOBAL_VAR.$selectedCategory = $('ul.event_tab_lst>li:first-child').find("a.anchor");
            $.GLOBAL_VAR.$selectedCategory.addClass("active");
            $('ul.event_tab_lst>li:last-child').find("a.anchor").addClass("last");
        },

    };

    $.headModule.init($('header.header_tit'), 'a.lnk_logo', 'a.btn_my');
    categoryModule.init();
    productListModule.init();
    $('#log_out').on("click", function() {

    })


})(window);






