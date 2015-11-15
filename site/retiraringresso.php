<?php
$title = 'Retirar Ingresso - E-Movie';
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
				<h2 class="heading">Retirar Ingresso</h2>

				<center>
					<form id="cadastro">
						Token:<br/>
						<input type="text" name="token" size="25">
						<br/>
						<br/>
						<input type="hidden" name="operation" value="retrieveToken" required>
						<input class="link-button blue" type="submit" value="Consultar">
					</form>
				</center>
				<br/>
				<br/>

				<div id="comprovante" style="display: none; background-color: white;">
					<center>
						</br>
						<h3>Ingresso</h3>
						<br/>
						<h2 id="ticket"></h2>
						<br/>
						<h3 id="email"></h3>
						<br/>
						<h4 id="filme"></h4>
						<br/>
						<h4 id="horario"></h4>
						<br/>
						<h4 id="sala"></h4>
						<h4 id="cadeira"></h4>
						</br>
					</center>
				</div>
				</br>
				</br>

				<div id="erro" style="display: none;">
					<center>
						<h3>Nenhum ingresso correspondente ao token inserido</h3>
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

	function mountHorario(session) {
		var sessionDay = daysWeek[parseInt(session['dayWeek'])];
		var sessionTime = getFormattedTime(session['hour']);

		return sessionDay + ', ' + sessionTime;
	}
</script>

<?php
include_once 'footer.php';
?>
