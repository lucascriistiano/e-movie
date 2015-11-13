function enviarInformacoes($id, $operacao, $paginaok, $paginafail) {
	var datastring = $("#" + $id).serialize();

	var retorno;

	console.log(datastring);

  event.preventDefault(); //prevenir o form de fazer submit

  $.ajax({
  	type: "POST",
  	url: "http://localhost:8000/emovie/" + $operacao,
  	data: datastring,
  	dataType: "json",
  	success: function(data) {
  		console.log(data);
  		retorno = data;
  		if(data['success'] == true) {
  			window.location.href = $paginaok;
  		} else {
  			window.location.href = $paginafail;
  		}
  	},
  	error: function(){
  		window.location.href = $paginaok;
  	}
  });

  return retorno;
}