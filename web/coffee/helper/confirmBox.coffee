define [], () ->
	messagePopup = (message,callback,callback_param)->
	  	jQuery('.msg-outer-div').remove()
	  	try
	  		popupapperence.play() if playSound
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
		  		try
		  			closebutton.play() if playSound
		  		jQuery('.msg-outer-div').remove()
	  	else
	  		msgCancelDiv = document.createElement("div")
		  	msgCancelDiv.className = 'msgbox-cancel'
		  	msgCancelDiv.innerHTML = 'cancel'
	  		msgOkDiv.className = 'msgbox-ok msgbox-cancel'
	  		
	  		msgCancelDiv.onclick = ->
	  			try
		  			closebutton.play() if playSound
		  		#jQuery('.msg-outer-div').addClass("hinge")
		  		jQuery('.msg-outer-div').remove()
		  		
		  		
	  		msgOkDiv.onclick = ->
		  		callback(callback_param)
		  		try
		  			closebutton.play() if playSound
		  		jQuery('.msg-outer-div').remove()
	  		  	
	  	msgTextDiv.appendChild msgTextP  	
	  	msgInnerDiv.appendChild msgTextDiv
	  	msgInnerDiv.appendChild msgOkDiv  	
	  	msgInnerDiv.appendChild msgCancelDiv if msgCancelDiv
	  	msgDiv.appendChild msgInnerDiv
	  	jQuery('#active-screen').append(jQuery(msgDiv))