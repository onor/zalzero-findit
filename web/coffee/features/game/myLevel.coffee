define ['../../helper/utils'], (utils)->
	GB_UINFO = {}
	GB_UINFO.PFN = ""
		
	usersRecord = (gameRecords) ->

		# active game
		$(".Mylevel", "#rip_active_rh").remove()
		try
			message =  gameRecords.RH
			for gameId of message
				
				urDiv = $ """<div class="userArea Mylevel" id="myLevel_active_#{gameId}" ><div class="imgCon"></div></div>"""
				urDiv.append """<div class="round_no">#{message[gameId].CR}</div>"""  if message[gameId].CR
				
				for index of message[gameId].PLSC
					
					seatID = message[gameId].PLSC[index]
					
					continue  if message[gameId].PLRS[seatID].GSS is 5 or message[gameId].PLRS[seatID].GSS is 3
					
					if gameRecords.UINFO.UI is message[gameId].PLRS[seatID].UI
						urDiv.append """<div class="rank_active" >#{utils.playerRank(message[gameId].PLRS[seatID].PR)}</div>"""
					
					status = (if message[gameId].PLRS[seatID].PON is 1 then "online" else "offline")
					
					$('.imgCon',urDiv).append """<div class="imageWrapper" id="myLevel_active_Images#{gameId}"><img src="https://graph.facebook.com/#{message[gameId].PLRS[seatID].PFB}/picture" alt="#{message[gameId].PLRS[seatID].PFN}" class="#{status}" id="myLevel_active_who_am_i_#{seatID}" /></div>"""
					
				$("#rip_active_rh").append urDiv
			
			$(".ap_games").remove()
		
		try
			message = gameRecords.APG
	
			for gameId of message
				urDiv = $ """<div class="userArea Mylevel" id="myLevel_apg_#{gameId}" ></div>"""
				
				urDiv.append """<div class="end_date">#{message[gameId].ED}</div>"""
				urDiv.append """<div class="msgbox-ok">Rematch</div>"""
				FBids = []
				for index of message[gameId].PLRS
					
					continue  if message[gameId].PLRS[index].GSS is 5 or message[gameId].PLRS[index].GSS is 3
					
					FBids.push message[gameId].PLRS[index].PFB
					
					if gameRecords.UINFO.UI is message[gameId].PLRS[index].UI
						
						urDiv.append """<div class="point">Points- #{message[gameId].PLRS[index].PSC} </div>"""
						urDiv.append """<div class="rank #{if message[gameId].PLRS[index].PR is "1" then 'winner'}" >#{utils.playerRank(message[gameId].PLRS[index].PR)}</div>"""
					
					status = (if message[gameId].PLRS[index].PON is 1 then "online" else "offline")
					
					urDiv.append """<div class="imageWrapper" id="myLevel_apg_Images#{gameId}"><img src="https://graph.facebook.com/#{message[gameId].PLRS[index].PFB}/picture" alt="#{message[gameId].PLRS[index].PFN}" class="#{status}" id="myLevel_apg_who_am_i_#{index}" /></div>"""  if message[gameId].PLRS[index].PFB
				
				$('.msgbox-ok',urDiv).click({ids:FBids,gameOption:"Rematch",gameId:gameId}, (e)->
					rematchPastGames(e.data.ids, e.data.gameOption, e.data.gameId)
				)
					
				$("#rip_won_apg").append urDiv

		$ ->
			$(".scroll-pane").jScrollPane
				showArrows: true
				autoReinitialise: true
		
		try
			is_apg = true
			# APG hols the data about all past games
			if gameRecords.APG is "ALL_PAST_GAME" or gameRecords.APG is "" or not gameRecords.APG? or typeof (gameRecords.APG) is "undefined"
				APG = {}
				is_apg = false
			else
					APG = gameRecords.APG
				
			return  if typeof (gameRecords.UINFO) is "undefined"
			
			UINFO = gameRecords.UINFO
			GB_UINFO = UINFO
			UINFO.PL = 1  if typeof (UINFO.PL) is "undefined" or parseInt(UINFO.PL) is 0
			UINFO.user_pic = "https://graph.facebook.com/" + UINFO.PFB + "/picture?type=square"
			cm_fbids = ""
			gssCount = 0
		catch err


		try
			user_level = UINFO.PL
			cls = belt_array[user_level].toLowerCase()
			top_level = sizeOfObj(belt_array)
			if user_level < top_level
				next_level = user_level + 1
			else
				next_level = user_level
			next_cls = belt_array[next_level].toLowerCase()
			low_next_won_arr = total_won_array[next_level - 1].split("-")
			need_won_next = low_next_won_arr[0] - UINFO.GW
			low_next_game_arr = total_games_array[next_level - 1].split("-")
			need_game_next = low_next_game_arr[0] - UINFO.GP
			
			# cr_belt_html will hold the data of current belt level like played games and won games till now
			cr_belt_html = "<li>You played " + UINFO.GP + " game"
			cr_belt_html += "s"  if UINFO.GP > 0
			cr_belt_html += "</li><li>You won " + UINFO.GW + "</li>"
			
			#next_belt_html will hold the data, user need to complete like need t play 10 games and need to win 3 games to reach next level
			next_belt_html = ""
			if need_game_next > 0 and need_game_next > need_won_next
				next_belt_html += "<li>" + need_game_next + " more game"
				next_belt_html += "s"  if need_game_next > 1
				next_belt_html += "</li>"
			next_belt_html += "<li>" + need_won_next + " more to win</li>"  if need_won_next > 0
			$("#next_belt_ul").html next_belt_html
			$("#current_belt_ul").html cr_belt_html
			$("#current_belt_h4").html capFirst(cls)
			$("#next_belt_h4").html capFirst(next_cls)
			$("#belt-info").attr "class", "belt-info " + cls
			$("#belt-info").css "background", "url(" + baseUrl + "/images/zalerio_1.2/5.all_popup/mystats/mylevel/girl/girl_in" + cls + "/girl_" + cls + "_1.png) no-repeat -20px 33px"
			$("#belt_text").html capFirst(cls)
			if user_level is next_level
				$("#belt-info .next-belt").hide()
				$("#belt-info .dummy_div").show()
			else
				$("#belt-info .next-belt").show()
				$("#belt-info .dummy_div").hide()
			vertical_text = ""
			
			# this will add all belts and the highlighted belt according to user level on right side of my level pop up
			i = top_level
			while i > 0
				cl = belt_array[i].toLowerCase()
				cl_up = capFirst(cl)
				if user_level is i
					vertical_text += """<li class="active"><img src="../images/zalerio_1.2/5.all_popup/mystats/mylevel/belts/belts_highlighted/#{cl}.png" alt="Black belt"><a class="#{cl}" >{cl_up}</a></li>"""
				else
					vertical_text += """<li><img src="../images/zalerio_1.2/5.all_popup/mystats/mylevel/belts/belts_ideal/#{cl}.png" alt="Black belt"><a class="#{cl}" >#{cl_up}</a></li>"""
				i--
			$("#vertical_belt_conent").html vertical_text
			
			# show mylevel-check
			$("#mylevel-check").show()
		try
			
			$("#stat_image_main").attr "src", UINFO.user_pic
			$("#stat_image_tri_level").attr "src", baseUrl + "/images/zalerio_1.2/5.all_popup/mystats/mystatsCommon/stats_main_player_belts/" + userLevelImgBig[UINFO.PL - 1]
			$("#popup-stats .div_top .div_img #stat_userName").html UINFO.PDN
			$("#popup-stats .div_top #joined_time").html " " + UINFO.JD

			$("#user-level-span").removeClass().addClass(capFirst(cls.toLowerCase()) + "Belt").html capFirst(cls.toLowerCase()) + " Belt"
