
// Generated by CoffeeScript 1.4.0

define('zalzero.deffereds',[], function() {
  return {
    chainDeffereds: (function() {
      var _defCreator;
      _defCreator = function(def, _prevDef, func) {
        if (_prevDef === null) {
          func(null, def);
        } else {
          _prevDef.then(function(arg) {
            func(arg, def);
            return true;
          });
        }
        return this;
      };
      return function(funcs) {
        var func, _i, _len, _prevDef;
        _prevDef = null;
        for (_i = 0, _len = funcs.length; _i < _len; _i++) {
          func = funcs[_i];
          _prevDef = $.Deferred(function(def) {
            return _defCreator.call(this, def, _prevDef, func);
          });
        }
        return true;
      };
    })()
  };
});

// Generated by CoffeeScript 1.4.0

define('zalzero.config',[], function() {
  return {
    POPUP_MESSAGE_BEFORE_INIT_TEXT_POINT: "The one who uncovers the most valuable objects wins!\n  		<ul>\n<li>	fun game for 2-5 players	</li>\n<li>	play over 7 rounds	</li>\n<li>	place tiles that uncover objects	</li>\n<li>	play multiple games at same time	</li>\n<li>	strategy game that needs some smarts and skills. Easy to learn! </li></ul>",
    POPUP_MESSAGE_STEP_1: "Check out the Numbers hidden in the Matrix and then press 'BACK TO GAME' button.",
    POPUP_MESSAGE_STEP_2: "Place the tiles to figure out the patterns of Numbers which are hidden in the matrix.",
    POPUP_MESSAGE_STEP_3: "After you place all tiles, press the 'PLAY' button and wait for your friends to place their tiles.",
    POPUP_MESSAGE_STEP_4: "The tiles which you placed correctly will be green, your wrong tiles will be orange, while other players wrong tiles are grey.",
    POPUP_MESSAGE_STEP_5: "Now its time for you to enjoy the game. Hit 'Start a Game' button to play with your friends. ",
    POPUP_MESSAGE_CONGRATULATION: "You are done with our short tutorial!\n<br>\n\n  		<ul>\n	You can always find more help at \n	<li>	Cheat Sheet	</li>\n	<li>	Help Section </li>\n</ul>",
    BETS_ARR: [
      {
        id: 'boardTile-3',
        className: 'box-currentRoundCorrect',
        content: '1',
        showFirst: true
      }, {
        id: 'boardTile-30',
        className: 'box-currentRoundIncorrect',
        content: '',
        showFirst: true
      }, {
        id: 'boardTile-100',
        className: 'box-currentRoundCorrect',
        content: '1',
        showFirst: true
      }, {
        id: 'boardTile-121',
        className: 'box-currentRoundCorrect',
        content: '1',
        showFirst: true
      }, {
        id: 'boardTile-154',
        className: 'box-currentRoundIncorrect',
        content: '',
        showFirst: true
      }, {
        id: 'boardTile-160',
        className: 'box-currentRoundCorrect',
        content: '2',
        showFirst: true
      }, {
        id: 'boardTile-201',
        className: 'box-currentRoundIncorrect',
        content: '',
        showFirst: true
      }, {
        id: 'boardTile-251',
        className: 'box-currentRoundCorrect',
        content: '1',
        showFirst: true
      }, {
        id: 'boardTile-223',
        className: 'box-previousRoundOtherPlayer',
        content: '',
        showFirst: false
      }, {
        id: 'boardTile-224',
        className: 'box-previousRoundOtherPlayer',
        content: '',
        showFirst: false
      }, {
        id: 'boardTile-250',
        className: 'box-previousRoundOtherPlayer',
        content: '',
        showFirst: false
      }
    ]
  };
});

// Generated by CoffeeScript 1.4.0

define('zalzero.utils',["zalzero.config"], function(config) {
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
      exit = $(" <div class=\"exit\">  </div> ");
      exit.click(function() {
        delete window.tutorialFlag;
        $(".tutorial-overlay").remove();
        $("#tutorial-accordion").css("display", "none");
        $(".gameInfoPanel").css("display", "block");
        $(".gameScore").css("display", "block");
        def.reject();
        jDocument.trigger("gameChangeListener", '');
        return eval("tutorial = false");
      });
      return $(".tutorial-overlay").append(exit);
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
      nowPlay = 'Now let\'s play!';
      popup = $("<div class=\"popup-wrapper\"><div class=\"start-popup congratPopup\"><div  class=\"title\">Congratulations!</div>\n		<div class=\"details\">" + config.POPUP_MESSAGE_CONGRATULATION + "</div>\n		<div class=\"nowPlay\">" + nowPlay + "</div>\n		<div class=\"ok-button finish\"></div>\n</div></div>");
      $('#active-screen').append(popup);
      popup.on('click', '.ok-button', function(e) {
        return popup.remove();
      });
      return popup;
    }
  };
});

// Generated by CoffeeScript 1.4.0

define('zalzero.tutorial.step1',['zalzero.utils', 'zalzero.config'], function(utils, config) {
  var _arrow, _backToTheGameClick, _cheatSheetClick, _def, _finish, _initialize, _popup;
  _arrow = null;
  _def = null;
  _popup = null;
  _cheatSheetClick = function(e) {
    var scrollApi, _glassLayer;
    _arrow.remove();
    _glassLayer = $('<div id="glass_layer" style="position:fixed;top:0;bottom:0;right:0px;left:0px;z-index:1002"></div>');
    $('body').append(_glassLayer);
    _glassLayer.on('click', function(e) {
      return false;
    });
    $(".popup-cheatsheet .jspPane").css('top', '0px');
    $(".popup-cheatsheet .jspDrag").css('top', '0px');
    scrollApi = $('.popup-cheatsheet').data('jsp');
    scrollApi.reinitialise({
      animateDuration: 8500
    });
    setTimeout((function() {
      $('#glass_layer').remove();
      _popup = utils.createPopup(200, 200, config.POPUP_MESSAGE_STEP_1, function(e) {
        return false;
      });
      $('#cheat-sheet').off('click', _cheatSheetClick);
      $('#cheat-sheet').on('click', _backToTheGameClick);
      _arrow = utils.createArrow(225, 0, 'top');
      return true;
    }), 8500);
    scrollApi.scrollToBottom(true);
    return true;
  };
  _backToTheGameClick = function(e) {
    return _finish();
  };
  _initialize = function(args, def) {
    _def = def;
    utils.addHighlight('.friendChallenge', '.popup-cheatsheet');
    _arrow = utils.createArrow(225, 0, 'top');
    $('#cheat-sheet').on('click', _cheatSheetClick);
    return true;
  };
  _finish = function() {
    utils.removeHighlight('.friendChallenge', '.popup-cheatsheet');
    _arrow.remove();
    $('#cheat-sheet').off('click', _cheatSheetClick);
    $('#cheat-sheet').off('click', _backToTheGameClick);
    return _def.resolve();
  };
  return {
    chainFunc: function(arg, def) {
      _initialize(null, def);
      return true;
    }
  };
});

// Generated by CoffeeScript 1.4.0

define('zalzero.tutorial.step2',['zalzero.utils', 'zalzero.deffereds', 'zalzero.config'], function(utils, deffereds, config) {
  var _arrow, _betCellsArr, _def, _finish, _initTiles, _initialize, _moveBetGenerator, _popup, _processBetsMove;
  _def = null;
  _arrow = null;
  _popup = null;
  _betCellsArr = $.map($.grep(config.BETS_ARR, function(obj, i) {
    return obj.showFirst === true;
  }), function(obj, e) {
    return obj.id;
  });
  _moveBetGenerator = function(elem, dest) {
    return function(arg, def) {
      var $e, destOffset, sourceOffset;
      $e = $(elem);
      sourceOffset = $e.offset();
      destOffset = dest.offset();
      $e.addClass('draggableBetsClick').addClass('transition').removeClass('draggableBets');
      $e.appendTo(document.body);
      $e.css({
        left: sourceOffset.left - 7,
        top: sourceOffset.top - 7
      });
      setTimeout((function() {
        $e.css({
          left: destOffset.left - 10,
          top: destOffset.top - 11
        });
        setTimeout((function() {
          dest.addClass('box-newBet');
          $e.remove();
          return def.resolve();
        }), 300);
        return true;
      }), 5);
      return true;
    };
  };
  _processBetsMove = function() {
    var funcs;
    funcs = $.map($('.draggableBets'), function(elem, index) {
      return _moveBetGenerator(elem, $("#" + _betCellsArr[index]));
    });
    funcs.push(function(args, def) {
      return _finish();
    });
    deffereds.chainDeffereds(funcs);
    return true;
  };
  _initTiles = function() {
    $('.draggableBets:first').draggable({
      scope: "drop_tile",
      start: function(e, ui) {
        $(this).css({
          opacity: '0'
        });
        return true;
      },
      stop: function(e, ui) {
        $(this).css({
          opacity: '1'
        });
        return true;
      },
      helper: function(e) {
        return $("<div class=\"draggableBetsClick tutorial-highlight\"></div>");
      }
    });
    return $('.box-blank').droppable({
      scope: "drop_tile",
      drop: function(e, ui) {
        ui.draggable.remove();
        ui.helper.remove();
        _arrow.remove();
        $(this).addClass('box-newBet');
        _processBetsMove();
        config.BETS_ARR.push({
          id: this.id,
          className: 'box-currentRoundCorrect',
          content: '2'
        });
        return true;
      }
    });
  };
  _initialize = function(args, def) {
    _def = def;
    utils.addHighlight('#mainGame');
    utils.addLowHighlight('.friendChallenge');
    utils.activateAccordion(1);
    $('#gameBetPanel').on('click', '.draggableBets', function(e) {
      return false;
    });
    _popup = utils.createPopup(200, 200, config.POPUP_MESSAGE_STEP_2, function(e) {
      _arrow = utils.createArrow(326, 0, 'top');
      _initTiles();
      return false;
    });
    return true;
  };
  _finish = function() {
    _arrow.remove();
    utils.removeHighlight('#mainGame');
    utils.removeLowHighlight('.friendChallenge');
    return _def.resolve();
  };
  return {
    chainFunc: function(arg, def) {
      _initialize(null, def);
      return true;
    }
  };
});

// Generated by CoffeeScript 1.4.0

define('zalzero.tutorial.step3',['zalzero.utils', 'zalzero.config'], function(utils, config) {
  var _arrow, _def, _finish, _initialize, _playButtonClick, _popup;
  _def = null;
  _arrow = null;
  _popup = null;
  _playButtonClick = function(e) {
    _finish();
    return false;
  };
  _initialize = function(args, def) {
    _def = def;
    utils.activateAccordion(2);
    _popup = utils.createPopup(200, 200, config.POPUP_MESSAGE_STEP_3, function(e) {
      _arrow = utils.createArrow(512, 480, 'top');
      $('#bottomHUDbuttons-play').removeClass();
      utils.addHighlight('#placeBetOnServer');
      utils.addPositionRelative('#placeBetOnServer');
      $('#placeBetOnServer').on('click', _playButtonClick);
      return false;
    });
    return true;
  };
  _finish = function() {
    utils.removeHighlight('#placeBetOnServer');
    _arrow.remove();
    $('#placeBetOnServer').off('click', _playButtonClick);
    _def.resolve();
    return true;
  };
  return {
    chainFunc: function(arg, def) {
      _initialize(null, def);
      return true;
    }
  };
});

// Generated by CoffeeScript 1.4.0

define('zalzero.tutorial.step4',['zalzero.utils', 'zalzero.config'], function(utils, config) {
  var _def, _finish, _initialize, _popup, _processBets, _showPopup;
  _def = null;
  _popup = null;
  _showPopup = function() {
    _popup = utils.createPopup(200, 200, config.POPUP_MESSAGE_STEP_4, function(e) {
      return _finish();
    });
    return true;
  };
  _processBets = function() {
    var betObj, i, _bet, _i, _len, _ref;
    _ref = config.BETS_ARR;
    for (i = _i = 0, _len = _ref.length; _i < _len; i = ++_i) {
      betObj = _ref[i];
      _bet = $("#" + betObj.id);
      _bet.addClass(betObj.className);
      _bet.html(betObj.content);
    }
    setTimeout(_showPopup, 1000);
    return true;
  };
  _initialize = function(args, def) {
    _def = def;
    utils.addHighlight('#gamewall');
    utils.addPositionRelative('#gamewall');
    utils.activateAccordion(3);
    setTimeout(_processBets, 300);
    return true;
  };
  _finish = function() {
    utils.removeHighlight('#gamewall');
    _def.resolve();
    return true;
  };
  return {
    chainFunc: function(arg, def) {
      _initialize(null, def);
      return true;
    }
  };
});

// Generated by CoffeeScript 1.4.0

define('zalzero.tutorial.step5',['zalzero.utils', 'zalzero.config'], function(utils, config) {
  var _arrow, _arrow_right, _def, _finish, _initialize, _popup, _startGameButtonClick;
  _def = null;
  _popup = null;
  _arrow = null;
  _arrow_right = null;
  _startGameButtonClick = function(e) {
    _finish();
    return false;
  };
  _initialize = function(args, def) {
    _def = def;
    utils.congratPopup();
    $('.finish').on('click', _startGameButtonClick);
    return true;
  };
  _finish = function() {
    $('.finish').off('click', _startGameButtonClick);
    $('.popup-wrapper').remove();
    $('.gameInfoPanel').css('display', 'block');
    $('.gameScore').css('display', 'block');
    $('#tutorial-accordion').css('display', 'none');
    if (secondArrow === '0') {
      showFrndSelector();
    } else {
      showFrndSelector();
    }
    _def.resolve();
    return true;
  };
  return {
    chainFunc: function(arg, def) {
      _initialize(null, def);
      return true;
    }
  };
});

// Generated by CoffeeScript 1.4.0

define('zalzero.tutorial',['zalzero.deffereds', 'zalzero.utils', 'zalzero.tutorial.step1', 'zalzero.tutorial.step2', 'zalzero.tutorial.step3', 'zalzero.tutorial.step4', 'zalzero.tutorial.step5'], function(deffereds, utils, step1, step2, step3, step4, step5) {
  var _afterTutorial, _beforeTutorial, _getChainFuncsArr, _overlay;
  _overlay = null;
  _getChainFuncsArr = function(modules) {
    var module, _i, _len, _results;
    _results = [];
    for (_i = 0, _len = modules.length; _i < _len; _i++) {
      module = modules[_i];
      if (module instanceof Function) {
        _results.push(module);
      } else {
        _results.push(module.chainFunc);
      }
    }
    return _results;
  };
  _beforeTutorial = function(arg, def) {
    var jQueryOverlay;
    window.tutorialFlag = true;
    $('#tutorial-accordion').accordion({
      autoHeight: false,
      disabled: true
    });
    $('.gameInfoPanel').css('display', 'none');
    $('.gameScore').css('display', 'none');
    $('#tutorial-accordion').css('display', 'block');
    $('.box-blank').text('').removeClass().addClass('box-blank box-black ');
    $('#gameBetPanel').css('display', 'block');
    $('#gameBetPanel').html('<li class="draggableBets" id="bet_0" draggable="true"><a class="nbrs" id="new-0"></a></li><li class="draggableBets" id="bet_1" draggable="true"><a class="nbrs" id="new-1"></a></li><li class="draggableBets" id="bet_2" draggable="true"><a class="nbrs" id="new-2"></a></li><li class="draggableBets" id="bet_3" draggable="true"><a class="nbrs" id="new-3"></a></li><li class="draggableBets" id="bet_4" draggable="true"><a class="nbrs" id="new-4"></a></li><li class="draggableBets" id="bet_5" draggable="true"><a class="nbrs" id="new-5"></a></li><li class="draggableBets" id="bet_6" draggable="true"><a class="nbrs" id="new-6"></a></li><li class="draggableBets" id="bet_7" draggable="true"><a class="nbrs" id="new-7"></a></li><li class="draggableBets" id="bet_8" draggable="true"><a class="nbrs" id="new-8"></a></li>');
    jQueryOverlay = $('.tutorial-overlay');
    if (jQueryOverlay.length === 0) {
      _overlay = $("<div class=\"tutorial-overlay\"></div>");
      $('#active-screen').prepend(_overlay);
    } else {
      _overlay = jQueryOverlay;
    }
    _overlay.on('click', function(e) {
      return false;
    });
    utils.startPopup(def);
    return true;
  };
  _afterTutorial = function(arg, def) {
    delete window.tutorialFlag;
    try {
      jDocument.trigger("gameChangeListener", '');
    } catch (_error) {}
    eval("tutorial = false");
    _overlay.remove();
    return true;
  };
  return {
    launch: function() {
      deffereds.chainDeffereds(_getChainFuncsArr([_beforeTutorial, step1, step2, step3, step4, step5, _afterTutorial]));
      return true;
    }
  };
});
