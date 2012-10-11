// Generated by CoffeeScript 1.3.3

define(["zalzero.config"], function(config) {
  var _createItemsProcessor;
  _createItemsProcessor = (function() {
    var _processItem;
    _processItem = function(item, methodName, className) {
      $(item)[methodName](className);
      return true;
    };
    return function(methodName, className) {
      return function(elem) {
        var item, _i, _len, _ref;
        if (arguments.length > 1) {
          _ref = Array.prototype.slice.call(arguments, 0);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            item = _ref[_i];
            _processItem(item, methodName, className);
          }
        } else {
          _processItem(elem, methodName, className);
        }
        return true;
      };
    };
  })();
  return {
    addHighlight: _createItemsProcessor('addClass', 'tutorial-highlight'),
    removeHighlight: _createItemsProcessor('removeClass', 'tutorial-highlight'),
    addLowHighlight: _createItemsProcessor('addClass', 'tutorial-highlight-low'),
    removeLowHighlight: _createItemsProcessor('removeClass', 'tutorial-highlight-low'),
    addPositionRelative: function(selector) {
      $(selector).css({
        position: 'relative'
      });
      return true;
    },
    activateAccordion: function(index) {
      var _acc;
      _acc = $('#tutorial-accordion');
      _acc.accordion('enable');
      _acc.accordion('activate', index);
      _acc.accordion('disable');
      return true;
    },
    createArrow: function(x, y, position) {
      var arrow;
      arrow = $("<div class=\"tutorial-arrow tutorial-highlight\"></div>");
      switch (position) {
        case 'right':
          arrow.addClass('right');
          break;
        case 'top':
          arrow.addClass('top');
          break;
        case 'left':
          arrow.addClass('left');
      }
      arrow.css({
        left: "" + x + "px",
        top: "" + y + "px"
      });
      $('#active-screen .girlimg').append(arrow);
      return arrow;
    },
    createPopup: function(x, y, text, handler) {
      var popup;
      popup = $("<div class=\"tutorial-popup\">" + text + "<div class=\"popup-button\"></div></div>");
      popup.css({
        left: "" + x + "px",
        top: "" + y + "px"
      });
      $('#active-screen .girlimg').append(popup);
      popup.on('click', '.popup-button', function(e) {
        return popup.remove();
      });
      popup.on('click', function(e) {
        return false;
      });
      if (handler != null) {
        popup.on('click', '.popup-button', handler);
      }
      return popup;
    },
    addExit: function() {
      var exit;
      return exit = $(" <div class=\"exit\">  </div> ");
    },
    startPopup: function(def) {
      var popup;
      popup = $("<div class=\"popup-wrapper\"><div class=\"start-popup\">\n		<div  class=\"title\">Welcome to Zalerio!</div>\n		<div class=\"details\">" + config.POPUP_MESSAGE_BEFORE_INIT_TEXT_POINT + "\n		</div>\n			<div class=\"right-button\"></div>\n			<div class=\"left-button\"></div>\n</div></div>");
      $('#active-screen').append(popup);
      popup.on('click', '.right-button', function(e) {
        popup.remove();
        return def.resolve();
      });
      return popup.on('click', '.left-button', function(e) {
        delete window.tutorialFlag;
        $(".tutorial-overlay").remove();
        $("#tutorial-accordion").css("display", "none");
        $(".gameInfoPanel").css("display", "block");
        $(".gameScore").css("display", "block");
        def.reject();
        jDocument.trigger("gameChangeListener", '');
        eval("tutorial = false");
        return popup.remove();
      });
    },
    congratPopup: function(def) {
      var nowPlay, popup;
      if (parseInt(gameInstId, 10) !== 0) {
        nowPlay = 'Now let\'s play with';
      } else {
        nowPlay = 'Now let\'s play!';
      }
      popup = $("<div class=\"popup-wrapper\"><div class=\"start-popup congratPopup\"><div  class=\"title\">Congratulations!</div>\n		<div class=\"details\">" + config.POPUP_MESSAGE_CONGRATULATION + "</div>\n		<div class=\"nowPlay\">" + nowPlay + "</div>\n		<div class=\"ok-button\"></div>\n</div></div>");
      $('#active-screen').append(popup);
      popup.on('click', '.ok-button', function(e) {
        return popup.remove();
      });
      return popup;
    }
  };
});
