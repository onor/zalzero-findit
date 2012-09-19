<?php
$this->breadcrumbs=array(
	'Zzgameinsts',
);

$this->menu=array(
	array('label'=>'Create Zzgameinst', 'url'=>array('create')),
	array('label'=>'Manage Zzgameinst', 'url'=>array('admin')),
);
?>

<h1>Zzgameinsts</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>
