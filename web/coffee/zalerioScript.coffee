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

resignStatus = 0
flag_roundBetsDrawn = false
drawRoundsPanel = ->
showRoundScorePopup = ->
_currentRoundStatus = -1
roundVOsIdxRightHUD = {}
flag_roundDrawn = false
acceptInvitationAdd = (div,gameIdToAdd)->
	div.on 'click', (e) ->
		acceptInvitation(gameIdToAdd)
		
     
remindUserAdd = (gameLinkID, remindUsersLeft, x, fbUser,div) ->
  div.onclick = ->
  	remindUser gameLinkID, remindUsersLeft, x, fbUser
  	
unless typeof valDevEnvironment is "undefined" 
	isDevEnvironment = valDevEnvironment
else
	isDevEnvironment = true  #if not Dev disable logs
	
jQuery ->
  
  jDocument = jQuery(document)
  unionClientId = null
  roomID = null
  pad2 = (number) ->
    _ref = undefined
    (if (_ref = number < 10)? then _ref else 0: "") + number
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
    	return
	
    drawUserPanel = ->
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
      jDocument.bind zzEvents.JOINED_ROOM, drawUserPanel
      jDocument.bind zzEvents.RECEIVE_USERVO, pushToUserVO
      jDocument.bind zzEvents.REMOVE_USERVO, removeFromUserVO

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
        
# ********** move to gamePlaycontroller.coffee ********
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
  ZalerioUserComponent = (->
    ZalerioUserComponent = ->
        
    onClickRedrict = (userRecodeDivValue, gameID) ->
        game = undefined
        game = gameID
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
        #changeWinner()

    renderUserVOToUserPanel = (idx, userVO, userOffline, userSerial) ->
    	return;
#      userDisplayName = undefined
#      userFacebookImgUrl = undefined
#      userOffline = userOffline or false
#      if userVOsDivIndex[idx] and userVO and userVO[zzGlobals.clientCodes.USER_ID]
#        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_TR].style.visibility = "visible"
#        userDisplayName = "-"
#        userDisplayName = userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME]  if userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME]?
#        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_NAME].innerHTML = userDisplayName
#        userFacebookImgUrl = DEFAULT_PLAYER_IMG_URL
#        userFacebookImgUrl = "https://graph.facebook.com/" + userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID] + "/picture"  if (userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID]?) and userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID].length > 2
#        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SERAIL].innerHTML = userSerial + "."
#        if userVO[zzGlobals.clientCodes.USER_SEAT_ID]
#          userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SCORE].innerHTML = userScoreObj[userVO[zzGlobals.clientCodes.USER_SEAT_ID]]
#        else
#          userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SCORE].innerHTML = 0
#        unless (zzGlobals.roomVars.PP).indexOf(userVO[zzGlobals.clientCodes.USER_SEAT_ID]) is -1
#          $(userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_BETSTATUS]).removeClass "arrowclass"
#        else
#          $(userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_BETSTATUS]).addClass "arrowclass"
#        console.log userScoreObj if isDevEnvironment

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

	
    jQuery ->
      _this = this

      jDocument.bind zzEvents.RECEIVE_USERVO, pushToUserVO
      jDocument.bind zzEvents.REMOVE_USERVO, removeFromUserVO

      jDocument.bind "room:" + zzGlobals.roomCodes.ZALERIO_SCORES, updateZalerioScores

      jDocument.bind "room:" + zzGlobals.roomCodes.PLAYER_BETS_PLACE, renderUserVOToUserPanel

      jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, drawUserPanel



    ZalerioUserComponent
  )()
  
  objSize = (obj) ->
    size = 0
    for key of obj
      size++  if obj.hasOwnProperty(key)
    size
 # jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|RG" ]
  
   

  oloUserComponent()