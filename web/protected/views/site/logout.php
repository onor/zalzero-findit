<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="language" content="en" />
        <script type="text/javascript">
            var baseUrl = "<?php echo Yii::app()->request->baseUrl;?>" + "/index.php";
        </script>

        <!-- blueprint CSS framework -->
        <link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/zalerioStyle.css" media="screen, projection" />
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

            .con{ width: 758px; height: 640px;
                text-align: center;
                position:relative;
            	background: url(<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/2.login_screen_additional/get_back_to_the_game/speechbubble.png) center 10px no-repeat
            }
           .con:after{
            	content:"Session Closed!";
            	position:absolute;
            	top:65px;
            	font-size:30px;
            	color:#fff;
            	left:210px;
            	width:350px
            }
            .innercon{
                /* background: url(<?php echo Yii::app()->request->baseUrl ?>/images/Dicesfloating.png) center 105px no-repeat */
            }
            #head{ width: 758px;  height: 146px;

            }
            #mid{
                text-align: center;
                margin: 0 auto;    
                height: 500px;
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/2.login_screen_additional/landing_screen/login_button.png) center 218px no-repeat
            }
            .girl{
            	margin-top:27px
            }
            .girl img{
            	padding-right: 25px;
            }
            .closethis{
				width: 175px;
				height: 90px;
				display: block;
				overflow: hidden;
				margin: 0px 0 0 292px;
				float: left;
				padding-top: 36px;
				background: url(<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/2.login_screen_additional/landing_screen/openinmoretab.png) 0px -126px no-repeat;    
            }
            .closethis:hover{
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/2.login_screen_additional/landing_screen/openinmoretab.png) 0px -1px no-repeat;                
            }
            .closethis, .playhere{
            		color:#fff;
            		font-size:21px;
            		text-decoration:none;
            		font-family: FuturaLT-Bold;
            }
            .playhere{
					width: 175px;
					height: 105px;
					display: block;
					overflow: hidden;
					font-size: 21px;
					margin: 0px 0 0 0px;
					float: left;
					padding-top: 20px;
					background: url(<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/2.login_screen_additional/landing_screen/openinmoretab.png) 0px -126px no-repeat;              
            }
            .playhere:hover{
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/2.login_screen_additional/landing_screen/openinmoretab.png) 0px -1px no-repeat;                
            }
        </style>

    </head>
    <body>
        <div class="con">
          <div class="innercon">   
            <div id="head">
                <!-- image remove -->
            </div>
            <div id="mid">
                <div class="girl">
						<img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/2.login_screen_additional/get_back_to_the_game/girl.png" />
                </div>
                <div class="login">
                    <a class="closethis" href="" onclick="return false;" >Session <br />Closed!</a>               
                </div> 
            </div>
          </div>
        </div>
    </body>
</html>
