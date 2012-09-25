define [], () ->	
	showFrndSelector : ->
		if typeof tutorial != 'undefined' 
			if tutorial is true
				boxBlank = jQuery '.box-blank'
				boxBlank.text ''
				boxBlank.removeClass()
				boxBlank.addClass 'box-blank box-black'
		tutorial = false
				
		fbUserData = {}
		
		FB.api({
				method : 'fql.query'
				query : "SELECT uid, name, pic_square, online_presence, username FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) order by name"
			}, (response)->
				for i in response
					fbUserData [ i ] = response [ i ]
					html = ''
					for j in fbUserData
						html += '<div class="rep"><img src="'+fbUserData[j].pic_square+'" /><div>'+ fbUserData[j].name + '<br/></div>'
						html +='<input type="button" style="display:none" value="'+fbUserData[j].uid+'" class="status" />'
						html +='<span class="'+fbUserData[j].online_presence+'">'+fbUserData[j].online_presence+'</span>'
						html +='<a class="select_button right">Select</a></div><div class="line"></div>'
				$.ajax({
				 	type:'POST'
				 	url: siteUrl+"/user/getFriend"
				}).done (data) ->
					$('body').append data 
					$("#floatingBarsG").css 'display','none'
					$('.friendlist').html html
					
					if playSound
						popupapperence.play()						
			)