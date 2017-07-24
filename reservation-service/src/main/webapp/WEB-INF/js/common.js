/**
 * Created by ODOL on 2017. 7. 17..
 */
(function ($) {

    $.GLOBAL_VAR = {
        //common var
        API_ROOT_URL: '/api/',
        ROOT_URL: '/main/',
        CONTENT_TYPE_JSON: "application/json; charset=UTF-8",
        CONTENT_TYPE_DEFAULT: "application/x-www-form-urlencoded",

        //selector wrapping by jquery
        $headerClass: $('header.header_tit'),
        $lnkLogoClass: $('a.lnk_logo'),
        $btnMyClass: $('a.btn_my'),
        $eventTabLst: $('ul.event_tab_lst'),
        $lstEventBox: $('ul.lst_event_box'),
        $ulVisualImg: $('ul.visual_img'),
        $pEventLstTxt: $('span.pink'),
        $btnMore: $('div.more>button'),
        $divSectionStoreDetails: $('div.section_store_details'),
        $divSectionEvent: $('div.section_event'),
        $divSectionReview: $('div.section_review_list'),

        //selector
        lnkLogoClass: "a.lnk_logo",

        //global
        $selectedCategory: $('ul.event_tab_lst>li:first-child').find("a"),
        activeCategory: 0,
        productList: [],
        offset: 0
    };

})($);

var HeadModule = (function() {
    var init = function ($wrapper, $targetMain, $targetMy) {
        bindClickEvent($wrapper, $targetMain, $targetMy);
    };

    var bindClickEvent = function ($wrapper, $targetMain, $targetMy) {
        $wrapper.on("click", $targetMain, function () {
            location.href = $.GLOBAL_VAR.ROOT_URL;
        });

        $wrapper.on("click", $targetMy, function () {
            location.href =  "/myReservation";
        });
    };

    return {
        init : init
    }
})();

var CommonAPIModule = (function(){

    var ajax = function (data, url, dataType, type, contentType) {
        return $.ajax({
            data: JSON.stringify(data),
            url: url,
            dataType: dataType,
            type: type,
            contentType: ((contentType === undefined) ? $.GLOBAL_VAR.CONTENT_TYPE_DEFAULT : $.GLOBAL_VAR.CONTENT_TYPE_JSON)
        });
    };


    var templeToElement = function (template, context) {
        var templateScript = Handlebars.compile(template);
        return templateScript(context);
    };

    return {
        "ajax" : ajax,
        "templeToElement" : templeToElement
    };
})();
