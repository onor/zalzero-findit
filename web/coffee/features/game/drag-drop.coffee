define [], () ->
	addDrop : (el) ->
		return if window.tutorialFlag
		$(el).droppable
	      scope: "drop_tile"
	      
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
		 
	addDrag : (el) ->
		return if window.tutorialFlag
		$('.box-blank').droppable "enable"
		$('.box-newBet').droppable("disable")
		$('.box-previousRoundCurrentPlayerIncorrect').droppable("disable")
		$('.box-previousRoundCurrentPlayerCorrect').droppable("disable")
		
		$(el).draggable
			drag: (event, ui) ->
				$dragme = $(event.target)
				console.log(ui.position);
		
				ui.position.top = event.pageY
			
				
				console.log(ui.position);

			scope: "drop_tile"
			revert : 'invalid'
			
	addDragClone : (el) ->
		return if window.tutorialFlag
		$('.box-blank').droppable "enable"
		$('.box-newBet').droppable("disable")
		$('.box-previousRoundCurrentPlayerIncorrect').droppable("disable")
		$('.box-previousRoundCurrentPlayerCorrect').droppable("disable")
		$(el).draggable
			scope: "drop_tile"
			revert : 'invalid'
			helper:'clone'