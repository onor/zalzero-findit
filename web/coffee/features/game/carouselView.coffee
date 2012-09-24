define ["../../config/globals"], (globals)->
	drawCarousel = (usersObject, appendto, gameLinkID, typeOfCarousel) ->
      currentPlayerList = document.getElementById(appendto)
      usersObjectPastRound = usersObject
      usersObject = jQuery.parseJSON(usersObject.PLRS)
      remindUsersLeft = {}    		
      $("#" + appendto).empty()
      for x of usersObject
        userObject = jQuery.parseJSON(usersObject[x])
        remindUsersLeft[userObject.PFB] = { GSS : userObject.GSS, CRS : userObject.CRS } 
        #skip if resign game
        #return if userObject.PRE is 1 and gameInstId isnt gameLinkID and userObject.UI is oloGlobals.currentUserDBId
        
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
        else if parseInt(userObject.UI) is parseInt(oloGlobals.currentUserDBId)
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
        if userObject.PDN and oloGlobals.clientVars.UI
          if userObject.CRS is 0
            if oloGlobals.clientVars.UI is userObject.UI
              userRecodePlayerRemindDiv.innerHTML = "place your tiles..."
            else
            	unless oloGlobals.clientVars.UI is userObject.UI
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