define [], () ->
	bindStatus = false	
	$('.draggableBets').live( "mouseover", () ->
		return if window.tutorialFlag
#		$('.box-blank').draggable
#			scope: "drop_tile"
#			start : ( e,ui )->
#				$('.box-newBet').droppable("disable")
#				$('.box-previousRoundCurrentPlayerIncorrect').droppable("disable")
#				$('.box-previousRoundCurrentPlayerCorrect').droppable("disable")
#			revert : 'invalid'
#			helper:'clone'
#				
#		$('.box-blank').draggable( "disable" )
		
		$('.box-blank').droppable
	      scope: "drop_tile"
#	      hoverClass: "box-drophover"
	      drop: (e, ui) ->
	      	unless ui.draggable.hasClass 'box-blank'
	      		ui.draggable.remove()
	      	else
	      		ui.draggable.draggable "disable"     		
	      		ui.draggable.droppable "enable"
	      		
	      	$(@).droppable "disabled"
	      	
	      	window.handleDropNew(e,ui)
	      	 	
	      	$(@).draggable( "enable" )
	      	
	      	true
	      	
	      true
	 )
	 
	addDrag : (el) ->
		return if window.tutorialFlag
		$(el).draggable
			scope: "drop_tile"
			revert : 'invalid'
			
	addDragClone : (el) ->
		return if window.tutorialFlag
		$(el).draggable
			scope: "drop_tile"
			revert : 'invalid'
			helper:'clone'