define [], () ->
	getFriendList : ->
		FB.api({
				method : 'fql.query'
				query : "SELECT uid, name, pic_square, online_presence, username FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) order by name"
			}, (response)->
				return response
		)