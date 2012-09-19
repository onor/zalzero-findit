<?php
class SiteController extends Controller
{
	protected $_identity;
	/**
	 * Declares class-based actions.
	 */
	public function actions()
	{
		return array(
			// captcha action renders the CAPTCHA image displayed on the contact page
			'captcha'=>array(
				'class'=>'CCaptchaAction',
				'backColor'=>0xFFFFFF,
			),
			// page action renders "static" pages stored under 'protected/views/site/pages'
			// They can be accessed via: index.php?r=site/page&view=FileName
			'page'=>array(
				'class'=>'CViewAction',
			),
		);
	}
	
	/**
	 * This is the default 'index' action that is invoked
	 * when an action is not explicitly requested by users.
	 */
	public function actionIndex()
	{
		// renders the view file 'protected/views/site/index.php'
		// using the default layout 'protected/views/layouts/main.php'
		if(isset($_REQUEST['gameinst_id']) && is_numeric($_REQUEST['gameinst_id'])) {
                        Yii::app()->session['gameinst_id'] = $_REQUEST['gameinst_id'];
                }
		$facebook = Yii :: app()->params['facebook'];
		$myFbId = $facebook->getUser();
		$get_fbuser_status = get_fbuser_status($myFbId);
		$userId = Yii::app()->user->getId();
		$fql_query  =   array(
			'method' => 'fql.query',
			'query' => 'SELECT name, first_name, last_name, email FROM user WHERE uid = ' . $myFbId
			);   // getting the email id which is a extended permission
			$fql_info = $facebook->api($fql_query);
			$userEmail = $fql_info['0']['email'];  // fb logged in user email id
			$userFbName = $fql_info['0']['name']; // fb user name
			$userFbFirstName = $fql_info['0']['first_name']; // fb user first name
			$userFbLastName = $fql_info['0']['last_name']; // fb user last name
		$canvasUrl = $facebook->getCanvasPage();
			

		// if a user is a new user in game and is a non-invited user redirect him to the new user landing page. 	
		if($get_fbuser_status == 0) {
			//If required, then in the future we will enabl restricted entry into the system
			$urlForCreatingUser = "/user/createFbUser?Zzuser[user_email]=".$userEmail."&Zzuser[user_fbid]=".$myFbId."&Zzuser[user_fname]=".$userFbFirstName."&Zzuser[user_handle]=".$userEmail."&Zzuser[user_lname]=".$userFbLastName."&Zzuser[user_name]=".$userFbName;
			$this->redirect(array("$urlForCreatingUser"));
	//		echo("<script> top.location.href='" . $urlForCreatingUser. "'</script>");
			exit;	
		}


		// if user is present in database i.e is a registered user
		if($get_fbuser_status == 2) {
			// if user session is not generated the session by redirecting user to application session generation page 	
			if(!is_numeric($userId)) {
				$urlForGeneratingSession = "/site/fbLogin?user_email=$userEmail&user_fbid=$myFbId";
				$this->redirect(array("$urlForGeneratingSession"));
				//echo("<script> top.location.href='" . $urlForGeneratingSession . "'</script>");
                		exit;
			}
			// If user session is already present into the system	
	        if(is_numeric($userId)) {
				$get_user_id = get_user_id($myFbId);  // for solving the logout issue #120
				if($get_user_id != $userId) {
					$urlForGeneratingSession = "/site/fbLogin?user_email=$userEmail&user_fbid=$myFbId";
					$this->redirect(array("$urlForGeneratingSession"));
                    			exit;
				} else {
					update_loggedin_user_status($myFbId,$userEmail);
					update_invitation_status($myFbId);
				} 
			}
			
			// if user is coming from fabebook invited link
			if(isset(Yii::app()->session['gameinst_id'])) {
				$getUserFaceBookId = $myFbId;
				$gameId = Yii::app()->session['gameinst_id'];
				// check whether the fbuser is really invited for that game or not. If yes then book the seat and redirect him to game page
//				$getValidInvitedUser = getValidInvitedUser($getUserFaceBookId,$gameId);
				$gameSeat = Zzgameseat::model()->findByAttributes(array('gameseat_user_id' => Yii::app()->user->id,'gameseat_gameinst_id' => $gameId));
				if(isset($gameSeat)) {
					update_loggedin_user_status($myFbId,$userEmail);
					update_invitation_status($myFbId);
					unset(Yii::app()->session['gameinst_id']);
					$this->redirect(array("/gameinst/play?gameinst_id=$gameId"));
					exit;
				} else {  // if not invited then redirect him to a new game
					unset(Yii::app()->session['gameinst_id']);
					update_loggedin_user_status($myFbId,$userEmail);
					update_invitation_status($myFbId);
			        	$this->redirect(array("gameinst/play?gameinst_id=0"));
					exit;
				}
			}
		  
			$nowTime = new CDbExpression('now()');
            		$gameInstList = Zzgameinst::model()->with('gameinstProduct')->findAll('gameinst_starttime > :nowTime', array(':nowTime'=>$nowTime));
			//$this->render('index',array('gameInstList'=>$gameInstList));
			$this->redirect(array("gameinst/play?gameinst_id=0"));
		} 
	}

	/**
	 * This is the action to handle external exceptions.
	 */
	public function actionError()
	{
		$this->layout = false;
	    if($error=Yii::app()->errorHandler->error)
	    {   
	    	if(Yii::app()->request->isAjaxRequest){                    
                echo $error['message'];
            } else {
				$this->render('error', $error);
			}	        	
	    }
	}

	/**
	 * Displays the contact page
	 */
	public function actionContact()
	{
		$model=new ContactForm;
		if(isset($_POST['ContactForm']))
		{
			$model->attributes=$_POST['ContactForm'];
			if($model->validate())
			{
				$headers="From: {$model->email}\r\nReply-To: {$model->email}";
				mail(Yii::app()->params['adminEmail'],$model->subject,$model->body,$headers);
				Yii::app()->user->setFlash('contact','Thank you for contacting us. We will respond to you as soon as possible.');
				$this->refresh();
			}
		}
		$this->render('contact',array('model'=>$model));
	}
	

	/**
	 * Displays the login page
	 */
	public function actionLogin()
	{
		$model=new LoginForm;

		// if it is ajax validation request
		if(isset($_POST['ajax']) && $_POST['ajax']==='login-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}

		// collect user input data
		if(isset($_POST['LoginForm']))
		{
			$model->attributes=$_POST['LoginForm'];
			// validate user input and redirect to the previous page if valid
			if($model->validate() && $model->login()) {
				$this->redirect(Yii::app()->user->returnUrl);
			}
		}
		// display the login form
		$this->render('login',array('model'=>$model));
	}
        
	public function actionFblogin() {
		
		if($this->_identity === null) {
			if((strlen($_GET['user_email']) > 0) && (strlen($_GET['user_fbid']) > 0)) {
				$username         = $_GET['user_email'];
				$userid           = $_GET['user_fbid'  ];
				//$access_token     = $this->getAccessToken($_GET['logout']);
				//$decoded_response = $this->getAccessTokenResponse($access_token);

				$this->_identity = new FacebookIdentity($username, $userid);
				switch($this->_identity->authenticate()) {
					
					case FacebookIdentity::ERROR_UNKNOWN_IDENTITY:
						$this->redirect(array('/registration/fbregister', 'facebook' => $this->_identity->id));
						break;
					
					case FacebookIdentity::ERROR_NONE:
						//$this->_logouturl = $_GET['logout'];
						$duration = 3600*24*30;
						Yii::app()->user->login($this->_identity, $duration);
						// redirect login user to game board page
						if(isset(Yii::app()->session['gameinst_id'])) {
							$gameinst_id = Yii::app()->session['gameinst_id'];
						        update_loggedin_user_status($userid,$username);	
							update_invitation_status($userid);
							unset(Yii::app()->session['gameinst_id']);
							$this->redirect(Yii::app()->homeUrl.'?gameinst_id='.$gameinst_id);
							exit;
						} else {
							update_loggedin_user_status($userid,$username);
							update_invitation_status($userid);
							$this->redirect(Yii::app()->homeUrl.'gameinst/play?gameinst_id=0');
						}
						break;
					
					default: // should not happen... just in case -> get out of here (see below)
						$this->redirect('/');
						break;
				}
			} else {
				// if we didn't get all required parameters (username, userid, logout) then just redirect to main page and pretend nothing happened
				$this->redirect(Yii::app()->homeUrl);
				return;
			}
		}
	}

	/**
	 * Logs out the current user and redirect to homepage.
	 */
	public function actionLogout()
	{
		Yii::app()->user->logout();
		//$this->redirect(Yii::app()->homeUrl);
	}
	
	/**
	 * Close other tabs if user try to open more then one tab
	 */
	public function actionClosesession()
	{
		$this->layout = false;
		$this->render('logout');
	}
}
