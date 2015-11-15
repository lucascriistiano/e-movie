function sendFormData($id, $operation, $okPage, $failPage) {
	var datastring = $("#" + $id).serialize();
	event.preventDefault(); //prevenir o form de fazer submit
	return sendSerializedData(datastring, $operation, $okPage, $failPage);
}

function sendSerializedData($data, $operation, $okPage, $failPage) {
	event.preventDefault(); //prevenir o form de fazer submit
	$.ajax({
		type: "POST",
		url: "http://localhost:8000/emovie/" + $operation,
		data: $data,
		dataType: "json",
		success: function(data) {
			if(data['success'] == true) {
				window.location.href = $okPage;
			} else {
				window.location.href = $failPage;
			}
		},
		error: function(){
			window.location.href = $failPage;
		}
	});
}