<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="language" content="en" />
<script type="text/javascript">
            var baseUrl = "<?php echo Yii::app()->request->baseUrl; ?>";
        </script>
<link rel="shortcut icon"
	href="<?php echo Yii::app()->request->baseUrl; ?>/images/favicon.ico"
	type="image/x-icon" />
<!-- blueprint CSS framework -->
<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/screen.css" media="screen, projection" />
<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/print.css" media="print" />
<!--[if lt IE 8]>
<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/ie.css" media="screen, projection" />
<![endif]-->
 	
<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/form.css" />

<link rel="stylesheet" type="text/css"
	href="<?php echo Yii::app()->request->baseUrl; ?>/css/zalerioStyle.css" />

<?php
$userId = Yii::app()->user->getID();
$userEmail = getUserEmailId($userId);
?>
<?php
$usersummary = Zzgameusersummary::model()->findByAttributes(array('user_id'=>$userId));
$new_user = false;
if($usersummary['last_gameinst_id'] == NULL && $usersummary['last_gameinst_id'] == ''){
	$new_user = true;
}
?>
<script type="text/javascript">
            var userLoginId = '<?php echo $userEmail;?>';
           
            var belt_array = <?php echo CJSON::encode($GLOBALS['belt_array']);?>;
            var total_games_array = <?php echo CJSON::encode($GLOBALS['total_games_array']);?>;
            var total_won_array = <?php echo CJSON::encode($GLOBALS['total_won_array']);?>;
            <?php if($new_user == true){ ?>
            	var tutorial = true
            	
            <?php }else{?>
            
            	var tutorial = false
            	
             <?php } ?>
            
           
        </script>

<link rel="stylesheet" type="text/css"
	href="<?php echo Yii::app()->request->baseUrl; ?>/css/zalzero.tutorial.css" />
<?php 
$baseUrl = Yii::app()->request->baseUrl;
$cs = Yii::app()->clientScript;
$cs->registerCoreScript('jquery');

$cs->registerScriptFile(Yii::app()->baseUrl . '/js/zaleroJs_header.js');
$cs->registerScriptFile(Yii::app()->baseUrl . '/js/jquery.jscrollpane.min.js');
$cs->registerScriptFile(Yii::app()->baseUrl . '/js/jquery.mousewheel.js');
$cs->registerScriptFile(Yii::app()->baseUrl . '/js/OrbiterMicro_2.0.0.782_Release_min.js');
$cs->registerScriptFile(Yii::app()->baseUrl . '/js/zalerioScript.js');
$cs->registerScriptFile(Yii::app()->baseUrl . '/lib/require.js');
$cs->registerScriptFile(Yii::app()->baseUrl . '/dist/zalzero.tutorial-build.js');
$cs->registerScriptFile(Yii::app()->baseUrl . '/js/zzScript.js');


//$cs->registerScriptFile('http://connect.facebook.net/en_US/all.js');
?>

<title><?php echo CHtml::encode($this->pageTitle); ?></title>
<?php if($new_user == true ) {?>
<script>
	        jQuery(function() {
		        require(['zalzero.tutorial'], function(tutorial) {
	        		tutorial.launch();
	        	});
	        });
	        </script>
<?php } ?>

<script>
	        jQuery(document).ready(function() {
	        	require(['gameEventManager'], function(gameEventManager) {
		        	start = new gameEventManager();	        		
	        	});
	  
	        });
	        </script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script src="<?php echo Yii::app()->baseUrl.'/js/jquery.ui.touch-punch.min.js'?>"> </script>

<script>

  $(document).ready(function() {
      if (!$.browser.webkit){
            scrollBar = document.createElement("div");
            scrollBar.id ="slider";
            $("#scroll_carousel").after(scrollBar);
            var scrollbar =  $(scrollBar).slider({ 
                                orientation: "vertical",                                
                                min: 0,
                                max: 100,
                                value:100,
                                slide: function( event, ui ) {
                                        scroll_height = parseInt($( "#scroll_con" ).height());
                                        new_height = ((scroll_height * parseInt(ui.value))/100) + ( 350 * (100 - parseInt(ui.value) ) / 100);
                                        new_height  = 	scroll_height - new_height;
                                        $( "#scroll_con" ).css('margin-top',- new_height);
                                }
            });
            $( "#scroll_con" ).css('margin-top',0);
      }else{
         $("#scroll_carousel").css('overflow','scroll');
      }

  });
  </script>
<?php
Yii::app()->clientScript->registerScript(
'myHideEffect', '$(".flash-success").animate({opacity: 1.0}, 3000).fadeOut("slow");
		$(".flash-notice").animate({opacity: 1.0}, 3000).fadeOut("slow");
		$(".flash-error").animate({opacity: 1.0}, 3000).fadeOut("slow");
		', CClientScript::POS_READY
);
?>
</head>
<?php $facebookConfig = new facebookCredetials(); ?>
<body>
	<div id="fb-root"></div>
	<script>

        // Load the SDK Asynchronously
        (function(d){
            var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
            if (d.getElementById(id)) {return;}
            js = d.createElement('script'); js.id = id; js.async = true;
            js.src = "//connect.facebook.net/en_US/all.js";
            ref.parentNode.insertBefore(js, ref);
        }(document));

        
        jQuery(document).ready(function($) {
            window.fbAsyncInit = function() {
                FB.init({
                    appId      : "<?php echo $facebookConfig->config->appId; ?>",
                    channelUrl : '//WWW.YOUR_DOMAIN.COM/channel.html', // Channel File
                    status     : true, // check login status
                    cookie     : false, // enable cookies to allow the server to access the session
                    xfbml      : true  // parse XFBML
                });

                // Additional initialization code here
            };
        });
        
        </script>
	<div class="container" id="page">

		<!--	<div id="header">
                            <div id="logo"><?php echo CHtml::encode(Yii::app()->name); ?></div>
                            <div>
            <?php
            if (Yii::app()->user->isGuest) {
                echo CHtml::image($baseUrl . "/images/fb.gif", "Facebook Connect", array('onClick' => "javascript:facebookConnect();"));
            } else {
                if (isset(Yii::app()->user->user_fbid)) {
                    echo CHtml::image("https://graph.facebook.com/" . Yii::app()->user->user_fbid . "/picture", "user facebook image");
                } else {
                    //default image
                }
            }
            ?>
                                </div>
                    </div>-->
		<!-- header -->

		<!--<div id="mainmenu">
            <?php
            $this->widget('zii.widgets.CMenu', array(
                'items' => array(
                    array('label' => 'Home', 'url' => array('/site/index')),
                    array('label' => 'About', 'url' => array('/site/page', 'view' => 'about')),
                    array('label' => 'Products', 'url' => array('/product/index'), 'visible' => !Yii::app()->user->isGuest),
                    array('label' => 'GameInstances', 'url' => array('/gameinst/index'), 'visible' => !Yii::app()->user->isGuest),
                    array('label' => 'SignUp', 'url' => array('/user/create'), 'visible' => Yii::app()->user->isGuest),
                    array('label' => 'Login', 'url' => array('/site/login'), 'visible' => Yii::app()->user->isGuest),
                    array('label' => 'myGames', 'url' => array('/user/myGames'), 'visible' => !Yii::app()->user->isGuest),
                    array('label' => 'Logout (' . Yii::app()->user->name . ')', 'url' => array('/site/logout'), 'visible' => !Yii::app()->user->isGuest)
                ),
            ));
            ?>
            </div>-->
		<!-- mainmenu -->
		<!--    <?php if (Yii::app()->user->hasFlash('success')): ?>
                            <div class="flash-success">
                <?php echo Yii::app()->user->getFlash('success'); ?>
                            </div>
            <?php endif; ?>
            <?php if (Yii::app()->user->hasFlash('notice')): ?>
                            <div class="flash-notice">
                <?php echo Yii::app()->user->getFlash('notice'); ?>
                            </div>
            <?php endif; ?>
            <?php if (Yii::app()->user->hasFlash('error')): ?>
                            <div class="flash-error">
                <?php echo Yii::app()->user->getFlash('error'); ?>
                            </div>
            <?php endif; ?>
            <?php if (isset($this->breadcrumbs)): ?>
                <?php
                $this->widget('zii.widgets.CBreadcrumbs', array(
                    'links' => $this->breadcrumbs,
                ));
                ?>
            <?php endif ?>
            -->

		<?php echo $content; ?>

		<div class="clear"></div>
		<!--
            <div id="footer">
                    Copyright &copy; <?php echo date('Y'); ?> by My Company.<br/>
                    All Rights Reserved.<br/>
            <?php echo Yii::powered(); ?>
            </div>-->
		<!-- footer -->

	</div>
	<!-- page -->

	<div class="score_show_popup zalerio_popup" id="winnerscreen"
		style="display: none">
		<div id="score_friendpopup" class="outer_base">
			<a href="#" id="close" class="positionAbsolute"
				onClick="jQuery('.wait').remove(); jQuery('.score_show_popup').css('display','none'); return false;"></a>
			<div class="show_score">
				<div id="topScore_div"></div>
				<div id="bottomScore_div"></div>
				<div class="button_score">
					<a href="" id="rematch" class="rematch">Rematch</a> <a href=""
						class="dismiss"
						onclick="jQuery('.score_show_popup').hide(); return false;">Dismiss</a>
					<div></div>
				</div>
			</div>
		</div>
	</div>

	<div class="s_show_popup zalerio_popup">
		<div id="s_friendpopup" class="outer_base">
			<a href="#" id="close"
				onClick="jQuery('.wait').remove(); jQuery('.s_show_popup').css('display','none'); return false;"></a>
			<div id="popup-stats" class="popup-block">
				<div class="div_top">
					<div class="div_img">
						<img id="stat_image_tri_level" class="tri_level_belt" src='' /> <img
							id="stat_image_main" width='59' height='59' src='' /> <span
							id='stat_userName'></span>
					</div>
					<div class="div_top_right">
						<div id="lineone">
							<label>Joined :</label><label id="joined_time"></label><span
								id="user-level-span" class="GreenBelt"></span>
						</div>
						<div id="lineTwo">
							<!--Number of friends playing -->
							<span id="frnd_playing"></span>
						</div>
						<div id="lineThree">
							<!-- Number of friends not playing -->
							<span id="frnd_total"></span>
						</div>
					</div>
				</div>
				<ul class="tab_nav">
					<li><a id="mygames_a" class="active statslink" href="#">My Games</a>
					</li>
					<!--<li><a id="myfriends_a"href="#" class="statslink">My friends</a></li>-->
					<li><a id="mylevel_a" href="#" class="statslink">My Level</a></li>
				</ul>
				<div id="mygames_stats" class="div_botttom allstats">
					<div id="rip_active_container" class="div_left">
						<div class="titles">
							<span class="players">Players</span> <span class="round">Round</span>
							<span class="rank">Rank</span>
						</div>
						<div id='rip_active' class="scroll-pane scroll_bar">
							<div id='rip_active_rh'></div>
						</div>
					</div>
					<div class="div_right" id="rip_win_lost_container">
						<div class="won scroll-pane scroll_bar" id='rip_won'>
							<div id='rip_won_apg'></div>
						</div>
						<div class="lost scroll-pane scroll_bar" id='rip_lost'>

							<div id="rip_lost_apg"></div>
						</div>
					</div>
				</div>



				<!-- MY level pop up template -->
				<div id="mylevel_stats" class="my-level">
					<div id="mylevel-check">
						<div id="belt-info" class="belt-info">
							<h3>
								Congrats! You are a <span id="belt_text"></span> belt!
							</h3>


							<div class="next-belt">
								<h4>
									Want to be <span id="next_belt_h4"></span> belt ?<br />You
									need:
								</h4>
								<ul id="next_belt_ul">
								</ul>
							</div>
							<div class="dummy_div"></div>
							<div class="current-belt">
								<h4>
									You are a <span id="current_belt_h4"></span> belt.<br />So far:
								</h4>
								<ul id="current_belt_ul">
								</ul>
							</div>
							<div class="perform">
								<a id="learn-performance-level" href="#">Learn about performance
									levels</a>
							</div>
						</div>
						<div class="belts">
							<ul id="vertical_belt_conent">
							</ul>
						</div>
					</div>
				</div>






				<!-- Performance pop-up  -->
				<div id="performance_level" class="performance_level popup-block">
					<a href="#" id="close-performance-level" class="close closesound"></a>
					<h3>The journey to becoming a Black Belt</h3>
					<p>You start as a WHITE BELT and can progress as you play games and
						win them. Both conditions number of games played and number of
						games won must be met in order to advance to the next belt level.
						For example, you need to have played 10 games and you also need to
						have won at least 2 games in order to advance to the GREEN BELT
						level. We wish you much fun in becoming BLACK BELT.</p>
					<div class="belt_table">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<th class="level">Level</th>
								<th width="304" align="center">Number of Games played</th>
								<th align="center">Game's won</th>
							</tr>
							<tr>
								<td class="level">Black-belt</td>
								<td>>400</td>
								<td>>180</td>
							</tr>
							<tr>
								<td class="level">Brown-belt</td>
								<td>>200</td>
								<td>>90</td>
							</tr>
							<tr>
								<td class="level">Red-belt</td>
								<td>>100</td>
								<td>>40</td>
							</tr>
							<tr>
								<td class="level">Purple-belt</td>
								<td>>75</td>
								<td>>25</td>
							</tr>
							<tr>
								<td class="level">Blue-belt</td>
								<td>>30</td>
								<td>>8</td>
							</tr>
							<tr>
								<td class="level">Green-belt</td>
								<td>>10</td>
								<td>>2</td>
							</tr>
							<tr>
								<td class="level">Orange-belt</td>
								<td>>5</td>
								<td>>1</td>
							</tr>
							<tr>
								<td class="level">Yellow-belt</td>
								<td>>2</td>
								<td>>0</td>
							</tr>
							<tr>
								<td class="level">White-belt</td>
								<td>>1</td>
								<td>>0</td>
							</tr>
						</table>
					</div>
				</div>


			</div>
			<!-- end of popup-stats -->


			<!-- pop up help block template -->
			<div id="popup-help" class="help popup-block">
				<div id="showmehow">Show Me How</div>
				<ul class="help_nav">

					<li id="li-help-contact-us"><a id="a-help-contactus">Contact us</a>
					</li>
					<li id="li-help-tutorial"><a id="a-help-tutorial" class=" active">Game
							Rules</a></li>
				</ul>
				<!-- help tutorial block -->
				<div id="help-tutorial" class="tutorial">
					<h2></h2>

					<div class="rules-content">
						<h3 style="margin-left: 10px;">Content:</h3>
						<ul>
							<li><a id="tutorial_startgame" href="#">Start a game</a></li>
							<li><a id="tutorial_taketurn" href="#">Take your turn</a></li>
							<li><a id="tutorial_scoring" href="#">Scoring and winning</a></li>
							<li><a id="tutorial_jokers" href="#">Overview of hidden numbers
									and jokers</A></li>
						</ul>
					</div>
					<div id="rules-container" class="rules-container">
						<div class="rules">
							<div id="tutorial_0" class="fll">
								<h3>You need some smarts to win ZALERIO! Find things first
									before your friends!</h3>
								<p>
									<strong>ZALERIO</strong> is a fun game for you and your friends
									for 2 to 5 players per game! You place tiles and try to uncover
									as many objects and Jokers as you can over the course of 7
									rounds. The player who scores the highest wins! But be careful,
									there are some twists that you need to watch out for!
								</p>
							</div>
							<div id="tutorial_1" class="fll">
								<h3 id="startgame">1. Start a game.</h3>
								<img
									src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_start_a_ game.png"
									align="Start a Game">
								<p>
									Click <strong>'START A GAME'</strong> and <strong>Invite
										Friends</strong> to play a game with you.
								</p>
								<img
									src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_search_bar.png"
									alt="Find Friends">
								<p>
									The <strong>friend search bar</strong> helps you find your
									friends. You can also simply scroll through the list with
									pictures and select. Your friends do not have to be online at
									the same time in order to play the game but you might get a
									faster response if they are. If they are not online right now
									they will still get invited and can chose to join as soon as
									they receive the invite. It is most fun to play the game with 5
									players so invite your friends! You will see why soon!
								</p>
								<img class="none"
									src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_indicator_green.png"
									alt="online">
								<p>Green represents online</p>
								<img class="none"
									src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_indicator_red.png"
									alt="online">
								<p>Red represents offline</p>
								<img
									src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_select_friend.png"
									align="select Friend">
								<p>
									Just click <strong>'SELECT'</strong> for each friend you would
									like to invite to the game. It will show you clearly if your
									friend has declined your request earlier. Keep trying to
									convince your friends to play but don't annoy them � you might
									be called a "whiner"!
								</p>

								<p>
									<img class="left"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_send _challange.png"
										alt="Send Challenge"> After you select your friends press <strong>'SEND
										CHALLENGE'</strong> to start the game. Invites will be sent to
									your friends and you will be able to see who accepted and who
									did not. This way you can see which players you invited joined
									your game! You can start multiple games at the same time with
									many friends, which is even more fun. So don't be shy,
									challenge them and see who has the best strategy! There is an
									exciting journey to become a black-belt!
								</p>

							</div>
							<div id="tutorial_2" class="fll">
								<h3 id="taketurn">2. Take Your Turn</h3>
								<ul>
									<li>Now it's your turn! You have a total of 9 tiles to place in
										each round and you have a total of 7 rounds to play in order
										to win the game!</li>
									<li>A red tile with a search mark <img class="none"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/3.ingame_board/board/board_question_mark_tile.png"
										alt=""> represents a new tile which has been placed on the
										board by you in the current round.
									</li>

									<li>Green tiles <img class="none"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_tile_with _white_digit.png"
										alt=""> remain green if they "hit" (your placement uncovered a
										part of a hidden object). The digit on the tile represents the
										number of players who have placed a tile on the same space (if
										you hover over the tile you will also see pictures of your
										friends who placed a tile on the same space).
									</li>

									<li>An orange tile <img class="none"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_yellow_tile.png"
										alt=""> represents a "miss" by you. A grey tile <img
										class="none"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_grey_tile.png"
										alt=""> represents a miss by one of your fellow players. A
										"miss" is a tile that was placed that did not uncover any part
										of a hidden object or hit a joker. You will always see your
										own misses and the misses of your fellow players.
									</li>


									<li style="list-style-position: inside"><img class="left"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_complete_ digit.png"
										alt="">When the object is fully uncovered by you all the tiles
										turn blue. Only you will see your uncovered objects. Here an
										example of the object 5. Each object from 0 - 9 is placed on
										the grid. To make this a little harder the objects can be
										placed in any position. To see all of the hidden objects you
										can uncover please take a look at the cheat sheet.</li>

									<li>To add to the fun, there are up to <strong>6 Joker</strong>
										tiles <img class="none"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_joker_tile.png"
										alt=""> hidden on the grid. Whoever hits the Joker first gets
										<strong style="text-decoration: underline">33 joker points</strong>
										- so go for it!
									</li>

									<li>Even better, there is only <strong>1 Super Joker</strong>
										tile <img class="none"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_sjoker_tile.png"
										alt=""> hidden on the board. Whoever hits the Super Joker
										first gets <strong style="text-decoration: underline">77
											super-joker points!</strong> Find him and you have an edge!
										(by the way � if multiple players uncover the Joker or
										Super-Joker in the same round they all get the bonus joker
										points!)
									</li>

									<li>Once you have placed your tiles and you are satisfied with
										all your <img class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_play_ button.png"
										alt=""> moves press the <strong>"PLAY"</strong> button. The
										results of each round will only be published when all players
										have placed their tiles. So it might take a while until your
										friends have all played but that just makes it more exciting
										as you can plan your strategy for your next moves. Ever played
										chess?
									</li>

									<li>There are <strong>7 rounds in total</strong> in every game
										<img class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_round_bar.png"
										alt=""> (6 rounds + 1 final round). On the dashboard, the
										current round is highlighted in yellow. The past rounds are
										highlighted in brown. The rounds not played yet are grey
									</li>

									<li>On the left-hand side you can see who <img class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_left_hud.png"
										alt=""> is playing with you. The user panel below is of great
										help for you to manage the game with your friends (i.e. who is
										online, who you should remind to play, etc.)
									</li>

									<li><img class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_right_hud.png"
										alt=""> On the right hand side you can see all the other games
										that you are participating in. There are two sections your
										turn and their turn. In <strong>"YOUR TURN"</strong> you will
										find the games in which it is your turn to place your tiles.
										In <strong>"THEIR TURN"</strong> you will find all the games
										in which your fellow players currently need to place tiles.</li>

									<li>On the score board you can see the arrow in front of
										players who have not yet placed their bet in a particular
										round. Never <img class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_arrow _on score_board.png"
										alt=""> be the last one placing your tiles as you might get a
										"boooh" from your friends :>). If you don't want to finish the
										game then just press select resign under the more button.
									</li>

									<li>If our gentle reminders don't work well enough we will need
										your help! <img class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_carousel.png"
										alt=""> You always have the opportunity to poke your friends
										and remind them to play. Just click <strong>"remind symbol -
											BELL..."</strong> and a notification will be sent. In both
										sections of "their turn" and "my turn" players will have a
										choice to see all the players playing the game.
									</li>
								</ul>

							</div>
							<div id="tutorial_3" class="fll">
								<h3 id="scoring">3. Scoring and Winning.</h3>
								<p>
									To win you have to score points. To score points you have to
									uncover or find hidden objects and jokers. There are a total of
									10 objects <strong>hidden on</strong> the board. In addition,
									there are 6 Jokers and 1 Super Joker. The minimum number of
									players who can start a game is 2. But you are free to start
									games with up to 5 players. The more the merrier!
								</p>
								<p>To find objects and Jokers you should keep the following in
									mind:</p>
								<h3>A) In-game Points</h3>
								<ul>
									<li>You have 9 search tiles in total for each of the 7 rounds,
										so you have a total of 63 tiles to play per game</li>

									<li>The completion of objects <strong>1 - 9 gives 100 points</strong>
										each and the completion of the object <strong>0 gives 200
											points</strong>
									</li>

									<li>The score of completed objects is evenly split among all
										the players who complete the object during the whole game</li>

									<li>Each correctly placed tile gives <strong>10 points</strong>.
										Each wrongly placed tile gives a <strong>penalty of -10 (minus
											10) points</strong>
									</li>
								</ul>
								<h3>B) Bonus Points</h3>
								<ul>
									<li>In order to win the bonus points, player has to be the 1st
										one to find and finish the object. Each player who uncovers a
										object first receives an <strong>additional 75 bonus points</strong>
										(bonus points are never split among other players who uncover
										the same object later).
									</li>
								</ul>
								<h3>C) Joker and Super Jokers</h3>
								<ul>
									<li>A joker is a "one tile object" only so you can uncover the
										joker with just one hit. The uncovered jokers will be visible
										to other players so that you can party together! Also, we want
										to make sure your friends always have to look jealously at
										your Joker wins! The Joker and Super Joker is exclusive to
										whoever hits it first. In case of a tie (more than one player
										uncovers the joker in the same round) all players who uncover
										the joker are awarded the full Joker points.</li>

									<li>There are up to 6 "Jokers" and 1 "Super Joker"</li>

									<li>Each Joker gets <strong>33 points</strong>, 1 <strong>Super
											Joker gets 77 points</strong>.
									</li>
								</ul>
								<p>So let us give you a final hint: Try to complete as many
									objects as possible that require the least amount of tiles but
									at the same time are not targeted by too many other players. Of
									course, try to get all the Jokers as well.</p>
								<h3>D) Final Tips.</h3>
								<ul>
									<li>Check out Help if you are stuck or want some more
										information. If you want to contact us just jot down an email
										and we will reply as quickly as we can! Let us <img
										class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_help_button.png"
										alt=""> know anytime what you think. We love your feed-back as
										it helps as to make better games for you!
									</li>

									<li>Stats is your own dashboard and performance section. <img
										class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_stats_ button.png"
										alt=""> You can get all the details like how many games you
										played, which of your friends has and has not played with you
										and what your overall performance and level is. This is
										designed for you in a way so that you can easily plan your
										next games.
									</li>

									<li><img class="right"
										src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/tutorial_more_ button.png"
										alt="">Under "MORE" you find the resign button. Just in case
										you are really ready to let your friends win!</li>

								</ul>
								<p>
									Now let's go and sharpen your strategy skills while having fun
									with friends!<br> <br> Your ZALERIO Team!
								</p>
							</div>
							<div id="tutorial_4" class="fll">
								<h3 id="jokers">4. Overview of hidden objects and jokers</h3>
								<table border="1" width="90%">
									<tr>
										<th bgcolor="#ccc">Object</th>
										<th bgcolor="#999">Shape</th>
										<th bgcolor="#CCCCCC">Tiles needed to complete object</th>
										<th bgcolor="#999">Available points if object is fully
											uncovered</th>
									</tr>
									<tr class="even">
										<td valign="middle" align="center">0</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/0.png"
											alt=""></td>
										<td>12</td>
										<td>200</td>
									</tr>
									<tr class="odd">
										<td>1</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/1.png"
											alt=""></td>
										<td>6</td>
										<td>100</td>
									</tr>
									<tr class="even">
										<td>2</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/2.png"
											alt=""></td>
										<td>11</td>
										<td>100</td>
									</tr>
									<tr class="odd">
										<td>3</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/3.png"
											alt=""></td>
										<td>11</td>
										<td>100</td>
									</tr>
									<tr class="even">
										<td>4</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/4.png"
											alt=""></td>
										<td>7</td>
										<td>100</td>
									</tr>
									<tr class="odd">
										<td>5</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/5.png"
											alt=""></td>
										<td>11</td>
										<td>100</td>
									</tr>
									<tr class="even">
										<td>6</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/6.png"
											alt=""></td>
										<td>12</td>
										<td>100</td>
									</tr>
									<tr class="odd">
										<td>7</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/7.png"
											alt=""></td>
										<td>8</td>
										<td>100</td>
									</tr>
									<tr class="even">
										<td>8</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/8.png"
											alt=""></td>
										<td>13</td>
										<td>100</td>
									</tr>
									<tr class="odd">
										<td>9</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/9.png"
											alt=""></td>
										<td>12</td>
										<td>100</td>
									</tr>
									<tr class="even">
										<td>Joker</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/j.png"
											alt=""></td>
										<td>1</td>
										<td>33</td>
									</tr>
									<tr class="odd">
										<td>Super Joker</td>
										<td><img
											src="<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/5.all_popup/help/tutorial/sj.png"
											alt=""></td>
										<td>1</td>
										<td>77</td>
									</tr>
								</table>
							</div>
						</div>
					</div>

				</div>

				<!-- help contact us block -->
				<div id="help-contactus" class="popup-block"></div>

			</div>
		</div>

	</div>





</body>
</html>
