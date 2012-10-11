// Generated by CoffeeScript 1.3.3

define(['zalzero.utils', 'zalzero.config'], function(utils, config) {
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
