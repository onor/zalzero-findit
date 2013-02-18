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
     
    utils.congratPopup()
	    
    $('.finish').on 'click', _startGameButtonClick
    
    true
  
  _finish = () ->
    
    $('.finish').off 'click', _startGameButtonClick
    $('.popup-wrapper').remove()
    $('.gameInfoPanel').css('display', 'block')
    $('.gameScore').css('display', 'block');
    $('#tutorial-accordion').css('display', 'none')
    
    _def.resolve()
    
    true
    
  chainFunc: (arg, def) ->
    _initialize null, def
    
    true
