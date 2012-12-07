define ["./utils"], (utils) ->
	# // TODO: remove repated param and make function more generic (by Pankaj Anupam) 
	remindUsers : (gameId, UsersData = 0, seatId, userData) ->
	  $.ajax(
	  	
	    type: "POST"
	    url: baseUrl + "/user/remindUser"
	    
	    data :
	      game_id	: gameId
	      user_data	: UsersData
	      lh_users_data	: userData
	      
	    success : (responseData, textStatus, jqXHR) ->
	    	if responseData
		    	FB.ui({method: 'apprequests', message: 'You are invited to play a game of zalerio', to: responseData }, (response) ->
		    			if response
		    				messagePopup popupMSG.remindSucess('')
		    			else
		    				utils.log("notificatoion send fail : "+responseData+"\n errorThrown : "+errorThrown);
		    		)
	    	else
	    		messagePopup popupMSG.remindSucess('')
	    		
	    error : (responseData, textStatus, errorThrown) ->
	    	utils.log("notificatoion send fail : "+responseData+"\n errorThrown : "+errorThrown);
	    	
	  )