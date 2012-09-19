<?php
$this->breadcrumbs=array(
	'Zzgameinsts'=>array('index'),
	'Manage',
);

$this->menu=array(
	array('label'=>'List Zzgameinst', 'url'=>array('index')),
	array('label'=>'Create Zzgameinst', 'url'=>array('create')),
);

Yii::app()->clientScript->registerScript('search', "
$('.search-button').click(function(){
	$('.search-form').toggle();
	return false;
});
$('.search-form form').submit(function(){
	$.fn.yiiGridView.update('zzgameinst-grid', {
		data: $(this).serialize()
	});
	return false;
});
");
?>

<h1>Manage Zzgameinsts</h1>

<p>
You may optionally enter a comparison operator (<b>&lt;</b>, <b>&lt;=</b>, <b>&gt;</b>, <b>&gt;=</b>, <b>&lt;&gt;</b>
or <b>=</b>) at the beginning of each of your search values to specify how the comparison should be done.
</p>

<?php echo CHtml::link('Advanced Search','#',array('class'=>'search-button')); ?>
<div class="search-form" style="display:none">
<?php $this->renderPartial('_search',array(
	'model'=>$model,
)); ?>
</div><!-- search-form -->

<?php $this->widget('zii.widgets.grid.CGridView', array(
	'id'=>'zzgameinst-grid',
	'dataProvider'=>$model->search(),
	'filter'=>$model,
	'columns'=>array(
		'gameinst_id',
		'gameinst_game_id',
		'gameinst_product_id',
		'gameinst_starttime',
		'gameinst_endtime',
		'gameinst_entrytime',
		/*
		'gameinst_capacity',
		'gameinst_threshold',
		'gameinst_paramnum1',
		'gameinst_paramnum2',
		'gameinst_paramnum3',
		'gameinst_paramnum4',
		'gameinst_paramnum5',
		'gameinst_paramnum6',
		'gameinst_paramnum7',
		'gameinst_paramnum8',
		'gameinst_paramnum9',
		'gameinst_paramnum10',
		'gameinst_paramdate1',
		'gameinst_paramdate2',
		'gameinst_paramdate3',
		'gameinst_paramdate4',
		'gameinst_paramdate5',
		'gameinst_paramvar1',
		'gameinst_paramvar2',
		'gameinst_paramvar3',
		'gameinst_paramvar4',
		'gameinst_paramvar5',
		*/
		array(
			'class'=>'CButtonColumn',
		),
	),
)); ?>
