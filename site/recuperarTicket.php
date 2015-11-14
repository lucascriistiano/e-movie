<?php
$title = 'Recuperar Ticket - E-Movie';
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
				<h2 class="heading">Recuperar Ticket</h2>

				<center>
					<form id="cadastro">
						Ticket:<br/>
						<input type="text" name="token">
						<br/>
						<br/>
						<input type="hidden" name="operation" value="retrieveToken" required>
						<input class="link-button blue" type="submit" value="Consultar">
					</form>
				</center>

				<div id="comprovante" style="display: none;">
					<br/>
					<br/>
					<center>
						<h1>Seu Ticket</h1>
						<br/>
						<h2 id="ticket">afionsafoiasn</h2>
						<br/>
						<h3 id="email">exemplo@email.com</h3>
						<br/>
						<h4 id="filme">Vingadores</h4>
						<br/>
						<h4 id="horario">20/10/2015 10:35</h4>
						<br/>
						<h4 id="sala">1</h4>
						<h4 id="cadeira">1</h4>
					</center>
				</div>

				<div id="erro" style="display: none;">
					<center>
						<h1>Nada encontrado!</h1>
					</center>
				</div>

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
	  	url: "http://localhost:8000/emovie/tickets",
	  	data: datastring,
	  	dataType: "json",
	  	success: function(data) {
	  		if(data != null) {
					preencherTicket(data);
				} else {
					$("#comprovante").hide();
					$("#erro").show();
				}
	  	},
	  	error: function(){
				$("#comprovante").hide();
				$("#erro").show();
	  	}
	  });

	  return retorno;
	});

	function preencherTicket($data) {
		$("#comprovante").show();
		$("#erro").hide();

		document.getElementById("ticket").innerHTML = $data["token"];
		document.getElementById("email").innerHTML = $data["user"]["email"];
		document.getElementById("filme").innerHTML = $data["exhibition"]["movie"]["name"];
		document.getElementById("horario").innerHTML = mountHorario($data["exhibition"]["session"]);
		document.getElementById("sala").innerHTML = "Sala: " + $data["exhibition"]["room"]["id"];
		document.getElementById("cadeira").innerHTML = "Cadeira: " + $data["chairNumber"];
	}

	function mountHorario($horario) {
		var retorno = "";
		switch($horario["dayWeek"]) {
			case 0:
				retorno = "Domingo";
				break;
			case 1:
				retorno = "Segunda";
				break;
			case 2:
				retorno = "Terça";
				break;
			case 3:
				retorno = "Quarta";
				break;
			case 4:
				retorno = "Quinta";
				break;
			case 5:
				retorno = "Sexta";
				break;
			case 6:
				retorno = "Sábado";
				break;
		}

		retorno += ", " + $horario["hour"].substring(12, $horario["hour"].length);

		return retorno;
	}
</script>

<?php
include_once 'footer.php';
?>
