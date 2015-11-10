<?php
	$title = 'Cadastro - E-Movie';
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
						<h2 class="heading">Cadastro</h2>

						<center>
							<form id="cadastro">
						  		Nome Completo:<br/>
						  		<input type="text" name="name" required>
						  		<br/>
						  		Email:<br/>
						  		<input type="email" name="email" required>
								<br/>
								<br/>
								<input type="hidden" name="operation" value="create" required>
								<input class="link-button blue" type="submit" value="Cadastrar">
							</form>
						</center>
					</div>
				</div>
			</div>
		</div>
		<!-- ENDS MAIN -->

		<script type="text/javascript">
			$("#cadastro").submit(function(evento) {
				enviarInformacoes("cadastro", "users", "cadastrofinalizado.php", "erro.php");
			});
		</script>

<?php
	include_once 'footer.php';
?>
