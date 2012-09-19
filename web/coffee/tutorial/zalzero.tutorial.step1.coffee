define ['zalzero.utils', 'zalzero.config'], (utils, config) ->
  _arrow = null
  
  _def = null
  
  _popup = null
  
  _cheatSheetClick = (e) ->
    _arrow.remove()
    
    _glassLayer = $('<div id="glass_layer" style="position:fixed;top:0;bottom:0;right:0px;left:0px;z-index:1002"></div>')
    $('body').append _glassLayer
        
    _glassLayer.on 'click', (e)->
    	false
    
    $(".popup-cheatsheet .jspPane").css 'top','0px' 
    $(".popup-cheatsheet .jspDrag").css 'top','0px'
    
    scrollApi = $('.popup-cheatsheet').data('jsp')
    
    scrollApi.reinitialise
      animateDuration: 8500
    
    setTimeout (->
      $('#glass_layer').remove()
      _popup = utils.createPopup 200, 200, config.POPUP_MESSAGE_STEP_1, (e) ->
               
        false
      
      $('#cheat-sheet').off 'click', _cheatSheetClick
      
      $('#cheat-sheet').on 'click', _backToTheGameClick
      
      _arrow = utils.createArrow 225, 0, 'top'
            
      true), 8500
      
    scrollApi.scrollToBottom true
    
    true
  
  _backToTheGameClick = (e) ->
    _finish()
  
  _initialize = (args, def) ->
    _def = def
    
    utils.addHighlight '.friendChallenge', '.popup-cheatsheet'
    
    _arrow = utils.createArrow 225, 0, 'top'
    
    $('#cheat-sheet').on 'click', _cheatSheetClick
    
    true
  
  _finish = () ->
    utils.removeHighlight '.friendChallenge', '.popup-cheatsheet'
    
    _arrow.remove()
    
    $('#cheat-sheet').off 'click', _cheatSheetClick
    $('#cheat-sheet').off 'click', _backToTheGameClick
    
    _def.resolve()
    
  chainFunc: (arg, def) ->
    _initialize null, def
    
    true
