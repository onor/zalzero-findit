define ['zalzero.utils', 'zalzero.deffereds', 'zalzero.config'], (utils, deffereds, config) ->
  _def = null
  
  _arrow = null
  
  _popup = null
  
  _betCellsArr = $.map ($.grep config.BETS_ARR, (obj, i) ->
    obj.showFirst is true), (obj, e) ->
    obj.id
  
  _moveBetGenerator = (elem, dest) ->
    (arg, def) ->
      $e = $(elem)
      
      sourceOffset = $e.offset()
      
      destOffset = dest.offset()
      
      $e.addClass('draggableBetsClick').addClass('transition').removeClass('draggableBets')
        
      $e.appendTo document.body
      
      $e.css
        left: sourceOffset.left - 7
        top: sourceOffset.top - 7
      
      setTimeout (->
        $e.css
          left: destOffset.left - 10
          top: destOffset.top - 11
        
        setTimeout (-> 
          dest.addClass 'box-newBet'
          
          $e.remove()
          
          def.resolve()), 300
        
        true), 5
      
      true
  
  _processBetsMove = () ->
    funcs = $.map $('.draggableBets'), (elem, index) ->
      _moveBetGenerator elem, $ "##{_betCellsArr[index]}"
    
    funcs.push (args, def) ->
      _finish()
      
    deffereds.chainDeffereds funcs
    
    true
  
  _initTiles = () ->
    $('.draggableBets:first').draggable
      scope:"drop_tile"
      start: (e, ui) ->
        $(@).css
          opacity: '0'
          
        true
      
      stop: (e, ui) ->
        $(@).css
          opacity: '1'
          
        true
      
      helper: (e) ->
        $ "<div class=\"draggableBetsClick tutorial-highlight\"></div>"

    $('.box-blank').droppable
      scope:"drop_tile"
      drop: (e, ui) ->
        ui.draggable.remove()
        ui.helper.remove()
        
        _arrow.remove()

        $(@).addClass 'box-newBet'
        
        _processBetsMove()
        
        config.BETS_ARR.push
          id: @.id
          className: 'box-currentRoundCorrect'
          content: '2'
        
        true
        
  _initialize = (args, def) ->
    _def = def
    
    utils.addHighlight '#mainGame'
    utils.addLowHighlight '.friendChallenge'
    
    utils.activateAccordion 1
    
    $('#gameBetPanel').on 'click', '.draggableBets', (e) -> false
    
    _popup = utils.createPopup 200, 200, config.POPUP_MESSAGE_STEP_2, (e) ->
      _arrow = utils.createArrow 326, 0, 'top'
      
      _initTiles()
      
      false
    
    true
    
  _finish = () ->
    _arrow.remove()
    
    utils.removeHighlight '#mainGame'
    utils.removeLowHighlight '.friendChallenge'
    
    _def.resolve()
    
  chainFunc: (arg, def) ->
    _initialize null, def
    
    true
