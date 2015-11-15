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
						<input type="password" name="senha" required>
						<br/>
						<br/>
						<input type="hidden" name="operation" value="login" required>
						<input class="link-button blue" type="submit" value="Entrar">
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

	  var retorno;
	  $.ajax({
	  	type: "GET",
	  	url: "http://localhost:8000/emovie/users",
	  	data: datastring,
	  	dataType: "json",
	  	success: function(data) {
	  		if(data != null) {
				switch (data["type"]) {
					case "ADMIN":
						window.location.href = 'admin/index.php';
						break;
					case "VENDAS":
						window.location.href = 'vendas/index.php?id_user='+ data["id"];
						break;
					default:
						window.location.href = 'index.php';
						break;
				}
			} else {
				alert("Login/Senha incorretos!");
			}
	  	},
	  	error: function(){
				console.log("Um erro ocorreu!");
	  	}
	  });

	  return retorno;
	});

</script>

<?php
include_once 'footer.php';
?>
