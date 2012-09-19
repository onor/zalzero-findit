<?php
$this->breadcrumbs=array(
	'Zzproducts'=>array('index'),
	$model->product_id=>array('view','id'=>$model->product_id),
	'Update',
);

$this->menu=array(
	array('label'=>'List Zzproduct', 'url'=>array('index')),
	array('label'=>'Create Zzproduct', 'url'=>array('create')),
	array('label'=>'View Zzproduct', 'url'=>array('view', 'id'=>$model->product_id)),
	array('label'=>'Manage Zzproduct', 'url'=>array('admin')),
);
?>

<h1>Update Zzproduct <?php echo $model->product_id; ?></h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>