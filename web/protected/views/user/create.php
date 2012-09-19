<?php
$this->breadcrumbs=array(
	'Zzusers'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Zzuser', 'url'=>array('index')),
	array('label'=>'Manage Zzuser', 'url'=>array('admin')),
);
?>

<h1>Create Zzuser</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>