define ["./config","../helper/utils"], (config,utils) ->
	DEFAULT_PLAYER_IMG_URL = "/olotheme/images/fbDefaultUser.gif"
	zzEvents =
	    JOINED_ROOM: "joinedRoomListener"
	    RESET_GAME_VARIABLES: "resetGameVariables"
	    CLIENT_ADDED_TO_ROOM: "clientAddedListener"
	    CLIENT_REMOVED_FROM_ROOM: "clientRemovedListener"
	    ROOM_SNAPSHOT: "roomSnapshotListener"
	    CLIENT_SNAPSHOT: "clientSnapshotListener"
	    ROOM_ATTR_UPDATE: "roomAttrUpdateListener"
	    CLIENT_ATTR_UPDATE: "clientAttrUpdateListener"
	    ROOM_OCCUPANTCOUNT_UPDATE: "roomOccupantCountUpdateListener"
	    LOGGED_IN: "onLoginResult"
	    RECEIVE_MESSAGE: "messageListener"
	    SEND_UPC_MESSAGE: "send_upc_message"
	    RECEIVE_USERVO: "received_userVO"
	    REMOVE_USERVO: "remove_userVO"
	    UPDATE_TTL: "update_ttl"
	    CONNECTION_CLOSE: "connection_close"
	    SEND_CHAT_MESSAGE: "send_chat_message"
	    UPDATE_LOCAL_TTL: "update_local_ttl"
	    BID_CHANGED: "bid_changed"
	    SERVER_MESSAGE: "SERVER_MESSAGE"
	    WINNER_CHANGE: "winner_change"
	    CLIENT_JOINED: "client_joined"
	    CLIENT_LEFT: "client_left"
	    OLOTUTS_MESSAGE: "olotuts_message"
	    CLIENT_BETS_PLACED: "zalerioUserBetPlaced"
	    
	zzGlobals = ->
	    inviteStatus = null
	    ttl = null
	    currentUserDBId = null    
	    offlinePlayers = null
	    clientCodes =
	      USER_ID: "UI"
	      USER_DISPLAY_NAME: "UD"
	      USER_NAME: "UN"
	      USER_UNION_ID: "UU"
	      USER_FACEBOOK_ID: "UF"
	      USER_PARTY_ID: "UP"
	      USER_SEAT_ID: "UR"
	      Z_USER_SCORE: "ZS"
	      MY_TURN: "MT"
	      THEIR_TURN: "TT"
	      ALL_PAST_GAME: "APG"
	      USERINFO: "UINFO"
	      
		# new implementation
	    msgCodes =
	      RIGHT_HUD:"RH"
	    dataObjCodes =
	      ALL_PLAYER_INFO:"AP"
	
	    roomCodes =
	      ROOM_ID: "GI"
	      USER_TOTAL: "UT"
	      USER_COUNT: "UC"
	      WINNER_NAME: "WN"
	      WINNER_ID: "WI"
	      GAME_ENDED: "GE"
	      TIME_LEFT: "TL"
	      GAME_DURATION: "GD"
	      START_TIME: "ST"
	      END_TIME: "ET"
	      ROOM_NAME: "RN"
	      TOTAL_SEATS: "TS"
	      ROOM_PRIZE_NAME: "RP"
	      ROOM_MRP: "RM"
	      ROOM_IMAGEROOM_MRP: "RI"
	      TOTALROUNDS: "TR"
	      CURRENTROUND: "CR"
	      ROUND_ENDTIME: "RE"
	      ROUND_ENDTIMEINMS: "EM"
	      ROUND_NOOFBETS: "NB"
	      BOARDVARS: "BV"
	      BOARD_XY: "XY"
	      TOTALFIGS: "TF"
	      ROOM_ALLROUNDS: "AR"
	      FIGURE_DETAILS: "FD"
	      ZALERIO_SCORES: "ZS"
	      GAME_FINISH: "FR"
	      PLAYER_BETS_PLACE: "PP"
	      ALL_PLAYER_INFO: "AP"
	
	    userVOsIndex = {}
	    userVOsSeatIndex = {}
	    userVOsPartyIndex = {}
	    generateAttrVars = (attrCodes) ->
	      attrV = {}
	      for i of attrCodes
	        attrV[attrCodes[i]] = i
	      attrV
	      
	    msgVars = generateAttrVars(msgCodes)
	    dataObjVars = generateAttrVars(dataObjCodes)
	    clientVars = generateAttrVars(clientCodes)
	    roomVars = generateAttrVars(roomCodes)
	    roomVarsFlag = generateAttrVars(roomCodes)
	    userVOs = {}
	    userFBVOs = {}
	    gameDuration = ""
	    setClockInterval = ""
	    UserVO = (userUnionId, userSnapshotObj) ->
	      if config.isDevEnvironment
	      	utils.log "Added to room " + userSnapshotObj
	      	utils.log "Current User local ID : " + zzGlobals.currentUserDBId;
	      for i of clientCodes
	        this[clientCodes[i]] = "-1"
	      userVArr = userSnapshotObj.split("|")
	      userVArrLen = userVArr.length
	      i = 0
	      while i < userVArrLen
	        this[userVArr[i]] = userVArr[i + 1]  if clientVars[userVArr[i]]
	        i += 3
	      this[clientCodes.USER_UNION_ID] = userUnionId  if this[clientCodes.USER_ID]
	      if this[clientCodes.USER_ID] is zzGlobals.currentUserDBId
	        i = 0
	        while i < userVArrLen
	          zzGlobals.clientVars[userVArr[i]] = userVArr[i + 1]  if clientVars[userVArr[i]]
	          i += 3
	      this
	
	    getUserName = (clientID) ->
	      userName = clientID
	      userName = zzGlobals.userVOsIndex[clientID][clientCodes.USER_DISPLAY_NAME]  if clientID and zzGlobals.userVOsIndex[clientID]
	      userName
	
	    updateGameDuration = (event, data) ->
	      data = (if data then parseInt(data) else null)
	      gameDuration = data  if data and not isNaN(data)
	
	    updateClock = ->
	      ttl--
	      if ttl > 0
	        pageRefresh.clearTimer()  if pageRefresh and pageRefresh.isTimerEnabled and pageRefresh.isTimerEnabled()
	      else
	        if pageRefresh and ttl <= 0
	          if pageRefresh.isTimerEnabled and not (pageRefresh.isTimerEnabled()) and currentScreen isnt "postscreen"
	            utils.log "Pagerefresh.isTimerEnabled false!" if config.isDevEnvironment
	            pageRefresh.enableTimer()
	      jDocument.trigger oloEvents.UPDATE_LOCAL_TTL, @ttl
	
	    updateTTL = (event, data) ->
	      clearInterval setClockInterval  if setClockInterval
	      data = (if data then parseInt(data) else null)
	      ttl = data  if data and typeof data is "number"
	      setClockInterval = setInterval(updateClock, 10000)
	
	    pageRefresh = ->
	      timer = null
	      enableTimerFn = ->
	        _this = this
	        timer = setTimeout(->
	          redirectToPlayNow gameInstId
	        , 5000)
	        utils.log "refresh timer set!" if config.isDevEnvironment
	
	      clearTimerFn = ->
	        timer = null
	        utils.log "refresh timer cleared!" if config.isDevEnvironment
	
	      isTimerEnabled: ->
	        if timer
	          true
	        else
	          false
	
	      enableTimer: ->
	        enableTimerFn()
	
	      clearTimer: ->
	        clearTimerFn()
	
	
	    generateAttrVarsFn: (p) ->
	      generateAttrVars p
	
	    roomCodes: roomCodes
	    clientCodes: clientCodes
	    msgCodes: msgCodes
	    dataObjCodes: dataObjCodes
	    dataObjVars: dataObjVars
	    UserVO: UserVO
	    userVOsIndex: userVOsIndex
	    roomVars: roomVars
	    roomVarsFlag: roomVarsFlag
	    clientVars: clientVars
	    msgVars: msgVars
	    userVOs: userVOs
	    userFBVOs: userFBVOs
	    getUserName: getUserName
	    userVOsPartyIndex: userVOsPartyIndex
	    getTtl: ->
	      ttl
	
	    userVOsSeatIndex: userVOsSeatIndex
	    getGameDuration: ->
	      gameDuration
	
	    offlinePlayers: offlinePlayers
	    currentUserDBId: currentUserDBId
	    
	zzCMDListners =
      ORIG_FIGS: "OF"
      BET_RESPONSE: "PB"
      BET_CHANGES: "CB"
      RESIGN_GAME:"RG"
      CLOSE_INVITE : "CI"
      RIGHT_HUD:"MT"
      DECLINE_STATUS:"DG"
    window.jDocument = jQuery(document)
    window.zalerioCMDListners = zzCMDListners
	window.zzGlobals = new zzGlobals()
	window.zzEvents = zzEvents