<div class="show_popup zalerio_popup">
    <style type="text/css">
        .zalerio_popup div,.zalerio_popup{overflow: hidden}
        div{ display: block}
        .zalerio_popup .friendlist{
        	overflow: scroll;
        	overflow-x:hidden;
        }
        .title{ margin-top: 20px; border-bottom: 2px solid #909090; padding:0 75px 3px; }

        #searchbutton{ margin-left: -5px; }
        .friendlist{ width: 500px; height: 295px; margin-top: 5px; margin-bottom:0px; overflow-y:scroll }
        .left{ float:left !important }
        .right{ float:right !important }
        .rep *{ float: left}
        .rep div{
         line-height: 20px; color:#fff;text-align: left; padding-top: 8px;
            width: 130px
        }            
        .status{
            height: 18px;
            width: 18px;
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/zalerio_1.2/4.ingame_ui/indicator_offline.png') 1px 0 no-repeat;
            display: block;
            overflow: hidden;
            margin-top: 38px;
            text-indent:999px
        }

        .on{
            background: url('<?php echo Yii::app()->request->baseUrl; ?>/images/Indicator_online.png') -3px 1 no-repeat;
            margin-top: 35px
        }

        .show_popup{
            position:absolute;
            top:135px;
            z-index:9999;
            width:100%
        }
    </style>
<div id="friendpopup" class="outer_base">
    <a href="#" id="close" onClick="jQuery('.wait').remove(); jQuery('.show_popup').remove(); return false;"></a>

<div>

        <input type="image" id="sendrinvite" align="left"  src="/images/zalerio_1.2/avatar/1314909381.jpg" />
        <input type="image" id="sendpinvite" align="right"  src="/images/zalerio_1.2/avatar/1271191620.jpg" />
        

</div>            
    <div class="selectfriendbase inner_base_invite">
        <div class="findfriend">
            <label>Find Friends</label>                    
            <input type="text" value="" id="findfriend" name="findfrind" />
            <input type="image" id="searchButton"  src=<?php echo Yii::app()->request->baseUrl."/images/zalerio_1.2/5.all_popup/friendselection/friendselection_searchbar_button_ideal.png" ?> />
	    <a href="#" id='show_all_friends'>Show All</a>
        </div>
        <div class="friendlist">
       
        </div>
        <div class="footerbutton ">

            <input type="button" id="sendinvite" class="basebutton right" value="Send Challenge" />
        </div>
    </div>            
</div>
</div>
