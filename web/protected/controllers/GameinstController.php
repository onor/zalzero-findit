<?php

class GameinstController extends Controller
{
	/**
	 * @var string the default layout for the views. Defaults to '//layouts/column2', meaning
	 * using two-column layout. See 'protected/views/layouts/column2.php'.
	 */
	public $layout='//layouts/column2';

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
				'actions'=>array('index','view'),
				'roles'=>array('admin'),
			),
                        array('allow',  // allow authenticated users to perform 'play' and 'create' actions
				'actions'=>array('play','create','createNewGame'),
				'users'=>array('@'),
                        ),
			array('allow', // allow authenticated user to perform 'create' and 'update' actions
				'actions'=>array('create','update','generateZalerioFigures','play','createNewGame'),
				'roles'=>array('admin'),
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
       // action to create a new game once the users are invited
	public function actionCreateNewGame() {
	  $facebook = Yii :: app()->params['facebook'];
          $getLoggedInuserFbId = $facebook->getUser(); 
	  $gameId = $_REQUEST['gameId'];
	  // removing logged in user fbid from the $_Request fbdata parameter
	 
	 // checking if the user has requested for rematch
	  if(isset($_REQUEST) && $_REQUEST['gameOption'] == "Rematch") {
             foreach($_REQUEST['fbUserData'] as $data) {       // get the user id from there fb id and check there seat status. 
		if($data['uid'] != $getLoggedInuserFbId) {
		  $userFbId = $data['uid'];
		  $get_user_id = get_user_id($userFbId);
		  $gameSeat = Zzgameseat::model()->findByAttributes(array('gameseat_user_id' => "$get_user_id",'gameseat_gameinst_id' => "$gameId",'zzgameseat_status'=>'accepted'));
		  if(isset($gameSeat)) {
		    $getFbUsersInvitedData[] = $data; //// if they are in accepted stage then add them to array
		  }
		}
             }	       
	  } else {
	    foreach($_REQUEST['fbUserData'] as $data) { // if user is creating the game
		if($data['uid'] != $getLoggedInuserFbId) {
		  $getFbUsersInvitedData[] = $data;
		}
	    }
	 }
	  $gameDefaultParameter = new gameDefaultParameters(); // get the default parameters to generate the game
	  $gameDefaultData = $gameDefaultParameter->getGameDefaultParameters();
	  $gameDefaultData['gameinst_capacity'] = count($getFbUsersInvitedData)+1; // assign the game capacity by count the number of users which have been invited to the game including the game creator

	// Create a new game
	   $getGameId = createNewGame($gameDefaultData);

	// Get list of invited users and pass them to the function to create a account in zzusers table. All the invited friends will have the status invited if they are not available in the database.
	   getListOfInvitedUsers($getFbUsersInvitedData);
	// Paasing the list of invited facebook users so that we can enter the users to the invitedfriends table 
	   getFbFriendsList($getGameId,$getFbUsersInvitedData);
	// Booking seat for all the invited users with the game Id
	   bookSeatForInvitedUsers($getGameId,$getFbUsersInvitedData);
	// Passing the Game Id to the jquery so that we can be redirected to the game Page
	  print $getGameId;
	  // bookGameSeat();	
	}

	/**
	 * Creates a new model.
	 * If creation is successful, the browser will be redirected to the 'view' page.
	 */
	public function actionCreate()
	{       $model=new Zzgameinst;
            if (Yii::app()->request->isAjaxRequest){
		// Uncomment the following line if AJAX validation is needed
		// $this->performAjaxValidation($model);
                if(isset($_POST['Zzgameinst']))
		{
                            $transaction = Yii::app()->db->beginTransaction();
                            $flag_error = false;
                            $gameInstTmpl = ZzGameInstTempl::model()->findByPk($_POST['Zzgameinst']['gameinst_gameinsttempl_id']);
                            $model->attributes = $gameInstTmpl->attributes;
                            $model->attributes=$_POST['Zzgameinst'];
                            
                            $model->gameinst_starttime  = date('Y-m-d H:i:s');
                            $model->gameinst_endtime  = date('Y-m-d H:i:s',strtotime("+7 days"));
                            $model->gameinst_entrytime  = date('Y-m-d H:i:s');
                 
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
                            //$gameInstGamelId = $_POST['Zzgameinst']['gameinst_game_id'];
                            //$gammeInstTmpl = ZzGameInstTempl::model()->fin

                            if($model->save()){
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
                                                
                                                $this->printmodel($randomListEntry);
                                                
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
                                                $this->printmodel($n);
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
                                                    $this->printmodel($currentFigInstForReqEntry);
                                                
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
                                            $this->printmodel($randomListEntry);
                                            $zlroFigInstListCtr++ ;
                                            
                                            echo "\nSTART : NEW LIST==============================";
                                            foreach($zlroFigInstList as $n){
                                                $this->printmodel($n);
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
                                                        $isCollided = $this->rectIntersect($currentRect,$tempRect);
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
                                    echo $model->gameinst_id; // return game id
                            }			
                }
                
 /*               if (! Yii::app()->request->isAjaxRequest) {
                    $this->render('create',array(
                            'model'=>$model,
                    ));
                } */
                
            }else{
                // if not ajax request
                $this->layout = false;            
                $this->render('error', $error);
            }
	}
        
        /** 
         * rectIntersect function for checking rectangle intersection
         parameters needed for $rectangles
        $rectangle_array = array(
            $R1 = array("UL" => array("x" => 10, "y" => 20), "LR" => array("x" => 22, "y" => 5)),
            $R2 = array("UL" => array("x" => 32, "y" => 44), "LR" => array("x" => 65, "y" => 20)),
            $R3 = array("UL" => array("x" => 20, "y" => 16), "LR" => array("x" => 25, "y" => 10))
         * return : true for intersection, false for no-intersection
        );
        */
        private function rectIntersect2($rectangles) {
            $num_rectangles = count($rectangles);
            for ($i = 0; $i < $num_rectangles; $i++) {
                //for each rectangle, compare points to every following rectangle
                $R1 = $rectangles[$i];
                for ($k = $i; $k < ($num_rectangles - $i); $k++) {
                    $R2 = $rectangles[$k + 1];
                    if ($R1['LR']['x'] > $R2['UL']['x'] && $R1['UL']['x'] < $R2['LR']['x']) {
                        //rectangles cross on x-axis
                        if (($R1['LR']['y'] < $R2['UL']['y'] && $R1['UR']['y'] < $R2['LR']['y']) ||
                                ($R1['UL']['y'] > $R2['LR']['y'] && $R1['LR']['y'] < $R2['UL']['y'])) {
                            //rectangles intersect
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
        //Only for 2 rectangles
        private function rectIntersect($r1,$r2){
            if(max($r1['left'], $r2['left']) <= min($r1['right'], $r2['right']) 
                    && max($r1['top'], $r2['top']) <= min($r1['bottom'], $r2['bottom'])){
                return true;
            }
            return false;
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

		if(isset($_POST['Zzgameinst']))
		{
			$model->attributes=$_POST['Zzgameinst'];
			if($model->save())
				$this->redirect(array('view','id'=>$model->gameinst_id));
		}

		$this->render('update',array(
			'model'=>$model,
		));
	}

        public function actionPlay(){
        	$user_id = Yii::app()->user->id;
        	$usersummary = Zzgameusersummary::model()->findByAttributes(array('user_id'=>$user_id));
        	$new_user = false;
        	if($usersummary['last_gameinst_id'] == NULL && $usersummary['last_gameinst_id'] == ''){
        		$new_user = true;
        	}
        		
			if(!isset($_REQUEST['force'])){				
				if($usersummary["current_gameinst_id"] != NULL){
					$this->layout = false;
					$this->render('confirmsession',array("gameinst_id"=>$_REQUEST['gameinst_id']));
					exit;
				}
			}

        	
        	if(isset($_REQUEST['gameinst_id'])) {
        		$getGameId = $_REQUEST['gameinst_id'];
        		if((is_numeric($getGameId)) && $getGameId > 0) {
        			update_game_seat_status($user_id,$getGameId);
        		}
        	}
        	
            $this->layout = "play";
            $gameInstId = $_REQUEST['gameinst_id'];
            if($gameInstId == '0'){
            	$last_active_gameInstId = $this->LastActiveGameId($user_id);
                if(!empty($last_active_gameInstId)){
                    //$game_inst_id=$last_active_game_id;
                    // sent to last active game
                    if(isset($_REQUEST['force'])){
                    	$this->redirect(Yii::app()->baseUrl.'/gameinst/play?gameinst_id='.$last_active_gameInstId."&force=play");
                    }else{
                    	$this->redirect(Yii::app()->baseUrl.'/gameinst/play?gameinst_id='.$last_active_gameInstId);
                    }
                }
            }
            // if user come with gameinst 0 render blank play board and exit
            if($gameInstId == '0'){
            	$param = array('startGame'=>'true');
            	if($new_user == true){
            		$param = array('startGame'=>'false','tutorial'=>'true');
            	}
                $this->render('play',$param);
                exit;
            }
            $user_id = Yii::app()->user->id;
            //echo "Game inst id : " , $gameInstId;
            //echo "user id : " , $user_id;
            $gameSeat = Zzgameseat::model()->with(array('gameseatGameinst','gameseatGameinst.gameinstProduct'))->findByAttributes(array('gameseat_user_id'=>$user_id,'gameseat_gameinst_id'=>$gameInstId));
            //$gameInst = Zzgameinst::model()->with(array('gameinstProduct',array('zzgameseats'=>array('condition' =>'gameseat_user_id = ' . $user_id))))->findByAttributes(array('gameinst_id' => $gameInstId));
            //$games = Zzgameseat::model()->with(array('gameseatGameinst'=>array('condition'=> 'gameinst_id =  ' . $_REQUEST['gameinst_id']) ,'gameseatGameinst.gameinstProduct' ))->findAllByAttributes(array('gameseat_user_id'=> $user_id));
            //$this->printmodel(games);
            /*foreach ($gameInst['zzgameseats'] as $game){
                $this->printmodel($game);
            }*/
            if(isset($gameSeat)){
            	$param = array('game'=>$gameSeat);
            	if($new_user == true){
            		$param = array('game'=>$gameSeat,'tutorial'=>'true');
            	}
                $this->render('play',$param);
                $gameInstTemplId = Zzgameinst::model()->findByPk($gameInstId)->getAttribute('gameinst_gameinsttempl_id'); 
                
                $count = Zzgameusersummary::model()->updateAll(array('last_gameinst_id'=>$gameInstId,'last_gameinst_update_time'=>new CDbExpression('NOW()')), "zz_gameinsttemp_id = '".$gameInstTemplId."' and user_id='".$user_id."'",array());
                if($count == 0)
                {
                    $model = new Zzgameusersummary();
                    
                    $model->user_id = $user_id;
                    $model->last_gameinst_id = $gameInstId;
                    $model->last_gameinst_update_time = new CDbExpression('NOW()');
                    $model->zz_gameinsttemp_id = $gameInstTemplId;
                    $model->user_level = 1;
                   
                    if($model->validate())
                    {   
                        $model->save();
                    }
                }
               
            }else{
                Yii::app()->user->setFlash('notice', "You have to buy the ticket for this game first!");
                $this->redirect(array('view','id'=>$gameInstId));
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
		$dataProvider=new CActiveDataProvider('Zzgameinst');
		$this->render('index',array(
			'dataProvider'=>$dataProvider,
		));
	}

	/**
	 * Manages all models.
	 */
	public function actionAdmin()
	{
		$model=new Zzgameinst('search');
		$model->unsetAttributes();  // clear any default values
		if(isset($_GET['Zzgameinst']))
			$model->attributes=$_GET['Zzgameinst'];

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
		$model=Zzgameinst::model()->findByPk($id);
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
		if(isset($_POST['ajax']) && $_POST['ajax']==='zzgameinst-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}
        
        public function actionGenerateZalerioFigures(){
            $transaction = Yii::app()->db->beginTransaction();
            $flag_error = false;
            $figInstOrientationList = array(
                                        'T'=>true,
                                        'R'=>true,
                                        'B'=>true,
                                        'L'=>true
                                      );
            $oloZlroFigures = Zzzlrofig::model()->findAll();
            
            foreach($oloZlroFigures as $olofig){
                $this->printmodel($olofig);
                
                //Getting fig coordinates for the current figure
                $oloZlroFigCoordList = Zzzlrofigcoord::model()->findAllByAttributes(array('zlfigcoord_zlfig_id'=>$olofig['zlfig_id']));
                
                
                foreach($figInstOrientationList as $figOrientation=>$val){
                    $currentFigInstId = $olofig->zlfig_id . '_' . $figOrientation;
                    $newFig = Zzzlrofiginst::model()->findByPk($currentFigInstId);
                    if(!isset($newFig)){
                        $newFig = new Zzzlrofiginst;
                        $newFig->zlfiginst_id = $currentFigInstId;
                    }
                    $newFig->zlfiginst_zlfig_id = $olofig->zlfig_id;
                    //$this->printmodel($newFig);
                    //break;
                    /*
                    if($newFig->zlfiginst_valid == null || $newFig->zlfiginst_valid != 0){
                        $newFig->zlfiginst_valid = 1;
                    }
                    */
                    $newFig->zlfiginst_orientation = $figOrientation ;
                    
                    /* Start : Extracting the highest value for posX and posY in the current figure */
                        
                    $oloZlroFigCoordMaxList = Zzzlrofigcoord::model()->findAllByAttributes(array('zlfigcoord_zlfig_id'=>$olofig->zlfig_id));
                    $currentHighX = 0;
                    $currentHighY = 0;
                    foreach($oloZlroFigCoordMaxList as $oloZlroFigCoordMaxEntry){
                        $cPosX = $oloZlroFigCoordMaxEntry->zlfigcoord_posx;
                        $cPosY = $oloZlroFigCoordMaxEntry->zlfigcoord_posy;
                        if($cPosX < 0){
                            $cPosX = $cPosX * -1;
                        }
                        if($cPosY < 0){
                            $cPosY = $cPosY * -1;
                        }
                        if($cPosX > $currentHighX){
                            $currentHighX = $cPosX;
                        }
                        if($cPosY > $currentHighY){
                            $currentHighY = $cPosY;
                        }
                        
                    }
                    /* End : Extracting the highest value for posX and posY in the current figure */
                    
                    $flag_horizontalFig = false;
                    if($figOrientation == 'R' || $figOrientation == 'L'){
                        $flag_horizontalFig = true;
                    }
                    
                    $highY = $currentHighY + 1;
                    $highX = $currentHighX + 1;
                    
                    if($flag_horizontalFig){
                        $newFig->zlfiginst_noofcolumns = $highY;
                        $newFig->zlfiginst_noofrows = $highX;
                    }else{
                        $newFig->zlfiginst_noofcolumns = $highX;
                        $newFig->zlfiginst_noofrows = $highY;
                    }
                    
                    if($newFig->validate() && $newFig->save()){
                         echo "New Figure Saved!";
                    }else{
                        $flag_error++;
                        echo "New Figure Not Saved! ";
                        $this->printmodel($newFig->getErrors());
                    }
                    
                    /* Clearing the current entries in the OloZlroFigInstCoord */
                    //$clearOloZlroFigInstCoordList = Zzzlrofiginstcoord::model()->deleteAll('zlfiginstcoord_zlfiginst_id = ?',array($currentFigInstId));
                    
                    $currentOloZlroFigInstCoordList = array();
                    $ctrOloZlroFigCoordEntry = 0;
                    $newOloZlroFigCoordList = array();
                    
                    foreach($oloZlroFigCoordList as $oloZlroFigCoordEntry){
                        $ctrOloZlroFigCoordEntry++;
                        
                        /* START : Normalising the coordinates w.r.t the Normal Matrix */
                        $currentZlroFigInstCoordId = $newFig->zlfiginst_id . '_' . $ctrOloZlroFigCoordEntry;
                        $newOloZlroFigCoordList[$currentZlroFigInstCoordId] = $currentZlroFigInstCoordId ;
                        
                        
                        $currentOloZlroFigInstCoord = Zzzlrofiginstcoord::model()->findByPk($currentZlroFigInstCoordId);
                        if(!isset($currentOloZlroFigInstCoord)){
                            $currentOloZlroFigInstCoord = new Zzzlrofiginstcoord;
                            $currentOloZlroFigInstCoord->zlfiginstcoord_id = $currentZlroFigInstCoordId ;
                        }
                        
                        $currentOloZlroFigInstCoord->zlfiginstcoord_zlfiginst_id = $newFig->zlfiginst_id ; 
                        
                        $newPosX = $oloZlroFigCoordEntry->zlfigcoord_posx ; 
                        $newPosY = $oloZlroFigCoordEntry->zlfigcoord_posy ; 
                        
                        /* For a 0 degree rotated figure */
                        if($figOrientation == 'T'){
                            
                        }
                        
                        /* For a 90 degree rotated figure */
                        if($figOrientation == 'R'){
                            //Interchanging the x,y positions
                            $newPosX = $oloZlroFigCoordEntry->zlfigcoord_posy ; 
                            $newPosY = $oloZlroFigCoordEntry->zlfigcoord_posx ; 
                            
                            $newPosY = $newPosY * -1;
                        }
                        
                        /* For a 180 degree rotated figure */
                        if($figOrientation == 'B'){
                            $newPosX = $newPosX * -1;
                            $newPosY = $newPosY * -1;
                        }
                        
                        /* For a 270 degree rotated figure */
                        if($figOrientation == 'L'){
                            //Interchanging the x,y positions
                            $newPosX = $oloZlroFigCoordEntry->zlfigcoord_posy ; 
                            $newPosY = $oloZlroFigCoordEntry->zlfigcoord_posx ; 
                            
                            $newPosX = $newPosX * -1;
                        }
                        
                        $currentOloZlroFigInstCoord->zlfiginstcoord_posx = $newPosX ;
                        $currentOloZlroFigInstCoord->zlfiginstcoord_posy = $newPosY ;
                        
                        $currentOloZlroFigInstCoordList[$currentOloZlroFigInstCoord->zlfiginstcoord_id] = $currentOloZlroFigInstCoord ; 
                        /* END : Normalising the coordinates w.r.t the Normal Matrix */
                    }
                    
                    /* Calculating the topLeft(X,Y) point of the figure to align the figure to origin */
                    
                    $topLeftX = 1000;
                    $topLeftY = -1000;
                    
                    foreach($currentOloZlroFigInstCoordList as $oloZlroFigInstCoordVO){
                        if($topLeftX > $oloZlroFigInstCoordVO->zlfiginstcoord_posx){
                            $topLeftX = $oloZlroFigInstCoordVO->zlfiginstcoord_posx;
                        }
                        if($topLeftY < $oloZlroFigInstCoordVO->zlfiginstcoord_posy){
                            $topLeftY = $oloZlroFigInstCoordVO->zlfiginstcoord_posy;
                        }
                    }
                    
                    /* Calculating the increment to be done to Normalise the coordinates to the origin for the matric system */
                    $incrX = 0 - $topLeftX ;
                    $incrY = 0 - $topLeftY ;
                    
                    /* Normalizing the figure to the origin(0,0) for the matric system from coordinate system */
                    foreach($currentOloZlroFigInstCoordList as $oloZlroFigInstCoordVO){
                        $newPosX = $oloZlroFigInstCoordVO->zlfiginstcoord_posx + $incrX ;
                        $newPosY = $oloZlroFigInstCoordVO->zlfiginstcoord_posy + $incrY ;
                        
                        if($newPosY < 0){
                            $newPosY = $newPosY * -1;
                        }
                        $oloZlroFigInstCoordVO->zlfiginstcoord_posx = $newPosX ;
                        $oloZlroFigInstCoordVO->zlfiginstcoord_posy = $newPosY ;
                    }
                    
                    /* Saving it to DB */
                    foreach($currentOloZlroFigInstCoordList as $oloZlroFigInstCoordVO){
                        echo "Huha" . $this->printmodel($oloZlroFigInstCoordVO);
                        if($oloZlroFigInstCoordVO->save()){
                            echo "Saved";
                        }else{
                            $flag_error++;
                            echo "Not Saved!" . $oloZlroFigInstCoordVO->getErrors();
                        }
                    }
                }
                echo "--------------------------------------------";
                
            }
            
            echo "==========================================================================";
            $recs = Zzzlrofiginst::model()->findAll();
            foreach($recs as $rec){
                $this->printmodel($rec);
            }
            
            if($flag_error > 0){
                $transaction->rollback();
                echo "Action Failed";
            }else{
                $transaction->commit();
                echo "Action Successfully Executed";
            }
            
            return 0;
            
        }
        
        public function LastActiveGameId($user_id)
        {       
                // default gameinst_id that will be returned 
                $game_inst_id = '0';
                // calculate last active games
                $last_active_gameinstid_arr = Zzgameusersummary::model()->findAll(array('condition'=>"user_id='$user_id'",'order'=>'last_gameinst_update_time desc'));
                $i=0;
                $active_gameinstid_arr = array();
                foreach($last_active_gameinstid_arr as $arr)
                {
                    $active_gameinstid_arr[$i]= $arr['last_gameinst_id'];
                    $i++;
                }
                // calculate all active games of user
                $allGames = Zzgameseat::model()->with(array('gameseatGameinst'))->findAll(array('condition'=>"t.gameseat_user_id ='$user_id' and t.zzgameseat_status='accepted'", 'order' => '"gameseatGameinst"."create_time" DESC'));
                // user's active games arr and user's past game arr
                $all_active_games = array();$all_past_games = array();$i=0;$j=0;
                foreach($allGames as $Game)
                {
                    if($Game['gameseatGameinst']['zzgameinst_status'] == 'active')
                    {    
                       $all_active_games[$i] = $Game['gameseatGameinst']['gameinst_id'];  
                       $i++;
                    }
                    else
                    {
                        $all_past_games[$j] = $Game['gameseatGameinst']['gameinst_id'];
                        $j++;
                    }
                }
                
                // calculate the appropriate last active game
                foreach($active_gameinstid_arr as $id)
                {
                    if(in_array($id,$all_active_games))
                    {
                        $game_inst_id = $id;break;
                    }
                }
                // if game_inst_id is empty that means these games have been finished , now assign the last active created game by user 
                if(empty($game_inst_id))
                {
                    if(!empty($all_active_games))
                    {   
                        //assign one of active gameinst id
                        $game_inst_id = $all_active_games[0];
                    }
                    
                }
                
                // if there is no active game , search for user last active_game_id in past games
                foreach($active_gameinstid_arr as $id)
                {
                    if(in_array($id,$all_past_games))
                    {
                        $game_inst_id = $id;break;
                    }
                }
                // if still game_inst_id is empty or 0 then assign last created past game
                if(empty($game_inst_id))
                {
                    if(!empty($all_past_games))
                    {   
                        //assign one of active gameinst id
                        $game_inst_id = $all_past_games[0];
                    }
                    
                }
               
                return $game_inst_id;
                
        }
        
}
