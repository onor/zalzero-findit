<div class="show_popup">
    <style type="text/css">
        div{ display: block; overflow: hidden}
        #friendpopup{ width: 531px; height: 460px; margin:0 auto;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/popupBase.png') center center no-repeat;
        }
        #close{ width: 12px; height: 12px; margin: 15px 17px 0 0;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/Popup_closebutton.png') no-repeat;
            float: right; clear: left
        }
        .selectfriendbase{ width: 526px; height: 432px;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/popupselectfriendbase.png') center center no-repeat;
            text-align: center
        }
        .title{ margin-top: 20px;  padding:0 75px 3px; }
        .findfriend { margin:0 auto; padding:10px 0; width:95%; border-bottom: 2px solid #909090;border-top: 2px solid #909090 }
        .findfriend *{float: left}
        .findfriend label{margin-left: 30px; font-family: arial; line-height: 21px }
        #findfrind{ width: 355px; height: 21px;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/Searchbar.png') no-repeat;
            border: none; margin-left: 5px;
        }
        #searhcbutton{ margin-left: -5px; }
        .friendlist{ width: 480px; height: 120px; margin-top: 5px; padding-left:30px; overflow-y:scroll }
        .basebutton{ width: 148px; height: 30px;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/basebutton.png') no-repeat;
            text-align: center; color:#fff;
            text-decoration: none; line-height: 28px;
            font-family: arial; font-weight: bold
        }
        .basebutton:hover{
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/basebutton_hover.png') no-repeat;
        }

        .rep{ height: 55px; width: 210px; float:left;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/Frienddescriptionbase.png') no-repeat;
            margin:4px 5px 0 0;             
        }
        .rep *{ float: left}
        .rep img{ padding: 5px}
        .rep div{
            color:#616362; line-height: 20px; font-family: arial; text-align: left; padding-top: 8px;
            width: 130px
        }            

        .select_button{ height: 30px; width: 97px;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/Selectbutton.png') no-repeat;
            display: block; overflow: hidden; line-height:30px; text-decoration: none; color: #fff;
            font-family: arial; font-size: 15px; margin-top:10px
        }

        .select_button:hover,.select_button.selected{
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/Selectbutton_hover.png') no-repeat;
            color:#fff
        }
        .show_popup{position:fixed;top:20%;z-index:9999;width:100%}
        .rep.selected{ background:#555; border-radius:6px }
        .fbottom{
            width:497px;
            height:164px;margin-left:18px; margin-top:30px;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/fun.png') no-repeat;
        }
        #sendFbInvite{
            color:#fff; text-decoration:none; width:122px; height:23px; display:block;float:right;margin:63px 15px 0 0;line-height:23px; font-weight:bold;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/button_invite.png') no-repeat;
        }
        #sendFbInvite:hover{
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/button_invitehover.png') no-repeat;
        }
    </style>
<div id="friendpopup">
    <a href="#" id="close" onClick="jQuery('.show_popup').remove(); return false;"></a>
    <div class="selectfriendbase">
        <img class="title" src=<?php echo Yii::app()->request->baseUrl."/images/inviteurfriends.png" ?> />
        <div class="findfriend">
            <label>Find Friends</label>                    
            <input type="text" value="" id="findfrind" name="findfrind" />
            <input type="image" id="searhcbutton" src=<?php echo Yii::app()->request->baseUrl."/images/Searchbar_button.png" ?> />
        </div>
        <div class="friendlist fblist"></div>
        <div class="fbottom">
            <a href="#" id="sendFbInvite" onclick="return false;" > Invite </a>
        </div>
        
    </div>            
</div>
</div>