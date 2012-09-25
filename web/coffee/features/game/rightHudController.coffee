define ["../../config/config"], (config) ->
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

    onMouseOverShowDetails = (urDiv,gameDetails,gameId)->
    	urDiv.onclick = ->
    		try
    			selectbuttonSound.play() if playSound
    			console.log("gameDetails",gameDetails)
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
    		gdUserImgbelt.src = baseUrl + "/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/" + config.userLevelImgBig[parseInt(gameDetails.PLRS[gameSeatID].PL) - 1]
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
    	jQuery('#active-screen').append(jQuery(gdDiv))
   
	jDocument.bind "client:" + zzGlobals.msgCodes.RIGHT_HUD, updateRightHud

	onClickAddRedrict = (userRecodeDivValue, gameID, remove) ->
    	game = gameID
    	jQuery(jQuery(userRecodeDivValue)).click(->
    			if typeof remove isnt 'undefined'
    				jQuery(remove).remove()
    			#gameChangeListener(game)
    			jDocument.trigger "gameChangeListener" , game
    		)
    	return
	
	true