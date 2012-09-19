<?php
/*
<?php echo CHtml::beginForm('','post',array
('enctype'=>'multipart/form-data'))?>
<?php echo CHtml::textField('first_name')?>
<?php echo CHtml::textField('last_name')?>
<?php echo CHtml::textField('email')?>
<?php echo CHtml::textField('subject')?>
<?php echo CHtml::submitButton('Upload')?>
<?php echo CHtml::endForm()?>
*/

?>

<!-- this is the template of help contact us form -------- --> 

<div class="contactus">
            <form id="help_contact_form" method="post" onsubmit="return false">
			<ul>
				<li><label>First Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</label><input maxlength="50"name="first_name" type="text"></li>
                                <li><label>Last Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</label><input maxlength="50" name="last_name" type="text"></li>
				<li><label>Email Address &nbsp;&nbsp;&nbsp;:</label><input maxlength="100" name="email" type="text"></li>
				<li><label>Subject &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</label>
                                                            <select class="styled"id="id-contactus-subject"name="subject" >
                                                                <option value="General feed-back or question">General feed-back or question</option>
                                                                <option value="Issue with game">Issue with game</option>
                                                                <option value="Issue with other player">Issue with other player</option>
                                                               <option value="Other technical issues"> Other technical issues</option>
                                                            </select>
                                                            <!--<div class="dropdown-img"></div>-->
                                                        </li>
				<li><textarea name="body" maxlength="2000"></textarea>
			</ul>	
			<div class="captcha">
					<span><?php $this->widget('CCaptcha')?></span>
					<input type="text" name="verifyCode" value="">
				<p>*Please fill in the same as appear, in the box</p>
			</div>
			<div class="submit">
				<input type="submit" id="upload_help_contactus" name="upload_help_contactus" value="">		
			</div>
	</form>
</div>



