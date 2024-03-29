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
		    
		    $a = var_export($obj,true);
		    
		    Yii::log($a, CLogger::LEVEL_ERROR, 'Complete data********************');
		    try {
			    $b = var_export($obj['entry'][0]['uid'],true);
			    
			    Yii::log($b, CLogger::LEVEL_ERROR, 'user id*********************');
		    }catch (Exception $e){}   
		    
		    try{
		    	
		    	$user_id = $obj['entry'][0]['uid'];
		    	
		    	$_result = file_get_contents('http://graph.facebook.com/'.$user_id.'?fields=id,first_name,last_name');
		    	
		    	$_result = json_decode($_result, true);
		    	
		    	Yii::log(var_export($_result,true), CLogger::LEVEL_ERROR, 'result id*********************');
		    	
				$update_query = "update zzuser set user_fname = '".$_result['first_name']."' , user_lname = '".$_result['last_name']."' where user_fbid = '".$user_id."'";

				$connection=Yii::app()->db;
				
				$command=$connection->createCommand($update_query);
				
				if($command->execute()){
					Yii::log('data updated', CLogger::LEVEL_ERROR, '**********uid query: '.$update_query.' '.$user_id );
				}else{
					Yii::log('query fail', CLogger::LEVEL_ERROR, '##########uid'.$user_id );
				}
		    	
		    }catch (Exception $e){
		    	Yii::log($e, CLogger::LEVEL_ERROR, 'uid exp -'.$user_id );
		    }
		
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