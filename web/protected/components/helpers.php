<?php

// helper function to get the facebook id of a user

function getUserFaceBookId($userId) {

	$model=Zzuser::model()->findByPk($userId);
	if(isset($model)) {
		return $model->user_fbid;
	}
}

// function by mobicules to get the email if of the logged in user

function getUserEmailId($userId) {
       $model=Zzuser::model()->findByPk($userId);
        if(isset($model)) {
                return $model->user_email;
        }
}  

// helper function to get the user id from facebook id

function get_user_id($fbId) {
	$userModelWithFb = Zzuser::model()->findByAttributes(array('user_fbid'=>$fbId));
	$userId = $userModelWithFb->user_id;
	return $userId;
}

// function to get the Name of user. its done because its Fast that accesing data from graph api

function get_user_fb_name($fbId) {
	$userModelWithFb = Zzuser::model()->findByAttributes(array('user_fbid'=>"$fbId"));
        $userName = $userModelWithFb->user_name;
        return $userName;
}



// function by mobicules to check whether the invited user is a valid invited user for a game or not

function getValidInvitedUser($fbId,$gameId) {
	$checkForExistenceData = Zzinvitefriends::model()->findByAttributes(array('fbid'=>$fbId,'gameid'=>$gameId));
	    	if(isset($checkForExistenceData)) {
                        return true;
        	} else {
                        return false;
        	}
}

// function by mobicules to get the user name as full first name and single character of last name
function getDisplayName($fname, $lname) {
    $name='';
	if(!empty($fname) && (strlen($fname) <= 6 || empty($lname) || strlen($lname) > 6)) {
		if(strlen($fname) <= 6) {
			$name = $fname;
		} else {
			$name = substr($fname,0,6). ".";
		}
			
		if(!empty($lname)) {
			$name .= " ".substr($lname,0,1).".";
		}
	} else {
		if(!empty($lname)) {
			if(strlen($lname) <=6 ) {
				$name = $lname;
			} else {
				$name = substr($lname,0,6). ".";
			}
			
			if(!empty($fname)) {
				$name .= " ".substr($fname,0,1).".";
			}
		}
	}
	return $name;
}

// function by mobicules to store the avatars of facebook users
 
function storeFbUserPictures($fbId) {
	$img = file_get_contents('https://graph.facebook.com/'.$fbId.'/picture?type=square');
        $file = '/var/www/'.Yii::app()->request->baseUrl.'/images/avatar/'.$fbId.'.jpg';
	file_put_contents($file, $img);       
}

// function by mobicules to check for local storage of fb profile pic image

function getFbProfilePic($fbId) {
	$imageFile = '/var/www/'.Yii::app()->request->baseUrl.'/images/avatar/'.$fbId.'.jpg';
	if (file_exists($imageFile)) {
 		$fbCurrentUserImg = Yii::app()->request->baseUrl.'/images/avatar/'.$fbId.'.jpg';
	} else {
		$fbCurrentUserImg = "https://graph.facebook.com/" . $fbId . "/picture?type=square";
	}
	return $fbCurrentUserImg;
}

// helper function for getting the number of rounds in a game

function get_total_number_of_rounds($game_templ_id) {
	$game_template_id = $game_templ_id;
	$get_game_temp_details = ZzGameInstTempl::model()->findByPk($game_template_id);
	$no_of_rounds = $get_game_temp_details->gameinst_paramnum4;

	return $no_of_rounds;
}


// function to check we have to marg game as completed ot not. If there is only one accepted and resigned user can game will be marked as cancelled

function check_the_game_status($game_id) {
	
    $check_game_status = "select count(gameseat_id) as count from zzgameseat where gameseat_gameinst_id = '$game_id' and zzgameseat_status='accepted' or zzgameseat_status='resigned'";
    $get_count_of_users = Zzgameseat::model()->countBySql($check_game_status);
    return $get_count_of_users;
}

// function to get users of a game

function get_users_of_game($game_id) {

	$game_users = Zzgameseat::model()->findAllByAttributes(array('gameseat_gameinst_id'=>"$game_id",'zzgameseat_status'=>'accepted'));
	$get_game_users = array();
	if(!empty($game_users)) {
		foreach($game_users as $game_user) {
			$get_game_users[] = $game_user->gameseat_user_id;
		}
	}
	return $get_game_users;
}

// function to get total count of game user has completely played the games 

function get_total_count_of_games($user_id) {
	$get_total_games_count = "select g.gameinst_id as gameinst_id from zzgameinst g inner join zzgameseat s on g.gameinst_id = s.gameseat_gameinst_id where s.gameseat_user_id = '$user_id'and g.zzgameinst_status = 'completed' and s.zzgameseat_status = 'accepted'";
	$get_completed_games = Zzgameinst::model()->findAllBySql($get_total_games_count);
	$get_games_count =  count($get_completed_games);
	return $get_games_count;
}

// function to get total number of games which user have won in the completed games

function get_total_count_of_won_games($user_id) {
	$get_total_won_games_count = "select g.gameinst_id as gameinst_id from zzgameinst g inner join zzgameseat s on g.gameinst_id = s.gameseat_gameinst_id where s.gameseat_user_id = '$user_id'and g.zzgameinst_status = 'completed' and s.game_rank = 1 and s.zzgameseat_status = 'accepted'";
        $get_won_games = Zzgameinst::model()->findAllBySql($get_total_won_games_count);
        $get_won_games_count =  count($get_won_games);
        return $get_won_games_count;
}

// function to get the belts of user 

function get_user_belt($total_games,$total_won) {

$belt_array = $GLOBALS['belt_array'];

$total_games_array = $GLOBALS['total_games_array'];
$total_won_array = $GLOBALS['total_won_array'];
$store_total_game_id = 0;
$store_total_won_game_id = 0;
foreach($total_games_array as $key=>$value) {
	$get_val = explode("-",$value);
	$lower_limit = $get_val[0];
	$upper_limit = $get_val[1];
	
	if($total_games >= $lower_limit && $total_games <= $upper_limit) {	
		$store_total_game_id = $key+1;
		break;	
	}

}

foreach($total_won_array as $key=>$value) {
        $get_val = explode("-",$value);
        $lower_limit = $get_val[0];
        $upper_limit = $get_val[1];
        
        if($total_won >= $lower_limit && $total_won <= $upper_limit) {
                $store_total_won_game_id = $key+1;
                break;
        }

}
if($store_total_game_id <= $store_total_won_game_id) {
	$belt_id = $store_total_game_id;
} else {
	$belt_id = $store_total_won_game_id;
}
if($total_games >=2 && $total_won == 0) {
	$belt_color_code = 2;
} else if($belt_id == 0) {
	$belt_color_code = 1;
} else {
	$belt_color_code = $belt_id;
}
return $belt_color_code;
}

// helper function to check if the facebook user is invited / registered or new user

function get_fbuser_status($fbId) {
// using variables 2 for the registered user / 1 for invited user / 0 for new user
$userModelWithFb = Zzuser::model()->findByAttributes(array('user_fbid'=>$fbId));
if(isset($userModelWithFb)){
	$get_fb_user_status = 2;
} else {
$getFbUid = Zzinvitefriends::model()->findAll('fbid = :userFbId',array(':userFbId'=>$fbId));
if(!empty($getFbUid)) {
	$get_fb_user_status = 1;
} else {
	$get_fb_user_status = 0;
}
}
return $get_fb_user_status;
}

// class for getting all the default parametes for creating the game

class gameDefaultParameters {

	private $gameData;
	public function getGameDefaultParameters() {
		$this->gameData = array('gameinst_gameinsttempl_id'=>'ZalerioTempl1','gameinst_game_id'=>'ZALERIO_1','gameinst_product_id'=>'1','gameinst_threshold'=>'2');
	return $this->gameData;
	}
}

// function for sending message to the users when they are rematched. Preparing the message

function get_message_to_email($fbData,$currentFbId) {
     $message = "you";
     if(count($fbData) > 1) {
      foreach($fbData as $data) {
	if($data['uid'] != $currentFbId) {
		$otherNames[] = $data['name'];
	}
      }
     $data = implode(", ",$otherNames);
     $replace = ' and ';
     $search = ',';
     $messageToAppend = str_lreplace($search, $replace, $data);
     if(count($otherNames) == 1) {
     	$message .= " and ". $messageToAppend;
     } else {
	$message .= ", ". $messageToAppend;	
     }
     }
     
     return $message;
}


// function for adding command and "and" for last player

function str_lreplace($search, $replace, $subject)
{
    $pos = strrpos($subject, $search);

    if($pos !== false)
    {
        $subject = substr_replace($subject, $replace, $pos, strlen($search));
    }

    return $subject;
}



// creating a class for the prefinery details for staging and prod

class fetchPrefineryDetails {
        
        private $prefinery_details;
        
        public function getPrefineryDetails() {
        if(Yii::app()->request->serverName == "zalerio.com" || Yii::app()->request->serverName == "www.zalerio.com" )   // If production server
                {
                $this->prefinery_details = array('prefineryKey'=>'d9a2f4ec5802909510d801079cbf9aabcad46655','betaId'=>'3092','prefinerySubdomain'=>'https://mobicules.prefinery.com/','invitationCode'=>'bAbA1934sInGh','decodeKey'=>'36343cb7ac8fe16281aca9bdae86277df86cbd87');
                }
        else {
                $this->prefinery_details = array('prefineryKey'=>'d9a2f4ec5802909510d801079cbf9aabcad46655','betaId'=>'3172','prefinerySubdomain'=>'https://mobicules.prefinery.com/','invitationCode'=>'FINDITSTAGING','decodeKey'=>'e499493a0c1f970faabef8d1bfda725dc4766a8c');
             }
	return $this->prefinery_details;
        }

}



// helper function for creating account in prefinery with applied status
// will comment the code after fully removing the prefinery  from the application. Not removing right now as only Sitecontroller is cleaned up 
function create_prefinery_applied_account($email,$name,$userStatus) {

$prefineryDetails = new fetchPrefineryDetails();
$prefineryData = $prefineryDetails->getPrefineryDetails();
$prefineryKey = $prefineryData['prefineryKey'];
$betaId = $prefineryData['betaId'];
$prefinerySubdomain = $prefineryData['prefinerySubdomain'];
$invitationCode = $prefineryData['invitationCode'];

$command = '<?xml version="1.0" encoding="UTF-8"?><tester><email>'.$email.'</email><invitation-code>'.$invitationCode.'</invitation-code><status>'.$userStatus.'</status><profile><first-name>'.$name.'</first-name><last-name></last-name><employer></employer><jobtitle></jobtitle><address-line1></address-line1><address-line2></address-line2><city></city><state></state><postal-code></postal-code><country></country><telephone></telephone></profile></tester>';
//	$curl_command = 'curl --header "Content-Type: text/xml" --data "'.$command.'"'. $prefinerySubdomain.'api/v1/betas/'.$betaId.'/testers?api_key='.$prefineryKey;
	$curl_command = 'curl --header "Content-Type: text/xml" --data "'.$command.'" https://mobicules.prefinery.com/api/v1/betas/'.$betaId.'/testers?api_key='.$prefineryKey;
	$json = shell_exec($curl_command);

}


class sendEmail{

        private $url;
        private $user;
        private $pass;

        public function __construct(){

                $this->url = 'http://sendgrid.com/';
                $this->user = SENDGRID_USER;
                $this->pass = SENDGRID_PWD;

        }

        public function notifyemail($params){

                /* check input parameters like to, from and subject */
                $chkmsg = $this->checkParams($params);


                /*if($chkmsg['msg'] != 'ok'){

                        drupal_set_message($chkmsg['msg'],"error");
                        return array("msg"=>$chkmsg['msg']);

                }
                else{
                }*/
                /* creating to array for multiple recepient */
                if(!is_array($params['to'])){
                        $to = array($params['to']);
                }else{
                        $to = $params['to'];
                }
                $json_string = array(
                  'to' => $to,
                  'category' => ''
                );

       //$body = "<html><head><title>HTML email</title></head><body><p>This email contains HTML Tags!</p><table><tr><th>Firstname</th><th>Lastname</th></tr><tr><td>John</td><td>Doe<img src='http://i325.photobucket.com/albums/k394/slemkeatpb/Snapbucket/EA8E1F91.jpg' alt='LOGO'> </img></td></tr></table></body></html>";

                /* finalizing input array */
                $inputArr = array(
                        'api_user'  => $this->user,
                        'api_key'   => $this->pass,
                        'x-smtpapi' => json_encode($json_string),
                        'subject'   => $params['subject'],
                        'html'      => $params['body'],
                        'to'        => $params['to'],
                        'from'      => $params['from'],
                        'fromname'  => $params['fromname'],
                );


                 $response = $this->sendmail($inputArr);
                  $respArray = json_decode($response,true);
                  return $respArray;
            if($respArray['message'] == "success"){
                //drupal_set_message("Email successfully sent");
               }else {
                //drupal_set_message("Mail not sent.Please try again","error");
               }


        }

        private function sendmail($params){

                $request =  $this->url.'api/mail.send.json';

                // Generate curl request
                $session = curl_init($request);
                // Tell curl to use HTTP POST
                curl_setopt ($session, CURLOPT_POST, true);
                // Tell curl that this is the body of the POST
                curl_setopt ($session, CURLOPT_POSTFIELDS, $params);
                // Tell curl not to return headers, but do return the response
                curl_setopt($session, CURLOPT_HEADER, false);
                curl_setopt($session, CURLOPT_RETURNTRANSFER, true);

                // obtain response
                $response = curl_exec($session);
                curl_close($session);

                // print everything out
                return $response;
        }

 private function checkParams($params){
                if(!array_key_exists('to', $params)){
                        //return array("msg"=>"Please add atleast one recipient");
                }elseif(is_array($params['to']) && $params['to'][0] == ""){
                        //return array("msg"=>"Please add atleast one recipient");
                }elseif($params['to']==""){
                //      return array("msg"=>"Please add atleast one recipient");
                }



                if($params['from']==""){
                        return array("msg"=>"Please add from Email");
                }

                if($params['subject']==""){
                        return array("msg"=>"Please add Subject");
                }
                return array("msg"=>'ok');
        }
}

/**
 * Send winner notification mail to all players except players decline the game 
 * @param int $gameInstId
 */
function sendWinnerNotificationMail( $gameInstId)
{
	$game = new stdClass;
	$game->id = $gameInstId;
	$game->winner = new stdClass;
	$game->otherWinner = '';
	$game->allPlayerImgs = '';
	$i = 0;
	
	// game id and score sort by rank
	$gameUsersDetails =  Zzgameseat::model()->with(  array( 'gameseatUser' => array('condition'=>'zzuser_subscribe_status=TRUE') ) )->findAllByAttributes( array( 'gameseat_gameinst_id'=>"$gameInstId", 'zzgameseat_status'=> array( 'accepted', 'resigned' )),array('order'=>'game_rank ASC,gameseat_score DESC') );
	if(!$gameUsersDetails){
		return;		// return if no record found
	}

	$gameInstRecord = Zzgameinst::model()->findByAttributes(array('gameinst_id'=>"$gameInstId"));
	$game->startDate = date("M d", strtotime( $gameInstRecord->create_time ));

	foreach($gameUsersDetails as $gameUserDetails)
	{	$i++;
		$game->sendEmailTo[$gameUserDetails->gameseatUser->user_fbid] = $gameUserDetails->gameseatUser->user_email;
		$userDisplayName = getDisplayName( $gameUserDetails->gameseatUser->user_fname, $gameUserDetails->gameseatUser->user_lname );
		
		$game->allPlayerImgs .= '<img style=" margin-left:1px;" width="32" height="32" src="http://graph.facebook.com/'.$gameUserDetails->gameseatUser->user_fbid.'/picture" />';
		
		//continue;
		// set game creator data
		if($gameInstRecord->gameinst_created_by == $gameUserDetails->gameseat_user_id)
		{
			$game->creatorFBid 	= 	$gameUserDetails->gameseatUser->user_fbid;
			$game->creator		= 	$userDisplayName;
		}
			
		if($i == 1)
		{	// set winner data
			$game->winner->name		=	$userDisplayName;
			$game->winner->FBid		=	$gameUserDetails->gameseatUser->user_fbid;
			$game->winner->score	=	$gameUserDetails->gameseat_score;
			
		}else{

			switch ($i)
			{
				case 1:
					$rank = '1st';
					break;
				case 2:
					$rank = '2nd';
					break;
				case 3:
					$rank = '3rd';
					break;
				case 4:
					$rank = '4th';
					break;
				case 5:
					$rank = '5th';
					break;
			}
			
			$game->otherWinner .= <<<EOD
				<tr>
					    <td>
					          		<table width="486" border="0" style="color:white;background:#8194cc; padding:5px 15px 0; margin:0 auto; border-radius:5px;">
								        <tr>
								          <td width="10%" style="font-size:30px;">{$rank}</td>
								          <td  width="60%"align="left" valign="bottom"><img  width="45" height="45" src="http://graph.facebook.com/{$gameUserDetails->gameseatUser->user_fbid}/picture" /><span style=" padding-top: 15px;margin:0 0 0 5px; font-size: 24px;">{$userDisplayName}</span></td>
								          <td width="20%" align="right" style="font-size:24px; color:#fff;">{$gameUserDetails->gameseat_score}</td>
								        </tr>
				      				</table>
					    </td>
				    	<td>&nbsp;</td>
				</tr>
EOD;
		}
	}

		
	$headers = "MIME-Version: 1.0" . "\r\n";
	$headers .= "Content-type:text/html;charset=iso-8859-1" . "\r\n";
	$headers .= "From: zalerio@zalerio.com" . "\r\n";
	
	foreach($game->sendEmailTo as $user_fbid=>$to){   // array key ($user_fbid) content user facebook id
		
		$game->to = $to;
		$emailTemplate	= emailTemplate($game,$user_fbid);
		
		@mail( $to,'Zalerio: Winner Notification',$emailTemplate, $headers );
	}
	
	return;
}


function emailTemplate($game,$user_fbid){

	$FBConfig = new facebookCredetials();
	$url = $FBConfig->config->canvasPage;
	$privacypolicy = $url.'site/privacypolicy';
	$tos = $url.'site/tos';
	
	$unsub_url = $url.'deauthorise/unsubscribe?user_id='.$user_fbid;
	
	$emailTemplate = <<<EOD
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
					<html xmlns="http://www.w3.org/1999/xhtml">
					<head>
					<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
					<title>Untitled Document</title>
					<style type="text/css">
					table {
						font-family:arial;
						/* fallback */
						background-color: #1a274a;
						/* Safari 4-5, Chrome 1-9 */
						background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#1a274a), to(#3366ff));
						/* Safari 5.1, Chrome 10+ */
						background: -webkit-linear-gradient(top, #1a274a, #3366ff);
						/* Firefox 3.6+ */
						background: -moz-linear-gradient(top, #1a274a, #3366ff);
						/* IE 10 */
						background: -ms-linear-gradient(top, #1a274a, #3366ff);
						/* Opera 11.10+ */
						background: -o-linear-gradient(top, #1a274a, #3366ff);
						color: #fff;
					}
					</style>
					</head>
					
					<body>
					<table width="580" border="0" align="center" style="color:white;background-color: #1a274a; background: -webkit-linear-gradient(top, #1a274a, #3366ff); ">
					  <tr>
					    <td align="left"><img src="{$url}images/email/Zalerio.png" alt="Logo" /></td>
					    <td colspan="0">&nbsp;</td>
					  </tr>
					  <tr>
					    <td><table width="545" border="0" style="color:white;background:#7c85a2; padding:2px; margin-left:15px; border-radius:5px;">
					        <tr>
					          <th width="30%" align="left" st>Created by</th>
					          <th width="55%" align="center">All Players</th>
					          <th width="15%">Start Date</th>
					        </tr>
							<tr>
								<td align="bottom" valign="middle"><img width="50" height="50" src="http://graph.facebook.com/{$game->creatorFBid}/picture" /><span style=" padding-top: 15px;margin:0 0 0 5px; font-size: 20px;">{$game->creator}</span></td>
								<td align="center" valign="bottom">
										{$game->allPlayerImgs}
								</td>
								<td align="center" valign="bottom" style="font-size:20px">{$game->startDate}</td>
							        </tr>
						      </table></td>
						    <td>&nbsp;</td>
						  </tr>
						  <tr>
						    <td><table width="486" border="0" style="color:white;background:#818eba; padding:8px 50px; margin:0 auto; border-radius:5px;">
						        <tr>
						          <td colspan="3" style="background: -webkit-gradient(linear, left top, right top, from(#1a2b3f), to(#7b85a0));/* Safari 5.1, Chrome 10+ */background: -webkit-linear-gradient(left, #1a2b3f, #7b85a0);/* Firefox 3.6+ */background: -moz-linear-gradient(left, #1a2b3f, #7b85a0);/* IE 10 */background: -ms-linear-gradient(left, #1a2b3f, #7b85a0);/* Opera 11.10+ */background: -o-linear-gradient(left, #1a2b3f, #7b85a0); font-size:24px; font-weight:bold; padding:10px 5px; border-radius:5px;">And the Winner is</td>
						        </tr>
						        <tr><td>&nbsp;</td></tr>
						        <tr>
						          <td width="10%" style="font-size:40px;">1st</td>
						          <td width="70%"align="left" valign="bottom"><img  width="66" height="66" src="http://graph.facebook.com/{$game->winner->FBid}/picture" /><span style=" padding-top: 15px;margin:0 0 0 5px; font-size: 24px;">{$game->winner->name}</span></td>
						          <td width="20%" align="right" style="font-size:30px; font-weight:bold; color:#435b77; text-shadow:1px 1px #424d6d;">{$game->winner->score}</td>
						        </tr>
						      </table></td>
						    <td>&nbsp;</td>
						  </tr>
						 {$game->otherWinner}
						  <tr><td align="center"><!-- <a href="#"><img src="Sprites/rematch_ideal.png" alt="Rematch" /></a></td><td>&nbsp;</td> --> </tr>
					</table>
						<table width="580" border="0" align="center" style="background:#fff; color:#000">
						  <tr>
						    <td>This email is intended for {$game->to}. You received this message because you signed up for 'Zalerio' e-mail program. To unsubscribe from Zalerio (Game Invitation) emails <a href="{$unsub_url}">unsubscribe here</a></td>
						  </tr>
						  <tr>
						    <td>&copy; Zalzero Inc., Palo Alto, CA California, U.S.A. <a href="{$privacypolicy}" style="color:#000" >Privacy Policy</a> & <a href="{$tos}" style="color:#000" >Terms & Conditions</a> </td>
						  </tr>
						</table>
					
					</body>
					</html>
EOD;
	return $emailTemplate;
}



//desk support mail
define('DESK_SUPPORT_MAIL','support@zalzero.com');

//sendgrid details
define('SENDGRID_USER', 'zalerio');
define('SENDGRID_PWD', 'Cytzmlk1');
// this contains the subject of mail when user will submit the rating and suggestion about the game
define('RATING_FEATURE_SUBJECT','Rating about game');
// this is the global array that contains level's number and corresponding level text that will be displayed to end user
$GLOBALS['belt_array'] = array("1"=>"white","2"=>"yellow","3"=>"orange","4"=>"green","5"=>"blue","6"=>"purple","7"=>"red","8"=>"brown","9"=>"black");
// this array contains minimum number of games that should be played to reach a particular level
$GLOBALS['total_games_array'] = array('1-1','2-4','5-9','10-29','30-74','75-99','100-199','200-399','400-10000');
// this array contains minimum number of games that should be won to reach a particular level
$GLOBALS['total_won_array'] = array('0-0','0-0','1-1','2-7','8-24','25-39','40-89','90-179','180-10000');
// this array contains minimum number of friends that should be invited to reach a particular level
$GLOBALS['invited_friends'] = array('1'=>0,'2'=>0,'3'=>0,'4'=>0,'5'=>10,'6'=>25,'7'=>50,'8'=>100,'9'=>200);
// this array contains the rating level number
$GLOBALS['rating_level'] = array('1','2','3','4','5');
