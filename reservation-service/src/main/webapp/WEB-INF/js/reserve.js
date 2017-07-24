(function (window) {
    var ReserveVars = (function () {
        var productId;
        var productDto;
        var productPriceList = [];
        var totalPrice = 0;
        var ticketCount = 0;


        return {
            productId: productId,
            productDto: productDto,
            productPriceList: productPriceList,
            totalPrice: totalPrice,
            ticketCount: ticketCount
        };
    })();

    var TemplateComponent = eg.Class.extend(eg.Component, {
        titleTemplete: '<h2><span class="title">{{title}}</span></h2>',

        visualTemplate:
        '<li class="item" style="width: 414px;">' +
        '<img alt="" class="img_thumb" src="{{imgSrc}}"> <span class="img_bg"></span>\n' +
        '<div class="preview_txt">\n' +
        '<h2 class="preview_txt_tit">{{title}}</h2> <em class="preview_txt_dsc">₩{{startPrice}} ~ </em>' +
        '<em class="preview_txt_dsc">{{displayStart}} ~ {{displayEnd}}</em> </div>\n' +
        '</li>',

        storeDetailTemplate:
        '<h3 class="in_tit">{{title}}</h3>' +
        '<p class="dsc">' +
        '장소 : {{locate}}<br> 기간 : {{displayStart}}~{{displayEnd}}' +
        '</p>' +
        '<h3 class="in_tit">관람시간</h3>' +
        '<p class="dsc">' +
        '{{observationTime}}' +
        '</p>' +
        '<h3 class="in_tit">요금</h3>' +
        '<p class="dsc">' +
        '성인(만 19~64세) {{priceType1}}원 / 청소년(만 13~18세) {{priceType2}}원<br> 어린이(만 4~12세) {{priceType3}}원 / 20인 이상 단체 20% 할인<br> 국가유공자, 장애인, 65세 이상 4,000원' +
        '</p>',

        ticketBodyTemplate:
        '<div class="qty">' +
        '<div class="count_control">' +
        <!-- [D] 수량이 최소 값이 일때 ico_minus3, count_control_input에 disabled 각각 추가, 수량이 최대 값일 때는 ico_plus3에 disabled 추가 -->
        '<div class="clearfix">' +
        '<a href="#" class="btn_plus_minus spr_book2 ico_minus3 disabled" title="빼기"> </a> ' +
        '<input type="tel" class="count_control_input disabled" value="0" readonly title="수량">' +
        '<a href="#" class="btn_plus_minus spr_book2 ico_plus3" title="더하기">' +
        '</a>' +
        '</div>' +
        <!-- [D] 금액이 0 이상이면 individual_price에 on_color 추가 -->
        '<div class="individual_price"><span class="total_price">0</span><span class="price_type">원</span></div>' +
        '</div>' +
        '<div class="qty_info_icon"> <strong class="product_amount"> <span>{{type}}</span> </strong> ' +
        '<strong class="product_price"> <span class="price">{{price}}</span> <span class="price_type">원</span> </strong> ' +
        '<em class="product_dsc" data-dcPrice="{{discountPrice}}">{{discountPrice}}원 ({{discountRate}}% 할인가)</em> </div>' +
        '</div>'


    });
    var templateComponent = new TemplateComponent();



    var TicketComponent = eg.Class.extend(eg.Component, {

        init: function () {
            var templateComponent = new TemplateComponent();
            this.rendering(templateComponent);
            this.bindEvent();
        },
        bindEvent: function () {
            $("div.ticket_body").on("click", "a.ico_plus3", function() {
                ticket.trigger("plus", event.target);
            });
            $("div.ticket_body").on("click", "a.ico_minus3", function() {
                ticket.trigger("minus", event.target);
            });
        },
        rendering: function () {
            this.trigger("rendering");
        }
    });
    var ticket = new TicketComponent();
    ticket.on("rendering", function () {
        var elementList = [];

        var context;
        var productPrice = ReserveVars.productPriceList;

        for (var i = 0; i < productPrice.length; i++) {

            var price = productPrice[i];
            var discountPrice = (price.price * (1 - price.discountRate));
            var discountRate = (price.discountRate * 100);
            var type;
            switch (price.priceType) {
                case 1 :
                    type = "성인";
                    break;
                case 2 :
                    type = "청소년";
                    break;
                case 3 :
                    type = "유아";
                    break;
                default :
                    type = undefined;
                    break;
            }

            context = {
                "type": type,
                "price": price.price,
                "discountPrice": discountPrice,
                "discountRate": discountRate
            }
            elementList.push(CommonAPIModule.templeToElement(templateComponent.ticketBodyTemplate, context));
        }
        $("div.ticket_body").html(elementList.join(""));
    });

    ticket.on("plus", function (target) {
        var $count = $(target).closest(".clearfix").find("input.count_control_input");
        var count = $count.prop("value");
        $count.prop("value", ++count);
        $(target).closest(".clearfix").find("a.ico_minus3").removeClass("disabled");
        ReserveVars.ticketCount += 1;
        ticket.trigger("priceChange", target);
    });

    ticket.on("minus", function (target) {
        var $count = $(target).closest(".clearfix").find("input.count_control_input");
        var count = $count.prop("value");
        if(ReserveVars.ticketCount > 0) {
            $count.prop("value", --count);
            ReserveVars.ticketCount -= 1;
            ticket.trigger("priceChange", target);
        }
        if (ReserveVars.ticketCount <= 0){
            $(target).closest(".clearfix").find("a.ico_minus3").addClass("disabled");
        }
    });

    ticket.on("priceChange", function (target) {
        var $totalPrice = $(target).closest("div.qty").find("span.total_price");
        var dcPrice = $(target).closest("div.qty").find("em.product_dsc").data("dcprice");
        var tempPrice = $totalPrice.text() * 1;
        if($(target).hasClass("ico_plus3")) {
            tempPrice += dcPrice;
            $totalPrice.text(tempPrice);
            ReserveVars.totalPrice += dcPrice;
            console.log(ReserveVars.ticketCount);
        } else if ($(target).hasClass("ico_minus3")) {
            tempPrice -= dcPrice;
            $totalPrice.text(tempPrice);
            ReserveVars.totalPrice -= dcPrice;
            console.log(ReserveVars.ticketCount);

        }

    });

    ticket.on("setTotalCount", function() {

    })



    var ProductInfoModule = (function () {
        var templateComponent = new TemplateComponent();

        var init = function () {
            $("a.btn_back").on("click", function () {
                history.back();
            });

            ReserveVars.productId = location.href.split("/")[4];
            productInfoSectionTrigger();

        };

        var rendering = function () {
            var productDto = ReserveVars.productDto;
            var productPriceList = ReserveVars.productPriceList;

            var context = {
                "title": ReserveVars.productDto.name
            };
            $("div.top_title").append(CommonAPIModule.templeToElement(templateComponent.titleTemplete, context));

            context = {
                "imgSrc": productDto.saveFileName,
                "title": productDto.name,
                "startPrice": productPriceList[0].price,
                "displayStart": productDto.displayStart,
                "displayEnd": productDto.displayEnd
            };

            $("ul.visual_img").append(CommonAPIModule.templeToElement(templateComponent.visualTemplate, context));


            context = {
                "title": productDto.name,
                "locate": productDto.placeStreet,
                "observationTime": productDto.observationTime,
                "priceType1": productPriceList[0].price,
                "priceType2": ((productPriceList[1] === undefined) ? productPriceList[0].price : productPriceList[1].price),
                "priceType3": ((productPriceList[2] === undefined) ? productPriceList[0].price : productPriceList[2].price)
            };


            $("div.store_details").append(CommonAPIModule.templeToElement(templateComponent.storeDetailTemplate, context));


        };

        var productInfoSectionTrigger = function () {
            var getProductSummaryData = CommonAPIModule.ajax(undefined, "/api/reservations/" + ReserveVars.productId, "json", "get", "json");
            getProductSummaryData.then(function (data) {
                ReserveVars.productDto = data.productDto;
                ReserveVars.productPriceList = data.productPriceList;
                rendering();
                ticket.init();
            });
        };

        return {
            init: init
        }

    })();

    ProductInfoModule.init();


})(window);



