<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="en" />
	<link rel="shortcut icon" href="<?php echo Yii::app()->request->baseUrl; ?>/images/favicon.ico" type="image/x-icon" />
        <script type="text/javascript">
            var baseUrl = "<?php echo Yii::app()->request->baseUrl;?>" + "/index.php";
        </script>

	<!-- blueprint CSS framework -->
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/screen.css" media="screen, projection" />
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/print.css" media="print" />
	<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/ie.css" media="screen, projection" />
	<![endif]-->

	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/main.css" />
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/form.css" />
        
        <?php
            $baseUrl=Yii::app()->request->baseUrl;
            $cs= Yii::app()->clientScript;
            $cs->registerCoreScript('jquery');
            
            $cs->registerScriptFile(Yii::app()->baseUrl.'/js/zaleroJs_header.js');
            
            //$cs->registerScriptFile('http://connect.facebook.net/en_US/all.js');
        ?>
	<title>Zalerio</title>
        
        <?php
            Yii::app()->clientScript->registerScript(
            'myHideEffect',
            '$(".flash-success").animate({opacity: 1.0}, 3000).fadeOut("slow");
                $(".flash-notice").animate({opacity: 1.0}, 3000).fadeOut("slow");
                $(".flash-error").animate({opacity: 1.0}, 3000).fadeOut("slow");
            ',
            CClientScript::POS_READY
            );
        ?>

</head>

<body>
<div id="fb-root"></div>
<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : oloFBAppId, // App ID
      channelUrl : '//WWW.YOUR_DOMAIN.COM/channel.html', // Channel File
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true  // parse XFBML
    });

    // Additional initialization code here
  };

  // Load the SDK Asynchronously
  (function(d){
     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement('script'); js.id = id; js.async = true;
     js.src = "//connect.facebook.net/en_US/all.js";
     ref.parentNode.insertBefore(js, ref);
   }(document));
</script>
<div class="container" id="page">

	<div id="header">
		<div id="logo"><?php echo CHtml::encode(Yii::app()->name); ?></div>
                <div>
                    <?php 
                        if(Yii::app()->user->isGuest){
                            echo CHtml::image($baseUrl . "/images/fb.gif", "Facebook Connect", array('onClick'=>"javascript:facebookConnect();")); 
                        }else{
                            if(isset(Yii::app()->user->user_fbid)){
                                echo CHtml::image("https://graph.facebook.com/" . Yii::app()->user->user_fbid . "/picture", "user facebook image"); 
                            }else{
                                //default image
                            }
                            
                        }
                    ?>
                    </div>
	</div>

<!-- header -->

	<div id="mainmenu">
		<?php $this->widget('zii.widgets.CMenu',array(
			'items'=>array(
				array('label'=>'Home', 'url'=>array('/site/index')),
				array('label'=>'About', 'url'=>array('/site/page', 'view'=>'about')),
                                array('label'=>'Products', 'url'=>array('/product/index'), 'visible'=>Yii::app()->user->checkAccess('admin')),
                                array('label'=>'Users', 'url'=>array('/user/admin'), 'visible'=>Yii::app()->user->checkAccess('admin')),
                                array('label'=>'GameInstances', 'url'=>array('/gameinst/index'), 'visible'=>Yii::app()->user->checkAccess('admin')),
                                array('label'=>'GenerateFigures', 'url'=>array('/gameinst/generateZalerioFigures'), 'visible'=>Yii::app()->user->checkAccess('admin')),
                                array('label'=>'SignUp', 'url'=>array('/user/create'),'visible'=>  Yii::app()->user->isGuest),
				array('label'=>'Login', 'url'=>array('/site/login'), 'visible'=>Yii::app()->user->isGuest),
				array('label'=>'myGames','url'=>array('/user/myGames'),'visible'=>!Yii::app()->user->isGuest),
                                array('label'=>'Logout ('.Yii::app()->user->name.')', 'url'=>array('/site/logout'), 'visible'=>!Yii::app()->user->isGuest)
			),
		)); ?>
	</div><!-- mainmenu -->
        <?php if(Yii::app()->user->hasFlash('success')):?>
            <div class="flash-success">
                <?php echo Yii::app()->user->getFlash('success'); ?>
            </div>
        <?php endif; ?>
        <?php if(Yii::app()->user->hasFlash('notice')):?>
            <div class="flash-notice">
                <?php echo Yii::app()->user->getFlash('notice'); ?>
            </div>
        <?php endif; ?>
        <?php if(Yii::app()->user->hasFlash('error')):?>
            <div class="flash-error">
                <?php echo Yii::app()->user->getFlash('error'); ?>
            </div>
        <?php endif; ?>
	<?php if(isset($this->breadcrumbs)):?>
		<?php $this->widget('zii.widgets.CBreadcrumbs', array(
			'links'=>$this->breadcrumbs,
		)); ?><!-- breadcrumbs -->
	<?php endif?>
                
	<?php echo $content; ?>

	<div class="clear"></div>

	<div id="footer">
		Copyright &copy; <?php echo date('Y'); ?> by My Company.<br/>
		All Rights Reserved.<br/>
		<?php echo Yii::powered(); ?>
	</div><!-- footer -->

</div><!-- page -->
<script type="text/javascript">
  var prefineryJsHost = ("https:" == document.location.protocol) ? 'https://www.prefinery.com/' : 'http://www.prefinery.com/';
  document.write(unescape("%3Cscript src='" + prefineryJsHost + "javascripts/widget.js' type='text/javascript'%3E%3C/script%3E"))
</script>
<script type='text/javascript' charset='utf-8'>
  var prefinery_apply_options = {
    account: "mobicules",
    beta_id: "3092",
    link_id: "prefinery_apply_link"
  };
  Prefinery.apply(prefinery_apply_options);
</script>
</body>
</html>
