<?php
$this->breadcrumbs=array(
	'Zzgameseats'=>array('index'),
	$model->gameseat_id,
);

$this->menu=array(
	array('label'=>'List Zzgameseat', 'url'=>array('index')),
	array('label'=>'Create Zzgameseat', 'url'=>array('create')),
	array('label'=>'Update Zzgameseat', 'url'=>array('update', 'id'=>$model->gameseat_id)),
	array('label'=>'Delete Zzgameseat', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->gameseat_id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Zzgameseat', 'url'=>array('admin')),
);
?>

<h1>View Zzgameseat #<?php echo $model->gameseat_id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'gameseat_id',
		'gameseat_user_id',
		'gameseat_gameinst_id',
		'gameseat_createdat',
		'create_time',
		'update_time',
	),
)); ?>
