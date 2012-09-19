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

        <link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/main.css" />
        <link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/form.css" />
        
        <?php
            $baseUrl=Yii::app()->request->baseUrl;
            $cs= Yii::app()->clientScript;
            $cs->registerCoreScript('jquery');

            $cs->registerScriptFile(Yii::app()->baseUrl.'/js/zaleroJs_header.js');

            //$cs->registerScriptFile('http://connect.facebook.net/en_US/all.js');
        ?>
        <title><?php echo CHtml::encode($this->pageTitle); ?></title>

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
        
        <script type="text/javascript">
            
            (function($) {
	if(!document.defaultView || !document.defaultView.getComputedStyle){ // IE6-IE8
		var oldCurCSS = $.curCSS;
		$.curCSS = function(elem, name, force){
			if(name === 'background-position'){
				name = 'backgroundPosition';
			}
			if(name !== 'backgroundPosition' || !elem.currentStyle || elem.currentStyle[ name ]){
				return oldCurCSS.apply(this, arguments);
			}
			var style = elem.style;
			if ( !force && style && style[ name ] ){
				return style[ name ];
			}
			return oldCurCSS(elem, 'backgroundPositionX', force) +' '+ oldCurCSS(elem, 'backgroundPositionY', force);
		};
	}
	
	var oldAnim = $.fn.animate;
	$.fn.animate = function(prop){
		if('background-position' in prop){
			prop.backgroundPosition = prop['background-position'];
			delete prop['background-position'];
		}
		if('backgroundPosition' in prop){
			prop.backgroundPosition = '('+ prop.backgroundPosition;
		}
		return oldAnim.apply(this, arguments);
	};
	
	function toArray(strg){
		strg = strg.replace(/left|top/g,'0px');
		strg = strg.replace(/right|bottom/g,'100%');
		strg = strg.replace(/([0-9\.]+)(\s|\)|$)/g,"$1px$2");
		var res = strg.match(/(-?[0-9\.]+)(px|\%|em|pt)\s(-?[0-9\.]+)(px|\%|em|pt)/);
		return [parseFloat(res[1],10),res[2],parseFloat(res[3],10),res[4]];
	}
	
	$.fx.step. backgroundPosition = function(fx) {
		if (!fx.bgPosReady) {
			var start = $.curCSS(fx.elem,'backgroundPosition');
			if(!start){//FF2 no inline-style fallback
				start = '0px 0px';
			}
			
			start = toArray(start);
			fx.start = [start[0],start[2]];
			var end = toArray(fx.end);
			fx.end = [end[0],end[2]];
			
			fx.unit = [end[1],end[3]];
			fx.bgPosReady = true;
		}
		//return;
		var nowPosX = [];
		nowPosX[0] = ((fx.end[0] - fx.start[0]) * fx.pos) + fx.start[0] + fx.unit[0];
		nowPosX[1] = ((fx.end[1] - fx.start[1]) * fx.pos) + fx.start[1] + fx.unit[1];           
		fx.elem.style.backgroundPosition = nowPosX[0]+' '+nowPosX[1];

	};
})(jQuery);
            var countImg = 1;
            jQuery(function($){
               $('.girl img').css('display','none');
               $('.girl img:nth-child(1)').css('display','block');
               $('.con').css({'backgroundPosition': $('#head').offset().left +200+"px 366px"});
               
               //$('.con').css('backgroundPosition','-350px 373px');  uncomment if need animation
               
               setInterval(function animateGirl(){
                   $('.girl img').css('display','none');
                   $('.girl img:nth-child('+countImg+')').css('display','block');
                   countImg++;
                   if(countImg == 13){
                       countImg =1;
                   }
               },85);
               
               // animate login button
               /* disable by commit
               $('.login').css('left', -350).animate({
                    left: $('#head').offset().left + 250
                }, 400, function() {
                    $('.login').animate({
                        left: $('#head').offset().left + 210
                    }, 100, function() {
                        $('.login').css('position','static')
                    });
                });
                
                $('.con').animate({
                    'backgroundPosition': $('#head').offset().left + 229+" 373"
                },400, function() {
                    $('.con').animate({
                       'backgroundPosition': $('#head').offset().left +200+" 373"
                    }, 100, function() {
                        
                    });
                });               
                
                */
               $(window).resize(function() {
                    $('.con').css({'backgroundPosition': $('#head').offset().left +200+"px 366px"});
                    $('.login').css({'position': 'static'});
                });
            })                
        </script>

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
                background: url(images/Base_login_button.png) 495px 366px no-repeat; 
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
            #mid .facebook,#mid .invite{
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/Facebook-connect-Button.png) left top no-repeat;
                height: 55px;
                width: 337px;
                display: block;
                overflow: hidden;
                text-indent: 9999px;
                margin: 0 auto;
            }

            #mid .facebook:hover{
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/Facebook-connect-Button_hover.png) left top no-repeat;
            }

            #mid .invite{
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/Requestaninvite.png) left top no-repeat;
            }
            #mid .invite:hover{
                background: url(<?php echo Yii::app()->request->baseUrl ?>/images/Requestaninvite_hover.png) left top no-repeat;
            }


            /* login button animations 
            .login{ 
                position:absolute;
                left: -350px;
                top:446px
            }
            */
            
            .login{
                position:absolute;
                top:446px;
                left: 505px;
            }
      

            #prefinery_overlay{overflow: hidden}

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
        <img src="<?php echo Yii::app()->request->baseUrl ?>/images/Bg.png" style="display:none" />
        <div class="con">
          <div class="innercon">   
            <div id="head">
                <!-- image remove -->
            </div>
            <div id="mid">
                <div class="girl">
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0001.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0002.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0003.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0004.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0005.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0006.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0007.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0008.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0009.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0010.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0011.png" />
                    <img src="<?php echo Yii::app()->request->baseUrl ?>/images/zalerio_1.2/1.mainscreen/girl_animation_sprite/ani0012.png" />
                </div>
                <div class="login">
                    <a class="facebook" onclick="facebookConnect(); return false;" href="#">&nbsp;</a>
                    <a class="invite" id="prefinery_apply_link"  onclick="return false;" href="#">&nbsp;</a>                    
                </div> 
            </div>
          </div>
        </div>
    </body>
</html>
