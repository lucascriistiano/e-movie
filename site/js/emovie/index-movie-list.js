$(document).on("update", function() {

    //##########################################
    // COLUMNIZR
    //##########################################

    $('.multicolumn').columnize({
        columns: 2
    });


    //##########################################
    // CAROUSEL
    //##########################################

    $('#mycarousel').jcarousel({
        // Configuration goes here (http://sorgalla.com/projects/jcarousel/)
        vertical: false
    });

    $('#mycarousel-vertical').jcarousel({
        // Configuration goes here (http://sorgalla.com/projects/jcarousel/)
        vertical: true
    });

    //##########################################
    // LOF SLIDER
    //##########################################


    var buttons = { previous:$('#home-slider .button-previous') ,
                        next:$('#home-slider .button-next') };



    $('#home-slider').lofJSidernews( {
        interval        : 4000,
        direction       : 'opacitys',
        easing          : 'easeInOutExpo',
        duration        : 1200,
        auto            : false,
        maxItemDisplay  : 5,
        navPosition     : 'horizontal', // horizontal
        navigatorHeight : 73,
        navigatorWidth  : 188,
        mainWidth       : 940,
        buttons: buttons
    });




    //##########################################
    // Superfish
    //##########################################

    $("ul.sf-menu").superfish({
        animation: {height:'show'},   // slide-down effect without fade-in
        delay:     800 ,              // 1.2 second delay on mouseout
        autoArrows:  false,
        speed: 100
    });


    //##########################################
    // PROJECT SLIDER
    //##########################################

    $('.project-slider').flexslider({
        animation: "fade",
        controlNav: true,
        directionNav: false,
        keyboardNav: true
    });


    //##########################################
    // Filter - Isotope
    //##########################################


    var $container = $('#filter-container');

    $container.imagesLoaded( function(){
        $container.isotope({
            itemSelector : 'figure',
            filter: '*',
            resizable: false,
            animationEngine: 'jquery'
        });
    });

    // filter buttons

    $('#filter-buttons a').click(function(){

        // select current
        var $optionSet = $(this).parents('#filter-buttons');
        $optionSet.find('.selected').removeClass('selected');
        $(this).addClass('selected');

        var selector = $(this).attr('data-filter');
        $container.isotope({ filter: selector });
        return false;
    });

    //##########################################
    // Tool tips
    //##########################################


    $('.poshytip').poshytip({
        className: 'tip-twitter',
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        offsetY: 5,
        allowTipHover: false
    });



    $('.form-poshytip').poshytip({
        className: 'tip-twitter',
        showOn: 'focus',
        alignTo: 'target',
        alignX: 'right',
        alignY: 'center',
        offsetX: 5
    });

    //##########################################
    // Tweet feed
    //##########################################

    $("#tweets").tweet({
        count: 3,
        username: "ansimuz"
    });

    //##########################################
    // PrettyPhoto
    //##########################################

    $('a[data-rel]').each(function() {
        $(this).attr('rel', $(this).data('rel'));
    });

    $("a[rel^='prettyPhoto']").prettyPhoto();


    //##########################################
    // Accordion box
    //##########################################

    $('.accordion-container').hide();
    $('.accordion-trigger:first').addClass('active').next().show();
    $('.accordion-trigger').click(function(){
        if( $(this).next().is(':hidden') ) {
            $('.accordion-trigger').removeClass('active').next().slideUp();
            $(this).toggleClass('active').next().slideDown();
        }
        return false;
    });

    //##########################################
    // Toggle box
    //##########################################

    $('.toggle-trigger').click(function() {
        $(this).next().toggle('slow');
        $(this).toggleClass("active");
        return false;
    }).next().hide();




    //##########################################
    // Tabs
    //##########################################

    $(".tabs").tabs("div.panes > div", {effect: 'fade'});



    //##########################################
    // Create Combo Navi
    //##########################################

    // Create the dropdown base
    $("<select id='comboNav' />").appendTo("#combo-holder");

    // Create default option "Go to..."
    $("<option />", {
        "selected": "selected",
        "value"   : "",
        "text"    : "Navigation"
    }).appendTo("#combo-holder select");

    // Populate dropdown with menu items
    $("#nav a").each(function() {
        var el = $(this);
        var label = $(this).parent().parent().attr('id');
        var sub = (label == 'nav') ? '' : '- ';

        $("<option />", {
         "value"   : el.attr("href"),
         "text"    :  sub + el.text()
        }).appendTo("#combo-holder select");
    });


    //##########################################
    // Combo Navigation action
    //##########################################

    $("#comboNav").change(function() {
      location = this.options[this.selectedIndex].value;
    });


    //##########################################
    // Resize event
    //##########################################

    $(window).resize(function() {

        var w = $(window).width();
        //console.log(w);

        $container.isotope('reLayout');

    }).trigger("resize");


});//close

$(document).ready(function (){
    $.ajax({
        type: "GET",
        url: "http://localhost:8000/emovie/movies",
        dataType: "json",
        success: function(data) {
            console.log(data);

            var $container = $("#filter-container");
            var $slider = $("#movies-slider");
            var $navigator = $("#navigator");

            $container.empty();
            $slider.empty();
            $navigator.empty();

            $.each(data, function(index, movie) {
                console.log(movie);

                var sliderHTML = '';
                sliderHTML += '<li>';
                sliderHTML += '<img src="img/movies/' + movie['image'] + '.jpg" title="" alt="alt" />';
                sliderHTML += '<div class="slider-description">';
                sliderHTML += '<h4>' + movie['name'] + '</h4>';
                sliderHTML += '<p>' + movie['advertisement'] + '. Clique para ver a descrição completa e comprar seu ingresso.';
                sliderHTML += '<a class="link" href="detalhesfilme.php?id=' + movie['id'] + '">Leia Mais </a>';
                sliderHTML += '</p>';
                sliderHTML += '</div>';
                sliderHTML += '</li>';
                $slider.append(sliderHTML);

                var navigatorHTML = '';
                navigatorHTML += '<li><img src="img/movies/' + movie['image'] + '_small.jpg" alt="alt" /></li>';
                $navigator.append(navigatorHTML);

                var movieHTML = '';
                movieHTML += '<figure class="web">';
                movieHTML += '<a href="detalhesfilme.php?id=' + movie['id'] + '" class="thumb"><img src="img/movies/' + movie['image'] + '.jpg" alt="alt" /></a>';
                movieHTML += '<figcaption>';
                movieHTML += '<a href="detalhesfilme.php?id=' + movie['id'] + '"><h3 class="heading">' + movie['name'] + '</h3></a>';
                movieHTML += movie['advertisement'];
                movieHTML += '</figcaption>';
                movieHTML += '</figure>';
                $container.append(movieHTML);
            });

            $(document).trigger("update");
        },
        error: function(){}
    });
});