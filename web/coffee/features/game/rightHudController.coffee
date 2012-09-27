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
				urDiv.className = "userArea selected" if gameInstId is gameId
   
				$(urDiv).append("<div class=\"round_no\">#{message[gameId].CR}</div>") if typeof message[gameId].CR isnt 'undefined'
    			
				$(urDiv).append("<div class=\"game_end_time\">#{message[gameId].ED}</div>") if typeof message[gameId].ED isnt 'undefined'
				
				for index of message[gameId].PLSC
					gameSeatID = message[gameId].PLSC[index]
					
					if message[gameId].PLRS[gameSeatID].GSS is 5 or  message[gameId].PLRS[gameSeatID].GSS is 3
						continue
    					
    				## user images
					status = if message[gameId].PLRS[gameSeatID].PON is 1 then "online" else "offline"
					$(urDiv).append("""<div class="imageWrapper" id="right_hud_Images#{gameId}"><img src="https://graph.facebook.com/#{message[gameId].PLRS[gameSeatID].PFB}/picture" alt="#{message[gameId].PLRS[gameSeatID].PFN}" class="#{status}" id="who_am_i_#{gameSeatID}" /></div>""") if typeof message[gameId].PLRS[gameSeatID].PFB isnt 'undefined'
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
						
			# update recode
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
	
    	
    	$(gdDiv).append("""<div class="gdScoreCardDiv"></div>""")
    	#<div class="gdAllRoundDiv" id="gdAllRoundDivId"></div>
    	
    	gdAllRoundDiv = document.createElement("div")
    	gdAllRoundDiv.className = 'gdAllRoundDiv'
    	gdAllRoundDiv.id = 'gdAllRoundDivId'
    	$(".gdScoreCardDiv",gdDiv).append gdAllRoundDiv
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
    	
    	$(".gdScoreCardDiv",gdDiv).append """<div class="gdScoreInfoDiv"></div>"""
    	
    	$(".gdScoreInfoDiv",gdDiv).append """<table class="gdScoreCardTbl"></table>"""
    	
    	$(gdDiv).append """<div class="gdUsersImgdiv"></div>"""
    	
    	gdReminddiv = document.createElement("div")
    	gdReminddiv.className = 'gdReminddiv'

    	gdDiv.appendChild gdReminddiv
    	    	
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
    		
    		gdUserImgclassName = if gameDetails.PLRS[gameSeatID].PON is 1 then 'gdUserImg user_online' else 'gdUserImg'
    		
    		$('gdUsersImgdiv',gdDiv).append """<div class="gdUserImgDiv user_offline">
<img src="#{baseUrl}/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/#{config.userLevelImgBig[parseInt(gameDetails.PLRS[gameSeatID].PL) - 1]}" class="#{gdUserImgbelt}" />
<img src="https://graph.facebook.com/#{gameDetails.PLRS[gameSeatID].PFB}/picture" class="#{gdUserImgclassName}" />
</div> """
    		    		
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
    					$(gdScoreCardNameTd).append "<s>#{userObject.PDN}</s>"
    					gdScoreCardBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo" # remove arrow
	          
    		$(gdScoreCardTr).append gdScoreCardNameTd
    		_score = if gameDetails.PLRS[gameSeatID].PSC then gameDetails.PLRS[gameSeatID].PSC else '0'
    		$(gdScoreCardTr).append(""" <td class="userScoreHUD_playerScore"> </td> """)
 
    		$(".gdScoreCardTbl",gdDiv).append gdScoreCardTr
    	try
    		popupapperence.play() if playSound
    	gdReminddiv.onclick = -> 
        	remindUser gameId,remindUsersData
        	return
    	jQuery('#active-screen').append(jQuery(gdDiv))
   
	jDocument.bind "client:" + zzGlobals.msgCodes.RIGHT_HUD, updateRightHud

	onClickAddRedrict = (userRecodeDivValue, gameID, remove) ->
    	game = gameID
    	$(userRecodeDivValue).click(->
    			if typeof remove isnt 'undefined'
    				$(remove).remove()
    			#gameChangeListener(game)
    			jDocument.trigger "gameChangeListener" , game
    		)
    	return
	
	true