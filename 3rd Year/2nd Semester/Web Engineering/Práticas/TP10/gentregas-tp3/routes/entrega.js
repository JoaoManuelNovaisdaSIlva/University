/* Operações CRUD sobre Entrega 
  2024-04-21 @jcr
   ----------------------- */
var express = require('express');
var router = express.Router();
var Entrega = require('../controllers/entrega')
var Projeto = require('../controllers/projeto')
var EntregaRemovida = require('../controllers/entregaRemovida')
const Auth = require('../auth/auth')

var multer = require('multer')
var fs = require('fs')

var upload = multer({dest : 'uploads'})

/* Listar as Entrega (R) */
router.get('/', function(req, res) {
  if(req.query.projeto){
    Entrega.findByProject(req.query.projeto)
        .then(data => res.jsonp(data))
        .catch(erro => res.jsonp(erro))
  }else{
    Entrega.list()
        .then(data => res.jsonp(data))
        .catch(erro => res.jsonp(erro))
  }
});

/* Consultar uma Entrega (R) */
router.get('/:id', function(req, res) {
    Entrega.findById(req.params.id)
      .then(data => res.jsonp(data))
      .catch(erro => res.jsonp(erro))
  });

/* Criar uma Entrega (C) */
router.post('/', upload.single('submisao'), function(req, res) {
  oldPath = __dirname + '/../' + req.file.path
  newPath = __dirname + '/../FileStore/Entrega/' + req.body.id + "." + req.file.originalname.split(".")[1]

  console.log("old: " + oldPath);
  console.log("new: " + newPath);

  fs.renameSync(oldPath, newPath)

  var ent = {
    _id : req.body.id,
    creationDate : new Date(),
    uc : req.body.uc,
    projectId : req.body.projectId,
    designacao : req.body.designacao,
    idEquipa : req.body.idEquipa,
    desigEquipa : req.body.desigEquipa,
    ficheiro : req.body.id + "." + req.file.originalname.split(".")[1],
    obs : req.body.obs
  }


  Entrega.insert(ent)
    .then(data => res.jsonp(data))
    .catch(erro => res.jsonp(erro))
});

/* Alterar uma Entrega (U) */
router.put('/:id', function(req, res) {
    Entrega.update(req.params.id, req.body)
      .then(data => res.jsonp(data))
      .catch(erro => res.jsonp(erro))
  });

/* Remover uma Entrega (D ) */
router.delete('/:id', Auth.verificaAcesso, function(req, res) {
    Entrega.remove(req.params.id)
      .then((entrega) => {
        var entregaRemoved = {
          _id : entrega._id,
          deleteDate : new Date(),
          uc : entrega.uc,
          projectId : entrega.projectId,
          idEquipa : entrega.idEquipa,
          justificacao : req.body.justificacao
        }
        EntregaRemovida.insert(entregaRemoved)
          .then(obj => {
            fs.unlinkSync(__dirname + "/../FileStore/Entrega/" + entrega.ficheiro)
            res.jsonp(obj)
          })
          .catch(erro => res.jsonp(erro))
        console.log("Deleted " + req.params.id)
      })
      .catch(erro => res.jsonp(erro))
  });

module.exports = router;
