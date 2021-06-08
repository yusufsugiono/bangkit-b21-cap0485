const data = require('./data');

const getAllDataHandler = (request, h) => {
	// get query
	const {date} = request.query;

	// if there is query
	if (date) {
		// filter the data based on query (date)
		const filteredData = data.filter(
      (d) => d.tanggal.indexOf(date) !== -1);

		// if filtered data empty
		// return 404 , data not found
		if (filteredData.length == 0) {
			const response = h.response({
				status: 'fail',
				message: 'data not found'
			});
			response.code(404);
			return response;
		}

		// if filtered data not empty
		// return 200 and the filtered data
		const response = h.response({
			status: 'success',
			data: filteredData.sort(function(a, b){
		    return b.prediksi - a.prediksi;
			}),
		});
		response.code(200);
		return response;
	}

	// if there isnt query
	// return all data
	const response = h.response({
		status: 'success',
		data: data.sort(function(a, b){
	    return b.prediksi - a.prediksi;
		})
	});
	response.code(200);
	return response;
};

module.exports = { getAllDataHandler };