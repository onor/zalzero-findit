<?php
$this->breadcrumbs=array(
	'Zzusers',
);

$this->menu=array(
	array('label'=>'Create Zzuser', 'url'=>array('create')),
	array('label'=>'Manage Zzuser', 'url'=>array('admin')),
);
?>

<h1>Zzusers</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>
