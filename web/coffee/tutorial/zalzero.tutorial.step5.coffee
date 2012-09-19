define ['zalzero.utils', 'zalzero.config'], (utils, config) ->
  _def = null
  
  _popup = null
  
  _arrow = null
  
  _startGameButtonClick = (e) ->
    _finish()
    
    false
    
  _initialize = (args, def) ->
    _def = def
    
    utils.addHighlight '#startButton'
    utils.addPositionRelative '#startButton'
    
    utils.activateAccordion 4
    
    _arrow = utils.createArrow 190, 100, 'right'
    
    _popup = utils.createPopup 200, 200, config.POPUP_MESSAGE_STEP_5, (e) ->
         
      false
    
    $('#startButton').on 'click', _startGameButtonClick
    
    true
  
  _finish = () ->
    utils.removeHighlight '#startButton'
    
    $('#startButton').off 'click', _startGameButtonClick
    
    _arrow.remove()
    
    _def.resolve()
    
    true
    
  chainFunc: (arg, def) ->
    _initialize null, def
    
    true
