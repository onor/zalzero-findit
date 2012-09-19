<?php
$this->breadcrumbs=array(
	'Zzgameseats'=>array('index'),
	'gameSeat',
);

?>

<h1>Game Seat </h1>

<ul>
    <li>Game Prize : <?php echo $model['gameseatGameinst']['gameinstProduct']['product_name'] ;?> </li>
    <li>Prize Value : <?php echo $model['gameseatGameinst']['gameinstProduct']['product_price'] ;?> </li>
    <li>start Time : <?php echo $model['gameseatGameinst']['gameinst_starttime'] ;?> </li>
    <li>End Time : <?php echo $model['gameseatGameinst']['gameinst_endtime'] ;?> </li>
    <li> <?php echo CHtml::link('Play',array('gameinst/play','gameinst_id'=>$model['gameseatGameinst']['gameinst_id'])) ;?>
    <hr/>        
</ul>