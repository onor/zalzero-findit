<?php

class UserController extends Controller
{
	/**
	 * @var string the default layout for the views. Defaults to '//layouts/column2', meaning
	 * using two-column layout. See 'protected/views/layouts/column2.php'.
	 */
	public $layout='//layouts/column2';
	public $prefinary_api_key = 'd9a2f4ec5802909510d801079cbf9aabcad46655';
	public $game_inst_tmpl_id = 'ZalerioTempl1';
	public $decode_key;

	function __construct($id, $module = null)
	{
		parent::__construct($id, $module);

		if(Yii::app()->request->serverName == "localhost" ||
		Yii::app()->request->serverName == "zl.mobicules.com")    // Active more button for staging and developers
		{
			$this->decode_key = 'e499493a0c1f970faabef8d1bfda725dc4766a8c'; // staging decode key
		}else{
			$this->decode_key = '36343cb7ac8fe16281aca9bdae86277df86cbd87';
		}
	}

	/**
	 * @return array action filters
	 */
	public function filters()
	{
		return array(
				'accessControl', // perform access control for CRUD operations
		);
	}

	/**
	 * Specifies the access control rules.
	 * This method is used by the 'accessControl' filter.
	 * @return array access control rules
	 */
	public function accessRules()
	{
		return array(
		array('allow',  // allow all users to perform 'index' and 'view' actions
						'actions'=>array('index','view','create','createFbUser','fbCheckLoginAjax','verifyUser','testQuery','getFriend','inviteFriend','friendFun','getFbFriendsList'),
						'roles'=>array('admin'),
		),
		array('allow',
						'actions'=>array('create','createFbUser','fbCheckLoginAjax','verifyUser','remindUser','acceptInvite','inviteUser','testQuery','decode_key','getFriend','friendFun','getFbFriendsList'),
						'users'=>array('*'),
		),
		array('allow', // allow authenticated user to perform 'create' and 'update' actions
						'actions'=>array('update','myGames','inviteFriend','getFbFriendsList','waiting_users_remove','waiting_users','waiting_users_status_change','getActiveMember','friendFun'),
						'users'=>array('@'),
		),
		array('allow', // allow admin user to perform 'admin' and 'delete' actions
						'actions'=>array('admin','delete'),
						'roles'=>array('admin'),
		),
		array('deny',  // deny all users
						'users'=>array('*'),
		),
		);
	}

	/**
	 * Displays a particular model.
	 * @param integer $id the ID of the model to be displayed
	 */
	public function actionView($id)
	{
		$this->render('view',array(
				'model'=>$this->loadModel($id),
		));
	}

	/**
	 * Creates a new model.
	 * If creation is successful, the browser will be redirected to the 'view' page.
	 */
	public function actionCreate()
	{
		$model=new Zzuser;

		// Uncomment the following line if AJAX validation is needed
		// $this->performAjaxValidation($model);

		if(isset($_POST['Zzuser']))
		{

			/*
			 * end of setting primary key
			 */
			//print_r($_POST['Zzuser']);
			//break;
			$model->attributes=$_POST['Zzuser'];
			$model->user_role_id = '2';
			//user handle can be FB, Normal signup, or other authentication method
			//$model->user_handle = "Normal";
			//password encryption
			if($model->validate()){
				$model->user_password = md5($model->user_password);
				//user role id 2 is for customer

				if($model->save()){
					//$this->redirect(array('view','id'=>$model->user_id));
					//authenticating the newly created user for login
					$loginModel=new LoginForm;
					// collect user input data
					$loginModel->username = $model->user_email;
					$loginModel->password = $_POST['Zzuser']['user_password'];

					if($loginModel->validate() && $loginModel->login()){
						//$this->redirect(Yii::app()->user->returnUrl);
						$this->redirect (array('site/index'));
					}
				}
			}
		}

		$this->render('create',array(
				'model'=>$model,
		));
	}

	/**
	 * Updates a particular model.
	 * If update is successful, the browser will be redirected to the 'view' page.
	 * @param integer $id the ID of the model to be updated
	 */
	public function actionUpdate($id)
	{
		$model=$this->loadModel($id);

		// Uncomment the following line if AJAX validation is needed
		// $this->performAjaxValidation($model);

		if(isset($_POST['Zzuser']))
		{
			$model->attributes=$_POST['Zzuser'];
			if($model->validate()){
				$model->user_password = md5($model->user_password);
				if($model->save()){
					$this->redirect(array('view','id'=>$model->user_id));
				}
			}
		}

		$this->render('update',array(
				'model'=>$model,
		));
	}

	public function actionFbCheckLoginAjax(){
		if (Yii::app()->request->isAjaxRequest) {
			//$res = array('userValid'=>true,'user_email'=>$_REQUEST['user_email'],'name'=>"mohd waqar");
			$userEmail = '';
			$user_fbid = '';
			$userValid = false;
			$fbIdFoundInDb = false;
			$fbIdNotFoundInDbButEmailExist = false;
			$userModelWithFb = Zzuser::model()->findByAttributes(array('user_fbid'=>$_REQUEST['user_fbid']));
			if(isset($userModelWithFb)){
				$getUserId =  yii::app()->user->getId();
				/* commenting this code for beta but it will be in use when the site in production. Not deleting this because it will be useful in future */
				/*
				 $inviteBy = Zzinvitefriends::model()->findAllByAttributes(array('fbid'=>$_REQUEST['user_fbid']));
				 if(!empty($inviteBy)){
				 foreach($inviteBy as $inviteFriend){
				 $iFriends = new Zzzlrofriends;
				 $iFriends->user_id = $getUserId;
				 $iFriends->friend_id = $inviteFriend->id;
				 //check for existence. Dont save if its already in db
				 $checkForExistence = Zzzlrofriends::model()->findByAttributes(array('user_id'=>"'$getUserId'",'friend_id'=>"'$inviteFriend->id'"));
				 if(empty($checkForExistence)) {
				 $iFriends->save();
				 }
				 }
				 foreach($inviteBy as $inviteFriend){
				 $iFriends = new Zzzlrofriends;
				 $iFriends->user_id = $inviteFriend->id;
				 $iFriends->friend_id = $getUserId;
				 //check for existence. Dont save if its already in db
				 $checkForExistenceData = Zzzlrofriends::model()->findByAttributes(array('user_id'=>"'$inviteFriend->id'",'friend_id'=>"'$getUserId'"));
				 if(empty($checkForExistenceData)) {
				 $iFriends->save();
				 }
				 }
				 Zzinvitefriends::model()->deleteAllByAttributes(array('fbid'=>$_REQUEST['user_fbid']));
				 }
				 */
				$fbIdFoundInDb = true;
				$userValid = true;
				$userEmail = $userModelWithFb->user_email;
				$user_fbid = $userModelWithFb->user_fbid;
			}else{
				//In case user is only web user and not connected to FB right now .. but has authenticated FB for OLO
				$userModelWithoutFb = Zzuser::model()->findByAttributes(array('user_email'=>$_REQUEST['user_email']));
				if(isset($userModelWithoutFb)){
					$userModelWithoutFb->user_fbid = $_REQUEST['user_fbid'];
					$fbIdNotFoundInDbButEmailExist = true;
					if($userModelWithoutFb->save()){
						$userValid = true;
						$userEmail = $userModelWithoutFb->user_email;
						$user_fbid = $userModelWithoutFb->user_fbid;
					}
				}

			}
			$res = array('userValid'=>$userValid,'user_mail'=>$userEmail,
					'user_fbid'=>$user_fbid,'fbidFound'=>$fbIdFoundInDb,
					'fbIdNotFoundInDbButEmailExist'=>$fbIdNotFoundInDbButEmailExist,
			);

			echo CJSON::encode($res);
			Yii::app()->end();
		}

	}

	public function  actionCreateFbUser(){
		$model=new Zzuser;

		if(isset($_REQUEST['Zzuser']))     // changing the parameter from Request to POST as passing the values in url
		{
			$model->attributes=$_REQUEST['Zzuser'];
			$model->zzuser_status = 'active';
			$model->user_password = md5(uniqid('',true));
			//user role id 2 is for customer
			$model->user_role_id = '2';
			if($model->save()){
				/* An entry should be made for each Game Template Id */
				/* For now hard coding it to one template id */
				$userGameSummaryModel = new Zzgameusersummary;
				$userGameSummaryModel->zz_gameinsttemp_id = $this->game_inst_tmpl_id;
				$userGameSummaryModel->user_id = $model->user_id;
				$userGameSummaryModel->total_games_played = 0;//At the start Games played is 0
				$userGameSummaryModel->total_game_won = 0;//At the start Games Won is 0
				$userGameSummaryModel->user_level = 1;//At the start the Level is 1
				$userGameSummaryModel->save();

				$urlForGeneratingSession = "/site/fbLogin?user_email=$model->user_email&user_fbid=$model->user_fbid";
				$this->redirect(array("$urlForGeneratingSession"));

				exit;
			}
		}
	}

	public function actionMyGames(){
		$currentTime = new CDbExpression('now()');
		$games = Zzgameseat::model()->with(array('gameseatGameinst'=>array('condition'=> 'gameinst_endtime > ' . $currentTime) ,'gameseatGameinst.gameinstProduct' ))->findAllByAttributes(array('gameseat_user_id'=>  Yii::app()->user->id));

		$this->render('myGames', array('games'=>$games));

	}


	/*
	 * To be deleted used for testing queries.
	 */
	public function actionTestQuery(){
		$userModelWithFb = Zzuser::model()->findByAttributes(array('user_fbid'=>$_REQUEST['user_fbid']));
		if(isset($userModelWithFb)){
			echo "fbid Found " . $userModelWithFb->user_fbid;
		}else{
			$userModelWithoutFb = Zzuser::model()->findByAttributes(array('user_email'=>$_REQUEST['user_email']));
			if(isset($userModelWithoutFb)){
				print_r($userModelWithoutFb->attributes);
			}
		}
	}

	/**
	 * Deletes a particular model.
	 * If deletion is successful, the browser will be redirected to the 'admin' page.
	 * @param integer $id the ID of the model to be deleted
	 */
	public function actionDelete($id)
	{
		if(Yii::app()->request->isPostRequest)
		{
			// we only allow deletion via POST request
			$this->loadModel($id)->delete();

			// if AJAX request (triggered by deletion via admin grid view), we should not redirect the browser
			if(!isset($_GET['ajax']))
			$this->redirect(isset($_POST['returnUrl']) ? $_POST['returnUrl'] : array('admin'));
		}
		else
		throw new CHttpException(400,'Invalid request. Please do not repeat this request again.');
	}

	/**
	 * Lists all models.
	 */
	public function actionIndex()
	{
		$dataProvider=new CActiveDataProvider('Zzuser');
		$this->render('index',array(
				'dataProvider'=>$dataProvider,
		));
	}

	/**
	 * Manages all models.
	 */
	public function actionAdmin()
	{
		$model=new Zzuser('search');
		$model->unsetAttributes();  // clear any default values
		if(isset($_GET['Zzuser']))
		$model->attributes=$_GET['Zzuser'];

		$this->render('admin',array(
				'model'=>$model,
		));
	}

	/**
	 * Returns the data model based on the primary key given in the GET variable.
	 * If the data model is not found, an HTTP exception will be raised.
	 * @param integer the ID of the model to be loaded
	 */
	public function loadModel($id)
	{
		$model=Zzuser::model()->findByPk($id);
		if($model===null)
		throw new CHttpException(404,'The requested page does not exist.');
		return $model;
	}

	/**
	 * Performs the AJAX validation.
	 * @param CModel the model to be validated
	 */
	protected function performAjaxValidation($model)
	{
		if(isset($_POST['ajax']) && $_POST['ajax']==='zzuser-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}

	/**
	 * Performs the AJAX validation for prefinary user
	 * written by pankaj anupam
	 */
	public function actionverifyUser(){
		// process when ajax request
		if (Yii::app()->request->isAjaxRequest) {    //
			$user = Zzuser::model()->findAll('user_email = :userEmail', array(':userEmail'=>$_REQUEST['user_email']));
			if(!empty($user)){
				echo 'true'; exit;
			} else {
				if(isset($_SESSION['get_code'])) {
					if(trim($_SESSION['get_code']) == sha1($this->decode_key.trim($_REQUEST['user_email']))){
						//$this->addError('invitation_code','Incorrect Invitation Code.');
						echo 'true';
						exit;
					} else {
						echo 'false';
						exit;
					}
					unset($_SESSION['get_code']);
				} else {

					$getFbUid = Zzinvitefriends::model()->findAll('fbid = :userFbId',array(':userFbId'=>$_REQUEST['user_fbid']));
					if(!empty($getFbUid)) {
						echo 'true'; exit;
					} else {
						echo 'false';
						exit;
					}
				}
	  }
		}
	}
	// function to remid the user of the game. A message will be send to the user who is getting reminded
	public function actionremindUser() {
		$nonActiveMember = array();
		if (Yii::app()->request->isAjaxRequest) {

			$userData = $_REQUEST['user_data'];  // get the list of users of the game
			$gameId = $_REQUEST['game_id'];  // get the game id

			$loggedInFbId = Yii::app()->user->getState('user_fbid');
			$fbDataArray = array();
			$fbDataPlayed = array();
			$currentUserStatus = 0;
			if(count($userData) > 0) {  // if more than one users are there iterate on them
				foreach($userData as $key => $value) {   //  parsing the list of users who have accepted
					$fbId = $key;
					$userGssValue = $value['GSS'];
					$userCrsvalue = $value['CRS'];
					if($fbId == $loggedInFbId) { // status of the logged in use user. If he had played the bets or not. This is used in message
						if($userGssValue == 2 && $userCrsvalue == 9) {
							$currentUserStatus = 1;
						} else {
							$currentUserStatus = 0;
						}
					}
					if($userGssValue == 2) {
						$get_user_name = get_user_fb_name($fbId);
						$fbDataArray[] = $get_user_name;
					}
					if($userGssValue == 2 && $userCrsvalue == 9) {
						$get_user_name = get_user_fb_name($fbId);
						$fbDataPlayed[] = $get_user_name;
					}
				}
				// if reminder is sent from left hud
				if(isset($_POST['lh_users_data']) && $_POST['lh_users_data']!=0) {

					foreach($_POST['lh_users_data'] as $key => $value) {
						$fbData = $key;
						$gssValue = $value['GSS']; // get the gss value of the user
						$crsValue = $value['CRS']; // get the cres value of the user
						// We have to remind the user if they are in invited and accepted status. In case of accepted status we need to check there CRS status
						// IF GSS == 1 the they are invited. If GSS == 2 the we will only send the invitation if there CRS status is 0
						if($gssValue == 1) {
							$_result = FALSE;
							$_result = remindUserOnFb($gameId,$fbData,$fbDataArray,'invited',$currentUserStatus);  // invited paramere to for the message changes
							if($_result){
								$nonActiveMember[] = $_result;
							}
						} else {
							if($gssValue == 2 && $crsValue == 0) {
								$_result = FALSE;
								$_result = remindUserOnFb($gameId,$fbData,$fbDataPlayed,'turnremaining',$currentUserStatus);  // turnremaining  parameter for message changes
								if($_result){
									$nonActiveMember[] = $_result;

								}
							}
						}
					}
		  } else { // if reminder is sent from right hud
		  	foreach($userData as $key => $value) {
		  		$fbData = $key;
		  		$gssValue = $value['GSS']; // get the gss value of the user
		  		$crsValue = $value['CRS']; // get the cres value of the user
		  		// We have to remind the user if they are in invited and accepted status. In case of accepted status we need to check there CRS status
		  		// IF GSS == 1 the they are invited. If GSS == 2 the we will only send the invitation if there CRS status is 0
		  		if($gssValue == 1) {
		  			$_result = FALSE;
		  			$_result = remindUserOnFb($gameId,$fbData,$fbDataArray,'invited',$currentUserStatus);  // invited paramere to for the message changes
		  			if($_result){
		  				$nonActiveMember[] = $_result;
		  			}
		  			 
		  		} else {
		  			if($gssValue == 2 && $crsValue == 0) {
		  				$_result = FALSE;
		  				$_result = remindUserOnFb($gameId,$fbData,$fbDataPlayed,'turnremaining',$currentUserStatus);  // turnremaining  parameter for message changes
		  				 
		  				if($_result){
		  					$nonActiveMember[] = $_result;
		  				}
		  			}
		  		}
		  	}
		  }
			}
			if(!empty($nonActiveMember)){
				print_r( implode(',', $nonActiveMember) );
			}else{
				echo false;
			}
		}
	}
	// function to update the invitation status of user
	public function actionacceptInvite() {
		if (Yii::app()->request->isAjaxRequest) {
			if(isset($_REQUEST['game_id']) && is_numeric($_REQUEST['game_id'])) {
				$user_id = Yii::app()->user->id;
				$gameId = $_REQUEST['game_id'];  // get the game id
				if((is_numeric($gameId)) && $gameId > 0) {
					update_game_seat_status($user_id,$gameId);
				}
			}
		}
	}

	public function actiongetActiveMember(){
		if (Yii::app()->request->isAjaxRequest) {

			$FBid_array = $_REQUEST['usersID'];

			$user_FBids = implode('\',\'',$_REQUEST['usersID']);
			$query = "select user_fbid from zzuser where zzuser_status = 'active' and user_fbid in ('$user_FBids')";
			$users = Zzuser::model()->findAllBySql($query);

			$activeMembers = array();

			if($users){

				foreach($users as $user){

					if( ( $key = array_search( $user->user_fbid, $FBid_array ) ) !== false ) {

						unset($FBid_array[$key]);
					}
				}

			}

			$non_active_member = implode(',', $FBid_array);

			print_r( $non_active_member );
			exit;

		}
	}
	
	function actionWaiting_users_remove(){
			if( isset($_REQUEST['usersFBID']) ){
				
					$users_FBid = $_REQUEST['usersFBID'];

					$update_query = "DELETE FROM zzrandomgame where user_fbid in('".$users_FBid."')";

					$connection=Yii::app()->db;
					
					$command=$connection->createCommand($update_query);
					
					if($command->execute()){}
					
			}
	}
	
	function actionWaiting_users_status_change(){
		
			if( isset($_REQUEST['usersFBID']) ){
				
					$users_FBid = explode(',', $_REQUEST['usersFBID']);
					
					$update_query = "update zzrandomgame set status = TRUE where user_fbid in('".$users_FBid[0]."')";

					$connection=Yii::app()->db;
					
					$command=$connection->createCommand($update_query);
					
					if($command->execute()){}
			}
	}
	
	/** find user for random game **/
	function actionWaiting_users(){
		//if (Yii::app()->request->isAjaxRequest) {
			
			$waiting_users = false;
			//$FBid = $_REQUEST['userFBID'];
			
			$FBid = Yii::app()->session['fbid'];;
			$query = "select user_fbid from zzrandomgame where status = TRUE and user_fbid != '".$FBid."' order by create_time LIMIT 1 offset 0";
			$users = Zzrandomgame::model()->findAllBySql($query);

			if($users){
				foreach($users as $user){
					$users_FBid[] = $user->user_fbid;
				}
				
				if( sizeof($users_FBid) == 1 ){
					
					$waiting_users = implode(',',$users_FBid);
					
					// update user status
					$update_query = "update zzrandomgame set status = FALSE where user_fbid in('".$users_FBid[0]."')";

					$connection=Yii::app()->db;
					
					$command=$connection->createCommand($update_query);
					
					if($command->execute()){}
							
				}else{
					$check_user = "select user_fbid from zzrandomgame where user_fbid = '".$FBid."'";
					$users_exist = Zzrandomgame::model()->findAllBySql($check_user);

					if(!$users_exist){ 
						$add_waiting_user = new Zzrandomgame;
						$add_waiting_user->status = TRUE;
						$add_waiting_user->user_id = Yii::app()->user->getId();
						$add_waiting_user->user_fbid = $FBid;
						$add_waiting_user->save();
					}	
				}
			}else{
				$check_user = "select user_fbid from zzrandomgame where user_fbid = '".$FBid."'";
				$users_exist = Zzrandomgame::model()->findAllBySql($check_user);
				
				if(!$users_exist){
					$add_waiting_user = new Zzrandomgame;
					$add_waiting_user->status = TRUE;
					$add_waiting_user->user_id = Yii::app()->user->getId();
					$add_waiting_user->user_fbid = $FBid;
					$add_waiting_user->save();
				}	
			}

			print_r( $waiting_users );
			exit;
		//}
	}
	
	

	/**
	 * Get friend list
	 * written by pankaj anupam
	 */
	public function actiongetFriend(){
		// process when ajax request
		if (Yii::app()->request->isAjaxRequest) {

			$this->layout = false;
			//  $this->render('inviteFriends',array('users'=>$user));
			$this->render('inviteFriends');

		}
	}

	/**
	 * Send invitaton mail
	 * written by pankaj anupam
	 */
	public function actioninviteFriend(){
		// Removing this action right now due to new flow change. Now instead of sending email to users notification will be send to them on facebook.
		if (Yii::app()->request->isAjaxRequest) {
			if(empty($_REQUEST['friendId'])){
				echo 'Please select a friend';
				exit;
			}
			$user_id = implode('\',\'',$_REQUEST['friendId']);
			$query = "select * from zzuser where user_id in ('$user_id')";
			$users = Zzuser::model()->findAllBySql($query);

			if(!empty($users)){
				$headers = "MIME-Version: 1.0" . "\r\n";
				$headers .= "Content-type:text/html;charset=iso-8859-1" . "\r\n";
				$headers .= 'From: zalzero@zalzero.com' . "\r\n";


				foreach($users as $user){
					// emal body
					$body ='<a href="'.Yii::app()->getBaseUrl(true).'/gameseat/bookSeat?Zzgameseat[gameseat_user_id]='.$user->user_id.'&Zzgameseat[gameseat_gameinst_id]='.$_REQUEST['gameseat_gameinst_id'].'">Click Here</a> to play game';
					if(! mail($user->user_email,'Zalzero Game invitation',$body, $headers)){
						echo "Unable to send email";
						exit;
					}
				}
				echo 'done';
			}else{
				echo 'Please select a friend';
			}
		}
	}




	/**
	 * Get facebook frined and send mail
	 * written by pankaj anupam
	 */
	public function actionfriendFun(){ //print_r($_REQUEST['fbUserData']);
		// process when ajax request
		if (Yii::app()->request->isAjaxRequest) {
			$this->layout = false;
			$this->render('friendFun');
		}
	}


	/** functioin by mobicules to create prefinary account once friends are invited **/

	public function actiongetFbFriendsList() {
		// process when ajax request
		if (Yii::app()->request->isAjaxRequest) {

			$canvasUrl = '' ; //

			$fbUserFriends = $_REQUEST['fbUserData'];
			$userId = Yii::app()->user->getId();

			$getLoggedinUserEmail = getUserEmailId($userId);
			$gameId = $_REQUEST['gameseat_gameinst_id'];

			foreach ($fbUserFriends as $fbUser) {
				$name = $fbUser['name'];
				$id = $fbUser['uid'];
				$email = $id.'@zalerio.com';
				if($fbUser['username'] != '') {
					$fbEmail = $fbUser['username'].'@facebook.com';
				} else {
					$fbEmail = $fbUser['uid'].'@facebook.com';
				}
				$saveFriends = new Zzinvitefriends;
				$saveFriends->id = $userId;
				$saveFriends->fbid = $fbUser['uid'];
				$saveFriends->gameid = $gameId;
				$checkForExistenceData = Zzinvitefriends::model()->findByAttributes(array('id'=>"$userId",'fbid'=>$id,'gameid'=>$gameId));

				$headers = "MIME-Version: 1.0" . "\r\n";
				$headers .= "Content-type:text/html;charset=iso-8859-1" . "\r\n";
				$headers .= "From: $getLoggedinUserEmail" . "\r\n";

				$body ='<a href="'.$canvasUrl.'/?gameinst_id='.$gameId.'">Zalerio game Invitation</a>';
				if(!strstr(Yii::app()->getBaseUrl(true),"localhost")){
					mail($fbEmail,'Zalerio',$body, $headers);
				}

				if(empty($checkForExistenceData)) {
					$saveFriends->save();
				}

				create_prefinery_applied_account($email,$name,'invited');

				/*        $command = "<?xml version='1.0' encoding='UTF-8'?><tester><email>".$email."</email><invitation-code>bAbA1934sInGh</invitation-code><status>invited</status><profile><first-name>".$name."</first-name><last-name></last-name><employer></employer><jobtitle></jobtitle><address-line1></address-line1><address-line2></address-line2><city></city><state></state><postal-code></postal-code><country></country><telephone></telephone></profile></tester>";

				$curl_command = 'curl --header "Content-Type: text/xml" --data "'.$command.'" http://mobicules.prefinery.com/api/v1/betas/3092/testers?api_key='.$this->prefinary_api_key;
				$json = shell_exec($curl_command);
				*/

			}
		}
	}
}
