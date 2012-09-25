define [], () ->
	
	playSound : true
	
	# attach sound file
	try
		audioBaseUrl = baseUrl.replace( '/index.php','/' )
		otherbuttonSound : new Audio( audioBaseUrl + "/sound/otherbuttons.wav" )
		selectbuttonSound : new Audio( audioBaseUrl + "/sound/select_button.wav" )
		popupapperence : new Audio( audioBaseUrl + "/sound/popupapperence.wav" )
		closebutton : new Audio( audioBaseUrl + "/sound/closebutton.wav" )
		tilepickup : new Audio( audioBaseUrl + "/sound/Tilepickup.wav" ) 	# tile pickup sound file
		titledrop : new Audio( baseUrl + "/sound/Tiledrop.wav")
		playbutton : new Audio( baseUrl + "/sound/playbutton.wav" )

	catch error
		console.log "Sound loading fail and error is" + error