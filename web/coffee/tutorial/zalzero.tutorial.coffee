define ['zalzero.deffereds','zalzero.utils', 'zalzero.tutorial.step1', 'zalzero.tutorial.step2', 'zalzero.tutorial.step3', 'zalzero.tutorial.step4', 'zalzero.tutorial.step5'],

(deffereds,utils, step1, step2, step3, step4, step5) ->
  _overlay = null
  
  _getChainFuncsArr = (modules) ->
    for module in modules
      if module instanceof Function
        module
      else
        module.chainFunc
  
  _beforeTutorial = (arg, def) ->
    window.tutorialFlag = true
    
    $('#tutorial-accordion').accordion
      autoHeight: false
      disabled: true
    
    $('.gameInfoPanel').css 'display','none' 
    $('.gameScore').css 'display','none' 
    $('#tutorial-accordion').css 'display','block' 
    $('.box-blank').text('').removeClass().addClass('box-blank box-black ')
    $('#gameBetPanel').css 'display','block'
    $('#gameBetPanel').html('<li class="draggableBets" id="bet_0" draggable="true"><a class="nbrs" id="new-0"></a></li><li class="draggableBets" id="bet_1" draggable="true"><a class="nbrs" id="new-1"></a></li><li class="draggableBets" id="bet_2" draggable="true"><a class="nbrs" id="new-2"></a></li><li class="draggableBets" id="bet_3" draggable="true"><a class="nbrs" id="new-3"></a></li><li class="draggableBets" id="bet_4" draggable="true"><a class="nbrs" id="new-4"></a></li><li class="draggableBets" id="bet_5" draggable="true"><a class="nbrs" id="new-5"></a></li><li class="draggableBets" id="bet_6" draggable="true"><a class="nbrs" id="new-6"></a></li><li class="draggableBets" id="bet_7" draggable="true"><a class="nbrs" id="new-7"></a></li><li class="draggableBets" id="bet_8" draggable="true"><a class="nbrs" id="new-8"></a></li>')
    
    jQueryOverlay = $('.tutorial-overlay')
    
    if jQueryOverlay.length is 0
      _overlay = $("<div class=\"tutorial-overlay\"></div>")
  
      $('#active-screen').prepend _overlay
      
    else _overlay = jQueryOverlay
    
    _overlay.on 'click', (e) ->
      false
    utils.startPopup(def)
    
    true
  
  _afterTutorial = (arg, def) ->
    delete window.tutorialFlag
    try
    	jDocument.trigger "gameChangeListener", ''
    eval("tutorial = false")
       	
    _overlay.remove()
    
    true
    
  launch: ->
    deffereds.chainDeffereds _getChainFuncsArr [_beforeTutorial, step1, step2, step3, step4, step5, _afterTutorial]
    
    true