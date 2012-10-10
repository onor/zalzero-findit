define ["./utils"], (utils) ->
	# // TODO: remove repated param and make function more generic (by Pankaj Anupam) 
	remindUsers : (gameId, UsersData = 0, seatId, userData) ->
	  jQuery.ajax(
	  	
	    type: "POST"
	    url: baseUrl + "/user/remindUser"
	    
	    data :
	      game_id	: gameId
	      user_data	: UsersData
	      lh_users_data	: userData
	      
	    success : (responseData, textStatus, jqXHR) ->
	    	messagePopup popupMSG.remindSucess('')
		      
	    error : (responseData, textStatus, errorThrown) ->
	    	utils.log("notificatoion send fail : "+responseData+"\n errorThrown : "+errorThrown);
	    	
	  )