var jwt = require('jsonwebtoken')

module.exports.verificaAcesso = function (req, res, next){
    var myToken = req.query.token || req.body.token
    if(myToken){
      jwt.verify(myToken, "EngWeb2024", function(e, payload){
        if(e){
          res.status(401).jsonp({error: e})
        }
        else{
          console.log(payload)
          next()
        }
      })
    }
    else{
      res.status(401).jsonp({error: "Token inexistente!"})
    }
  }

