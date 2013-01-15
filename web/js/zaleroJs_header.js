/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var loader_small = '<div id="floatingBarsGs">\
	<div class="blockG" id="rotateG_01">\
	</div>\
	<div class="blockG" id="rotateG_02">\
	</div>\
	<div class="blockG" id="rotateG_03">\
	</div>\
	<div class="blockG" id="rotateG_04">\
	</div>\
	<div class="blockG" id="rotateG_05">\
	</div>\
	<div class="blockG" id="rotateG_06">\
	</div>\
	<div class="blockG" id="rotateG_07">\
	</div>\
	<div class="blockG" id="rotateG_08">\
	</div>\
	</div>';

var userLevelImgBig = new Array("white.png", "yellow.png", "orange.png",
		"green.png", "blue.png", "purple.png", "red.png", "brown.png",
		"black.png");

var playSound = true;

try {
	var audioBaseUrl = baseUrl.replace('/index.php', '/');
	var otherbuttonSound = new Audio(audioBaseUrl + "/sound/otherbuttons.wav"); // other
	// button
	// sound
	// file
	var selectbuttonSound = new Audio(audioBaseUrl + "/sound/select_button.wav");
	var popupapperence = new Audio(audioBaseUrl + "/sound/popupapperence.wav");
	var closebutton = new Audio(audioBaseUrl + "/sound/closebutton.wav");
	var tilepickup = new Audio(audioBaseUrl + "/sound/Tilepickup.wav"); // tile
	// pickup
	// sound
	// file
	var titledrop = new Audio(baseUrl + "/sound/Tiledrop.wav");
	var playbutton = new Audio(baseUrl + "/sound/playbutton.wav");
} catch (err) {
	playSound = false
}

var ofbizUser = {};
var flag_fbPullAuthorised = false;
var oloFunctionRequests = {};
var InviteFriends;
var usersRecord;
jQuery(function() {
	facebookInit();
});

jQuery('#close').live('click', function() {
	if (playSound) {
		closebutton.play(); // play sound on close button click
	}

})

jQuery('.closesound').live('click', function() {
	if (playSound) {
		closebutton.play(); // play sound on close button click
	}

})

function facebookInit() {
	if (typeof (FB) !== "undefined") {
		FB.getLoginStatus(function(response) {
			if (response.status && response.status === "connected") {
				updateFBInfo(response);
			} else {
			}
		});
	} else {
		// console.log("Facebook NOT initialized!");
	}
}

function updateFBInfo(response) {
	if (typeof (response) !== "undefined") {
		if ((response.status && response.status === "connected"
				&& response.authResponse && response.authResponse.userID && response.authResponse.accessToken)) {
			ofbizUser["$user_fbid"] = response["authResponse"]["userID"];
			ofbizUser["accessToken"] = response["authResponse"]["accessToken"];
			ofbizUser["fbStatus"] = 1;
			serveFBRequests();
		} else if (typeof (response[2]) !== "undefined"
				&& typeof (response[2][0]) !== "undefined"
				&& typeof (response[2][0]["status"]) !== "undefined") {
			if (response[2][0]["status"] === "notConnected") {
				return -1;
			}
		}
	}
	return 0;
}

function serveFBRequests() {
	oloFunctionRequests["muteX"] = 0;
	var ctr = 0;
	for ( var i in oloFunctionRequests) {
		request = oloFunctionRequests[i];
		if (request !== 0) {
			if (request === 1) {// request w/o parameters
				window[i]();
			} else if (request === 2) { // request with parameters
				eval(i);
			}
			oloFunctionRequests[i] = 0;
		}
	}
}

function createDocInputElement(elName, elValue) {
	var el = document.createElement("input");
	el.type = "hidden";
	el.name = elName;
	el.value = elValue;
	return el;
}

/**
 * User created game written by pankaj anupam
 */
var inviteUserId = new Array();
var siteUrl = baseUrl;// 'http://localhost/zalzero';
function showFrndSelector() {
	if (typeof tutorial != 'undefined') {
		if (tutorial == true) {
			boxBlank = jQuery('.box-blank');
			boxBlank.text('');
			boxBlank.removeClass();
			boxBlank.addClass('box-blank box-black ');
		}
		tutorial = false
	}

	$('body')
			.append(
					'<div class="wait"><div id="floatingBarsG">\
    		<div class="blockG" id="rotateG_01">\
    		</div>\
    		<div class="blockG" id="rotateG_02">\
    		</div>\
    		<div class="blockG" id="rotateG_03">\
    		</div>\
    		<div class="blockG" id="rotateG_04">\
    		</div>\
    		<div class="blockG" id="rotateG_05">\
    		</div>\
    		<div class="blockG" id="rotateG_06">\
    		</div>\
    		<div class="blockG" id="rotateG_07">\
    		</div>\
    		<div class="blockG" id="rotateG_08">\
    		</div>\
    		</div></div>')
	flag_fbPullAuthorised = true;
	if (flag_fbPullAuthorised === true) {
		FB
				.api(
						{
							method : 'fql.query',
							query : "SELECT uid, name, pic_square, online_presence, username FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) order by name"
						},
						function(response) {
							fbUserData = {};
							for ( var i in response) {
								fbUserData[i] = response[i];
							}
							// console.log(fbUserData) ;// alert('check');
							var html = '';
							for ( var j in fbUserData) {
								html += '<div class="rep"><img src="'
										+ fbUserData[j].pic_square
										+ '" /><div>' + fbUserData[j].name
										+ '<br/></div>';
								html += '<input type="button" style="display:none" value="'
										+ fbUserData[j].uid
										+ '" class="status" />';
								html += '<span class="'
										+ fbUserData[j].online_presence + '">'
										+ fbUserData[j].online_presence
										+ '</span>';
								html += '<a class="select_button right">Select</a></div><div class="line"></div>';
							}

							$.ajax({
								
								type : 'POST',
								url : siteUrl + "/user/getFriend"
								
							}).done(function(data) { // alert(data);
								$('body').append(data);
								$("#floatingBarsG").css('display', 'none');
								$('.friendlist').html(html);
								if (playSound) {
									popupapperence.play(); // popup apperence
								}
							});
						});
		flag_fbPullAuthorised = false;
	}
}

jQuery(function($) {
	$('#startButton').click(function() {
		if (playSound) {
			otherbuttonSound.play();
		}
		try {
			jQuery('.gameInfoPanel').css('display', 'block');
			jQuery('.gameScore').css('display', 'block');
			jQuery('#tutorial-accordion').css('display', 'none');
		} catch (err) {
		}
		showFrndSelector();
	});

	$('#findfriend').live('keyup', function() {

		var findFriend = $('#findfriend').val().toLowerCase();
		$('.rep').css('display', 'none');
		$('.line').css('display', 'none');

		$('.rep div').each(function(i) {
			if ($(this).text().toLowerCase().indexOf(findFriend) != -1) {
				$(this).parent('.rep').css('display', 'block');
				$(this).parent('.rep').next().css('display', 'block');
			}

		});
		// $('#findfriend').val('');
	});

	$('#show_all_friends').live('click', function() {
		$('.rep').css('display', 'block');
		$('.line').css('display', 'block');
		$('#findfriend').val('');

	});

	$('.select_button').live(
			"click",
			function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
					$(this).parent('.rep').find('.countme').removeClass(
							'countme');
				} else {
					if (playSound) {
						selectbuttonSound.play(); // select friend sound
					}
					$(this).addClass('selected');
					$(this).parent('.rep').find('input[type=button]').addClass(
							'countme');
				}
			});

	InviteFriends = function(id, gameOption, gameId) {
		var friends_id = id;
		var fbUserData = {};
		var flag = false;
		if (typeof (gameOption) == 'undefined') {
			gameOption = 'Create';
		}
		if (typeof (gameId) == 'undefined') {
			gameId = 0;
		}

		todayDate = new Date(); // get the current date

		FB
				.api(
						{
							method : 'fql.query',
							query : 'SELECT uid, name, first_name, last_name,pic_square, online_presence, username FROM user WHERE uid in('
									+ friends_id + ')'
						}, function(response) {
							fbUserData = response;

							$.ajax({
								type : 'POST',
								url : siteUrl + "/gameinst/createnewgame",
								data : {
									'gameOption' : gameOption,
									'fbUserData' : fbUserData,
									'gameId' : gameId
								}
							}).done(
									function(data) {
										// change game
										var gameseat_gameinst_id = data;
										messagePopup(popupMSG.gameCreate,
												gameChangeListener,
												gameseat_gameinst_id)
										jQuery('.wait').remove();
										jQuery('.show_popup').remove();
										flag = true;
									});
						});
		return flag;
	}
	
	function removeA(arr) {
	    var what, a = arguments, L = a.length, ax;
	    while (L > 1 && arr.length) {
	        what = a[--L];
	        while ((ax= arr.indexOf(what)) !== -1) {
	            arr.splice(ax, 1);
	        }
	    }
	    return arr;
	}
	
	$('#sendrinvite').live("click", function() {
		
		jQuery('.footerbutton')
		.append(
				'<div id="floatingBarsGs" style="left:130px">\
<div class="blockG" id="rotateG_01">\
</div>\
<div class="blockG" id="rotateG_02">\
</div>\
<div class="blockG" id="rotateG_03">\
</div>\
<div class="blockG" id="rotateG_04">\
</div>\
<div class="blockG" id="rotateG_05">\
</div>\
<div class="blockG" id="rotateG_06">\
</div>\
<div class="blockG" id="rotateG_07">\
</div>\
<div class="blockG" id="rotateG_08">\
</div>\
</div>');
jQuery('#sendrinvite').attr('value', 'Sending...');
jQuery('#sendrinvite').css('cursor', 'default');
jQuery('#sendinvite').css('display', 'none');

		id = '1'; // not in use function will select login user id.
		jQuery.ajax({
			type : 'POST',
			url : baseUrl + "/user/waiting_users",
			data : {
				'userFBID' : id
			}
		}).done( function(user_ids) { 

			if(user_ids != "" && user_ids != false ){

					// get the id's and send app request
					FB.ui({method: 'apprequests',
				          message: 'My Great Request',
				          to: user_ids
				    }, function(response){

				    	if(response ){
				    		//sizeOfObj(response)
				    		//remove usr
				    		//if(typeof response->to != 'undefined'){
				    			InviteFriends(user_ids);
				    			
				    			// remove users
				    			
				    			jQuery.ajax({
									type : 'POST',
									url : baseUrl + "/user/waiting_users_remove",
									data : {
										'usersFBID' : user_ids
									}
								}).done(function(data) {})
				    			
				    			
				    		//}else{
				    			// change usr status
				    		//}
				    			
				    	}else{
				    		jQuery('.wait').remove();
							jQuery('.show_popup').remove();
							messagePopup('Please select a friend.');
							
							// change usr status
							
							jQuery.ajax({
								type : 'POST',
								url : baseUrl + "/user/waiting_users_status_change",
								data : {
									'usersFBID' : user_ids
								}
							}).done(function(data) {})
							
				    	}
				    });
			}else{
	    		jQuery('.wait').remove();
				jQuery('.show_popup').remove();
				messagePopup('Oops enough players are not available.Please wait we will inform as soon as possible.');
	    	}		
		});
	});

	$('#sendinvite')
			.live(
					"click",
					function() {
						if (playSound) {
							otherbuttonSound.play();
						}
						var id = new Array();
						$('.countme').each(function() {
							id.push($(this).val());
						});
						if (id.length == 0) {
							messagePopup('Please select a friend.');
						} else if (id.length > 4) {
							messagePopup('Please select up to 4 friends.');
						} else {
							if (jQuery('#sendinvite').attr('value') == 'Sending...') {
								return;
						}

							jQuery('.footerbutton')
									.append(
											'<div id="floatingBarsGs">\
    		<div class="blockG" id="rotateG_01">\
    		</div>\
    		<div class="blockG" id="rotateG_02">\
    		</div>\
    		<div class="blockG" id="rotateG_03">\
    		</div>\
    		<div class="blockG" id="rotateG_04">\
    		</div>\
    		<div class="blockG" id="rotateG_05">\
    		</div>\
    		<div class="blockG" id="rotateG_06">\
    		</div>\
    		<div class="blockG" id="rotateG_07">\
    		</div>\
    		<div class="blockG" id="rotateG_08">\
    		</div>\
    		</div>');
							jQuery('#sendinvite').attr('value', 'Sending...');
							jQuery('#sendinvite').css('cursor', 'default');
							jQuery('#sendrinvite').css('display', 'none');

									// get the id's and send app request
									
									FB.ui({method: 'apprequests',
								          message: 'You are invited to play a game of zalerio',
								          to: id
								    }, function(response){
								    	
								    	if(response ){
								    		InviteFriends(id);
								    		// nothing to do									    		
								    	}else{
								    		
											jQuery('.wait').remove();
											jQuery('.show_popup').remove();
											messagePopup('Not enough players to create a game .Please select again and try.');
											
								    	}
										
								    });
						}
					});

	$('.fblist .rep').live("click", function() {
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');
			$(this).find('.countme').removeClass('countme');
		} else {
			$(this).addClass('selected');
			$(this).find('input[type=hidden]').addClass('countme');
		}
	});

	// Perfinary ajax call
	$('#sendFbInvite').live("click", function() {
		var id = new Array();
		$('.fblist .rep .countme').each(function() {
			id.push($(this).val());
		});
		if (id.length < 1) {
			messagePopup("Please Select a Friend");
			return;
		}
		var selectedUser = {};
		var count = 0;
		for (i in fbUserData) {
			if (fbUserData.hasOwnProperty(i)) {
				count++;
			}
		}
		for ( var i = 0, j = 0; i < count; i++) {
			if (jQuery.inArray(fbUserData[i].uid, id) != -1) {
				selectedUser[j++] = fbUserData[i];
				fb_publish(fbUserData[i].uid) // post on user wall/timeline
			}
		}

		// console.log(selectedUser) ;
		$.ajax({
			type : 'POST',
			url : siteUrl + "/user/getFbFriendsList",
			data : {
				'fbUserData' : selectedUser
			}
		}).done(function(data) {
			// console.log(data);console.log('data');
			$('.show_popup').remove();
			$('.wait').remove();
		});
	});
	function fb_publish(friendId, gameId) {
		/* if(opts == 'undefined') { */
		var opts = {
			message : 'Zalerio game Invitation',
			name : 'Zalerio',
			link : "http://" + window.location.hostname + "/?invited_game_id="
					+ gameId,
			description : 'Join Zalerio to play game'
		};
		/* } */
		$.holdReady(true);
		FB.api('/' + friendId + '/feed', 'post', opts, function(response) {
			if (!response || response.error) {
				$.holdReady(false);
				// console.log(response);
			} else {
				$.holdReady(false);
			}
		});
	}
	jQuery("#bottomHUDbuttons-acc").css('display', 'none');
	/** *********** this wil show stats popup****************** */
	var showStats = function() {
		// define funtion
		// shows my games data
		// hide all popup blocks
		jQuery(".popup-block").hide();
		// show only popup-stats block
		jQuery("#popup-stats").show();
		// remove my level and my friends div
		jQuery("#mylevel_stats").hide();
		jQuery("#myfriends_stats").remove();
		jQuery("#mygames_stats").show();
		// jQuery("#active-screen").append("<div class='wait'></div>");
		jQuery(".s_show_popup").show();
	}
	// this function will show data of My Level tab in stats screen
	var showMyLevel = function() {
		// remove my friends and level data
		jQuery("#myfriends_stats").hide();

		// hide my games data
		jQuery("#mygames_stats").hide();
		// get data for my level
		jQuery("#mylevel_stats").show();
		jQuery("#learn-performance-level").click(function() {
			if (playSound) {
				otherbuttonSound.play();
			}
			jQuery('.performance_level').show();
		});
		jQuery("#close-performance-level").click(function() {
			jQuery('.performance_level').hide();
		});

	}
	var showHelp = function() {

		jQuery(".popup-block").hide();
		jQuery("#help-tutorial").show();
		jQuery("#popup-help").show();
		jQuery(".s_show_popup").show();
		// this will add custom scroller
		/** ** for scroll bar ***** */
		jQuery(function() {
			jQuery('.rules-container').jScrollPane({
				showArrows : true
			});

		});

		/** ** end ** */

		jQuery("#tutorial_startgame").click(
				function(event) {
					event.preventDefault();
					var temp_h = jQuery("#tutorial_0").height();
					jQuery('#rules-container .jspContainer .jspPane').css(
							'top', "-" + temp_h + "px");
				});

		jQuery("#tutorial_taketurn").click(
				function(event) {
					event.preventDefault();
					var temp_h = jQuery("#tutorial_0").height()
							+ jQuery("#tutorial_1").height();
					jQuery('#rules-container .jspContainer .jspPane').css(
							'top', "-" + temp_h + "px");
				});

		jQuery("#tutorial_scoring").click(
				function(event) {
					event.preventDefault();
					var temp_h = jQuery("#tutorial_0").height()
							+ jQuery("#tutorial_1").height()
							+ jQuery("#tutorial_2").height();
					jQuery('#rules-container .jspContainer .jspPane').css(
							'top', "-" + temp_h + "px");
				});

		jQuery("#tutorial_jokers").click(
				function(event) {
					event.preventDefault();
					var temp_h = jQuery("#tutorial_0").height()
							+ jQuery("#tutorial_1").height()
							+ jQuery("#tutorial_2").height()
							+ jQuery("#tutorial_3").height();
					jQuery('#rules-container .jspContainer .jspPane').css(
							'top', "-" + temp_h + "px");
				});

	}
	var getHelpContactUsForm = function() {
		jQuery.post(baseUrl + '/help/contactus', {}, function(data) {

			if (data) {
				jQuery("#help-contactus").html(data);
				jQuery(function() {
					jQuery('select.styled').customStyle();
				});
			}
		});
	}

	// show stats
	jQuery("#bottomHUDbuttons-stats").click(function() {
		if (playSound) {
			otherbuttonSound.play();
		}
		if (jQuery(".s_show_popup").css('display') == 'none') {
			showStats();
		} else {
			if (jQuery("#popup-stats").css('display') == 'none') {
				showStats();
			} else {
				jQuery(".s_show_popup").hide();
			}
		}
	});
	/** ***** show help pop up******** */

	jQuery(".rightHUDPanel .gameMenu ul li.helpButton").click(function() {
		if (playSound) {
			otherbuttonSound.play();
		}
		if (jQuery(".s_show_popup").css('display') == 'none') {
			showHelp();
		} else {
			if (jQuery("#popup-help").css('display') == 'none') {
				showHelp();
			} else {
				jQuery(".s_show_popup").hide();
			}
		}
	});

	/** **** end******** */

	/** ************ toggle between help tutorials and contact us ***** */

	jQuery("#a-help-tutorial").click(function() {
		if (playSound) {
			otherbuttonSound.play();
		}
		jQuery(this).addClass('active');
		jQuery("#a-help-contactus").removeClass('active');
		jQuery("#help-contactus").hide();
		jQuery("#help-tutorial").show();
	});

	jQuery("#a-help-contactus").click(function() {
		if (playSound) {
			otherbuttonSound.play();
		}
		jQuery(this).addClass('active');
		jQuery("#a-help-tutorial").removeClass('active');
		getHelpContactUsForm();

		jQuery("#help-contactus").show();
		jQuery("#help-tutorial").hide();
	});
	// toggle between stats tabs
	jQuery(".statslink").click(function(data) {
		jQuery(".statslink").removeClass('active');
		jQuery(this).addClass('active');
		var id = jQuery(this).attr('id');
		if (id == 'mygames_a') {
			if (playSound) {
				otherbuttonSound.play();
			}
			showStats();
		}
		if (id == 'mylevel_a') {
			if (playSound) {
				otherbuttonSound.play();
			}
			showMyLevel();
		}
	});
	jQuery("#upload_help_contactus").live(
			'click',
			function() {
				jQuery("#help_contact_form input,select,textarea,submit")
						.removeClass('error');
				;
				var first_name = jQuery(
						"#help_contact_form input[name=first_name]").val();
				var last_name = jQuery(
						"#help_contact_form input[name=last_name]").val();
				var email = jQuery("#help_contact_form input[name=email]")
						.val();
				var subject = jQuery("#help_contact_form select[name=subject]")
						.val();
				var body = jQuery("#help_contact_form textarea[name=body]")
						.val();
				var verifyCode = jQuery(
						"#help_contact_form input[name=verifyCode]").val();
				var valid = '1';
				if (first_name == "" || first_name == null
						|| first_name.length > 50) {
					jQuery("#help_contact_form input[name=first_name]")
							.addClass('error');
					valid = '0';
				}
				if (last_name.length > 50) {
					jQuery("#help_contact_form input[name=last_name]")
							.addClass('error');
					valid = '0';
				}
				if (!valid_email(email) || email.length > 100) {
					jQuery("#help_contact_form input[name=email]").addClass(
							'error');
					valid = '0';
				}
				if (subject == "" || subject == null || subject.length > 50) {
					jQuery("#help_contact_form select[name=subject]").addClass(
							'error');
					valid = '0';
				}
				if (body == "" || body == null || body.length > 2000) {
					jQuery("#help_contact_form textarea[name=body]").addClass(
							'error');
					valid = '0';
				}
				if (verifyCode == "" || verifyCode == null) {
					jQuery("#help_contact_form input[name=verifyCode]")
							.addClass('error');
					valid = '0';
				}
				if (valid == 1) {
					jQuery.post(baseUrl + '/help/contactus', {
						'first_name' : first_name,
						'last_name' : last_name,
						'email' : email,
						'subject' : subject,
						'body' : body,
						'verifyCode' : verifyCode
					}, function(data) {
						var obj = JSON.parse(data);
						if (obj.status == '1') {
							jQuery("#help_contact_form input[name=first_name]")
									.val('').removeClass('error');
							jQuery("#help_contact_form input[name=last_name]")
									.val('').removeClass('error');
							jQuery("#help_contact_form input[name=email]").val(
									'').removeClass('error');
							jQuery("#help_contact_form textarea[name=body]")
									.val('').removeClass('error');
							jQuery("#help_contact_form input[name=verifyCode]")
									.val('').removeClass('error');

						}
						messagePopup(obj.msg);
					});
				}
			});
	jQuery("#help_contact_form input,select,textarea,submit").live('click',
			function() {
				jQuery("#msg-submit-popup").remove();
			});

	$('.dismiss').live(
			'click',
			function() {
				if (playSound) {
					otherbuttonSound.play();
				}
					jQuery("#rating_form input[name=rating_level]").val('');
					jQuery("#rating_form textarea[name=comment_improvement]")
							.val('Write a comment here');
					jQuery("#rating_form textarea[name=comment_like]").val(
							'Write a comment here');
					jQuery(".rating-popup").show();
				
			});
	var countImg = 1;
	var lastImg = 1;
	var url;

	// Mute Unmute
	jQuery(".audioButton a").click(function() {
		if(!isError){
				if (playSound){
					console.log(sound.offSound());
					playSound = false;
					$(this).parent('.audioButton').addClass('mute');
				} else {
					sound.onSound()
					playSound = true;
					$(this).parent('.audioButton').removeClass('mute');
				}
		}
	});
}) // jQuery

jQuery(document)
		.ready(
				function() {
					// when user does not land in any game then show friend
					// selector
					if (typeof gameInstId != 'undefined'
							&& typeof tutorial != 'undefined'
							&& tutorial != true) {
						if (gameInstId == '0') {
							setTimeout(showFrndSelector, 3000);
						}
					}
					/** * when some one clicks on cheat sheet button cheat sheet * */
					jQuery("#cheat-sheet")
							.click(
									function() {
										if (playSound) {
											otherbuttonSound.play();
										}
										if (jQuery(".popup-cheatsheet").css(
												'display') == 'none') {
											jQuery(".popup-cheatsheet").show();
											jQuery(".friendChallenge")
													.addClass(
															"back_to_the_game");
											jQuery('.popup-cheatsheet')
													.jScrollPane({
														showArrows : true
													})
											jQuery("body")
													.click(
															function() {
																if (jQuery(
																		".popup-cheatsheet")
																		.css(
																				'display') == 'block') {
																	if (playSound) {
																		closebutton
																				.play(); // play
																		// sound
																		// on
																		// close
																		// button
																		// click
																	}
																	jQuery(
																			".popup-cheatsheet")
																			.hide();
																	jQuery(
																			".friendChallenge")
																			.removeClass(
																					"back_to_the_game");
																}

															});
											jQuery(".popup-cheatsheet").click(
													function(e) {
														e.stopPropagation();
													});
											jQuery(".friendChallenge").click(
													function(e) {
														e.stopPropagation();
													});
										} else {
											jQuery(".popup-cheatsheet").hide();
											jQuery(".friendChallenge")
													.removeClass(
															"back_to_the_game");
										}

									});
				});

/** **for select box (contact us)*** */
(function($) {
	$.fn
			.extend({
				customStyle : function(options) {
					if (!$.browser.msie
							|| ($.browser.msie && $.browser.version > 6)) {
						return this
								.each(function() {
									var currentSelected = $(this).find(
											':selected');
									$(this).after(
											'<span class="customStyleSelectBox"><span class="customStyleSelectBoxInner">'
													+ currentSelected.text()
													+ '</span></span>').css(
											{
												position : 'absolute',
												opacity : 0,
												fontSize : $(this).next().css(
														'font-size', '15px')
											});
									var selectBoxSpan = $(this).next();
									var selectBoxWidth = parseInt($(this)
											.width())
											- parseInt(selectBoxSpan
													.css('padding-left'))
											- parseInt(selectBoxSpan
													.css('padding-right'));
									var selectBoxSpanInner = selectBoxSpan
											.find(':first-child');
									selectBoxSpan.css({
										display : 'inline-block'
									});
									selectBoxSpanInner.css({
										width : selectBoxWidth,
										display : 'inline-block'
									});
									var selectBoxHeight = parseInt(selectBoxSpan
											.height())
											+ parseInt(selectBoxSpan
													.css('padding-top'))
											+ parseInt(selectBoxSpan
													.css('padding-bottom'));
									$(this).height(selectBoxHeight).change(
											function() {
												selectBoxSpanInner.text(
														$(this).find(
																':selected')
																.text())
														.parent().addClass(
																'changed');
											});
								});
					}
				}
			});
})(jQuery);
/** ** end ***** */
/** * name and email validation *** */
function valid_email(email) {
	email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	if (email_regex.test(email))
		return true;
	return false;
}
/** *** end **** */

/** * submit rating *** */
jQuery("#winnerscreen #close").live(
		'click',
		function() {
			jQuery("#rating_form input[name=rating_level]").val('');
			jQuery("#rating_form textarea[name=comment_improvement]").val(
					'Write a comment here');
			jQuery("#rating_form textarea[name=comment_like]").val(
					'Write a comment here');
			jQuery(".rating-popup").show();
		});
jQuery(".star_5").live('click', function() {
	jQuery("#rating_form span").removeClass('selected');
	jQuery(".star_5").addClass('selected');
	jQuery("#rating_form input[name=rating_level]").val(5);
});
jQuery(".star_4").live('click', function() {
	jQuery("#rating_form span").removeClass('selected');
	jQuery(".star_4").addClass('selected');
	jQuery("#rating_form input[name=rating_level]").val(4);
});
jQuery(".star_3").live('click', function() {
	jQuery("#rating_form span").removeClass('selected');
	jQuery(".star_3").addClass('selected');
	jQuery("#rating_form input[name=rating_level]").val(3);
});
jQuery(".star_2").live('click', function() {
	jQuery("#rating_form span").removeClass('selected');
	jQuery(".star_2").addClass('selected');
	jQuery("#rating_form input[name=rating_level]").val(2);
});
jQuery(".star_1").live('click', function() {
	jQuery("#rating_form span").removeClass('selected');
	jQuery(".star_1").addClass('selected');
	jQuery("#rating_form input[name=rating_level]").val(1);
});
jQuery("#submit_rating")
		.live(
				'click',
				function() {
					var rating_level = jQuery(
							"#rating_form input[name=rating_level]").val();
					var comment_improvement = jQuery(
							"#rating_form textarea[name=comment_improvement]")
							.val();
					var comment_like = jQuery(
							"#rating_form textarea[name=comment_like]").val();
					if (rating_level == null || rating_level == '') {
						messagePopup("Please rate the game first.");
					} else {
						try{
							usersObjectUINFO = jQuery.parseJSON(zzGlobals.clientVars.UINFO);
							PDN = usersObjectUINFO.PDN;
						}catch(error){ 
							PDN = '';
						}
						 
						jQuery
								.post(
										baseUrl + '/help/ratezalerio',
										{
											'email' : userLoginId,
											'rating_level' : rating_level,
											'comment_improvement' : comment_improvement,
											'comment_like' : comment_like,
											'name' : PDN
										},
										function(data) {
											try {
												var obj = JSON.parse(data)
											} catch (error) {
											};
											if (obj.status == '1') {
												jQuery(
														"#rating_form input[name=rating_level]")
														.val('');
												jQuery(
														"#rating_form textarea[name=comment_improvement]")
														.val('');
												jQuery(
														"#rating_form textarea[name=comment_like]")
														.val('');
												jQuery("#rating_form span")
														.removeClass('selected');
												
												jQuery('.wait').remove();
												jQuery('.rating-popup').css('display','none');
												
												messagePopup(obj.msg);
											} else {
												messagePopup(obj.msg);
											}
										});
					}
				});
jQuery("#rating_form textarea[name=comment_improvement]").live('click',
		function() {
			if (jQuery(this).val() == "Write a comment here") {
				jQuery(this).val('')
			}
		});
jQuery("#rating_form textarea[name=comment_like]").live('click', function() {
	if (jQuery(this).val() == "Write a comment here") {
		jQuery(this).val('');
	}
});

jQuery("#rating_form textarea[name=comment_improvement]").live('focus',
		function() {
			if (jQuery(this).val() == "Write a comment here") {
				jQuery(this).val('')
			}
		});
jQuery("#rating_form textarea[name=comment_like]").live('focus', function() {
	if (jQuery(this).val() == "Write a comment here") {
		jQuery(this).val('');
	}
});

/** ** end ** */
// get the size of an object
sizeOfObj = function(obj) {
	var key, size;
	key = void 0;
	size = void 0;
	size = 0;
	key = null;
	for (key in obj) {
		if (obj.hasOwnProperty(key)) {
			size++;
		}
	}
	return size;
};
// this function will make the first letter of a string in uppercase

function capFirst(string) {
	return string.charAt(0).toUpperCase() + string.slice(1);
}

function acceptInvitation(gameId) {
	jQuery("#accept_decline_" + gameId).addClass("loader_small");
	txt = jQuery("#accept_decline_" + gameId).html();
	jQuery("#accept_decline_" + gameId).html(loader_small)
	jQuery.ajax({
		type : 'POST',
		url : baseUrl + "/user/acceptInvite",
		data : {
			'game_id' : gameId
		}
	}).done(
			function(data) {
				try {
					var gameseat_gameinst_id = gameId;
					if (typeof tutorial != 'undefined') {
						if (tutorial == true) {
							boxBlank = jQuery('.box-blank');
							boxBlank.text('');
							boxBlank.removeClass();
							boxBlank.addClass('box-blank box-black ');
						}
						tutorial = false
					}
					messagePopup(popupMSG.acceptInvite, gameChangeListener,
							gameseat_gameinst_id)
					try {
						$("right_hud_" + gameId).click({
							gameDetails : zzGlobals.msgVars.RH[gameId],
							id : gameId
						}, function(e) {
							// sound.playSelectButtonSound()
							window.createGameDetailsPopup(e.data.id);
						});
					} catch (err) {
						console.log('unable to bind click event ' + err)
					}

					jQuery("#accept_decline_" + gameId).removeClass(
							"loader_small");
					jQuery("#accept_decline_" + gameId).html('');
				} catch (err) {
				}
			}).error(function(data) {
		jQuery("#accept_decline_" + gameId).removeClass("loader_small");
		jQuery("#accept_decline_" + gameId).html(txt);
	});
}
function getOrdinal(intNum, includeNumber) {
	if (intNum == '' || intNum == ' ' || intNum == 'undefined'
			|| intNum == null)
		return '';
	return (includeNumber ? intNum : "")
			+ (((intNum = Math.abs(intNum) % 100) % 10 == 1 && intNum != 11) ? "st"
					: (intNum % 10 == 2 && intNum != 12) ? "nd"
							: (intNum % 10 == 3 && intNum != 13) ? "rd" : "th");

};


function rematchPastGames(ids, gameOption, gameId) {
	//loginUserFBId
	inviteId = ids;

	inviteId.splice($.inArray(loginUserFBId, inviteId),1);

		FB.ui({method: 'apprequests',
	        message: 'You are invited to play a game of zalerio',
	        to: inviteId
	  }, function(response){
	  	
	  	if(response ){

	  		var check = InviteFriends(ids, gameOption, gameId);
	  		// nothing to do									    		
	  	}else{
	  		
				jQuery('.wait').remove();
				jQuery('.show_popup').remove();
				messagePopup('Not enough players to create a game .Please select again and try.');
				
	  	}
			
	  });
	
	jQuery('.s_show_popup').css('display', 'none');
	return true;

}

jQuery('.newCarouselblanktile').live('click', function() {
	showFrndSelector();
})
try {
	jQuery('#showmehow').live('click', function(e) {
		tutorial = true
		jQuery('.zalerio_popup').css('display', 'none');
		require([ 'zalzero.tutorial' ], function(tutorial) {
			tutorial.launch();
		});
	})
} catch (err) {
}

jQuery(document).ready(function(){
	
	jQuery("#tos_link").click(function(event){
		event.preventDefault();
		
		$('body')
		.append(
				'<div class="wait"><div id="floatingBarsG">\
		<div class="blockG" id="rotateG_01">\
		</div>\
		<div class="blockG" id="rotateG_02">\
		</div>\
		<div class="blockG" id="rotateG_03">\
		</div>\
		<div class="blockG" id="rotateG_04">\
		</div>\
		<div class="blockG" id="rotateG_05">\
		</div>\
		<div class="blockG" id="rotateG_06">\
		</div>\
		<div class="blockG" id="rotateG_07">\
		</div>\
		<div class="blockG" id="rotateG_08">\
		</div>\
		</div></div>');
		
		
		$.ajax({
			url : siteUrl + "/zalerio/tos"
		}).done(function(data) {

			html = '<div class="show_popup zalerio_popup"> \
				<div id="" class="outer_base"> \
				<a href="#" id="close" onclick="jQuery(\'.wait\').remove(); jQuery(\'.show_popup\').remove(); return false;"></a> \
				<div style="padding-top:35px;"><div style="display: block; width: 426px; margin: 0 auto; overflow: hidden; background: white; padding: 25px; height: 345px; overflow: hidden; overflow-y: scroll;">'+data+'</div>\
				</div></div> \
				</div>';
			
			$('body').append(html);
		});
	})
	
	jQuery("#tos_privacy").click(function(event){
		event.preventDefault();
		
		$('body')
		.append(
				'<div class="wait"><div id="floatingBarsG">\
		<div class="blockG" id="rotateG_01">\
		</div>\
		<div class="blockG" id="rotateG_02">\
		</div>\
		<div class="blockG" id="rotateG_03">\
		</div>\
		<div class="blockG" id="rotateG_04">\
		</div>\
		<div class="blockG" id="rotateG_05">\
		</div>\
		<div class="blockG" id="rotateG_06">\
		</div>\
		<div class="blockG" id="rotateG_07">\
		</div>\
		<div class="blockG" id="rotateG_08">\
		</div>\
		</div></div>');
		
		$.ajax({
			url : siteUrl + "/zalerio/privacypolicy"
		}).done(function(data) {
			
			html = '<div class="show_popup zalerio_popup"> \
				<div id="" class="outer_base"> \
				<a href="#" id="close" onclick="jQuery(\'.wait\').remove(); jQuery(\'.show_popup\').remove(); return false;"></a> \
				<div style="padding-top:35px;"><div style="display: block; width: 426px; margin: 0 auto; overflow: hidden; background: white; padding: 25px; height: 345px; overflow: hidden; overflow-y: scroll;">'+data+'</div>\
				</div></div> \
				</div>';
			
			$('body').append(html);
		});
	})
	
})


jQuery(document).ready(function(){
	
	jQuery(window).load(function(){
		window.gameLoadStatus = true;
		if( window.gameUnionStatus == true ){
			jQuery('#active-screen').css('display','');
			jQuery('#lodder').fadeOut();
		}
	})
})