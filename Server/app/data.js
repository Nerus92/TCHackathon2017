var local_file = require('./file');

//var local_data = {"test_data" : "true"};
var local_data = local_file.get_file_data();

exports.get_data = function()
{
	return local_data;
}

exports.set_data = function(data)
{
	// merge with local data
	//local_data = Object.assign({}, local_data, data);
	console.log("INPUT" + data);
	console.log(" . . .");

	// No merge for now, just use new data if lastUpdate is higher
	let json_data = JSON.parse(data);
	remote_lastUpdated = json_data.lastUpdated;

	if (remote_lastUpdated == "undefined")
		return false;

	local_lastUpdated = local_data['lastUpdated'];

	console.log("local is "+ local_lastUpdated);
	console.log("remote is "+ remote_lastUpdated);
	if ((local_lastUpdated == undefined) || (remote_lastUpdated > local_lastUpdated))
	{
		console.log("update server data");
		// write to disk
		local_data = json_data;
		local_file.write_file_data(data);
		console.log("data is now" +  local_data['lastUpdated']);
		return true;
	}

	return false;
}


function parse_input(data) {

	// merge existing and incoming inputs

	// first merge osbsolete
	local_data['obsolete'] = Object.assign({}, local_data['obsolete'], data['obsolete']);

}
