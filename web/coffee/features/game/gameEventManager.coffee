# This script does the following
# Connects to Union Server
# Initializes and Implements Listeners for the following events:
#	JOINED_ROOM
#   CLIENT_ADDED_TO_ROOM
#   CLIENT_REMOVED_FROM_ROOM
#   ROOM_SNAPSHOT
#   CLIENT_SNAPSHOT
#   ROOM_ATTR_UPDATE
#   CLIENT_ATTR_UPDATE
#   LOGGED_IN
#   LOGGED_OFF
#   RECEIVE_MESSAGE
# Registers all the Room Attribue and Client Attribute listeners as defined in Globals Config
# Provides method to Send UPC Messages to Server
#
define ["../../config/config","../../helper/confirmBox","../../config/globals","./carouselView","./rightHudController","./leftHudController","./gamePlayController","../../popup/resignPopup","../../popup/showInviteStatus","../../popup/showFinalScore","../../popup/showFrndSelector","../../helper/utils"],(config,confirmBox,globals,carouselView,rightHudController,leftHudController,gamePlayController,resignPopup,showInviteStatus,showFinalScore,showFrndSelector,utils) ->
	zzUnionConnection = (->
		zzUnionConnection = ->
			window.UPC = UPC
			init()
		
		UPC = net.user1.orbiter.UPC
		orbiter = null
		msgManager = null
					
		init = ->
			orbiter = new net.user1.orbiter.Orbiter()
			msgManager = orbiter.getMessageManager()
			addZzListeners()
			utils.log "defining UPC"
			utils.log "ololisteners :" + zzListeners
			utils.log "UPC definition completed!"
			unless orbiter.getSystem().isJavaScriptCompatible()
				oloGame.displayChatMessage "systemChatMessage", "Your browser is not supported."
				return
			if orbiter
				orbiter.addEventListener net.user1.orbiter.OrbiterEvent.READY, readyListener, this
				orbiter.addEventListener net.user1.orbiter.OrbiterEvent.CLOSE, closeListener, this
				
			#connecting to union	      
			jQuery ->
				orbiter.connect config.unionConnection.url, config.unionConnection.port

		roomAttrUpdateListener = (e, roomId, attrKey, attrVal) ->
			if zzGlobals.roomVars[attrKey]
				zzGlobals.roomVars[attrKey] = attrVal
				utils.log "Triggered room:", attrKey, attrVal
				jDocument.trigger "room:" + attrKey, attrVal
      
		#TODO: this needs to be reviewed as it may not be used
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
			i = 0
			while i < len
				if roomAttrsSplit[i] and roomAttrsSplit[i + 1]
					roomAttrKey = roomAttrsSplit[i]
					roomAttrVal = roomAttrsSplit[i + 1]
					if zzGlobals.roomVars[roomAttrKey]
						unless roomAttrKey is zzGlobals.roomCodes.WINNER_ID and roomAttrVal is "-1"
							utils.log console.log "Triggered via ss room:", roomAttrKey, roomAttrVal
							zzGlobals.roomVars[roomAttrKey] = {}
							zzGlobals.roomVars[roomAttrKey] = roomAttrVal
							jDocument.trigger("room:" + roomAttrKey, roomAttrVal)
				i += 2
			true

		clientSnapshotListener = (requestID, clientID, userID, a4, clientAttrsStr) ->
			console.log "ClientSnapshotListener ",clientAttrsStr if config.isDevEnvironment
			if userLoginId is userID
				clientAttrsSplit = clientAttrsStr.split("|")
				len = clientAttrsSplit.length
				clientAttrKey = null
				clientAttrVal = null
				i = 0
				while i < len
					if clientAttrsSplit[i] and clientAttrsSplit[i + 1]
						clientAttrKey = clientAttrsSplit[i]
						clientAttrVal = clientAttrsSplit[i + 1]
						if clientVars[clientAttrKey]
							clientVars[clientAttrKey] = clientAttrVal
							jDocument.trigger("client:" + clientAttrKey, clientAttrVal)
					i += 2
				true

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
			utils.log "user added and triggered : [UserVO:", userVO, ",clientId:" + clientID + ",args:" + arguments[3] + "]" if config.isDevEnvironment
			jDocument.trigger zzEvents.CLIENT_JOINED, userVO
			jDocument.trigger zzEvents.RECEIVE_USERVO, [ userVO, true ]

		clientRemovedListener = (roomID, clientID) ->
			userName = clientID
			jDocument.trigger zzEvents.REMOVE_USERVO, clientID  if clientID and zzGlobals.userVOsIndex[clientID]

		messageListener = (messageName, broadcastType, fromClientID, roomID, message) ->
			jDocument.trigger zzEvents.SERVER_MESSAGE, [ messageName, broadcastType, fromClientID, roomID, message, orbiter.getClientID() ]

		clientAttrUpdateListener = (roomId, clientId, userId, attrKey, attrVal) ->
			if zzGlobals.currentUserDBId is userId
				if zzGlobals.clientVars[attrKey]
					zzGlobals.clientVars[attrKey] = attrVal
					jDocument.trigger("client:" + attrKey, attrVal)

		askClientData = (e) ->
			utils.log "askfordata"
			msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|AGD", "UID|" + zzGlobals.currentUserDBId

		readyListener = (e) ->
			msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|LI", "UI|" + userLoginId, "GI|" + gameInstId
			jDocument.bind zzEvents.SEND_UPC_MESSAGE, sendUpcMessageToServer

		# change game
		window.gameChangeListener = (e,gameInstIdTemp) ->	# fire when user click on carousel to change game
			utils.log("gameInstIdTemp", gameInstIdTemp)
			if(typeof e is 'string')
				gameInstIdTemp = e
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
			jQuery('#accept_decline_' + gameId).html(utils.getMiniLoaderHTML())
			msgManager.sendUPC UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|DG", "GSID|" + gameSeatId

		sendUpcMessageToServer = (event, upcFunctionCode,p2,p3,p4,p5,p6) ->
			utils.log("sending upc message [upcFunctionCode:" + upcFunctionCode + ",p2:" + p2 + ",p3:" + p3 + ",p4:" + p4 + ",p5:" + p5 + ",p6:" + p6 + "]") if config.isDevEnvironment
			argArr = [];
			for i in [0...arguments.length]
				if i is 0
					continue;
				else
					if(arguments[i])
						argArr.push(arguments[i]);
					else
						argArr.push('');

			utils.log(argArr)
			if(upcFunctionCode)
				msgManager.sendUPC.apply(msgManager,argArr);
      
		onLogoutResult = (clientUnionId,userId)->
			if parseInt(unionClientId) is parseInt(clientUnionId)
				window.location = "http://" + document.domain + baseUrl + "/site/closesession"
		
		addZzListeners = ->
			for msgEvt of zzListeners
				if msgManager and msgManager.addMessageListener
					if zzListeners[msgEvt] is "messageListener"
						msgManager.addMessageListener(UPC[msgEvt], zzListeners[msgEvt], this, gameInstId)
					else
						msgManager.addMessageListener(UPC[msgEvt], zzListeners[msgEvt], this)
			true
		
		# Listeners object topl level
		zzListeners =
			JOINED_ROOM: joinedRoomListener
			CLIENT_ADDED_TO_ROOM: clientAddedListener
			CLIENT_REMOVED_FROM_ROOM: clientRemovedListener
			ROOM_SNAPSHOT: roomSnapshotListener
			CLIENT_SNAPSHOT: clientSnapshotListener
			ROOM_ATTR_UPDATE: roomAttrUpdateListener
			CLIENT_ATTR_UPDATE: clientAttrUpdateListener
			LOGGED_IN: onLoginResult
			LOGGED_OFF: onLogoutResult
			RECEIVE_MESSAGE: messageListener
		
		# return this
		zzUnionConnection
	)()