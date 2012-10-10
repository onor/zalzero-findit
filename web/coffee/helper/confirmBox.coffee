define ['./sound'], (sound) ->
	removeMessagePopup = ->
		sound.playCloseButtonSound()
		$('.overlay').remove()	  			  		
		$('.msg-outer-div').remove()
		
	window.messagePopup = (message,callback,callback_param)->
	  	removeMessagePopup()
	  	
	  	# play popup apperence sound if sound enable
	  	sound.playPopupApperenceSound()
	  	
	  	popUp = $ """<div class="overlay"></div><div class=\"msg-outer-div bounceIn animated\">
	  					<div class=\"msg-inner-div\"><div class=\"msgbox-msg\"><p>#{message}</p></div><div class="msgbox-ok">ok</div></div>
					</div>"""
	  		  		  	  		  	  	
	  	if typeof callback is 'undefined'
		  	$('.msgbox-ok',popUp).on 'click', (e) ->
		  		removeMessagePopup()
		  		
	  	else
	  		msgCancelDiv = $ "<div class=\"msgbox-cancel\">cancel</div>"
	  		
	  		$('.msgbox-ok',popUp).addClass("msgbox-cancel")
	  		
	  		msgCancelDiv.on 'click', (e) ->
	  			removeMessagePopup()	  		

	  		$('.msgbox-ok',popUp).on 'click', (e) ->	  			
		  		callback callback_param
		  		removeMessagePopup()
		  		 	
	  	$('.msgbox-ok',popUp).after msgCancelDiv

	  	$('#active-screen').append(popUp)