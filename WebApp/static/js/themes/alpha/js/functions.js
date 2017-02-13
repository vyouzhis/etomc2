/**
 * Theme functions file
 */

jQuery(function( $ ) {

    /**
     * Activate FitVids.
     */
    $(".entry, .slides, .video_cover").fitVids();


    $(function() {
        var _scroll = {
            delay: 1000,
            easing: 'linear',
            items: 1,
            duration: 0.07,
            timeoutDuration: 0,
            pauseOnHover: 'immediate'
        };
        $('#ticker').carouFredSel({
            width: 1000,
            align: false,
            items: {
                width: 'variable',
                height: 35,
                visible: 1
            },
            scroll: _scroll
        });

        $('.caroufredsel_wrapper').css('width', '100%');


    });


    $('#toggle-top').click(function() {
        $('#topmenu-wrap').slideToggle(400);
        $(this).toggleClass("active");

        return false;

    });

    $('#toggle-main').click(function() {
        $('#menu-wrap').slideToggle(400);
        $(this).toggleClass("active");

        return false;

    });


    function mobileadjust() {

        var windowWidth = $(window).width();

        if( typeof window.orientation === 'undefined' ) {
            $('#menu-wrap').removeAttr('style');
            $('#topmenu-wrap').removeAttr('style');
        }

        if( windowWidth < 769 ) {
            $('#menu-wrap').addClass('mobile-menu');
            $('#topmenu-wrap').addClass('mobile-menu');
        }

    }

    mobileadjust();

    $(window).resize(function() {
        mobileadjust();
    });



    if ( $('#respond input, #respond textarea').length > 0 && 'placeholder' in $('#respond input, #respond textarea')[0] ) {
        $('#respond').find('#commentform p label').hide();
    }


});



/**
 * Homepage Slider & Video API Integration
 */

var fp_vimeoPlayers = [],fp_youtubeIDs = [], fp_youtubePlayers = [];
var vimeoPlayers = [], youtubeIDs = [], youtubePlayers = [];
jQuery(document).ready(function($) {

    $('#recent-videos').flexslider({
        animation: "slide",
        controlNav: true,
        directionNav: true,
        animationLoop: false,
        useCSS: false,
        video: true,
        smoothHeight: true,
        slideshow: false,
        before: $.fn.stopDontMove
    });

    $("h3.title:empty").remove();

    $('input, textarea').placeholder();

});

function onYouTubePlayerAPIReady() {
    jQuery(document).ready(function($) {
        $.fn.fp_youtubeStateChange = function(event) {
            if (featured_flex === undefined) return;

            if (event.data === 1) {
                featured_flex.flexslider('stop');
            }
        }

        $(youtubeIDs).each(function(index, value) {
            youtubePlayers.push(new YT.Player(value));
        });

        $(fp_youtubeIDs).each(function(index, value) {
            fp_youtubePlayers.push(new YT.Player(value, {
                events: {
                    'onStateChange' : $.fn.fp_youtubeStateChange
                }
            }));
        });
    });
}

(function($) {
    $.fn.stopDontMove = function() {
        $(vimeoPlayers).each(function(i, el) {
            if ($(el).attr('src') != '') {
                $f(el).api('pause');
            }
        });

        $(youtubePlayers).each(function() {
            if ($.isFunction(this.stopVideo)) {
                this.stopVideo();
            }
        });
    }


    $.fn.fp_vimeoReady = function(playerID) {
        if (featured_flex === undefined) return;

        $f(playerID).addEvent('play', function() {
            featured_flex.flexslider('stop');
        });
    }
})(jQuery);

jQuery(document).ready(function($) {
    var selectors = [
        "iframe[src*='player.vimeo.com']",
        "iframe[src*='youtube.com']",
        "iframe[src*='youtube-nocookie.com']"
    ];

    $('#recent-videos').find(selectors.join(',')).each(function() {
        var $this = $(this);
        var src = $this.prop('src');

        if (!$this.attr('id')) {
            $this.attr('id', 'ai' + Math.floor(Math.random() * 999999));
        }

        if (src.indexOf('vimeo') !== -1) {
            $(this).prop('src', src + '&api=1&player_id=' + $(this).prop('id'));
            vimeoPlayers.push($this.get(0));
        }

        if (src.indexOf('youtube') !== -1) {
            $(this).prop('src', src + '&enablejsapi=1');
            youtubeIDs.push($this.prop('id'));
        }
    });

    $('#slider').find(selectors.join(',')).each(function() {
        var $this = $(this);
        var src = $this.prop('src');

        if (!$this.attr('id')) {
            $this.attr('id', 'ai' + Math.floor(Math.random() * 999999));
        }

        if (src.indexOf('vimeo') !== -1) {
            $(this).prop('src', src + '&api=1&player_id=' + $(this).prop('id'));
            fp_vimeoPlayers.push($this.get(0));
            $f(this).addEvent('ready', $.fn.fp_vimeoReady);
        }

        if (src.indexOf('youtube') !== -1) {
            $(this).prop('src', src + '&enablejsapi=1');
            fp_youtubeIDs.push($this.prop('id'));
        }
    });
});