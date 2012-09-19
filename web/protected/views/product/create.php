<?php
$this->breadcrumbs=array(
	'Zzproducts'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Zzproduct', 'url'=>array('index')),
	array('label'=>'Manage Zzproduct', 'url'=>array('admin')),
);
?>

<h1>Create Zzproduct</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>