<?php
$this->breadcrumbs=array(
	'Zzusers'=>array('index'),
	$model->user_id=>array('view','id'=>$model->user_id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Zzuser', 'url'=>array('index')),
	array('label'=>'Create Zzuser', 'url'=>array('create')),
	array('label'=>'View Zzuser', 'url'=>array('view', 'id'=>$model->user_id)),
	array('label'=>'Manage Zzuser', 'url'=>array('admin')),
);
?>

<h1>Update Zzuser <?php echo $model->user_id; ?></h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>