popupMSG =
	remindSucess: (userName) ->
		"You have sent reminder successfuly"
	acceptInvite: 'Would you like to start playing'
	declineInvite:()->
		"You have declined successfuly"
	gameCreate: 'Challenge has been sent. Would you like to start playing.'
		
__hasProp_ = Object::hasOwnProperty
rematchCall = ->
usersRecord = ->
sendDeclinedToServer = ->
gameChangeListener = ->
resignStatus = 0
flag_roundBetsDrawn = false
drawRoundsPanel = ->
showRoundScorePopup = ->
_currentRoundStatus = -1
roundVOsIdxRightHUD = {}
flag_roundDrawn = false
acceptInvitationAdd = (div,gameIdToAdd)->
	div.onclick = ->
		acceptInvitation(gameIdToAdd)
declineInvitationAdd = (div, gameSeatIdToDecline, gameId) ->
  div.onclick = ->
     sendDeclinedToServer gameSeatIdToDecline, gameId
     
remindUserAdd = (gameLinkID, remindUsersLeft, x, fbUser,div) ->
  div.onclick = ->
  	remindUser gameLinkID, remindUsersLeft, x, fbUser
  	
unless typeof valDevEnvironment is "undefined" 
	isDevEnvironment = valDevEnvironment
else
	isDevEnvironment = false  #if not Dev disable logs
	
jQuery ->
  unionGameServerId = "zalerioGameServer"
  userLevelImg = new Array("carauselbelts_otherplayers_white_belt.png", "carauselbelts_otherplayers_yellow_belt.png", "carauselbelts_otherplayers_orange_belt.png", "carauselbelts_otherplayers_green_belt.png", "carauselbelts_otherplayers_blue_belt.png", "carauselbelts_otherplayers_purple_belt.png", "carauselbelts_otherplayers_red_belt.png", "carauselbelts_otherplayers_brown_belt.png", "carauselbelts_otherplayers_black_belt.png")
  userLevelImgBig = new Array("carauselbelts_main_player_white_belt.png", "carauselbelts_main_player_yellow_belt.png", "carauselbelts_main_player_orange_belt.png", "carauselbelts_main_player_green_belt.png", "carauselbelts_main_player_blue_belt.png", "carauselbelts_main_player_purple_belt.png", "carauselbelts_main_player_red_belt.png", "carauselbelts_main_player_brown_belt.png", "carauselbelts_main_player_black_belt.png")
  
  
  jDocument = jQuery(document)
  unionClientId = null
  roomID = null
  pad2 = (number) ->
    _ref = undefined
    (if (_ref = number < 10)? then _ref else 0: "") + number
#  ### move to globals
#  zzEvents =
#    JOINED_ROOM: "joinedRoomListener"
#    RESET_GAME_VARIABLES: "resetGameVariables"
#    CLIENT_ADDED_TO_ROOM: "clientAddedListener"
#    CLIENT_REMOVED_FROM_ROOM: "clientRemovedListener"
#    ROOM_SNAPSHOT: "roomSnapshotListener"
#    CLIENT_SNAPSHOT: "clientSnapshotListener"
#    ROOM_ATTR_UPDATE: "roomAttrUpdateListener"
#    CLIENT_ATTR_UPDATE: "clientAttrUpdateListener"
#    ROOM_OCCUPANTCOUNT_UPDATE: "roomOccupantCountUpdateListener"
#    LOGGED_IN: "onLoginResult"
#    RECEIVE_MESSAGE: "messageListener"
#    SEND_UPC_MESSAGE: "send_upc_message"
#    RECEIVE_USERVO: "received_userVO"
#    REMOVE_USERVO: "remove_userVO"
#    UPDATE_TTL: "update_ttl"
#    CONNECTION_CLOSE: "connection_close"
#    SEND_CHAT_MESSAGE: "send_chat_message"
#    UPDATE_LOCAL_TTL: "update_local_ttl"
#    BID_CHANGED: "bid_changed"
#    SERVER_MESSAGE: "SERVER_MESSAGE"
#    WINNER_CHANGE: "winner_change"
#    CLIENT_JOINED: "client_joined"
#    CLIENT_LEFT: "client_left"
#    OLOTUTS_MESSAGE: "olotuts_message"
#    CLIENT_BETS_PLACED: "zalerioUserBetPlaced"
#
#  OloGlobals = ->
#    inviteStatus = null
#    ttl = null
#    currentUserDBId = null    
#    offlinePlayers = null
#    clientCodes =
#      USER_ID: "UI"
#      USER_DISPLAY_NAME: "UD"
#      USER_NAME: "UN"
#      USER_UNION_ID: "UU"
#      USER_FACEBOOK_ID: "UF"
#      USER_PARTY_ID: "UP"
#      USER_SEAT_ID: "UR"
#      Z_USER_SCORE: "ZS"
#      MY_TURN: "MT"
#      THEIR_TURN: "TT"
#      ALL_PAST_GAME: "APG"
#      USERINFO: "UINFO"
#      
#	# new implementation
#    msgCodes =
#      RIGHT_HUD:"RH"
#    dataObjCodes =
#      ALL_PLAYER_INFO:"AP"
#
#    roomCodes =
#      ROOM_ID: "GI"
#      USER_TOTAL: "UT"
#      USER_COUNT: "UC"
#      WINNER_NAME: "WN"
#      WINNER_ID: "WI"
#      GAME_ENDED: "GE"
#      TIME_LEFT: "TL"
#      GAME_DURATION: "GD"
#      START_TIME: "ST"
#      END_TIME: "ET"
#      ROOM_NAME: "RN"
#      TOTAL_SEATS: "TS"
#      ROOM_PRIZE_NAME: "RP"
#      ROOM_MRP: "RM"
#      ROOM_IMAGEROOM_MRP: "RI"
#      TOTALROUNDS: "TR"
#      CURRENTROUND: "CR"
#      ROUND_ENDTIME: "RE"
#      ROUND_ENDTIMEINMS: "EM"
#      ROUND_NOOFBETS: "NB"
#      BOARDVARS: "BV"
#      BOARD_XY: "XY"
#      TOTALFIGS: "TF"
#      ROOM_ALLROUNDS: "AR"
#      FIGURE_DETAILS: "FD"
#      ZALERIO_SCORES: "ZS"
#      GAME_FINISH: "FR"
#      PLAYER_BETS_PLACE: "PP"
#      ALL_PLAYER_INFO: "AP"
#
#    userVOsIndex = {}
#    userVOsSeatIndex = {}
#    userVOsPartyIndex = {}
#    generateAttrVars = (attrCodes) ->
#      attrV = {}
#      for i of attrCodes
#        attrV[attrCodes[i]] = i
#      attrV
#      
#    msgVars = generateAttrVars(msgCodes)
#    dataObjVars = generateAttrVars(dataObjCodes)
#    clientVars = generateAttrVars(clientCodes)
#    roomVars = generateAttrVars(roomCodes)
#    roomVarsFlag = generateAttrVars(roomCodes)
#    userVOs = {}
#    userFBVOs = {}
#    gameDuration = ""
#    setClockInterval = ""
#    UserVO = (userUnionId, userSnapshotObj) ->
#      if isDevEnvironment
#      	console.log "Added to room " + userSnapshotObj
#      	console.log "Current User local ID : " + zzGlobals.currentUserDBId;
#      for i of clientCodes
#        this[clientCodes[i]] = "-1"
#      userVArr = userSnapshotObj.split("|")
#      userVArrLen = userVArr.length
#      i = 0
#      while i < userVArrLen
#        this[userVArr[i]] = userVArr[i + 1]  if clientVars[userVArr[i]]
#        i += 3
#      this[clientCodes.USER_UNION_ID] = userUnionId  if this[clientCodes.USER_ID]
#      if this[clientCodes.USER_ID] is zzGlobals.currentUserDBId
#        i = 0
#        while i < userVArrLen
#          zzGlobals.clientVars[userVArr[i]] = userVArr[i + 1]  if clientVars[userVArr[i]]
#          i += 3
#      this
#
#    getUserName = (clientID) ->
#      userName = clientID
#      userName = zzGlobals.userVOsIndex[clientID][clientCodes.USER_DISPLAY_NAME]  if clientID and zzGlobals.userVOsIndex[clientID]
#      userName
#
#    updateGameDuration = (event, data) ->
#      data = (if data then parseInt(data) else null)
#      gameDuration = data  if data and not isNaN(data)
#
#    updateClock = ->
#      ttl--
#      if ttl > 0
#        pageRefresh.clearTimer()  if pageRefresh and pageRefresh.isTimerEnabled and pageRefresh.isTimerEnabled()
#      else
#        if pageRefresh and ttl <= 0
#          if pageRefresh.isTimerEnabled and not (pageRefresh.isTimerEnabled()) and currentScreen isnt "postscreen"
#            console.log "Pagerefresh.isTimerEnabled false!" if isDevEnvironment
#            pageRefresh.enableTimer()
#      jDocument.trigger zzEvents.UPDATE_LOCAL_TTL, @ttl
#
#    updateTTL = (event, data) ->
#      clearInterval setClockInterval  if setClockInterval
#      data = (if data then parseInt(data) else null)
#      ttl = data  if data and typeof data is "number"
#      setClockInterval = setInterval(updateClock, 10000)
#
#    pageRefresh = ->
#      timer = null
#      enableTimerFn = ->
#        _this = this
#        timer = setTimeout(->
#          redirectToPlayNow gameInstId
#        , 5000)
#        console.log "refresh timer set!" if isDevEnvironment
#
#      clearTimerFn = ->
#        timer = null
#        console.log "refresh timer cleared!" if isDevEnvironment
#
#      isTimerEnabled: ->
#        if timer
#          true
#        else
#          false
#
#      enableTimer: ->
#        enableTimerFn()
#
#      clearTimer: ->
#        clearTimerFn()
#
#
#    generateAttrVarsFn: (p) ->
#      generateAttrVars p
#
#    roomCodes: roomCodes
#    clientCodes: clientCodes
#    msgCodes: msgCodes
#    dataObjCodes: dataObjCodes
#    dataObjVars: dataObjVars
#    UserVO: UserVO
#    userVOsIndex: userVOsIndex
#    roomVars: roomVars
#    roomVarsFlag: roomVarsFlag
#    clientVars: clientVars
#    msgVars: msgVars
#    userVOs: userVOs
#    userFBVOs: userFBVOs
#    getUserName: getUserName
#    userVOsPartyIndex: userVOsPartyIndex
#    getTtl: ->
#      ttl
#
#    userVOsSeatIndex: userVOsSeatIndex
#    getGameDuration: ->
#      gameDuration
#
#    offlinePlayers: offlinePlayers
#    currentUserDBId: currentUserDBId
#
#  window.zzGlobals = zzGlobals = OloGlobals()
#  OloUnionConnection = (->
#    OloUnionConnection = ->
#      window.UPC = UPC
#      init()
#    UPC = net.user1.orbiter.UPC
#    orbiter = null
#    msgManager = null
#    getClientSnapshotResult = ->
#    	if isDevEnvironment
#    		console.log "client snapshot result"
#    		console.log arguments
#
#    roomOccupantCountUpdateListener = (roomID, numOccupants) ->
#      numOccupants = parseInt(numOccupants)
#      if numOccupants is 1
#
#      else if numOccupants is 2
#
#      else
#
#    roomAttrUpdateListener = (e, roomId, attrKey, attrVal) ->
#      if zzGlobals.roomVars[attrKey]
#        zzGlobals.roomVars[attrKey] = attrVal
#        if zzGlobals.roomCodes.WINNING_BID is attrKey
#          try
#            wbBid = jQuery.parseJSON(attrVal)
#            zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_ID] = wbBid[zzGlobals.roomCodes.WINNER_ID]
#            zzGlobals.roomVars[zzGlobals.roomCodes.WINNING_AMOUNT] = wbBid[zzGlobals.roomCodes.WINNING_AMOUNT]
#            zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_NAME] = wbBid[zzGlobals.roomCodes.WINNER_NAME]
#            return jDocument.trigger(zzEvents.WINNER_CHANGE, [ wbBid[zzGlobals.roomCodes.WINNING_AMOUNT], wbBid[zzGlobals.roomCodes.WINNER_NAME], wbBid[zzGlobals.roomCodes.WINNER_ID] ])
#          catch e
#            console.log(e) if isDevEnvironment
#            return
#        else
#        	if isDevEnvironment
#        		console.log "Triggered room:", attrKey, attrVal
#        	jDocument.trigger "room:" + attrKey, attrVal
#      
#    closeListener = (e) ->
#      _this = this
#      jDocument.trigger zzEvents.CONNECTION_CLOSE
#      el = document.getElementById("loadingGame")
#      el.style.display = "block"  if el and el.style
#      window.setTimeout (->
#        document.location.reload true
#      ), 5000
#
#    onLoginResult = (clientID, userID, arg2) ->
#      unionClientId = clientID
#      zzGlobals.currentUserDBId = userID
#
#    clientAttrUpdate = (attrKey) ->
#      switch attrKey
#        when clientCodes.USER_ID then
#        when clientCodes.USER_DISPLAY_NAME then
#        when clientCodes.USER_NAME then
#
#    roomSnapshotListener = (requestID, roomID, occupantCount, observerCount, roomAttrsStr) ->
#      argLen = arguments.length
#      i = 5
#      while i < argLen
#        userVO = new zzGlobals.UserVO(arguments[i], arguments[i + 3])
#        jDocument.trigger zzEvents.RECEIVE_USERVO, [ userVO, false ]
#        i += 5
#      userFBVOs = getGameInstWithFBFriends([ zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID] ])  if (typeof getGameInstWithFBFriends isnt "undefined" and getGameInstWithFBFriends isnt null) and zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID]
#      roomAttrsSplit = roomAttrsStr.split("|")
#      len = roomAttrsSplit.length
#      roomAttrKey = null
#      roomAttrVal = null
#      _results = []
#      i = 0
#      while i < len
#        if roomAttrsSplit[i] and roomAttrsSplit[i + 1]
#          roomAttrKey = roomAttrsSplit[i]
#          roomAttrVal = roomAttrsSplit[i + 1]
#          if zzGlobals.roomCodes.WINNING_BID is roomAttrKey
#            try
#              wbBid = jQuery.parseJSON(roomAttrVal)
#              zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_ID] = wbBid[zzGlobals.roomCodes.WINNER_ID]
#              zzGlobals.roomVars[zzGlobals.roomCodes.WINNING_AMOUNT] = wbBid[zzGlobals.roomCodes.WINNING_AMOUNT]
#              zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_NAME] = wbBid[zzGlobals.roomCodes.WINNER_NAME]
#              _results.push jDocument.trigger(zzEvents.WINNER_CHANGE)
#            catch e
#              _results.push console.log(e) #if isDevEnvironment
#          else
#            if zzGlobals.roomVars[roomAttrKey]
#              unless roomAttrKey is zzGlobals.roomCodes.WINNER_ID and roomAttrVal is "-1"
#              	if isDevEnvironment
#              		console.log "Triggered via ss room:", roomAttrKey, roomAttrVal
#               zzGlobals.roomVars[roomAttrKey] = {}
#               zzGlobals.roomVars[roomAttrKey] = roomAttrVal
#               _results.push jDocument.trigger("room:" + roomAttrKey, roomAttrVal)
#            else
#              _results.push undefined
#        else
#          _results.push undefined
#        i += 2
#      _results
#
#    clientSnapshotListener = (requestID, clientID, userID, a4, clientAttrsStr) ->
#      console.log "ClientSnapshotListener ",clientAttrsStr if isDevEnvironment
#      if userLoginId is userID
#        clientAttrsSplit = clientAttrsStr.split("|")
#        len = clientAttrsSplit.length
#        clientAttrKey = null
#        clientAttrVal = null
#        _results = []
#        i = 0
#        while i < len
#          if clientAttrsSplit[i] and clientAttrsSplit[i + 1]
#            clientAttrKey = clientAttrsSplit[i]
#            clientAttrVal = clientAttrsSplit[i + 1]
#            if clientVars[clientAttrKey]
#              clientVars[clientAttrKey] = clientAttrVal
#              _results.push jDocument.trigger("client:" + clientAttrKey, clientAttrVal)
#            else
#              _results.push undefined
#          else
#            _results.push undefined
#          i += 2
#        _results
#
#    joinedRoomListener = (rId) ->
#      roomID = rId
#      #zzGlobals.roomVars.FR = 0
#      resetGameVariables() # reset all global variable on game change
#      jDocument.trigger zzEvents.JOINED_ROOM
#	
#	# reset global variable on game change
#    resetGameVariables = ()->
#    	flag_roundDrawn = false
#    	jQuery("#userTopPicHUDMain").css("left","0")
#    	jQuery("#scroll_carousel .selected").removeClass "selected"
#    	jQuery("#gameBetPanel").css("display",'block')
#    	jQuery("#right_hud_"+gameInstId).addClass "selected"
#	
#    clientAddedListener = (roomID, clientID, userID) ->
#      userVO = new zzGlobals.UserVO(clientID, arguments[3])
#      console.log "user added and triggered : [UserVO:", userVO, ",clientId:" + clientID + ",args:" + arguments[3] + "]" if isDevEnvironment
#      jDocument.trigger zzEvents.CLIENT_JOINED, userVO
#      jDocument.trigger zzEvents.RECEIVE_USERVO, [ userVO, true ]
#
#    clientRemovedListener = (roomID, clientID) ->
#      userName = clientID
#      jDocument.trigger zzEvents.REMOVE_USERVO, clientID  if clientID and zzGlobals.userVOsIndex[clientID]
#
#    messageListener = (messageName, broadcastType, fromClientID, roomID, message) ->
#      jDocument.trigger zzEvents.SERVER_MESSAGE, [ messageName, broadcastType, fromClientID, roomID, message, orbiter.getClientID() ]
#
#    clientAttrUpdateListener = (roomId, clientId, userId, attrKey, attrVal) ->
#      if zzGlobals.currentUserDBId is userId
#        if zzGlobals.clientVars[attrKey]
#          zzGlobals.clientVars[attrKey] = attrVal
#          return jDocument.trigger("client:" + attrKey, attrVal)
#
#    init = ->
#      orbiter = new net.user1.orbiter.Orbiter()
#      msgManager = orbiter.getMessageManager()
#      addOloListeners()
#      if isDevEnvironment
#      	console.log "defining UPC"
#      	console.log "ololisteners :", oloListeners
#      	console.log "UPC definition completed!"
#      unless orbiter.getSystem().isJavaScriptCompatible()
#        oloGame.displayChatMessage "systemChatMessage", "Your browser is not supported."
#        return
#      if orbiter
#        orbiter.addEventListener net.user1.orbiter.OrbiterEvent.READY, readyListener, this
#        orbiter.addEventListener net.user1.orbiter.OrbiterEvent.CLOSE, closeListener, this
#      jQuery ->
#        if document.domain is "localhost" or document.domain is "zl.mobicules.com"
#            orbiter.connect document["domain"], 9933
#        else
#            orbiter.connect "union.zalerio.com", 80
#
#    askClientData = (e) ->
#      console.log "askfordata" if isDevEnvironment
#      msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, unionGameServerId, "REQ", "C|AGD", "UID|" + zzGlobals.currentUserDBId
#
#    readyListener = (e) ->
#	    msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, unionGameServerId, "REQ", "C|LI", "UI|" + userLoginId, "GI|" + gameInstId
#	    jDocument.bind zzEvents.SEND_UPC_MESSAGE, sendUpcMessageToServer
#    
#    # change game
#    gameChangeListener = (gameInstIdTemp) ->	# fire when user click on crouse to change game
#      if typeof gameInstIdTemp is 'undefined'
#      	eval("gameInstIdTemp = gameInstId")
#      else
#      	eval("gameInstId = gameInstIdTemp")
#      zzGlobals.roomVars.FR = -1
#      flag_roundDrawn = false
#      flag_roundBetsDrawn = false
#      msgManager.sendUPC(UPC.SEND_SERVERMODULE_MESSAGE, unionGameServerId, "REQ", "C|CG", "UI|" + userLoginId, "GI|" + gameInstIdTemp);
# 
#    sendDeclinedToServer = (gameSeatId,gameId) ->   # fire when user click decined
#    	jQuery('#accept_decline_' + gameId).text('')
#    	jQuery('#accept_decline_' + gameId).css('cursor', 'default')
#    	jQuery('#accept_decline_' + gameId).html("""<div id="floatingBarsGs">
#		<div class="blockG" id="rotateG_01">
#		</div>
#		<div class="blockG" id="rotateG_02">
#		</div>
#		<div class="blockG" id="rotateG_03">
#		</div>
#		<div class="blockG" id="rotateG_04">
#		</div>
#		<div class="blockG" id="rotateG_05">
#		</div>
#		<div class="blockG" id="rotateG_06">
#		</div>
#		<div class="blockG" id="rotateG_07">
#		</div>
#		<div class="blockG" id="rotateG_08">
#		</div>
#		</div>""")
#    	
#    	msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, unionGameServerId, "REQ", "C|DG", "GSID|" + gameSeatId
#
#    sendUpcMessageToServer = (event, upcFunctionCode,p2,p3,p4,p5,p6) ->
#      console.log("sending upc message [upcFunctionCode:" + upcFunctionCode + ",p2:" + p2 + ",p3:" + p3 + ",p4:" + p4 + ",p5:" + p5 + ",p6:" + p6 + "]") if isDevEnvironment
#      argArr = [];
#      for i in [0...arguments.length]
#          if i is 0
#              continue;
#          else
#              if(arguments[i])
#                  argArr.push(arguments[i]);
#              else
#                  argArr.push('');
#
#      console.log(argArr) if isDevEnvironment
#      if(upcFunctionCode)
#          msgManager.sendUPC.apply(msgManager,argArr);
#          
#    onLogoutResult = (clientUnionId,userId)->
#    	if parseInt(unionClientId) is parseInt(clientUnionId)
#    		window.location = "http://" + document.domain + baseUrl + "/site/closesession"
#    	
#    oloListeners =
#      JOINED_ROOM: joinedRoomListener
#      CLIENT_ADDED_TO_ROOM: clientAddedListener
#      CLIENT_REMOVED_FROM_ROOM: clientRemovedListener
#      ROOM_SNAPSHOT: roomSnapshotListener
#      CLIENT_SNAPSHOT: clientSnapshotListener
#      ROOM_ATTR_UPDATE: roomAttrUpdateListener
#      CLIENT_ATTR_UPDATE: clientAttrUpdateListener
#      ROOM_OCCUPANTCOUNT_UPDATE: roomOccupantCountUpdateListener
#      LOGGED_IN: onLoginResult
#      LOGGED_OFF: onLogoutResult
#      RECEIVE_MESSAGE: messageListener
#      GET_CLIENT_SNAPSHOT_RESULT: getClientSnapshotResult
#
#    addOloListeners = ->
#      _results = []
#      for msgEvt of oloListeners
#        if msgManager and msgManager.addMessageListener
#          if oloListeners[msgEvt] is "messageListener"
#            _results.push msgManager.addMessageListener(UPC[msgEvt], oloListeners[msgEvt], this, gameInstId)
#          else
#            _results.push msgManager.addMessageListener(UPC[msgEvt], oloListeners[msgEvt], this)
#        else
#          _results.push undefined
#      _results
#
#    OloUnionConnection
#  )()
#  ###
  oloUserComponent = ->
    flag_drawUserPanel = false
    docElems = {}
    DEFAULT_PLAYER_IMG_URL = ""
    PLAYER_ONLINE_IMG_URL = ""
    PLAYER_OFFLINE_IMG_URL = ""
    showAllUsers = "ALL"
    userStatusCurrent = 10
    userVOsLen = 0
    userVOsDivIndex = {}
    MAX_PLAYERS_IN_A_GAME = 100
    currentNoOfUsers = 0
    userDivCodes =
      USER_IMAGE: "userImg"
      USER_DISPLAY_NAME: "userDisplayNameSpan"
      USER_SCORE: "userScoreSpanValue"
      USER_RANK: "userRankSpan"
      USER_LI: "userLi"
      USER_ONLINE_STATUS: "userOnlineStatusDiv"

    updateUserIndex = ->
      zzGlobals.userVOsIndex = {}
      for i of zzGlobals.userVOs
        zzGlobals.userVOsIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_UNION_ID]] = zzGlobals.userVOs[i]
      updateUserIndexForPartyId()

    updateUserIndexForPartyId = ->
      _results = []
      for i of zzGlobals.userVOs
        unless zzGlobals.userVOsPartyIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_PARTY_ID]]
          _results.push zzGlobals.userVOsPartyIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_PARTY_ID]] = zzGlobals.userVOs[i]
        else
          _results.push undefined
      _results

    updateUserIndexForPartyIdViaOfflinePlayers = ->
      _results = []
      for i of zzGlobals.offlinePlayers
        unless zzGlobals.userVOsPartyIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_PARTY_ID]]
          _results.push zzGlobals.userVOsPartyIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_PARTY_ID]] = zzGlobals.offlinePlayers[i]
        else
          _results.push undefined
      _results

    refreshUserPanel = ->
      userCtr = 0
      for i of zzGlobals.userVOs
        if zzGlobals.userVOs.hasOwnProperty(i)
          if (showAllUsers isnt "FB") or (showAllUsers is "FB" and zzGlobals.userFBVOs[i])
            userVO = zzGlobals.userVOs[i]
            renderUserVOToUserPanel userCtr++, userVO
      if (typeof getGameInstWithFriends isnt "undefined" and getGameInstWithFriends isnt null) and not zzGlobals.offlinePlayers and getGameInstWithFriends(zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID])
        zzGlobals.offlinePlayers = getGameInstWithFriends(zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID])
        updateUserIndexForPartyIdViaOfflinePlayers()
        changeWinner()
      for i of zzGlobals.offlinePlayers
        if zzGlobals.offlinePlayers.hasOwnProperty(i)
          offlineUserVO = zzGlobals.offlinePlayers[i]
          if showAllUsers is "FB"
            renderUserVOToUserPanel userCtr++, offlineUserVO, true  unless userFBVOs[i]
          else
            renderUserVOToUserPanel userCtr++, offlineUserVO, true  unless zzGlobals.userVOs[i]
      currentNoOfUsers = userCtr
      while userCtr < MAX_PLAYERS_IN_A_GAME
        userVOsDivIndex[userCtr][userDivCodes.USER_DIV].style.display = "none"  if userVOsDivIndex and userVOsDivIndex[userCtr] and userVOsDivIndex[userCtr][userDivCodes.USER_DIV]
        userCtr++
      activateUserPanelJQuery currentNoOfUsers

    activateUserPanelJQuery = (currentNoOfUsers) ->
      zzGlobals.roomVars[zzGlobals.roomCodes.USER_TOTAL] = currentNoOfUsers
      jDocument.trigger "room:" + zzGlobals.roomCodes.USER_TOTAL, currentNoOfUsers

    renderUserVOToUserPanel = (idx, userVO, userOffline) ->
      userOffline = userOffline or false
      if userVO and userVO[zzGlobals.clientCodes.USER_ID]
        userVOsDivIndex[idx][userDivCodes.USER_LI].style.display = "block"
        if userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME]
          userVOsDivIndex[idx][userDivCodes.USER_DISPLAY_NAME].innerHTML = userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME]
        else
          userVOsDivIndex[idx][userDivCodes.USER_DISPLAY_NAME].innerHTML = "-"
        if userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID] and userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID].length > 2
          userVOsDivIndex[idx][userDivCodes.USER_IMAGE].src = "https://graph.facebook.com/" + userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID] + "/picture"
        else
          userVOsDivIndex[idx][userDivCodes.USER_IMAGE].src = DEFAULT_PLAYER_IMG_URL
        if userOffline
          userVOsDivIndex[idx][userDivCodes.USER_ONLINE_STATUS].className = "user-offline"
        else
          userVOsDivIndex[idx][userDivCodes.USER_ONLINE_STATUS].className = "user-online"

    drawUserPanel = ->
      userVOIndex = {}
      playerListUl = document.getElementById("nowPlayers")
      playerListUl.innerHML = ""
      i = 0
      while (if 0 <= MAX_PLAYERS_IN_A_GAME then i < MAX_PLAYERS_IN_A_GAME else i > MAX_PLAYERS_IN_A_GAME)
        userLi = document.createElement("li")
        userLi.style.display = "none"
        userImgDiv = document.createElement("div")
        userImgDiv.className = "imgOfWn"
        userImg = document.createElement("img")
        userImg.src = DEFAULT_PLAYER_IMG_URL
        userImgDiv.appendChild userImg
        userOnlineStatusDiv = document.createElement("div")
        userOnlineStatusDiv.className = "offline"
        userImgDiv.appendChild userOnlineStatusDiv
        userLi.appendChild userImgDiv
        userDisplayNameDiv = document.createElement("div")
        userDisplayNameDiv.className = "uName"
        userLi.appendChild userDisplayNameDiv
        playerListUl.appendChild userLi
        userVODivIndexObj = {}
        userVODivIndexObj[userDivCodes.USER_LI] = userLi
        userVODivIndexObj[userDivCodes.USER_IMAGE] = userImg
        userVODivIndexObj[userDivCodes.USER_DISPLAY_NAME] = userDisplayNameDiv
        userVODivIndexObj[userDivCodes.USER_ONLINE_STATUS] = userOnlineStatusDiv
        userVOsDivIndex[i] = userVODivIndexObj
        (if 0 <= MAX_PLAYERS_IN_A_GAME then i++ else i--)
      flag_drawUserPanel = true
      refreshUserPanel()

    pushToUserVO = (event, userVO, flag_updateUserIdx) ->
      if userVO and userVO[zzGlobals.clientCodes.USER_ID]
        zzGlobals.userVOs[userVO[zzGlobals.clientCodes.USER_ID]] = userVO
        updateUserIndex()
      refreshUserPanel()  if flag_drawUserPanel

    removeFromUserVO = (event, clientID) ->
      clientName = undefined
      if clientID and zzGlobals.userVOsIndex[clientID]
        clientName = zzGlobals.userVOsIndex[clientID][zzGlobals.clientCodes.USER_DISPLAY_NAME]
        delete zzGlobals.userVOs[zzGlobals.userVOsIndex[clientID][zzGlobals.clientCodes.USER_ID]]

        jDocument.trigger zzEvents.CLIENT_LEFT, [ clientID, clientName ]
        updateUserIndex()

    jQuery ->
      docElems["currentWinnerName"] = document.getElementById("jCurrentWinnerName")
      docElems["currentWinnerImage"] = document.getElementById("jCurrentWinnerImage")
      docElems["prizeName"] = document.getElementById("")
      jDocument.bind zzEvents.JOINED_ROOM, drawUserPanel
      jDocument.bind zzEvents.RECEIVE_USERVO, pushToUserVO
      jDocument.bind zzEvents.REMOVE_USERVO, removeFromUserVO


  OloChatComponent = (->
    OloChatComponent = ->
    chatPane = null
    chatInput = null
    displayChatMessage = (messageClass, message, user) ->
      msgSpan = undefined
      msgUser = undefined
      msgSpan = document.createElement("span")
      msgSpan.className = messageClass
      if user
        msgUser = document.createElement("label")
        msgUser.innerHTML = user
        msgSpan.appendChild msgUser
      msgSpan.appendChild document.createTextNode(message)
      msgSpan.appendChild document.createElement("br")
      chatPane.appendChild msgSpan
      chatPane.removeChild chatPane.firstChild  if chatPane.childNodes.length > 700
      chatPane.scrollTop = chatPane.scrollHeight

    sendMessageToRoomFn = (event) ->
      msg = undefined
      msg = chatInput.value
      msg = (if msg? then msg else msg.trim())
      jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_MESSAGE_TO_ROOMS, "CHAT_MESSAGE", roomID, "true", "", msg ]  if msg?
      chatInput.value = ""
      chatInput.focus()

    closeConnectionMessage = ->
      displayChatMessage "systemChatMessage", "Game connection closed."

    userSignedOutMessage = (event, clientID, clientName) ->
      if isDevEnvironment
      	console.log "userSignedOutMessage"
      	console.log arguments
      displayChatMessage "systemChatMessage", clientName + " left the game."

    receiveChatMessage = (event, className, msg, userName) ->
      displayChatMessage className, msg, userName

    messageListener = (event, messageName, broadcastType, fromClientID, roomID, message, orbiterClientId) ->
      switch messageName
        when "CHAT_MESSAGE"
          className = null
          userName = null
          if orbiterClientId is fromClientID
            displayChatMessage "myChatMessage", message, "me : "
          else
            userName = zzGlobals.getUserName(fromClientID)
            displayChatMessage "userChatMessage", message, userName + " : "

    clientJoined = (event, userVO) ->
      if isDevEnvironment
      	console.log "clientJoined"
      	console.log arguments
      userName = userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME]
      displayChatMessage "systemChatMessage", userName + " joined the game."

    jQuery ->
      chatPane = document.getElementById("chatPane")
      chatInput = document.getElementById("outgoingChat")
      jDocument.bind zzEvents.SERVER_MESSAGE, messageListener
      jDocument.bind zzEvents.CLIENT_JOINED, clientJoined
      jDocument.bind zzEvents.SEND_CHAT_MESSAGE, sendMessageToRoomFn
      jDocument.bind zzEvents.CONNECTION_CLOSE, closeConnectionMessage
      jDocument.bind zzEvents.CLIENT_LEFT, userSignedOutMessage

    OloChatComponent
  )()
  sendChatMessage = ->
    jDocument.trigger zzEvents.SEND_CHAT_MESSAGE

  acceptTermsForSeat = ->
    checkObj = document.getElementById("checkCondition")
    if (checkObj?) and checkObj.checked is true
      return true
    else
      messagePopup "Please accept the terms."
    false

  redirectToPlayNow = (gameInstId) ->
    _this = this
    urlToRe = ofbizUrl + "playnowGame?gameInstId=" + gameInstId
    jQuery.ajax ->
      url: urlToRe
      success: (data) ->
        jQuery("#gameScreenPanel").html data

  jQuery ->
    jDocument.bind "room:ZS", printOnConsole
    jDocument.bind "room:AR", printOnConsole
    jDocument.bind "room:FR", noUserExist
    
  noUserExist = ->
    if zzGlobals.roomVars.FR is "2"
      messagePopup("Game cancel!!! <br/>  Insufficient Users in Game")
      jQuery("#gameBetPanel").hide()
    
  printOnConsole = (obj, val) ->
    if (obj?) and (obj.type?) and (val?)
      console.log obj.type, " : ", val if isDevEnvironment
      if obj.type is "room:AR"
        roundVOs = {}
        roundObjs = jQuery.parseJSON(val)
        for i of roundObjs
          roundStr = roundObjs[i]
          roundObj = jQuery.parseJSON(roundStr)
          roundVOs[i] = roundObj
        console.log "RoundVOs :", roundVOs if isDevEnvironment
      if obj.type is "room:BV"
        roundVOs = {}
        roundObjs = jQuery.parseJSON(val)
        for i of roundObjs
          roundStr = roundObjs[i]
          roundObj = jQuery.parseJSON(roundStr)
          roundObj["PR"] = jQuery.parseJSON(roundObj["PR"])  if roundObj["PR"]?
          roundVOs[i] = roundObj
        console.log "TilesVOs :", roundVOs if isDevEnvironment

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
      	messagePopup "Not so fast.... lets wait for your friends to play their turn"
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
        messagePopup "No bets placed!"
        return false
      else unless betCtr is 9
        console.log "Bets count is less then 9!" if isDevEnvironment
        messagePopup "Not so fast... please place all of your 9 tiles!"
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
      		 	   messagePopup(popupMSG.declineInvite())
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
        	console.log 'MT',zzGlobals.msgVars.RH if isDevEnvironment
        	jDocument.trigger "client:" + zzGlobals.msgCodes.RIGHT_HUD,usersObject
        when zalerioCMDListners.CLOSE_INVITE
        	if 0 is message
        		messagePopup('Sorry!!! Unable to Close Invite, <br /> Plese try again..');
        	else
        		jQuery(".status_show_popup").remove();
        		jQuery(".gdWrapper").remove();
        when zalerioCMDListners.RESIGN_GAME
        	if message is 0
        		messagePopup('Sorry!!! Unable to Resign, \n Plese try again..');
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
  basicFx = ->
    docElems = {}
    pageRefresh = ->
      timer = null
      enableTimerFn = ->
        _this = this
        timer = setTimeout(->
          location.reload true
        , 5000)

      clearTimerFn = ->
        timer = null

      returnObj =
        isTimerEnabled: ->
          if timer
            true
          else
            false

        enableTimer: ->
          enableTimerFn()

        clearTimer: ->
          clearTimerFn()

  basicFx()
  updateClock = ->
    secs = ttl--
    if secs > 0
      timeRemaining = secs + " secs"
      classForTime = "clockRed"
      flag_gameStarted = true
      if gameDuration < secs
        secs = secs - gameDuration
        flag_gameStarted = false
      if not flag_gameJustStarted and flag_gameStarted
        flag_gameJustStarted = true
        refreshBidPanel()
      if (secs > 100 and flag_gameStarted) or not flag_gameStarted
        hours = Math.floor(secs / (60 * 60))
        divisor_for_minutes = secs % (60 * 60)
        minutes = Math.floor(divisor_for_minutes / 60)
        divisor_for_seconds = divisor_for_minutes % 60
        seconds = Math.ceil(divisor_for_seconds)
        timeRemaining = pad2(hours) + " : " + pad2(minutes) + " : " + pad2(seconds)
        if flag_gameStarted
          classForTime = "clockBlack"
        else
          timeRemaining = "Starting in : " + timeRemaining
          classForTime = "clockBefore"
      if (typeof docElems isnt "undefined" and docElems isnt null) and (docElems["clock"]?)
        docElems["clock"].className = classForTime
        docElems["clock"].innerHTML = timeRemaining
    else
      timeRemaining = "Time Up!"
      docElems["clock"].innerHTML = timeRemaining  if (typeof docElems isnt "undefined" and docElems isnt null) and (docElems["clock"]?)

  changeTotalUserCount = ->
    docElems["totalPlayers"].innerHTML = zzGlobals.roomVars[zzGlobals.roomCodes.USER_TOTAL]  if (typeof docElems isnt "undefined" and docElems isnt null) and (docElems["totalPlayers"]?)

  changeActiveUserCount = ->
    docElems["totalOnlinePlayers"].innerHTML = zzGlobals.roomVars[zzGlobals.roomCodes.USER_COUNT]  if (typeof docElems isnt "undefined" and docElems isnt null) and (docElems["totalOnlinePlayers"]?)

  messageListener = ->
    {}

  winnerChange = ->
    console.log "Winner changed!", arguments if isDevEnvironment

  jQuery ->
    jDocument.bind zzEvents.SERVER_MESSAGE, messageListener
    jDocument.bind zzEvents.WINNER_CHANGE, winnerChange
    jDocument.bind "room:" + zzGlobals.roomCodes.USER_TOTAL, changeTotalUserCount
    jDocument.bind "room:" + zzGlobals.roomCodes.USER_COUNT, changeActiveUserCount

  OloClockComponent = (->
    OloClockComponent = ->
    flag_gameJustStarted = false
    docElems = {}
    updateClock = (event, ttl) ->
      gameDuration = zzGlobals.getGameDuration()
      secs = zzGlobals.getTtl()
      if secs > 0
        timeRemaining = secs + " secs"
        classForTime = "clockRed"
        flag_gameStarted = true
        if gameDuration < secs
          secs = secs - gameDuration
          flag_gameStarted = false
        flag_gameJustStarted = true  if not flag_gameJustStarted and flag_gameStarted
        gameTimeText = "Ending in"
        if (secs > 100 and flag_gameStarted) or not flag_gameStarted
          hours = Math.floor(secs / (60 * 60))
          divisor_for_minutes = secs % (60 * 60)
          minutes = Math.floor(divisor_for_minutes / 60)
          divisor_for_seconds = divisor_for_minutes % 60
          seconds = Math.ceil(divisor_for_seconds)
          timeRemaining = pad2(hours) + " : " + pad2(minutes) + " : " + pad2(seconds)
          if flag_gameStarted
            classForTime = "clockBlack"
          else
            gameTimeText = "Starting in"
            timeRemaining = timeRemaining
            classForTime = "clockBefore"
        if (docElems?) and (docElems["clock"]?)
          docElems["gameTimeText"].innerHTML = (if gameTimeText then gameTimeText else "")
          docElems["clock"].className = classForTime
          docElems["clock"].innerHTML = timeRemaining
      else
        timeRemaining = "Time Up!"
        if (docElems?) and (docElems["clock"]?)
          docElems["gameTimeText"].innerHTML = ""
          docElems["clock"].innerHTML = timeRemaining

    jQuery ->
      docElems =
        clock: document.getElementById("clock")
        gameTimeText: document.getElementById("gameTimeText")

      jDocument.bind zzEvents.UPDATE_LOCAL_TTL, updateClock

    OloClockComponent
  )()
  ZalerioUserComponent = (->
    ZalerioUserComponent = ->
    onClickAddRedrict = (userRecodeDivValue, gameID, remove) ->
    	game = gameID
    	jQuery(jQuery(userRecodeDivValue)).click(->
    			if typeof remove isnt 'undefined'
    				jQuery(remove).remove()
    			gameChangeListener(game)
    		)
    	return
        
    onClickRedrict = (userRecodeDivValue, gameID) ->
        game = undefined
        game = gameID
        #gameChangeListener(game)
        window.location = "http://" + document.domain + baseUrl + "/gameinst/play?gameinst_id=" + game
        
    flag_drawUserPanel = false
    docElems = {}
    userPanelMov = null
    docElemsCodes =
      LEFT_HUD_YOUR_TURN: "leftHudYourTurn"
      LEFT_HUD_MY_TURN: "leftHudMyTurn"

    DEFAULT_PLAYER_IMG_URL = ""
    PLAYER_ONLINE_IMG_URL = ""
    PLAYER_OFFLINE_IMG_URL = ""
    showAllUsers = "ALL"
    userStatusCurrent = 10
    userVOsLen = 0
    userVOsDivIndex = {}
    MAX_PLAYERS_IN_A_GAME = 0
    userScoreObj = {}
    userScoreArr = []
    currentNoOfUsers = 0
    userDivCodes =
      USER_PIC_TOP_HUD_LI: "userPicTopHUDLi"
      USER_PIC_TOP_HUD_IMG: "userPicTopHUDImg"
      USER_PIC_TOP_HUD_NAME: "userPicTopHUDName"
      USER_PIC_TOP_HUD_LAST_PLAY: "userPicTopHUDLastPlay"
      USER_PIC_TOP_HUD_PLAY_STATUS: "userPicTopHUDPlayStatus"
      USER_PIC_TOP_HUD_REMIND: "userPicTopHUDRemind"
      USER_TOP_HUD_TR: "userTopHUDTr"
      USER_TOP_HUD_SERAIL: "userTopHUDSerial"
      USER_TOP_HUD_NAME: "userTopHUDName"
      USER_TOP_HUD_SCORE: "userTopHUDScore"
      USER_TOP_HUD_ONLINE: "userTopHUDOnline"
      USER_TOP_HUD_BETSTATUS: "userTopHUDBetstatus"
      USER_LEFT_HUDMT_LI: "userLeftHUDMTLi"
      USER_LEFT_HUDMT_IMG: "userLeftHUDMTImg"
      USER_LEFT_HUDMT_NAME: "userLeftHUDMTName"
      USER_LEFT_HUDMT_BET_MSG: "userLeftHUDMTBetMsg"
      USER_LEFT_HUDMT_LASTBET_MSG: "userLeftHUDMTLastbetMsg"
      USER_LEFT_HUDYT_LI: "userLeftHUDYTLi"
      USER_LEFT_HUDYT_IMG: "userLeftHUDYTImg"
      USER_LEFT_HUDYT_NAME: "userLeftHUDYTName"
      USER_LEFT_HUDYT_BET_MSG: "userLeftHUDYTBetMsg"
      USER_LEFT_HUDYT_LASTBET_MSG: "userLeftHUDYTLastbetMsg"

    updateUserIndex = ->
      zzGlobals.userVOsIndex = {}
      for i of zzGlobals.userVOs
        zzGlobals.userVOsIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_UNION_ID]] = zzGlobals.userVOs[i]
      updateUserIndexForPartyId()
      updateUserIndexForSeatId()

    updateUserIndexForPartyId = ->
      i = undefined
      _results = undefined
      _results = []
      for i of zzGlobals.userVOs
        unless zzGlobals.userVOsPartyIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_PARTY_ID]]
          _results.push zzGlobals.userVOsPartyIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_PARTY_ID]] = zzGlobals.userVOs[i]
        else
          _results.push undefined
      _results

    updateUserIndexForSeatId = ->
      _results = []
      for i of zzGlobals.userVOs
        unless zzGlobals.userVOsSeatIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_SEAT_ID]]
          _results.push zzGlobals.userVOsSeatIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_SEAT_ID]] = zzGlobals.userVOs[i]
        else
          _results.push undefined
      _results

    updateUserIndexForPartyIdViaOfflinePlayers = ->
      _results = []
      for i of zzGlobals.offlinePlayers
        unless zzGlobals.userVOsPartyIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_PARTY_ID]]
          _results.push zzGlobals.userVOsPartyIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_PARTY_ID]] = zzGlobals.offlinePlayers[i]

      _results

    updateUserIndexForSeatIdViaOfflinePlayers = ->
      i = undefined
      _results = undefined
      _results = []
      for i of zzGlobals.offlinePlayers
        unless zzGlobals.userVOsSeatIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_SEAT_ID]]
          _results.push zzGlobals.userVOsSeatIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_SEAT_ID]] = zzGlobals.offlinePlayers[i]
        else
          _results.push undefined
      _results

    refreshUserPanel = ->
      arrLen = undefined
      userCtr = undefined
      userPos = undefined
      userVO = undefined
      userCtr = 0
      console.log "refreshUserPanel : ", userScoreArr if isDevEnvironment
      arrLen = userScoreArr.length
      userPos = 0
      while (if 0 <= arrLen then userPos < arrLen else userPos > arrLen)
        userVO = zzGlobals.userVOsSeatIndex[userScoreArr[userPos]]
        console.log userVO if isDevEnvironment
        renderUserVOToUserPanel userCtr++, userVO, null, userPos + 1  if userVO
        (if 0 <= arrLen then userPos++ else userPos--)
      if (typeof getGameInstWithFriends isnt "undefined" and getGameInstWithFriends isnt null) and not zzGlobals.offlinePlayers and getGameInstWithFriends(zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID])
        zzGlobals.offlinePlayers = getGameInstWithFriends(zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID])
        updateUserIndexForPartyIdViaOfflinePlayers()
        updateUserIndexForSeatIdViaOfflinePlayers()
        changeWinner()

    renderUserVOToUserPanel = (idx, userVO, userOffline, userSerial) ->
      userDisplayName = undefined
      userFacebookImgUrl = undefined
      userOffline = userOffline or false
      if userVOsDivIndex[idx] and userVO and userVO[zzGlobals.clientCodes.USER_ID]
        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_TR].style.visibility = "visible"
        userDisplayName = "-"
        userDisplayName = userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME]  if userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME]?
        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_NAME].innerHTML = userDisplayName
        userFacebookImgUrl = DEFAULT_PLAYER_IMG_URL
        userFacebookImgUrl = "https://graph.facebook.com/" + userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID] + "/picture"  if (userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID]?) and userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID].length > 2
        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SERAIL].innerHTML = userSerial + "."
        if userVO[zzGlobals.clientCodes.USER_SEAT_ID]
          userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SCORE].innerHTML = userScoreObj[userVO[zzGlobals.clientCodes.USER_SEAT_ID]]
        else
          userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SCORE].innerHTML = 0
        unless (zzGlobals.roomVars.PP).indexOf(userVO[zzGlobals.clientCodes.USER_SEAT_ID]) is -1
          $(userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_BETSTATUS]).removeClass "arrowclass"
        else
          $(userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_BETSTATUS]).addClass "arrowclass"
        console.log userScoreObj if isDevEnvironment

	# draw left site round panel and score board
    drawUserPanel = ->
      console.log "drawUserPanel" if isDevEnvironment
      playerListTopHUDTbl = document.getElementById("userScoreHUDMain")
      playerListTopHUDTbl.innerHTML = ""
      usersObject = jQuery.parseJSON(zzGlobals.roomVars.AP)
      console.log "Score Board left (zzGlobals.roomVars.AP)", usersObject if isDevEnvironment
      for i of usersObject
        usersInfoObject = jQuery.parseJSON(usersObject[i])
      i = 0
      usersInfoObject = jQuery.parseJSON(usersInfoObject.PLRS)
      scoreArray = []
      seatIdArray = []
      usersInfoObjectx=[]
      for seatId of usersInfoObject
      	usersInfoObjectx[seatId] = jQuery.parseJSON(usersInfoObject[seatId])
      	scoreArray[seatId] = parseInt(usersInfoObjectx[seatId].PSC)
      	seatIdArray.push seatId
      seatIdArray.sort (x, y) ->
      	scoreArray[y] - scoreArray[x]

      for y of seatIdArray
        x = seatIdArray[y]
        userObject = jQuery.parseJSON(usersInfoObject[x])
        continue if userObject.GSS is 3 or userObject.GSS is 5
        console.log "drawing for user #", i + 1 if isDevEnvironment
        userTopHUDTr = document.createElement("tr")
        userTopHUDTr.className = "userScoreHUD_player"
        userTopHUDBetsPlacedTd = document.createElement("td")
        userTopHUDBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo arrowclass"
        userTopHUDBetsPlacedTd.innerHTML = ""
        userTopHUDBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo"  if userObject.CRS is 9
        userTopHUDTr.appendChild userTopHUDBetsPlacedTd
        userTopHUDSerialNumTd = document.createElement("td")
        userTopHUDSerialNumTd.className = "userScoreHUD_playerSerial"
        userTopHUDSerialNumTd.innerHTML = ++i
        userTopHUDTr.appendChild userTopHUDSerialNumTd
        userTopHUDNameTd = document.createElement("td")
        userTopHUDNameTd.className = "userScoreHUD_playerName"
        userTopHUDNameTd.innerHTML = ""
        if userObject.PDN
          if parseInt(userObject.PRE) != 1
              if parseInt(userObject.PON) == 0
                  userTopHUDTr.className = "userScoreHUD_player GRAY"
              userTopHUDNameTd.innerHTML = userObject.PDN
          else
              if parseInt(userObject.UI) is parseInt(zzGlobals.currentUserDBId)
              		jQuery("#gameBetPanel").hide()
              		jQuery(".draggableBets").attr("draggable","false");
              s = document.createElement("s");
              s.innerHTML = userObject.PDN;
              userTopHUDNameTd.appendChild(s);
              userTopHUDBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo" # remove arrow
          
        userTopHUDTr.appendChild userTopHUDNameTd
        userTopHUDScoreTd = document.createElement("td")
        userTopHUDScoreTd.className = "userScoreHUD_playerScore"
        if userObject.PSC
          userTopHUDScoreTd.innerHTML = userObject.PSC
        else
          try
            usersScoreObject = jQuery.parseJSON(zzGlobals.roomVars.ZS)
            userTopHUDScoreTd.innerHTML = if typeof usersScoreObject[x] isnt "undefined"
                usersScoreObject[x] 
            else 0
        userTopHUDTr.appendChild userTopHUDScoreTd
        playerListTopHUDTbl.appendChild userTopHUDTr
      flag_drawUserPanel = true
      console.log "Draw score Panel Bord", userObject if isDevEnvironment
      refreshUserPanel()

    updatePlayerPlate = ->
      currentUserObj = jQuery.parseJSON(zzGlobals.clientVars.UINFO)
      console.log "Player Plate : ", currentUserObj if isDevEnvironment
      jQuery("#currentUserName").text currentUserObj.PDN
      unless typeof currentUserObj.PL is "undefined"
        if parseInt(currentUserObj.PL) is 0
        	currentUserObj.PL = 1
        currentUserLevel = document.createElement("img")
        currentUserLevel.className = "userlevelbelt"
        currentUserLevel.src = baseUrl + "/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/" + userLevelImgBig[parseInt(currentUserObj.PL) - 1]
        jQuery("#currentUserAreaImg .userlevelbelt").remove()
        jQuery("#currentUserAreaImg").prepend currentUserLevel

    updateLeftHud = ->
      usersObject = jQuery.parseJSON(zzGlobals.roomVars.AP)
      console.log "All Users Data (zzGlobals.roomVars.AP)", usersObject if isDevEnvironment
      for i of usersObject
        usersInfoObject = jQuery.parseJSON usersObject[i]
        break
      drawCarousel usersInfoObject, "userTopPicHUDMain", i, "usercrousel"
      userPanelMov = new UserPanelMov(jQuery("#userTopPicHUDMain"), null, null, null, crouselObjSize(jQuery.parseJSON(usersInfoObject.PLRS)))
      jQuery("#navCarouselRight").unbind('click').click ->
        userPanelMov.move "RIGHT", panelMoveMagnitudeCodes.ONE

      jQuery("#navCarouselLeft").unbind('click').click ->
        userPanelMov.move "LEFT", panelMoveMagnitudeCodes.ONE

    drawCarousel = (usersObject, appendto, gameLinkID, typeOfCarousel) ->
      currentPlayerList = document.getElementById(appendto)
      console.log "crousel usersobject", usersObject if isDevEnvironment
      usersObjectPastRound = usersObject
      usersObject = jQuery.parseJSON(usersObject.PLRS)
      remindUsersLeft = {}    		
      $("#" + appendto).empty()
      for x of usersObject
        userObject = jQuery.parseJSON(usersObject[x])
        remindUsersLeft[userObject.PFB] = { GSS : userObject.GSS, CRS : userObject.CRS } 
        #skip if resign game
        #return if userObject.PRE is 1 and gameInstId isnt gameLinkID and userObject.UI is zzGlobals.currentUserDBId
        
        # skep user if user declined game 
        continue if userObject.GSS is 3 or userObject.GSS is 5
        userRecodeLi = document.createElement("li")
        userRecodeLi.style.display = "block"
        userRecodeDiv = document.createElement("div")
        userRecodeDiv.className = "userArea"
        userRecodeDiv.id = "thisgame_seat_"+x
        #if parseInt(gameInstId) is parseInt(gameLinkID)
        	 #userRecodeDiv.className = "userArea current"       	
        userRecodeImgDiv = document.createElement("div")
        userRecodeImgDiv.className = "userAreaImg"
        
        # redirect to game on crousel click
        #onClickAddRedrict userRecodeDiv, gameLinkID  if typeOfCarousel isnt "usercrousel" and parseInt(userObject.GSS) isnt 1 and gameLinkID isnt gameInstId
        
        userRecodeImgEl = document.createElement("img")
        userRecodeImgEl.className = "backendImage"
        userRecodeImgDiv.appendChild userRecodeImgEl
        unless typeof userObject.PL is "undefined"
          if parseInt(userObject.PL) is 0
          		userObject.PL = 1
          userRecodeImgLevelEl = document.createElement("img")
          userRecodeImgLevelEl.className = "userlevelbelt otherbelt"
          userRecodeImgLevelEl.src = baseUrl + "/images/zalerio_1.2/4.ingame_ui/carauselbelts_otherplayers/" + userLevelImg[parseInt(userObject.PL) - 1]
          userRecodeImgDiv.appendChild userRecodeImgLevelEl
        userRecodePlayerNameDiv = document.createElement("div")
        userRecodePlayerNameDiv.className = "userAreaName"
        userRecodeLastPlayDiv = document.createElement("div")
        userRecodeLastPlayDiv.className = "userLastPlayed"
        userRecodePlayStatusDiv = document.createElement("div")
        userRecodePlayStatusDiv.className = "userPlayStatus"
        userRecodePlayerRemindDiv = document.createElement("div")
        userRecodePlayerRemindDiv.className = "userRemind"
        userRecodePlayerRoundNoDiv = document.createElement("div")
        userRecodePlayerRoundNoDiv.className = "userRoundNo"
        userRecodePlayerOnlineStatusDiv = document.createElement("div")
        userRecodePlayerOnlineStatusDiv.className = "userOnlineStatus"
        
        # game invitation msg
        userAcceptDeclinedDiv = document.createElement("div")
        userAcceptDeclinedDiv.className = "userAcceptDeclinedDiv"
        
        userAcceptDeclinedMsgDiv = document.createElement("div")
        userAcceptDeclinedMsgDiv.innerHTML = "Invited you (NEW)"
        acceptSpan = document.createElement("span")
        acceptSpan.className = "acceptSpan"
        acceptSpan.innerHTML = "Accept"
                
        # function call to redirect to new game
        acceptSpan.onclick = () -> acceptInvitation gameLinkID
        
        declineSpan = document.createElement("span")
        declineSpan.innerHTML = "Decline"
        declineSpan.className = "declineSpan"
        
        # function call to send declined msg to union server
        declineSpan.onclick = () -> sendDeclinedToServer userObject.PSI
        
        userRecodeDiv.appendChild userRecodeImgDiv
        userRecodeDiv.appendChild userRecodePlayerNameDiv

        userRecodePlayStatusDiv.innerHTML = "not played yet"
        if parseInt(userObject.GSS) isnt 1
        	userRecodeDiv.appendChild userRecodeLastPlayDiv
        	userRecodeDiv.appendChild userRecodePlayStatusDiv
        	userRecodeDiv.appendChild userRecodePlayerRemindDiv
        else if parseInt(userObject.UI) is parseInt(zzGlobals.currentUserDBId)
       		userAcceptDeclinedDiv.appendChild userAcceptDeclinedMsgDiv
	        userAcceptDeclinedDiv.appendChild acceptSpan
	        userAcceptDeclinedDiv.appendChild declineSpan
	        # add to crousel
	        userRecodeDiv.appendChild userAcceptDeclinedDiv
	        userRecodePlayStatusDiv.innerHTML = ''
        else
        	userRecodeDiv.appendChild userRecodePlayStatusDiv
        	userRecodeDiv.appendChild userRecodePlayerRemindDiv
	        userRecodePlayStatusDiv.innerHTML = 'not accepted'
       		
        userRecodeDiv.appendChild userRecodePlayerRoundNoDiv
        userRecodeDiv.appendChild userRecodePlayerOnlineStatusDiv
        userRecodeLi.appendChild userRecodeDiv
        currentPlayerList.appendChild userRecodeLi
        userFacebookImgUrl = "https://graph.facebook.com/" + userObject.PFB + "/picture"
        userRecodeImgEl.src = userFacebookImgUrl
        userRecodePlayerNameDiv.innerHTML = userObject.PDN  if userObject.PDN
        userRecodeLastPlayDiv.innerHTML = userObject.PLP  if userObject.PLP
        userRecodePlayerRoundNoDiv.innerHTML = userObject.PCR  if userObject.PCR
        if typeOfCarousel and usersObjectPastRound.SD
          userRecodeGameStartDateUserDiv = document.createElement("div")
          userRecodeGameStartDateUserDiv.className = "userStartDate"
          userRecodeGameStartDateUserDiv.innerHTML = usersObjectPastRound.SD
          userRecodeDiv.appendChild userRecodeGameStartDateUserDiv
        if typeOfCarousel is "pastGame" and usersObjectPastRound.TP and userObject.PR
          userRecodePlayerTotalUserDiv = document.createElement("div")
          userRecodePlayerTotalUserDiv.className = "userTotal"
          if userObject.PR is "1"
            userRank = userObject.PR + "<sup>st</sup>"
          else if userObject.PR is "2"
            userRank = userObject.PR + "<sup>nd</sup>"
          else if userObject.PR is "3"
            userRank = userObject.PR + "<sup>rd</sup>"
          else
            userRank = userObject.PR + "<sup>th</sup>"
          userRecodePlayerTotalUserDiv.innerHTML = userRank + "/" + usersObjectPastRound.TP
          userRecodeDiv.appendChild userRecodePlayerTotalUserDiv
        if userObject.PDN and zzGlobals.clientVars.UI
          if userObject.CRS is 0
            if zzGlobals.clientVars.UI is userObject.UI
              userRecodePlayerRemindDiv.innerHTML = "place your tiles..."
            else
            	unless zzGlobals.clientVars.UI is userObject.UI
            		fbUser = {}
            		fbUser[userObject.PFB] = { GSS : userObject.GSS, CRS : userObject.CRS }
            		remindUserAdd gameLinkID, remindUsersLeft, x, fbUser,userRecodePlayerRemindDiv
            		userRecodePlayerRemindDiv.innerHTML = "remind " + userObject.PDN.slice(0, 6)                           
        if userObject.CRS is 0
          userRecodePlayStatusDiv.className = "userPlayStatus"
        else if userObject.CRS is 5
          userRecodePlayStatusDiv.innerHTML = '...playing now'
          userRecodePlayStatusDiv.className = "userPlayStatus green"
        else if userObject.CRS is 9
          userRecodePlayStatusDiv.innerHTML = "finished round"
          userRecodePlayStatusDiv.className = "userPlayStatus green"
        if userObject.PON is 1
          userRecodePlayerOnlineStatusDiv.className = "userOnlineStatus status_online"
        else
          userRecodePlayerOnlineStatusDiv.className = "userOnlineStatus"

# new logic implimentation on 16 Aug 2012
    onMouseOverShowDetails = (urDiv,gameDetails,gameId)->
    	urDiv.onclick = ->
    		try
    			selectbuttonSound.play() if playSound
    		createGameDetailsPopup gameDetails,gameId
    		
    createGameDetailsPopup = (gameDetails,gameId)->
    	jQuery('.gdWrapper').remove()
    	#console.log 'gameDetails',gameDetails
    	gdDiv = document.createElement("div")
    	gdDiv.className = 'gdWrapper  animated fadeInRight'
	
    	#gdRightImgDiv = document.createElement("div")
    	#gdRightImgDiv.className = 'gdRightImage'
    	#gdDiv.appendChild gdRightImgDiv
    	
    	gdScoreCardDiv = document.createElement("div")
    	gdScoreCardDiv.className = 'gdScoreCardDiv'
    	gdDiv.appendChild gdScoreCardDiv
    	
    	gdAllRoundDiv = document.createElement("div")
    	gdAllRoundDiv.className = 'gdAllRoundDiv'
    	gdAllRoundDiv.id = 'gdAllRoundDivId'
    	gdScoreCardDiv.appendChild gdAllRoundDiv
    	drawRoundsPanel(gdAllRoundDiv)
    	
    	try
	    	for roundId in [1..7]
		        el = roundVOsIdxRightHUD[roundId]
		        console.log('roundVOsIdx',roundVOsIdxRightHUD)
		        if roundId > parseInt gameDetails.CR
		          el.className = "notPlayedRound"
		        else
		          el.className = "doneRound"
		        el.className = "currentRound"  if roundId is parseInt gameDetails.CR
		        lastEl = el
		        lastRoundId = roundId
		      if lastEl and lastRoundId
		        if lastRoundId is parseInt gameDetails.CR
		          lastEl.className = "currentFinalRound"
		        else
	          lastEl.className = "finalRound"
    	
    	gdScoreInfoDiv = document.createElement("div")
    	gdScoreInfoDiv.className = 'gdScoreInfoDiv'
    	gdScoreCardDiv.appendChild gdScoreInfoDiv
    	
    	gdScoreCardTbl = document.createElement("table")
    	gdScoreInfoDiv.appendChild gdScoreCardTbl
    	
    	gdUsersImgdiv = document.createElement("div")
    	gdUsersImgdiv.className = 'gdUsersImgdiv'
    	gdDiv.appendChild gdUsersImgdiv

    	gdReminddiv = document.createElement("div")
    	gdReminddiv.className = 'gdReminddiv'

    	gdDiv.appendChild gdReminddiv
    	
    	#gdCloseInvitediv = document.createElement("div")
    	#gdCloseInvitediv.className = 'gdCloseInvitediv'
    	#gdCloseInvitediv.innerHTML = 'Close Invitation'
    	#gdCloseInvitediv.onclick = ->
    		#try
    			#otherbuttonSound.play() if playSound
    		#closeInvits()
    	#gdDiv.appendChild gdCloseInvitediv
    	
    	gdReturndiv = document.createElement("div")
    	gdReturndiv.className = 'gdReturndiv'
    	gdReturndiv.onclick = ->
    		try
    			closebutton.play() if playSound
    		jQuery('.gdWrapper').remove()
    	gdDiv.appendChild gdReturndiv
    	
    	gdPlaydiv = document.createElement("div")
    	gdPlaydiv.className = 'gdPlaydiv'
    	gdDiv.appendChild gdPlaydiv
    	
    	if gameId isnt gameInstId
    		onClickAddRedrict gdPlaydiv, gameId, '.gdWrapper'
    		gdPlaydiv.className = 'gdResumeDiv'
    	else
    		gdPlaydiv.onclick = ->
    			try
    				closebutton.play() if playSound
    			jQuery('.gdWrapper').remove()
    	## round panel
    	#for(i=0; i<7;i++)
    	
    	i = 0
    	remindUsersData = {}
    	for index of gameDetails.PLSC
    		gameSeatID = gameDetails.PLSC[index]
    		
    		# create remind user object
    		remindUsersData[gameDetails.PLRS[gameSeatID].PFB] = { GSS : gameDetails.PLRS[gameSeatID].GSS, CRS : gameDetails.PLRS[gameSeatID].CRS }
    		
    		if gameDetails.PLRS[gameSeatID].GSS is 5 or  gameDetails.PLRS[gameSeatID].GSS is 3
    			continue
    		gdUserImgDiv = document.createElement("div")
    		gdUserImgDiv.className = 'gdUserImgDiv user_offline'
    		gdUserImg = document.createElement("img")
    		gdUserImg.src =	'https://graph.facebook.com/' + gameDetails.PLRS[gameSeatID].PFB + '/picture'
    		gdUserImg.className = 'gdUserImg'
    		if gameDetails.PLRS[gameSeatID].PON is 1
    			gdUserImg.className = 'gdUserImg user_online' 
    		    		
    		gdUserImgbelt = document.createElement("img")
    		gdUserImgbelt.src = baseUrl + "/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/" + userLevelImgBig[parseInt(gameDetails.PLRS[gameSeatID].PL) - 1]
    		gdUserImgbelt.className = 'gdUserImgbelt'
    		
    		gdUserImgDiv.appendChild gdUserImgbelt
    		gdUserImgDiv.appendChild gdUserImg
    		gdUsersImgdiv.appendChild gdUserImgDiv
    		    		
    		gdScoreCardTr = document.createElement("tr")
    		gdScoreCardTr.className = "userScoreHUD_player"
    		gdScoreCardBetsPlacedTd = document.createElement("td")
    		gdScoreCardBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo arrowclass"
    		gdScoreCardBetsPlacedTd.innerHTML = ""
    		gdScoreCardBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo"  if gameDetails.PLRS[gameSeatID].CRS is 9
    		gdScoreCardTr.appendChild gdScoreCardBetsPlacedTd
    		gdScoreCardSerialNumTd = document.createElement("td")
    		gdScoreCardSerialNumTd.className = "userScoreHUD_playerSerial"
    		gdScoreCardSerialNumTd.innerHTML = ++i
    		gdScoreCardTr.appendChild gdScoreCardSerialNumTd
    		gdScoreCardNameTd = document.createElement("td")
    		gdScoreCardNameTd.className = "userScoreHUD_playerName"
    		gdScoreCardNameTd.innerHTML = ""
    		if gameDetails.PLRS[gameSeatID].PDN
    			if parseInt(gameDetails.PLRS[gameSeatID].PRE) != 1
    				if parseInt(gameDetails.PLRS[gameSeatID].PON) == 0
    					gdScoreCardTr.className = "userScoreHUD_player GRAY"
    				gdScoreCardNameTd.innerHTML = gameDetails.PLRS[gameSeatID].PDN
    			else
    				if parseInt(gameDetails.PLRS[gameSeatID].UI) is parseInt(zzGlobals.currentUserDBId)
    					s = document.createElement("s")
    					s.innerHTML = userObject.PDN
    					gdScoreCardNameTd.appendChild(s)
    					gdScoreCardBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo" # remove arrow
	          
    		gdScoreCardTr.appendChild gdScoreCardNameTd
    		gdScoreCardScoreTd = document.createElement("td")
    		gdScoreCardScoreTd.className = "userScoreHUD_playerScore"
    		if gameDetails.PLRS[gameSeatID].PSC
	        	gdScoreCardScoreTd.innerHTML = gameDetails.PLRS[gameSeatID].PSC
    		else
	        	gdScoreCardScoreTd.innerHTML = '0'
    		gdScoreCardTr.appendChild gdScoreCardScoreTd
    		gdScoreCardTbl.appendChild gdScoreCardTr
    	try
    		popupapperence.play() if playSound
    	gdReminddiv.onclick = -> 
        	remindUser gameId,remindUsersData
        	return
    	jQuery('#active-screen').append(jQuery(gdDiv));
    	
    updateRightHud = (event, message)->
    	rightHUD_yourturn = document.getElementById 'rightHUD-yourturn'
    	rightHUD_theirturn = document.getElementById 'rightHUD-theirturn'
    	for gameId of message
    		if document.getElementById("right_hud_"+gameId) is null    			
    			## carousel wrapper
    			urDiv = document.createElement("div")
    			urDiv.className = "userArea"
    			urDiv.id = "right_hud_"+gameId
    			if gameInstId is gameId
    				urDiv.className = "userArea selected"
    			
    			## image wrapper
    			urImageDiv = document.createElement("div")
    			urImageDiv.className = "imageWrapper"
    			urImageDiv.id = "right_hud_Images"+gameId
    			# append image to image wrapper
    			urDiv.appendChild urImageDiv
    			
    			if typeof message[gameId].CR isnt 'undefined'
    				urRoundDiv = document.createElement("div")
    				urRoundDiv.innerHTML = message[gameId].CR
    				urRoundDiv.className = 'round_no'
    				urDiv.appendChild urRoundDiv
    				
    			if typeof message[gameId].ED isnt 'undefined'
    				urGameEndDiv = document.createElement("div")
    				urGameEndDiv.innerHTML = message[gameId].ED
    				urGameEndDiv.className = 'game_end_time'
    				urDiv.appendChild urGameEndDiv
    			for index of message[gameId].PLSC
    				gameSeatID = message[gameId].PLSC[index]
    				if message[gameId].PLRS[gameSeatID].GSS is 5 or  message[gameId].PLRS[gameSeatID].GSS is 3
              continue
    				## user images
    				if typeof message[gameId].PLRS[gameSeatID].PFB isnt 'undefined'
    					imgElement =  document.createElement("img")
    					imgElement.src = 'https://graph.facebook.com/' + message[gameId].PLRS[gameSeatID].PFB + '/picture'
    					imgElement.setAttribute 'alt',message[gameId].PLRS[gameSeatID].PFN
    					imgElement.className =  if message[gameId].PLRS[gameSeatID].PON is 1 then "online" else "offline"
    					imgElement.id = "who_am_i_"+gameSeatID
    					urImageDiv.appendChild imgElement
    				## accept / Decline
    				if typeof message[gameId].PLRS[gameSeatID].GSS isnt 'undefined'
    					if message[gameId].PLRS[gameSeatID].GSS is 1 and zzGlobals.currentUserDBId is message[gameId].PLRS[gameSeatID].UI
    						urInviteDiv = document.createElement("div")
    						urInviteDiv.id= 'accept_decline_'+gameId
    						urInviteDiv.className= 'accept_decline'
    						urInviteAcceptDiv = document.createElement("div")
    						urInviteAcceptDiv.innerHTML = 'Accept'
    						urInviteAcceptDiv.className = 'right_hud_accept'
    						acceptInvitationAdd(urInviteAcceptDiv,gameId)
    						urInviteDeclineDiv = document.createElement("div")
    						urInviteDeclineDiv.innerHTML = 'Decline'
    						urInviteDeclineDiv.className = 'right_hud_decline'
    						declineInvitationAdd(urInviteDeclineDiv, gameSeatID, gameId);
              # append to userArea
    						urInviteDiv.appendChild urInviteAcceptDiv
    						urInviteDiv.appendChild urInviteDeclineDiv
    						urDiv.appendChild urInviteDiv
    					else if zzGlobals.currentUserDBId is message[gameId].PLRS[gameSeatID].UI
    						onMouseOverShowDetails urDiv, message[gameId], gameId
    						
    			if parseInt(message[gameId].GS) is 1
	    			# select carousel and append	    			
	    			rightHUD_yourturn.appendChild urDiv
	    		else if parseInt(message[gameId].GS) is 2	
	    			rightHUD_theirturn.appendChild urDiv
    		else
    			if parseInt(message[gameId].GS) is 0
    				jQuery("#right_hud_"+gameId).remove()
    				continue
    			else 
            if 	parseInt(message[gameId].GS) is 1
              if jQuery("#right_hud_"+gameId).parent().attr('id') is 'rightHUD-theirturn'
                divElement = jQuery("#right_hud_"+gameId).detach()
                divElement.appendTo('#rightHUD-yourturn')
            else if parseInt(message[gameId].GS) is 2
              if jQuery("#right_hud_" + gameId).parent().attr('id') is 'rightHUD-yourturn'
                divElement = jQuery("#right_hud_" + gameId).detach();
                divElement.appendTo('#rightHUD-theirturn');
            
            # update game round
            if  typeof message[gameId].CR isnt 'undefined'
              jQuery("#right_hud_" + gameId).find('.round_no').text(message[gameId].CR)
            
            #update game end time
            if  typeof message[gameId].ED isnt 'undefined'
              jQuery("#right_hud_" + gameId).find('.game_end_time').html(message[gameId].ED);
            
            for gameSeatID of message[gameId].PLRS                  
              ## update online status
              if typeof message[gameId].PLRS[gameSeatID].PON isnt 'undefined'
                if message[gameId].PLRS[gameSeatID].GSS is 5 or  message[gameId].PLRS[gameSeatID].GSS is 3
                  jQuery("#who_am_i_" + gameSeatID).remove()
                else
                  imgElement =  document.getElementById("who_am_i_" + gameSeatID)
                  imgElement.className = if imgElement.className and message[gameId].PLRS[gameSeatID].PON then "online" else "offline"
    				
              ## accept / Decline
              if typeof message[gameId].PLRS[gameSeatID].GSS isnt 'undefined'
                if message[gameId].PLRS[gameSeatID].GSS isnt 1 and zzGlobals.currentUserDBId is message[gameId].PLRS[gameSeatID].UI
                  jQuery('#accept_decline_'+gameId).remove()
                  urDiv = document.getElementById("right_hud_"+gameId)
                  onMouseOverShowDetails urDiv, message[gameId], gameId
                  
    	if  jQuery('#rightHUD-yourturn').find(".userArea").length is 0
        if  jQuery('#rightHUD-yourturn').find(".newCarouselblanktile").length is 0
            jQuery('#rightHUD-yourturn').append('<div class="newCarouselblanktile"></div>')
      else
            jQuery("#rightHUD-yourturn .newCarouselblanktile").remove()
            
    	if jQuery('#rightHUD-theirturn').find(".userArea").length is 0
        if  jQuery('#rightHUD-theirturn').find(".newCarouselblanktile").length is 0
            jQuery('#rightHUD-theirturn').append('<div class="newCarouselblanktile"></div>')
      else
          jQuery("#rightHUD-theirturn .newCarouselblanktile").remove()

    pushToUserVO = (event, userVO, flag_updateUserIdx) ->
      if (userVO?) and (userVO[zzGlobals.clientCodes.USER_ID]?)
        zzGlobals.userVOs[userVO[zzGlobals.clientCodes.USER_ID]] = userVO
        updateUserIndex()
      refreshUserPanel()  if flag_drawUserPanel?

    removeFromUserVO = (event, clientID) ->
      clientName = undefined
      if (clientID?) and (zzGlobals.userVOsIndex[clientID]?)
        clientName = zzGlobals.userVOsIndex[clientID][zzGlobals.clientCodes.USER_DISPLAY_NAME]
        delete zzGlobals.userVOs[zzGlobals.userVOsIndex[clientID][zzGlobals.clientCodes.USER_ID]]

        jDocument.trigger zzEvents.CLIENT_LEFT, [ clientID, clientName ]
        updateUserIndex()

    changeWinner = ->
      if zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_NAME] is "-1"
        docElems["currentWinnerName"].innerHTML = ""
      else
        if docElems?
          if (docElems["currentWinnerImage"]?) and (zzGlobals.userVOsPartyIndex[zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_ID]]?)
            if zzGlobals.userVOsPartyIndex[zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_ID]][zzGlobals.clientCodes.USER_FACEBOOK_ID] and zzGlobals.userVOsPartyIndex[zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_ID]][zzGlobals.clientCodes.USER_FACEBOOK_ID] isnt "-1"
              docElems["currentWinnerImage"].setAttribute "src", "https://graph.facebook.com/" + zzGlobals.userVOsPartyIndex[zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_ID]][zzGlobals.clientCodes.USER_FACEBOOK_ID] + "/picture"
            else
              docElems["currentWinnerImage"].setAttribute "src", DEFAULT_PLAYER_IMG_URL
          else
            docElems["currentWinnerImage"].setAttribute "src", DEFAULT_PLAYER_IMG_URL  if docElems["currentWinnerImage"]?
            docElems["currentWinnerName"].innerHTML = zzGlobals.roomVars[zzGlobals.roomCodes.WINNER_NAME]  if docElems["currentWinnerName"]?

    panelMoveMagnitudeCodes =
      ONE: "1"
      ONE_VIEW: "One screen at a time"
      START: "Be ginning of the list"
      END: "End of the list"

    UserPanelMov = (->
      UserPanelMov = (jUserPanelCenterNav, USER_PANEL_PLAYER_DIV_WIDTH, USER_PANEL_PLAYER_DIV_WIDTH_PADDING, USER_PANEL_VIEW_LIMIT, currentNoOfUsers) ->
        @jUserPanelCenterNav = jUserPanelCenterNav
        @USER_PANEL_PLAYER_DIV_WIDTH = (if USER_PANEL_PLAYER_DIV_WIDTH? then USER_PANEL_PLAYER_DIV_WIDTH else 145)
        @USER_PANEL_PLAYER_DIV_WIDTH_PADDING = (if USER_PANEL_PLAYER_DIV_WIDTH_PADDING? then USER_PANEL_PLAYER_DIV_WIDTH_PADDING else 0)
        @USER_PANEL_VIEW_LIMIT = (if USER_PANEL_VIEW_LIMIT? then USER_PANEL_VIEW_LIMIT else 1)
        @currentNoOfUsers = (if currentNoOfUsers? then currentNoOfUsers else 0)
        @currentPos = 0
      UserPanelMov::moveUserPanelDivNow = ->
        startPx = undefined
        _this = this
        startPx = @currentPos * (@USER_PANEL_PLAYER_DIV_WIDTH + @USER_PANEL_PLAYER_DIV_WIDTH_PADDING)
        console.log "[currentNoOfUsers:", @currentNoOfUsers, ",currentPos:", @currentPos, ",USER_PANEL_PLAYER_DIV_WIDTH:", @USER_PANEL_PLAYER_DIV_WIDTH, "USER_PANEL_PLAYER_DIV_WIDTH_PADDING:", @USER_PANEL_PLAYER_DIV_WIDTH_PADDING, "startPx:", startPx, " : Element : ", @jUserPanelCenterNav if isDevEnvironment
        if @jUserPanelCenterNav?
          console.log "step 1 for move" if isDevEnvironment
          if @jUserPanelCenterNav.animate?
            console.log "step 2 for move" if isDevEnvironment
            @jUserPanelCenterNav.animate
              left: "-" + startPx + "px"
            , "fast", ->
              {}
          else if (@jUserPanelCenterNav.style?) and (@jUserPanelCenterNav.style.offsetLeft?)
            console.log "setting left prop" if isDevEnvironment
            @jUserPanelCenterNav.style.offsetLeft = startPx + "px"

#      UserPanelMov::updateCount = (cnt) ->
#        @currentNoOfUsers = cnt

      UserPanelMov::move = (panelMoveDirection, panelMoveMagnitude) ->
        console.log "moveUserPanelFn() - " + panelMoveDirection + "," + panelMoveMagnitude if isDevEnvironment
        magnitude = 0
        flag_NO_EXTREME_END = false
        panelMoveDirection = (if panelMoveDirection isnt "LEFT" then "RIGHT" else "LEFT")
        switch panelMoveMagnitude
          when panelMoveMagnitudeCodes.ONE
            magnitude = 1
            flag_NO_EXTREME_END = true
          when panelMoveMagnitudeCodes.ONE_VIEW
            magnitude = @USER_PANEL_VIEW_LIMIT
            flag_NO_EXTREME_END = true
        if flag_NO_EXTREME_END is true
          if panelMoveDirection is "LEFT"
            if @currentNoOfUsers <= @USER_PANEL_VIEW_LIMIT or (@currentPos - magnitude) < 1
              panelMoveMagnitude = panelMoveMagnitudeCodes.START
            else if (@currentPos - magnitude) >= (@currentNoOfUsers - @USER_PANEL_VIEW_LIMIT)
              panelMoveMagnitude = panelMoveMagnitudeCodes.END
            else
              @currentPos = @currentPos - magnitude
          else
            if (@currentNoOfUsers <= @USER_PANEL_VIEW_LIMIT) or ((@currentPos + magnitude) < 1)
              panelMoveMagnitude = panelMoveMagnitudeCodes.START
            else if (@currentPos + magnitude) >= (@currentNoOfUsers - @USER_PANEL_VIEW_LIMIT)
              panelMoveMagnitude = panelMoveMagnitudeCodes.END
            else
              @currentPos = @currentPos + magnitude
        switch panelMoveMagnitude
          when panelMoveMagnitudeCodes.END
            @currentPos = @currentNoOfUsers - @USER_PANEL_VIEW_LIMIT
          when panelMoveMagnitudeCodes.START
            @currentPos = 0
        @moveUserPanelDivNow()

      UserPanelMov
    )()
    updateTotalSeats = (evt, val) ->
      MAX_PLAYERS_IN_A_GAME = parseInt(val)
      console.log "Max Seats in game :  ", val if isDevEnvironment
      drawUserPanel()

    updateZalerioScores = (evt, val) ->
      _this = this
      userScoreObj = jQuery.parseJSON(val)
      userScoreArr = []
      for userSeatId of userScoreObj
        userScoreArr.push userSeatId
      userScoreArr.sort (x, y) ->
        userScoreObj[y] - userScoreObj[x]

      refreshUserPanel()

    showFinalScore = () ->
      try
        if zzGlobals.roomVars.FR is "1" # if Game finished
          jQuery("#gameBetPanel").hide()
          jQuery(".resignPopup").hide()
          ii = 0
          rankHtmlArray = []
          rankHtml = ""
          disableRematch = false
          
          usersObject = zzGlobals.dataObjVars.AP

          for index of usersObject.PLSC
          	seatId = usersObject.PLSC[index]
          	if parseInt(usersObject.PLRS[seatId].PRE) is 1 or parseInt(usersObject.PLRS[seatId].GSS) isnt 2
          		if parseInt(zzGlobals.currentUserDBId) is parseInt(usersObject.PLRS[seatId].UI)
          			disableRematch = true
          		continue
          	if ii is 0
          		rankHtmlArray.push "<div  id='score" + ++ii + "'><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture' /><div class='name'>" + usersObject.PLRS[seatId].PDN + "</div><div class='score'>" + usersObject.PLRS[seatId].PSC + "</div></div>"
          	else
          		rankHtml += "<div class='score_rep' id='score" + ++ii + "'><div class='rank'>" + ii + "nd</div><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture' /><div class='name'>" + usersObject.PLRS[seatId].PDN + "</div><div class='score'>" + usersObject.PLRS[seatId].PSC + "</div></div>"
          
          if ii < 2 or disableRematch or resignStatus is 1
          	jQuery("#rematch").hide();
          	jQuery(".dismiss").css({marginTop: '1px', marginLeft: '50px'})
          else
          	jQuery("#rematch").show();
          	jQuery(".dismiss").css({marginTop: '', marginLeft: ''})
            
          rankHtmlArray.push rankHtml
          jQuery("#topScore_div").html rankHtmlArray[0]
          jQuery("#bottomScore_div").html rankHtmlArray[1]
          jQuery(".score_show_popup").css "display", "block"
    
    showRoundScorePopup = () ->
      return  if zzGlobals.roomVars.CR.split("_")[1] is "0" or zzGlobals.roomVars.FR is "1"
      if parseInt(zzGlobals.roomVars.CR.split("_")[1]) isnt _currentRoundStatus
        _currentRoundStatus = parseInt(zzGlobals.roomVars.CR.split("_")[1])
      else
        return
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
        			
      usersObject = usersObject[i]
      
      scorePopup = document.createElement("div")
      scorePopup.className = "roundresult"
      scorePopupContent = document.createElement("div")
      scorePopupContent.className = "content_r bounceIn animated"
      scorePopuptopthree = document.createElement("div")
      scorePopuptopthree.className = "top_three_r"
      i = 0
      rankHtml = ""
      userCount = 0
      for index of usersObject.PLSC
          seatId = usersObject.PLSC[index]
          continue if parseInt(usersObject.PLRS[seatId].GSS) isnt 2
          continue if parseInt(usersObject.PLRS[seatId].PRE) is 1
          userCount++
          if userCount <= 3 
            rankHtml += "<div class='score'><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB  + "/picture' /><div class='points plus'>" + usersObject.PLRS[seatId].PSC + "</div></div>"
          if zzGlobals.currentUserDBId is usersObject.PLRS[seatId].UI
            selfHtml = document.createElement("div")
            selfHtml.className = "score self"
            selfHtmlImg = document.createElement("img")
            selfHtmlImg.src = "https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture"
            selfHtmlPoints = document.createElement("div")
            selfHtmlPoints.className = "points plus"
            selfHtmlPoints.innerHTML = usersObject.PLRS[seatId].PSC
            selfHtml.appendChild selfHtmlImg
            selfHtml.appendChild selfHtmlPoints
      jQuery(".roundresult").remove()
      jQuery(scorePopuptopthree).append rankHtml
      roundPopupHeading = document.createElement("h2")
      roundPopupHeading.id = "round_no_r"
      jQuery(roundPopupHeading).text "Round " + parseInt(zzGlobals.roomVars.CR.split("_")[1])
      scorePopupContent.appendChild roundPopupHeading
      if selfHtml 
	      scorePopupContent.appendChild selfHtml
	      scorePopupContent.appendChild scorePopuptopthree
	      scorePopup.appendChild scorePopupContent
	      if parseInt(zzGlobals.roomVars.CR.split("_")[1]) > 0
	        $("#active-screen").append scorePopup
	        setTimeout (->
	          $(".roundresult").remove()
	        ), 5000
	        $(".roundresult").click ->
	          $(this).remove()
	
	#
	# current game invite status popup trigger when ALL_PLAYER_INFO (AP) update
	# 
    showInviteStatus = (currentGameUsersData) ->
    	popupDivBase = document.createElement("div")
    	popupDivBase.className = "status_show_popup zalerio_popup"
    	popupDivBase.id = "invitestatus"
    	popupDivBase.style.display = "block"
		
    	popupDiv = document.createElement("div")
    	popupDiv.id = "score_friendpopup"
    	popupDiv.className = "outer_base"
    	
    	popupDivClose = document.createElement("a")
    	popupDivClose.id = "close"
    	popupDivClose.className = "positionAbsolute"
    	popupDivClose.onclick = () -> jQuery('.status_show_popup').remove(); return false;
    	
    	popupStatusBase = document.createElement("div")
    	popupStatusBase.className = "invite_status_base"
    	popupStatusBase.style.paddingTop = "32px"
    	
    	popuptopUserInfo = document.createElement("div")
    	popuptopUserInfo.className = "topUserInfo"
    	
    	popupStatusWrapper = document.createElement("div")
    	popupStatusWrapper.className = "statusWrapper"
    	
    	popupacceptBlock = document.createElement("div")
    	popupacceptBlock.className = "acceptBlock"
    	
    	popupinviteBlock = document.createElement("div")
    	popupinviteBlock.className = "inviteBlock"
    	
    	popupdeclineBlock = document.createElement("div")
    	popupdeclineBlock.className = "declineBlock"
    	
    	# popup buttons
    	popupbuttonWrapper = document.createElement("div")
    	popupbuttonWrapper.className = "buttonWrapper"
    	
    	# popup left button Wrapper
    	popupleftButtonWrapper = document.createElement("div")
    	popupleftButtonWrapper.className = "leftButtonWrapper"
    	
    	# popup left button
    	popupleftButton = document.createElement("div")
    	popupleftButton.className = "leftButton"
    	popupleftButton.innerHTML = "Close Invitations"
    	popupleftButton.onclick = -> closeInvits() #close invite trigger
    	
    	# popup text below left button 
    	popuptextLeftButton = document.createElement("div")
    	popuptextLeftButton.className = "textLeftButton"
    	popuptextLeftButton.innerHTML = "Game will kick-off with all friends that have accepted your game invitation so far"	
    	
    	# popup right button Wrapper
    	popuprightButtonWrapper = document.createElement("div")
    	popuprightButtonWrapper.className = "rightButtonWrapper"
    	
    	# popup right button
    	popuprightButton = document.createElement("div")
    	popuprightButton.className = "rightButton"
    	popuprightButton.innerHTML = "Send Reminder"
    	
    	# popup text below right button 
    	popuptextRightButton = document.createElement("div")
    	popuptextRightButton.className = "textRightButton"
    	popuptextRightButton.innerHTML = "Friends who did not respond yet will get a gentle reminder to join your game..."

    	#collect data form current game users object    	    	
    	for x of currentGameUsersData
    		usersInfoObject = currentGameUsersData[x]
    		totalUser =  usersInfoObject.TP
    		toShow = usersInfoObject.GCB
    		gameObjPLRS = jQuery.parseJSON(usersInfoObject.PLRS)
    		break; # As per logic AP content only 1 game data
    	invitedImgs = []
    	acceptedImgs = []
    	declinedImgs = []
    	rejected = []
    	for y of gameObjPLRS
    			userInfo = jQuery.parseJSON(gameObjPLRS[y])
    			if parseInt(userInfo.GSS) is 1
	    			img = document.createElement("img")
	    			img.src = "https://graph.facebook.com/" + userInfo.PFB + "/picture"
	    			invitedImgs.push img
	    		else if parseInt(userInfo.GSS) is 2
	    			img = document.createElement("img")
	    			img.src = "https://graph.facebook.com/" + userInfo.PFB + "/picture"
	    			acceptedImgs.push img
	    		else if parseInt(userInfo.GSS) is 3
	    			img = document.createElement("img")
	    			img.src = "https://graph.facebook.com/" + userInfo.PFB + "/picture"
	    			declinedImgs.push img
	    		else
	    			rejected.push userInfo.GSS 	
    	popupimgWrapper = document.createElement("div")
    	popupimgWrapper.className = "imgWrapper"
    	
    	unless parseInt(toShow) is parseInt(zzGlobals.currentUserDBId)
    		return
    	
    	unless acceptedImgs.length + declinedImgs.length > totalUser / 2 and invitedImgs.length != 0
    		return
    	
    	if zzGlobals.inviteStatus isnt null and typeof zzGlobals.inviteStatus isnt "undefined"
    		unless rejected.length + acceptedImgs.length + declinedImgs.length > zzGlobals.inviteStatus 
    			return
    	
    	zzGlobals.inviteStatus = rejected.length + acceptedImgs.length + declinedImgs.length
    	
    	# Accepted user html    	
    	if acceptedImgs.length isnt 0
    		# if Accepted User find
    		imgWrapper = document.createElement("div")
    		imgWrapper.className = "imgWrapper"
    		spanTag = document.createElement("span")
    		spanTag.innerHTML = "Accepted"
    		for z of acceptedImgs
    			imgWrapper.appendChild acceptedImgs[z]
    		popupacceptBlock.appendChild spanTag  		
    		popupacceptBlock.appendChild imgWrapper
    	
    	# Declined user html
    	if declinedImgs.length isnt 0
    		# if Declined user find
    		imgWrapper = document.createElement("div")
    		imgWrapper.className = "imgWrapper"
    		spanTag = document.createElement("span")
    		spanTag.innerHTML = "Declined"    		
    		for z of declinedImgs
    			imgWrapper.appendChild declinedImgs
    		popupdeclineBlock.appendChild spanTag
    		popupdeclineBlock.appendChild imgWrapper
    		
    	# Not responeded yet users html
    	if invitedImgs.length isnt 0
    		# if Not responeded user find
    		imgWrapper = document.createElement("div")
    		imgWrapper.className = "imgWrapper"
    		spanTag = document.createElement("span")
    		spanTag.innerHTML = "Not responded yet ..."
	    	for z of invitedImgs
	    		console.log("invitedImgs",invitedImgs)
	    		imgWrapper.appendChild invitedImgs[z]
	    	popupinviteBlock.appendChild spanTag
	    	popupinviteBlock.appendChild imgWrapper
    	
    	#append node to contanier    	    	
    	popupStatusWrapper.appendChild popupacceptBlock
    	popupStatusWrapper.appendChild popupdeclineBlock
    	popupStatusWrapper.appendChild popupinviteBlock
    	
    	popupStatusBase.appendChild popuptopUserInfo
    	popupStatusBase.appendChild popupStatusWrapper
    	
    	popupleftButtonWrapper.appendChild popupleftButton
    	popupleftButtonWrapper.appendChild popuptextLeftButton
    	
    	popuprightButtonWrapper.appendChild popuprightButton
    	popuprightButtonWrapper.appendChild popuptextRightButton
    	
    	popupbuttonWrapper.appendChild popupleftButtonWrapper
    	popupbuttonWrapper.appendChild popuprightButtonWrapper
    	
    	popupStatusBase.appendChild popupbuttonWrapper
    	
    	#append to popupDiv
    	popupDiv.appendChild popupDivClose
    	popupDiv.appendChild popupStatusBase
    	
    	#append popupDiv to Divbase
    	popupDivBase.appendChild popupDiv
    	
    	#remove last add popup if exist
    	jQuery('.status_show_popup').remove();
    		
		#append to body
    	jQuery("body").append(popupDivBase)
    
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
    	showInviteStatus(usersObject) # Function call to show current game invite player status
	
    jQuery ->
      _this = this
      docElems["currentWinnerName"] = document.getElementById("jCurrentWinnerName")
      docElems["currentWinnerImage"] = document.getElementById("jCurrentWinnerImage")
      jDocument.bind zzEvents.RECEIVE_USERVO, pushToUserVO
      jDocument.bind zzEvents.REMOVE_USERVO, removeFromUserVO
      jDocument.bind zzEvents.WINNER_CHANGE, changeWinner
      jDocument.bind "room:" + zzGlobals.roomCodes.ZALERIO_SCORES, updateZalerioScores
      jDocument.bind "room:" + zzGlobals.roomCodes.TOTAL_SEATS, updateTotalSeats
      jDocument.bind "room:" + zzGlobals.roomCodes.PLAYER_BETS_PLACE, renderUserVOToUserPanel
		
      jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, setPlayersInfo
      jDocument.bind "dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, showFinalScore
      jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, updateLeftHud
      jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, drawUserPanel
      jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, showRoundScorePopup

      jDocument.bind "client:" + zzGlobals.clientCodes.UINFO, usersRecordCall
      jDocument.bind "client:" + zzGlobals.clientCodes.ALL_PAST_GAME, usersRecordCall
      jDocument.bind "client:" + zzGlobals.msgCodes.RIGHT_HUD, usersRecordCall
      
      jDocument.bind "client:" + zzGlobals.msgCodes.RIGHT_HUD, updateRightHud
      

      jDocument.bind "client:" + zzGlobals.clientCodes.USERINFO, updatePlayerPlate


    ZalerioUserComponent
  )()
  
  crouselObjSize = (obj)->
    size = 0
    for key of obj
    	userObject = jQuery.parseJSON obj[key]
    	continue if userObject.GSS is 3 or userObject.GSS is 5
    	size++  if obj.hasOwnProperty(key)
    size
    
  objSize = (obj) ->
    size = 0
    for key of obj
      size++  if obj.hasOwnProperty(key)
    size

  sendOriginalFigsRequest = ->
    jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|OF" ]

  placeBetsToServer = (betStr) ->
    jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|PB", "BI|" + betStr ]
  
  sendResignToServer = ->   # fire when user click resign
    jQuery(".draggableBets").attr("draggable","false")
    jQuery(".resignPopup").hide()
    jQuery("#gameBetPanel").hide()
    jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|RG" ]
  
  # trigger on close invite request
  closeInvits = ->
    jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|CI"]
    
  resignme = document.getElementById("resignme")
  addEventHandler resignme, "click", sendResignToServer, false  # resign event handler

  # fire when user rematch and create game
  rematchCall = (e,rematchPlayerFBID)->
  	e.preventDefault() if e.preventDefault
  	otherbuttonSound.Play if playSound
  	if typeof rematchPlayerFBID is "undefined"
  		usersObject = jQuery.parseJSON(zzGlobals.roomVars.AP)
  		console.log "Score Board left (zzGlobals.roomVars.AP", userObject if isDevEnvironment
  		for gameId of usersObject
  			usersInfoObject = jQuery.parseJSON(usersObject[gameId])
  			break
  		i=0
  		usersInfoObject = jQuery.parseJSON(usersInfoObject.PLRS)
  		_results = []
  		for x of usersInfoObject
  			userObject = jQuery.parseJSON(usersInfoObject[x])
  			#continue if parseInt(userObject.PRE) == 1  			
  			_results.push(userObject.PFB)
  	else
  		_results = []
  		_results.push(rematchPlayerFBID)
  	InviteFriends(_results,'Rematch',gameId)
  	jQuery('.zalerio_popup').css('display','none')
  	return

  addEventHandler document.getElementById('rematch'), 'click' ,rematchCall, false # attach event with rematch botton
  
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
  	usersRecord usersInfo
  	return
    
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

  if typeof String::startsWith isnt "function"
    String::startsWith = (str) ->
      @indexOf(str) is 0
  jQuery ->
    SpriteAnimation = (->
      SpriteAnimation = (picHeight, picWidth, el, noOfFrames, horizontalMajor, animSpeed) ->
        @picHeight = picHeight
        @picWidth = picWidth
        @el = el
        @noOfFrames = noOfFrames
        @horizontalMajor = horizontalMajor
        @animSpeed = animSpeed
      SpriteAnimation.cFrame = 0
      SpriteAnimation.elBgIndex = []
      iFrame = 0
      _ref = SpriteAnimation.noOfFrames

      while (if 0 <= _ref then iFrame <= _ref else iFrame >= _ref)
        if SpriteAnimation.horizontalMajor
          SpriteAnimation.elBgIndex[iFrame] = "-" + (iFrame * SpriteAnimation.picWidth) + "px 0px"
        else
          SpriteAnimation.elBgIndex[iFrame] = "0px " + "-" + (iFrame * SpriteAnimation.picWidth) + "px"
        (if 0 <= _ref then iFrame++ else iFrame--)
      SpriteAnimation::nextFrame = ->
        if ++@cFrame % @noOfFrames is 0
          @cFrame = 0
          @elBgIndex[@cFrame]

      SpriteAnimation::animate = ->
        that = undefined
        if (@el?) and (@el.style?) and (@el.style.backgroundPosition?)
          @el.style.backgroundPosition = @nextFrame()
          that = this
          @animateFlag = window.setTimeout(->
            that.animate()
          , @animSpeed)

      SpriteAnimation::startAnimation = ->
        @animate()

      SpriteAnimation::stopAnimation = ->
        window.clearTimeout @animateFlag

      SpriteAnimation
    )()
    gameStartAnimation = new SpriteAnimation(200, 125, document.getElementById("loadWaitAnimation"), 12, true, 70)
    gameStartAnimation.startAnimation()

  #oloConnection = new OloUnionConnection
  oloUserComponent()
  # show resign Popup
  resignPopUP = ->
      jQuery("#bottomHUDbuttons-more").click(->
        totalPlayer=0
        usersObjectAP = jQuery.parseJSON(zzGlobals.roomVars.AP)
        
        for i of usersObjectAP
        	usersObjectAP[i] = jQuery.parseJSON(usersObjectAP[i])
        	usersObjectAP[i].PLRS = jQuery.parseJSON(usersObjectAP[i].PLRS)
        	for seatId of usersObjectAP[i].PLRS
        		usersObjectAP[i].PLRS[seatId] = jQuery.parseJSON(usersObjectAP[i].PLRS[seatId])
        		if usersObjectAP[i].PLRS[seatId].GSS is 2 or usersObjectAP[i].PLRS[seatId].GSS is 1        			
        			++totalPlayer
        	break
        currentUserObj = jQuery.parseJSON(zzGlobals.clientVars.UINFO)
        if totalPlayer > 1 and parseInt(zzGlobals.roomVars.FR) isnt 1 and parseInt(currentUserObj.PRE) isnt 1  # show if total number of user > 1 and Game Not finished
          jQuery(".resignPopup").show()
      );
      
  if document.domain is "localhost" or document.domain is "zl.mobicules.com"
  	# if development environment version
    addEventHandler document.getElementById("bottomHUDbuttons-more"), "click", sendOriginalFigsRequest, false
    window.sendOriginalFigsRequest = sendOriginalFigsRequest
    resignPopUP()	# call resign popup
    
  else
    resignPopUP()   # call resign popup
  return

  ## new alert popup
messagePopup = (message,callback,callback_param)->
  	jQuery('.msg-outer-div').remove()
  	try
  		popupapperence.play() if playSound
  	msgDiv = document.createElement("div")
  	msgDiv.className = 'msg-outer-div bounceIn animated'
  	
  	msgInnerDiv = document.createElement("div")
  	msgInnerDiv.className = 'msg-inner-div'
  	
  	msgTextDiv = document.createElement("div")
  	msgTextDiv.className = 'msgbox-msg'
  	
  	msgTextP = document.createElement("p")
  	msgTextP.innerHTML = message
  	
  	msgOkDiv = document.createElement("div")
  	msgOkDiv.className = 'msgbox-ok'
  	msgOkDiv.innerHTML = 'ok'
  	  	
  	if typeof callback is 'undefined'
	  	msgOkDiv.onclick = ->
	  		try
	  			closebutton.play() if playSound
	  		jQuery('.msg-outer-div').remove()
  	else
  		msgCancelDiv = document.createElement("div")
	  	msgCancelDiv.className = 'msgbox-cancel'
	  	msgCancelDiv.innerHTML = 'cancel'
  		msgOkDiv.className = 'msgbox-ok msgbox-cancel'
  		
  		msgCancelDiv.onclick = ->
  			try
	  			closebutton.play() if playSound
	  		#jQuery('.msg-outer-div').addClass("hinge")
	  		jQuery('.msg-outer-div').remove()
	  		
	  		
  		msgOkDiv.onclick = ->
	  		callback(callback_param)
	  		try
	  			closebutton.play() if playSound
	  		jQuery('.msg-outer-div').remove()
  		  	
  	msgTextDiv.appendChild msgTextP  	
  	msgInnerDiv.appendChild msgTextDiv
  	msgInnerDiv.appendChild msgOkDiv  	
  	msgInnerDiv.appendChild msgCancelDiv if msgCancelDiv
  	msgDiv.appendChild msgInnerDiv
  	jQuery('#active-screen').append(jQuery(msgDiv))