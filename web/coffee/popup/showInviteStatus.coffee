define [] , () ->
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
    	
	jDocument.bind "dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, showInviteStatus
	
	true