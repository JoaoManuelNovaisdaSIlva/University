var express = require('express');
var router = express.Router();
var Aluno = require('../controllers/aluno')

router.get('/alunos', function(req, res) {
  Aluno.list()
  .then(data => res.jsonp(data))
  .catch(erro => res.status(521).jsonp(erro))
});

router.get('/alunos/:id', function(req, res){
  Aluno.findById(req.params.id)
  .then(data => res.jsonp(data))
  .catch(erro => res.status(522).jsonp(erro))
});

router.post('/alunos', function(req, res){
  console.log(req.body);
  Aluno.insert(req.body)
    .then(data => res.status(201).jsonp(data))
    .catch(erro => res.status(523).jsonp(erro))
});

module.exports = router;
