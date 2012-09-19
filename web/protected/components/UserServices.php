<?php
// function to create a new user

function createFacebookUser($Zzuser) {
$model=new Zzuser;
            $facebook = Yii :: app()->params['facebook'];
            $canvasUrl = $facebook->getCanvasPage();
            if(isset($Zzuser))     // If we have the user's data of whom the account is to be created
            {
		// Check if the user does not existes in the database by users facebook id
		$userModelWithFb = Zzuser::model()->findByAttributes(array('user_fbid'=>$Zzuser['user_fbid']));
		// If user does not exist then create the user. if exists just pass
                if(!isset($userModelWithFb)){
                $model->attributes=$Zzuser;
		
                $model->user_password = md5(uniqid('',true));
                //user role id 2 is for customer
                $model->user_role_id = '2';
                if($model->save()){
                                        /* An entry should be made for each Game Template Id */
                                        /* For now hard coding it to one template id */
				// Entrying to the Zzgameusersummary table so that we would be able to get the initial data in cron for users level, total games played etc.
                                        $userGameSummaryModel = new Zzgameusersummary;
                                        $userGameSummaryModel->zz_gameinsttemp_id = 'ZalerioTempl1';
                                        $userGameSummaryModel->user_id = $model->user_id;
                                        $userGameSummaryModel->total_games_played = 0;//At the start Games played is 0
                                        $userGameSummaryModel->total_game_won = 0;//At the start Games Won is 0
                                        $userGameSummaryModel->user_level = 1;//At the start the Level is 1
                                        $userGameSummaryModel->save();

                }
	      }
            }
}

// function to get the list of invited users and create there account
function getListOfInvitedUsers($fbUserData) {
// get all invited users list
$fbUserFriends = $fbUserData;

foreach ($fbUserFriends as $fbUser) {
// As we can not capture the email of the invited user as its a extender permission we are putting the facebook email of the user in data. Once the user comes to join the game his original email will be updated.
	$Zzuser = array();
	// User facebook Name
        $name = $fbUser['name'];
	// User First Name   
	$fname = $fbUser['first_name'];
	// user Last name
	$lname = $fbUser['last_name'];
	// user facebook id
        $id = $fbUser['uid'];
	// Passing all the parameters in array
	$Zzuser['user_fbid'] = $id;
	$Zzuser['user_fname'] = $fname;
	$Zzuser['user_lname'] = $lname;
	$Zzuser['user_name'] = $name;
	$Zzuser['user_email'] = $id.'@facebook.com';
	$Zzuser['user_handle'] = $id.'@facebook.com';
	$Zzuser['zzuser_status'] = 'invited';  // putting the users status to 'invited'
       // Passing the values in form of array to the function to create the account of the User
	createFacebookUser($Zzuser);
	$Zzuser = NULL;
}

}


// function to insert invited users to zzinvited friends table and send email to the invited users. If the users is already active in the application we are not going to enter in the table. If he is not in the system then we are going insert data with stattus invited 

function getFbFriendsList($gameId,$getFbUsersInvitedData) {
if (Yii::app()->request->isAjaxRequest) {
 $facebook = Yii :: app()->params['facebook'];
 $canvasUrl = $facebook->getCanvasPage();
 $loggedInFbId = $facebook->getUser();
 $fbme = $facebook->api('/me');
 $loggedInUserName = $fbme['name'];
 $fbUserFriends = $getFbUsersInvitedData;
 $userId = Yii::app()->user->getId();
 $getLoggedinUserEmail = getUserEmailId($userId);
// $gameId = $_REQUEST['gameseat_gameinst_id'];
 
 foreach ($fbUserFriends as $fbUser) {
        $name = $fbUser['name'];
        $id = $fbUser['uid'];
        $email = $id.'@zalerio.com';
	// Creating the email so that we can email user to there facebook id regarding the invitation
        if($fbUser['username'] != '') {
            $fbEmail = $fbUser['username'].'@facebook.com';
        } else {
            $fbEmail = $fbUser['uid'].'@facebook.com';
        }
	if(isset($_REQUEST) && $_REQUEST['gameOption'] == "Create") {
           $saveFriends = new Zzinvitefriends;
           $saveFriends->user_id = $userId;
           $saveFriends->fbid = $fbUser['uid'];
           $saveFriends->zzinvitefriends_status = 'invited';  //marking the user facebook id status as invited
	
           $headers = "MIME-Version: 1.0" . "\r\n";
           $headers .= "Content-type:text/html;charset=iso-8859-1" . "\r\n";
           $headers .= "From: $getLoggedinUserEmail" . "\r\n";
           
       	   $message = get_message_to_email($fbUserFriends,$id);
	   if(count($fbUserFriends) == 1) {
	   $body =$loggedInUserName.' wants to play ZALERIO with '.$message.'. <a href="'.$canvasUrl.'?gameinst_id='.$gameId.'">Join '.$loggedInUserName.' now!</a>';
	   } else {
           $body =$loggedInUserName.' wants to play ZALERIO with '.$message.'. <a href="'.$canvasUrl.'?gameinst_id='.$gameId.'">Join your friends now!</a>';
	   }
	//    $body ='<a href="'.$canvasUrl.'?gameinst_id='.$gameId.'">Find IT game Invitation</a>';
           if(!strstr(Yii::app()->getBaseUrl(true),"localhost")){
	    // send emails to the invited users
            mail($fbEmail,'Find IT',$body, $headers);
           }
	   // if user is not active in system the insert him with status invited
	   // checking if the user is in active state in zzusers table or not. If not then we are going to insert in zzinvitre table
	   $checkForExistenceData = Zzuser::model()->findByAttributes(array('user_fbid'=>$id,'zzuser_status'=>'invited'));
           if(!empty($checkForExistenceData)) {
                $saveFriends->save();
           }
        } else if(isset($_REQUEST) && $_REQUEST['gameOption'] == "Rematch") {
	  $headers = "MIME-Version: 1.0" . "\r\n";
          $headers .= "Content-type:text/html;charset=iso-8859-1" . "\r\n";
          $headers .= "From: $getLoggedinUserEmail" . "\r\n";
	  $message = get_message_to_email($fbUserFriends,$id);
	   if(count($fbUserFriends) == 1) {
           $body =$loggedInUserName.' wants to rematch you in ZALERIO game. Take the challenge - <a href="'.$canvasUrl.'?gameinst_id='.$gameId.'">Join '.$loggedInUserName.' now!</a>';
           } else {
           $body =$loggedInUserName.' wants to rematch you in ZALERIO game. Take the challenge - <a href="'.$canvasUrl.'?gameinst_id='.$gameId.'">Join '.$loggedInUserName.' and other friends now!</a>';
           }
           if(!strstr(Yii::app()->getBaseUrl(true),"localhost")){
            // send emails to the invited users
            mail($fbEmail,'Find IT',$body, $headers);
           }
	} else {
		// Do nothing for Now.

	}
 }
 }
}

// function for reminding the user to play the game 

function remindUserOnFb($gameId,$userData,$UsersFbData,$checkForMessage,$loggedInUserStatus) {
   if (Yii::app()->request->isAjaxRequest) {
	 $facebook = Yii :: app()->params['facebook']; // initialize fb onject
	 $canvasUrl = $facebook->getCanvasPage(); // get canvas url
 	 $loggedInFbId = $facebook->getUser(); // get logged in user fbId
         $fbme = $facebook->api('/me');
         $loggedInUserName = $fbme['name']; // get logged in user fb name
	 $userId = Yii::app()->user->getId();
	 $getLoggedinUserEmail = getUserEmailId($userId);// get logged in email
	 //foreach($userData as $fbUid) {
		$fbUid = $userData;
		$getFbUserDetails = $facebook->api("/$fbUid");
		$fbUserName = $getFbUserDetails['username'];	
		if($fbUserName != '') {
         	   $fbEmail = $fbUserName.'@facebook.com';
        	} else {
            	   $fbEmail = $fbUid.'@facebook.com';
        	}		
	   $headers = "MIME-Version: 1.0" . "\r\n";
           $headers .= "Content-type:text/html;charset=iso-8859-1" . "\r\n";
           $headers .= "From: $getLoggedinUserEmail" . "\r\n";

	   if($checkForMessage == 'invited') {
		if(count($UsersFbData) > 1) {
		   $data = implode(", ",$UsersFbData);
	           $replace = ' and ';
     		   $search = ',';
     		   $messageToAppend = str_lreplace($search, $replace, $data);
		   $message = $messageToAppend;
		   $body = $loggedInUserName.' has invited you and other friends to play ZALERIO.'. '<a href="'.$canvasUrl.'?gameinst_id='.$gameId.'"> Please join to play now!.</a>';
		} else if(count($UsersFbData) == 1) {
		   $data = implode(", ",$UsersFbData);
		   $body = $data .' has joined a new ZALERIO game. The game starts shortly.'. '<a href="'.$canvasUrl.'?gameinst_id='.$gameId.'"> Please join to play now!.</a>';
		} 
		else {
		  $body = $loggedInUserName.' has invited you to play ZALERIO. <a href="'.$canvasUrl.'?gameinst_id='.$gameId.'"> Please join '.$loggedInUserName.' now.</a>'; 
		}
	   }
	  if($checkForMessage == 'turnremaining') {
		if(count($UsersFbData) > 1) {
		   $data = implode(", ",$UsersFbData);
                   $replace = ' and ';
                   $search = ',';
                   $messageToAppend = str_lreplace($search, $replace, $data);
                   $message = $messageToAppend;
		   if($loggedInUserStatus == 0) {
		   $body = 'New round in ZALERIO game has started. We are waiting for you &#9786;. Check out where you stand and'. '<a href="'.$canvasUrl.'?gameinst_id='.$gameId.'"> play your next turn now!.</a>';
		   } else {
	
                   $body = $loggedInUserName .' has played round in ZALERIO and is waiting for you.'. '<a href="'.$canvasUrl.'?gameinst_id='.$gameId.'"> Please play your turn now!.</a>';
		  }
                } else if(count($UsersFbData) == 1) {
		  $data = implode(", ",$UsersFbData);
		  if($loggedInUserStatus == 0) {
                   $body = 'New round in ZALERIO game has started. We are waiting for you &#9786;. Check out where you stand and'. '<a href="'.$canvasUrl.'?gameinst_id='.$gameId.'"> play your next turn now!.</a>';
                   } else {

                   $body = $loggedInUserName .' has played round in ZALERIO and is waiting for you.'. '<a href="'.$canvasUrl.'?gameinst_id='.$gameId.'"> Please play your turn now!.</a>';
                  }  
		}
                else {
                  $body = 'New round in ZALERIO game has started. We are waiting for you &#9786;. Check out where you stand and'. '<a href="'.$canvasUrl.'?gameinst_id='.$gameId.'"> play your next turn now!.</a>';           
                }
	  }

	   // remind user for game
         //  $body =$loggedInUserName.' has reminded to play the game. <a href="'.$canvasUrl.'?gameinst_id='.$gameId.'">Find IT game Invitation</a>';
           if(!strstr(Yii::app()->getBaseUrl(true),"localhost")){
            // send emails to the invited users
	    if($loggedInFbId != $fbUid) {
            mail($fbEmail,'Find IT',$body, $headers);
	    }
           } 
         //}
  }
}


// function to update the users status once he comes to join the application 

function update_loggedin_user_status($fbId,$email) {
// marking the status as active and updating his email address if the user in presend in database 
 $check_for_user_existence = Zzuser::model()->findByAttributes(array('user_fbid'=>$fbId,'zzuser_status'=>'invited'));
	if(isset($check_for_user_existence)) {
		$check_for_user_existence->user_email = $email;
		$check_for_user_existence->user_handle = $email;
		$check_for_user_existence->zzuser_status = 'active';
		$check_for_user_existence->save();
	}
}

// Updating the column of the zzinvieted friends table that user has accepted teh invitation
function update_invitation_status($fbId) {
 $check_for_invited_friends = Zzinvitefriends::model()->findByAttributes(array('fbid'=>$fbId,'zzinvitefriends_status'=>'invited'));
  
	if(isset($check_for_invited_friends)) {
		$check_for_invited_friends->zzinvitefriends_status = 'accepted';
		$check_for_invited_friends->save();
	}
}

// if gameid is greater than 0, will be passing game id 0 in case user is coming to the site with any invitation. If coming from invitation then his status will be set to accepted

function update_game_seat_status($userId,$gameId) {
   if($gameId > 0) {
   $get_user_id = $userId;
   $gameSeat = Zzgameseat::model()->findByAttributes(array('gameseat_user_id' => "$get_user_id",'gameseat_gameinst_id' => "$gameId",'zzgameseat_status'=>'invited'));
   if(isset($gameSeat)) {
	$currentUpadteTime = new CDbExpression('now()');
	$gameSeat->zzgameseat_status = 'accepted';
	$gameSeat->gameseat_status_update_time = "$currentUpadteTime";
	$gameSeat->save();
   } 
   }
}
