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
	
    if parseInt(gameInstId,10) is 0    
    	_arrow = utils.createArrow 190, 100, 'right'
    else
    	_arrow = utils.createArrow 190, 400, 'left'
    
    utils.congratPopup()
	    
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
