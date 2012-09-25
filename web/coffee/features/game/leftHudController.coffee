define ["../../config/config","./carouselView","./animation"], (config,carouselView,animation) ->
	updatePlayerPlate = ->
      currentUserObj = jQuery.parseJSON(zzGlobals.clientVars.UINFO)
      console.log "Player Plate : ", currentUserObj if isDevEnvironment
      jQuery("#currentUserName").text currentUserObj.PDN
      unless typeof currentUserObj.PL is "undefined"
        if parseInt(currentUserObj.PL) is 0
        	currentUserObj.PL = 1
        currentUserLevel = document.createElement("img")
        currentUserLevel.className = "userlevelbelt"
        currentUserLevel.src = baseUrl + "/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/" + config.userLevelImgBig[parseInt(currentUserObj.PL) - 1]
        jQuery("#currentUserAreaImg .userlevelbelt").remove()
        jQuery("#currentUserAreaImg").prepend currentUserLevel

    updateLeftHud = ->
      usersObject = jQuery.parseJSON(zzGlobals.roomVars.AP)
      console.log "All Users Data (zzGlobals.roomVars.AP)", usersObject
      for i of usersObject
        usersInfoObject = jQuery.parseJSON usersObject[i]
        break
      carouselView.drawCarousel usersInfoObject, "userTopPicHUDMain", i, "usercrousel"
      userPanelMov = new animation.UserPanelMov(jQuery("#userTopPicHUDMain"), null, null, null, crouselObjSize(jQuery.parseJSON(usersInfoObject.PLRS)))
      jQuery("#navCarouselRight").unbind('click').click ->
        userPanelMov.move "RIGHT", panelMoveMagnitudeCodes.ONE

      jQuery("#navCarouselLeft").unbind('click').click ->
        userPanelMov.move "LEFT", panelMoveMagnitudeCodes.ONE

    crouselObjSize = (obj)->
	    size = 0
	    for key of obj
	    	userObject = jQuery.parseJSON obj[key]
	    	continue if userObject.GSS is 3 or userObject.GSS is 5
	    	size++  if obj.hasOwnProperty(key)
	    size
    
    jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, updateLeftHud
    jDocument.bind "client:" + zzGlobals.clientCodes.USERINFO, updatePlayerPlate
    
    true