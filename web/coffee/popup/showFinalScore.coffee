define [], () ->
	showFinalScore = () ->
      try
        if zzGlobals.roomVars.FR is "1" # if Game finished
          jQuery("#gameBetPanel").hide()
          jQuery(".resignPopup").hide()
          ii = 0
          rankHtmlArray = []
          rankHtml = ""
          disableRematch = false
          
          usersObject = zzGlobals.dataObjVars.AP

          for index of usersObject.PLSC
          	seatId = usersObject.PLSC[index]
          	if parseInt(usersObject.PLRS[seatId].PRE) is 1 or parseInt(usersObject.PLRS[seatId].GSS) isnt 2
          		if parseInt(zzGlobals.currentUserDBId) is parseInt(usersObject.PLRS[seatId].UI)
          			disableRematch = true
          		continue
          	if ii is 0
          		rankHtmlArray.push "<div  id='score" + ++ii + "'><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture' /><div class='name'>" + usersObject.PLRS[seatId].PDN + "</div><div class='score'>" + usersObject.PLRS[seatId].PSC + "</div></div>"
          	else
          		rankHtml += "<div class='score_rep' id='score" + ++ii + "'><div class='rank'>" + ii + "nd</div><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture' /><div class='name'>" + usersObject.PLRS[seatId].PDN + "</div><div class='score'>" + usersObject.PLRS[seatId].PSC + "</div></div>"
          
          if ii < 2 or disableRematch or resignStatus is 1
          	jQuery("#rematch").hide();
          	jQuery(".dismiss").css({marginTop: '1px', marginLeft: '50px'})
          else
          	jQuery("#rematch").show();
          	jQuery(".dismiss").css({marginTop: '', marginLeft: ''})
            
          rankHtmlArray.push rankHtml
          jQuery("#topScore_div").html rankHtmlArray[0]
          jQuery("#bottomScore_div").html rankHtmlArray[1]
          jQuery(".score_show_popup").css "display", "block"
          
	jDocument.bind "dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, showFinalScore