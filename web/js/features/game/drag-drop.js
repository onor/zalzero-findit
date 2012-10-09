// Generated by CoffeeScript 1.3.3

define([], function() {
  var bindStatus;
  bindStatus = false;
  $('.box-blank').live("mouseover", function() {
    return $('.box-newBet').droppable("disable");
  });
  return $('.draggableBets').live("mouseover", function() {
    if (window.tutorialFlag) {
      return;
    }
    $('.draggableBets').draggable({
      scope: "drop_tile",
      revert: 'invalid'
    });
    $('.box-blank').draggable({
      scope: "drop_tile",
      revert: 'invalid',
      helper: 'clone'
    });
    if (bindStatus === false) {
      bindStatus = true;
      $('.box-blank').draggable("disable");
    }
    return $('.box-blank').droppable({
      scope: "drop_tile",
      drop: function(e, ui) {
        if (!ui.draggable.hasClass('box-blank')) {
          ui.draggable.remove();
        } else {
          ui.draggable.draggable("disable");
          ui.draggable.droppable("enable");
        }
        $(this).droppable("disabled");
        window.handleDropNew(e, ui);
        $(this).draggable("enable");
        return true;
      }
    }, true);
  });
});
