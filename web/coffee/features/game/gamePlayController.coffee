define ["../../helper/confirmBox"], (confirmBox) ->
	
	addEventHandler = (node, evtType, func, isCapture) ->
    if window and window.addEventListener
      node.addEventListener evtType, func, isCapture
    else
      node.attachEvent "on" + evtType, func
	
	removeEventHandler = (node, evtType, func, isCapture) ->
	    if window and window.removeEventListener
	      node.removeEventListener evtType, func, isCapture
	    else
	      node.detachEvent "on" + evtType, func
	
	removeClassName = (node, cls) ->
	    reg = undefined
	    if (node?) and node.className
	      reg = new RegExp("(\\s|^)" + cls + "(\\s|$)")
	      node.className = node.className.replace(reg, " ")
	      
	bets = {}
	boardVo = {}
	responseVo = {}
      
	ZalerioGame = (->
	    ZalerioGame = ->
	    _this = this
	    docElems = {}
	    DEFAULT_PLAYER_IMG_URL = "/olotheme/images/fbDefaultUser.gif"
	    tilesIdxVOs = {}
	    currentBets = {}
	    currentBetsIdx = {}
	    betsPanelIndexVO = {}
	    currentRoundBidPlaced = -1
	    internalDNDType = "text/x-betiddata"
	    tileClassOverload =
	      BASE_TILE_CLASS:
	        N: "box-blank box-black"
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
	
	    flag_zoomTrue = false
	    currPlayerFigVOs = {}
	    playerBetTiles = {}
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
	
	    betChangeVOs = {}
	    boardVOs = {}
	    # message name container
	    zalerioCMDListners =
	      ORIG_FIGS: "OF"
	      BET_RESPONSE: "PB"
	      BET_CHANGES: "CB"
	      RESIGN_GAME:"RG"
	      CLOSE_INVITE : "CI"
	      RIGHT_HUD:"MT"
	      DECLINE_STATUS:"DG"
	
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
	      console.log "BoardXY : " + zzGlobals.roomVars[zzGlobals.roomCodes.BOARD_XY] if isDevEnvironment
	      boardDimension = zzGlobals.roomVars[zzGlobals.roomCodes.BOARD_XY].split(":")
	      board_X = boardDimension[0]
	      board_Y = boardDimension[1]
	      drawGameBoard()
	
	    initRoundBets = ->
	      console.log "Round Bets : " + zzGlobals.roomVars[zzGlobals.roomCodes.ROUND_NOOFBETS] if isDevEnvironment
	      roundBets = zzGlobals.roomVars[zzGlobals.roomCodes.ROUND_NOOFBETS]
	      reDrawBetsPanel()
	
	    parseRounds = (el, val) ->
	      console.log "parse Ar data : " + zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ALLROUNDS] if isDevEnvironment
	      obj = jQuery.parseJSON(val)
	      roundVOs = {}
	      for i of obj
	        roundVOs[i] = jQuery.parseJSON(obj[i])
	      refreshRoundsPanel()
	
	    refreshRoundsPanel = ->
	      drawRoundsPanel()  unless flag_roundDrawn
	      lastRoundId = null
	      lastEl = null
	      for roundId of roundVOs
	        el = roundVOsIdx[roundId]
	        roundEndTimeTTL = parseInt(roundVOs[roundId]["EM"])
	        if roundEndTimeTTL > 0
	          el.className = "notPlayedRound"
	        else
	          el.className = "doneRound"
	        el.className = "currentRound"  if roundId is zzGlobals.roomVars[zzGlobals.roomCodes.CURRENTROUND]
	        lastEl = el
	        lastRoundId = roundId
	      if lastEl and lastRoundId
	        if lastRoundId is zzGlobals.roomVars[zzGlobals.roomCodes.CURRENTROUND]
	          lastEl.className = "currentFinalRound"
	        else
	          lastEl.className = "finalRound"
	
	    drawRoundsPanel = (elementDiv)->
	      if typeof elementDiv is 'undefined'
	      	gameRoundulElem = document.getElementById("gameScore-round")
	      	roundVOsIdx = {}
	      else
	      	gameRoundulElem = elementDiv
	      	roundVOsIdxRightHUD = {}
	      	
	      gameRoundulElem.innerHTML = ""
	      cnt = 0
	      aHrefElemClone = null
	      
	      for roundId of roundVOs
	        cnt++
	        liElem = document.createElement("li")
	        aHrefElem = document.createElement("a")
	        aHrefElem.href = "#"
	        aHrefElem.className = "notPlayedRound"
	        aHrefElem.innerHTML = roundVOs[roundId]["RN"]
	        console.log "roundVo : ", roundId if isDevEnvironment
	        liElem.appendChild aHrefElem
	        if typeof elementDiv is 'undefined'
	        	roundVOsIdx[roundId] = aHrefElem
	       	else
	       		roundVOsIdxRightHUD[cnt] = aHrefElem
	       		
	        gameRoundulElem.appendChild liElem
	        aHrefElemClone = aHrefElem
	      if cnt > 0
	        aHrefElemClone.innerHTML = "Final"
	        flag_roundDrawn = true
	
	    reDrawBetsPanel = ->
	      try
	      	if tutorial is true
	      		return
	      playButtonEl = document.getElementById("placeBetOnServer")
	      if currentRoundBidPlaced > 0
	        i = 0
	        while (if 0 <= roundBets then i < roundBets else i > roundBets)
	          currentBetId = "bet_" + i
	          el = betsPanelIndexVO[currentBetId]
	          el.className = "betsAlreadyPlaced"
	          el.dragBet = 0
	          el.draggable = false  if el.draggable?
	          removeEventHandler el, "dragstart", handleDragStart, false
	          (if 0 <= roundBets then i++ else i--)
	        playButtonEl.href = "#Already Placed Bets"
	        playButtonEl.className = 'bet_done'
	        playButtonEl.parentNode.setAttribute "class", "bottomHUDbuttons-play-gray"
	        removeEventHandler playButtonEl, "click", sendPlaceBetRequest, true
	      else
	        count = 0
	        for k of currentBets
	          count++
	        if count < 9
	          playButtonEl.parentNode.setAttribute "class", "bottomHUDbuttons-play-gray"
	        else
	          playButtonEl.parentNode.setAttribute "class", ""
	        drawBetPanel()  unless flag_roundBetsDrawn
	        _results = []
	        i = 0
	        while (if 0 <= roundBets then i < roundBets else i > roundBets)
	          currentBetId = "bet_" + i
	          el = betsPanelIndexVO[currentBetId]
	          flag_alreadyUsed = false
	          for k of currentBets
	            continue  unless __hasProp_.call(currentBets, k)
	            usedBetId = currentBets[k]
	            if usedBetId is currentBetId
	              flag_alreadyUsed = true
	              break
	          if flag_alreadyUsed
	            el.className = "usedDraggableBets"
	            el.dragBet = 0
	            el.draggable = false  if el.draggable?
	            removeEventHandler el, "dragstart", handleDragStart, false
	          else
	            el.className = "draggableBets"
	            el.dragBet = 1
	            el.draggable = true  if el.draggable?
	            addEventHandler el, "dragstart", handleDragStart, false
	          playButtonEl.href = "#Place Bets"
	          playButtonEl.className = ""
	          _results.push addEventHandler(playButtonEl, "click", sendPlaceBetToServer, true)
	          (if 0 <= roundBets then i++ else i--)
	        _results
	
	    drawBetPanel = ->
	      betsPanelIndexVO = {}
	      betsPanel = document.getElementById("gameBetPanel")
	      betsPanel.innerHTML = ""
	      _results = []
	      i = 0
	      while (if 0 <= roundBets then i < roundBets else i > roundBets)
	        currentBetId = "bet_" + i
	        divNbrPanel = document.createElement("li")
	        divNbrPanel.className = "draggableBets"
	        divNbrPanel.id = currentBetId
	        divNbrPanel.draggable = true  if divNbrPanel.draggable?
	        addEventHandler divNbrPanel, "dragstart", handleDragStart, false
	        divAnc = document.createElement("a")
	        divAnc.className = "nbrs"
	        divAnc.id = "new-" + i
	        bets[i] = divAnc.id
	        divNbrPanel.appendChild divAnc
	        betsPanel.appendChild divNbrPanel
	        betsPanelIndexVO[currentBetId] = divNbrPanel
	        _results.push flag_roundBetsDrawn = true
	        (if 0 <= roundBets then i++ else i--)
	      _results
	
	    customSetDragImage = (e) ->
	      dragIcon = document.createElement("img")
	      dragIcon.src = baseUrl + "/images/zalerio_1.2/3.ingame_board/stand/stand_tile_pickup.png"
	      dragIcon.width = 45
	      e.dataTransfer.setDragImage dragIcon, 35, 30
	
	    handleDragStart = (e) ->
	      console.log e.target.id if isDevEnvironment
	      customSetDragImage e
	      e.dataTransfer.setData internalDNDType, @id
	      e.target.className += " moving"
	      if isDevEnvironment
	      	console.log e.target, e.target.className
	      	console.log "drag started!"
	
	    handleDragStartWithinBoard = (e) ->
	      betId = @getAttribute("placedBetId")
	      customSetDragImage e
	      e.dataTransfer.setData internalDNDType, betId
	      e.target.className += " moving"
	      if isDevEnvironment
	      	console.log e.target, e.target.className
	      	console.log "drag started!"
	
	    parseCoordsAsNum = (coordX, coordY) ->
	      (coordY * board_X) + coordX
	
	    drawGameBoard = ->
	      gameWallDiv = document.getElementById("gamewall")
	      gameWallDiv.setAttribute "dropzone", "move s:text/x-betiddata"
	      addEventHandler gameWallDiv, "drop", handleDropNew, false
	      addEventHandler gameWallDiv, "dragover", handleDragoverNew, false
	      addEventHandler gameWallDiv, "dragenter", handleDragEnterNew, false
	      addEventHandler gameWallDiv, "dragleave", handleDragleave, false
	      gameWallDiv.innerHTML = ""
	      i = 0
	      while (if 0 <= board_Y then i < board_Y else i > board_Y)
	        j = 0
	        while (if 0 <= board_X then j < board_X else j > board_X)
	          csBlankTileClassName = "box-blank box-black"
	          gameWallTileDiv = document.createElement("div")
	          gameWallTileDiv.className = csBlankTileClassName
	          tileIdx = parseCoordsAsNum(j, i)
	          gameWallTileDiv.id = "boardTile-" + tileIdx
	          gameWallTileDiv.setAttribute "tileIdx", tileIdx
	          gameWallTileDiv.className = csBlankTileClassName + " boardRowLastTile"  if j is (board_X - 1)
	          tilesIdxVOs[tileIdx] = gameWallTileDiv
	          gameWallDiv.appendChild gameWallTileDiv
	          (if 0 <= board_X then j++ else j--)
	        (if 0 <= board_Y then i++ else i--)
	      elBr = document.createElement("br")
	      elBr.setAttribute "clear", "all"
	      gameWallDiv.appendChild elBr
	
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
	      csBlankTileClassName = getTileClass(tileClassOverload.BASE_TILE_CLASS)
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
	              currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.JOKER_BET)
	              currentTileVal = ""
	            else unless currentFigId.indexOf("_NUMERIC_11") is -1
	              dropEnable = false
	              currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.SUPPER_JOKER_BET)
	              currentTileVal = ""
	          unless boardVOs[tileIdx][boardVOCodes.FIGURE_ID]
	            currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.OTHER_TURN)
	            currentTileVal = ""
	          playersObjs = boardVOs[tileIdx][boardVOCodes.PLAYER_INFO_OBJ]
	          for playerSeatId of playersObjs
	            if playerSeatId is currentSeatId
	              dropEnable = false
	              currentFigId = boardVOs[tileIdx][boardVOCodes.FIGURE_ID]
	              if currentFigId
	                currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.PRV_CURRPLYR_CORRECT_TILE)
	                console.log "figureDetailsVO", currentFigId if isDevEnvironment
	                if currPlayerFigVOs[currentFigId] is figureDetailsVO[currentFigId]
	                  currentTilePriority = 10
	                  currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.CURR_PLYR_FIG_COMPLETE)
	                  if currentFigId.indexOf("_NUMERIC_12") isnt -1 or currentFigId.indexOf("_NUMERIC_13") isnt -1 or currentFigId.indexOf("_NUMERIC_14") isnt -1 or currentFigId.indexOf("_NUMERIC_15") isnt -1 or currentFigId.indexOf("_NUMERIC_16") isnt -1 or currentFigId.indexOf("_NUMERIC_17") isnt -1
	                    currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.JOKER_BET)
	                    flag = true
	                  else unless currentFigId.indexOf("_NUMERIC_11") is -1
	                    currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.SUPPER_JOKER_BET)
	                    flag = true
	                if flag
	                  currentTileVal = ""
	                else
	                  currentTileVal = boardVOs[tileIdx][boardVOCodes.TILE_COUNT]
	              else
	                currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.PRV_CURRPLYR_INCORRECT_TILE)
	                currentTileVal = " "
	              break
	        lastRound = false
	        lastRound = true  if zzGlobals.roomVars.FR is "1"
	        unless lastRound
	          if (currentBets[tileIdx]?) and currentBets[tileIdx] isnt null
	            dropEnable = false
	            currentEl.draggable = true
	            currentEl.dragBet = 1
	            currentEl.setAttribute "placedBetId", currentBets[tileIdx]
	            currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.CURR_PLYR_NEWBET)
	            addEventHandler currentEl, "dragstart", handleDragStartWithinBoard, false
	            currentTileVal = ""
	          if currentTilePriority < 10 and (betChangeVOs[tileIdx]?)
	            if parseInt(betChangeVOs[tileIdx]) is 1
	              currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.CURR_PLYR_NEWBET)
	              currentTileVal = ""
	            else
	              currentTileClass = csBlankTileClassName + getTileClass(tileClassOverload.CURR_PLYR_NEWBET)
	              currentTileVal = ""
	        currentTileClass += " boardRowLastTile "  if (parseInt(tileIdx) % board_X) is 0
	        dropEnableVal = null
	        console.log "[dropEnable:", dropEnable, ",currentEl:", currentEl.getAttribute("droppable")  if currentEl.id is "boardTile-35" and isDevEnvironment
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
	      _results = []
	      for tileIdx of tilesIdxVOs
	        continue  unless __hasProp_.call(tilesIdxVOs, tileIdx)
	        currentEl = tilesIdxVOs[tileIdx]
	        switch currentEl.getAttribute("droppable")
	          when "1"
	            _results.push currentEl.setAttribute("droppable", "2")
	          when "0"
	            _results.push currentEl.setAttribute("droppable", "-1")
	          else
	            _results.push undefined
	      _results
	
	    handleDragoverNew = (e) ->
	      e.preventDefault()  if e.preventDefault
	      false
	
	    handleDragEnterNew = (e) ->
	      if e.dataTransfer?
	        betId = e.dataTransfer.getData(internalDNDType)
	        if betId?
	          if (e.target?) and (e.target.getAttribute?) and e.target.getAttribute("droppable") is "2"
	            e.target.className += " box-drophover"
	            return true
	      false
	
	    handleDropNew = (e) ->
	      titledrop.play()  if playSound
	      removeClassName e.target, "box-drophover"  if e.target?
	      e.preventDefault()  if e.preventDefault
	      e.stopPropagation()  if e.stopPropagation
	      betId = e.dataTransfer.getData(internalDNDType)
	      if (betId?) and betId isnt ""
	        if e.target.getAttribute("droppable") is "2"
	          unless currentBets[e.target.getAttribute("tileidx")]
	            currentBetsIdx[betId] = e.target.getAttribute("tileidx")
	            currentBets = {}
	            for betd of currentBetsIdx
	              continue  unless __hasProp_.call(currentBetsIdx, betd)
	              betTileIdx = currentBetsIdx[betd]
	              currentBets[betTileIdx] = betd
	            refreshGameBoard()
	            reDrawBetsPanel()
	            return true
	      false
	
	    handleDragleave = (e) ->
	      removeClassName e.target, "box-drophover"  if e.target?
	
	    sendPlaceBetToServer = ->
	#      jQuery("#placeBetOnServer").live('click',()->
	#      		try{
	#      			jQuery("#placeBetOnServer").append(loader_small);
	#      		}catch(err){}
	#      )
	      console.log "bet Validation before sen  ding the request t  o server" if isDevEnvironment
	      if tutorial is true
	      	return
	      playButtonEl = document.getElementById("placeBetOnServer");
	      if playButtonEl.className is "bet_done"
	      	confirmBox.messagePopup "Not so fast.... lets wait for your friends to play their turn"
	      	return
	      betStr = ""
	      betCtr = 0
	      playbutton.play()  if playSound
	      for betPanelId of currentBetsIdx
	        continue  unless __hasProp_.call(currentBetsIdx, betPanelId)
	        betTileId = currentBetsIdx[betPanelId]
	        betId = betTileId.replace(/\bboardTile-\b/, "")
	        betStr += (if betStr is "" then betId else ":" + betId)
	        ++betCtr
	        console.log "bets[" + betCtr + "] : " + betId if isDevEnvironment
	      if betStr is ""
	        console.log "No bets placed!" if isDevEnvironment
	        confirmBox.messagePopup "No bets placed!"
	        return false
	      else unless betCtr is 9
	        console.log "Bets count is less then 9!" if isDevEnvironment
	        confirmBox.messagePopup "Not so fast... please place all of your 9 tiles!"
	        return false
	      else
	        console.log "every thing is fine s    end the bets to the server" if isDevEnvironment
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
	        _results = []
	        for curCoordId of mapData
	          curCoordObj = mapData[curCoordId]
	          _results.push jQuery("#boardTile-" + (parseInt(curCoordId))).addClass(mapType.className)
	        _results
	
	# message listener        
	    messageListener = (event, messageName, broadcastType, fromClientID, roomID, message) ->
	      switch messageName
	      	when zalerioCMDListners.DECLINE_STATUS
	      		 if parseInt(message) is 1
	      		 	   confirmBox.messagePopup(popupMSG.declineInvite())
	      		 	   jQuery(".draggableBets").attr("draggable","false")
	      		 	   jQuery(".resignPopup").hide()
	      		 	   jQuery("#gameBetPanel").hide()
	      	when zalerioCMDListners.RIGHT_HUD
	        	usersObject = jQuery.parseJSON(message)      
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
	
	        	zzGlobals.msgVars.RH = usersObject
	        	console.log 'MT & TT',zzGlobals.msgVars.RH if isDevEnvironment
	        	
	        	jDocument.trigger "client:" + zzGlobals.msgCodes.RIGHT_HUD,usersObject
	        when zalerioCMDListners.CLOSE_INVITE
	        	if 0 is message
	        		confirmBox.messagePopup('Sorry!!! Unable to Close Invite, <br /> Plese try again..');
	        	else
	        		jQuery(".status_show_popup").remove();
	        		jQuery(".gdWrapper").remove();
	        when zalerioCMDListners.RESIGN_GAME
	        	if message is 0
	        		confirmBox.messagePopup('Sorry!!! Unable to Resign, \n Plese try again..');
	        		jQuery("#gameBetPanel").show()
	        	else
	        		resignStatus = 1
	        		jQuery(".draggableBets").attr("draggable","false");
	        		jQuery("#gameBetPanel").hide()
	        		jQuery(".resignPopup").hide();
	        when zalerioCMDListners.ORIG_FIGS
	        	newCoordObj = jQuery.parseJSON(message)
	        	console.log newCoordObj if isDevEnvironment
	        	parseToGameBoard zalerioMapType.ORIG_MAP, newCoordObj
	        when zalerioCMDListners.BET_RESPONSE
	        	parsedObj = jQuery.parseJSON(message)
	        	console.log "zalerioCMDListners.BET_RESPONSE if failed : ", parsedObj if isDevEnvironment
	        when zalerioCMDListners.BET_CHANGES
	        	betChangeVOs = {}
	        	playerBetsChangeObj = jQuery.parseJSON(message)
	        	console.log "zalerioCMDListners.BET_CHANGES : ", playerBetsChangeObj if isDevEnvironment
	        	currentBets = {}
	        	currentBetsIdx = {}
	        	for i of playerBetsChangeObj
	        		if i is "PB"
	        			playerBetTiles = jQuery.parseJSON(playerBetsChangeObj[i])
	        			for tileId of playerBetTiles
	        				betChangeVOs[tileId] = playerBetTiles[tileId]
	        	currentRoundBidPlaced = playerBetsChangeObj["BC"]
	        	jDocument.trigger zzEvents.CLIENT_BETS_PLACED, currentRoundBidPlaced
	        	reDrawBetsPanel()
	        	refreshGameBoard()
	
	    drawResponseTiles = (responseObj) ->
	      console.log "responseObj in drawResponseTiles(): ", responseObj if isDevEnvironment
	      _results = []
	      for tileId of responseObj
	        correctFlag = responseObj[tileId]
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
	      _results
	
	    updateBoardVars = (evt, val) ->
	      currPlayerFigVOs = {}
	      playerBetTiles = {}
	      el = document.getElementById("loadingGame")
	      el.style.display = "none"  if (el?) and (el.style?)
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
	
	    updateFlagZoomTrue = (val) ->
	      if (val?) and val is true
	        flag_zoomTrue = true
	      else
	        flag_zoomTrue = false
	      refreshGameBoard()
	
	    jQuery ->
	      jQuery("#zoomBtm").click ->
	        jQuery(".playersInfo").toggleClass "playersInfoZoom"
	        jQuery(".turnBase").toggleClass "turnBaseZoom"
	        jQuery(".playableArea").toggleClass "playableAreaZoom"
	        jQuery("#gamewall").toggleClass "game_wallZoom"
	        jQuery(".box-MyTurn").toggleClass "box-MyTurnZoom"
	        jQuery(".box-MyWorngTurn").toggleClass "box-MyWorngTurnZoom"
	        jQuery(".box-RevealingNumber").toggleClass "box-RevealingNumberZoom"
	        jQuery(".box-OthersTurn").toggleClass "box-OthersTurnZoom"
	        jQuery(".box-priviousOthersTurn").toggleClass "box-priviousOthersTurnZoom"
	        jQuery(".panelZoom").toggleClass "panelZoomZoom"
	        jQuery(".zoomBtm").toggleClass "zoomBtmZoom"
	        jQuery(".dragFromHere").toggleClass "dragFromHereZoom"
	        jQuery(".nbrs").toggleClass "nbrsZoom"
	        jQuery(".nbrs-Panel").toggleClass "nbrs-PanelZoom"
	        updateFlagZoomTrue (if flag_zoomTrue then false else true)
	
	      jTileHoverDiv = jQuery("#showOnMouseOver")
	      tileHoverDivBids = jTileHoverDiv.find(".roundForBidsCount")[0]
	      jTileHoverDivContent = jQuery(jTileHoverDiv.find(".roundForBidsCountUl")[0])
	      console.log "jTileHoverDiv : ", jTileHoverDiv if isDevEnvironment
	      isMousingOver = false
	      jQuery("#gamewall").delegate ".box-previousRoundOtherPlayer,.box-previousRoundCurrentPlayerIncorrect,.box-previousRoundCurrentPlayerCorrect,.box-dizitCompleted,.joker,.superJoker", "mouseover mouseout", (e) ->
	        usersObject =  jQuery.parseJSON(zzGlobals.roomVars.AP)
	        for i of usersObject
	        		usersObject[i] = jQuery.parseJSON(usersObject[i])
	        		usersObject[i].PLRS = jQuery.parseJSON(usersObject[i].PLRS)
	
		        	for seatId of usersObject[i].PLRS
		        		usersObject[i].PLRS[seatId] = jQuery.parseJSON(usersObject[i].PLRS[seatId])
	        if e.type is "mouseover" and not isMousingOver
	          tileIdx = @getAttribute("tileIdx")
	          console.log "offset :", @offsetLeft + " : " + @offsetTop if isDevEnvironment
	          if boardVOs[tileIdx]?
	            playersObjs = boardVOs[tileIdx][boardVOCodes.PLAYER_INFO_OBJ]
	            strOut = ""
	            imgSrc = ""
	            for playerSeatId of playersObjs
	              console.log "mouseover", zzGlobals.userVOsSeatIndex[playerSeatId].UR if isDevEnvironment
	              if (usersObject[i].PLRS[playerSeatId]?) and (usersObject[i].PLRS[playerSeatId].PFB?)
	                imgSrc = "https://graph.facebook.com/" + usersObject[i].PLRS[playerSeatId].PFB + "/picture"
	              else
	                imgSrc = DEFAULT_PLAYER_IMG_URL
	              if usersObject[i].PLRS[playerSeatId]?
	                currentFigId = boardVOs[tileIdx][boardVOCodes.FIGURE_ID]
	                unless typeof currentFigId is "undefined"
	                  if currentFigId.indexOf("_NUMERIC_12") isnt -1 or currentFigId.indexOf("_NUMERIC_13") isnt -1 or currentFigId.indexOf("_NUMERIC_14") isnt -1 or currentFigId.indexOf("_NUMERIC_15") isnt -1 or currentFigId.indexOf("_NUMERIC_16") isnt -1 or currentFigId.indexOf("_NUMERIC_17") isnt -1
	                    strOut += "<li><img src='" + baseUrl + "/images/zalerio_1.2/3.ingame_board/board/player_countindicator_joker.png' alt='Joker' ></li>"
	                  else strOut += "<li><img src='" + baseUrl + "/images/zalerio_1.2/3.ingame_board/board/player_countindicator_Superjoker.png' alt='Supper Joker' ></li>"  unless currentFigId.indexOf("_NUMERIC_11") is -1
	                strOut += "<li><img src='" + imgSrc + "' alt='" + usersObject[i].PLRS[playerSeatId].PFB + "' ></li>"
	            jTileHoverDivContent.html strOut
	            tileHoverDivBids.innerHTML = boardVOs[tileIdx][boardVOCodes.TILE_COUNT]
	            tileNo = parseInt(tileIdx)
	            noOfRows = tileNo / board_X
	            noOfCols = tileNo % board_Y
	            leftMajor = (if noOfRows > board_Y / 2 then false else true)
	            topMajor = (if noOfCols > board_X / 2 then false else true)
	            elLeft = e.pageX + 10
	            elTop = e.pageY
	            elLeft = elLeft - $("#showOnMouseOver").width() - 10  if @offsetLeft > 300
	            elTop = elTop - $("#showOnMouseOver").height()  if @offsetTop > 400
	            $(".roundForBidsCount").remove()
	            jTileHoverDiv.show().offset
	              top: elTop
	              left: elLeft
	          isMousingOver = true
	          console.log "enlarged" if isDevEnvironment
	        else if e.type is "mouseout"
	          jTileHoverDiv.hide()
	          isMousingOver = false
	          console.log "resetting" if isDevEnvironment
	
	      jDocument.bind "room:" + zzGlobals.roomCodes.FIGURE_DETAILS, updateFigureDetails
	      jDocument.bind "room:" + zzGlobals.roomCodes.BOARD_XY, initBoard
	      jDocument.bind "room:" + zzGlobals.roomCodes.ROUND_NOOFBETS, initRoundBets
	      jDocument.bind "room:" + zzGlobals.roomCodes.ROOM_ALLROUNDS, parseRounds
	      jDocument.bind "room:" + zzGlobals.roomCodes.BOARDVARS, updateBoardVars
	      jDocument.bind "room:" + zzGlobals.roomCodes.CURRENTROUND, refreshRoundsPanel
	      jDocument.bind zzEvents.SERVER_MESSAGE, messageListener
	
	    ZalerioGame
	  ).call(this)
	  
	placeBetsToServer = (betStr) ->
    	jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|PB", "BI|" + betStr ]
    
    sendPlaceBetRequest = ->
	    console.log "bet Validation before sen  ding the request t  o server" if isDevEnvironment
	    betStr = ""
	    for bet of bets
	      betStr += (if betStr is "" then bets[bet] else ":" + bets[bet])
	      console.log "bets[" + bet + "] : " + bets[bet] if isDevEnvironment
	    console.log "[betStr: " + betStr + "]" if isDevEnvironment
	    if betStr is ""
	      console.log "No bets placed!" if isDevEnvironment
	      messagePopup "No bets placed!"
	      return false
	    else
	      console.log "every thing is fine end the bets to the server" if isDevEnvironment
	      placeBetsToServer betStr
	    false
  