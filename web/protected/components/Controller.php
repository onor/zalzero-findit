<?php

Yii::import('application.config.*');
require_once('mobile.php');
require_once('fbappconfig.php');
require_once('GameServices.php');
require_once("UserServices.php");

/**
 * Controller is the customized base controller class.
 * All controller classes for this application should extend from this base class.
 */
class Controller extends CController
{
	/**
	 * @var string the default layout for the controller view. Defaults to '//layouts/column1',
	 * meaning using a single column layout. See 'protected/views/layouts/column1.php'.
	 */
	public $layout='//layouts/column1';
	/**
	 * @var array context menu items. This property will be assigned to {@link CMenu::items}.
	 */
	public $menu=array();
	/**
	 * @var array the breadcrumbs of the current page. The value of this property will
	 * be assigned to {@link CBreadcrumbs::links}. Please refer to {@link CBreadcrumbs::links}
	 * for more details on how to specify this property.
	 */
	public $breadcrumbs=array();

	public $facebook;

	public $oauth_token;
	
	public $detect;
	
	public $loggedInUser;

	function __construct($id,$module=null) {

		parent::__construct($id, $module);

		// Get an instance of the Facebook class distributed as the PHP SDK by facebook:
		$this->facebook = new facebookCredetials();

	}

	/**
	 * Filters for all request
	 */
	public function filters() {
		return array(
				'facebook',
		);
	}

	public function filterFacebook($filterChain) {
				
		$_auth_token = false;
		
		$this->detect = new Mobile_Detect();
		
		if ( $this->detect->isMobile() ) {
			
			if (isset($_REQUEST["code"])){
				
				if(!Yii::app()->session['oauth_token'])
				{
					$_auth_token = $this->get_auth_token($_REQUEST["code"]);
				}else{
					$_auth_token = Yii::app()->session['oauth_token'];
				}	
			}else{
				$this->get_auth();
			}
		}

		if(Yii::app()->request->isAjaxRequest){
			
			$filterChain->run();
			
			exit;
		}
		
		// check if safari browser
		if( true ){
			
			// set test cookies for browser
			setcookie("zalerio_safari_test", "1");
			
			setcookie("zalerio_","1",time() + 30 * 84000 );
			
			// if cookies count zero or less then zero then redirect to zalerio
			if(	! count($_COOKIE) > 0 ){
				
				echo("<script> top.location.href='" . $this->facebook->config->canvasUrl . "'</script>");
			}
		}
		
		if(isset($_REQUEST["signed_request"]) || $_auth_token != false ){ // user come for facebook iframe
			
			if( $_auth_token == false ){
				list($encoded_sig, $payload) = explode('.', $_REQUEST["signed_request"], 2);
				$signedRequestData = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);
			}else{
				
				// get facebook me data
				$facebookUser	=	$this->get_fb_user_info($_auth_token);
				
				$signedRequestData["oauth_token"] = $_auth_token;
				
			}
			
			if(!empty($signedRequestData["oauth_token"])){
				
				Yii::app()->session['oauth_token'] = $signedRequestData["oauth_token"];
				
				if( $_auth_token == false ){
					// get user if exists
					$this->loggedInUser = Zzuser::model()->findByAttributes(	array( 'user_fbid' => $signedRequestData["user_id"] ));
					
				}else{
					// get user if exists
					$this->loggedInUser = Zzuser::model()->findByAttributes(	array( 'user_fbid' => $facebookUser->id ));
				}
				
				if( ! $this->loggedInUser ){
					
					$this->loggedInUser = ''; // reset variable
				
					// get facebook me data
					$facebookUser	=	$this->get_fb_user_info($signedRequestData["oauth_token"]);

					Yii::app()->session['fbid'] = $facebookUser->id;

					$this->loggedInUser = new Zzuser;
					
					if(isset($facebookUser->name)){
						$this->loggedInUser->user_name	=	substr($facebookUser->name, 0, 49); // user name can't more then 50 char
					}else{
						$this->loggedInUser->user_name	= $facebookUser->id;
					}
					// User First Name
					$this->loggedInUser->user_fname	=	@$facebookUser->first_name;

					// user Last name
					$this->loggedInUser->user_lname	=	@$facebookUser->last_name;

					// user facebook id
					$this->loggedInUser->user_fbid	=	$facebookUser->id;

					if(isset($facebookUser->email)){
						$this->loggedInUser->user_email	=	$facebookUser->email;
					}else{
						$this->loggedInUser->user_email	=	$facebookUser->id.'@facebook.com';
					}

					$this->loggedInUser->user_handle		=	$facebookUser->id.'@facebook.com';

					$this->loggedInUser->zzuser_status	=	'invited';
					
					$this->loggedInUser->user_password = md5($facebookUser->id);
					
					$this->loggedInUser->user_role_id	= '2';
					
					if($this->loggedInUser->save()){
						
						// create game users summary
						$userGameSummaryModel = new Zzgameusersummary;
						$userGameSummaryModel->zz_gameinsttemp_id = 'ZalerioTempl1';
						$userGameSummaryModel->user_id = $this->loggedInUser->user_id;
						$userGameSummaryModel->total_games_played = 0;//At the start Games played is 0
						$userGameSummaryModel->total_game_won = 0;//At the start Games Won is 0
						$userGameSummaryModel->user_level = 1;//At the start the Level is 1
						$userGameSummaryModel->save();
						
					}
				}

				if( $this->loggedInUser->zzuser_status == 'invited'){
					
					// get facebook me data
					$facebookUser	=	$this->get_fb_user_info($signedRequestData["oauth_token"]);
					
					if(isset($facebookUser->email)){
						Yii::app()->session['fb_email']	=	$facebookUser->email;
					}else{
						Yii::app()->session['fb_email']	=	$facebookUser->id.'@facebook.com';
					}
					
				}else{
					Yii::app()->session['fb_email'] =	$this->loggedInUser->user_email;
				}
			
				// we have logged-in user if we are here
				Yii::app()->session['fbid']		=	$this->loggedInUser->user_fbid;

				$filterChain->run();
				
				exit;
				
			}
		}
		
		$this->get_auth();
	}
	
	function get_auth(){
		$get_param = "gameinst_id=0";
		
		if(isset($_REQUEST["force"])){
			
			$get_param .= "&force=play";
		}
		
		$get_param = urlencode($get_param);
		
//		$permission = "email,user_birthday,sms,publish_stream,read_friendlists,friends_online_presence";
		$permission = "email,friends_online_presence";
		if ( $this->detect->isMobile() ) {
			
			$redirect_uri = urlencode($this->facebook->config->canvasUrl);
			
			$domain = 'https://m.facebook.com/';
			
		}else{
			
			$domain = 'https://www.facebook.com/';
			
			$redirect_uri = urlencode($this->facebook->config->canvasPage);
		}
		
		$auth_url = $domain."dialog/oauth?scope=".$permission."&client_id=".$this->facebook->config->appId."&redirect_uri=".$redirect_uri.'?'.$get_param;

		echo("<script> top.location.href='" . $auth_url . "'</script>");
		
		Yii::app()->end();
		
		exit;
	}

	function get_fb_user_info($oauth_token = false)
	{
		if ($oauth_token !== false){
			// oatuh token set
			$this->oauth_token = $oauth_token;

		}elseif( !$this->oauth_token ) {
			// if access token not set
			$this->set_access_token();
		}

		// Attempt to query the graph:
		$graph_url = "https://graph.facebook.com/me?". "access_token=" . $this->oauth_token;

		$response = $this->curl_get_file_contents($graph_url);

		$decoded_response = json_decode($response);

		//Check for errors
		if (@$decoded_response->error) {
			// check to see if this is an oAuth error:
			if ($decoded_response->error->type == "OAuthException") {
				// Retrieving a valid access token.
				$this->get_auth();
				exit;
			}

			echo "other error has happened";
			return false;  // error
		}
		return $decoded_response;  // success
	}

	function get_auth_token($code) {
		
      		if(isset($_REQUEST['ref'])){
        		$redirect_uri = $this->facebook->config->canvasUrl.'?ref=bookmark';
        	}else{
        		$redirect_uri = $this->facebook->config->canvasUrl."?gameinst_id=0";
        	}
        	
            $token_url = "https://graph.facebook.com/oauth/access_token?client_id="
                    . $this->facebook->config->appId . "&redirect_uri=".urlencode($redirect_uri)
                    . "&client_secret=" . $this->facebook->config->appSecretId
                    . "&code=" . $code . "&display=popup";
                                        
            $response = file_get_contents($token_url);
            
            $params = null;
            parse_str($response, $params);
            
            return $params['access_token'];
            
	}
	
	// we should have access token now
	function curl_get_file_contents($URL) {
		$c = curl_init();
		curl_setopt($c, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($c, CURLOPT_SSL_VERIFYPEER, false);
		curl_setopt($c, CURLOPT_URL, $URL);
		$contents = curl_exec($c);
		$err = curl_error($c);
		curl_close($c);
		if ($err) return $err;
		// if not error
		return $contents;
	}
}
