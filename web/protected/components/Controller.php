<?php
Yii::import('application.vendors.*');
Yii::import('application.config.*');
require_once('facebook-php-sdk-b14edfa/src/facebookwrapper.php');
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
        
	function __construct($id,$module=null) {
    parent::__construct($id, $module);
    // Get an instance of the Facebook class distributed as the PHP SDK by facebook:
    $getFbCredentialsObj = new getFbCredentials();
    $getFbCredentials = $getFbCredentialsObj->getFbAppData();
    $fbAppID = $getFbCredentials['fbAppID'];
    $fbAppSecretId = $getFbCredentials['fbAppSecretId'];
    $fbCanvasPage = $getFbCredentials['fbCanvasPage'];
    $fbCanvasUrl = $getFbCredentials['fbCanvasUrl'];
    $fbAppName = $getFbCredentials['fbAppName'];

     Yii::app()->params['facebook'] = new FacebookWrapper(array(
      'appId'  => $fbAppID,
      'secret' => $fbAppSecretId,
      'cookie' => true,
      'appName' => $fbAppName,
      'canvasPage' => $fbCanvasPage,
      'canvasUrl'  => $fbCanvasUrl
      ));
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
    header('P3P: CP="CAO PSA OUR"');
    $facebook = Yii::app()->params['facebook'];
    $session = $facebook->getSession();
    $fbme = null;
    if ($session) {
      try {
        $uid = $facebook->getUser();
        $fbme = $facebook->api('/me');
      } catch (FacebookApiException $e) {
        print_r("<pre>".$e."</pre>");
      }
    }
    if ($fbme) {
      // call $filterChain->run() to continue filtering and action execution
      $filterChain->run();
    }
    else {
      $appId = $facebook->getAppId();
      if(isset($_REQUEST['gameinst_id']) && is_numeric($_REQUEST['gameinst_id'])) {
      		$canvasUrl = $facebook->getCanvasPage()."?gameinst_id=".$_REQUEST['gameinst_id'];
      } 
      else {
		$canvasUrl = $facebook->getCanvasPage();
      }
	// Set the required permissions for the application
      $perms = "email,user_birthday,sms,publish_stream,read_friendlists,friends_online_presence";
      $loginUrl = "https://www.facebook.com/dialog/oauth?scope=".$perms.
        "&client_id=".$facebook->getAppId().
        "&redirect_uri=".urlencode($canvasUrl);
      echo("<script> top.location.href='" . $loginUrl . "'</script>");
      Yii::app()->end();
    }
  }
	
	
        public function printmodel($model){
            if(is_object($model) || is_array($model)){
                echo "<PRE>";
                echo print_r($model->attributes);
                echo "</PRE>";
            }else{
                echo $model;
            }
        }
}
