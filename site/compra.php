<?php
	$title = 'Pagamento - emovie';
	$home = false;
	include_once 'header.php';
 ?>

<script language="javascript">

function somente_numero(campo){
var digits="0123456789"
var x;
var z;
var campo_temp
    for (var i=0;i<campo.value.length;i++){
        campo_temp=campo.value.substring(i,i+1)
        if (digits.indexOf(campo_temp)==-1){
            campo.value = campo.value.substring(0,i);
        }

		x = campo.value;
		z = x.substring(0,1);
		if(z==4){
			document.getElementById('master').src = "";
			document.getElementById('visa').src = "img/bandeiras/visa.gif";

		}
		if( z == 5){
			document.getElementById('visa').src = "";
			document.getElementById('master').src = "img/bandeiras/master.gif";

		}
		if(z != 4 && z != 5){

			document.getElementById('master').src = "img/bandeiras/master.gif";
			document.getElementById('visa').src = "img/bandeiras/visa.gif";

			alert("Cartão não aceito, digite um Visa ou um Master");

			campo.value = "";

		}



    }



}

function somente_numeroSeguranca(campo){
var digits="0123456789"
var campo_temp
    for (var i=0;i<campo.value.length;i++){
        campo_temp=campo.value.substring(i,i+1)
        if (digits.indexOf(campo_temp)==-1){
            campo.value = campo.value.substring(0,i);
        }

	}
}

$("#pagamento").submit(function(evento) {
				enviarInformacoes("pagamento", "users", "cadastrofinalizado.php", "erro.php");
});


</script>



<div id="main">
	<div class="wrapper cf">
		<!-- page content-->
				<div id="page-content" class="cf">
			<!-- entry-content -->
					<div class="entry-content cf">

						Digite os Campos abaixo para finalizar sua compra:
						<br>

						<form id="pagamento">
							<br>
							Nome no Cartão:<input type="text" name="nomeNoCartao" required>
							<br>
							<br>
							Número do Cartão:<input type="text" id="numeroDoCartao" name="numeroDoCartao" onKeyUp="javascript:somente_numero(this); bandeira(this);" value=""   maxlength="16" required> <img src="img/bandeiras/visa.gif" width="42" height="35" border="0"  id ="visa"   > <img src="img/bandeiras/master.gif" width="42" height="35" border="0"  id ="master"  >
							<br>
							<br>
							Número de Segurança:<input type="text" name="numeroDeSeguranca"  size="1" onfocus="this.value='';" onKeyUp="javascript:somente_numeroSeguranca(this)" maxlength="3"  required>
							<br>
							<br>
							Data de Vencimento:<input type="text" onKeyUp="javascript:somente_numeroSeguranca(this)" size="1" maxlength="2" name="MesVencimento" required> <input type="text" maxlength="2" size="1" onKeyUp="javascript:somente_numeroSeguranca(this)" name="AnoVencimento" required >
							<br>
							<br>
							Valor do Ticket:<input type="text" value="18,50" id="valorTicket" name="valorTicket" readonly="readonly"/>
							<br>
							<br>
							Forma de Pagamento:<input type="text" value="Somente À vista" name="pagamento" readonly="readonly"/>
							<br>
							<br>
							<input class="link-button blue" type="submit" value="Comprar">
							<input class="link-button blue"  type="reset" value="Limpar Dados">
						</form>

			</div>
		</div>
	</div>
</div>



<?php
	include_once 'footer.php';
?>
