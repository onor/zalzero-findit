<?php
$this->breadcrumbs=array(
	'Zzgameinsts'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Zzgameinst', 'url'=>array('index')),
	array('label'=>'Manage Zzgameinst', 'url'=>array('admin')),
);
?>

<h1>Create Zzgameinst</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>