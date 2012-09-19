<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="language" content="en" />
        <script type="text/javascript">
            var baseUrl = "<?php echo Yii::app()->request->baseUrl;?>" + "/index.php";
        </script>

        <!-- blueprint CSS framework -->
        <link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/screen.css" media="screen, projection" />
        <link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/print.css" media="print" />
        <!--[if lt IE 8]>
        <link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/ie.css" media="screen, projection" />
        <![endif]-->
        <?php
            $baseUrl=Yii::app()->request->baseUrl;
            $cs= Yii::app()->clientScript;
            $cs->registerCoreScript('jquery');

            $cs->registerScriptFile(Yii::app()->baseUrl.'/js/zaleroJs_header.js');
        ?>
        <title><?php echo CHtml::encode($this->pageTitle); ?></title>

        <style type="text/css">
           body{
                margin: 0 auto;
                background:#111 url(<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/bg.png) center top no-repeat;
                text-align: center;
            }
            div{
                display:block; overflow-x:visible; overflow-y:hidden; margin: 0 auto;text-align: center;
            }

            .con{ width: 100%; height: 640px;
                text-align: center;

            }
            .innercon{
                /*background: url(<?php echo Yii::app()->request->baseUrl ?>/images/Dicesfloating.png) center 105px no-repeat */
            }
            #head{ width: 758px;  height: 146px}
            #mid{
                text-align: center;
                margin: 0 auto;    
                height: 500px
            }
            #mid .girl img{ margin: 0 auto; display: none }
            .goback{
                width: 349px;
                height: 69px;
                display: block;
                overflow: hidden;
                margin: 295px auto 0;
                text-indent:9999px;
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/getbacktourgame_ideal.png) no-repeat;                
            }
            .goback:hover{
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/getbacktourgame_hover.png) 2px 2px no-repeat;                
            }
        </style>

    </head>
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

    <body>
        <img src="<?php echo Yii::app()->request->baseUrl ?>/images/Errorscreen_BG.jpg" style="display:none" />
        <div class="con">
          <div class="innercon">   
            <div id="head">
                <!-- image remove -->
            </div>
            <div id="mid">
                <div class="girl">

                </div>
                <div class="login">
                    <a class="goback" href="<?php echo Yii::app()->request->baseUrl ?>/gameinst/play?gameinst_id=0">&nbsp;</a>                  
                </div> 
            </div>
          </div>
        </div>
    </body>
</html>
