define [], () ->
	bindStatus = false

	$('.box-blank').live("mouseover",() ->
		$('.box-newBet').droppable("disable")
	)
	
	$('.draggableBets').live( "mouseover", () ->
		return if window.tutorialFlag
		$('.draggableBets').draggable
			scope: "drop_tile"
			revert : 'invalid'
	
		$('.box-blank').draggable
			scope: "drop_tile"
			revert : 'invalid'
			helper:'clone'
				
		if bindStatus is false
			bindStatus = true
			$('.box-blank').draggable( "disable" )
		
		$('.box-blank').droppable
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
	 )