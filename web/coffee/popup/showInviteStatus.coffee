define [] , () ->
	# trigger on close invite request
	closeInvits = ->
		jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|CI"]
	
	showInviteStatus = () ->
    	unless parseInt(zzGlobals.dataObjVars.AP.GCB) is parseInt(zzGlobals.currentUserDBId)
    		return
    	
    	usersInfoObject = zzGlobals.dataObjVars.AP
    	gameObjPLRS = usersInfoObject.PLRS
    	totalResponse = 0
    	invitedImgs = 0
    	
    	popupDivBase = $ """<div class="status_show_popup zalerio_popup" id="invitestatus" style="display:block" >
								<div id="score_friendpopup" class="outer_base">
									<a class="positionAbsolute" id="close"></a>
									<div class="invite_status_base" style="padding-top:32px">
										<div class="topUserInfo"></div>
										<div class="statusWrapper">
											<div class="acceptBlock"><span>Accepted</span><div class="imgWrapper"></div></div>
											<div class="declineBlock"><span>Declined</span><div class="imgWrapper"></div></div>
											<div class="inviteBlock"><span>Not responded yet ...</span><div class="imgWrapper"></div></div>
										</div>
										<div class="buttonWrapper">
											<div class="leftButtonWrapper">
												<div class="leftButton">Close Invitations</div>
												<div class="textLeftButton">Game will kick-off with all friends that have accepted your game invitation so far</div>
											</div>
											<div class="rightButtonWrapper">
												<div class="rightButton">Send Reminder</div>
												<div class="textRightButton">Friends who did not respond yet will get a gentle reminder to join your game...</div>
											</div>
										</div>
									</div>
								</div>
							</div>"""

    	$('.positionAbsolute', popupDivBase).click( ->
    		jQuery('.status_show_popup').remove()
    		false
    	)
    	$('.leftButton',popupDivBase).click(-> closeInvits() )#close invite trigger
    	rejected = 0
    	for seatid of gameObjPLRS
    			totalResponse++
    			if parseInt(gameObjPLRS[seatid].GSS) is 1
    				totalResponse--
    				invitedImgs++
    				$('.inviteBlock .imgWrapper',popupDivBase).append """<img src="https://graph.facebook.com/#{gameObjPLRS[seatid].PFB}/picture" />"""

	    		else if parseInt(gameObjPLRS[seatid].GSS) is 2
	    			$('.acceptBlock .imgWrapper',popupDivBase).append """<img src="https://graph.facebook.com/#{gameObjPLRS[seatid].PFB}/picture" />"""
	    			
	    		else if parseInt(gameObjPLRS[seatid].GSS) is 3    			
	    			$('.declineBlock .imgWrapper',popupDivBase).append """<img src="https://graph.facebook.com/#{gameObjPLRS[seatid].PFB}/picture" />"""

    	$('.imgWrapper:not(:has(img))',popupDivBase).parent().remove()
    	
    	unless totalResponse > usersInfoObject.TP / 2
    		return
    	
    	return if invitedImgs == 0
    	
    	if zzGlobals.inviteStatus isnt null and typeof zzGlobals.inviteStatus isnt "undefined"
    		unless totalResponse > zzGlobals.inviteStatus 
    			return
    	
    	zzGlobals.inviteStatus = totalResponse #rejected.length + acceptedImgs.length + declinedImgs.length
   	
    	#remove last add popup if exist
    	jQuery('.status_show_popup').remove();
    		
		#append to body
    	jQuery("body").append popupDivBase
    	
    	true
    	
	jDocument.bind "dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, showInviteStatus
	
	true