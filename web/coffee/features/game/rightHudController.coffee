define ["../../config/config","../../helper/sound"], (config,sound) ->
	updateRightHud = (event, message)->
		for gameId of message
			if document.getElementById("right_hud_"+gameId) is null    			

				urDivClassName = if gameInstId is gameId then "userArea selected" else "userArea"
				
				urDiv = $ """<div class="#{urDivClassName}" id="right_hud_#{gameId}" ></div>"""
   				
				urDiv.append """<div class="round_no">#{message[gameId].CR}</div>"""  if message[gameId].CR
    			
				urDiv.append """<div class="game_end_time">#{message[gameId].ED}</div>"""  if message[gameId].ED
				
				for index of message[gameId].PLSC
					gameSeatID = message[gameId].PLSC[index]
					
					continue if message[gameId].PLRS[gameSeatID].GSS is 5 or  message[gameId].PLRS[gameSeatID].GSS is 3					
    					
    				## user images
					status = if message[gameId].PLRS[gameSeatID].PON is 1 then "online" else "offline"
					urDiv.append("""<div class="imageWrapper" id="right_hud_Images#{gameId}"><img src="https://graph.facebook.com/#{message[gameId].PLRS[gameSeatID].PFB}/picture" alt="#{message[gameId].PLRS[gameSeatID].PFN}" class="#{status}" id="who_am_i_#{gameSeatID}" /></div>""") if message[gameId].PLRS[gameSeatID].PFB
										
					## accept / Decline
					if message[gameId].PLRS[gameSeatID].GSS
						
						if message[gameId].PLRS[gameSeatID].GSS is 1 and zzGlobals.currentUserDBId is message[gameId].PLRS[gameSeatID].UI
							
							urDiv.append """<div class="accept_decline" id="accept_decline_#{gameId}"><div class="right_hud_accept">Accept</div><div class="right_hud_decline">Decline</div></div>"""
						
							acceptInvitationAdd( $("#accept_decline_#{gameId} .right_hud_accept") , gameId )
							
							$("#accept_decline_#{gameId} .right_hud_decline").click( {gsID:gameSeatID, id:gameId}, (e)->
								# trigger decline event
								jDocument.trigger "sendDeclinedToServer" , [e.data.gsID, e.data.id]
							)
							
						else if zzGlobals.currentUserDBId is message[gameId].PLRS[gameSeatID].UI
							
							urDiv.click { gameDetails: message[gameId], id : gameId }, (e) ->						
								sound.playSelectButtonSound()								
								createGameDetailsPopup e.data.gameDetails,e.data.id
								return					

					$('#rightHUD-yourturn').append urDiv if parseInt(message[gameId].GS) is 1

					$('#rightHUD-theirturn').append urDiv if parseInt(message[gameId].GS) is 2	
						
			# update recode
			else
				if parseInt(message[gameId].GS) is 0
					jQuery("#right_hud_"+gameId).remove()
					continue
				else 
					if 	parseInt(message[gameId].GS) is 1
						$("#right_hud_"+gameId).detach().appendTo('#rightHUD-yourturn')	if jQuery("#right_hud_"+gameId).parent().attr('id') is 'rightHUD-theirturn'
							
					else if parseInt(message[gameId].GS) is 2
						$("#right_hud_" + gameId).detach().appendTo('#rightHUD-theirturn') if $("#right_hud_" + gameId).parent().attr('id') is 'rightHUD-yourturn'
	            
					$("#right_hud_" + gameId).find('.round_no').text message[gameId].CR  if message[gameId].CR	# update game round
	            
					$("#right_hud_" + gameId).find('.game_end_time').html message[gameId].ED  if message[gameId].ED	#update game end time
	            
					for gameSeatID of message[gameId].PLRS                  
					## update online status
						if typeof message[gameId].PLRS[gameSeatID].PON isnt "undefined"
							
							if message[gameId].PLRS[gameSeatID].GSS is 5 or  message[gameId].PLRS[gameSeatID].GSS is 3
								$("#who_am_i_" + gameSeatID).remove()
							else
								$("#who_am_i_#{gameSeatID}").removeClass().addClass message[gameId].PLRS[gameSeatID].PON ? "online" : "offline"
	    				
						## accept / Decline
						if typeof message[gameId].PLRS[gameSeatID].GSS isnt 'undefined'
							if message[gameId].PLRS[gameSeatID].GSS isnt 1 and zzGlobals.currentUserDBId is message[gameId].PLRS[gameSeatID].UI
								$('#accept_decline_'+gameId).remove()
								
								$("#right_hud_#{gameId}").click { gameDetails: message[gameId], id : gameId }, (e) ->							
									sound.playSelectButtonSound()								
									createGameDetailsPopup e.data.gameDetails,e.data.id
                  
		if  $('#rightHUD-yourturn').find(".userArea").length is 0
			$('#rightHUD-yourturn').append('<div class="newCarouselblanktile"></div>') if  $('#rightHUD-yourturn').find(".newCarouselblanktile").length is 0
				
		else
			$("#rightHUD-yourturn .newCarouselblanktile").remove()
	
		if $('#rightHUD-theirturn').find(".userArea").length is 0
			$('#rightHUD-theirturn').append '<div class="newCarouselblanktile"></div>'	if  $('#rightHUD-theirturn').find(".newCarouselblanktile").length is 0				
		else
			$("#rightHUD-theirturn .newCarouselblanktile").remove()
		
		true
			
	
	window.createGameDetailsPopup = (gameDetails,gameId)->
    	jQuery('.gdWrapper').remove()
	
    	gdDiv = $ """<div class="gdWrapper animated fadeInRight">
						<div class="gdScoreCardDiv">
							<div class="gdAllRoundDiv" id="gdAllRoundDivId"></div>

							<div class="gdScoreInfoDiv">
								<table class="gdScoreCardTbl"></table>
							</div>
						</div>
						<div class="gdUsersImgdiv"></div>
						<div class="gdReminddiv"></div>
						<div class="gdReturndiv"></div>
					</div>"""

#    	drawRoundsPanel(gdAllRoundDiv)
    	
    	$('.gdReminddiv',gdDiv).click(->
    		remindUser gameId,remindUsersData
    		return
    	)
    	    	
    	$('.gdReturndiv',gdDiv).on 'click', (e) ->
    		sound.playCloseButtonSound()
    		$('.gdWrapper').remove()

    	
    	gdPlaydivClassName = if gameId isnt gameInstId then 'gdResumeDiv' else 'gdPlaydiv'

    	gdDiv.append """<div class="#{gdPlaydivClassName}"></div>"""
    	
    	if gameId isnt gameInstId
    		$(".#{gdPlaydivClassName}",gdDiv).click( { id: gameId, remove : '.gdWrapper' }, (e)->
    			sound.playCloseButtonSound()
    			$(e.data.remove).remove() if e.data.remove
    			jDocument.trigger "gameChangeListener" , e.data.id
    		)
    	else
    		$(".#{gdPlaydivClassName}",gdDiv).on "click", (e) ->
    			sound.playCloseButtonSound()
    			$('.gdWrapper').remove()

    	i = 0
    	remindUsersData = {}
    	for index of gameDetails.PLSC

    		plrs = gameDetails.PLRS[ gameDetails.PLSC[index] ] # player list
    		
    		# create remind user object
    		remindUsersData[plrs.PFB] = { GSS : plrs.GSS, CRS : plrs.CRS }
    		
    		continue if plrs.GSS is 5 or  plrs.GSS is 3
    		    		
    		gdUserImgclassName = plrs.PON is 1 ? 'gdUserImg user_online' : 'gdUserImg'
    		
    		_score = if plrs.PSC then plrs.PSC else '0'
    		
    		trClass = "userScoreHUD_player"
    		
    		betPlacedNoClass = 'userScoreHUD_playerBetPlacedNo arrowclass'
    		if plrs.PDN
    			playerName = plrs.PDN
    			if parseInt(plrs.PRE) != 1    				
    					trClass = "userScoreHUD_player GRAY" if parseInt(plrs.PON) == 0
    			else
    				if parseInt(plrs.UI) is parseInt(zzGlobals.currentUserDBId)
    					
    					playerName = "<s>#{plrs.PDN}</s>"
    					
    					betPlacedNoClass = 'userScoreHUD_playerBetPlacedNo' # remove arrow
    		
    		betPlacedNoClass = 'userScoreHUD_playerBetPlacedNo'  if plrs.CRS is 9
    		
    		$('.gdUsersImgdiv',gdDiv).append """<div class="gdUserImgDiv user_offline"><img src="#{baseUrl}/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/#{config.userLevelImgBig[parseInt(plrs.PL) - 1]}" class="gdUserImgbelt" /><img src="https://graph.facebook.com/#{plrs.PFB}/picture" class="#{gdUserImgclassName}" /></div> """
				
    		$(".gdScoreCardTbl",gdDiv).append """" <tr class="#{trClass}">
											<td  class="#{betPlacedNoClass}"></td> 
											<td  class="userScoreHUD_playerSerial" >#{++i}</td>
											<td  class="userScoreHUD_playerName" >#{playerName}</td>
											<td class="userScoreHUD_playerScore">#{_score}</td>
										</tr>
									"""
    		
    	sound.playPopupApperenceSound()
    	
    	$('#active-screen').append gdDiv
   
	jDocument.bind "client:" + zzGlobals.msgCodes.RIGHT_HUD, updateRightHud
	
	true