<?php
$title = 'Cadastro Finalizado - E-Movie';
$home = false;
include_once 'header.php';
?>

<script>
	var chairs = { 'A01': 1, 'A02': 0, 'A03': 0, 'A04': 0, 'A05': 0, 'A06': 0, 'A07': 0, 'A08': 0, 'A09': 0, 'A10': 0,
				   'B01': 1, 'B02': 1, 'B03': 0, 'B04': 0, 'B05': 1, 'B06': 0, 'B07': 0, 'B08': 0, 'B09': 0, 'B10': 0,
				   'C01': 0, 'C02': 0, 'C03': 0, 'C04': 0, 'C05': 0, 'C06': 0, 'C07': 0, 'C08': 0, 'C09': 0, 'C10': 0,
				   'D01': 0, 'D02': 0, 'D03': 0, 'D04': 0, 'D05': 0, 'D06': 0, 'D07': 0, 'D08': 0, 'D09': 0, 'D10': 0,
				   'E01': 0, 'E02': 0, 'E03': 0, 'E04': 0, 'E05': 0, 'E06': 1, 'E07': 0, 'E08': 0, 'E09': 0, 'E10': 0,
				   'F01': 1, 'F02': 1, 'F03': 0, 'F04': 0, 'F05': 0, 'F06': 0, 'F07': 0, 'F08': 0, 'F09': 0, 'F10': 0,
				   'G01': 0, 'G02': 0, 'G03': 0, 'G04': 0, 'G05': 0, 'G06': 0, 'G07': 0, 'G08': 0, 'G09': 0, 'G10': 0,
				   'H01': 0, 'H02': 0, 'H03': 0, 'H04': 0, 'H05': 0, 'H06': 0, 'H07': 0, 'H08': 0, 'H09': 0, 'H10': 0,
				   'I01': 0, 'I02': 0, 'I03': 0, 'I04': 0, 'I05': 0, 'I06': 0, 'I07': 0, 'I08': 0, 'I09': 0, 'I10': 1 }

	var imgFolder = "img/chair/";
	var freeImg = imgFolder + "free.png";
	var occupiedImg = imgFolder + "occupied.png";
	var selectedImg = imgFolder + "selected.png";
	var selectedChair = '';

	function setOccupied(chairCode) {
		$("#" + chairCode).attr("src", occupiedImg);
	}

	function setFree(chairCode) {
		$("#" + chairCode).attr("src", freeImg);
	}

	function setCurrent(chairCode) {
		$("#" + chairCode).attr("src", selectedImg);
	}

	function updateState() {
		// Consulta dados de estado do servidor


		// Atualiza exibição da tabela
		for (var chairCode in chairs) {
			if(chairs[chairCode] == 0) {
				setFree(chairCode);
			} else {
				setOccupied(chairCode);
			}
		}

		// Verifica se a poltrona do usuário foi tomada
		if(chairs[selectedChair] == 1) {
			releaseCurrentChair();
			alert('A poltrona que você havia selecionado foi tomada');
		} else {
			// Mantém a imagem da poltrona selecionada pelo usuário
			setCurrent(selectedChair);
		}
	}

	function releaseCurrentChair() {
		if(chairs[selectedChair] == 0) {
			$("#" + selectedChair).attr("src", freeImg);
		} else {
			$("#" + selectedChair).attr("src", occupiedImg);
		}

		selectedChair = '';
		$('#selected-chair').text(selectedChair);
	}

	function selectChair(chairCode) {
		if(chairs[chairCode] == 0) {
			if(selectedChair != '') {
				releaseCurrentChair();
			}

			setCurrent(chairCode);
			selectedChair = chairCode;
			// alert("Poltrona selecionada");
		} else {
			alert("Poltrona já está ocupada");
		}

		$('#selected-chair').text(selectedChair);
	}

	function createTable() {
		var fileiras = [ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'];
		var numLinhas = fileiras.length;
		var numColunas = 10;

		var tableContent = '';

		//Gera linhas com cadeiras
		for (var linha = 0; linha < numLinhas; linha++){
			var fileira = fileiras[linha];

			tableContent += '<tr>';
			tableContent += '<td class="chair-number">' + fileira +'</td>';
			for (var j = 1; j <= numColunas; j++) {
				var coluna = ("0" + j).slice(-2);
				var chairCode = fileira + coluna;

				tableContent += '<td><a href="#" target="_self">' + '<img id="' + chairCode + '" class="chair-img" src="" onclick="selectChair(\'' + chairCode + '\')"/>' +'</a></td>';
			}
			tableContent += '</tr>';
		}

		//Gera última linha
		tableContent += '<tr>';
		tableContent += '<td></td>';
		for (var k = 1; k <= numColunas; k++){
			tableContent += '<td class="chair-number">' + (k) + '</td>';
		}
		tableContent += '<td></td>';
		tableContent += '</tr>';

		$('#table-chairs').append(tableContent);
	}

	$(document).ready(function () {
		createTable();
		updateState();
		setInterval(function(){updateState()},2000);
	});
</script>

<!-- MAIN -->
<div id="main">
	<div class="wrapper cf">
		<!-- page content-->
		<div id="page-content" class="cf">
			<!-- entry-content -->
			<div class="entry-content cf">
				<h2 class="heading">Seleção de Poltrona</h2>

				<p>Selecione abaixo a poltrona para a exibição de filme escolhida.</p>

				<table id="table-chairs">
					<!-- automatic table -->
				</table>

				<p>Poltrona atual: <strong><span id="selected-chair"></span></strong></p>

				<form id="chair-selection">
					<input type="hidden" name="operation" value="" required>
					<input class="link-button blue" type="submit" value="Selecionar">
				</form>
			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<?php
include_once 'footer.php';
?>