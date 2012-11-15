// Generated by CoffeeScript 1.3.3

define([], function() {
  return {
    addDrop: function(el) {
      if (window.tutorialFlag) {
        return;
      }
      $(el).droppable({
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
      });
      return true;
    },
    addDrag: function(el) {
      if (window.tutorialFlag) {
        return;
      }
      $('.box-blank').droppable("enable");
      $('.box-newBet').droppable("disable");
      $('.box-previousRoundCurrentPlayerIncorrect').droppable("disable");
      $('.box-previousRoundCurrentPlayerCorrect').droppable("disable");
      return $(el).draggable({
        drag: function(event, ui) {
          var $dragme, mult;
          mult = 2;
          $dragme = $(event.target);
          ui.position.top = $.offset().top;
          ui.position.left = $.offset().left;
          return $dragme.css({
            top: ui.position.top,
            left: ui.position.left
          });
        },
        scope: "drop_tile",
        revert: 'invalid'
      });
    },
    addDragClone: function(el) {
      if (window.tutorialFlag) {
        return;
      }
      $('.box-blank').droppable("enable");
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
