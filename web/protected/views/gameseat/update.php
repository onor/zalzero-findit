<?php
$this->breadcrumbs=array(
	'Zzgameseats'=>array('index'),
	$model->gameseat_id=>array('view','id'=>$model->gameseat_id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Zzgameseat', 'url'=>array('index')),
	array('label'=>'Create Zzgameseat', 'url'=>array('create')),
	array('label'=>'View Zzgameseat', 'url'=>array('view', 'id'=>$model->gameseat_id)),
	array('label'=>'Manage Zzgameseat', 'url'=>array('admin')),
);
?>

<h1>Update Zzgameseat <?php echo $model->gameseat_id; ?></h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>