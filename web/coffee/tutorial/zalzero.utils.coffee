define ["zalzero.config"], (config) ->
  _createItemsProcessor = do ->
    _processItem = (item, methodName, className) ->
      $(item)[methodName] className

      true
    
    (methodName, className) ->
      (elem) ->
        if arguments.length > 1
          for item in Array.prototype.slice.call arguments, 0
            _processItem item, methodName, className
        else _processItem elem, methodName, className
        
        true
      
  addHighlight: (_createItemsProcessor 'addClass', 'tutorial-highlight')
  removeHighlight: (_createItemsProcessor 'removeClass', 'tutorial-highlight')
  
  addLowHighlight: (_createItemsProcessor 'addClass', 'tutorial-highlight-low')
  removeLowHighlight: (_createItemsProcessor 'removeClass', 'tutorial-highlight-low')
  
  addPositionRelative: (selector) ->
    $(selector).css
      position: 'relative'
    
    true
  
  activateAccordion: (index) ->
    _acc = $('#tutorial-accordion')
    
    _acc.accordion 'enable'
    _acc.accordion 'activate', index
    _acc.accordion 'disable'
    
    true
  
  createArrow: (x, y, position) ->
    arrow = $ "<div class=\"tutorial-arrow tutorial-highlight\"></div>"
    
    switch position
      when 'right' then arrow.addClass 'right'
      when 'top' then arrow.addClass 'top'
      when 'left' then arrow.addClass 'left'
      
    arrow.css
      left: "#{x}px"
      top: "#{y}px"
      
    $('#active-screen .girlimg').append arrow
    
    arrow
  
  createPopup: (x, y, text, handler) ->
    popup = $ "<div class=\"tutorial-popup\">#{text}<div class=\"popup-button\"></div></div>"
    
    popup.css
      left: "#{x}px"
      top: "#{y}px"
    
    $('#active-screen .girlimg').append popup
    
    popup.on 'click', '.popup-button', (e) -> popup.remove()
    
    popup.on 'click', (e) -> false
    
    if handler? then popup.on 'click', '.popup-button', handler
    
    popup
    
  addExit : ()->
  	exit = $ """ <div class="exit">  </div> """
  	
  	exit.click(->
  		delete window.tutorialFlag
  		$(".tutorial-overlay").remove()
  		$("#tutorial-accordion").css("display","none")
  		$(".gameInfoPanel").css("display","block")
  		$(".gameScore").css("display","block")
  		def.reject()
  		jDocument.trigger "gameChangeListener", ''
  		eval("tutorial = false")
  	)
  	
  	$(".tutorial-overlay").append exit
  	  	
    
  startPopup : (def) ->
  	popup = $ """<div class="popup-wrapper"><div class="start-popup">
						<div  class="title">Welcome to Zalerio!</div>
						<div class="details">#{config.POPUP_MESSAGE_BEFORE_INIT_TEXT_POINT}
						</div>
							<div class="right-button"></div>
							<div class="left-button"></div>
				</div></div>"""
  	$('#active-screen').append popup
	
  	popup.on 'click', '.right-button', (e) ->
  		popup.remove()
  		def.resolve()
	
  	popup.on 'click', '.left-button', (e) ->
  		delete window.tutorialFlag
  		$(".tutorial-overlay").remove()
  		$("#tutorial-accordion").css("display","none")
  		$(".gameInfoPanel").css("display","block")
  		$(".gameScore").css("display","block")
  		def.reject()
  		jDocument.trigger "gameChangeListener", gameInstId
  		$("#gameBetPanel").html('')
  		eval("tutorial = false")
  		popup.remove()
  	
  congratPopup : (def) ->
  	
  	nowPlay = 'Now let\'s play!'
  		
  	popup = $ """<div class="popup-wrapper"><div class="start-popup congratPopup"><div  class="title">Congratulations!</div>
						<div class="details">#{config.POPUP_MESSAGE_CONGRATULATION}</div>
						<div class="nowPlay">#{nowPlay}</div>
						<div class="ok-button finish"></div>
				</div></div>"""
  	$('#active-screen').append popup
	
  	popup.on 'click', '.ok-button', (e) ->  		
  		popup.remove()
  	
  	popup