<?php
$this->breadcrumbs=array(
	'Zzgameinsts'=>array('index'),
	$model->gameinst_id,
);

$this->menu=array(
	array('label'=>'List Zzgameinst', 'url'=>array('index')),
	array('label'=>'Create Zzgameinst', 'url'=>array('create')),
	array('label'=>'Update Zzgameinst', 'url'=>array('update', 'id'=>$model->gameinst_id)),
	array('label'=>'Delete Zzgameinst', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->gameinst_id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Zzgameinst', 'url'=>array('admin')),
);
?>

<h1>View Zzgameinst #<?php echo $model->gameinst_id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'gameinst_id',
		'gameinst_game_id',
		'gameinst_product_id',
		'gameinst_starttime',
		'gameinst_endtime',
		'gameinst_entrytime',
		'gameinst_capacity',
		'gameinst_threshold',
		'gameinst_paramnum1',
		'gameinst_paramnum2',
		'gameinst_paramnum3',
		'gameinst_paramnum4',
		'gameinst_paramnum5',
		'gameinst_paramnum6',
		'gameinst_paramnum7',
		'gameinst_paramnum8',
		'gameinst_paramnum9',
		'gameinst_paramnum10',
		'gameinst_paramdate1',
		'gameinst_paramdate2',
		'gameinst_paramdate3',
		'gameinst_paramdate4',
		'gameinst_paramdate5',
		'gameinst_paramvar1',
		'gameinst_paramvar2',
		'gameinst_paramvar3',
		'gameinst_paramvar4',
		'gameinst_paramvar5',
	),
)); ?>
