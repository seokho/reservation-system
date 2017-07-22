/**
 * Created by ODOL on 2017. 7. 17..
 */
(function (window) {
    var $h1Logo = $('h1.logo');
    var $aLnkLogo = $('a.lnk_logo');
    var $ulInfoTab = $('ul.info_tab_lst');
    var $ulDetailInfo = $('ul.detail_info_group');
    var API_URL = $.GLOBAL_VAR.API_ROOT_URL + "details";
    var productId;
    var PRODUCT_DATA;
    var REVIEW_DATA;
    var DISPLAY_INFO_DATA;
    var OFF_SET = 3;

    var initModule = {
        init: function () {
            this.detailSectionTrigger();
        },

        detailSectionTrigger: function () {
            productId = location.href.split("/")[4];
            var getProductData = $.commonAPIModule.ajax(undefined, API_URL + "/" + productId, "json", "get", "json");
            getProductData.then(function (data) {
                PRODUCT_DATA = data;
                detailModule.init();
                initModule.reviewSectionTrigger();
                initModule.infoSectionTrigger();
            });
        },

        reviewSectionTrigger: function () {
            var getReviewData = $.commonAPIModule.ajax(undefined, API_URL + "/" + productId + "/review", "json", "get", "json");
            getReviewData.then(function (data) {
                REVIEW_DATA = data;
                reviewModule.init();
                initModule.mapSectionTrigger();
            });
        },

        infoSectionTrigger: function () {
            infoModule.init();
        },

        mapSectionTrigger: function () {
            var getDisplayInfoData = $.commonAPIModule.ajax(undefined, API_URL + "/" + productId + "/displayInfo", "json", "get", "json");
            getDisplayInfoData.then(function (data) {
                DISPLAY_INFO_DATA = data;
                mapModule.init();
            });
        }
    };

    var mapModule = {
        init: function () {
            var template = Handlebars.compile($('#storeInfoTemplate').html());
            this.callNaverMapApi();
            this.rendering(template);
        },

        rendering: function (template) {
            $('div.detail_location').append(template(DISPLAY_INFO_DATA));
        },

        callNaverMapApi: function () {
            var map = new naver.maps.Map('map');
            var myaddress = DISPLAY_INFO_DATA.placeStreet;

            naver.maps.Service.geocode({address: myaddress}, function (status, response) {
                if (status !== naver.maps.Service.Status.OK) {
                    return alert(myaddress + '의 검색 결과가 없거나 기타 네트워크 에러');
                }

                var result = response.result;
                var myaddr = new naver.maps.Point(result.items[0].point.x, result.items[0].point.y);
                map.setCenter(myaddr);

                var marker = new naver.maps.Marker({
                    position: myaddr,
                    map: map
                });

                naver.maps.Event.addListener(marker, "click", function (e) {
                    if (infowindow.getMap()) {
                        infowindow.close();
                    } else {
                        infowindow.open(map, marker);
                    }
                });
                // 마크 클릭시 인포윈도우 오픈
                var infowindow = new naver.maps.InfoWindow({
                    content: '<a href="https://developers.naver.com" target="_blank"><img src="https://developers.naver.com/inc/devcenter/images/nd_img.png"></a>'
                });
            });
        }
    };

    var infoModule = {
        init: function () {
            this.bindEvent();
            $ulInfoTab.find("li._detail>a.anchor").addClass("active");
            $ulDetailInfo.find("li.detail_info_lst>p.in_dsc").text(PRODUCT_DATA[0].content);
        },
        bindEvent: function () {
            $ulInfoTab.on("click", infoModule.toggleActive.bind(this));
        },
        toggleActive: function (event) {
            $ulInfoTab.find("a.anchor").removeClass("active");
            $(event.target).closest("a.anchor").addClass("active");
            if ($(event.target).closest("li").hasClass("_detail")) {
                $('div.detail_area_wrap').show();
                $('div.detail_location').addClass("hide");
            } else {
                $('div.detail_area_wrap').hide();
                $('div.detail_location').removeClass("hide");
            }
        },
    };

    var reviewModule = {
        init: function () {
            this.rendering(REVIEW_DATA, PRODUCT_DATA);
            this.bindClickEvent();
        },
        bindClickEvent: function () {
            $('a.btn_review_more').on("click", reviewModule.moveToLocation);
        },
        popUp: function () {

        },
        moveToLocation: function () {
            location.href = "/review/" + productId;
        },
        rendering: function (reviewData, productData) {
            var joinCount = reviewData.length;
            $.GLOBAL_VAR.$divSectionReview.find('em.green').text(joinCount + "건");
            if (joinCount <= OFF_SET) {
                $('a.btn_review_more').hide();
            }
            reviewModule.appendElement(joinCount, reviewData, productData);
        },
        appendElement: function (count, reviewData, productData) {
            var avg = 0;
            for (var i = 0; i < reviewData.length && i < OFF_SET; i++) {
                var $target = $.GLOBAL_VAR.$divSectionReview.find('ul.list_short_review');
                var template = $('#shortReviewTemplate').html();
                var content = {
                    "imgSrc": reviewData[i].saveFileName,
                    "imgCount": reviewData[i].count,
                    "title": productData[0].name,
                    "review": reviewData[i].comment,
                    "grade": reviewData[i].score,
                    "userId": reviewData[i].username
                }
                var element = $.commonAPIModule.templeToElement(template, content);
                $target.append(element);
                if (reviewData[i].saveFileName === null) {
                    $('div.thumb_area').hide();
                }
                avg += reviewData[i].score;
            }

            avg = avg / count;
            var per = avg * 20;
            $('strong.text_value>span').text(avg);
            $('span.graph_mask>em.graph_value').css("width", per + "%");
        }
    };

    var detailModule = {
        init: function () {
            this.rendering();
            this.eventInfo();
            this.bindEvent();
        },
        rendering: function () {
            var pagination = PRODUCT_DATA.length;
            $('span.off>span').text(pagination);
            $('p.dsc').text(PRODUCT_DATA[0].content);
            detailModule.appendElement();
        },
        appendElement: function () {
            var productData = PRODUCT_DATA;
            for (var i in productData) {
                var $target = $.GLOBAL_VAR.$ulVisualImg;
                var template = $('#visualTemplate').html();
                var content = {
                    "imgSrc": productData[i].saveFileName,
                    "name": productData[i].name
                };
                var element = $.commonAPIModule.templeToElement(template, content);
                $target.append(element);
            }
        },
        eventInfo: function () {
            if (PRODUCT_DATA[0].event !== undefined) {
                $.GLOBAL_VAR.$divSectionEvent.find("div.in_dsc").text(PRODUCT_DATA[0].event);
            } else {
                $.GLOBAL_VAR.$divSectionEvent.hide();
            }
        },
        bindEvent: function () {
            $.GLOBAL_VAR.$divSectionStoreDetails.on("click", "a.bk_more", detailModule.toggleButton);
        },
        toggleButton: function () {
            var $open = $('a._open');
            var $close = $('a._close');
            var $details = $('div.store_details');
            $details.toggleClass("close3");
            if ($details.hasClass("close3")) {
                $close.hide();
                $open.show();
            } else {
                $open.hide();
                $close.show();

            }
        }
    };

    $.headModule.init($('header'), 'a.lnk_logo', 'a.btn_my');
    initModule.init();

})(window);
