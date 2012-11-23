<!DOCTYPE html>
<html lang="en">
<head>
<title>Dashboard</title>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/style.css" rel="stylesheet" media="screen">

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid rootnode">
		<div class="row-fluid">
			<div class="span4 login-wrapper" >
				<form
					action="http://rocketgeek.com/filter-hooks/bootstrap-icons-for-the-login-form/"
					method="POST" class="form">
					<fieldset>
						<label for="username">Username</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-user"></i> </span><input name="log" type="text" id="log inputIcon" value="" class="username" placeholder="user name">
							</div>
						<label for="password">Password</label>

							<div class="input-prepend">
								<span class="add-on"><i class="icon-lock"></i> </span><input placeholder="password" name="pwd" type="password" id="pwd inputIcon" class="password">
							</div>

						<input type="hidden" name="redirect_to"
							value="http://rocketgeek.com/filter-hooks/bootstrap-icons-for-the-login-form/"><input
							name="a" type="hidden" value="login">
							
						<div class="button_div">
							<input name="rememberme" type="checkbox" id="rememberme"
								value="forever">&nbsp;Remember me&nbsp;&nbsp;<input
								type="submit" name="Submit" value="Login"
								class="btn btn-primary">
						</div>
						<div class="clear"></div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
