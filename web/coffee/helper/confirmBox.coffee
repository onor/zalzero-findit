define ['./sound'], (sound) ->
	window.messagePopup = (message,callback,callback_param)->
	  	jQuery('.msg-outer-div').remove()
	  	
	  	# play popup apperence sound if sound enable
	  	sound.playPopupApperenceSound()
		  	
	  	msgDiv = document.createElement("div")
	  	msgDiv.className = 'msg-outer-div bounceIn animated'
	  	
	  	msgInnerDiv = document.createElement("div")
	  	msgInnerDiv.className = 'msg-inner-div'
	  	
	  	msgTextDiv = document.createElement("div")
	  	msgTextDiv.className = 'msgbox-msg'
	  	
	  	msgTextP = document.createElement("p")
	  	msgTextP.innerHTML = message
	  	
	  	msgOkDiv = document.createElement("div")
	  	msgOkDiv.className = 'msgbox-ok'
	  	msgOkDiv.innerHTML = 'ok'
	  	  	
	  	if typeof callback is 'undefined'
		  	msgOkDiv.onclick = ->
		  		# play close button sound if sound enable
		  		sound.playCloseButtonSound()		  		
		  		jQuery('.msg-outer-div').remove()
	  	else
	  		msgCancelDiv = document.createElement("div")
		  	msgCancelDiv.className = 'msgbox-cancel'
		  	msgCancelDiv.innerHTML = 'cancel'
	  		msgOkDiv.className = 'msgbox-ok msgbox-cancel'
	  		
	  		msgCancelDiv.onclick = ->
	  			# play close button sound if sound enable
		  		sound.playCloseButtonSound()
		  		
		  		#jQuery('.msg-outer-div').addClass("hinge")
		  		jQuery('.msg-outer-div').remove()
		  		
		  		
	  		msgOkDiv.onclick = ->
		  		callback(callback_param)
		  		# play close button sound if sound enable
		  		sound.playCloseButtonSound()
		  		
		  		jQuery('.msg-outer-div').remove()
	  		  	
	  	msgTextDiv.appendChild msgTextP  	
	  	msgInnerDiv.appendChild msgTextDiv
	  	msgInnerDiv.appendChild msgOkDiv  	
	  	msgInnerDiv.appendChild msgCancelDiv if msgCancelDiv
	  	msgDiv.appendChild msgInnerDiv
	  	jQuery('#active-screen').append(jQuery(msgDiv))