define ["../helper/utils"], (utils) ->
	resignPopUP = ->
		jQuery("#bottomHUDbuttons-more").click(->
			totalPlayer=0
			
			plrs = zzGlobals.dataObjVars.AP.PLRS
			
			for seatId of plrs
				++totalPlayer	if plrs[seatId].GSS is 2 or plrs[seatId].GSS is 1 unless plrs[seatId].PRE isnt 0 

			currentUserObj = jQuery.parseJSON(zzGlobals.clientVars.UINFO)
			if totalPlayer > 1 and parseInt(zzGlobals.roomVars.FR) isnt 1 and parseInt(currentUserObj.PRE) isnt 1  # show if total number of user > 1 and Game Not finished
				jQuery(".resignPopup").show()
				jQuery(".resignPopup").before """<div class="overlay"></div>"""
		)
	      
	resignPopUP()   # call resign popup
	
	sendResignToServer = ->   # fire when user click resign
	    jQuery(".draggableBets").attr("draggable","false")
	    jQuery(".resignPopup").hide()
	    jQuery(".overlay").remove()
	    jQuery("#gameBetPanel").hide()
	    jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [ UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|RG" ]
    
  resignme = document.getElementById("resignme")
  utils.addEventHandler resignme, "click", sendResignToServer, false  # resign event handler