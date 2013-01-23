define ['zalzero.utils', 'zalzero.config'], (utils, config) ->
  _def = null
  
  _popup = null
  
  _arrow = null
  
  _arrow_right = null
  
  _startGameButtonClick = (e) ->
    _finish()
    
    false
    
  _initialize = (args, def) ->
    _def = def
    
    utils.addHighlight '#startButton'
    utils.addPositionRelative '#startButton'
	
    if window.secondArrow is '0'
    	_arrow = utils.createArrow 190, 100, 'right'
    else
    	_arrow_right = utils.createArrow 540, 100, 'left'
    	
    	_arrow = utils.createArrow 190, 100, 'right'
    	
    	$('#rightHUDbackground').addClass 'tutorial-highlight'
    
    utils.congratPopup()
	    
    $('#startButton').on 'click', _startGameButtonClick
    $('#rightHUDbackground').on 'click', _startGameButtonClick
    
    true
  
  _finish = () ->
    utils.removeHighlight '#startButton'
    
    $('#startButton').off 'click', _startGameButtonClick
    $('#rightHUDbackground').off 'click', _startGameButtonClick
    
    _arrow.remove()
    
    _arrow_right.remove()
    
    utils.removeHighlight '#rightHUDbackground'
    
    _def.resolve()
    
    true
    
  chainFunc: (arg, def) ->
    _initialize null, def
    
    true
