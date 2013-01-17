<?php

class DeauthoriseController extends Controller
{
	public function actionIndex()
	{
		
		list($encoded_sig, $payload) = explode('.', $_REQUEST["signed_request"], 2);
		$signedRequestData = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);
		
		$check_for_user_existence = Zzuser::model()->findByAttributes( array('user_fbid'=>$signedRequestData["user_id"]) );
		
		if(isset($check_for_user_existence)) {
			$check_for_user_existence->zzuser_status = 'invited';
			$check_for_user_existence->save();
			
			$update_query = "delete from zzrandomgame where user_fbid in('".$signedRequestData["user_id"]."')";
	
			$connection = Yii::app()->db;
			
			$command = $connection->createCommand($update_query);
			
			if($command->execute()){}
			
			Yii::log($signedRequestData["user_id"], CLogger::LEVEL_ERROR, 'User leave the game: Status changed to invited ');
		}
		
		foreach($signedRequestData as $key=>$val){
			Yii::log($key.'=>'.$val, CLogger::LEVEL_ERROR, 'Any category/label will work');
		}
	}
	
	public function actionUpdateinfo(){

		if ( $_SERVER['REQUEST_METHOD'] == 'GET' && isset($_GET['hub_mode']) && $_GET['hub_mode'] == 'subscribe' )  {

			echo $_GET['hub_challenge'];
		      
		  } else if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
		  	
		  	Yii::log('post call', CLogger::LEVEL_ERROR, 'update user name with data');
		  	
		    $post_body = file_get_contents('php://input');
		    
		    $obj = json_decode($post_body, true);
		    
		    $a = var_export($obj->entry,true);
		    
		    Yii::log($a, CLogger::LEVEL_ERROR, 'update user name with data');
		    		    
		    // $obj will contain the list of fields that have changed		    
		  }else{
		  	
		  	Yii::log('else part', CLogger::LEVEL_ERROR, 'else part run');
		  }
	}

	public function actionUnsubscribe()
	{	
		if( isset($_REQUEST['user_id']) ) {
			
			$usr = Zzuser::model()->findByAttributes( array('user_fbid'=>$_REQUEST['user_id']) );
			
			if(isset($usr)) {

				$email = urlencode($usr->user_email);
				$url = 'https://sendgrid.com/api/unsubscribes.add.json?email='.$email.'&api_user=zalerio&api_key=Cytzmlk1';
				$url = file_get_contents($url);
				$result = json_decode($url);
				
				if(isset($result->message) && $result->message == 'success'){

					echo '<h1>You have been successfully unsubscribed!</h1>';

					echo '<p>You have requested to be removed from our mailing list, which has been successful, <br />You will not receive any future correspondence from us.</p>';
				
				}else{
					echo '<h1>Unsubscribe failed please contact at support@zalerio.com.</h1>';
				}
				die();
			}
		}
	}
	
 	public function filters()
	{
		return array( 'accessControl');
	}
	
 	public function accessRules()
    {
        	return array(
        			array('allow',
        				  'actions'=>array('index','unsubscribe','updateinfo'),
        				  'users'=>array('*'),
        			),
        	);
    }
    
    
}