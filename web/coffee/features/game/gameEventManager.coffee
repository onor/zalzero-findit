define ["../../config/config","../../helper/confirmBox","../../config/globals","./carouselView","./rightHudController","./leftHudController","./gamePlayController","../../popup/resignPopup","../../popup/showInviteStatus","../../popup/showFinalScore","../../popup/showFrndSelector"],(config,confirmBox,globals,carouselView,rightHudController,leftHudController,gamePlayController,resignPopup,showInviteStatus,showFinalScore,showFrndSelector) ->
  zzUnionConnection = (->
    zzUnionConnection = ->
      window.UPC = UPC
      init()
    UPC = net.user1.orbiter.UPC
    orbiter = null
    msgManager = null
    getClientSnapshotResult = ->
    	if config.isDevEnvironment
    		console.log "client snapshot result"
    		console.log arguments

    roomOccupantCountUpdateListener = (roomID, numOccupants) ->
      numOccupants = parseInt(numOccupants)
      if numOccupants is 1

      else if numOccupants is 2

      else

    roomAttrUpdateListener = (e, roomId, attrKey, attrVal) ->
      if zzGlobals.roomVars[attrKey]
        zzGlobals.roomVars[attrKey] = attrVal
       	if config.isDevEnvironment
       		console.log "Triggered room:", attrKey, attrVal
       	jDocument.trigger "room:" + attrKey, attrVal
      
    closeListener = (e) ->
      _this = this
      jDocument.trigger zzEvents.CONNECTION_CLOSE
      el = document.getElementById("loadingGame")
      el.style.display = "block"  if el and el.style
      window.setTimeout (->
        document.location.reload true
      ), 5000

    onLoginResult = (clientID, userID, arg2) ->
      unionClientId = clientID
      zzGlobals.currentUserDBId = userID

    clientAttrUpdate = (attrKey) ->
      switch attrKey
        when clientCodes.USER_ID then
        when clientCodes.USER_DISPLAY_NAME then
        when clientCodes.USER_NAME then

    roomSnapshotListener = (requestID, roomID, occupantCount, observerCount, roomAttrsStr) ->
      argLen = arguments.length
      i = 5
      while i < argLen
        userVO = new zzGlobals.UserVO(arguments[i], arguments[i + 3])
        jDocument.trigger zzEvents.RECEIVE_USERVO, [ userVO, false ]
        i += 5
      userFBVOs = getGameInstWithFBFriends([ zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID] ])  if (typeof getGameInstWithFBFriends isnt "undefined" and getGameInstWithFBFriends isnt null) and zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID]
      roomAttrsSplit = roomAttrsStr.split("|")
      len = roomAttrsSplit.length
      roomAttrKey = null
      roomAttrVal = null
      _results = []
      i = 0
      while i < len
        if roomAttrsSplit[i] and roomAttrsSplit[i + 1]
          roomAttrKey = roomAttrsSplit[i]
          roomAttrVal = roomAttrsSplit[i + 1]
          if zzGlobals.roomCodes.WINNING_BID is roomAttrKey
            try
              wbBid = jQuery.parseJSON(roomAttrVal)
              zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_ID] = wbBid[zzGlobals.roomCodes.WINNER_ID]
              zzGlobals.roomVars[zzGlobals.roomCodes.WINNING_AMOUNT] = wbBid[zzGlobals.roomCodes.WINNING_AMOUNT]
              zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_NAME] = wbBid[zzGlobals.roomCodes.WINNER_NAME]
              _results.push jDocument.trigger(zzEvents.WINNER_CHANGE)
            catch e
              _results.push console.log(e) #if config.isDevEnvironment
          else
            if zzGlobals.roomVars[roomAttrKey]
              unless roomAttrKey is zzGlobals.roomCodes.WINNER_ID and roomAttrVal is "-1"
              	if config.isDevEnvironment
              		console.log "Triggered via ss room:", roomAttrKey, roomAttrVal
               zzGlobals.roomVars[roomAttrKey] = {}
               zzGlobals.roomVars[roomAttrKey] = roomAttrVal
               _results.push jDocument.trigger("room:" + roomAttrKey, roomAttrVal)
            else
              _results.push undefined
        else
          _results.push undefined
        i += 2
      _results

    clientSnapshotListener = (requestID, clientID, userID, a4, clientAttrsStr) ->
      console.log "ClientSnapshotListener ",clientAttrsStr if config.isDevEnvironment
      if userLoginId is userID
        clientAttrsSplit = clientAttrsStr.split("|")
        len = clientAttrsSplit.length
        clientAttrKey = null
        clientAttrVal = null
        _results = []
        i = 0
        while i < len
          if clientAttrsSplit[i] and clientAttrsSplit[i + 1]
            clientAttrKey = clientAttrsSplit[i]
            clientAttrVal = clientAttrsSplit[i + 1]
            if clientVars[clientAttrKey]
              clientVars[clientAttrKey] = clientAttrVal
              _results.push jDocument.trigger("client:" + clientAttrKey, clientAttrVal)
            else
              _results.push undefined
          else
            _results.push undefined
          i += 2
        _results

    joinedRoomListener = (rId) ->
      roomID = rId
      #zzGlobals.roomVars.FR = 0
      resetGameVariables() # reset all global variable on game change
      jDocument.trigger zzEvents.JOINED_ROOM
	
	# reset global variable on game change
    resetGameVariables = ()->
    	flag_roundDrawn = false
    	jQuery("#userTopPicHUDMain").css("left","0")
    	jQuery("#scroll_carousel .selected").removeClass "selected"
    	jQuery("#gameBetPanel").css("display",'block')
    	jQuery("#right_hud_"+gameInstId).addClass "selected"
	
    clientAddedListener = (roomID, clientID, userID) ->
      userVO = new zzGlobals.UserVO(clientID, arguments[3])
      console.log "user added and triggered : [UserVO:", userVO, ",clientId:" + clientID + ",args:" + arguments[3] + "]" if config.isDevEnvironment
      jDocument.trigger zzEvents.CLIENT_JOINED, userVO
      jDocument.trigger zzEvents.RECEIVE_USERVO, [ userVO, true ]

    clientRemovedListener = (roomID, clientID) ->
      userName = clientID
      jDocument.trigger zzEvents.REMOVE_USERVO, clientID  if clientID and zzGlobals.userVOsIndex[clientID]

    messageListener = (messageName, broadcastType, fromClientID, roomID, message) ->
#      switch messageName
#      	when zalerioCMDListners.DECLINE_STATUS
#      		 if parseInt(message) is 1
#      		 	   confirmBox.messagePopup(popupMSG.declineInvite())
#      		 	   jQuery(".draggableBets").attr("draggable","false")
#      		 	   jQuery(".resignPopup").hide()
#      		 	   jQuery("#gameBetPanel").hide()
#      	when zalerioCMDListners.RIGHT_HUD
#        	usersObject = jQuery.parseJSON(message)      
#        	for i of usersObject
#        		usersObject[i] = jQuery.parseJSON(usersObject[i])
#        		usersObject[i].PLRS = jQuery.parseJSON(usersObject[i].PLRS)
#        		scoreArray = []
#        		seatIdArray = []	
#	        	for seatId of usersObject[i].PLRS
#	        		usersObject[i].PLRS[seatId] = jQuery.parseJSON(usersObject[i].PLRS[seatId])
#	        		scoreArray[seatId] = parseInt(usersObject[i].PLRS[seatId].PSC)
#	        		seatIdArray.push seatId
#        		seatIdArray.sort (x, y) ->
#        			scoreArray[y] - scoreArray[x]
#        		usersObject[i].PLSC = {}
#        		for x of seatIdArray
#        			usersObject[i].PLSC[x] = seatIdArray[x]
#
#        	zzGlobals.msgVars.RH = usersObject
#        	console.log 'MT & TT',zzGlobals.msgVars.RH if isDevEnvironment
#        	
#        	jDocument.trigger "client:" + zzGlobals.msgCodes.RIGHT_HUD,usersObject
#        when zalerioCMDListners.CLOSE_INVITE
#        	if 0 is message
#        		confirmBox.messagePopup('Sorry!!! Unable to Close Invite, <br /> Plese try again..');
#        	else
#        		jQuery(".status_show_popup").remove();
#        		jQuery(".gdWrapper").remove();
#        when zalerioCMDListners.RESIGN_GAME
#        	if message is 0
#        		confirmBox.messagePopup('Sorry!!! Unable to Resign, \n Plese try again..');
#        		jQuery("#gameBetPanel").show()
#        	else
#        		resignStatus = 1
#        		jQuery(".draggableBets").attr("draggable","false");
#        		jQuery("#gameBetPanel").hide()
#        		jQuery(".resignPopup").hide();
#        when zalerioCMDListners.ORIG_FIGS
#        	newCoordObj = jQuery.parseJSON(message)
#        	console.log newCoordObj if isDevEnvironment
#        	parseToGameBoard zalerioMapType.ORIG_MAP, newCoordObj
#        when zalerioCMDListners.BET_RESPONSE
#        	parsedObj = jQuery.parseJSON(message)
#        	console.log "zalerioCMDListners.BET_RESPONSE if failed : ", parsedObj if isDevEnvironment
#        when zalerioCMDListners.BET_CHANGES
#        	betChangeVOs = {}
#        	playerBetsChangeObj = jQuery.parseJSON(message)
#        	console.log "zalerioCMDListners.BET_CHANGES : ", playerBetsChangeObj if isDevEnvironment
#        	currentBets = {}
#        	currentBetsIdx = {}
#        	for i of playerBetsChangeObj
#        		if i is "PB"
#        			playerBetTiles = jQuery.parseJSON(playerBetsChangeObj[i])
#        			for tileId of playerBetTiles
#        				betChangeVOs[tileId] = playerBetTiles[tileId]
#        	currentRoundBidPlaced = playerBetsChangeObj["BC"]
#        	jDocument.trigger zzEvents.CLIENT_BETS_PLACED, currentRoundBidPlaced
#        	gamePlayController.reDrawBetsPanel()
#        	gamePlayController.refreshGameBoard()
      jDocument.trigger zzEvents.SERVER_MESSAGE, [ messageName, broadcastType, fromClientID, roomID, message, orbiter.getClientID() ]

    clientAttrUpdateListener = (roomId, clientId, userId, attrKey, attrVal) ->
      if zzGlobals.currentUserDBId is userId
        if zzGlobals.clientVars[attrKey]
          zzGlobals.clientVars[attrKey] = attrVal
          return jDocument.trigger("client:" + attrKey, attrVal)

    init = ->
      orbiter = new net.user1.orbiter.Orbiter()
      msgManager = orbiter.getMessageManager()
      addOloListeners()
      if config.isDevEnvironment
      	console.log "defining UPC"
      	console.log "ololisteners :", zzListeners
      	console.log "UPC definition completed!"
      unless orbiter.getSystem().isJavaScriptCompatible()
        oloGame.displayChatMessage "systemChatMessage", "Your browser is not supported."
        return
      if orbiter
        orbiter.addEventListener net.user1.orbiter.OrbiterEvent.READY, readyListener, this
        orbiter.addEventListener net.user1.orbiter.OrbiterEvent.CLOSE, closeListener, this
      jQuery ->
        orbiter.connect config.unionConnection.url, config.unionConnection.port

    askClientData = (e) ->
      console.log "askfordata" if config.isDevEnvironment
      msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|AGD", "UID|" + zzGlobals.currentUserDBId

    readyListener = (e) ->
	    msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|LI", "UI|" + userLoginId, "GI|" + gameInstId
	    jDocument.bind zzEvents.SEND_UPC_MESSAGE, sendUpcMessageToServer
    
    # change game
    gameChangeListener = (e,gameInstIdTemp) ->	# fire when user click on crouse to change game
      console.log(gameInstIdTemp)
      if typeof gameInstIdTemp is 'undefined'
      	eval("gameInstIdTemp = gameInstId")
      else
      	eval("gameInstId = gameInstIdTemp")
      zzGlobals.roomVars.FR = -1
      flag_roundDrawn = false
      flag_roundBetsDrawn = false
      msgManager.sendUPC(UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|CG", "UI|" + userLoginId, "GI|" + gameInstIdTemp);
    jDocument.bind "gameChangeListener" , gameChangeListener
	
    sendDeclinedToServer = (gameSeatId,gameId) ->   # fire when user click decined
    	jQuery('#accept_decline_' + gameId).text('')
    	jQuery('#accept_decline_' + gameId).css('cursor', 'default')
    	jQuery('#accept_decline_' + gameId).html("""<div id="floatingBarsGs">
		<div class="blockG" id="rotateG_01">
		</div>
		<div class="blockG" id="rotateG_02">
		</div>
		<div class="blockG" id="rotateG_03">
		</div>
		<div class="blockG" id="rotateG_04">
		</div>
		<div class="blockG" id="rotateG_05">
		</div>
		<div class="blockG" id="rotateG_06">
		</div>
		<div class="blockG" id="rotateG_07">
		</div>
		<div class="blockG" id="rotateG_08">
		</div>
		</div>""")
    	
    	msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|DG", "GSID|" + gameSeatId

    sendUpcMessageToServer = (event, upcFunctionCode,p2,p3,p4,p5,p6) ->
      console.log("sending upc message [upcFunctionCode:" + upcFunctionCode + ",p2:" + p2 + ",p3:" + p3 + ",p4:" + p4 + ",p5:" + p5 + ",p6:" + p6 + "]") if config.isDevEnvironment
      argArr = [];
      for i in [0...arguments.length]
          if i is 0
              continue;
          else
              if(arguments[i])
                  argArr.push(arguments[i]);
              else
                  argArr.push('');

      console.log(argArr) if config.isDevEnvironment
      if(upcFunctionCode)
          msgManager.sendUPC.apply(msgManager,argArr);
          
    onLogoutResult = (clientUnionId,userId)->
    	if parseInt(unionClientId) is parseInt(clientUnionId)
    		window.location = "http://" + document.domain + baseUrl + "/site/closesession"
    	
    zzListeners =
      JOINED_ROOM: joinedRoomListener
      CLIENT_ADDED_TO_ROOM: clientAddedListener
      CLIENT_REMOVED_FROM_ROOM: clientRemovedListener
      ROOM_SNAPSHOT: roomSnapshotListener
      CLIENT_SNAPSHOT: clientSnapshotListener
      ROOM_ATTR_UPDATE: roomAttrUpdateListener
      CLIENT_ATTR_UPDATE: clientAttrUpdateListener
      ROOM_OCCUPANTCOUNT_UPDATE: roomOccupantCountUpdateListener
      LOGGED_IN: onLoginResult
      LOGGED_OFF: onLogoutResult
      RECEIVE_MESSAGE: messageListener
      GET_CLIENT_SNAPSHOT_RESULT: getClientSnapshotResult

    addOloListeners = ->
      _results = []
      for msgEvt of zzListeners
        if msgManager and msgManager.addMessageListener
          if zzListeners[msgEvt] is "messageListener"
            _results.push msgManager.addMessageListener(UPC[msgEvt], zzListeners[msgEvt], this, gameInstId)
          else
            _results.push msgManager.addMessageListener(UPC[msgEvt], zzListeners[msgEvt], this)
        else
          _results.push undefined
      _results
	
#    jQuery( ($) ->
#    	$('#startButton').click () ->
#    		alert(showFrndSelector)
#    	)
	
    zzUnionConnection
  )()