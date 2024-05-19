var express = require('express');
var router = express.Router();
var Plantas = require('../controllers/plantas')

router.get('/', function(req, res){
    if(req.query.especie){
        console.log(req.query.especie)
        Plantas.findByEspecie(req.query.especie)
        .then(data => res.status(203).jsonp(data))
        .catch(erro => res.status(503).jsonp(erro))
    }else if(req.query.implant){
        Plantas.findByImplant(req.query.implant)
        .then(data => {res.status(204).jsonp(data)})
        .catch(erro => res.status(504).jsonp(erro))
    }
    else{
        Plantas.list()
        .then(data => res.status(201).jsonp(data))
        .catch(erro => res.status(501).jsonp(erro))
    }
});

router.get('/freguesias', function(req, res){
    Plantas.findFreguesias()
    .then(data => res.status(205).jsonp(data))
    .catch(erro => res.status(505).jsonp(erro))
});

router.get('/especies', function(req, res){
    Plantas.findEspecies()
    .then(data => res.status(206).jsonp(data))
    .catch(erro => res.status(506).jsonp(erro))
});

router.post('/', function(req, res){
    Plantas.insert(req.body)
    .then(data => res.status(207).jsonp(data))
    .catch(erro => res.status(507).jsonp(erro))
});

router.delete('/:id', function(req, res){
    Plantas.delete(req.params.id)
    .then(data => res.status(208).jsonp(data))
    .catch(erro => res.status(508).jsonp(erro))
});

router.get('/:id', function(req, res){
    Plantas.findByID(req.params.id)
    .then(data => res.status(202).jsonp(data))
    .catch(erro => res.status(502).jsonp(erro))
});

module.exports = router;