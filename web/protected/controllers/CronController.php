<?php

class CronController extends Controller
{

	public $cron_id = 1;
	public $game_inst_tmpl_id = 'ZalerioTempl1';    // need to change this once multitemplate game will be started. Will have to make a function for the same.
	public function actionIndex()
	{
//		$timestampantipattern = 'Y-m-d H:i';
	//	$zlroRoundStartTime =  time();
	//	print $zlroRoundStartTime;
//	print  CTimestamp::formatDate($timestampantipattern,$zlroRoundStartTime);
//		exit;

		$get_cron_id = $this->cron_id;  // getting the cron id
		$get_cron_details = Zzcron::model()->findByPk($get_cron_id);
		$get_cron_last_update_time = $get_cron_details->getAttribute('update_time');
		$get_total_rounds = get_total_number_of_rounds($this->game_inst_tmpl_id);
		$currentTime = new CDbExpression('now()');
		$query = "select zlgameround_gameinst_id from zzzlrogameround where zlgameround_timeend >= "."'$get_cron_last_update_time'"." and zlgameround_timeend<= "."'$currentTime'"." and   zlgameround_roundname = "."'$get_total_rounds'"; // query for checking the game which is completed
		$get_completed_games = Zzzlrogameround::model()->findAllBySql($query);
		$user_array = array(); // array for storing users uid's
		if($get_completed_games) {	
		foreach($get_completed_games as $get_completed_game) { 
			$game_id =  $get_completed_game->zlgameround_gameinst_id;
			//print $game_id;
			$get_users_of_game = get_users_of_game($game_id); // getting the users of the game
			
			// iterating on the game to get the insert data on the user in game summary table 
			$update_game = Zzgameinst::model()->findByPk($game_id);
                        $update_game->game_complete = 1;
                        $update_game->save();
			
			if(count($get_users_of_game) > 0) {
			 	foreach($get_users_of_game as $user_id) {
					// get total games played by the user
					$total_games = get_total_count_of_games($user_id);
					// get total games won by the user
					$won_games = get_total_count_of_won_games($user_id);
					//get the level of the user
					$get_user_level = get_user_belt($total_games,$won_games);
					$check_for_user_existence = Zzgameusersummary::model()->findByAttributes(array('user_id'=>$user_id,'zz_gameinsttemp_id'=>$this->game_inst_tmpl_id));
					if(isset($check_for_user_existence)) {  // updating data for user if not in database
					$check_for_user_existence->zz_gameinsttemp_id = $this->game_inst_tmpl_id;
					$check_for_user_existence->user_id = $user_id;
					$check_for_user_existence->total_games_played = $total_games;
					$check_for_user_existence->total_game_won = $won_games;
					$check_for_user_existence->user_level = $get_user_level;
					$check_for_user_existence->save();
					} else {// inserting data for user id if not in database
					$save_summary_data = new Zzgameusersummary;
                                        $save_summary_data->zz_gameinsttemp_id = $this->game_inst_tmpl_id;
                                        $save_summary_data->user_id = $user_id;
                                        $save_summary_data->total_games_played = $total_games;
                                        $save_summary_data->total_game_won = $won_games;
                                        $save_summary_data->user_level = $get_user_level;
                                        $save_summary_data->save();	
					}
				}
			}
		}
		}
		$get_cron_details->save();
		
		
		$this->layout = false;
	//	$this->render('index');
	}

	// Uncomment the following methods and override them if needed
	/*
	public function filters()
	{
		// return the filter configuration for this controller, e.g.:
		return array(
			'inlineFilterName',
			array(
				'class'=>'path.to.FilterClass',
				'propertyName'=>'propertyValue',
			),
		);
	}

	public function actions()
	{
		// return external action classes, e.g.:
		return array(
			'action1'=>'path.to.ActionClass',
			'action2'=>array(
				'class'=>'path.to.AnotherActionClass',
				'propertyName'=>'propertyValue',
			),
		);
	}
	*/
}
