define [] , () ->
	# trigger on close invite request
	closeInvits = ->
		jDocument.trigger zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|CI"]
	 
	showInviteStatus = () ->
		
    	usersInfoObject = zzGlobals.dataObjVars.AP
    	totalUser =  usersInfoObject.TP
    	toShow = usersInfoObject.GCB
    	gameObjPLRS = usersInfoObject.PLRS
    	
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
    	
    	rejected = []
    	totalResponse = 0
    	invitedImgs = 0
    	for seatid of gameObjPLRS
    			userInfo = gameObjPLRS[seatid]
    			if parseInt(userInfo.GSS) is 1
    				totalResponse++
    				$('.acceptBlock .imgWrapper',popupDivBase).append """<img src="https://graph.facebook.com/#{userInfo.PFB}/picture" />"""

	    		else if parseInt(userInfo.GSS) is 2
	    			totalResponse++
	    			$('.declineBlock .imgWrapper',popupDivBase).append """<img src="https://graph.facebook.com/#{userInfo.PFB}/picture" />"""
	    			
	    		else if parseInt(userInfo.GSS) is 3
	    			invitedImgs++
	    			$('.inviteBlock .imgWrapper',popupDivBase).append """<img src="https://graph.facebook.com/#{userInfo.PFB}/picture" />"""
	    			
	    		else
	    			rejected.push userInfo.GSS
	    			

    	
    	$('.imgWrapper:not(:has(img))',popupDivBase).parent().remove()
    	
    	unless parseInt(toShow) is parseInt(zzGlobals.currentUserDBId)
    		return
    	
    	unless totalResponse > totalUser / 2 and invitedImgs != 0
    		return
    	
    	if zzGlobals.inviteStatus isnt null and typeof zzGlobals.inviteStatus isnt "undefined"
    		unless rejected.length + invitedImgs + totalResponse > zzGlobals.inviteStatus 
    			return
    	
    	zzGlobals.inviteStatus = rejected.length + acceptedImgs.length + declinedImgs.length
    	
   	
    	#remove last add popup if exist
    	jQuery('.status_show_popup').remove();
    		
		#append to body
    	jQuery("body").append popupDivBase
    	
	jDocument.bind "dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, showInviteStatus
	
	true