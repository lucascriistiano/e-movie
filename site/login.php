<?php
$title = 'Login - E-Movie';
$home = false;
include_once 'header.php';
?>

<!-- MAIN -->
<div id="main">
	<div class="wrapper cf">
		<!-- page content-->
		<div id="page-content" class="cf">

			<!-- entry-content -->
			<div class="entry-content cf">
				<h2 class="heading">Login</h2>

				<center>
					<form id="cadastro">
						Email:<br/>
						<input type="email" name="email" required>
						<br/>
						Senha:<br/>
						<input type="password" name="password" required>
						<br/>
						<input class="link-button blue" type="submit" value="Entrar">
					</form>
				</center>
			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<?php
include_once 'footer.php';
?>