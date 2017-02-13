jQuery(function ($) {
    $('.shortcode-tabs').each(function () {
        var tabCount = 1;

        $(this).children('.tab').each(function (index, element) {
            var idValue = jQuery(this).parents('.shortcode-tabs').attr('id');

            var newId = idValue + '-tab-' + tabCount;

            $(this).attr('id', newId);
            $(this).parents('.shortcode-tabs').find('ul.tab_titles').children('li').eq(index).find('a').attr('href', '#' + newId);

            tabCount++;
        });

        var thisID = $(this).attr('id');

        var tabber = $(this).tabs({ fx: { opacity: 'toggle', duration: 200 } });
        tabber.on('tabsactivate', function (event, ui) {
            var panel = ui.newPanel.clone();

            if (panel.find('iframe').length) {
                ui.newPanel.empty();
                ui.newPanel.append(panel);
            }
        });

        // Check for a matching hash and select that tab if one is found.
        var currentHash = window.location.hash;
        currentHash = currentHash.replace('#', '');
        if (currentHash != '') {
            tabber.tabs('select', currentHash);
        }
    });
})