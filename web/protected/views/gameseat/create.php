<?php
$this->breadcrumbs=array(
	'Zzgameseats'=>array('index'),
	'Create',
);

$this->menu=array(
	array('label'=>'List Zzgameseat', 'url'=>array('index')),
	array('label'=>'Manage Zzgameseat', 'url'=>array('admin')),
);
?>

<h1>Create Zzgameseat</h1>

<?php echo $this->renderPartial('_form', array('model'=>$model)); ?>