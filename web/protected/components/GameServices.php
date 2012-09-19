<?php

// game service to create a new game 

function createNewGame($gameData) {

 $model=new Zzgameinst;
	 
            if (Yii::app()->request->isAjaxRequest){
                // Uncomment the following line if AJAX validation is needed
	                // $this->performAjaxValidation($model);
        if(isset($gameData))
                {
                            $transaction = Yii::app()->db->beginTransaction();
                            $flag_error = false;
			    $loggedInUserId = Yii::app()->user->getId();
                            $gameInstTmpl = ZzGameInstTempl::model()->findByPk($gameData['gameinst_gameinsttempl_id']);
                            $model->attributes = $gameInstTmpl->attributes;
                            $model->attributes = $gameData;

                            $model->gameinst_starttime  = date('Y-m-d H:i:s');
                            $model->gameinst_endtime  = date('Y-m-d H:i:s',strtotime("+28 days"));
                            $model->gameinst_entrytime  = date('Y-m-d H:i:s');
			    $model->gameinst_created_by = $loggedInUserId; 	
                            if(!empty($model->gameinst_starttime)){
                                $model->addError($model->gameinst_starttime, "Start time NOT defined!");
                            }

                            if(!empty($model->gameinst_endtime)){
                                $model->addError($model->gameinst_endtime, "End time NOT defined!");
                            }

                            if($model->gameinst_endtime < $model->gameinst_starttime){
                                $model->addError($model->gameinst_starttime, "start time can not be greater than end time");
                            }
                            if(!isset($model->gameinst_threshold)){
                                $model->addError($model->gameinst_threshold, "Please specify a game seat threshold limit");
                            }
			   //$gameInstGamelId = $gameData['Zzgameinst']['gameinst_game_id'];
                            //$gammeInstTmpl = ZzGameInstTempl::model()->fin

                            if($model->save()){
				// updating user current game status to NULL
				$update_users_current_game = Zzgameusersummary::model()->findByAttributes(array('user_id'=>$loggedInUserId));
                                        if(isset($update_users_current_game)) {  // updating data for user if not in database
                                        $update_users_current_game->current_gameinst_id = NULL;
                                        $update_users_current_game->save();
					}
                                /* Saving specifics for Zalerio Game */
                                if($model->gameinst_game_id == 'ZALERIO_1'){
                                    /**
                                     * 
                                     * gIParamNum1 : noOfColumns
                                     * gIParamNum2 : noOfRows
                                     * gIParamNum3 : noOfBets
                                     * gIParamNum4 : noOfRounds
                                     * gIParamNum5 : noOfFigs
                                     * gIParamVar1 : oloZlroFigGroupId
                                     */
                                    $currentGameInstMap = array();
                                    $currentGameInstMap['noOfColumns'] = $model->gameinst_paramnum1 ;
                                    $currentGameInstMap['noOfRows'] = $model->gameinst_paramnum2 ;
                                    $currentGameInstMap['noOfBets'] = $model->gameinst_paramnum3 ;
                                    $currentGameInstMap['noOfRounds'] = $model->gameinst_paramnum4 ;
                                    $currentGameInstMap['noOfFigures'] = $model->gameinst_paramnum5 ;
                                    $currentGameInstMap['zlfiggroup_id'] = $model->gameinst_paramvar1 ;

                                    $currentZlroFigGroup = Zzzlrofiggroup::model()->findByPk($currentGameInstMap['zlfiggroup_id']);

                                    $oloZlroFigInstReqList = Zzzlrofiginst::model()->with(array('zlfiginstZlfig'=>array('condition'=>'zlfig_zlfiggroup_id = \'' . $currentGameInstMap['zlfiggroup_id'] . '\' and zlfig_required = 1')))->findAll();

                                    $oloZlroFigInstBasicList = Zzzlrofiginst::model()->with(array('zlfiginstZlfig'=>array('condition'=>'zlfig_zlfiggroup_id = \'' . $currentGameInstMap['zlfiggroup_id'] . '\' and zlfig_required != 1')))->findAll();

                                    $zlroFigInstBasicListCount = $currentGameInstMap['noOfFigures'] - $currentZlroFigGroup->zlfiggroup_nooffigrequired ;
				   $currentFigInstForBasicList = array();
                                    foreach($oloZlroFigInstBasicList as $v){
                                        $currentFigInstForBasicList[count ($currentFigInstForBasicList)] = clone $v;
                                    }

                                    $currentFigInstForReqList = array();
                                    foreach($oloZlroFigInstReqList as $v){
                                        $currentFigInstForReqList[count ($currentFigInstForReqList)] = clone $v;
                                    }

                                    $tempCtr = 0;
                                    $zlroFigInstList = array();
                                    $zlroFigInstListCtr = 0;
                                    $zlroFigInstBasicListCtr = 0;
                                    $zlroFigInstReqListCtr = 0;

                                    echo $currentGameInstMap['noOfFigures'];
                                    echo $zlroFigInstBasicListCount;
                                    while($zlroFigInstListCtr < $currentGameInstMap['noOfFigures']){
                                        $tempCtr++;
                                        $flag_listTypeCtr = '';
                                        $randomListEntry = null;

                                        /* For non-required figures (they will appear once per their original figure i.e. only one orientation per figure */
                                        if($zlroFigInstBasicListCtr < $zlroFigInstBasicListCount){
                                            echo "Inside Basic";
                                            $flag_listTypeCtr = 'zlroFigInstBasicListCtr';
                                            if(($cft = count ($currentFigInstForBasicList)) > 0){
                                                $randomNum = mt_rand(0, $cft -1 );

                                                $randomListEntry = $currentFigInstForBasicList[$randomNum];

                                                printmodel($randomListEntry);

                                                $basicListCtr = 0;
                                                $removedListCtr = 0;
						$newFigInstForBasicList = array();
                                                foreach($currentFigInstForBasicList as $currentFigInstForBasicEntry){
                                                    $basicListCtr++;
                                                    if($currentFigInstForBasicEntry->zlfiginst_zlfig_id == $randomListEntry->zlfiginst_zlfig_id){
                                                        $removedListCtr++;
                                                    }else{
                                                        $newFigInstForBasicList[count($newFigInstForBasicList)] = $currentFigInstForBasicEntry ;
                                                    }
                                                }
                                                $currentFigInstForBasicList = $newFigInstForBasicList;

                                                /*
                                                echo "====printing newfigforbasiclist";
                                                foreach($currentFigInstForBasicList as $n){
                                                    $this->printmodel($n);
                                                }
                                                 * 
                                                 */




                                                $zlroFigInstBasicListCtr++;
                                            }
                                        }else{
					 /* For required figures (they will appear once per their figureInst (NOT original figure) i.e. multiple orientations per figure */
                                            echo $zlroFigInstReqListCtr . '<' . $currentZlroFigGroup->zlfiggroup_nooffigrequired . ($zlroFigInstReqListCtr < $currentZlroFigGroup->zlfiggroup_nooffigrequired);
                                            foreach($currentFigInstForReqList as $n){
                                                printmodel($n);
                                            }
                                            if($zlroFigInstReqListCtr < $currentZlroFigGroup->zlfiggroup_nooffigrequired ){
                                                $flag_listTypeCtr = 'zlroFigInstReqListCtr' ;
                                                if(($cft = count ($currentFigInstForReqList)) > 0){
                                                    $randomNum = mt_rand(0, $cft -1);
                                                    //echo $randomNum;
                                                    //return 0;
                                                    $randomListEntry = $currentFigInstForReqList[$randomNum];
                                                    $newFigInstForReqList = array();
                                                    foreach($currentFigInstForReqList as $currentFigInstForReqEntry){
                                                        //if($currentFigInstForReqEntry->zlfiginst_zlfig_id == $randomListEntry->zlfiginst_zlfig_id)
                                                        if($currentFigInstForReqEntry->zlfiginst_id == $randomListEntry->zlfiginst_id){
                                                            //clear field $currentFigInstForReqEntry

                                                        }else{
                                                            $newFigInstForReqList[count($newFigInstForReqList)] = $currentFigInstForReqEntry;
                                                        }
                                                    }
                                                    $currentFigInstForReqList = $newFigInstForReqList ;

                                                    echo "Inside Req";
                                                    printmodel($currentFigInstForReqEntry);

                                                    $zlroFigInstReqListCtr++;
                                                }
                                            }else{
                                                //Should not be here!
                                                echo "Should not be here!";
                                            }
                                        }
					 $flag_validRandomVal = 1;

                                        echo "Testing for duplicacy!";
                                        foreach($zlroFigInstList as $zlroFigInstListEntry){
                                            if($zlroFigInstListEntry->zlfiginst_id == $randomListEntry->zlfiginst_id ){
                                                $flag_validRandomVal = 0;
                                                $$flag_listTypeCtr = $$flag_listTypeCtr - 1;
                                            }
                                        }
                                        echo "Testing for duplicacy2!";
                                        if($flag_validRandomVal == 1){

                                            $zlroFigInstList[count ($zlroFigInstList)] = $randomListEntry ;
                                            echo "ADDING IT TO THE LIST";
                                            printmodel($randomListEntry);
                                            $zlroFigInstListCtr++ ;

                                            echo "\nSTART : NEW LIST==============================";
                                            foreach($zlroFigInstList as $n){
                                                printmodel($n);
                                            }
                                            echo ' Total : ' . $zlroFigInstListCtr;
                                            echo ' Basic Figures : ' . $zlroFigInstBasicListCtr . '\n';
                                            echo ' Req Figures : ' . $zlroFigInstReqListCtr . '\n';
                                            echo "\nEND : NEW LIST==============================\n";
                                        }

                                    }

                                    echo "Selected 11 figures !";

                                    $zlroFigInstWPosListCtr = 0;
                                    $zlroFigInstWPosList = array() ;
				   /* Flag for ascertaining whether a position is allocated for each iteration in the zlroFigInstList */
                                    $flag_figInstPosSet = 1;

                                    foreach($zlroFigInstList as $zlroFigInstEntry){
                                        if($flag_figInstPosSet == 1){
                                            $flag_figInstPosSet = 0;
                                            $trialCtr = 0;

                                            $availablePosCols = $currentGameInstMap['noOfColumns'] - $zlroFigInstEntry->zlfiginst_noofcolumns ;
                                            $availablePosRows = $currentGameInstMap['noOfRows'] - $zlroFigInstEntry->zlfiginst_noofrows ;


                                            while($zlroFigInstWPosListCtr < $currentGameInstMap['noOfFigures'] && $trialCtr < 1000){
                                                $trialCtr++;
                                                $retPosLeft = mt_rand(0, $availablePosCols);
                                                $retPosTop = mt_rand(0, $availablePosRows);

                                                $retPosRight = $zlroFigInstEntry->zlfiginst_noofcolumns + $retPosLeft - 1;
                                                $retPosBottom = $zlroFigInstEntry->zlfiginst_noofrows + $retPosTop - 1;

                                                /* START : For giving one row-column space in each figure's position */
                                                $rectRetPosLeft = $retPosLeft;
                                                $rectRetPosTop = $retPosTop;
                                                $rectRetPosRight = $retPosRight;
                                                $rectRetPosBottom = $retPosBottom;
                                                if ($retPosLeft > 0){
                                                    $rectRetPosLeft--;
                                                }
                                                if($retPosTop > 0){
                                                    $rectRetPosTop--;
                                                }
                                                if($retPosRight < $currentGameInstMap['noOfColumns'] - 1){
                                                    $rectRetPosRight++;
                                                }
                                                if($retPosBottom < $currentGameInstMap['noOfRows'] - 1){
                                                    $rectRetPosBottom++;
                                                }
						/* END : For giving one row-column space in each figure's position */

                                                $currentRect = array('left'=>$rectRetPosLeft,'top'=>$rectRetPosTop,'right'=>$rectRetPosRight,'bottom'=>$rectRetPosBottom);

                                                $flag_isFigPosValid = 1;
                                                foreach($zlroFigInstWPosList as $zlroFigInstWPosEntry){
                                                    if($flag_isFigPosValid==1){
                                                        $tempNoOfCols = $zlroFigInstWPosEntry['posLeft'] + $zlroFigInstWPosEntry['zlfiginst_noofcolumns'] - 1;
                                                        $tempNoOfRows = $zlroFigInstWPosEntry['posTop'] + $zlroFigInstWPosEntry['zlfiginst_noofrows'] - 1;

                                                        $tempRect = array('left'=>$zlroFigInstWPosEntry['posLeft'],'top'=>$zlroFigInstWPosEntry['posTop'],'right'=>$tempNoOfCols,'bottom'=>$tempNoOfRows);
                                                        $isCollided = rectIntersect($currentRect,$tempRect);
                                                        if($isCollided){
                                                            $flag_isFigPosValid = 0;
                                                        }
                                                    }
                                                }

                                                if($flag_isFigPosValid == 1){
                                                    $trialCtr = 1000;
                                                    $flag_figInstPosSet = 1;
                                                    $newFigInstWPosEntry = array();
                                                    $newFigInstWPosEntry['zlroFigInstEntry'] = $zlroFigInstEntry ;
                                                    $newFigInstWPosEntry['posLeft'] = $retPosLeft ;
                                                    $newFigInstWPosEntry['posTop'] = $retPosTop ;
                                                    $newFigInstWPosEntry['zlfiginst_noofrows'] = $zlroFigInstEntry->zlfiginst_noofrows ;
                                                    $newFigInstWPosEntry['zlfiginst_noofcolumns'] = $zlroFigInstEntry->zlfiginst_noofcolumns ;
                                                    $zlroFigInstWPosList[count($zlroFigInstWPosList)] = $newFigInstWPosEntry ;
                                                    $zlroFigInstWPosListCtr++;
                                                    $newFigInstWPosEntry = null;
                                                }

                                            }
                                        }
					}

                                    if($zlroFigInstWPosListCtr == $currentGameInstMap['noOfFigures'] || true){
                                        /* Start pushing the newly randomized zlroFigInstList to the database in OloZlroGameInstFig */
                                        $temp_oloZlroGameInstFigList = array();
                                        foreach($zlroFigInstWPosList as $zlroFigInstWPosEntry){
                                            $currentZlroGameInstFigureId = $model->gameinst_id . '_' . $zlroFigInstWPosEntry['zlroFigInstEntry']->zlfiginst_id ;

                                            $oloZlroGameInstFigVO = Zzzlrogameinstfig::model()->findByPk($currentZlroGameInstFigureId);
                                            if(!isset($oloZlroGameInstFigVO)){
                                                $oloZlroGameInstFigVO = new Zzzlrogameinstfig ;
                                                $oloZlroGameInstFigVO->zlgameinstfig_id = $currentZlroGameInstFigureId ;
                                            }

                                            $temp_oloZlroGameInstFigList[$currentZlroGameInstFigureId] = "'" . $currentZlroGameInstFigureId . "'";

                                            $oloZlroGameInstFigVO->zlgameinstfig_zlfiginst_id = $zlroFigInstWPosEntry['zlroFigInstEntry']->zlfiginst_id ;
                                            $oloZlroGameInstFigVO->zlgameinstfig_gameinst_id = $model->gameinst_id;
                                            $oloZlroGameInstFigVO->zlgameinstfig_posleft = $zlroFigInstWPosEntry['posLeft'] ;
                                            $oloZlroGameInstFigVO->zlgameinstfig_postop = $zlroFigInstWPosEntry['posTop'] ;

                                            if($oloZlroGameInstFigVO->save()){
                                                echo "Saved!";
                                            }else{
                                                echo "Not Saved!" . $oloZlroGameInstFigVO->getErrors();
                                            }


                                        }

                                        /* Deleting records that belong to currentGameInstance but are not generated in the above method */
                                        if(count ($temp_oloZlroGameInstFigList)> 0){
                                            $temp_oloZlroGameInstFigList_delete = Zzzlrogameinstfig::model()->deleteAll('zlgameinstfig_gameinst_id = \'?\' and zlgameinstfig_zlfiginst_id not in (' . implode(',',$temp_oloZlroGameInstFigList) . ')',array($model->gameinst_id));
                                        }
					/* Generating Zalerio Game Rounds for the current game instance */
                                        $startTimeInMs = strtotime($model->gameinst_starttime) ;
                                        $endTimeInMs = strtotime($model->gameinst_endtime);

                                        //Calculating timeInterval (in millis) for each round
                                        $gameRoundTimeInterval = ($endTimeInMs - $startTimeInMs)/($currentGameInstMap["noOfRounds"]);

                                        $zlroRoundStartTime = $startTimeInMs ;
                                        $zlroRoundEndTime = $endTimeInMs;
                                        $temp_zlroGameRoundList = array();

                                        for($currentRoundCtr = 0;$currentRoundCtr < $currentGameInstMap["noOfRounds"];$currentRoundCtr++){
                                            $currentZlroGameRoundId = $model->gameinst_id . '_' . $currentRoundCtr;


                                            //setting endTime for currentRound
                                            if($currentRoundCtr == ($currentGameInstMap["noOfRounds"]-1)){
                                                $zlroRoundEndTime = $endTimeInMs ;
                                            }else{
                                                $zlroRoundEndTime = $zlroRoundStartTime + $gameRoundTimeInterval ;
                                            }

                                            $temp_zlroGameRoundList[$currentZlroGameRoundId] = "'" . $currentZlroGameRoundId . "'";

                                            $oloZlroGameRoundVO = Zzzlrogameround::model()->findByPk($currentZlroGameRoundId);

                                            if(!isset($oloZlroGameRoundVO)){
                                                $oloZlroGameRoundVO = new Zzzlrogameround;
                                                $oloZlroGameRoundVO->zlgameround_id = $currentZlroGameRoundId;
                                            }

                                            $oloZlroGameRoundVO->zlgameround_roundname = $currentRoundCtr + 1;
                                            $oloZlroGameRoundVO->zlgameround_gameinst_id = $model->gameinst_id;
                                            $timestampantipattern = 'Y-m-d H:i';

                                            $oloZlroGameRoundVO->zlgameround_timestart = CTimestamp::formatDate($timestampantipattern,$zlroRoundStartTime);
					 $oloZlroGameRoundVO->zlgameround_timeend = CTimestamp::formatDate($timestampantipattern,$zlroRoundEndTime) ;

                                            if(!$oloZlroGameRoundVO->save()){
                                                echo "Saved!";
                                            }else{
                                                echo "Not Saved!" . $oloZlroGameRoundVO->getErrors();
                                            }

                                            $zlroRoundStartTime = $zlroRoundEndTime ;

                                        }

                                        /* Deleting all the zalerio-game-rounds that are no longer valid for the current gameInst */
                                        if(count ($temp_zlroGameRoundList)> 0){
                                            $temp_zlroGameRoundList_delete = Zzzlrogameround::model()->deleteAll('zlgameround_id not in (' . implode(',',$temp_zlroGameRoundList) . ') and zlgameround_gameinst_id = \'?\'' ,array($model->gameinst_id));
                                        }



                                    }else{
                                        $flag_error++;
                                        echo "Number of tries to select the random positions exceeded, retry after some time.";
                                    }


                                }

                            }else{
                                $flag_error++;
                            }
                            if($flag_error > 0){
                                $transaction->rollback();
                            }else{
                                $transaction->commit();
                                if (! Yii::app()->request->isAjaxRequest) {
                                    $this->redirect(array('view','id'=>$model->gameinst_id));
				}
                                // do if ajaxcall
                                    ob_clean ();    // clean output buffer
                                    return $model->gameinst_id; // return game id
                            }
                }

 /*               if (! Yii::app()->request->isAjaxRequest) {
                    $this->render('create',array(
                            'model'=>$model,
                    ));
                } */
            }else{
                // if not ajax request
                //$this->layout = false;
                //$this->render('error', $error);
            }
}

// function to book seat for the users


function printmodel($model){
            if(is_object($model) || is_array($model)){
                echo "<PRE>";
                echo print_r($model->attributes);
                echo "</PRE>";
            }else{
                echo $model;
            }
        }

function printmodeldata($model){
            if(is_object($model) || is_array($model)){
                echo "<PRE>";
                echo print_r($model->attributes);
                echo "</PRE>";
            }else{
                echo $model;
            }
        }
function rectIntersect($r1,$r2){
            if(max($r1['left'], $r2['left']) <= min($r1['right'], $r2['right'])
                    && max($r1['top'], $r2['top']) <= min($r1['bottom'], $r2['bottom'])){
                return true;
            }
            return false;
}
// function to book the game seat for users
function bookGameSeat($Zzgameseat) {
 $model=new Zzgameseat;
                // Uncomment the following line if AJAX validation is needed
                // $this->performAjaxValidation($model);
                //echo "<pre>" , print_r($_GET) , "</pre>";
                //break;
                if(isset($Zzgameseat))
                {
			$gameInstId = $Zzgameseat['gameseat_gameinst_id'];
			$userId = $Zzgameseat['gameseat_user_id'];
                        $bookedSeats = Zzgameseat::model()->countByAttributes(array('gameseat_gameinst_id' => "$gameInstId"));
                        $gameInst = Zzgameinst::model()->findByPk($gameInstId);
                        $gameSeat = Zzgameseat::model()->findByAttributes(array('gameseat_user_id' => "'$userId'",'gameseat_gameinst_id' => "$gameInstId"));
			
                        if($gameInst->gameinst_capacity == $bookedSeats){
                            if(isset($gameSeat)){
                                // if you have already booked the set go to game page
                        //        $this->redirect(array('gameinst/play','gameinst_id'=>$Zzgameseat['gameseat_gameinst_id']));
                                exit;
                            } else {
                             Yii::app()->user->setFlash('notice', "All seats are booked for this game please select other game");
                        //     $this->redirect(array('site/index'));
                             exit;
                           }
                        } else{
		 if(isset($gameSeat)){
                              //  Yii::app()->user->setFlash('notice', "You have already bought this game"); removing the notice
                                // if you have already booked the seat go to game page
                              //  $this->redirect(array('gameinst/play','gameinst_id'=>$Zzgameseat['gameseat_gameinst_id']));
                                //$this->redirect(array('viewGameSeat','id'=>$gameSeat->gameseat_id));
                                //$this->redirect(array('gameinst/play','gameinst_id'=>$gameSeat->gameseat_id));
                            }else{
                                //echo "you have not bought this game before so u cn buy it";
                                $model->attributes=$Zzgameseat;
                                $model->gameseat_createdat = new CDbExpression('NOW()');
				// if the creator of game is there marking his status as active while booking
				if($userId == Yii::app()->user->id) {
                                $model->zzgameseat_status = 'accepted'; 
				} else {
				// if not then marking his status as invited
				$model->zzgameseat_status = 'invited';
				}
                                if($model->save()){
                                    //$this->redirect(array('viewGameSeat','id'=>$model->gameseat_id));
                                    // redirect user to game play
                                    if (!Yii::app()->request->isAjaxRequest){
                                    $this->redirect(array('gameinst/play','gameinst_id'=>$_REQUEST['Zzgameseat']['gameseat_gameinst_id']));
                                    }
                                }
                            }
                        }
                }
}
// get the list of the invited users along with the logged in users and book there seat 
function bookSeatForInvitedUsers($gameId,$getFbUsersInvitedData) {
        $fbUserFriends = $getFbUsersInvitedData;
	$gameInstId = $gameId;
	$ZzgameseatForLoogedInUser = array();
	$ZzgameseatForLoogedInUser['gameseat_user_id'] = Yii::app()->user->id; 
	$ZzgameseatForLoogedInUser['gameseat_gameinst_id'] = $gameInstId;
	bookGameSeat($ZzgameseatForLoogedInUser);  // calling the function for booked seat
	// loop for booking the seat of invited users
	foreach ($fbUserFriends as $fbUser) {
	$Zzgameseat = array();
	$id = $fbUser['uid'];
	$get_user_id = get_user_id($id); // get user id by facebook id
	$Zzgameseat['gameseat_user_id'] = $get_user_id;
	$Zzgameseat['gameseat_gameinst_id'] = $gameInstId; // get game id
        bookGameSeat($Zzgameseat);	
	$Zzgameseat = NULL;
	}	
}
