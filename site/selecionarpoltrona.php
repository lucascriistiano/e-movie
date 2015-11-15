<?php
$title = 'Seleção de Poltrona - E-Movie';
$home = false;
include_once 'header.php';
?>

<script>
	function setOccupied(chairCode) {
		$("#" + chairCode).attr("src", occupiedImg);
	}

	function setFree(chairCode) {
		$("#" + chairCode).attr("src", freeImg);
	}

	function setCurrent(chairCode) {
		$("#" + chairCode).attr("src", selectedImg);
	}

	var imgFolder = "img/chair/";
	var freeImg = imgFolder + "free.png";
	var occupiedImg = imgFolder + "occupied.png";
	var selectedImg = imgFolder + "selected.png";
	var selectedChair = '';

	var exhibitionId;
	var chairs;
	var roomRows;

	function loadExhibitionRoomRows() {
		// Get number of rows of room from server
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/exhibitions/" + exhibitionId,
			dataType: "json",
			async: false,
			success: function(data) {
				var exhibition = data;
				var room = exhibition['room']
				roomRows = parseInt(room['rows'])
			},
			error: function(){
				window.location.href = "erro.php";
			}
		});
	}

	function updateState() {
		// Get chair state from server
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/emovie/chairs",
			data: 'id_exhibition=' + exhibitionId,
			dataType: "json",
			success: function(data) {
				chairs = data;
				updateView();
			},
			error: function(){
				window.location.href = "erro.php";
			}
		});
	}

	function updateView() {
		// Updates table exhibition
		for (var chairCode in chairs) {
			if(chairs[chairCode] == 0) {
				setFree(chairCode);
			} else {
				setOccupied(chairCode);
			}
		}

		// Check if the chair was taken
		if(chairs[selectedChair] == 1) {
			releaseCurrentChair();
			alert('A poltrona que você havia selecionado foi tomada');
		} else {
			// Maintains the image on chair selected by user
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
		loadExhibitionRoomRows();

		var rowCodes = [ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				 		 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];
		var rowChairs = 10;

		var tableContent = '';
		//Gera linhas com cadeiras
		for (var row = 0; row < roomRows; row++){
			var rowCode = rowCodes[row];
			tableContent += '<tr>';
			tableContent += '<td class="chair-number">' + rowCode +'</td>';
			for (var j = 1; j <= rowChairs; j++) {
				var chairNumber = ("0" + j).slice(-2);
				var chairCode = rowCode + chairNumber;
				tableContent += '<td><a href="#" target="_self">' + '<img id="' + chairCode + '" class="chair-img" src="" onclick="selectChair(\'' + chairCode + '\')"/>' +'</a></td>';
			}
			tableContent += '</tr>';
		}

		// Generates last row
		tableContent += '<tr>';
		tableContent += '<td></td>';
		for (var chairNumber = 1; chairNumber <= rowChairs; chairNumber++){
			tableContent += '<td class="chair-number">' + (chairNumber) + '</td>';
		}
		tableContent += '<td></td>';
		tableContent += '</tr>';
		$('#table-chairs').append(tableContent);
	}

	function checkValidId () {
		if(exhibitionId == '') {
			alert("É necessário selecionar o filme e exibição antes da escolha da poltrona");
			window.location.href = "/index.php";
		} else {
			// Check if id is invalid
			$.ajax({
				type: "GET",
				url: "http://localhost:8000/emovie/chairs",
				data: 'id_exhibition=' + exhibitionId,
				dataType: "json",
				success: function(data) {
					if(data == null) {
						alert("O id não corresponde a nenhuma exibição cadastrada");
						window.location.href = "index.php";
					}
				},
				error: function(){
					window.location.href = "erro.php";
				}
			});
		}
	}

	$(document).ready(function () {
		exhibitionId = getQueryVariable('id_exhibition');
		checkValidId();

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

				<form id="select-chair">
					<input class="link-button blue" type="submit" value="Selecionar">
				</form>
			</div>
		</div>
	</div>
</div>
<!-- ENDS MAIN -->

<script type="text/javascript">
	// Add listener to button click to redirect to buy screen
	$("#select-chair").submit(function(evento) {
		event.preventDefault(); //prevenir o form de fazer submit
		if(selectedChair != '') {
			window.location.href = "finalizarcompra.php?id_exhibition=" + exhibitionId + "&chair_num=" + selectedChair;
		} else {
			alert('Você deve selecionar uma poltrona para prosseguir');
		}
	});


</script>

<?php
include_once 'footer.php';
?>