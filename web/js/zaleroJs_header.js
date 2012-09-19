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
var userLevelImgBig = new Array("white.png", "yellow.png", "orange.png", "green.png", "blue.png", "purple.png", "red.png", "brown.png", "black.png");
var playSound = true;
try{
	var audioBaseUrl = baseUrl.replace('/index.php','/');
	var otherbuttonSound = new Audio(audioBaseUrl + "/sound/otherbuttons.wav");  // other button sound file
	var selectbuttonSound = new Audio(audioBaseUrl + "/sound/select_button.wav");
	var popupapperence = new Audio(audioBaseUrl + "/sound/popupapperence.wav");
	var closebutton = new Audio(audioBaseUrl + "/sound/closebutton.wav");
	var tilepickup = new Audio(audioBaseUrl + "/sound/Tilepickup.wav"); // tile pickup sound file
	var titledrop = new Audio(baseUrl + "/sound/Tiledrop.wav");
	var playbutton = new Audio(baseUrl + "/sound/playbutton.wav");
}catch(err){}


var ofbizUser = {} ;
var flag_fbPullAuthorised = false ;
var oloFunctionRequests = {} ;
var InviteFriends;
var usersRecord;
jQuery ( function () {    
    facebookInit () ;
} ) ;

jQuery('#close').live('click',function(){
    if(playSound){
        closebutton.play(); // play sound on close button click
    }
    
})

jQuery('.closesound').live('click',function(){
    if(playSound){
        closebutton.play(); // play sound on close button click
    }
    
})
function facebookInit () {
    if(typeof(FB)!=="undefined"){
    //FB.init ( { appId : oloFBAppId , status : true , cookie : true , xfbml : true , oauth : true} ) ;

    FB.getLoginStatus ( function ( response ) {
            //console.log("FB.getLoginStatus() : [response:" + response + "]");
            //tempGlobalResponse["fbresponse"] = response;
                if ( response.status &&  response.status === "connected") {
                // logged in and connected user, someone you know
                        //alert("FB Connected!");
                        updateFBInfo ( response ) ;
                        //jQuery ( "#notConnectedWithFB_displayBtn" ).hide () ;
                } else {
                // no user session available, someone you dont know
                        //alert("FB NOT Connected!");
                       // jQuery ( "#notConnectedWithFB_displayBtn" ).show () ;
                }
            } ) ;
    }else{
            console.log("Facebook NOT initialized!");
    }
}

var oloFBAppId;
if ( document["domain"] === "localhost" ) {
    //oloFBAppId = "186820681347768" ;
    oloFBAppId = "358029084260694" ;
}else if(document["domain"] === "zalerio.com" || document["domain"] === "www.zalerio.com") {
    oloFBAppId = "264696926970805";
}else if(document["domain"] === "zl.mobicules.com") {
    oloFBAppId = "392619034110411";
}else {
    //oloFBAppId = "155589847826768";
    oloFBAppId = "264696926970805" ;
}

function facebookConnect () {
    if ( typeof ( FB ) !== "undefined" ) {
        //console.log("in facebook connect FB is undefined");
        //FB.init ( { appId : oloFBAppId , status : true , cookie : true, xfbml : true , oauth : true} ) ;
        FB.login ( loginSuccess_redirect , {scope : 'email,user_birthday,sms,publish_stream,read_friendlists,friends_online_presence'} ) ;
    }
}

function loginSuccess_redirect ( response ) {
    //console.log("loginSuccess_redirect()");
    if ( typeof ( response ) !== "undefined" ) {
       
        var fbUid = "" ;
        var flag_FBConnected = false ;
        
        if ( typeof ( response [ "session" ] ) !== "undefined" && typeof ( response [ "session" ] [ "status" ] ) !== "undefined" && response [ "session" ] [ "status" ] === "connected" ) {
            flag_FBConnected = true ;
            fbUid = response [ "session" ] [ "uid" ] ;
            //console.log('fbuid : ' + fbUid);
        } else if ( typeof ( response [ "status" ] ) !== "undefined" && response [ "status" ] === "connected" && response [ "authResponse"] && response [ "authResponse" ] [ "userID" ]) {
            flag_FBConnected = true ;
            fbUid = response [ "authResponse" ] [ "userID" ];
            //console.log('fbuid : ' + fbUid);
        } else if ( typeof ( response [ 2 ] ) !== "undefined" && typeof ( response [ 2 ] [ 0 ] ) !== "undefined" && typeof ( response [ 2 ] [ 0 ] [ "session" ] ) !== "undefined" ) {
            //alert("Step3");
            flag_FBConnected = true ;
            fbUid = response [ 2 ] [ 0 ] [ "session" ] [ "uid" ] ;
        }

        if ( flag_FBConnected ) {
            
            updateFBInfo ( response ) ;
            //var partyId = "" ;
            //dont know why this if block is used here
            //if ( typeof ( ofbizUser ) !== "undefined" && typeof ( ofbizUser [ "partyId" ] ) !== "undefined" ) {
                //partyId = ofbizUser [ "partyId" ] ;
            //}
            FB.api
            ( 
                {
                    method : 'fql.query' ,
                    query : 'SELECT uid,name,email,first_name,last_name,birthday_date,sex,pic_square FROM user WHERE uid = me()'
                } ,
                function ( response ) {
                
                    var fbUserData = {} ;
                    for ( var i in response [ 0 ] ) {
                            fbUserData [ i ] = response [ 0 ] [ i ] ;
                    }
                    jQuery.ajax ( {
                        type : 'POST' ,
                        url : baseUrl + "/user/verifyUser" ,
                        data : {'user_email' : fbUserData ["email"],'user_fbid' :fbUserData ["uid"]}
                    } ).done(function(data) {
                        if(data == 'true'){
                            jQuery.ajax ( {
                                type : 'POST',
                                url : baseUrl + "/user/fbCheckLoginAjax" ,
                                data : {'user_fbid' : fbUid , 'user_email' : fbUserData [ "email" ]} ,
                                success : fbLoginSuccessRedirect,
                                dataType : 'json'
                            } ) ;
                        }else{
                            alert('Hey! You are not authorized to play this game.');
                        }
                    });                    
                }
            );

        }
    }
}



function updateFBInfo ( response ) {
    if ( typeof ( response ) !== "undefined" ) {
        if ( ( response.status && response.status === "connected" && response.authResponse && response.authResponse.userID && response.authResponse.accessToken )){
            ofbizUser["$user_fbid"] = response [ "authResponse" ] [ "userID" ] ;
            ofbizUser["accessToken"] = response [ "authResponse" ] [ "accessToken" ] ;
            ofbizUser["fbStatus"] = 1 ;
            serveFBRequests () ;
            // now hide the facebook conect button
            //jQuery ( "#notConnectedWithFB_displayBtn" ).hide () ;
        } else if ( typeof ( response[2] ) !== "undefined" && typeof ( response[2][0] ) !== "undefined" && typeof ( response[2][0]["status"] ) !== "undefined" ) {
            if ( response[2][0]["status"] === "notConnected" ) {
                    return -1 ;
            }
        }
    }		            
    return 0 ;
}


function serveFBRequests () {
    oloFunctionRequests[ "muteX" ] = 0 ;
    var ctr = 0 ;
    for ( var i in oloFunctionRequests ) {
        request = oloFunctionRequests [ i ] ;
        if ( request !== 0 ) {
            //alert(i);
            if ( request === 1 ) {//request w/o parameters
                window [ i ] () ;
            } else if ( request === 2 ) { //request with parameters
                //alert("hi");
                //window [ i ] () ;//
                eval ( i ) ;
                //alert("hi2");
            }
            oloFunctionRequests [ i ] = 0 ;
        }
    }
}



function fbLoginSuccessRedirect ( data ) {
    
    if ( typeof ( data ) !== "undefined" && typeof ( data [ "userValid" ] ) !== "undefined" && data [ "userValid" ] === true ) {
        /*
        * User is authorized on facebook and is a valid OLO-ofbiz user
        * Therefore, log him in
        */
        //alert("Loggin now!");
        window.location = baseUrl + "/site/fbLogin?user_email=" + data['user_mail'] + "&user_fbid=" + data['user_fbid']
    } else {
        /*
        * User is authorized on facebook but is NOT a valid OLO-Ofbiz user
        * Therefore give him the registration form 
        */
        //alert("opening registration box");
        flag_fbPullAuthorised = true ;
        //the following two functions will not be needed once the page sign up process is finalised instead of popup registration
        //openRegistrationFormPopup () ;
        //loadRegistrationFormFields () ;
        //filling the signup page form field
        loadSignUpFormFields();

        //No need for redirection
        /*
        var currentUrl = window.location.pathname;
        var redirectUrl = ofbizUrl + "signUp";
        console.log("currentURL : " + currentUrl);
        console.log("redirectURL : " + redirectUrl);
        if(currentUrl === redirectUrl){
                console.log("on the signup page no need to redirect");
                loadSignUpFormFields();

        }else{
                console.log("redirect it to signup page");
                //redirect to signup page and on that page check if flag_fbPullAuthorised = true than call facebookCnnect again
                window.location = redirectUrl + "?flag_fbPullAuthorised=true";
        }
        */
        //window.location = ofbizUrl + "signUp";

    }
}


function loadSignUpFormFields(){
	//alert("loading fields");
	//select_dropdown_Field_index ( "USER_DOB_YEAR" , "1990" ) ;
	//alert("Hi");
	fbPullData () ;
}

function fbPullData () {
	//alert("fbPullData invoked() 1");
	if ( flag_fbPullAuthorised === true ) {
		//alert("fbPullData invoked() 2");
		FB.api ( 
                        {
                                method : 'fql.query' ,
                                query : 'SELECT uid,name,email,first_name,last_name,birthday_date,sex,pic_square FROM user WHERE uid = me()'
                        } ,
                        function ( response ) {
                                var fbUserData = {} ;
                                for ( var i in response [ 0 ] ) {
                                        fbUserData [ i ] = response [ 0 ] [ i ] ;
                                }
                                autoFillForm ( fbUserData ) ;
                        }
		) ;
		flag_fbPullAuthorised = false ;
	}
}

function autoFillForm ( fbData ) {
	//alert("registering the form 1");
	var formObj = document.createElement("form");
	formObj.name = "register-user-form";
	formObj.method = "POST";
	formObj.action = baseUrl + "/user/createFbUser" ;
        var model="Zzuser";
    if (formObj && formObj.action && formObj.method) {
		if ( typeof ( formObj ) !== "undefined" && typeof ( fbData ) !== "undefined" ) {
			//console.log(createDocInputElement("USER_FIRST_NAME",fbData [ "first_name" ]));
                        var name = fbData [ "first_name" ]+' '+fbData [ "last_name" ];
			formObj.appendChild ( createDocInputElement(model + "[user_name]",name) );
			//formObj.appendChild ( createDocInputElement("USER_LAST_NAME",fbData [ "last_name" ]) );
			formObj.appendChild ( createDocInputElement(model + "[user_email]",fbData [ "email" ]) );
                        formObj.appendChild ( createDocInputElement(model + "[user_handle]",fbData [ "email" ]) );
			formObj.appendChild ( createDocInputElement(model + "[user_fname]",fbData [ "first_name" ]) );
                        formObj.appendChild ( createDocInputElement(model + "[user_lname]",fbData [ "last_name" ]) );
			/*
			if ( typeof ( fbData [ "birthday_date" ] ) !== "undefined" ) {
				var birthdate = fbData [ "birthday_date" ].split ( "/" ) ;
				birthdate [ 0 ] = ( ++birthdate [ 0 ] - 1 ) ; //Month
				birthdate [ 1 ] = ( ++birthdate [ 1 ] - 1 ) ; //Year
				
				formObj.appendChild ( createDocInputElement( "USER_BIRTHDATE" , birthdate [ 2 ] + "-" + pad2 ( birthdate [ 0 ] ) + "-" + pad2( birthdate [ 1 ] ) ) );
				//setFormFieldValue ( formObj.elements [ "USER_DOB_YEAR" ] , birthdate [ 2 ] ) ;
				//setFormFieldValue ( formObj.elements [ "USER_DOB_DATE" ] , birthdate [ 1 ] ) ;
			}
                        *
                        */
                       
			formObj.appendChild ( createDocInputElement (model + "[user_fbid]",fbData [ "uid" ]) );
			//enableRegFormForFacebook () ;
			document.body.appendChild(formObj);
			//Submit the form
			formObj.submit();			
		}
	}
	//createUserFromFacebook
}
function createDocInputElement(elName,elValue){
	var el = document.createElement("input");
	el.type = "hidden";
	el.name = elName;
	el.value = elValue;
	return el;
}



/**
 *  User created game
 *  written by pankaj anupam 
 */
var inviteUserId = new Array();
var siteUrl = baseUrl;//'http://localhost/zalzero';
function showFrndSelector()
{
	if(typeof tutorial != 'undefined'){
		if(tutorial == true ){
			boxBlank = jQuery('.box-blank');
			boxBlank.text('');
			boxBlank.removeClass();
			boxBlank.addClass('box-blank box-black ');		
		}
    	tutorial = false
	}
	
    $('body').append('<div class="wait"><div id="floatingBarsG">\
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
        if ( flag_fbPullAuthorised === true ) {
                //alert("fbPullData invoked() 2");
                FB.api (
                        {
                                method : 'fql.query' ,
                                query : "SELECT uid, name, pic_square, online_presence, username FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) order by name"              
                        } ,
                        function ( response ) {
                                fbUserData = {} ;
                                for ( var i in response  ) {
                                        fbUserData [ i ] = response [ i ] ;
                                }
                                //console.log(fbUserData) ;// alert('check');
                                var html='';
                                for(var j in fbUserData){
                                       html += '<div class="rep"><img src="'+fbUserData[j].pic_square+'" /><div>'+ fbUserData[j].name + '<br/></div>';
                                       html +='<input type="button" style="display:none" value="'+fbUserData[j].uid+'" class="status" />';
				       html +='<span class="'+fbUserData[j].online_presence+'">'+fbUserData[j].online_presence+'</span>';
				       html +='<a class="select_button right">Select</a></div><div class="line"></div>';
                                }
                               // alert(htmlt);
                                $.ajax({
                                        type:'POST',
                                        url: siteUrl+"/user/getFriend"
                                        //data:{'fbUserData' : fbUserData}
                                }).done(function(data) { //alert(data);
                                        $('body').append(data);
                                        $("#floatingBarsG").css('display','none');
                                        $('.friendlist').html(html);
                                        if(playSound){
                                            popupapperence.play(); // popup apperence sound
                                        }
                                        

                                });
                        }
                ) ;
                flag_fbPullAuthorised = false ;
} 
/*       $.ajax({
            type:'POST',
            url: siteUrl+"/user/getFriend"
       }).done(function(data) { 
            $('body').append(data);
       });
*/
}

jQuery(function($){
    $('#startButton').click(function(){
        if(playSound){
            otherbuttonSound.play();
        }
        try{
        	jQuery('.gameInfoPanel').css('display','block');
        	jQuery('.gameScore').css('display','block');
        	jQuery('#tutorial-accordion').css('display','none');
        }catch(err){}
        showFrndSelector();
    }); 

    $('#findfriend').live('keyup',function(){
   	 
	var findFriend = $('#findfriend').val().toLowerCase();
	$('.rep').css('display','none');
	$('.line').css('display','none');
        
	$('.rep div').each(function(i) {
		if($(this).text().toLowerCase().indexOf(findFriend) != -1) {
		     $(this).parent('.rep').css('display','block');
		     $(this).parent('.rep').next().css('display','block');
		}

	});
	//$('#findfriend').val('');
    }); 

  $('#show_all_friends').live('click',function(){
	$('.rep').css('display','block');
	$('.line').css('display','block');
        $('#findfriend').val('');
	
 });
    
    $('.select_button').live("click",function(){
        if($(this).hasClass('selected')){            
            $(this).removeClass('selected');
            $(this).parent('.rep').find('.countme').removeClass('countme');
        }else{
            if(playSound){
                selectbuttonSound.play(); // select friend sound
            }
            $(this).addClass('selected');
            $(this).parent('.rep').find('input[type=button]').addClass('countme');
        }
    });
    
    InviteFriends = function(id,gameOption,gameId){       
       var friends_id = id;
       var fbUserData = {} ;
       var flag = false;
		if(typeof(gameOption) == 'undefined'){
	                gameOption = 'Create';
	    }
		if(typeof(gameId) == 'undefined'){
			gameId = 0;
		}
		
       
       todayDate = new Date(); // get the current date

        FB.api( 
                {
                    method : 'fql.query' ,
                    query : 'SELECT uid, name, first_name, last_name,pic_square, online_presence, username FROM user WHERE uid in('+friends_id+')'
                } ,
                function ( response ) {
                    fbUserData = response;                    
                        $.ajax({
                            type:'POST',
                            url: siteUrl+"/gameinst/createnewgame",
                            data:{
                            	'gameOption':gameOption,
                                'fbUserData':fbUserData,
                                'gameId':gameId
			    }
                    }).done(function(data) {
                    	// change game
                        var gameseat_gameinst_id = data;
                        messagePopup(popupMSG.gameCreate,gameChangeListener,gameseat_gameinst_id)
                        jQuery('.wait').remove();jQuery('.show_popup').remove();
                        flag = true;
                        //window.location = "http://"+window.location.hostname+window.location.pathname+'?gameinst_id='+gameseat_gameinst_id; 
                    }); 
                }
        );
             return flag;
    }
    
    $('#sendrinvite').live("click",function(){
        var id = new Array();
        $('.friendlist .rep .status').each(function(){
                id.push($(this).val());
        });
        var arr = []
        while(arr.length < 5 && arr.length < id.length){
            var randomnumber=Math.ceil(Math.random()* id.length-1 )
            var found=false;
            for(var i=0;i<arr.length;i++){
                if(arr[i]==id[randomnumber]){found=true;break}
            }
            if(!found)arr[arr.length]=id[randomnumber];
        }
        InviteFriends(arr);
    });
   
    $('#sendinvite').live("click",function(){
        if(playSound){
            otherbuttonSound.play();
        }
        var id = new Array();
        $('.countme').each(function(){
                id.push($(this).val());
        });
        if(id.length == 0 ){
        	messagePopup('Please select a friend.');
        }else if(id.length > 4){
        	messagePopup('Please select max upto 4 friends.');
        }else{
        	if(jQuery('#sendinvite').attr('value') == 'Sending...'){
    			return;
    		}
        	
    		jQuery('.footerbutton').append('<div id="floatingBarsGs">\
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
    
    		jQuery('#sendinvite').attr('value','Sending...');
    		jQuery('#sendinvite').css('cursor','default');
            InviteFriends(id);
        }        
    });
        
    $('.fblist .rep').live("click",function(){
        if($(this).hasClass('selected')){
            $(this).removeClass('selected');
            $(this).find('.countme').removeClass('countme');
        }else{
            $(this).addClass('selected');
            $(this).find('input[type=hidden]').addClass('countme');
        }
    });
    
    
    // Perfinary ajax call
    $('#sendFbInvite').live("click",function(){
        var id = new Array();
        $('.fblist .rep .countme').each(function(){
                id.push($(this).val());
        });
        if(id.length<1){
        	messagePopup("Please Select a Friend");
            return;
        }
        var selectedUser = {} ;
        var count = 0;
        for (i in fbUserData) {
            if (fbUserData.hasOwnProperty(i)) {
                count++;
            }
        }
        for ( var i =0,j=0 ; i < count ; i++ ){
            if(jQuery.inArray(fbUserData[i].uid, id) != -1){
                selectedUser[j++] = fbUserData[i];
                fb_publish(fbUserData[i].uid) // post on user wall/timeline
            }
        }
        
        //console.log(selectedUser) ;
        $.ajax({
              type:'POST',
              url: siteUrl+"/user/getFbFriendsList",
              data:{'fbUserData' : selectedUser}
        }).done(function(data) {
            //console.log(data);console.log('data');
            $('.show_popup').remove();
            $('.wait').remove();
        });
    });
    function fb_publish(friendId,gameId) {
       	    /*if(opts == 'undefined') {*/ 
             var opts = {
                message : 'Find IT game Invitation',
                name : 'Find IT',
                link : "http://"+window.location.hostname+"/?invited_game_id="+gameId,
                description : 'Join Find IT to play game'
            };
	   /* } */
	   $.holdReady(true);
            FB.api('/'+friendId+'/feed', 'post', opts, function(response)
            {
                if (!response || response.error)
                {$.holdReady(false);
			//console.log(response); 
		} else {
			$.holdReady(false);
		}
            }); 
    }
    jQuery("#bottomHUDbuttons-acc").css('display','none');
    /************* this wil show stats popup*******************/
    var showStats = function(){
               // define  funtion
               // shows my games data
               //hide all popup blocks
               jQuery(".popup-block").hide();
               // show only popup-stats block
               jQuery("#popup-stats").show();
               // remove my level and my friends div
               jQuery("#mylevel_stats").hide();
               jQuery("#myfriends_stats").remove();
               jQuery("#mygames_stats").show();
               //jQuery("#active-screen").append("<div class='wait'></div>");
                 jQuery(".s_show_popup").show();
   }
   // this function will show data of My Level tab in stats screen
   var showMyLevel = function(){
       // remove my friends and level data
       jQuery("#myfriends_stats").hide();
      
       
       //hide my games data
       jQuery("#mygames_stats").hide();
       // get data for my level
           jQuery("#mylevel_stats").show();
           jQuery("#learn-performance-level").click(function(){if(playSound){otherbuttonSound.play();}jQuery('.performance_level').show();});
           jQuery("#close-performance-level").click(function(){jQuery('.performance_level').hide();});
      
   }
    var showHelp = function(){
        
                 jQuery(".popup-block").hide();
                 jQuery("#help-tutorial").show();
                 jQuery("#popup-help").show();
                 jQuery(".s_show_popup").show();
                 // this will add custom scroller 
                 /**** for scroll bar ******/
                jQuery(function()
			{      
				jQuery('.rules-container').jScrollPane({showArrows: true});

			});
                        
                  /**** end ***/ 
                  
     
                        jQuery("#tutorial_startgame").click(function(event){event.preventDefault();
                            var temp_h = jQuery("#tutorial_0").height();
                            jQuery('#rules-container .jspContainer .jspPane').css('top',"-"+temp_h+"px");});
                        
                  jQuery("#tutorial_taketurn").click(function(event){event.preventDefault();
                      var temp_h = jQuery("#tutorial_0").height()+jQuery("#tutorial_1").height();
                      jQuery('#rules-container .jspContainer .jspPane').css('top',"-"+temp_h+"px");});
                  
                  jQuery("#tutorial_scoring").click(function(event){event.preventDefault();
                      var temp_h = jQuery("#tutorial_0").height()+jQuery("#tutorial_1").height()+jQuery("#tutorial_2").height();
                      jQuery('#rules-container .jspContainer .jspPane').css('top',"-"+temp_h+"px");});
                  
                  jQuery("#tutorial_jokers").click(function(event){event.preventDefault();
                      var temp_h = jQuery("#tutorial_0").height()+jQuery("#tutorial_1").height()+jQuery("#tutorial_2").height()+jQuery("#tutorial_3").height();
                      jQuery('#rules-container .jspContainer .jspPane').css('top',"-"+temp_h+"px");});
        
    }
    var getHelpContactUsForm = function()
    {
        jQuery.post(baseUrl+'/help/contactus',{},function(data){
            
            if(data)
                {
                    jQuery("#help-contactus").html(data);
                    jQuery(function() {
                        jQuery('select.styled').customStyle();
                        });
                }
        });
    }
    
    // show stats
    jQuery("#bottomHUDbuttons-stats").click(function(){
        if(playSound){
            otherbuttonSound.play();
        }
        if(jQuery(".s_show_popup").css('display')=='none')
            {
                showStats();
            }
            else{
                    if(jQuery("#popup-stats").css('display') == 'none'){
                            showStats();
                    }else{jQuery(".s_show_popup").hide();}
        }
    });
       /******* show help pop up*********/
     
     jQuery(".rightHUDPanel .gameMenu ul li.helpButton").click(function(){
         if(playSound){
            otherbuttonSound.play();
         }
         if(jQuery(".s_show_popup").css('display')=='none')
             {
                 showHelp();                 
             }else
                 {
                     if(jQuery("#popup-help").css('display')== 'none'){
                             showHelp();
                     }
                     else{
                                 jQuery(".s_show_popup").hide();
                     }                     
                 }
     });
     
     /****** end*********/
     
      /************** toggle between help tutorials and contact us ******/
     
     jQuery("#a-help-tutorial").click(function(){
         if(playSound){
            otherbuttonSound.play();
         }
         jQuery(this).addClass('active');
         jQuery("#a-help-contactus").removeClass('active');
        jQuery("#help-contactus").hide();
        jQuery("#help-tutorial").show();
     });
     
     
     jQuery("#a-help-contactus").click(function(){
         if(playSound){
            otherbuttonSound.play();
         }
         jQuery(this).addClass('active');
         jQuery("#a-help-tutorial").removeClass('active');
         getHelpContactUsForm();
         
        jQuery("#help-contactus").show();
        jQuery("#help-tutorial").hide();
     });     
     //toggle between stats tabs
     jQuery(".statslink").click(function(data){
         jQuery(".statslink").removeClass('active');
         jQuery(this).addClass('active');
         var id = jQuery(this).attr('id');
         if(id=='mygames_a'){if(playSound){otherbuttonSound.play();}showStats();}
         if(id=='mylevel_a'){if(playSound){otherbuttonSound.play();}showMyLevel();}
     });
      jQuery("#upload_help_contactus").live('click',function(){
          jQuery("#help_contact_form input,select,textarea,submit").removeClass('error');;
          var first_name = jQuery("#help_contact_form input[name=first_name]").val();
          var last_name  = jQuery("#help_contact_form input[name=last_name]").val();
          var email = jQuery("#help_contact_form input[name=email]").val();
          var subject  = jQuery("#help_contact_form select[name=subject]").val();
          var body = jQuery("#help_contact_form textarea[name=body]").val();
          var verifyCode = jQuery("#help_contact_form input[name=verifyCode]").val();
          var valid ='1';
          if(first_name == "" || first_name == null || first_name.length > 50){jQuery("#help_contact_form input[name=first_name]").addClass('error');valid = '0';}
          if(last_name.length > 50){jQuery("#help_contact_form input[name=last_name]").addClass('error');valid = '0';}
          if(!valid_email(email) || email.length > 100){jQuery("#help_contact_form input[name=email]").addClass('error');valid = '0';}
          if(subject == "" || subject == null || subject.length > 50){jQuery("#help_contact_form select[name=subject]").addClass('error');valid = '0';}
          if(body == "" || body == null || body.length > 2000){jQuery("#help_contact_form textarea[name=body]").addClass('error');valid = '0';}
          if(verifyCode == "" || verifyCode == null){jQuery("#help_contact_form input[name=verifyCode]").addClass('error');valid = '0';}
          if(valid ==1){
          jQuery.post(baseUrl+'/help/contactus',{'first_name':first_name,'last_name':last_name,'email':email,'subject':subject,'body':body,'verifyCode':verifyCode},function(data){
              var obj = JSON.parse(data);
              if(obj.status =='1')
                  {
                        jQuery("#help_contact_form input[name=first_name]").val('').removeClass('error');
                        jQuery("#help_contact_form input[name=last_name]").val('').removeClass('error');
                         jQuery("#help_contact_form input[name=email]").val('').removeClass('error');
                         jQuery("#help_contact_form textarea[name=body]").val('').removeClass('error');
                        jQuery("#help_contact_form input[name=verifyCode]").val('').removeClass('error');
                   
                  }
                  messagePopup(obj.msg);
          }); 
          }
      });
     jQuery("#help_contact_form input,select,textarea,submit").live('click',function(){jQuery("#msg-submit-popup").remove();});     
     mousedownFunction = function(e){          
          if($(this).attr('draggable') === 'false') return;
          if(playSound){
            tilepickup.play(); // play sound on tile pickup
          }
          div = $('<div></div>')
          for(var i = 0,  attributes = $(this).get(0).attributes; i < attributes.length; i++) {
                div.attr(attributes[i].name, attributes[i].value);
          }
          $(div).addClass("draggableBetsClick");
          $('body').append(div);
          $('.draggableBetsClick').css({'position':'absolute','left':e.pageX-15,'top':e.pageY-15}); 
      };
      
      $('.box-newBet').live('mousedown',mousedownFunction);
      $('.draggableBets').live('mousedown',mousedownFunction); 
     
      $('.draggableBetsClick').live('mousemove',function(e){
         $('.draggableBetsClick').remove();
      })
      
      $('.dismiss').live('click',function(){
          if(playSound){
            otherbuttonSound.play();
          }
      });      
      var countImg = 1;
      var lastImg = 1;
      var url;

      // Mute Unmute
      jQuery(".audioButton a").click(function(){
          if(playSound){
              playSound = false;
              $(this).parent('.audioButton').addClass('mute');
          }else{
              playSound = true;
              $(this).parent('.audioButton').removeClass('mute');
          }
      });              
}) //jQuery





// added for stat caraousal



$.fn.infiniteCarousel = function () {

    function repeat(str, num) {
        return new Array( num + 1 ).join( str );
    }
  
    return this.each(function () {
        var $wrapper = $('> div', this).css('overflow', 'hidden'),
            $slider = $wrapper.find('> ul'),
            $items = $slider.find('> li'),
            $single = $items.filter(':first'),
            
            singleWidth = $single.outerWidth(), 
            visible = parseInt($wrapper.innerWidth() / singleWidth), // note: doesn't include padding or border
            currentPage = 1,
            pages = Math.ceil($items.length / visible);            
			//console.log($wrapper.innerWidth() / singleWidth);

        // 1. Pad so that 'visible' number will always be seen, otherwise create empty items
        if (($items.length % visible) != 0) {
            $slider.append(repeat('<li class="empty" />', visible - ($items.length % visible)));
            $items = $slider.find('> li');
        }

        // 2. Top and tail the list with 'visible' number of items, top has the last section, and tail has the first
        $items.filter(':first').before($items.slice(- visible).clone().addClass('cloned'));
        $items.filter(':last').after($items.slice(0, visible).clone().addClass('cloned'));
        $items = $slider.find('> li'); // reselect
        
        // 3. Set the left position to the first 'real' item
        $wrapper.scrollLeft(singleWidth * visible);
        
        // 4. paging function
        function gotoPage(page) {
            var dir = page < currentPage ? -1 : 1,
                n = Math.abs(currentPage - page),
                left = singleWidth * dir * visible * n;
            
            $wrapper.filter(':not(:animated)').animate({
                scrollLeft : '+=' + left
            }, 100, function () {
                if (page == 0) {
                    $wrapper.scrollLeft(singleWidth * visible * pages);
                    page = pages;
                } else if (page > pages) {
                    $wrapper.scrollLeft(singleWidth * visible);
                    // reset back to start position
                    page = 1;
                } 

                currentPage = page;
            });                
            
            return false;
        }
        
        $wrapper.after('<a class="arrow back carousel_link">&lt;</a><a class="arrow forward carousel_link">&gt;</a>');
        
        // 5. Bind to the forward and back buttons
        $('a.back', this).click(function () {
            return gotoPage(currentPage - 1);                
        });
        
        $('a.forward', this).click(function () {
            return gotoPage(currentPage + 1);
        });
        
        // create a public interface to move to a specific page
        $(this).bind('goto', function (event, page) {
            gotoPage(page);
        });
    });  
};

jQuery(document).ready(function(){
    // when user does not land in any game then show friend selector
    if(typeof gameInstId != 'undefined' && typeof tutorial != 'undefined'  && tutorial != true ){
            if (gameInstId =='0'){
                setTimeout(showFrndSelector,3000);  
            }
        }
        /*** when some one clicks on cheat sheet button cheat sheet **/
        jQuery("#cheat-sheet").click(function()
        {  
            if(playSound){otherbuttonSound.play();}
            if(jQuery(".popup-cheatsheet").css('display') =='none')
            {
                jQuery(".popup-cheatsheet").show();
                jQuery(".friendChallenge").addClass("back_to_the_game");
                jQuery('.popup-cheatsheet').jScrollPane({showArrows: true})		
                jQuery("body").click(function(){
                if(jQuery(".popup-cheatsheet").css('display') =='block')
                {   
                   if(playSound){
                        closebutton.play(); // play sound on close button click
                    } 
                   jQuery(".popup-cheatsheet").hide();
                   jQuery(".friendChallenge").removeClass("back_to_the_game");
                }
            
            });
            jQuery(".popup-cheatsheet").click(function(e){e.stopPropagation();});
            jQuery(".friendChallenge").click(function(e){e.stopPropagation();});
        }
        else{ 
        	jQuery(".popup-cheatsheet").hide();
        	jQuery(".friendChallenge").removeClass("back_to_the_game");
        }
     
        });
});

/****for select box (contact us)****/
(function($){
    $.fn.extend({
    customStyle : function(options) {
        if(!$.browser.msie || ($.browser.msie&&$.browser.version>6)) {
            return this.each(function() {
                var currentSelected = $(this).find(':selected');
                $(this).after('<span class="customStyleSelectBox"><span class="customStyleSelectBoxInner">'+currentSelected.text()+'</span></span>').css({position:'absolute', opacity:0,fontSize:$(this).next().css('font-size','15px')});
                var selectBoxSpan = $(this).next();
                var selectBoxWidth = parseInt($(this).width()) - parseInt(selectBoxSpan.css('padding-left')) -parseInt(selectBoxSpan.css('padding-right'));            
                var selectBoxSpanInner = selectBoxSpan.find(':first-child');
                selectBoxSpan.css({display:'inline-block'});
                selectBoxSpanInner.css({width:selectBoxWidth, display:'inline-block'});
                var selectBoxHeight = parseInt(selectBoxSpan.height()) + parseInt(selectBoxSpan.css('padding-top')) + parseInt(selectBoxSpan.css('padding-bottom'));
                $(this).height(selectBoxHeight).change(function() {
                    selectBoxSpanInner.text($(this).find(':selected').text()).parent().addClass('changed');
                });
         });
        }
    }
    });
})(jQuery);
/**** end ******/
/*** name and email validation ****/
function valid_email(email)
{
    email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if(email_regex.test(email)) return true;
    return false;
}
/***** end *****/  

/*** submit rating ****/
jQuery("#winnerscreen #close").live('click',function(){
    jQuery("#rating_form input[name=rating_level]").val('');
    jQuery("#rating_form textarea[name=comment_improvement]").val('Write a comment here');
    jQuery("#rating_form textarea[name=comment_like]").val('Write a comment here');
    jQuery(".rating-popup").show();
});
jQuery(".star_5").live('click',function(){jQuery("#rating_form span").removeClass('selected');jQuery(".star_5").addClass('selected');jQuery("#rating_form input[name=rating_level]").val(5);});
jQuery(".star_4").live('click',function(){jQuery("#rating_form span").removeClass('selected');jQuery(".star_4").addClass('selected');jQuery("#rating_form input[name=rating_level]").val(4);});
jQuery(".star_3").live('click',function(){jQuery("#rating_form span").removeClass('selected');jQuery(".star_3").addClass('selected');jQuery("#rating_form input[name=rating_level]").val(3);});
jQuery(".star_2").live('click',function(){jQuery("#rating_form span").removeClass('selected');jQuery(".star_2").addClass('selected');jQuery("#rating_form input[name=rating_level]").val(2);});
jQuery(".star_1").live('click',function(){jQuery("#rating_form span").removeClass('selected');jQuery(".star_1").addClass('selected');jQuery("#rating_form input[name=rating_level]").val(1);});
jQuery("#submit_rating").live('click',function(){
          var rating_level = jQuery("#rating_form input[name=rating_level]").val();
          var comment_improvement  = jQuery("#rating_form textarea[name=comment_improvement]").val();
          var comment_like = jQuery("#rating_form textarea[name=comment_like]").val();          
          if(rating_level == null || rating_level == '')
           {
               messagePopup("Please rate the game first.");
           }
           else
           {    
          jQuery.post(baseUrl+'/help/ratezalerio',{'email':userLoginId,'rating_level':rating_level,'comment_improvement':comment_improvement,'comment_like':comment_like,'name':GB_UINFO.PFN},function(data){
              var obj = JSON.parse(data);
              if(obj.status =='1')
                  {
                        jQuery("#rating_form input[name=rating_level]").val('');
                        jQuery("#rating_form textarea[name=comment_improvement]").val('');
                         jQuery("#rating_form textarea[name=comment_like]").val('');
                         jQuery("#rating_form span").removeClass('selected');
                        messagePopup(obj.msg);
                  }
                  else
                      {
                        messagePopup(obj.msg);
                      }
                  
            }); 
         }
      });
      jQuery("#rating_form textarea[name=comment_improvement]").live('click',function(){
          if(jQuery(this).val()=="Write a comment here"){jQuery(this).val('')}
      });
      jQuery("#rating_form textarea[name=comment_like]").live('click',function(){
          if(jQuery(this).val()=="Write a comment here"){jQuery(this).val('');}
      });
 /**** end ***/
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

	function capFirst(string)
	{
	    return string.charAt(0).toUpperCase() + string.slice(1);
	}
try{
	// global var which will contain user info
	var GB_UINFO = {};
	GB_UINFO.PFN = "";
}catch(err){}

/***** users record ***/
/**this function updates the stats popup - mygames and mylevel */
function usersRecord(gameRecords)
{   
   
   try
   {
    var is_apg = true;
    
  
    // APG hols the data about all past games    
    if(gameRecords.APG == 'ALL_PAST_GAME' || gameRecords.APG =='' || gameRecords.APG == null || typeof(gameRecords.APG) == 'undefined')
        {
            var APG = {};is_apg = false;
        }
        else
        {
            var APG = gameRecords.APG;
        }
   if (typeof(gameRecords.UINFO) == 'undefined' ){
       return;
   }
   // this variable holds user info     
   var UINFO = gameRecords.UINFO;
   GB_UINFO = UINFO;
    if ( typeof(UINFO.PL) == 'undefined' || parseInt(UINFO.PL) == 0){
            UINFO.PL = 1;
    }

   UINFO.user_pic = "https://graph.facebook.com/"+UINFO.PFB+ "/picture?type=square";
   // current user id
   var cuid = UINFO.UI;
   //this array will hold all the data about past games and user info
   var pastGames = {'won':{},'lost':{},'usersInfo':{}};
    
   var cm_fbids = "";
   var gssCount = 0; 
   }
   catch(err)
   {
       if(isDevEnvironment === true)
       {
           //console.log('building past games data',err);
       }
       return;
   }
   
  try
  {
    for(i in APG)
        {
            // here i corresponds to gameid
            // gameData will hold the about a particular game
            var gameData = APG[i];
            for(j in gameData)
                {
                    // if key equals PLRS, then corresponding value array will hold the data about players 
                    if(j == 'PLRS')
                        {
                            var PLRS = gameData[j];
                            var has_won = 0;
                            user_arr = {};
                            for(seatid in PLRS)
                                {
                                    pldata = PLRS[seatid];
                                    if(pldata['UI'] == cuid && pldata['PR'] == 1){has_won = 1;}
                                    user_arr[pldata['UI']] = {};
                                    user_arr[pldata['UI']]['fbid']= pldata['PFB'];
                                    user_arr[pldata['UI']]['pre']= pldata['PRE'];
                                    user_arr[pldata['UI']]['gss']= pldata['GSS'];
                                    pastGames['usersInfo'][pldata['UI']] = {};
                                    pastGames['usersInfo'][pldata['UI']]['name'] = pldata['PDN'];
                                    pastGames['usersInfo'][pldata['UI']]['fbid'] = pldata['PFB'];
                                    pastGames['usersInfo'][pldata['UI']]['gss'] = pldata['GSS'];
                                    pastGames[i] = {};
                                    pastGames[i]['user_arr'] = user_arr;
                                }
                                if(has_won == 1)
                                    {
                                        pastGames[i]['won'] = 1; 
                                    }
                                    else
                                        {
                                            pastGames[i]['won'] = 0; 
                                        }
                                
                        }
                    
                }
        }
  }
  catch(err)
  {
      if(isDevEnvironment === true)
       {
                      //console.log('building past games data',err);
       }
  }
          /***** RH will contain active ganmes data *****/
          try
          {
            if(typeof(gameRecords.RH) == 'object')
            {
               jQuery.each(gameRecords.RH,
                            function(gameId,gameData)
                            { 
                              jQuery("#active_rh_"+gameId).remove();  
                              if(gameData.GS != '0')
                              { 
                               var div_rip  = document.createElement("div");
                               div_rip.className = "rip";
                               div_rip.setAttribute('id','active_rh_'+gameId);
                               var div_caro  = document.createElement("div");
                               div_caro.className = "game_stats_user infiniteCarousel";
                               
                               
                               var div_wrapper  = document.createElement("div");
                               div_wrapper.className = "wrapper";
                               
                               var ul  = document.createElement("ul");
                               
                                
                                var pl_rank = '';
                                jQuery.each(gameData.PLRS,function(index,eachPL)
                                    { 
                                        var li  = document.createElement("li");
                                        var img  = document.createElement("img");
                                        var span  = document.createElement("span");
										var a = document.createElement("a");
                                        span.innerHTML=eachPL.PDN;
                                        img.setAttribute('width','31');
                                        img.setAttribute('height','31');
                                        img.setAttribute('src',"https://graph.facebook.com/"+ eachPL.PFB+ "/picture?type=square");
										a.setAttribute('href','#');
                                        a.appendChild(img);a.appendChild(span);li.appendChild(a);
                                       ul.appendChild(li);
                                       
                                       if(UINFO.PFB == eachPL.PFB){pl_rank = eachPL.PR};
                                    }
                                );
                                 
                                 div_wrapper.appendChild(ul);
                                 div_caro.appendChild(div_wrapper);
                                 
                                 
                                 
                                  var div_round_stats  = document.createElement("div");
                                  div_round_stats.className = 'round_stats';
                                  if(gameData.CR == 'F')
                                  {
                                      div_round_stats.innerHTML = gameData.CR;
                                  }
                                  else
                                  {    
                                    div_round_stats.innerHTML = getOrdinal(gameData.CR,true);
                                  }
                                  var div_rank_stats  = document.createElement("div");
                                  div_rank_stats.className = 'rank_stats';
                                  if(pl_rank != 0 && pl_rank != '' && pl_rank != ' ')
                                  {    
                                    div_rank_stats.innerHTML = getOrdinal(pl_rank,true);
                                  }else
                                      {
                                          div_rank_stats.innerHTML = '-';
                                      }
                                  div_rip.appendChild(div_caro);
                                  div_rip.appendChild(div_round_stats);
                                  div_rip.appendChild(div_rank_stats);
                                 
                                   jQuery("#rip_active_rh").append(div_rip);
								   
                                }
                                
                            }
                           );
                    }         
          }
          catch(err)
          {
              if(isDevEnvironment === true)
                  {
                      //console.log('stats RH active games',err);
                  }
          }
          
          
          
     // show past games data
     try
     {
          // if data of all past games has changed
          if(is_apg)
          {
                // first remove the data of all past games that are present and rebuild with start
                jQuery(".ap_games").remove(); //console.log('past',pastGames);
                jQuery.each(gameRecords.APG_SORT,
                            function(index,value)
                            {
                                // value is gameId
                                var div_rip  = document.createElement("div");
                                div_rip.className = 'rip ap_games';
                                
                               var div_caro  = document.createElement("div");
                                div_caro.className = 'game_stats_user infiniteCarousel';
                                
                                var div_wrapper  = document.createElement("div");
                                div_wrapper.className = 'wrapper';
                             
                                var ul  = document.createElement("ul");   
                                jQuery.each(pastGames[value]['user_arr'],function(uid,value)
                                    { 
                                        var li  = document.createElement("li");
                                        var img  = document.createElement("img");
                                        var span  = document.createElement("span");
                                        var a = document.createElement("a");
                                        span.innerHTML = pastGames['usersInfo'][uid]['name'];
                                        img.setAttribute('width','31');
                                        img.setAttribute('height','31');
                                        img.setAttribute('src',"https://graph.facebook.com/"+ pastGames['usersInfo'][uid]['fbid']+ "/picture?type=square");
					a.setAttribute('href','#');
                                        a.appendChild(img);a.appendChild(span);li.appendChild(a);
                                        ul.appendChild(li);
                                    }
                                );
                                    div_wrapper.appendChild(ul);
                                    div_caro.appendChild(div_wrapper);
                                    cm_fbids = "'";gssCount = 0;
                                    for(key in pastGames[value]['user_arr'])
                                    {
                                            if(pastGames[value]['user_arr'][key].gss =='2')
                                            {
                                                if(cm_fbids == "'")
                                                    {
                                                        cm_fbids +=pastGames[value]['user_arr'][key].fbid; 
                                                    }
                                                    else{cm_fbids +=","+pastGames[value]['user_arr'][key].fbid;}
                                                    gssCount += 1;
                                            }   
                                    }
                                   cm_fbids += "'"; 
                                    var div_medal  = document.createElement("div");
                                    div_medal.className = 'medal';
                                    var div_rematch  = document.createElement("div");
                                    div_rematch.className = 'rematch';
                                    if(gssCount > 1 && typeof pastGames[value]['user_arr'][UINFO.UI] != 'undefined' && pastGames[value]['user_arr'][UINFO.UI].pre == 0 )
                                    {    
                                        div_rematch.innerHTML =  '<a href="#" onclick="rematchPastGames('+cm_fbids+','+"'Rematch','"+value+"'"+'); return false;">Rematch</a>';
                                    }
				if(pastGames[value]['won'] == 1)
				{
                                    var img_medal  = document.createElement("img"); 
                                    img_medal.setAttribute('width','40');
                                    img_medal.setAttribute('height','31');
                                    img_medal.setAttribute('src',baseUrl+'/images/zalerio_1.2/5.all_popup/mystats/mygames/cup.png');
                                    div_medal.appendChild(img_medal);
				}
                                    div_rip.appendChild(div_caro);
                                    div_rip.appendChild(div_medal);
                                    div_rip.appendChild(div_rematch);
                                
                                    jQuery("#rip_won_apg").append(div_rip);
                            }
                    );
                        
                     
              }
     }
     catch(err)
     {
         if(isDevEnvironment === true)
                  {
                      //console.log('showing past games',err);
                  }
     }
               /**** for scroll bar ******/
                jQuery(function()
			{ 
				jQuery('.scroll-pane').jScrollPane({showArrows: true,autoReinitialise: true});

			});
                        jQuery(".carousel_link").remove();
                  $('.infiniteCarousel').infiniteCarousel();
              
        // show level data
        try
        {
              var user_level = UINFO.PL;
              var cls = belt_array[user_level].toLowerCase();
              var top_level = sizeOfObj(belt_array);
              if(user_level < top_level)
              {
               var next_level = user_level+1;
                }
                else{
                    var next_level = user_level; 
                }
              var next_cls = belt_array[next_level].toLowerCase();
              var low_next_won_arr = total_won_array[next_level-1].split('-');
              var need_won_next = low_next_won_arr[0] - UINFO.GW;
             
              var low_next_game_arr = total_games_array[next_level-1].split('-');
              var need_game_next = low_next_game_arr[0] - UINFO.GP;
              
              // cr_belt_html will hold the data of current belt level like played games and won games till now
              var cr_belt_html = "<li>You played "+UINFO.GP+" game";
              if(UINFO.GP > 0){cr_belt_html +="s";}
              cr_belt_html += "</li><li>You won "+UINFO.GW+"</li>";
              
              //next_belt_html will hold the data, user need to complete like need t play 10 games and need to win 3 games to reach next level
              var next_belt_html ="";
              if(need_game_next > 0 && need_game_next > need_won_next)
                  {
                      next_belt_html +="<li>"+ need_game_next + " more game";
                      if(need_game_next > 1){next_belt_html +="s";}
                      next_belt_html +="</li>";
                  }
              if(need_won_next > 0 )
                  {
                      next_belt_html +="<li>"+ need_won_next + " more to win</li>";
                      
                  }
              
              jQuery("#next_belt_ul").html(next_belt_html);
              jQuery("#current_belt_ul").html(cr_belt_html);
              
              jQuery("#current_belt_h4").html(capFirst(cls));
              jQuery("#next_belt_h4").html(capFirst(next_cls));
              
              jQuery("#belt-info").attr('class','belt-info '+cls);
              jQuery("#belt-info").css('background','url('+baseUrl+'/images/zalerio_1.2/5.all_popup/mystats/mylevel/girl/girl_in'+cls+'/girl_'+cls+'_1.png) no-repeat -20px 33px');
              jQuery("#belt_text").html(capFirst(cls));
              if(user_level == next_level)
              {jQuery("#belt-info .next-belt").hide();jQuery("#belt-info .dummy_div").show();}
              else{jQuery("#belt-info .next-belt").show();jQuery("#belt-info .dummy_div").hide();}
              
              var vertical_text = "";
              // this will add all belts and the highlighted belt according to user level on right side of my level pop up
              for(i=top_level;i>0;i--)
                  {
                      var cl = belt_array[i].toLowerCase();
                      var cl_up = capFirst(cl);
                      if(user_level == i)
                          {
                              vertical_text += '<li class="active"><img src="../images/zalerio_1.2/5.all_popup/mystats/mylevel/belts/belts_highlighted/'+cl+'.png" alt="Black belt"><a class="'+cl+'" >'+cl_up+'</a></li>';
                          }else
                              {
                                  vertical_text += '<li><img src="../images/zalerio_1.2/5.all_popup/mystats/mylevel/belts/belts_ideal/'+cl+'.png" alt="Black belt"><a class="'+cl+'" >'+cl_up+'</a></li>';
                              }
                  }
              jQuery("#vertical_belt_conent").html(vertical_text);
               // show mylevel-check
                    jQuery("#mylevel-check").show();
    }
    catch(err)
    {
        if(isDevEnvironment === true)
        {
           console.log('showing level data',err);
        }
    }
    
    try
    {
        
    
                /*** user info that is displayed on top of my games and my level tabs ****/
                    jQuery("#stat_image_main").attr('src',UINFO.user_pic);
                    jQuery("#stat_image_tri_level").attr('src',baseUrl+"/images/zalerio_1.2/5.all_popup/mystats/mystatsCommon/stats_main_player_belts/"+userLevelImgBig[UINFO.PL-1]);
                    jQuery("#popup-stats .div_top .div_img #stat_userName").html(UINFO.PDN);
                    jQuery("#popup-stats .div_top #joined_time").html(" "+UINFO.JD);
                    //jQuery("#frnd_playing").html(obj.frnd_stats.on_zl);
                    //jQuery("#frnd_total").html(obj.frnd_stats.total - obj.frnd_stats.on_zl);
                    jQuery("#user-level-span").removeClass().addClass(capFirst(cls.toLowerCase())+'Belt').html(capFirst(cls.toLowerCase())+' Belt');
                   
    }
    catch(err)
    {
        if(isDevEnvironment === true)
        {
           console.log('showing stats top data',err);
        }
    }
                    
}
/** end **/
//function to remind the user for the game
function remindUser(gameId,UsersData,seatId,userData) {
	if(typeof userData == 'undefined'){
		userData = 0;
	}
	jQuery("#thisgame_seat_"+seatId+" .userRemind").addClass("loader_small");
	txt = jQuery("#thisgame_seat_"+seatId+" .userRemind").text();
	jQuery("#thisgame_seat_"+seatId+" .userRemind").html(loader_small);
	jQuery.ajax ( {
                        type : 'POST' ,
                        url : baseUrl + "/user/remindUser" ,
                        data : {'game_id':gameId,'user_data':UsersData,'lh_users_data':userData}
                    } ).done(function(data){
                    	try{
                    		jQuery("#thisgame_seat_"+seatId+" .userRemind").removeClass('loader_small');
                    		jQuery("#thisgame_seat_"+seatId+" .userRemind").text(txt);
                    		msg = popupMSG.remindSucess(jQuery("#thisgame_seat_"+seatId+" .userAreaName").text());
                    		messagePopup(msg);
                    	}catch(err){}                   		
                    });
}
function acceptInvitation(gameId) {
	jQuery("#accept_decline_"+gameId).addClass("loader_small");
	txt = jQuery("#accept_decline_"+gameId).html();
	jQuery("#accept_decline_"+gameId).html(loader_small)
        jQuery.ajax ( {
                        type : 'POST' ,
                        url : baseUrl + "/user/acceptInvite" ,
                        data : {'game_id':gameId}
                    } ).done(function(data) {
                    	try{
                    		var gameseat_gameinst_id = gameId;
                    		if(typeof tutorial != 'undefined'){
                    			if(tutorial == true ){
                    				boxBlank = jQuery('.box-blank');
                    				boxBlank.text('');
                    				boxBlank.removeClass();
                    				boxBlank.addClass('box-blank box-black ');		
                    			}
                    	    	tutorial = false
                    		}
                            messagePopup(popupMSG.acceptInvite,gameChangeListener,gameseat_gameinst_id)
                            jQuery("#accept_decline_"+gameId).removeClass("loader_small");
                            jQuery("#accept_decline_"+gameId).html('');
                    	}catch(err){}                      
                    }).error(function(data) {
                    	jQuery("#accept_decline_"+gameId).removeClass("loader_small");
                    	jQuery("#accept_decline_"+gameId).html(txt);
                    });
}
function getOrdinal(intNum, includeNumber)
{
    if(intNum == '' || intNum == ' ' || intNum == 'undefined' || intNum == null) return '';
    return (includeNumber ? intNum : "") + (((intNum = Math.abs(intNum) % 100)
        % 10 == 1 && intNum != 11) ? "st" : (intNum % 10 == 2 && intNum != 12)
        ? "nd" : (intNum % 10 == 3 && intNum != 13) ? "rd" : "th");

};
function rematchPastGames(ids,gameOption,gameId)
{
    var check = InviteFriends(ids,gameOption,gameId);
   
    jQuery('.s_show_popup').css('display','none'); return true;
        
    
}

jQuery('.newCarouselblanktile').live('click',function(){
	showFrndSelector();
})
try{
	jQuery('#showmehow').live('click',function(e){
		    tutorial = true
		    jQuery('.zalerio_popup').css('display','none');
			require(['zalzero.tutorial'], function(tutorial) {
	    		tutorial.launch();
	    	});
	})
}catch(err){}