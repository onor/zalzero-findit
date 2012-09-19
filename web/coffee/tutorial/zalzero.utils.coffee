define [], () ->
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