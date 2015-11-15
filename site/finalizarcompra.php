<?php
$title = 'Finalizar Compra - E-movie';
$home = false;
include_once 'header.php';
?>

<script language="javascript">
	function somente_numero(campo){
		var digits = "0123456789"
		var x, z;
		var campo_temp;

		for (var i = 0; i < campo.value.length; i++) {
			campo_temp = campo.value.substring(i,i+1)
			if (digits.indexOf(campo_temp) == -1) {
				campo.value = campo.value.substring(0,i);
			}

			x = campo.value;
			z = x.substring(0,1);
			if(z == 4) {
				document.getElementById('master').src = "";
				document.getElementById('visa').src = "img/bandeiras/visa.gif";
			}
			if(z == 5) {
				document.getElementById('visa').src = "";
				document.getElementById('master').src = "img/bandeiras/master.gif";
			}
			if(z != 4 && z != 5) {
				document.getElementById('master').src = "img/bandeiras/master.gif";
				document.getElementById('visa').src = "img/bandeiras/visa.gif";
				alert("Cartão não aceito, digite um Visa ou um Master");
				campo.value = "";
			}
		}
	}

	function somente_numeroSeguranca(campo) {
		var digits="0123456789";
		var campo_temp;
		for (var i = 0; i < campo.value.length; i++) {
			campo_temp = campo.value.substring(i,i+1);
			if(digits.indexOf(campo_temp) == -1) {
				campo.value = campo.value.substring(0,i);
			}
		}
	}

	function loadExhibitionPrice() {
		// Check if exhibition_id and chair_num are valid
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/exhibitions/" + exhibitionId,
			dataType: "json",
			success: function(data) {
				var exhibition = data;
				var price = parseFloat(exhibition['price']).toFixed(2);
				$("#ticket-price").val(price);
			},
			error: function(){
				window.location.href = "erro.php";
			}
		});
	}

	function checkValidIds () {
		if(exhibitionId == '') {
			alert("É necessário selecionar o filme e exibição antes da escolha da poltrona");
			window.location.href = "/index.php";
		} else {
			// Check if exhibition_id and chair_num are valid
			$.ajax({
				type: "GET",
				url: "http://localhost:8000/emovie/chairs",
				data: 'id_exhibition=' + exhibitionId,
				dataType: "json",
				success: function(data) {
					if(data == null) {
						alert("O id não corresponde a nenhuma exibição cadastrada");
						window.location.href = "index.php";
					} else {
						//Valid exhibition id
						var chairs = data;
						var selectedChair = chairs[numChair];

						if(selectedChair != 0) {
							//Occuped chair
							alert("A poltrona selecionada encontra-se indisponível. Escolha outra poltrona para continuar a compra.");
							window.location.href = "selecionarpoltrona.php?id_exhibition=" + exhibitionId;
						} else {
							// define hidden inputs values
							$("#id_exhibition").val(exhibitionId);
							$("#chair_num").val(numChair);
							loadExhibitionPrice();
						}
					}
				},
				error: function(){
					window.location.href = "erro.php";
				}
			});
		}
	}

	var exhibitionId, numChair;

	$(document).ready(function() {
		exhibitionId = getQueryVariable('id_exhibition');
		numChair = getQueryVariable('chair_num');
		checkValidIds();
	});
</script>

<div id="main">
	<div class="wrapper cf">
		<!-- page content-->
		<div id="page-content" class="cf">
			<!-- entry-content -->
			<div class="entry-content cf">
				<h2 class="heading">Finalizar Compra</h2>
				<p>Preencha os campos abaixo para concluir a sua compra:</p>

				<form id="finalizar-compra">
					<div id="payment-info">
						<h3 class="heading">Informações de Cobrança</h3>
						Nome no Cartão:
						<input type="text" name="name" required>
						</br></br>

						Número do Cartão:
						<input type="text" id="numeroDoCartao" name="card_number" onKeyUp="javascript:somente_numero(this); bandeira(this);" value="" maxlength="16" required>
						<img src="img/bandeiras/visa.gif" width="42" height="35" border="0"  id ="visa">
						<img src="img/bandeiras/master.gif" width="42" height="35" border="0"  id ="master"  >
						</br></br>

						Dígito de Segurança:
						<input type="text" name="security_digit"  size="1" onfocus="this.value='';" onKeyUp="javascript:somente_numeroSeguranca(this)" maxlength="3" required>
						</br></br>

						Vencimento:
						<input type="text" name="expiration_month" onKeyUp="javascript:somente_numeroSeguranca(this)" size="2" maxlength="2" required>
						/
						<input type="text" name="expiration_year" maxlength="2" size="2" onKeyUp="javascript:somente_numeroSeguranca(this)" required>
						</br></br>

						Valor do Ticket: R$
						<input id="ticket-price" type="text" name="ticket_price" readonly="readonly"/>
						</br></br>

						Forma de Pagamento:
						<input type="text" name="payment_form" value="Somente Débito" readonly="readonly"/>
						</br></br>
					</div>

					<div id="authentication-info">
						<h3 class="heading">Autenticação</h3>
						Email cadastrado:<br/>
						<input type="email" name="email" required>
						<br/>

						Senha:<br/>
						<input type="password" name="password" required>
						<br/>
						<br/>

						<input id="id_exhibition" type="hidden" name="id_exhibition">
						<input id="chair_num" type="hidden" name="chair_num">
						<input type="hidden" name="purchase_location" value="internet">
						<input type="hidden" name="operation" value="create">
					</div>

					<input class="link-button blue" type="submit" value="Comprar">
					<input class="link-button blue"  type="reset" value="Limpar Dados">
				</form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$("#finalizar-compra").submit(function(evento) {
		var serializedData = $("#authentication-info input").serialize();
		sendSerializedData(serializedData, "tickets", "comprafinalizada.php", "erro.php");
	});
</script>

<?php
include_once 'footer.php';
?>