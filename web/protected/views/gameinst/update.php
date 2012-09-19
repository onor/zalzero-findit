<?php
$this->breadcrumbs=array(
	'Zzgameinsts'=>array('index'),
	$model->gameinst_id=>array('view','id'=>$model->gameinst_id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Zzgameinst', 'url'=>array('index')),
	array('label'=>'Create Zzgameinst', 'url'=>array('create')),
	array('label'=>'View Zzgameinst', 'url'=>array('view', 'id'=>$model->gameinst_id)),
	array('label'=>'Manage Zzgameinst', 'url'=>array('admin')),
);
?>

<h1>Update Zzgameinst <?php echo $model->gameinst_id; ?></h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>