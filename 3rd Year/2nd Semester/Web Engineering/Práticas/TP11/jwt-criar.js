var jwt = require('jsonwebtoken');
try{
    var token = jwt.sign(
      { username: 'jcr', level: 'editor' }, 
      'EngWeb2024',
      {expiresIn: 100});
    console.log('Token: ' + token);
}
catch(e){
    console.log('Erro na criaÃ§Ã£o do token: ' + e)
}