define ['zalzero.utils', 'zalzero.config'], (utils, config) ->
  _def = null
  
  _arrow = null
  
  _popup = null
  
  _playButtonClick = (e) ->
    _finish()
    
    false    
  
  _initialize = (args, def) ->
    _def = def
    
    utils.activateAccordion 2
    
    _popup = utils.createPopup 200, 200, config.POPUP_MESSAGE_STEP_3, (e) ->
      _arrow = utils.createArrow 512, 480, 'top'
      $('#bottomHUDbuttons-play').removeClass()
      utils.addHighlight '#placeBetOnServer'
      utils.addPositionRelative '#placeBetOnServer'
      
      $('#placeBetOnServer').on 'click', _playButtonClick
      
      false
      
    true
    
  _finish = () ->
    utils.removeHighlight '#placeBetOnServer'
    
    _arrow.remove()
    
    $('#placeBetOnServer').off 'click', _playButtonClick
    
    _def.resolve()
    
    true
    
  chainFunc: (arg, def) ->
    _initialize null, def
        
    true
