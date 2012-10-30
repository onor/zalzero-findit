define ["../../helper/confirmBox","../../helper/utils","gamePlayView"], (confirmBox,utils,gamePlayView)->
		# message listener        
	    window.messageListener = (event, messageName, broadcastType, fromClientID, roomID, message) ->
	      switch messageName
	      	
	      	when zalerioCMDListners.DECLINE_STATUS
	      		 if parseInt(message) is 1
	      		 	   confirmBox(popupMSG.declineInvite())
	      		 	   gamePlayView.setGameDisable()
	      		 	   
	      	when zalerioCMDListners.RIGHT_HUD
	        	return
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
	        			
	        	if zzGlobals.msgVars.RH is 'RIGHT_HUD'
	        		zzGlobals.msgVars.RH = usersObject
	        	else
	        		$.extend zzGlobals.msgVars.RH , usersObject
	        		
	        	utils.log 'MT & TT',zzGlobals.msgVars.RH
	        	
	        	jDocument.trigger "client:" + zzGlobals.msgCodes.RIGHT_HUD,usersObject
	        	
	        when zalerioCMDListners.CLOSE_INVITE
	        	if 0 is message
	        		confirmBox('Sorry!!! Unable to Close Invite, <br /> Plese try again..');
	        	else
	        		gamePlayView.removeStatusPopup()
	        		
	        when zalerioCMDListners.RESIGN_GAME
	        	if message is 0
	        		confirmBox('Sorry!!! Unable to Resign, \n Plese try again..');
	        		gamePlayView.showBetsPanel()
	        	else
	        		resignStatus = 1
	        		gamePlayView.setGameDisable()
	        		
	        when zalerioCMDListners.ORIG_FIGS
	        	newCoordObj = jQuery.parseJSON(message)
	        	utils.log newCoordObj
	        	parseToGameBoard zalerioMapType.ORIG_MAP, newCoordObj
	        	
	        when zalerioCMDListners.BET_RESPONSE
	        	parsedObj = jQuery.parseJSON(message)
	        	utils.log "zalerioCMDListners.BET_RESPONSE if failed : ", parsedObj
	        	
	        when zalerioCMDListners.BET_CHANGES
	        	window.betChangeVOs = {}
	        	playerBetsChangeObj = jQuery.parseJSON(message)
	        	utils.log "zalerioCMDListners.BET_CHANGES : ", playerBetsChangeObj
	        	window.currentBets = {}
	        	window.currentBetsIdx = {}
	        	for i of playerBetsChangeObj
	        		if i is "PB"
	        			playerBetTiles = jQuery.parseJSON(playerBetsChangeObj[i])
	        			for tileId of playerBetTiles
	        				betChangeVOs[tileId] = playerBetTiles[tileId]
	        	window.currentRoundBidPlaced = playerBetsChangeObj["BC"]
	        	jDocument.trigger zzEvents.CLIENT_BETS_PLACED, window.currentRoundBidPlaced
	        	
	    jDocument.bind zzEvents.SERVER_MESSAGE, window.messageListener
	    
	    true
#	        	reDrawBetsPanel()
#	        	refreshGameBoard()