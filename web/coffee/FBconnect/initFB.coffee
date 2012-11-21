require.config({
    paths:
        'facebook' : 'http://connect.facebook.net/en_US/all.js'
})

require ['facebook'], (facebook) ->
	
	facebookInit : () ->
		if typeof(FB) isnt "undefined"
			# FB is initialized check login status
			FB.getLoginStatus ( response ) ->
				if response.status and response.status is "connected"
					#logged in and connected user, someone you know
					updateFBInfo response
				else
					#no user session available, someone you dont know
					console.log("Facebook is initialized but not connected!")
		else
			console.log("Facebook NOT initialized!")
			
			false