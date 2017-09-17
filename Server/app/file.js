const fs = require('fs');

file_path = "./data/data.json";

function read_file() {
	rawdata = fs.readFileSync(file_path);
	json_data = JSON.parse(rawdata);
	return json_data;
}

exports.write_file_data = function(data) {
	let json_data = JSON.stringify(data);  
	fs.writeFileSync(file_path, json_data);  
}

exports.get_file_data = function() {
	try {
		return read_file();
	}
	catch(err) {
		console.log("No file to read.");
		return {};
	}
	
}
