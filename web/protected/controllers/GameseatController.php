<?php

class GameseatController extends Controller
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
				'actions'=>array('bookSeat'),
				'users'=>array('@'),
			),
			array('allow', // allow authenticated user to perform 'create' and 'update' actions
				'actions'=>array('index','create','update','admin','delete'),
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
        
        public function actionViewGameSeat($id){
            $model = Zzgameseat::model()->with('gameseatGameinst')->findByPk($id);
            $this->render('viewGameSeat',array('model'=>$model));
        }

	/**
	 * Creates a new model.
	 * If creation is successful, the browser will be redirected to the 'view' page.
	 */
	public function actionCreate()
	{
		$model=new Zzgameseat;

		// Uncomment the following line if AJAX validation is needed
		// $this->performAjaxValidation($model);

		if(isset($_POST['Zzgameseat']))
		{
			$model->attributes=$_POST['Zzgameseat'];
                        $model->gameseat_createdat = new CDbExpression('NOW()');
                        
			if($model->save())
				$this->redirect(array('view','id'=>$model->gameseat_id));
		}

		$this->render('create',array(
			'model'=>$model,
		));
	}
	public function actionBookSeat()
	{
		$model=new Zzgameseat;
                if (Yii::app()->request->isAjaxRequest){
                    if (isset($_REQUEST['Zzgameseat']['gameseat_user_email'])){
                        $_REQUEST['Zzgameseat']['gameseat_user_id'] = Yii::app()->user->id;
                        
                    }elseif(!isset($_REQUEST['Zzgameseat']['gameseat_user_id'])){
                        return 0;
                    }                    
                }
		// Uncomment the following line if AJAX validation is needed
		// $this->performAjaxValidation($model);
                //echo "<pre>" , print_r($_GET) , "</pre>";
                //break;
		if(isset($_REQUEST['Zzgameseat']))
		{
                        $bookedSeats = Zzgameseat::model()->countByAttributes(array('gameseat_gameinst_id' => $_REQUEST['Zzgameseat']['gameseat_gameinst_id']));
                        $gameInst = Zzgameinst::model()->findByPk($_REQUEST['Zzgameseat']['gameseat_gameinst_id']);
			
                        $gameSeat = Zzgameseat::model()->findByAttributes(array('gameseat_user_id' => Yii::app()->user->id,'gameseat_gameinst_id' => $_REQUEST['Zzgameseat']['gameseat_gameinst_id']));

                        if($gameInst->gameinst_capacity == $bookedSeats){
			    if(isset($gameSeat)){
				// if you have already booked the set go to game page
			//	$this->redirect(array('gameinst/play','gameinst_id'=>$_REQUEST['Zzgameseat']['gameseat_gameinst_id']));
				exit;
			    } else {
                             Yii::app()->user->setFlash('notice', "All seats are booked for this game please select other game");
                         //    $this->redirect(array('site/index'));
			     exit;
			   }
                        } else{

                            if(isset($gameSeat)){
                              //  Yii::app()->user->setFlash('notice', "You have already bought this game"); removing the notice
				// if you have already booked the seat go to game page
			//	$this->redirect(array('gameinst/play','gameinst_id'=>$_REQUEST['Zzgameseat']['gameseat_gameinst_id']));
                                //$this->redirect(array('viewGameSeat','id'=>$gameSeat->gameseat_id));
                                //$this->redirect(array('gameinst/play','gameinst_id'=>$gameSeat->gameseat_id));
                            }else{
                                //echo "you have not bought this game before so u cn buy it";
                                $model->attributes=$_REQUEST['Zzgameseat'];
                                $model->gameseat_createdat = new CDbExpression('NOW()');
                                $model->gameseat_user_id = Yii::app()->user->id;
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

		if(isset($_POST['Zzgameseat']))
		{
			$model->attributes=$_POST['Zzgameseat'];
			if($model->save())
				$this->redirect(array('view','id'=>$model->gameseat_id));
		}

		$this->render('update',array(
			'model'=>$model,
		));
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
		$dataProvider=new CActiveDataProvider('Zzgameseat');
		$this->render('index',array(
			'dataProvider'=>$dataProvider,
		));
	}

	/**
	 * Manages all models.
	 */
	public function actionAdmin()
	{
		$model=new Zzgameseat('search');
		$model->unsetAttributes();  // clear any default values
		if(isset($_GET['Zzgameseat']))
			$model->attributes=$_GET['Zzgameseat'];

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
		$model=Zzgameseat::model()->findByPk($id);
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
		if(isset($_POST['ajax']) && $_POST['ajax']==='zzgameseat-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}
}
