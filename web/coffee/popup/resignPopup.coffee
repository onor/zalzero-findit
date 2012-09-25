define ["../helper/utils"], (utils) ->
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
		)
	      
	resignPopUP()   # call resign popup
	
	sendResignToServer = ->   # fire when user click resign
	    jQuery(".draggableBets").attr("draggable","false")
	    jQuery(".resignPopup").hide()
	    jQuery("#gameBetPanel").hide()
	    jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|RG" ]
    
  resignme = document.getElementById("resignme")
  utils.addEventHandler resignme, "click", sendResignToServer, false  # resign event handler