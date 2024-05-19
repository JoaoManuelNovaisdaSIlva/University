var express = require('express');
var router = express.Router();
var passport = require('passport')

/* GET home page. */
router.get('/', function(req, res, next) {
  console.log('Na cb do index...')
  console.log(req.sessionID)
  res.render('index');
});

// Login page
router.get('/login', function(req, res) {
  console.log('Na cb do GET login...')
  console.log(req.sessionID)
  res.render('login')
  })
  
router.post('/login', passport.authenticate('local'), 
  function(req, res){
       console.log('Na cb do POST login...')
       console.log('Auth: ' + JSON.stringify(req.user))
       res.redirect('/protegida')
  })

function verificaAutenticacao(req,res,next){
    console.log('User (verif.): ' + JSON.stringify(req.user))
    if(req.isAuthenticated()){
    //req.isAuthenticated() will return true if user is logged in
        next();
    } else{
      res.redirect("/login");
 }
 }
 

router.get('/protegida', verificaAutenticacao, 
  (req,res) => {
           res.send('Atingiste a área protegida!!!' + 'User: ' + JSON.stringify(req.user))
})
  
module.exports = router;
