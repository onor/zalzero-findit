define ["../../config/config","../../helper/notifications"], (config,notifications) ->
	updatePlayerPlate = ->
      currentUserObj = jQuery.parseJSON(zzGlobals.clientVars.UINFO)

      $("#currentUserName").text currentUserObj.PDN
      
      unless typeof currentUserObj.PL is "undefined"
        if parseInt(currentUserObj.PL) is 0
        	currentUserObj.PL = 1

        $("#currentUserAreaImg .userlevelbelt").remove()
        
        $("#currentUserAreaImg").prepend """<img class="userlevelbelt" src="#{baseUrl}/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/#{config.userLevelImgBig[parseInt(currentUserObj.PL) - 1]}" />"""

    updateLeftHud = ->
    	plrs = zzGlobals.dataObjVars.AP.PLRS
    	$("#gameInfo-game-players").empty()
    	userList = $ """<div class="background"></div>"""
    	for seatID of plrs
    		
    		continue if zzGlobals.currentUserDBId is plrs[seatID].UI or plrs[seatID].GSS isnt 1 and plrs[seatID].GSS isnt 2 
    		
    		remind_user = ''
    		
    		userPlayStatusText = "not played yet"
    		userPlayStatusClassName = "userPlayStatus"
	    	if plrs[seatID].CRS is 0
	    		userPlayStatusClassName = "userPlayStatus"
	    		remind_user = """<div class="reminder" title="Remind" alt="Remind" ></div>"""
	    	else if plrs[seatID].CRS is 5
	    		userPlayStatusText = '...playing now'
	    		userPlayStatusClassName = "userPlayStatus green"
	    	else if plrs[seatID].CRS is 9
	    		userPlayStatusText = "finished round"
	    		userPlayStatusClassName = "userPlayStatus green"
	    	
	    	if plrs[seatID].GSS is 1
	    		userPlayStatusClassName = "userPlayStatus red"
	    		userPlayStatusText = "not accepted yet"    	
	    		remind_user = """<div class="reminder" title="Remind" alt="Remind"></div>"""
	    		
	    	fbUser = {};
	    	fbUser[plrs[seatID].PFB] = {GSS:plrs[seatID].GSS,CRS:plrs[seatID].CRS}

	    	infoPlate = $ """ <div class="infoPlate">
									#{remind_user}
									<div class="userAreaImg" id="">
											<img class="userlevelbelt" src="#{baseUrl}/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/#{config.userLevelImgBig[parseInt(plrs[seatID].PL) - 1]}" />
			                                <img class="backendImage #{if plrs[seatID].PON isnt 1 then "offline" }" src="https://graph.facebook.com/#{plrs[seatID].PFB}/picture" />
			                        </div>
									<div class="userinfo">
										<div class="username">#{plrs[seatID].PDN}</div>
										<div class="lastPlayed">#{if plrs[seatID].PLP then plrs[seatID].PLP else '' }</div>
										<div class="#{userPlayStatusClassName}">#{userPlayStatusText}</div>
									</div>
						 </div>
					"""
	    	
	    	$('.reminder',infoPlate).click( {gameId:gameInstId,seatID:seatID, user:fbUser}, (e)->
	    		notifications.remindUsers(e.data.gameId, e.data.user,e.data.seatID,e.data.user);
	    	)
	    	
	    	userList.append infoPlate
	    
	 #   $('.reminder',userList).tooltip();
	    	
    	$("#gameInfo-game-players").append userList

    
    jDocument.bind "dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, updateLeftHud
    jDocument.bind "client:" + zzGlobals.clientCodes.USERINFO, updatePlayerPlate
    
    true