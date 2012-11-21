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

	
	public function filters()
	{
		return array(
			
				'accessControl', // perform access control for CRUD operations
		);
	}
	
	
	public function accessRules()
	{
		return array(
			
				array('allow',  // allow all users to perform 'index' and 'view' actions
						'actions'=>array('privacypolicy','tos'),
						'users'=>array('@'),
				),
		);
	}
	
	/**
	 * This is the default 'index' action that is invoked
	 * when an action is not explicitly requested by users.
	 */
	public function actionIndex()
	{

		if(isset($_REQUEST['gameinst_id']) && is_numeric($_REQUEST['gameinst_id'])) {
			Yii::app()->session['gameinst_id'] = $_REQUEST['gameinst_id'];
		}

		$facebookUserId = Yii::app()->session['fbid'];

		$userId = Yii::app()->user->getId();

		$userEmail = Yii::app()->session['fb_email'];

		// if user session is not generated the session by redirecting user to application session generation page (by Pankaj Anupam)
		if(!is_numeric($userId) || Yii::app()->session['fbid'] != Yii::app()->user->getState('user_fbid') ) {

			$this->loginUser($facebookUserId, $userEmail);
		}

		// if user is coming from fabebook invited link
		// TODO: verify is in use if not remove
		if(isset(Yii::app()->session['gameinst_id'])) {
			$getUserFaceBookId = $facebookUserId;
			$gameId = Yii::app()->session['gameinst_id'];
			// check whether the fbuser is really invited for that game or not. If yes then book the seat and redirect him to game page
			//				$getValidInvitedUser = getValidInvitedUser($getUserFaceBookId,$gameId);
			$gameSeat = Zzgameseat::model()->findByAttributes(array('gameseat_user_id' => Yii::app()->user->id,'gameseat_gameinst_id' => $gameId));
		}
		
		// update user status and forword to play (by Pankaj Anupam)
		update_loggedin_user_status($facebookUserId,$userEmail);
		update_invitation_status($facebookUserId);
		CController::forward('/gameinst/play/');
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

	/**
	 * set user auth
	 * @param int $userid
	 * @param string $username
	 */
	//TODO: move this to facebook auth class
	public function loginUser($userid,$username){

		$this->_identity = new FacebookIdentity($userid,'');

		switch($this->_identity->authenticate()) {

			case FacebookIdentity::ERROR_UNKNOWN_IDENTITY:

				$this->redirect(array('/'));
				break;
					
			case FacebookIdentity::ERROR_NONE:

				Yii::app()->user->login($this->_identity);

				update_loggedin_user_status($userid,$username);
				update_invitation_status($userid);
					
				CController::forward('/gameinst/play/');

				break;
					
			default: // should not happen... just in case -> get out of here (see below)

				$this->redirect('/');
				break;
		}
	}

	// TODO: verify it is in use or not, remove if not in use
	public function actionFblogin() {

		if($this->_identity === null) {
			if((strlen($_GET['user_email']) > 0) && (strlen($_GET['user_fbid']) > 0)) {

				$username         = $_GET['user_email'];
				$userid           = $_GET['user_fbid'  ];
				//$access_token     = $this->getAccessToken($_GET['logout']);
				//$decoded_response = $this->getAccessTokenResponse($access_token);

				$this->_identity = new FacebookIdentity($userid,'');
				switch($this->_identity->authenticate()) {

					case FacebookIdentity::ERROR_UNKNOWN_IDENTITY:
						echo "error";
						//$this->redirect(array('/registration/fbregister', 'facebook' => $this->_identity->id));
						break;
							
					case FacebookIdentity::ERROR_NONE:
						//$this->_logouturl = $_GET['logout'];

						Yii::app()->user->login($this->_identity);
						// redirect login user to game board page
						if(isset(Yii::app()->session['gameinst_id'])) {
							$gameinst_id = Yii::app()->session['gameinst_id'];
							update_loggedin_user_status($userid,$username);
							update_invitation_status($userid);
							CController::forward("/gameinst/play"); //(Yii::app()->homeUrl.'?gameinst_id='.$gameinst_id);
							exit;
						} else {
							update_loggedin_user_status($userid,$username);
							update_invitation_status($userid);
							CController::forward("/gameinst/play"); //$this->redirect(Yii::app()->homeUrl.'gameinst/play?gameinst_id=0');
						}
						break;
							
					default: // should not happen... just in case -> get out of here (see below)
						CController::forward("/gameinst/play");
						break;
				}
			} else {
				// if we didn't get all required parameters (username, userid, logout) then just redirect to main page and pretend nothing happened
				$this->redirect(Yii::app()->homeUrl);
				return;
			}
		}
	}

	
	public function actionPrivacypolicy(){
		$this->layout = false;
		if(Yii::app()->request->isAjaxRequest){
			$this->render('ajax_privacypolicy');
		}else{
			$this->render('privacypolicy');
		}
		exit;
	}
	
	public function actiontos(){
		$this->layout = false;
		if(Yii::app()->request->isAjaxRequest){
			$this->render('ajax_tos');
		}else{
			$this->render('tos');
		}
		
		exit;
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
