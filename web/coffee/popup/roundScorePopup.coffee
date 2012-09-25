define [], () ->
    showRoundScorePopup = () ->
      return  if zzGlobals.roomVars.CR.split("_")[1] is "0" or zzGlobals.roomVars.FR is "1"
      if parseInt(zzGlobals.roomVars.CR.split("_")[1]) isnt _currentRoundStatus
        _currentRoundStatus = parseInt(zzGlobals.roomVars.CR.split("_")[1])
      else
        return
      usersObject = jQuery.parseJSON(zzGlobals.roomVars.AP)
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
        			
      usersObject = usersObject[i]
      
      scorePopup = document.createElement("div")
      scorePopup.className = "roundresult"
      scorePopupContent = document.createElement("div")
      scorePopupContent.className = "content_r bounceIn animated"
      scorePopuptopthree = document.createElement("div")
      scorePopuptopthree.className = "top_three_r"
      i = 0
      rankHtml = ""
      userCount = 0
      for index of usersObject.PLSC
          seatId = usersObject.PLSC[index]
          continue if parseInt(usersObject.PLRS[seatId].GSS) isnt 2
          continue if parseInt(usersObject.PLRS[seatId].PRE) is 1
          userCount++
          if userCount <= 3 
            rankHtml += "<div class='score'><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB  + "/picture' /><div class='points plus'>" + usersObject.PLRS[seatId].PSC + "</div></div>"
          if zzGlobals.currentUserDBId is usersObject.PLRS[seatId].UI
            selfHtml = document.createElement("div")
            selfHtml.className = "score self"
            selfHtmlImg = document.createElement("img")
            selfHtmlImg.src = "https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture"
            selfHtmlPoints = document.createElement("div")
            selfHtmlPoints.className = "points plus"
            selfHtmlPoints.innerHTML = usersObject.PLRS[seatId].PSC
            selfHtml.appendChild selfHtmlImg
            selfHtml.appendChild selfHtmlPoints
      jQuery(".roundresult").remove()
      jQuery(scorePopuptopthree).append rankHtml
      roundPopupHeading = document.createElement("h2")
      roundPopupHeading.id = "round_no_r"
      jQuery(roundPopupHeading).text "Round " + parseInt(zzGlobals.roomVars.CR.split("_")[1])
      scorePopupContent.appendChild roundPopupHeading
      if selfHtml 
	      scorePopupContent.appendChild selfHtml
	      scorePopupContent.appendChild scorePopuptopthree
	      scorePopup.appendChild scorePopupContent
	      if parseInt(zzGlobals.roomVars.CR.split("_")[1]) > 0
	        $("#active-screen").append scorePopup
	        setTimeout (->
	          $(".roundresult").remove()
	        ), 5000
	        $(".roundresult").click ->
	          $(this).remove()
	          
    jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, showRoundScorePopup
    
    true