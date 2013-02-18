define ["../../helper/confirmBox","../../helper/utils","./myLevel","../../config/globals","gamePlayView","messageListener",'drag-drop'], (confirmBox,utils,myLevel,globals,gamePlayView,messageListenerObj,dragdrop) ->
	bets = {}
	boardVo = {}
	responseVo = {}
	window.currentBets = {}
	window.currentBetsIdx = {}
	ZalerioGame = (->
	    ZalerioGame = ->
	    _this = this
	    docElems = {}
	    tilesIdxVOs = {}
	    
	    betsPanelIndexVO = {}
	    window.currentRoundBidPlaced = -1
	    internalDNDType = "text/x-betiddata"	    
	
	    flag_zoomTrue = false
	    currPlayerFigVOs = {}
	    window.playerBetTiles = {}
	    boardVOCodes =
	      TILE_COUNT: "BC"
	      PLAYER_INFO_OBJ: "PR"
	      FIGURE_ID: "CF"
	      BET_WINNER: "BW"
	
	    betChangeCode =
	      PLAYER_SEAT_ID: "PS"
	      TILE_COUNT: "BC"
	      BET_OBJ: "PB"
	      ROUND_ID: "PR"
	
	    window.betChangeVOs = {}
	    boardVOs = {}
	
	    flag_roundDrawn = false
	    coordCodes =
	      COORD_COUNT: "BC"
	      COORD_NUM: "CN"
	      COORD_X: "CX"
	      COORD_Y: "CY"
	
	    zalerioMapType = ORIG_MAP:
	      code: "OF"
	      className: "box-RevealingNumber"
	
	    roundVOsIdx = {}
	    board_X = null
	    board_Y = null
	    roundBets = null
	    roundVOs = {}
	    figureDetailsVO = {}
	    initBoard = ->
	      utils.log "BoardXY : " + zzGlobals.roomVars[zzGlobals.roomCodes.BOARD_XY]
	      boardDimension = zzGlobals.roomVars[zzGlobals.roomCodes.BOARD_XY].split(":")
	      board_X = boardDimension[0]
	      board_Y = boardDimension[1]
	      drawGameBoard()
	
	    initRoundBets = ->
	      utils.log "Round Bets : " + zzGlobals.roomVars[zzGlobals.roomCodes.ROUND_NOOFBETS]
	      roundBets = zzGlobals.roomVars[zzGlobals.roomCodes.ROUND_NOOFBETS]
	      reDrawBetsPanel()
	
	    parseRounds = (el, val) ->
	      utils.log "parse Ar data : " + zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ALLROUNDS]
	      obj = jQuery.parseJSON(val)
	      roundVOs = {}
	      for i of obj
	        roundVOs[i] = jQuery.parseJSON(obj[i])
	      refreshRoundsPanel()
	
	    refreshRoundsPanel = ->
	      drawRoundsPanel()  #unless flag_roundDrawn
	      lastRoundId = null
	      lastEl = null
	      for roundId of roundVOs
	        el = roundVOsIdx[roundId]
	        roundEndTimeTTL = parseInt(roundVOs[roundId]["EM"])
	        if roundEndTimeTTL > 0
	          gamePlayView.getNotPlayedRoundClass el
	        else
	          gamePlayView.getDoneRoundClass el
	          
	        gamePlayView.getCurrentRoundClass el   if roundId is zzGlobals.roomVars[zzGlobals.roomCodes.CURRENTROUND]
	        lastEl = el
	        lastRoundId = roundId
	      if lastEl and lastRoundId
	        if lastRoundId is zzGlobals.roomVars[zzGlobals.roomCodes.CURRENTROUND]
	          gamePlayView.getCurrentFinalRoundClass el
	        else
	          gamePlayView.getFinalRoundClass el
		
		#TODO: move code to right class and remove global scope
	    window.drawRoundsPanel = (elementDiv)->
	      if typeof elementDiv is 'undefined'
	      	gameRoundulElem = gamePlayView.getGameRoundulElem()
	      	roundVOsIdx = {}
	      else
	      	gameRoundulElem = elementDiv
	      	window.roundVOsIdxRightHUD = {}
	      	
	      gameRoundulElem.innerHTML = ""
	      cnt = 0
	      aHrefElemClone = null
	      
	      for roundId of roundVOs
	        cnt++
	        liElem = gamePlayView.createliElem() # document.createElement("li")
	        aHrefElem = gamePlayView.createAHrefElem() #document.createElement("a")
	        aHrefElem.href = "#"
	        
	        gamePlayView.getNotPlayedRoundClass aHrefElem #aHrefElem.className = "notPlayedRound"
	        gamePlayView.setInnerHTML aHrefElem, roundVOs[roundId]["RN"]
	        
	        utils.log "roundVo : ", roundId
	        
	        liElem.appendChild aHrefElem
	        if typeof elementDiv is 'undefined'
	        	roundVOsIdx[roundId] = aHrefElem
	       	else
	       		roundVOsIdxRightHUD[cnt] = aHrefElem
	       		
	        gameRoundulElem.appendChild liElem
	        aHrefElemClone = aHrefElem
	      if cnt > 0
	        gamePlayView.setFinalInnerHTML aHrefElemClone	        
	        flag_roundDrawn = true
	
	    reDrawBetsPanel = ->
	      try
	      	if tutorial is true
	      		return
	      playButtonEl = gamePlayView.getPlayButtonEl()
	      if currentRoundBidPlaced > 0
	        i = 0
	        while (if 0 <= roundBets then i < roundBets else i > roundBets)
	          currentBetId = "bet_" + i
	          el = betsPanelIndexVO[currentBetId]
	          gamePlayView.getBetsAlreadyPlacedClass el	          
	          el.dragBet = 0
	          el.draggable = false  if el.draggable?
	          # remove event handler
	          #utils.removeEventHandler el, "dragstart", handleDragStart, false
	          
	          (if 0 <= roundBets then i++ else i--)
	        gamePlayView.setBetDonePlayButtonEl playButtonEl
	        utils.removeEventHandler playButtonEl, "click", sendPlaceBetRequest, true
	      else
	        count = 0
	        for k of window.currentBets
	          count++
	        if count < 9
	          gamePlayView.disablePlayBoutton playButtonEl
	        else
	          gamePlayView.enablePlayBoutton playButtonEl
	        drawBetPanel()  unless flag_roundBetsDrawn
	        _results = []
	        i = 0
	        while (if 0 <= roundBets then i < roundBets else i > roundBets)
	          currentBetId = "bet_" + i
	          el = betsPanelIndexVO[currentBetId]
	          flag_alreadyUsed = false
	          for k of window.currentBets
	            continue  unless __hasProp_.call(window.currentBets, k)
	            usedBetId = window.currentBets[k]
	            if usedBetId is currentBetId
	              flag_alreadyUsed = true
	              break
	          if flag_alreadyUsed
	            gamePlayView.getUsedDraggableBetsClass el	            
	            el.dragBet = 0
	            el.draggable = false  if el.draggable?
	            #utils.removeEventHandler el, "dragstart", handleDragStart, false
	          else
	            gamePlayView.getDraggableBetsClass el	            
	            el.dragBet = 1
	            el.draggable = true  if el.draggable?
	            dragdrop.addDrag el
	            #utils.addEventHandler el, "dragstart", handleDragStart, false
	          gamePlayView.enablePlayBoutton playButtonEl
	          _results.push utils.addEventHandler(playButtonEl, "click", sendPlaceBetToServer, true)
	          (if 0 <= roundBets then i++ else i--)
	        _results
	
	    drawBetPanel = ->
	      betsPanelIndexVO = {}
	      betsPanel = gamePlayView.getGameBetPanelEl()
	      betsPanel.innerHTML = ""
	      _results = []
	      i = 0
	      while (if 0 <= roundBets then i < roundBets else i > roundBets)
	        currentBetId = "bet_" + i
	        divNbrPanel = gamePlayView.createliElem()
	        gamePlayView.getDraggableBetsClass divNbrPanel
	        divNbrPanel.id = currentBetId
	        divNbrPanel.draggable = true  if divNbrPanel.draggable?
	        #utils.addEventHandler divNbrPanel, "dragstart", handleDragStart, false
	        dragdrop.addDrag divNbrPanel
	        divAnc = gamePlayView.createAHrefElem()
	        gamePlayView.getSpaceClass divAnc
	        divAnc.id = "new-" + i
	        bets[i] = divAnc.id
	        divNbrPanel.appendChild divAnc
	        betsPanel.appendChild divNbrPanel
	        betsPanelIndexVO[currentBetId] = divNbrPanel
	        _results.push flag_roundBetsDrawn = true
	        (if 0 <= roundBets then i++ else i--)
	      _results
	      
	    		
		#fired when tiles are lifted from the upper basket
#	    handleDragStart = (e) ->
#	      utils.log e.target.id
#	      gamePlayView.setCustomDragImage e
#	      e.dataTransfer.setData internalDNDType, @id
#	      gamePlayView.addMoveClass e.target	      
#	      utils.log e.target + " : " + e.target.className
#	      utils.log "drag started!"
		
		#fired when tiles are lifted from the board
#	    handleDragStartWithinBoard = (e) ->
#	      betId = @getAttribute("placedBetId")
#	      gamePlayView.setCustomDragImage e
#	      e.dataTransfer.setData internalDNDType, betId
#	      gamePlayView.addMoveClass e.target
#	      utils.log e.target, e.target.className
#	      utils.log "drag started!"
	
	    
#	    $('.draggableBets').draggable
#	      start: (e, ui) ->
#	      	$(@).css
#	      		opacity: '0'
#	      	true
#      
#	      stop: (e, ui) ->
#	      	$(@).css
#	      		opacity: '1'
#	      true
#
#	    $('.box-blank').droppable
#	      drop: (e, ui) ->
##	      	ui.draggable.remove()
##	      	ui.helper.remove()
#	      	
#	      	$(@).addClass 'box-newBet'
#	      	
#	      true
        
	    drawGameBoard = ->
	      gameWallDiv = gamePlayView.getGameWall(board_X,board_Y)
	      tilesIdxVOs = gamePlayView.getTilesIdxVOs()
	      
	      #utils.addEventHandler gameWallDiv, "drop", handleDropNew, false
	      #utils.addEventHandler gameWallDiv, "dragover", handleDragoverNew, false
	      #utils.addEventHandler gameWallDiv, "dragenter", handleDragEnterNew, false
	      #utils.addEventHandler gameWallDiv, "dragleave", handleDragleave, false
	      	      
	
	    getTileClass = (tileClassOverLoadObj) ->
	      if flag_zoomTrue
	        if tileClassOverLoadObj
	          if tileClassOverLoadObj.N
	            if tileClassOverLoadObj.Z
	              return " " + tileClassOverLoadObj.N + " " + tileClassOverLoadObj.Z
	            else
	              return " " + tileClassOverLoadObj.N + " "
	      else
	        return " " + tileClassOverLoadObj.N + " "  if tileClassOverLoadObj.N  if tileClassOverLoadObj
	      " "
	
	    refreshGameBoard = ->
	      csBlankTileClassName = getTileClass(gamePlayView.tileClassOverload.BASE_TILE_CLASS)
	      currentTileClass = ""
	      currentTileVal = ""
	      currentEl = null
	      currentSeatId = zzGlobals.clientVars[zzGlobals.clientCodes.USER_SEAT_ID]
	      currentTilePriority = 0
	      for tileIdx of tilesIdxVOs
	        flag = false
	        continue  unless __hasProp_.call(tilesIdxVOs, tileIdx)
	        currentEl = tilesIdxVOs[tileIdx]
	        currentTilePriority = 0
	        currentTileClass = csBlankTileClassName
	        dropEnable = true
	        currentEl.draggable = false
	        currentEl.dragBet = 0
	        currentTileVal = ""
	        oldDraggableState = currentEl.draggable
	        if boardVOs[tileIdx]?
	          if boardVOs[tileIdx][boardVOCodes.FIGURE_ID]
	            currentFigId = boardVOs[tileIdx][boardVOCodes.FIGURE_ID]
	            if currentFigId.indexOf("_NUMERIC_12") isnt -1 or currentFigId.indexOf("_NUMERIC_13") isnt -1 or currentFigId.indexOf("_NUMERIC_14") isnt -1 or currentFigId.indexOf("_NUMERIC_15") isnt -1 or currentFigId.indexOf("_NUMERIC_16") isnt -1 or currentFigId.indexOf("_NUMERIC_17") isnt -1
	              dropEnable = false
	              currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.JOKER_BET)
	              currentTileVal = ""
	            else unless currentFigId.indexOf("_NUMERIC_11") is -1
	              dropEnable = false
	              currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.SUPPER_JOKER_BET)
	              currentTileVal = ""
	          unless boardVOs[tileIdx][boardVOCodes.FIGURE_ID]
	            currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.OTHER_TURN)
	            currentTileVal = ""
	          playersObjs = boardVOs[tileIdx][boardVOCodes.PLAYER_INFO_OBJ]
	          for playerSeatId of playersObjs
	            if playerSeatId is currentSeatId
	              dropEnable = false
	              currentFigId = boardVOs[tileIdx][boardVOCodes.FIGURE_ID]
	              if currentFigId
	                currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.PRV_CURRPLYR_CORRECT_TILE)
	                utils.log "figureDetailsVO", currentFigId
	                if currPlayerFigVOs[currentFigId] is figureDetailsVO[currentFigId]
	                  currentTilePriority = 10
	                  currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.CURR_PLYR_FIG_COMPLETE)
	                  if currentFigId.indexOf("_NUMERIC_12") isnt -1 or currentFigId.indexOf("_NUMERIC_13") isnt -1 or currentFigId.indexOf("_NUMERIC_14") isnt -1 or currentFigId.indexOf("_NUMERIC_15") isnt -1 or currentFigId.indexOf("_NUMERIC_16") isnt -1 or currentFigId.indexOf("_NUMERIC_17") isnt -1
	                    currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.JOKER_BET)
	                    flag = true
	                  else unless currentFigId.indexOf("_NUMERIC_11") is -1
	                    currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.SUPPER_JOKER_BET)
	                    flag = true
	                if flag
	                  currentTileVal = ""
	                else
	                  currentTileVal = boardVOs[tileIdx][boardVOCodes.TILE_COUNT]
	              else
	                currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.PRV_CURRPLYR_INCORRECT_TILE)
	                currentTileVal = " "
	              break
	        lastRound = false
	        lastRound = true  if zzGlobals.roomVars.FR is "1"
	        unless lastRound
	          if (window.currentBets[tileIdx]?) and window.currentBets[tileIdx] isnt null
	            dropEnable = false
	            currentEl.draggable = true
	            currentEl.dragBet = 1
	            currentEl.setAttribute "placedBetId", window.currentBets[tileIdx]
	            currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.CURR_PLYR_NEWBET)
	            #utils.addEventHandler currentEl, "dragstart", handleDragStartWithinBoard, false
	            dragdrop.addDragClone currentEl
	            currentTileVal = ""
	          if currentTilePriority < 10 and (betChangeVOs[tileIdx]?)
	            if parseInt(betChangeVOs[tileIdx]) is 1
	              currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.CURR_PLYR_NEWBET)
	              currentTileVal = ""
	            else
	              currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.CURR_PLYR_NEWBET)
	              currentTileVal = ""
	        currentTileClass += " boardRowLastTile "  if (parseInt(tileIdx) % board_X) is 0
	        dropEnableVal = null
	        utils.log "[dropEnable:", dropEnable, ",currentEl:", currentEl.getAttribute("droppable")  if currentEl.id is "boardTile-35" and isDevEnvironment
	        switch currentEl.getAttribute("droppable")
	          when "-1"
	            dropEnableVal = "1"  if dropEnable is true
	          when "0"
	            dropEnableVal = "1"  if dropEnable is true
	          when "1"
	            dropEnableVal = "0"  if dropEnable is false
	          when "2"
	            dropEnableVal = "0"  if dropEnable is false
	        currentEl.setAttribute "droppable", "1"  unless currentEl.getAttribute("droppable")?
	        currentEl.setAttribute "droppable", dropEnableVal  if dropEnableVal isnt null
	        currentEl.className = currentTileClass
	        currentEl.innerHTML = currentTileVal
	      resetDropZoneOnGameBoard()
	
	    resetDropZoneOnGameBoard = ->
	      for tileIdx of tilesIdxVOs
	        continue  unless __hasProp_.call(tilesIdxVOs, tileIdx)
	        currentEl = tilesIdxVOs[tileIdx]
	        switch currentEl.getAttribute("droppable")
	          when "1"
	            currentEl.setAttribute("droppable", "2")
	          when "0"
	            currentEl.setAttribute("droppable", "-1")
#	
#	    handleDragoverNew = (e) ->
#	      e.preventDefault()  if e.preventDefault
#	      false
#	
#	    handleDragEnterNew = (e) ->
#	      if e.dataTransfer?
#	        betId = e.dataTransfer.getData(internalDNDType)
#	        if betId?
#	          if (e.target?) and (e.target.getAttribute?) and e.target.getAttribute("droppable") is "2"
#	            gamePlayView.addBoxDropHoverClass e.target	            
#	            return true
#	      false
	
	    window.handleDropNew = (e,ui) ->
	    	
	      #play tile drop sound if sound enable
	      sound.playTitleDropSound()
	      
	      utils.removeClassName e.target, "box-drophover"  if e.target?
#	    
#	      e.preventDefault()  if e.preventDefault
#	      e.stopPropagation()  if e.stopPropagation

	      if(ui.draggable.attr('placedbetid'))
	      	betId = ui.draggable.attr('placedbetid')
	      	ui.draggable.removeAttr('placedbetid');
	      else
	      	betId = ui.draggable.attr('id')
	      
	      if (betId?) and betId isnt ""
	        if e.target.getAttribute("droppable") is "2"
	          unless window.currentBets[e.target.getAttribute("tileidx")]
	            window.currentBetsIdx[betId] = e.target.getAttribute("tileidx")
	            window.currentBets = {}
	            for betd of window.currentBetsIdx
	              continue  unless __hasProp_.call(window.currentBetsIdx, betd)
	              betTileIdx = window.currentBetsIdx[betd]
	              window.currentBets[betTileIdx] = betd
	            refreshGameBoard()
	            reDrawBetsPanel()
	            return true
	      true
	
#	    handleDragleave = (e) ->
#	      utils.removeClassName e.target, "box-drophover"  if e.target?
	
	    sendPlaceBetToServer = ->
	      utils.log "bet Validation before sen  ding the request t  o server"
	      if tutorial is true
	      	return
	      playButtonEl = gamePlayView.getPlayButtonEl()
	      if playButtonEl.className is "bet_done"
	      	confirmBox "Not so fast.... lets wait for your friends to play their turn"
	      	return
	      betStr = ""
	      betCtr = 0
	      
	      #play tile play sound if sound enable
	      sound.playPlayButtonSound()

	      for betPanelId of window.currentBetsIdx
	        continue  unless __hasProp_.call(window.currentBetsIdx, betPanelId)
	        betTileId = window.currentBetsIdx[betPanelId]
	        betId = betTileId.replace(/\bboardTile-\b/, "")
	        betStr += (if betStr is "" then betId else ":" + betId)
	        ++betCtr
	        utils.log "bets[" + betCtr + "] : " + betId
	      if betStr is ""
	        utils.log "No bets placed!"
	        confirmBox "No bets placed!"
	        return false
	      else unless betCtr is 9
	        utils.log "Bets count is less then 9!"
	        confirmBox "Not so fast... please place all of your 9 tiles!"
	        return false
	      else
	        utils.log "every thing is fine's end the bets to the server"
	        $('.box-newBet').draggable('disable')
	        placeBetsToServer betStr
	      false
	
	    parseCoord = (coordNum, boardX, boardY) ->
	      boardX = boardX or board_X
	      boardY = boardY or board_Y
	      coordX = coordNum % board_X
	      coordY = coordNum / board_X
	      x: coordX
	      y: coordY
	
	    parseToGameBoard = (mapType, mapData) ->
	      if (mapType?) and (mapType.code?) and (mapType.className?)
	        ctr = 0
	        for curCoordId of mapData
	          curCoordObj = mapData[curCoordId]
	          jQuery("#boardTile-" + (parseInt(curCoordId))).addClass(mapType.className)
	      true

	    betChange = ->
	    	reDrawBetsPanel()
	    	refreshGameBoard()
	    	
	    jDocument.bind zzEvents.CLIENT_BETS_PLACED, betChange
		
	    drawResponseTiles = (responseObj) ->
	      utils.log "responseObj in drawResponseTiles(): " + responseObj
	      _results = []
	      for tileId of responseObj
	        correctFlag = responseObj[tileId]	        
	        _results.push gamePlayView.markMyTurnTiles(correctFlag)
	      _results
	
	    updateBoardVars = (evt, val) ->
	      currPlayerFigVOs = {}
	      playerBetTiles = {}
	      boardVOs = {}
	      boardObjs = jQuery.parseJSON(val)
	      for i of boardObjs
	        boardStr = boardObjs[i]
	        boardObj = jQuery.parseJSON(boardStr)
	        if boardObj["PR"]?
	          boardObj["PR"] = jQuery.parseJSON(boardObj["PR"])
	          for seatId of boardObj["PR"]
	            if boardObj["CF"]?
	              playerTiles = {}
	              playerTiles = playerBetTiles[seatId]  if playerBetTiles[seatId]
	              playerTiles[i] = 1
	              playerBetTiles[seatId] = playerTiles
	              if zzGlobals.clientVars[zzGlobals.clientCodes.USER_SEAT_ID] is seatId
	                noOfTiles = 0
	                noOfTiles = currPlayerFigVOs[boardObj["CF"]]  if currPlayerFigVOs[boardObj["CF"]]?
	                currPlayerFigVOs[boardObj["CF"]] = ++noOfTiles
	        boardVOs[i] = boardObj
	      refreshGameBoard()
	
	    updateFigureDetails = (evt, val) ->
	      figureDetailsVO = {}
	      figureDetailsVO = jQuery.parseJSON(val)  if val?
	      		  	
	    jQuery ->	
	      jTileHoverDiv = gamePlayView.getShowOnMouseOverEl()	      
	      utils.log "jTileHoverDiv : " + jTileHoverDiv
	      
	      isMousingOver = false
	      jQuery("#gamewall").delegate ".box-previousRoundOtherPlayer,.box-previousRoundCurrentPlayerIncorrect,.box-previousRoundCurrentPlayerCorrect,.box-dizitCompleted,.joker,.superJoker", "mouseover mouseout", (e) ->
	        usersObject = zzGlobals.dataObjVars.AP

	        if e.type is "mouseover" and not isMousingOver
	          tileIdx = @getAttribute("tileIdx")
	          utils.log "offset :" + @offsetLeft + " : " + @offsetTop
	          if boardVOs[tileIdx]?
	            
	            # tile palced by users
	            gamePlayView.showBetPlacedBy(boardVOs,usersObject,boardVOCodes,tileIdx)
	            
	            tileNo = parseInt(tileIdx)
	            noOfRows = tileNo / board_X
	            noOfCols = tileNo % board_Y
	            leftMajor = (if noOfRows > board_Y / 2 then false else true)
	            topMajor = (if noOfCols > board_X / 2 then false else true)
	            elLeft = e.pageX + 10
	            elTop = e.pageY
	            elLeft = elLeft - jTileHoverDiv.width() - 10  if @offsetLeft > 300
	            elTop = elTop - jTileHoverDiv.height()  if @offsetTop > 400
	            
	            jTileHoverDiv.show().offset
	              top: elTop
	              left: elLeft
	          isMousingOver = true
	          utils.log "enlarged"
	        else if e.type is "mouseout"
	          jTileHoverDiv.hide()
	          isMousingOver = false
	          utils.log "resetting"
	          
	      jDocument.bind "client:" + zzGlobals.clientCodes.UINFO, usersRecordCall
	      jDocument.bind "client:" + zzGlobals.clientCodes.ALL_PAST_GAME, usersRecordCall
	      jDocument.bind "client:" + zzGlobals.msgCodes.RIGHT_HUD, usersRecordCall
	      jDocument.bind "room:" + zzGlobals.roomCodes.FIGURE_DETAILS, updateFigureDetails
	      jDocument.bind "room:" + zzGlobals.roomCodes.BOARD_XY, initBoard
	      jDocument.bind "room:" + zzGlobals.roomCodes.ROUND_NOOFBETS, initRoundBets
	      jDocument.bind "room:" + zzGlobals.roomCodes.ROOM_ALLROUNDS, parseRounds
	      jDocument.bind "room:" + zzGlobals.roomCodes.BOARDVARS, updateBoardVars
	      jDocument.bind "room:" + zzGlobals.roomCodes.CURRENTROUND, refreshRoundsPanel
	      jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, setPlayersInfo
#	      jDocument.bind zzEvents.SERVER_MESSAGE, messageListener
	
	    ZalerioGame
	  ).call(this)
	
	# call My level popup function
	usersRecordCall = ->
	  	if typeof zzGlobals.clientVars.UINFO isnt 'undefined' and zzGlobals.clientVars.UINFO isnt 'USERINFO'
	  		usersObjectUINFO = jQuery.parseJSON(zzGlobals.clientVars.UINFO)
	
		#parse APG
	  	if typeof zzGlobals.clientVars.APG isnt 'undefined' and zzGlobals.clientVars.APG isnt 'ALL_PAST_GAME'
	  		usersObjectAPG = jQuery.parseJSON(zzGlobals.clientVars.APG)
	  		scoreArray = []
	  		gameIdArray = []
	  		for i of usersObjectAPG
	  			usersObjectAPG[i] = jQuery.parseJSON(usersObjectAPG[i])
	  			usersObjectAPG[i].PLRS = jQuery.parseJSON(usersObjectAPG[i].PLRS)  			
	  			scoreArray[i] = parseInt(usersObjectAPG[i].EDL)
	  			gameIdArray.push i
	  			for seatId of usersObjectAPG[i].PLRS
	  				usersObjectAPG[i].PLRS[seatId] = jQuery.parseJSON(usersObjectAPG[i].PLRS[seatId])
	  		gameIdArray.sort (x, y) ->
	  			scoreArray[y] - scoreArray[x]
	  	usersInfo = 
	  		RH : zzGlobals.msgVars.RH
	  		APG : usersObjectAPG
	  		APG_SORT : gameIdArray
	  		UINFO: usersObjectUINFO
	  	
	  	myLevel usersInfo
	
	currentGameUsersData = {}
	# set ALL_PLAYER_INFO (AP) to currentGameUsersData, trigger when ALL_PLAYER_INFO (AP) update
	setPlayersInfo = ->    	
    	usersObject = jQuery.parseJSON(zzGlobals.roomVars.AP)
    	for i of usersObject
	    	usersObject[i] = jQuery.parseJSON(usersObject[i])
	    	usersObject[i].PLRS = jQuery.parseJSON(usersObject[i].PLRS)
	    	scoreArray = []
	    	seatIdArray = []	
    		for seatId of usersObject[i].PLRS
    			usersObject[i].PLRS[seatId] = jQuery.parseJSON(usersObject[i].PLRS[seatId])
    			scoreArray[seatId] = parseInt(usersObject[i].PLRS[seatId].PSC)
    			seatIdArray.push seatId
    		seatIdArray.sort (x, y) ->
    			scoreArray[y] - scoreArray[x]
    		usersObject[i].PLSC = {}
    		for x of seatIdArray
    			usersObject[i].PLSC[x] = seatIdArray[x]
 			
    	zzGlobals.dataObjVars.AP = usersObject[i]
    	jDocument.trigger "dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, zzGlobals.dataObjVars.AP
	
	if document.domain is "localhost" or document.domain is "zl.mobicules.com"
  	# if development environment version
    	utils.addEventHandler document.getElementById("bottomHUDbuttons-more"), "click", sendOriginalFigsRequest, false
    	window.sendOriginalFigsRequest = sendOriginalFigsRequest
	
	sendOriginalFigsRequest = ->
		jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|OF" ]
    
	rematchCall = (e,rematchPlayerFBID)->
	  	e.preventDefault() if e.preventDefault
	  	sound.playOtherButtonSound()
	  	if typeof rematchPlayerFBID is "undefined"
	  		plrs = zzGlobals.dataObjVars.AP.PLRS
	  		_results = []
	  		gameId = gameInstId
	  		for seatID of plrs 			
	  			_results.push(plrs[seatID].PFB)
	  	else
	  		_results = []
	  		_results.push(rematchPlayerFBID)
	  	callRematchFunction(_results,'Rematch',gameId)
	  	jQuery('.zalerio_popup').css('display','none')
	  	return
	
	utils.addEventHandler document.getElementById('rematch'), 'click' ,rematchCall, false
      
	placeBetsToServer = (betStr) ->
    	jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|PB", "BI|" + betStr ]
    
    sendPlaceBetRequest = ->
	    utils.log "bet Validation before sending the request t  o server"
	    betStr = ""
	    for bet of bets
	      betStr += (if betStr is "" then bets[bet] else ":" + bets[bet])
	      utils.log "bets[" + bet + "] : " + bets[bet]
	    utils.log "[betStr: " + betStr + "]"
	    if betStr is ""
	      utils.log "No bets placed!"
	      messagePopup "No bets placed!"
	      return false
	    else
	      utils.log "every thing is fine end the bets to the server"
	      placeBetsToServer betStr
	    false