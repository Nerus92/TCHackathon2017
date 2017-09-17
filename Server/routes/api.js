var express = require('express');
var data = require('../app/data');
var router = express.Router();


/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('API HOME');
});

router.get('/all', function(req, res, next) {
  res.send(data.get_data());
});

router.post('/all', function(req, res, next) {
  console.log(req.body);

  if ("data" in req.body) {
    data.set_data(JSON.parse(req.body.data));
  }

  res.send('ok');
});


module.exports = router;
