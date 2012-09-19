<?php
$this->breadcrumbs=array(
	'Zzusers'=>array('index'),
	$model->user_id,
);

$this->menu=array(
	array('label'=>'List Zzuser', 'url'=>array('index')),
	array('label'=>'Create Zzuser', 'url'=>array('create')),
	array('label'=>'Update Zzuser', 'url'=>array('update', 'id'=>$model->user_id)),
	array('label'=>'Delete Zzuser', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->user_id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Zzuser', 'url'=>array('admin')),
);
?>

<h1>View Zzuser #<?php echo $model->user_id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'user_id',
		'user_name',
		'user_email',
		'user_fbid',
		'user_password',
		'user_handle',
	),
)); ?>
