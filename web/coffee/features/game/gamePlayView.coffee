define [], () ->
	class gameView
		# constructor class
		constructor: () ->
			@tilesIdxVOs = {}
			@tileClassOverload =
		      BASE_TILE_CLASS:
		        N: "box-blank box-black ui-droppable"
		        Z: "box-blankZoom box-blackZoom"
		
		      OTHER_TURN:
		        N: "box-previousRoundOtherPlayer"
		        Z: "box-previousRoundOtherPlayerZoom"
		
		      PRV_CURRPLYR_CORRECT_TILE:
		        N: "box-previousRoundCurrentPlayerCorrect"
		        Z: "box-previousRoundCurrentPlayerCorrectZoom"
		
		      PRV_CURRPLYR_INCORRECT_TILE:
		        N: "box-previousRoundCurrentPlayerIncorrect"
		        Z: "box-previousRoundCurrentPlayerIncorrectZoom"
		
		      CURRENT_CORRECT_TILE:
		        N: "box-currentRoundCorrect"
		        Z: "box-currentRoundCorrectZoom"
		
		      CURRENT_INCORRECT_TILE:
		        N: "box-currentRoundIncorrect"
		        Z: "box-currentRoundIncorrectZoom"
		
		      CURR_PLYR_FIG_COMPLETE:
		        N: "box-dizitCompleted"
		        Z: "box-dizitCompletedZoom"
		
		      CURR_PLYR_NEWBET:
		        N: "box-newBet"
		        Z: "box-newBetZoom"
		
		      JOKER_BET:
		        N: "joker"
		        Z: "jokerZoom"
		
		      SUPPER_JOKER_BET:
		        N: "superJoker"
		        Z: "superJokerZoom"
		
		# not played round class name
		getNotPlayedRoundClass : (el) ->
			el.className = "notPlayedRound"
		
		# done round class name
		getDoneRoundClass : (el) ->
			el.className = "doneRound"
		
		# current round class name
		getCurrentRoundClass : (el) ->
			el.className = "currentRound"
		
		# current final round class name
		getCurrentFinalRoundClass : (el) ->
			el.className = "currentFinalRound"
		
		# final round class name
		getFinalRoundClass : (el) ->
			el.className = "finalRound"
		
		#
		getBetsAlreadyPlacedClass : (el) ->
			el.className = "betsAlreadyPlaced"
		
		#
		getUsedDraggableBetsClass : (el) ->
			el.className = "usedDraggableBets"
		
		#
		getDraggableBetsClass : (el) ->
			el.className = "draggableBets ui-draggable"
		
		# Done bet class name to play button
		setBetDonePlayButtonEl : (el) ->
			el.href = "#Already Placed Bets"
			el.className = 'bet_done'
			@disablePlayBoutton el
		
		setInnerHTML : (el, val)->
			el.innerHTML = val
		
		disablePlayBoutton : (el) ->
			el.parentNode.setAttribute "class", "bottomHUDbuttons-play-gray"
		
		enablePlayBoutton : (el) ->
			el.href = "#Place Bets"
			el.className = ''
			el.parentNode.setAttribute "class", ""
		
		getPlayButtonEl : () ->
			document.getElementById("placeBetOnServer")
		
		# game round element
		getGameRoundulElem : () ->
			document.getElementById "gameScore-round"
		
		getGameBetPanelEl : () ->
			document.getElementById("gameBetPanel")
		
		getSpaceClass : (divAnc) ->
			divAnc.className = "nbrs"
		
		getShowOnMouseOverEl : () ->
			jQuery("#showOnMouseOver")
				
		addMoveClass : (el) ->
			el.className += " moving"
		
		addBoxDropHoverClass : (el) ->
			el.className += " box-drophover"
		
		setGameDisable : ()	->
			jQuery(".draggableBets").attr("draggable","false")
			jQuery(".resignPopup").hide()
			jQuery("#gameBetPanel").hide()
		
		removeStatusPopup : () ->
			jQuery(".status_show_popup").remove()
			jQuery(".gdWrapper").remove()
		
		showBetsPanel : () ->
			jQuery("#gameBetPanel").show()
		
		markMyTurnTiles : (correctFlag) ->
			_results = []
			tempTileElem = document.getElementById("boardTile-" + tileId)
			if correctFlag is 0 or correctFlag is 1
				if correctFlag is 1
					tempTileElem.className = tempTileElem.className + " box-MyTurn"
					_results.push tempTileElem.innerHTML = "N"
				else
					tempTileElem.className = tempTileElem.className + " box-MyWorngTurn"
					_results.push tempTileElem.innerHTML = "X"
			else
				_results.push undefined
		
		showBetPlacedBy : (currentFigId,usersObject,playerSeatId) ->
			jTileHoverDiv = @getShowOnMouseOverEl()
			$(".roundForBidsCount").remove()
			strOut = ''
			imgSrc = ""
			if (usersObject.PLRS[playerSeatId]?) and (usersObject.PLRS[playerSeatId].PFB?)
	                imgSrc = "https://graph.facebook.com/" + usersObject.PLRS[playerSeatId].PFB + "/picture"
	              else
	                imgSrc = ''
	                
			jTileHoverDivContent = jQuery(jTileHoverDiv.find(".roundForBidsCountUl")[0])
			unless typeof currentFigId is "undefined"
				if currentFigId.indexOf("_NUMERIC_12") isnt -1 or currentFigId.indexOf("_NUMERIC_13") isnt -1 or currentFigId.indexOf("_NUMERIC_14") isnt -1 or currentFigId.indexOf("_NUMERIC_15") isnt -1 or currentFigId.indexOf("_NUMERIC_16") isnt -1 or currentFigId.indexOf("_NUMERIC_17") isnt -1
					strOut += "<li><img src='" + baseUrl + "/images/zalerio_1.2/3.ingame_board/board/player_countindicator_joker.png' alt='Joker' ></li>"
				else strOut += "<li><img src='" + baseUrl + "/images/zalerio_1.2/3.ingame_board/board/player_countindicator_Superjoker.png' alt='Supper Joker' ></li>"  unless currentFigId.indexOf("_NUMERIC_11") is -1
			strOut += "<li><img src='" + imgSrc + "' alt='" + usersObject.PLRS[playerSeatId].PFB + "' ></li>"
			jTileHoverDivContent.html strOut
			
			true
			
		#HTML5 way of showing larger image when the tiles are dragged
		setCustomDragImage : (e) ->
			dragIcon = document.createElement("img")
			dragIcon.src = baseUrl + "/images/zalerio_1.2/3.ingame_board/stand/stand_tile_pickup.png"
			dragIcon.width = 45
			e.dataTransfer.setDragImage dragIcon, 35, 30
		
		#
		createliElem : () ->
			document.createElement "li"
		
		#
		createAHrefElem : () ->
			document.createElement "a"
		
		setFinalInnerHTML : (el) ->
			el.innerHTML = "Final"
			
		parseCoordsAsNum : ( coordX, coordY, board_X ) ->
			( coordY * board_X ) + coordX
			
		getGameWall : (board_X,board_Y) ->
			gameWallDiv = document.getElementById("gamewall")
			gameWallDiv.setAttribute "dropzone", "move s:text/x-betiddata"	
			gameWallDiv.innerHTML = ""
			i = 0
			while (if 0 <= board_Y then i < board_Y else i > board_Y)
				j = 0
				while (if 0 <= board_X then j < board_X else j > board_X)
					csBlankTileClassName = "box-blank box-black ui-droppable"
					gameWallTileDiv = document.createElement("div")
					gameWallTileDiv.className = csBlankTileClassName
					tileIdx = @parseCoordsAsNum( j, i, board_X )
					gameWallTileDiv.id = "boardTile-" + tileIdx
					gameWallTileDiv.setAttribute "tileIdx", tileIdx
					gameWallTileDiv.className = csBlankTileClassName + " boardRowLastTile"  if j is (board_X - 1)
					@tilesIdxVOs[tileIdx] = gameWallTileDiv
					gameWallDiv.appendChild gameWallTileDiv
					(if 0 <= board_X then j++ else j--)
				(if 0 <= board_Y then i++ else i--)
			elBr = document.createElement("br")
			elBr.setAttribute "clear", "all"
			gameWallDiv.appendChild elBr
			gameWallDiv
		
		getTilesIdxVOs : () ->
			@tilesIdxVOs
			
	new gameView