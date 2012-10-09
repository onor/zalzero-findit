define [], () ->
	class Sound
		# constructor class
		constructor: () ->
			try
				@audioBaseUrl = baseUrl.replace( '/index.php','/' )
				@otherButton = new Audio( @audioBaseUrl + "/sound/otherbuttons.wav" )
				@selectButton = new Audio( @audioBaseUrl + "/sound/select_button.wav" )
				@popupApperence = new Audio( @audioBaseUrl + "/sound/popupapperence.wav" )
				@closeButton = new Audio( @audioBaseUrl + "/sound/closebutton.wav" )
				@tilePickup = new Audio( @audioBaseUrl + "/sound/Tilepickup.wav" ) 	# tile pickup sound file
				@titleDrop = new Audio( baseUrl + "/sound/Tiledrop.wav")
				@playButton = new Audio( baseUrl + "/sound/playbutton.wav" )
				@isPlaySound = true
				@isError = false
				window.isError = false
			catch err
				@isPlaySound = false
				@isError = true
				
				#TODO: // move complete sound to one class and remove this
				window.isError = true
				window.playSound = false
		
		onSound : () ->
			unless @isError
				@isPlaySound = true
		
		offSound : () ->
			@isPlaySound = false
			
		playOtherButtonSound : () ->
			if @isPlaySound
				@otherButton.play()
		
		playSelectButtonSound : () ->
			if @isPlaySound
				@selectButton.play()
		
		playPopupApperenceSound : () ->
			if @isPlaySound
				@popupApperence.play()
		
		playCloseButtonSound : () ->
			if @isPlaySound
				@closeButton.play()
				
		playTilePickupSound : () ->
			if @isPlaySound
				@tilePickup.play()
		
		playTitleDropSound : () ->
			if @isPlaySound
				@titleDrop.play()
				
		playPlayButtonSound : () ->
			if @isPlaySound
				@playButton.play()
	
	# return class instance			
	window.sound = new Sound