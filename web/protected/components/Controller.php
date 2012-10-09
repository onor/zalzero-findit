<?php

Yii::import('application.config.*');

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

	function __construct($id,$module=null) {

		parent::__construct($id, $module);

		// Get an instance of the Facebook class distributed as the PHP SDK by facebook:
		$this->facebook = new facebookCredetials();

	}

	/**
	 * Filters for all the request coming to facebook application
	 */
	public function filters() {
		return array(
				'facebook',
		);
	}

	public function filterFacebook($filterChain) {
	
		if(isset($_REQUEST["signed_request"])){ // user come for facebook iframe
			
			list($encoded_sig, $payload) = explode('.', $_REQUEST["signed_request"], 2);
			$data = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);

			if(!empty($data["oauth_token"])){

				// get user if exists
				$userSatus = Zzuser::model()->findByAttributes(	array( 'user_fbid' => $data["user_id"] ));

				if($userSatus){
										
					Yii::app()->session['fbid'] = $userSatus->user_fbid;
					Yii::app()->session['fb_email'] = $userSatus->user_email;

				}else{

					// get facebook me data
					$facebookUser	=	$this->get_fb_user_info($data["oauth_token"]);

					Yii::app()->session['fbid'] = $facebookUser->id;

					$model = new Zzuser;
					
					if(isset($facebookUser->name)){
						$model->user_name	=	$facebookUser->name;
					}else{
						$model->user_name	= $facebookUser->id;
					}
					// User First Name
					$model->user_fname	=	@$facebookUser->first_name;

					// user Last name
					$model->user_lname	=	@$facebookUser->last_name;

					// user facebook id
					$model->user_fbid	=	$facebookUser->id;

					if(isset($facebookUser->email)){
						$model->user_email	=	$facebookUser->email;
					}else{
						$model->user_email	=	$facebookUser->id.'@facebook.com';
					}

					$model->user_handle		=	$facebookUser->id.'@facebook.com';

					$model->zzuser_status	=	'';
					
					$model->user_password = md5($facebookUser->id);
					
					$model->user_role_id	= '2';
					
					$model->save();
														
					Yii::app()->session['fbid'] = $facebookUser->id;
					Yii::app()->session['fb_email'] = $model->user_email;
				}

				$filterChain->run();
			}else{
				$this->get_auth();
			}

		}
		
		$this->get_auth();
	}
	
	function get_auth(){
		$get_param = "gameinst_id=0";
		
		if(isset($_REQUEST["force"])){
			$get_param .= "&force=play";
		}
		
		$permission = "email,user_birthday,sms,publish_stream,read_friendlists,friends_online_presence";
		
		$auth_url = "https://www.facebook.com/dialog/oauth?scope=".$permission."&client_id=".$this->facebook->config->appId."&redirect_uri=".urlencode($this->facebook->config->canvasPage).'?'.$get_param;
		
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
				$this->request_oauth();
				exit;
			}

			echo "other error has happened";
			return false;  // error
		}
		return $decoded_response;  // success
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