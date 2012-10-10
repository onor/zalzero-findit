define [], () ->
    window._currentRoundStatus = -1
    showRoundScorePopup = () ->
      roundNo = parseInt(zzGlobals.roomVars.CR.split("_")[1],10)
      return  if roundNo is 0 or zzGlobals.roomVars.FR is "1"

      if roundNo isnt window._currentRoundStatus
      	window._currentRoundStatus = roundNo
      else
      	return
      
      usersObject = zzGlobals.dataObjVars.AP
      
      scorePopup = $ """ <div class="roundresult"><div class="content_r bounceIn animated"><h2 class="round_no_r">Round #{zzGlobals.roomVars.CR.split("_")[1]}</h2></div></div> """

      scorePopuptopthree = $ """<div class="top_three_r"> </div>"""
      
      rankHtml = ""
      selfHtml = ""
      userCount = 0
      for index of usersObject.PLSC
          seatId = usersObject.PLSC[index]
          
          continue if parseInt(usersObject.PLRS[seatId].GSS) isnt 2 or usersObject.PLRS[seatId].PRE is 1
          
          userCount++
          if userCount <= 3 
            rankHtml += "<div class='score'><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB  + "/picture' /><div class='points plus'>" + usersObject.PLRS[seatId].PSC + "</div></div>"
            
          if zzGlobals.currentUserDBId is usersObject.PLRS[seatId].UI
            selfHtml = $ """ <div class="score self"><img src="https://graph.facebook.com/#{usersObject.PLRS[seatId].PFB}/picture" /><div class="points plus">#{usersObject.PLRS[seatId].PSC}</div></div> """

      $(".roundresult").remove()
      scorePopuptopthree.append rankHtml      

      if selfHtml isnt ""
	      $(".content_r",scorePopup).append selfHtml
	      $(".content_r",scorePopup).append scorePopuptopthree
	      
	      $("#active-screen").append scorePopup
	      setTimeout (->
	      		$(".roundresult").remove()
	      ), 5000
	      $(".roundresult").click ->
	          $(this).remove()
	          
    jDocument.bind "room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, showRoundScorePopup
    
    true