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
				var datastring = $("#cadastro").serialize();
				console.log(datastring);

				event.preventDefault(); //prevenir o form de fazer submit

				$.ajax({
					type: "POST",
					url: "http://localhost:8000/emovie/users",
					data: datastring,
					dataType: "json",
					success: function(data) {
						console.log(data);
						if(data['success'] == true) {
							window.location.href = "cadastrofinalizado.php";
						} else {
							window.location.href = "erro.php";
						}
					},
					error: function(){
						window.location.href = "erro.php";
					}
				});
			});
		</script>

<?php
	include_once 'footer.php';
?>
