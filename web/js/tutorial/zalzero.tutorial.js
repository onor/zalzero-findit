// Generated by CoffeeScript 1.3.3

define(['zalzero.deffereds', 'zalzero.tutorial.step1', 'zalzero.tutorial.step2', 'zalzero.tutorial.step3', 'zalzero.tutorial.step4', 'zalzero.tutorial.step5'], function(deffereds, step1, step2, step3, step4, step5) {
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
      $('#active-screen .girlimg').append(_overlay);
    } else {
      _overlay = jQueryOverlay;
    }
    _overlay.on('click', function(e) {
      return false;
    });
    def.resolve();
    return true;
  };
  _afterTutorial = function(arg, def) {
    delete window.tutorialFlag;
    try {
      gameChangeListener();
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
