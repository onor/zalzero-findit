// Generated by CoffeeScript 1.3.3

define([], function() {
  var bindStatus;
  bindStatus = false;
  $('.draggableBets').live("mouseover", function() {
    if (window.tutorialFlag) {
      return;
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
  return {
    addDrag: function(el) {
      if (window.tutorialFlag) {
        return;
      }
      $('.box-newBet').droppable("disable");
      $('.box-previousRoundCurrentPlayerIncorrect').droppable("disable");
      $('.box-previousRoundCurrentPlayerCorrect').droppable("disable");
      return $(el).draggable({
        scope: "drop_tile",
        revert: 'invalid'
      });
    },
    addDragClone: function(el) {
      if (window.tutorialFlag) {
        return;
      }
      $('.box-newBet').droppable("disable");
      $('.box-previousRoundCurrentPlayerIncorrect').droppable("disable");
      $('.box-previousRoundCurrentPlayerCorrect').droppable("disable");
      return $(el).draggable({
        scope: "drop_tile",
        revert: 'invalid',
        helper: 'clone'
      });
    }
  };
});
