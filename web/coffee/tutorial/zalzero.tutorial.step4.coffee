define ['zalzero.utils', 'zalzero.config'], (utils, config) ->
  _def = null
  
  _popup = null
  
  _showPopup = () ->
    _popup = utils.createPopup 200, 200, config.POPUP_MESSAGE_STEP_4, (e) ->
      _finish()
      
      false
    
    true
  
  _processBets = () ->
    for betObj, i in config.BETS_ARR
      _bet = $("##{betObj.id}")
      
      _bet.addClass betObj.className
      _bet.html betObj.content
    
    setTimeout _showPopup, 1000
    
    true
  
  _initialize = (args, def) ->
    _def = def
    
    utils.addHighlight '#gamewall'
    utils.addPositionRelative '#gamewall'
    
    utils.activateAccordion 3
    
    setTimeout _processBets, 300
    
    true
  
  _finish = () ->
    utils.removeHighlight '#gamewall'
    
    _def.resolve()
    
    true
    
  chainFunc: (arg, def) ->
    _initialize null, def
    
    true
