<?php $this->pageTitle=Yii::app()->name; ?>

<p>Games</p>
<br/>
<?php if(isset($gameInstList)){ ?>
    
    <ul>
    <?php foreach ($gameInstList as $gameInst) { ?>
        <li>
            <div> Game Prize : <?php echo $gameInst['gameinstProduct']['product_name']; ?></div>
            <div> Prize value : <?php echo $gameInst['gameinstProduct']['product_price']; ?></div>
            <div> Game Start Time : <?php echo $gameInst->gameinst_starttime ; ?></div>
            <div> Game End Time : <?php echo $gameInst->gameinst_endtime; ?></div>
            <div><?php echo CHtml::link("Book Seat", array("/gameseat/bookSeat",'Zzgameseat[gameseat_user_id]'=>  Yii::app()->user->id, 'Zzgameseat[gameseat_gameinst_id]' => $gameInst->gameinst_id)); ?></div>
        </li>
        <hr/>
        
        
    <?php } ?>
   </ul>
   
<?php }else{ ?>
    <p> No game scheduled </p>
<?php } ?>
