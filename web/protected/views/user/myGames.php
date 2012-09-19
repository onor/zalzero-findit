<?php
$this->breadcrumbs=array(
	'Zzusers'=>array('index'),
	'myGames',
);

?>

<h1>My Games</h1>

<ul>
<?php foreach ($games as $game) { ?>
    
    <li>Game Prize : <?php echo $game['gameseatGameinst']['gameinstProduct']['product_name'] ;?> </li>
    <li>Prize Value : <?php echo $game['gameseatGameinst']['gameinstProduct']['product_price'] ;?> </li>
    <li>start Time : <?php echo $game['gameseatGameinst']['gameinst_starttime'] ;?> </li>
    <li>End Time : <?php echo $game['gameseatGameinst']['gameinst_endtime'] ;?> </li>
    <li> <?php echo CHtml::link('Play',array('gameinst/play','gameinst_id'=>$game['gameseatGameinst']['gameinst_id'])) ;?>
    <hr/>        
<?php
    
    }
?>
</ul>